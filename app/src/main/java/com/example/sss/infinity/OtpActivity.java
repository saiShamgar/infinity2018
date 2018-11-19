package com.example.sss.infinity;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.example.sss.infinity.api.AlertMsgBox1;
import com.example.sss.infinity.api.Appcontroller;
import com.example.sss.infinity.api.CustomJsonObjectRequest;
import com.example.sss.infinity.helpers.SmsReceiver;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.concurrent.RunnableFuture;

import pl.droidsonroids.gif.GifImageView;

public class OtpActivity extends AppCompatActivity
{

    private TextView verifyText,statusSuccess;

    private GifImageView loadingGif,successgif;
    private SmsReceiver receiver;

    private AlertMsgBox1 alertbox;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        SharedPreferences pref=getApplicationContext().getSharedPreferences("mobilenumber",MODE_PRIVATE);
        SharedPreferences.Editor editor=pref.edit();

        SharedPreferences regnum=getApplicationContext().getSharedPreferences("getnumber",MODE_PRIVATE);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        alertbox=new AlertMsgBox1(this);
        verifyText=(TextView)findViewById(R.id.verifyText);
        statusSuccess=(TextView)findViewById(R.id.statusSuccess);
        loadingGif=(GifImageView)findViewById(R.id.loadingGif);
        successgif=(GifImageView)findViewById(R.id.successgif);
       receiver = new SmsReceiver();
        Bundle extras=getIntent().getExtras();
        if(extras!=null)
        {
            String Adress=extras.getString("messageNumber");
            String message=extras.getString("message");


            verifyText.setText("your otp is "+message +" From "+Adress);
            loadingGif.setVisibility(View.GONE);
            successgif.setVisibility(View.VISIBLE);
            statusSuccess.setVisibility(View.VISIBLE);

            String number=extras.getString("mobile");

            editor.putString("num",number);
            editor.commit();

          String reg_num=regnum.getString("regnum",null);

          if(reg_num!=null)
          {
              registereduser(reg_num);
          }

        }


    }



    public void finish() {
        super.finish();
    }
    @Override
    protected void onPause()
    {
        super.onPause();
        unregisterReceiver(receiver);
        finish();
    }
    private void registereduser(String number)
    {
        alertbox.showProgressDialog();
        String url = Appcontroller.API_SERVER_URL+"reg_user.php?userid="+number;

        HashMap<String, String> params = new HashMap<>();
        params.put("", "");



        CustomJsonObjectRequest request = new CustomJsonObjectRequest(url, params,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        // TODO Auto-generated method stub
                        alertbox.hideProgressDialog();
                        try {

                            //int message = response.getInt("status");
                            String success = response.getString("status");
                            if (success .equals("user successfully registered") || success.equals("existing user"))
                            {
                                Toast.makeText(OtpActivity.this, success, Toast.LENGTH_LONG).show();

                                new Handler().postDelayed(new Runnable()
                                {
                                    @Override
                                    public void run()
                                    {
                                        Intent mainactivity=new Intent(OtpActivity.this,MainActivity.class);
                                        overridePendingTransition(0,0);
                                        startActivity(mainactivity);
                                        finish();
                                    }
                                },2000);

                            }
                            else {
                                String message = response.getString("status");
                                alertbox.alertMsgBox(message);
                            }
                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        alertbox.hideProgressDialog();
                        if (error instanceof TimeoutError)
                        {
                            Toast.makeText(OtpActivity.this, "Request Time out error. Your internet connection is too slow to work", Toast.LENGTH_LONG).show();
                        } else if (error instanceof ServerError) {
                            Toast.makeText(OtpActivity.this, "Connection Server error", Toast.LENGTH_LONG).show();
                        } else if (error instanceof NetworkError || error instanceof NoConnectionError) {
                            Toast.makeText(OtpActivity.this, "Network connection error! Check your internet Setting", Toast.LENGTH_LONG).show();
                        } else if (error instanceof ParseError) {
                            Toast.makeText(OtpActivity.this, "Parsing error", Toast.LENGTH_LONG).show();
                        }

                    }
                });
        request.setShouldCache(false);
        request.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Appcontroller.getInstance().addToRequestQueue(request);
    }


    @Override
    protected void onResume() {
        super.onResume();

        IntentFilter filter = new IntentFilter();
        filter.addAction("close");
        registerReceiver(receiver, filter);
    }
}
