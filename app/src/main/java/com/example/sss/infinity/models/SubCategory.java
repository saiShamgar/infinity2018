package com.example.sss.infinity.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SubCategory {
    @SerializedName("results")
    private List<SubCategoryObject> subCategoryObjects;

    public List<SubCategoryObject> getSubCategoryObjects() {
        return subCategoryObjects;
    }
}

