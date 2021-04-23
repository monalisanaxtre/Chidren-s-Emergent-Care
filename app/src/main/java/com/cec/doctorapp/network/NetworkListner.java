package com.cec.doctorapp.network;


import com.cec.doctorapp.model.response.ResultModel;

public interface NetworkListner {


    void onSuccess(ResultModel<?> responseBody);

    void onError(String msg);

    void onComplete();

    void onStart();
}



