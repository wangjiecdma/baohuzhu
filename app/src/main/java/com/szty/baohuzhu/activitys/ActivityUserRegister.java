package com.szty.baohuzhu.activitys;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.szty.baohuzhu.R;
import com.szty.baohuzhu.adapter.UserStatus;
import com.szty.baohuzhu.webapi.WebServiceManager;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class ActivityUserRegister extends BaseActivity implements View.OnClickListener{

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

                mWebServer.registerUser(phone, smscode, pwd, new WebServiceManager.HttpCallback() {
                    @Override
                    public void onResonse(boolean sucess, String body) {
                        if (sucess){
                            showToast("注册成功");
                        }else{
                            showToast("注册失败");
                        }
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

                mWebServer.userLogin(phone, pwd, new WebServiceManager.HttpCallback() {
                    @Override
                    public void onResonse(boolean sucess, String body) {
                        if (sucess){
                            showToast("登录成功");


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
                                user.setCrashIng(js.getString("carshIng"));
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

                            }catch (JSONException e){
                                e.printStackTrace();
                            }

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
