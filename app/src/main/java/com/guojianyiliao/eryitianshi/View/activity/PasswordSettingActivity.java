package com.guojianyiliao.eryitianshi.View.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.guojianyiliao.eryitianshi.Data.Http_data;
import com.guojianyiliao.eryitianshi.Data.User_Http;
import com.guojianyiliao.eryitianshi.MyUtils.interfaceservice.GetService;
import com.guojianyiliao.eryitianshi.MyUtils.manager.RetrofitClient;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.MyLogcat;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.SharedPreferencesTools;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.SpUtils;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.StringUtils;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.ToolUtils;
import com.guojianyiliao.eryitianshi.MyUtils.view.activity.BindingPhoneActivity;
import com.guojianyiliao.eryitianshi.MyUtils.view.activity.HomeAcitivtyMy;
import com.guojianyiliao.eryitianshi.R;
import com.guojianyiliao.eryitianshi.Utils.MyBaseActivity;
import com.guojianyiliao.eryitianshi.Utils.SharedPsaveuser;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.im.android.api.JMessageClient;
import okhttp3.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;

/**
 *
 */
public class PasswordSettingActivity extends MyBaseActivity  {

    @BindView(R.id.phone_et)
    EditText phone_et;
    @BindView(R.id.phone_code_et)
    EditText code_et;
    @BindView(R.id.get_code)
    TextView code_tv;
    @BindView(R.id.first_pas_et)
    EditText first_pas_et;
    @BindView(R.id.again_pas_et)
    EditText again_pas_et;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_setting);
        ButterKnife.bind(this);

    }

    /**当前是否能获取验证码**/
    private boolean canGetCode = true;
    /**
     * 获取验证码
     */
    @OnClick(R.id.get_code)
    public void getCode() {
        if (!ToolUtils.isFastDoubleClick()) {
            if (canGetCode) {
                String phone = phone_et.getText().toString();
                MyLogcat.jLog().e("code:" + phone + "/" + StringUtils.isEmpty(phone) + "/" + StringUtils.isMobile(phone));
                if (StringUtils.isEmpty(phone)) {
                    ToolUtils.showToast(PasswordSettingActivity.this, "请填写手机号！", Toast.LENGTH_SHORT);
                    return;
                }
                if (!StringUtils.isMobile(phone)) {
                    ToolUtils.showToast(PasswordSettingActivity.this, "请正确填写手机号！", Toast.LENGTH_SHORT);
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
                                ToolUtils.showToast(PasswordSettingActivity.this, "验证码已发送", Toast.LENGTH_LONG);
                            }

                            @Override
                            public void onFailure(retrofit2.Call<String> call, Throwable t) {
                                ToolUtils.showToast(PasswordSettingActivity.this, "网络连接失败！", Toast.LENGTH_SHORT);
                            }
                        });
            }
        }


    }

    CountDownTimer timer = new CountDownTimer(45000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            code_tv.setText((millisUntilFinished / 1000) + "秒后可重发");
        }

        @Override
        public void onFinish() {
            code_tv.setEnabled(true);
            code_tv.setText("获取验证码");
            canGetCode = true;
        }
    };


    /**
     * 完成
     */
    @OnClick(R.id.pas_finish)
    public void changPas(){
        Log.e("PasswordSettingActivity","finish");
        final String phone = phone_et.getText().toString();
        String code = code_et.getText().toString();
        final String fisPsw = first_pas_et.getText().toString();
        String againPsw = again_pas_et.getText().toString();

        if(TextUtils.isEmpty(phone.trim())){
            ToolUtils.showToast(PasswordSettingActivity.this, "手机号不能为空", Toast.LENGTH_LONG);
        }else if(!StringUtils.isMobile(phone.trim())){
            ToolUtils.showToast(PasswordSettingActivity.this, "请正确填写手机号！", Toast.LENGTH_SHORT);
        }else if(TextUtils.isEmpty(code)){
            ToolUtils.showToast(PasswordSettingActivity.this, "请输入验证码！", Toast.LENGTH_SHORT);
        }else if(TextUtils.isEmpty(fisPsw) || TextUtils.isEmpty(againPsw)){
            ToolUtils.showToast(PasswordSettingActivity.this, "密码不能为空！", Toast.LENGTH_SHORT);
        }else if(!fisPsw.equals(againPsw)){
            ToolUtils.showToast(PasswordSettingActivity.this, "两次密码不一致", Toast.LENGTH_SHORT);
        }else{
            Log.e("PasswordSettingActivity","参数正常");
            String pasMD5 = StringUtils.MD5encrypt(fisPsw).toUpperCase();
            RetrofitClient.getinstance(this).create(GetService.class).resetPsw(phone,code,pasMD5).enqueue(new Callback<String>() {
                @Override
                public void onResponse(retrofit2.Call<String> call, Response<String> response) {
                    if(response.isSuccessful()){
                        String body = response.body();
                        Log.e("PasswordSettingActivity","response = "+ body);
                        if(body.contains("codeError")){
                            ToolUtils.showToast(PasswordSettingActivity.this, "验证码错误！", Toast.LENGTH_SHORT);
                        }else if (body.equals("true")) {
                            String type = SharedPreferencesTools.GetLoginType(PasswordSettingActivity.this, "userSave", "userType");
                            if (type.equals("phone")) {
                                SharedPreferencesTools.SaveLoginData(PasswordSettingActivity.this, "userSave", "userLoginData", phone + "," + fisPsw);
                            }
                            // 密码修改成功
                            ToolUtils.showToast(getApplicationContext(), "密码修改成功！", Toast.LENGTH_SHORT);
                            onBackPressed();
                        }
                    }
                }

                @Override
                public void onFailure(retrofit2.Call<String> call, Throwable t) {
                    ToolUtils.showToast(PasswordSettingActivity.this, "网络连接错误，请重试！", Toast.LENGTH_SHORT);
                }
            });
        }

    }

    /**
     * 返回按键
     */
    @OnClick(R.id.psa_back)
    public void back(){
        onBackPressed();
    }

}
