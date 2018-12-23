package com.szty.baohuzhu.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.szty.baohuzhu.R;
import com.szty.baohuzhu.activitys.ActivityManager;
import com.szty.baohuzhu.activitys.ActivityUserRegister;
import com.szty.baohuzhu.utils.PreferenceUtils;

import java.util.HashMap;
import java.util.List;

public class ProjectAdapter extends BaseAdapter {

    final static int BUTTON_CLICK_AUTH = 1;
    final static int BUTTON_CLICK_BID = 2;
    final static int BUTTON_CLICK_ITEM = 3;

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
        if (item.getFirst() ==1){
            type.setText("首标");
        }else{
            type.setText("续标");
        }
        //String str = String.format("%d个月期限 | %d人 | 还差%d人 | %d人竞标中",item.getMonth(),item.getNeedNum(),item.getCompleteNum(),item.getNeedNum()-item.getCompleteNum(),item.getBidNum());

        TextView project_detail = project.findViewById(R.id.project_detail);
//        project_detail.setText(str);
        project_detail.setText(item.getProjectDetailStr());

        TextView total = project.findViewById(R.id.project_total);
        total.setText(Integer.toString(item.getTotalMoney()));

        TextView project_rate = project.findViewById(R.id.project_rate);
        project_rate.setText(item.getInterestRate());

        TextView project_helpcount = project.findViewById(R.id.project_helpcount);
        project_helpcount.setText(item.getHelpSelfMoney());

        TextView text_in_authbutton = project.findViewById(R.id.text_in_authbutton);
        text_in_authbutton.setText(item.getAuthStr());

        TextView text_in_bidbutton = project.findViewById(R.id.text_in_bidbutton);
        text_in_bidbutton.setText(item.getStatusStr());

        project.findViewById(R.id.project_btn_help).setOnClickListener(new ClickListener(item, BUTTON_CLICK_AUTH));
//        project.findViewById(R.id.project_btn_help).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(PreferenceUtils.isLogin()) {
//                    ActivityManager.startFragment(mContext, "我要授权");
//                }
//                else{
//                    Intent intent = new Intent(mContext, ActivityUserRegister.class);
//                    mContext.startActivity (intent);
//                }
//            }
//        });

        project.findViewById(R.id.project_btn_bidding).setOnClickListener(new ClickListener(item, BUTTON_CLICK_BID));
//         project.findViewById(R.id.project_btn_bidding).setOnClickListener(new View.OnClickListener() {
//             @Override
//             public void onClick(View v) {
//                 if(PreferenceUtils.isLogin()) {
//                    ActivityManager.startFragment(mContext,"我要竞标");
//                 }
//                 else{
//                     Intent intent = new Intent(mContext, ActivityUserRegister.class);
//                     mContext.startActivity (intent);
//                 }
//
//             }
//         });

         if(item.isAuthButtonEnabled() != true){
             project.findViewById(R.id.project_btn_help).setClickable(false);
             project.findViewById(R.id.project_btn_help).setBackgroundResource(R.drawable.button4);
             project.findViewById(R.id.project_btn_help).setEnabled(false);

         }
         else{
             project.findViewById(R.id.project_btn_help).setBackgroundResource(R.drawable.button1);
         }

         if(item.isBidButtonEnabled() != true){
             project.findViewById(R.id.project_btn_bidding).setClickable(false);
             project.findViewById(R.id.project_btn_bidding).setBackgroundResource(R.drawable.button4);

         }
         else{
             project.findViewById(R.id.project_btn_bidding).setBackgroundResource(R.drawable.button2);
         }



         project.findViewById(R.id.project_listItem).setOnClickListener(new ClickListener(item, BUTTON_CLICK_ITEM));

       return project;
    }

    private class ClickListener implements View.OnClickListener {
        ProjectItem mItem;
        int mButtonClickType;
        public ClickListener(ProjectItem item, int type) {

            mItem=item;
            mButtonClickType = type;
        }

        @Override
        public void onClick(View v) {
            Log.d("www","OnClickListener\n");
            HashMap mapParam = new HashMap();
            mapParam.put("projectId",mItem.getID());

            if(mButtonClickType != BUTTON_CLICK_ITEM){
                if(PreferenceUtils.isLogin() == false) {

                    Intent intent = new Intent(mContext, ActivityUserRegister.class);
                    mContext.startActivity (intent);
                    return;
                }
            }

            if(mButtonClickType == BUTTON_CLICK_ITEM) {
    //            if (mItem.getType() ==1){
    //                //首标详情
    //                ActivityManager.startFragment(mContext,"项目详情",mapParam);
    //            }else{
    //                //续标详情
    //                ActivityManager.startFragment(mContext,"项目详情2",mapParam);
    //            }
                ActivityManager.startFragment(mContext, "项目详情", mapParam);
            }
            else if(mButtonClickType == BUTTON_CLICK_AUTH){
                ActivityManager.startFragment(mContext, "我要授权",mapParam);

            }
            else if (mButtonClickType == BUTTON_CLICK_BID){
                if(mItem.getBidStatus() == ProjectItem.PROJECT_BIDING){
                    ActivityManager.startFragment(mContext,"我要竞标", mapParam);
                }
                else if(mItem.getBidStatus() == ProjectItem.PROJECT_CANCONTINUE){
                    ActivityManager.startFragment(mContext,"我要续标", mapParam);

                }
                else  if(mItem.getBidStatus() == ProjectItem.PROJECT_TO_BE_RETURN ||
                        mItem.getBidStatus() == ProjectItem.PROJECT_OVERDUE){
                    ActivityManager.startFragment(mContext,"我要归还", mapParam);
                }

            }

        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
