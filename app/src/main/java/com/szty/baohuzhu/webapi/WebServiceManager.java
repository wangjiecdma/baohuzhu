package com.szty.baohuzhu.webapi;

import android.content.Context;
import android.os.Build;
import android.provider.Settings;

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
    private String mUserID="";
    private String URL_BASE = "http://47.97.170.249:8888/";


    private final String URL_PROJECT_LIST = "/mutualhelp/pages";
    private final String URL_PROJECT_CREATE = "/mutualhelp/createHelp";

    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    private WebServiceManager(Context context){

        mHeader = new JSONObject();
        String m_szAndroidID = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);


        try {
            mHeader.put("deviceNo", m_szAndroidID);
            mHeader.put("version",BuildConfig.VERSION_NAME);
            mHeader.put("versionCode",BuildConfig.VERSION_CODE);
            mHeader.put("ostype",1);
            mHeader.put("osversion", Build.VERSION.INCREMENTAL);
            mHeader.put("mobileModel",android.os.Build.BRAND);
            mHeader.put("uid",mUserID);

        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    public static WebServiceManager sManager= null;

    public static WebServiceManager getInstance(){

        return sManager;
    }
    public static void  initManager(Context context){
        if (sManager == null){
            sManager = new WebServiceManager(context);
        }
    }

    public void setUserID(String id){
        mUserID = id;
        try {
            mHeader.put("uid", id);
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    public void httpPost(final JSONObject header,final String method,final HttpCallback callback){

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                RequestBody body = RequestBody.create(JSON, header.toString());
                Request request = new Request.Builder()
                        .url(URL_BASE+method)
                        .post(body)
                        .build();
                try{
                    Response response = client.newCall(request).execute();
                    String result = response.body().string();
                    callback.onResonse(true,result);
                }catch (Exception e){
                    e.printStackTrace();
                    callback.onResonse(false,"");
                }
            }
        });
        thread.start();


    }

    public void createProject(ProjectItem  item , HttpCallback callback){
        JSONObject custom = new JSONObject();
        try{
            custom.put("header",mHeader);

            custom.put("type",item.getType());
            custom.put("title",item.getTitle());
            custom.put("totalMoney",item.getTotalMoney());
            custom.put("month",item.getMonth());
            custom.put("interestRate",item.getInterestRate());
            custom.put("needNum",item.getNeedNum());
            custom.put("startTime",item.getStartTime());
            custom.put("endTime",item.getEndTime());
            custom.put("completeNum",item.getCompleteNum());
            custom.put("bidNum",item.getBidNum());
            custom.put("closeTime",item.getCloseTime());
            custom.put("helpSelfMoney",item.getHelpSelfMoney());
            custom.put("cashMoney",item.getCashMoney());
            custom.put("notMention",item.getNotMention());

            httpPost(custom,URL_PROJECT_LIST,callback);
        }catch (JSONException e){
            e.printStackTrace();
        }    }

    public void getProjectList(int index,int pagesize ,HttpCallback callback){
        JSONObject custom = new JSONObject();
        try{
            custom.put("header",mHeader);
            custom.put("index",index);
            custom.put("pagesize",pagesize);

            httpPost(custom,URL_PROJECT_LIST,callback);
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    public interface HttpCallback{
        public void onResonse(boolean sucess , String body);
    }
}
