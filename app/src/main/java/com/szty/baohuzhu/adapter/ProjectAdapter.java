package com.szty.baohuzhu.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.szty.baohuzhu.ProjectActivity;
import com.szty.baohuzhu.R;

import org.w3c.dom.Text;

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

        TextView title = project.findViewById(R.id.project_name);
        title.setText(item.getTitle());
        TextView type = project.findViewById(R.id.project_name2);
        if (item.getType() ==1){
            type.setText("首标");
        }else{
            type.setText("续标");
        }
        String str = String.format("%d个月期限 | %d人 | 还差%d人 | %d人竞标中",item.getMonth(),item.getNeedNum(),item.getCompleteNum(),item.getNeedNum()-item.getCompleteNum(),item.getBidNum());

        TextView project_detail = project.findViewById(R.id.project_detail);
        project_detail.setText(str);

        TextView total = project.findViewById(R.id.project_total);
        total.setText(Integer.toString(item.getTotalMoney()));

        TextView project_rate = project.findViewById(R.id.project_rate);
        project_rate.setText(item.getInterestRate());

        TextView project_helpcount = project.findViewById(R.id.project_helpcount);
        project_helpcount.setText(item.getHelpSelfMoney());



        return project;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
