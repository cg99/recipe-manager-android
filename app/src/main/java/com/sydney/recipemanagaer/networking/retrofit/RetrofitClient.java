package com.sydney.recipemanagaer.networking.retrofit;

import android.content.Context;
import android.content.SharedPreferences;

import com.sydney.recipemanagaer.model.repository.UserRepository;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static final String BASE_URL = "http://10.0.2.2:8000/api/v1/";
    private static Retrofit retrofit;
    private static String token;
    private static SharedPreferences sharedPreferences;
    private static Context applicationContext; // Rename to applicationContext for clarity



    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            OkHttpClient client;
            UserRepository userRepository = new UserRepository(applicationContext);
            token = userRepository.getToken();

            if (token != null) {
                client = new OkHttpClient.Builder()
                        .addInterceptor(new Interceptor() {
                            @Override
                            public okhttp3.Response intercept(Chain chain) throws IOException {
                                Request newRequest = chain.request().newBuilder()
                                        .addHeader("Authorization", "Bearer " + token)
                                        .build();
                                return chain.proceed(newRequest);
                            }
                        })
                        .build();
            } else {
                client = new OkHttpClient.Builder().build();
            }
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
