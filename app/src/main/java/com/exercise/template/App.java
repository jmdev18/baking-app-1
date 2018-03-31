package com.exercise.template;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;
import timber.log.Timber;

/**
 * File Created by pandu on 30/03/18.
 */
public class App extends DaggerApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        if(BuildConfig.DEBUG){
            Timber.plant(new Timber.DebugTree());
        }
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
//        return null;
        return DaggerAppComponent.builder().create(this);
    }
}
