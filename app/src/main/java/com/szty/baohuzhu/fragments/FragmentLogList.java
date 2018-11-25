package com.szty.baohuzhu.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.szty.baohuzhu.R;
import com.szty.baohuzhu.activitys.ActivityManager;
import com.szty.baohuzhu.webapi.WebServiceManager;

import org.json.JSONObject;

public class FragmentLogList extends FragmentBase {

    private ListView mListView ;

    public FragmentLogList(){
        super();
        title = "详情记录";
        layoutId = R.layout.myrecords_detail;
    }


    @Override
    protected void onInitData() {
        WebServiceManager.getInstance().getLog(0, 100, new WebServiceManager.HttpCallback() {
            @Override
            public void onResonse(boolean sucess, String body) {
                Log.d("www","get log :"+body);
                if (sucess){

                    try{
                        JSONObject object = new JSONObject(body);

                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }
            }
        });
    }



}
