package com.example.sss.infinity.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AllCategory {
    @SerializedName("category")
    private List<String> category;

    public List<String> getCategory() {
        return category;
    }
}
