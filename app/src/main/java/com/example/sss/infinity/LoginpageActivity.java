package com.example.sss.infinity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.AsyncTask;

import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telecom.TelecomManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.RequestQueue;

import com.android.volley.toolbox.Volley;
import com.example.sss.infinity.helpers.AlertMsgBox;
import com.example.sss.infinity.helpers.ViewDialog;


import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;


import java.net.URLEncoder;


public class LoginpageActivity extends AppCompatActivity {
    private String OTP;
    private Button btnlogin;

    private TextView txtskip;
    private AlertMsgBox alertbox;
    private AlertDialog dialog;
    private EditText edtmobilenumber;
    int length = 4;
    private RequestQueue mqueue;
    private String mobile;
    private String query, mylink;
    private String message;

    boolean boolean_permission;
    public static final int REQUEST_PERMISSIONS = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginpage);
        alertbox = new AlertMsgBox(this);
        btnlogin = (Button) findViewById(R.id.btnloginto);
        edtmobilenumber = (EditText) findViewById(R.id.edtmobilenumber);

        fn_permission();
        mqueue = Volley.newRequestQueue(this);

        Toast.makeText(getApplicationContext(),"Enter your current number",Toast.LENGTH_LONG).show();
        //ViewDialog viewDialog=new ViewDialog();



        //viewDialog.showDialog(LoginpageActivity.this,"otp will sent to our number and it will be automatically verified","please provide phone number");

        btnlogin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                mobile = edtmobilenumber.getText().toString();
                OTP = generateOTP();

                if(mobile.length()==10)
                {
                    try {
                        query = URLEncoder.encode(mobile, "utf-8");
                    } catch (UnsupportedEncodingException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                    mylink ="http://smslogin.mobi/spanelv2/api.php?username=Shamgar&password=Shamgar1234&to="+mobile+"&from=SHAMGR&message="+OTP;

                    HttpAsyncTask hat = new HttpAsyncTask();
                    hat.execute(mylink);
                }
                else
                {
                    edtmobilenumber.setError("check the number");
                }

            }

        });

    }


    private void fn_permission()
    {
        if ((ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED))
        {

            if ((ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_PHONE_STATE))) {
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE},
                        REQUEST_PERMISSIONS);

            }

        } else {
            boolean_permission = true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "premission granted", Toast.LENGTH_SHORT).show();
        } else {

            Toast.makeText(this, "premission not granted", Toast.LENGTH_SHORT).show();
            // Your app will not have this permission. Turn off all functions
            // that require this permission or it will force close like your
            // original question
        }
    }



    private class HttpAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {

            return httpRequestResponse(urls[0]);
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result)
        {
            if (result!="Invalid user credentials")
            {
                SharedPreferences pref=getApplicationContext().getSharedPreferences("getnumber",MODE_PRIVATE);
                SharedPreferences.Editor editor=pref.edit();

                editor.putString("regnum",mobile);
                editor.commit();
                Intent otp = new Intent(LoginpageActivity.this, OtpActivity.class);

                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat
                        .makeSceneTransitionAnimation(LoginpageActivity.this, findViewById(R.id.btnloginto), "btn");
                overridePendingTransition(0,0);
                startActivity(otp, optionsCompat.toBundle());
                finish();
            }

        }
    }

    //For HttpAsync Functions: sending requests and receiving responses
    public static String httpRequestResponse(String url){
        InputStream inputStream = null;
        String result = "";
        try {
            // create HttpClient
            HttpClient httpclient = new DefaultHttpClient();

            // make GET request to the given URL
            org.apache.http.HttpResponse httpResponse = httpclient.execute(new HttpGet(url));

            // receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();

            // convert InputStream to string
            if(inputStream != null) {
                result = convertInputStreamToString(inputStream);
            }
            else
                result = "InputStream did not work";

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        return result;
    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;
    }



    public static String generateOTP() {
        int randomPin   =(int)(Math.random()*9000)+1000;
        String otp  =String.valueOf(randomPin);
        return otp;
    }


}


