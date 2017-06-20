package com.example.jesse.tentamen;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
 * Created by Silvano on 19-6-2017.
 */

public class DetailedRentalActivity extends AppCompatActivity {

    TextView titleView;
    Button handin;
    String id, url, urldel, customerid;
    int inventoryid, rentalid;
    public final String TAG = this.getClass().getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rental_detailed);



        titleView = (TextView) findViewById(R.id.yourrentaltitle);
        Bundle extras = getIntent().getExtras();
        String title = (String) extras.get("title");
        id = (String) extras.get("id");
        inventoryid = (int) extras.get("inventoryid");
        rentalid = (int) extras.get("rentalid");




        urldel = new String("https://tentamenprogrammeren4js.herokuapp.com/api/v1/rentalsdelete/");
        url = new String("https://tentamenprogrammeren4js.herokuapp.com/api/v1/rentals/");
        titleView.setText(title);

        handin = (Button) findViewById(R.id.handinbutton);
        handin.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v){
                handleHandin(id, inventoryid);
                handlePut(rentalid);
            }
        });
    }

    private void handleHandin(final String id, int inventoryid) {
        //
        // Maak een JSON object met username en password. Dit object sturen we mee
        // als request body (zoals je ook met Postman hebt gedaan)
        //
        System.out.println("2");

        try {
            JSONObject jsonBody = new JSONObject();
            JsonObjectRequest jsObjRequest = new JsonObjectRequest
                    (Request.Method.DELETE, url + id + "/" + inventoryid, jsonBody, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                           try {
                                System.out.println("3");

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

    private void handlePut(int rentalid) {
        //
        // Maak een JSON object met username en password. Dit object sturen we mee
        // als request body (zoals je ook met Postman hebt gedaan)
        //
        System.out.println("2");

        try {
            JSONObject jsonBody = new JSONObject();
            JsonObjectRequest jsObjRequest = new JsonObjectRequest
                    (Request.Method.PUT, urldel + rentalid, jsonBody, new Response.Listener<JSONObject>() {

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
