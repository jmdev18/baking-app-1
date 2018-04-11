package com.exercise.template.di;

import com.exercise.template.MainActivityTest;
import com.exercise.template.TestApp;

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
        TestRetrofitModule.class,
        ActivityBuilder.class
})
@Singleton
public interface TestAppComponent extends AndroidInjector<TestApp> {

    @Component.Builder
    abstract class Builder extends AndroidInjector.Builder<TestApp> {}

    void inject(MainActivityTest mainActivityTest);
}
