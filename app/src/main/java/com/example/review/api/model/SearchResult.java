package com.example.review.api.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchResult<T> {
    @SerializedName("total_count") private int count;
    @SerializedName("items") private List<T> items;

    public int getCount() {
        return count;
    }

    public List<T> getItems() {
        return items;
    }
}
