package com.example.sss.infinity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sss.infinity.Adapters.SearchAdapter;
import com.example.sss.infinity.Adapters.ServicesAdapter;
import com.example.sss.infinity.models.Category;
import com.example.sss.infinity.models.Items;
import com.jaychang.srv.SimpleCell;
import com.jaychang.srv.SimpleRecyclerView;
import com.jaychang.srv.decoration.SectionHeaderProvider;
import com.jaychang.srv.decoration.SimpleSectionHeaderProvider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class BeautyServices extends AppCompatActivity
{

    private SimpleRecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beauty_services);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView=(SimpleRecyclerView)findViewById(R.id.servicesrecyclerView);
        this.bindData();

    }




    private void bindData()
    {
        List<Category> services = getData();
        //CUSTOM SORT ACCORDING TO CATEGORIES

        List<ServicesAdapter>   cells = new ArrayList<>();

        //LOOP THROUGH GALAXIES INSTANTIATING THEIR CELLS AND ADDING TO CELLS COLLECTION
        for (Category galaxy : services) {
            ServicesAdapter cell = new ServicesAdapter(galaxy);
            // There are two default cell listeners: OnCellClickListener<CELL, VH, T> and OnCellLongClickListener<CELL, VH, T>
            cell.setOnCellClickListener2(new SimpleCell.OnCellClickListener2<ServicesAdapter, ServicesAdapter.ViewHolder, Category>() {
                @Override
                public void onCellClicked(ServicesAdapter GalaxyCell, ServicesAdapter.ViewHolder viewHolder, Category item) {
                    Toast.makeText(getApplicationContext(), item.getName(), Toast.LENGTH_SHORT).show();
                }
            });
            cell.setOnCellLongClickListener2(new SimpleCell.OnCellLongClickListener2<ServicesAdapter, ServicesAdapter.ViewHolder, Category>()
            {
                @Override
                public void onCellLongClicked(ServicesAdapter GalaxyCell, ServicesAdapter.ViewHolder viewHolder, Category item) {
                    Toast.makeText(getApplicationContext(), item.getName(), Toast.LENGTH_SHORT).show();

                }
            });
            cells.add(cell);
        }
        recyclerView.addCells(cells);
    }


    private ArrayList<Category> getData()
    {
        ArrayList<Category> services=new ArrayList<>();

        //CREATE CATEGORIES
        Category Beauty=new Category(R.drawable.slider_one,"Facials");
        Category Cakes=new Category(R.drawable.slider_one,"Hair Style");
        Category Dogs=new Category(R.drawable.slider_one,"De-tan");
        Category others=new Category(R.drawable.slider_one,"Other Services");
        Category Packages=new Category(R.drawable.slider_one,"Tattoos");

        //INSTANTIATE GALAXY OBJECTS AND ADD THEM TO GALAXY LIST

        services.add(Beauty);
        services.add(Cakes);
        services.add(Dogs);
        services.add(Beauty);
        services.add(others);
        services.add(Packages);
        services.add(Beauty);
        services.add(Cakes);
        services.add(Dogs);
        services.add(Beauty);
        services.add(others);
        services.add(Packages);

        return services;
    }

















    @Override
    public void onBackPressed()
    {
        super.onBackPressed();

        Intent back=new Intent(BeautyServices.this,MainActivity.class);
        startActivity(back);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if(item.getItemId()==android.R.id.home)
        {
            Intent back=new Intent(BeautyServices.this,MainActivity.class);
            startActivity(back);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

}
