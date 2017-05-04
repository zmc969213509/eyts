package com.guojianyiliao.eryitianshi.View.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.guojianyiliao.eryitianshi.Data.Http_data;
import com.guojianyiliao.eryitianshi.R;
import com.guojianyiliao.eryitianshi.Utils.MyBaseActivity;
import com.guojianyiliao.eryitianshi.Utils.SharedPsaveuser;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;
/**
 *
 */
public class PhonechangeActivity extends MyBaseActivity {
    private EditText et_verificationcode, et_phone;
    private TextView tv_pas;
    private LinearLayout ll_rutphone;
    SharedPsaveuser sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phonechange);
        try {

            sp=new SharedPsaveuser(this);
            findView();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void findView() {
        et_verificationcode = (EditText) findViewById(R.id.et_verificationcode);
        et_phone = (EditText) findViewById(R.id.et_phone);
        tv_pas = (TextView) findViewById(R.id.tv_pas);
        ll_rutphone = (LinearLayout) findViewById(R.id.ll_rutphone);
        et_verificationcode.addTextChangedListener(textListener);
        et_phone.addTextChangedListener(textListener);
        ll_rutphone.setOnClickListener(listener);
        tv_pas.setOnClickListener(listener);
    }



    private TextWatcher textListener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (et_phone.length() != 0 && et_verificationcode.length() != 0) {
                tv_pas.setTextColor(android.graphics.Color.parseColor("#6fc9e6"));
            } else {
                tv_pas.setTextColor(android.graphics.Color.parseColor("#e0e0e0"));
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
                case R.id.ll_rutphone:
                    finish();
                    break;
                case R.id.tv_pas:
                    OkHttpUtils
                            .post()
                            .url(Http_data.http_data + "/bingding")
                            .addParams("id", sp.getTag().getId() + "")
                            .addParams("phone", et_phone.getText().toString())
                            .addParams("code", et_verificationcode.getText().toString())
                            .build()
                            .execute(new StringCallback() {
                                @Override
                                public void onError(Call call, Exception e, int id) {
                                    Toast.makeText(PhonechangeActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onResponse(String response, int id) {
                                    finish();

                                }
                            });
                    break;
            }
        }
    };
}
