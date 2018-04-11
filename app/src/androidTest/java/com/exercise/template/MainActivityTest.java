package com.exercise.template;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.exercise.template.di.TestAppComponent;
import com.exercise.template.views.activities.main.MainActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.SocketPolicy;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItem;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * File Created by pandu on 07/04/18.
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Inject
    MockWebServer mockWebServer;

    @Rule
    public ActivityTestRule<MainActivity> mainActivityTestRule =
            new ActivityTestRule<>(MainActivity.class, true, false);

    @Before
    public void init() {
        Context targetContext = InstrumentationRegistry.getTargetContext().getApplicationContext();
        AndroidInjector<? extends DaggerApplication> androidInjector = ((TestApp) targetContext).applicationInjector();
        ((TestAppComponent) androidInjector).inject(this);
    }

    @Test
    public void performClickOnRecyclerView_ToGetTheFirstRowIngredientTitle() throws Exception {
        MockResponse response = new MockResponse();
        response.setResponseCode(200);
        response.setBody(RawReader
                .getStringFromFile(InstrumentationRegistry.getTargetContext(), "mock_response.json"));
        mockWebServer.enqueue(response);
        mockWebServer.start(TestConstants.PORT);

        mainActivityTestRule.launchActivity(new Intent());

        onView(withId(R.id.recycler_view))
                .perform(actionOnItemAtPosition(0, click()));

        onView(TestUtils.withRecyclerView(R.id.rv_ingredient)
                .atPositionOnView(0, R.id.tv_title))
        .check(matches(withText("• Graham Cracker crumbs (2.0 CUP)")));
    }

    @Test
    public void performFailToConnect_ForTheFirstTime() throws Exception {
        //YOU NEED TO UNINSTALL THE APP FIRST IF YOU HAVE ALREADY SUCCESSFUL CONNECTING FOR THE FIRST TIME
        //BECAUSE THE SECOND TIME YOU OPEN THE APP ITS ONLY LOAD THE DATA FROM CONTENT PROVIDER
        MockResponse response = new MockResponse();
        response.setResponseCode(200);
        response.setSocketPolicy(SocketPolicy.NO_RESPONSE);
        response.setBody(RawReader
                .getStringFromFile(InstrumentationRegistry.getTargetContext(), "mock_response.json"));
        mockWebServer.enqueue(response);
        mockWebServer.start(TestConstants.PORT);

        mainActivityTestRule.launchActivity(new Intent());

        onView(withId(R.id.rlError))
               .check(matches(isDisplayed()));
    }

    @After
    public void shutDown() throws IOException {
        mockWebServer.shutdown();
    }
}
