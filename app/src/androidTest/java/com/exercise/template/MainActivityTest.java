package com.exercise.template;

import android.app.Instrumentation;
import android.content.Context;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.ViewAssertion;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;

import com.exercise.template.views.activities.main.MainActivity;
import com.exercise.template.views.activities.main.fragments.MainFragment;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import dagger.android.AndroidInjector;
import okhttp3.mockwebserver.MockWebServer;

import static org.junit.Assert.assertEquals;

/**
 * File Created by pandu on 07/04/18.
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mainActivityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    private MockWebServer mockWebServer;

    @Before
    public void init(){
        mainActivityTestRule.getActivity()
                .getSupportFragmentManager().beginTransaction()
                .add(MainFragment.newInstance(), MainFragment.TAG)
                .addToBackStack(MainFragment.TAG)
                .commit();
    }

    @Test
    public void test() {
        Espresso.onView(ViewMatchers.withId(R.id.tv_test))
                .check(ViewAssertions.matches(ViewMatchers.withText("TEST")));
    }
}
