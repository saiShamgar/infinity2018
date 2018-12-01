package com.example.sss.infinity;

import android.content.Context;

import com.example.sss.infinity.api.ApiUtils;
import com.example.sss.infinity.api.CategoryApi;
import com.example.sss.infinity.db.ProductDatabase;
import com.example.sss.infinity.db.ProductDetails;
import com.example.sss.infinity.models.SubCategory;
import com.example.sss.infinity.models.SubCategoryObject;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommonUtils {

    private Context context;
    SharedPreferenceConfig sharedPreferenceConfig;
    public CommonUtils(Context context) {
        this.context = context;
        sharedPreferenceConfig = new SharedPreferenceConfig(context);

    }

    public void fetchProductsAndInsertToDb(String category) {

        final ProductDatabase mDb = ProductDatabase.getsInstance(context);
        CategoryApi categoryApi = ApiUtils.getCategoryApi();
        categoryApi.getRelatedSubCategory(category).enqueue(new Callback<SubCategory>() {
            @Override
            public void onResponse(Call<SubCategory> call, Response<SubCategory> response) {
               final SubCategory subCategory = response.body();
               final List<SubCategoryObject> list = subCategory.getSubCategoryObjects();

               for(int i=0; i<list.size();i++){
                   final int position = i;
                   Executors.newSingleThreadExecutor().execute(new Runnable()
                   {
                       @Override
                       public void run() {
                           List<ProductDetails> pDetails = mDb.productDao().getSingleProducts(list.get(position).getProductId());
                           if(pDetails.isEmpty()){
                               ProductDetails singleProduct =
                                       new ProductDetails(
                                               category,
                                               list.get(position).getProductId(),
                                               list.get(position).getProductName(),
                                               list.get(position).getProductDesc(),
                                               list.get(position).getProductPrice(),
                                               list.get(position).getProductImageUrl(),
                                               list.get(position).getProductDiscountPrice(),
                                               0,
                                               0);
                               //insert product object to room
                               insertProductToDbLocal(singleProduct, mDb);
                           }else {
                               if(pDetails.get(0).getProductPrice() != list.get(position).getProductPrice() ||
                                       pDetails.get(0).getProductDiscountPrice() != list.get(position).getProductDiscountPrice()){

                                   mDb.productDao().updatePrice(list.get(position).getProductPrice(),
                                           list.get(position).getProductDiscountPrice(),
                                           pDetails.get(0).getId());
                               }

                           }
                       }
                   });

               }
            }
            @Override
            public void onFailure(Call<SubCategory> call, Throwable t) {

            }
        });

    }

    private void insertProductToDbLocal(ProductDetails singleProduct, ProductDatabase mDb) {
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                mDb.productDao().insert(singleProduct);
            }
        });

    }
}
