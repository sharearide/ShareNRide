package com.example.shikhajain.shareride.Main;


import android.app.Activity;
import android.content.Intent;
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

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        getActionBar().setBackgroundDrawable(getResources().getDrawable(R.color.colorBase));
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

        //validation
        Boolean flag = datavalidation();

        //------- end validation
        if (flag = true) {


            HttpClient hclient = new DefaultHttpClient();
            HttpPost post_url = new HttpPost("http://allrounderservices.com/mypool/register.php");

            List<NameValuePair> data_list = new ArrayList<NameValuePair>();
            data_list.add(new BasicNameValuePair("name", Pname.getText().toString()));
            data_list.add(new BasicNameValuePair("email", Pemail.getText().toString()));
            data_list.add(new BasicNameValuePair("mobile", Pmobile.getText().toString()));
            data_list.add(new BasicNameValuePair("password", Ppass.getText().toString()));
            data_list.add(new BasicNameValuePair("gender", GenderButton.getText().toString()));
            data_list.add(new BasicNameValuePair("status", "1"));

            try {

                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(data_list);
                post_url.setEntity(entity);
                HttpResponse send_response = hclient.execute(post_url);

                BufferedReader br = new BufferedReader(new InputStreamReader(send_response.getEntity().getContent()));
                String line = br.readLine();
                //Toast.makeText(this, line, Toast.LENGTH_LONG).show();
                Log.d("Response", line);
                JSONObject object = new JSONObject(line.trim());

                String errorBoolean = object.getString("error");
                if (errorBoolean == "true") {
                    Toast.makeText(this, "Email ID already exist.", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(this, "User Registered Successfully", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(this, Login.class);
                    // i.putExtra("EmailKey", Pemail.getText().toString());
                    startActivity(i);
                    UserRegistration.this.finish();
                    //String longitude = object.getString("message");
                    //Log.d("ErrorBoolean",latitude);
                }
            } catch (Exception e) {
                System.err.println(e);
            }

        }
    }

    private Boolean datavalidation() {
        Pattern pattern1 = Pattern.compile("^([a-zA-Z0-9_.-])+@([a-zA-Z0-9_.-])+\\.([a-zA-Z])+([a-zA-Z])+");

        Matcher matcher1 = pattern1.matcher(Pemail.getText());

        if (!matcher1.matches()) {
            Toast.makeText(this, "Incorrect Email Format", Toast.LENGTH_LONG).show();
            return false;
        }
        if (Pmobile.getText().toString().length() != 10)
        {
            Toast.makeText(this, "Incorrect Phone Number", Toast.LENGTH_LONG).show();
            return false;
        }
        if ((Ppass.getText().toString() == null)|| (Pname.getText().toString() == null)){
            Toast.makeText(this, "All fields required", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}


