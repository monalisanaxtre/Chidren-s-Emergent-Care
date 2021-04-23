package com.cec.doctorapp.app;

import android.content.Context;
import android.content.SharedPreferences;

import com.cec.doctorapp.helper.Constants;
import com.cec.doctorapp.helper.SPUtils;
import com.cec.doctorapp.helper.Utility;
import com.cec.doctorapp.network.DoctorConfig;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;


@Module
public class AppModule {

    private AppController application;

    public AppModule(AppController myApplication) {
        application = myApplication;
    }


    @Provides
    @Singleton
    Context provideContext() {
        return application;
    }


    @Provides
    @Singleton
    SharedPreferences getSharedPref(Context context) {
        return context.getSharedPreferences("cec", Context.MODE_PRIVATE);
    }


    @Provides
    @Singleton
    SPUtils provideSPUtils(SharedPreferences sharedPreferences) {
        return new SPUtils(sharedPreferences);
    }

    @Provides
    @Singleton
    Utility provideUtilFunctions(Context context, DoctorConfig allUrls) {
        return new Utility(context, allUrls);
    }

    @Provides
    @Singleton
    Constants provideConstants() {
        return new Constants();
    }


}
