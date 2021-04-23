package com.cec.doctorapp.ui.presenter;

import android.content.Context;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

import com.cec.doctorapp.app.AppComponents;
import com.cec.doctorapp.databinding.ActivityHomeBinding;
import com.cec.doctorapp.helper.Helper;
import com.cec.doctorapp.helper.Vu;
import com.cec.doctorapp.model.response.ResultModel;
import com.cec.doctorapp.network.NetworkListner;
import com.cec.doctorapp.ui.activities.HomeActivity;
import com.cec.doctorapp.ui.adapters.ViewPagerAdapter;
import com.cec.doctorapp.ui.fragments.BookigFrag;
import com.cec.doctorapp.ui.fragments.FeedFrag;
import com.cec.doctorapp.ui.fragments.MoreFrag;
import com.cec.doctorapp.ui.fragments.PricingFrag;
import com.cec.doctorapp.ui.interfaces.FragmentCallback;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class HomePresenter implements NetworkListner, FragmentCallback {

    private final Context context;
    private AppComponents graph;
    public ActivityHomeBinding activityHomeBinding;
    MenuItem prevMenuItem;
    String title = "";
    private TextView badgeTextView;

    public HomePresenter(Context context, ActivityHomeBinding activityHomeBinding, AppComponents graph) {
        this.context = context;
        this.graph = graph;
        this.activityHomeBinding = activityHomeBinding;
    }

    //Bottom NavigationItem Selected

    public void OnBottomNaviItemSelectedListener() {

        Helper.Companion.disableTooltip(activityHomeBinding.bottomNavView);
        activityHomeBinding.bottomNavView.setOnNavigationItemSelectedListener(item -> {
            String id = item.getTitle().toString();
            switch (id) {
                case "Feed":
                    activityHomeBinding.viewpager.setCurrentItem(0);
                    title = "Feed";
                    break;
                case "Booking":
                    activityHomeBinding.viewpager.setCurrentItem(1);
                    title = "Booking";
                    break;
                case "Pricing":
                    activityHomeBinding.viewpager.setCurrentItem(2);
                    title = "Pricing";
                    break;
                case "Consent form":
                    activityHomeBinding.viewpager.setCurrentItem(3);
                    title = "Consent form";
                    break;
            }
//                ((HomeActivity) context).setActionBarData(title);
            return true;
        });
    }

    public void setViewPager() {
        activityHomeBinding.viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (prevMenuItem != null) {
                    prevMenuItem.setChecked(false);
                } else {
                    activityHomeBinding.bottomNavView.getMenu().getItem(0).setChecked(false);
//                    activityHomeBinding.getMenu().getItem(0).setChecked(false);
                }

                Log.d("page", "onPageSelected: " + position);
                activityHomeBinding.bottomNavView.getMenu().getItem(position).setChecked(true);
                prevMenuItem = activityHomeBinding.bottomNavView.getMenu().getItem(position);
                if (position == 0) {
                    title = "Feed";
                } else if (position == 1) {
                    title = "Booking";
                } else if (position == 2) {
                    title = "Pricing";
                } else if (position == 3) {
                    title = "Consent form";
                }
//                ((HomeActivity) context).setActionBarData(title);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        setupViewPagerItem( activityHomeBinding.viewpager);
    }

    private void setupViewPagerItem(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(((HomeActivity) context).getSupportFragmentManager());
        FeedFrag feedFrag = new FeedFrag();
        BookigFrag bookigFrag = new BookigFrag();
        PricingFrag pricingFrag = new PricingFrag();
        MoreFrag moreFrag = new MoreFrag();
        adapter.addFragment(feedFrag);
        adapter.addFragment(bookigFrag);
        adapter.addFragment(pricingFrag);
        adapter.addFragment(moreFrag);
        viewPager.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onSuccess(ResultModel<?> responseBody) {

    }

    @Override
    public void onError(String msg) {
//        context.hideProgress();
    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onStart() {

    }
    @Override
    public void setNotificationsCount(int count) {
        if (count>0) {
            String mCount=count>9?String.valueOf(9).concat("+"):String.valueOf(count);
            badgeTextView.setText(mCount);
            if (Vu.isNotVisible(badgeTextView)){
                Vu.fabStyleShow(badgeTextView);
            }
        }else {
            if (Vu.isVisible(badgeTextView)){
                Vu.fabStyleGone(badgeTextView);
            }
        }

    }
}
