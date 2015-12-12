package com.example.shikhajain.shareride.Fragments;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.KeyListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


import com.example.shikhajain.shareride.AutoComplete.PlaceJSONParser;
import com.example.shikhajain.shareride.R;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.entity.UrlEncodedFormEntity;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.message.BasicNameValuePair;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Fragment_ViewUser.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Fragment_ViewUser#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_ViewUser extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    TextView editName, editMobEmail;
    EditText UEmail, UName,UMobile;
    KeyListener key1, key2, key3;
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Offer_a_Ride.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_ViewUser newInstance(String param1, String param2) {
        Fragment_ViewUser fragment = new Fragment_ViewUser();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public Fragment_ViewUser() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        StrictMode.ThreadPolicy tr = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(tr);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.viewprofile, container, false);
        editMobEmail = (TextView)v.findViewById(R.id.edit_email);
        editName = (TextView)v.findViewById(R.id.edit_name);
        editMobEmail.setOnClickListener(this);
        editName.setOnClickListener(this);

        UEmail = (EditText)v.findViewById(R.id.Pemail);
        key1 = UEmail.getKeyListener();
        UEmail.setKeyListener(null);
        UName = (EditText)v.findViewById(R.id.Pname);
        key2 = UName.getKeyListener();
        UName.setKeyListener(null);
        UMobile = (EditText)v.findViewById(R.id.Pmobile);
        key3 = UMobile.getKeyListener();
        UMobile.setKeyListener(null);
        return v;
    }






    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    public void onClick(View view) {
        switch (view.getId()) {
                case R.id.edit_email:
                    UEmail.setKeyListener(key1);
                    UEmail.setFocusable(true);
                    UMobile.setKeyListener(key3);
                    break;
                case R.id.edit_name:
                    UName.setKeyListener(key2);
                    UName.setFocusable(true);
                    break;
                default: break;
        }
    }
}
