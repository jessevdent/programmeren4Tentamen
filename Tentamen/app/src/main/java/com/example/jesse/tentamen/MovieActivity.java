package com.example.jesse.tentamen;

/**
 * Created by Jesse on 18-6-2017.
 */


import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;

public class MovieActivity extends Activity implements AdapterView.OnItemClickListener{
    // Log tag
    private static final String TAG = MainActivity.class.getSimpleName();

    // Movies json url
    private static final String url = "https://tentamenprogrammeren4js.herokuapp.com/api/v1/films?limit=20&offset=";
    private ProgressDialog pDialog;
    private ArrayList<Item> itemList = new ArrayList();
    private ListView listView;
    private CustomListAdapter adapter;
    String customerid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);
        Bundle extras = getIntent().getExtras();
        customerid = (String) extras.get("id");
        System.out.println(customerid);


        final Button button = (Button) findViewById(R.id.btnLogin);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(
                        getString(R.string.preference_file_key), Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.remove(getString(R.string.saved_token));
                editor.commit();
                Intent login2 = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(login2);
                finish();
            }
        });

        final Button btnRental = (Button) findViewById(R.id.btnRental);
        btnRental.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent rental = new Intent(getApplicationContext(), RentalActivity.class);
                rental.putExtra("id", customerid);
                startActivity(rental);
                finish();
            }
        });


        System.out.println("1");
        listView = (ListView) findViewById(R.id.listview);
        adapter = new CustomListAdapter(this, itemList);
        listView.setOnItemClickListener(this);
        listView.setAdapter(adapter);

        System.out.println("2");
        pDialog = new ProgressDialog(this);
        // Showing progress dialog before making http request
        pDialog.setMessage("Loading...");
        pDialog.show();


        System.out.println("3");
        // Creating volley request obj
        JsonArrayRequest movieReq = new JsonArrayRequest(url + 0,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());
                        hidePDialog();

                        // Parsing json
                        for (int i = 0; i < response.length(); i++) {
                            try {

                                JSONObject obj = response.getJSONObject(i);
                                Item item = new Item();
                                item.setTitle(obj.getString("title"));
                                item.setDescription(obj.getString("description"));
                                item.setSpecialfeatures(obj.getString("special_features"));
                                item.setReleaseyear(obj.getString("release_year"));
                                item.setRating(obj.getString("rating"));
                                item.setLength(obj.getString("length"));
                                item.setRentalduratiom(obj.getString("rental_duration"));
                                item.setRentalrate(obj.getString("rental_rate"));
                                item.setReplacementcost(obj.getString("replacement_cost"));


                                // adding movie to movies array
                                itemList.add(item);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        // notifying list adapter about data changes
                        // so that it renders the list view with updated data
                        adapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                hidePDialog();

            }
        });
        System.out.println("4");
        // Adding request to request queue
        VolleyRequestQueue.getInstance().addToRequestQueue(movieReq);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        hidePDialog();
    }

    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.i(TAG, "Position " + position + " is geselecteerd");

        Item item = itemList.get(position);
        Intent intent = new Intent(getApplicationContext(), DetailedActivity.class);
        intent.putExtra("title", item.getTitle());
        intent.putExtra("beschrijving", item.getDescription());
        intent.putExtra("features", item.getSpecialfeatures());
        intent.putExtra("releaseyear", item.getReleaseyear());
        intent.putExtra("rating", item.getRating());
        intent.putExtra("length", item.getLength());
        intent.putExtra("duration", item.getRentalduratiom());
        intent.putExtra("rate", item.getRentalrate());
        intent.putExtra("cost", item.getReplacementcost());
        intent.putExtra("id", customerid);


        startActivity(intent);
        finish();
    }



}