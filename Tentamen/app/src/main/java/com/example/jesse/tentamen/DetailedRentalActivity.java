package com.example.jesse.tentamen;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by Silvano on 19-6-2017.
 */

public class DetailedRentalActivity extends AppCompatActivity {

    TextView titleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rental_detailed);

        titleView = (TextView) findViewById(R.id.yourrentaltitle);
        Bundle extras = getIntent().getExtras();
        String title = (String) extras.get("title");

        titleView.setText(title);
    }
}
