package com.example.sss.infinity.Fragments;


import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.sss.infinity.Adapters.BookingListAdapter;
import com.example.sss.infinity.MainActivity;
import com.example.sss.infinity.R;
import com.example.sss.infinity.api.ApiUtils;
import com.example.sss.infinity.api.Appcontroller;
import com.example.sss.infinity.api.CustomJsonObjectRequest;
import com.example.sss.infinity.helpers.AlertMsgBox;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.example.sss.infinity.MainActivity.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class Bookings extends Fragment
{

    private RecyclerView recyclerView;
    private AlertMsgBox alertbox;
    private AlertDialog dialog;
    BookingListAdapter Adapter;
    ArrayList<String> orderId=new ArrayList<String>();
    ArrayList<String> totalItems=new ArrayList<String>() ;
    ArrayList<String> totalcost=new ArrayList<String>() ;
    ArrayList<String> date=new ArrayList<String>() ;
    private RequestQueue mqueue;

    public Bookings() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Bookings");
        View view= inflater.inflate(R.layout.fragment_bookings, container, false);



        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        dialog=builder.create();

        alertbox=new AlertMsgBox(getActivity());

        mqueue= Volley.newRequestQueue(getActivity());

        recyclerView=(RecyclerView)view.findViewById(R.id.bookinglistrecyclerview);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

         bookingList();


        return view;
    }

    private void bookingList()
    {
        String url="http://shamgar.online/infinity/orderstatus.php?userid=9642542514";
        alertbox.showProgressDialog();

        HashMap<String, String> params = new HashMap<>();
        params.put("", "");

        CustomJsonObjectRequest request = new CustomJsonObjectRequest(url, params,
                new Response.Listener<JSONObject>()
                {

                    @Override
                    public void onResponse(JSONObject response)
                    {
                        // TODO Auto-generated method stub
                        alertbox.hideProgressDialog();
                        try
                        {
//
//                            JSONArray data =jsonObj.getJSONArray("results");
//                            JSONArray dataa =data.getJSONArray("results");
                           JSONObject jsonObj = new JSONObject(String.valueOf(response));

                            JSONArray   _jObjTarget1 = jsonObj.getJSONArray("ordered_ids");
                            JSONArray   _jObjTarget2 = jsonObj.getJSONArray("cost");
                            JSONArray   _jObjTarget3= jsonObj.getJSONArray("count");
                            JSONArray   _jObjTarget4 = jsonObj.getJSONArray("ordered on");

                           // Toast.makeText(getActivity(),"hfhdsfdsf",Toast.LENGTH_LONG).show();

                          // int dd=_jObjTarget1.length();

                            for(int i=0;i<_jObjTarget1.length();i++)
                            {

                               // JSONObject _jObj = jsonobj1.getJSONObject(String.valueOf(i));




                                String id =_jObjTarget1.getString(i);
                                String cost =_jObjTarget2.getString(i);
                               String items =_jObjTarget3.getString(i);
                                String dates =_jObjTarget4.getString(i);



                                orderId.add(id);
                                totalcost.add(cost);
                               totalItems.add(items);
                               date.add(dates);




                                Adapter=new BookingListAdapter(getActivity(),orderId,totalcost,totalItems,date);
                                recyclerView.setAdapter(Adapter);

                            }

                        } catch (JSONException e)
                        {
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
                            Toast.makeText(getActivity(), "Request Time out error. Your internet connection is too slow to work", Toast.LENGTH_LONG).show();
                        } else if (error instanceof ServerError) {
                            Toast.makeText(getActivity(), "Connection Server error", Toast.LENGTH_LONG).show();
                        } else if (error instanceof NetworkError) {
                            Toast.makeText(getActivity(), "Network connection error! Check your internet Setting", Toast.LENGTH_LONG).show();
                        } else if (error instanceof ParseError) {
                            Toast.makeText(getActivity(), "Parsing error", Toast.LENGTH_LONG).show();
                        }

                    }
                });

        request.setShouldCache(false);
        request.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Appcontroller.getInstance().addToRequestQueue(request);

    }


}
