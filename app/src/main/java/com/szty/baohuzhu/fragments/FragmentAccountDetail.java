package com.szty.baohuzhu.fragments;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.szty.baohuzhu.R;
import com.szty.baohuzhu.webapi.WebServiceManager;

import org.json.JSONArray;
import org.json.JSONObject;

public class FragmentAccountDetail extends FragmentBase {
    public FragmentAccountDetail(){
        super();
        title = "账户详情";
        layoutId= R.layout.list_layout;
    }

    @Override
    protected void onInitData() {

        WebServiceManager.getInstance().getLog(0, 100, new WebServiceManager.HttpCallback() {
            @Override
            public void onResonse(boolean sucess, String body) {
                ListView listView= (ListView)findViewById(R.id.list);
                Log.d("www","get log :"+body);
                try {
                    final JSONObject jsonObject = new JSONObject(body);

                    final JSONArray array = jsonObject.getJSONObject("datas").getJSONObject("page").getJSONArray("datas");
                    listView.setAdapter(new BaseAdapter() {
                        @Override
                        public int getCount() {
                            return array.length();
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
                            View view =  LayoutInflater.from(getContext()).inflate(R.layout.account_detail, null);

                            TextView title = (TextView) view.findViewById(R.id.withdraw_text);
                            TextView date_text = (TextView) view.findViewById(R.id.date_text);
                            TextView withdrw_number_text = (TextView) view.findViewById(R.id.withdrw_number_text);

                            try {
                                JSONObject item = array.getJSONObject(position);
                                title.setText(item.getString("mark"));
                                date_text.setText(item.getString("time"));
                                if (item.getInt("type") == 1){
                                    withdrw_number_text.setText("+"+item.getString("money"));
                                }else{
                                    withdrw_number_text.setText("-"+item.getString("money"));

                                }
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                            return view;
                        }
                    });
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });


    }
}
