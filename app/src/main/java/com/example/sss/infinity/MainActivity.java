package com.example.sss.infinity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sss.infinity.Fragments.Bookings;
import com.example.sss.infinity.Fragments.Home;
import com.example.sss.infinity.Fragments.Profile;
import com.example.sss.infinity.Fragments.Search;
import com.example.sss.infinity.Fragments.Wallet;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

public class MainActivity extends AppCompatActivity
{
    Toolbar toolbar;
    TextView mTitle;
    boolean doubleBackToExitPressedOnce = false;

    public static final String TAG="MainActivity";
    public static final int ERROR_DIAOG_REQUEST=9001;

    private LinearLayout userlocation;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar=(Toolbar)findViewById(R.id.maintoolbar);
        mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);

        isservicesOk();

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(toolbar.getTitle());

        Fragment home=null;
        home=new Home();

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_container,home);
        fragmentTransaction.commit();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);

        navigation.setSoundEffectsEnabled(true);

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        navigation.setSelectedItemId(R.id.navigation_home);

    }



    public boolean isservicesOk()
    {
        Log.d(TAG,"isServicesOk: Checking google services version");

        int avaliable= GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(MainActivity.this);

        if(avaliable== ConnectionResult.SUCCESS)
        {
            Log.d(TAG,"isServicesOk: Google play services working fine");
            return true;
        }
        else if (GoogleApiAvailability.getInstance().isUserResolvableError(avaliable))
        {
            Log.d(TAG,"isServicesOk: An error occured but we can fix it");

            Dialog dialog=GoogleApiAvailability.getInstance().getErrorDialog(MainActivity.this,avaliable,ERROR_DIAOG_REQUEST);
            dialog.show();
        }

        else
        {
            Toast.makeText(this,"you can't make up this request",Toast.LENGTH_SHORT);

        }

        return false;
    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.navigation_search:

                    fragment = new Search();
                    loadFragment(fragment);

                    return true;
                case R.id.navigation_bookings:

                    fragment = new Bookings();
                    loadFragment(fragment);

                    return true;
                case R.id.navigation_home:

                    fragment = new Home();
                    loadFragment(fragment);

                    return true;


                case R.id.navigation_profile:


                    fragment = new Profile();
                    loadFragment(fragment);

                    return true;
            }
            return false;
        }
    };


    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.commit();
    }


    public void setTitle(String title) {
        mTitle.setText(title);
    }
    @Override
    public void onBackPressed()
    {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            super.finish();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

}

