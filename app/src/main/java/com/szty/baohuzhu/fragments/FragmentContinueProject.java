package com.szty.baohuzhu.fragments;

import android.app.Dialog;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.szty.baohuzhu.R;
import com.szty.baohuzhu.adapter.Durations;
import com.szty.baohuzhu.adapter.ProjectItem;
import com.szty.baohuzhu.utils.ActionSheetDialog;
import com.szty.baohuzhu.utils.CommomDialog;
import com.szty.baohuzhu.webapi.WebServiceManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class FragmentContinueProject extends FragmentBase {
    private int projectID;
    ProjectItem mProjectItem;
    ArrayList<Durations> mDuraList;
    int mDuraIndex;
    TextView mDurationTextView;

    public FragmentContinueProject(){
        super();
        layoutId = R.layout.project_continue;
        title="我要续标";
    }

    @Override
    protected void onInitData() {
        super.onInitData();
        Intent intent = getActivity().getIntent();
        HashMap map=(HashMap)intent.getSerializableExtra("userParam");
        projectID = Integer.parseInt(map.get("projectId").toString());

        WebServiceManager.getInstance().getContinueDetail(4, projectID, new WebServiceManager.HttpCallback() {
            @Override
            public void onResonse(boolean sucess, String body) {
                try {
                    JSONObject jsonObject = new JSONObject(body);
                    String msg = jsonObject.getString("msg");

                    if(sucess){

                        JSONObject jsDatas = jsonObject.getJSONObject("datas");
                        JSONObject jsProject  = jsDatas.getJSONObject("info");
                        JSONArray  jsDuraArray  = jsDatas.getJSONArray("months");

                        mProjectItem = new ProjectItem();
                        mProjectItem.initFromJson(jsProject);

                        mDuraList = new ArrayList<Durations>();
                        if(jsDuraArray != null) {
                            for (int i = 0; i < jsDuraArray.length(); i++) {
                                JSONObject oj = jsDuraArray.getJSONObject(i);
                                Durations dura = new Durations();
                                dura.initFromJson(oj);
                                mDuraList.add(dura);

                            }
                        }

                        UpdateUI();

                    }
                    else{
                        toShowErrorMsg(msg,true);

                    }

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });


        mDurationTextView = (TextView)findViewById(R.id.project_continue_dura_text);
        findViewById(R.id.project_continue_dura_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showActionSheetDialog(view);
            }
        });


        findViewById(R.id.project_continue_confirm_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(mDurationTextView.getText().toString().equals("")){
                    toShowErrorMsg("还没选择续标期限", false);
                    return;
                }

                WebServiceManager.getInstance().saveContinue(mDuraList.get(mDuraIndex).getType(), projectID,
                        mDuraList.get(mDuraIndex).getDuration(), mDuraList.get(mDuraIndex).getRate(), new WebServiceManager.HttpCallback() {
                            @Override
                            public void onResonse(boolean sucess, String body) {
                                try {
                                    JSONObject jsonObject = new JSONObject(body);
                                    String msg = jsonObject.getString("msg");

                                    if(sucess){

                                        JSONObject jsDatas = jsonObject.getJSONObject("datas");
                                        toShowErrorMsg(msg, true);
                                        //getActivity().finish();
                                    }
                                    else{
                                        toShowErrorMsg(msg, false);

                                    }
                                }catch (Exception e){
                                    e.printStackTrace();
                                }
                            }
                        });


            }
        });
        findViewById(R.id.project_continue_cancle_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();

            }
        });


    }

    private void toShowErrorMsg(String msg, final boolean isFinish){
        //弹出提示框
        new CommomDialog(getContext(), R.style.dialog, msg, new CommomDialog.OnCloseListener() {
            @Override
            public void onClick(Dialog dialog, boolean confirm) {
                if(confirm){
                    dialog.dismiss();
                    //getActivity().finish();
                }
                else{
                    dialog.dismiss();
                    //getActivity().finish();
                }

                if(isFinish){
                    getActivity().finish();
                }


            }
        })
                .setTitle(" ").show();
    }

    public void showActionSheetDialog(View view){

        ActionSheetDialog dialog = new ActionSheetDialog(getContext());
        dialog.builder()
                .setCancelable(true)
                .setCanceledOnTouchOutside(true);

        ActionSheetDialog.OnSheetItemClickListener actionSheetListener = new ActionSheetDialog.OnSheetItemClickListener() {
            @Override
            public void onClick(int which) {
                Log.d("ActionSheetDialog", String.format("选择了第%d个", which));
                mDuraIndex = which -1;

                mDurationTextView.setText(mDuraList.get(mDuraIndex).getDurationString());
                ((TextView)findViewById(R.id.project_continue_datenum_text)).setText(mDuraList.get(mDuraIndex).getRate());
            }
        };

        for(int i = 0;  i < mDuraList.size(); i++) {
            dialog.addSheetItem(mDuraList.get(i).getDurationString(), ActionSheetDialog.SheetItemColor.Blue, actionSheetListener);
        }
        dialog.show();

    }

    private void  UpdateUI(){

        ((TextView)findViewById(R.id.project_continue_title)).setText(mProjectItem.getTitle());

        ((TextView)findViewById(R.id.project_continue_amountnum_text)).setText(String.format("%d", mProjectItem.getTotalMoney()));

        ((TextView)findViewById(R.id.project_continue_period)).setText(String.format("%s~%s", mProjectItem.getStartTime(), mProjectItem.getEndTime()));

        ((TextView)findViewById(R.id.project_continue_closetime)).setText(mProjectItem.getCloseTime());



    }
}
