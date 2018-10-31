package com.example.sss.infinity.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.sss.infinity.R;
import com.example.sss.infinity.RecyclerViewClickListener;

import java.util.ArrayList;
import java.util.List;

public class Horizantal_rv extends RecyclerView.Adapter<Horizantal_rv.MyviewHolder>
{
    private RecyclerViewClickListener mListener;
    private ArrayList<String> names=new ArrayList<String>();
    private Context context;

    private boolean status;

    public Horizantal_rv(RecyclerViewClickListener listener)
    {

        mListener = listener;

    }



    public Horizantal_rv(Context context, ArrayList<String> names, boolean status)
    {
        this.context=context;
        this.names=names;
        this.status=status;
    }
    @NonNull
    @Override
    public MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {


        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.horizantal_rv_search,parent,false);

        MyviewHolder myholder=new MyviewHolder(view);



        return myholder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyviewHolder holder, int position)
    {
        if (status=true && position==2)
        {
            holder.imageselected.setVisibility(View.VISIBLE);
        }

        holder.name.setText(names.get(position));
        //set values of data here

       // holder.Image.setImageResource(tag);

    }

    @Override
    public int getItemCount()
    {
        return names.size();
    }

    class MyviewHolder extends RecyclerView.ViewHolder
    {
        TextView name;
        ImageView Image;
        FrameLayout imageselected;



        public MyviewHolder(View itemView)
        {
            super(itemView);

            name=(TextView)itemView.findViewById(R.id.txtname);
            Image=(ImageView) itemView.findViewById(R.id.imagerv);
            imageselected=(FrameLayout)itemView.findViewById(R.id.imageselected);

        }

    }
}
