package com.example.review.api.model;

import com.google.gson.annotations.SerializedName;

public class UserInfo {
    @SerializedName("login") private String login;
    @SerializedName("avatar_url") private String headImageUrl;
    @SerializedName("name") private String name;
    @SerializedName("company") private String company;
    @SerializedName("location") private String location;
    @SerializedName("bio") private String bio;
    @SerializedName("email") private String email;
    @SerializedName("id") private int id;
    @SerializedName("contributions") private int contributions;

    public int getContributions() {
        return contributions;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getLogin() {
        return login;
    }

    public String getHeadImageUrl() {
        return headImageUrl;
    }

    public String getName() {
        return name;
    }

    public String getCompany() {
        return company;
    }

    public String getLocation() {
        return location;
    }

    public String getBio() {
        return bio;
    }
}

