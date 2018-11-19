package com.example.sss.infinity;

import android.arch.paging.PagedListAdapter;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sss.infinity.db.ProductDatabase;
import com.example.sss.infinity.db.ProductDetails;
import com.example.sss.infinity.helpers.DialogAction;

import java.util.List;
import java.util.concurrent.Executors;

public class ProductListAdapter extends PagedListAdapter<ProductDetails, ProductListAdapter.ProductViewHolder> {


    private static final int INCREMENT_QTY = 11;
    private static final int DECREMENT_QTY = 22;

    private static DiffUtil.ItemCallback<ProductDetails> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<ProductDetails>() {
                @Nullable
                @Override
                public Object getChangePayload(@NonNull ProductDetails oldItem, @NonNull ProductDetails newItem) {

                    return super.getChangePayload(oldItem, newItem);
                }

                @Override
                public boolean areItemsTheSame(@NonNull ProductDetails oldItem, @NonNull ProductDetails newItem) {
                    return oldItem.getId() == newItem.getId();
                }

                @Override
                public boolean areContentsTheSame(@NonNull ProductDetails oldItem, @NonNull ProductDetails newItem) {
                    return oldItem.equals(newItem);
                }
            };

    private Context mCtx;
    DialogAction dialogAction;

    public ProductListAdapter(Context mCtx) {
        super(DIFF_CALLBACK);
        this.mCtx = mCtx;
        dialogAction  = new DialogAction(mCtx);

    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_list_item,parent,false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {

        final ProductDetails product = getItem(position);

        holder.description.setText(product.getProductName());
        holder.priceOriginal.setText(String.valueOf(product.getProductPrice().toString()));
        holder.discountPrice.setText(String.valueOf(product.getProductDiscountPrice()));
        holder.quantity.setText(String.valueOf(product.getProductCount()));

        holder.increment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                incrementOrDecrementOrderQuantity(INCREMENT_QTY,product.getId(),product.getProductCount()+1);
            }
        });

        holder.decrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(product.getProductCount()<=0){
                    Toast.makeText(mCtx,"Quantity cannot be -ve",Toast.LENGTH_SHORT).show();
                }else
                    incrementOrDecrementOrderQuantity(DECREMENT_QTY,product.getId(),product.getProductCount()-1);
            }
        });


    }

    private void incrementOrDecrementOrderQuantity(int operation, int id, int count)
    {
        new operateToDb().execute(operation,id,count);

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

    private class operateToDb extends AsyncTask<Integer,Void,Void> {
        final ProductDatabase mDb = ProductDatabase.getsInstance(mCtx);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialogAction.showDialog("Updating", "Wait Please..");
        }



        @Override
        protected Void doInBackground(Integer... params) {
            List<ProductDetails> productDetails;


            Executors.newSingleThreadExecutor().execute(new Runnable() {
                @Override
                public void run() {
                    mDb.productDao().updateCount(params[2],params[1]);
//                    List<ProductDetails> productDetails = mDb.productDao().getNoOfProductInCart();
//                    for (int i=0;i<productDetails.size();i++){
//
//                    }
                }
            });

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
//            notifyDataSetChanged();
//            notifyItemChanged(1);
            super.onPostExecute(aVoid);
            dialogAction.hideDialog();
        }
    }
}
