package com.example.shikhajain.shareride.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.shikhajain.shareride.R;

import it.neokree.materialtabs.MaterialTab;
import it.neokree.materialtabs.MaterialTabHost;
import it.neokree.materialtabs.MaterialTabListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class RideStatus extends Fragment implements MaterialTabListener {

    private MaterialTabHost tabHost;
    private ViewPager viewPager;


    public RideStatus() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_ride_status, container, false);
        tabHost= (MaterialTabHost) v.findViewById(R.id.materialTabHost);
        viewPager= (ViewPager) v.findViewById(R.id.viewPager);
        ViewPagerAdapter adapter=new ViewPagerAdapter(getFragmentManager());


        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                // when user do a swipe the selected tab change
                tabHost.setSelectedNavigationItem(position);
            }
        });


        for (int i = 0; i < adapter.getCount(); i++) {
            tabHost.addTab(
                    tabHost.newTab()
                            .setText(adapter.getPageTitle(i))
                            .setTabListener(this)
            );
        }



        return v;

    }

    @Override
    public void onTabSelected(MaterialTab materialTab) {
        viewPager.setCurrentItem(materialTab.getPosition());
    }

    @Override
    public void onTabReselected(MaterialTab materialTab) {

    }

    @Override
    public void onTabUnselected(MaterialTab materialTab) {

    }


    private class ViewPagerAdapter extends FragmentPagerAdapter {

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Log.d("fragment position is",position+"");
            Fragment fragment=null;

            switch(position)
            {
                case 0:
                    Log.d("call to","fagment 1");
                   // fragment= Search_Result_Bus.newInstance("", "");
                    break;


                case 1:
                    Log.d("call to","fagment 2");
                  //  fragment= Search_Result_Two_Wheeler.newInstance("", "");

                    break;




            }
            return fragment;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return getResources().getStringArray(R.array.Status)[position];
            //   return "one";
        }
    }
}