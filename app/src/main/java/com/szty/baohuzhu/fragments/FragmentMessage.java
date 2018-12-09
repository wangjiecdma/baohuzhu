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
import com.szty.baohuzhu.webapi.WebServiceManager;

public class FragmentMessage extends FragmentBase implements View.OnClickListener {

    public FragmentMessage(){
        super();
        title = "消息";
        layoutId = R.layout.notice;
    }


    @Override
    protected void onInitData() {
        WebServiceManager.getInstance().getMessageList(0, 0, new WebServiceManager.HttpCallback() {
            @Override
            public void onResonse(boolean sucess, String body) {
                Log.d("www","message list :"+body);
                if (sucess){

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
