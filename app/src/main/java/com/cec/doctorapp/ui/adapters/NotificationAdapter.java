package com.cec.doctorapp.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.cec.doctorapp.R;
import com.cec.doctorapp.databinding.NotificationAdapterBinding;
import com.cec.doctorapp.model.response.NotificationItemModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {
    private  ArrayList<NotificationItemModel> datalist = new ArrayList<>();


    private Context context;
    public NotificationAdapter(){}

    public NotificationAdapter(Context context, ArrayList<NotificationItemModel> dataList) {
        this.context = context;
        this.datalist = dataList;
    }

    //    private final NotificationAdapterInterface notificationsAdapterInterface;
//
//    public interface NotificationAdapterInterface{
//        void updateNotification(NotificationItemModel NotificationItemModel);
//    }
//public void update(ArrayList<NotificationItemModel> list ){
//    this.list.clear();
//    this.list.addAll(list);
//    notifyDataSetChanged();
//}

    @NotNull
    @Override
    public NotificationAdapter.ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
    NotificationAdapterBinding binding= NotificationAdapterBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new NotificationAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(NotificationAdapter.ViewHolder holder, int position) {
        holder.bind();

    }



    @Override
    public int getItemCount() {
        return datalist.size();
    }

    public class ViewHolder extends BindingViewHolder {


        public ViewHolder(@NotNull ViewBinding binding) {
            super(binding);
        }

        @Override
        public void bind() {
            NotificationItemModel model = datalist.get(getAdapterPosition());
            if (binding instanceof NotificationAdapterBinding) {
              NotificationAdapterBinding mBinding = ((NotificationAdapterBinding) binding);
                mBinding.textTitl.setText(model.title);
                mBinding.textTitl2.setText(model.msg);
                Glide.with(mBinding.img1)
                        .load(model.image)
                        .placeholder(R.drawable.ceclogo)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(mBinding.img1);


            }

        }
    }
}

