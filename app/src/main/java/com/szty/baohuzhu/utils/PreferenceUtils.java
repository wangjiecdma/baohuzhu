package com.szty.baohuzhu.utils;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PreferenceUtils {

    public static SharedPreferences sharedPreferences;
    private static Editor editor;

    public static void init(Context context) {
        sharedPreferences = context.getSharedPreferences("hbsc-hzb", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

    }

    public static void setVersion(String version) {
        editor.putString("version", version);
        editor.commit();
    }

    public static String getVersion() {
        if (sharedPreferences == null) {
            return "";
        }
        return sharedPreferences.getString("version", "");
    }

    /**
     * 记录设备id
     */
    public static void setDeviceId(String device) {
        editor.putString("deviceId", device);
        editor.commit();
    }

    public static String getDeviceId() {
        if (sharedPreferences == null) {
            return "";
        }
        return sharedPreferences.getString("deviceId", "");
    }

    public static void setUserName(String userName) {
        editor.putString("username", userName == null ? "" : userName);
        editor.commit();
    }

    public static String getUserName() {
        if (sharedPreferences == null) {
            return "";
        }
        return sharedPreferences.getString("username", "");
    }

    public static void setPassword(String password) {
        editor.putString("password", password == null ? "" : password);
        editor.commit();
    }

    public static void setTimestampType(int type,int value){
        editor.putInt("timestamp_"+type,value);
        editor.commit();
    }
    public static int getTimestampType(int type){
       return  sharedPreferences.getInt("timestamp_"+type,0);
    }


    public static String getpassword() {
        if (sharedPreferences == null) {
            return "";
        }
        return sharedPreferences.getString("password", "");
    }

    public static void setLogin(boolean bool) {
        editor.putBoolean("islogon", bool);
        editor.commit();
    }

    public static boolean isLogin() {
        if (sharedPreferences == null) {
            return false;
        }
        return sharedPreferences.getBoolean("islogon", false);
    }

    public static boolean isAutoLogin() {
        if (sharedPreferences == null) {
            return false;
        }
        return sharedPreferences.getBoolean("isAutologon", false);
    }

    public static void setAutoLogin(boolean isAuto) {
        editor.putBoolean("isAutologon", isAuto);
        editor.commit();
    }



}
