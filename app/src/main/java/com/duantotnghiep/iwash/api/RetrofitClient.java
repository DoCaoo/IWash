package com.duantotnghiep.iwash.api;

import com.duantotnghiep.iwash.model.User;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
//    public static final String link = "http://192.168.1.11:5000/";
    public static final String link = "https://serveriwash.herokuapp.com/";
    public static Retrofit retrofit;
    public static String JWT = "";
    public static User user;
    public static String tokenDevice = "";

    static OkHttpClient okHttpClient() {
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(chain -> {
            Request request = chain.request().newBuilder().addHeader("Authorization", JWT).build();
            return chain.proceed(request);
        }).build();
        return client;
    }

    public static Retrofit getInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(link)
                    .client(okHttpClient())
                    .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setLenient().create()))
                    .build();
        }
        return retrofit;
    }
}
