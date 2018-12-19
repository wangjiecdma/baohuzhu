package com.szty.baohuzhu.utils;

import android.app.Dialog;
import android.content.DialogInterface;
import android.widget.EditText;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.text.Spanned;
import android.text.SpannedString;

public class UITool {

    public void showDialog(){


    }

    public static void  setEditTextHintSize(EditText editText,String hintText,int size){
        SpannableString ss = new SpannableString(hintText);//定义hint的值
        AbsoluteSizeSpan ass = new AbsoluteSizeSpan(size,true);//设置字体大小 true表示单位是sp
        ss.setSpan(ass, 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        editText.setHint(new SpannedString(ss));
    }

}
