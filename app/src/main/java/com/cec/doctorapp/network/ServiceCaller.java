package com.cec.doctorapp.network;


import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.cec.doctorapp.helper.SPUtils;
import com.cec.doctorapp.helper.Utility;
import com.cec.doctorapp.model.response.ResultModel;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import okhttp3.ResponseBody;
import rx.Observable;
import rx.Observer;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class ServiceCaller<T> {
    T t;
    //    private CompositeSubscription subscriptions;
//    private Context context;
    CompositeSubscription subscriptions;
    Utility utilFunctions;
    SPUtils spUtils;
    Context context;
//    public ServiceCaller(Context context) {
//        subscriptions = new CompositeSubscription();
//    }


    public ServiceCaller(CompositeSubscription subscriptions, Utility utilFunctions, SPUtils spUtils, Context context) {
        this.subscriptions = subscriptions;
        this.utilFunctions = utilFunctions;
        this.spUtils = spUtils;
        this.context = context;
    }


    public void callService1(Observable<T> observable, @NonNull NetworkListner listner) {
        subscriptions.add(observable.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .timeout(60, TimeUnit.SECONDS).subscribe(new Subscriber<T>() {

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(T t) {

                    }
                }));
        // callService(Schedulers.newThread(), AndroidSchedulers.mainThread(), 30, listner, observable);
    }

    public void callService(Observable<T> observable, NetworkListner listner) {
        callService(Schedulers.newThread(), AndroidSchedulers.mainThread(), 30, listner, observable);
    }


    public void callService(Observable<T> observable, NetworkListner listner, long time) {
        callService(Schedulers.newThread(), AndroidSchedulers.mainThread(), time, listner, observable);
    }

    public void callService(Scheduler subscribeThread, Scheduler observeThread, long timeOut, final NetworkListner listner, Observable<T> observable) {
        if (!Utility.isInternetOn(context)) {
            listner.onError("NO_INTERNET");
            LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(context);
            Intent local = new Intent("NO_INTERNET");
            localBroadcastManager.sendBroadcast(local);
            return;
        }

        listner.onStart();

        subscriptions.add(observable.subscribeOn(subscribeThread).observeOn(observeThread)
                .timeout(timeOut, TimeUnit.SECONDS).subscribe(new Subscriber<T>() {
                    @Override
                    public void onCompleted() {
                        listner.onComplete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        if (e instanceof TimeoutException) {
                            listner.onError("Network Error");
                            listner.onComplete();
                        } else if (e instanceof retrofit2.adapter.rxjava.HttpException && ((retrofit2.adapter.rxjava.HttpException) e).code() == 403) {
                            LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(context);
                            Intent local = new Intent("SESSION_EXPIRED");
                            localBroadcastManager.sendBroadcast(local);
                        } else {
                            listner.onError(e.getMessage());
                            listner.onComplete();
                        }
                    }

                    @Override
                    public void onNext(T responseBody) {
                        if (responseBody instanceof ResultModel) {
                            listner.onSuccess((ResultModel<?>) responseBody);
                        }
                        listner.onComplete();
                    }
                }));

    }


    void callService(Scheduler subscribeThread, Scheduler observeThread, long timeOut, Observable<ResponseBody> observable) {
        if (!Utility.isInternetOn(context)) {
            return;
        }
        subscriptions.add(observable.subscribeOn(subscribeThread).observeOn(observeThread).timeout(timeOut, TimeUnit.SECONDS).subscribe(new Observer<ResponseBody>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(ResponseBody responseBody) {
            }

        }));

    }
}