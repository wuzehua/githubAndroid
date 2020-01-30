package com.example.review.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.example.review.R;
import com.example.review.api.service.GithubService;
import com.example.review.fragment.FilesFragment;

public class ContentActivity extends AppCompatActivity {


    private Retrofit mRetrofit;
    private GithubService mApiGithubService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);

        Intent intent = getIntent();

        String fullName = intent.getStringExtra("fullName");
        Fragment fragment = new FilesFragment(getApiService(), fullName);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.contentFrameLayout, fragment)
                .commit();

    }

    private GithubService getApiService(){
        if(mRetrofit == null){
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(GithubService.GITHUB_API_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        if(mApiGithubService == null){
            mApiGithubService = mRetrofit.create(GithubService.class);
        }

        return mApiGithubService;
    }
}