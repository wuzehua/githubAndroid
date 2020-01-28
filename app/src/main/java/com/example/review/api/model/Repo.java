package com.example.review.api.model;

import com.google.gson.annotations.SerializedName;


public class Repo {

    @SerializedName("name") private String name;
    @SerializedName("forks_count") private int fork;
    @SerializedName("language") private String language;
    @SerializedName("stargazers_count") private int star;
    @SerializedName("description") private String description;

    public String getName(){ return name;}
    public int getFork(){return fork;}
    public String getDescription() { return description; }
    public int getStar() { return star; }
    public String getLanguage() { return language; }

    public void setName(String name) { this.name = name; }
    public void setFork(int fork) { this.fork = fork; }
    public void setDescription(String description) { this.description = description; }
    public void setLanguage(String language) { this.language = language; }
    public void setStar(int star) { this.star = star; }
}
