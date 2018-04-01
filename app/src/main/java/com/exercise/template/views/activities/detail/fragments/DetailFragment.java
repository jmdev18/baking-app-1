package com.exercise.template.views.activities.detail.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.exercise.template.R;
import com.exercise.template.adapters.IngredientAdapter;
import com.exercise.template.adapters.StepAdapter;
import com.exercise.template.api.models.Step;
import com.exercise.template.views.activities.detail.DetailActivity;
import com.exercise.template.views.activities.detail.viewmodels.DetailViewModel;
import com.exercise.template.views.activities.main.viewmodels.MainViewModel;
import com.exercise.template.views.base.BaseFragment;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import timber.log.Timber;

/**
 * File Created by pandu on 31/03/18.
 */
public class DetailFragment extends BaseFragment {

    public static final String TAG = DetailFragment.class.getSimpleName();

    @Inject
    IngredientAdapter ingredientAdapter;

    @Inject
    StepAdapter stepAdapter;

    @BindView(R.id.rv_ingredient)
    RecyclerView rvIngredient;

    @BindView(R.id.rv_step)
    RecyclerView rvStep;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    Unbinder unbinder;

    DetailViewModel detailViewModel;

    public static DetailFragment newInstance() {

        Bundle args = new Bundle();

        DetailFragment fragment = new DetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setNavigationOnClickListener(v -> {
            getActivity().onBackPressed();
        });

        detailViewModel = ViewModelProviders.of(getActivity()).get(DetailViewModel.class);
        detailViewModel.getRecipe().observe(getActivity(), recipe -> {
            if(recipe != null) {
                toolbar.setTitle(recipe.getName());

                ingredientAdapter.setData(recipe.getIngredients());
                stepAdapter.setData(recipe.getSteps());

                //SET DEFAULT
                if(!recipe.getSteps().isEmpty())
                detailViewModel.getSelectedStep().setValue(recipe.getSteps().get(0));
            }
        });

        stepAdapter.setStepListener(step -> {
            detailViewModel.getSelectedStep().setValue(step);
            ((DetailActivity) getActivity()).gotoTheater();
        });

        rvIngredient.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        rvIngredient.setNestedScrollingEnabled(false);
        rvStep.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        rvStep.setNestedScrollingEnabled(false);

        rvIngredient.setAdapter(ingredientAdapter);
        rvStep.setAdapter(stepAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
