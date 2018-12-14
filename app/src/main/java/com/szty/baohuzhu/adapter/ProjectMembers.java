package com.szty.baohuzhu.adapter;

import org.json.JSONObject;

public class ProjectMembers {
    private int uid;
    private String timePartin;//标的标题
    private String aliasNo;//标的标题
    private int type; // 1 竞标 2 授权

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getTimePartin() {
        return timePartin;
    }

    public void setTimePartin(String timePartin) {
        this.timePartin = timePartin;
    }

    public String getAliasNo() {
        return aliasNo;
    }

    public void setAliasNo(String aliasNo) {
        this.aliasNo = aliasNo;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void  initFromJson(JSONObject item ){
        try {
            this.setUid(item.getInt("uid"));
            this.setType(item.getInt("type"));
            this.setTimePartin(item.getString("ctime"));
            this.setAliasNo(item.getString("aliasNo"));


        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
