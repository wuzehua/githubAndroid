package com.example.review.api.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Release {

    @SerializedName("name") private String name;
    @SerializedName("published_at") private String publishDate;
    @SerializedName("assets") private List<Asset> assets;
    @SerializedName("body") private String body;

    public String getBody() {
        return body;
    }

    public String getName() {
        return name;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public List<Asset> getAssets() {
        return assets;
    }
}
