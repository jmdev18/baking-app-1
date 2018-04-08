package com.exercise.template;

import com.exercise.template.di.ActivityBuilder;
import com.exercise.template.di.AppModule;
import com.exercise.template.di.RetrofitModule;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

/**
 * File Created by pandu on 08/04/18.
 */
@Component(modules = {
        AndroidSupportInjectionModule.class,
        TestAppModule.class,
        RetrofitModule.class,
        ActivityBuilder.class
})
@Singleton
public interface TestAppComponent extends AndroidInjector<TestApp> {

    @Component.Builder
    abstract class Builder extends AndroidInjector.Builder<TestApp> {}
}
