package com.szty.baohuzhu.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.szty.baohuzhu.R;

import java.util.List;

public class PContinuesDetailAdapter extends BaseAdapter {

    private List<ProjectContinuesDetail > mList;
    private Context mContext;
    public PContinuesDetailAdapter(List<ProjectContinuesDetail> list, Context context){
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

        ProjectContinuesDetail item  = mList.get(position);
        LinearLayout project =  (LinearLayout) LayoutInflater.from(mContext).inflate(R.layout.continues_item,null);


        TextView continue_times = project.findViewById(R.id.continue_times);

        continue_times.setText(Html.fromHtml(item.getContinueTimesStrHtml()));

        TextView continue_duration = project.findViewById(R.id.continue_duration);
        continue_duration.setText(Html.fromHtml(item.getDurationStrHtml()));


        TextView continue_rate = project.findViewById(R.id.continue_rate);
        continue_rate.setText(Html.fromHtml(item.getContinueRateStrHtml()));

        TextView continue_period = project.findViewById(R.id.continue_period);
        continue_period.setText(Html.fromHtml(item.getContinuePeriodStrHtml()));

        return project;
    }



    @Override
    public long getItemId(int position) {
        return position;
    }
}
