package com.example.sss.infinity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class DetailsActivity extends AppCompatActivity
{
    private TextView pname,pprice,txtdescription;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Bundle extras=getIntent().getExtras();
        String  desc=extras.getString("desc");
        String  name=extras.getString("name");
        String  price=extras.getString("price");

        pname=(TextView)findViewById(R.id.txtproductname);
        pprice=(TextView)findViewById(R.id.txtproductcost);
        txtdescription=(TextView)findViewById(R.id.txtdescription);

        pname.setText(name);
        pprice.setText("\u20B9"+price);
        txtdescription.setText(desc);

      // Toast.makeText(getApplicationContext(),"description  "+desc,Toast.LENGTH_LONG).show();



    }
}
