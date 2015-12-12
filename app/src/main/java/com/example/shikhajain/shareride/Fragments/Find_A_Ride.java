package com.example.shikhajain.shareride.Fragments;


import android.app.DatePickerDialog;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.shikhajain.shareride.AutoComplete.PlaceJSONParser;
import com.example.shikhajain.shareride.Interface.Communicator;
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
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class Find_A_Ride extends Fragment implements View.OnClickListener, View.OnFocusChangeListener {


    AutoCompleteTextView Fsource,Fdestination, Fboarding;
            EditText Fdate;
    ImageButton FsourceX,FdestinationX;

   Button Search;
    private DatePickerDialog FPickDate;
    private SimpleDateFormat dateFormatter;
    PlacesTask placesTask;
    ParserTask parserTask;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    Geocoder geoCoder;
    private Communicator communicator;


    public Find_A_Ride() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        communicator= (Communicator) getActivity();
    }

    public static Find_A_Ride newInstance(String param1, String param2) {
       Find_A_Ride fragment = new Find_A_Ride();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v=inflater.inflate(R.layout.fragment_find__a__ride, container, false);
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        Fsource= (AutoCompleteTextView) v.findViewById(R.id.Fsource);
        Fdestination= (AutoCompleteTextView) v.findViewById(R.id.Fdestination);
        Fboarding = (AutoCompleteTextView) v.findViewById(R.id.Fboarding);
        Fdate=(EditText) v.findViewById(R.id.Fdate);
        FsourceX= (ImageButton) v.findViewById(R.id.FsourceX);
        FdestinationX= (ImageButton) v.findViewById(R.id.FdestinationX);
        Search= (Button) v.findViewById(R.id.Fsearch);
        FsourceX.setOnClickListener(this);
        FdestinationX.setOnClickListener(this);
        Search.setOnClickListener(this);
        Fdate.setOnClickListener(this);
        setDateTimeField();
        Fdate.setOnFocusChangeListener(this);


        Fsource.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                placesTask = new PlacesTask();
                placesTask.execute(s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }
        });

        Fdestination.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                placesTask = new PlacesTask();
                placesTask.execute(s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }
        });

        Fboarding.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                placesTask = new PlacesTask();
                placesTask.execute(s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }
        });

        return v;
    }

    private void setDateTimeField() {

        Calendar newCalendar = Calendar.getInstance();
        FPickDate = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                Fdate.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));


    }

    @Override
    public void onClick(View view) {

        switch(view.getId())
        {
            case R.id.FsourceX:
                Fsource.setText("");
                break;


            case R.id.FdestinationX:
                Fdestination.setText("");
                break;

            case R.id.Fsearch:

                //if(Fsource.getText().toString())
                Log.d("data",Fsource.getText().toString() +""+ Fdestination.getText()+""+ Fboarding.getText()+""+ Fdate.getText());
                if(!Fsource.getText().toString().equals("") &&
                        !Fdestination.getText().toString().equals("") &&
                        !Fboarding.getText().toString().equals("") &&
                !Fdate.getText().toString().equals(""))
                {
                    Toast.makeText(getActivity(),"all the details entered",Toast.LENGTH_SHORT).show();
                    String FindARideDeatils[]=new String[4];
                    FindARideDeatils[0]=Fsource.getText().toString();
                    FindARideDeatils[1]=Fdestination.getText().toString();
                    FindARideDeatils[2]=Fdate.getText().toString();
                    FindARideDeatils[3]="";
                    communicator.Fsearch(FindARideDeatils);

                }
                else
                {
                    Toast.makeText(getActivity(),"enter all the details",Toast.LENGTH_SHORT).show();
                }


                break;

           /* case R.id.Fsearch:
                //Find_A_Ride find_a_ride=new Find_A_Ride();

                FragmentManager fragmentManager=getFragmentManager();
               FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
               /* Intent i=new Intent(getActivity(), NavigationDrawer.class);
                i.putExtra("Fsearch",true);
                startActivity(i);

                Find_Ride_Search_Result find_ride_search_result=new Find_Ride_Search_Result();

// /                fragmentTransaction.replace(R.id.findARide,find_ride_search_result).commit();
                fragmentTransaction
                        .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                        .replace(R.id.findride,find_ride_search_result)
                        ;
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
//                checkEntries();


                /*Intent i=new Intent(getActivity(), FindRideActivity.class);
                startActivity(i);

                break;*/
            case R.id.Fdate:
                FPickDate.show();
                break;

        }
    }

    private void checkEntries() {
        if(!Fdate.getText().toString().equals("") && !Fsource.getText().toString().equals("")
           && !Fdestination.getText().toString().equals(""))
        {
            String Date=Fdate.getText().toString();
            String source=Fsource.getText().toString();
            String destination=Fdestination.getText().toString();
            Log.d("data","date is"+Date+"source is"+source+"destination is"+destination);
            convertAddress(source);

        }
        else
        {
            Toast.makeText(getActivity(),"enter all the data",Toast.LENGTH_SHORT).show();
        }
    }

    public void convertAddress(String address) {

        Geocoder gc = new Geocoder(getActivity(), Locale.getDefault());
        String result = "";
        Address address1 = null, address_d = null;
        // LatLng src_pts = new LatLng(0,0);
        //LatLng dest_pts= new LatLng(0,0);
        //finding Source and Destination names's  Lat,Long
        try {
            List addressList = gc.getFromLocationName(address, 1);
            if (addressList != null && addressList.size() > 0) {
                address1 = (Address) addressList.get(0);
                StringBuilder sb = new StringBuilder();
                sb.append(address1.getLatitude()).append("\n");
                sb.append(address1.getLongitude()).append("\n");
                result = sb.toString();
                Log.d("lat long",result);
                //src_pts = new LatLng(address.getLatitude(),address.getLongitude());
            }


        /*if (address != null && !address.isEmpty()) {
            try {

                Log.d("address is",address);
                List<Address> addressList = geoCoder.getFromLocationName(address, 5);
                Log.d("size of list is",addressList.size()+"");
                if (addressList != null && addressList.size() > 0) {
                    double lat = addressList.get(0).getLatitude();
                    double lng = addressList.get(0).getLongitude();
                    Log.d("source lat long is", "" + lat + "" + lng);
//                    Toast.makeText(getContext(),""+lat+""+lng,Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } // end catch
        }*/ // end if
        } // end convert
        catch (IOException e) {
            e.printStackTrace();
        }
    }
        @Override
    public void onFocusChange(View view, boolean b) {
        if(b)
        {
            setDateTimeField();
            FPickDate.show();
        }
    }



    private class ParserTask extends AsyncTask<String, Integer, List<HashMap<String,String>>> {

        JSONObject jObject;

        @Override
        protected List<HashMap<String, String>> doInBackground(String... jsonData) {

            List<HashMap<String, String>> places = null;

            PlaceJSONParser placeJsonParser = new PlaceJSONParser();

            try{
                jObject = new JSONObject(jsonData[0]);

                // Getting the parsed data as a List construct
                places = placeJsonParser.parse(jObject);

            }catch(Exception e){
                Log.d("Exception", e.toString());
            }
            return places;
        }

        @Override
        protected void onPostExecute(List<HashMap<String, String>> result) {

            String[] from = new String[] { "description"};
            int[] to = new int[] { android.R.id.text1 };

            // Creating a SimpleAdapter for the AutoCompleteTextView
            SimpleAdapter adapter = new SimpleAdapter(getContext(), result, android.R.layout.simple_list_item_1, from, to);

            // Setting the adapter
            Fsource.setAdapter(adapter);
            Fdestination.setAdapter(adapter);
            Fboarding.setAdapter(adapter);
        }
    }

    // Fetches all places from GooglePlaces AutoComplete Web Service
    private class PlacesTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... place) {
            // For storing data from web service
            String data = "";

            // Obtain browser key from https://code.google.com/apis/console
            String key = "key=AIzaSyDbqzXfYEJIMe5Py6gm4vaE5bHoZ-jy27o";

            String input="";

            try {
                input = "input=" + URLEncoder.encode(place[0], "utf-8");
            } catch (UnsupportedEncodingException e1) {
                e1.printStackTrace();
            }

            // place type to be searched
            String types = "types=geocode";

            // Sensor enabled
            String sensor = "sensor=false";

            // Building the parameters to the web service
            String parameters = input+"&"+types+"&"+sensor+"&"+key;

            // Output format
            String output = "json";

            // Building the url to the web service
            String url = "https://maps.googleapis.com/maps/api/place/autocomplete/"+output+"?"+parameters;
            Log.d("url is",url);

            try{
                // Fetching the data from we service
                data = downloadUrl(url);
            }catch(Exception e){
                Log.d("Background Task",e.toString());
            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            // Creating ParserTask
            parserTask = new ParserTask();

            // Starting Parsing the JSON string returned by Web Service
            parserTask.execute(result);
        }
    }

    /** A method to download json data from url */
    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try{
            URL url = new URL(strUrl);

            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();

            // Connecting to url
            urlConnection.connect();

            // Reading data from url
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while( ( line = br.readLine()) != null){
                sb.append(line);
            }

            data = sb.toString();

            br.close();

        }catch(Exception e){
            Log.d("Exception", " Exception while downloading url"+e.toString());
        }finally{
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }

}
