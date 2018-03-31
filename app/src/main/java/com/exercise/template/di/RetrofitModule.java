package com.exercise.template.di;

import com.exercise.template.api.AppApi;
import com.google.gson.Gson;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * File Created by pandu on 30/03/18.
 */
@Module(includes = NetworkModule.class)
public class RetrofitModule {

    @Provides
    @Singleton
    public Gson providesGson(){
        return new Gson();
    }

    @Provides
    @Singleton
    public Retrofit providesRetrofit(Gson gson){
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.addCallAdapterFactory(RxJava2CallAdapterFactory.create());
        builder.addConverterFactory(GsonConverterFactory.create(gson));
        builder.baseUrl("https://d17h27t6h515a5.cloudfront.net");
        return builder.build();
    }

    @Provides
    @Singleton
    public AppApi providesAppApi(Retrofit retrofit){
        return retrofit.create(AppApi.class);
    }
}
