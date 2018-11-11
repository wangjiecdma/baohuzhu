package com.szty.baohuzhu.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.szty.baohuzhu.R;
import com.szty.baohuzhu.adapter.UserStatus;

public class ActivityMyMainBoard extends BaseActivity {

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

                Intent intent =  new Intent(ActivityMyMainBoard.this,ActivityManager.class);
                startActivity(intent);

            }
        });
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
