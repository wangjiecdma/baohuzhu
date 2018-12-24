package com.wyhzb.hbsc.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wyhzb.hbsc.activitys.ActivityManager;

public class FragmentBase extends Fragment {

    protected String title;
    protected int layoutId;
    public  FragmentBase(){
        super();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(layoutId,container,false);
        return view;
    }

    protected View findViewById(int id){
        return getView().findViewById(id);
    }
    protected void onInitData(){

    }

    @Override
    public void onResume() {
        super.onResume();
        if (getActivity() instanceof ActivityManager) {
            ActivityManager activityManager = (ActivityManager) getActivity();
            activityManager.setTitle(title);
        }
        onInitData();
    }
}
