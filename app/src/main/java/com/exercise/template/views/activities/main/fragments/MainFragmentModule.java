package com.exercise.template.views.activities.main.fragments;

import android.support.v4.app.Fragment;

import com.exercise.template.adapters.MainAdapter;
import com.exercise.template.api.AppApi;
import com.exercise.template.di.scopes.PerFragment;
import com.exercise.template.views.activities.main.viewmodels.MainViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

/**
 * File Created by pandu on 31/03/18.
 */
@Module
public abstract class MainFragmentModule {

    @Binds
    @PerFragment
    abstract Fragment bindsMainFragment(MainFragment fragment);

    @Provides
    @PerFragment
    static MainAdapter providesMainAdapter(MainFragment fragment){
        return new MainAdapter(fragment.getContext());
    }

    @Provides
    @PerFragment
    static MainViewModel.Factory providesViewModelFactory(AppApi api) {
        return new MainViewModel.Factory(api);
    }
}
