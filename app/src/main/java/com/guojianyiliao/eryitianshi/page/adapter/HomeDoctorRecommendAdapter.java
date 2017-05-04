package com.guojianyiliao.eryitianshi.page.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.guojianyiliao.eryitianshi.Data.entity.Inquiry;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.MyLogcat;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.ToolUtils;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.UIUtils;
import com.guojianyiliao.eryitianshi.R;
import com.guojianyiliao.eryitianshi.View.activity.ChatpageActivity;
import com.guojianyiliao.eryitianshi.View.activity.MyCashCouponsActivity;
import com.guojianyiliao.eryitianshi.View.activity.PayOrderActivity;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2016/10/31 0031.
 */
public class HomeDoctorRecommendAdapter extends BaseAdapter {
    Context context;
    List<Inquiry> list;
    private Dialog setHeadDialog;
    private View dialogView;


    RadioButton rb_wenx;

    RadioButton rb_zhifb;

    LinearLayout ll_cancel;

    Button btn_confirm_payment;

    ProgressBar progressBar;

    TextView tv_cash_coupons_stater;

    RelativeLayout rl_use_cash_coupons;


    public HomeDoctorRecommendAdapter(Context context, List<Inquiry> list) {
        this.context = context;
        this.list = list;

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Inquiry getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = UIUtils.getinflate(R.layout.home_doctor_recommend_list_item);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tv_name.setText(list.get(position).getName());
        viewHolder.tv_home_docotor_title.setText(list.get(position).getTitle());
        viewHolder.tv_home_doctor_section.setText(list.get(position).getSection());

        if (list.get(position).getAdept() == null || list.get(position).getAdept().equals("")) {
            viewHolder.tv_home_doctor_adept.setText("");
        } else {
            viewHolder.tv_home_doctor_adept.setText("擅长：" + list.get(position).getAdept());
        }

        // viewHolder.btn_home_doctor_chatCost.setText("￥" + list.get(position).getChatCost());

        /** 3.21 修改*/

        if (list.get(position).getChatCost().equals("0")) {
            viewHolder.iv_istrue_home.setBackgroundResource(R.drawable.home_doctor_state);
            viewHolder.iv_istrue_home.setVisibility(View.VISIBLE);
            viewHolder.btn_home_doctor_chatCost.setText("免费");
        } else {
            //viewHolder.btn_home_doctor_chatCost.setText("￥" + list.get(position).getChatCost());
            viewHolder.btn_home_doctor_chatCost.setText("不可咨询");
            viewHolder.iv_istrue_home.setVisibility(View.INVISIBLE);
        }

        if (list.get(position).getIcon() == null || list.get(position).getIcon().equals("")) {
            viewHolder.iv_home_doctor_icon.setImageResource(R.drawable.default_icon);
        } else {
            ImageLoader.getInstance().displayImage(list.get(position).getIcon(), viewHolder.iv_home_doctor_icon);
        }


        viewHolder.btn_home_doctor_chatCost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payistrue(position, v);
            }
        });
        return convertView;
    }

    private void payistrue(int pos, View v) {
        // 跳转到聊天界面
        MyLogcat.jLog().e("跳转到聊天界面");
        if (list.get(pos).getChatCost().equals("0")) {
            Intent intent = new Intent(context, ChatpageActivity.class);
            intent.putExtra("name", list.get(pos).getName());
            intent.putExtra("icon", list.get(pos).getIcon());
            intent.putExtra("doctorID", list.get(pos).getId());
            intent.putExtra("username", list.get(pos).getUsername());
            intent.putExtra("page", "2");
            context.startActivity(intent);
        } else if (list.get(pos).getChatCost().equals("250")) {
            paydetails(pos);

        } else {
            ToolUtils.showToast(UIUtils.getContext(), "不可咨询", Toast.LENGTH_SHORT);
            /*Snackbar.make(v, "不可咨询", Snackbar.LENGTH_SHORT)
                    .show();*/
        }
    }

    private void paydetails(final int position) {
        setHeadDialog = new Dialog(context, R.style.CustomDialog);
        setHeadDialog.show();
        dialogView = UIUtils.getinflate(R.layout.inquiry_chat_dialog);
        setHeadDialog.getWindow().setContentView(dialogView);
        WindowManager.LayoutParams lp = setHeadDialog.getWindow().getAttributes();
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
                //TODO 功能暂定。支付先进入到订单确认界面
                //payDialog(position);//1.0的支付dialog
                //1.0.3的支付页面
                Intent intent = new Intent(context, PayOrderActivity.class);
               /*intent.putExtra("name",list.get(position).getName());
               intent.putExtra("title",list.get(position).getTitle());
               intent.putExtra("section",list.get(position).getSection());*/
                intent.putExtra("page", "2");
                Inquiry inquiry = list.get(position);
                intent.putExtra("Inquiry", inquiry);
                context.startActivity(intent);
                setHeadDialog.dismiss();
            }

        });
    }

    private void payDialog(int pos) {

        setHeadDialog = new Dialog(context, R.style.CustomDialog);
        setHeadDialog.show();
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        dialogView = View.inflate(context, R.layout.payment_dialog, null);
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
        paydialogonclick(pos);


    }


    //支付点击事件
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


        //确认支付
        btn_confirm_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btn_confirm_payment.getText().toString().equals("确认支付 ￥25")) {
                    Toast.makeText(context, "请支付", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(context, ChatpageActivity.class);
                    intent.putExtra("name", list.get(pos).getName());
                    intent.putExtra("icon", list.get(pos).getIcon());
                    intent.putExtra("doctorID", list.get(pos).getId());
                    intent.putExtra("username", list.get(pos).getUsername());
                    intent.putExtra("page", "2");
                    context.startActivity(intent);
                    setHeadDialog.dismiss();
                }
            }
        });


        rl_use_cash_coupons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MyCashCouponsActivity.class);
                intent.putExtra("type", "pay");
                context.startActivity(intent);
            }
        });

    }


//从现金卷页面收到的广播，如果使用了现金卷则修改现金卷item和去人按钮
//    @Override
//    public void notifyAllActivity(String str) {
//        if (str.equals("更新首页问诊支付弹出框")) {
//            try {
//                tv_cash_coupons_stater.setText("快速问诊劵 ￥25");
//                btn_confirm_payment.setText("确认支付 ￥0");
//            } catch (Exception e) {
//                e.printStackTrace();
//            } finally {
//                dialogView = View.inflate(context, R.layout.payment_dialog, null);
//                tv_cash_coupons_stater = (TextView) dialogView.findViewById(R.id.tv_cash_coupons_stater);
//                btn_confirm_payment = (Button) dialogView.findViewById(R.id.btn_confirm_payment);
//                tv_cash_coupons_stater.setText("快速问诊劵 ￥25");
//                btn_confirm_payment.setText("确认支付 ￥0");
//            }
//
//        }
//    }


    static class ViewHolder {
        public TextView tv_name, tv_home_docotor_title, tv_home_doctor_section, tv_home_doctor_adept;
        public CircleImageView iv_home_doctor_icon;
        public Button btn_home_doctor_chatCost;
        public ImageView iv_istrue_home;


        ViewHolder(View v) {
            tv_name = (TextView) v.findViewById(R.id.tv_name);
            iv_home_doctor_icon = (CircleImageView) v.findViewById(R.id.iv_home_doctor_icon);
            tv_home_docotor_title = (TextView) v.findViewById(R.id.tv_home_docotor_title);
            tv_home_doctor_section = (TextView) v.findViewById(R.id.tv_home_doctor_section);
            tv_home_doctor_adept = (TextView) v.findViewById(R.id.tv_home_doctor_adept);
            btn_home_doctor_chatCost = (Button) v.findViewById(R.id.btn_home_doctor_chatCost);
            iv_istrue_home = (ImageView) v.findViewById(R.id.iv_istrue_home);

        }
    }
}
