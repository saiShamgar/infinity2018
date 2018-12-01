package com.example.sss.infinity.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sss.infinity.R;
import com.example.sss.infinity.db.ProductDatabase;
import com.example.sss.infinity.db.ProductDetails;
import com.example.sss.infinity.models.SubCategory;
import com.example.sss.infinity.models.SubCategoryObject;

import java.util.List;
import java.util.concurrent.Executors;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private final SubCategory mDataSet;
    private Context context;
    TextView count, totalPrice;
    static int noOfProduct = 0;
    static List<ProductDetails> productDetails;


    public ProductAdapter(SubCategory mDataSet, Context context, TextView count, TextView totalPrice) {
        this.mDataSet = mDataSet;
        this.context = context;
        this.count = count;
        this.totalPrice = totalPrice;

    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
//        checkConnection = new CheckConnection(context);

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_list_item,parent,false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        final int pos = position;

        holder.description.setText(mDataSet.getSubCategoryObjects().get(position).getProductName());
        holder.priceOriginal.setText("Rs."+mDataSet.getSubCategoryObjects().get(position).getProductPrice().toString());
        if(mDataSet.getSubCategoryObjects().get(position).getProductDiscountPrice() > 0){
            holder.discountPrice.setText("Rs."+mDataSet.getSubCategoryObjects().get(position).getProductDiscountPrice().toString());
        }else
            holder.discountPrice.setVisibility(View.INVISIBLE);

        getOrderedQuantity(mDataSet.getSubCategoryObjects().get(position).getProductId(), holder.quantity);


        updateOrderDetails();
        holder.increment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("increment", "checked");
                ProductDetails productDetails1 = getProductDetails(mDataSet.getSubCategoryObjects().get(pos),0);
                insertProductToDb(productDetails1);
//                holder.quantity.setText("1");
                getDatabaseDetails();

                getOrderedQuantity(mDataSet.getSubCategoryObjects().get(position).getProductId(), holder.quantity);
//                holder.quantity.setText(String.valueOf(orderQuantity));
//                Log.e("increment", " : "+orderQuantity);
            }
        });



    }

    private void getOrderedQuantity(String productId, TextView quantity) {
        Log.e("product id",""+productId);
        final ProductDatabase mDb = ProductDatabase.getsInstance(context);
        int[] aaa = {0};
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
              aaa[0] = mDb.productDao().getProductCount(productId);
//              quantity.setText(String.valueOf(aaa[0]));
//              aaa[0] = productDetails.size();
                Log.e("count",""+aaa[0]);
//                return;
            }
        });
//        return aaa[0];
        quantity.setText(String.valueOf(aaa[0]));
    }

    private void getDatabaseDetails() {
        final ProductDatabase mDb = ProductDatabase.getsInstance(context);
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                List<ProductDetails> listProduct = mDb.productDao().getOrderDetails();
                Log.e("table details",""+listProduct.size());
            }
        });
    }

    private void insertProductToDb(ProductDetails pDetails) {
        final ProductDatabase mDb = ProductDatabase.getsInstance(context);
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {

                int a = mDb.productDao().getProductCount(pDetails.getProductId());
                pDetails.setProductCount(a+1);
                Log.e("insert :",""+a);
                if(a>1){
//                    mDb.productDao().updateCount(a+1,pDetails.getProductId());
                }else
                    mDb.productDao().insert(pDetails);
            }
        });
//        notifyDataSetChanged();
    }



    private ProductDetails getProductDetails(SubCategoryObject subCategoryObject, int quantity) {
        ProductDetails  orderProduct = new ProductDetails("color",
                subCategoryObject.getProductId(),
                subCategoryObject.getProductName(),
                subCategoryObject.getProductDesc(),
                subCategoryObject.getProductPrice(),
                subCategoryObject.getProductImageUrl(),
                subCategoryObject.getProductDiscountPrice(),
                quantity,
                1);

        return orderProduct;

    }

    private void updateOrderDetails() {
        count.setText(String.valueOf(noOfProduct));
//        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mDataSet.getSubCategoryObjects().size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder{
        private TextView description,priceOriginal,discountPrice,offerPercentage,quantity;
        private ImageView imageView;
        Button increment, decrement;
        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            description = itemView.findViewById(R.id.product_name);
            priceOriginal = itemView.findViewById(R.id.price_original);
            discountPrice = itemView.findViewById(R.id.price_discount);
            offerPercentage = itemView.findViewById(R.id.price_discount_percentage);
            quantity = itemView.findViewById(R.id.quantity);

            imageView = itemView.findViewById(R.id.imageView);

            increment = itemView.findViewById(R.id.increment);
            decrement = itemView.findViewById(R.id.decrement);
        }
    }
}
