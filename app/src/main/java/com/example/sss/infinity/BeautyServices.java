package com.example.sss.infinity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sss.infinity.Adapters.ServicesAdapter;
import com.example.sss.infinity.api.ApiUtils;
import com.example.sss.infinity.api.CategoryApi;
import com.example.sss.infinity.helpers.CheckConnection;
import com.example.sss.infinity.models.AllCategory;
import com.example.sss.infinity.models.Category;
import com.jaychang.srv.SimpleCell;
import com.jaychang.srv.SimpleRecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BeautyServices extends AppCompatActivity
{

    private SimpleRecyclerView recyclerView;
    private ProgressBar progressBar;
    TextView checkStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beauty_services);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CheckConnection checkConnection = new CheckConnection(this);

        recyclerView=(SimpleRecyclerView)findViewById(R.id.servicesrecyclerView);
//        this.bindData(allCategory.getCategory());
        progressBar = findViewById(R.id.check_status_progress);
        progressBar.setVisibility(View.VISIBLE);

        checkStatus = findViewById(R.id.check_status);
        if(checkConnection.isConnected()){
            CategoryApi categoryApi = ApiUtils.getCategoryApi();
            categoryApi.getAllCategory().enqueue(new Callback<AllCategory>() {
                @Override
                public void onResponse(Call<AllCategory> call, Response<AllCategory> response) {
                    final AllCategory allCategory = response.body();
                    bindData(allCategory.getCategory());
                    Log.e("Success"," : "+allCategory.getCategory().get(1));
                    progressBar.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onFailure(Call<AllCategory> call, Throwable t) {
                    Log.e("Success"," : "+t.toString());
                    progressBar.setVisibility(View.INVISIBLE);
                    checkStatus.setText("Something went wrong !!!");

                }
            });
        }else {
            progressBar.setVisibility(View.INVISIBLE);
            checkStatus.setText("Check Internet Connection !!!");
        }



    }




    public void bindData(List<String> category)
    {
        List<Category> services = getData(category);
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
                    Intent intent = new Intent(BeautyServices.this,AllServices.class);
                    intent.putExtra("categoryName",item.getName());
                    startActivity(intent);
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


    private ArrayList<Category> getData(List<String> category)
    {
        ArrayList<Category> services=new ArrayList<>();
        for (int i=0; i<category.size();i++){
            services.add(new Category(R.drawable.slider_one,category.get(i)));
        }



//        //CREATE CATEGORIES
//        Category Beauty=new Category(R.drawable.slider_one,"Facials");
//        Category Cakes=new Category(R.drawable.slider_one,"Hair Style");
//        Category Dogs=new Category(R.drawable.slider_one,"De-tan");
//        Category others=new Category(R.drawable.slider_one,"Other Services");
//        Category Packages=new Category(R.drawable.slider_one,"Tattoos");
//
//        //INSTANTIATE GALAXY OBJECTS AND ADD THEM TO GALAXY LIST
//
//        services.add(Beauty);
//        services.add(Cakes);
//        services.add(Dogs);
//        services.add(Beauty);
//        services.add(others);
//        services.add(Packages);
//        services.add(Beauty);
//        services.add(Cakes);
//        services.add(Dogs);
//        services.add(Beauty);
//        services.add(others);
//        services.add(Packages);

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
