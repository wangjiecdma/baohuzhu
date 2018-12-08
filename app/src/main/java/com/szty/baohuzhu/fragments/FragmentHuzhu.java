package com.szty.baohuzhu.fragments;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.szty.baohuzhu.R;
import com.szty.baohuzhu.activitys.ActivityManager;
import com.szty.baohuzhu.webapi.WebServiceManager;

public class FragmentHuzhu extends FragmentBase implements View.OnClickListener{
    public FragmentHuzhu(){
        super();
        layoutId= R.layout.segment;
    }

    @Override
    protected void onInitData() {
        findViewById(R.id.segment_index1).setOnClickListener(this);
        findViewById(R.id.segment_index2).setOnClickListener(this);
        findViewById(R.id.segment_index3).setOnClickListener(this);


        // 在此处添加初始化数据的代码:
        WebServiceManager.getInstance().getSucessList(0, 100, new WebServiceManager.HttpCallback() {
            @Override
            public void onResonse(boolean sucess, String body) {

            }
        });

        WebServiceManager.getInstance().getProjectList(0, 100, new WebServiceManager.HttpCallback() {
            @Override
            public void onResonse(boolean sucess, String body) {

            }
        });


        //默认选中第一个segment
        onClick(findViewById(R.id.segment_index1));

        findViewById(R.id.new_project).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                ActivityManager.startFragment(getContext(),"我要创建");

            }
        });
    }

    @Override
    public void onClick(View v) {
        TextView leftText = (TextView)findViewById(R.id.segment_text_left);
        TextView centerText = (TextView)findViewById(R.id.segment_text_center);
        TextView rightText = (TextView)findViewById(R.id.segment_text_right);
        leftText.setTextColor(Color.BLACK);
        rightText.setTextColor(Color.BLACK);
        centerText.setTextColor(Color.BLACK);

        findViewById(R.id.segment_line_left).setVisibility(View.INVISIBLE);
        findViewById(R.id.segment_line_center).setVisibility(View.INVISIBLE);
        findViewById(R.id.segment_line_right).setVisibility(View.INVISIBLE);


        findViewById(R.id.create_panel).setVisibility(View.INVISIBLE);
        findViewById(R.id.huzhu_panel).setVisibility(View.INVISIBLE);
        findViewById(R.id.sucecss_listview).setVisibility(View.INVISIBLE);

        //set setment to normal state



        //set selected state for setmeng index and panel view
        if (v.getId()== R.id.segment_index1){
            leftText.setTextColor(Color.RED);
            findViewById(R.id.segment_line_left).setVisibility(View.VISIBLE);
            findViewById(R.id.huzhu_panel).setVisibility(View.VISIBLE);

        }else if (v.getId()== R.id.segment_index2){
            centerText.setTextColor(Color.RED);
            findViewById(R.id.segment_line_center).setVisibility(View.VISIBLE);
            findViewById(R.id.create_panel).setVisibility(View.VISIBLE);

        }else if (v.getId()== R.id.segment_index3){
            rightText.setTextColor(Color.RED);
            findViewById(R.id.segment_line_right).setVisibility(View.VISIBLE);
            findViewById(R.id.sucecss_listview).setVisibility(View.VISIBLE);

        }
    }
}
