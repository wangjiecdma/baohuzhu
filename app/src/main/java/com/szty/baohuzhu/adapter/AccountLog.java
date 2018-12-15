package com.szty.baohuzhu.adapter;

import org.json.JSONObject;

public class AccountLog {

    //账户余额增加  ---界面上通过type类型在金额数字前面使用 "+"或 "-"号
    public final static int ACCOUNT_LOG_IN = 1;
    //账户余额减少
    public final static int ACCOUNT_LOG_OUT = 2;

    //记录描述
    private  String  mark;

    //记录时间
    private  String time;

    //1 进账 2 出账
    private  int type;

    //金额
    private  int money;

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getMoneyType() {
        return moneyType;
    }

    public void setMoneyType(int moneyType) {
        this.moneyType = moneyType;
    }

    //资金类型 1 银币 2 金币
    private  int moneyType;


    public void  initFromJson(JSONObject item ){
        try {
            this.setMark(item.getString("mark"));
            this.setTime(item.getString("time"));
            this.setType(item.getInt("type"));
            this.setMoney(item.getInt("money"));
            this.setMoneyType(item.getInt("moneyType"));

        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
