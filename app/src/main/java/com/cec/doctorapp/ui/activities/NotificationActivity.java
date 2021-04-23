package com.cec.doctorapp.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import androidx.core.content.ContextCompat;

import com.cec.doctorapp.R;
import com.cec.doctorapp.databinding.ActivityCommonNotificationBinding;
import com.cec.doctorapp.helper.Constants;
import com.cec.doctorapp.helper.SPUtils;
import com.cec.doctorapp.helper.Vu;
import com.cec.doctorapp.model.request.NotificationModel;
import com.cec.doctorapp.model.response.NotificationItemModel;
import com.cec.doctorapp.model.response.ResultModel;
import com.cec.doctorapp.network.NetworkListner;
import com.cec.doctorapp.ui.adapters.NotificationAdapter;
import com.cec.doctorapp.ui.interfaces.FragmentCallback;

import java.util.ArrayList;

public class NotificationActivity  extends BaseActivity  implements View.OnClickListener{

    private ActivityCommonNotificationBinding binding;
    boolean runNotification = true;
    private FragmentCallback fragmentCallback;
    private NotificationAdapter notificationsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCommonNotificationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        rootLayout = binding.rootLayout;
        binding.toolBar.docHome.setTextColor(ContextCompat.getColor(NotificationActivity.this, R.color.black_exact));
        Vu.setTransparentBG(binding.toolBar.commontoolbar);
       Vu.gone( binding.toolBar.notify);
        binding.toolBar.docHome.setText("Notification");
        binding.toolBar.back.setOnClickListener(v -> {
            Intent i = new Intent(NotificationActivity.this, HomeActivity.class);
            startActivity(i);
        });
        getnotification();

        binding.pullToRefresh.setOnRefreshListener(() -> {
            refreshData();
            binding.pullToRefresh.setRefreshing(false);
        });
    }

    private void refreshData(){

        new Handler().postDelayed(() -> binding.pullToRefresh.setRefreshing(false), 2000);
getnotification();
    }

    private void getnotification() {
        NotificationModel notification = new NotificationModel();
        SPUtils fcmPref = SPUtils.getFCMPref(this);
        String token = fcmPref.get(Constants.DEVICE_TOKEN, "");
        notification.setToken(token);
        //noinspection unchecked
        graph.getServiceCaller().callService(graph.getApis().getnotification(notification), new NetworkListner() {
            @Override
            public void onSuccess(ResultModel<?> responseBody) {
                binding.getRoot().post(() -> {

                    runOnUiThread(() -> {
                        if (responseBody.status == 0) {
//                            Vu.setTransparentBG(binding.toolBar.commontoolbar);
//                            binding.toolBar.docHome.setText("Notification");
                            showNoData("No Notification found");
                        }
                        else{
                            Log.d("TAG", "SUCCESS");
                            hideProgress();
                            if (responseBody.data instanceof ArrayList<?>) {
                                //noinspection unchecked
                                ArrayList<NotificationItemModel> datalist = ((ArrayList<NotificationItemModel>) responseBody.data);
                                //now pass the list to adapter
                                NotificationAdapter adpter = new NotificationAdapter(NotificationActivity.this, datalist);
                                binding.notiRv.setAdapter(adpter);
//                            if (fragmentCallback != null) {
//                                fragmentCallback.setNotificationsCount(datalist.size());
//                            }
//                        } else {
//                            if (fragmentCallback != null) {
//                                fragmentCallback.setNotificationsCount(0);
                            }


                        }
                    });
                });
            }

            @Override
            public void onError(String msg) {
                hideProgress();

            }

            @Override
            public void onComplete() {
                hideProgress();
            }

            @Override
            public void onStart() {
                showProgress();
            }
        });

    }

    //    pushnotification refresh through broadcast
    static void updateMyNotification(Context context, String message) {

        Intent intent = new Intent("REFRESH_NOTIFICATION");


        //send broadcast
        context.sendBroadcast(intent);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {


            case R.id.back:
            {
                onBackPressed();
                break;
            }
            case R.id.notify:{
                Intent i = new Intent(this, NotificationActivity.class);
                startActivity(i);
                break;
            }
        }
    }
}