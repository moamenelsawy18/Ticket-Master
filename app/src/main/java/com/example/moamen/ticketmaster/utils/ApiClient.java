package com.example.moamen.ticketmaster.utils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by MoaMeN on 09-Sep-17.
 */

public class ApiClient {
    public static final String BASE_URL = "https://app.ticketmaster.com/discovery/v2/";
    public static final String ApiKey = "ooPleDOqCzfTBruXp5CQ1NhJBVchdWfk";

    static OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build();

    public static Retrofit retrofit = null;

    public static Retrofit getApiClient(){

        if (retrofit == null)
        {
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL).client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create()).build();
        }

        return retrofit;
    }

}
