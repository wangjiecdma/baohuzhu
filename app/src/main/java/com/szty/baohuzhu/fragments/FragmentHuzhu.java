package com.szty.baohuzhu.fragments;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.szty.baohuzhu.ProjectActivity;
import com.szty.baohuzhu.R;
import com.szty.baohuzhu.activitys.ActivityManager;
import com.szty.baohuzhu.adapter.ProjectAdapter;
import com.szty.baohuzhu.adapter.ProjectItem;
import com.szty.baohuzhu.webapi.WebServiceManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

public class FragmentHuzhu extends FragmentBase implements View.OnClickListener{
    public FragmentHuzhu(){
        super();
        layoutId= R.layout.segment;
    }

    @Override
    protected void onInitData() {
        findViewById(R.id.segment_index1).setOnClickListener(this);
        findViewById(R.id.segment_index2).setOnClickListener(this);
        findViewById(R.id.segment_index3).setOnClickListener(this);


        // 在此处添加初始化数据的代码:
        WebServiceManager.getInstance().getSucessList(0, 100, new WebServiceManager.HttpCallback() {
            @Override
            public void onResonse(boolean sucess, String body) {

                //成功案例列表
            }
        });

        WebServiceManager.getInstance().getProjectList(0, 100, new WebServiceManager.HttpCallback() {
            @Override
            public void onResonse(boolean sucess, String body) {

            }
        });


        //默认选中第一个segment
        onClick(findViewById(R.id.segment_index1));

        findViewById(R.id.new_project).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                ActivityManager.startFragment(getContext(),"我要创建");

            }
        });

        initSucessProjectList();
        initProjectList();
        initMyProjectList();
    }


    private void initProjectList(){
        ListView list = (ListView)findViewById(R.id.huzhu_listview);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("www","project item click ");
                ActivityManager.startFragment(getContext(),"项目详情");
            }
        });
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
                        ListView listView = (ListView)findViewById(R.id.huzhu_listview) ;

                        listView.setAdapter(new ProjectAdapter(projectList,getContext()));

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }else{
                    Log.d("www","update list error");
                }
            }
        });
    }

    private void initSucessProjectList(){

        ListView listView = (ListView)findViewById(R.id.sucecss_listview);

        listView.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return 10;
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View  view =LayoutInflater.from(getContext()).inflate(R.layout.sucess_project,null);

                return view;
            }
        });
    }

    private void initMyProjectList(){
        ListView listView = (ListView)findViewById(R.id.listview_history);
        listView.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return 5;
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View  view =LayoutInflater.from(getContext()).inflate(R.layout.project_view,null);

                return view;
            }
        });
    }

    @Override
    public void onClick(View v) {
        TextView leftText = (TextView)findViewById(R.id.segment_text_left);
        TextView centerText = (TextView)findViewById(R.id.segment_text_center);
        TextView rightText = (TextView)findViewById(R.id.segment_text_right);
        leftText.setTextColor(Color.BLACK);
        rightText.setTextColor(Color.BLACK);
        centerText.setTextColor(Color.BLACK);

        findViewById(R.id.segment_line_left).setVisibility(View.INVISIBLE);
        findViewById(R.id.segment_line_center).setVisibility(View.INVISIBLE);
        findViewById(R.id.segment_line_right).setVisibility(View.INVISIBLE);


        findViewById(R.id.create_panel).setVisibility(View.INVISIBLE);
        findViewById(R.id.huzhu_panel).setVisibility(View.INVISIBLE);
        findViewById(R.id.sucecss_listview).setVisibility(View.INVISIBLE);

        //set setment to normal state



        //set selected state for setmeng index and panel view
        if (v.getId()== R.id.segment_index1){
            leftText.setTextColor(Color.RED);
            findViewById(R.id.segment_line_left).setVisibility(View.VISIBLE);
            findViewById(R.id.huzhu_panel).setVisibility(View.VISIBLE);

        }else if (v.getId()== R.id.segment_index2){
            centerText.setTextColor(Color.RED);
            findViewById(R.id.segment_line_center).setVisibility(View.VISIBLE);
            findViewById(R.id.create_panel).setVisibility(View.VISIBLE);

        }else if (v.getId()== R.id.segment_index3){
            rightText.setTextColor(Color.RED);
            findViewById(R.id.segment_line_right).setVisibility(View.VISIBLE);
            findViewById(R.id.sucecss_listview).setVisibility(View.VISIBLE);

        }
    }
}
