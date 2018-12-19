package com.szty.baohuzhu.adapter;

import org.json.JSONObject;

public class UserLevelInfo {

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public int getStartMoney() {
        return startMoney;
    }

    public void setStartMoney(int startMoney) {
        this.startMoney = startMoney;
    }

    public int getEndMoney() {
        return endMoney;
    }

    public void setEndMoney(int endMoney) {
        this.endMoney = endMoney;
    }

    public int getMaxMutualMoney() {
        return maxMutualMoney;
    }

    public void setMaxMutualMoney(int maxMutualMoney) {
        this.maxMutualMoney = maxMutualMoney;
    }

    public int getMaxMutualTimes() {
        return maxMutualTimes;
    }

    public void setMaxMutualTimes(int maxMutualTimes) {
        this.maxMutualTimes = maxMutualTimes;
    }

    public int getMaxMutualPersons() {
        return maxMutualPersons;
    }

    public void setMaxMutualPersons(int maxMutualPersons) {
        this.maxMutualPersons = maxMutualPersons;
    }

    public int getFailGetGoldRate() {
        return failGetGoldRate;
    }

    public void setFailGetGoldRate(int failGetGoldRate) {
        this.failGetGoldRate = failGetGoldRate;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    //会员等级名称
    private String  levelName;

    private int startMoney;

    private int endMoney;

    //标的允许最大金额
    private int maxMutualMoney;

    private int maxMutualTimes;

    //标的允许参与最大人数
    private int maxMutualPersons;

    //竞标失败返回 金币率 除以 100
    private int failGetGoldRate;

    private int level;



    public void  initFromJson(JSONObject item ){
        try {
            this.setLevelName(item.getString("levelName"));
            this.setStartMoney(item.getInt("startMoney"));
            this.setEndMoney(item.getInt("endMoney"));
            this.setMaxMutualMoney(item.getInt("maxMutualMoney"));
            this.setMaxMutualTimes(item.getInt("maxMutualTimes"));
            this.setMaxMutualPersons(item.getInt("maxMutualPersons"));
            this.setFailGetGoldRate(item.getInt("failGetGoldRate"));
            this.setLevel(item.getInt("level"));

        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
