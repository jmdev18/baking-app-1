package com.exercise.template.views.activities.main;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;

import com.exercise.template.Constants;
import com.exercise.template.R;
import com.exercise.template.views.activities.detail.DetailActivity;
import com.exercise.template.views.activities.main.fragments.MainFragment;
import com.exercise.template.views.activities.main.viewmodels.MainViewModel;
import com.exercise.template.views.base.BaseActivity;

import javax.inject.Inject;

import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    @Inject
    MainViewModel.Factory mainViewModelFactory;
    private MainViewModel mainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mainViewModel = ViewModelProviders.of(this, mainViewModelFactory)
                .get(MainViewModel.class);

        addFragment(R.id.container_main, MainFragment.newInstance());
        mainViewModel.getNumberOfCols().setValue(numberOfColumns());
    }

    public void gotoDetail(){
        Intent i = new Intent(this, DetailActivity.class);
        i.putExtra(Constants.INTENT_RECIPE_DETAIL, mainViewModel.getSelectedRecipe().getValue());
        startActivity(i);
    }

    private int numberOfColumns() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        if(getWindowManager() != null) {
            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            int widthDivider = 600;
            int width = displayMetrics.widthPixels;
            int nColumns = width / widthDivider;
            if (nColumns < 3) return 1;
            return nColumns;
        }

        return 1;
    }
}
