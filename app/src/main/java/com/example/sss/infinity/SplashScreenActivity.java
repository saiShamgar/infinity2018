package com.example.sss.infinity;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class SplashScreenActivity extends AppCompatActivity
{
    private int MY_PERMISSIONS_REQUEST_SMS_RECEIVE = 10;
    private Button btngetstarted;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.RECEIVE_SMS},
                MY_PERMISSIONS_REQUEST_SMS_RECEIVE);
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.READ_SMS},
                MY_PERMISSIONS_REQUEST_SMS_RECEIVE);
        btngetstarted=(Button)findViewById(R.id.btngetstarted);
        SharedPreferences pref=getApplicationContext().getSharedPreferences("getnumber",MODE_PRIVATE);
        String regnumber=pref.getString("regnum",null);
        btngetstarted.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (regnumber!=null)
                {
                    Intent login=new Intent(SplashScreenActivity.this,MainActivity.class);
                    ActivityOptionsCompat optionsCompat=ActivityOptionsCompat
                            .makeSceneTransitionAnimation(SplashScreenActivity.this,findViewById(R.id.transitionImage),"myimage");


                    startActivity(login,optionsCompat.toBundle());
                    finish();
                }
                else
                {
                    Intent login=new Intent(SplashScreenActivity.this,LoginpageActivity.class);
                    ActivityOptionsCompat optionsCompat=ActivityOptionsCompat
                            .makeSceneTransitionAnimation(SplashScreenActivity.this,findViewById(R.id.transitionImage),"myimage");

                    startActivity(login,optionsCompat.toBundle());
                    finish();
                }

            }
        });



    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_PERMISSIONS_REQUEST_SMS_RECEIVE) {
            // YES!!
            Log.i("TAG", "MY_PERMISSIONS_REQUEST_SMS_RECEIVE --> YES");
        }
    }
}
