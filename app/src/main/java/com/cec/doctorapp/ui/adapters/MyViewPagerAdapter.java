package com.cec.doctorapp.ui.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

import com.cec.doctorapp.R;
import com.cec.doctorapp.databinding.ItemViewpagerBinding;
import com.cec.doctorapp.model.response.GetAllPriceModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class
MyViewPagerAdapter extends RecyclerView.Adapter<MyViewPagerAdapter.ViewHolder> {
    ArrayList<GetAllPriceModel> dataList;

    private final int[] colorArray = new int[]{
            R.drawable.gradient_pricing_blue,
            R.drawable.gradient_orange_viewpager,
            R.drawable.gradient_purple_viewpager
    };

    public MyViewPagerAdapter(ArrayList<GetAllPriceModel> dataList) {
        this.dataList = dataList;
    }


    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        ItemViewpagerBinding binding = ItemViewpagerBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
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

        @Override
        public void bind() {
            GetAllPriceModel list = dataList.get(getAdapterPosition());
            if (binding instanceof ItemViewpagerBinding) {
                ItemViewpagerBinding mBinding = ((ItemViewpagerBinding) binding);
                mBinding.bkngtxt.setText(list.offer);
                mBinding.bkngtxtprc.setText(String.valueOf("$"+list.price));

                if (getAdapterPosition()==0){
                    mBinding.bkingLnear.setBackground(ContextCompat.getDrawable(mBinding.getRoot().getContext(),colorArray[0]));
                }else if (getAdapterPosition()%2==0){
                    mBinding.bkingLnear.setBackground(ContextCompat.getDrawable(mBinding.getRoot().getContext(),colorArray[2]));
                }else {
                    mBinding.bkingLnear.setBackground(ContextCompat.getDrawable(mBinding.getRoot().getContext(),colorArray[1]));
                }
            }
        }

    }
}
