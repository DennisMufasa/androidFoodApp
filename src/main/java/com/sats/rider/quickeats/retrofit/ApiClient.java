package com.sats.rider.quickeats.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by mobua06 on 16/6/17.
 */

public class ApiClient {

    //public static final String BASE_URL = "http://mobulous.co.in/restaurent/CustomerServices/";
    //public static final String BASE_URL = "https://ekr7eb9nl4.execute-api.us-west-2.amazonaws.com/V1/";
    public static final String BASE_URL = "http://18.216.60.204/restaurent/CustomerServices/";

    private static Retrofit retrofit = null;



    public static Retrofit getClient()
    {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(80, TimeUnit.SECONDS)
                .readTimeout(80, TimeUnit.SECONDS)
                .addInterceptor(interceptor).build();

        if (retrofit == null)
        {
            retrofit = new Retrofit.Builder()
                    .client(client)
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }
}
