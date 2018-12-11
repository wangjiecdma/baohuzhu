package com.szty.baohuzhu.fragments;

import com.szty.baohuzhu.R;
import com.szty.baohuzhu.adapter.ProjectAdapter;
import com.szty.baohuzhu.adapter.ProjectItem;
import com.szty.baohuzhu.webapi.WebServiceManager;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
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
//        if (projectItem.getType() ==1){
//            type.setText("首标");
//        }else{
//            type.setText("续标");
//        }

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

    return projectDetail;

    }

    public void setUI(ProjectItem  projectItem){
        titleProject.setText(projectItem.getTitle());

        if (projectItem.getType() ==1){
            type.setText("首标");
        }else{
            type.setText("续标");
        }


        totalCount.setText(Integer.toString(projectItem.getTotalMoney()));


        dur.setText(Integer.toString(projectItem.getMonth()));


        project_rate.setText(projectItem.getInterestRate());

        needPerson.setText(Integer.toString(projectItem.getNeedNum()));

        takePartin.setText(Integer.toString(projectItem.getCompleteNum()));

        bidNum.setText(Integer.toString(projectItem.getBidNum()));


        String str = String.format("%s~%s", projectItem.getStartTime(), projectItem.getEndTime());

        duration.setText(str);

    }

    @Override
    protected void onInitData() {
        super.onInitData();

        Intent intent = getActivity().getIntent();
        HashMap map=(HashMap)intent.getSerializableExtra("userParam");
        projectID = Integer.parseInt(map.get("projectId").toString());

        WebServiceManager.getInstance().getHelpDetail(1, projectID, new WebServiceManager.HttpCallback() {
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
                        projectItem.setID(projectInfo.getInt("id"));
                        projectItem.setType(projectInfo.getInt("type"));
                        projectItem.setTitle(projectInfo.getString("title"));
                        projectItem.setTotalMoney(projectInfo.getInt("totalMoney"));
                        projectItem.setCloseTime(projectInfo.getString("closeTime"));
                        projectItem.setEndTime(projectInfo.getString("endTime"));
                        projectItem.setStartTime(projectInfo.getString("startTime"));
                        projectItem.setNeedNum(projectInfo.getInt("needNum"));
                        projectItem.setInterestRate(projectInfo.getString("interestRate"));
                        projectItem.setBidNum(projectInfo.getInt("bidNum"));
                        projectItem.setCompleteNum(projectInfo.getInt("completeNum"));
                        projectItem.setMonth(projectInfo.getInt("timeLimit"));
                        projectItem.setHelpSelfMoney(projectInfo.getString("price"));


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
