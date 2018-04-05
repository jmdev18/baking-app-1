package com.exercise.template.views.activities.main.fragments;

import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModelProviders;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.exercise.template.R;
import com.exercise.template.adapters.MainAdapter;
import com.exercise.template.api.models.Recipe;
import com.exercise.template.db.RecipeContract;
import com.exercise.template.db.RecipeProvider;
import com.exercise.template.views.activities.main.MainActivity;
import com.exercise.template.views.activities.main.viewmodels.MainViewModel;
import com.exercise.template.views.base.BaseFragment;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import timber.log.Timber;

/**
 * File Created by pandu on 31/03/18.
 */
public class MainFragment extends BaseFragment implements LoaderManager.LoaderCallbacks<Cursor> {

    public static final String TAG = MainFragment.class.getSimpleName();

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @Inject
    MainAdapter adapter;

    @Inject
    MainViewModel.Factory mainViewModelFactory;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.rlProgress)
    RelativeLayout rlProgress;

    @BindView(R.id.rlError)
    RelativeLayout rlError;

    private MainViewModel mainViewModel;

    Unbinder unbinder;

    private static final int LOADER_ID_RECIPES = 9090;

    public static MainFragment newInstance() {
        Bundle args = new Bundle();
        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        unbinder = ButterKnife.bind(this, view);

        toolbar.setTitle("Baking App");
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mainViewModel = ViewModelProviders.of(getActivity(), mainViewModelFactory)
                .get(MainViewModel.class);

        mainViewModel.getStatus().observe(this, status -> {
            assert status != null;
            switch (status) {
                case LOADING:
                    showLoader();
                    break;
                case SUCCESS:
                    hideLoader();
                    break;
                case FAILED:
                    showError();
                    break;
            }
        });

        mainViewModel.getNumberOfCols().observe(this, cols -> {
            if(cols == null) cols = 1;
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), cols));
        });

        //adapter.setData(recipes);
        mainViewModel.getRecipes().observe(this, this::insertRecipes);

        adapter.setRecipeListener(new MainAdapter.RecipeListener() {
            @Override
            public void onRecipeClick(Recipe selectedRecipe) {
                mainViewModel.getSelectedRecipe().setValue(selectedRecipe);
                ((MainActivity) getActivity()).gotoDetail();
            }

            @Override
            public void onFavouriteClick(String recipeId) {
                ContentValues values = new ContentValues();
                values.put(RecipeContract.COLUMN_DESIRED, 0);
                getActivity().getContentResolver().update(
                        RecipeProvider.Recipes.CONTENT_URI,
                        values,
                        null,
                        null
                );

                values = new ContentValues();
                values.put(RecipeContract.COLUMN_DESIRED, 1);
                getActivity().getContentResolver().update(
                        RecipeProvider.Recipes.CONTENT_URI_WITH_ID(recipeId),
                        values,
                        null,
                        null
                );
            }
        });

        recyclerView.setAdapter(adapter);
    }

    private void insertRecipes(List<Recipe> data){
        @SuppressLint("StaticFieldLeak")
        AsyncTask<Void, Void, Void> insertRecipes = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                Gson gson = new Gson();
                ContentValues[] recipes = new ContentValues[data.size()];

                for (int i = 0; i < data.size(); i++) {
                    ContentValues recipe = new ContentValues();
                    Recipe r = data.get(i);

                    recipe.put(RecipeContract.COLUMN_RECIPE_ID, r.getId());
                    recipe.put(RecipeContract.COLUMN_NAME, r.getName());
                    recipe.put(RecipeContract.COLUMN_IMAGE, r.getImage());
                    recipe.put(RecipeContract.COLUMN_SERVINGS, r.getServings());
                    recipe.put(RecipeContract.COLUMN_INGREDIENTS, gson.toJson(r.getIngredients()));
                    recipe.put(RecipeContract.COLUMN_STEPS, gson.toJson(r.getSteps()));
                    recipe.put(RecipeContract.COLUMN_INGREDIENTS_SIZE, r.getIngredients().size());
                    recipe.put(RecipeContract.COLUMN_STEPS_SIZE, r.getSteps().size());
                    recipe.put(RecipeContract.COLUMN_DESIRED, 0);

                    recipes[i] = recipe;
                }

                getActivity().getContentResolver()
                        .bulkInsert(RecipeProvider.Recipes.CONTENT_URI, recipes);
                return null;
            }
        };

        insertRecipes.execute();
    }

    public void showLoader() {
        rlProgress.setVisibility(View.VISIBLE);
        rlError.setVisibility(View.GONE);
    }

    public void hideLoader() {
        rlProgress.setVisibility(View.GONE);
    }

    public void showError() {
        rlProgress.setVisibility(View.GONE);
        rlError.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.btn_try_again)
    public void tryAgain(){
        mainViewModel.fetchRecipes();
    }

    @Override
    public void onResume() {
        super.onResume();

        getActivity()
                .getSupportLoaderManager()
                .initLoader(LOADER_ID_RECIPES, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(
                getContext(),
                RecipeProvider.Recipes.CONTENT_URI,
                RecipeContract.PROJECTION,
                null,
                null,
                null
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if(data.getCount() > 0) {
            adapter.setCursor(data);
        }
        else{
            mainViewModel.fetchRecipes();
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
