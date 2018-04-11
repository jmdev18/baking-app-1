package com.exercise.template.di;

import android.content.Context;

import com.exercise.template.Constants;
import com.exercise.template.TestConstants;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Handler;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import timber.log.Timber;

/**
 * File Created by pandu on 08/04/18.
 */
@Module
public class TestNetworkModule {

    private final MockWebServer mockWebServer;

    public TestNetworkModule() {
        mockWebServer = new MockWebServer();
    }

    @Provides
    @Singleton
    public HttpLoggingInterceptor providesHttpLoggingInterceptor(){
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return httpLoggingInterceptor;
    }

//    @Provides
//    @Singleton
//    public Cache providesCache(Context context){
//        Long maxSize = (long) (1024 * 10);
//        return new Cache(context.getCacheDir(), maxSize);
//    }

    @Provides
    @Singleton
    public OkHttpClient providesOkHttpClient(HttpLoggingInterceptor interceptor){
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addInterceptor(interceptor);
        builder.connectTimeout(TestConstants.TIMEOUT_DURATION, TimeUnit.SECONDS);
        builder.readTimeout(TestConstants.TIMEOUT_DURATION, TimeUnit.SECONDS);
        builder.writeTimeout(TestConstants.TIMEOUT_DURATION, TimeUnit.SECONDS);
        builder.followRedirects(false);
//        builder.cache(cache);
        return builder.build();
    }

    @Provides
    @Singleton
    public MockWebServer providesMockWebServer(){
        return mockWebServer;
    }
}
