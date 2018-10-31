package com.example.sss.infinity;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SplashScreenActivity extends AppCompatActivity
{
    private Button btngetstarted;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        btngetstarted=(Button)findViewById(R.id.btngetstarted);

        btngetstarted.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent login=new Intent(SplashScreenActivity.this,LoginpageActivity.class);
                ActivityOptionsCompat optionsCompat=ActivityOptionsCompat
                        .makeSceneTransitionAnimation(SplashScreenActivity.this,findViewById(R.id.transitionImage),"myimage");


                startActivity(login,optionsCompat.toBundle());
                finish();
            }
        });



    }
}
