package com.example.sss.infinity.db;

import android.arch.paging.DataSource;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface ProductDao {

    @Query("SELECT * FROM ProductDetails")
    List<ProductDetails> getOrderDetails();

    @Query("SELECT * FROM ProductDetails WHERE categoryName = :catName")
    DataSource.Factory<Integer, ProductDetails> getProductDetails(String catName);
//    List<ProductDetails> getProductDetails()getProductDetails();

    @Query("SELECT productCount FROM ProductDetails WHERE productId = :productid")
    int getProductCount(String productid);

    @Query("SELECT * FROM ProductDetails WHERE productCount > 0")
    List<ProductDetails> getNoOfProductInCart();

    @Query("SELECT * FROM ProductDetails WHERE productId = :productidS")
    List<ProductDetails> getSingleProducts(String productidS);

    @Insert
    void insert(List<ProductDetails> news);

    @Insert
    void insert(ProductDetails productDetails);

    @Delete
    void delete(ProductDetails productDetails);

    @Query("Update ProductDetails SET productCount = :count WHERE id = :pId")
    void updateCount(int count,int pId);

    @Query("Update ProductDetails SET status = :status WHERE id = :pId")
    void updateStatus(int status,int pId);

}
