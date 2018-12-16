package com.szty.baohuzhu.fragments;

import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.szty.baohuzhu.R;
import com.szty.baohuzhu.adapter.UserStatus;
import com.szty.baohuzhu.webapi.WebServiceManager;

public class FragmentEditNickName extends FragmentBase {
    public FragmentEditNickName(){
        title = "修改昵称";
        layoutId = R.layout.edit_nickname;
    }

    @Override
    protected void onInitData() {
        super.onInitData();
        final EditText textView = (EditText) findViewById(R.id.text_nickname);
        textView.setText(UserStatus.user().getNickName());

        findViewById(R.id.btn_commit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebServiceManager.getInstance().updateUserInfo("", UserStatus.user().getSex(), UserStatus.user().getBirthday(), textView.getText().toString(), new WebServiceManager.HttpCallback() {
                    @Override
                    public void onResonse(boolean sucess, String body) {
                        if (sucess) {
                            Toast.makeText(getContext(), "修改成功", Toast.LENGTH_LONG).show();
                            UserStatus.user().setNickName(textView.getText().toString());
                            getActivity().finish();
                        }
                        else
                            Toast.makeText(getContext(),"修改失败",Toast.LENGTH_LONG).show();

                    }
                });

            }
        });
    }
}
