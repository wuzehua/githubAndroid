package com.example.review.api.model;

import com.google.gson.annotations.SerializedName;


public class Repo {

    @SerializedName("full_name") private String name;
    @SerializedName("forks_count") private int fork;
    @SerializedName("language") private String language;
    @SerializedName("stargazers_count") private int star;
    @SerializedName("description") private String description;
    @SerializedName("private") private boolean isPrivate;
    @SerializedName("license") private License license;
    @SerializedName("watchers_count") private int watches;

    public String getName(){ return name;}
    public int getFork(){return fork;}
    public String getDescription() { return description; }
    public int getStar() { return star; }
    public String getLanguage() { return language; }
    public boolean isPrivate() { return isPrivate; }

    public License getLicense() {
        return license;
    }

    public int getWatches() {
        return watches;
    }

}
