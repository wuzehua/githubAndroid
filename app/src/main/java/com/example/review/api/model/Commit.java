package com.example.review.api.model;

import com.google.gson.annotations.SerializedName;

public class Commit {
    @SerializedName("message") private String message;
    @SerializedName("committer") private Coder committer;

    public String getMessage() {
        return message;
    }

    public Coder getCommitter() {
        return committer;
    }
}
