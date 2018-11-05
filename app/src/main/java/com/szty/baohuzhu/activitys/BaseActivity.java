package com.szty.baohuzhu.activitys;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.szty.baohuzhu.fragments.CustomDatePickerDialogFragment;
import com.szty.baohuzhu.webapi.WebServiceManager;

import java.util.Calendar;

public class BaseActivity extends AppCompatActivity implements CustomDatePickerDialogFragment.OnSelectedDateListener{
    long day = 24 * 60 * 60 * 1000;
    private void showDatePickDialog() {
        CustomDatePickerDialogFragment fragment = new CustomDatePickerDialogFragment();
        fragment.setOnSelectedDateListener(this);
        Bundle bundle = new Bundle();
        Calendar currentDate = Calendar.getInstance();
        currentDate.setTimeInMillis(System.currentTimeMillis());
        currentDate.set(Calendar.HOUR_OF_DAY, 0);
        currentDate.set(Calendar.MINUTE, 0);
        currentDate.set(Calendar.SECOND, 0);
        currentDate.set(Calendar.MILLISECOND, 0);
        bundle.putSerializable(CustomDatePickerDialogFragment.CURRENT_DATE, currentDate);
        long start = currentDate.getTimeInMillis() - day * 2;
        long end = currentDate.getTimeInMillis() - day;
        Calendar startDate = Calendar.getInstance();
        startDate.setTimeInMillis(start);
        Calendar endDate = Calendar.getInstance();
        endDate.setTimeInMillis(end);
        bundle.putSerializable(CustomDatePickerDialogFragment.START_DATE, startDate);
        bundle.putSerializable(CustomDatePickerDialogFragment.END_DATE, currentDate);
        fragment.setArguments(bundle);
        fragment.show(getSupportFragmentManager(), CustomDatePickerDialogFragment.class.getSimpleName());
    }

    @Override
    public void onSelectedDate(int year, int monthOfYear, int dayOfMonth) {
        Toast.makeText(this, year + "年" + (monthOfYear + 1) + "月" + dayOfMonth + "日", Toast.LENGTH_SHORT).show();
    }

    protected WebServiceManager mWebServer= null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WebServiceManager.initManager(this);
        mWebServer = WebServiceManager.getInstance();
    }

    protected void showToast(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }
}
