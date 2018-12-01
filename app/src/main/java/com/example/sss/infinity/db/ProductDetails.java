package com.example.sss.infinity.db;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity
public class ProductDetails {

    @PrimaryKey(autoGenerate = true)
    private int id = 0;
    private String categoryName;
    private String productId;
    private String productName;
    private String productDesc;
    private Double productPrice;
    private String productUrl;
    private Double productDiscountPrice;
    private int productCount;
    private int status;
    @Ignore
    public ProductDetails(String categoryName, String productId, String productName, String productDesc, Double productPrice, String productUrl, Double productDiscountPrice, int productCount, int status) {
        this.categoryName = categoryName;
        this.productId = productId;
        this.productName = productName;
        this.productDesc = productDesc;
        this.productPrice = productPrice;
        this.productUrl = productUrl;
        this.productDiscountPrice = productDiscountPrice;
        this.productCount = productCount;
        this.status = status;
    }

    public ProductDetails(int id, String categoryName, String productId, String productName, String productDesc, Double productPrice, String productUrl, Double productDiscountPrice, int productCount, int status) {
        this.id = id;
        this.categoryName = categoryName;
        this.productId = productId;
        this.productName = productName;
        this.productDesc = productDesc;
        this.productPrice = productPrice;
        this.productUrl = productUrl;
        this.productDiscountPrice = productDiscountPrice;
        this.productCount = productCount;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    public Double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Double productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductUrl() {
        return productUrl;
    }

    public void setProductUrl(String productUrl) {
        this.productUrl = productUrl;
    }

    public Double getProductDiscountPrice() {
        return productDiscountPrice;
    }

    public void setProductDiscountPrice(Double productDiscountPrice) {
        this.productDiscountPrice = productDiscountPrice;
    }

    public int getProductCount() {
        return productCount;
    }

    public void setProductCount(int productCount) {
        this.productCount = productCount;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
