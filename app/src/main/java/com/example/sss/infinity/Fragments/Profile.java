package com.example.sss.infinity.Fragments;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.sss.infinity.LoginpageActivity;
import com.example.sss.infinity.MainActivity;
import com.example.sss.infinity.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Profile extends Fragment
{
    private Button btnlogIn;


    public Profile() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Profile");
        View view= inflater.inflate(R.layout.fragment_profile, container, false);

        btnlogIn=(Button)view.findViewById(R.id.btnlogIn);

        btnlogIn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent login=new Intent(getActivity(), LoginpageActivity.class);
                startActivity(login);
                getActivity().finish();
            }
        });





        return view;
    }

}
