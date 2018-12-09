package com.szty.baohuzhu.fragments;

import android.support.v4.app.Fragment;
import android.view.View;

import com.szty.baohuzhu.R;
import com.szty.baohuzhu.activitys.ActivityManager;

public class FragmentReturnCach extends FragmentBase {
   public FragmentReturnCach(){
       super();
       layoutId = R.layout.tixian_guanli;
       title = "提现管理";
   }

    @Override
    protected void onInitData() {
        findViewById(R.id.btn_edit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ActivityManager.startFragment(getContext(),"提现绑卡");
            }
        });
    }
}
