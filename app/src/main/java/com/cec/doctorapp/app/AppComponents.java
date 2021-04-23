package com.cec.doctorapp.app;
import com.cec.doctorapp.helper.Constants;
import com.cec.doctorapp.helper.SPUtils;
import com.cec.doctorapp.helper.Utility;
import com.cec.doctorapp.network.Apis;
import com.cec.doctorapp.network.DoctorConfig;
import com.cec.doctorapp.network.NetworkModule;
import com.cec.doctorapp.network.ServiceCaller;
import com.cec.doctorapp.ui.activities.BaseActivity;


import javax.inject.Singleton;

import dagger.Component;
import retrofit2.Retrofit;


@Singleton
@Component(modules = {AppModule.class, NetworkModule.class})
public
interface AppComponents {

    void inject(AppController application);

    void inject(BaseActivity application);

    Apis getApis();

    Retrofit getRetrofit();

    DoctorConfig config();

    Utility utils();

    SPUtils spUtils();

    ServiceCaller getServiceCaller();

    Constants constants();
}


