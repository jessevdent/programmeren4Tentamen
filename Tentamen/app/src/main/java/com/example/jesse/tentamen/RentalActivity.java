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

import static java.lang.System.out;

/**
 * Created by Silvano on 19-6-2017.
 */

public class RentalActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    // Log tag
    private static final String TAG = MainActivity.class.getSimpleName();
    private String url = "https://tentamenprogrammeren4js.herokuapp.com/api/v1/rentals/";
    private ProgressDialog pDialog;
    private ArrayList<Item> rentalList = new ArrayList();
    private ListView listView;
    private RentalListAdapter adapter;
    String customerid;
    //private Context context;


    //private LoginActivity.ToDoListener listener;


    /*
    public RentalActivity(Context context)
    {
        this.context = context;

    }
    */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rental);

        Bundle extras = getIntent().getExtras();
        customerid = (String) extras.get("id");
        out.println(customerid);
        handlePost();


        }

        public void handlePost()
    {
        out.println("1");
        listView = (ListView) findViewById(R.id.rentallistview);
        adapter = new RentalListAdapter(this, rentalList);
        listView.setOnItemClickListener(this);
        listView.setAdapter(adapter);

        out.println("2");
        pDialog = new ProgressDialog(this);
        // Showing progress dialog before making http request
        pDialog.setMessage("Loading...");
        pDialog.show();

        out.println("3");
        //SharedPreferences sharedPref = context.getSharedPreferences(
        // context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        //final String token = sharedPref.getString(context.getString(R.string.saved_token), "dummy default token");
        //if (token != null && !token.equals("dummy default token")) {
        //System.out.println(token);
        // Creating volley request obj
        JsonArrayRequest rentalReq = new JsonArrayRequest(url + customerid,
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
                                item.setInventoryid(obj.getInt("inventory_id"));
                                item.setRentalid(obj.getInt("rental_id"));


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
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                headers.put("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE0OTgxMzY0OTgsImlhdCI6MTQ5NzUzMTY5OCwic3ViIjoiVGVzdDEifQ.n93eyWMgdY5bVnTRdEeBzceolmLwu_pRsJ-w_9i5C7g");
                return headers;

            }
        };
        out.println("4");
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
        Intent intent = new Intent(getApplicationContext(), DetailedRentalActivity.class);
        intent.putExtra("title", item.getTitle());
        intent.putExtra("id", customerid);
        intent.putExtra("inventoryid", item.getInventoryid());
        intent.putExtra("rentalid", item.getRentalid());
        System.out.println(item.getInventoryid());
        startActivity(intent);
    }



}
