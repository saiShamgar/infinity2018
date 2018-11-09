package com.example.sss.infinity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.paging.PagedList;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sss.infinity.api.ApiUtils;
import com.example.sss.infinity.api.CategoryApi;
import com.example.sss.infinity.db.ProductDatabase;
import com.example.sss.infinity.db.ProductDetails;
import com.example.sss.infinity.helpers.CheckConnection;
import com.example.sss.infinity.models.ProductViewModel;

import java.util.ArrayList;
import java.util.List;

public class AllServices extends AppCompatActivity {


    RecyclerView recyclerView;
    ProgressBar progressBar;
    CheckConnection checkConnection;
    TextView count,totalPrice;
    private CommonUtils commonUtils;

    private ProductViewModel viewModel;
    private ProductListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_services);

        count = findViewById(R.id.order_quantity);
        totalPrice = findViewById(R.id.total_price);

        commonUtils = new CommonUtils(this);

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
        checkConnection = new CheckConnection(this);
        if(checkConnection.isConnected()){
            new fatchAndInsertToDbAsyncTask().execute(categoryName);
        }


        recyclerView = findViewById(R.id.recycler_view_sub_category);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        viewModel = ViewModelProviders.of(this).get(ProductViewModel.class);

        adapter = new ProductListAdapter(this);

        recyclerView.setAdapter(adapter);

        progressBar = findViewById(R.id.progressBar_sub_cat);
        progressBar.setVisibility(View.VISIBLE);



        CategoryApi categoryApi = ApiUtils.getCategoryApi();

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private class fatchAndInsertToDbAsyncTask extends AsyncTask<String, Void, String> {


        @Override
        protected String doInBackground(String... param) {
            commonUtils.fetchProductsAndInsertToDb(param[0]);
            return param[0];
        }

        @Override
        protected void onPostExecute(String Cat) {
            subscribeUi(Cat);
            progressBar.setVisibility(View.INVISIBLE);
            super.onPostExecute(Cat);
        }


    }

    private void subscribeUi(String Cat) {
        viewModel.getListLiveData(Cat).observe(this, new Observer<PagedList<ProductDetails>>() {
            @Override
            public void onChanged(@Nullable PagedList<ProductDetails> productDetails) {
                if(!productDetails.isEmpty()){
                    adapter.submitList(productDetails);
//                    recyclerView.setAdapter(adapter);
//                    adapter.notifyDataSetChanged();
                    new UpdateCartAsyncTask().execute();
                }
            }
        });
    }

    private class UpdateCartAsyncTask extends AsyncTask<Void, Void, ArrayList<Double>> {
        final ProductDatabase mDb = ProductDatabase.getsInstance(AllServices.this);
        ArrayList<Double> det = new ArrayList<>();

        @Override
        protected ArrayList<Double> doInBackground(Void... voids) {
            List<ProductDetails> productDetails = mDb.productDao().getNoOfProductInCart();
            Double count = 0.00;
            Double price = 0.00;
            for (int i=0;i<productDetails.size();i++){

                count = count+productDetails.get(i).getProductCount();
                price = price+productDetails.get(i).getProductPrice()*productDetails.get(i).getProductCount();
            }
            det.add(count);
            det.add(price);
            return det;
        }

        @Override
        protected void onPostExecute(ArrayList<Double> aVoid) {
            super.onPostExecute(aVoid);

            count.setText(String.valueOf(aVoid.get(0)));
            totalPrice.setText(String.valueOf(aVoid.get(1)));

        }
    }
}
