package com.example.sss.infinity;
import android.Manifest;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
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
import com.example.sss.infinity.Adapters.CustomWindowAdapter;
import com.example.sss.infinity.api.AlertMsgBox1;
import com.example.sss.infinity.api.Appcontroller;
import com.example.sss.infinity.api.CustomJsonObjectRequest;
import com.example.sss.infinity.models.PlaceInfo;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import com.google.android.gms.location.places.Places;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;


public class UserLocation extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = "MapActivity";
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUESTED_CODE = 1234;
    private Boolean mlocation_permission_granted = false;
    private FusedLocationProviderClient mFusedLocationProviderClient;

    private AlertMsgBox1 alertbox;
    private GoogleMap map;
    private ArrayList<LatLng> listpoints=new ArrayList<LatLng>();
    private Marker mMarker;
    private static final float DEFAULT_ZOOM = 15f;
    private PlaceInfo mplace;

    private GoogleApiClient googleApiClient;
    private Location location;

    private TextView getCurrentLocation;
    private TextView confirmlocation;
    boolean req_loc=false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_location);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        alertbox=new AlertMsgBox1(this);

        getCurrentLocation=(TextView)findViewById(R.id.getCurrentLocation);
        confirmlocation=(TextView)findViewById(R.id.confirmlocation);

        getCurrentLocation.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                getLocationPermission();
            }
        });







    }

    private void getLocationPermission()
    {
        Log.d(TAG,"getting location permission: called");
        String [] permissions={
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};
        if(ContextCompat.checkSelfPermission(this.getApplicationContext(),FINE_LOCATION)== PackageManager.PERMISSION_GRANTED)
        {
            if(ContextCompat.checkSelfPermission(this.getApplicationContext(), COURSE_LOCATION)==PackageManager.PERMISSION_GRANTED)
            {
                Log.d(TAG,"permissions granted");
                mlocation_permission_granted=true;
                initMap();
            }
            else
            {
                Log.d(TAG,"permissions not granted");
                ActivityCompat.requestPermissions(this,permissions,LOCATION_PERMISSION_REQUESTED_CODE);
            }
        }
        else
        {
            Log.d(TAG,"permissions not granted");
            ActivityCompat.requestPermissions(this,permissions,LOCATION_PERMISSION_REQUESTED_CODE);
        }
    }
    private void initMap()
    {
        Log.d(TAG,"intializing the map");
        final SupportMapFragment mapFragment=(SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map);

        mapFragment.getMapAsync(UserLocation.this);

    }



    //on map ready call back
    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d(TAG, "Check the location");
        Toast.makeText(this, "Check the location", Toast.LENGTH_SHORT).show();
        map = googleMap;

        if (mlocation_permission_granted) {
            getdevicelocation();

            if (ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED)
            {
                return;
            }
            map.setMyLocationEnabled(true);
            map.getUiSettings().setMyLocationButtonEnabled(false);

            init();
        }
    }


    //getting device location
    private void getdevicelocation() {
        Log.d(TAG, "getDeviceLocation: getting the devices locations");

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        try {
            if (mlocation_permission_granted) {
                final Task location = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "onComplete : found location");

                           Location location = (Location) task.getResult();


                            Geocoder geocoder;
                            List<Address> addresses = null;
                            geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());

                            try {
                                addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                            String city = addresses.get(0).getLocality();
                            String phonenum=addresses.get(0).getPhone();

                            mplace=new PlaceInfo();
                            mplace.setName(city);
                            mplace.setAddress(address);
                            mplace.setPhone(phonenum);

                            Log.d(TAG,address+" "+city+" ");

                            movecamera(new LatLng(location.getLatitude(), location.getLongitude()), DEFAULT_ZOOM,mplace,"My location");

                            getCurrentLocation.setClickable(false);
                            listpoints.add(new LatLng(location.getLatitude(), location.getLongitude()));

                        } else {
                            Log.d(TAG, "oncomplete : current location is null");

                            Toast.makeText(UserLocation.this, "unable to get the location ", Toast.LENGTH_SHORT).show();


                        }
                    }
                });
            }
        } catch (SecurityException e) {
            Log.d(TAG, "getDeviceLocation:  Security Exception " + e.getMessage());


        }
    }

    //camera for latlag
    private void movecamera(LatLng latLng, float zoom,PlaceInfo placeInfo,String title) {
        Log.d(TAG, "moveCamera: move the camera lat  " + latLng.latitude + ",  log " + latLng.longitude);

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));

        map.clear();

        map.setInfoWindowAdapter(new CustomWindowAdapter(UserLocation.this));

        if (title.equals("My location"))
        {

            if (placeInfo != null)
            {

                try {
                    String snippet = "Address: " + placeInfo.getAddress() + "\n" +
                            "Phone number: " + placeInfo.getPhone() + "\n" +
                            "WebSite: " + placeInfo.getWebsite() + "\n" +
                            "Price rating: " + placeInfo.getRating() + "\n";

                    MarkerOptions options = new MarkerOptions()
                            .position(latLng)
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
                            .title(placeInfo.getName())
                            .snippet(snippet);

                    SharedPreferences location=getApplicationContext().getSharedPreferences("location",MODE_PRIVATE);
                    SharedPreferences.Editor editor=location.edit();

                    editor.putString("loc",snippet);
                    editor.commit();

                    mMarker = map.addMarker(options);

                    getCurrentLocation.setVisibility(View.GONE);
                    confirmlocation.setVisibility(View.VISIBLE);

                    //get usernum and store into database
                    SharedPreferences pref=getApplicationContext().getSharedPreferences("getnumber",MODE_PRIVATE);
                   String number= pref.getString("regnum",null);
                    confirmlocation.setOnClickListener(new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View view)
                        {
                            Intent back=new Intent(UserLocation.this,Summary.class);
                            startActivity(back);
                            finish();

                        }
                    });
                } catch (NullPointerException e) {
                    Log.d(TAG, "move cemara: NullPointerException  " + e.getMessage());
                }
            }
            else
                {
                map.addMarker(new MarkerOptions().position(latLng));
            }
        }
        else
        {

            mMarker= map.addMarker(new MarkerOptions().position(latLng));
        }


    }
    private void init() {
        Log.d(TAG, "init  : initializing");

        googleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(this, this)
                .build();


    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        Log.d(TAG,"on request permisions calling");
        mlocation_permission_granted=false;

        switch (requestCode)
        {
            case LOCATION_PERMISSION_REQUESTED_CODE:
            {
                if(grantResults.length>0)
                {
                    for(int i=0;i<grantResults.length;i++)
                    {
                        if((grantResults[i] != PackageManager.PERMISSION_GRANTED))
                        {
                            Log.d(TAG,"not get request permissions");
                            mlocation_permission_granted=false;
                        }
                    }
                    Log.d(TAG,"permissions granted");
                    mlocation_permission_granted=true;

                }
            }
        }
    }

    @Override
    public void onBackPressed()
    {

        super.onBackPressed();

        Intent back=new Intent(UserLocation.this,Summary.class);
        startActivity(back);
        finish();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if(item.getItemId()==android.R.id.home)
        {
            Intent back=new Intent(UserLocation.this,MainActivity.class);
            startActivity(back);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
