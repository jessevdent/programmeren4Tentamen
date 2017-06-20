package com.example.jesse.tentamen;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jesse on 4/3/2017.
 */

public class DetailedActivity extends AppCompatActivity
{
    TextView titleView, realeaseView, lengthView, ratingView, featuresView, descriptionView, rentaldurationView, rentalrateView, replacement;
    Button rent;
    public final String TAG = this.getClass().getSimpleName();
    String id, url;
    int filmid, inventoryid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        titleView = (TextView) findViewById(R.id.title);
        realeaseView = (TextView) findViewById(R.id.release_year);
        lengthView = (TextView) findViewById(R.id.length);
        ratingView = (TextView) findViewById(R.id.rating);
        featuresView = (TextView) findViewById(R.id.specialfeatures);
        descriptionView = (TextView) findViewById(R.id.description);
        rentaldurationView = (TextView) findViewById(R.id.rentalduration);
        rentalrateView = (TextView) findViewById(R.id.rentalrate);
        replacement = (TextView) findViewById(R.id.replacementcost);

        Bundle extras = getIntent().getExtras();
        String title = (String) extras.get("title");
        String beschrijving = (String) extras.get("beschrijving");
        String features = (String) extras.get("features");
        String releaseyear = (String) extras.get("releaseyear");
        String rating = (String) extras.get("rating");
        String length = (String) extras.get("length");
        String duration = (String) extras.get("duration");
        String rate = (String) extras.get("rate");
        String cost = (String) extras.get("cost");
        filmid = (int) extras.get("filmid");
        inventoryid = (int) extras.get("inventoryid");
        id = (String) extras.get("id");
        //Log.i(TAG, item.toString());

        url = new String("https://tentamenprogrammeren4js.herokuapp.com/api/v1/rentals/");

        titleView.setText("" + title);
        realeaseView.setText("Releaseyear: \n" + releaseyear);
        lengthView.setText("Length: \n" + length + " Minutes");
        ratingView.setText("Rating: \n" + rating);

        System.out.println(features);
        featuresView.setText("Features: \n" + features);
        descriptionView.setText("Description: \n" + beschrijving);
        rentaldurationView.setText("Rental Duration: \n" + duration + " Days");
        rentalrateView.setText("Rental Rate: \n" + "€" + rate + " Per day");
        replacement.setText("Replacement Cost: \n" + "€" + cost);

        System.out.println("1");

        rent = (Button) findViewById(R.id.rentbutton);
        rent.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v){
                handleRent(id, inventoryid);
                handlePut(id, inventoryid);
            }
        });

    }


    private void handleRent(final String id, int inventoryid) {
        //
        // Maak een JSON object met username en password. Dit object sturen we mee
        // als request body (zoals je ook met Postman hebt gedaan)
        //
        System.out.println("2");

        try {
            JSONObject jsonBody = new JSONObject();
            JsonObjectRequest jsObjRequest = new JsonObjectRequest
                    (Request.Method.POST, url + id + "/" + inventoryid, jsonBody, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            // Succesvol response - dat betekent dat we een geldig token hebben.
                            // txtLoginErrorMsg.setText("Response: " + response.toString());
                            //displayMessage("Succesvol ingelogd!");

                            // We hebben nu het token. We kiezen er hier voor om
                            // het token in SharedPreferences op te slaan. Op die manier
                            // is het token tussen app-stop en -herstart beschikbaar -
                            // totdat het token expired.
                            try {
                                System.out.println("3");
                                // Start the main activity, and close the login activity
                                Intent success = new Intent(getApplicationContext(), RentalActivity.class);
                                success.putExtra("id", id);
                                startActivity(success);
                                // Close the current activity
                                finish();
                                System.out.println("4");
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

                    }){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("Content-Type", "application/json");
                    headers.put("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE0OTgxMzY0OTgsImlhdCI6MTQ5NzUzMTY5OCwic3ViIjoiVGVzdDEifQ.n93eyWMgdY5bVnTRdEeBzceolmLwu_pRsJ-w_9i5C7g");
                    return headers;

                }
            };

            jsObjRequest.setRetryPolicy(new DefaultRetryPolicy(
                    1500, // SOCKET_TIMEOUT_MS,
                    2, // DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            // Access the RequestQueue through your singleton class.
            VolleyRequestQueue.getInstance(this).addToRequestQueue(jsObjRequest);
        } catch (Exception e) {
            // e.printStackTrace();
        }
        System.out.println("5");
        return;
    }

    private void handlePut(final String id, int inventoryid) {
        //
        // Maak een JSON object met username en password. Dit object sturen we mee
        // als request body (zoals je ook met Postman hebt gedaan)
        //
        System.out.println("2");

        try {
            JSONObject jsonBody = new JSONObject();
            JsonObjectRequest jsObjRequest = new JsonObjectRequest
                    (Request.Method.PUT, url + id + "/" + inventoryid, jsonBody, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            // Succesvol response - dat betekent dat we een geldig token hebben.
                            // txtLoginErrorMsg.setText("Response: " + response.toString());
                            //displayMessage("Succesvol ingelogd!");

                            // We hebben nu het token. We kiezen er hier voor om
                            // het token in SharedPreferences op te slaan. Op die manier
                            // is het token tussen app-stop en -herstart beschikbaar -
                            // totdat het token expired.
                            try {
                                System.out.println("3");
                                // Start the main activity, and close the login activity
                                Intent success = new Intent(getApplicationContext(), RentalActivity.class);
                                success.putExtra("id", id);
                                startActivity(success);
                                // Close the current activity
                                finish();
                                System.out.println("4");
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

                    }){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("Content-Type", "application/json");
                    headers.put("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE0OTgxMzY0OTgsImlhdCI6MTQ5NzUzMTY5OCwic3ViIjoiVGVzdDEifQ.n93eyWMgdY5bVnTRdEeBzceolmLwu_pRsJ-w_9i5C7g");
                    return headers;

                }
            };

            jsObjRequest.setRetryPolicy(new DefaultRetryPolicy(
                    1500, // SOCKET_TIMEOUT_MS,
                    2, // DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            // Access the RequestQueue through your singleton class.
            VolleyRequestQueue.getInstance(this).addToRequestQueue(jsObjRequest);
        } catch (Exception e) {
            // e.printStackTrace();
        }
        System.out.println("5");
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
