package com.example.sss.infinity.Fragments;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.sss.infinity.Adapters.ViewpagerAdapter;
import com.example.sss.infinity.BeautyServices;
import com.example.sss.infinity.MainActivity;
import com.example.sss.infinity.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Home extends Fragment
{
    LinearLayout sliderDotspanel;

    private ViewPager viewPager;

    private CardView cardone,cardtwo,cardthree;

    private LinearLayout services,cup_cakes,dogs;


    private int dotscount;
    private ImageView[] dots;

    public Home()
    {
        // Required empty public constructor
    }


    @SuppressLint("ResourceType")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Home Services");
        View rootview= inflater.inflate(R.layout.fragment_home, container, false);



        Animation left = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_in_left);
        Animation right = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_in_right);




        viewPager=(ViewPager)rootview.findViewById(R.id.Homeviewpager);

        cardone=(CardView)rootview.findViewById(R.id.card_viewone);
        cardtwo=(CardView)rootview.findViewById(R.id.card_viewtwo);
        cardthree=(CardView)rootview.findViewById(R.id.card_viewthree);

        services=(LinearLayout)rootview.findViewById(R.id.llservices);
        cup_cakes=(LinearLayout)rootview.findViewById(R.id.llcupcake);
        dogs=(LinearLayout)rootview.findViewById(R.id.lldogs);



        viewPager.setAnimation(left);
        cardone.setAnimation(right);
        cardtwo.setAnimation(left);
        cardthree.setAnimation(right);



        sliderDotspanel = (LinearLayout)rootview.findViewById(R.id.SliderDots);

        ViewpagerAdapter viewpagerAdapter=new ViewpagerAdapter(getActivity());

        viewPager.setAdapter(viewpagerAdapter);

        dotscount = viewpagerAdapter.getCount();
        dots = new ImageView[dotscount];

        for(int i = 0; i < dotscount; i++){

            dots[i] = new ImageView(getActivity());
            dots[i].setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.nonactive_dots));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            params.setMargins(8, 0, 8, 0);

            sliderDotspanel.addView(dots[i], params);

        }

        dots[0].setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.active_dots));

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                for(int i = 0; i< dotscount; i++){
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.nonactive_dots));
                }

                dots[position].setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.active_dots));

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });



        services.setOnClickListener(v -> {
            Intent services=new Intent(getActivity(),BeautyServices.class);
            startActivity(services);
            getActivity().finish();

        });

        return rootview;
    }


}
