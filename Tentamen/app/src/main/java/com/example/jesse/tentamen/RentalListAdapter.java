package com.example.jesse.tentamen;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;
/**
 * Created by Jesse on 18-6-2017.
 */



public class RentalListAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<Item> Items;


    public RentalListAdapter(Activity activity, List<Item> Items) {
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
            convertView = inflater.inflate(R.layout.listview_row_rental, null);

        TextView title = (TextView) convertView.findViewById(R.id.rentaltitle);
        Item m = Items.get(position);

        title.setText(m.getTitle());

        return convertView;
    }

}
