package com.szty.baohuzhu.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.TextView;

import com.szty.baohuzhu.ProjectActivity;
import com.szty.baohuzhu.R;
import com.szty.baohuzhu.adapter.UserStatus;
import com.szty.baohuzhu.utils.PreferenceUtils;

public class ActivityMyMainBoard extends BaseActivity {
    //这个类没有实际实现，实际使用的时FragmentMyMain

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_main_activity);

        findViewById(R.id.login_regist).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityMyMainBoard.this,ActivityUserRegister.class);

                startActivityForResult(intent,100);
            }
        });

        findViewById(R.id.pop_cash).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                ActivityManager.startFragment(ActivityMyMainBoard.this,"提现");

            }
        });
        findViewById(R.id.push_cash).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityManager.startFragment(ActivityMyMainBoard.this,"充值");

            }
        });
        findViewById(R.id.my_project_list).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityManager.startFragment(ActivityMyMainBoard.this,"我的项目");

            }
        });
        findViewById(R.id.mylog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityManager.startFragment(ActivityMyMainBoard.this,"详情记录");

            }
        });
        findViewById(R.id.my_information).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityManager.startFragment(ActivityMyMainBoard.this,"个人信息");

            }
        });
        findViewById(R.id.setting).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityManager.startFragment(ActivityMyMainBoard.this,"设置");

            }
        });
        findViewById(R.id.message).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityManager.startFragment(ActivityMyMainBoard.this,"消息");

            }
        });

        findViewById(R.id.btn_account).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("www","zhang hu xiangqing ");
                ActivityManager.startFragment(ActivityMyMainBoard.this,"账户详情");

            }
        });

        findViewById(R.id.btn_account2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("www","zhang hu xiangqing ");

                ActivityManager.startFragment(ActivityMyMainBoard.this,"账户详情");
            }
        });

        if(PreferenceUtils.isLogin()){
            this.updateUserStates();
        }
    }



    private void updateUserStates(){
        UserStatus user =  UserStatus.user();
        TextView name = findViewById(R.id.login_regist);
        name.setText(user.getNickName());

        TextView balance = findViewById(R.id.balance);
        balance.setText(user.getBalance());

        TextView crashing = findViewById(R.id.crashing);
        crashing.setText(user.getCrashIng());

        TextView bidmoney = findViewById(R.id.bidmoney);
        bidmoney.setText(user.getBidMoney());

        TextView noRechargeBalance = findViewById(R.id.noRechargeBalance);
        noRechargeBalance.setText(user.getNoRechargeBalance());

        TextView noBidMoney = findViewById(R.id.noBidMoney);
        noBidMoney.setText(user.getNoBidMoney());

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100){
            updateUserStates();
        }
    }
}
