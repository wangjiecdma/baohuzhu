package com.szty.baohuzhu.activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.TextView;

import com.szty.baohuzhu.R;
import com.szty.baohuzhu.fragments.FragmentBindCard;
import com.szty.baohuzhu.fragments.FragmentCreateNew;
import com.szty.baohuzhu.fragments.FragmentDeposit;
import com.szty.baohuzhu.fragments.FragmentLogList;
import com.szty.baohuzhu.fragments.FragmentMessage;
import com.szty.baohuzhu.fragments.FragmentMyInformation;
import com.szty.baohuzhu.fragments.FragmentMyProjects;
import com.szty.baohuzhu.fragments.FragmentSetting;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class ActivityManager extends BaseActivity  {
    private HashMap<String,Class> mFragmentMap = new LinkedHashMap<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);

        String name = getIntent().getStringExtra("name");



        final FragmentManager manager = getSupportFragmentManager();

        mFragmentMap.put("提现",FragmentBindCard.class);
        mFragmentMap.put("充值",FragmentBindCard.class);
        mFragmentMap.put("详情记录",FragmentLogList.class);
        mFragmentMap.put("个人信息",FragmentMyInformation.class);
        mFragmentMap.put("设置",FragmentSetting.class);
        mFragmentMap.put("消息",FragmentMessage.class);
        mFragmentMap.put("我的项目",FragmentMyProjects.class);
        mFragmentMap.put("我要创建",FragmentCreateNew.class);
        //


        Class cls = mFragmentMap.get(name);
        if (cls != null){
            try {
                Fragment fragment =(Fragment) cls.newInstance();
                manager.beginTransaction().add(R.id.fragment_content, fragment).commit();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        /*
        if(name.equals("提现")) {
            FragmentBindCard fragmentBindCard = new FragmentBindCard();
            manager.beginTransaction().add(R.id.fragment_content, fragmentBindCard).commit();

        }else if(name.equals("充值")){
            FragmentDeposit deposit = new FragmentDeposit();
            manager.beginTransaction().add(R.id.fragment_content, deposit).commit();
        }else if(name.equals("详情记录")){
            FragmentLogList list = new FragmentLogList();
            manager.beginTransaction().add(R.id.fragment_content,list).commit();
        }else if(name.equals("个人信息")){
            FragmentMyInformation info = new FragmentMyInformation();
            manager.beginTransaction().add(R.id.fragment_content,info).commit();
        }else if(name.equals("设置")){
            FragmentSetting setting = new FragmentSetting();
            manager.beginTransaction().add(R.id.fragment_content,setting).commit();
        }else if(name.equals("消息")){
            FragmentMessage message = new FragmentMessage();
            manager.beginTransaction().add(R.id.fragment_content,message).commit();
        }*/





        findViewById(R.id.back_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(title);
        TextView textView =  findViewById(R.id.title);
        textView.setText(title);
    }

    public static void startFragment(Context context,String name){
        Intent intent = new Intent(context,ActivityManager.class);
        intent.putExtra("name",name);
        context.startActivity(intent);
    }



}
