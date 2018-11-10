package com.example.sss.infinity;

import android.arch.lifecycle.ViewModelProviders;
import android.arch.paging.PagedList;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.sss.infinity.db.ProductDatabase;
import com.example.sss.infinity.db.ProductDetails;
import com.example.sss.infinity.helpers.CheckConnection;
import com.example.sss.infinity.models.ProductViewModel;

import java.util.List;

public class Summary extends AppCompatActivity {

    RecyclerView recyclerView;
    ProgressBar progressBar;
    CheckConnection checkConnection;
    TextView count,totalPrice,goToCart;
    private CommonUtils commonUtils;
//    static PagedList<ProductDetails> list;

    private ProductViewModel viewModel;
    private ProductListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        count = findViewById(R.id.sorder_quantity);
        totalPrice = findViewById(R.id.stotal_price);
        goToCart = findViewById(R.id.scontinue);

        commonUtils = new CommonUtils(this);

        Toolbar toolbar = findViewById(R.id.stoolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        if (getSupportActionBar() != null) {
            actionbar.setDisplayHomeAsUpEnabled(true);
            actionbar.setTitle("Summary");
        }

        recyclerView = findViewById(R.id.srecycler_view_sub_category);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        viewModel = ViewModelProviders.of(this).get(ProductViewModel.class);

        adapter = new ProductListAdapter(this);

        recyclerView.setAdapter(adapter);

        //Off the blinking of recycler view

        RecyclerView.ItemAnimator animator = recyclerView.getItemAnimator();
        if (animator instanceof SimpleItemAnimator) {
            ((SimpleItemAnimator) animator).setSupportsChangeAnimations(false);
        }

        subscribeUi();

    }

    private void subscribeUi() {
        viewModel.getOrderListLiveData().observe(this, (@Nullable PagedList<ProductDetails> productDetail)-> {
            adapter.submitList(productDetail);
            Log.e("size summery",":"+productDetail.size());
            new UpdateCartAsyncTask().execute();
        });
    }





    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private class UpdateCartAsyncTask extends AsyncTask<Void, Void, OrderSummary> {
        final ProductDatabase mDb = ProductDatabase.getsInstance(Summary.this);
        OrderSummary det = new OrderSummary();

        @Override
        protected OrderSummary doInBackground(Void... voids) {
            List<ProductDetails> productDetails = mDb.productDao().getNoOfProductInCart();
            Log.e("list Size:",""+productDetails.size());
            int count = 0;
            Double price = 0.00;
            for (int i=0;i<productDetails.size();i++){

                count = count+productDetails.get(i).getProductCount();
                price = price+productDetails.get(i).getProductPrice()*productDetails.get(i).getProductCount();
            }
            det.setCount(count);
            det.setPrice(price);
            return det;
        }

        @Override
        protected void onPostExecute(OrderSummary aVoid) {
            super.onPostExecute(aVoid);

            count.setText(String.valueOf(aVoid.getCount()));
            totalPrice.setText(String.valueOf(aVoid.getPrice()));

        }
    }

    private class OrderSummary {
        private int count;
        private Double price;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public Double getPrice() {
            return price;
        }

        public void setPrice(Double price) {
            this.price = price;
        }
    }
}