package com.szty.baohuzhu.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.szty.baohuzhu.R;
import com.szty.baohuzhu.activitys.ActivityManager;
import com.szty.baohuzhu.webapi.WebServiceManager;

public class FragmentBindCard extends Fragment implements View.OnClickListener{

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bidder_information,container,false);
        view.findViewById(R.id.sms_code).setOnClickListener(this);
        view.findViewById(R.id.commit).setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.sms_code){
            WebServiceManager.getInstance().getSMSCode("13564043982", new WebServiceManager.HttpCallback() {
                @Override
                public void onResonse(boolean sucess, String body) {
                    if (sucess){
                        Toast.makeText(getContext(),"获取验证码成功",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }else if(v.getId() == R.id.commit){
            EditText smscode = getView().findViewById(R.id.text_sms_code);
            EditText bankCard = getView().findViewById(R.id.bank_name);
            EditText bankUser = getView().findViewById(R.id.card_number);
            EditText bankName = getView().findViewById(R.id.bank_name);
            EditText userCard =getView().findViewById(R.id.usercard);

            WebServiceManager.getInstance().saveUserAccount("13564043982",smscode.getText().toString(),bankCard.getText().toString()
                    ,bankName.getText().toString(),bankUser.getText().toString(),userCard.getText().toString(),
                    new WebServiceManager.HttpCallback(){
                        @Override
                        public void onResonse(boolean sucess, String body) {
                            Log.d("httplog","bind user infomation:"+body);



                        }
                    });

            FragmentWithrawCach cach =new FragmentWithrawCach();
            getFragmentManager().beginTransaction().add(R.id.fragment_content,cach).commit();


        }
    }


    @Override
    public void onResume() {
        super.onResume();
        ActivityManager activityManager = (ActivityManager) getActivity();
        activityManager.setTitle("提现绑卡");
    }
}
