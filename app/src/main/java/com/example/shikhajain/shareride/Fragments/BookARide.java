package com.example.shikhajain.shareride.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


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
public class BookARide extends AppCompatActivity implements View.OnClickListener {

    EditText no_of_seats;
    Button book_A_Ride;
    Each_User each_user;
    ProgressDialog progressDialog;
    int seats_available;
    int seats_to_be_booked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_a_ride);

        no_of_seats = (EditText) findViewById(R.id.Seat_Check);
        book_A_Ride = (Button) findViewById(R.id.book_ride);
        book_A_Ride.setOnClickListener(this);
        each_user = getIntent().getParcelableExtra("user_data");
        progressDialog = new ProgressDialog(this);


        Toast.makeText(BookARide.this, "name is" + each_user.getUname(), Toast.LENGTH_SHORT).show();


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
                            Toast.makeText(getApplicationContext(), "reduce the number of seats", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "perfect", Toast.LENGTH_SHORT).show();
                            callStatusOfRide();

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
            Toast.makeText(this, "Enter the correct no of seats to be booked", Toast.LENGTH_SHORT).show();
        }
    }

    private void callStatusOfRide() {

     RideStatus rideStatus=new RideStatus();
        getSupportFragmentManager().beginTransaction().replace(R.id.book_a_ride,rideStatus).commit();

    }
}