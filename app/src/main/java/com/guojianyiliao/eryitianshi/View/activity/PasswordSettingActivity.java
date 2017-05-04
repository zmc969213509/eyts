package com.guojianyiliao.eryitianshi.View.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.guojianyiliao.eryitianshi.Data.Http_data;
import com.guojianyiliao.eryitianshi.Data.User_Http;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.SpUtils;
import com.guojianyiliao.eryitianshi.R;
import com.guojianyiliao.eryitianshi.Utils.MyBaseActivity;
import com.guojianyiliao.eryitianshi.Utils.SharedPsaveuser;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import cn.jpush.im.android.api.JMessageClient;
import okhttp3.Call;

/**
 *
 */
public class PasswordSettingActivity extends MyBaseActivity implements View.OnClickListener {
    private TextView tv_pas, tv_passet;
    private EditText et_newpassword, et_forpassword;
    private LinearLayout linearlayout;
    String forpas;
    String newpas;
    SharedPsaveuser sp;
    TextView tv_pass_page_top, tv_new_pass, tv_for_pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_setting);
        try {

            sp = new SharedPsaveuser(this);
            findView();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void findView() {
        tv_pas = (TextView) findViewById(R.id.tv_pas);
        et_newpassword = (EditText) findViewById(R.id.et_newpassword);
        et_forpassword = (EditText) findViewById(R.id.et_forpassword);
        linearlayout = (LinearLayout) findViewById(R.id.linearlayout);
        tv_passet = (TextView) findViewById(R.id.tv_passet);
        tv_pass_page_top = (TextView) findViewById(R.id.tv_pass_page_top);

        tv_new_pass = (TextView) findViewById(R.id.tv_new_pass);

        tv_for_pass = (TextView) findViewById(R.id.tv_for_pass);

        forpas = et_forpassword.getText().toString();
        newpas = et_newpassword.getText().toString();
        linearlayout.setOnClickListener(this);
        tv_passet.setOnClickListener(this);
        et_newpassword.addTextChangedListener(textchangelisterer);
        et_forpassword.addTextChangedListener(textchangelisterer);

        if (sp.getTag().getPassword() != null) {
            tv_pass_page_top.setText("密码修改");
            tv_new_pass.setText("旧密码");
            tv_for_pass.setText("新密码");

            et_forpassword.setHint("请输入原密码");

            et_newpassword.setHint("请输入新密码");


        } else {
            tv_pass_page_top.setText("密码设置");
            tv_new_pass.setText("新密码");
            tv_for_pass.setText("新密码");

            et_forpassword.setHint("请输入密码");

            et_newpassword.setHint("请再次输入密码");

        }
    }


    private TextWatcher textchangelisterer = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (et_newpassword.getText().toString().length() != 0 && et_forpassword.getText().toString().length() != 0) {

                tv_pas.setTextColor(android.graphics.Color.parseColor("#6fc9e6"));
                if (sp.getTag().getPassword() != null) {
                    tv_pas.setOnClickListener(changelistener);

                } else {
                    tv_pas.setOnClickListener(setlistener);
                }

            } else {
                tv_pas.setTextColor(android.graphics.Color.parseColor("#e0e0e0"));

                tv_pas.setOnClickListener(null);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };


    private View.OnClickListener setlistener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!et_newpassword.getText().toString().equals(et_forpassword.getText().toString())) {
                Toast.makeText(PasswordSettingActivity.this, "2次输入的密码不一致", Toast.LENGTH_SHORT).show();
            } else {
                OkHttpUtils
                        .post()
                        .url(Http_data.http_data + "/ChangeP")
                        .addParams("id", sp.getTag().getId() + "")
                        .addParams("pass", et_forpassword.getText().toString())
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {
                                Toast.makeText(PasswordSettingActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onResponse(String response, int id) {
                                if (response.equals("0")) {
                                    Toast.makeText(PasswordSettingActivity.this, "设置成功，以后你可以使用账号密码登陆", Toast.LENGTH_SHORT).show();
                                    JMessageClient.logout();
                                    sp.clearinit();
                                    SpUtils.getInstance(PasswordSettingActivity.this).clear();
                                    HomeActivity.text_homeactivity.finish();
                                    Intent intent = new Intent(PasswordSettingActivity.this, LoginSelectActivity.class);
                                    Toast.makeText(PasswordSettingActivity.this, "请重新登录", Toast.LENGTH_SHORT).show();
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(PasswordSettingActivity.this, "密码设置失败", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        }
    };


    private View.OnClickListener changelistener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            OkHttpUtils
                    .post()
                    .url(Http_data.http_data + "/ChangeP")
                    .addParams("id", sp.getTag().getId() + "")
                    .addParams("oldPassword", et_forpassword.getText().toString())
                    .addParams("newPassword", et_newpassword.getText().toString())
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            Toast.makeText(PasswordSettingActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onResponse(String response, int id) {
                            if (response.equals("1")) {
                                Toast.makeText(PasswordSettingActivity.this, "修改失败", Toast.LENGTH_SHORT).show();
                            } else {
                                User_Http.user.setPhone(et_newpassword.getText().toString());

                                sp.clearinit();
                                Intent intent5 = new Intent(PasswordSettingActivity.this, LoginActivity.class);
                                startActivity(intent5);
                                Toast.makeText(PasswordSettingActivity.this, "密码修改成功,请重新登录", Toast.LENGTH_SHORT).show();
                                HomeActivity.text_homeactivity.finish();
                                finish();
                            }
                        }
                    });
        }
    };


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_passet:
                finish();
                break;

        }
    }
}
