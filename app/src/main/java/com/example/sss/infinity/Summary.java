package com.example.sss.infinity;

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
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SimpleItemAnimator;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sss.infinity.db.ProductDatabase;
import com.example.sss.infinity.db.ProductDetails;
import com.example.sss.infinity.helpers.CheckConnection;
import com.example.sss.infinity.models.ProductViewModel;

import java.util.List;

public class Summary extends AppCompatActivity implements View.OnClickListener{


    RecyclerView recyclerView;
    ProgressBar progressBar;
    CheckConnection checkConnection;
    TextView count,totalPrice,goToCart;
    private CommonUtils commonUtils;
//    static PagedList<ProductDetails> list;

    private ProductViewModel viewModel;
    private ProductListAdapter adapter;

    List<ProductDetails> productDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        count = findViewById(R.id.sorder_quantity);
        totalPrice = findViewById(R.id.stotal_price);
        goToCart = findViewById(R.id.scontinue);
        goToCart.setOnClickListener(Summary.this);

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
        if (animator instanceof SimpleItemAnimator)
        {
            ((SimpleItemAnimator) animator).setSupportsChangeAnimations(false);
        }

        subscribeUi();

    }

    private void subscribeUi() {
        viewModel.getOrderListLiveData().observe(this, (@Nullable PagedList<ProductDetails> productDetail)-> {
            adapter.submitList(productDetail);
            Log.e("size summery",":"+productDetail.size());
            new UpdateCartAsyncTask().execute(false);
        });
    }



    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.scontinue:
                new UpdateCartAsyncTask().execute(true);
                break;
        }
    }

    private class UpdateCartAsyncTask extends AsyncTask<Boolean, Void, OrderSummary> {
        final ProductDatabase mDb = ProductDatabase.getsInstance(Summary.this);
        OrderSummary det = new OrderSummary();

        @Override
        protected OrderSummary doInBackground(Boolean... voids) {

            productDetails = mDb.productDao().getNoOfProductInCart();
            Log.e("list Size:",""+productDetails.size());
            int count = 0;
            Double price = 0.00;
            String ids = "";
            for (int i=0;i<productDetails.size();i++){

              String  pd=productDetails.get(i).getProductId();
              if (i>0)
              {
                  ids += ',';

              }

              ids += pd;

                count = count+productDetails.get(i).getProductCount();
                price = price+productDetails.get(i).getProductPrice()*productDetails.get(i).getProductCount();
            }
            det.setCart(voids[0]);
            det.setCount(count);
            det.setPrice(price);
            det.setIds(ids);

            return det;
        }

        @Override
        protected void onPostExecute(OrderSummary aVoid) {
            super.onPostExecute(aVoid);
            if(aVoid.getCount()>0){
                if(aVoid.isCart()){
                    // Continue is clicked
                    //go to cart activity
                    // productDetails is available with list of products

                    Toast.makeText(Summary.this,"ids : "+aVoid.getIds(),Toast.LENGTH_LONG).show();
                    String pids= aVoid.getIds();
                    Intent payment=new Intent(Summary.this,PaymentActivity.class);
                    Bundle extras=new Bundle();
                    extras.putString("productId", pids);
                    extras.putString("count", String.valueOf(aVoid.getCount()));
                    extras.putDouble("totalcost",aVoid.getPrice());
                    payment.putExtras(extras);
                    startActivity(payment);
                    finish();

                }
            }
            count.setText(String.valueOf(aVoid.getCount()));
            totalPrice.setText(String.valueOf(aVoid.getPrice()));

        }
    }

    private class OrderSummary {
        public boolean isCart() {
            return cart;
        }

        public void setCart(boolean cart) {
            this.cart = cart;
        }

        private boolean cart;
        private int count;
        private Double price;

        public String getIds() {
            return ids;
        }

        public void setIds(String ids)
        {
            this.ids = ids;
        }

        private String ids;

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
