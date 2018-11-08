package com.example.sss.infinity.models;

import com.google.gson.annotations.SerializedName;

public class SubCategoryObject{
    @SerializedName("product_id")
    private String productId;
    @SerializedName("product_name")
    private String productName;

    @SerializedName("product_desc")
    private String productDesc;

    @SerializedName("product_price")
    private Double productPrice;

    @SerializedName("product_discount_price")
    private Double productDiscountPrice;

    @SerializedName("product_stock")
    private int productStock;

    @SerializedName("product_image_url")
    private String productImageUrl;

    @SerializedName("inserted_on")
    private String insertedOn;

    @SerializedName("updated_on")
    private String updatedOn;

    public String getProductDesc() {
        return productDesc;
    }

    public Double getProductPrice() {
        return productPrice;
    }

    public Double getProductDiscountPrice() {
        return productDiscountPrice;
    }

    public int getProductStock() {
        return productStock;
    }

    public String getProductImageUrl() {
        return productImageUrl;
    }

    public String getInsertedOn() {
        return insertedOn;
    }

    public String getUpdatedOn() {
        return updatedOn;
    }

    public String getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }
}
