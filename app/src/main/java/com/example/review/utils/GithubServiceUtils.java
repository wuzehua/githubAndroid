package com.example.review.utils;

import com.example.review.api.service.GithubService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class GithubServiceUtils {

    private static GithubService githubApiService = null;
    private static GithubService githubService = null;

    public static final String AUTHOR_URL = "https://github.com/login/oauth/authorize";
    public static final String CLIENT_ID = "74c29ed1c122fa9547d1";
    public static final String CLIENT_SECRET = "671f67129c2d67417bac90200a1ee2f0ad66fb62";
    public static final String CALLBACK_URI = "androidgithub://callback";

    public static int RESPONSE_COUNT_PER_PAGE = 30;

    public static GithubService getGithubService(){
        if(githubService == null){
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(GithubService.GITHUB_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            githubService = retrofit.create(GithubService.class);
        }
        return githubService;
    }

    public static GithubService getGithubApiService(){
        if(githubApiService == null){
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(GithubService.GITHUB_API_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            githubApiService = retrofit.create(GithubService.class);
        }

        return githubApiService;
    }


}
