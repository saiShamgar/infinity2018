package com.example.sss.infinity.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

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

    public SubCategoryObject(String productId, String productName, String productDesc, Double productPrice, Double productDiscountPrice, int productStock, String productImageUrl, String insertedOn, String updatedOn) {
        this.productId = productId;
        this.productName = productName;
        this.productDesc = productDesc;
        this.productPrice = productPrice;
        this.productDiscountPrice = productDiscountPrice;
        this.productStock = productStock;
        this.productImageUrl = productImageUrl;
        this.insertedOn = insertedOn;
        this.updatedOn = updatedOn;
    }

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

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    public void setProductPrice(Double productPrice) {
        this.productPrice = productPrice;
    }

    public void setProductDiscountPrice(Double productDiscountPrice) {
        this.productDiscountPrice = productDiscountPrice;
    }

    public void setProductStock(int productStock) {
        this.productStock = productStock;
    }

    public void setProductImageUrl(String productImageUrl) {
        this.productImageUrl = productImageUrl;
    }

    public void setInsertedOn(String insertedOn) {
        this.insertedOn = insertedOn;
    }

    public void setUpdatedOn(String updatedOn) {
        this.updatedOn = updatedOn;
    }
}
