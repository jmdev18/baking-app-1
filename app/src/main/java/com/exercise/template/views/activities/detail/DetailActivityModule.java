package com.exercise.template.views.activities.detail;

import android.support.v7.app.AppCompatActivity;

import com.exercise.template.di.scopes.PerActivity;

import dagger.Binds;
import dagger.Module;

/**
 * File Created by pandu on 01/04/18.
 */
@Module(includes = DetailActivityFragmentBuilder.class)
public abstract class DetailActivityModule {

    @Binds
    @PerActivity
    abstract AppCompatActivity bindsDetailActivity(DetailActivity detailActivity);
}
