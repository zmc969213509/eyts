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
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.guojianyiliao.eryitianshi.Data.entity.DoctorList;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.MyLogcat;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.ToolUtils;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.UIUtils;
import com.guojianyiliao.eryitianshi.R;
import com.guojianyiliao.eryitianshi.View.activity.ChatpageActivity;
import com.guojianyiliao.eryitianshi.View.activity.MyCashCouponsActivity;
import com.guojianyiliao.eryitianshi.View.activity.PayOrderActivityTwo;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2016/9/1 0001.
 */
public class InquiryAdapter extends BaseAdapter {

    private Context context;
    private List<DoctorList> list;
    private Dialog setHeadDialog;
    private View dialogView;

    RadioButton rb_wenx;

    RadioButton rb_zhifb;

    LinearLayout ll_cancel;

    Button btn_confirm_payment;

    ProgressBar progressBar;

    TextView tv_cash_coupons_stater;

    RelativeLayout rl_use_cash_coupons;


    public InquiryAdapter(Context context, List<DoctorList> list) {
        this.context = context;
        this.list = list;


    }

    private int onclick;

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public DoctorList getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        onclick = position;
        if (convertView == null) {
            convertView = UIUtils.getinflate(R.layout.inquiry_list_item);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tvTitle.setText(list.get(position).getTitle());
        viewHolder.tvName.setText(list.get(position).getName());
        viewHolder.tvIntro.setText("擅长：" + list.get(position).getAdept());
        viewHolder.tvSection.setText(list.get(position).getSection());
        ImageLoader.getInstance().displayImage(list.get(position).getIcon(), viewHolder.ivIcon);

        /** 3.21 修改 ----------
         *     1.1版本 暂时没用
         * */
       /* if (list.get(position).getChatCost().equals("0")) {
            viewHolder.iv_istrue_seek.setVisibility(View.VISIBLE);
            viewHolder.iv_istrue_seek.setBackgroundResource(R.drawable.home_doctor_state);
            viewHolder.btn_money.setText("免费");
            // viewHolder.btn_money.setBackgroundResource(R.color.empty);
        } else {
            viewHolder.iv_istrue_seek.setVisibility(View.INVISIBLE);
            //viewHolder.btn_money.setText("￥" + list.get(position).getChatCost());
            viewHolder.btn_money.setText("不可咨询");
        }
        if (list.get(position).getAdept() == null || list.get(position).getAdept().equals("")) {
            viewHolder.tv_intro.setText("");
        } else {
            viewHolder.tv_intro.setText("擅长：" + list.get(position).getAdept());
        }

        viewHolder.tv_section.setText(list.get(position).getSection());
        if (list.get(position).getIcon() == null || list.get(position).getIcon().equals("")) {
            viewHolder.iv_icon.setImageResource(R.drawable.default_icon);
        } else {
            ImageLoader.getInstance().displayImage(list.get(position).getIcon(), viewHolder.iv_icon);
        }*/


        viewHolder.llFlChat.setOnClickListener(new View.OnClickListener() {
                                                   @Override
                                                   public void onClick(View v) {
                                                       // 跳转到聊天界面
                                                       MyLogcat.jLog().e("跳转到聊天界面");
                                                       if (list.get(position).getChatcost().equals("0")) {
                                                           Intent intent = new Intent(context, ChatpageActivity.class);
                                                           intent.putExtra("name", list.get(position).getName());
                                                           intent.putExtra("icon", list.get(position).getIcon());
                                                           intent.putExtra("doctorID", list.get(position).getDoctorid());
                                                           intent.putExtra("username", list.get(position).getUsername());
                                                           intent.putExtra("page", "2");
                                                           context.startActivity(intent);
                                                       } else if (list.get(position).getChatcost().equals("250")) {
                                                           payClick(position);

                                                       } else {
                                                           ToolUtils.showToast(UIUtils.getContext(), "不可咨询", Toast.LENGTH_SHORT);
                                                       }
                                                   }

                                               }

        );
        return convertView;
    }


    private void payClick(final int position) {
        setHeadDialog = new Dialog(context, R.style.CustomDialog);
        setHeadDialog.show();
        View dialogView = View.inflate(context, R.layout.inquiry_chat_dialog, null);
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

                payDialog(position);
            }

        });

    }

    private void payDialog(int pos) {
        Intent intent = new Intent(context, PayOrderActivityTwo.class);
        intent.putExtra("name", list.get(pos).getName());
        intent.putExtra("title", list.get(pos).getTitle());
        intent.putExtra("section", list.get(pos).getSection());
        intent.putExtra("icon", list.get(pos).getIcon());
        intent.putExtra("doctorID", list.get(pos).getDoctorid());
        intent.putExtra("username", list.get(pos).getUsername());
        intent.putExtra("page", "2");
        context.startActivity(intent);

    }

    //TODO 支付弹出框,支付功能未集成，暂定
    private void payDialog1(int pos) {

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


        //确认支付
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


        btn_confirm_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btn_confirm_payment.getText().toString().equals("确认支付 ￥25")) {
                    Toast.makeText(context, "请支付", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(context, ChatpageActivity.class);
                    intent.putExtra("name", list.get(pos).getName());
                    intent.putExtra("icon", list.get(pos).getIcon());
                    intent.putExtra("doctorID", list.get(pos).getDoctorid());
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
//        if (str.equals("更新问诊支付弹出框")) {
//            try {
//
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
//
//        }
//    }

    static class ViewHolder {
        @BindView(R.id.ll_fl_chat)
        LinearLayout llFlChat;

        @BindView(R.id.iv_icon)
        CircleImageView ivIcon;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_intro)
        TextView tvIntro;
        @BindView(R.id.tv_section)
        TextView tvSection;


        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
