package com.cec.doctorapp.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cec.doctorapp.R;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.ViewHolder> {
    @NonNull
    @Override
    public BookingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_booking_adapter, parent, false);

        return new BookingAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }



    @Override
    public int getItemCount() {
        return 10;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
//        public ImageView imageView,imgbtm;
       public TextView text1,text2,text3;


        public ViewHolder(View itemView) {
            super(itemView);
            this.text1=(TextView) itemView.findViewById(R.id.text1);


        }
    }
}

