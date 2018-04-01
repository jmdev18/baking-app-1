package com.exercise.template.di;

import com.exercise.template.di.scopes.PerActivity;
import com.exercise.template.views.activities.detail.DetailActivity;
import com.exercise.template.views.activities.detail.DetailActivityModule;
import com.exercise.template.views.activities.main.MainActivity;
import com.exercise.template.views.activities.main.MainActivityModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * File Created by pandu on 30/03/18.
 */
@Module
public abstract class ActivityBuilder {

    @PerActivity
    @ContributesAndroidInjector(modules = MainActivityModule.class)
    abstract MainActivity bindsMainActivity();

    @PerActivity
    @ContributesAndroidInjector(modules = DetailActivityModule.class)
    abstract DetailActivity bindsDetailActivity();
}