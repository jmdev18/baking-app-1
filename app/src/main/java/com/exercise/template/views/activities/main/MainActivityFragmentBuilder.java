package com.exercise.template.views.activities.main;

import com.exercise.template.di.scopes.PerFragment;
import com.exercise.template.views.activities.main.fragments.DetailFragment;
import com.exercise.template.views.activities.main.fragments.DetailFragmentModule;
import com.exercise.template.views.activities.main.fragments.MainFragment;
import com.exercise.template.views.activities.main.fragments.MainFragmentModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * File Created by pandu on 31/03/18.
 */
@Module
public abstract class MainActivityFragmentBuilder {

    @PerFragment
    @ContributesAndroidInjector(modules = MainFragmentModule.class)
    abstract MainFragment bindMainFragment();

    @PerFragment
    @ContributesAndroidInjector(modules = DetailFragmentModule.class)
    abstract DetailFragment bindDetailFragment();
}
