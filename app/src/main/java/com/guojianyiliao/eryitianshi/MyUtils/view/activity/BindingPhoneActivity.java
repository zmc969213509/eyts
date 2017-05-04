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
import com.guojianyiliao.eryitianshi.MyUtils.utlis.SpUtils;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.StringUtils;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.ToolUtils;
import com.guojianyiliao.eryitianshi.R;
import com.guojianyiliao.eryitianshi.View.activity.HomeActivity;
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
 * 绑定手机页面
 * jnluo,jnluo5889@126.com
 */

public class BindingPhoneActivity extends BaseActivity {

    @BindView(R.id.ivb_back_finsh)
    ImageButton ivbBackFinsh;
    @BindView(R.id.tv_foot_center)
    TextView tvFootCenter;
    @BindView(R.id.ed_binding_phone)
    EditText edBindingPhone;
    @BindView(R.id.ed_binding_code)
    EditText edBindingCode;
    @BindView(R.id.tv_binding_code)
    TextView tvBindingCode;
    @BindView(R.id.btn_binding)
    Button btnBinding;

    String type;
    String uid;
    String name;
    String gender;
    String icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_activity_binding_phone);
        ButterKnife.bind(this);
        tvFootCenter.setText("绑定手机号");

        type = SpUtils.getInstance(this).get("type", null);
        uid = SpUtils.getInstance(this).get("uid", null);
        name = SpUtils.getInstance(this).get("name", null);
        gender = SpUtils.getInstance(this).get("gender", null);
        icon = SpUtils.getInstance(this).get("iconurl", null);

    }

    @OnClick(R.id.tv_binding_code)
    public void SendCode() {
        String phone = edBindingPhone.getText().toString();
        MyLogcat.jLog().e("code:" + phone + "/" + StringUtils.isEmpty(phone) + "/" + StringUtils.isMobile(phone));
        if (StringUtils.isEmpty(phone)) {
            ToolUtils.showToast(BindingPhoneActivity.this, "请正确填写手机号！", Toast.LENGTH_SHORT);
            return;
        }
        if (!StringUtils.isMobile(phone)) {
            ToolUtils.showToast(BindingPhoneActivity.this, "请正确填写手机号！", Toast.LENGTH_SHORT);
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
                        ToolUtils.showToast(BindingPhoneActivity.this, "验证码已发送", Toast.LENGTH_LONG);
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        ToolUtils.showToast(BindingPhoneActivity.this, "网络连接失败！", Toast.LENGTH_SHORT);
                    }
                });
    }
    CountDownTimer timer = new CountDownTimer(45000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            tvBindingCode.setText((millisUntilFinished / 1000) + "秒后可重发");
        }

        @Override
        public void onFinish() {
            tvBindingCode.setEnabled(true);
            tvBindingCode.setText("获取验证码");
        }
    };
    @OnClick(R.id.btn_binding)
    public void CompletePhone(){
        String phone = edBindingPhone.getText().toString();
        MyLogcat.jLog().e("code:" + phone + "/" + StringUtils.isEmpty(phone) + "/" + StringUtils.isMobile(phone));

        if (StringUtils.isEmpty(phone)) {
            ToolUtils.showToast(BindingPhoneActivity.this, "请正确填写手机号！", Toast.LENGTH_SHORT);
            return;
        }
        if (!StringUtils.isMobile(phone)) {
            ToolUtils.showToast(BindingPhoneActivity.this, "请正确填写手机号！", Toast.LENGTH_SHORT);
            return;
        }
        String code = edBindingCode.getText().toString();

        if (StringUtils.isEmpty(code)) {
            ToolUtils.showToast(BindingPhoneActivity.this, "请正确填写手机号！", Toast.LENGTH_SHORT);
            return;
        }
        MyLogcat.jLog().e("phone" + phone + "/name"+name+"/gender"+gender+"/icon"+icon+"/uid"+uid+"/type"+type);
        Retrofit rt = new Retrofit.Builder()
               // .baseUrl(Http_data.http_data)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GetService getService = rt.create(GetService.class);
        getService.BindingPhone(phone, name, gender, icon, uid, type)
                .enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        MyLogcat.jLog().e(" binding success ");
                        ToolUtils.showToast(BindingPhoneActivity.this, "绑定成功！", Toast.LENGTH_SHORT);
                        startActivity(new Intent(BindingPhoneActivity.this, HomeActivity.class));
                        finish();
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        ToolUtils.showToast(BindingPhoneActivity.this, "网络获取失败！请重试", Toast.LENGTH_SHORT);
                        startActivity(new Intent(BindingPhoneActivity.this, LoginSelectActivity.class));
                        finish();
                    }
                });


    }
}
