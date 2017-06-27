package com.guojianyiliao.eryitianshi.MyUtils.view.activity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.guojianyiliao.eryitianshi.Data.Http_data;
import com.guojianyiliao.eryitianshi.MyUtils.interfaceservice.GetService;
import com.guojianyiliao.eryitianshi.MyUtils.manager.RetrofitClient;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.MyLogcat;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.SharedPreferencesTools;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.StringUtils;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.ToolUtils;
import com.guojianyiliao.eryitianshi.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by zmc on 2017/6/15.
 * 忘记密码界面
 */

public class ForgerPswActivity extends AppCompatActivity{

    @BindView(R.id.ed_regist_phone)
    EditText edRegistPhone;
    @BindView(R.id.ed_regist_code)
    EditText edRegistCode;
    @BindView(R.id.tv_regist_code)
    TextView tvRegistCode;
    @BindView(R.id.ed_regist_pas)
    EditText edRegistPas;
    @BindView(R.id.ed_regist_pas_next)
    EditText edRegistPasNext;
    @BindView(R.id.btn_regist)
    Button btnRegist;
    @BindView(R.id.tv_foot_center)
    TextView tvFootCenter;

    //用户协议
    @BindView(R.id.rl_pas_next_below)
    RelativeLayout view;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_activity_regist);
        ButterKnife.bind(this);
        tvFootCenter.setText("忘记密码");
        view.setVisibility(View.GONE);
        btnRegist.setText("完成");
    }

    @OnClick(R.id.ivb_back_finsh)
    public void back(){
        onBackPressed();
    }

    /**
     * 完成
     */
    @OnClick(R.id.btn_regist)
    public void changPas(){
        Log.e("ForgerPswActivity","finish");
        final String phone = edRegistPhone.getText().toString();
        String code = edRegistCode.getText().toString();
        final String fisPsw = edRegistPas.getText().toString();
        String againPsw = edRegistPasNext.getText().toString();

        if(TextUtils.isEmpty(phone.trim())){
            ToolUtils.showToast(ForgerPswActivity.this, "手机号不能为空", Toast.LENGTH_LONG);
        }else if(!StringUtils.isMobile(phone.trim())){
            ToolUtils.showToast(ForgerPswActivity.this, "请正确填写手机号！", Toast.LENGTH_SHORT);
        }else if(TextUtils.isEmpty(code)){
            ToolUtils.showToast(ForgerPswActivity.this, "请输入验证码！", Toast.LENGTH_SHORT);
        }else if(TextUtils.isEmpty(fisPsw) || TextUtils.isEmpty(againPsw)){
            ToolUtils.showToast(ForgerPswActivity.this, "密码不能为空！", Toast.LENGTH_SHORT);
        }else if(!fisPsw.equals(againPsw)){
            ToolUtils.showToast(ForgerPswActivity.this, "两次密码不一致", Toast.LENGTH_SHORT);
        }else{
            Log.e("ForgerPswActivity","参数正常");
            String pasMD5 = StringUtils.MD5encrypt(fisPsw).toUpperCase();
            RetrofitClient.getinstance(this).create(GetService.class).resetPsw(phone,code,pasMD5).enqueue(new Callback<String>() {
                @Override
                public void onResponse(retrofit2.Call<String> call, Response<String> response) {
                    if(response.isSuccessful()){
                        String body = response.body();
                        Log.e("ForgerPswActivity","response = "+ body);
                        if(body.contains("codeError")){
                            ToolUtils.showToast(ForgerPswActivity.this, "验证码错误！", Toast.LENGTH_SHORT);
                        }else if (body.equals("true")) {
                            String type = SharedPreferencesTools.GetLoginType(ForgerPswActivity.this, "userSave", "userType");
                            if (type.equals("phone")) {
                                SharedPreferencesTools.SaveLoginData(ForgerPswActivity.this, "userSave", "userLoginData", phone + "," + fisPsw);
                            }
                            // 密码修改成功
                            ToolUtils.showToast(getApplicationContext(), "密码修改成功！", Toast.LENGTH_SHORT);
                            onBackPressed();
                        }
                    }
                }

                @Override
                public void onFailure(retrofit2.Call<String> call, Throwable t) {
                    ToolUtils.showToast(ForgerPswActivity.this, "网络连接错误，请重试！", Toast.LENGTH_SHORT);
                }
            });
        }

    }

    /**当前是否能获取验证码**/
    private boolean canGetCode = true;
    /**
     * 获取验证码
     */
    @OnClick(R.id.tv_regist_code)
    public void sendCode() {
        if (!ToolUtils.isFastDoubleClick()) {
            if (canGetCode) {
                String phone = edRegistPhone.getText().toString();
                MyLogcat.jLog().e("code:" + phone + "/" + StringUtils.isEmpty(phone) + "/" + StringUtils.isMobile(phone));
                if (StringUtils.isEmpty(phone)) {
                    ToolUtils.showToast(ForgerPswActivity.this, "请填写手机号！", Toast.LENGTH_SHORT);
                    return;
                }
                if (!StringUtils.isMobile(phone)) {
                    ToolUtils.showToast(ForgerPswActivity.this, "请正确填写手机号！", Toast.LENGTH_SHORT);
                    return;
                }
                Retrofit rt = new Retrofit.Builder()
                        .baseUrl(Http_data.http_data)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                GetService getService = rt.create(GetService.class);
                getService.sendCode(phone)
                        .enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(retrofit2.Call<String> call, Response<String> response) {
                                MyLogcat.jLog().e("SendCode: " + response.body().toString());
                                timer.start();
                                canGetCode = false;
                                ToolUtils.showToast(ForgerPswActivity.this, "验证码已发送", Toast.LENGTH_LONG);
                            }

                            @Override
                            public void onFailure(retrofit2.Call<String> call, Throwable t) {
                                ToolUtils.showToast(ForgerPswActivity.this, "网络连接失败！", Toast.LENGTH_SHORT);
                            }
                        });
            }
        }
    }

    /** 谷歌提供的倒计时类*/
    CountDownTimer timer = new CountDownTimer(45000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            tvRegistCode.setText((millisUntilFinished / 1000) + "秒后可重发");
        }

        @Override
        public void onFinish() {
            tvRegistCode.setEnabled(true);
            tvRegistCode.setText("获取验证码");
        }
    };
}
