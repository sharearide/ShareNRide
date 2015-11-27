package com.example.shikhajain.shareride.Main;

/**
 * Created by bunty on 11/11/2015.
 */

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.shikhajain.shareride.Network.GetData;
import com.example.shikhajain.shareride.R;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import cz.msebera.android.httpclient.Header;


public class Login extends Activity implements View.OnClickListener
        , GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, FacebookCallback<LoginResult> {

    private static final int RC_SIGN_IN = 0;
    private GoogleApiClient mGoogleApiClient;
    private boolean mIsResolving = false;
    private boolean mShouldResolve = false;
    CallbackManager callbackManager;
    static String loginStatus="loginStatus";
    static SharedPreferences sharedPreferencesLoginStatus;
     static SharedPreferences.Editor editor;


    Button login, fblogin, signup;
    EditText uname, upass;
    //    LoginButton loginButton;
    ProgressDialog progressDialog;

    String name, pass,facebookname, fid, femail, id, headerCode, errorMessage, U_verified,
            U_name, U_email, U_police, U_address, U_number, U_landmark, U_image,U_gender;

    String GpersonName="",GpersonPhotoUrl="",GpersonGooglePlusProfile="";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //      FacebookSdk.sdkInitialize(this);
        setContentView(R.layout.login);
        getActionBar().setBackgroundDrawable(getResources().getDrawable(R.color.colorBase));
        setlayout();
        getvariables();
       /* fbkey();
        callbackManager = CallbackManager.Factory.create();
        loginButton.setReadPermissions(Arrays.asList("public_profile, email, user_birthday, user_friends"));
        loginButton.registerCallback(callbackManager, this);*/
        login.setOnClickListener(this);
        signup.setOnClickListener(this);


        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Plus.API)
                .addScope(new Scope(Scopes.PROFILE))
                .build();


    }

    private void fbkey() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.example.femion_3.zanskar",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
        //check if the user is already logged  in
        login();
    }


    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();
    }

    private void getvariables() {
        name = uname.getText().toString();
        pass = upass.getText().toString();
    }

    private void setlayout() {
        login = (Button) findViewById(R.id.Ulogin);
        //fblogin = (Button) findViewById(R.id.login_button);
        uname = (EditText) findViewById(R.id.Uname);
        upass = (EditText) findViewById(R.id.Upass);
        signup = (Button)findViewById(R.id.USignUp);
        //findViewById(R.id.sign_in_button).setOnClickListener(this);
        //    loginButton = (LoginButton) findViewById(R.id.login_button);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Ulogin:
                hideSoftKeyboard(this);
                RequestParams params = new RequestParams();
                params.put("user_email", uname.getText().toString());
                params.put("password", upass.getText().toString());
                if (!uname.getText().toString().equals("") && !upass.getText().toString().equals("")) {
                    progressDialog = new ProgressDialog(this);
                    progressDialog.setMessage("checking the credentials");
                    progressDialog.show();
                    progressDialog.setCancelable(false);
                    GetData.post("login", params, new BaseJsonHttpResponseHandler<JSONObject>() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, JSONObject response) {
                            Log.d("response is", response + "");
                            try {
                                String error=response.getString("error");

                                if(error.equals("0")) {

                                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(i);
                                }
                                else{
                                    String error_message=response.getString("error_msg");
                                    Toast.makeText(getApplication(),error_message,Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, JSONObject errorResponse) {

                        }

                        @Override
                        protected JSONObject parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                            Log.d("response in parseResponse is", rawJsonData);
                            JSONObject jsonObject = new JSONObject(rawJsonData);
                            progressDialog.cancel();
                            return jsonObject;
                        }
                    });
                }

                else
                {
                    Toast.makeText(this,"enter all the details",Toast.LENGTH_SHORT).show();
                }
                //  GetData.post("login",params,new JsonHttpResponseHandler());
/*
                    GetData.post("login", params, new JsonHttpResponseHandler() {

                        public void onSuccess(int statusCode, PreferenceActivity.Header[] headers, JSONObject response) {
                            Log.d("data returned from url is", "" + response);
                            try {
                                id = response.getString("user_id");
                                U_verified = response.getString("U_verified");
                                headerCode = response.getString("headerCode");
                                errorMessage = response.getString("errorMessage");
                                U_name = response.getString("U_name");
                                U_email = response.getString("U_email");
                                U_police = response.getString("U_police");
                                U_address = response.getString("U_address");
                                U_number = response.getString("U_number");
                                U_landmark = response.getString("U_landmark");
                                U_image = response.getString("U_image");
                                U_gender = response.getString("U_gender");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            if (!id.equals("") && !headerCode.equals("1") && errorMessage.equals("") && !U_verified.equals("0")) {
                                settings = getApplication().getSharedPreferences(NavigationDrawer.PREFS_NAME, 0);
                                editor = settings.edit();
                                setTheUserDetailsInSharedPreferenec();
                                progressDialog.cancel();

                                Intent i1 = new Intent(getApplicationContext(), NavigationDrawer.class);
                                startActivity(i1);
                            } else {
                                if (U_verified.equals("0")) {
                                    progressDialog.cancel();
                                   // Intent i1 = new Intent(getApplicationContext(), Authentication.class);
                                   // startActivity(i1);
                                } else {
                                    if (errorMessage.equals("Please enter valid email or password")) {
                                        progressDialog.cancel();
                                        Toast.makeText(getApplicationContext(), "Please enter valid email or password", Toast.LENGTH_SHORT).show();

                                    } else {
                                        progressDialog.cancel();
                                        Toast.makeText(getApplicationContext(), "user doesn't exists", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                            Log.d("error", responseString);
                        }
                    });
                } else {
                    Toast.makeText(this, "enter the details", Toast.LENGTH_SHORT).show();
                }*/


                break;

      /*      case R.id.sign_in_button:
                mShouldResolve = true;
                mGoogleApiClient.connect();
                onSignInClicked();
                break;*/
            case R.id.USignUp:
                Intent i = new Intent(getApplicationContext(),UserRegistration.class);
                startActivity(i);
                break;
            default:
                break;
        }
    }

    public static void hideSoftKeyboard(Activity activity) {
        if (activity.getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        }
    }

    private void setTheUserDetailsInSharedPreferenec() {
        editor.putString("U_id", id);
        editor.putString("U_name", U_name);
        editor.putString("U_email", U_email);
        editor.putString("U_police", U_police);
        editor.putString("U_address", U_address);
        editor.putString("U_number", U_number);
        editor.putString("U_landmark", U_landmark);
        editor.putString("U_verified", U_verified);
        editor.putString("U_image", U_image);
        editor.putString("U_gender", U_gender);
        editor.commit();
    }

    public void performLoginViaFacebookAndGmail(final String userid, final String facebookname, final String femail) {

  /*      System.out.println("login via fb called"+userid);

        RequestParams params = new RequestParams();

        params.put("U_id", userid);
        params.put("U_email",femail);
        params.put("U_name",facebookname);
        params.put("U_image","https://graph.facebook.com/"+userid+"/picture?type=large");
        Log.d("U_image", "https://graph.facebook.com/" + userid + "/picture?type=large");

        if (!userid.equals("")) {
            GetData.post("loginfb", params, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    Log.d("data returned from fb url is", "" + response);
                    try {
                        id = response.getString("user_id");
                        U_verified = response.getString("U_verified");
                        headerCode = response.getString("headerCode");
                        errorMessage = response.getString("errorMessage");
                        U_name = response.getString("U_name");
                        U_email = response.getString("U_email");
                        U_police = response.getString("U_police");
                        U_address = response.getString("U_address");
                        U_number = response.getString("U_number");
                        U_landmark = response.getString("U_landmark");
                        U_image=response.getString("U_image");
                        U_gender=response.getString("U_gender");                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (!id.equals("") && !headerCode.equals("1") && errorMessage.equals("") && !U_verified.equals("0")) {

//                        settings = getApplication().getSharedPreferences(NavigationDrawer.PREFS_NAME, 0);
                        editor = settings.edit();


                        editor.putString("U_id", id);
                        editor.putString("U_name", U_name);
                        editor.putString("U_email", U_email);
                        editor.putString("U_police", U_police);
                        editor.putString("U_address", U_address);
                        editor.putString("U_number", U_number);
                        editor.putString("U_landmark", U_landmark);
                        editor.putString("U_verified", U_verified);
                        editor.putString("U_image",U_image);
                        editor.putString("U_gender",U_gender);

                        editor.commit();

                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(i);
                        Log.d("data added", "to shared preference");

                    } else {
                        if (U_verified.equals("0")) {

                            *//*Intent i1 = new Intent(getApplicationContext(), Authentication.class);
                            startActivity(i1);*//*
                        } else {
                            if (errorMessage.equals("Please enter valid email or password")) {

                                Toast.makeText(getApplicationContext(), "Please enter valid email or password", Toast.LENGTH_SHORT).show();

                            } else {

                                Toast.makeText(getApplicationContext(), "user doesn't exists", Toast.LENGTH_SHORT).show();
                                Toast.makeText(getApplicationContext(), "registering user", Toast.LENGTH_SHORT).show();

                                //settings = getApplication().getSharedPreferences(MainActivity.PREFS_NAME, 0);
                                editor = settings.edit();
                                editor.putString("U_id",userid);
                                editor.putString("U_name",facebookname);
                                editor.putString("U_email",femail);
                                editor.putString("U_police","");
                                editor.putString("U_address", "");
                                editor.putString("U_number", "");
                                editor.putString("U_landmark", "");
                                editor.putString("U_verified", "");
                                editor.putString("U_image","https://graph.facebook.com/"+userid+"/picture?type=large");
                                editor.putString("U_gender","");
                                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(i);
                            }
                        }
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    Log.d("error", responseString);
                }
            });
        } else {
            Toast.makeText(getApplicationContext(), "enter the details", Toast.LENGTH_SHORT).show();
        }*/



    }

    private void onSignInClicked() {

        mShouldResolve = true;
        mGoogleApiClient.connect();
        Toast.makeText(this, "singning in", Toast.LENGTH_SHORT).show();

        // Show a message to the user that we are signing in.

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("TAG", "onActivityResult:" + requestCode + ":" + resultCode + ":" + data);

        if (requestCode == RC_SIGN_IN) {
            // If the error resolution was not successful we should not resolve further.
            if (resultCode != RESULT_OK) {
                mShouldResolve = false;
            }

            mIsResolving = false;
            mGoogleApiClient.connect();

        } else {
            Toast.makeText(this, "fbactivityonresult called", Toast.LENGTH_SHORT).show();
            callbackManager.onActivityResult(requestCode, resultCode, data);

        }
    }


    @Override
    public void onConnected(Bundle bundle) {
        Toast.makeText(this, "on conected called", Toast.LENGTH_SHORT).show();
        Log.d("TAG", "onConnected:" + bundle);
        mShouldResolve = false;
        Person currentPerson = Plus.PeopleApi
                .getCurrentPerson(mGoogleApiClient);
        try {
            GpersonName = currentPerson.getDisplayName();
            GpersonPhotoUrl = currentPerson.getImage().getUrl();
            GpersonGooglePlusProfile = currentPerson.getUrl();
        }catch (Exception e)
        {
            System.out.println(e.toString());
        }

        String email = Plus.AccountApi.getAccountName(mGoogleApiClient);


        Log.i("all the details are",
                //   personName+" "+personPhotoUrl+" "+personGooglePlusProfile+
                " " + email);

        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.d("TAG", "onConnectionFailed:" + connectionResult);

        if (!mIsResolving && mShouldResolve) {
            if (connectionResult.hasResolution()) {
                try {
                    connectionResult.startResolutionForResult(this, RC_SIGN_IN);
                    mIsResolving = true;
                } catch (IntentSender.SendIntentException e) {
                    Log.e("TAG", "Could not resolve ConnectionResult.", e);
                    mIsResolving = false;
                    mGoogleApiClient.connect();
                }
            } else {

                Toast.makeText(this, "some error occured", Toast.LENGTH_SHORT).show();
            }
        } else {
        }
    }

    private void login() {

        sharedPreferencesLoginStatus = getSharedPreferences(Login.loginStatus, 0);
        boolean hasLoggedIn = sharedPreferencesLoginStatus.getBoolean("hasLoggedIn", false);
        if (hasLoggedIn) {
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
            Login.this.finish();
        }
    }

    @Override
    public void onSuccess(LoginResult loginResult) {
        setfbloginsharedpreference();
        Toast.makeText(this, "onsuccess called", Toast.LENGTH_SHORT).show();

        final GraphRequest request = GraphRequest.newMeRequest(
                loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject jsonObject, GraphResponse graphResponse) {
                        Log.d("oncomplete", "called");
                        Log.d("response", graphResponse + "");
                        Log.d("json response is", "" + jsonObject);

                        try {
                            fid = jsonObject.getString("id");
                            facebookname = jsonObject.getString("name");
                            femail = jsonObject.getString("email");

                            Log.d("json parsed", "fid" + fid + "name" + facebookname + "email" + femail);
                            Log.d("hi", "calling performloginviafbandgmail");
                           // performLoginViaFacebookAndGmail(fid, facebookname, femail);
                            Log.d("hi", "called performloginviafbandgmail");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email");
        request.setParameters(parameters);
        request.executeAsync();
    }




    private void setfbloginsharedpreference() {
       // settings = getApplication().getSharedPreferences(NavigationDrawer.PREFS_NAME, 0);
       // editor = settings.edit();

//Set "hasLoggedIn" to true
        editor.putBoolean("fblogin", true);

// Commit the edits!

        editor.commit();
    }

    private void userlist() {

        new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/me/friends",
                null,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
                        Log.d("friends", response + "");
                    }
                }
        ).executeAsync();
    }


    @Override
    public void onCancel() {

    }

    @Override
    public void onError(FacebookException e) {

        Log.d("fb error", e.toString());

    }


}
