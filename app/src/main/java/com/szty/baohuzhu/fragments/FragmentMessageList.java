package com.szty.baohuzhu.fragments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.szty.baohuzhu.R;

import java.util.List;

public class FragmentMessageList extends FragmentBase {
    public FragmentMessageList(){
        super();
        layoutId = R.layout.list_layout;
        title = "账号信息";
    }

    @Override
    protected void onInitData() {
        ListView listView = (ListView)findViewById(R.id.list);
        listView.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return 4;
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                 return LayoutInflater.from(getContext()).inflate(R.layout.message_item,null);
            }
        });
    }
}
