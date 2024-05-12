package com.sydney.recipemanagaer.networking.retrofit;

import android.content.Context;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static final String BASE_URL = "http://10.0.2.2:8000/api/v1/";
    private static Retrofit retrofit;

    private static Context applicationContext; // Rename to applicationContext for clarity

    public static void clearRetrofitInstance() {
        retrofit = null;
    }
    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            OkHttpClient client;


                client = new OkHttpClient.Builder()
                        .addInterceptor(new AuthInterceptor(applicationContext))
                        .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static ApiService getApiService(Context context) {
        applicationContext = context.getApplicationContext();
        return getRetrofitInstance().create(ApiService.class);
    }
}
