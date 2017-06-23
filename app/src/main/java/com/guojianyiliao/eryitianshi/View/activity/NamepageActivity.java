package com.guojianyiliao.eryitianshi.View.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.guojianyiliao.eryitianshi.Data.Http_data;
import com.guojianyiliao.eryitianshi.Data.User_Http;
import com.guojianyiliao.eryitianshi.MyUtils.bean.UserInfoLogin;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.SharedPreferencesTools;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.SpUtils;
import com.guojianyiliao.eryitianshi.R;
import com.guojianyiliao.eryitianshi.Utils.MyBaseActivity;
import com.guojianyiliao.eryitianshi.Utils.SharedPsaveuser;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 *
 */
public class NamepageActivity extends MyBaseActivity {
    private EditText et_name_save;
    private TextView tv_pas;
    private LinearLayout ll_rutname;

    Gson gson;
    UserInfoLogin user ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_namepage);
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
        et_name_save = (EditText) findViewById(R.id.et_name_save);
        tv_pas = (TextView) findViewById(R.id.tv_pas);
        ll_rutname = (LinearLayout) findViewById(R.id.ll_rutname);

        et_name_save.addTextChangedListener(textListener);
        ll_rutname.setOnClickListener(listener);
    }

    private TextWatcher textListener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            if (et_name_save.length() == 0) {
                tv_pas.setTextColor(android.graphics.Color.parseColor("#e0e0e0"));
                tv_pas.setOnClickListener(null);
            } else {
                tv_pas.setTextColor(android.graphics.Color.parseColor("#6fc9e6"));
                tv_pas.setOnClickListener(listener);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.ll_rutname:
                    finish();
                    break;
                case R.id.tv_pas:

                    if (et_name_save.getText().toString().trim().equals("") || et_name_save.getText().toString() == null) {
                        Toast.makeText(NamepageActivity.this, "昵称不能为空", Toast.LENGTH_SHORT).show();
                    } else {
                        String userid = SharedPreferencesTools.GetUsearId(NamepageActivity.this,"userSave","userId");
                        OkHttpUtils
                                .post()
                                .url(Http_data.http_data + "user/updateUser")
                                .addParams("userid", userid)
                                .addParams("name", et_name_save.getText().toString())
                                .build()
                                .execute(new StringCallback() {
                                    @Override
                                    public void onError(Call call, Exception e, int id) {
                                        Toast.makeText(NamepageActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onResponse(String response, int id) {
                                        if (response.equals("true")) {
                                            user.setName(et_name_save.getText().toString());
                                            SharedPreferencesTools.SaveUserInfo(NamepageActivity.this, "userSave", "userInfo",gson.toJson(user));
                                            setResult(RESULT_OK);
                                            finish();
                                        } else {
                                            Toast.makeText(NamepageActivity.this, "修改失败", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }

                    break;
            }
        }
    };
}
