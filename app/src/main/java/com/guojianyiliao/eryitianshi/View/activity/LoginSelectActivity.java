package com.guojianyiliao.eryitianshi.View.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.guojianyiliao.eryitianshi.Data.Http_data;
import com.guojianyiliao.eryitianshi.MyUtils.bean.UserInfoLogin;
import com.guojianyiliao.eryitianshi.MyUtils.interfaceservice.GetService;
import com.guojianyiliao.eryitianshi.MyUtils.manager.RetrofitClient;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.AnimLoadingUtil;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.MyLogcat;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.SharedPreferencesTools;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.StringUtils;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.ToolUtils;
import com.guojianyiliao.eryitianshi.MyUtils.view.activity.BindingInfoData;
import com.guojianyiliao.eryitianshi.MyUtils.view.activity.BindingPhoneActivity;
import com.guojianyiliao.eryitianshi.MyUtils.view.activity.ForgerPswActivity;
import com.guojianyiliao.eryitianshi.MyUtils.view.activity.HomeAcitivtyMy;
import com.guojianyiliao.eryitianshi.MyUtils.view.activity.RegistActivity;
import com.guojianyiliao.eryitianshi.R;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import okhttp3.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 登录界面
 * jnluo，jnluo5889@126.com
 */

public class LoginSelectActivity extends AppCompatActivity {
    private static final int SUCCEED_REGIST = 1;

    @BindView(R.id.et_login_phone)
    EditText etLoginPhone;
    @BindView(R.id.et_login_pas)
    EditText etLoginPas;
    @BindView(R.id.bt_login)
    Button btLogin;
    @BindView(R.id.tv_regist)
    TextView tvRegist;
    @BindView(R.id.tv_find_pas)
    TextView tvFindPas;
    @BindView(R.id.ivb_tencent)
    ImageButton ivbTencent;
    @BindView(R.id.ivb_wechat)
    ImageButton ivbWechat;
    @BindView(R.id.ivb_sina)
    ImageButton ivbSina;


    View animView;

    AnimLoadingUtil animLoadingUtil;

    String uid;
    String name;
    String gender;
    String iconurl;



    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            String uid = (String)msg.obj;
            Intent intent = new Intent(LoginSelectActivity.this, BindingPhoneActivity.class);
            intent.putExtra("uid",uid);
            startActivity(intent);
            finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.a_activity_login);

        ButterKnife.bind(this);

        gson = new Gson();
        animView = findViewById(R.id.anim_view_layout);
        animLoadingUtil = new AnimLoadingUtil(animView);
    }

    /**
     * 判断当前第三方UID是否已经注册了
     */
    private void isRegistered(){
        animLoadingUtil.startAnim("第三方应用登录中...");
        Log.e("Test~~~~ss","第三方应用登录中...");
        RetrofitClient.getinstance(this).create(GetService.class).isRegistered(uid).enqueue(new Callback<UserInfoLogin>() {
            @Override
            public void onResponse(retrofit2.Call<UserInfoLogin> call, Response<UserInfoLogin> response) {
                animLoadingUtil.finishAnim();
                if(response.isSuccessful()){
                    Log.e("Test~~~~ss","isRegistered - response = "+response.body());
                    try{
                        UserInfoLogin body = response.body();
                        String s = gson.toJson(body);
                        SharedPreferencesTools.SaveUserInfo(LoginSelectActivity.this,"userSave","userInfo",s);
                        SharedPreferencesTools.SaveUserId(LoginSelectActivity.this,"userSave","userId",body.getUserid());
                        SharedPreferencesTools.SaveLoginData(LoginSelectActivity.this, "userSave", "userLoginData", uid);
                        Log.e("Test~~~~ss","isRegistered - response  suc");
                        Intent intent = new Intent(LoginSelectActivity.this, HomeAcitivtyMy.class);
                        startActivity(intent);
                        finish();
                    }catch (Exception e){
                        Log.e("Test~~~~ss","isRegistered - response  fail  e:"+e.getMessage());
                        Intent intent = new Intent(LoginSelectActivity.this, BindingPhoneActivity.class);
                        intent.putExtra("uid",uid);
                        intent.putExtra("name",name);
                        intent.putExtra("iconurl",iconurl);
                        intent.putExtra("gender",gender);
                        startActivity(intent);
                        finish();
                    }
                }
            }

            @Override
            public void onFailure(retrofit2.Call<UserInfoLogin> call, Throwable t) {
                Log.e("Test~~~~ss","isRegistered - response  error  e:"+t.getMessage());
                animLoadingUtil.finishAnim();
                Intent intent = new Intent(LoginSelectActivity.this, BindingPhoneActivity.class);
                intent.putExtra("uid",uid);
                intent.putExtra("name",name);
                intent.putExtra("iconurl",iconurl);
                intent.putExtra("gender",gender);
                startActivity(intent);
                finish();
            }
        });
    }



    /**
     * 忘记密码
     */
    @OnClick(R.id.tv_find_pas)
    public void HttpFindPas() {
        startActivity(new Intent(this, ForgerPswActivity.class));
    }

    /**
     * qq登录
     */
    @OnClick(R.id.ivb_tencent)
    public void LoginTencent() {
        if (!ToolUtils.isFastDoubleClick()) {
            animLoadingUtil.startAnim("正在拉取应用信息...");
            UMShareAPI umShareAPI = UMShareAPI.get(this);
            umShareAPI.getPlatformInfo(this, SHARE_MEDIA.QQ, new UMAuthListener() {
                @Override
                public void onStart(SHARE_MEDIA share_media) {
                    MyLogcat.jLog().e("onStart:");
                }

                @Override
                public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                    animLoadingUtil.finishAnim();
                    uid = map.get("openid");
                    name = map.get("name");
                    gender = map.get("gender");
                    iconurl = map.get("iconurl");

                    Log.e("LoginSelectActivity","qq - openid = "+uid);
                    Log.e("LoginSelectActivity","qq - name = "+name);
                    Log.e("LoginSelectActivity","qq - gender = "+gender);
                    Log.e("LoginSelectActivity","qq - iconurl = "+iconurl);


                    SharedPreferencesTools.SaveLoginType(LoginSelectActivity.this,"userSave","userType","qq");
                    isRegistered();
                }

                @Override
                public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
                    animLoadingUtil.finishAnim();
                    MyLogcat.jLog().e("onError:");
                }

                @Override
                public void onCancel(SHARE_MEDIA share_media, int i) {
                    animLoadingUtil.finishAnim();
                    MyLogcat.jLog().e("onCancel:");
                }
            });

        }
    }

    /**
     * 微信登录
     */
    @OnClick(R.id.ivb_wechat)
    public void LoginWechat() {
        if (!ToolUtils.isFastDoubleClick()) {
            animLoadingUtil.startAnim("正在拉取应用信息...");
            UMShareAPI.get(this).getPlatformInfo(this, SHARE_MEDIA.WEIXIN, new UMAuthListener() {
                @Override
                public void onStart(SHARE_MEDIA share_media) {
                    Log.e("LoginSelectActivity","onStart");
                }

                @Override
                public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                    animLoadingUtil.finishAnim();
                    uid = map.get("uid");
                    name = map.get("name");
                    gender = map.get("gender");
                    iconurl = map.get("iconurl");
                    Log.e("Test~~~~ss","wechat - uid = "+uid);
                    Log.e("Test~~~~ss","wechat - name = "+name);
                    Log.e("Test~~~~ss","wechat - gender = "+gender);
                    Log.e("Test~~~~ss","wechat - iconurl = "+iconurl);

                    SharedPreferencesTools.SaveLoginType(LoginSelectActivity.this,"userSave","userType","wechat");
                    isRegistered();

                }

                @Override
                public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
                    animLoadingUtil.finishAnim();
                    Log.e("LoginSelectActivity","onError");
                }

                @Override
                public void onCancel(SHARE_MEDIA share_media, int i) {
                    animLoadingUtil.finishAnim();
                    Log.e("LoginSelectActivity","onCancel");
                }
            });
        }
    }

    /**
     * 微博登录
     */
    @OnClick(R.id.ivb_sina)
    public void LoginSina() {
        if (!ToolUtils.isFastDoubleClick()) {
            animLoadingUtil.startAnim("正在拉取应用信息...");
            UMShareAPI umShareAPI = UMShareAPI.get(this);
            umShareAPI.getPlatformInfo(this, SHARE_MEDIA.SINA, new UMAuthListener() {
                @Override
                public void onStart(SHARE_MEDIA share_media) {

                }

                @Override
                public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                    animLoadingUtil.finishAnim();
                    uid = map.get("uid");
                    name = map.get("name");
                    gender = map.get("gender");
                    iconurl = map.get("iconurl");

                    Log.e("LoginSelectActivity","sina - uid = "+uid);
                    Log.e("LoginSelectActivity","sina - name = "+name);
                    Log.e("LoginSelectActivity","sina - gender = "+gender);
                    Log.e("LoginSelectActivity","sina - iconurl = "+iconurl);
                    SharedPreferencesTools.SaveLoginType(LoginSelectActivity.this,"userSave","userType","webo");
                    isRegistered();

                }

                @Override
                public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
                    animLoadingUtil.finishAnim();
                }

                @Override
                public void onCancel(SHARE_MEDIA share_media, int i) {
                    animLoadingUtil.finishAnim();
                }
            });
        }
    }

    /**
     * 注册
     */
    @OnClick(R.id.tv_regist)
    public void Regist() {
        if (!ToolUtils.isFastDoubleClick()) {
            startActivityForResult(new Intent(this, RegistActivity.class), SUCCEED_REGIST);
        }

    }

    String phone;
    String pas;
    String pasMD5;
    Gson gson = new Gson();

    /**
     * 登录
     */
    @OnClick(R.id.bt_login)
    public void passLogin() {

        phone = etLoginPhone.getText().toString();
        if (StringUtils.isEmpty(phone)) {
            ToolUtils.showToast(LoginSelectActivity.this, "请填写手机号！", Toast.LENGTH_SHORT);
            return;
        }

        pas = etLoginPas.getText().toString();
        if (StringUtils.isEmpty(pas)) {
            ToolUtils.showToast(LoginSelectActivity.this, "请填写密码！", Toast.LENGTH_SHORT);
            return;
        }
        animLoadingUtil.startAnim("登录中...");
        pasMD5 = StringUtils.MD5encrypt(pas).toUpperCase();
        OkHttpUtils.post()
                .url(Http_data.http_data + "user/Login")
                .addParams("phone", phone)
                .addParams("password", pasMD5)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        animLoadingUtil.finishAnim();
                        Log.e("Login",e.getMessage());
                        ToolUtils.showToast(LoginSelectActivity.this, "登录失败！请检查网络！", Toast.LENGTH_LONG);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        animLoadingUtil.finishAnim();
                        Log.e("Login","response = "+response);
                        if (response.contains("phoneError")) {
                            ToolUtils.showToast(LoginSelectActivity.this, "电话有误", Toast.LENGTH_SHORT);
                        } else if(response.contains("pwdError")){
                            ToolUtils.showToast(LoginSelectActivity.this, "密码错误", Toast.LENGTH_SHORT);
                        }else {
                            try {

                                UserInfoLogin user = gson.fromJson(response, UserInfoLogin.class);
                                Log.e("Login","user = "+user.toString());
                                SharedPreferencesTools.SaveUserInfo(LoginSelectActivity.this,"userSave","userInfo",response);
                                SharedPreferencesTools.SaveUserId(LoginSelectActivity.this,"userSave","userId",user.getUserid());
                                SharedPreferencesTools.SaveLoginType(LoginSelectActivity.this,"userSave","userType","phone");
                                SharedPreferencesTools.SaveLoginData(LoginSelectActivity.this, "userSave", "userLoginData", phone+","+pas);
                                if (!StringUtils.isEmpty(user.getName())) {
                                    Log.e("Logins","succ");
                                    Intent intent = new Intent(LoginSelectActivity.this, HomeAcitivtyMy.class);
                                    startActivity(intent);
                                } else {
                                    Log.e("Logins","fail");
                                    startActivity(new Intent(LoginSelectActivity.this, BindingInfoData.class));
                                }
                                finish();
                            } catch (Exception e) {
                                Log.e("Logins","e:"+e.getMessage());
                                ToolUtils.showToast(LoginSelectActivity.this, "登录异常" + e.getMessage(), Toast.LENGTH_SHORT);
                                MyLogcat.jLog().e("登录异常:" + e.getMessage());
                            }
                        }
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onResume() {
        super.onResume();
        JPushInterface.onResume(this);

    }

    @Override
    protected void onPause() {
        super.onPause();
        JPushInterface.onPause(this);
    }
}
