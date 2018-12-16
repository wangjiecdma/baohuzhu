package com.szty.baohuzhu.adapter;

import android.util.Log;

import org.json.JSONObject;

public class NewMessageCountsInfo {
    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getMutualMsgCount() {
        return mutualMsgCount;
    }

    public void setMutualMsgCount(int mutualMsgCount) {
        this.mutualMsgCount = mutualMsgCount;
    }

    public int getShopMsgCount() {
        return shopMsgCount;
    }

    public void setShopMsgCount(int shopMsgCount) {
        this.shopMsgCount = shopMsgCount;
    }

    public int getAccountMsgCount() {
        return accountMsgCount;
    }

    public void setAccountMsgCount(int accountMsgCount) {
        this.accountMsgCount = accountMsgCount;
    }

    public int getSysMsgCount() {
        return sysMsgCount;
    }

    public void setSysMsgCount(int sysMsgCount) {
        this.sysMsgCount = sysMsgCount;
    }

    public LastMessage getLastAccountMsg() {
        return lastAccountMsg;
    }

    public void setLastAccountMsg(LastMessage lastAccountMsg) {
        this.lastAccountMsg = lastAccountMsg;
    }

    public LastMessage getLastMutualMsg() {
        return lastMutualMsg;
    }

    public void setLastMutualMsg(LastMessage lastMutualMsg) {
        this.lastMutualMsg = lastMutualMsg;
    }

    public LastMessage getLastBookingMsg() {
        return lastBookingMsg;
    }

    public void setLastBookingMsg(LastMessage lastBookingMsg) {
        this.lastBookingMsg = lastBookingMsg;
    }

    public LastMessage getLastSysMsg() {
        return lastSysMsg;
    }

    public void setLastSysMsg(LastMessage lastSysMsg) {
        this.lastSysMsg = lastSysMsg;
    }

    public LastMessage getLastShopMsg() {
        return lastShopMsg;
    }

    public void setLastShopMsg(LastMessage lastShopMsg) {
        this.lastShopMsg = lastShopMsg;
    }

    //新消息总数
    private int   count;

    //互助消息
    private int   mutualMsgCount;

    //商城消息
    private int   shopMsgCount;

    //账号消息
    private int   accountMsgCount;

    //系统通知
    private int   sysMsgCount;

    LastMessage  lastAccountMsg;

    LastMessage  lastMutualMsg;

    LastMessage  lastBookingMsg;

    LastMessage  lastSysMsg;

    LastMessage  lastShopMsg;



    public void  initFromJson(JSONObject item ){
        try {
            this.setCount(item.getInt("count"));
            this.setAccountMsgCount(item.getInt("accountMsgCount"));
            this.setMutualMsgCount(item.getInt("mutualMsgCount"));
            this.setShopMsgCount(item.getInt("shopMsgCount"));
            this.setSysMsgCount(item.getInt("sysMsgCount"));

            if (item.has("lastAccountMsg")&& (item.get("lastAccountMsg")!= JSONObject.NULL)) {

                Log.d("www","last account msg :"+item.get("lastAccountMsg") );
                JSONObject jsLastAccountMsg = item.getJSONObject("lastAccountMsg");
                this.lastAccountMsg = new LastMessage();
                this.lastAccountMsg.initFromJson(jsLastAccountMsg);
            }
            if (item.has("lastMutualMsg")&& (item.get("lastMutualMsg")!= JSONObject.NULL)) {
                JSONObject jsLastMutualMsg = item.getJSONObject("lastMutualMsg");
                this.lastMutualMsg = new LastMessage();
                this.lastMutualMsg.initFromJson(jsLastMutualMsg);
            }
            if (item.has("lastSysMsg")&& (item.get("lastSysMsg")!= JSONObject.NULL)) {
                JSONObject jsLastSysMsg = item.getJSONObject("lastSysMsg");
                this.lastSysMsg = new LastMessage();
                this.lastSysMsg.initFromJson(jsLastSysMsg);
            }


        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
