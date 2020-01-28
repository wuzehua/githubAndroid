package com.example.review.activity;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.review.R;
import com.example.review.api.model.AccessToken;
import com.example.review.api.service.GithubService;

public class LoginActivity extends AppCompatActivity {

    private final static String AUTHOR_URL = "https://github.com/login/oauth/authorize";
    private final static String CLIENT_ID = "74c29ed1c122fa9547d1";
    private final static String CLIENT_SECRET = "671f67129c2d67417bac90200a1ee2f0ad66fb62";
    private final static String CALLBACK_URI = "androidgithub://callback";

    private Button mLoginButton;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mLoginButton = findViewById(R.id.loginButton);

        Log.d("LoginActivity","<----------onCreate------------->");

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(AUTHOR_URL + "?client_id=" + CLIENT_ID + "&scope=repo&redirect_uri=" + CALLBACK_URI));
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
        if(uri != null && uri.toString().startsWith(CALLBACK_URI)){


            String code = uri.getQueryParameter("code");

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(GithubService.GITHUB_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            GithubService service = retrofit.create(GithubService.class);

            Call<AccessToken> call = service.getAccessToken(
                    CLIENT_ID,
                    CLIENT_SECRET,
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
                    Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                }
            });

        }

    }
}