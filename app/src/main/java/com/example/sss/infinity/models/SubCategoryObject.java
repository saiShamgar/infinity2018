package com.example.sss.infinity.models;

import com.google.gson.annotations.SerializedName;

public class SubCategoryObject{
    @SerializedName("product_id")
    private String productId;
    @SerializedName("product_name")
    private String productName;

    public String getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }
}
