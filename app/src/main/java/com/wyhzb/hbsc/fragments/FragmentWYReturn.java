package com.wyhzb.hbsc.fragments;

import android.app.Dialog;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wyhzb.hbsc.R;
import com.wyhzb.hbsc.utils.ActionSheetDialog;
import com.wyhzb.hbsc.utils.CommomDialog;
import com.wyhzb.hbsc.utils.ConvertUtil;
import com.wyhzb.hbsc.webapi.WebServiceManager;

import org.json.JSONObject;

import java.util.HashMap;
import com.wyhzb.hbsc.adapter.ProjectTakeInDesc;

import android.provider.MediaStore;
import android.net.Uri;
import android.graphics.Bitmap;
import android.os.Bundle;

public class FragmentWYReturn extends FragmentBase {
    private int projectID;
    ProjectTakeInDesc mDesc;

    static final int ALBUM_RESULT_CODE = 0x10;
    static final int CROP_RESULT_CODE  = 0x20;

    public FragmentWYReturn(){
        super();
        layoutId = R.layout.project_wyreturn;
        title="我要归还";
    }

    @Override
    protected void onInitData() {
        super.onInitData();

        Intent intent = getActivity().getIntent();
        HashMap map=(HashMap)intent.getSerializableExtra("userParam");
        projectID = Integer.parseInt(map.get("projectId").toString());

        WebServiceManager.getInstance().getWillBackDetail(projectID, new WebServiceManager.HttpCallback() {
            @Override
            public void onResonse(boolean sucess, String body) {
                try {
                    JSONObject jsonObject = new JSONObject(body);
                    String msg = jsonObject.getString("msg");

                    if(sucess){

                        JSONObject jsDatas = jsonObject.getJSONObject("datas");
                        JSONObject jsDesc = jsDatas.getJSONObject("desc");

                        mDesc = new ProjectTakeInDesc();
                        mDesc.initFromJson(jsDesc);
                        UpdateUI(mDesc);

                    }
                    else{
                        toShowErrorMsg(msg, true);

                    }

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        findViewById(R.id.project_wyreturn_usebal_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mDesc.isIs_balanceBack()){

                    float willbackNum  = ConvertUtil.convertToFloat(mDesc.getWillBackMoney(), 0);
                    float bal  = ConvertUtil.convertToFloat(mDesc.getBalance(), 0);;
                    float notbal  = ConvertUtil.convertToFloat(mDesc.getNoRechargeBalance(), 0);

                    if(willbackNum > notbal){
                        bal  = willbackNum - notbal;
                    }
                    else {
                        bal = 0;
                        notbal = willbackNum;
                    }

                    WebServiceManager.getInstance().backByBalance(projectID, bal, notbal, new WebServiceManager.HttpCallback() {
                        @Override
                        public void onResonse(boolean sucess, String body) {

                            try {
                                JSONObject jsonObject = new JSONObject(body);
                                String msg = jsonObject.getString("msg");

                                if(sucess){

                                    JSONObject jsDatas = jsonObject.getJSONObject("datas");

                                    toShowErrorMsg(msg, true);

                                }
                                else{
                                    toShowErrorMsg(msg, true);

                                }

                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    });

                }
                else{
                    toShowErrorMsg("余额不足，请去充值", false);
                }

            }
        });

        findViewById(R.id.project_wyreturn_upload_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showActionSheetDialog(view);

            }
        });
    }

    public void showActionSheetDialog(View view){

        ActionSheetDialog dialog = new ActionSheetDialog(getContext());
        dialog.builder()
                .setCancelable(true)
                .setTitle("选择上传凭证")
                .setCanceledOnTouchOutside(true)
                .addSheetItem("启动相机拍照", ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener(){
                    @Override
                    public void onClick(int which) {


                    }
                })
                .addSheetItem("从相册选择", ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
//                        //相册
////                        PictureSelector.create(getActivity())
////                                .openGallery(PictureMimeType.ofImage())
////                                .maxSelectNum(1)
////                                .minSelectNum(1)
////                                .imageSpanCount(4)
////                                .selectionMode(PictureConfig.SINGLE)
////                                .forResult(PictureConfig.CHOOSE_REQUEST);
                        Intent albumIntent = new Intent(Intent.ACTION_PICK);
                        albumIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                        startActivityForResult(albumIntent, ALBUM_RESULT_CODE);

                    }
                })
                .show();
    }

    private void UpdateUI(ProjectTakeInDesc desc){
        ((TextView)findViewById(R.id.project_wyreturn_title)).setText(desc.getTitle());
        ((TextView)findViewById(R.id.project_wyreturn_total_value)).setText(desc.getTotalMutualMoney());
        ((TextView)findViewById(R.id.project_wyreturn_rate_value)).setText(desc.getWillBackMoney());
        ((TextView)findViewById(R.id.project_wyreturn_closedate_value)).setText(desc.getEndTime());
        ((TextView)findViewById(R.id.project_wyreturn_totalbal_value)).setText(desc.getTotalBalance());

        String strTotalBalDetail = String.format("可提现钱包：%s, 不可提现钱包：%s", desc.getBalance(), desc.getNoRechargeBalance());

        ((TextView)findViewById(R.id.project_myreturn_totalbal_detail)).setText(strTotalBalDetail);

        ((TextView)findViewById(R.id.bankbranch_name_text)).setText(desc.getBankName());
        ((TextView)findViewById(R.id.card_number)).setText(desc.getBankCard());


    }

    private void toShowErrorMsg(String msg, final boolean isFinish){
        //弹出提示框
        new CommomDialog(getContext(), R.style.dialoghzb, msg, new CommomDialog.OnCloseListener() {
            @Override
            public void onClick(Dialog dialog, boolean confirm) {
                if(confirm){
                    dialog.dismiss();
                    //getActivity().finish();
                }
                else{
                    dialog.dismiss();
                    //getActivity().finish();
                }

                if(isFinish){
                    getActivity().finish();
                }


            }
        })
                .setTitle(" ").show();
    }



    /**
     * 裁剪图片
     *
     * @param data
     */ private void cropPic(Uri data) {
         if (data == null) {
             return;
         }
         Intent cropIntent = new Intent("com.android.camera.action.CROP");
         cropIntent.setDataAndType(data, "image/*");

         // 开启裁剪：打开的Intent所显示的View可裁剪
         cropIntent.putExtra("crop", "true");
         // 裁剪宽高比
         cropIntent.putExtra("aspectX", 1);
         cropIntent.putExtra("aspectY", 1);
         // 裁剪输出大小
         cropIntent.putExtra("outputX", 320);
         cropIntent.putExtra("outputY", 320);
         cropIntent.putExtra("scale", true);
          /**
          *return-data
          * 这个属性决定我们在 onActivityResult 中接收到的是什么数据，
          * 如果设置为true 那么data将会返回一个bitmap
          * 如果设置为false，则会将图片保存到本地并将对应的uri返回，当然这个uri得有我们自己设定。
          * 系统裁剪完成后将会将裁剪完成的图片保存在我们所这设定这个uri地址上。我们只需要在裁剪完成后直接调用该uri来设置图片，就可以了。
          */
         cropIntent.putExtra("return-data", true);
         // 当 return-data 为 false 的时候需要设置这句
//         cropIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
         // 图片输出格式
//         cropIntent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
         // 头像识别 会启动系统的拍照时人脸识别
//         cropIntent.putExtra("noFaceDetection", true);
         startActivityForResult(cropIntent, CROP_RESULT_CODE);
     }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (resultCode){
            case CROP_RESULT_CODE:
                // 裁剪时,这样设置 cropIntent.putExtra("return-data", true); 处理方案如下
                if (data != null) {
                    Bundle bundle = data.getExtras();
                    if (bundle != null) {
                        Bitmap bitmap = bundle.getParcelable("data");
                        ImageView imageView = new ImageView(getContext());
                        imageView.setImageBitmap(bitmap);
                        // 把裁剪后的图片保存至本地 返回路径
//                        String urlpath = FileUtilcll.saveFile(this, "crop.jpg", bitmap);
//                        L.e("裁剪图片地址->" + urlpath);
                    }
                }
                // 裁剪时,这样设置 cropIntent.putExtra("return-data", false); 处理方案如下 //
//                try {
//                    ivHead.setImageBitmap(BitmapFactory.decodeStream( GetActivity().getContentResolver().openInputStream(imageUri)));
//
//                    } catch (FileNotFoundException e) {
//                        e.printStackTrace();
//                }

                break;
            case ALBUM_RESULT_CODE:
                // 相册
                cropPic(data.getData());
                break;

        }

    }
}
