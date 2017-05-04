package com.guojianyiliao.eryitianshi.View.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.animation.Transformation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.guojianyiliao.eryitianshi.Data.Http_data;
import com.guojianyiliao.eryitianshi.Data.entity.Doctor;
import com.guojianyiliao.eryitianshi.Data.entity.Doctorcomment;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.ToolUtils;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.UIUtils;
import com.guojianyiliao.eryitianshi.R;
import com.guojianyiliao.eryitianshi.Utils.MyBaseActivity1;
import com.guojianyiliao.eryitianshi.page.adapter.DoctorparticularsAdapter;
import com.guojianyiliao.eryitianshi.page.view.ListViewForScrollView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;

/**
 *
 */
public class DoctorparticularsActivity extends MyBaseActivity1 {

    ImageView expandView;

    int maxDescripLine = 12;

    RelativeLayout rl_look_full_introduce, rl_loading, rl_nonetwork;

    TextView tv_doctor_look_all, tv_doctor_top_name, tv_doctor_name, tv_doctor_title, tv_hospital_address, tv_doctor_aptitude, tv_doctor_commenCount;

    CircleImageView tv_doctor_icon;

    private DoctorparticularsAdapter adapter;

    List<Doctorcomment> list = new ArrayList<>();

    ListViewForScrollView lv_doctor_particulars_assess;

    ScrollView scrovView;

    Doctor doctor;

    Gson gson = new Gson();

    String doctor_id;

    LinearLayout ll_doctor_find;

    Button btn_pay;

    private Dialog setHeadDialog;

    private View dialogView;


    RadioButton rb_wenx;

    RadioButton rb_zhifb;

    LinearLayout ll_cancel;

    Button btn_confirm_payment;

    ProgressBar progressBar;

    TextView tv_cash_coupons_stater;
    TextView tv_money;

    RelativeLayout rl_use_cash_coupons;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }

        setContentView(R.layout.activity_doctorparticulars);

        try {

            doctor_id = getIntent().getStringExtra("doctot_id");

            findView();

            doctorinit();

            commenInit();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void commenInit() {
        OkHttpUtils
                .post()
                .url(Http_data.http_data + "/FindDoctorCommentByDoctorId")
                .addParams("doctorId", doctor_id)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Type listtype = new TypeToken<LinkedList<Doctorcomment>>() {
                        }.getType();
                        LinkedList<Doctorcomment> leclist = gson.fromJson(response, listtype);
                        for (Iterator it = leclist.iterator(); it.hasNext(); ) {
                            Doctorcomment doctorcomment = (Doctorcomment) it.next();
                            list.add(doctorcomment);
                        }
                        adapter = new DoctorparticularsAdapter(DoctorparticularsActivity.this, list);

                        lv_doctor_particulars_assess.setAdapter(adapter);

                        adapter.notifyDataSetChanged();

                    }

                });
    }

    private void doctorinit() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpUtils
                        .post()
                        .url(Http_data.http_data + "/FindDoctorById")
                        .addParams("doctorId", doctor_id)
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {
                                handler.sendEmptyMessage(0);
                            }

                            @Override
                            public void onResponse(String response, int id) {
                                doctor = gson.fromJson(response, Doctor.class);

                                handler.sendEmptyMessage(1);
                            }
                        });
            }
        }).start();
    }


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    rl_loading.setVisibility(View.GONE);
                    rl_nonetwork.setVisibility(View.VISIBLE);
                    Toast.makeText(DoctorparticularsActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    tv_doctor_top_name.setText(doctor.getName());

                    tv_doctor_name.setText(doctor.getName());

                    tv_doctor_title.setText(doctor.getTitle() + "/" + doctor.getSection());

                    tv_hospital_address.setText(doctor.getHospital());

                    String a = doctor.getSeniority().replace(",", "\n");

                    String particulars = doctor.getName().trim() + "资历  " + "\n" + a + "\n\n" + doctor.getName().trim() + "简介  " + "\n" + doctor.getBio() + "\n\n" + "擅长  " + "\n" + doctor.getAdept();

                    int fstart = particulars.indexOf(doctor.getName().trim() + "资历  ");
                    int fstartfend = fstart + (doctor.getName().trim() + "资历  ").length();

                    int intro = particulars.indexOf(doctor.getName().trim() + "简介  ");
                    int introfend = intro + (doctor.getName().trim() + "简介  ").length();

                    int good = particulars.indexOf("擅长  ");
                    int goodfend = good + "擅长  ".length();

                    SpannableStringBuilder style = new SpannableStringBuilder(particulars);

                    style.setSpan(new RelativeSizeSpan((float) 1.2), fstart, fstartfend, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                    style.setSpan(new ForegroundColorSpan(android.graphics.Color.parseColor("#323232")), fstart, fstartfend, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                    style.setSpan(new RelativeSizeSpan((float) 1.2), intro, introfend, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                    style.setSpan(new ForegroundColorSpan(android.graphics.Color.parseColor("#323232")), intro, introfend, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                    style.setSpan(new RelativeSizeSpan((float) 1.2), good, goodfend, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                    style.setSpan(new ForegroundColorSpan(android.graphics.Color.parseColor("#323232")), good, goodfend, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                    tv_doctor_aptitude.setText(style);

                    if (doctor.getIcon() == null || doctor.getIcon().equals("")) {
                        tv_doctor_icon.setImageResource(R.drawable.default_icon);

                    } else {
                        ImageLoader.getInstance().displayImage(doctor.getIcon(), tv_doctor_icon);
                    }

                    tv_doctor_commenCount.setText("用户评价(" + doctor.getCommentCount() + "人)");

                    btn_pay.setVisibility(View.VISIBLE);

                    rl_loading.setVisibility(View.GONE);

                    lookfullintroduce();
                    if(doctor.getChatCost().equals("0")){
                        tv_money.setText("免费");
                    }else {
                        tv_money.setText("不可咨询");
                    }

                    break;
            }
        }
    };

    private void findView() {
        tv_doctor_aptitude = (TextView) findViewById(R.id.tv_doctor_aptitude);
        rl_look_full_introduce = (RelativeLayout) findViewById(R.id.rl_look_full_introduce);
        tv_doctor_look_all = (TextView) findViewById(R.id.tv_doctor_look_all);
        scrovView = (ScrollView) findViewById(R.id.scrovView);

        ll_doctor_find = (LinearLayout) findViewById(R.id.ll_doctor_find);

        tv_doctor_top_name = (TextView) findViewById(R.id.tv_doctor_top_name);
        tv_doctor_name = (TextView) findViewById(R.id.tv_doctor_name);
        tv_doctor_title = (TextView) findViewById(R.id.tv_doctor_title);
        tv_hospital_address = (TextView) findViewById(R.id.tv_hospital_address);

        tv_doctor_icon = (CircleImageView) findViewById(R.id.tv_doctor_icon);
        tv_doctor_commenCount = (TextView) findViewById(R.id.tv_doctor_commenCount);

        rl_nonetwork = (RelativeLayout) findViewById(R.id.rl_nonetwork);
        rl_loading = (RelativeLayout) findViewById(R.id.rl_loading);

        btn_pay = (Button) findViewById(R.id.btn_pay);
        tv_money = (TextView) findViewById(R.id.tv_money);

        lv_doctor_particulars_assess = (ListViewForScrollView) findViewById(R.id.lv_doctor_particulars_assess);


        scrovView.smoothScrollTo(1, 1);

        lv_doctor_particulars_assess.setVerticalScrollBarEnabled(false);
        scrovView.setVerticalScrollBarEnabled(false);

        expandView = (ImageView) findViewById(R.id.expand_view);
        tv_doctor_aptitude.setHeight(tv_doctor_aptitude.getLineHeight() * maxDescripLine);

        ll_doctor_find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_pay.setOnClickListener(listener);
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //affirm();pay图文问诊
            if(doctor.getChatCost().equals("0")){
                Intent intent = new Intent(DoctorparticularsActivity.this, ChatpageActivity.class);
                intent.putExtra("name", doctor.getName());
                intent.putExtra("icon", doctor.getIcon());
                intent.putExtra("doctorID", doctor_id);
                intent.putExtra("username", doctor.getUsername());
                intent.putExtra("page", "2");
                startActivity(intent);
            }else{
                ToolUtils.showToast(UIUtils.getContext(), "不可咨询", Toast.LENGTH_SHORT);
            }
        }
    };

    private void affirm() {
        setHeadDialog = new Dialog(this, R.style.CustomDialog);
        setHeadDialog.show();
        dialogView = View.inflate(this, R.layout.inquiry_chat_dialog, null);
        setHeadDialog.getWindow().setContentView(dialogView);
        WindowManager.LayoutParams lp = setHeadDialog.getWindow()
                .getAttributes();
        setHeadDialog.getWindow().setAttributes(lp);

        RelativeLayout rl_confirm = (RelativeLayout) dialogView.findViewById(R.id.rl_confirm);
        RelativeLayout lr_cancel = (RelativeLayout) dialogView.findViewById(R.id.lr_cancel);

        lr_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setHeadDialog.dismiss();
            }
        });

        rl_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setHeadDialog.dismiss();
                //TODO 暂定，问诊页面图文问诊
                //payDialog();
                paydetails();
            }
        });
    }

    private void paydetails() {
        Intent intent = new Intent(this, PayOrderActivityThirdly.class);
        intent.putExtra("name", doctor.getName());
        intent.putExtra("title", doctor.getTitle());
        intent.putExtra("section", doctor.getSection());
        intent.putExtra("icon", doctor.getIcon());
        intent.putExtra("doctorID", doctor_id);
        intent.putExtra("username", doctor.getUsername());
        intent.putExtra("page", "2");
        startActivity(intent);
    }

    private void payDialog() {
        setHeadDialog = new Dialog(this, R.style.CustomDialog);
        setHeadDialog.show();
        WindowManager windowManager = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        dialogView = View.inflate(this, R.layout.payment_dialog, null);
        tv_cash_coupons_stater = (TextView) dialogView.findViewById(R.id.tv_cash_coupons_stater);

        rb_wenx = (RadioButton) dialogView.findViewById(R.id.rb_wenx);
        rb_zhifb = (RadioButton) dialogView.findViewById(R.id.rb_zhifb);
        ll_cancel = (LinearLayout) dialogView.findViewById(R.id.ll_cancel);

        rl_use_cash_coupons = (RelativeLayout) dialogView.findViewById(R.id.rl_use_cash_coupons);

        btn_confirm_payment = (Button) dialogView.findViewById(R.id.btn_confirm_payment);

        tv_cash_coupons_stater.setText("快速问诊劵 ￥25");
        btn_confirm_payment.setText("确认支付 ￥0");

        rb_wenx.setChecked(true);
        progressBar = (ProgressBar) dialogView.findViewById(R.id.progressBar);
        setHeadDialog.getWindow().setContentView(dialogView);
        WindowManager.LayoutParams lp = setHeadDialog.getWindow().getAttributes();
        lp.width = display.getWidth();
        setHeadDialog.getWindow().setAttributes(lp);


        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                int progressBarMax = progressBar.getMax();
                try {

                    while (progressBarMax != progressBar.getProgress()) {
                        int stepProgress = progressBarMax / 1000;
                        int currentprogress = progressBar.getProgress();
                        progressBar.setProgress(currentprogress + stepProgress);
                        Thread.sleep(180);
                    }
                    setHeadDialog.dismiss();

                } catch (InterruptedException e) {
                    e.printStackTrace();

                }
            }
        });
        thread.start();

        paydialogonclick();

    }


    private void paydialogonclick() {
        rb_zhifb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rb_wenx.setChecked(false);
            }
        });
        rb_wenx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rb_zhifb.setChecked(false);
            }
        });
        ll_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setHeadDialog.dismiss();
            }
        });


        btn_confirm_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btn_confirm_payment.getText().toString().equals("确认支付 ￥25")) {
                    Toast.makeText(DoctorparticularsActivity.this, "请支付", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(DoctorparticularsActivity.this, ChatpageActivity.class);

                    intent.putExtra("name", doctor.getName());
                    intent.putExtra("icon", doctor.getIcon());
                    intent.putExtra("doctorID", doctor_id);
                    intent.putExtra("username", doctor.getUsername());
                    intent.putExtra("page", "2");
                    startActivity(intent);
                    setHeadDialog.dismiss();
                }
            }
        });


        rl_use_cash_coupons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DoctorparticularsActivity.this, MyCashCouponsActivity.class);
                intent.putExtra("type", "pay");
                startActivity(intent);
            }
        });

    }

    private void lookfullintroduce() {
        tv_doctor_aptitude.post(new Runnable() {

            @Override
            public void run() {
                expandView.setVisibility(tv_doctor_aptitude.getLineCount() > maxDescripLine ? View.VISIBLE : View.GONE);

            }
        });

        rl_look_full_introduce.setOnClickListener(new View.OnClickListener() {
            boolean isExpand;

            @Override
            public void onClick(View v) {
                isExpand = !isExpand;
                tv_doctor_aptitude.clearAnimation();
                final int deltaValue;
                final int startValue = tv_doctor_aptitude.getHeight();
                int durationMillis = 350;
                if (isExpand) {
                    deltaValue = tv_doctor_aptitude.getLineHeight() * tv_doctor_aptitude.getLineCount() - startValue;
                    RotateAnimation animation = new RotateAnimation(0, 180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                    animation.setDuration(durationMillis);
                    animation.setFillAfter(true);
                    expandView.startAnimation(animation);
                    tv_doctor_look_all.setText("收回");

                } else {
                    deltaValue = tv_doctor_aptitude.getLineHeight() * maxDescripLine - startValue;
                    RotateAnimation animation = new RotateAnimation(180, 0, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                    animation.setDuration(durationMillis);
                    animation.setFillAfter(true);
                    expandView.startAnimation(animation);
                    tv_doctor_look_all.setText("完整介绍");
                }

                Animation animation = new Animation() {
                    protected void applyTransformation(float interpolatedTime, Transformation t) {
                        tv_doctor_aptitude.setHeight((int) (startValue + deltaValue * interpolatedTime));
                    }
                };
                animation.setDuration(durationMillis);
                tv_doctor_aptitude.startAnimation(animation);
                tv_doctor_aptitude.setAutoLinkMask(1);

            }
        });

    }

}
