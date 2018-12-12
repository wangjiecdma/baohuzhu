package com.szty.baohuzhu.fragments;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import android.widget.PopupWindow;
import android.widget.TextView;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.szty.baohuzhu.MainActivity;
import com.szty.baohuzhu.R;
import com.szty.baohuzhu.activitys.ActivityManager;
import com.szty.baohuzhu.webapi.WebServiceManager;

import org.json.JSONObject;

import java.util.List;

import static android.app.Activity.RESULT_OK;

public class FragmentMyInformation extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.personal_information,container,false);

        view.findViewById(R.id.change_user_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //showPop();
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                View view= LayoutInflater.from(getActivity()).inflate(R.layout.picture_selector, null);
                builder.setView(view);
                builder.create().show();

            }
        });
        return view;
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
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        List<LocalMedia> images;
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调

                    images = PictureSelector.obtainMultipleResult(data);

                    Log.d("www","image select size :"+images.size());
                    break;
            }
        }
    }
}
