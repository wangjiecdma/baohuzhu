package com.szty.baohuzhu.adapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.ArrayList;



public class ProjectItem {

    private int ID;
    private int type; // 1 竞标 2 授权
    private String title;//标的标题
    private int totalMoney;//需要总额
    private int month;//标的月份
    private String interestRate;//失败返回金币利率金币利率
    private int needNum;//需要人数
    private String startTime;//开始时间
    private String endTime;//结束时间
    private int  completeNum;//参与人数
    private int bidNum;//竞标人数
    private String closeTime;//竞标关闭日期
    private String  helpSelfMoney;//单份 金额
    private int cashMoney;//可提现账户使用金额
    private int notMention;//不可提现账户使用金额
    private int bidStatus;//项目状态

    private String completeTime;//竞标完成实际
    private int timeType;
    private boolean isAuth;
    private int first;

    private int continueTimes; //根据这个参数来显示 参标人员还是续标详情

    private ArrayList<ProjectMembers> projectMembers = new ArrayList<ProjectMembers>();

    public ArrayList<ProjectContinuesDetail> getProjectContinues() {
        return projectContinues;
    }

    public void setProjectContinues(ArrayList<ProjectContinuesDetail> projectContinues) {
        this.projectContinues = projectContinues;
    }

    private ArrayList<ProjectContinuesDetail> projectContinues = new ArrayList<ProjectContinuesDetail>();


    public int getContinueTimes() {
        return continueTimes;
    }

    public void setContinueTimes(int continueTimes) {
        this.continueTimes = continueTimes;
    }

    public int getFirst() {
        return first;
    }

    public void setFirst(int first) {
        this.first = first;
    }

    public ArrayList<ProjectMembers> getProjectMembers() {
        return projectMembers;
    }

    public void setProjectMembers(ArrayList<ProjectMembers> projectMembers) {
        this.projectMembers = projectMembers;
    }

    //互助期限类型
    public final static int PROJECT_DURATION_TYPE_DAY = 1;
    public final static int PROJECT_DURATION_TYPE_MONTH = 2;
    public final static int PROJECT_DURATION_TYPE_YEAR = 3;
 
    //互助项目状态
    //标的状态 3 竞标中 4 待评标 6 服务中 8待结项 9已完结 10逾期 20 已经竞标 21 可以续标  22 已经续标
    final static int PROJECT_BIDING = 3;
    final static int PROJECT_EVALUATING = 4;
    final static int PROJECT_SERVING = 6;
    final static int PROJECT_TO_BE_RETURN = 7;
    final static int PROJECT_ENDING = 8;
    final static int PROJECT_ENDED = 9;
    final static int PROJECT_OVERDUE = 10;
    final static int PROJECT_BIDED = 20;
    final static int PROJECT_CANCONTINUE = 21;
    final static int PROJECT_CANCONTINUED = 22;

    //项目详情：1 详情   2 授权详情  3 竞标详情
    public final static int PROJECT_DETAIL = 1;
    public final static int PROJECT_AUTH_DETAIL = 2;
    public final static int PROJECT_BID_DETAIL  = 3;

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getID() {
        return ID;
    }

    public int getBidNum() {
        return bidNum;
    }

    public int getCashMoney() {
        return cashMoney;
    }

    public int getCompleteNum() {
        return completeNum;
    }

    public String getInterestRate() {
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

    public String getCompleteTime() {
        return completeTime;
    }

    public int getTimeType(){return  timeType;}

    public int getBidStatus(){return bidStatus;}

    public boolean isAuth(){
        return  isAuth;
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

    public void setInterestRate(String interestRate) {
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

    public void setCompleteTime(String startTime) {
        this.completeTime = startTime;
    }

    public void setTotalMoney(int totalMoney) {
        this.totalMoney = totalMoney;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setTimeType(int timetype){this.timeType = timetype;}

    public void  setBidStatus(int bidstatus){this.bidStatus = bidstatus;}

    public void setAuth(boolean auth){
        isAuth = auth;
    }


    public void  initFromJson(JSONObject item ){
        try {
            this.setID(item.getInt("id"));
            this.setType(item.getInt("type"));
            this.setTitle(item.getString("title"));
            this.setTotalMoney(item.getInt("totalMoney"));
            this.setCloseTime(item.getString("closeTime"));
            this.setEndTime(item.getString("endTime"));
            this.setStartTime(item.getString("startTime"));
            this.setNeedNum(item.getInt("needNum"));
            this.setInterestRate(item.getString("interestRate"));
            this.setBidNum(item.getInt("bidNum"));
            this.setCompleteNum(item.getInt("completeNum"));
            this.setMonth(item.getInt("timeLimit"));
            this.setHelpSelfMoney(item.getString("price"));
            this.setCompleteTime(item.getString("completeTime"));
            this.setTimeType(item.getInt("timeType"));
            this.setBidStatus(item.getInt("bidStatus"));
            this.setAuth(item.getBoolean("auth"));
            this.setFirst(item.getInt("first"));
            this.setContinueTimes(item.getInt("continueTimes"));

            JSONArray array = item.getJSONArray("members");
            if(array != null) {
                for (int i = 0; i < array.length(); i ++){
                    JSONObject oj = array.getJSONObject(i);
                    ProjectMembers member =  new ProjectMembers();
                    member.initFromJson(oj);
                    projectMembers.add(member);
                }
            }
            JSONArray continues = item.getJSONArray("continues");
            if(continues != null) {
                for (int i = 0; i < continues.length(); i ++){
                    JSONObject cn = continues.getJSONObject(i);
                    ProjectContinuesDetail cnDetail =  new ProjectContinuesDetail();
                    cnDetail.initFromJson(cn);
                    projectContinues.add(cnDetail);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    //获取互助期限 字符串
    public String getDurationString(){
        String strAttr = String.format("%d个月", getMonth());

        if(timeType == PROJECT_DURATION_TYPE_DAY){
            strAttr = String.format("%d天", getMonth());
        }
        else if(timeType == PROJECT_DURATION_TYPE_MONTH){
            strAttr = String.format("%d个月", getMonth());
        }
        else if(timeType == PROJECT_DURATION_TYPE_YEAR){
            strAttr = String.format("%d年", getMonth());
        }


        return  strAttr;

    }

    public boolean isAuthButtonEnabled(){
        int bidstatus = this.getBidStatus();
        boolean isAuthButton = false;
        
        if(bidstatus == PROJECT_BIDING){
           
            isAuthButton = true;

            if(this.isAuth){
                isAuthButton = false;
            }
        }
        else if(bidstatus == PROJECT_BIDED){
            
            isAuthButton = false;
        }
        else if(bidstatus == PROJECT_EVALUATING){
            
            isAuthButton = false;
        }
        else if(bidstatus == PROJECT_SERVING){
            
            isAuthButton = false;
        }
        else if(bidstatus == PROJECT_TO_BE_RETURN){
            
            isAuthButton = false;
        }
        else if(bidstatus == PROJECT_ENDING){
            
            isAuthButton = false;
        }
        else if(bidstatus == PROJECT_ENDED){
            
            isAuthButton = false;
        }
        else if(bidstatus == PROJECT_OVERDUE){
            
            isAuthButton = false;
        }
        else if(bidstatus == PROJECT_CANCONTINUE){
            
            isAuthButton = false;
        }
        else if(bidstatus == PROJECT_CANCONTINUED){
            
            isAuthButton = false;
        }
        
        return isAuthButton;
    }
    
    public boolean isBidButtonEnabled(){
        int bidstatus = this.getBidStatus();
        boolean isBidButton = false;

        if(bidstatus == PROJECT_BIDING){
            isBidButton = true;
            
        }
        else if(bidstatus == PROJECT_BIDED){
            isBidButton = false;
            
        }
        else if(bidstatus == PROJECT_EVALUATING){
            isBidButton = false;
            
        }
        else if(bidstatus == PROJECT_SERVING){
            isBidButton = false;
            
        }
        else if(bidstatus == PROJECT_TO_BE_RETURN){
            isBidButton = true;
            
        }
        else if(bidstatus == PROJECT_ENDING){
            isBidButton = false;
            
        }
        else if(bidstatus == PROJECT_ENDED){
            isBidButton = false;
            
        }
        else if(bidstatus == PROJECT_OVERDUE){
            isBidButton = true;
            
        }
        else if(bidstatus == PROJECT_CANCONTINUE){
            isBidButton = true;
            
        }
        else if(bidstatus == PROJECT_CANCONTINUED){
            isBidButton = false;
            
        }

        return isBidButton;
        
    }

    public String getAttrStr(){
        String  strAttr = "首标";;

        if(first != 1){
            strAttr = "续标";
        }

        return strAttr;

    }
    
    public String getAuthStr(){
        String  strAuth = "我要授权";;

        if(this.isAuth()){
            strAuth = "已经授权";
        }

        return strAuth;
    }

    public  String getStatusStr(){
        String  strbidStatus = String.format("竞标中");

        if(bidStatus == PROJECT_BIDING){
            strbidStatus = String.format("竞标中");
        }
        else if(bidStatus == PROJECT_BIDED){
            strbidStatus = String.format("已经竞标");
        }
        else if(bidStatus == PROJECT_EVALUATING){
            strbidStatus = String.format("评标中");
        }
        else if(bidStatus == PROJECT_SERVING){
            strbidStatus = String.format("服务中");
        }
        else if(bidStatus == PROJECT_CANCONTINUE){
            strbidStatus = String.format("可续标");
        }
        else if(bidStatus == PROJECT_CANCONTINUED){
            strbidStatus = String.format("已续标");
        }
        else if(bidStatus == PROJECT_TO_BE_RETURN){
            strbidStatus = String.format("待归还");
        }
        else if(bidStatus == PROJECT_ENDING){
            strbidStatus = String.format("待结项");
        }
        else if(bidStatus == PROJECT_OVERDUE){
            strbidStatus = String.format("已逾期");
        }
        else if(bidStatus == PROJECT_ENDED){
            strbidStatus = String.format("已结项");
        }
        else{
            strbidStatus = String.format("未定义");
        }

        return strbidStatus;
    }

    public String getProjectWarningStrWithHtml(){

        String  strAttr = "";

//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date dateClose = null;
        try {
            dateClose = format.parse(this.closeTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        Date nowDate = new Date(System.currentTimeMillis());;

        long days = ProjectItem.getTimeDistance(nowDate, dateClose);

        if(bidStatus == PROJECT_BIDING){

            strAttr = String.format("本项目离竞标结束日期还剩：<font color='#00CBB1'><big>%d天</big></font>",days);

        }
        else if(bidStatus == PROJECT_BIDED){
            strAttr = String.format("本项目离竞标结束日期还剩：<font color='#00CBB1'><big>%d天</big></font>",days);
        }
        else if(bidStatus == PROJECT_EVALUATING){

        }
        else if(bidStatus == PROJECT_SERVING){

        }
        else if(bidStatus == PROJECT_CANCONTINUE){
            strAttr = String.format("本项目离续标结束日期还剩：<font color='#00CBB1'><big>%d天</big></font>",days);
        }
        else if(bidStatus == PROJECT_CANCONTINUED){
            strAttr = String.format("你已经续标了，可以继续使用");
        }
        else if(bidStatus == PROJECT_TO_BE_RETURN){
            strAttr = String.format("本项目最后资金归还日期还剩：<font color='#00CBB1'><big>%d天</big></font>",days);
        }
        else if(bidStatus == PROJECT_ENDING){

        }
        else if(bidStatus == PROJECT_OVERDUE){
            strAttr = String.format("本项目你已经逾期：<font color='#FF0000'><big>%d天</big></font>",-days);

        }
        else if(bidStatus == PROJECT_ENDED){

        }
        else{

        }

        return strAttr;

    }

    /**
     * 获得两个日期间距多少天
     *
     * @param beginDate
     * @param endDate
     * @return
     */
    public static long getTimeDistance(Date beginDate, Date endDate) {
        Calendar fromCalendar = Calendar.getInstance();
        fromCalendar.setTime(beginDate);
        fromCalendar.set(Calendar.HOUR_OF_DAY, fromCalendar.getMinimum(Calendar.HOUR_OF_DAY));
        fromCalendar.set(Calendar.MINUTE, fromCalendar.getMinimum(Calendar.MINUTE));
        fromCalendar.set(Calendar.SECOND, fromCalendar.getMinimum(Calendar.SECOND));
        fromCalendar.set(Calendar.MILLISECOND, fromCalendar.getMinimum(Calendar.MILLISECOND));

        Calendar toCalendar = Calendar.getInstance();
        toCalendar.setTime(endDate);
        toCalendar.set(Calendar.HOUR_OF_DAY, fromCalendar.getMinimum(Calendar.HOUR_OF_DAY));
        toCalendar.set(Calendar.MINUTE, fromCalendar.getMinimum(Calendar.MINUTE));
        toCalendar.set(Calendar.SECOND, fromCalendar.getMinimum(Calendar.SECOND));
        toCalendar.set(Calendar.MILLISECOND, fromCalendar.getMinimum(Calendar.MILLISECOND));

        long dayDistance = (toCalendar.getTime().getTime() - fromCalendar.getTime().getTime())/(1000 * 60 * 60 * 24);
        dayDistance = Math.abs(dayDistance);

        return dayDistance;
    }

    public String getProjectDetailStr(){

        String  detail = "";
        String  period = "";

        if( timeType == PROJECT_DURATION_TYPE_DAY){
            period = String.format("%d天期限", this.getMonth());
        }
        else if( timeType == PROJECT_DURATION_TYPE_YEAR){
            period= String.format("%d年期限", this.getMonth());
        }
        else{
            period= String.format("%d个月期限", this.getMonth());
        }



        if(bidStatus == PROJECT_BIDING){
            detail= String.format("%s|%d人|还差%d人|%d人竞标中", period, this.getNeedNum(), this.getNeedNum() - this.getCompleteNum(), this.getBidNum());
        }
        else if(bidStatus == PROJECT_EVALUATING){
            detail= String.format("%s|%d人|还差%d人|%d人竞标", period, this.getNeedNum(), this.getNeedNum() - this.getCompleteNum(),  this.getBidNum());
        }
        else{
            detail= String.format("%s|%d人|实际%d人|%d人竞标", period, this.getNeedNum(), this.getCompleteNum(), this.getBidNum());

        }

        return detail;
    }

    //for several common ui show
    public  String getTotalHtmlStrWithTwoColors(){
        String strAttr = String.format("<font black>互助总额：</font><small>%d</small>",this.totalMoney);
        return strAttr;
    }
}
