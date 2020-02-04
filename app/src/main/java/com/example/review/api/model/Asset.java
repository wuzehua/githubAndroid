package com.example.review.api.model;

import com.google.gson.annotations.SerializedName;

public class Asset {
    @SerializedName("name") private String name;
    @SerializedName("browser_download_url") private String downloadUrl;

    public String getName() {
        return name;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }
}
