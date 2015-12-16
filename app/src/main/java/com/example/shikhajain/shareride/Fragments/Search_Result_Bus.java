package com.example.shikhajain.shareride.Fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.shikhajain.shareride.Interface.Communicator;
import com.example.shikhajain.shareride.Network.GetData;
import com.example.shikhajain.shareride.POJO.Each_User;
import com.example.shikhajain.shareride.R;
import com.example.shikhajain.shareride.adapter.AdapterSearchResult;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Search_Result_Bus.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Search_Result_Bus#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Search_Result_Bus extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    RecyclerView SeachView;
    AdapterSearchResult adapterSearchResult;
    //    Each_User each_user=new Each_User();
    ArrayList<Each_User> each_users = new ArrayList<>();
    ProgressDialog progressDialog;
    Communicator communicator;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Search_Result_Bus.
     */
    // TODO: Rename and change types and number of parameters
    public static Search_Result_Bus newInstance(String param1, String param2) {
        Search_Result_Bus fragment = new Search_Result_Bus();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public Search_Result_Bus() {
        // Required empty public constructor
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        communicator= (Communicator) getActivity();

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("on create view ", "of card result called");
        each_users.clear();
        View v = inflater.inflate(R.layout.fragment_search__results, container, false);

        SeachView = (RecyclerView) v.findViewById(R.id.rv);
        //      swipeRefreshLayout= (SwipeRefreshLayout) v.findViewById(R.id.swipeRefreshLayout);

        SeachView.setLayoutManager(new LinearLayoutManager(getActivity()));
        SeachView.setHasFixedSize(true);
        //      swipeRefreshLayout.setOnRefreshListener(this);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Fetching The File....");
        progressDialog.show();
        DoJsonParsing();


        /*SeachView.setLayoutManager(new LinearLayoutManager(getActivity()));
        SeachView.setHasFixedSize(true);
        adapterSearchResult = new AdapterSearchResult(getActivity());
        progressDialog = new ProgressDialog(getActivity());
        SeachView.setAdapter(adapterSearchResult);*/


        final GestureDetector mGestureDetector = new GestureDetector(getActivity(), new GestureDetector.SimpleOnGestureListener() {

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }

        });


        SeachView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
                View child = recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());


                if (child != null && mGestureDetector.onTouchEvent(motionEvent)) {

                    Toast.makeText(getActivity(), "clicked", Toast.LENGTH_SHORT).show();
                    sendDataToMainActivity(recyclerView.getChildPosition(child));
                    return true;

                }

                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {


            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });
//        Bundle b=getArguments();
        //      if(b!=null) {
//            Log.d("ride type is", b.getString("ride"));
        // Log.d("position is", b.getInt("position") + "");
        //    }
           /* result= (ListView) v.findViewById(R.id.listView);
        ArrayAdapter<String> l=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,data);
        result.setAdapter(l);
        result.setOnItemClickListener(this);*/


        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("on resume", "called");
        DoJsonParsing();
    }

    private void sendDataToMainActivity(int childPosition) {

        each_users.get(childPosition);


        Log.d("data in the arraylist is", each_users.get(childPosition).getUname() + "");
        /*Intent i=new Intent(getActivity(),BookARide.class);
        i.putExtra("user_data", each_users.get(childPosition));

        startActivity(i);*/
        Bundle bundle=new Bundle();
        bundle.putParcelable("user_data", each_users.get(childPosition));
        communicator.callBookARide(bundle);

    }

    private void DoJsonParsing() {


        final RequestParams requestParams = new RequestParams();
        requestParams.add("Date", "04-08-2015");
        requestParams.add("Source", "Nityanand Nagar, Mumbai, Maharashtra");
        requestParams.add("Destination", "Nityanand Nagar, Mumbai, Maharashtra");
        GetData.post("request_ride", requestParams, new BaseJsonHttpResponseHandler<JSONObject>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, JSONObject response) {
                Log.d("data is", response + "");


                try {

                    JSONArray rides = response.getJSONArray("rides");

                    for (int i = 0; i < rides.length(); i++) {
                        ;
                        JSONObject data = rides.getJSONObject(i);
                        String Uid = data.getString("id");
                        String Uname = data.getString("userId");
                        String source = data.getString("source");
                        String destination = data.getString("destination");
                        String time = data.getString("time");
                        String seats = data.getString("seats");
                        String fare = data.getString("fare");
                        Log.d("parsed data is", Uname + source + destination + time + seats + fare);

                        Each_User each_user = new Each_User(Uid,Uname,"",seats,"",fare,time,source,destination);

/*                        each_user.setUname(Uname);
                        each_user.setUsource(source);
                        each_user.setUdestination(destination);
                        each_user.setUtime(time);
                        each_user.setUseat(seats);
                        each_user.setUfare(fare);*/

                        each_users.add(each_user);


                        Log.d("list size is", each_users.size() + "");


                    }


                    adapterSearchResult = new AdapterSearchResult(getActivity(), each_users);
                    SeachView.setAdapter(adapterSearchResult);

                    //              progressDialog.dismiss();
                    // adapterSearchResult.SetData(each_users);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

/*
                {
                    "error": 0,
                        "rides": [
                    {
                        "id": "7",
                            "userId": "1",
                            "source": "AWADHPURI",
                            "destination": "MP NAGAR",
                            "date": "2015-09-19",
                            "time": "06:59:59",
                            "latitude": "23.2376957",
                            "longitude": "77.4927514",
                            "vehicleId": "1",
                            "seats": "0",
                            "fare": "0",
                            "status": "0",
                            "timestamp": "0000-00-00 00:00:00"
                    },*/


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, JSONObject errorResponse) {

            }

            @Override
            protected JSONObject parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                progressDialog.cancel();
                JSONObject jsonObject = new JSONObject(rawJsonData);
                return jsonObject;
            }
        });

    }
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
  //          mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

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

}
