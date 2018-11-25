package com.szty.baohuzhu.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.szty.baohuzhu.R;
import com.szty.baohuzhu.activitys.ActivityManager;

public class FragmentSetting extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.setting,container,false);


        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        ActivityManager activityManager = (ActivityManager) getActivity();
        activityManager.setTitle("设置");
    }
}
