package com.guojianyiliao.eryitianshi.MyUtils.view.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.guojianyiliao.eryitianshi.MyUtils.interfaceservice.GetService;
import com.guojianyiliao.eryitianshi.MyUtils.manager.RetrofitClient;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.AnimLoadingUtil;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.SharedPreferencesTools;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.TimeUtil;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.ToolUtils;
import com.guojianyiliao.eryitianshi.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by zmc on 2017/6/22.
 * 添加对医生评论activity
 */

public class AddCommentActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "AddCommentActivity";
    ImageView back_img;
    TextView submit_tv;
    EditText et;

    String docID;
    /**进入此页标识  0：医生聊天结束后进入的医生评论   1：预约挂号进入的医生评论**/
    int flag;
    String regId;

    View animView;
    AnimLoadingUtil animLoadingUtil;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zmc_activity_add_commnet);

        docID = getIntent().getStringExtra("docId");
        flag = getIntent().getIntExtra("flag",0);
        if(flag == 1){
            regId = getIntent().getStringExtra("regid");
        }

        animView = findViewById(R.id.anim_view_layout);
        animLoadingUtil = new AnimLoadingUtil(animView);

        back_img = (ImageView) findViewById(R.id.add_comment_back);
        submit_tv = (TextView) findViewById(R.id.add_comment_submit);
        et = (EditText) findViewById(R.id.add_comment_et);

        back_img.setOnClickListener(this);
        submit_tv.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v == back_img){
            onBackPressed();
        }else if(v == submit_tv){
            submit();
        }
    }

    PopupWindow mPopupWindow;
    View contentView;
    /**
     * 弹出window
     */
    private void showWindow() {
        mPopupWindow = new PopupWindow(LinearLayoutCompat.LayoutParams.WRAP_CONTENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT);
        contentView = LayoutInflater.from(this).inflate(R.layout.zmc_window_wenzhen, null);
        mPopupWindow.setContentView(contentView);
        //为mPopupWindow设置点击其他地方消失
        mPopupWindow.setFocusable(true);
        Bitmap bitmap = null;
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable(getResources(), bitmap));
        mPopupWindow.showAtLocation(contentView, Gravity.CENTER, 0, 0);

        ((TextView)contentView.findViewById(R.id.zmc_window_title)).setText("是否退出医生评论");

        //取消
        contentView.findViewById(R.id.zmc_window_cancle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();
            }
        });
        //确认
        contentView.findViewById(R.id.zmc_window_sure).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();
                finish();
            }
        });

        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.5f;
        getWindow().setAttributes(lp);
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1f;
                getWindow().setAttributes(lp);
            }
        });

    }


    /**
     * 提交医生评价
     */
    private void submit() {
        String comment = et.getText().toString();
        if(TextUtils.isEmpty(comment)){
            Toast.makeText(this,"评论不能为空~~~",Toast.LENGTH_SHORT).show();
            return;
        }
        animLoadingUtil.startAnim("评论提交中...");

        ToolUtils.closeKeybord(et,this);

        String userId = SharedPreferencesTools.GetUsearId(this, "userSave", "userId");

        Call<String> stringCall = null;
        if(flag == 0){
            stringCall = RetrofitClient.getinstance(this).create(GetService.class).addDocComment(userId, docID, comment, TimeUtil.currectTimess());
        }else if(flag == 1){
            stringCall = RetrofitClient.getinstance(this).create(GetService.class).addDocComment(userId, docID, comment, TimeUtil.currectTimess(),regId);
        }

        stringCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                animLoadingUtil.finishAnim();
                if(response.isSuccessful()){
                    String body = response.body();
                    if(body.contains("true")){
                        Toast.makeText(AddCommentActivity.this,"评论提交成功",Toast.LENGTH_SHORT).show();
                        if(flag == 1){ //预约挂号
                            setResult(RESULT_OK);
                        }
                        finish();
                    }else if(body.contains("false")){
                        Toast.makeText(AddCommentActivity.this,"评论提交失败，请重试",Toast.LENGTH_SHORT).show();
                    }else if(body.contains("error")){
                        Toast.makeText(AddCommentActivity.this,"参数异常",Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                animLoadingUtil.finishAnim();
                Toast.makeText(AddCommentActivity.this,"网络连接错误，请重试",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        showWindow();
    }
}
