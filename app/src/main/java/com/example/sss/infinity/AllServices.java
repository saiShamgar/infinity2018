package com.example.sss.infinity;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.sss.infinity.Adapters.ProductAdapter;
import com.example.sss.infinity.api.ApiUtils;
import com.example.sss.infinity.api.CategoryApi;
import com.example.sss.infinity.helpers.RecyclerItemClickListener;
import com.example.sss.infinity.models.SubCategory;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllServices extends AppCompatActivity {


    RecyclerView recyclerView;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_services);



        Intent intent = getIntent();
        String categoryName = intent.getStringExtra("categoryName");
        Toast.makeText(this,"c :"+categoryName,Toast.LENGTH_SHORT).show();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        if (getSupportActionBar() != null) {
            actionbar.setDisplayHomeAsUpEnabled(true);
            actionbar.setTitle(categoryName);
        }

        recyclerView = findViewById(R.id.recycler_view_sub_category);
        progressBar = findViewById(R.id.progressBar_sub_cat);
        progressBar.setVisibility(View.VISIBLE);

        CategoryApi categoryApi = ApiUtils.getCategoryApi();
        categoryApi.getRelatedSubCategory(categoryName).enqueue(new Callback<SubCategory>() {
            @Override
            public void onResponse(Call<SubCategory> call, Response<SubCategory> response) {
                progressBar.setVisibility(View.INVISIBLE);

                final SubCategory subCategory = response.body();
                Log.e("Success"," : "+subCategory.getSubCategoryObjects().get(1).getProductName());

                LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(layoutManager);



                ProductAdapter productAdapter = new ProductAdapter(subCategory,AllServices.this);
                recyclerView.setAdapter(productAdapter);

                recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position)
                    {

//                     Intent details=new Intent(AllServices.this,DetailsActivity.class);
//                     startActivity(details);
//                     finish();
                    }

                    @Override
                    public void onItemLongClick(View view, int position) {
                        Toast.makeText(getApplicationContext(),"long clicked",Toast.LENGTH_SHORT).show();
                    }
                }));
            }

            @Override
            public void onFailure(Call<SubCategory> call, Throwable t) {
                Log.e("Failed"," : "+t.toString());
            }
        });








    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
