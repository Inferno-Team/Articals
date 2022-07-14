package com.inferno.mobile.articals;

import android.app.Application;

import com.inferno.mobile.articals.services.API;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class App extends Application {
    private static API api;

    @Override
    public void onCreate() {
        super.onCreate();
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(API.BASE_URL)
                .build();
        api = retrofit.create(API.class);
    }

    public static API getAPI() {
        return api;
    }
}
