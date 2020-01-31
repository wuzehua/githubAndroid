package com.example.review.api.model;

import com.google.gson.annotations.SerializedName;

public class CommitInfo {
    @SerializedName("sha") private String sha;
    @SerializedName("commit") private Commit commit;
    @SerializedName("committer") private UserInfo committer;

    public String getSha() {
        return sha;
    }

    public Commit getCommit() {
        return commit;
    }

    public UserInfo getCommitter() {
        return committer;
    }
}
