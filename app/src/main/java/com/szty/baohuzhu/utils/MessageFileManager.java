package com.szty.baohuzhu.utils;

import android.content.Context;

import com.szty.baohuzhu.adapter.MessageItem;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MessageFileManager {

    Context mCon ;

    private  String fileName ;
    public MessageFileManager(Context context,int type){

        mCon = context;

        fileName = String.format("message_%d.txt",type);
    }


    public List<MessageItem > loadFromFile( ){
        try {
            FileInputStream inputStream = new FileInputStream(mCon.getFileStreamPath(fileName));
            byte buffer[] = new byte[1024*1024];
            int len =  inputStream.read(buffer);

            String str = new String(buffer,0,len);
            JSONObject object = new JSONObject(str);

            JSONArray array =  object.getJSONArray("items");
            ArrayList<MessageItem> list = new ArrayList<>();
            for (int i =0;i<array.length();i++){
                JSONObject obj= array.getJSONObject(i);
                MessageItem item = new MessageItem();
                item.initFromJson(obj);
                //item.readed = obj.getBoolean("readed");  //服务器端没有这个参数读取会异常端
                list.add(item);
            }
            inputStream.close();

            Collections.sort(list, new Comparator<MessageItem>() {
                @Override
                public int compare(MessageItem o1, MessageItem o2) {
                    return (o2.getSendtimes().compareTo(o1.getSendtimes()));
                }
            });

            return list;
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public void  saveToFile(List<MessageItem> list){
        try {
            FileOutputStream outputStream = new FileOutputStream(mCon.getFileStreamPath(fileName));

            JSONObject jsonObject = new JSONObject();

            JSONArray array = new JSONArray();
            for (MessageItem item :list){
                JSONObject obj = new JSONObject();
                obj.put("createTime",item.createTime);
                obj.put("content",item.content);
                obj.put("title",item.title);
                obj.put("type",item.type);
                obj.put("extend",item.extend);
                obj.put("sendtimes",item.sendtimes);
                array.put(obj);
            }
            jsonObject.put("items",array);
            outputStream.write(jsonObject.toString().getBytes());
            outputStream.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
