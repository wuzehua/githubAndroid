package com.example.review.api.model;

import com.google.gson.annotations.SerializedName;

public class Coder {
    @SerializedName("name") private String name;
    @SerializedName("email") private String email;
    @SerializedName("date") private String date;

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getDate() {
        return date;
    }
}
