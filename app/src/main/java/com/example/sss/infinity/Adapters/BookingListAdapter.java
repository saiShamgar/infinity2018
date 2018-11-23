package com.example.sss.infinity.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sss.infinity.R;

import java.util.ArrayList;

public class BookingListAdapter extends RecyclerView.Adapter<BookingListAdapter.BookingViewHolder>
{
     Context context;
    ArrayList<String> orderId=new ArrayList<String>();
    ArrayList<String> totalItems=new ArrayList<String>() ;
    ArrayList<String> totalcost=new ArrayList<String>() ;
    ArrayList<String> date=new ArrayList<String>() ;


    public BookingListAdapter(Context context,ArrayList<String> orderId,ArrayList<String> totalcost,ArrayList<String> totalItems,ArrayList<String> date)
    {
        this.context=context;
        this.orderId=orderId;
        this.totalItems=totalItems;
        this.totalcost=totalcost;
        this.date=date;
    }

    @NonNull
    @Override
    public BookingViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.bookinglistlayout,viewGroup,false);

        BookingViewHolder viewHolder=new BookingViewHolder(view);

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull BookingViewHolder bookingViewHolder, int position)
    {
        String id= "ODR"+orderId.get(position)+"XX";
        String cost= totalcost.get(position).toUpperCase();
        String items= totalItems.get(position);
        String dates= date.get(position);
        bookingViewHolder.orderId.setText(id);
        bookingViewHolder.totalCost.setText(cost);
        bookingViewHolder.totalItems.setText(items);
        bookingViewHolder.date.setText(dates);

    }

    @Override
    public int getItemCount() {
        return orderId.size();
    }

    public class BookingViewHolder extends RecyclerView.ViewHolder
    {
        private TextView orderId,totalCost,totalItems,status,date;
        public BookingViewHolder(@NonNull View itemView)
        {
            super(itemView);
            orderId=(TextView)itemView.findViewById(R.id.txtorderedId_db);
            totalCost=(TextView)itemView.findViewById(R.id.txtcostdb);
            totalItems=(TextView)itemView.findViewById(R.id.txtItemsdb);
            status=(TextView)itemView.findViewById(R.id.txtstatus);
            date=(TextView)itemView.findViewById(R.id.txtdatedb);

        }
    }
}
