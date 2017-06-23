package com.guojianyiliao.eryitianshi.View.activity;

import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.guojianyiliao.eryitianshi.MyUtils.base.BaseActivity;
import com.guojianyiliao.eryitianshi.MyUtils.bean.UserInfoLogin;
import com.guojianyiliao.eryitianshi.MyUtils.interfaceservice.HttpStaticApi;
import com.guojianyiliao.eryitianshi.MyUtils.interfaceservice.RetrofitCallBack;
import com.guojianyiliao.eryitianshi.MyUtils.interfaceservice.RetrofitHttpUpLoad;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.MyLogcat;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.SharedPreferencesTools;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.SpUtils;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.StringUtils;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.TimeUtil;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.ToolUtils;
import com.guojianyiliao.eryitianshi.MyUtils.view.activity.view.xpdialog.XProgressDialog;
import com.guojianyiliao.eryitianshi.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.soundcloud.android.crop.Crop;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.RequestBody;
import retrofit2.Response;

/**
 * 个人资料
 */
public class PersonaldataActivity extends BaseActivity implements RetrofitCallBack {

    public static final int GALLERY_REQUEST_CODE = 1;
    private static final int CAMERA_REQUEST_CODE = 2;
    private static final int CROP_REQUEST_CODE = 3;
    @BindView(R.id.iv_icon)
    CircleImageView ivIcon;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_sex)
    TextView tvSex;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.ll_rutmypage)
    LinearLayout llRutmypage;
    @BindView(R.id.ll_icon)
    LinearLayout llIcon;
    @BindView(R.id.ll_name)
    LinearLayout llName;
    @BindView(R.id.ll_sex)
    LinearLayout llSex;

    Gson gson;
    UserInfoLogin user ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personaldata);
        ButterKnife.bind(this);

        gson = new Gson();
        String s = SharedPreferencesTools.GetUsearInfo(this, "userSave", "userInfo");
        user = gson.fromJson(s, UserInfoLogin.class);

        ImageLoader.getInstance().displayImage(user.getIcon(),ivIcon);
        String gender = user.getGender();
        String name = user.getName();
        String phone = user.getPhone();
        tvName.setText(name);
        tvSex.setText(gender);
        tvPhone.setText(phone);

        audioFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/text/icon/");
        audioFile.mkdirs();

    }

    @OnClick(R.id.ll_rutmypage)
    public void back() {
        finish();
    }

    @OnClick(R.id.ll_icon)
    public void setIcon() {
        showAvatarPop();
    }

    @OnClick(R.id.ll_name)
    public void setName() {
        startActivityForResult(new Intent(PersonaldataActivity.this, NamepageActivity.class),0);
    }

    @OnClick(R.id.ll_sex)
    public void setGender() {
        startActivity(new Intent(PersonaldataActivity.this, SexpageActivity.class));
    }

    @Override
    protected void onStart() {
        super.onStart();

        String name = user.getName();
        String gender = user.getGender();
        String phone = user.getPhone();

        tvName.setText(name);
        tvSex.setText(gender);
        tvPhone.setText(phone);

       /* if ((User_Http.user.getIcon() == null || User_Http.user.getIcon().equals("")) && (sp.getTag().getIcon() == null || sp.getTag().getIcon().equals(""))) {

            iv_icon.setBackgroundResource(R.drawable.default_icon);


        } else if (User_Http.user.getIcon() == null || User_Http.user.getIcon().equals("")) {
            ImageLoader.getInstance().displayImage("file:///" + sp.getTag().getIcon(), iv_icon);
        } else {

            ImageLoader.getInstance().displayImage(User_Http.user.getIcon(), iv_icon);
        }*/


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent result) {
        if(requestCode == 0 && resultCode == RESULT_OK){
            String s = SharedPreferencesTools.GetUsearInfo(this, "userSave", "userInfo");
            user = gson.fromJson(s, UserInfoLogin.class);
            tvName.setText(user.getName());
            setResult(RESULT_OK);
        }
        if (requestCode == Crop.REQUEST_PICK && resultCode == RESULT_OK) {
            MyLogcat.jLog().e("beginCrop:" + result.getData());
            beginCrop(result.getData());
        } else if (requestCode == Crop.REQUEST_CROP) {
            handleCrop(resultCode, result);
            /** 相机回调*/
        } else if (requestCode == CAMERA_REQUEST_CODE) {
            Bundle extras = result.getExtras();
            if (extras != null) {
                Bitmap bm = extras.getParcelable("data");
                MyLogcat.jLog().e("Bitmap:" + bm);
                try {
                    Uri uri = saveBitmap(bm);
                    MyLogcat.jLog().e("startImageZoom uri:" + uri);
                    startImageZoom(uri);
                    // beginCrop(uri);
                } catch (Exception e) {
                    MyLogcat.jLog().e("startImageZoom:" + e.getMessage());
                }


            }
            /** 相机裁剪的result*/
        } else if (requestCode == CROP_REQUEST_CODE) {
            if (result == null) {
                return;
            }
            Bundle extras = result.getExtras();
            if (extras == null) {
                return;
            }
            MyLogcat.jLog().e("相机result:" + extras.getParcelable("data"));
            Bitmap bm = extras.getParcelable("data");
            ivIcon.setImageBitmap(bm);
            /** bm - uri*/
            icon = Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(), bm, null, null));
            upLoadImage(icon);
            // XProgressDialog xProgressDialog = new XProgressDialog(this, "头像上传中...", XProgressDialog.THEME_CIRCLE_PROGRESS);
            //xProgressDialog.show();
        }
    }


    private Uri saveBitmap(Bitmap bm) {
        File tmpDir = new File(Environment.getExternalStorageDirectory() + "/ryts");
        if (!tmpDir.exists()) {
            tmpDir.mkdir();
        }
        //String filename = new SimpleDateFormat("yyMMddHHmmss").format(new java.util.Date());
        long filename = TimeUtil.dateToLong(new Date());
        File imgFile = new File(tmpDir.getAbsolutePath() + filename);
        MyLogcat.jLog().e("路径:" + imgFile.toString());
        try {
            FileOutputStream fos = new FileOutputStream(imgFile);
            bm.compress(Bitmap.CompressFormat.PNG, 85, fos);
            fos.flush();
            fos.close();
            return Uri.fromFile(imgFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void startImageZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, CROP_REQUEST_CODE);
    }

    Dialog dialog;

    private void showAvatarPop() {

        View view = View.inflate(this, R.layout.photo_choose_dialog, null);
        Button mbtnGallery = (Button) view.findViewById(R.id.btn_photo_gallery);
        Button mbtnPhotograph = (Button) view.findViewById(R.id.btn_photo_photograph);
        Button mbtnCancel = (Button) view.findViewById(R.id.btn_photo_cancel);
        mbtnGallery.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                /*Intent intent1 = new Intent(Intent.ACTION_GET_CONTENT);
                intent1.setType("image");
                startActivityForResult(intent1, GALLERY_REQUEST_CODE);*/
                try {
                    Crop.pickImage(PersonaldataActivity.this);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(PersonaldataActivity.this, "打开图库失败，请查看是否开启权限或稍后再试", Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }
        });
        mbtnPhotograph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent2, CAMERA_REQUEST_CODE);
                dialog.dismiss();
            }
        });
        mbtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog = new Dialog(this, R.style.transparentFrameWindowStyle);
        dialog.setContentView(view, new LinearLayout.LayoutParams(WindowManager.LayoutParams.FILL_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        Window window = dialog.getWindow();
        // 设置显示动画
        window.setWindowAnimations(R.style.main_menu_animstyle);
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.x = 0;
        wl.y = this.getWindowManager().getDefaultDisplay().getHeight();

        wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
        wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;


        dialog.onWindowAttributesChanged(wl);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }

    File sdcardTempFile;
    File audioFile;  //相机是否也是这个文件？
    Uri destination;

    /**
     * 开始裁剪
     * @param source
     */
    private void beginCrop(Uri source) {
        try {
            sdcardTempFile = File.createTempFile(".icon", ".jpg", audioFile);
        } catch (IOException e) {
            MyLogcat.jLog().e("" + e.getMessage());
        }
        destination = Uri.fromFile(sdcardTempFile);
        Crop.of(source, destination).asSquare().start(this);
    }

    /**更新头像uri**/
    Uri icon ;
    /**
     * 将裁剪的数据进行处理
     * @param resultCode
     * @param result
     */
    private void handleCrop(final int resultCode, Intent result) {
        if (resultCode == RESULT_OK) {
            icon = Crop.getOutput(result);
            MyLogcat.jLog().e("output :" + icon.toString());
            ivIcon.setImageURI(icon);
            upLoadImage(icon);
        } else if (resultCode == Crop.RESULT_ERROR) {
            //失败
        }
    }

    private void upLoadImage(Uri uri) {
        String userid = user.getUserid();
        RetrofitHttpUpLoad retrofitHttpUpLoad = RetrofitHttpUpLoad.getInstance();
        retrofitHttpUpLoad.clear();
        String realFilePath = StringUtils.getRealFilePath(this, uri);
        MyLogcat.jLog().e("realFilePath:" + realFilePath);
        retrofitHttpUpLoad = retrofitHttpUpLoad.addParameter("pic", new File(realFilePath));
        Map<String, RequestBody> params = retrofitHttpUpLoad
                .addParameter("userid", userid)
                .bulider();
        retrofitHttpUpLoad.addToEnqueue(retrofitHttpUpLoad.mHttpService.upLoadAgree(params),
                this, HttpStaticApi.HTTP_UPLOADIMAGE);
    }

    @Override
    public void onResponse(Response response, int method) {
        switch (method) {
            case HttpStaticApi.HTTP_UPLOADIMAGE:
                ToolUtils.showToast(this,"头像上传成功",Toast.LENGTH_SHORT);
                user.setIcon(icon.toString());
                SharedPreferencesTools.SaveUserInfo(this,"userSave","userInfo",gson.toJson(user));
                setResult(RESULT_OK);
                break;
            default:
                break;
        }
    }

    @Override
    public void onFailure(Response response, int method) {
        ToolUtils.showToast(this,"头像上传失败",Toast.LENGTH_SHORT);
    }

    /*public void changeIcon(String key) {
        OkHttpUtils
                .post()
                .url(Http_data.http_data + "/changeIcon")
                .addParams("userId", sp.getTag().getId() + "")
                .addParams("fileName", key)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                        Toast.makeText(PersonaldataActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        if (response.equals("2")) {
                            Toast.makeText(PersonaldataActivity.this, "修改头像失败", Toast.LENGTH_SHORT).show();
                        } else {
                            Bitmap bmp = BitmapFactory.decodeFile(sdcardTempFile.getAbsolutePath());

                            iv_icon.setImageBitmap(bmp);

                            String icon = sdcardTempFile.getAbsolutePath();

                            sp.setUsericon(icon);

                            User_Http.user.setIcon(null);

                            Toast.makeText(PersonaldataActivity.this, "修改头像成功", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }*/


}


