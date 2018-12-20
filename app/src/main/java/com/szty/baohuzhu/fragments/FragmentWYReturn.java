package com.szty.baohuzhu.fragments;

import android.app.Dialog;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.szty.baohuzhu.R;
import com.szty.baohuzhu.utils.CommomDialog;
import com.szty.baohuzhu.utils.ConvertUtil;
import com.szty.baohuzhu.webapi.WebServiceManager;

import org.json.JSONObject;

import java.util.HashMap;
import com.szty.baohuzhu.adapter.ProjectTakeInDesc;

public class FragmentWYReturn extends FragmentBase {
    private int projectID;
    ProjectTakeInDesc mDesc;
    public FragmentWYReturn(){
        super();
        layoutId = R.layout.project_wyreturn;
        title="我要归还";
    }

    @Override
    protected void onInitData() {
        super.onInitData();

        Intent intent = getActivity().getIntent();
        HashMap map=(HashMap)intent.getSerializableExtra("userParam");
        projectID = Integer.parseInt(map.get("projectId").toString());

        WebServiceManager.getInstance().getWillBackDetail(projectID, new WebServiceManager.HttpCallback() {
            @Override
            public void onResonse(boolean sucess, String body) {
                try {
                    JSONObject jsonObject = new JSONObject(body);
                    String msg = jsonObject.getString("msg");

                    if(sucess){

                        JSONObject jsDatas = jsonObject.getJSONObject("datas");
                        JSONObject jsDesc = jsDatas.getJSONObject("desc");

                        mDesc = new ProjectTakeInDesc();
                        mDesc.initFromJson(jsDesc);
                        UpdateUI(mDesc);

                    }
                    else{
                        toShowErrorMsg(msg, true);

                    }

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        findViewById(R.id.project_wyreturn_usebal_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mDesc.isIs_balanceBack()){

                    float willbackNum  = ConvertUtil.convertToFloat(mDesc.getWillBackMoney(), 0);
                    float bal  = ConvertUtil.convertToFloat(mDesc.getBalance(), 0);;
                    float notbal  = ConvertUtil.convertToFloat(mDesc.getNoRechargeBalance(), 0);

                    if(willbackNum > notbal){
                        bal  = willbackNum - notbal;
                    }
                    else {
                        bal = 0;
                        notbal = willbackNum;
                    }

                    WebServiceManager.getInstance().backByBalance(projectID, bal, notbal, new WebServiceManager.HttpCallback() {
                        @Override
                        public void onResonse(boolean sucess, String body) {

                            try {
                                JSONObject jsonObject = new JSONObject(body);
                                String msg = jsonObject.getString("msg");

                                if(sucess){

                                    JSONObject jsDatas = jsonObject.getJSONObject("datas");

                                    toShowErrorMsg(msg, true);

                                }
                                else{
                                    toShowErrorMsg(msg, true);

                                }

                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    });

                }
                else{
                    toShowErrorMsg("余额不足，请去充值", false);
                }

            }
        });

        findViewById(R.id.project_wyreturn_upload_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void UpdateUI(ProjectTakeInDesc desc){
        ((TextView)findViewById(R.id.project_wyreturn_title)).setText(desc.getTitle());
        ((TextView)findViewById(R.id.project_wyreturn_total_value)).setText(desc.getTotalMutualMoney());
        ((TextView)findViewById(R.id.project_wyreturn_rate_value)).setText(desc.getWillBackMoney());
        ((TextView)findViewById(R.id.project_wyreturn_closedate_value)).setText(desc.getEndTime());
        ((TextView)findViewById(R.id.project_wyreturn_totalbal_value)).setText(desc.getTotalBalance());

        String strTotalBalDetail = String.format("可提现钱包：%s, 不可提现钱包：%s", desc.getBalance(), desc.getNoRechargeBalance());

        ((TextView)findViewById(R.id.project_myreturn_totalbal_detail)).setText(strTotalBalDetail);

        ((TextView)findViewById(R.id.bankbranch_name_text)).setText(desc.getBankName());
        ((TextView)findViewById(R.id.card_number)).setText(desc.getBankCard());


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
}
