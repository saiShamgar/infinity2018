package com.example.sss.infinity.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sss.infinity.R;
import com.example.sss.infinity.models.Category;
import com.jaychang.srv.SimpleCell;
import com.jaychang.srv.SimpleViewHolder;

public class ServicesAdapter extends SimpleCell<Category,ServicesAdapter.ViewHolder>
{

    public ServicesAdapter(@NonNull Category item)
    {
        super(item);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.services_custom_model;
    }
    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(ViewGroup parent, View cellView)
    {
        return new ViewHolder(cellView);
    }


    @Override
    protected void onBindViewHolder(@NonNull ViewHolder viewHolder, int i, @NonNull Context context, Object o)
    {
        viewHolder.titleTxt.setText(getItem().getName());
        viewHolder.img.setImageResource(getItem().getId());

    }
    /**
     - Our ViewHolder class.
     - Inner static class.
     * Define your view holder, which must extend SimpleViewHolder.
     * */

    public static class ViewHolder extends SimpleViewHolder
    {
        TextView titleTxt;
        ImageView img;

        ViewHolder(View itemView)
        {
            super(itemView);
            titleTxt=itemView.findViewById(R.id.servicesText);
            img=itemView.findViewById(R.id.servicesImages);

        }
    }

}
