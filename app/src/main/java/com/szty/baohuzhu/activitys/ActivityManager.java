package com.szty.baohuzhu.activitys;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.TextView;

import com.szty.baohuzhu.R;
import com.szty.baohuzhu.fragments.FragmentBindCard;
import com.szty.baohuzhu.fragments.FragmentHelp;

public class ActivityManager extends BaseActivity  {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);

        final FragmentManager manager = getSupportFragmentManager();
        FragmentBindCard  fragmentBindCard = new FragmentBindCard();

        manager.beginTransaction().add(R.id.fragment_content,fragmentBindCard).commit();

        manager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {

            }
        });

        findViewById(R.id.back_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(title);
        TextView textView =  findViewById(R.id.title);
        textView.setText(title);
    }




}
