package com.wyhzb.hbsc.adapter;

import org.json.JSONObject;

public class LastMessage {

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    private String time;//最新消息发送实际
    private String content;//最新消息内容简要


    public void  initFromJson(JSONObject item ){
        try {
            this.setTime(item.getString("time"));
            this.setContent(item.getString("content"));



        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
