package com.exercise.template.views.activities.main.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.exercise.template.R;
import com.exercise.template.views.activities.main.viewmodels.MainViewModel;
import com.exercise.template.views.base.BaseFragment;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * File Created by pandu on 31/03/18.
 */
public class DetailFragment extends BaseFragment {

    public static final String TAG = DetailFragment.class.getSimpleName();

    @Inject
    MainViewModel.Factory mainViewModelFactory;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    Unbinder unbinder;

    private MainViewModel mainViewModel;

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
        mainViewModel = ViewModelProviders.of(getActivity(), mainViewModelFactory)
                .get(MainViewModel.class);

        mainViewModel.getSelectedRecipe().observe(this, recipe -> {
            if(recipe != null) {
                tvTitle.setText(recipe.getName());
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
