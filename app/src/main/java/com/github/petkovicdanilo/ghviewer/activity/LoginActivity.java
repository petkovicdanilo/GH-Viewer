package com.github.petkovicdanilo.ghviewer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.github.petkovicdanilo.ghviewer.api.ApiHelper;
import com.github.petkovicdanilo.ghviewer.databinding.ActivityLoginBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GithubAuthProvider;
import com.google.firebase.auth.OAuthCredential;
import com.google.firebase.auth.OAuthProvider;

import java.util.Arrays;

public class LoginActivity extends AppCompatActivity {

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
                    OAuthCredential credential = (OAuthCredential) authResult.getCredential();
                    ApiHelper.getInstance().setToken(credential.getAccessToken());

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Log in was not successful", Toast.LENGTH_LONG);
                });
    }
}
