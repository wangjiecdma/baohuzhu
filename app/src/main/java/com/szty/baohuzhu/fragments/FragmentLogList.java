package com.szty.baohuzhu.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.szty.baohuzhu.R;
import com.szty.baohuzhu.activitys.ActivityManager;
import com.szty.baohuzhu.adapter.AccountLog;
import com.szty.baohuzhu.webapi.WebServiceManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class FragmentLogList extends FragmentBase {

    private ListView mListView ;

    public FragmentLogList(){
        super();
        title = "账户详情";
        layoutId = R.layout.myrecords_detail;
    }


    @Override
    protected void onInitData() {
        WebServiceManager.getInstance().getLog(0, 10, new WebServiceManager.HttpCallback() {
            @Override
            public void onResonse(boolean sucess, String body) {
                Log.d("www","get log :"+body);
                if (sucess){

                    try{
                        JSONObject object = new JSONObject(body);
                        //page 中的下面的参数 用作下拉 上拉刷新用
                        //{"page":{"index":0,"size":10,"totalpage":1,"nowpage":1,"totalcount":11,"datas":[{"mark":"万达 标的金币入账","time":"2018-12-11 11:12:36","type":1,"money":"5000.00","moneyType":2}]}}}
                        JSONObject  jsPage = object.getJSONObject("datas").getJSONObject("page");
                        JSONArray jsAccountLogArray = jsPage.getJSONArray("datas");

                        ArrayList<AccountLog> accountLogsList = new ArrayList<AccountLog>();
                        if(jsAccountLogArray != null) {
                            for (int i = 0; i < jsAccountLogArray.length(); i ++){
                                JSONObject oj = jsAccountLogArray.getJSONObject(i);
                                AccountLog accountLog =  new AccountLog();
                                accountLog.initFromJson(oj);
                                accountLogsList.add(accountLog);
                            }
                            String str = accountLogsList.toString();
                            Log.d("accountLogsList:", str);
                        }

                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }
            }
        });
    }



}
