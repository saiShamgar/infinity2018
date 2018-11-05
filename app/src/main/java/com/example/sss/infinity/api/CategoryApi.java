package com.example.sss.infinity.api;

import com.example.sss.infinity.models.AllCategory;
import com.example.sss.infinity.models.SubCategory;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CategoryApi {
    @GET("category.php")
    Call<AllCategory> getAllCategory();

    @GET("productlist.php")
    Call<SubCategory> getRelatedSubCategory(@Query("category_name") String categoryName);
}
