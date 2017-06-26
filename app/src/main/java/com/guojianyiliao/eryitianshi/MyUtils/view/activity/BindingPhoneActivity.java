package com.guojianyiliao.eryitianshi.MyUtils.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.guojianyiliao.eryitianshi.Data.Http_data;
import com.guojianyiliao.eryitianshi.MyUtils.base.BaseActivity;
import com.guojianyiliao.eryitianshi.MyUtils.bean.UserInfoLogin;
import com.guojianyiliao.eryitianshi.MyUtils.interfaceservice.GetService;
import com.guojianyiliao.eryitianshi.MyUtils.manager.RetrofitClient;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.HttpTools;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.MyLogcat;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.SharedPreferencesTools;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.SpUtils;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.StringUtils;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.ToolUtils;
import com.guojianyiliao.eryitianshi.R;
import com.guojianyiliao.eryitianshi.View.activity.HomeActivity;
import com.guojianyiliao.eryitianshi.View.activity.LoginSelectActivity;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.StringReader;

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

    Gson gson;
    HttpTools tools;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_activity_binding_phone);
        ButterKnife.bind(this);
        tvFootCenter.setText("绑定手机号");

        gson = new Gson();

        tools = new HttpTools();

        type = SharedPreferencesTools.GetLoginType(this,"userSave","userType");
        uid = getIntent().getStringExtra("uid");
        name = getIntent().getStringExtra("name");
        gender = getIntent().getStringExtra("gender");
        icon = getIntent().getStringExtra("iconurl");

    }

    @OnClick(R.id.tv_binding_code)
    public void SendCode() {
        String phone = edBindingPhone.getText().toString();
        MyLogcat.jLog().e("code:" + phone + "/" + StringUtils.isEmpty(phone) + "/" + StringUtils.isMobile(phone));
        if (StringUtils.isEmpty(phone)) {
            ToolUtils.showToast(BindingPhoneActivity.this, "请填写手机号！", Toast.LENGTH_SHORT);
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

    /**电话号码**/
    String phone;
    /**
     * 绑定完成
     */
    @OnClick(R.id.btn_binding)
    public void CompletePhone(){
        phone = edBindingPhone.getText().toString();
        MyLogcat.jLog().e("code:" + phone + "/" + StringUtils.isEmpty(phone) + "/" + StringUtils.isMobile(phone));

        if (StringUtils.isEmpty(phone)) {
            ToolUtils.showToast(BindingPhoneActivity.this, "请填写手机号！", Toast.LENGTH_SHORT);
            return;
        }
        if (!StringUtils.isMobile(phone)) {
            ToolUtils.showToast(BindingPhoneActivity.this, "请正确填写手机号！", Toast.LENGTH_SHORT);
            return;
        }
        String code = edBindingCode.getText().toString();

        if (StringUtils.isEmpty(code)) {
            ToolUtils.showToast(BindingPhoneActivity.this, "请填写注册码！", Toast.LENGTH_SHORT);
            return;
        }

        String url = "";

        String ip = "http://139.224.196.16/AppServer/";
        if(type.equals("qq")){
            url = ip + "user/bindPhone?phone="+phone+"&name="+name+"&gender="+gender+"&icon="+icon+"&qq="+uid+"&code="+code;
        }else if(type.equals("wechat")){
            url = ip+"user/bindPhone?phone="+phone+"&name="+name+"&gender="+gender+"&icon="+icon+"&wechat="+uid+"&code="+code;
        }else if(type.equals("webo")){
            url = ip+"user/bindPhone?phone="+phone+"&name="+name+"&gender="+gender+"&icon="+icon+"&weibo="+uid+"&code="+code;
        }
        /**
         *  此处由于不同的情况  返回的数据不是同一对象类型  故用自己的
         */
        tools.execute(url, new HttpTools.HttpCallBack() {
            @Override
            public void success(String result) {
                Log.e("LoginSelectActivity","result = "+result);
                if(result.contains("codeError")){
                        ToolUtils.showToast(BindingPhoneActivity.this, "验证码错误！", Toast.LENGTH_SHORT);
                        Log.e("LoginSelectActivity","验证码错误");
                    }else if(result.contains("used")){
                        ToolUtils.showToast(BindingPhoneActivity.this, "该号码已注册", Toast.LENGTH_SHORT);
                        Log.e("LoginSelectActivity","该号码已注册");
                    }else{//21322
                        try{
                            UserInfoLogin userInfoLogin = gson.fromJson(result, UserInfoLogin.class);
                            Log.e("LoginSelectActivity","1");
                            SharedPreferencesTools.SaveUserInfo(BindingPhoneActivity.this,"userSave","userInfo",result);
                            SharedPreferencesTools.SaveUserId(BindingPhoneActivity.this,"userSave","userId",userInfoLogin.getUserid());
                            SharedPreferencesTools.SaveLoginData(BindingPhoneActivity.this, "userSave", "userLoginData", uid);
                            Intent intent = new Intent(BindingPhoneActivity.this, HomeAcitivtyMy.class);
                            startActivity(intent);
                            finish();
                        }catch (Exception e){
                            Log.e("LoginSelectActivity","e = " + e.getMessage());
                        }
                    }
            }
            @Override
            public void fail() {
                Log.e("LoginSelectActivity","fail");
            }
        });
    }

    /**
     * 绑定成功后 进行登录
     */
//    private void login(final String pas){
//        String pasMD5 = StringUtils.MD5encrypt(pas).toUpperCase();
//        OkHttpUtils.post()
//                .url(Http_data.http_data + "user/Login")
//                .addParams("phone", phone)
//                .addParams("password", pasMD5)
//                .build()
//                .execute(new StringCallback() {
//                    @Override
//                    public void onError(okhttp3.Call call, Exception e, int id) {
//                        Log.e("Login",e.getMessage());
//                        ToolUtils.showToast(BindingPhoneActivity.this, "登录失败！请检查网络！", Toast.LENGTH_LONG);
//                    }
//
//                    @Override
//                    public void onResponse(String response, int id) {
//                        try {
//                            UserInfoLogin user = gson.fromJson(response, UserInfoLogin.class);
//                            SharedPreferencesTools.SaveUserInfo(BindingPhoneActivity.this, "userSave", "userInfo", response);
//                            SharedPreferencesTools.SaveUserId(BindingPhoneActivity.this, "userSave", "userId", user.getUserid());
//
//                            if (!StringUtils.isEmpty(user.getName())) {
//                                Log.e("LoginSelectActivity", "succ");
//                                Intent intent = new Intent(BindingPhoneActivity.this, HomeAcitivtyMy.class);
//                                startActivity(intent);
//                            } else {
//                                Log.e("LoginSelectActivity", "fail");
//                                startActivity(new Intent(BindingPhoneActivity.this, BindingInfoData.class));
//                            }
//                            finish();
//                        } catch (Exception e) {
//                            Log.e("LoginSelectActivity", "e:" + e.getMessage());
//                            ToolUtils.showToast(BindingPhoneActivity.this, "登录异常" + e.getMessage(), Toast.LENGTH_SHORT);
//                            MyLogcat.jLog().e("登录异常:" + e.getMessage());
//                        }
//                    }
//                });
//    }


}
