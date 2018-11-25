package com.szty.baohuzhu.activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.TextView;

import com.szty.baohuzhu.R;
import com.szty.baohuzhu.fragments.FragmentBindCard;
import com.szty.baohuzhu.fragments.FragmentDeposit;
import com.szty.baohuzhu.fragments.FragmentLogList;
import com.szty.baohuzhu.fragments.FragmentMessage;
import com.szty.baohuzhu.fragments.FragmentMyInformation;
import com.szty.baohuzhu.fragments.FragmentSetting;

public class ActivityManager extends BaseActivity  {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);

        String name = getIntent().getStringExtra("name");



        final FragmentManager manager = getSupportFragmentManager();

        if(name.equals("提现")) {
            FragmentBindCard fragmentBindCard = new FragmentBindCard();
            manager.beginTransaction().add(R.id.fragment_content, fragmentBindCard).commit();

            manager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
                @Override
                public void onBackStackChanged() {

                }
            });
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
        }


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
