package com.szty.baohuzhu.fragments;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.szty.baohuzhu.R;
import com.szty.baohuzhu.adapter.MessageItem;
import com.szty.baohuzhu.adapter.NewMessageCountsInfo;
import com.szty.baohuzhu.webapi.WebServiceManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;
import java.util.ArrayList;

public class FragmentMessageList extends FragmentBase {

    public FragmentMessageList(){
        super();
        layoutId = R.layout.list_layout;
        title = "账号信息";
    }

    @Override
    protected void onInitData() {
        ListView listView = (ListView)findViewById(R.id.list);

        final int messageType = MessageItem.MESSAGE_TYPE_ACCOUNT;//MessageItem.MESSAGE_TYPE_MUTUAL;//MessageItem.MESSAGE_TYPE_SYSTEM;
        int index = 0;
        int timeStamp = 0;
        WebServiceManager.getInstance().getMessageListByType(index, messageType, timeStamp, new WebServiceManager.HttpCallback() {
            @Override
            public void onResonse(boolean sucess, String body) {
                try{
                    ArrayList<MessageItem> newMessageList = new ArrayList<MessageItem>();

                    JSONObject jsonObject = new JSONObject(body);
                    JSONObject js =jsonObject.getJSONObject("datas");
                    JSONArray jsMessageArray = js.getJSONArray("msgs");
                    MessageItem messageItm = new MessageItem();

                    if(jsMessageArray != null) {
                        for (int i = 0; i < jsMessageArray.length(); i ++){
                            JSONObject oj = jsMessageArray.getJSONObject(i);
                            MessageItem message =  new MessageItem();
                            message.initFromJson(oj);
                            newMessageList.add(message);
                        }
                    }

                    String str = newMessageList.toString();
                    Log.d("getMessageListByType", str);
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });


        listView.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return 4;
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                 return LayoutInflater.from(getContext()).inflate(R.layout.message_item,null);
            }
        });
    }
}
