package com.example.review.api.service;

import com.example.review.api.model.AccessToken;
import com.example.review.api.model.Repo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GithubService {

    String GITHUB_URL = "https://github.com/";
    String GITHUB_API_URL = "https://api.github.com/";


    @Headers("Accept: application/json")
    @POST("login/oauth/access_token")
    @FormUrlEncoded
    Call<AccessToken> getAccessToken(
            @Field("client_id") String clientID,
            @Field("client_secret") String clientSecret,
            @Field("code") String code
    );

    @GET("users/{name}/repos")
    Call<List<Repo>> getUserRepos(
            @Path("name") String userName
    );

    @GET("user/repos")
    Call<List<Repo>> getRepos(
            @Query("access_token") String accessToken
    );

    @GET("user/starred")
    Call<List<Repo>> getStarred(
            @Query("access_token") String accessToken
    );


}
