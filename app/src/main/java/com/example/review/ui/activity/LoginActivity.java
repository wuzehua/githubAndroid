package com.example.review.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.example.review.R;
import com.example.review.api.model.AccessToken;
import com.example.review.api.service.GithubService;
import com.example.review.utils.GithubServiceUtils;

public class LoginActivity extends AppCompatActivity {


    private Button mLoginButton;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.TRANSPARENT);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE);

        mLoginButton = findViewById(R.id.loginButton);

        Log.d("LoginActivity","<----------onCreate------------->");

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse(GithubServiceUtils.AUTHOR_URL + "?client_id=" + GithubServiceUtils.CLIENT_ID
                                + "&scope=repo&redirect_uri=" + GithubServiceUtils.CALLBACK_URI));
                //mLoginButton.setEnabled(false);
                startActivity(intent);
                LoginActivity.this.finish();
            }
        });

        sharedPreferences = getSharedPreferences("loginStat", Context.MODE_PRIVATE);
        boolean alreadyLogin = sharedPreferences.getBoolean("login",false);
        if(alreadyLogin){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }

    }



    @Override
    protected void onResume() {
        super.onResume();

        Log.d("LoginActivity","<----------onResume------------->");

        Uri uri = getIntent().getData();
        if(uri != null && uri.toString().startsWith(GithubServiceUtils.CALLBACK_URI)){

            mLoginButton.setEnabled(false);
            String code = uri.getQueryParameter("code");


            Call<AccessToken> call = GithubServiceUtils.getGithubService().getAccessToken(
                    GithubServiceUtils.CLIENT_ID,
                    GithubServiceUtils.CLIENT_SECRET,
                    code
            );


            call.enqueue(new Callback<AccessToken>() {
                @Override
                public void onResponse(Call<AccessToken> call, Response<AccessToken> response) {
                    if(response.body() != null && response.isSuccessful()) {
                        sharedPreferences.edit()
                                .putBoolean("login",true)
                                .putString("accessToken",response.body().getAccessToken())
                                .apply();
                        Log.d("LoginActivity", "token=" + response.body().getAccessToken());
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        LoginActivity.this.finish();
                    }
                }

                @Override
                public void onFailure(Call<AccessToken> call, Throwable t) {
                    Log.d("LoginActivity","Failed");
                    mLoginButton.setEnabled(true);
                    Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                }
            });

        }

    }
}