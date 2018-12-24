package com.wyhzb.hbsc.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.wyhzb.hbsc.R;
import com.wyhzb.hbsc.activitys.ActivityManager;
import com.wyhzb.hbsc.adapter.UserStatus;
import com.wyhzb.hbsc.webapi.WebServiceManager;

public class FragmentBindCard extends Fragment implements View.OnClickListener{

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bidder_information,container,false);
        view.findViewById(R.id.sms_code).setOnClickListener(this);
        view.findViewById(R.id.commit).setOnClickListener(this);

        TextView textView = (TextView)view.findViewById(R.id.bider_info_phonenumber);
        textView.setText(UserStatus.user().getMobile());
        return view;
    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.sms_code){
            WebServiceManager.getInstance().getSMSCode(UserStatus.user().getMobile(), new WebServiceManager.HttpCallback() {
                @Override
                public void onResonse(boolean sucess, String body) {
                    if (sucess){
                        Toast.makeText(getContext(),"获取验证码成功",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }else if(v.getId() == R.id.commit){
            EditText smscode = getView().findViewById(R.id.text_sms_code);
            final EditText bankCard = getView().findViewById(R.id.card_number);
            final  EditText bankUser = getView().findViewById(R.id.card_number);
            final  EditText bankName = getView().findViewById(R.id.bank_name);
            EditText userCard =getView().findViewById(R.id.usercard);

            WebServiceManager.getInstance().saveUserAccount(UserStatus.user().getMobile(),smscode.getText().toString(),bankCard.getText().toString()
                    ,bankName.getText().toString(),bankUser.getText().toString(),userCard.getText().toString(),
                    new WebServiceManager.HttpCallback(){
                        @Override
                        public void onResonse(boolean sucess, String body) {
                            Log.d("httplog","bind user infomation:"+body);

                            if (sucess){
                                UserStatus.user().setBankName(bankName.getText().toString());
                                UserStatus.user().setBankCard(bankCard.getText().toString());
                                Toast.makeText(getContext(),"绑卡成功",Toast.LENGTH_SHORT).show();
                                getActivity().finish();

                            }else{
                                Toast.makeText(getContext(),"绑卡失败",Toast.LENGTH_SHORT).show();
                            }


                        }
                    });

//            FragmentWithrawCach cach =new FragmentWithrawCach();
//            getFragmentManager().beginTransaction().add(R.id.fragment_content,cach).commit();


        }
    }


    @Override
    public void onResume() {
        super.onResume();
        ActivityManager activityManager = (ActivityManager) getActivity();
        activityManager.setTitle("提现绑卡");
    }
}
