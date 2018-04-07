package com.exercise.template.views.activities.detail;

import com.exercise.template.di.scopes.PerFragment;
import com.exercise.template.views.activities.detail.fragments.DetailFragment;
import com.exercise.template.views.activities.detail.fragments.DetailFragmentModule;
import com.exercise.template.views.activities.detail.fragments.SlidingTheaterFragment;
import com.exercise.template.views.activities.detail.fragments.SlidingTheaterFragmentModule;
import com.exercise.template.views.activities.detail.fragments.TheaterFragment;
import com.exercise.template.views.activities.detail.fragments.TheaterFragmentModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * File Created by pandu on 01/04/18.
 */
@Module
public abstract class DetailActivityFragmentBuilder {

    @PerFragment
    @ContributesAndroidInjector(modules = DetailFragmentModule.class)
    abstract DetailFragment bindDetailFragment();

    @PerFragment
    @ContributesAndroidInjector(modules = TheaterFragmentModule.class)
    abstract TheaterFragment bindTheaterFragment();

    @PerFragment
    @ContributesAndroidInjector(modules = SlidingTheaterFragmentModule.class)
    abstract SlidingTheaterFragment bindSlidingTheaterFragment();
}
