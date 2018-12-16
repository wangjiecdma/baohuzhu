package com.szty.baohuzhu.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.szty.baohuzhu.R;
import com.szty.baohuzhu.adapter.MessageItem;
import com.szty.baohuzhu.adapter.NewMessageCountsInfo;
import com.szty.baohuzhu.utils.MessageFileManager;
import com.szty.baohuzhu.utils.PreferenceUtils;
import com.szty.baohuzhu.webapi.WebServiceManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;

public class FragmentMessageList extends FragmentBase {


    int type ;
    public FragmentMessageList(){
        super();
        layoutId = R.layout.list_layout;
        title = "账号消息";
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        title = bundle.getString("title");
        if (title.equals("账号消息")){
            type = MessageItem.MESSAGE_TYPE_ACCOUNT;
        }else if(title.equals("系统消息")){
            type = MessageItem.MESSAGE_TYPE_SYSTEM;
        }else if(title.equals("互助消息")){
            type = MessageItem.MESSAGE_TYPE_MUTUAL;
        }else if(title.equals("订单消息")){
            type = MessageItem.MESSAGE_TYPE_SHOPMALL;
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected void onInitData() {

        final int messageType =type;//MessageItem.MESSAGE_TYPE_MUTUAL;//MessageItem.MESSAGE_TYPE_SYSTEM;
        int index = 0;
        int timeStamp = PreferenceUtils.getTimestampType(type);

        MessageFileManager manager = new MessageFileManager(getContext(),type);
        updateList(manager.loadFromFile());
        WebServiceManager.getInstance().getMessageListByType(index, messageType, timeStamp, new WebServiceManager.HttpCallback() {
            @Override
            public void onResonse(boolean sucess, String body) {
                try{
                    ArrayList<MessageItem> newMessageList =  new ArrayList<MessageItem>();
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
                    if (newMessageList.size() >0){
                        MessageItem first =  newMessageList.get(0);

                        PreferenceUtils.setTimestampType(type ,Integer.valueOf( first.getSendtimes()));

                        MessageFileManager manager = new MessageFileManager(getContext(),type);
                        List<MessageItem> old = manager.loadFromFile();
                        old.addAll(newMessageList);

                        Collections.sort(old, new Comparator<MessageItem>() {
                            @Override
                            public int compare(MessageItem o1, MessageItem o2) {
                                return (o2.getSendtimes().compareTo(o1.getSendtimes()));                }
                        });

                        manager.saveToFile(old);
                        updateList(old);
                    }
                    Log.d("getMessageListByType", str);
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });



    }

    private void updateList(final List<MessageItem> list){
        ListView listView = (ListView)findViewById(R.id.list);
        listView.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return list.size();
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
                View view =  LayoutInflater.from(getContext()).inflate(R.layout.message_item,null);

                TextView title = (TextView)view.findViewById(R.id.message_type);
                TextView content = (TextView)view.findViewById(R.id.message_content);
                TextView time = (TextView)view.findViewById(R.id.message_time);

                MessageItem msg=  list.get(position);
                time.setText(msg.getCreateTime());
                content.setText(msg.getContent());
                title.setText(msg.getTitle());

                return view;
            }
        });
    }
}
