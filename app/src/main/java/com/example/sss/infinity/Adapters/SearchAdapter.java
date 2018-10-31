package com.example.sss.infinity.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sss.infinity.R;
import com.example.sss.infinity.models.Items;
import com.jaychang.srv.SimpleCell;
import com.jaychang.srv.SimpleViewHolder;

public class SearchAdapter extends SimpleCell<Items,SearchAdapter.ViewHolder>
{

    public SearchAdapter(@NonNull Items item)
    {
        super(item);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.model;
    }
    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(ViewGroup parent, View cellView) {
        return new ViewHolder(cellView);
    }


    @Override
    protected void onBindViewHolder(@NonNull ViewHolder viewHolder, int i, @NonNull Context context, Object o) {
        viewHolder.titleTxt.setText(getItem().getName());
        viewHolder.descTxt.setText(getItem().getDescription());
        viewHolder.img.setImageResource(getItem().getImage());
        viewHolder.costtxt.setText("\u20B9"+getItem().getCost());
    }
    /**
     - Our ViewHolder class.
     - Inner static class.
     * Define your view holder, which must extend SimpleViewHolder.
     * */

    public static class ViewHolder extends SimpleViewHolder
    {
        TextView titleTxt,descTxt,costtxt;
        ImageView img;

        ViewHolder(View itemView)
        {
            super(itemView);
            titleTxt=itemView.findViewById(R.id.nameTxt);
            descTxt=itemView.findViewById(R.id.descTxt);
            costtxt=itemView.findViewById(R.id.costTxt);
            img=itemView.findViewById(R.id.galaxyImageview);

        }
    }

}
