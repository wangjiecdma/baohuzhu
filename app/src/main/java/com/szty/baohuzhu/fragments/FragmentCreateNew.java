package com.szty.baohuzhu.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.szty.baohuzhu.R;
import com.szty.baohuzhu.activitys.ActivityManager;
import com.szty.baohuzhu.adapter.AccountInfo;
import com.szty.baohuzhu.adapter.Durations;
import com.szty.baohuzhu.adapter.ProjectItem;
import com.szty.baohuzhu.adapter.UserLevelInfo;
import com.szty.baohuzhu.utils.CommomDialog;
import com.szty.baohuzhu.utils.ConvertUtil;
import com.szty.baohuzhu.webapi.WebServiceManager;

import android.view.View;
import android.view.MotionEvent;
import android.view.inputmethod.InputMethodManager;
import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.view.View.OnTouchListener;

import android.widget.RadioGroup;
import android.widget.Toast;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import com.szty.baohuzhu.utils.ActionSheetDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import java.util.Calendar;
import java.text.SimpleDateFormat;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class FragmentCreateNew extends FragmentBase {
    EditText mPojectTitleInput;
    EditText mTotalInput;
    EditText mNeedPersonsInput;
    TextView mDurationTextView;
    TextView mProjectStartDate;

    TextView mMyShareText;
    int mTotal;
    int mPersonNum;
    float mShare;

    AccountInfo mAccountInfo;
    UserLevelInfo mUserLevel;
    ArrayList<Durations> mDuraList;
    int  mDuraIndex;

    String mStartDatestr;

    RadioButton mRadioButtonNeed;
    RadioButton mRadioButtonSupport;
    boolean mCanCreateNeed;


    public FragmentCreateNew(){
        super();
        layoutId= R.layout.mycreate_project_new;
        title="我要创建";
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  super.onCreateView(inflater, container, savedInstanceState);


//        //Scroll view 不能使用下面的这种方案
//        view.setOnTouchListener(new OnTouchListener() {
//
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//
//                InputMethodManager manager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
//                if(event.getAction() == MotionEvent.ACTION_DOWN){
//                    if(getActivity().getCurrentFocus()!=null && getActivity().getCurrentFocus().getWindowToken()!=null){
//                        manager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
//                    }
//                }
//                return false;
//            }
//        });

        return view;
    }

    private  void validateMyShareAndAuth(float share){

        float bal = ConvertUtil.convertToFloat(mAccountInfo.getBalance(), 0);
        float notbal = ConvertUtil.convertToFloat(mAccountInfo.getNoRechargeBalance(),0);
        if(share > bal + notbal){
            //弹出提示框
            new CommomDialog(getContext(), R.style.dialog, "余额不足，请充值或调整总额或人数", new CommomDialog.OnCloseListener() {
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


                }
            })
                    .setTitle(" ").show();
        }
        else {
            if(bal > share){
                ((TextView)findViewById(R.id.project_mycreate_auth)).setText(String.format("%.2f", share));
                ((TextView)findViewById(R.id.project_mycreate_auth_not)).setText("0");
            }
            else {
                ((TextView)findViewById(R.id.project_mycreate_auth)).setText(mAccountInfo.getBalance());
                String str = String.format("%.2f", share - bal);
                ((TextView)findViewById(R.id.project_mycreate_auth_not)).setText(str);

            }
        }

    }

    @Override
    protected void onInitData() {
        super.onInitData();


        mTotal = 0;
        mPersonNum = 0;
        mShare = 0;
        mCanCreateNeed = true;

        mMyShareText =  (TextView) findViewById(R.id.project_self_share);
        mMyShareText.setText("0");

        mPojectTitleInput = (EditText)findViewById(R.id.mycreate_newproject_edit);

        mTotalInput = (EditText) findViewById(R.id.mycreate_newproject_amount_spinner);
        mTotalInput.setOnEditorActionListener(new TextView.OnEditorActionListener(){
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH || i == EditorInfo.IME_ACTION_DONE ||
                        i == EditorInfo.IME_ACTION_NEXT || i == EditorInfo.IME_ACTION_SEND) {

                        String input = mTotalInput.getText().toString();
                        mTotal = ConvertUtil.convertToInt(input,0);

                        if(mTotal > mUserLevel.getMaxMutualMoney()){
                            mTotalInput.setText(String.format("%d",mUserLevel.getMaxMutualMoney()));
                            mTotal = mUserLevel.getMaxMutualMoney();
                        }
                        if(mPersonNum == 0){
                            mPersonNum = ConvertUtil.convertToInt(mNeedPersonsInput.getText().toString(),0);
                        }
                        if(mPersonNum > 0){
                            mShare = ((float) mTotal)/(float) mPersonNum;
                            String str = String.format("%.2f", mShare);

                            mMyShareText.setText(str);

                            validateMyShareAndAuth(mShare);

                        }

                }

                return false;
            }
        });
        mTotalInput.setOnFocusChangeListener(new android.view.View.OnFocusChangeListener() {
            @Override public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    // 此处为得到焦点时的处理内容

                } else {
                    // 此处为失去焦点时的处理内容
                    String input = mTotalInput.getText().toString();
                    mTotal = ConvertUtil.convertToInt(input,0);
                    if(mTotal > mUserLevel.getMaxMutualMoney()){
                        mTotalInput.setText(String.format("%d",mUserLevel.getMaxMutualMoney()));
                        mTotal = mUserLevel.getMaxMutualMoney();
                    }
                    if(mPersonNum == 0){
                        mPersonNum = ConvertUtil.convertToInt(mNeedPersonsInput.getText().toString(),0);
                    }
                    if(mPersonNum > 0){
                        mShare = ((float) mTotal)/(float) mPersonNum;
                        String str = String.format("%.2f", mShare);

                        mMyShareText.setText(str);
                        //validateMyShareAndAuth(mShare);
                    }

                }
            }
        });

        mDurationTextView = ((TextView)findViewById(R.id.mycreate_newproject_date_spinner));

        mNeedPersonsInput = (EditText) findViewById(R.id.mycreate_newproject_PopTotal_spinner);
        mNeedPersonsInput.setOnEditorActionListener(new TextView.OnEditorActionListener(){
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH || i == EditorInfo.IME_ACTION_DONE ||
                        i == EditorInfo.IME_ACTION_NEXT || i == EditorInfo.IME_ACTION_SEND) {
                    String numStr = mNeedPersonsInput.getText().toString();
                    int num = ConvertUtil.convertToInt(numStr,0);
                    if(num > mUserLevel.getMaxMutualPersons()){
                        mNeedPersonsInput.setText(String.format("%d",mUserLevel.getMaxMutualPersons()));
                        num = mUserLevel.getMaxMutualPersons();
                    }
                    if(num > 0){
                        mPersonNum = num;
                        if(mTotal == 0){
                            mTotal = ConvertUtil.convertToInt(mTotalInput.getText().toString(),0);
                        }
                        mShare = ((float) mTotal)/(float) mPersonNum;
                        String str = String.format("%.2f", mShare);

                        mMyShareText.setText(str);
                        validateMyShareAndAuth(mShare);
                    }
                }
                return false;

            }
        });
        mNeedPersonsInput.setOnFocusChangeListener(new android.view.View.OnFocusChangeListener() {
            @Override public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    // 此处为得到焦点时的处理内容

                } else {
                    // 此处为失去焦点时的处理内容
                    String numStr = mNeedPersonsInput.getText().toString();
                    int num = ConvertUtil.convertToInt(numStr,0);
                    if(num > 0){
                        mPersonNum = num;

                        if(mTotal == 0){
                            mTotal = ConvertUtil.convertToInt(mTotalInput.getText().toString(),0);
                        }
                        mShare = ((float) mTotal)/(float) mPersonNum;
                        String str = String.format("%.2f", mShare);

                        mMyShareText.setText(str);
                        //validateMyShareAndAuth(mShare);

                    }

                }
            }
        });


        WebServiceManager.getInstance().toCreateHelp(new WebServiceManager.HttpCallback() {
            @Override
            public void onResonse(boolean sucess, String body) {
                if(sucess){
                    try {
                        JSONObject jsonObject = new JSONObject(body);
                        JSONObject jsDatas = jsonObject.getJSONObject("datas");
                        JSONObject jsCount = jsDatas.getJSONObject("account");
                        JSONObject jsLevelInfo = jsDatas.getJSONObject("levelInfo");
                        JSONArray  jsDuraArray = jsDatas.getJSONArray("timeInfos");
                        mAccountInfo = new AccountInfo();
                        mAccountInfo.initFromJson(jsCount);;
                        mUserLevel = new UserLevelInfo();
                        mUserLevel.initFromJson(jsLevelInfo);

                        mCanCreateNeed = jsDatas.getBoolean("continue");

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

                        if( mCanCreateNeed == false){
                            String msg = jsonObject.getString("msg");
                            toNoRightsNum(msg);
                        }


                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                else{

                }
            }
        });


        RadioGroup radioGroup = (RadioGroup)findViewById(R.id.radio_group);
        mRadioButtonNeed = (RadioButton)findViewById(R.id.radio_button_need);
        mRadioButtonSupport = (RadioButton)findViewById(R.id.radio_button_support);
//        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup radioGroup, int i) {
//                switch (i) {
//                    case R.id.radio_button_need:
//
//                        mRadioButtonNeed.setChecked(true);
//                        mRadioButtonSupport.setChecked(false);
//
//                        break;
//                    case R.id.radio_button_support:
//
//                        mRadioButtonNeed.setChecked(false);
//                        mRadioButtonSupport.setChecked(true);
//                        break;
//                }
//            }
//        });

        mRadioButtonSupport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRadioButtonNeed.setChecked(false);
                mRadioButtonSupport.setChecked(true);
            }
        });
        mRadioButtonNeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mCanCreateNeed) {
                    mRadioButtonNeed.setChecked(true);
                    mRadioButtonSupport.setChecked(false);
                }
                else {
                    mRadioButtonNeed.setChecked(false);
                    mRadioButtonSupport.setChecked(true);
                    Toast toast = Toast.makeText(getContext(), "您的竞标次数用完了，请升级等级或创建提供标",1);
                    toast.setGravity(Gravity.TOP,0,0);
                    toast.show();
                }
            }
        });

        findViewById(R.id.project_duration_menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showActionSheetDialog(view);
            }
        });


        //开始日期
        mProjectStartDate = (TextView) findViewById(R.id.mycreate_newproject_start_spinner);
        //开始日期所在的线性控件区域
        findViewById(R.id.project_mycreate_startdate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePicker();

            }
        });


        EditText authEdit =  (EditText) findViewById(R.id.project_mycreate_auth);
        authEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count,int after) {
                }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(mShare > 0){
                    float authValue = ConvertUtil.convertToFloat(editable.toString(),0);
                    float bal  =  ConvertUtil.convertToFloat(mAccountInfo.getBalance(), 0);
                    float notbal = ConvertUtil.convertToFloat(mAccountInfo.getNoRechargeBalance(),0);
                    if(authValue > mShare || authValue > bal){
                        Toast toast = Toast.makeText(getContext(),"无效输入，请注意有效范围", 1);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();

                    }
                    else if(mShare - authValue > notbal){

                        Toast toast = Toast.makeText(getContext(),"您的输入导致剩余部分超出不可提现总余额，请注意有效范围", 1);
                    }
                    else {

                        String str = String.format("%.2f", mShare - authValue);
                        ((TextView)findViewById(R.id.project_mycreate_auth_not)).setText(str);

                        if(authValue == 0){
                            if(editable.toString().equals("")) {
                                ((TextView) findViewById(R.id.project_mycreate_auth)).setText("0");
                            }
                        }
                    }
                }

            }
        });

        findViewById(R.id.project_mycreate_next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NextButtonClicked();
            }
        });

    }

    private void toNoRightsNum(String msg){
        //弹出提示框
        new CommomDialog(getContext(), R.style.dialog, msg, new CommomDialog.OnCloseListener() {
            @Override
            public void onClick(Dialog dialog, boolean confirm) {
                if(confirm){
                    dialog.dismiss();
                    //getActivity().finish();
                    mRadioButtonNeed.setChecked(false);
                    mRadioButtonSupport.setChecked(true);
                }
                else{
                    dialog.dismiss();
                    getActivity().finish();
                }


            }
        })
                .setTitle(" ").show();
    }

    private  void UpdateUI(){
        String strTips = String.format("请输入总金额，不超过%d", mUserLevel.getMaxMutualMoney());
        mTotalInput.setHint(strTips);
        strTips = String.format("请输入人数，不超过%d人", mUserLevel.getMaxMutualPersons());
        mNeedPersonsInput.setHint(strTips);

        ((TextView)findViewById(R.id.project_mycreate_balance)).setText(mAccountInfo.getBalance());
        ((TextView)findViewById(R.id.project_mycreate_notbalance)).setText(mAccountInfo.getNoRechargeBalance());


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
            }
        };

        for(int i = 0;  i < mDuraList.size(); i++) {
            dialog.addSheetItem(mDuraList.get(i).getDurationString(), ActionSheetDialog.SheetItemColor.Blue, actionSheetListener);
        }
        dialog.show();

    }

    public void showDatePicker(){
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity()
                //,DatePickerDialog.THEME_HOLO_LIGHT
                ,DatePickerDialog.THEME_DEVICE_DEFAULT_LIGHT
                ,new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {

                        //SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        mStartDatestr = String.format("%d-%d-%d", datePicker.getYear(),datePicker.getMonth()+ 1,datePicker.getDayOfMonth());

                        ((TextView)findViewById(R.id.mycreate_newproject_start_spinner)).setText(mStartDatestr);

                    }

                }
                // 设置初始日期
                ,calendar.get(Calendar.YEAR)
                ,calendar.get(Calendar.MONTH)
                ,calendar.get(Calendar.DAY_OF_MONTH));

        DatePicker datePicker = datePickerDialog.getDatePicker();
        datePicker.setMinDate(calendar.getTimeInMillis());

        Calendar calendarMax = Calendar.getInstance();
        calendarMax.add(Calendar.MONTH, +3);
        datePicker.setMaxDate(calendarMax.getTimeInMillis());
        datePickerDialog.show();;



    }


    void NextButtonClicked(){
        if(mPojectTitleInput.getText().toString().equals("") || mTotalInput.getText().toString().equals("")
            || mDurationTextView.getText().toString().equals("") || mNeedPersonsInput.getText().toString().equals("") ||
                mProjectStartDate.getText().toString().equals("")){
            //弹出提示框
            new CommomDialog(getContext(), R.style.dialog, "资料没有填写完，请填写完再提交", new CommomDialog.OnCloseListener() {
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

                    return;


                }
            })
                    .setTitle(" ").show();

            return;
        }

        HashMap mapParam = new HashMap();

        String type = "2"; //提供标
        if(mRadioButtonNeed.isChecked()) {
            type = "1"; //需求标
        }
        mapParam.put("type",type);
        mapParam.put("title", mPojectTitleInput.getText().toString());
        mapParam.put("totalMoney",mTotalInput.getText().toString());
        mapParam.put("month",mDuraList.get(mDuraIndex).getDuration());
        mapParam.put("timeType",mDuraList.get(mDuraIndex).getType());
        //mapParam.put("durationStr",mDuraList.get(mDuraIndex).getDurationString());
        mapParam.put("rate",mDuraList.get(mDuraIndex).getRate());
        mapParam.put("needNum",mNeedPersonsInput.getText().toString());

        String startDateStr = mProjectStartDate.getText().toString();
        mapParam.put("startTime",startDateStr);

        SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
        try {

            Date date =sdf.parse(startDateStr);
            Calendar calendarClose = Calendar.getInstance();
            calendarClose.setTime(date);

            Calendar calendarEnd = Calendar.getInstance();
            calendarEnd.setTime(date);

            calendarClose.add(Calendar.DAY_OF_MONTH, -1);
            String closeTimeStr =  sdf.format(calendarClose.getTime());

            int timeType = mDuraList.get(mDuraIndex).getType();
            if(timeType == ProjectItem.PROJECT_DURATION_TYPE_DAY){
                calendarEnd.add(Calendar.DAY_OF_YEAR, +mDuraList.get(mDuraIndex).getDuration());
            }
            else if(timeType == ProjectItem.PROJECT_DURATION_TYPE_MONTH){
                calendarEnd.add(Calendar.MONTH, +mDuraList.get(mDuraIndex).getDuration());
            }
            else {
                calendarEnd.add(Calendar.YEAR, +mDuraList.get(mDuraIndex).getDuration());
            }
            String endTimeStr = sdf.format(calendarEnd.getTime());

            mapParam.put("endTime", endTimeStr);
            mapParam.put("closeTime",closeTimeStr);
        }
        catch (Exception e){
            e.printStackTrace();
        }

        mapParam.put("helpSelfMoney", mMyShareText.getText().toString());
        mapParam.put("cashMoney",((TextView)findViewById(R.id.project_mycreate_auth)).getText().toString());
        mapParam.put("notMention",((TextView)findViewById(R.id.project_mycreate_auth_not)).getText().toString());




        ActivityManager.startFragment(getContext(), "创建项目",mapParam);
        getActivity().finish();


    }


}
