package com.szty.baohuzhu.adapter;

import org.json.JSONObject;

public class MessageItem {

    public final static int MESSAGE_TYPE_MUTUAL = 1;
    public final static int MESSAGE_TYPE_ACCOUNT = 2;
    public final static int MESSAGE_TYPE_SYSTEM  = 3;

    public final static int MESSAGE_TYPE_BOOKING = 4;
    public final static int MESSAGE_TYPE_SHOPMALL = 5;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getExtend() {
        return extend;
    }

    public void setExtend(String extend) {
        this.extend = extend;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getSendtimes() {
        return sendtimes;
    }

    public void setSendtimes(String sendtimes) {
        this.sendtimes = sendtimes;
    }


    public String  content;

    public String  extend;

    public String  createTime;

    public String  title;

    public int     type;

    public String  sendtimes;

    public void  initFromJson(JSONObject item ){
        try {
            this.setContent(item.getString("content"));
            this.setExtend(item.getString("extend"));
            this.setCreateTime(item.getString("createTime"));
            this.setTitle(item.getString("title"));
            this.setType(item.getInt("type"));
            this.setSendtimes(item.getString("sendtimes"));

        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
