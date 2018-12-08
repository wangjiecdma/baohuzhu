package com.szty.baohuzhu;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.szty.baohuzhu.webapi.WebServiceManager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ArrayList<View>      mTabArray = new ArrayList<View>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WebServiceManager.initManager(this);
        //startActivity(new Intent(this,ProjectActivity.class));

        final TabHost tabHost = (TabHost) findViewById(android.R.id.tabhost);
        tabHost.setup();

        for (int i =0;i<4;i++){
            View view = createTabCustomView(i);
            mTabArray.add(view);
        }

        //创建选项卡
        TabHost.TabSpec tab1  //创建标题卡片，参数一：指定标题卡片的文本内容，参数二：指定标题卡片的背景图片
                //将setContent（int）参数指定的组件（即面板）和上面的卡片标题进行绑定
                ;
        tab1 = tabHost.newTabSpec("tab1")

                //创建标题卡片，参数一：指定标题卡片的文本内容，参数二：指定标题卡片的背景图片
                .setIndicator(mTabArray.get(0))

                //将setContent（int）参数指定的组件（即面板）和上面的卡片标题进行绑定
                .setContent(R.id.content_tab1);

        //将上面创建好的一个选项卡（包括面板和卡片标题）添加到tabHost容器中
        tabHost.addTab(tab1);


        //按照上面的方法创建剩余的三个选项卡，并进行添加
        TabHost.TabSpec tab2 = tabHost.newTabSpec("tab2")
                .setIndicator(mTabArray.get(1))
                .setContent(R.id.content_tab2);
        tabHost.addTab(tab2);

        TabHost.TabSpec tab3 = tabHost.newTabSpec("tab3")
                .setIndicator(mTabArray.get(2))
                .setContent(R.id.content_tab3);
        tabHost.addTab(tab3);

        TabHost.TabSpec tab4 = tabHost.newTabSpec("tab4")
                .setIndicator(mTabArray.get(3))
                .setContent(R.id.content_tab4);

        tabHost.addTab(tab4);


        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                int index = tabHost.getCurrentTab();
                selectTabIndex(0,false);
                selectTabIndex(1,false);
                selectTabIndex(2,false);
                selectTabIndex(3,false);
                selectTabIndex(index,true);
            }
        });
        tabHost.setCurrentTab(3);
    }
    private View createTabCustomView(int index){
        View view = LayoutInflater.from(getBaseContext()).inflate(R.layout.tabcustomview, null);
        if (index ==0) {
            ImageView img = view.findViewById(R.id.tab_img);
            img.setImageResource(R.drawable.store1);
            TextView text = view.findViewById(R.id.tab_txt);
            text.setText("商城");
        }else if(index ==1){
            ImageView img = view.findViewById(R.id.tab_img);
            img.setImageResource(R.drawable.mutual_aid1);
            TextView text = view.findViewById(R.id.tab_txt);
            text.setText("互助");
        }else if(index ==2){
            ImageView img = view.findViewById(R.id.tab_img);
            img.setImageResource(R.drawable.store1);
            TextView text = view.findViewById(R.id.tab_txt);
            text.setText("购物车");
        }else {
            ImageView img = view.findViewById(R.id.tab_img);
            img.setImageResource(R.drawable.user1);
            TextView text = view.findViewById(R.id.tab_txt);
            text.setText("我的");
        }

        return view;

    }
    private void selectTabIndex(int index,boolean enable){
        View view = mTabArray.get(index);
        if (enable){
            if (index ==0) {
                ImageView img = view.findViewById(R.id.tab_img);
                img.setImageResource(R.drawable.store2);
            }else if(index ==1){
                ImageView img = view.findViewById(R.id.tab_img);
                img.setImageResource(R.drawable.mutual_aid2);
            }else if(index ==2){
                ImageView img = view.findViewById(R.id.tab_img);
                img.setImageResource(R.drawable.store2);
            }else {
                ImageView img = view.findViewById(R.id.tab_img);
                img.setImageResource(R.drawable.user2);
            }
            TextView text = view.findViewById(R.id.tab_txt);
            text.setTextColor(Color.RED);

        }else{
            if (index ==0) {
                ImageView img = view.findViewById(R.id.tab_img);
                img.setImageResource(R.drawable.store1);
            }else if(index ==1){
                ImageView img = view.findViewById(R.id.tab_img);
                img.setImageResource(R.drawable.mutual_aid1);
            }else if(index ==2){
                ImageView img = view.findViewById(R.id.tab_img);
                img.setImageResource(R.drawable.store1);
            }else {
                ImageView img = view.findViewById(R.id.tab_img);
                img.setImageResource(R.drawable.user1);
            }
            TextView text = view.findViewById(R.id.tab_txt);
            text.setTextColor(Color.GRAY);
        }
    }
}
