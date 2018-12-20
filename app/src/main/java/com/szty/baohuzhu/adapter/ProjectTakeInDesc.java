package com.szty.baohuzhu.adapter;

import org.json.JSONObject;

public class ProjectTakeInDesc {

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    //项目名称
    private String title;

    //银行卡号
    private String bankCard;

    //银行名称
    private String bankName;

    //项目id
    private int mid;

    //总资产
    private String totalMoney;

    //利率
    private String rate;

    //总利率
    private String totalRate;

    //截止日期
    private String endTime;

    //即将还款总额
    private String willBackMoney;

    //总余额
    private String totalBalance;

    //可提现余额
    private String balance;

    //不可提现余额
    private String noRechargeBalance;

    //互助总额
    private String totalMutualMoney;

    //消息
    private String msg;


    public String getBankCard() {
        return bankCard;
    }

    public void setBankCard(String bankCard) {
        this.bankCard = bankCard;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public int getMid() {
        return mid;
    }

    public void setMid(int mid) {
        this.mid = mid;
    }

    public String getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(String totalMoney) {
        this.totalMoney = totalMoney;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getTotalRate() {
        return totalRate;
    }

    public void setTotalRate(String totalRate) {
        this.totalRate = totalRate;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getWillBackMoney() {
        return willBackMoney;
    }

    public void setWillBackMoney(String willBackMoney) {
        this.willBackMoney = willBackMoney;
    }

    public String getTotalBalance() {
        return totalBalance;
    }

    public void setTotalBalance(String totalBalance) {
        this.totalBalance = totalBalance;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getNoRechargeBalance() {
        return noRechargeBalance;
    }

    public void setNoRechargeBalance(String noRechargeBalance) {
        this.noRechargeBalance = noRechargeBalance;
    }

    public String getTotalMutualMoney() {
        return totalMutualMoney;
    }

    public void setTotalMutualMoney(String totalMutualMoney) {
        this.totalMutualMoney = totalMutualMoney;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isIs_balanceBack() {
        return is_balanceBack;
    }

    public void setIs_balanceBack(boolean is_balanceBack) {
        this.is_balanceBack = is_balanceBack;
    }

    //balanceBack 是否可以使用余额还款 true可以， false不可以
    private boolean    is_balanceBack;
    

    public void  initFromJson(JSONObject item ){
        try {
            this.setTitle(item.getString("title"));
            this.setBankCard(item.getString("bankCard"));
            this.setBankName(item.getString("bankName"));
            this.setMid(item.getInt("mid"));
            this.setTotalMoney(item.getString("totalMoney"));

            this.setRate(item.getString("rate"));
            this.setTotalRate(item.getString("totalRate"));
            this.setEndTime(item.getString("endTime"));
            this.setWillBackMoney(item.getString("willBackMoney"));

            this.setTotalBalance(item.getString("totalBalance"));
            this.setBalance(item.getString("balance"));
            this.setNoRechargeBalance(item.getString("noRechargeBalance"));
            this.setTotalMutualMoney(item.getString("totalMutualMoney"));

            this.setMsg(item.getString("msg"));

            this.setIs_balanceBack(item.getBoolean("balanceBack"));

        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
