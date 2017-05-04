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
public class EvaluatepageActivity extends MyBaseActivity {
    private TextView tv_evaluate;
    private EditText et_evaluate;
    private LinearLayout ll_rutname;
    String doctor_id=null;

    SharedPsaveuser sp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluatepage);
        try {

            sp=new SharedPsaveuser(this);
            findView();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void findView() {
        tv_evaluate= (TextView) findViewById(R.id.tv_evaluate);
        et_evaluate= (EditText) findViewById(R.id.et_evaluate);
        ll_rutname= (LinearLayout) findViewById(R.id.ll_rutname);
        et_evaluate.addTextChangedListener(textlistener);
        ll_rutname.setOnClickListener(listener);
        doctor_id=getIntent().getStringExtra("doctorid");
    }
    private TextWatcher textlistener=new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (et_evaluate.getText().toString().length() != 0) {
                tv_evaluate.setOnClickListener(listener);
                tv_evaluate.setTextColor(android.graphics.Color.parseColor("#6fc9e6"));
            } else {
                tv_evaluate.setTextColor(android.graphics.Color.parseColor("#e0e0e0"));
                tv_evaluate.setOnClickListener(null);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
    private View.OnClickListener listener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.tv_evaluate:
                    OkHttpUtils
                            .post()
                            .url(Http_data.http_data + "/addappraise")
                            .addParams("user_id", sp.getTag().getId()+"")
                            .addParams("doctor_id",doctor_id)
                            .addParams("content",et_evaluate.getText().toString())
                            .build()
                            .execute(new StringCallback() {
                                @Override
                                public void onError(Call call, Exception e, int id) {
                                    Toast.makeText(EvaluatepageActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onResponse(String response, int id) {
                                    if(response.equals("0")){
                                        Toast.makeText(EvaluatepageActivity.this, "评论成功", Toast.LENGTH_SHORT).show();
                                        finish();
                                    }else {
                                        Toast.makeText(EvaluatepageActivity.this, "评论失败", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                    break;
                case R.id.ll_rutname:
                    finish();
                    break;
            }

        }
    };
}
