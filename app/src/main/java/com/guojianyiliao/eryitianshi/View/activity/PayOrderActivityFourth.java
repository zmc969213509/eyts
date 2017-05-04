package com.guojianyiliao.eryitianshi.View.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.guojianyiliao.eryitianshi.Data.Http_data;
import com.guojianyiliao.eryitianshi.Data.entity.Inquiry;
import com.guojianyiliao.eryitianshi.R;
import com.guojianyiliao.eryitianshi.MyUtils.MyAppliction;
import com.guojianyiliao.eryitianshi.Utils.MyBaseActivity;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.MyLogcat;
import com.guojianyiliao.eryitianshi.Utils.SharedPsaveuser;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.ToolUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;


public class PayOrderActivityFourth extends MyBaseActivity implements View.OnClickListener {

    @BindView(R.id.tv_pay_department)//科室
            TextView tvPayDepartment;
    @BindView(R.id.tv_pay_doctor)//主任医生
            TextView tvPayDoctor;
    @BindView(R.id.tv_pay_time)//时间？
            TextView tvPayTime;
    @BindView(R.id.tv_pay_name)//姓名
            TextView tvPayName;
    @BindView(R.id.pay_zfb)
    ImageButton payZfb;
    @BindView(R.id.pay_wx)
    ImageButton payWx;
    @BindView(R.id.bt_payment)
    Button btPayment;
    @BindView(R.id.tv_paypoucon)//优惠券
            TextView tvPaypoucon;
    @BindView(R.id.tv_close)
    TextView tvClose;
    private Inquiry bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payorder);
        ButterKnife.bind(this);
        init();
    }


    private void init() {
        tvPayName.setText(getIntent().getStringExtra("name"));
        tvPayDoctor.setText(getIntent().getStringExtra("title"));
        tvPayDepartment.setText(getIntent().getStringExtra("section"));
        try {
            tvPayTime.setText(ToolUtils.CurrentTime());
        } catch (Exception e) {
            //
        }
        payZfb.setOnClickListener(PayOrderActivityFourth.this);
        payWx.setOnClickListener(PayOrderActivityFourth.this);
        btPayment.setOnClickListener(PayOrderActivityFourth.this);
        tvPaypoucon.setOnClickListener(PayOrderActivityFourth.this);
        tvClose.setOnClickListener(PayOrderActivityFourth.this);
        btPayment.setOnClickListener(PayOrderActivityFourth.this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pay_zfb:
                payZfb.setBackgroundResource(R.drawable.remind_circle_isonclilc);
                payWx.setBackgroundResource(R.drawable.remind_circle_unmep);

                Snackbar.make(payZfb, "支付功能正在建设中", Snackbar.LENGTH_SHORT)
                        .show();
                break;
            case R.id.pay_wx:
                payZfb.setBackgroundResource(R.drawable.remind_circle_unmep);
                payWx.setBackgroundResource(R.drawable.remind_circle_isonclilc);
                Snackbar.make(payZfb, "支付功能正在建设中", Snackbar.LENGTH_SHORT)
                        .show();
                break;
            case R.id.bt_payment:
                int i1 = 0;
                int i2 = 0;
                int i3 = 0;
                try {
                    i1 = Integer.parseInt(money1.substring(0,2));
                    i2 = Integer.parseInt(money2.substring(0,2));
                    i3 = Integer.parseInt(money3.substring(0,2));
                } catch (Exception e) {
                    MyLogcat.jLog().e("" + e);
                }
                MyLogcat.jLog().e("payid:" + i1 + i2 + i3);
                if (i1 + i2 + i3 >= 25) {
                    HttpPay();
                } else {
                    ToolUtils.showToast(this, "代金券金额不够支付！", Toast.LENGTH_SHORT);
                }

                break;
            case R.id.tv_close:
                finish();
                break;
            case R.id.tv_paypoucon:
                try {
                    money.clear();
                    id.clear();
                } catch (Exception e) {
                }
                tvPaypoucon.setText("请选择代金券");
                money1 = "";
                money2 = "";
                money3 = "";
                Intent intent = new Intent(this, MyCashCouponsCheckBoxActivity.class);
                startActivityForResult(intent, MyAppliction.PAY_OTIEM_COUPON);

                break;
        }

    }

    private void paytrue() {
        Intent intent = new Intent(this, ChatpageActivity.class);
        intent.putExtra("name", getIntent().getStringExtra("name"));
        intent.putExtra("icon", getIntent().getStringExtra("icon"));
        intent.putExtra("doctorID", getIntent().getStringExtra("doctorID"));
        intent.putExtra("username", getIntent().getStringExtra("username"));
        intent.putExtra("page", "2");
        this.startActivity(intent);
        finish();
    }

    private String Id1 = "";
    private String Id2 = "";
    private String Id3 = "";
    private String money1 = "";
    private String money2 = "";
    private String money3 = "";
    private ArrayList<String> money;
    private ArrayList<String> id;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 200) {
            try {
                money = data.getStringArrayListExtra("money");
                money1 = money.get(0);
                MyLogcat.jLog().e("jnluo" + money1);
                money2 = money.get(1);
                MyLogcat.jLog().e("jnluo" + money2);
                money3 = money.get(2);
                MyLogcat.jLog().e("jnluo" + money3);
            } catch (Exception e) {
                MyLogcat.jLog().e("" + e);
            }
            try {
                id = data.getStringArrayListExtra("id");
                Id1 = id.get(0);
                MyLogcat.jLog().e("jnluo" + Id1);
                Id2 = id.get(1);
                MyLogcat.jLog().e("jnluo" + Id2);
                Id3 = id.get(2);
                MyLogcat.jLog().e("jnluo" + Id3);
            } catch (Exception e) {
                MyLogcat.jLog().e("" + e);
            }
            try {
                if (!money1.equals("") && !money2.equals("") && !money3.equals("")) {
                    tvPaypoucon.setText("￥" + Integer.parseInt(money1.substring(0, 2)) + "、" + Integer.parseInt(money2.substring(0, 2)) + "、" + Integer.parseInt(money3.substring(0, 2)) + "在线问诊券");
                } else if (!money1.equals("") && !money2.equals("")) {
                    tvPaypoucon.setText("￥" + Integer.parseInt(money1.substring(0, 2)) + "、" + Integer.parseInt(money2.substring(0, 2)) + "在线问诊券");
                } else if (!money1.equals("")) {
                    tvPaypoucon.setText("￥" + Integer.parseInt(money1.substring(0, 2)) + "在线问诊券");
                }
            } catch (Exception e) {
                MyLogcat.jLog().e("" + e);
            }

        }

    }

    private void HttpPay() {
        SharedPsaveuser sp = new SharedPsaveuser(this);
        OkHttpUtils.post()
                .url(Http_data.http_data + "/userVoucher")
                .addParams("userId", sp.getTag().getId() + "")
                .addParams("vouIds", Id1 + "," + Id2 + "," + Id3)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        paytrue();
                    }
                });
    }


    /*private void updatedetection() {
        final Gson gson = new Gson();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpUtils
                        .post()
                        .url(Http_data.http_data + "/VersionUpdate")
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {
                                Toast.makeText(PayOrderActivity.this, "检查更新失败", Toast.LENGTH_SHORT).show();

                            }

                            @Override
                            public void onResponse(String response, int id) {

                                update = gson.fromJson(response, Update.class);

                                if (update.getVersion().trim().equals(version_numberSP.getversionNumber().trim())) {

                                } else {
                                    handler1.sendEmptyMessage(0);
                                    ll_hint_version_number.setOnClickListener(verlistener);

                                }

                            }
                        });
            }
        });

        thread.start();

    }*/

}
