package com.cec.doctorapp.helper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.cec.doctorapp.R;
import com.cec.doctorapp.model.response.BookingDataModel;

import java.util.ArrayList;
import java.util.List;

@SuppressLint({"SetTextI18n", "ViewHolder", "InflateParams"})
@SuppressWarnings("rawtypes")
public class CustomArrayAdapter extends ArrayAdapter {
    @NonNull
    private final Context context;
    private final List<?> items;

    public CustomArrayAdapter(@NonNull Context context, List items) {
        //noinspection unchecked
        super(context, android.R.layout.simple_list_item_1, items);
        this.context = context;
        this.items = new ArrayList<>(items);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        TextView v = (TextView) super.getView(position, convertView, parent);

        v.setTextColor(Color.BLACK);
        if (items.get(position) instanceof BookingDataModel) {
            BookingDataModel item = ((BookingDataModel) items.get(position));
            if (item.isDummy())
                v.setText(item.getName());
            else
                v.setText(item.fromTime + "-" + item.toTime);
        } else if (items.get(position) instanceof String) {
            v.setText(((String) items.get(position)));
        }
//        else if (items.get(position) instanceof BookingDataModel) {
//            v.setText(((BookingDataModel) items.get(position)).getName());
//        }
        else {
            v.setText("NA111");
        }

        return v;

    }


    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {


        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.only_textview, null, false);

        TextView lbl = (TextView) view.findViewById(R.id.textView);
        view.findViewById(R.id.viewLine).setVisibility(View.GONE);
        lbl.setTextColor(Color.TRANSPARENT);

        if (items.get(position) instanceof BookingDataModel) {
            BookingDataModel item = ((BookingDataModel) items.get(position));
            if (item.isDummy())
                lbl.setText(item.getName());
            else
                lbl.setText(item.fromTime + "-" + item.toTime);
//            lbl.setText(((BookingDataModel) items.get(position)).getName());
        } else if (items.get(position) instanceof String) {
            lbl.setText(((String) items.get(position)));
        } else if (items.get(position) instanceof BookingDataModel) {
//            lbl.setText(((BookingDataModel) items.get(position)).getName());
        } else {
            lbl.setText("NA");
        }


        return view;
    }

}

