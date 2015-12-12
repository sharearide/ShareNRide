package com.example.shikhajain.shareride.Main;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shikhajain.shareride.R;
import com.example.shikhajain.shareride.smsListener.SmsListener;

import org.json.JSONObject;

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

public class OTPActivity extends Activity implements  View.OnClickListener{

    private EditText edtxt_code;
    private TextView txt_next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);


        init();
    }

    private void init() {
        edtxt_code = (EditText) findViewById(R.id.otp_edtxt_code);
        txt_next   = (TextView) findViewById(R.id.otp_txt_next);

        edtxt_code.setEnabled(false);
        txt_next.setOnClickListener(this);
        txt_next.setEnabled(false);

        registerReceiver(broadcastReceiver, new IntentFilter("com.example.shikhajain.shareride.smsListener.SmsListener"));


    }

    BroadcastReceiver broadcastReceiver =  new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            Bundle b = intent.getExtras();

            String code = b.getString("code");

            Log.e("newmesage", "" + code);

            edtxt_code.setEnabled(true);
            edtxt_code.setText(code.substring(code.length() - 6));

            txt_next.setEnabled(true);
            txt_next.setBackgroundColor(getResources().getColor(R.color.LightTextColor));

        }
    };

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.otp_txt_next:

                //Toast.makeText(OTPActivity.this, "HO HO", Toast.LENGTH_SHORT).show();
                apiCalling();
                break;
        }
    }

    public void codeUpdate(String code){
        edtxt_code.setEnabled(true);
        edtxt_code.setText(code);

        txt_next.setEnabled(true);
        txt_next.setBackgroundColor(getResources().getColor(R.color.LightTextColor));

    }

    private void apiCalling(){
        HttpClient hclient = new DefaultHttpClient();
        HttpPost post_url = new HttpPost("http://allrounderservices.com/mypool/verifyOtp.php");

        List<NameValuePair> data_list = new ArrayList<NameValuePair>();
        data_list.add(new BasicNameValuePair("otpCode", edtxt_code.getText().toString()));

        try {
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(data_list);
            post_url.setEntity(entity);
            HttpResponse send_response = hclient.execute(post_url);

            BufferedReader br = new BufferedReader(new InputStreamReader(send_response.getEntity().getContent()));
            String line = br.readLine();
            //Toast.makeText(this, line, Toast.LENGTH_LONG).show();
            Log.d("Response", line);
            JSONObject object = new JSONObject(line.trim());

            String errorBoolean = object.getString("success");
            if (object.getString("success").equalsIgnoreCase("0")) {
                Toast.makeText(this, object.getString("message"), Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(this, object.getString("message"), Toast.LENGTH_LONG).show();
                Intent i = new Intent(this, MainActivity.class);
                // i.putExtra("EmailKey", Pemail.getText().toString());
                startActivity(i);
                OTPActivity.this.finish();
                //String longitude = object.getString("message");
                //Log.d("ErrorBoolean",latitude);
            }
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
