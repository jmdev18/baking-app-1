package com.exercise.template.views.activities.detail;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.exercise.template.Constants;
import com.exercise.template.R;
import com.exercise.template.api.models.Recipe;
import com.exercise.template.views.activities.detail.fragments.DetailFragment;
import com.exercise.template.views.activities.detail.fragments.TheaterFragment;
import com.exercise.template.views.activities.detail.viewmodels.DetailViewModel;
import com.exercise.template.views.base.BaseActivity;

public class DetailActivity extends BaseActivity {

    DetailViewModel detailViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        boolean isTablet = findViewById(R.id.container_detail) != null;

        detailViewModel = ViewModelProviders.of(this).get(DetailViewModel.class);
        detailViewModel.getRecipe().setValue((Recipe) getIntent().getSerializableExtra(Constants.INTENT_RECIPE_DETAIL));

        addFragment(R.id.container_main, DetailFragment.newInstance());

        if(findViewById(R.id.container_detail) != null){
            addFragment(R.id.container_detail, TheaterFragment.newInstance());
        }
    }

    public void gotoTheater(){
        if(findViewById(R.id.container_detail) == null) {
            addFragment(R.id.container_main, TheaterFragment.newInstance());
        }
    }

    @Override
    public void onBackPressed() {
        if(getSupportFragmentManager().getBackStackEntryCount() < 2
                || findViewById(R.id.container_detail) != null) {
            finish();
        }
        else{
            super.onBackPressed();
        }
    }
}
