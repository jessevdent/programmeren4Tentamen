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

public class MovieActivity extends Activity {
    // Log tag
    private static final String TAG = MainActivity.class.getSimpleName();

    // Movies json url
    private static final String url = "https://tentamenprogrammeren4js.herokuapp.com/api/v1/films?limit=20&offset=10";
    private ProgressDialog pDialog;
    private List<Item> itemList = new ArrayList<Item>();
    private ListView listView;
    private CustomListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);

        final Button button = (Button) findViewById(R.id.btnLogin);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(
                        getString(R.string.preference_file_key), Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.remove(getString(R.string.saved_token));
                editor.commit();
                setContentView(R.layout.activity_main);
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
        JsonArrayRequest movieReq = new JsonArrayRequest(url,
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
                                item.setReleaseyear(obj.getString("release_year"));
                                item.setLength(obj.getString("length"));
                                item.setRating(obj.getString("rating"));
                                item.setSpecialfeatures(obj.getString("special_features"));



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

        itemList itemlist = itemList.get(position);
        Intent intent = new Intent(getApplicationContext(), activity.class);
        intent.putExtra(TODO_DATA, toDo);
        startActivity(intent);
    }



}