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
    TextView titleView, realeaseView, lengthView, ratingView, featuresView, descriptionView, rentaldurationView, rentalrateView, replacement;
    Button rent, handIn;
    public final String TAG = this.getClass().getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);



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
        //Log.i(TAG, item.toString());

        titleView.setText("Titel: " + title);
        realeaseView.setText("Release: " + releaseyear);
        lengthView.setText("Length: " + length);
        ratingView.setText("Rating: " + rating);

        System.out.println(features);
        featuresView.setText("Features: " + features);
        descriptionView.setText("Description: " + beschrijving);
        rentaldurationView.setText("Rental Duration: " + duration);
        rentalrateView.setText("Rental Rate: " + rate);
        replacement.setText("Replacement Cost: " + cost);


        rent = (Button) findViewById(R.id.rentbutton);
        handIn = (Button) findViewById(R.id.handinmovie);
    }



}
