package com.example.sss.infinity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
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
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;


public class PaymentActivity extends AppCompatActivity implements PaymentResultListener {
    private static final String TAG = PaymentActivity.class.getSimpleName();
    private Bundle extras;
    private AlertMsgBox1 alertbox;
    private Button paymentButton;
    private TextView txt_ord_id,txt_tot_cst;
    private Double cst;
    private SharedPreferences pref1;
    private String regnumber;
    private SharedPreferences.Editor editor;
    private String loc;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

         pref1=getApplicationContext().getSharedPreferences("getnumber",MODE_PRIVATE);
         regnumber=pref1.getString("regnum",null);
        setContentView(R.layout.activity_payment);
        extras=getIntent().getExtras();

        alertbox=new AlertMsgBox1(this);

        cst=extras.getDouble("totalcost");

        paymentButton=(Button)findViewById(R.id.paymentButton);
        txt_ord_id=(TextView) findViewById(R.id.orderid);
        txt_tot_cst=(TextView)findViewById(R.id.totalcost);
        txt_ord_id.setText("XXXXXXXXXX104");

        txt_tot_cst.setText(cst.toString());

        /*
         To ensure faster loading of the Checkout form,
          call this method as early as possible in your checkout flow.
         */
        Checkout.preload(getApplicationContext());

        // Payment button created by you in XML layout

        SharedPreferences location=getApplicationContext().getSharedPreferences("location",MODE_PRIVATE);
         editor=location.edit();


         loc=location.getString("loc",null);
        paymentButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {


                if (loc!=null)
                {
                       startPayment();
                }
                else
                {
                    Intent location=new Intent(PaymentActivity.this,UserLocation.class);
                    startActivity(location);
                    finish();
                }

            }
        });


    }

    public void startPayment() {
        /*
          You need to pass current activity in order to let Razorpay create CheckoutActivity
         */
        final Activity activity = this;

        final Checkout co = new Checkout();

        try {
            JSONObject options = new JSONObject();
            options.put("name", "Razorpay");
            options.put("description", "Including all Charges");
            //You can omit the image option to fetch the image from dashboard
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
            options.put("currency", "INR");
            options.put("amount",cst*100);

            JSONObject preFill = new JSONObject();
            preFill.put("email", "enter email");
            preFill.put("contact", regnumber);

            options.put("prefill", preFill);

            co.open(activity, options);
        } catch (Exception e) {
            Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT)
                    .show();
            e.printStackTrace();
        }
    }

    /**
     * The name of the function has to be
     * onPaymentSuccess
     * Wrap your code in try catch, as shown, to ensure that this method runs correctly
     */
    @SuppressWarnings("unused")
    @Override
    public void onPaymentSuccess(String razorpayPaymentID) {
        try {

            String pid=extras.getString("productId");
            String ct=extras.getString("count");
            ordered_details(razorpayPaymentID,pid,cst,ct);
            storinglocation(loc);
            Toast.makeText(this, "Payment Successful: " + razorpayPaymentID, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e(TAG, "Exception in onPaymentSuccess", e);
        }
    }

    private void storinglocation(String loc)
    {
        String url = Appcontroller.API_SERVER_URL+"location.php?location="+loc+"&userid="+regnumber;

        HashMap<String, String> params = new HashMap<>();
        params.put("", "");



        CustomJsonObjectRequest request = new CustomJsonObjectRequest(url, params,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        // TODO Auto-generated method stub
                        try {

                            //int message = response.getInt("status");
                            String success = response.getString("status");
                            if (success .equals("location stored"))
                            {
                                Toast.makeText(PaymentActivity.this, success, Toast.LENGTH_LONG).show();

                              editor.clear();
                              editor.commit();

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
                        if (error instanceof TimeoutError)
                        {
                            Toast.makeText(PaymentActivity.this, "Request Time out error. Your internet connection is too slow to work", Toast.LENGTH_LONG).show();
                        } else if (error instanceof ServerError) {
                            Toast.makeText(PaymentActivity.this, "Connection Server error", Toast.LENGTH_LONG).show();
                        } else if (error instanceof NetworkError || error instanceof NoConnectionError) {
                            Toast.makeText(PaymentActivity.this, "Network connection error! Check your internet Setting", Toast.LENGTH_LONG).show();
                        } else if (error instanceof ParseError) {
                            Toast.makeText(PaymentActivity.this, "Parsing error", Toast.LENGTH_LONG).show();
                        }

                    }
                });
        request.setShouldCache(false);
        request.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Appcontroller.getInstance().addToRequestQueue(request);
    }

    private void ordered_details(String razorpayPaymentID, String pid, Double cst, String ct)
    {

        alertbox.showProgressDialog();
        String url =Appcontroller.API_SERVER_URL+"orederedItems.php?userid="+regnumber+"&products="+pid+"&cost="+cst+"&items="+ct+"&ordered_id="+orderedid();

        HashMap<String, String> params = new HashMap<>();
        params.put("", "");

        Log.d(TAG, url+params.toString());

        CustomJsonObjectRequest request = new CustomJsonObjectRequest(url, params,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        // TODO Auto-generated method stub
                        alertbox.hideProgressDialog();
                        try {
                            Log.d(TAG, response.toString());
                             //int message = response.getInt("status");
                            String success = response.getString("status");
                            if (success .equals("sucessfully ordered"))
                            {
                                Toast.makeText(PaymentActivity.this, success, Toast.LENGTH_LONG).show();

                                Intent beautyservies=new Intent(PaymentActivity.this,BeautyServices.class);
                                startActivity(beautyservies);
                                finish();
                            }
                            else {
                                String message = response.getString("status");
                                alertbox.alertMsgBox(message);
                                Log.d(TAG, "Failure");
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
                            Toast.makeText(PaymentActivity.this, "Request Time out error. Your internet connection is too slow to work", Toast.LENGTH_LONG).show();
                        } else if (error instanceof ServerError) {
                            Toast.makeText(PaymentActivity.this, "Connection Server error", Toast.LENGTH_LONG).show();
                        } else if (error instanceof NetworkError || error instanceof NoConnectionError) {
                            Toast.makeText(PaymentActivity.this, "Network connection error! Check your internet Setting", Toast.LENGTH_LONG).show();
                        } else if (error instanceof ParseError) {
                            Toast.makeText(PaymentActivity.this, "Parsing error", Toast.LENGTH_LONG).show();
                        }

                    }
                });
        request.setShouldCache(false);
        request.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Appcontroller.getInstance().addToRequestQueue(request);
    }

    /**
     * The name of the function has to be
     * onPaymentError
     * Wrap your code in try catch, as shown, to ensure that this method runs correctly
     */
    @SuppressWarnings("unused")
    @Override
    public void onPaymentError(int code, String response) {
        try {
            Toast.makeText(this, "Payment failed: " + code + " " + response, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e(TAG, "Exception in onPaymentError", e);
        }
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();

        Intent back=new Intent(PaymentActivity.this,Summary.class);
        startActivity(back);
        finish();
    }

    public static String orderedid()
    {
        int randomPin   =(int)(Math.random()*9000)+1000;
        String otp  =String.valueOf(randomPin);
        return otp;
    }

}
