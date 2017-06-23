package com.guojianyiliao.eryitianshi.MyUtils.view.activity.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
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

import com.google.gson.Gson;
import com.guojianyiliao.eryitianshi.Data.Http_data;
import com.guojianyiliao.eryitianshi.MyUtils.bean.UserInfoLogin;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.MyLogcat;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.SharedPreferencesTools;
import com.guojianyiliao.eryitianshi.MyUtils.view.activity.AllDoctorActivity;
import com.guojianyiliao.eryitianshi.MyUtils.view.activity.MyDoctorActivity;
import com.guojianyiliao.eryitianshi.MyUtils.view.activity.MyInquiryActivity;
import com.guojianyiliao.eryitianshi.MyUtils.view.activity.MycollectActivity;
import com.guojianyiliao.eryitianshi.MyUtils.view.activity.TODOActivity;
import com.guojianyiliao.eryitianshi.MyUtils.view.activity.YYGHHisActivity;
import com.guojianyiliao.eryitianshi.MyUtils.view.activity.imChatHistoryActivity;
import com.guojianyiliao.eryitianshi.R;
import com.guojianyiliao.eryitianshi.View.activity.MyCashCouponsActivity;
//import com.guojianyiliao.eryitianshi.View.activity.MyDoctorActivity;
//import com.guojianyiliao.eryitianshi.View.activity.MycollectActivity;
import com.guojianyiliao.eryitianshi.View.activity.PersonaldataActivity;
import com.guojianyiliao.eryitianshi.View.activity.SetPageActivity;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMVideo;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.media.UMusic;
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

/**
 * 个人界面
 */
public class MyPageUserFragment extends Fragment {

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

    Gson gson;
    UserInfoLogin user ;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userid = SharedPreferencesTools.GetUsearId(getActivity(),"userSave","userId");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.a_fragment_mypage, container, false);
        ButterKnife.bind(this, view);
        gson = new Gson();
        String s = SharedPreferencesTools.GetUsearInfo(getActivity(), "userSave", "userInfo");
        user = gson.fromJson(s, UserInfoLogin.class);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        String name = user.getName();
        String gender = user.getGender();

        String icon = user.getIcon();
        if(TextUtils.isEmpty(icon)){
            ivIcon.setImageResource(R.drawable.default_icon);
        }else{
            ImageLoader.getInstance().loadImage(icon, new ImageLoadingListener() {
                @Override
                public void onLoadingStarted(String imageUri, View view) {
                    ivIcon.setImageResource(R.drawable.default_icon);
                }
                @Override
                public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

                }
                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    ivIcon.setImageBitmap(loadedImage);
                }
                @Override
                public void onLoadingCancelled(String imageUri, View view) {
                }
            });
        }


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
         showUmeng();
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
        startActivity(new Intent(getActivity(), YYGHHisActivity.class));
//        startActivity(new Intent(getContext(), ReservationActivity.class));
    }

    /**
     * 我的问诊
     */
    @OnClick(R.id.ll_inquiry)
    public void myInquiry() {
        startActivity(new Intent(getActivity(), imChatHistoryActivity.class));
    }

    /**
     * 用户修改信息
     */
    @OnClick(R.id.iv_icon)
    public void GetHttpHeadIcon() {
        startActivityForResult(new Intent(getContext(), PersonaldataActivity.class),0);
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
                Intent intent = new Intent(getActivity(), AllDoctorActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * 分享
     */
    private void showUmeng() {

        final SHARE_MEDIA[] displaylist = new SHARE_MEDIA[]
                {
                        SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE,
                        SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE, SHARE_MEDIA.SINA
                };
        //分享成功后显示的图片
        UMImage image = new UMImage(getActivity(),
                BitmapFactory.decodeResource(getResources(), R.drawable.app_log));
        //分享的视频
        UMVideo video = new UMVideo("http://video.sina.com.cn/p/sports/cba/v/2013-10-22/144463050817.html");
        //分享的音乐
        UMusic music = new UMusic("http://music.huoxing.com/upload/20130330/1364651263157_1085.mp3");
        music.setTitle("儿医天使（儿科名医）");
        music.setThumb(new UMImage(getActivity(), "http://www.umeng.com/images/pic/social/chart_1.png"));

        UMWeb web = new UMWeb("http://www.eryitianshi.com/index.html");
        web.setTitle("儿医天使（儿科名医）");
        web.setDescription("一键登录，三秒问诊，三甲儿科医生在线解决了我的问题，你也来试试");
        web.setThumb(image);

        new ShareAction(getActivity()).setDisplayList(displaylist)
                .withMedia(web)
                .setListenerList(new umShareListener())
                .open();
    }
    class umShareListener implements UMShareListener {

        @Override
        public void onCancel(SHARE_MEDIA arg0) {
            // TODO Auto-generated method stub
//            Log.e("ArticleDetailActivity","onCancel  arg0 = "+arg0);
//            Toast.makeText(getActivity(), " 分享取消了", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(SHARE_MEDIA arg0, Throwable arg1) {
            // TODO Auto-generated method stub
//            Log.e("ArticleDetailActivity","onError  arg0 = "+arg0 +"  ,   arg1 = "+arg1.getMessage());
            Toast.makeText(getActivity()," 分享失败,请检查配置", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onStart(SHARE_MEDIA share_media) {
//            Log.e("ArticleDetailActivity","onStart  share_media = "+share_media);
        }

        @Override
        public void onResult(SHARE_MEDIA arg0) {
            // TODO Auto-generated method
//            Log.e("ArticleDetailActivity","onResult  arg0 = "+arg0);
//            Toast.makeText(getActivity(), " 分享成功啦", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get( getActivity() ).onActivityResult( requestCode, resultCode, data);
        if(requestCode == 0){
            if(resultCode == Activity.RESULT_OK){
                String s = SharedPreferencesTools.GetUsearInfo(getActivity(), "userSave", "userInfo");
                user = gson.fromJson(s, UserInfoLogin.class);
                ImageLoader.getInstance().displayImage(user.getIcon(),ivIcon);
                tvName.setText(user.getName());
            }
        }
    }

}
