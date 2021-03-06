package com.exercise.template.views.activities.detail.fragments;

import android.support.v4.app.Fragment;

import com.exercise.template.adapters.IngredientAdapter;
import com.exercise.template.adapters.StepAdapter;
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
public abstract class DetailFragmentModule {

    @Binds
    @PerFragment
    abstract Fragment bindsDetailFragment(DetailFragment fragment);

    @Provides
    @PerFragment
    static IngredientAdapter providesIngredientAdapter(DetailFragment fragment){
        return new IngredientAdapter(fragment.getContext());
    }

    @Provides
    @PerFragment
    static StepAdapter providesStepAdapter(DetailFragment fragment){
        return new StepAdapter(fragment.getContext());
    }

    @Provides
    @PerFragment
    static MainViewModel.Factory providesViewModelFactory(AppApi api) {
        return new MainViewModel.Factory(api);
    }
}
