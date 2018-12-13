package com.szty.baohuzhu.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.szty.baohuzhu.R;
import com.szty.baohuzhu.activitys.ActivityManager;

import java.util.HashMap;
import java.util.List;

public class SucessProjectAdapter extends BaseAdapter {

    private List<ProjectItem > mList;
    private Context mContext;
    public SucessProjectAdapter(List<ProjectItem> list, Context context){
        mList = list;
        mContext = context;
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ProjectItem item  = mList.get(position);
        LinearLayout project =  (LinearLayout) LayoutInflater.from(mContext).inflate(R.layout.sucess_project,null);


        TextView title = project.findViewById(R.id.sucess_help_text);
        title.setText(item.getTitle());
        TextView type = project.findViewById(R.id.sucess_firstsid_text);
        if (item.getType() ==1){
            type.setText("首标");
        }else{
            type.setText("续标");
        }

        TextView project_rate = project.findViewById(R.id.sucess_rate_text);
        project_rate.setText(item.getInterestRate());

        TextView total = project.findViewById(R.id.project_total);
        total.setText(Integer.toString(item.getTotalMoney()));



        TextView sucessdate = project.findViewById(R.id.sucess_date_edit);
        sucessdate.setText(item.getCompleteTime());


        TextView project_duration = project.findViewById(R.id.project_duration);
        project_duration.setText(item.getDurationString());



//        TextView project_helpcount = project.findViewById(R.id.project_helpcount);
//        project_helpcount.setText(item.getHelpSelfMoney());

//        project.findViewById(R.id.project_btn_help).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ActivityManager.startFragment(mContext,"我要授权");
//            }
//        });
//
//         project.findViewById(R.id.project_btn_bidding).setOnClickListener(new View.OnClickListener() {
//             @Override
//             public void onClick(View v) {
//                 ActivityManager.startFragment(mContext,"我要竞标");
//
//             }
//         });
//
        project.findViewById(R.id.sucess_listItem).setOnClickListener(new ClickListener(item));

        return project;
    }

    private class ClickListener implements View.OnClickListener {
        ProjectItem mItem;
        public ClickListener(ProjectItem item) {
            mItem=item;
        }

        @Override
        public void onClick(View v) {
            Log.d("www","OnClickListener\n");
            HashMap mapParam = new HashMap();
            mapParam.put("projectId",mItem.getID());
            if (mItem.getType() ==1){
                //首标详情
                ActivityManager.startFragment(mContext,"项目详情",mapParam);
            }else{
                //续标详情
                ActivityManager.startFragment(mContext,"项目详情2",mapParam);
            }

        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
