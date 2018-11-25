package com.szty.baohuzhu.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.szty.baohuzhu.R;
import com.szty.baohuzhu.activitys.ActivityManager;
import com.szty.baohuzhu.webapi.WebServiceManager;

import org.json.JSONObject;

public class FragmentMyInformation extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.personal_information,container,false);


        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        ActivityManager activityManager = (ActivityManager) getActivity();
        activityManager.setTitle("个人信息");
    }
}
