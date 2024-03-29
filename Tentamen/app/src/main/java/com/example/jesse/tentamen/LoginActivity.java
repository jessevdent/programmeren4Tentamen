package com.example.jesse.tentamen;

/**
 * Created by Jesse on 14-6-2017.
 */

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

//import nl.avans.android.todos.R;
//import nl.avans.android.todos.service.Config;/
//import nl.avans.android.todos.service.VolleyRequestQueue;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextUsername;
    private EditText editTextPassword;
    private TextView txtLoginErrorMsg;
    private TextView mTvRegister;
    private Button btnLogin;

    private String mUsername;
    private String mPassword;

    public final String TAG = this.getClass().getSimpleName();





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTvRegister = (TextView) findViewById(R.id.link_to_register);
        editTextUsername = (EditText) findViewById(R.id.edittextUsername);
        editTextPassword = (EditText) findViewById(R.id.edittextPassword);
        txtLoginErrorMsg = (TextView) findViewById(R.id.txtLoginErrorMessage);
        editTextUsername.setText(getIntent().getStringExtra("username"));
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUsername = editTextUsername.getText().toString();
                mPassword = editTextPassword.getText().toString();
                txtLoginErrorMsg.setText("");

                // TODO Checken of username en password niet leeg zijn

                handleLogin(mUsername, mPassword);
            }
        });

        mTvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent register = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(register);
            }
        });
    }

    private void handleLogin(String username, String password) {
        //
        // Maak een JSON object met username en password. Dit object sturen we mee
        // als request body (zoals je ook met Postman hebt gedaan)
        //
        String body = "{\"username\":\"" + username + "\",\"password\":\"" + password + "\"}";
        Log.i(TAG, "handleLogin - body = " + body);

        try {
            JSONObject jsonBody = new JSONObject(body);
            JsonObjectRequest jsObjRequest = new JsonObjectRequest
                    (Request.Method.POST, Config.URL_LOGIN, jsonBody, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            // Succesvol response - dat betekent dat we een geldig token hebben.
                            // txtLoginErrorMsg.setText("Response: " + response.toString());
                            displayMessage("Succesvol ingelogd!");

                            // We hebben nu het token. We kiezen er hier voor om
                            // het token in SharedPreferences op te slaan. Op die manier
                            // is het token tussen app-stop en -herstart beschikbaar -
                            // totdat het token expired.
                            try {
                                String token = response.getString("token");
                                String customerid = response.getString("Customerid");

                                Context context = getApplicationContext();
                                SharedPreferences sharedPref = context.getSharedPreferences(
                                        getString(R.string.preference_file_key), Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPref.edit();
                                editor.putString(getString(R.string.saved_token), token);
                                editor.apply();
                                System.out.println(token);

                                // Start the main activity, and close the login activity
                                Intent success = new Intent(getApplicationContext(), MovieActivity.class);
                                success.putExtra("id", customerid);
                                startActivity(success);

                                // Close the current activity
                                finish();

                            } catch (JSONException e) {
                                // e.printStackTrace();
                                Log.e(TAG, e.getMessage());
                            }
                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            handleErrorResponse(error);
                            System.out.println(error);
                        }
                    });

            jsObjRequest.setRetryPolicy(new DefaultRetryPolicy(
                    1500, // SOCKET_TIMEOUT_MS,
                    2, // DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            // Access the RequestQueue through your singleton class.
            VolleyRequestQueue.getInstance(this).addToRequestQueue(jsObjRequest);
        } catch (JSONException e) {
            txtLoginErrorMsg.setText(e.getMessage());
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
            txtLoginErrorMsg.setText(getString(R.string.error_server_offline));
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