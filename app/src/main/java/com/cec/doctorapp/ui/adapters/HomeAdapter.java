package com.cec.doctorapp.ui.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.cec.doctorapp.R;
import com.cec.doctorapp.databinding.ActivityHomeAdapterBinding;

import com.cec.doctorapp.helper.Constants;
import com.cec.doctorapp.helper.Vu;
import com.cec.doctorapp.model.response.GetFeedModel;


import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {
    ArrayList<GetFeedModel> dataList;
//GetToken token=new GetToken();

    private Context context;

    public HomeAdapter(Context context, ArrayList<GetFeedModel> dataList) {
        this.context = context;
        this.dataList = dataList;

    }

    @NotNull
    @Override
    public HomeAdapter.ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        ActivityHomeAdapterBinding binding = ActivityHomeAdapterBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new HomeAdapter.ViewHolder(binding);
    }


    @Override
    public void onBindViewHolder(HomeAdapter.ViewHolder holder, int position) {
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
            GetFeedModel list = dataList.get(getAdapterPosition());
            if (binding instanceof ActivityHomeAdapterBinding) {
                ActivityHomeAdapterBinding mBinding = ((ActivityHomeAdapterBinding) binding);
//                mBinding.text1.setText(token.name);
                mBinding.text3.setText(list.message);
                mBinding.text4.setText(list.permalink_url);
                Log.d("TAG", "set");
                String time = getLocalDateStringFromUTCString(list.createdTime);
                mBinding.text2.setText(time);
                Log.d("TIME_PARSE", "time" + time);
                Glide.with(mBinding.imgbtm)
                        .load(list.full_picture)
                        .placeholder(R.drawable.p)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                               mBinding.imageLoader.setVisibility(View.GONE);
                                return false;
                            }
                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                mBinding.imageLoader.setVisibility(View.GONE);
                                return false;
                            }
                        })
                        .into(mBinding.imgbtm);
            }
//                if (!list.getFull_picture().isEmpty()) {
//                    try {
//                        Vu.show(((ActivityHomeAdapterBinding) binding).imageLoader);
////                        Glide.with(((ActivityHomeAdapterBinding) binding).imgbtm)
//                                .load(list.full_picture)
//                                .listener(new RequestListener<Drawable>() {
//                                    @Override
//                                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
//                                        Vu.gone(((ActivityHomeAdapterBinding) binding).imageLoader);
//                                        return false;
//                                    }
//
//                                    @Override
//                                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
//                                        Vu.gone(((ActivityHomeAdapterBinding) binding).imageLoader);
//                                        ((ActivityHomeAdapterBinding) binding).imgbtm.post(() -> {
//                                            ((ActivityHomeAdapterBinding) binding).imgbtm.setBackground(resource);
//                                            ((ActivityHomeAdapterBinding) binding).imgbtm.setImageDrawable(resource);
//                                            ((ActivityHomeAdapterBinding) binding).imgbtm.setImageDrawable(null);
//                                        });
//                                        return false;
//                                    }
//                                })
//                                .placeholder(R.drawable.p)
//                                .submit();
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                        Log.wtf(Constants.FATAL, e.getMessage());
//                    }
//                } else {
//
//
//                    Glide.with(mBinding.imgbtm)
//                            .load(list.full_picture)
//                            .optionalFitCenter()
//                            .diskCacheStrategy(DiskCacheStrategy.ALL)
//                            .into(mBinding.imgbtm);
//                }
//
//            }
        }


        public String getLocalDateStringFromUTCString(String utcLongDateTime) {
            Log.d("TIME_PARSE", "utcLongDateTime" + utcLongDateTime);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ssZ", Locale.ROOT);
            SimpleDateFormat viewFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm aa", Locale.ROOT);
            try {
                //noinspection ConstantConditions
                return viewFormat.format(dateFormat.parse(utcLongDateTime));
            } catch (java.text.ParseException e) {
                e.printStackTrace();
                return "";
            }

        }
    }
}

