package com.cec.doctorapp.network;

import android.content.Context;


import com.cec.doctorapp.helper.SPUtils;
import com.cec.doctorapp.helper.Utility;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.subscriptions.CompositeSubscription;

@Module
public class NetworkModule {
    @Provides
    @Singleton
    OkHttpClient getClient(Interceptor interceptor) {
        HttpLoggingInterceptor logger = new HttpLoggingInterceptor();
        logger.setLevel(HttpLoggingInterceptor.Level.BODY);
        return new OkHttpClient().newBuilder()
                .addInterceptor(interceptor)
                .addInterceptor(logger)
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build();
    }

    @Provides
    @Singleton
    Interceptor getInterceptor(final SPUtils spUtils) {


        return new Interceptor() {

            @Override
            public Response intercept(Chain chain) throws IOException {
                String token = "";
//                LoginResponseModel userResponseModel = spUtils.getUserData();
//                if (userResponseModel != null) {
//                    //token = (userResponseModel.getToken() != null) ? userResponseModel.getToken() : "";
//
//                }
                Request original = chain.request();
                Request.Builder requestBuilder = original.newBuilder()
                        .addHeader("Content-Type", "application/json")
                        .method(original.method(), original.body());
                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        };
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(Retrofit.Builder builder) {
//        builder.addConverterFactory(MoshiConverterFactory.create().asLenient());
        return builder.build();
    }

    @Provides
    @Singleton
   DoctorConfig provideAllurl(SPUtils spUtils) {
        return new DoctorConfig(spUtils);
    }

    @Provides
    @Singleton
    Retrofit.Builder provideRetrofitBuilder(DoctorConfig config, OkHttpClient okHttpClient) {

        return new Retrofit.Builder()
                .baseUrl(config.getBaseUrl())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient);
    }

    @Provides
    @Singleton
    Apis provideApis(Retrofit retrofit) {
        return retrofit.create(Apis.class);
    }

    @Provides
    @Singleton
    ServiceCaller getServiceCaller(Utility utilFunctions, SPUtils spUtils, Context context) {
        CompositeSubscription subscriptions = new CompositeSubscription();
        return new ServiceCaller(subscriptions, utilFunctions, spUtils, context);
    }
}
