package com.exercise.template.views.activities.detail;

import android.app.FragmentManager;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.exercise.template.Constants;
import com.exercise.template.R;
import com.exercise.template.api.models.Recipe;
import com.exercise.template.views.activities.detail.fragments.DetailFragment;
import com.exercise.template.views.activities.detail.fragments.SlidingTheaterFragment;
import com.exercise.template.views.activities.detail.fragments.TheaterFragment;
import com.exercise.template.views.activities.detail.viewmodels.DetailViewModel;
import com.exercise.template.views.base.BaseActivity;

import timber.log.Timber;

public class DetailActivity extends BaseActivity {

    DetailViewModel detailViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        boolean isTablet = findViewById(R.id.container_detail) != null;

        detailViewModel = ViewModelProviders.of(this).get(DetailViewModel.class);
        detailViewModel.getRecipe().setValue((Recipe) getIntent().getSerializableExtra(Constants.INTENT_RECIPE_DETAIL));
        detailViewModel.getIsTablet().setValue(isTablet);

        int backStackEntryCount = getSupportFragmentManager().getBackStackEntryCount();

        if(isTablet){
            if(backStackEntryCount == 2) getSupportFragmentManager().popBackStackImmediate();
            addFragment(R.id.container_main, DetailFragment.newInstance());
            addFragment(R.id.container_detail, TheaterFragment.newInstance());
        }
        else {
            if(findViewById(R.id.v_tablet) != null) {
                if (backStackEntryCount == 2) {
                    getSupportFragmentManager().popBackStackImmediate();
                    addFragment(R.id.container_main, TheaterFragment.newInstance());
                }
                else addFragment(R.id.container_main, DetailFragment.newInstance());
            }
            else addFragment(R.id.container_main, DetailFragment.newInstance());
        }
    }

    public void gotoTheater(){
        if(findViewById(R.id.container_detail) == null) {
            addFragment(R.id.container_main, SlidingTheaterFragment.newInstance());
        }
    }

    @Override
    public void onBackPressed() {
        if(getSupportFragmentManager().getBackStackEntryCount() < 2
                || findViewById(R.id.container_detail) != null) {
            finish();
        }
        else{
            detailViewModel.getVideoPosition().setValue(0L);
            super.onBackPressed();
        }
    }
}
