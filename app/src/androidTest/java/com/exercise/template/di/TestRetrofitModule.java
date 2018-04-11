package com.exercise.template.di;

import com.exercise.template.TestConstants;
import com.exercise.template.api.AppApi;
import com.google.gson.Gson;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.mockwebserver.MockWebServer;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * File Created by pandu on 08/04/18.
 */
@Module(includes = TestNetworkModule.class)
public class TestRetrofitModule {

    @Provides
    @Singleton
    public Gson providesGson(){
        return new Gson();
    }

    @Provides
    @Singleton
    public Retrofit providesRetrofit(Gson gson, OkHttpClient okHttpClient){
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.addCallAdapterFactory(RxJava2CallAdapterFactory.create());
        builder.addConverterFactory(GsonConverterFactory.create(gson));
        builder.client(okHttpClient);
        builder.baseUrl("http://localhost:" + String.valueOf(TestConstants.PORT));
        return builder.build();
    }

    @Provides
    @Singleton
    public AppApi providesAppApi(Retrofit retrofit){
        return retrofit.create(AppApi.class);
    }
}
