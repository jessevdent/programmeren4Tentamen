package com.example.jesse.tentamen;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Jesse on 15-6-2017.
 */

public class RegisterActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private EditText firstName;
    private EditText lastName;
    private EditText email;
    private EditText address;
    private EditText postalCode;
    private EditText city;
    private EditText country;
    private EditText houseNumber;
    private Button bRegister;

    private String mUsername;
    private String mPassword;
    private String mFirstName;
    private String mLastName;
    private String mEmail;
    private String mAddress;
    private String mPostalCode;
    private String mCity;
    private String mCountry;
    private String mHouseNumber;
    private String mRegister;

    public final String TAG = this.getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username = (EditText) findViewById(R.id.edittextUsername);
        password = (EditText) findViewById(R.id.edittextPassword);
        firstName = (EditText) findViewById(R.id.edittextFirst);
        lastName = (EditText) findViewById(R.id.edittextLast);
        email = (EditText) findViewById(R.id.edittextEmail);
        address = (EditText) findViewById(R.id.edittextAddress);
        postalCode = (EditText) findViewById(R.id.edittextPostal);
        city = (EditText) findViewById(R.id.edittextCity);
        country = (EditText) findViewById(R.id.edittextCountry);
        houseNumber = (EditText) findViewById(R.id.edittextNumber);
        bRegister = (Button) findViewById(R.id.btnRegister);
        bRegister.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mUsername = username.getText().toString();
                mPassword = password.getText().toString();
                mFirstName = firstName.getText().toString();
                mLastName = lastName.getText().toString();
                mEmail = email.getText().toString();
                mAddress = address.getText().toString();
                mPostalCode = password.getText().toString();
                mCity = city.getText().toString();
                mCountry = country.getText().toString();
                mHouseNumber = houseNumber.getText().toString();

                handleRegister(mUsername, mPassword, mFirstName, mLastName, mEmail, mAddress, mPostalCode, mCity, mCountry, mHouseNumber);
            }
        });

    }

    private void handleRegister(String username, String password, String firstname, String lastname, String email, String address, String postalcode, String city, String country, String housenumber) {
        //
        // Maak een JSON object met username en password. Dit object sturen we mee
        // als request body (zoals je ook met Postman hebt gedaan)
        //
        String body = "{\"username\":\"" + username + "\",\"password\":\"" + password + "\", \"first_name\":\"" + firstname + "\", \"last_name\":\"" + lastname + "\", \"email\":\"" + email + "\", \"address\":\"" + address + "\", \"postal_code\":\"" + postalcode + "\", \"city\":\"" + city + "\", \"country\":\"" + country + "\", \"house_number\":\"" + housenumber + "\"}";
        Log.i(TAG, "handleRegister - body = " + body);

        try {
            JSONObject jsonBody = new JSONObject(body);
            JsonObjectRequest jsObjRequest = new JsonObjectRequest
                    (Request.Method.POST, Config.URL_REGISTER, jsonBody, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            // Succesvol response - dat betekent dat we een geldig token hebben.
                            // txtLoginErrorMsg.setText("Response: " + response.toString());
                            displayMessage("Succesvol geregistreerd!");

                            // We hebben nu het token. We kiezen er hier voor om
                            // het token in SharedPreferences op te slaan. Op die manier
                            // is het token tussen app-stop en -herstart beschikbaar -
                            // totdat het token expired.
                            try {
                                Intent login = new Intent(getApplicationContext(), LoginActivity.class);

                                login.putExtra("username", mUsername);
                                startActivity(login);
                                finish();

                            } catch (Exception e) {
                                // e.printStackTrace();
                                Log.e(TAG, e.getMessage());
                            }
                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            handleErrorResponse(error);
                        }
                    });

            jsObjRequest.setRetryPolicy(new DefaultRetryPolicy(
                    1500, // SOCKET_TIMEOUT_MS,
                    2, // DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            // Access the RequestQueue through your singleton class.
            VolleyRequestQueue.getInstance(this).addToRequestQueue(jsObjRequest);
        } catch (JSONException e) {
            //txtLoginErrorMsg.setText(e.getMessage());
            // e.printStackTrace();
        }
        return;
    }

    public void handleErrorResponse(VolleyError error) {
        Log.e(TAG, "handleErrorResponse");

        if(error instanceof com.android.volley.AuthFailureError) {

            String json = null;
            NetworkResponse response = error.networkResponse;
            if (response != null && response.data != null) {
                json = new String(response.data);
                json = trimMessage(json, "error");
                if (json != null) {
                    json = "Error " + response.statusCode + ": " + json;
                    displayMessage(json);
                }
            } else {
                Log.e(TAG, "handleErrorResponse: kon geen networkResponse vinden.");
            }
        } else if(error instanceof com.android.volley.NoConnectionError) {
            Log.e(TAG, "handleErrorResponse: server was niet bereikbaar");
            //txtLoginErrorMsg.setText(getString(R.string.error_server_offline));
        } else {
            Log.e(TAG, "handleErrorResponse: error = " + error);
        }
    }

    public String trimMessage(String json, String key){
        Log.i(TAG, "trimMessage: json = " + json);
        String trimmedString = null;

        try{
            JSONObject obj = new JSONObject(json);
            trimmedString = obj.getString(key);
        } catch(JSONException e){
            e.printStackTrace();
            return null;
        }
        return trimmedString;
    }

    public void displayMessage(String toastString){
        Toast.makeText(getApplicationContext(), toastString, Toast.LENGTH_LONG).show();
    }
}

