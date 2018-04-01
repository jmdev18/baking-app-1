package com.exercise.template.views.activities.main.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.exercise.template.R;
import com.exercise.template.adapters.MainAdapter;
import com.exercise.template.views.activities.main.MainActivity;
import com.exercise.template.views.activities.main.viewmodels.MainViewModel;
import com.exercise.template.views.base.BaseFragment;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * File Created by pandu on 31/03/18.
 */
public class MainFragment extends BaseFragment {

    public static final String TAG = MainFragment.class.getSimpleName();

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @Inject
    MainAdapter adapter;

    @Inject
    MainViewModel.Factory mainViewModelFactory;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private MainViewModel mainViewModel;

    Unbinder unbinder;

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

        mainViewModel.getNumberOfCols().observe(this, cols -> {
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), cols));
        });

        mainViewModel.getRecipes().observe(this, recipes -> {
            adapter.setData(recipes);
        });

        adapter.setRecipeListener(selectedRecipe -> {
            mainViewModel.getSelectedRecipe().setValue(selectedRecipe);
            ((MainActivity) getActivity()).gotoDetail();
        });

        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();

        mainViewModel.fetchRecipes();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
