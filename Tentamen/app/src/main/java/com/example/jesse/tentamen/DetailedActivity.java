package com.example.jesse.tentamen;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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
    Item thisItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);


        Intent intent = getIntent();
        thisItem = (Item) intent.getSerializableExtra("ITEM");

        titleView = (TextView) findViewById(R.id.Title);
        titleView.setText("Titel: " + thisItem.getTitle());

        realeaseView = (TextView) findViewById(R.id.release_year);
        realeaseView.setText("Beschrijving: " + thisItem.getReleaseyear());

        lengthView = (TextView) findViewById(R.id.length);
        lengthView.setText("Taal: " + thisItem.getLength());

        ratingView = (TextView) findViewById(R.id.rating);
        ratingView.setText("Releasedate: " + thisItem.getRating());

        featuresView = (TextView) findViewById(R.id.special_features);
        featuresView.setText("Beoordeling: " + thisItem.getSpecialfeatures());

        //ticketKnop = (Button) findViewById(R.id.TicketKnop);
        //ticketKnop = (Button) findViewById(R.id.TicketKnop);
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
