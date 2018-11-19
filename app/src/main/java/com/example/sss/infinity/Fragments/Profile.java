package com.example.sss.infinity.Fragments;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.sss.infinity.LoginpageActivity;
import com.example.sss.infinity.MainActivity;
import com.example.sss.infinity.R;

import org.w3c.dom.Text;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class Profile extends Fragment
{
    private TextView logout,viewnum,regnum;



    public Profile() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Profile");
        View view= inflater.inflate(R.layout.fragment_profile, container, false);

        SharedPreferences pref1=getActivity().getSharedPreferences("getnumber",MODE_PRIVATE);
        SharedPreferences.Editor editor1=pref1.edit();

        SharedPreferences pref=getActivity().getSharedPreferences("mobilenumber",MODE_PRIVATE);
        SharedPreferences.Editor editor=pref.edit();

        logout=(TextView)view.findViewById(R.id.logout);
        viewnum=(TextView)view.findViewById(R.id.viewnumber);
        regnum=(TextView)view.findViewById(R.id.regnum);


        String regnumber=pref1.getString("regnum",null);
        regnum.setText(regnumber);

        viewnum.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (!regnum.isShown())
                {
                    regnum.setVisibility(View.VISIBLE);
                }
                else
                {
                    regnum.setVisibility(View.GONE);
                }
            }
        });

        logout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                editor1.clear();
                editor1.commit();

                editor.clear();
                editor.commit();

                Intent logout=new Intent(getActivity(),LoginpageActivity.class);
                startActivity(logout);
                getActivity().finish();
            }
        });









        return view;
    }

}
