package com.wyhzb.hbsc.fragments;

import android.view.View;
import android.widget.TextView;

import com.wyhzb.hbsc.R;
import com.wyhzb.hbsc.activitys.ActivityManager;
import com.wyhzb.hbsc.adapter.UserStatus;

public class FragmentReturnCach extends FragmentBase {
   public FragmentReturnCach(){
       super();
       layoutId = R.layout.tixian_guanli;
       title = "提现管理";
   }

    @Override
    protected void onInitData() {

       TextView phone = (TextView) findViewById(R.id.tixian_phonenumber);
       phone.setText(UserStatus.user().getMobile());
        TextView bank = (TextView) findViewById(R.id.tixian_bankname);
        bank.setText(UserStatus.user().getBankName());

        TextView number = (TextView) findViewById(R.id.tixian_cardNumber);
        number.setText(UserStatus.user().getBankCard());

        findViewById(R.id.btn_edit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ActivityManager.startFragment(getContext(),"提现绑卡");
            }
        });
    }
}
