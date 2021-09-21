package com.cec.doctorapp.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.cec.doctorapp.R;
import com.cec.doctorapp.databinding.ActivityOtpBookingBinding;
import com.cec.doctorapp.helper.Vu;
import com.cec.doctorapp.model.request.GetOtpModel;
import com.cec.doctorapp.model.response.OtpRegisterResponseModelBean;
import com.cec.doctorapp.model.response.ResultModel;
import com.cec.doctorapp.network.NetworkListner;

public class OTPActivity extends BaseActivity implements View.OnClickListener {
    private ActivityOtpBookingBinding binding;
    private EditText[] otpEt = new EditText[6];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOtpBookingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        rootLayout = binding.rootLayout;
        Vu.gone(binding.otptoolBar.back);
        Vu.gone(binding.otptoolBar.notify);
        binding.otptoolBar.docHome.setText("Verification Code");
        binding.verifyOtpBtn.setOnClickListener(this);

//        otpverification();
        otpEt[0] = binding.otpEditBox1;
        otpEt[1] = binding.otpEditBox2;
        otpEt[2] = binding.otpEditBox3;
        otpEt[3] = binding.otpEditBox4;
        otpEt[4] = binding.otpEditBox5;
        otpEt[5] = binding.otpEditBox6;
        Intent i = getIntent();
        String text = i.getStringExtra("text_label");
// Now set this value to EditText
        binding.otptxt2.setText(text );
        binding.otptxt2.setEnabled(false);

    }




    //        for (int i = 0; i < 6; i++) { //Its designed for 6 digit OTP
//            final int iVal = i;
//            otpEt[iVal].addTextChangedListener(new TextWatcher() {
//                @Override
//                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//                }
//
//                @Override
//                public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//
//                }
//
//                @Override
//                public void afterTextChanged(Editable s) {
//                    if (iVal == 5 && !otpEt[iVal].getText().toString().isEmpty()) {
//                        binding.rootLayout.requestFocus(); //Clears focus when you have entered the last digit of the OTP.
//                    } else if (!otpEt[iVal].getText().toString().isEmpty()) {
//                        otpEt[iVal + 1].requestFocus(); //focuses on the next edittext after a digit is entered.
//                    }
//                }
//            });
////            for starting index -1 always if you srting from 0th index
////            otpEt[iVal].setOnFocusChangeListener((view, hasFocus) -> {
////                boolean hasEmpty=false;
////                if (hasFocus)
////                {
////                    for (int index=iVal-1;index>=0;index--){
////                        if(otpEt[index].getText().toString().isEmpty()){
////                            otpEt[index].requestFocus();
////                            hasEmpty=true;
////                        }
////                    }
////                    if(!hasEmpty){
////                        otpEt[5].requestFocus();
////                    }
////                }
////            });
//            otpEt[iVal].setOnFocusChangeListener((view, hasFocus) -> {
//                if (hasFocus)
//                {
//                    for (int index=iVal-1;index>0;index--){
//                        if(otpEt[index-1].getText().toString().isEmpty()){
//                            otpEt[index-1].requestFocus();
//
//
////                            otpEt[5].requestFocus();
//                        }
//                    }
//                }
//            });
//
//
//
////
//            otpEt[iVal].setOnKeyListener((v, keyCode, event) -> {
//                if (event.getAction() != KeyEvent.ACTION_DOWN) {
//                    return false; //Dont get confused by this, it is because onKeyListener is called twice and this condition is to avoid it.
//                }
//                if (keyCode == KeyEvent.KEYCODE_DEL &&
//                        otpEt[iVal].getText().toString().isEmpty() && iVal != 0) {
////this condition is to handel the delete input by users.
//                    otpEt[iVal -1].setText("");//Deletes the digit of OTP
//                    otpEt[iVal -1].requestFocus();//and sets the focus on previous digit
//                }
//                return false;
//            });
//        }







    private void otpverification() {
       if (!this.validateotp()) return;
        GetOtpModel otpModel = new GetOtpModel();
        otpModel.setOtp(binding.oevView.getText().toString());
//        otpModel.setOtp(binding.otpEditBox1.getText().toString() +
//                binding.otpEditBox2.getText().toString() +
//                binding.otpEditBox3.getText().toString() +
//                binding.otpEditBox4.getText().toString() +
//                binding.otpEditBox5.getText().toString() +
//                binding.otpEditBox6.getText().toString());
        String bookingno = getIntent().getStringExtra("bookingno");
        Integer patientid = getIntent().getIntExtra("Patientid", -1);
        otpModel.setBookingNo(bookingno);
        otpModel.setPatientId(patientid);
                graph.getServiceCaller().callService(graph.getApis().getOtp(otpModel), new NetworkListner() {
            @Override
            public void onSuccess(ResultModel<?> responseBody) {
                if (responseBody.status == 0) {
                    showSnack("Wrong Otp");
                } else {
                    Log.d("TAG", "SUCCESS");
                    hideProgress();
                    if (responseBody.data instanceof OtpRegisterResponseModelBean) {
                        OtpRegisterResponseModelBean otpRegisterResponseModelBean = (OtpRegisterResponseModelBean) responseBody.data;
                        Intent i = new Intent(OTPActivity.this, ConfirmBookingActivity.class);
                        i.putExtra("bookdate", otpRegisterResponseModelBean.getBookDate());
                        i.putExtra("patientname", otpRegisterResponseModelBean.getName());
                        i.putExtra("booktime", otpRegisterResponseModelBean.getBookTime());
                        i.putExtra("bookingno", otpRegisterResponseModelBean.getBookingNo());
                        i.putExtra("docname", otpRegisterResponseModelBean.getDrName());
                        i.putExtra("speclize", otpRegisterResponseModelBean.getDrSpeciality());
                        i.putExtra("doc_img",otpRegisterResponseModelBean.getImgDoc());
                        startActivity(i);
                    }


                }
            }




            @Override
            public void onError(String msg) {
                Log.d("TAG", "ONERROR");
                hideProgress();
            }

            @Override
            public void onComplete() {
                Log.d("TAG", "ONComplete");
                hideProgress();

            }

            @Override
            public void onStart() {
                Log.d("TAG", "onstart");
                showProgress();

            }
        });


    }

    private boolean validateotp() {
        if (binding.oevView.getText().toString().trim().isEmpty()) {
            showSnack("Please enter All Fields");
            return false;


        }
        return true;

    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.verify_otp_btn:
              otpverification();
                break;

        }

    }

    }



