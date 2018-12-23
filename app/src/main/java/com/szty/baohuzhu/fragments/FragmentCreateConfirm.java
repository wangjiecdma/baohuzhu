package com.szty.baohuzhu.fragments;

import android.app.Dialog;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.szty.baohuzhu.R;
import com.szty.baohuzhu.activitys.ActivityManager;
import com.szty.baohuzhu.adapter.Durations;
import com.szty.baohuzhu.utils.CommomDialog;
import com.szty.baohuzhu.webapi.WebServiceManager;

import org.json.JSONObject;

import java.util.HashMap;

public class FragmentCreateConfirm extends FragmentBase {
    HashMap mMapParam;
    public FragmentCreateConfirm(){
        super();
        layoutId = R.layout.mycreate_project_reconfirm;
        title="创建项目";
    }

    @Override
    protected void onInitData() {
        super.onInitData();

        Intent intent = getActivity().getIntent();
        mMapParam =(HashMap)intent.getSerializableExtra("userParam");
        JSONObject param = new JSONObject(mMapParam);
        //projectID = Integer.parseInt(map.get("projectId").toString());
        String title = mMapParam.get("title").toString();

        String total = mMapParam.get("totalMoney").toString();
        String duration  = mMapParam.get("month").toString();
        String timeType = mMapParam.get("timeType").toString();
        //String durationStr = map.get("durationStr").toString();
        Durations dura = new Durations();
        dura.setType(Integer.parseInt(timeType));
        dura.setDuration(Integer.parseInt(duration));
        String durationStr = dura.getDurationString();

        String rate = mMapParam.get("rate").toString();
        String needNum = mMapParam.get("needNum").toString();

        String startDate  = mMapParam.get("startTime").toString();
        String endDate  = mMapParam.get("endTime").toString();

        String closeDate = mMapParam.get("closeTime").toString();

        //标的类型
        String type = mMapParam.get("type").toString();


        ((TextView)findViewById(R.id.project_mycreate_confirm_title)).setText(title);

        ((TextView)findViewById(R.id.mycreate_reconfirm_amountnum_text)).setText(total);
        ((TextView)findViewById(R.id.mycreate_reconfirm_datenum_text)).setText(durationStr);

        ((TextView)findViewById(R.id.mycreate_reconfirm_ratenum_text)).setText(rate);
        ((TextView)findViewById(R.id.mycreate_reconfirm_PopTotalnum_text)).setText(String.format("%s人", needNum));

        ((TextView)findViewById(R.id.project_mycreate_confirm_period)).setText(String.format("%s~%s", startDate, endDate));

        ((TextView)findViewById(R.id.mycreate_reconfirm_duedatenum_text)).setText(closeDate);

        if(type.equals("1")){
            ((TextView) findViewById(R.id.mycreate_reconfirm_demandbidding_text)).setText("需求标");
        }
        else {
            ((TextView) findViewById(R.id.mycreate_reconfirm_demandbidding_text)).setText("提供标");
        }


        //处理相关控件的显示和隐藏
        findViewById(R.id.project_mycreate_sucess_tips).setVisibility(View.GONE);
        findViewById(R.id.project_mycreate_return_layout).setVisibility((View.GONE));
        findViewById(R.id.project_mycreate_protocal).setVisibility(View.VISIBLE);
        findViewById(R.id.project_mycreate_pretips_layout).setVisibility(View.VISIBLE);
        findViewById(R.id.project_mycreate_confirmcacel_layout).setVisibility(View.VISIBLE);

        findViewById(R.id.project_mycreate_confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                WebServiceManager.getInstance().createHelp(mMapParam, new WebServiceManager.HttpCallback() {
                    @Override
                    public void onResonse(boolean sucess, String body) {
                        try {
                            JSONObject jsonObject = new JSONObject(body);
                            String msg = jsonObject.getString("msg");

                            if(sucess){

                                JSONObject jsDatas = jsonObject.getJSONObject("datas");

                                findViewById(R.id.project_mycreate_protocal).setVisibility(View.GONE);
                                findViewById(R.id.project_mycreate_pretips_layout).setVisibility(View.GONE);
                                findViewById(R.id.project_mycreate_confirmcacel_layout).setVisibility(View.GONE);
                                findViewById(R.id.project_mycreate_sucess_tips).setVisibility(View.VISIBLE);
                                findViewById(R.id.project_mycreate_return_layout).setVisibility((View.VISIBLE));

                            }
                            else{
                                toShowErrorMsg(msg);

                            }

                        }catch (Exception e){
                            e.printStackTrace();
                        }

                    }
                });

            }
        });

        findViewById(R.id.project_mycreate_return).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getActivity().finish();

            }
        });


    }

    private void toShowErrorMsg(String msg){
        //弹出提示框
        new CommomDialog(getContext(), R.style.dialoghzb, msg, new CommomDialog.OnCloseListener() {
            @Override
            public void onClick(Dialog dialog, boolean confirm) {
                if(confirm){
                    dialog.dismiss();
                    getActivity().finish();
                }
                else{
                    dialog.dismiss();
                    getActivity().finish();
                }


            }
        })
                .setTitle(" ").show();
    }
}
