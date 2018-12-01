package com.example.sss.infinity.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.util.Log;

import com.example.sss.infinity.models.SubCategoryObject;

@Database(entities = {ProductDetails.class}, version = 6,exportSchema = false)
public abstract class ProductDatabase extends RoomDatabase {

    private static final String LOG_TAG = ProductDatabase.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "orderi.db";
    private static ProductDatabase sInstance;

    public static ProductDatabase getsInstance(Context context){
        if(sInstance == null){
            synchronized (LOCK){
                Log.d(LOG_TAG,"Creating new Database Instance");
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        ProductDatabase.class,ProductDatabase.DATABASE_NAME)
                        .build();
            }
        }
        Log.d(LOG_TAG,"getting the Database Instance");
        return sInstance;
    }

    public abstract ProductDao productDao();

}
