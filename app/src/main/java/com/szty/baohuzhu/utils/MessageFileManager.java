package com.szty.baohuzhu.utils;

import android.content.Context;

import com.szty.baohuzhu.adapter.MessageItem;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class MessageFileManager {

    Context mCon ;

    private static final String fileName = "message.txt";
    public MessageFileManager(Context context){
        mCon = context;
    }

    public List< MessageItem> loadMessage(int type){
        List<MessageItem> list = loadFromFile(fileName);
        if (list.size()>0) {
            for (int i = list.size() - 1; i >= 0; i--) {
                if (list.get(i).type != type){
                    list.remove(i);
                }
            }

        }
        return list;
    }

    public void addMessage(MessageItem item){
        List<MessageItem > list = loadFromFile(fileName );
        for (int i =0;i<list.size();i++){
            MessageItem obj = list.get(i);
            if (obj.createTime.equals(item.createTime) && item.type == obj.type){
                return;
            }
        }
        list.add(item);
        saveToFile(list,fileName);
    }

    public void readMessage(MessageItem item){

        List<MessageItem > list = loadFromFile(fileName );
        for (int i =0;i<list.size();i++){
            MessageItem obj= list.get(i);

            if (obj.type == item.type && obj.createTime.equals(item.createTime)){
                obj.readed =true;

                saveToFile(list,fileName);
                return;

            }
        }
    }

    private List<MessageItem > loadFromFile(String file ){
        try {
            FileInputStream inputStream = new FileInputStream(file);
            byte buffer[] = new byte[1024*1024];
            int len =  inputStream.read(buffer);

            String str = new String(buffer,0,len);
            JSONObject object = new JSONObject(str);

            JSONArray array =  object.getJSONArray("items");
            ArrayList<MessageItem> list = new ArrayList<>();
            for (int i =0;i<array.length();i++){
                JSONObject obj= array.getJSONObject(i);
                MessageItem item = new MessageItem();
                item.type = obj.getInt("type");
                item.content = obj.getString("content");
                item.title = obj.getString("title");
                item.createTime = obj.getString("createTime");
                //item.readed = obj.getBoolean("readed");  //服务器端没有这个参数读取会异常端
                list.add(item);
            }
            inputStream.close();
            return list;
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    private void  saveToFile(List<MessageItem> list , String  file){
        try {
            FileOutputStream outputStream = new FileOutputStream(new File(file));

            JSONObject jsonObject = new JSONObject();

            JSONArray array = new JSONArray();
            for (MessageItem item :list){
                JSONObject obj = new JSONObject();
                obj.put("date",item.createTime);
                obj.put("content",item.content);
                obj.put("title",item.title);
                obj.put("type",item.type);
                obj.put("readed",item.readed);
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
