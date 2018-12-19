package com.szty.baohuzhu.adapter;

import org.json.JSONObject;

public class Durations {
    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    //互助期限
    //服务期
    private int duration;

    //利率
    private String rate;

    //1 duraion单位是日； 2 duraion单位是月； 3 duraion单位是年
    private  int type;


    public void  initFromJson(JSONObject item ){
        try {
            this.setDuration(item.getInt("monthOrYear"));
            this.setType(item.getInt("type"));
            this.setRate(item.getString("rate"));

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    //获取互助期限 字符串
    public String getDurationString(){
        String strAttr = String.format("%d个月", getDuration());

        if(type == ProjectItem.PROJECT_DURATION_TYPE_DAY){
            strAttr = String.format("%d天", getDuration());
        }
        else if(type == ProjectItem.PROJECT_DURATION_TYPE_MONTH){
            strAttr = String.format("%d个月", getDuration());
        }
        else if(type == ProjectItem.PROJECT_DURATION_TYPE_YEAR){
            strAttr = String.format("%d年", getDuration());
        }

        return  strAttr;

    }
}
