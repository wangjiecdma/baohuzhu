package com.szty.baohuzhu.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

import com.szty.baohuzhu.ProjectActivity;
import com.szty.baohuzhu.R;

import java.util.List;

public class ProjectAdapter extends BaseAdapter {

    private List<ProjectItem > mList;
    private Context mContext;
    public ProjectAdapter(List<ProjectItem> list, Context context){
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
        LinearLayout project =  (LinearLayout) LayoutInflater.from(mContext).inflate(R.layout.project_view,null);
        project.findViewById(R.id.project_btn_help).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("www","project left button click ");
            }
        });
        project.setTag(item);



        return project;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
