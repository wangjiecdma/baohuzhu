package com.szty.baohuzhu.fragments;

import android.util.Log;
import android.widget.ListView;

import com.szty.baohuzhu.ProjectActivity;
import com.szty.baohuzhu.R;
import com.szty.baohuzhu.adapter.ProjectAdapter;
import com.szty.baohuzhu.adapter.ProjectItem;
import com.szty.baohuzhu.webapi.WebServiceManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

public class FragmentMyProjects extends FragmentBase {
    private ListView mListView;
    public FragmentMyProjects(){
        super();
        layoutId = R.layout.activity_project;
        title = "我的项目";

    }

    @Override
    protected void onInitData() {
        mListView = getView().findViewById(R.id.list_help);


        WebServiceManager.getInstance().myHelpList2(0, 10, new WebServiceManager.HttpCallback() {
            @Override
            public void onResonse(boolean sucess, String body) {

                Log.d("www","onResponse :"+sucess + " value:\n"+body);

                if(sucess) {
                    try {
                        JSONObject jsonObject = new JSONObject(body);
                        JSONArray list =jsonObject.getJSONObject("datas").getJSONObject("page").getJSONArray("datas");
                        List<ProjectItem> projectList = new LinkedList<>();
                        for (int i =0;i<list.length();i++){
                            JSONObject item = list.getJSONObject(i);
                            ProjectItem  projectItem = new ProjectItem();
//                            projectItem.setID(item.getInt("id"));
//                            projectItem.setType(item.getInt("type"));
//                            projectItem.setTitle(item.getString("title"));
//                            projectItem.setTotalMoney(item.getInt("totalMoney"));
//                            projectItem.setCloseTime(item.getString("closeTime"));
//                            projectItem.setEndTime(item.getString("endTime"));
//                            projectItem.setStartTime(item.getString("startTime"));
//                            projectItem.setNeedNum(item.getInt("needNum"));
//                            projectItem.setInterestRate(item.getString("interestRate"));
//                            projectItem.setBidNum(item.getInt("bidNum"));
//                            projectItem.setCompleteNum(item.getInt("completeNum"));
//                            projectItem.setMonth(item.getInt("timeLimit"));
//                            projectItem.setHelpSelfMoney(item.getString("price"));
                            projectItem.initFromJson(item);
                            projectList.add(projectItem);


                        }
                        mListView.setAdapter(new ProjectAdapter(projectList,getContext()));

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }else{
                    Log.d("www","update list error");
                }
            }
        });
    }
}
