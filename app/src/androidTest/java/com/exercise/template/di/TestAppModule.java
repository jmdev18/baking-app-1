package com.exercise.template.di;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * File Created by pandu on 08/04/18.
 */
@Module
public class TestAppModule {

//    @Provides
//    @Singleton
//    Context provideContext(Application application){
//        return application;
//    }

    @Provides
    @Singleton
    String provideAppMode(){
        return "TEST";
    }
}
