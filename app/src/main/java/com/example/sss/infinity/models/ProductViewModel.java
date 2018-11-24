package com.example.sss.infinity.models;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.paging.DataSource;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;
import android.arch.persistence.room.Database;
import android.support.annotation.NonNull;

import com.example.sss.infinity.db.ProductDatabase;
import com.example.sss.infinity.db.ProductDetails;

public class ProductViewModel extends AndroidViewModel {
    private LiveData<PagedList<ProductDetails>> pListLiveData;
    private LiveData<PagedList<ProductDetails>> orderListLiveData;
    private LiveData<PagedList<ProductDetails>> searchListLiveData;
    private Application application;

    public ProductViewModel(@NonNull Application application) {
        super(application);
        this.application = application;
    }

    public LiveData<PagedList<ProductDetails>> getListLiveData(String Cat){

        pListLiveData = null;

        DataSource.Factory<Integer, ProductDetails> factory = ProductDatabase.getsInstance(application).productDao().getProductDetails(Cat);

        PagedList.Config pagConfig = new PagedList.Config.Builder().setPageSize(3).setEnablePlaceholders(false).build();
        LivePagedListBuilder<Integer, ProductDetails> pagedListBuilder = new LivePagedListBuilder(factory, pagConfig);
        pListLiveData = pagedListBuilder.build();
        return pListLiveData;
    }

    public LiveData<PagedList<ProductDetails>> getOrderListLiveData(){

        orderListLiveData = null;

        DataSource.Factory<Integer, ProductDetails> factory = ProductDatabase.getsInstance(application).productDao().getOrderedProductDetails();

        PagedList.Config pagConfig = new PagedList.Config.Builder().setPageSize(3).setEnablePlaceholders(false).build();
        LivePagedListBuilder<Integer, ProductDetails> pagedListBuilder = new LivePagedListBuilder(factory, pagConfig);
        orderListLiveData = pagedListBuilder.build();
        return orderListLiveData;
    }


    public LiveData<PagedList<ProductDetails>> getSearchListLiveData(String s){

        searchListLiveData = null;
        String query = "%".concat(s).concat("%");

        DataSource.Factory<Integer, ProductDetails> factory = ProductDatabase.getsInstance(application).productDao().getAllSearchProductDetails(query);

        PagedList.Config pagConfig = new PagedList.Config.Builder().setPageSize(3).setEnablePlaceholders(false).build();
        LivePagedListBuilder<Integer, ProductDetails> pagedListBuilder = new LivePagedListBuilder(factory, pagConfig);
        searchListLiveData = pagedListBuilder.build();
        return searchListLiveData;
    }
}
