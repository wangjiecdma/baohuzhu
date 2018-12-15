package com.szty.baohuzhu.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.szty.baohuzhu.R;
import com.szty.baohuzhu.activitys.ActivityManager;
import com.szty.baohuzhu.adapter.NewMessageCountsInfo;
import com.szty.baohuzhu.webapi.WebServiceManager;

import org.json.JSONObject;

public class FragmentMessage extends FragmentBase implements View.OnClickListener {

    public FragmentMessage(){
        super();
        title = "消息";
        layoutId = R.layout.notice;
    }


    @Override
    protected void onInitData() {
        //timestamp 需要实际的上次获取到的最新的那条消息的timeStamp
        int timstamp = 0; //
        WebServiceManager.getInstance().getNewMessageCount(0, 0, timstamp, new WebServiceManager.HttpCallback() {
            @Override
            public void onResonse(boolean sucess, String body) {
                Log.d("www","message list :"+body);
                if (sucess){
                    try{
                        JSONObject jsonObject = new JSONObject(body);
                        JSONObject js =jsonObject.getJSONObject("datas").getJSONObject("cs");
                        NewMessageCountsInfo newMessageCountsInfo = new NewMessageCountsInfo();
                        newMessageCountsInfo.initFromJson(js);
                        String str = String.format("new message total counts: %d, sys message count:%d", newMessageCountsInfo.getCount(), newMessageCountsInfo.getSysMsgCount());
                        Log.d("getNewMessageCount", str);
                    }catch (Exception e){
                        e.printStackTrace();
                    }


                }
            }
        });

        findViewById(R.id.help_enter).setOnClickListener(this);
        findViewById(R.id.mall_enter).setOnClickListener(this);
        findViewById(R.id.notice_enter).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        ActivityManager.startFragment(getContext(),"账号消息");

    }
}
