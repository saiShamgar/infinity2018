package com.example.sss.infinity;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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

import org.w3c.dom.Text;

import pl.droidsonroids.gif.GifImageView;

public class OtpActivity extends AppCompatActivity
{

    private TextView verifyText,statusSuccess;

    private GifImageView loadingGif,successgif;
    private BroadcastReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        verifyText=(TextView)findViewById(R.id.verifyText);
        statusSuccess=(TextView)findViewById(R.id.statusSuccess);
        loadingGif=(GifImageView)findViewById(R.id.loadingGif);
        successgif=(GifImageView)findViewById(R.id.successgif);
       receiver = new BroadcastReceiver()
        {

            @Override
            public void onReceive(Context context, Intent intent)
            {
                finish();
            }


        };

        Bundle extras=getIntent().getExtras();
        if(extras!=null)
        {
            String Adress=extras.getString("messageNumber");
            String message=extras.getString("message");

            verifyText.setText("your otp is "+message +" From "+Adress);
            loadingGif.setVisibility(View.GONE);
            successgif.setVisibility(View.VISIBLE);
            statusSuccess.setVisibility(View.VISIBLE);


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
            },5000);
        }


    }
    public void finish() {
        super.finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }

    @Override
    protected void onResume() {
        super.onResume();

        IntentFilter filter = new IntentFilter();

        filter.addAction("com.hello.action");
        registerReceiver(receiver, filter);
    }
}
