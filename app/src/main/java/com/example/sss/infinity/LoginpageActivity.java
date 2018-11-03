package com.example.sss.infinity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpResponse;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.sss.infinity.helpers.AlertMsgBox;
import com.google.android.gms.dynamic.IFragmentWrapper;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Random;

public class LoginpageActivity extends AppCompatActivity {
    private  String OTP;
    private Button btnlogin;

    private TextView txtskip;
    private AlertMsgBox alertbox;
    private AlertDialog dialog;
    private EditText edtmobilenumber;
    int length = 4;
    private RequestQueue mqueue;
    private String mobile;
    private String query,mylink;
    private String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginpage);
        alertbox=new AlertMsgBox(this);
        btnlogin = (Button) findViewById(R.id.btnloginto);
        txtskip = (TextView) findViewById(R.id.txtskip);
        edtmobilenumber = (EditText) findViewById(R.id.edtmobilenumber);

        mqueue= Volley.newRequestQueue(this);
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                mobile = edtmobilenumber.getText().toString();
                OTP=generateOTP();


                try {
                    query = URLEncoder.encode(mobile, "utf-8");
                } catch (UnsupportedEncodingException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                mylink = "http://smslogin.mobi/spanelv2/api.php?username=Shamgar&password=Shamgar1234&to="+mobile+"&from=SHAMGR&message="+OTP;

                HttpAsyncTask hat = new HttpAsyncTask();
                hat.execute(mylink);
            }

        });


        txtskip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent skip = new Intent(LoginpageActivity.this, MainActivity.class);
                startActivity(skip);
                finish();
            }
        });




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


