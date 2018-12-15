package com.szty.baohuzhu.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.szty.baohuzhu.R;
import com.szty.baohuzhu.activitys.ActivityManager;
import com.szty.baohuzhu.activitys.ActivityMyMainBoard;
import com.szty.baohuzhu.activitys.ActivityUserRegister;
import com.szty.baohuzhu.adapter.UserStatus;
import com.szty.baohuzhu.utils.PreferenceUtils;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;


public class FragmentMyMain extends FragmentBase {

    private BroadcastReceiver dataChangedReceiver;

    TextView loginOrRegister;
    TextView userLever;


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

                if(PreferenceUtils.isLogin() == false) {
                    Intent intent = new Intent(getContext(), ActivityUserRegister.class);

                    startActivityForResult(intent, 100);
                }
            }
        });

        findViewById(R.id.pop_cash).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                if(PreferenceUtils.isLogin() == false) {
                    Intent intent = new Intent(getContext(), ActivityUserRegister.class);

                    startActivityForResult(intent, 100);
                    return;
                }

                ActivityManager.startFragment(getContext(),"提现");

            }
        });
        findViewById(R.id.push_cash).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(PreferenceUtils.isLogin() == false) {
                    Intent intent = new Intent(getContext(), ActivityUserRegister.class);

                    startActivityForResult(intent, 100);
                    return;
                }

                ActivityManager.startFragment(getContext(),"充值");

            }
        });
        findViewById(R.id.my_project_list).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(PreferenceUtils.isLogin() == false) {
                    Intent intent = new Intent(getContext(), ActivityUserRegister.class);

                    startActivityForResult(intent, 100);
                    return;
                }

                ActivityManager.startFragment(getContext(),"我的项目");
            }
        });
        findViewById(R.id.mylog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(PreferenceUtils.isLogin() == false) {
                    Intent intent = new Intent(getContext(), ActivityUserRegister.class);

                    startActivityForResult(intent, 100);
                    return;
                }

                ActivityManager.startFragment(getContext(),"详情记录");
            }
        });
        findViewById(R.id.my_information).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(PreferenceUtils.isLogin() == false) {
                    Intent intent = new Intent(getContext(), ActivityUserRegister.class);

                    startActivityForResult(intent, 100);
                    return;
                }

                ActivityManager.startFragment(getContext(),"个人信息");
            }
        });
        findViewById(R.id.setting).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(PreferenceUtils.isLogin() == false) {
                    Intent intent = new Intent(getContext(), ActivityUserRegister.class);

                    startActivityForResult(intent, 100);
                    return;
                }

                ActivityManager.startFragment(getContext(),"设置");
            }
        });
        findViewById(R.id.message).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(PreferenceUtils.isLogin() == false) {
                    Intent intent = new Intent(getContext(), ActivityUserRegister.class);

                    startActivityForResult(intent, 100);
                    return;
                }

                ActivityManager.startFragment(getContext(),"消息");
            }
        });

        loginOrRegister = (TextView)findViewById(R.id.login_regist);
        userLever = (TextView) findViewById(R.id.user_type);

        if(PreferenceUtils.isLogin()){
            this.updateUserStates();
        }


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(getActivity());
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("hzb.dataChanged");
//        dataChangedReceiver = new BroadcastReceiver() {
//            @Override
//            public void onReceive(Context context, Intent intent){
//                String msg = intent.getStringExtra("data");
//                //FragmentMyMain.updateUserStates();
//
//
//            }
//        };
        dataChangedReceiver = new MyBroadcastReceiver(this);

        broadcastManager.registerReceiver(dataChangedReceiver, intentFilter);

    }

    private class MyBroadcastReceiver extends BroadcastReceiver{
        FragmentMyMain myMain;
        public MyBroadcastReceiver(FragmentMyMain frag) {
            myMain=frag;
        }

        @Override
        public void onReceive(Context context, Intent intent){
            String msg = intent.getStringExtra("data");
            myMain.updateUserStates();

        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (dataChangedReceiver != null) {
            LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(dataChangedReceiver);
        }
    }

    @Override
    public void onResume() {

        super.onResume();


    }

    public void updateUserStates(){


        UserStatus status =  UserStatus.user();

        if(PreferenceUtils.isLogin()) {
            if (status.getMobile() != null) {
                Log.d("www", "set text for user ");

                loginOrRegister.setText(status.getMobile());

                userLever.setVisibility(View.VISIBLE);
                userLever.setText(status.getLevelName());

            }
        }
        else{
            loginOrRegister.setText("登录/注册");
            userLever.setVisibility(View.INVISIBLE);

        }
//        TextView name = findViewById(R.id.login_regist);
//        name.setText(user.getNickName());
//
//        TextView balance = findViewById(R.id.balance);
//        balance.setText(user.getBalance());
//
//        TextView crashing = findViewById(R.id.crashing);
//        crashing.setText(user.getCrashIng());
//
//        TextView bidmoney = findViewById(R.id.bidmoney);
//        bidmoney.setText(user.getBidMoney());
//
//        TextView noRechargeBalance = findViewById(R.id.noRechargeBalance);
//        noRechargeBalance.setText(user.getNoRechargeBalance());
//
//        TextView noBidMoney = findViewById(R.id.noBidMoney);
//        noBidMoney.setText(user.getNoBidMoney());

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("www","onActivityResult ");
        UserStatus status =  UserStatus.user();
        if (status.getMobile() !=null){
            Log.d("www","set text for user ");
            TextView textView = (TextView)findViewById(R.id.login_regist);
            textView.setText(status.getMobile());

            TextView userType = (TextView) findViewById(R.id.user_type);
            userType.setVisibility(View.VISIBLE);
            userType.setText(status.getLevelName());

        }

        Log.d("www","fragment ");
    }
}
