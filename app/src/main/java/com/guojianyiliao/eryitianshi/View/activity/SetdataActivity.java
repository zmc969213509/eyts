package com.guojianyiliao.eryitianshi.View.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.guojianyiliao.eryitianshi.Data.Http_data;
import com.guojianyiliao.eryitianshi.Data.entity.User;
import com.guojianyiliao.eryitianshi.Data.User_Http;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.SpUtils;
import com.guojianyiliao.eryitianshi.R;
import com.guojianyiliao.eryitianshi.Utils.IListener;
import com.guojianyiliao.eryitianshi.Utils.ListenerManager;
import com.guojianyiliao.eryitianshi.Utils.MyBaseActivity;
import com.guojianyiliao.eryitianshi.Utils.SharedPsaveuser;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/*
 ** 第一次登录，进入的界面，在进入home
 */
public class SetdataActivity extends MyBaseActivity implements IListener {
    private EditText et_nickname;
    private RadioButton rbtn_man, rb_madam;
    private Button btn;
    private int editStart;
    private int editEnd;
    private int maxLen = 10; // the max byte
    String sex = null;
    User user = new User(User_Http.user);
    SharedPsaveuser sp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setdata);


        try {

            ListenerManager.getInstance().registerListtener(this);
            sp = new SharedPsaveuser(this);
            finView();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void finView() {
        et_nickname = (EditText) findViewById(R.id.et_nickname);
        rbtn_man = (RadioButton) findViewById(R.id.rbtn_man);
        rb_madam = (RadioButton) findViewById(R.id.rb_madam);
        btn = (Button) findViewById(R.id.btn);
        rbtn_man.performClick();
        btn.setOnClickListener(listener);
        et_nickname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                editStart = et_nickname.getSelectionStart();
                editEnd = et_nickname.getSelectionEnd();
                if (!TextUtils.isEmpty(et_nickname.getText())) {
                    int varlength = 0;
                    int size = 0;
                    String etstring = et_nickname.getText().toString().trim();
                    char[] ch = etstring.toCharArray();
                    for (int i = 0; i < ch.length; i++) {
                        size++;
                        if (ch[i] >= 0x4e00 && ch[i] <= 0x9fbb) {
                            varlength = varlength + 2;
                        } else
                            varlength++;
                        if (varlength > 12) {
                            break;
                        }
                    }
                    if (varlength > 12) {
                        s.delete(size - 1, etstring.length());
                    }
                }
            }
        });
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (rbtn_man.isChecked()) {
                sex = "男";
            } else {
                sex = "女";
            }

            if (et_nickname.getText().toString().trim().equals("") || et_nickname.getText().toString() == null) {
                Toast.makeText(SetdataActivity.this, "昵称不能为空", Toast.LENGTH_SHORT).show();
            } else {

                OkHttpUtils
                        .post()
                        .url(Http_data.http_data + "/AddNameAndGender")
//                        .addParams("id", SpUtils.getInstance(SetdataActivity.this).get("Userid",null)+"")
                        .addParams("name", et_nickname.getText().toString())
                        .addParams("gender", sex)
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(SetdataActivity.this, "网络连接失败", Toast.LENGTH_LONG).show();
                                    }
                                });
                            }

                            @Override
                            public void onResponse(String response, int id) {

                                if (response.equals("0")) {
                                    Toast.makeText(SetdataActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
//                                    SpUtils.getInstance(SetdataActivity.this).put("Name",et_nickname.getText().toString());
//                                    SpUtils.getInstance(SetdataActivity.this).put("gender",sex);
                                    Http_data.giveCashState = 2;
                                    Intent intent = new Intent(SetdataActivity.this, HomeActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(SetdataActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }

        }
    };

    @Override
    public void notifyAllActivity(String str) {

    }
}
