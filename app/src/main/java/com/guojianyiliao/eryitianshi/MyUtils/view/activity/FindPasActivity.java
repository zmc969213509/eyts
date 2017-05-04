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
import com.guojianyiliao.eryitianshi.R;
import com.guojianyiliao.eryitianshi.View.activity.FindpawActivity;
import com.guojianyiliao.eryitianshi.View.activity.LoginSelectActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 忘记密码页面
 * jnluo，jnluo5889@126.com
 */
public class FindPasActivity extends BaseActivity {

    @BindView(R.id.ivb_back_finsh)
    ImageButton ivbBackFinsh;
    @BindView(R.id.tv_foot_center)
    TextView tvFootCenter;
    @BindView(R.id.ed_find_phone)
    EditText edFindPhone;
    @BindView(R.id.ed_find_code)
    EditText edFindCode;
    @BindView(R.id.tv_find_code)
    TextView tvFindCode;
    @BindView(R.id.ed_find_pas)
    EditText edFindPas;
    @BindView(R.id.ed_find_pas_next)
    EditText edFindPasNext;
    @BindView(R.id.btn_find_http)
    Button btnFindHttp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_activity_find_password);
        ButterKnife.bind(this);
        tvFootCenter.setText("忘记密码");
    }

    @OnClick(R.id.ivb_back_finsh)
    public void Finsh() {
        this.finish();
    }

    @OnClick(R.id.tv_find_code)
    public void SendCode() {
        String phone = edFindPhone.getText().toString();
        if (StringUtils.isEmpty(phone)) {
            ToolUtils.showToast(FindPasActivity.this, "请正确填写手机号！", Toast.LENGTH_SHORT);
            return;
        }
        if (!StringUtils.isMobile(phone)) {
            ToolUtils.showToast(FindPasActivity.this, "请正确填写手机号！", Toast.LENGTH_SHORT);
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
                        ToolUtils.showToast(FindPasActivity.this, "验证码已发送", Toast.LENGTH_LONG);
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        ToolUtils.showToast(FindPasActivity.this, "网络连接失败！", Toast.LENGTH_SHORT);
                    }
                });
    }

    @OnClick(R.id.btn_find_http)
    public void HttpFindPas() {
        String phone = edFindPhone.getText().toString();
        if (StringUtils.isEmpty(phone)) {
            ToolUtils.showToast(FindPasActivity.this, "请填写手机号！", Toast.LENGTH_SHORT);
            return;
        }

        String pas = edFindPas.getText().toString();
        if (StringUtils.isEmpty(pas)) {
            ToolUtils.showToast(FindPasActivity.this, "请填写密码！", Toast.LENGTH_SHORT);
            return;
        }
        String pasnext = edFindPasNext.getText().toString();
        if (StringUtils.isEmpty(pasnext)) {
            ToolUtils.showToast(FindPasActivity.this, "请确认密码！", Toast.LENGTH_SHORT);
            return;
        }

        if (!pas.equals(pasnext)) {
            ToolUtils.showToast(FindPasActivity.this, "两次输入的密码不一致！", Toast.LENGTH_SHORT);
            return;
        }

        String smscode = edFindCode.getText().toString();

        if (StringUtils.isEmpty(smscode)) {
            ToolUtils.showToast(FindPasActivity.this, "请正确填写验证码！", Toast.LENGTH_SHORT);
            return;
        }
        String pasMD5 = StringUtils.MD5encrypt(pas).toString().toUpperCase();
        MyLogcat.jLog().e("Http regist:" + phone + "/" + pasMD5 + "/" + smscode);
        Retrofit rt = new Retrofit.Builder()
                .baseUrl(Http_data.http_data)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GetService getService = rt.create(GetService.class);
        getService.HttpFindPas(phone, smscode, pasMD5)
                .enqueue(new Callback<String>() {
                             @Override
                             public void onResponse(Call<String> call, Response<String> response) {
                                 MyLogcat.jLog().e("HttpRegist: " + response.body().toString());
                                 ToolUtils.showToast(FindPasActivity.this, "密码修改成功！", Toast.LENGTH_SHORT);
                                 startActivity(new Intent(FindPasActivity.this, LoginSelectActivity.class));
                                 finish();
                             }

                             @Override
                             public void onFailure(Call<String> call, Throwable t) {
                                 ToolUtils.showToast(FindPasActivity.this, "网络连接失败！请重试！", Toast.LENGTH_SHORT);
                                 startActivity(new Intent(FindPasActivity.this, LoginSelectActivity.class));
                                 Finsh();
                             }
                         }

                );

    }

    CountDownTimer timer = new CountDownTimer(45000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            tvFindCode.setText((millisUntilFinished / 1000) + "秒后可重发");
        }

        @Override
        public void onFinish() {
            tvFindCode.setEnabled(true);
            tvFindCode.setText("获取验证码");
        }
    };

}
