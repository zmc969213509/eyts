package com.guojianyiliao.eryitianshi.View.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.guojianyiliao.eryitianshi.Data.entity.Inquiryrecord;
import com.guojianyiliao.eryitianshi.Data.User_Http;
import com.guojianyiliao.eryitianshi.Data.entity.Userinfo;
import com.guojianyiliao.eryitianshi.R;
import com.guojianyiliao.eryitianshi.Utils.db.InquiryrecordDao;
import com.guojianyiliao.eryitianshi.Utils.MyBaseActivity;
import com.guojianyiliao.eryitianshi.Utils.SharedPsaveuser;
import com.guojianyiliao.eryitianshi.page.adapter.InquiryrecordAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class InquiryrecordActivity extends MyBaseActivity {
    private ListView lv_inquiryrecord;
    private InquiryrecordAdapter adapter;
    private LinearLayout ll_rut;
    InquiryrecordDao db;
    List<Inquiryrecord> list;
    private RelativeLayout rl_nonetwork;


    private Dialog setHeadDialog;

    private View dialogView;

    RadioButton rb_wenx;

    RadioButton rb_zhifb;

    LinearLayout ll_cancel;

    Button btn_confirm_payment;

    ProgressBar progressBar;

    TextView tv_cash_coupons_stater;

    RelativeLayout rl_use_cash_coupons;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inquiryrecord);
        try {

            list = new ArrayList<>();
            db = new InquiryrecordDao(this);
            findView();
            init();

        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    private void findView() {
        lv_inquiryrecord = (ListView) findViewById(R.id.lv_inquiryrecord);
        rl_nonetwork = (RelativeLayout) findViewById(R.id.rl_nonetwork);
        ll_rut = (LinearLayout) findViewById(R.id.ll_rut);
        lv_inquiryrecord.setVerticalScrollBarEnabled(false);
        lv_inquiryrecord.setOnItemClickListener(lvlistener);
        ll_rut.setOnClickListener(listener);
    }

    private void init() {

        if (User_Http.user.getPhone() != null) {
            list = db.chatfind(User_Http.user.getPhone());
        } else {
            SharedPsaveuser sp = new SharedPsaveuser(this);
            Userinfo userinfo = sp.getTag();
            list = db.chatfind(userinfo.getPhone());
        }

        adapter = new InquiryrecordAdapter(this, list);
        lv_inquiryrecord.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private AdapterView.OnItemClickListener lvlistener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//            affirm(position);
            Intent intent = new Intent(InquiryrecordActivity.this, ChatpageActivity.class);
            intent.putExtra("name", list.get(position).getDoctorname());
            intent.putExtra("icon", list.get(position).getDoctoricon());
            intent.putExtra("doctorID", list.get(position).getId());
            intent.putExtra("username", list.get(position).getDoctorid());
            intent.putExtra("page", "1");
            startActivity(intent);

        }
    };


    private void affirm(final int pos) {
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
                //TODO 问诊历史页面，支付暂定.未做完，数据不全
                // payDialog(pos);
                //paydetails(pos);
                Toast.makeText(InquiryrecordActivity.this,"正在建设中！",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void paydetails(int position) {
       /* Intent intent = new Intent(this, PayOrderActivity.class);
        intent.putExtra("name", list.get(position).getName());
        intent.putExtra("title", list.get(position).getTitle());
        intent.putExtra("section", list.get(position).getSection());*/
        //startActivity(intent);
    }

    private void payDialog(int pos) {

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


        //
        btn_confirm_payment = (Button) dialogView.findViewById(R.id.btn_confirm_payment);

        tv_cash_coupons_stater.setText("快速问诊劵 ￥25");
        btn_confirm_payment.setText("确认支付 ￥0");

        rb_wenx.setChecked(true);
        progressBar = (ProgressBar) dialogView.findViewById(R.id.progressBar);
        setHeadDialog.getWindow().setContentView(dialogView);
        WindowManager.LayoutParams lp = setHeadDialog.getWindow().getAttributes();
        lp.width = display.getWidth();
        setHeadDialog.getWindow().setAttributes(lp);

        //
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                int progressBarMax = progressBar.getMax();
                try {
                    //x
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

        paydialogonclick(pos);

    }


    //
    private void paydialogonclick(final int pos) {
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


        //
        btn_confirm_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btn_confirm_payment.getText().toString().equals("确认支付 ￥25")) {
                    Toast.makeText(InquiryrecordActivity.this, "请支付", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(InquiryrecordActivity.this, ChatpageActivity.class);
                    intent.putExtra("name", list.get(pos).getDoctorname());
                    intent.putExtra("icon", list.get(pos).getDoctoricon());
                    intent.putExtra("doctorID", list.get(pos).getId());
                    intent.putExtra("username", list.get(pos).getDoctorid());
                    startActivity(intent);
                    setHeadDialog.dismiss();
                }
            }
        });


        rl_use_cash_coupons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InquiryrecordActivity.this, MyCashCouponsActivity.class);
                intent.putExtra("type", "pay");
                startActivity(intent);
            }
        });


    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.ll_rut:
                    finish();
                    break;
            }
        }
    };

}
