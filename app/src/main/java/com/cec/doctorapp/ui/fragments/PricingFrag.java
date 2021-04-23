package com.cec.doctorapp.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.cec.doctorapp.R;
import com.cec.doctorapp.databinding.PricingFragBinding;
import com.cec.doctorapp.helper.Vu;
import com.cec.doctorapp.model.response.GetAllPriceModel;
import com.cec.doctorapp.model.response.ResultModel;
import com.cec.doctorapp.network.NetworkListner;
import com.cec.doctorapp.ui.activities.BookingConsultActivity;
import com.cec.doctorapp.ui.activities.NotificationActivity;
import com.cec.doctorapp.ui.adapters.HorizontalMarginItemDecoration;
import com.cec.doctorapp.ui.adapters.MyViewPagerAdapter;

import java.util.ArrayList;

public class PricingFrag extends BaseFragment  {
    PricingFragBinding binding;
    private MyViewPagerAdapter adapter;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = PricingFragBinding.inflate(inflater);
        rootLayout=binding.rootLayout;
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Vu.gone( binding.pricingToolbar.back);
        binding.pricingToolbar.docHome.setTextColor(ContextCompat.getColor(requireContext(),R.color.black_exact));
        binding.pricingToolbar.docHome.setText("Pricing");
        binding.pricingToolbar.notify.setOnClickListener(v -> {
            Intent i=new Intent(requireContext(), NotificationActivity.class);
            startActivity(i);
        });
        binding.pricingToolbar.notify.setImageResource(R.drawable.ic_baseline_blue_notifications_24);
        Vu.setTransparentBG(binding.pricingToolbar.commontoolbar);
        this.setUpPagerUi();
        binding.bkingBtn.setOnClickListener(v -> {
            Intent i = new Intent(requireContext(), BookingConsultActivity.class);
            startActivity(i);
        });
       allprice();
//       binding.bkingBtn.setOnClickListener(this);
    }
    private void allprice() {
        graph.getServiceCaller().callService(graph.getApis().getallprice(), new NetworkListner() {
            @Override
            public void onSuccess(ResultModel<?> responseBody) {
                binding.getRoot().post(() -> {
                        if (responseBody.status == 0) {

                            showNoData("No Pricing found");
                        } else {
                            hideProgress();
                            Log.d("TAG", responseBody.status + "");
                            //noinspection unchecked
                            ArrayList<GetAllPriceModel> dataList = ((ArrayList<GetAllPriceModel>) responseBody.data);
                            if (dataList != null) {
                                Log.d("collcheck", dataList.size() + " ");
                                adapter = new MyViewPagerAdapter(dataList);
                                if (getActivity() != null) {
                                    requireActivity().runOnUiThread(() -> {
//                        binding.bookingPager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
                                        binding.bookingPager.setAdapter(adapter);
                                        Log.d("M_TAG", "ADAPOTER ");
                                    });
                                }
                            }

                        }



                });
            }


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

    private void setUpPagerUi() {

        binding.bookingPager.setClipToPadding(false);
//        binding.bookingPager.setPadding(48,8,48,8);


        //viewPager2.setPadding(50,0,50,0);

        // You need to retain one page on each side so that the next and previous items are visible
       binding.bookingPager.setOffscreenPageLimit(3);
        // Add a PageTransformer that translates the next and previous items horizontally
        // towards the center of the screen, which makes them visible
        float nextItemVisiblePx = requireContext().getResources().getDimension(R.dimen.viewpager_next_item_visible);
        float currentItemHorizontalMarginPx =
                requireContext().getResources().getDimension(R.dimen.margin_10dp);
        float pageTranslationX = nextItemVisiblePx + currentItemHorizontalMarginPx;
        ViewPager2.PageTransformer pageTransformer = (page, position) -> {
            page.setTranslationX((-pageTranslationX * position));
            // Next line scales the item's height. You can remove it if you don't want this effect
            //page.setScaleY(1 - (0.25f * Math.abs(position)));
            // If you want a fading effect uncomment the next line:
            //page.alpha = 0.25f + (1 - abs(position1))
        };
        binding.bookingPager.setPageTransformer(pageTransformer);

        // The ItemDecoration gives the current (centered) item horizontal margin so that
        // it doesn't occupy the whole screen width. Without it the items overlap
        HorizontalMarginItemDecoration itemDecoration = new HorizontalMarginItemDecoration(
                requireContext(),
                R.dimen.viewpager_current_item_horizontal_margin
        );
        binding.bookingPager.addItemDecoration(itemDecoration);

    }



//    private PagerAdapter buildAdapter() {
//        return(new SampleAdapter(getActivity(), getChildFragmentManager()));
//    }
}


