package com.exercise.template.views.activities.detail.fragments;

import android.support.v4.app.Fragment;

import com.exercise.template.di.scopes.PerFragment;

import dagger.Binds;
import dagger.Module;

/**
 * File Created by pandu on 01/04/18.
 */
@Module
public abstract class TheaterFragmentModule {

    @Binds
    @PerFragment
    abstract Fragment bindsTheaterFragment(TheaterFragment fragment);

}
