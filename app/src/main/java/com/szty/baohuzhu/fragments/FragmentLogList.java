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

public class FragmentLogList extends Fragment {

    private ListView mListView ;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.activity_main,container,false);

        mListView = view.findViewById(R.id.list_help);
        udpateRecorders();
        return view;
    }

    private void udpateRecorders(){

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

    @Override
    public void onResume() {
        super.onResume();
        ActivityManager activityManager = (ActivityManager) getActivity();
        activityManager.setTitle("详情记录");
    }
}
