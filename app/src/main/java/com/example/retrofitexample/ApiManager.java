package com.example.retrofitexample;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiManager {
    private Retrofit retrofit;
    private static ApiManager apiManager;

    private ApiManager(){
        //if you want you want to make null json field in PATCH method use this gson serialize null
        //or create method which will pass @fields and update them
        Gson gson = new GsonBuilder().serializeNulls().create();

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder().
                addInterceptor(new Interceptor() {
                    @NotNull
                    @Override
                    public Response intercept(@NotNull Chain chain) throws IOException {
                        Request originalRequest = chain.request();
                        Request newRequest = originalRequest.newBuilder().
                                addHeader("Interceptor-header", "xyz")
                                .build();
                        return chain.proceed(newRequest);
                    }
                }).
                addInterceptor(loggingInterceptor).build();

        retrofit = new Retrofit.Builder().baseUrl("https://jsonplaceholder.typicode.com/")
//                .addConverterFactory(GsonConverterFactory.create(gson)).build();
                .client(okHttpClient) /* this used to make logs*/
                .addConverterFactory(GsonConverterFactory.create()).build();
    }

    public static ApiManager getInstance() {
        if(apiManager == null){
            apiManager = new ApiManager();
        }
        return apiManager;
    }

    public JsonPlaceHolderApi getJson(){
        return retrofit.create(JsonPlaceHolderApi.class);
    }
}

