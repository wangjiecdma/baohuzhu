package com.wyhzb.hbsc.fragments;

import com.wyhzb.hbsc.R;
import com.wyhzb.hbsc.adapter.PContinuesDetailAdapter;
import com.wyhzb.hbsc.adapter.ProjectMembersAdapter;
import com.wyhzb.hbsc.adapter.ProjectItem;
import com.wyhzb.hbsc.webapi.WebServiceManager;

import java.util.HashMap;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;


import org.json.JSONObject;

public class FragmentProjectDetail extends FragmentBase {
    private int projectID;

    TextView titleProject;
    TextView type;
    TextView totalCount;
    TextView dur;
    TextView project_rate;
    TextView needPerson;
    TextView takePartin;
    TextView bidNum;
    TextView duration;
    TextView closeTime;
    TextView status;
    TextView projectWarning;

    TextView list_text_indicator;
    ListView partin_or_continue ;


    public FragmentProjectDetail(){
        super();
        layoutId= R.layout.project_detail;
        title = "项目详情";

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);

        View projectDetail = inflater.inflate(R.layout.project_detail, container, false);
        titleProject = projectDetail.findViewById(R.id.xx_project_name);
        //title.setText(projectItem.getTitle());
        type = projectDetail.findViewById(R.id.project_attr);


        totalCount = projectDetail.findViewById(R.id.project_amount_edit);
//        totalCount.setText(Integer.toString(projectItem.getTotalMoney()));

        dur = projectDetail.findViewById(R.id.project_date_edit);
//        dur.setText(Integer.toString(projectItem.getMonth()));

        project_rate = projectDetail.findViewById(R.id.project_ratenum_text);
//        project_rate.setText(projectItem.getInterestRate());

        needPerson = projectDetail.findViewById(R.id.project_PopTotalnum_edit);
//        needPerson.setText(Integer.toString(projectItem.getNeedNum()));

        takePartin = projectDetail.findViewById(R.id.preject_joinnum_text);
//        takePartin.setText(Integer.toString(projectItem.getCompleteNum()));


        bidNum = projectDetail.findViewById(R.id.project_biddingnum_text);
//        bidNum.setText(Integer.toString(projectItem.getBidNum()));


//        String str = String.format("%s~%s", projectItem.getStartTime(), projectItem.getEndTime());
        duration = projectDetail.findViewById(R.id.project_datetime_edit);


        closeTime = projectDetail.findViewById(R.id.project_close_time);

        status = projectDetail.findViewById(R.id.project_status_text_value);

        projectWarning = projectDetail.findViewById(R.id.project_warning);

        list_text_indicator = projectDetail.findViewById(R.id.list_text_indicator);

        partin_or_continue =  projectDetail.findViewById(R.id.partin_or_continue_list);



    return projectDetail;

    }

    public void setUI(ProjectItem  projectItem){
        titleProject.setText(projectItem.getTitle());

        type.setText(projectItem.getAttrStr());


        totalCount.setText(Integer.toString(projectItem.getTotalMoney()));


        dur.setText(projectItem.getDurationString());


        project_rate.setText(projectItem.getInterestRate());

        needPerson.setText(String.format("%d人", projectItem.getNeedNum()));

        takePartin.setText(String.format("%d人", projectItem.getCompleteNum()));

        bidNum.setText(String.format("%d人", projectItem.getBidNum()));


        String str = String.format("%s~%s", projectItem.getStartTime(), projectItem.getEndTime());

        duration.setText(str);

        closeTime.setText(projectItem.getCloseTime());

        status.setText(projectItem.getStatusStr());

        projectWarning.setText(Html.fromHtml(projectItem.getProjectWarningStrWithHtml()));

        if(projectItem.getContinueTimes() == 0) {
            list_text_indicator.setText(Html.fromHtml("<small>标的参与者：</small>"));;
            partin_or_continue.setAdapter(new ProjectMembersAdapter(projectItem.getProjectMembers(), getContext()));
        }
        else{
            list_text_indicator.setText(Html.fromHtml("<small>标的续标详情：</small>"));
            partin_or_continue.setAdapter(new PContinuesDetailAdapter(projectItem.getProjectContinues(), getContext()));
        }

    }

    @Override
    protected void onInitData() {
        super.onInitData();

        Intent intent = getActivity().getIntent();
        HashMap map=(HashMap)intent.getSerializableExtra("userParam");
        projectID = Integer.parseInt(map.get("projectId").toString());

        WebServiceManager.getInstance().getHelpDetail(ProjectItem.PROJECT_DETAIL, projectID, new WebServiceManager.HttpCallback() {
            @Override
            public void onResonse(boolean sucess, String body) {

                Log.d("www","onResponse :"+sucess + " value:\n"+body);

                if(sucess) {
                    try {
                        JSONObject jsonObject = new JSONObject(body);
//                        JSONArray list =jsonObject.getJSONObject("datas").getJSONObject("page").getJSONArray("datas");
                        JSONObject projectInfo =jsonObject.getJSONObject("datas").getJSONObject("info");
                        //JSONObject accountInfo =jsonObject.getJSONObject("datas").getJSONObject("account");

                        ProjectItem  projectItem = new ProjectItem();
                        projectItem.initFromJson(projectInfo);
//                        projectItem.setID(projectInfo.getInt("id"));
//                        projectItem.setType(projectInfo.getInt("type"));
//                        projectItem.setTitle(projectInfo.getString("title"));
//                        projectItem.setTotalMoney(projectInfo.getInt("totalMoney"));
//                        projectItem.setCloseTime(projectInfo.getString("closeTime"));
//                        projectItem.setEndTime(projectInfo.getString("endTime"));
//                        projectItem.setStartTime(projectInfo.getString("startTime"));
//                        projectItem.setNeedNum(projectInfo.getInt("needNum"));
//                        projectItem.setInterestRate(projectInfo.getString("interestRate"));
//                        projectItem.setBidNum(projectInfo.getInt("bidNum"));
//                        projectItem.setCompleteNum(projectInfo.getInt("completeNum"));
//                        projectItem.setMonth(projectInfo.getInt("timeLimit"));
//                        projectItem.setHelpSelfMoney(projectInfo.getString("price"));


//                        LinearLayout projectDetail =  (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.project_detail,null);
//                        TextView title = projectDetail.findViewById(R.id.xx_project_name);
//                        title.setText(projectItem.getTitle());
//                        TextView type = projectDetail.findViewById(R.id.project_attr);
//                        if (projectItem.getType() ==1){
//                            type.setText("首标");
//                        }else{
//                            type.setText("续标");
//                        }
//
//                        TextView totalCount = projectDetail.findViewById(R.id.project_amount_edit);
//                        totalCount.setText(Integer.toString(projectItem.getTotalMoney()));
//
//                        TextView total = projectDetail.findViewById(R.id.project_date_edit);
//                        total.setText(Integer.toString(projectItem.getMonth()));
//
//                        TextView project_rate = projectDetail.findViewById(R.id.project_ratenum_text);
//                        project_rate.setText(projectItem.getInterestRate());
//
//                        TextView needPerson = projectDetail.findViewById(R.id.project_PopTotalnum_edit);
//                        needPerson.setText(Integer.toString(projectItem.getNeedNum()));
//
//                        TextView takePartin = projectDetail.findViewById(R.id.preject_joinnum_text);
//                        takePartin.setText(Integer.toString(projectItem.getCompleteNum()));
//
//
//                        TextView bidNum = projectDetail.findViewById(R.id.project_biddingnum_text);
//                        bidNum.setText(Integer.toString(projectItem.getBidNum()));
//
//
//                        String str = String.format("%s~%s", projectItem.getStartTime(), projectItem.getEndTime());
//                        TextView duration = projectDetail.findViewById(R.id.project_datetime_edit);
//
//                        duration.setText(str);
//
//                        projectDetail.invalidate();
                        setUI(projectItem);

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }else{
                    Log.d("www","getHelpDetail error");
                }

            }
        });


    }
}
