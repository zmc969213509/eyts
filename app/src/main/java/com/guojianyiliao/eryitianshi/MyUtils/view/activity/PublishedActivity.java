package com.guojianyiliao.eryitianshi.MyUtils.view.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.guojianyiliao.eryitianshi.MyUtils.base.BaseActivity;
import com.guojianyiliao.eryitianshi.MyUtils.bean.Moment;
import com.guojianyiliao.eryitianshi.MyUtils.interfaceservice.HttpStaticApi;
import com.guojianyiliao.eryitianshi.MyUtils.interfaceservice.RetrofitCallBack;
import com.guojianyiliao.eryitianshi.MyUtils.interfaceservice.RetrofitHttpUpLoad;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.MyLogcat;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.SpUtils;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.StringUtils;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.TimeUtil;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.ToolUtils;
import com.guojianyiliao.eryitianshi.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.photopicker.activity.BGAPhotoPickerActivity;
import cn.bingoogolapple.photopicker.activity.BGAPhotoPickerPreviewActivity;
import cn.bingoogolapple.photopicker.widget.BGASortableNinePhotoLayout;
import okhttp3.RequestBody;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Response;

/**
 * description: 发布说说页面
 * autour: jnluo jnluo5889@126.com
 * date: 2017/4/17 14:21
 * update: 2017/4/17
 */
public class PublishedActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks, BGASortableNinePhotoLayout.Delegate, RetrofitCallBack {
    @BindView(R.id.ivb_back_finsh)
    ImageButton ivbBackFinsh;
    @BindView(R.id.tv_back_left)
    TextView tvBackLeft;
    @BindView(R.id.tv_foot_center)
    TextView tvFootCenter;
    @BindView(R.id.et_moment_add_content)
    EditText etMomentAddContent;
    @BindView(R.id.snpl_moment_add_photos)
    BGASortableNinePhotoLayout snplMomentAddPhotos;
    @BindView(R.id.tv_right)
    TextView tvRight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_activity_published);
        ButterKnife.bind(this);
        ivbBackFinsh.setVisibility(View.GONE);
        tvBackLeft.setVisibility(View.VISIBLE);
        tvFootCenter.setText("发布");
        tvRight.setVisibility(View.VISIBLE);

        snplMomentAddPhotos.setMaxItemCount(9);
        snplMomentAddPhotos.setEditable(true);
        snplMomentAddPhotos.setPlusEnable(true);
        snplMomentAddPhotos.setDelegate(this);

    }

    @OnClick(R.id.tv_right)
    public void published() {

        if (!ToolUtils.isFastDoubleClick()) {
            String content = etMomentAddContent.getText().toString();
            MyLogcat.jLog().e("content:" + StringUtils.isEmpty(content));
            if (StringUtils.isEmpty(content) && snplMomentAddPhotos.getItemCount() == 0) {
                ToolUtils.showToast(this, "必须填写这一刻的想法或选择照片！", Toast.LENGTH_SHORT);
                return;
            }

            MyLogcat.jLog().e("snplMomentAddPhotos:" + snplMomentAddPhotos.getData());
            Intent intent = new Intent();
            intent.putExtra(EXTRA_MOMENT, new Moment(etMomentAddContent.getText().toString().trim(), snplMomentAddPhotos.getData()));
            setResult(RESULT_OK, intent);

            //上传到服务器
            publishEssay();

        }
    }

    private void publishEssay() {
        try {
            String epubtime = TimeUtil.currectTime();
            String userid = SpUtils.getInstance(this).get("userid", null);
            ArrayList<String> data = snplMomentAddPhotos.getData();

            RetrofitHttpUpLoad retrofitHttpUpLoad = RetrofitHttpUpLoad.getInstance();
            retrofitHttpUpLoad.clear();
            for (int i = 0; i < data.size(); i++) {
                String images = "images" + i;
                retrofitHttpUpLoad = retrofitHttpUpLoad.addParameter(images, new File(data.get(i)));
            }

            Map<String, RequestBody> params = retrofitHttpUpLoad
                    .addParameter("userid", userid)
                    .addParameter("econtent", "android")
                    //.addParameter("epubtime", "")//2017-04-17 16:45
                    .bulider();

            retrofitHttpUpLoad.addToEnqueue(retrofitHttpUpLoad.mHttpService.publishEssay(params), this, HttpStaticApi.HTTP_UPLOADIMAGE_UP);
            MyLogcat.jLog().e("上传到服务器 qqq epubtime：" + epubtime);
        } catch (Exception e) {
            MyLogcat.jLog().e("onRespo上传到服务器 Exception:" + e.getMessage());
        }
    }


    private static final int REQUEST_CODE_PERMISSION_PHOTO_PICKER = 1;

    private static final int REQUEST_CODE_CHOOSE_PHOTO = 1;
    private static final int REQUEST_CODE_PHOTO_PREVIEW = 2;

    private static final String EXTRA_MOMENT = "EXTRA_MOMENT";

    @Override
    public void onClickAddNinePhotoItem(BGASortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, ArrayList<String> models) {
        choicePhotoWrapper();
    }

    @Override
    public void onClickDeleteNinePhotoItem(BGASortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, String model, ArrayList<String> models) {
        snplMomentAddPhotos.removeItem(position);
    }

    @Override
    public void onClickNinePhotoItem(BGASortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, String model, ArrayList<String> models) {
        startActivityForResult(BGAPhotoPickerPreviewActivity.newIntent(this, snplMomentAddPhotos.getMaxItemCount(), models, models, position, false), REQUEST_CODE_PHOTO_PREVIEW);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        if (requestCode == REQUEST_CODE_PERMISSION_PHOTO_PICKER) {
            Toast.makeText(this, "您拒绝了「图片选择」所需要的相关权限!", Toast.LENGTH_SHORT).show();
        }
    }

    @AfterPermissionGranted(REQUEST_CODE_PERMISSION_PHOTO_PICKER)
    private void choicePhotoWrapper() {
        String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
        if (EasyPermissions.hasPermissions(this, perms)) {
            // 拍照后照片的存放目录，改成你自己拍照后要存放照片的目录。如果不传递该参数的话就没有拍照功能
            File takePhotoDir = new File(Environment.getExternalStorageDirectory(), "BGAPhotoPickerTakePhoto");

            startActivityForResult(BGAPhotoPickerActivity.newIntent(this, takePhotoDir, 9, null, false), REQUEST_CODE_CHOOSE_PHOTO);
        } else {
            EasyPermissions.requestPermissions(this, "图片选择需要以下权限:\n\n1.访问设备上的照片\n\n2.拍照", REQUEST_CODE_PERMISSION_PHOTO_PICKER, perms);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_CHOOSE_PHOTO) {
            snplMomentAddPhotos.addMoreData(BGAPhotoPickerActivity.getSelectedImages(data));
        } else if (requestCode == REQUEST_CODE_PHOTO_PREVIEW) {
            snplMomentAddPhotos.setData(BGAPhotoPickerPreviewActivity.getSelectedImages(data));
        }
    }

    @Override
    public void onResponse(Response response, int method) {
        switch (method) {
            case HttpStaticApi.HTTP_UPLOADIMAGE_UP:
                MyLogcat.jLog().e("发布说说 onResponse:" + response.body().toString());
                ToolUtils.showToast(PublishedActivity.this, "发布成功！", Toast.LENGTH_SHORT);
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    public void onFailure(Response response, int method) {
        MyLogcat.jLog().e("发布说说 onFailure ");
        ToolUtils.showToast(PublishedActivity.this, "发布失败！请检查网络！", Toast.LENGTH_SHORT);
    }
}
