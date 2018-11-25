package com.szty.baohuzhu;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.szty.baohuzhu.adapter.ProjectAdapter;
import com.szty.baohuzhu.adapter.ProjectItem;
import com.szty.baohuzhu.webapi.WebServiceManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;
import java.util.zip.Inflater;

public class ProjectActivity extends AppCompatActivity {

    ListView            mListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);

        WebServiceManager.initManager(this);

        mListView = findViewById(R.id.list_help);
        mListView.setDividerHeight(0);
        updateProjectList();

    }

    private void updateProjectList(){
        WebServiceManager.getInstance().getProjectList(0, 10, new WebServiceManager.HttpCallback() {
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
                            projectItem.setID(item.getInt("id"));
                            projectItem.setType(item.getInt("type"));
                            projectItem.setTitle(item.getString("title"));
                            projectItem.setTotalMoney(item.getInt("totalMoney"));
                            projectItem.setCloseTime(item.getString("closeTime"));
                            projectItem.setEndTime(item.getString("endTime"));
                            projectItem.setStartTime(item.getString("startTime"));
                            projectItem.setNeedNum(item.getInt("needNum"));
                            projectItem.setInterestRate(item.getString("interestRate"));
                            projectItem.setBidNum(item.getInt("bidNum"));
                            projectItem.setCompleteNum(item.getInt("completeNum"));
                            projectItem.setMonth(item.getInt("timeLimit"));
                            projectItem.setHelpSelfMoney(item.getString("price"));
                            projectList.add(projectItem);


                        }
                        mListView.setAdapter(new ProjectAdapter(projectList,ProjectActivity.this));

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
