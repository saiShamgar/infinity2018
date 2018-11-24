package com.example.sss.infinity;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
public class SharedPreferenceConfig {
    private SharedPreferences sharedPreferences;
    private Context context;
    public SharedPreferenceConfig(Context context){
        this.context = context;
        sharedPreferences = context.getSharedPreferences(context.getResources().getString(R.string.status), Context.MODE_PRIVATE);
    }
    public void writeStatus(String status){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getResources().getString(R.string.status_preference), status);
        Log.i("SharedPreferanceWrite: ",""+status);
        editor.commit();
    }
    public String readStatus(){
        String status = "false";
        status = sharedPreferences.getString(context.getResources().getString(R.string.status_preference),"false");
        Log.i("SharedPreferanceRead: ",""+status);
        return status;
    }
}
