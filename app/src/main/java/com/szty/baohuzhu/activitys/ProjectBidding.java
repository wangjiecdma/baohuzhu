package com.szty.baohuzhu.activitys;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.szty.baohuzhu.R;

public class ProjectBidding extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.project_bidding);


        initViews();


    }
    private void initViews(){

        //使用HTML语言格式实现特殊效果的TextView
        TextView detail = findViewById(R.id.project_bidding_detail);
        detail.setText(Html.fromHtml("竞标结束直接获取可提现钱包授权资金<font color='red'>100%</font>金币回报，金币可商城购物，授权后不可撤销"));


        TextView profile = findViewById(R.id.project_profile);
        profile.setText(Html.fromHtml("<u>参与竞标协议</u>"));
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ProjectBidding.this,"click me ",Toast.LENGTH_SHORT).show();
            }
        });
    }

}
