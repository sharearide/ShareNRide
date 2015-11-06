package com.example.shikhajain.shareride.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shikhajain.shareride.R;

/**
 * Created by Shikha on 06-11-2015.
 */
public class UserRegistration extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.registration, container, false);

        return rootView;
    }
}
