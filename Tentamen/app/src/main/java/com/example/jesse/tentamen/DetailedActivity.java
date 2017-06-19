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

        titleView.setText("" + title);
        realeaseView.setText("Releaseyear: \n" + releaseyear);
        lengthView.setText("Length: \n" + length + " Minutes");
        ratingView.setText("Rating: \n" + rating);

        System.out.println(features);
        featuresView.setText("Features: \n" + features);
        descriptionView.setText("Description: \n" + beschrijving);
        rentaldurationView.setText("Rental Duration: \n" + duration + " Days");
        rentalrateView.setText("Rental Rate: \n" + "€" + rate + " Per day");
        replacement.setText("Replacement Cost: \n" + "€" + cost);

        rent = (Button) findViewById(R.id.rentbutton);
        rent.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v){

            }
        });

    }



}
