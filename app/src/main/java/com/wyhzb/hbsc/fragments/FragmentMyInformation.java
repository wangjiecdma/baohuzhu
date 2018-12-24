package com.wyhzb.hbsc.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.wyhzb.hbsc.R;
import com.wyhzb.hbsc.activitys.ActivityManager;
import com.wyhzb.hbsc.adapter.UserStatus;
import com.wyhzb.hbsc.webapi.WebServiceManager;

import java.io.File;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class FragmentMyInformation extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.personal_information,container,false);

        TextView textView = view.findViewById(R.id.text_sex);
        textView.setText(UserStatus.user().getSex());

        textView= view.findViewById(R.id.birthday_spinner);
        if (UserStatus.user().getBirthday() == null){
            textView.setText("1970-01-01");
        }else {
            textView.setText(UserStatus.user().getBirthday());
        }
        view.findViewById(R.id.change_user_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //showPop();

                PictureSelector.create(getActivity())
                        .openGallery(PictureMimeType.ofImage())
                        .forResult(PictureConfig.CHOOSE_REQUEST);



            }
        });
        view.findViewById(R.id.change_nickname).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityManager.startFragment(getContext(),"修改昵称");
            }
        });
        textView =  view.findViewById(R.id.nike_name);
        textView.setText(UserStatus.user().getNickName());

        view.findViewById(R.id.change_sex).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openPopupWindow(getView(), "男", "女", "保密", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("www","menu click ");
                        String sex="";
                        if (v.getId() == R.id.menu1){
                            sex = "男";
                        }else if(v.getId() == R.id.menu2){
                            sex = "女";
                        }else if(v.getId() == R.id.menu3){
                            sex = "保密";
                        }
                        final String updateSex = sex;
                        if (sex.length()>0) {
                            WebServiceManager.getInstance().updateUserInfo("", sex, UserStatus.user().getBirthday(), UserStatus.user().getNickName(), new WebServiceManager.HttpCallback() {
                                @Override
                                public void onResonse(boolean sucess, String body) {
                                    if (sucess){
                                        Toast.makeText(getContext(),"修改成功",Toast.LENGTH_LONG).show();
                                        UserStatus.user().setSex(updateSex);

                                        TextView textView = getView().findViewById(R.id.text_sex);
                                        textView.setText(UserStatus.user().getSex());

                                    }
                                }
                            });
                        }
                    }
                });

            }
        });

        view.findViewById(R.id.change_pwd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityManager.startFragment(getContext(),"修改密码");
            }
        });


        view.findViewById(R.id.change_date).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog.OnDateSetListener dateSetListener  = new DatePickerDialog.OnDateSetListener(){
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                       final String date= String.format("%d-%d-%d",year,month+1,dayOfMonth);
                        WebServiceManager.getInstance().updateUserInfo("", UserStatus.user().getSex(), date, UserStatus.user().getNickName(), new WebServiceManager.HttpCallback() {
                            @Override
                            public void onResonse(boolean sucess, String body) {

                                if (sucess){
                                    Toast.makeText(getContext(),"修改成功",Toast.LENGTH_LONG).show();
                                    UserStatus.user().setBirthday(date);

                                    TextView textView = getView().findViewById(R.id.birthday_spinner);
                                    textView.setText(UserStatus.user().getBirthday());

                                }else{
                                    Toast.makeText(getContext(),"修改失败",Toast.LENGTH_LONG).show();

                                }

                            }
                        });

                    }
                };
                Dialog dlg = new DatePickerDialog(getContext(),dateSetListener
                        ,
                        1980,
                        0,
                        1);
                dlg.show();

            }
        });

        return view;
    }

    private PopupWindow   popupWindow ;

    private void openPopupWindow(View v, String menu1, String menu2, String menu3,final View.OnClickListener listener) {
        //防止重复按按钮
        if (popupWindow != null && popupWindow.isShowing()) {
            return;
        }
        //设置PopupWindow的View
        View view = LayoutInflater.from(getContext()).inflate(R.layout.view_popupwindow, null);
        popupWindow = new PopupWindow(view, RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        //设置背景,这个没什么效果，不添加会报错
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        //设置点击弹窗外隐藏自身
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(false);
        //设置动画
        //popupWindow.setAnimationStyle(R.style.PopupWindow);
        //设置位置
        popupWindow.showAtLocation(v, Gravity.BOTTOM, 0, 0);
        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.cancel){

                }else {
                    listener.onClick(v);
                }
                popupWindow.dismiss();
                popupWindow = null;
            }
        };
        TextView textView =  view.findViewById(R.id.menu1);
        textView.setText(menu1);
        textView.setOnClickListener(clickListener);

        textView = view.findViewById(R.id.menu2);
        textView.setText(menu2);
        textView.setOnClickListener(clickListener);


        if(menu3 != null) {
            textView = view.findViewById(R.id.menu3);
            textView.setText(menu3);
            textView.setOnClickListener(clickListener);

            view.findViewById(R.id.menu_cancel).setOnClickListener(clickListener);
        }else{
            view.findViewById(R.id.menu3).setVisibility(View.GONE);
            view.findViewById(R.id.menu_line3).setVisibility(View.GONE);
        }
        //设置消失监听
       // popupWindow.setOnDismissListener(this);
        //设置背景色
       // setBackgroundAlpha(0.5f);
    }
    public void setBackgroundAlpha(float alpha) {
        WindowManager.LayoutParams lp =getActivity().getWindow().getAttributes();
        lp.alpha = alpha;
         getActivity().getWindow().setAttributes(lp);
    }

    private void showPop() {
        View bottomView = View.inflate(getContext(), R.layout.picture_selector, null);
        TextView mAlbum = (TextView) bottomView.findViewById(R.id.picture_select_file);
        TextView mCamera = (TextView) bottomView.findViewById(R.id.picture_select_camera);
        TextView mCancel = (TextView) bottomView.findViewById(R.id.picture_select_cancel);

        final PopupWindow pop = new PopupWindow(bottomView, -1, -2);
        pop.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        pop.setOutsideTouchable(true);
        pop.setFocusable(true);
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = 0.5f;
        getActivity().getWindow().setAttributes(lp);
        pop.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
                lp.alpha = 1f;
                getActivity().getWindow().setAttributes(lp);
            }
        });

        pop.showAtLocation(getActivity().getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);

        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.picture_select_file:
                        //相册
                        PictureSelector.create(FragmentMyInformation.this)
                                .openGallery(PictureMimeType.ofImage())
                                .maxSelectNum(1)
                                .minSelectNum(1)
                                .imageSpanCount(4)
                                .selectionMode(PictureConfig.MULTIPLE)
                                .forResult(PictureConfig.CHOOSE_REQUEST);
                        break;
                    case R.id.picture_select_camera:
                        //拍照
                        PictureSelector.create(FragmentMyInformation.this)
                                .openCamera(PictureMimeType.ofImage())
                                .forResult(PictureConfig.CHOOSE_REQUEST);
                        break;
                    case R.id.picture_select_cancel:
                        //取消
                        //closePopupWindow();
                        pop.dismiss();

                        break;
                }
            }
        };

        mAlbum.setOnClickListener(clickListener);
        mCamera.setOnClickListener(clickListener);
        mCancel.setOnClickListener(clickListener);

    }
    @Override
    public void onResume() {
        super.onResume();
        ActivityManager activityManager = (ActivityManager) getActivity();
        activityManager.setTitle("个人信息");
        Log.d("www","onResume  ");
        TextView textView =  getView().findViewById(R.id.nike_name);
        textView.setText(UserStatus.user().getNickName());



    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        List<LocalMedia> images;
        Log.d("www","activityu resutl ");
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调

                    images = PictureSelector.obtainMultipleResult(data);
                    try {
                        ImageView imageView=  getView().findViewById(R.id.user_image);
                         imageView.setImageURI(Uri.fromFile(new File(images.get(0).getPath())));
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    Log.d("www","image select size :"+images.size());
                    break;
            }



        }
    }
}
