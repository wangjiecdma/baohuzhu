package com.szty.baohuzhu.fragments;

import android.content.Intent;
import android.view.View;

import com.szty.baohuzhu.R;
import com.szty.baohuzhu.activitys.ActivityManager;
import com.szty.baohuzhu.activitys.ActivityMyMainBoard;
import com.szty.baohuzhu.activitys.ActivityUserRegister;

public class FragmentMyMain extends FragmentBase {

    public FragmentMyMain(){

        super();
        layoutId = R.layout.my_main_activity;
        title = "我的";
    }


    @Override
    protected void onInitData() {

        findViewById(R.id.login_regist).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),ActivityUserRegister.class);

                startActivityForResult(intent,100);
            }
        });

        findViewById(R.id.pop_cash).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                ActivityManager.startFragment(getContext(),"提现");

            }
        });
        findViewById(R.id.push_cash).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityManager.startFragment(getContext(),"充值");

            }
        });
        findViewById(R.id.my_project_list).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityManager.startFragment(getContext(),"我的项目");
            }
        });
        findViewById(R.id.mylog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityManager.startFragment(getContext(),"详情记录");
            }
        });
        findViewById(R.id.my_information).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityManager.startFragment(getContext(),"个人信息");
            }
        });
        findViewById(R.id.setting).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityManager.startFragment(getContext(),"设置");
            }
        });
        findViewById(R.id.message).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityManager.startFragment(getContext(),"消息");
            }
        });
    }
}
