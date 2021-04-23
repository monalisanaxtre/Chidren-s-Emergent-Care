package com.cec.doctorapp.utility;

import com.cec.doctorapp.network.Apis;
import com.cec.doctorapp.network.URL;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkClient {
    private static final String BASE_URL = "http://182.77.57.62:4202";
     private  static  OkHttpClient.Builder httpClient;

    private static Retrofit retrofit;

    private static Apis facebookGraphAPI;
    /*
    This public static method will return Retrofit client
    anywhere in the appplication
    */

    public static Retrofit getRetrofitClient() {
         return getRetrofitClient(null);

    }

    public static Retrofit getRetrofitClient(String baseUrl) {
        String newBaseUrl = BASE_URL;
        if(baseUrl != null && baseUrl.length()>0){
            newBaseUrl = baseUrl;
        }
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        sethttpclient();
        //If condition to ensure we don't create multiple retrofit instances in a single application

            //Defining the Retrofit using Builder

            retrofit = new Retrofit.Builder()
                    .baseUrl(newBaseUrl).client(httpClient.build()) //This is the only mandatory call on Builder object.
                    .addConverterFactory(GsonConverterFactory.create(gson)) // Convertor library used to convert response into POJO
                    .build();
        return retrofit;
    }
    public static void sethttpclient(){
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
         httpClient = new OkHttpClient.Builder();
        //   httpClient = new OkHttpClient.Builder()
        httpClient.connectTimeout(220, TimeUnit.SECONDS).writeTimeout(220, TimeUnit.SECONDS).readTimeout(230, TimeUnit.SECONDS);
        httpClient.addInterceptor(logging);
    }
    public static Apis getFacebookGraphAPI() {
        if (facebookGraphAPI == null)
            facebookGraphAPI = retrofit.newBuilder()
                    .baseUrl(URL.FACEBOOK_BASE_URL)
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build()
                    .create(Apis
                            .class);
        return facebookGraphAPI;
    }

}
