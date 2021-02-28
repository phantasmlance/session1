package com.example.session1.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

class RetrofitConfig {
    static Retrofit getClient() {
        OkHttpClient builder = new OkHttpClient.Builder()
                .readTimeout(5000, TimeUnit.MILLISECONDS)
                .writeTimeout(5000, TimeUnit.MILLISECONDS)
                .connectTimeout(10000, TimeUnit.MILLISECONDS)
                .retryOnConnectionFailure(true)
                .build();

        Gson gson = new GsonBuilder().setLenient().create();

        return new Retrofit.Builder()
                .baseUrl(APIUtilities.baseUrl)
                .client(builder)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }
}
