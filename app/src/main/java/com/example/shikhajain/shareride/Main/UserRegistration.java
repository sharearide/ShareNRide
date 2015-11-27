package com.example.shikhajain.shareride.Main;


import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;


import com.example.shikhajain.shareride.R;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.entity.UrlEncodedFormEntity;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.message.BasicNameValuePair;

/**
 * Created by Shikha on 06-11-2015.
 */
public class UserRegistration extends Activity implements View.OnClickListener {
    //@Override
   /* public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.registration, container, false);

        return rootView;
    }*/
    Button Register;
    EditText Pname, Pemail,Ppass,Pmobile;
    private RadioGroup GenderGroup;
    private RadioButton GenderButton;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);
        StrictMode.ThreadPolicy tr = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(tr);

        Register = (Button)findViewById(R.id.btn_register);
        Register.setOnClickListener(this);

        Pname = (EditText)findViewById(R.id.Pname);
        Pemail = (EditText)findViewById(R.id.Pemail);
        Ppass = (EditText)findViewById(R.id.Ppass);
        Pmobile = (EditText)findViewById(R.id.Pmobile);
        GenderGroup = (RadioGroup) findViewById(R.id.GenderGroup);
    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.btn_register:
                int selectedId = GenderGroup.getCheckedRadioButtonId();

                // find the radiobutton by returned id
                GenderButton = (RadioButton) findViewById(selectedId);

                send_UserData();
                break;
        }
    }
    public void send_UserData()
    {
        // if (req_id == res_id) {
        //   Bundle b1 = io.getExtras();

        HttpClient hclient = new DefaultHttpClient();
            HttpPost post_url = new HttpPost("http://allrounderservices.com/mypool/register.php");

        List<NameValuePair> data_list = new ArrayList<NameValuePair>();
        data_list.add(new BasicNameValuePair("name", Pname.getText().toString()));
        data_list.add(new BasicNameValuePair("email", Pemail.getText().toString()));
        data_list.add(new BasicNameValuePair("mobile", Pmobile.getText().toString()));
        data_list.add(new BasicNameValuePair("password", Ppass.getText().toString() ));
        data_list.add(new BasicNameValuePair("gender",GenderButton.getText().toString()));
        data_list.add(new BasicNameValuePair("status","1"));

        try {

            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(data_list);
            post_url.setEntity(entity);
            HttpResponse send_response = hclient.execute(post_url);

            BufferedReader br = new BufferedReader(new InputStreamReader(send_response.getEntity().getContent()));
            String line = br.readLine();
            Toast.makeText(this, line, Toast.LENGTH_LONG).show();
            Log.d("Response", line);
        } catch (Exception e) {
            System.err.println(e);
        }
        //}
        //else
        //  Log.d("Req_id",res_id+","+req_id);
    }
}


