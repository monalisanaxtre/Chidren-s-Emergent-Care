package com.cec.doctorapp.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.cec.doctorapp.R;
import com.cec.doctorapp.databinding.ActivityTimeBinding;
import com.cec.doctorapp.helper.Vu;
import com.cec.doctorapp.model.response.GetAvailabilityModel;
import com.cec.doctorapp.model.response.GetAvailabiltyModel;
import com.cec.doctorapp.model.response.ResultModel;
import com.cec.doctorapp.network.NetworkListner;
import com.cec.doctorapp.ui.adapters.AvailablityAdapter;


public class TimeActivity extends BaseActivity implements View.OnClickListener {

    private ActivityTimeBinding binding;
    private TextView doc_home;
    private AvailablityAdapter adapter;
    private GetAvailabiltyModel availabiltyModel;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTimeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        rootLayout=binding.rootLayout;
        binding.toolBar.docHome.setTextColor(ContextCompat.getColor(TimeActivity.this, R.color.black_exact));
        binding.toolBar.docHome.setText("Time");
        binding.toolBar.back.setOnClickListener(this);

        binding.toolBar.notify.setImageResource(R.drawable.ic_baseline_blue_notifications_24);
        binding.toolBar.notify.setOnClickListener(v -> {
            Intent intent=new Intent(TimeActivity.this,NotificationActivity.class);
            startActivity(intent);
        });
        Vu.setTransparentBG(binding.toolBar.commontoolbar);
//        Vu.gone(binding.toolBar.back);
        initview();
    }

    private void initview() {
        graph.getServiceCaller().callService(graph.getApis().getavailabilty(), new NetworkListner() {
            @Override
            public void onSuccess(ResultModel<?> responseBody) {
                if (responseBody.status == 0) {
//
                    showNoData("No Apponitment");
                } else {
                    hideProgress();
                    Log.d("TAG", responseBody.data + "");
                    //noinspection unchecked

                    GetAvailabilityModel getAvailabilityModel = ((GetAvailabilityModel) responseBody.data);
                    if (getAvailabilityModel != null) {
                        Log.d("timehck", getAvailabilityModel.timing + " ");
                        if (getAvailabilityModel.futureAvailStatus == 0) {
                            Vu.gone(binding.avaiblity);
                        } else {
                            Vu.show(binding.avaiblity);
                            binding.formTv.setText(getAvailabilityModel.fromDate);
                            binding.toTv.setText(getAvailabilityModel.toDate);
                        }
                        adapter = new AvailablityAdapter(TimeActivity.this, getAvailabilityModel.timing);
                        TimeActivity.this.runOnUiThread(() -> {
                            binding.timeRV.setAdapter(adapter);
                            Log.d("M_TAG", "ADAPOTER ");
                        });

                    }
                }
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
