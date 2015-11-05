package com.example.shikhajain.shareride.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.shikhajain.shareride.Interface.Communicator;
import com.example.shikhajain.shareride.Network.GetData;
import com.example.shikhajain.shareride.POJO.Each_User;
import com.example.shikhajain.shareride.R;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by bunty on 10/26/2015.
 */
public class BookARide extends Fragment implements View.OnClickListener {

    EditText no_of_seats;
    Button book_A_Ride;
    Each_User each_user;
    ProgressDialog progressDialog;
    int seats_available;
    int seats_to_be_booked;
    Communicator communicator;


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        communicator= (Communicator) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.book_a_ride,container,false);
        no_of_seats = (EditText) v.findViewById(R.id.Seat_Check);
        book_A_Ride = (Button) v.findViewById(R.id.book_ride);
        book_A_Ride.setOnClickListener(this);
        each_user = getArguments().getParcelable("user_data");
        progressDialog = new ProgressDialog(getActivity());


        Toast.makeText(getContext(), "name is" + each_user.getUname(), Toast.LENGTH_SHORT).show();
        return v;
    }


    @Override
    public void onClick(View v) {
        if (!no_of_seats.getText().toString().equals("") && Integer.parseInt(no_of_seats.getText().toString()) != 0) {
            seats_to_be_booked = Integer.parseInt(no_of_seats.getText().toString());
            //progressDialog.show(this,"checking","checking seat availability",false);
            progressDialog.setMessage("checking seat availability");
            progressDialog.show();
            progressDialog.setCancelable(false);
            final RequestParams requestParams = new RequestParams();
            requestParams.add("offer_ride_id", each_user.getUname());

            GetData.post(getResources().getString(R.string.Book_A_Ride_Seat_Check_Availablity), requestParams, new BaseJsonHttpResponseHandler<JSONObject>() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, JSONObject response) {
                    progressDialog.cancel();
                    Log.d("response for seat available is", response + "");
                    try {

                        String x = response.getString("seats_available");
                        seats_available = Integer.parseInt(x);
                        if (seats_available - seats_to_be_booked < 0) {
                            Toast.makeText(getContext(), "reduce the number of seats", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "perfect", Toast.LENGTH_SHORT).show();
                            callMainActivity();

                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, JSONObject errorResponse) {
                    progressDialog.cancel();
                }

                @Override
                protected JSONObject parseResponse(String rawJsonData, boolean isFailure) throws Throwable {


                    JSONObject jsonObject = new JSONObject(rawJsonData);
                    progressDialog.cancel();
                    return jsonObject;
                }
            });


        } else {
            Toast.makeText(getContext(), "Enter the correct no of seats to be booked", Toast.LENGTH_SHORT).show();
        }
    }

    private void callMainActivity() {
communicator.callRideStatus();

    }
}