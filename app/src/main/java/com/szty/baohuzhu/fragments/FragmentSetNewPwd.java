package com.szty.baohuzhu.fragments;

import android.support.design.widget.TextInputEditText;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.szty.baohuzhu.R;
import com.szty.baohuzhu.adapter.UserStatus;
import com.szty.baohuzhu.webapi.WebServiceManager;

public class FragmentSetNewPwd extends FragmentBase {
    public FragmentSetNewPwd(){
        title = "修改密码";
        layoutId = R.layout.set_new_pwd;
    }

    @Override
    protected void onInitData() {
        super.onInitData();
        TextView textView =(TextView) findViewById(R.id.bider_info_phonenumber);
        textView.setText(UserStatus.user().getMobile());
        findViewById(R.id.smscode).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebServiceManager.getInstance().getSMSCode(UserStatus.user().getMobile(), new WebServiceManager.HttpCallback() {
                    @Override
                    public void onResonse(boolean sucess, String body) {
                        if (sucess){
                            Toast.makeText(getContext(),"获取验证码成功",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        findViewById(R.id.commit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText sms = (EditText) findViewById(R.id.text_sms_code);
                EditText pwd1 = (EditText)findViewById(R.id.new_pwd1);
                EditText pwd2 = (EditText)findViewById(R.id.new_pwd2);
               if (pwd1.getText().toString().equals(pwd2.getText().toString()) == false){
                    Toast.makeText(getContext(),"两次输入的密码不一致",Toast.LENGTH_LONG).show();
                    return;
               }
               if (pwd1.getText().toString().length()<6){
                   Toast.makeText(getContext(),"密码长度不足6位",Toast.LENGTH_LONG).show();

                   return;
               }

               WebServiceManager.getInstance().resetPWD(UserStatus.user().getMobile(), sms.getText().toString(), pwd1.getText().toString(), new WebServiceManager.HttpCallback() {
                   @Override
                   public void onResonse(boolean sucess, String body) {

                       if (sucess){
                           Toast.makeText(getContext(),"修改成功",Toast.LENGTH_LONG).show();

                           getActivity().finish();
                       }else{
                           Toast.makeText(getContext(),"修改失败",Toast.LENGTH_LONG).show();

                       }


                   }
               });



            }
        });
    }
}
