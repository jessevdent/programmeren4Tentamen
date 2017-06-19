package com.example.jesse.tentamen;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Silvano on 19-6-2017.
 */

public class RentalActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    // Log tag
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String url = "https://tentamenprogrammeren4js.herokuapp.com/api/v1/rentals/45";
    private ProgressDialog pDialog;
    private ArrayList<Item> rentalList = new ArrayList();
    private ListView listView;
    private RentalListAdapter adapter;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rental);

        System.out.println("1");
        listView = (ListView) findViewById(R.id.rentallistview);
        adapter = new RentalListAdapter(this, rentalList);
        listView.setOnItemClickListener(this);
        listView.setAdapter(adapter);

        System.out.println("2");
        pDialog = new ProgressDialog(this);
        // Showing progress dialog before making http request
        pDialog.setMessage("Loading...");
        pDialog.show();


        System.out.println("3");




        

        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        final String token = sharedPref.getString(context.getString(R.string.saved_token), "dummy default token");











        // Creating volley request obj
        JsonArrayRequest rentalReq = new JsonArrayRequest(url,
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

                                rentalList.add(item);

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
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                headers.put("Authorization", "Bearer " + token);
                return headers;
            }
        };
        System.out.println("4");
        // Adding request to request queue
        VolleyRequestQueue.getInstance().addToRequestQueue(rentalReq);
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
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        Item item = rentalList.get(position);
        Intent intent = new Intent(getApplicationContext(), DetailedActivity.class);
        intent.putExtra("title", item.getTitle());

        startActivity(intent);
    }
}
