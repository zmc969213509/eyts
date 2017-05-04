package com.guojianyiliao.eryitianshi.MyUtils.view.activity.fragment;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.guojianyiliao.eryitianshi.Data.Http_data;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.MyLogcat;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.SpUtils;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.ToolUtils;
import com.guojianyiliao.eryitianshi.MyUtils.view.activity.MyInquiryActivity;
import com.guojianyiliao.eryitianshi.R;
import com.guojianyiliao.eryitianshi.View.activity.InquiryActivity;
import com.guojianyiliao.eryitianshi.View.activity.InquiryrecordActivity;
import com.guojianyiliao.eryitianshi.View.activity.MyCashCouponsActivity;
import com.guojianyiliao.eryitianshi.View.activity.MyDoctorActivity;
import com.guojianyiliao.eryitianshi.View.activity.MycollectActivity;
import com.guojianyiliao.eryitianshi.View.activity.PersonaldataActivity;
import com.guojianyiliao.eryitianshi.View.activity.ReservationActivity;
import com.guojianyiliao.eryitianshi.View.activity.SetPageActivity;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * 个人界面
 */
public class MyPageUserFragment extends Fragment implements EasyPermissions.PermissionCallbacks {

    private static final int INT_SHARE = 1;
    @BindView(R.id.iv_icon)
    CircleImageView ivIcon;
    @BindView(R.id.iv_gender)
    ImageView ivGender;
    @BindView(R.id.tv_name)
    TextView tvName;
    /**
     * 头部三个按钮
     */
    @BindView(R.id.ll_inquiry)
    LinearLayout llInquiry;
    @BindView(R.id.ll_ordered)
    LinearLayout llOrdered;
    @BindView(R.id.ll_helped)
    LinearLayout llHelped;

    @BindView(R.id.rl_my_cashcoupons)
    RelativeLayout rlMyCashcoupons;
    @BindView(R.id.rl_mycollect)
    RelativeLayout rlMycollect;
    @BindView(R.id.rl_mydpctor)
    RelativeLayout rlMydpctor;
    @BindView(R.id.rl_mypage_share)
    RelativeLayout rlMypageShare;
    @BindView(R.id.rl_set)
    RelativeLayout rlSet;

    String userid;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userid = SpUtils.getInstance(getActivity()).get("userid", null);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.a_fragment_mypage, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        String name = SpUtils.getInstance(getActivity()).get("name", null);
        String gender = SpUtils.getInstance(getActivity()).get("gender", null);

        MyLogcat.jLog().e("name:" + name + " //gender:" + gender);

        tvName.setText(name);
        if (gender.equals("男")) {
            MyLogcat.jLog().e("true");
            ivGender.setSelected(true);
        } else {
            MyLogcat.jLog().e("false");
            ivGender.setSelected(false);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    /**
     @OnClick(R.id.ivb_back_finsh) public void back() {
     finish();
     }
      * */


    /**我的支付*/

    /**
     * 设置
     */
    @OnClick(R.id.rl_set)
    public void mySet() {
        startActivity(new Intent(getContext(), SetPageActivity.class));
    }

    /**
     * 分享给好友
     */
    @OnClick(R.id.rl_mypage_share)
    public void myShare() {
        // showUmeng();
    }

    /**
     * 我的医生
     */
    @OnClick(R.id.rl_mydpctor)
    public void myOrdered() {
        Intent intent = new Intent(getContext(), MyDoctorActivity.class);
        startActivity(intent);
    }

    /**
     * 我的收藏
     */
    @OnClick(R.id.rl_mycollect)
    public void myCollert() {
        Intent intent = new Intent(getContext(), MycollectActivity.class);
        startActivity(intent);
    }

    /**
     * 我的代金券
     */
    @OnClick(R.id.rl_my_cashcoupons)
    public void myCashcoupons() {
        Intent intent = new Intent(getContext(), MyCashCouponsActivity.class);
        intent.putExtra("type", "mypage");
        startActivity(intent);
    }

    /**
     * 我的诊疗
     */
    @OnClick(R.id.ll_helped)
    public void myHelped() {
        startActivity(new Intent(getContext(), MyInquiryActivity.class));
    }

    /**
     * 我的预约
     */
    @OnClick(R.id.ll_ordered)
    public void myYuyueOrdered() {
        startActivity(new Intent(getContext(), ReservationActivity.class));
    }

    /**
     * 我的问诊
     */
    @OnClick(R.id.ll_inquiry)
    public void myInquiry() {
        startActivity(new Intent(getContext(), InquiryrecordActivity.class));
    }

    /**
     * 设置头像
     */
    @OnClick(R.id.iv_icon)
    public void GetHttpHeadIcon() {
        startActivity(new Intent(getContext(), PersonaldataActivity.class));//设置头像
    }

    String[] permss;
    UMWeb web;

    private void showUmeng() {
        if (Build.VERSION.SDK_INT >= 23) {
            permss = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
            if (EasyPermissions.hasPermissions(getActivity(), permss)) {
                MyLogcat.jLog().e("已经有这个权限了");
                web = new UMWeb("http://www.eryitianshi.com");
                web.setTitle("儿医天使（儿科名医）");//标题
                web.setThumb(new UMImage(getActivity(), R.drawable.app_log));  //缩略图
                web.setDescription("一键登录，三秒问诊，三甲儿科医生在线解决了我的问题，你也来试试");//描述
            } else {
                EasyPermissions.requestPermissions(getActivity(), "",
                        INT_SHARE, permss);
                MyLogcat.jLog().e("正在申请");
            }
        } else {
            MyLogcat.jLog().e("sdk<23");
            web = new UMWeb("http://www.eryitianshi.com");
            web.setTitle("儿医天使（儿科名医）");//标题
            web.setThumb(new UMImage(getActivity(), R.drawable.app_log));  //缩略图
            web.setDescription("一键登录，三秒问诊，三甲儿科医生在线解决了我的问题，你也来试试");//描述
        }
        /*try {
            if (Build.VERSION.SDK_INT >= 23) {
                String[] mPermissionList = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
                ActivityCompat.requestPermissions(getActivity(), mPermissionList, 123);
                web = new UMWeb("http://www.eryitianshi.com");
                web.setTitle("儿医天使");//标题
                web.setThumb(new UMImage(getActivity(), R.drawable.app_log));  //缩略图
                web.setDescription("http://www.eryitianshi.com");//描述
            } else {*/
        //  web = new UMWeb("http://www.eryitianshi.com");
        // web.setTitle("儿医天使");//标题
        //  web.setThumb(new UMImage(getActivity(), R.drawable.app_log));  //缩略图
        // web.setDescription("http://www.eryitianshi.com");//描述
        //   }
        //  } catch (Exception e) {
        //ToolUtils.showToast(getActivity(), "没有访问权限！", Toast.LENGTH_LONG);
        //  }


        new ShareAction(getActivity())
                .withMedia(web)
                .setDisplayList(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE, SHARE_MEDIA.SINA)
                .setCallback(new UMShareListener() {
                    @Override
                    public void onStart(SHARE_MEDIA share_media) {
                    }

                    @Override
                    public void onResult(SHARE_MEDIA share_media) {
                        Httprefresh();
                        giveCashCoupons();
                    }

                    @Override
                    public void onError(SHARE_MEDIA share_media, Throwable throwable) {
                        ToolUtils.showToast(getActivity(), "请去设置查看", Toast.LENGTH_SHORT);
                    }

                    @Override
                    public void onCancel(SHARE_MEDIA share_media) {
                        MyLogcat.jLog().e("onCancel");
                    }
                }).open();
    }

    Random random;
    private List<Integer> nummer;
    private String type;

    private void Httprefresh() {
        random = new Random();
        nummer = new ArrayList<>();
        for (int i = 1; i < 6; i++) {
            nummer.clear();
            nummer.add(random.nextInt(2));
        }

        if (nummer.size() != 0) {
            type = nummer.get(0).toString();
            if (type.equals("0")) {
                type = "1";
            } else {
                type = "2";
            }
        } else {
            type = "2";
        }
        OkHttpUtils.post()
                .url(Http_data.http_data + "/getShareVoucher")
                .addParams("userId", userid)
                .addParams("type", type)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        MyLogcat.jLog().e("getShareVoucher type分享 成功：" + type);
                    }
                });
    }

    Dialog setHeadDialog;
    View dialogView;

    public void giveCashCoupons() {

        setHeadDialog = new Dialog(getActivity(), R.style.CustomDialog);
        setHeadDialog.show();
        dialogView = View.inflate(getActivity(), R.layout.home_give_cash_coupons_sharedialog, null);
        setHeadDialog.setCanceledOnTouchOutside(true);

        setHeadDialog.getWindow().setContentView(dialogView);
        WindowManager.LayoutParams lp = setHeadDialog.getWindow().getAttributes();
        setHeadDialog.getWindow().setAttributes(lp);

        giveCashCouponsClick();
    }

    private void giveCashCouponsClick() {
        Button btn_look_cash_coupons = (Button) dialogView.findViewById(R.id.btn_look_cash_coupons);
        Button btn_payment_coupons_new = (Button) dialogView.findViewById(R.id.btn_payment_coupons_new);
        RelativeLayout ll_give_cash_coupons = (RelativeLayout) dialogView.findViewById(R.id.ll_give_cash_coupons);
        /*android:background="@drawable/give_cash_coupons_share"*/
        if (type.equals("1")) {
            ll_give_cash_coupons.setBackgroundResource(R.drawable.give_cash_coupons_share);
        } else {
            ll_give_cash_coupons.setBackgroundResource(R.drawable.home_givecashcoupons_healthy);
        }
        btn_look_cash_coupons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setHeadDialog.dismiss();
            }
        });

        btn_payment_coupons_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setHeadDialog.dismiss();
                Intent intent = new Intent(getActivity(), InquiryActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        if (requestCode == INT_SHARE) {
            web = new UMWeb("http://www.eryitianshi.com");
            web.setTitle("儿医天使");//标题
            web.setThumb(new UMImage(getActivity(), R.drawable.app_log));  //缩略图
            web.setDescription("http://www.eryitianshi.com");//描述
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        MyLogcat.jLog().e("失败 onPermissionsDenied 回调");
    }
}
