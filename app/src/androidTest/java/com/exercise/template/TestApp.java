package com.exercise.template;

import com.exercise.template.di.DaggerTestAppComponent;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;
import timber.log.Timber;

/**
 * File Created by pandu on 08/04/18.
 */
public class TestApp extends DaggerApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        if(BuildConfig.DEBUG){
            Timber.plant(new Timber.DebugTree());
        }
    }

    @Override
    public AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerTestAppComponent.builder().create(this);
    }
}
