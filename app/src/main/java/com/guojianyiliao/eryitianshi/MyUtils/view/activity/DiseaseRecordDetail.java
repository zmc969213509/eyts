package com.guojianyiliao.eryitianshi.MyUtils.view.activity;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import com.guojianyiliao.eryitianshi.MyUtils.base.BaseActivity;
import com.guojianyiliao.eryitianshi.MyUtils.bean.myCasesBean;
import com.guojianyiliao.eryitianshi.MyUtils.interfaceservice.GetService;
import com.guojianyiliao.eryitianshi.MyUtils.manager.RetrofitClient;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.MyLogcat;
import com.guojianyiliao.eryitianshi.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 我的病历的详细页面
 */
public class DiseaseRecordDetail extends BaseActivity {

    @BindView(R.id.tv_1)
    TextView tv1;
    @BindView(R.id.tv_2)
    TextView tv2;
    @BindView(R.id.tv_3)
    TextView tv3;
    @BindView(R.id.tv_4)
    TextView tv4;
    @BindView(R.id.tv_5)
    TextView tv5;
    @BindView(R.id.tv_6)
    TextView tv6;
    @BindView(R.id.tv_7)
    TextView tv7;

    @BindView(R.id.ivb_back_finsh)
    ImageButton ivbBackFinsh;
    @BindView(R.id.tv_foot_center)
    TextView tvFootCenter;

    @Override
    protected void onStart() {
        super.onStart();

        String caseid = getIntent().getStringExtra("caseid");
        MyLogcat.jLog().e("caseid:" + caseid);
        HttpData(caseid);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_fragment_disease);
        ButterKnife.bind(this);

        tvFootCenter.setText("病历记录");
        for (int i = 0; i < mydata.size(); i++) {
            tv1.setText("就诊医生：" + mydata.get(i).docname);
            tv2.setText("就诊科室：" + mydata.get(i).secname);
            tv3.setText("就诊医院：" + mydata.get(i).hospitalname);
            tv4.setText("就诊人：" + mydata.get(i).memo);
            tv5.setText("就诊时间：" + mydata.get(i).casetime + "");
            tv6.setText("就诊疾病：" + mydata.get(i).casename);
            tv7.setText("疾病描述：");
        }
    }

    @OnClick(R.id.ivb_back_finsh)
    public void Back() {
        finish();
    }

    List<myCasesBean> mydata = new ArrayList<>();

    private void HttpData(String caseid) {

        RetrofitClient.getinstance(this).create(GetService.class).getCaseInfo(caseid).enqueue(new Callback<myCasesBean>() {
            @Override
            public void onResponse(Call<myCasesBean> call, Response<myCasesBean> response) {
                if (response.isSuccessful()) {
                    MyLogcat.jLog().e("onResponse:" + response.body().toString());
                    mydata.add(response.body());
                    myCasesBean body = response.body();
                    MyLogcat.jLog().e("casename：" + body.casename);
                }
            }

            @Override
            public void onFailure(Call<myCasesBean> call, Throwable t) {
                MyLogcat.jLog().e("onFailure:");
            }
        });
    }
}
