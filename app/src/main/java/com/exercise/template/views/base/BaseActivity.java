package com.exercise.template.views.base;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.exercise.template.R;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import timber.log.Timber;

/**
 * File Created by pandu on 31/03/18.
 */
public class BaseActivity extends AppCompatActivity implements HasSupportFragmentInjector {

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentInjector;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
    }

    protected final void addFragment(@IdRes int containerViewId, Fragment fragment) {
        Fragment f = getSupportFragmentManager().findFragmentByTag(fragment.getClass().getSimpleName());

        if(f == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_right,
                            R.anim.slide_in_right, R.anim.slide_out_right)
                    .add(containerViewId, fragment, fragment.getClass().getSimpleName())
                    .addToBackStack(fragment.getClass().getSimpleName())
                    .commit();
        }
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentInjector;
    }
}
