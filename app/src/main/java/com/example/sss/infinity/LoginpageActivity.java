package com.example.sss.infinity;

import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class LoginpageActivity extends AppCompatActivity
{
    private Button btnlogin;

    private TextView txtskip;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginpage);

        btnlogin=(Button)findViewById(R.id.btnloginto);
        txtskip=(TextView) findViewById(R.id.txtskip);

        btnlogin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent otp=new Intent(LoginpageActivity.this,OtpActivity.class);

                ActivityOptionsCompat optionsCompat=ActivityOptionsCompat
                        .makeSceneTransitionAnimation(LoginpageActivity.this,findViewById(R.id.btnloginto),"btn");

                startActivity(otp,optionsCompat.toBundle());
                finish();
            }
        });

        txtskip.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent skip=new Intent(LoginpageActivity.this,MainActivity.class);
                startActivity(skip);
                finish();
            }
        });


    }
}
