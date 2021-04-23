package com.cec.doctorapp.helper;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;


public class SPUtils {
    SharedPreferences sharedPreferences;
    public String ISLOGGEDIN = "isloggedin";


    private Gson gson;

    public SPUtils(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
        gson = new Gson();
    }
    public static SPUtils getFCMPref(Context applicationContext){
        return new SPUtils(applicationContext.getSharedPreferences("cec_fcm", Context.MODE_PRIVATE));
    }
    public static SPUtils getDocInfo(Context applicationContext){
        return new SPUtils(applicationContext.getSharedPreferences("doc_info", Context.MODE_PRIVATE));
    }


public static SPUtils getFeedToken(Context applicationContext){
        return new SPUtils(applicationContext.getSharedPreferences("fb_token",Context.MODE_PRIVATE));
}
    public static SPUtils getSharedPreference(Context applicationContext){
        return new SPUtils(applicationContext.getSharedPreferences("cec", Context.MODE_PRIVATE));
    }



    public void saveLogin(String login){
        sharedPreferences.edit().putString(ISLOGGEDIN,login).apply();
    }

    public String getLogin(){
        String data = sharedPreferences.getString(ISLOGGEDIN,"");
        if (data.isEmpty()) {
            return null;
        }
        return data;
    }
    public void clearAll() {
        sharedPreferences.edit().clear().apply();
    }

    public void put(String key, String value) {
        sharedPreferences.edit().putString(key, value).apply();;
    }

    public void put(String key, int value) {
        sharedPreferences.edit().putInt(key, value).apply();
    }

    public void put(String key, float value) {
        sharedPreferences.edit().putFloat(key, value).apply();
    }

    public void put(String key, boolean value) {
        sharedPreferences.edit().putBoolean(key, value).apply();
    }

    public String get(String key, String defaultValue)
    {
        return sharedPreferences.getString(key, defaultValue);
    }

    public int get(String key, int defaultValue)
    {
        return sharedPreferences.getInt(key, defaultValue);
    }

    public Float get(String key, Float defaultValue) {
        return sharedPreferences.getFloat(key, defaultValue);
    }

    public Boolean get(String key, Boolean defaultValue) {
        return sharedPreferences.getBoolean(key, defaultValue);
    }


    public void deleteSavedData() {
        sharedPreferences.edit().clear().apply();;
//        sharedPreferences.edit().remove(key).apply();
    }

    public void clear() {
        sharedPreferences.edit().clear().apply();;
    }

}

