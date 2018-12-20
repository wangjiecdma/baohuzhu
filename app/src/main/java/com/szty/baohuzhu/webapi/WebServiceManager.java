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

import java.util.HashMap;

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
    private final String URL_USER_REGISTER="/user/register";
    private final String URL_GET_SMSCODE="/user/smsCode";
    private final String URL_RESET_PWD="/user/resetPwd";
    private final String URL_LOGIN="/user/login";
    private final String URL_LOGOUT="/user/logout";
    private final String URL_CHECK_VERSION="/appversioninfo/checkVersion";
    private final String URL_POST_PUSH_TOKEN="/token/bind";
    private final String URL_NEW_MESSAGE="/usermsg/getNewMsgCount";
    private final String URL_MESSAGE_LIST="/usermsg/getNewMsgs";

    private final String URL_SAVE_USER_ACCOUNT="/useraccount/saveUserAccount";

    private final String URL_TO_CREATE_HELP ="/mutualhelp/toCreateHelp";
    private final String URL_CREATE_HELP ="/mutualhelp/createHelp";
    private final String URL_MY_PAGES  = "/mutualhelp/myPages";  //我建立的项目
    private final String URL_MY_PAGES_2 ="/mutualhelp/myMutuals";//我参与的项目
    private final String URL_HELP_DETAIL ="/mutualhelp/desc"; //项目详情
    private final String URL_BIDHELP="/mutualhelpdesc/bidHelp";//竞标/授权
    private final String URL_GET_LOG ="/useroperationlog/getLog" ;//账户记录
    private final String URL_CONTINUE ="/mutualhelp/getContinue";//获取续标详情
    private final String URL_SAVE_CONTINUE="/mutualhelp/saveContinue";// 续标
    private final String URL_SUCESS_LIST="/mutualhelp/successMutuals";//成功案例列表
    private final String URL_WILL_BACK_LIST="/mutualhelp/willBackMutuals";//待还款列表
    private final String URL_WILL_BACK_DETAIL ="/mutualhelp/descBackMutual";//代还款详情
    private final String URL_BACK_BY_BALANCE="/mutualhelp/backByBalance";//余额还款
    private String       mToken="";

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

    public void setToken(String token){
        mToken = token;
    }


    public void httpPost(final JSONObject header,final String method,final HttpCallback callback){

        try {
            header.put("header", mHeader);
        }catch (JSONException e){
            Log.d("httplog","httpPost error",e);
        }
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                RequestBody body = RequestBody.create(JSON, header.toString());
                Request request = new Request.Builder()
                        .url(URL_BASE+method)
                        .post(body)
                        .addHeader("Mutual-Token",mToken)
                        .build();

                Log.d("httplog","http parram :"+header.toString());
                try{
                    Response response = client.newCall(request).execute();
                    final String result = response.body().string();
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            callback.onResonse(true,result);
                            Log.d("httplog","http "+method +" sucess ");
                            Log.d("httplog","http sucess  :"+result);
                        }
                    });

                }catch (Exception e){
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            callback.onResonse(false,"");
                        }
                    });
                    Log.d("httplog","http "+method +" faild ");
                    Log.d("httplog","http error  :",e);

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

    public void userLogout(HttpCallback callback){
        JSONObject param = new JSONObject();
        try{
            httpPost(param,URL_LOGOUT,callback);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void getNewMessageCount(int lastAccountId, int lastMutualId, int timeStamp,  HttpCallback callback){

        JSONObject param = new JSONObject();
        try{
            param.put("mutualLastId",lastMutualId);
            param.put("accountLastId",lastAccountId);
            param.put("time",timeStamp);
            httpPost(param,URL_NEW_MESSAGE,callback);
        }catch (JSONException e){
            e.printStackTrace();
        }


    }

    public void getMessageListByType(int lastID,int type, int timeStamp, HttpCallback callback){
        JSONObject param = new JSONObject();
        try{
            param.put("lastId",lastID);
            param.put("type",type);
            param.put("time",timeStamp);
            httpPost(param,URL_MESSAGE_LIST,callback);
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


    public void checkVersion(HttpCallback callback){
        JSONObject param = new JSONObject();
        try{
            httpPost(param,URL_CHECK_VERSION,callback);
        }catch (Exception e){
            Log.d("httplog","checkversion error",e);
        }
    }

    public void postPushToken(String token , HttpCallback callback){

        JSONObject param = new JSONObject();
        try{
            param.put("token",token);
            httpPost(param,URL_POST_PUSH_TOKEN,callback);
        }catch (Exception e){
            Log.d("httplog","checkversion error",e);
        }
    }

    public void toCreateHelp(HttpCallback callback){
        JSONObject param = new JSONObject();
        try{
            httpPost(param,URL_TO_CREATE_HELP,callback);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void myHelpList(int index,int pagesize,HttpCallback callback){
        JSONObject param = new JSONObject();
        try{
            param.put("index",index);
            param.put("pagesize",pagesize);
            httpPost(param,URL_MY_PAGES,callback);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void myHelpList2(int index,int pagesize,HttpCallback callback){
        JSONObject param = new JSONObject();
        try{
            param.put("index",index);
            param.put("pagesize",pagesize);
            httpPost(param,URL_MY_PAGES_2,callback);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void getHelpDetail(int type ,int mid, HttpCallback callback){
        JSONObject param = new JSONObject();
        try{
            param.put("type",type);
            param.put("mid",mid);
            httpPost(param,URL_HELP_DETAIL,callback);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void getContinueDetail(int type ,int mid, HttpCallback callback){
        JSONObject param = new JSONObject();
        try{
            param.put("type",type);
            param.put("mid",mid);
            httpPost(param,URL_CONTINUE,callback);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void bidHelp(int type ,int mid, String cashMoney , String notMention, HttpCallback callback){
        JSONObject param = new JSONObject();
        try{
            param.put("type",type);
            param.put("mid",mid);
            param.put("cashMoney",cashMoney);
            param.put("notMention",notMention);

            httpPost(param,URL_BIDHELP,callback);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void getLog(int index ,int pagesize,HttpCallback callback){
        JSONObject param = new JSONObject();
        try{
            param.put("index",index);
            param.put("pagesize",pagesize);
            httpPost(param,URL_GET_LOG,callback);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void getSucessList(int index ,int pagesize,HttpCallback callback){
        JSONObject param = new JSONObject();
        try{
            param.put("index",index);
            param.put("pagesize",pagesize);
            httpPost(param,URL_SUCESS_LIST,callback);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void getWillBackList(int index ,int pagesize,HttpCallback callback){
        JSONObject param = new JSONObject();
        try{
            param.put("index",index);
            param.put("pagesize",pagesize);
            httpPost(param,URL_WILL_BACK_LIST,callback);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void saveContinue(int type ,int mid, int month , String rate,HttpCallback callback){
        JSONObject param = new JSONObject();
        try{
            param.put("type",type);
            param.put("mid",mid);
            param.put("month",month);
            param.put("rate",rate);
            httpPost(param,URL_SAVE_CONTINUE,callback);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void getWillBackDetail(int mid ,HttpCallback callback){
        JSONObject param = new JSONObject();
        try{
            param.put("mid",mid);
            httpPost(param,URL_WILL_BACK_DETAIL,callback);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void backByBalance(int mid,float balance,float notMention ,HttpCallback callback){
        JSONObject param = new JSONObject();
        try{
            param.put("mid",mid);
            param.put("balance",balance);
            param.put("notMention",notMention);

            httpPost(param,URL_BACK_BY_BALANCE,callback);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void upload(String fileName , HttpCallback callback){
        JSONObject param = new JSONObject();
        try{
            httpPost(param,"/file/upload",callback);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void backByBank(String mid,String picPath,HttpCallback callback){
        JSONObject param = new JSONObject();
        try{
            param.put("mid",mid);
            param.put("picPath",picPath);
            httpPost(param,"/mutualhelp/backByBank",callback);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void toWishCrash(HttpCallback callback){
        JSONObject param = new JSONObject();
        try{
            httpPost(param,"/withdrawcashinfo/toWishCrash",callback);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void getUserInfo(HttpCallback callback){
        JSONObject param = new JSONObject();
        try{
            httpPost(param,"/user/userInfo",callback);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void updateUserInfo( String ico,String sex,String birthday , String nickName, HttpCallback callback){
        JSONObject param = new JSONObject();
        try{

            param.put("ico",ico);
            param.put("sex",sex);
            param.put("birthday",birthday);
            param.put("nickName",nickName);

            httpPost(param,"/user/updateUserInfo",callback);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void payOk(String orderNo ,int payType, HttpCallback callback){
        JSONObject param = new JSONObject();
        try{
            param.put("payType",payType);
            param.put("orderNo",orderNo);
            httpPost(param,"/rechargeinfo/payOk",callback);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void getPreWxPayOrder(String channel ,int payType,int totalFee,String spbillCreateIp,String body,String tradeType, HttpCallback callback){
        JSONObject param = new JSONObject();
        try{
            param.put("channel",channel);
            param.put("payType",payType);
            param.put("totalFee",totalFee);
            param.put("spbillCreateIp",spbillCreateIp);
            param.put("body",body);
            param.put("tradeType",tradeType);


            httpPost(param,"/rechargeinfo/getPreWxPayOrder",callback);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void wishCrash(String mobile , int  smsCode , int crash,HttpCallback callback){
        JSONObject param = new JSONObject();
        try{
            param.put("mobile",mobile);
            param.put("smsCode",smsCode);
            param.put("crash",crash);
            httpPost(param,"/withdrawcashinfo/wishCrash",callback);
        }catch (Exception e){
            e.printStackTrace();
        }
    }



    public void createHelp( int  type,String title , int totalMoney,int month,int needNum,String startTime,String endTime ,String closeTime,
                            String helpSelfMoney, int cashMoney , int notMention,HttpCallback callback){
        JSONObject param = new JSONObject();
        try{
            param.put("type",type);
            param.put("title",title);
            param.put("totalMoney",totalMoney);
            param.put("month",month);
            param.put("needNum",needNum);
            param.put("startTime",startTime);
            param.put("endTime",endTime);
            param.put("closeTime",closeTime);
            param.put("helpSelfMoney",helpSelfMoney);
            param.put("cashMoney",cashMoney);
            param.put("notMention",notMention);
            httpPost(param,URL_CREATE_HELP,callback);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void createHelp(HashMap mapParam, HttpCallback callback){
        JSONObject param = new JSONObject(mapParam);
        try{
//            param.put("type",type);
//            param.put("title",title);
//            param.put("totalMoney",totalMoney);
//            param.put("month",month);
//            param.put("needNum",needNum);
//            param.put("startTime",startTime);
//            param.put("endTime",endTime);
//            param.put("closeTime",closeTime);
//            param.put("helpSelfMoney",helpSelfMoney);
//            param.put("cashMoney",cashMoney);
//            param.put("notMention",notMention);
            httpPost(param,URL_CREATE_HELP,callback);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void saveUserAccount(String mobile,String smscode,String bankCard,String bankName,String realName,String userCard,HttpCallback callback){
        JSONObject param = new JSONObject();
        try{
            param.put("mobile",mobile);
            param.put("smsCode",smscode);
            param.put("bankCard",bankCard);
            param.put("bankName",bankName);
            param.put("realName",realName);
            param.put("userCard",userCard);
            httpPost(param,URL_SAVE_USER_ACCOUNT,callback);

        }catch (JSONException e){
            Log.d("httplog","error",e);
        }
    }

    public interface HttpCallback{
        public void onResonse(boolean sucess , String body);
    }
}
