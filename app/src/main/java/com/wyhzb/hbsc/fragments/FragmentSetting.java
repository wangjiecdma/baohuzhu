package com.wyhzb.hbsc.fragments;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.wyhzb.hbsc.R;
import com.wyhzb.hbsc.activitys.ActivityManager;
import com.wyhzb.hbsc.adapter.UserStatus;
import com.wyhzb.hbsc.utils.CommomDialog;
import com.wyhzb.hbsc.utils.PreferenceUtils;
import com.wyhzb.hbsc.webapi.WebServiceManager;

public class FragmentSetting extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.setting,container,false);


        view.findViewById(R.id.return_cach).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityManager.startFragment(getContext(),"提现管理");
            }
        });

        view.findViewById(R.id.loginout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //弹出提示框
                new CommomDialog(getContext(), R.style.dialoghzb, "您确定退出登陆？", new CommomDialog.OnCloseListener() {
                    @Override
                    public void onClick(Dialog dialog, boolean confirm) {
                        if(confirm){
                            //Toast.makeText(getContext(),"点击确定", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                            WebServiceManager.getInstance().userLogout(new WebServiceManager.HttpCallback() {
                                @Override
                                public void onResonse(boolean sucess, String body) {
                                    if(sucess){
                                        PreferenceUtils.setLogin(false);
                                        PreferenceUtils.setAutoLogin(false);
                                        WebServiceManager.getInstance().setToken("");

                                        Intent mIntent = new Intent("hzb.dataChanged");
                                        mIntent.putExtra("message","this message is from subActivity");
                                        LocalBroadcastManager.getInstance(getContext()).sendBroadcast(mIntent);

                                        Toast.makeText(getContext(),"退出成功", Toast.LENGTH_SHORT).show();

                                        UserStatus.loginOut();
                                        //退回上一界面
                                        getActivity().finish();

                                    }
                                    else{
                                        Toast.makeText(getContext(),"退出失败", Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });

                        }

                    }
                })
                        .setTitle("提示").show();

            }
        });

        return view;
    }

    public void Logout(){

    }

    @Override
    public void onResume() {
        super.onResume();
        ActivityManager activityManager = (ActivityManager) getActivity();
        activityManager.setTitle("设置");

    }

}
