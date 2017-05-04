package com.guojianyiliao.eryitianshi.MyUtils.view.activity;


import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.guojianyiliao.eryitianshi.Data.Http_data;
import com.guojianyiliao.eryitianshi.MyUtils.base.BaseActivity;
import com.guojianyiliao.eryitianshi.MyUtils.interfaceservice.GetService;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.MyLogcat;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.StringUtils;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.ToolUtils;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.UIUtils;
import com.guojianyiliao.eryitianshi.R;
import com.guojianyiliao.eryitianshi.View.activity.UserAgreementActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 注册页面
 * jnluo，jnluo5889@126.com
 */

public class RegistActivity extends BaseActivity {

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
    @BindView(R.id.ivb_back_finsh)
    ImageButton ivbBackFinsh;
    @BindView(R.id.tv_foot_center)
    TextView tvFootCenter;
    @BindView(R.id.iv_read_true)
    ImageButton ivReadTrue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_activity_regist);
        /** 一定要在setcontenview以后调用，非常汇报状态异常*/
        ButterKnife.bind(this);
        tvFootCenter.setText("注册");
    }

    boolean isRead = false;

    @OnClick(R.id.iv_read_true)
    public void isReadTrue() {
        if (!isRead) {
            isRead = true;
            ivReadTrue.setBackgroundResource(R.drawable.sele_icon);
        } else {
            isRead = false;
            ivReadTrue.setBackgroundResource(R.drawable.read_true);
        }
    }

    @OnClick(R.id.ivb_back_finsh)
    public void Back() {
        RegistActivity.this.finish();
    }


    @OnClick(R.id.tv_regist_code)
    public void sendCode() {
        String phone = edRegistPhone.getText().toString();
        MyLogcat.jLog().e("code:" + phone + "/" + StringUtils.isEmpty(phone) + "/" + StringUtils.isMobile(phone));
        if (StringUtils.isEmpty(phone)) {
            ToolUtils.showToast(RegistActivity.this, "请正确填写手机号！", Toast.LENGTH_SHORT);
            return;
        }
        if (!StringUtils.isMobile(phone)) {
            ToolUtils.showToast(RegistActivity.this, "请正确填写手机号！", Toast.LENGTH_SHORT);
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
                    public void onResponse(Call<String> call, Response<String> response) {
                        MyLogcat.jLog().e("SendCode: " + response.body().toString());
                        timer.start();
                        ToolUtils.showToast(RegistActivity.this, "验证码已发送", Toast.LENGTH_LONG);
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        MyLogcat.jLog().e("onFailure:"+ UIUtils.isRunOnUIThread()+"/"+ call.toString());
                        ToolUtils.showToast(RegistActivity.this, "网络连接失败！", Toast.LENGTH_SHORT);
                    }
                });
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

    @OnClick(R.id.btn_regist)
    public void httpRegist() {
        String phone = edRegistPhone.getText().toString();
        if (StringUtils.isEmpty(phone)) {
            ToolUtils.showToast(RegistActivity.this, "请填写手机号！", Toast.LENGTH_SHORT);
            return;
        }

        String pas = edRegistPas.getText().toString();
        if (StringUtils.isEmpty(pas)) {
            ToolUtils.showToast(RegistActivity.this, "请填写密码！", Toast.LENGTH_SHORT);
            return;
        }
        String pasnext = edRegistPasNext.getText().toString();
        if (StringUtils.isEmpty(pasnext)) {
            ToolUtils.showToast(RegistActivity.this, "请确认密码！", Toast.LENGTH_SHORT);
            return;
        }

        if (!pas.equals(pasnext)) {
            ToolUtils.showToast(RegistActivity.this, "两次输入的密码不一致！", Toast.LENGTH_SHORT);
            return;
        }

        String smscode = edRegistCode.getText().toString();
        if (StringUtils.isEmpty(smscode)) {
            ToolUtils.showToast(RegistActivity.this, "请正确填写验证码！", Toast.LENGTH_SHORT);
            return;
        }
        if (!isRead) {
            ToolUtils.showToast(RegistActivity.this, "请确认已经阅读注册协议！", Toast.LENGTH_SHORT);
            return;
        }
        String pasMD5 = StringUtils.MD5encrypt(pas).toString().toUpperCase();
        MyLogcat.jLog().e("Http regist:" + phone + "/" + pasMD5 + "/" + smscode);
        Retrofit rt = new Retrofit.Builder()
                .baseUrl(Http_data.http_data)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GetService getService = rt.create(GetService.class);
        getService.HttpRegist(phone, pasMD5, smscode)
                .enqueue(new Callback<String>() {
                             @Override
                             public void onResponse(Call<String> call, Response<String> response) {
                                 MyLogcat.jLog().e("HttpRegist: " + response.body().toString());
                                 if ("1".equals(response)) {
                                     ToolUtils.showToast(RegistActivity.this, "用户已注册", Toast.LENGTH_LONG);
                                 } else if ("2".equals(response)) {
                                     ToolUtils.showToast(RegistActivity.this, "请发送验证码", Toast.LENGTH_LONG);
                                 } else {
                                     ToolUtils.showToast(RegistActivity.this, "注册成功", Toast.LENGTH_LONG);
                                     finish();
                                 }
                             }

                             @Override
                             public void onFailure(Call<String> call, Throwable t) {
                                 ToolUtils.showToast(RegistActivity.this, "网络连接失败！", Toast.LENGTH_SHORT);
                             }
                         }

                );

    }

    @OnClick(R.id.tv_read_deal)
    public void redaDeal() {
        Intent intent = new Intent(RegistActivity.this, UserAgreementActivity.class);
        startActivity(intent);
    }
}
