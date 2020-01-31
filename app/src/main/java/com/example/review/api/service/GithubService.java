package com.example.review.api.service;

import com.example.review.api.model.AccessToken;
import com.example.review.api.model.FileContent;
import com.example.review.api.model.Repo;
import com.example.review.api.model.UserInfo;

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


    @GET("{user_name}/{repo_type}")
    Call<List<Repo>> getRepos(
            @Path(value = "user_name", encoded = true) String userName,
            @Path(value = "repo_type", encoded = true) String type,
            @Query("access_token") String accessToken
    );

    @GET("repos/{repo_name}/contents{file_path}")
    Call<List<FileContent>> getContentsOfRepo(
            @Path(value = "repo_name", encoded = true) String repoName,
            @Path(value = "file_path", encoded = true) String filePath,
            @Query("access_token") String accessToken
    );

    @GET("{user_name}")
    Call<UserInfo> getInfo(
            @Path(value = "user_name", encoded = true) String userName,
            @Query("access_token") String accessToken
    );

    @GET("{user_name}/{user_type}")
    Call<List<UserInfo>> getUsers(
            @Path(value = "user_name",encoded = true) String userName,
            @Path(value = "user_type", encoded = true) String userType,
            @Query("access_token") String accessToken
    );

}
