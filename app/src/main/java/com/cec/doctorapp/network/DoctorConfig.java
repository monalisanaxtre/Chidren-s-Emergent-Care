package com.cec.doctorapp.network;


import com.cec.doctorapp.helper.SPUtils;

import javax.inject.Inject;

public class DoctorConfig {

    public static String BASE_URL = null;
    private static String IMAGE_URL = null;
    public static Environment environment = Environment.DEV;

    @Inject
    public DoctorConfig(SPUtils spUtils) {
        setUrls();
    }

    String getBaseUrl() {
        setUrls();
        return BASE_URL;
    }

    public String getImageUrl() {
        setUrls();
        return IMAGE_URL;
    }


    void setUrls() {
        switch (environment) {
            case DEV: {
//                BASE_URL = "http://182.77.57.62:4202/api/";
                BASE_URL = "https://cec-nodejs.herokuapp.com/api/";
                IMAGE_URL = "http://developtech.co.in/mj/dashboard/";
                break;
            }
            case PRO: {
                BASE_URL = "https://cec-nodejs.herokuapp.com/api/";
                IMAGE_URL = "https://shggroups.com/dashboard/";
                break;
            }
        }
    }

    public enum Environment {
        DEV, PRO
    }
    public static String FACEBOOK_BASE_URL = "https://graph.facebook.com";
}

