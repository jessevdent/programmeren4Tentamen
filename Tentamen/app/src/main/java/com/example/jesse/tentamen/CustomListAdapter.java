package com.example.jesse.tentamen;

/**
 * Created by Jesse on 18-6-2017.
 */

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


public class CustomListAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<Item> Items;


    public CustomListAdapter(Activity activity, List<Item> Items) {
        this.activity = activity;
        this.Items = Items;
    }

    @Override
    public int getCount() {
        return Items.size();
    }

    @Override
    public Object getItem(int location) {
        return Items.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.listview_row, null);

        TextView title = (TextView) convertView.findViewById(R.id.title);
        TextView rating = (TextView) convertView.findViewById(R.id.rating);
        TextView releaseYear = (TextView) convertView.findViewById(R.id.release_year);
        TextView length = (TextView) convertView.findViewById(R.id.length);
        TextView specialfeatures = (TextView) convertView.findViewById(R.id.special_features);
        // getting movie data for the row
        Item m = Items.get(position);

        title.setText(m.getTitle());
        rating.setText("Rating: " + String.valueOf(m.getRating()));
        releaseYear.setText(String.valueOf(m.getReleaseyear()));
        length.setText(String.valueOf(m.getLength()));
        specialfeatures.setText(String.valueOf(m.getSpecialfeatures()));


        return convertView;
    }

}