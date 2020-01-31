package com.example.review.activity;

import com.example.review.api.service.GithubService;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceActivity extends AppCompatActivity {

    protected Retrofit mRetrofit;
    protected GithubService mApiGithubService;

    protected GithubService getApiService(){
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
