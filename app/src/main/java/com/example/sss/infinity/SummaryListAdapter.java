package com.example.sss.infinity;

import android.arch.paging.PagedListAdapter;
import android.content.Context;
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

import com.example.sss.infinity.db.ProductDetails;
import com.example.sss.infinity.helpers.DialogAction;

class SummaryListAdapter extends PagedListAdapter<ProductDetails, SummaryListAdapter.ProductViewHolder> {

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

    public SummaryListAdapter(Context mCtx) {
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
    public void onBindViewHolder(@NonNull SummaryListAdapter.ProductViewHolder productViewHolder, int i) {

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
