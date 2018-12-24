package com.wyhzb.hbsc.activitys;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import com.wyhzb.hbsc.R;
import com.wyhzb.hbsc.adapter.UserStatus;
import com.wyhzb.hbsc.utils.PreferenceUtils;
import com.wyhzb.hbsc.webapi.WebServiceManager;
import org.json.JSONException;
import org.json.JSONObject;

public class ActivityUserRegister extends BaseActivity implements View.OnClickListener{

    private  String  mPhone;
    private  String  mPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        findViewById(R.id.registe_user).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textView = findViewById(R.id.phone_number);
                String phone = textView.getText().toString();
                textView = findViewById(R.id.smscode);
                String smscode = textView.getText().toString();
                textView = findViewById(R.id.password);
                String pwd = textView.getText().toString();
                mWebServer.resetPWD(phone, smscode, pwd, new WebServiceManager.HttpCallback() {
                    @Override
                    public void onResonse(boolean sucess, String body) {
                        Log.d("httplog","reset pwd :"+body);
                    }
                });

            }
        });

        findViewById(R.id.btn_smscode).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textView = findViewById(R.id.phone_number);
                String phone = textView.getText().toString();
                mWebServer.getSMSCode(phone, new WebServiceManager.HttpCallback() {
                    @Override
                    public void onResonse(boolean sucess, String body) {
                        if (sucess){
                            showToast("获取验证码成功");
                        }else{
                            showToast("获取验证码失败");
                        }
                    }
                });
            }
        });


        findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textView = findViewById(R.id.phone_number_login);
                String phone = textView.getText().toString();
                textView = findViewById(R.id.password_login);
                String pwd = textView.getText().toString();

                mPhone = phone;
                mPwd = pwd;

                mWebServer.userLogin(phone, pwd, new WebServiceManager.HttpCallback() {
                    @Override
                    public void onResonse(boolean sucess, String body) {
                        if (sucess){
                            showToast("登录成功");


                            Log.d("httplog"," loging result :"+body);
                            try {
                                JSONObject jsonObject = new JSONObject(body);
                                JSONObject js =  jsonObject.getJSONObject("datas").getJSONObject("user");
                                String token = jsonObject.getJSONObject("datas").getString("token");
                                UserStatus user =  UserStatus.user();
                                user.setUid(js.getInt("uid"));
                                user.setNickName(js.getString("nickName"));
                                user.setAdCode(js.getString("adCode"));
                                user.setTotalMoney(js.getString("totalMoney"));
                                user.setTotalCrash(js.getString("totalCrash"));
                                user.setCrashIng(js.getString("crashIng"));
                                user.setTotalProfit(js.getString("totalProfit"));

                                user.setBalance(js.getString("balance"));
                                user.setBankCard(js.getString("bankCard"));
                                user.setBankName(js.getString("bankName"));
                                user.setIco(js.getString("ico"));
                                user.setSex(js.getString("sex"));
                                user.setBirthday(js.getString("birthday"));
                                user.setBidMoney(js.getString("bidMoney"));
                                user.setNoRechargeBalance(js.getString("noRechargeBalance"));
                                user.setNoBidMoney(js.getString("noBidMoney"));
                                user.setMobile(js.getString("mobile"));
                                user.setLevelName(js.getString("levelName"));
                                user.setUserNo(js.getString("userNo"));
                                user.setToken(token);
                                WebServiceManager.getInstance().setToken(token);

                               checkVersioin();
                               postPushToken();
                               getNewMessageCount();

                               getMessageList();

                               //设置登陆成功
                               PreferenceUtils.setLogin(true);
                               PreferenceUtils.setUserName(mPhone);
                               PreferenceUtils.setPassword(mPwd);
                               PreferenceUtils.setAutoLogin(true);
                               //保存登陆的用户其它信息，待加

                            }catch (JSONException e){
                                e.printStackTrace();
                                Log.d("httplog","response error",e);
                            }
                            //getUserInfo();
                            finish();

                        }else{
                            showToast("登录失败");
                        }
                    }
                });
            }
        });

        switchToLogin(true);

    }

//    private void getUserInfo(){
//        WebServiceManager.getInstance().getUserInfo(new WebServiceManager.HttpCallback() {
//            @Override
//            public void onResonse(boolean sucess, String body) {
//                if (sucess){
//                    try {
//                        JSONObject jsonObject = new JSONObject(body);
//
//                        Log.d("www","get user info :"+body);
//
//                    }catch (Exception e){
//                        e.printStackTrace();
//                    }
//                }
//            }
//        });
//    }

    private void checkVersioin(){
        WebServiceManager.getInstance().checkVersion(new WebServiceManager.HttpCallback() {
            @Override
            public void onResonse(boolean sucess, String body) {
                Log.d("www","checkversioin :"+body);
            }
        });
    }

    private void postPushToken(){

        WebServiceManager.getInstance().postPushToken("android_token", new WebServiceManager.HttpCallback() {
            @Override
            public void onResonse(boolean sucess, String body) {
                Log.d("httplog","post push token :"+body);
            }
        });
    }
    private void getMessageList(){
//        WebServiceManager.getInstance().getMessageList(0, 1, new WebServiceManager.HttpCallback() {
//            @Override
//            public void onResonse(boolean sucess, String body) {
//                Log.d("httplog","get message list :"+body);
//
//            }
//        });
    }

    private void getNewMessageCount(){
//        WebServiceManager.getInstance().getNewMessageCount(new WebServiceManager.HttpCallback() {
//            @Override
//            public void onResonse(boolean sucess, String body) {
//                Log.d("http_log","addnew message :"+body);
//            }
//        });
    }
    private void createHelp(){
        WebServiceManager.getInstance().toCreateHelp(new WebServiceManager.HttpCallback() {
            @Override
            public void onResonse(boolean sucess, String body) {
                Log.d("http_log","create help  :"+body);

            }
        });
    }


    @Override
    public void onClick(View v) {

        if (v.getId()== R.id.segment_login){
            switchToLogin(true);
        }else if(v.getId()== R.id.segment_regist){
            switchToLogin(false);
        }
    }

    private void switchToLogin(boolean toLogin){
        if (toLogin){
            setTitle("登录");
            findViewById(R.id.login_panel).setVisibility(View.VISIBLE);
            findViewById(R.id.regist_panel).setVisibility(View.GONE);
            TextView textView = findViewById(R.id.segment_login);
            textView.setTextColor(Color.RED);
            textView = findViewById(R.id.segment_regist);
            textView.setTextColor(Color.GRAY);
        }else{
            setTitle("注册");
            findViewById(R.id.login_panel).setVisibility(View.GONE);
            findViewById(R.id.regist_panel).setVisibility(View.VISIBLE);
            TextView textView = findViewById(R.id.segment_login);
            textView.setTextColor(Color.GRAY);
            textView = findViewById(R.id.segment_regist);
            textView.setTextColor(Color.RED);
        }
    }
}
