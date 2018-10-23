package com.szty.baohuzhu.adapter;

public class ProjectItem {

    private int type; // 1 竞标 2 授权
    private String title;//标的标题
    private int totalMoney;//需要总额
    private int month;//标的月份
    private int interestRate;//失败返回金币利率金币利率
    private int needNum;//需要人数
    private String startTime;//开始时间
    private String endTime;//结束时间
    private int  completeNum;//参与人数
    private int bidNum;//竞标人数
    private String closeTime;//竞标关闭日期
    private String  helpSelfMoney;//单份 金额
    private int cashMoney;//可提现账户使用金额
    private int notMention;//不可提现账户使用金额

    public int getBidNum() {
        return bidNum;
    }

    public int getCashMoney() {
        return cashMoney;
    }

    public int getCompleteNum() {
        return completeNum;
    }

    public int getInterestRate() {
        return interestRate;
    }

    public int getMonth() {
        return month;
    }

    public int getNeedNum() {
        return needNum;
    }

    public int getNotMention() {
        return notMention;
    }

    public int getTotalMoney() {
        return totalMoney;
    }

    public int getType() {
        return type;
    }

    public String getCloseTime() {
        return closeTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getHelpSelfMoney() {
        return helpSelfMoney;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getTitle() {
        return title;
    }

    public void setBidNum(int bidNum) {
        this.bidNum = bidNum;
    }

    public void setCashMoney(int cashMoney) {
        this.cashMoney = cashMoney;
    }

    public void setCloseTime(String closeTime) {
        this.closeTime = closeTime;
    }

    public void setCompleteNum(int completeNum) {
        this.completeNum = completeNum;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public void setHelpSelfMoney(String helpSelfMoney) {
        this.helpSelfMoney = helpSelfMoney;
    }

    public void setInterestRate(int interestRate) {
        this.interestRate = interestRate;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setNeedNum(int needNum) {
        this.needNum = needNum;
    }

    public void setNotMention(int notMention) {
        this.notMention = notMention;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTotalMoney(int totalMoney) {
        this.totalMoney = totalMoney;
    }

    public void setType(int type) {
        this.type = type;
    }
}
