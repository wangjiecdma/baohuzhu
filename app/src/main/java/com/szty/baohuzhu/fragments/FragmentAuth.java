package com.szty.baohuzhu.fragments;

import android.app.Dialog;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.szty.baohuzhu.R;
import com.szty.baohuzhu.adapter.AccountInfo;
import com.szty.baohuzhu.adapter.ProjectItem;
import com.szty.baohuzhu.adapter.UserStatus;
import com.szty.baohuzhu.utils.CommomDialog;
import com.szty.baohuzhu.utils.ConvertUtil;
import com.szty.baohuzhu.webapi.WebServiceManager;

import org.json.JSONObject;

import java.util.HashMap;

import android.widget.TextView.OnEditorActionListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Toast;

public class FragmentAuth extends FragmentBase {
    private int projectID;
    ProjectItem mProjectItem;
    AccountInfo mAccountInfo;

    public FragmentAuth(){
        super();
        title="我要授权";
        layoutId=R.layout.project_authorization_support;
    }

    @Override
    protected void onInitData() {
        super.onInitData();

        EditText balInput = (EditText) findViewById(R.id.project_authsupport_authorize1_text);
        balInput.setOnEditorActionListener(new TextListener(this));

        findViewById(R.id.project_authsupport_confirm_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText balInput = (EditText) findViewById(R.id.project_authsupport_authorize1_text);
                TextView notText = (TextView) findViewById(R.id.project_authsupport_authorize2_text);

                WebServiceManager.getInstance().bidHelp(2, projectID, balInput.getText().toString(), notText.getText().toString(), new WebServiceManager.HttpCallback() {
                    @Override
                    public void onResonse(boolean sucess, String body) {
                        if(sucess){
                            Toast.makeText(getContext(), "授权成功", Toast.LENGTH_SHORT).show();

                            //需要发送消息，通知其它相关方更新对应的数据
                            Intent mIntent = new Intent("hzb.dataChanged");
                            mIntent.putExtra("message","this message is from subActivity");
                            LocalBroadcastManager.getInstance(getContext()).sendBroadcast(mIntent);

                            getActivity().finish();

                        }
                        else {

                            Toast.makeText(getContext(), "授权失败", Toast.LENGTH_SHORT).show();
                            getActivity().finish();
                        }
                    }
                });


            }
        });
        findViewById(R.id.project_authsupport_cancle_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });



        Intent intent = getActivity().getIntent();
        HashMap map=(HashMap)intent.getSerializableExtra("userParam");
        projectID = Integer.parseInt(map.get("projectId").toString());

        WebServiceManager.getInstance().getHelpDetail(ProjectItem.PROJECT_AUTH_DETAIL, projectID, new WebServiceManager.HttpCallback() {
            @Override
            public void onResonse(boolean sucess, String body) {

                Log.d("www", "onResponse :" + sucess + " value:\n" + body);

                if (sucess) {
                    try {
                        JSONObject jsonObject = new JSONObject(body);
                        JSONObject jsProjectInfo = jsonObject.getJSONObject("datas").getJSONObject("info");
                        JSONObject jsAccountInfo =jsonObject.getJSONObject("datas").getJSONObject("account");

//                        ProjectItem projectItem = new ProjectItem();
//                        projectItem.initFromJson(jsProjectInfo);
//                        AccountInfo accountInfo = new AccountInfo();
//                        accountInfo.initFromJson(jsAccountInfo);
//
//                        updateUI(projectItem,accountInfo);
                        mProjectItem = new ProjectItem();
                        mProjectItem.initFromJson(jsProjectInfo);
                        mAccountInfo = new AccountInfo();
                        mAccountInfo.initFromJson(jsAccountInfo);

                        updateUI(mProjectItem,mAccountInfo);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.d("www", "getHelpDetail error");
                }
            }
        });


    }

    private void updateUI(ProjectItem pItem, AccountInfo aInfo){

        TextView title = (TextView) findViewById(R.id.project_title);
        title.setText(pItem.getTitle());

        TextView total = (TextView) findViewById(R.id.project_authsupport_amountnum_text);
        total.setText(String.format("%d",pItem.getTotalMoney()));
        TextView dura = (TextView) findViewById(R.id.project_authsupport_datenum_text);
        dura.setText(pItem.getDurationString());

        TextView rate = (TextView) findViewById(R.id.project_authsupport_ratenum_text);
        rate.setText(pItem.getInterestRate());
        TextView needPersonNum = (TextView) findViewById(R.id.project_authsupport_PopTotalnum_text);
        needPersonNum.setText(String.format("%d人", pItem.getNeedNum()));
        TextView PersonNum = (TextView) findViewById(R.id.project_authsupport_joinnum_text);
        PersonNum.setText(String.format("%d人", pItem.getCompleteNum()));
        TextView bidPersonNum = (TextView) findViewById(R.id.project_authsupport_submitnum_text);
        bidPersonNum.setText(String.format("%d人", pItem.getBidNum()));
        TextView projectPeriod = (TextView) findViewById(R.id.project_period);
        projectPeriod.setText(String.format("%s~%s", pItem.getStartTime(), pItem.getEndTime()));

        TextView closeTime = (TextView) findViewById(R.id.project_authsupport_duedatenum_text);
        closeTime.setText(String.format("%s", pItem.getCloseTime()));

        TextView selfNum = (TextView) findViewById(R.id.project_authsupport_demandbidding_text);
        selfNum.setText(pItem.getHelpSelfMoney());


        TextView balance = (TextView) findViewById(R.id.project_authsupport_withdrawals_text);
        balance.setText(aInfo.getBalance());

        TextView notBalance = (TextView) findViewById(R.id.project_authsupport_No_withdrawals_text);
        notBalance.setText(aInfo.getNoRechargeBalance());



        float selfCount  = ConvertUtil.convertToFloat(pItem.getHelpSelfMoney(), 0);
        float bal  = ConvertUtil.convertToFloat(aInfo.getBalance(), 0);
        float notbal  = ConvertUtil.convertToFloat(aInfo.getNoRechargeBalance(), 0);
        if( selfCount > bal + notbal) {

            String strTips = String.format("余额不足，本项目需要%s, 你的余额是%.2f，请去充值。", pItem.getHelpSelfMoney(), bal + notbal);
            //弹出提示框
            new CommomDialog(getContext(), R.style.dialoghzb, strTips, new CommomDialog.OnCloseListener() {
                @Override
                public void onClick(Dialog dialog, boolean confirm) {
                    if (confirm) {
                        //Toast.makeText(getContext(),"点击确定", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();

                        getActivity().finish();
                    }
                    else {
                        getActivity().finish();
                    }

                }
            })
                    .setTitle("提示").show();

            return;
        }

        TextView balInput = (TextView) findViewById(R.id.project_authsupport_authorize1_text);
        TextView notText = (TextView) findViewById(R.id.project_authsupport_authorize2_text);
        if( bal >= selfCount){

            balInput.setText(pItem.getHelpSelfMoney());
            notText.setText("0");

        }
        else{
            balInput.setText(aInfo.getBalance());

            String str = String.format("%.2f", selfCount - bal);
            notText.setText(str);

        }






    }

    private class TextListener implements TextView.OnEditorActionListener{
        FragmentAuth myAuth;

        public TextListener(FragmentAuth auth) {
            myAuth=auth;
        }

        @Override
        public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {

            if (i == EditorInfo.IME_ACTION_SEARCH || i == EditorInfo.IME_ACTION_DONE ||
                    i == EditorInfo.IME_ACTION_NEXT || i == EditorInfo.IME_ACTION_SEND) {
                Log.i("---","操作执行");
                myAuth.UpdateAuthNum();

            }

            return false;
        }
    }

    public void UpdateAuthNum(){

        EditText balInput = (EditText) findViewById(R.id.project_authsupport_authorize1_text);
        TextView notText = (TextView) findViewById(R.id.project_authsupport_authorize2_text);

        float selfCount  = ConvertUtil.convertToFloat(mProjectItem.getHelpSelfMoney(), 0);
        float bal  = ConvertUtil.convertToFloat(mAccountInfo.getBalance(), 0);
        float notbal  = ConvertUtil.convertToFloat(mAccountInfo.getNoRechargeBalance(), 0);

        float inpuValue = ConvertUtil.convertToFloat(balInput.getText().toString(),0);
        if(inpuValue > bal){
            Toast.makeText(getContext(),"输入数字超过余额数了", Toast.LENGTH_SHORT).show();

            balInput.setText(mAccountInfo.getBalance());

            String str = String.format("%.2f", selfCount - bal);
            notText.setText(str);

        }
        else if(selfCount - inpuValue > notbal ){
            Toast.makeText(getContext(),"输入数字导致超过不可提现余额数了", Toast.LENGTH_SHORT).show();

            if( bal >= selfCount){

                balInput.setText(mProjectItem.getHelpSelfMoney());
                notText.setText("0");

            }
            else{
                balInput.setText(mAccountInfo.getBalance());

                String str = String.format("%.2f", selfCount - bal);
                notText.setText(str);

            }


        }
        else if(inpuValue < 0){
            Toast.makeText(getContext(),"输入无效", Toast.LENGTH_SHORT).show();

            balInput.setText(mAccountInfo.getBalance());

            String str = String.format("%.2f", selfCount - bal);
            notText.setText(str);
        }
        else {
//            balInput.setText(mAccountInfo.getBalance());

            String str = String.format("%.2f", selfCount - inpuValue);
            notText.setText(str);
        }


    }


    private class ButtonListener implements TextView.OnClickListener{

        @Override
        public void onClick(View view) {
            EditText balInput = (EditText) findViewById(R.id.project_authsupport_authorize1_text);
            TextView notText = (TextView) findViewById(R.id.project_authsupport_authorize2_text);

        }
    }

}
