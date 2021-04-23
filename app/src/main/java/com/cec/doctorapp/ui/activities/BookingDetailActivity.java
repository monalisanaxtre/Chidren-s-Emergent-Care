package com.cec.doctorapp.ui.activities;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TimePicker;

import androidx.core.content.ContextCompat;

import com.cec.doctorapp.R;

import com.cec.doctorapp.databinding.ActivityBookingDetailBinding;
import com.cec.doctorapp.helper.CustomArrayAdapter;
import com.cec.doctorapp.helper.Vu;
import com.cec.doctorapp.model.request.GetBookData;
import com.cec.doctorapp.model.response.BookingDataModel;
import com.cec.doctorapp.model.response.PatientRegisterModel;

import com.cec.doctorapp.model.response.PatientRegisterResponseBean;
import com.cec.doctorapp.model.response.ResultModel;
import com.cec.doctorapp.network.NetworkListner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class BookingDetailActivity extends BaseActivity implements View.OnClickListener {
    private ActivityBookingDetailBinding binding;
    TimePickerDialog timePickerDialog;
    Calendar calendar;
    int currentHour;
    int currentMinute;
    private final static String[] TimeSlot = {"Select Time", "09:00AM 10:00AM", "11:00AM 12:00PM", "13:00PM 14:00PM", "15:00PM 16:00PM", "16.00PM 17.00PM", "18.00PM 19.00PM", "20.00PM 21.00PM", "22.00PM 23.00PM", "23.00PM 00.00AM"};
    private final static int TIME_PICKER_INTERVAL = 5;
    private TimePicker mTimePicker;
    int patientid = 1;
    String bookingno = "";
    int id = 0;
    String fromTime = "";
    String toTime = "";
    int count;
    ArrayList<BookingDataModel> arraybookingtiminglist = new ArrayList<>();
    //    String amPm;
    private int mYear, mMonth, mDay, mHour, mMinute;
    ArrayList<PatientRegisterModel> patientRegisterlist = new ArrayList<>();
    PatientRegisterModel model;

//    public BookingDetailActivity(Context context, TimePickerDialog.OnTimeSetListener listener, int hourOfDay, int minute, boolean is24HourView) {
//        super(context, TimePickerDialog.THEME_HOLO_LIGHT, null, hourOfDay,
//                minute / TIME_PICKER_INTERVAL, is24HourView);
//        mTimeSetListener = listener;
//    }


    public void updateTime(int hourOfDay, int minuteOfHour) {
        mTimePicker.setCurrentHour(hourOfDay);
        mTimePicker.setCurrentMinute(minuteOfHour / TIME_PICKER_INTERVAL);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBookingDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        rootLayout = binding.rootLayout;
        binding.toolBar.notify.setOnClickListener(this);
        binding.toolBar.back.setOnClickListener(this);
//        binding.toolBar.docHome.setTextColor(ContextCompat.getColor((),R.color.black_exact));
        binding.toolBar.notify.setImageResource(R.drawable.ic_baseline_blue_notifications_24);
        Vu.setTransparentBG(binding.toolBar.commontoolbar);
        binding.confirmbkngBtn.setOnClickListener(this);
        binding.toolBar.docHome.setTextColor(ContextCompat.getColor(BookingDetailActivity.this, R.color.black_exact));
        binding.toolBar.docHome.setText("Booking Detail");
        binding.confirmbkngBtn.setOnClickListener(this);
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(BookingDetailActivity.this,
//                android.R.layout.simple_spinner_item, TimeSlot);
//
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
//        binding.spinnerTime.setAdapter(adapter);
//            int index = Arrays.asList(TimeSlot).indexOf(binding.time.getText().toString());
//            if (index != -1) binding.spinnerTime.setSelection(index);
//           binding.time.setText(R.string.msg_Time);
//       index= Arrays.asList(TimeSlot).indexOf(binding.time.getText().toString());
//        if (index != -1)binding.spinnerTime.setSelection(index);
//
//
//      binding.spinnerTime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//              binding.time.setText(adapter.getItem(i));
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//            }
//        });
        binding.time.setOnClickListener(v1 -> {
        });
//            calendar = Calendar.getInstance();
//            currentHour = calendar.get(Calendar.HOUR_OF_DAY);
//            currentMinute = calendar.get(Calendar.MINUTE);
//            timePickerDialog = new TimePickerDialog(BookingDetailActivity.this, (timePicker, hourOfDay, minutes) -> {
////                    if (hourOfDay >= 12) {
////                        amPm = "PM";
////                    } else {
////                        amPm = "AM";
////                    }
//                binding.etTime.setText(String.format("%02d:%02d", hourOfDay, minutes));
//            }, currentHour, currentMinute, false);
//            timePickerDialog.show();
//
//        });
        binding.etDate.setOnClickListener(v1 -> {

//            final Calendar c = Calendar.getInstance();
//            mYear = c.get(Calendar.YEAR);
//            mMonth = c.get(Calendar.MONTH);
//            mDay = c.get(Calendar.DAY_OF_MONTH);
//
//            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
//                    (view, year, monthOfYear, dayOfMonth) -> binding.etDate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth), mYear, mMonth, mDay);
//            datePickerDialog.getDatePicker().setMinDate(Calendar.getInstance().getTimeInMillis());
//            datePickerDialog.show();
        });
        initview();
    }

    private void initview() {
        GetBookData bookData = new GetBookData();
        long date = getIntent().getLongExtra("date", -1L);
        //now create date and format to string
        Date date1 = new Date(date);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ROOT);
        String currentDateandTime = sdf.format(date1);
        bookData.setDate(currentDateandTime);
        binding.etDate.setText(currentDateandTime);
        //noinspection unchecked
        graph.getServiceCaller().callService(graph.getApis().get_booking_times(bookData), new NetworkListner() {
            @Override
            public void onSuccess(ResultModel<?> responseBody) {
                if (responseBody.status == 1 && responseBody.data != null&& count>0) {
                }
                    else{
                        setbookingtime((ArrayList<BookingDataModel>) responseBody.getResultData());
                    }

                Log.d("TAG", "SUCCESS");
            }


            @Override
            public void onError(String msg) {
                Log.d("ERRR", "Error");

            }

            @Override
            public void onComplete() {

            }

            @Override
            public void onStart() {

            }
        });


    }

    private void patientReg() {
//        showError();
        if (!this.validateInput()
        ) return;
        PatientRegisterModel patientRegisterModel = new PatientRegisterModel();
        patientRegisterModel.setName(binding.etName.getText().toString());
        patientRegisterModel.setPhone(binding.etMob.getText().toString());
        patientRegisterModel.setEmail(binding.etMail.getText().toString());
        String idval = String.valueOf(id);
        patientRegisterModel.setTime(idval);
        patientRegisterModel.setDate(binding.etDate.getText().toString());
        //noinspection unchecked
        graph.getServiceCaller().callService(graph.getApis().patientregister(patientRegisterModel), new NetworkListner() {
            @Override
            public void onSuccess(ResultModel<?> responseBody) {
                showError();
                if (responseBody.status == 0) {
                    showSessionExpiredDialog(responseBody.msg);
                } else {
                    Log.d("TAG", "SUCCESS");
                    hideProgress();
                    if (responseBody.data instanceof PatientRegisterResponseBean) {
                        PatientRegisterResponseBean patientRegisterResponseBean = (PatientRegisterResponseBean) responseBody.data;
                        Intent i = new Intent(BookingDetailActivity.this, OTPActivity.class);
                        i.putExtra("bookingno", patientRegisterResponseBean.getBookingNo());
                        i.putExtra("Patientid", patientRegisterResponseBean.getPatientId());
                        String theText = binding.etMail.getText().toString();
                        i.putExtra("text_label", theText);
                        startActivity(i);
                        resetalledit();
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

    private void showError() {
        showSnack(" Please Check your Internet Connection ");
    }


    private Boolean validateInput() {
        if (binding.etName.getText().toString().trim().isEmpty()) {
            showSnack("Please enter your Name");
            return false;

        } else if (binding.etMob.getText().toString().trim().isEmpty()) {
            showSnack("Please enter Your Mobile Number");
            return false;
        } else if (binding.etMail.getText().toString().trim().isEmpty()) {
            showSnack("Please enter Email");
            return false;
        } else if (binding.etDate.getText().toString().trim().isEmpty()) {
            showSnack("Please enter Date");
            return false;
        } else if (binding.time.getText().toString().trim().isEmpty()) {
            showSnack("Please enter Time");
            return false;
        } else if (!isValidEmail(binding.etMail.getText().toString())) {
            showSnack("Please enter valid email.");
            return false;

        } else if (!isValidMobile(binding.etMob.getText().toString())) {
            showSnack("Please Enter valid contact number.");
            return false;
        }


        return true;
    }

    private void resetalledit() {
        binding.etName.setText(null);
//        binding.time.setText(null);
        binding.etMail.setText(null);
        binding.etMob.setText(null);
//        binding.etDate.setText(null);
    }

//    private void setData(ArrayList<PatientRegisterModel> resultData) {
//        binding.etName.setText(resultData.get(0).getName());
//        binding.etMob.setText(resultData.get(0).getPhone());
//        binding.etMail.setText(resultData.get(0).getEmail());
//        binding.etDate.setText(resultData.get(0).getDate());
//        binding.etTime.setText(resultData.get(0).getTime());
//    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.confirmbkng_btn: {
                patientReg();
                break;
            }

            case R.id.back: {
                onBackPressed();
                break;
            }
            case R.id.notify: {
                Intent i = new Intent(this, NotificationActivity.class);
                startActivity(i);
                break;
            }

        }


    }

    void setbookingtime(ArrayList<BookingDataModel> list) {
        Log.d("TAG", "SUCCESS:::CAT::" + list.size());
        arraybookingtiminglist.clear();
        BookingDataModel tmp = new BookingDataModel(true);
        tmp.setName("Select Time");


//        tmp.setId(0);
        arraybookingtiminglist.add(tmp);
        arraybookingtiminglist.addAll(list);
        CustomArrayAdapter adapter = new CustomArrayAdapter(this, arraybookingtiminglist);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerTime.setVisibility(View.VISIBLE);
        binding.spinnerTime.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        if (arraybookingtiminglist.size() > 0) {
            for (int i = 0; i < arraybookingtiminglist.size(); i++) {
                if (arraybookingtiminglist.get(i).getId() == id) {
                    //noinspection unchecked
                    int spinnerPosition = adapter.getPosition(arraybookingtiminglist.get(i));
                    binding.spinnerTime.setSelection(spinnerPosition);
                    binding.time.setText(arraybookingtiminglist.get(i).getName());
//                    arraybookingtiminglist.get(i).getId();
//                  binding.time.setText(arraybookingtiminglist.get(i).getFromTime());
                    break;
                }
            }
        }


        binding.spinnerTime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @SuppressLint("SetTextI18n")
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                BookingDataModel model = arraybookingtiminglist.get(position);
                if (model.isDummy()) {
                    binding.time.setText(model.getName());
                } else {
                    id = arraybookingtiminglist.get(position).getId();
                    fromTime = arraybookingtiminglist.get(position).fromTime;
                    toTime = arraybookingtiminglist.get(position).toTime;
                    binding.time.setText(fromTime +  " - " +  toTime);
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }


}





