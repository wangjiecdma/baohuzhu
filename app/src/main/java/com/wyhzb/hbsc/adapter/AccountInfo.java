package com.wyhzb.hbsc.adapter;

import org.json.JSONObject;

public class AccountInfo {
    //实际用不到这多属性，后面再优化
    public int getAcountId() {
        return acountId;
    }

    public void setAcountId(int acountId) {
        this.acountId = acountId;
    }

    public String getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(String totalMoney) {
        this.totalMoney = totalMoney;
    }

    public String getTotalProfit() {
        return totalProfit;
    }

    public void setTotalProfit(String totalProfit) {
        this.totalProfit = totalProfit;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getTotalMutualRepay() {
        return totalMutualRepay;
    }

    public void setTotalMutualRepay(String totalMutualRepay) {
        this.totalMutualRepay = totalMutualRepay;
    }

    public String getTotalMutualCash() {
        return totalMutualCash;
    }

    public void setTotalMutualCash(String totalMutualCash) {
        this.totalMutualCash = totalMutualCash;
    }

    public String getTotalCash() {
        return totalCash;
    }

    public void setTotalCash(String totalCash) {
        this.totalCash = totalCash;
    }

    public String getMutualBalance() {
        return mutualBalance;
    }

    public void setMutualBalance(String mutualBalance) {
        this.mutualBalance = mutualBalance;
    }

    public String getTotalRecharge() {
        return totalRecharge;
    }

    public void setTotalRecharge(String totalRecharge) {
        this.totalRecharge = totalRecharge;
    }

    public String getRechargeBalance() {
        return rechargeBalance;
    }

    public void setRechargeBalance(String rechargeBalance) {
        this.rechargeBalance = rechargeBalance;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankCard() {
        return bankCard;
    }

    public void setBankCard(String bankCard) {
        this.bankCard = bankCard;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getUserCard() {
        return userCard;
    }

    public void setUserCard(String userCard) {
        this.userCard = userCard;
    }

    public String getTotalHelp() {
        return totalHelp;
    }

    public void setTotalHelp(String totalHelp) {
        this.totalHelp = totalHelp;
    }

    public String getBidMoney() {
        return bidMoney;
    }

    public void setBidMoney(String bidMoney) {
        this.bidMoney = bidMoney;
    }

    public String getVipLevel() {
        return vipLevel;
    }

    public void setVipLevel(String vipLevel) {
        this.vipLevel = vipLevel;
    }

    public String getNoRechargeBalance() {
        return noRechargeBalance;
    }

    public void setNoRechargeBalance(String noRechargeBalance) {
        this.noRechargeBalance = noRechargeBalance;
    }

    public String getNoBidMoney() {
        return noBidMoney;
    }

    public void setNoBidMoney(String noBidMoney) {
        this.noBidMoney = noBidMoney;
    }

    public String getFailrate() {
        return failrate;
    }

    public void setFailrate(String failrate) {
        this.failrate = failrate;
    }

    private int acountId ; //账户id
    private String totalMoney;//总资产
    private String totalProfit;//获取的金币
    private String balance;//可提现余额
    private String totalMutualRepay;//
    private String totalMutualCash ;//
    private String totalCash;//
    private String mutualBalance;//
    private String totalRecharge;//
    private String rechargeBalance;//
    private int uid;//用户id
    private int status;//
    private String createTime;//
    private String updateTime;//
    private String bankName;//银行名
    private String bankCard;//银行卡
    private String realName;
    private String userCard;//身份证号码
    private String  totalHelp;
    private String  bidMoney;
    private String vipLevel;//会员等级
    private String noRechargeBalance;//
    private String noBidMoney;//
    private String failrate;




    public void  initFromJson(JSONObject account ){
        try {
            this.setAcountId(account.getInt("id"));
            this.setTotalMoney(account.getString("totalMoney"));
            this.setTotalProfit(account.getString("totalProfit"));
            this.setBalance(account.getString("balance"));
            this.setTotalMutualRepay(account.getString("totalMutualRepay"));
            this.setTotalMutualCash(account.getString("totalMutualCash"));
            this.setTotalCash(account.getString("totalCash"));
            this.setMutualBalance(account.getString("mutualBalance"));
            this.setTotalRecharge(account.getString("totalRecharge"));

            this.setRechargeBalance(account.getString("rechargeBalance"));
            this.setUid(account.getInt("uid"));
            this.setStatus(account.getInt("status"));
            this.setCreateTime(account.getString("createTime"));
            this.setUpdateTime(account.getString("updateTime"));
            this.setBankName(account.getString("bankName"));
            this.setBankCard(account.getString("bankCard"));
            this.setRealName(account.getString("realName"));
            this.setUserCard(account.getString("userCard"));

            this.setTotalHelp(account.getString("totalHelp"));
            this.setBidMoney(account.getString("bidMoney"));
            this.setVipLevel(account.getString("vipLevel"));
            this.setNoRechargeBalance(account.getString("noRechargeBalance"));
            this.setNoBidMoney(account.getString("noBidMoney"));
            this.setFailrate(account.getString("failrate"));

            

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
