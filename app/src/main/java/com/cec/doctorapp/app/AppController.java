package com.cec.doctorapp.app;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkRequest;
import android.util.Log;
import com.cec.doctorapp.helper.NetworkState;
import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.multidex.MultiDexApplication;


import com.cec.doctorapp.helper.Constants;
import com.cec.doctorapp.network.NetworkModule;
import com.cec.doctorapp.utility.NotificationManager;
import com.downloader.PRDownloader;


public class AppController extends MultiDexApplication {
    private AppComponents graph;
    public static final String TAG = AppController.class.getSimpleName();
    public AppComponents getGraph() {
        return graph;
    }
    private static AppController mInstance;
    @Override
    public void onCreate() {
        super.onCreate();
        initDagger();
        PRDownloader.initialize(getApplicationContext());
        NotificationManager.Companion.initializeDefaults(this);
        this.initNetworkMonitor();
    }
    public static synchronized AppController getInstance() {
        return mInstance;
    }
    void initDagger() {
        graph = DaggerAppComponents.builder().appModule(new AppModule(this)).networkModule(new NetworkModule()).build();
        graph.inject(this);
    }
    void initNetworkMonitor(){
        ConnectivityManager.NetworkCallback callback = new ConnectivityManager.NetworkCallback() {
            @Override
            public void onAvailable(@NonNull Network network) {
                super.onAvailable(network);
                Log.d("CEC_MONITOR", "onAvailable");
                Intent intent = new Intent(Constants.NETWORK_STATE_CHANGE);
                intent.putExtra(Constants.NETWORK_STATE_CHANGE, NetworkState.AVAILABLE);
                LocalBroadcastManager.getInstance(AppController.this).sendBroadcast(intent);
            }

            @Override
            public void onLosing(@NonNull Network network, int maxMsToLive) {
                super.onLosing(network, maxMsToLive);
                Log.d("CEC_MONITOR", "onLosing");
                Intent intent = new Intent(Constants.NETWORK_STATE_CHANGE);
                intent.putExtra(Constants.NETWORK_STATE_CHANGE, NetworkState.LOSING);
                LocalBroadcastManager.getInstance(AppController.this).sendBroadcast(intent);
            }

            @Override
            public void onLost(@NonNull Network network) {
                super.onLost(network);
                Log.d("UE_MONITOR", "onLost");
                Intent intent = new Intent(Constants.NETWORK_STATE_CHANGE);
                intent.putExtra(Constants.NETWORK_STATE_CHANGE, NetworkState.LOST);
                LocalBroadcastManager.getInstance(AppController.this).sendBroadcast(intent);
            }

            @Override
            public void onUnavailable() {
                super.onUnavailable();
                Log.d("UE_MONITOR", "onUnavailable");
                Intent intent = new Intent(Constants.NETWORK_STATE_CHANGE);
                intent.putExtra(Constants.NETWORK_STATE_CHANGE, NetworkState.UNAVAILABLE);
                LocalBroadcastManager.getInstance(AppController.this).sendBroadcast(intent);
            }
        };
        ConnectivityManager manager=((ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE));
        manager.registerNetworkCallback(new NetworkRequest.Builder().build(), callback);
    }
}


