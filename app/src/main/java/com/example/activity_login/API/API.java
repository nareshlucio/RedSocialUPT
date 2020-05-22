package com.example.activity_login.API;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class API {
    private static final String BASEURL = "https://notificacionupt.andocodeando.net/";
    public static String token = "";

    private static Retrofit retrofit = null;

    public static Retrofit getApi(Context context){
        if(retrofit == null){

            SharedPreferences preferencias = context.getSharedPreferences("credenciales", Context.MODE_PRIVATE);
            token = preferencias.getString("TOKEN", "");

            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request newRequest  = chain.request().newBuilder()
                            .addHeader("Authorization", "Bearer " + token)
                            .build();
                    return chain.proceed(newRequest);
                }
            }).build();

            retrofit = new Retrofit.Builder()
                    .client(client)
                    .baseUrl(BASEURL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            return retrofit;
        }else {
            return retrofit;
        }
    }
}
