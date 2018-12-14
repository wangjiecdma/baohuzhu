package com.szty.baohuzhu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.szty.baohuzhu.R;

import java.util.List;

public class ProjectMembersAdapter extends BaseAdapter {

    private List<ProjectMembers > mList;
    private Context mContext;
    public ProjectMembersAdapter(List<ProjectMembers> list, Context context){
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

        ProjectMembers item  = mList.get(position);
        LinearLayout project =  (LinearLayout) LayoutInflater.from(mContext).inflate(R.layout.participant_item,null);


        TextView alias_no = project.findViewById(R.id.partin_alias);
        alias_no.setText(item.getAliasNo());

        TextView mem_attr = project.findViewById(R.id.mem_attr);
        if (item.getType() ==1){
            mem_attr.setText("竞标");
        }else{
            mem_attr.setText("授权");
        }

        TextView mem_in_time = project.findViewById(R.id.mem_in_time);
        mem_in_time.setText(item.getTimePartin());

        return project;
    }



    @Override
    public long getItemId(int position) {
        return position;
    }
}
