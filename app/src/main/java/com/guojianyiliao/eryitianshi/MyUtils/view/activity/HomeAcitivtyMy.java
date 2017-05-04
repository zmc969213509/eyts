package com.guojianyiliao.eryitianshi.MyUtils.view.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.guojianyiliao.eryitianshi.Data.Http_data;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.MyLogcat;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.SpUtils;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.UIUtils;
import com.guojianyiliao.eryitianshi.MyUtils.view.activity.fragment.BaikePageFragment;
import com.guojianyiliao.eryitianshi.MyUtils.view.activity.fragment.GrowingPageFragment;
import com.guojianyiliao.eryitianshi.MyUtils.view.activity.fragment.MyPageUserFragment;
import com.guojianyiliao.eryitianshi.MyUtils.view.activity.fragment.NewHomePageFragment;
import com.guojianyiliao.eryitianshi.MyUtils.view.activity.view.AndroidWorkaround;
import com.guojianyiliao.eryitianshi.R;
import com.tencent.bugly.crashreport.CrashReport;
import com.umeng.socialize.UMShareAPI;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.api.BasicCallback;

/**
 * description: 主页
 * autour: jnluo jnluo5889@126.com
 * date:
 * update:
 * version: Administrator
 */

public class HomeAcitivtyMy extends FragmentActivity implements View.OnClickListener {


    @BindView(R.id.rb_home_page)
    RelativeLayout rbHomePage;
    @BindView(R.id.rb_baike_page)
    RelativeLayout rbBaikePage;
    @BindView(R.id.rb_growing_page)
    RelativeLayout rbGrowingPage;
    @BindView(R.id.rb_my_page)
    RelativeLayout rbMyPage;

    @BindView(R.id.fl_icon)
    FrameLayout flIcon;
    @BindView(R.id.icon_task)
    ImageView iconTask;
    @BindView(R.id.icon_orders)
    ImageView iconOrders;
    @BindView(R.id.icon_message)
    ImageView iconMessage;
    @BindView(R.id.icon_user)
    ImageView iconUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //控制底部虚拟键盘
       /* getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
   //                     | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
//                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE);*/
        setContentView(R.layout.a_activity_home);
//状态栏 @ 顶部
       // getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);//A  透明状态栏
//导航栏 @ 底部


        if (AndroidWorkaround.checkDeviceHasNavigationBar(this)) {
            MyLogcat.jLog().e("虚拟键 w");
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
          //  AndroidWorkaround.assistActivity(findViewById(android.R.id.content));
        }

        ButterKnife.bind(this);
        String userid = SpUtils.getInstance(this).get("Userid", null);
        String phone = SpUtils.getInstance(this).get("phone", null);
        CrashReport.putUserData(UIUtils.getContext(), "userkey", userid + "phone:" + phone);/**carsh 上报 ID*/
        MyLogcat.jLog().e("user info id:" + userid + "/" + "phone:" + phone);

        //     JMessageClient.registerEventReceiver(this);
        //     JMessageClient.setNotificationMode(JMessageClient.NOTI_MODE_DEFAULT);

        if (Http_data.giveCashState == 2) {
            //       giveCashCoupons();
            Http_data.giveCashState = 1;
        }

        initView();
        initEvent();
        setSelect(0);


    }


    private void initEvent() {
        rbHomePage.setOnClickListener(this);
        rbBaikePage.setOnClickListener(this);
        rbGrowingPage.setOnClickListener(this);
        rbMyPage.setOnClickListener(this);

    }

    private void initView() {

    }

    NewHomePageFragment mTab01;
    BaikePageFragment mTab02;
    GrowingPageFragment mTab03;
    MyPageUserFragment mTab04;

    public void setSelect(int page) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        hideFragment(transaction);
        // 把图片设置为亮的
        // 设置内容区域
        switch (page) {
            case 0:
                if (mTab01 == null) {
                    mTab01 = new NewHomePageFragment();
                    transaction.add(R.id.fl_icon, mTab01);
                } else {
                    transaction.show(mTab01);
                }
                iconTask.setSelected(true);
                break;
            case 1:
                if (mTab02 == null) {
                    mTab02 = new BaikePageFragment();
                    transaction.add(R.id.fl_icon, mTab02);
                } else {
                    transaction.show(mTab02);

                }
                iconOrders.setSelected(true);
                break;
            case 2:
                if (mTab03 == null) {
                    mTab03 = new GrowingPageFragment();
                    transaction.add(R.id.fl_icon, mTab03);
                } else {
                    transaction.show(mTab03);
                }
                iconMessage.setSelected(true);
                break;
            case 3:
                if (mTab04 == null) {
                    mTab04 = new MyPageUserFragment();
                    transaction.add(R.id.fl_icon, mTab04);
                } else {
                    transaction.show(mTab04);
                }
                iconUser.setSelected(true);
                break;
            default:
                break;
        }

        transaction.commit();
    }

    private void hideFragment(FragmentTransaction transaction) {
        if (mTab01 != null) {
            transaction.hide(mTab01);
        }
        if (mTab02 != null) {
            transaction.hide(mTab02);
        }
        if (mTab03 != null) {
            transaction.hide(mTab03);
        }
        if (mTab04 != null) {
            transaction.hide(mTab04);
        }
    }

    @Override
    public void onClick(View v) {
        resetImgs();
        switch (v.getId()) {
            case R.id.rb_home_page:
                setSelect(0);
                break;
            case R.id.rb_baike_page:
                setSelect(1);
                break;
            case R.id.rb_growing_page:
                setSelect(2);
                break;
            case R.id.rb_my_page:
                setSelect(3);
                break;

            default:
                break;
        }
    }

    //切换图片至暗色
    private void resetImgs() {
        iconTask.setSelected(false);
        iconOrders.setSelected(false);
        iconMessage.setSelected(false);
        iconUser.setSelected(false);
    }

    private Dialog dialog;
    private Dialog dialognext;
    private Dialog dialognextnext;

    /**
     * 引导页面
     */
    private void GuideDailog() {
        dialog = new Dialog(this, R.style.GuideHelperDialog);
        dialog.show();
        View viewDialog = UIUtils.getinflate(R.layout.dialog_guide_helper);
        ImageButton ivb_next = (ImageButton) viewDialog.findViewById(R.id.ivb_next);
        ivb_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogshownext();
            }
        });
        int width = UIUtils.getDisplay(this).getWidth();
        int height = UIUtils.getDisplay(this).getHeight();
        WindowManager.LayoutParams attributes = dialog.getWindow().getAttributes();
        attributes.width = width;
        attributes.height = height;
        //dialog.getWindow().setAttributes(attributes);
        // dialog.setContentView(viewDialog);
        //设置dialog的宽高为屏幕的宽高
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(width, height);
        dialog.setContentView(viewDialog, layoutParams);

        /*final GuideHelper guideHelper = new GuideHelper(this);
        GuideHelper.TipData tipData1 = new GuideHelper.TipData(R.drawable.prompt01);
        guideHelper.addPage(tipData1);
        GuideHelper.TipData tipData2 = new GuideHelper.TipData(R.drawable.prompt02);
        guideHelper.addPage(tipData2);
        GuideHelper.TipData tipData3 = new GuideHelper.TipData(R.drawable.prompt03);
        guideHelper.addPage(tipData3);
        guideHelper.show(false);*/
    }

    private void dialogshownext() {
        dialog.dismiss();
        dialognext = new Dialog(this, R.style.GuideHelperDialog);
        dialognext.show();
        View viewDialog = UIUtils.getinflate(R.layout.dialog_guide_helper_next);
        int width = UIUtils.getDisplay(this).getWidth();
        int height = UIUtils.getDisplay(this).getHeight();
        WindowManager.LayoutParams attributes = dialog.getWindow().getAttributes();
        attributes.width = width;
        attributes.height = height;
        viewDialog.findViewById(R.id.ivb_next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialognext.dismiss();
                dialogshownextnext();
            }
        });
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(width, height);
        dialognext.setContentView(viewDialog, layoutParams);
        //dialognext.setContentView(viewDialog);
    }

    private void dialogshownextnext() {
        dialognextnext = new Dialog(this, R.style.GuideHelperDialog);
        dialognextnext.show();
        View viewDialog = UIUtils.getinflate(R.layout.dialog_guide_helper_next_next);
        int width = UIUtils.getDisplay(this).getWidth();
        int height = UIUtils.getDisplay(this).getHeight();
        WindowManager.LayoutParams attributes = dialog.getWindow().getAttributes();
        attributes.width = width;
        attributes.height = height;
        viewDialog.findViewById(R.id.ivb_next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialognextnext.dismiss();
            }
        });
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(width, height);
        dialognextnext.setContentView(viewDialog, layoutParams);
    }

    @Override
    protected void onDestroy() {
        //     JMessageClient.unRegisterEventReceiver(this);
        super.onDestroy();
    }

    private void jmessage() {
        //sp.getTag().getId();
        String phone = SpUtils.getInstance(this).get("phone", null);

      /*  if (User_Http.user.getPhone() == null) {
            username = sp.getTag().getPhone();
        } else {
            username = User_Http.user.getPhone();
        }*/
        // String password = "123456"+username;

        JMessageClient.register(phone, "123456", new BasicCallback() {
            @Override
            public void gotResult(int i, String s) {
                MyLogcat.jLog().e("js :" + i);
                if (i == 0) {
                    MyLogcat.jLog().e("js :" + "succ");
                } else if (i == 898001) {
                    MyLogcat.jLog().e("js :" + "falid");
                } else {
                    MyLogcat.jLog().e("js :" + "else" + i);

                }

            }
        });

        JMessageClient.login(phone, "123456", new BasicCallback() {
            @Override
            public void gotResult(int i, String s) {
                MyLogcat.jLog().e("js login: " + i);
                if (i == 0) {
                    Log.e("jmessage", "登录成功");
                } else {
                    Log.e("jmessage", "登录失败");
                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        //     JPushInterface.onPause(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //       JPushInterface.onResume(this);
    }

    private Dialog setHeadDialog;
    private View dialogView;

    public void giveCashCoupons() {

        setHeadDialog = new Dialog(this, R.style.CustomDialog);
        setHeadDialog.show();
        dialogView = View.inflate(getApplicationContext(), R.layout.home_give_cash_coupons_dialog, null);
        setHeadDialog.setCanceledOnTouchOutside(true);

        setHeadDialog.getWindow().setContentView(dialogView);
        WindowManager.LayoutParams lp = setHeadDialog.getWindow().getAttributes();
        setHeadDialog.getWindow().setAttributes(lp);
        giveCashCouponsClick();
    }


    private void giveCashCouponsClick() {
        Button btn_look_cash_coupons = (Button) dialogView.findViewById(R.id.btn_look_cash_coupons);
        Button btn_payment_coupons_new = (Button) dialogView.findViewById(R.id.btn_payment_coupons_new);

        btn_look_cash_coupons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setHeadDialog.dismiss();
                GuideDailog();
            }
        });

        btn_payment_coupons_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setHeadDialog.dismiss();
                //Intent intent = new Intent(this, InquiryActivity.class);
                //startActivity(intent);
            }
        });
    }


    private long exitTime = 0;


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                JMessageClient.logout();
                this.finish();
                // System.exit(0);
                //杀死该应用进程

            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
       /* Fragment fragmentByTag = fm.findFragmentByTag("");
        fragmentByTag.onActivityResult(requestCode, resultCode, data);*/
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);//完成回调
    }
}
