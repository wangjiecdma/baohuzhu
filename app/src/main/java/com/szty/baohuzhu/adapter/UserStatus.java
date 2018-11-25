package com.szty.baohuzhu.adapter;

public class UserStatus {
    private String token ; //登陆访问token
    private String userNo;//用户号
    private String levelName ;//会员级别
    private String mobile;//手机号
    private String noBidMoney;//不可提现授权金额
    private String noRechargeBalance;//不可提现余额
    private String bidMoney;//可提现授权金额
    private String birthday;//生日
    private String sex;//性别
    private String ico;//头像
    private String bankName;//银行名
    private String bankCard;//银行卡
    private String balance;//可提现余额
    private String totalProfit;//金币
    private String crashIng;//提现中
    private String totalMoney;//总资产
    private String adCode;//推广码
    private String nickName;//昵称
    private int uid;//用户id
    private String totalCrash;

    public String getTotalCrash() {
        return totalCrash;
    }

    public void setTotalCrash(String totalCrash) {
        this.totalCrash = totalCrash;
    }

    private UserStatus(){

    }
    public void setToken(String token) {
        this.token = token;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setNoBidMoney(String noBidMoney) {
        this.noBidMoney = noBidMoney;
    }

    public void setNoRechargeBalance(String noRechargeBalance) {
        this.noRechargeBalance = noRechargeBalance;
    }

    public void setBidMoney(String bidMoney) {
        this.bidMoney = bidMoney;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setIco(String ico) {
        this.ico = ico;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public void setBankCard(String bankCard) {
        this.bankCard = bankCard;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public void setTotalProfit(String totalProfit) {
        this.totalProfit = totalProfit;
    }

    public void setCrashIng(String crashIng) {
        this.crashIng = crashIng;
    }

    public void setTotalMoney(String totalMoney) {
        this.totalMoney = totalMoney;
    }

    public void setAdCode(String adCode) {
        this.adCode = adCode;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getToken() {
        return token;
    }

    public String getUserNo() {
        return userNo;
    }

    public String getLevelName() {
        return levelName;
    }

    public String getMobile() {
        return mobile;
    }

    public String getNoBidMoney() {
        return noBidMoney;
    }

    public String getNoRechargeBalance() {
        return noRechargeBalance;
    }

    public String getBidMoney() {
        return bidMoney;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getSex() {
        return sex;
    }

    public String getIco() {
        return ico;
    }

    public String getBankName() {
        return bankName;
    }

    public String getBankCard() {
        return bankCard;
    }

    public String getBalance() {
        return balance;
    }

    public String getTotalProfit() {
        return totalProfit;
    }

    public String getCrashIng() {
        return crashIng;
    }

    public String getTotalMoney() {
        return totalMoney;
    }

    public String getAdCode() {
        return adCode;
    }

    public String getNickName() {
        return nickName;
    }

    public int getUid() {
        return uid;
    }

    private static UserStatus  sUser = new UserStatus();

    public static UserStatus user(){
        return sUser;
    }
}