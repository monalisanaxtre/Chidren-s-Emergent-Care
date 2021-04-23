package com.cec.doctorapp.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.cec.doctorapp.R;
import com.cec.doctorapp.databinding.ActivityConfirmBookingBinding;
import com.cec.doctorapp.model.response.OtpRegisterResponseModelBean;
import com.cec.doctorapp.model.response.ResultModel;
import com.cec.doctorapp.network.NetworkListner;

public class ConfirmBookingActivity extends BaseActivity implements View.OnClickListener {

    private ActivityConfirmBookingBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityConfirmBookingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        patientreginfo();
        binding.donebtn.setOnClickListener(this);
    }


    private void patientreginfo() {
        String bookdate = getIntent().getStringExtra("bookdate");

        String patientname = getIntent().getStringExtra("patientname");

        String booktime = getIntent().getStringExtra("booktime");

        String bookingno = getIntent().getStringExtra("bookingno");

        String docname = getIntent().getStringExtra("docname");

        String speclize = getIntent().getStringExtra("speclize");

//        binding.textidTv.setText(bookingno);
        binding.Bookingno.setText(bookingno);
        binding.textname.setText(docname);
        binding.speclzeTv.setText(speclize);
        binding.nmTv.setText(patientname);
        binding.timeTv.setText( booktime);
        binding.dtTv.setText(bookdate );
//

    }

    private void bookinginfo() {
//        OtpRegisterResponseModelBean otpRegisterResponseModelBean=new OtpRegisterResponseModelBean();
//        binding.nmTv.setText(otpRegisterResponseModelBean.name);
//        binding.timeTv.setText(otpRegisterResponseModelBean.bookTime);
//        binding.dtTv.setText(otpRegisterResponseModelBean.bookDate);

        graph.getServiceCaller().callService(graph.getApis().getDoctorInfo(), new NetworkListner() {
            @Override
            public void onSuccess(ResultModel<?> responseBody) {
                hideProgress();
                Log.d("TAG", responseBody.data + "");
                //noinspection unchecked
                OtpRegisterResponseModelBean otpRegisterResponseModelBean = ((OtpRegisterResponseModelBean) responseBody.data);
                binding.textidTv.setText(otpRegisterResponseModelBean.bookingNo);
                binding.textname.setText(otpRegisterResponseModelBean.drName);
                binding.speclzeTv.setText(otpRegisterResponseModelBean.drSpeciality);
                Glide.with(getApplication())
                        .load(otpRegisterResponseModelBean.imgDoc)
                        .placeholder(R.drawable.d)
//                        .optionalFitCenter()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(binding.imgview);

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
        Intent i=new Intent(ConfirmBookingActivity.this,HomeActivity.class);
        startActivity(i);

    }
}






