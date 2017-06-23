package com.guojianyiliao.eryitianshi.View.activity;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.guojianyiliao.eryitianshi.Data.Http_data;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.AnimLoadingUtil;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.SharedPreferencesTools;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.ToolUtils;
import com.guojianyiliao.eryitianshi.R;
import com.guojianyiliao.eryitianshi.Utils.MyBaseActivity;
import com.guojianyiliao.eryitianshi.Utils.SharedPsaveuser;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 *  意见反馈
 */
public class FeedbackActivity extends MyBaseActivity implements View.OnClickListener {
    private TextView tv_feedback, tv_cancel;
    private EditText et_feedback;
    private LinearLayout ll_et_feedback;

    String uid;
    View animView;

    AnimLoadingUtil animLoadingUtil;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        animView = findViewById(R.id.anim_view_layout);
        animLoadingUtil = new AnimLoadingUtil(animView);
        uid = SharedPreferencesTools.GetUsearId(this, "userSave", "userId");
        findView();

    }

    private void findView() {
        tv_feedback = (TextView) findViewById(R.id.tv_feedback);
        et_feedback = (EditText) findViewById(R.id.et_feedback);
        ll_et_feedback = (LinearLayout) findViewById(R.id.ll_et_feedback);
        tv_cancel = (TextView) findViewById(R.id.tv_cancel);
        et_feedback.addTextChangedListener(textchanglistener);

        tv_cancel.setOnClickListener(this);
        ll_et_feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_feedback.requestFocus();
                InputMethodManager imm = (InputMethodManager) FeedbackActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            }
        });
    }

    private TextWatcher textchanglistener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {


            if (et_feedback.getText().toString().trim().length() == 0) {
                tv_feedback.setTextColor(android.graphics.Color.parseColor("#e0e0e0"));
                tv_feedback.setOnClickListener(null);
            } else {
                tv_feedback.setOnClickListener(listener);
                tv_feedback.setTextColor(android.graphics.Color.parseColor("#6fc9e6"));
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (et_feedback.getText().toString().length() < 2) {
                Toast.makeText(FeedbackActivity.this, "必须大于2个字", Toast.LENGTH_SHORT).show();
            } else {
                animLoadingUtil.startAnim("意见上传中...");
                OkHttpUtils
                        .post()
                        .url(Http_data.http_data + "feedback/addFeedback")
                        .addParams("content", et_feedback.getText().toString())
                        .addParams("ftime", ToolUtils.CurrentTime())
                        .addParams("userid", uid)
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {
                                animLoadingUtil.finishAnim();
                                Toast.makeText(FeedbackActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onResponse(String response, int id) {

                                if (response.equals("true")) {
                                    Toast.makeText(FeedbackActivity.this, "提交成功", Toast.LENGTH_SHORT).show();
                                    animLoadingUtil.finishAnim();
                                    finish();
                                }
                            }
                        });
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_cancel:
                finish();
                break;
        }
    }
}
