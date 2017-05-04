package com.guojianyiliao.eryitianshi.View.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.guojianyiliao.eryitianshi.Data.Http_data;
import com.guojianyiliao.eryitianshi.MyUtils.bean.UserInfoLogin;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.MyLogcat;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.SpUtils;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.StringUtils;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.ToolUtils;
import com.guojianyiliao.eryitianshi.MyUtils.view.activity.BindingInfoData;
import com.guojianyiliao.eryitianshi.MyUtils.view.activity.BindingPhoneActivity;
import com.guojianyiliao.eryitianshi.MyUtils.view.activity.HomeAcitivtyMy;
import com.guojianyiliao.eryitianshi.MyUtils.view.activity.RegistActivity;
import com.guojianyiliao.eryitianshi.MyUtils.view.activity.test.caidanshaixuan;
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

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1: //qq
                case 2://wechat
                case 3:///weibo
                default:
                    Intent intentqq = new Intent(LoginSelectActivity.this, BindingPhoneActivity.class);
                    startActivity(intentqq);
                    finish();
            }
            super.handleMessage(msg);
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  setContentView(R.layout.activity_login_select);

        setContentView(R.layout.a_activity_login);

        ButterKnife.bind(this);
    }


    @OnClick(R.id.tv_find_pas)
    public void HttpFindPas() {
        /** 测试类，layout*/
        // startActivity(new Intent(this, FindPasActivity.class));
        startActivity(new Intent(this, caidanshaixuan.class));
    }

    @OnClick(R.id.ivb_tencent)
    public void LoginTencent() {
        if (!ToolUtils.isFastDoubleClick()) {
            UMShareAPI umShareAPI = UMShareAPI.get(this);
            umShareAPI.getPlatformInfo(this, SHARE_MEDIA.QQ, new UMAuthListener() {
                @Override
                public void onStart(SHARE_MEDIA share_media) {
                    MyLogcat.jLog().e("onStart:");
                }

                @Override
                public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                    MyLogcat.jLog().e("onComplete:" + map.toString());
                    MyLogcat.jLog().e("name:" + map.get("name") + "//" + map.get("openid"));

                    String uid = map.get("openid");
                    String name = map.get("name");
                    String gender = map.get("gender");
                    String iconurl = map.get("iconurl");
                    SpUtils.getInstance(LoginSelectActivity.this).put("uid", uid);
                    SpUtils.getInstance(LoginSelectActivity.this).put("name", name);
                    SpUtils.getInstance(LoginSelectActivity.this).put("gender", gender);
                    SpUtils.getInstance(LoginSelectActivity.this).put("iconurl", iconurl);
                    SpUtils.getInstance(LoginSelectActivity.this).put("type", "qq");
                    Message message = new Message();
                    message.what = 1;
                    handler.sendMessage(message);

                    //{unionid=, is_yellow_vip=0, screen_name=summer, msg=, vip=0,
                    //    city=, accessToken=1B88D04E81555F8A877AC1FA1A617D00, gender=男,
                    //      province=, is_yellow_year_vip=0, openid=24DE0A4DC828969520D2B78848904521,
                    //       yellow_vip_level=0, profile_image_url=http://q.qlogo.cn/qqapp/1105577776/24DE0A4DC828969520D2B78848904521/100,
                    //  access_token=1B88D04E81555F8A877AC1FA1A617D00, iconurl=http://q.qlogo.cn/qqapp/1105577776/24DE0A4DC828969520D2B78848904521/100,
                    //   name=summer, uid=24DE0A4DC828969520D2B78848904521, expiration=1498032516493, expires_in=1498032516493, level=0, ret=0}
                }

                @Override
                public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
                    MyLogcat.jLog().e("onError:");
                }

                @Override
                public void onCancel(SHARE_MEDIA share_media, int i) {
                    MyLogcat.jLog().e("onCancel:");
                }
            });

        }
    }


    @OnClick(R.id.ivb_wechat)
    public void LoginWechat() {
        if (!ToolUtils.isFastDoubleClick()) {
            UMShareAPI.get(this).getPlatformInfo(this, SHARE_MEDIA.WEIXIN, new UMAuthListener() {
                @Override
                public void onStart(SHARE_MEDIA share_media) {

                }

                @Override
                public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                    MyLogcat.jLog().e("onComplete:" + map.toString());
                    MyLogcat.jLog().e("onComplete name :" + map.get("name"));
                    // unionid=ox14DvzKcSVld-Cpzpb4UELFfkx8, screen_name=summer, city=海淀,
                    //    accessToken=zQ7rFfFrf4pyesaTeX4gjHE_ugfvRFEXoIbiofkdRFWS5NTi1SNVJk9IqzM1k_sTJOpb6-BRwyxESmDHAEE0qPDqCge73UfeBNSTt5uWOyE,
                    //     refreshToken=JTSDm4Ww2sxCIXkj2JNHN4fTGkWBmTDZ8q57uHejOID8Z55YJDFY6DsD_ORuelmIjqGo3PnQZisWGMYm5WRKB4CD13HdJ9HV0kgWKHs0TFk,
                    //     gender=男, province=北京, openid=omwn5v_MCIylOhveXvvNsgYVFm_M,
                    //        profile_image_url=http://wx.qlogo.cn/mmopen/ajNVdqHZLLDxSiap7uic4rIU0cwKv3vN69DNevVuB8O4cGGJI4WnUtl4t32JBe9Tq7ibAytO55r2swND2Tpo6q6iaw/0,
                    // country=中国, access_token=zQ7rFfFrf4pyesaTeX4gjHE_ugfvRFEXoIbiofkdRFWS5NTi1SNVJk9IqzM1k_sTJOpb6-BRwyxESmDHAEE0qPDqCge73UfeBNSTt5uWOyE,
                    //           iconurl=http://wx.qlogo.cn/mmopen/ajNVdqHZLLDxSiap7uic4rIU0cwKv3vN69DNevVuB8O4cGGJI4WnUtl4t32JBe9Tq7ibAytO55r2swND2Tpo6q6iaw/0, name=summer,
                    //   uid=ox14DvzKcSVld-Cpzpb4UELFfkx8, expiration=1490265297665, language=zh_CN, expires_in=1490265297665}


                    String uid = map.get("uid");
                    String name = map.get("name");
                    String gender = map.get("gender");
                    String iconurl = map.get("iconurl");
                    SpUtils.getInstance(LoginSelectActivity.this).put("uid", uid);
                    SpUtils.getInstance(LoginSelectActivity.this).put("name", name);
                    SpUtils.getInstance(LoginSelectActivity.this).put("gender", gender);
                    SpUtils.getInstance(LoginSelectActivity.this).put("iconurl", iconurl);
                    SpUtils.getInstance(LoginSelectActivity.this).put("type", "wechat");
                    Message message = new Message();
                    message.what = 2;
                    handler.sendMessage(message);
                }

                @Override
                public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {

                }

                @Override
                public void onCancel(SHARE_MEDIA share_media, int i) {

                }
            });
        }
    }

    @OnClick(R.id.ivb_sina)
    public void LoginSina() {
        if (!ToolUtils.isFastDoubleClick()) {
            UMShareAPI.get(this).getPlatformInfo(this, SHARE_MEDIA.SINA, new UMAuthListener() {
                @Override
                public void onStart(SHARE_MEDIA share_media) {

                }

                @Override
                public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                    MyLogcat.jLog().e("onComplete:" + map.toString());
                    MyLogcat.jLog().e("onComplete name :" + map.get("name"));
                    Message message = new Message();
                    message.what = 3;
                    handler.sendMessage(message);

                }

                @Override
                public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {

                }

                @Override
                public void onCancel(SHARE_MEDIA share_media, int i) {

                }
            });
        }
    }

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

    @OnClick(R.id.bt_login)
    public void passLogin() {

        phone = etLoginPhone.getText().toString();
        if (StringUtils.isEmpty(phone) && StringUtils.isMobile(phone)) {
            ToolUtils.showToast(LoginSelectActivity.this, "请填写手机号！", Toast.LENGTH_SHORT);
            return;
        }

        pas = etLoginPas.getText().toString();
        if (StringUtils.isEmpty(pas)) {
            ToolUtils.showToast(LoginSelectActivity.this, "请填写密码！", Toast.LENGTH_SHORT);
            return;
        }
        pasMD5 = StringUtils.MD5encrypt(pas).toUpperCase();
        OkHttpUtils
                .post()
                .url(Http_data.http_data + "user/Login")
                .addParams("phone", phone)
                .addParams("password", pasMD5)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToolUtils.showToast(LoginSelectActivity.this, "登录失败！请检查网络！", Toast.LENGTH_LONG);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        //{"name":"??","password":"51596103F385101CE16BCC58B3C3B85B","phone":"18310904818","role":"1","userid":"4e4d86c1fab74d4982ccceec9c0ca38b"}
                        MyLogcat.jLog().e("login: " + response.toString());
                        if (response.equals("error")) {
                            ToolUtils.showToast(LoginSelectActivity.this, "账号或密码错误", Toast.LENGTH_SHORT);
                        } else {
                            UserInfoLogin user = gson.fromJson(response, UserInfoLogin.class);
                            try {
                                SpUtils.getInstance(LoginSelectActivity.this).put("phone", phone);
                                SpUtils.getInstance(LoginSelectActivity.this).put("password", pasMD5);
                                SpUtils.getInstance(LoginSelectActivity.this).put("userid", user.getUserid());
                                SpUtils.getInstance(LoginSelectActivity.this).put("name", user.getName());
                                SpUtils.getInstance(LoginSelectActivity.this).put("gender", user.getGender());
                                SpUtils.getInstance(LoginSelectActivity.this).put("iconurl", user.getIcon());

                                if (!StringUtils.isEmpty(user.getName())) {
                                    Intent intent = new Intent(LoginSelectActivity.this, HomeAcitivtyMy.class);
                                    startActivity(intent);
                                } else {
                                    //Intent intent = new Intent(LoginSelectActivity.this, SetdataActivity.class);
                                    startActivity(new Intent(LoginSelectActivity.this, BindingInfoData.class));
                                }
                                finish();
                            } catch (Exception e) {
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
