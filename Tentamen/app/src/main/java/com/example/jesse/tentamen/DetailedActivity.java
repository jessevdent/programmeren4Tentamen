package com.example.jesse.tentamen;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Jesse on 4/3/2017.
 */

public class DetailedActivity extends AppCompatActivity
{
    TextView titleView, realeaseView, lengthView, ratingView, featuresView;
    //Button ticketKnop;
    public final String TAG = this.getClass().getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);



        titleView = (TextView) findViewById(R.id.title);
        realeaseView = (TextView) findViewById(R.id.release_year);
        lengthView = (TextView) findViewById(R.id.length);
        ratingView = (TextView) findViewById(R.id.rating);
        featuresView = (TextView) findViewById(R.id.special_features);

        Bundle extras = getIntent().getExtras();

        Item item = (Item) extras.getSerializable("ITEMS");
        Log.i(TAG, item.toString());

        titleView.setText("Titel: " + item.getTitle());
        realeaseView.setText("Beschrijving: " + item.getReleaseyear());
        lengthView.setText("Taal: " + item.getLength());
        ratingView.setText("Releasedate: " + item.getRating());
        featuresView.setText("Beoordeling: " + item.getSpecialfeatures());

        //ticketKnop = (Button) findViewById(R.id.TicketKnop);
        //ticketKnop = (Button) findViewById(R.id.TicketKnop);*/
    }



}
