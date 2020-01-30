package com.example.review.api.model;

import com.google.gson.annotations.SerializedName;

public class FileContent {
    @SerializedName("name") private String name;
    @SerializedName("type") private String type;
    @SerializedName("path") private String path;

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getPath() {
        return path;
    }
}
