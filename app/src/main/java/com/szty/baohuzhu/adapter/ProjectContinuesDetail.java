package com.szty.baohuzhu.adapter;

import org.json.JSONObject;

public class ProjectContinuesDetail {
    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getTimeType() {
        return timeType;
    }

    public void setTimeType(int timeType) {
        this.timeType = timeType;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    //续标次数 0 为首标
    private int num;

    //续标时限
    private int month;

    //续标时限类型 1 日 2 月 3 年
    private int timeType;

    //续标利率
    private String rate;

    //续标有效开始时间
    private String startTime;

    //续标有效结束时间
    private String endTime;

     public void  initFromJson(JSONObject item ){
        try {
            this.setNum(item.getInt("num"));
            this.setMonth(item.getInt("month"));
            this.setTimeType(item.getInt("timeType"));
            this.setRate(item.getString("rate"));
            this.setStartTime(item.getString("startTime"));
            this.setEndTime(item.getString("endTime"));

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public String getContinueTimesStrHtml(){
        String strAttr = String.format("<small>续标次数：第%d次</small>", this.num);

        if(num == 0){
            strAttr = String.format("<small>续标次数：首标</small>");
        }

        return strAttr;
    }

    public String getDurationStrHtml(){
        String strAttr = String.format("<small>互助期限：%d个月</small>", this.month);

        if(timeType == ProjectItem.PROJECT_DURATION_TYPE_DAY){
            strAttr = String.format("<small>互助期限：%d天</small>", this.month);
        }
        else if(timeType == ProjectItem.PROJECT_DURATION_TYPE_MONTH){
            strAttr = String.format("<small>互助期限：%d个月</small>", this.month);
        }
        else if(timeType == ProjectItem.PROJECT_DURATION_TYPE_YEAR){
            strAttr = String.format("<small>互助期限：%d年</small>", this.month);
        }

        return  strAttr;
    }

    public String getContinueRateStrHtml(){
        String strAttr = String.format("<small>续标利率：%s</small>", this.rate);

        return strAttr;
    }

    public String getContinuePeriodStrHtml(){
        String strAttr = String.format("<small>续标期限：%s~%s</small>", this.startTime, this.endTime);

        return strAttr;
    }

}
