package com.cec.doctorapp.network;

public interface SpecificNetworkListener extends NetworkListner {
    
    <T> void  onTypedSuccess(T responseBody);

    void onError(String msg);

    void onComplete();

    void onStart();
}