package com.wyhzb.hbsc.fragments;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.wyhzb.hbsc.R;
import com.wyhzb.hbsc.activitys.ActivityManager;
import com.wyhzb.hbsc.adapter.NewMessageCountsInfo;
import com.wyhzb.hbsc.webapi.WebServiceManager;

import org.json.JSONObject;

public class FragmentMessage extends FragmentBase implements View.OnClickListener {

    public FragmentMessage(){
        super();
        title = "消息";
        layoutId = R.layout.hzbmessage;
    }


    @Override
    protected void onInitData() {
        //timestamp 需要实际的上次获取到的最新的那条消息的timeStamp
        int timstamp = 0; //
        WebServiceManager.getInstance().getNewMessageCount(0, 0, timstamp, new WebServiceManager.HttpCallback() {
            @Override
            public void onResonse(boolean sucess, String body) {
                Log.d("www","message list :"+body);
                if (sucess){
                    try{
                        JSONObject jsonObject = new JSONObject(body);
                        JSONObject js =jsonObject.getJSONObject("datas").getJSONObject("cs");
                        NewMessageCountsInfo newMessageCountsInfo = new NewMessageCountsInfo();
                        newMessageCountsInfo.initFromJson(js);
                        String str = String.format("new message total counts: %d, sys message count:%d", newMessageCountsInfo.getCount(), newMessageCountsInfo.getSysMsgCount());
                        Log.d("getNewMessageCount", str);

                        if (newMessageCountsInfo.getAccountMsgCount()>0){
                            TextView textView = (TextView) findViewById(R.id.mall_message_count);
                            textView.setVisibility(View.VISIBLE);
                            textView.setText(String.valueOf( newMessageCountsInfo.getAccountMsgCount()));
                        }
                        if (newMessageCountsInfo.getMutualMsgCount()>0){
                            TextView textView = (TextView) findViewById(R.id.help_message_count);
                            textView.setVisibility(View.VISIBLE);
                            textView.setText(String.valueOf( newMessageCountsInfo.getMutualMsgCount()));
                        }

                        if (newMessageCountsInfo.getShopMsgCount()>0){
                            TextView textView = (TextView) findViewById(R.id.order_message_count);
                            textView.setVisibility(View.VISIBLE);
                            textView.setText(String.valueOf( newMessageCountsInfo.getShopMsgCount()));
                        }
                        if (newMessageCountsInfo.getSysMsgCount()>0){
                            TextView textView = (TextView) findViewById(R.id.notice_message_count);
                            textView.setVisibility(View.VISIBLE);
                            textView.setText(String.valueOf( newMessageCountsInfo.getSysMsgCount()));
                        }

                        if (newMessageCountsInfo.getLastAccountMsg() != null){
                            TextView time = (TextView) findViewById(R.id.mall_time);
                            TextView content = (TextView) findViewById(R.id.mallcontent_text);
                            time.setText(newMessageCountsInfo.getLastAccountMsg().getTime());
                            content.setText(newMessageCountsInfo.getLastAccountMsg().getContent());
                        }
                        if (newMessageCountsInfo.getLastMutualMsg() != null){
                            TextView time = (TextView) findViewById(R.id.help_time);
                            TextView content = (TextView) findViewById(R.id.content_text);
                            time.setText(newMessageCountsInfo.getLastMutualMsg().getTime());
                            content.setText(newMessageCountsInfo.getLastMutualMsg().getContent());
                        }
                        if (newMessageCountsInfo.getLastShopMsg() != null){
                            TextView time = (TextView) findViewById(R.id.order_time);
                            TextView content = (TextView) findViewById(R.id.ordercontent_text);
                            time.setText(newMessageCountsInfo.getLastShopMsg().getTime());
                            content.setText(newMessageCountsInfo.getLastShopMsg().getContent());
                        }

                        if (newMessageCountsInfo.getLastSysMsg() != null){
                            TextView time = (TextView) findViewById(R.id.notice_time);
                            TextView content = (TextView) findViewById(R.id.noticecontent_text);
                            time.setText(newMessageCountsInfo.getLastSysMsg().getTime());
                            content.setText(newMessageCountsInfo.getLastSysMsg().getContent());
                            Log.d("www","system message :"+newMessageCountsInfo.getLastSysMsg().getContent()) ;
                        }

                    }catch (Exception e){
                        e.printStackTrace();
                    }


                }
            }
        });

        findViewById(R.id.help_enter).setOnClickListener(this);
        findViewById(R.id.mall_enter).setOnClickListener(this);
        findViewById(R.id.notice_enter).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.order_enter){
            ActivityManager.startFragment(getContext(),"订单消息");

        }else if(v.getId() == R.id.notice_enter){
            ActivityManager.startFragment(getContext(),"系统消息");

        }else if(v.getId() == R.id.help_enter){
            ActivityManager.startFragment(getContext(),"互助消息");

        }else if(v.getId() == R.id.mall_enter){
            ActivityManager.startFragment(getContext(),"账号消息");

        }


    }
}
