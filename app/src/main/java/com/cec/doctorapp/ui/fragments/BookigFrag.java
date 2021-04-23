package com.cec.doctorapp.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.cec.doctorapp.R;
import com.cec.doctorapp.databinding.BookingFragBinding;
import com.cec.doctorapp.helper.Constants;
import com.cec.doctorapp.helper.Vu;
import com.cec.doctorapp.model.response.DocInfoModel;
import com.cec.doctorapp.model.response.ResultModel;
import com.cec.doctorapp.network.NetworkListner;
import com.cec.doctorapp.ui.activities.BookingConsultActivity;
import com.cec.doctorapp.ui.activities.NotificationActivity;
import com.cec.doctorapp.ui.activities.TimeActivity;
import com.cec.doctorapp.ui.interfaces.FragmentCallback;


import org.jetbrains.annotations.NotNull;

public class BookigFrag extends BaseFragment {

    BookingFragBinding bookingFragBinding;
    @Nullable
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        bookingFragBinding = BookingFragBinding.inflate(inflater);
        rootLayout=bookingFragBinding.rootLayout;


        return bookingFragBinding.getRoot();

    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Vu.setTransparentBG(bookingFragBinding.toolBar.commontoolbar);
        Vu.gone(bookingFragBinding.toolBar.back);
        bookingFragBinding.toolBar.notify.setOnClickListener(v -> {
            Intent i=new Intent(requireContext(), NotificationActivity.class);
            startActivity(i);
        });
        bookingFragBinding.toolBar.notify.setImageResource(R.drawable.ic_baseline_blue_notifications_24);
        bookingFragBinding.card2.setOnClickListener(v -> {
//            bookingFragBinding.card2.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.deepblue));
//            bookingFragBinding.imageThumbnai.setColorFilter(ContextCompat.getColor(requireContext(), R.color.white), android.graphics.PorterDuff.Mode.MULTIPLY);
//            bookingFragBinding.textTitl.setTextColor(ContextCompat.getColor(requireContext(), R.color.white));
//            bookingFragBinding.text4.setTextColor(ContextCompat.getColor(requireContext(),R.color.white));
//

            Intent intent = new Intent(requireContext(), TimeActivity.class);
            startActivity(intent);
        });

        bookingFragBinding.card1.setOnClickListener(v -> {
//
//        bookingFragBinding.card1.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.deepblue));
//        bookingFragBinding.imageThumbnail.setColorFilter(ContextCompat.getColor(requireContext(), R.color.white), android.graphics.PorterDuff.Mode.MULTIPLY);
//        bookingFragBinding.textTitt.setTextColor(ContextCompat.getColor(requireContext(), R.color.white));
//        bookingFragBinding.text.setTextColor(ContextCompat.getColor(requireContext(),R.color.white));

            if(getContext() instanceof FragmentCallback){
                ((FragmentCallback)getContext()).gotoFrag(2);
            }
        });
        bookingFragBinding.card3.setOnClickListener(v -> {
//            bookingFragBinding.card3.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.deepblue));
//            bookingFragBinding.imageThumbna.setColorFilter(ContextCompat.getColor(requireContext(), R.color.white), android.graphics.PorterDuff.Mode.MULTIPLY);
//            bookingFragBinding.textTit.setTextColor(ContextCompat.getColor(requireContext(), R.color.white));
//            bookingFragBinding.tex.setTextColor(ContextCompat.getColor(requireContext(),R.color.white));
            Intent intent = new Intent(requireContext(), BookingConsultActivity.class);
            startActivity(intent);
        });
        docinfo();

//        bookingFragBinding.card1.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white));
////        bookingFragBinding.card2.setBackgroundColor(R.color.white);
////        bookingFragBinding.card3.setBackgroundColor(R.color.white);
//        bookingFragBinding.imageThumbnail.clearColorFilter();
//        bookingFragBinding.textTitt.setTextColor(ContextCompat.getColor(requireContext(), R.color.black_exact));
//        bookingFragBinding.text.setTextColor(ContextCompat.getColor(requireContext(), R.color.grey));
//        bookingFragBinding.scrollview.setVerticalScrollBarEnabled(false);
//       bookingFragBinding.scrollview.setHorizontalScrollBarEnabled(false);
//        bookingFragBinding.scrollview.setSmoothScrollingEnabled(false);

    }


//    @Override
//    public void onResume() {
//        super.onResume();

//        bookingFragBinding.card1.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white));
//        bookingFragBinding.card2.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white));
//        bookingFragBinding.card3.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white));
//        bookingFragBinding.imageThumbnail.setColorFilter(null);
//        bookingFragBinding.textTitt.setTextColor(ContextCompat.getColor(requireContext(), R.color.black_exact));
//        bookingFragBinding.text.setTextColor(ContextCompat.getColor(requireContext(), R.color.grey));
//        bookingFragBinding.imageThumbnai.setColorFilter(null);
//        bookingFragBinding.textTit.setTextColor(ContextCompat.getColor(requireContext(), R.color.black_exact));
//        bookingFragBinding.text4.setTextColor(ContextCompat.getColor(requireContext(), R.color.grey));
//        bookingFragBinding.imageThumbna.setColorFilter(null);
//        bookingFragBinding.textTitl.setTextColor(ContextCompat.getColor(requireContext(), R.color.black_exact));
//        bookingFragBinding.tex.setTextColor(ContextCompat.getColor(requireContext(), R.color.grey));


//    }

    private void docinfo() {
        graph.getServiceCaller().callService(graph.getApis().getDoctorInfo(), new NetworkListner() {
            @Override
            public void onSuccess(ResultModel<?> responseBody) {
                if (getActivity() != null) {
                    requireActivity().runOnUiThread(() -> {
                        hideProgress();
                        DocInfoModel docInfoModel = ((DocInfoModel) responseBody.data);
                        graph.spUtils().put(Constants.Name, docInfoModel.name);
                        graph.spUtils().put(Constants.SPECIALIZATION, docInfoModel.specialty);
                        graph.spUtils().put(Constants.DESCRIPTION, docInfoModel.description);
                        hideProgress();
                        Log.d("TAG", responseBody.status + "");
                        bookingFragBinding.text1.setText(docInfoModel.name);
                        bookingFragBinding.text2.setText(docInfoModel.specialty);
                        bookingFragBinding.text3.setText(docInfoModel.description);
                        bookingFragBinding.rt.setRating(Float.parseFloat(""+docInfoModel.rating)
                        );
                        bookingFragBinding.rtTxt.setText(String.valueOf(docInfoModel.rating));
                        Glide.with(requireContext())
                                .load(docInfoModel.imgDoc)
                                .placeholder(R.drawable.vipgrg)
//                        .optionalFitCenter()
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .into(bookingFragBinding.doctorImage);
                    });
                }
            }

                //noinspection unchecked
//                ArrayList<DocInfoModel> dataList = ((ArrayList<DocInfoModel>) responseBody.data);
//
//                if (dataList!=null){
//                    Log.d("collcheck", dataList.size() + " ");
//                    if (getActivity() != null) {
//                        requireActivity().runOnUiThread(() -> {
////
//                            Log.d("M_TAG", "ADAPOTER ");
//                        });
//                    }
//                }



            @Override
            public void onError(String msg) {
                Log.d("TAG", msg + "");
                hideProgress();
            }

            @Override
            public void onComplete() {
                Log.d("TAG", "onComplete");
                hideProgress();
            }

            @Override
            public void onStart() {
                Log.d("TAG", "onstart");
                showProgress();
            }
        });

    }
}


