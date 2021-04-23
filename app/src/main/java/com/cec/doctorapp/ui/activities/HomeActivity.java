package com.cec.doctorapp.ui.activities;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.cec.doctorapp.BuildConfig;
import com.cec.doctorapp.R;
import com.cec.doctorapp.databinding.ActivityHomeBinding;
import com.cec.doctorapp.helper.Constants;
import com.cec.doctorapp.helper.SPUtils;
import com.cec.doctorapp.model.request.GetNotificationModel;
import com.cec.doctorapp.model.response.ResultModel;
import com.cec.doctorapp.network.NetworkListner;

import com.cec.doctorapp.ui.adapters.ViewPagerAdapter;
import com.cec.doctorapp.ui.fragments.BaseFragment;
import com.cec.doctorapp.ui.interfaces.FragmentCallback;
import com.cec.doctorapp.ui.presenter.HomePresenter;

public class HomeActivity extends BaseActivity implements FragmentCallback {

    private GetNotificationModel getNotificationModel;
    @NonNull
    private ActivityHomeBinding binding;
    private Fragment current;
    private String token = "";
    HomePresenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
        SPUtils fcmPref = SPUtils.getFCMPref(this);
        if (BuildConfig.DEBUG) {
            token = fcmPref.get(Constants.DEVICE_TOKEN, "");
            Log.e("FCM_TOKEN", token);
            Log.e("SDK_INT", Build.VERSION.SDK_INT + "");
        }
        getnotification(token);
    }

    private void init() {
        presenter = new HomePresenter(this, binding, graph);
        presenter.setViewPager();
        presenter.OnBottomNaviItemSelectedListener();
    }

    private void getnotification(String token) {
        GetNotificationModel notificationModel = new GetNotificationModel();
        notificationModel.setDeviceType("android");
        notificationModel.setDeviceToken(token);
        graph.getServiceCaller().callService(graph.getApis().notification(notificationModel), new NetworkListner() {
            @Override
            public void onSuccess(ResultModel<?> responseBody) {
                hideProgress();
                //noinspection unchecked
//                    Intent i = new Intent(HomeActivity.this, NotificationActivity.class);
//                    i.putExtra("token", notificationModel.getDeviceToken());

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


    //    @Override
//    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//
//
//        Fragment fragment = null;
//
//        switch (item.getItemId()) {
//            case R.id.nav_feed:
//
//                fragment = new FeedFrag();
//                break;
//
//            case R.id.nav_book:
//
//                fragment = new BookigFrag();
//                break;
//
//            case R.id.nav_pricing:
//                fragment = new PricingFrag();
//                break;
//
//            case R.id.nav_more:
//                fragment = new MoreFrag();
//                break;
//        }
//
//        return loadFragment(fragment);
//    }
//
//    private boolean loadFragment(Fragment fragment) {
//        //switching fragment
//        if (current!=null){
//            getSupportFragmentManager().beginTransaction()
//                    .remove(current).commitNow();
//        }
//
//        if (fragment != null) {
//            current = fragment;
//            getSupportFragmentManager()
//                    .beginTransaction()
//                    .replace(R.id.fragmentContainer, fragment)
//                    .commitNow();
//            return true;
//        }
//        return false;
//    }
    @Override
    public void onBackPressed() {
        if (binding.viewpager.getCurrentItem() == 0) {
            ViewPagerAdapter adapter = (ViewPagerAdapter) binding.viewpager.getAdapter();
            Fragment fragment = adapter.getFragment(0);
            if (fragment instanceof BaseFragment) {
                if (!((BaseFragment) fragment).dispatchBackPress()) {

                    showQuestionDialog(getString(R.string.confirm_exit),
                            R.string.txt_yes_simple,
                            R.string.txt_no_simple,
                            (d, v) -> finish());
                }
            }
        } else {
            showQuestionDialog(getString(R.string.confirm_exit),
                    R.string.txt_yes_simple,
                    R.string.txt_no_simple,
                    (d, v) -> finish());
        }
    }

    @Override
    public void setNotificationsCount(int count) {
        presenter.setNotificationsCount(count);
    }


    @Override
    public void gotoFrag(int position) {
        binding.viewpager.setCurrentItem(position, true);
    }
}






