package com.guojianyiliao.eryitianshi.View.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.guojianyiliao.eryitianshi.Data.Http_data;
import com.guojianyiliao.eryitianshi.MyUtils.bean.UserInfoLogin;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.SharedPreferencesTools;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.SpUtils;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.StringUtils;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.ToolUtils;
import com.guojianyiliao.eryitianshi.R;
import com.guojianyiliao.eryitianshi.Utils.MyBaseActivity;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 *
 */
public class SexpageActivity extends MyBaseActivity {
    private LinearLayout ll_rutgender;
    private RadioButton rb_man, rb_nman;
    private TextView tv_send;
    String gender;

    Gson gson;
    UserInfoLogin user ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genderpage);
        gson = new Gson();
        String s = SharedPreferencesTools.GetUsearInfo(this, "userSave", "userInfo");
        user = gson.fromJson(s, UserInfoLogin.class);
        try {

            findView();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void findView() {
        ll_rutgender = (LinearLayout) findViewById(R.id.ll_rutgender);
        rb_man = (RadioButton) findViewById(R.id.rb_man);
        rb_nman = (RadioButton) findViewById(R.id.rb_nman);
        tv_send = (TextView) findViewById(R.id.tv_send);
        ll_rutgender.setOnClickListener(listener);
        rb_man.setOnClickListener(listener);
        rb_nman.setOnClickListener(listener);


        if (gender.equals("男")) {
            rb_man.setChecked(true);
        } else {
            rb_nman.setChecked(true);
        }
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.ll_rutgender:
                    finish();
                    break;
                case R.id.rb_man:
                    gender = "男";
                    break;
                case R.id.rb_nman:
                    gender = "女";
                    break;
                case R.id.tv_send:
                    httpinit();
                    break;
            }
        }
    };

    private void httpinit() {
        String userid = SharedPreferencesTools.GetUsearId(this,"userSave","userId");;

        if (StringUtils.isEmpty(gender)) {
            ToolUtils.showToast(SexpageActivity.this, "请选择性别！", Toast.LENGTH_SHORT);
            return;
        }

        OkHttpUtils
                .post()
                .url(Http_data.http_data + "user/updateUser")
                .addParams("userid", userid)
                .addParams("gender", gender)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(SexpageActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        if (response.equals("true")) {
                            user.setGender(gender);
                            SharedPreferencesTools.SaveUserInfo(SexpageActivity.this, "userSave", "userInfo",gson.toJson(user));
                            finish();
                        } else {
                            Toast.makeText(SexpageActivity.this, "修改失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}
