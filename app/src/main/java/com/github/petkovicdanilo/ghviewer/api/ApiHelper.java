package com.github.petkovicdanilo.ghviewer.api;

import android.util.Log;

import com.github.petkovicdanilo.ghviewer.api.dto.UserDto;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import lombok.Getter;
import lombok.Setter;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiHelper {
    private static String BASE_URL = "https://api.github.com";

    private volatile String token;

    @Getter
    private volatile UserDto currentUser;

    private static volatile ApiHelper INSTANCE = null;

    @Getter()
    private GitHubService gitHubService;

    private ApiHelper() {
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    Request newRequest = chain.request().newBuilder()
                            .addHeader("Authorization", "token " + this.token)
                            .build();
                    return chain.proceed(newRequest);
                })
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        this.gitHubService = retrofit.create(GitHubService.class);
    }

    public static ApiHelper getInstance()
    {
        if (INSTANCE == null)
        {
            synchronized (ApiHelper.class)
            {
                if(INSTANCE == null)
                {
                    INSTANCE = new ApiHelper();
                }

            }
        }
        return INSTANCE;
    }

    public void setToken(String token) {
        this.token = token;

        Log.i("TAG", token);

        Call<UserDto> call = gitHubService.getUser();
        call.enqueue(new Callback<UserDto>() {
            @Override
            public void onResponse(Call<UserDto> call, Response<UserDto> response) {
                if(response.isSuccessful()) {
                    ApiHelper.this.currentUser = response.body();
                }
            }

            @Override
            public void onFailure(Call<UserDto> call, Throwable t) {

            }
        });
    }
}