package com.exercise.template;

import com.exercise.template.di.ActivityBuilder;
import com.exercise.template.di.AppModule;
import com.exercise.template.di.NetworkModule;
import com.exercise.template.di.RetrofitModule;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

/**
 * File Created by pandu on 30/03/18.
 */
@Component(modules = {
        AndroidSupportInjectionModule.class,
        AppModule.class,
        RetrofitModule.class,
        ActivityBuilder.class
})
@Singleton
public interface AppComponent extends AndroidInjector<App> {

    @Component.Builder
    abstract class Builder extends AndroidInjector.Builder<App> {}
}
