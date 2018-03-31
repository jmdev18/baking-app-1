package com.exercise.template.views.activities.main;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.FrameLayout;

import com.exercise.template.R;
import com.exercise.template.api.AppApi;
import com.exercise.template.views.activities.main.fragments.DetailFragment;
import com.exercise.template.views.activities.main.viewmodels.MainViewModel;
import com.exercise.template.views.base.BaseActivity;
import com.exercise.template.views.activities.main.fragments.MainFragment;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    @Inject
    MainViewModel.Factory mainViewModelFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        MainViewModel mainViewModel = ViewModelProviders.of(this, mainViewModelFactory)
                .get(MainViewModel.class);

        mainViewModel.getNumberOfCols().setValue(numberOfColumns());

        addFragment(R.id.container_main, MainFragment.newInstance());

        if(findViewById(R.id.ll_container) != null){
            addFragment(R.id.container_detail, DetailFragment.newInstance());
        }
    }

    public void gotoDetail(){
        if(findViewById(R.id.ll_container) == null) {
            addFragment(R.id.container, DetailFragment.newInstance());
        }
    }

    private int numberOfColumns() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        if(findViewById(R.id.ll_container) == null && getWindowManager() != null) {
            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            int widthDivider = 400;
            int width = displayMetrics.widthPixels;
            int nColumns = width / widthDivider;
            if (nColumns < 3) return 1;
            return nColumns;
        }

        return 1;
    }
}
