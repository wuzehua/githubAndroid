package com.example.review.api.model;


import com.google.gson.annotations.SerializedName;

public class AccessToken {
    @SerializedName("access_token") private String accessToken;
    @SerializedName("token_type") private String tokenType;

    public String getAccessToken() {
        return accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }
}

