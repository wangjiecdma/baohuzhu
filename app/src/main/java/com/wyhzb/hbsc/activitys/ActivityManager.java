package com.wyhzb.hbsc.activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.wyhzb.hbsc.R;
import com.wyhzb.hbsc.fragments.FragmentAccountDetail;
import com.wyhzb.hbsc.fragments.FragmentAuth;
import com.wyhzb.hbsc.fragments.FragmentBid;
import com.wyhzb.hbsc.fragments.FragmentBindCard;
import com.wyhzb.hbsc.fragments.FragmentCreateConfirm;
import com.wyhzb.hbsc.fragments.FragmentCreateNew;
import com.wyhzb.hbsc.fragments.FragmentEditNickName;
import com.wyhzb.hbsc.fragments.FragmentMessage;
import com.wyhzb.hbsc.fragments.FragmentMessageList;
import com.wyhzb.hbsc.fragments.FragmentMyInformation;
import com.wyhzb.hbsc.fragments.FragmentMyProjects;
import com.wyhzb.hbsc.fragments.FragmentProjectDetail;
import com.wyhzb.hbsc.fragments.FragmentReturnCach;
import com.wyhzb.hbsc.fragments.FragmentSetNewPwd;
import com.wyhzb.hbsc.fragments.FragmentSetting;
import com.wyhzb.hbsc.fragments.FragmentContinueProject;
import com.wyhzb.hbsc.fragments.FragmentWYReturn;

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
        mFragmentMap.put("提现绑卡",FragmentBindCard.class);

        //mFragmentMap.put("详情记录",FragmentLogList.class);
        mFragmentMap.put("个人信息",FragmentMyInformation.class);
        mFragmentMap.put("设置",FragmentSetting.class);
        mFragmentMap.put("消息",FragmentMessage.class);
        mFragmentMap.put("我的项目",FragmentMyProjects.class);
        mFragmentMap.put("我要创建",FragmentCreateNew.class);
        mFragmentMap.put("创建项目",FragmentCreateConfirm.class);
        mFragmentMap.put("我要授权",FragmentAuth.class);
        mFragmentMap.put("我要竞标",FragmentBid.class);
        mFragmentMap.put("我要续标",FragmentContinueProject.class);
        mFragmentMap.put("我要归还",FragmentWYReturn.class);


        mFragmentMap.put("账号消息",FragmentMessageList.class);
        mFragmentMap.put("订单消息",FragmentMessageList.class);
        mFragmentMap.put("互助消息",FragmentMessageList.class);
        mFragmentMap.put("系统消息",FragmentMessageList.class);

        mFragmentMap.put("提现管理",FragmentReturnCach.class);
        mFragmentMap.put("账户详情",FragmentAccountDetail.class);

        mFragmentMap.put("项目详情",FragmentProjectDetail.class);
//        mFragmentMap.put("项目详情2",FragmentProjectDetail2.class);

        //

        mFragmentMap.put("修改昵称",FragmentEditNickName.class);
        mFragmentMap.put("修改密码",FragmentSetNewPwd.class);

        Class cls = mFragmentMap.get(name);
        if (cls != null){
            try {
                Fragment fragment =(Fragment) cls.newInstance();
                Bundle bundle = new Bundle();
                bundle.putString("title",name);
                fragment.setArguments(bundle);
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

    public static void startFragment(Context context,String name, HashMap mapParam){
        Intent intent = new Intent(context,ActivityManager.class);
        intent.putExtra("name",name);

        intent.putExtra("userParam", mapParam);
        context.startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Object  current = getSupportFragmentManager().findFragmentById(R.id.fragment_content);
        Log.d("www","onActivity result activity manager:"+current.getClass().getName());

        if (current instanceof FragmentMyInformation){
            FragmentMyInformation fragmentMyInformation = (FragmentMyInformation)current;
            fragmentMyInformation.onActivityResult(requestCode,resultCode,data);
        }
    }
}
