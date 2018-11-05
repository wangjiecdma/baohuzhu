package com.szty.baohuzhu.webapi;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;

import com.szty.baohuzhu.BuildConfig;
import com.szty.baohuzhu.adapter.ProjectItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class WebServiceManager {

    private JSONObject  mHeader;
    private int mUserID=1;
    private String URL_BASE = "http://47.97.170.249:8888/";




    private final String URL_PROJECT_LIST = "/mutualhelp/pages";
    private final String URL_PROJECT_CREATE = "/mutualhelp/createHelp";
    private final String URL_USER_REGISTER="/user/register";
    private final String URL_GET_SMSCODE="/user/smsCode";
    private final String URL_RESET_PWD="/user/resetPwd";
    private final String URL_LOGIN="/user/login";

    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    private Handler mHandler = null;

    private WebServiceManager(Context context){

        mHandler = new Handler();
        mHeader = new JSONObject();
        String m_szAndroidID = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);


        try {
            mHeader.put("deviceNo", m_szAndroidID);
            mHeader.put("version",BuildConfig.VERSION_NAME);
            mHeader.put("versionCode",""+BuildConfig.VERSION_CODE);
            mHeader.put("ostype",1);
            mHeader.put("osversion", Build.VERSION.INCREMENTAL);
            mHeader.put("mobileModel",android.os.Build.BRAND);
            mHeader.put("uid",mUserID);
            mHeader.put("channel","AppStore");
            mHeader.put("lat","0");
            mHeader.put("lon","0");


        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    private static WebServiceManager sManager= null;

    public static WebServiceManager getInstance(){

        return sManager;
    }
    public static void  initManager(Context context){
        if (sManager == null){
            sManager = new WebServiceManager(context);
        }
    }

    public void setUserID(int id){
        mUserID = id;
        try {
            mHeader.put("uid", id);
        }catch (JSONException e){
            e.printStackTrace();
        }
    }



    public void httpPost(final JSONObject header,final String method,final HttpCallback callback){

        try {
            header.put("header", mHeader);
        }catch (JSONException e){
            e.printStackTrace();
        }
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                RequestBody body = RequestBody.create(JSON, header.toString());
                Request request = new Request.Builder()
                        .url(URL_BASE+method)
                        .post(body)
                        .build();
                Log.d("httplog","http parram :"+header.toString());
                try{
                    Response response = client.newCall(request).execute();
                    final String result = response.body().string();
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            callback.onResonse(true,result);
                            Log.d("httplog","http sucess  :"+result);

                        }
                    });

                }catch (Exception e){
                    e.printStackTrace();
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            callback.onResonse(false,"");
                            Log.d("httplog","http error  :");

                        }
                    });

                }
            }
        });
        thread.start();
    }

    public void registerUser(String mobile,String smsCode,String pwd,HttpCallback callback){
        JSONObject param = new JSONObject();
        try{
            param.put("mobile",mobile);
            param.put("smsCode",smsCode);
            param.put("pwd",pwd);
            httpPost(param,URL_USER_REGISTER,callback);
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    public void getSMSCode(String mobile,HttpCallback callback){
        JSONObject param = new JSONObject();
        try{
            param.put("mobile",mobile);
            httpPost(param,URL_GET_SMSCODE,callback);
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    public void resetPWD(String mobile,String smsCode,String pwd,HttpCallback callback){
        JSONObject param = new JSONObject();
        try{

            param.put("mobile",mobile);
            param.put("smsCode",smsCode);
            param.put("pwd",pwd);
            httpPost(param,URL_RESET_PWD,callback);
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    public void userLogin(String mobile,String pwd,HttpCallback callback){
        JSONObject param = new JSONObject();
        try{
            param.put("mobile",mobile);
            param.put("pwd",pwd);
            httpPost(param,URL_LOGIN,callback);
        }catch (JSONException e){
            e.printStackTrace();
        }
    }



    public void createProject(ProjectItem  item , HttpCallback callback){
        JSONObject param = new JSONObject();
        try{

            param.put("type",item.getType());
            param.put("title",item.getTitle());
            param.put("totalMoney",item.getTotalMoney());
            param.put("month",item.getMonth());
            param.put("interestRate",item.getInterestRate());
            param.put("needNum",item.getNeedNum());
            param.put("startTime",item.getStartTime());
            param.put("endTime",item.getEndTime());
            param.put("completeNum",item.getCompleteNum());
            param.put("bidNum",item.getBidNum());
            param.put("closeTime",item.getCloseTime());
            param.put("helpSelfMoney",item.getHelpSelfMoney());
            param.put("cashMoney",item.getCashMoney());
            param.put("notMention",item.getNotMention());

            httpPost(param,URL_PROJECT_LIST,callback);
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    public void getProjectList(int index,int pagesize ,HttpCallback callback){
        JSONObject param = new JSONObject();
        try{
            param.put("index",index);
            param.put("pagesize",pagesize);

            httpPost(param,URL_PROJECT_LIST,callback);
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    public interface HttpCallback{
        public void onResonse(boolean sucess , String body);
    }
}
