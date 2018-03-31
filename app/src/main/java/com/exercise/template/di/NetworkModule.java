package com.exercise.template.di;

import android.content.Context;

import com.exercise.template.Constants;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * File Created by pandu on 30/03/18.
 */
@Module
public class NetworkModule {

    @Provides
    @Singleton
    public HttpLoggingInterceptor providesHttpLoggingInterceptor(){
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return httpLoggingInterceptor;
    }

    @Provides
    @Singleton
    public Cache providesCache(Context context){
        Long maxSize = (long) (1024 * 10);
        return new Cache(context.getCacheDir(), maxSize);
    }

    @Provides
    @Singleton
    public OkHttpClient providesOkHttpClient(HttpLoggingInterceptor interceptor, Cache cache){
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addInterceptor(interceptor);
        builder.connectTimeout(Constants.TIMEOUT_DURATION, TimeUnit.SECONDS);
        builder.readTimeout(Constants.TIMEOUT_DURATION, TimeUnit.SECONDS);
        builder.writeTimeout(Constants.TIMEOUT_DURATION, TimeUnit.SECONDS);
        builder.followRedirects(false);
        builder.cache(cache);
        return builder.build();
    }
}
