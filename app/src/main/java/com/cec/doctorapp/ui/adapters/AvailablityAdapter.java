package com.cec.doctorapp.ui.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

import com.cec.doctorapp.databinding.AvailabilityAdapterBinding;
import com.cec.doctorapp.model.response.Timing;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class AvailablityAdapter extends RecyclerView.Adapter<AvailablityAdapter.ViewHolder> {
    ArrayList<Timing> dataList;
    private Context context;

    public AvailablityAdapter(Context context, ArrayList<Timing> dataList) {
        this.context = context;
        this.dataList =new ArrayList<>(dataList);
    }

    @NonNull
    @Override
    public AvailablityAdapter.ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        AvailabilityAdapterBinding binding = AvailabilityAdapterBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);


    }

    @Override
    public void onBindViewHolder(AvailablityAdapter.ViewHolder holder, int position) {
        holder.bind();
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }


    public class ViewHolder extends BindingViewHolder {


        public ViewHolder(@NotNull ViewBinding binding) {
            super(binding);
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void bind() {
            Timing list = dataList.get(getAdapterPosition());
            if (binding instanceof AvailabilityAdapterBinding) {
                AvailabilityAdapterBinding mBinding = ((AvailabilityAdapterBinding) binding);
                mBinding.days.setText(list.day);
                Log.d("M_TAG", "ADAPOTER ");
                mBinding.from.setText(list.fromTime+" "+"   To  "+" "+ list.toTime);
//                mBinding.to.setText(list.toTime);
                Log.d("TAG", "set");
            }
        }
    }
}

