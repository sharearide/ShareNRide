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
 * {@link Offer_a_Ride.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Offer_a_Ride#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Offer_a_Ride extends Fragment implements View.OnClickListener, View.OnFocusChangeListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //Layout Components
    AutoCompleteTextView RSource, RDestination;// RBoarding;
    private DatePickerDialog FPickDate;
    private TimePickerDialog FPickTime;
    private SimpleDateFormat dateFormatter;
    EditText RDate, RTime, RSeat, RPrice, Rcomment;
    PlacesTask placesTask;
    ParserTask parserTask;

    Spinner vehicleType;
    private OnFragmentInteractionListener mListener;
    ImageButton SeatPlus, SeatMinus,PriceMinus,PricePlus;
    Button btnNext;
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Offer_a_Ride.
     */
    // TODO: Rename and change types and number of parameters
    public static Offer_a_Ride newInstance(String param1, String param2) {
        Offer_a_Ride fragment = new Offer_a_Ride();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public Offer_a_Ride() {
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
        View v=inflater.inflate(R.layout.offer_outstation_layout, container, false);

        View startLoc = (View)v.findViewById(R.id.startLocationLayout);
        View destLoc = (View)v.findViewById(R.id.endLocationLayout);
       // View boardLoc = (View)v.findViewById(R.id.stopOverLayout);

        TextView toLabel = (TextView)destLoc.findViewById(R.id.fromLabel);
        toLabel.setText("TO: ");

        RSource = (AutoCompleteTextView)startLoc.findViewById(R.id.currentLocation);
        RDestination = (AutoCompleteTextView)destLoc.findViewById(R.id.currentLocation);
        RDestination.setHint("Destination");
       // RBoarding = (AutoCompleteTextView)boardLoc.findViewById(R.id.stopOverLocation);

        RSource.addTextChangedListener(new TextWatcher() {

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
        RDestination.addTextChangedListener(new TextWatcher() {

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
       /* RBoarding.addTextChangedListener(new TextWatcher() {

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
        });*/

        //RelativeLayout dateTime = (RelativeLayout)v.findViewById(R.id.returnJourneyLayout);
        dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        RDate=(EditText)v.findViewById(R.id.startDateTimeEditText);
        RDate.setOnClickListener(this);
        setDateTimeField();
        RDate.setOnFocusChangeListener(this);

        RTime = (EditText)v.findViewById(R.id.startTimeEditText);
        RTime.setOnClickListener(this);
        setTimeField();

        //For Seats
        RSeat = (EditText)v.findViewById(R.id.availseatEditText);
        Rcomment = (EditText)v.findViewById(R.id.Rcomment);
        SeatMinus = (ImageButton)v.findViewById(R.id.minusseat);
        SeatPlus = (ImageButton)v.findViewById(R.id.plusseat);
        SeatMinus.setOnClickListener(this);
        SeatPlus.setOnClickListener(this);

        //For Price
        RPrice = (EditText)v.findViewById(R.id.priceseatEditText);

        PriceMinus = (ImageButton)v.findViewById(R.id.minusprice);
        PricePlus = (ImageButton)v.findViewById(R.id.plusprice);
        PriceMinus.setOnClickListener(this);
        PricePlus.setOnClickListener(this);

        btnNext = (Button)v.findViewById(R.id.btn_next);
        btnNext.setOnClickListener(this);

        vehicleType = (Spinner)v.findViewById(R.id.vehicleType);
       /* Resources res = getResources();
        ArrayAdapter dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, res.getStringArray(R.array.tabs) );

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        vehicleType.setAdapter(dataAdapter);*/

        return v;
    }

    private void setDateTimeField() {

        Calendar newCalendar = Calendar.getInstance();
        FPickDate = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                RDate.setText(dateFormatter.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }

    private void setTimeField(){
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        FPickTime = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                RTime.setText( selectedHour + ":" + selectedMinute + ":00");
            }
        }, hour, minute, true);//Yes 24 hour time

    }
    private static String padding_str(int c) {
        if (c >= 10)
        return String.valueOf(c);
        else
        return "0" + String.valueOf(c);
    }



    // TODO: Rename method, update argument and hook method into UI event
   /* public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }*/

    //@Override
  /*  public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }*/

   // @Override
   /* public void onDetach() {
        super.onDetach();
        mListener = null;
    }*/

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    public void onClick(View view) {
        String seat_cnt = null;
        int cnt = 0;
        switch(view.getId())
        {
            case R.id.startDateTimeEditText:
                FPickDate.show();
                break;

            case R.id.startTimeEditText:
                FPickTime.setTitle("Select Time");
                FPickTime.show();
                break;

            case R.id.plusseat: seat_cnt = RSeat.getText().toString();
            cnt = Integer.parseInt(seat_cnt);
                cnt +=1;
                RSeat.setText(String.valueOf(cnt));
            break;

            case R.id.minusseat:
                seat_cnt = RSeat.getText().toString();
                cnt = Integer.parseInt(seat_cnt);
                cnt -=1;
                if (cnt >= 0) {
                    RSeat.setText(String.valueOf(cnt));
                }
                break;

            case R.id.plusprice: seat_cnt = RPrice.getText().toString();
                cnt = Integer.parseInt(seat_cnt);
                cnt +=5;
                RPrice.setText(String.valueOf(cnt));
                break;

            case R.id.minusprice:
                seat_cnt = RPrice.getText().toString();
                cnt = Integer.parseInt(seat_cnt);
                cnt -=5;
                if (cnt >= 0) {
                    RPrice.setText(String.valueOf(cnt));
                }
                break;
            case R.id.btn_next:
                //Rcomment.getText.toStirng will contain the comment.
                send_OfferRideData();
                Fragment rideStatus=new RideStatus();
                getFragmentManager().beginTransaction().replace(R.id.frame_container,rideStatus).addToBackStack(null).commit();
                break;
        }

    }
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
            RSource.setAdapter(adapter);
            RDestination.setAdapter(adapter);
           // RBoarding.setAdapter(adapter);
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

    public void send_OfferRideData()
    {
        // if (req_id == res_id) {
        //   Bundle b1 = io.getExtras();

        HttpClient hclient = new DefaultHttpClient();
        HttpPost post_url = new HttpPost("http://allrounderservices.com/mypool/offer_ride.php");

        List<NameValuePair> data_list = new ArrayList<NameValuePair>();
        data_list.add(new BasicNameValuePair("userId", "1"));
        data_list.add(new BasicNameValuePair("source", RSource.getText().toString()));
        data_list.add(new BasicNameValuePair("destination", RDestination.getText().toString()));
        data_list.add(new BasicNameValuePair("date", RDate.getText().toString()));
        data_list.add(new BasicNameValuePair("time", RTime.getText().toString() ));
        data_list.add(new BasicNameValuePair("latitude","0"));
        data_list.add(new BasicNameValuePair("longitude","0"));
        //data_list.add(new BasicNameValuePair("vehicleId", "1"));
        data_list.add(new BasicNameValuePair("vehicle_type",vehicleType.getSelectedItem().toString()));
        data_list.add(new BasicNameValuePair("seats", RSeat.getText().toString()));
        data_list.add(new BasicNameValuePair("fare", RPrice.getText().toString()));
        data_list.add(new BasicNameValuePair("remarks",Rcomment.getText().toString()));

        try {

            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(data_list);
            post_url.setEntity(entity);
            HttpResponse send_response = hclient.execute(post_url);

            BufferedReader br = new BufferedReader(new InputStreamReader(send_response.getEntity().getContent()));
            String line = br.readLine();
            Toast.makeText(getActivity(), line, Toast.LENGTH_LONG).show();
            Log.d("Response",line);

        } catch (Exception e) {
            System.err.println(e);
        }
        //}
        //else
        //  Log.d("Req_id",res_id+","+req_id);
    }
}
