package com.example.jesse.tentamen;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // Logging tag
    //public final String TAG = this.getClass().getSimpleName();

    // The name for communicating Intents extras
    //public final static String TODO_DATA = "TODOS";

    // A request code for returning data from Intent - is supposed to be unique.
    //public static final int MY_REQUEST_CODE = 1234;
    String customerid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

            Intent login = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(login);

            finish();
            }

    /**
     * Aangeroepen door terugkerende Intents. Maakt het mogelijk om data
     * terug te ontvangen van een Intent.
     *
     * @param requestCode
     * @param resultCode
     * @param pData
     */


    /**
     * Check of een token in localstorage is opgeslagen. We checken niet de geldigheid -
     * alleen of het token bestaat.
     *
     * @return
     */
    private boolean tokenAvailable() {
        boolean result = false;

        Context context = getApplicationContext();
        SharedPreferences sharedPref = context.getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        String token = sharedPref.getString(getString(R.string.saved_token), "dummy default token");
        if (token != null && !token.equals("dummy default token")) {
            result = true;
        }
        return result;


        }


    }
