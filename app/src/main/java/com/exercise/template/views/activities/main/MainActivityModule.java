package com.exercise.template.views.activities.main;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.exercise.template.api.AppApi;
import com.exercise.template.di.scopes.PerActivity;
import com.exercise.template.di.scopes.PerFragment;
import com.exercise.template.views.activities.main.fragments.MainFragment;
import com.exercise.template.views.activities.main.viewmodels.MainViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

/**
 * File Created by pandu on 31/03/18.
 */
@Module(includes = MainActivityFragmentBuilder.class)
public abstract class MainActivityModule {

    @Binds
    @PerActivity
    abstract AppCompatActivity bindsMainActivity(MainActivity mainActivity);
}
