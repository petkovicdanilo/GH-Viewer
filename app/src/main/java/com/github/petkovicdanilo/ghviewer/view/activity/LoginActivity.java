package com.github.petkovicdanilo.ghviewer.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.github.petkovicdanilo.ghviewer.api.ApiHelper;
import com.github.petkovicdanilo.ghviewer.api.GitHubService;
import com.github.petkovicdanilo.ghviewer.api.dto.UserDto;
import com.github.petkovicdanilo.ghviewer.databinding.ActivityLoginBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GithubAuthProvider;
import com.google.firebase.auth.OAuthCredential;
import com.google.firebase.auth.OAuthProvider;

import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements Callback<UserDto> {

    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        binding.btnLogin.setOnClickListener(this::onLoginClick);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        setContentView(view);
    }

    private void onLoginClick(View v) {
        OAuthProvider.Builder provider = OAuthProvider.newBuilder(GithubAuthProvider.PROVIDER_ID);
        provider.setScopes(Arrays.asList("repo"));

        FirebaseAuth.getInstance()
                .startActivityForSignInWithProvider(this, provider.build())
                .addOnSuccessListener(authResult -> {
                    ApiHelper apiHelper = ApiHelper.getInstance();

                    OAuthCredential credential = (OAuthCredential) authResult.getCredential();
                    apiHelper.setToken(credential.getAccessToken());

                    GitHubService gitHubService = apiHelper.getGitHubService();
                    Call<UserDto> call = gitHubService.getUser();
                    call.enqueue(this);
                })
                .addOnFailureListener(e -> {
                    showFailureToast();
                });
    }

    @Override
    public void onResponse(Call<UserDto> call, Response<UserDto> response) {
        if (response.isSuccessful()) {
            ApiHelper.getInstance().setCurrentUser(response.body());

            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        }
        else {
            showFailureToast();
        }
    }

    @Override
    public void onFailure(Call<UserDto> call, Throwable t) {
        showFailureToast();
    }

    private void showFailureToast() {
        Toast.makeText(this, "Log in was not successful", Toast.LENGTH_LONG).show();
    }
}
