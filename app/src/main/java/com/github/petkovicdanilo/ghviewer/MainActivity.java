package com.github.petkovicdanilo.ghviewer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.github.petkovicdanilo.ghviewer.api.GitHubService;
import com.github.petkovicdanilo.ghviewer.api.dto.EmailDto;
import com.github.petkovicdanilo.ghviewer.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private String _token;

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();

        if (savedInstanceState == null) {
            _token = intent.getStringExtra("token");
            Log.i(TAG, _token);
        } else {

        }

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        setContentView(view);
    }

    public void onTestButtonClick(View view) {

        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();

//        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    Request newRequest = chain.request().newBuilder()
                            .addHeader("Authorization", "token " + _token)
                            .build();
                    return chain.proceed(newRequest);
                })
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        GitHubService service = retrofit.create(GitHubService.class);
//        Call<Map<String, Integer>> call = service.listLanguages("petkovicdanilo",
//        "NesEmulator", accessToken);
//
//
//        call.enqueue(new Callback<Map<String, Integer>>() {
//            @Override
//            public void onResponse(Call<Map<String, Integer>> call, Response<Map<String,
//            Integer>> response) {
//                Map<String, Integer> map = response.body();
//
//                for (String key : map.keySet()) {
//                    Log.i(TAG, key);
//                    Log.i(TAG, map.get(key).toString());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Map<String, Integer>> call, Throwable t) {
//                Log.e(TAG, t.getMessage());
//            }
//        });

        Call<List<EmailDto>> call = service.getEmails();
        call.enqueue(new Callback<List<EmailDto>>() {
            @Override
            public void onResponse(Call<List<EmailDto>> call, Response<List<EmailDto>> response) {
                Log.i(TAG, response.isSuccessful() ? "true" : "false");
                Log.i(TAG, response.message());
                if (response.body() == null || response.body().size() == 0) {
                    Log.i(TAG, "empty list");
                } else {
                    Log.i(TAG, response.body().get(0).getEmail());
                }
            }

            @Override
            public void onFailure(Call<List<EmailDto>> call, Throwable t) {
                Log.e(TAG, t.getMessage());
            }
        });

//        GitHubCreateRepo createRepo = GitHubCreateRepo.builder()
//                .name("test-repo")
//                .privateRepo(true)
//                .build();
//
//        Call<GitHubRepository> callRepo = service.createRepo(createRepo, "Bearer " + accessToken);
//        callRepo.enqueue(new Callback<GitHubRepository>() {
//            @Override
//            public void onResponse(Call<GitHubRepository> call, Response<GitHubRepository>
//            response) {
//                Log.i(TAG, response.body().getName());
//            }
//
//            @Override
//            public void onFailure(Call<GitHubRepository> call, Throwable t) {
//
//            }
//        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.app_bar, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.create_repo:
                Toast.makeText(getApplicationContext(), "Create repository", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}