package com.guojianyiliao.eryitianshi.page.fragment;

import android.Manifest;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.guojianyiliao.eryitianshi.Data.Http_data;
import com.guojianyiliao.eryitianshi.Data.User_Http;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.MyLogcat;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.ToolUtils;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.UIUtils;
import com.guojianyiliao.eryitianshi.R;
import com.guojianyiliao.eryitianshi.Utils.SharedPsaveuser;
import com.guojianyiliao.eryitianshi.View.activity.InquiryrecordActivity;
import com.guojianyiliao.eryitianshi.View.activity.LeadActivity;
import com.guojianyiliao.eryitianshi.View.activity.MyCashCouponsActivity;
//import com.guojianyiliao.eryitianshi.View.activity.MyDoctorActivity;
//import com.guojianyiliao.eryitianshi.View.activity.MycollectActivity;
import com.guojianyiliao.eryitianshi.View.activity.PersonaldataActivity;
import com.guojianyiliao.eryitianshi.View.activity.ReservationActivity;
import com.guojianyiliao.eryitianshi.View.activity.SetPageActivity;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by Administrator on 2016/8/26 0026.
 */
public class MypageFragment extends Fragment implements EasyPermissions.PermissionCallbacks {
    View view;
    private RelativeLayout rl_set;
    private RelativeLayout rl_mycollect, rl_myreservation, rl_personaldata, rl_mydpctor, rl_inquiryrecord, rl_MyCashCoupons, rl_mypage_share;
    private CircleImageView iv_ico;
    private TextView tv_name;
    SharedPsaveuser sp;
    Button btn_user_login_form;
    private Dialog setHeadDialog;
    private View dialogView;
    private UMWeb web;
    private String[] permss;
    // private OnekeyShare oks;


    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_mypage, null);

        try {
            //ShareSDK.initSDK(getContext());
            findView();
            sp = new SharedPsaveuser(getContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }

    private void findView() {
        rl_set = (RelativeLayout) view.findViewById(R.id.rl_set);
        rl_mycollect = (RelativeLayout) view.findViewById(R.id.rl_mycollect);
        rl_myreservation = (RelativeLayout) view.findViewById(R.id.rl_myreservation);
        rl_personaldata = (RelativeLayout) view.findViewById(R.id.rl_personaldata);
        rl_mydpctor = (RelativeLayout) view.findViewById(R.id.rl_mydpctor);
        rl_inquiryrecord = (RelativeLayout) view.findViewById(R.id.rl_inquiryrecord);
        iv_ico = (CircleImageView) view.findViewById(R.id.iv_icon);
        tv_name = (TextView) view.findViewById(R.id.tv_name);
        rl_MyCashCoupons = (RelativeLayout) view.findViewById(R.id.rl_MyCashCoupons);
        btn_user_login_form = (Button) view.findViewById(R.id.btn_user_login_form);
        rl_mypage_share = (RelativeLayout) view.findViewById(R.id.rl_mypage_share);
        rl_set.setOnClickListener(listerer);
        rl_mycollect.setOnClickListener(listerer);
        rl_myreservation.setOnClickListener(listerer);
        rl_personaldata.setOnClickListener(listerer);
        rl_mydpctor.setOnClickListener(listerer);
        rl_inquiryrecord.setOnClickListener(listerer);
        rl_MyCashCoupons.setOnClickListener(listerer);
        rl_mypage_share.setOnClickListener(listerer);

    }

    @Override
    public void onStart() {
        super.onStart();


        try {


            if ((User_Http.user.getIcon() == null || User_Http.user.getIcon().equals("")) && (sp.getTag().getIcon() == null || sp.getTag().getIcon().equals(""))) {

                iv_ico.setImageResource(R.drawable.default_icon);


            } else if (User_Http.user.getIcon() == null || User_Http.user.getIcon().equals("")) {

                ImageLoader.getInstance().displayImage("file:///" + sp.getTag().getIcon(), iv_ico);


            } else {


                ImageLoader.getInstance().displayImage(User_Http.user.getIcon(), iv_ico);
            }


            if (User_Http.user.getName() == null) {
                tv_name.setText(sp.getTag().getName());
            } else {
                tv_name.setText(User_Http.user.getName());
            }

            if (sp.getTag().getPassword() != null) {
                btn_user_login_form.setText("注册登录用户");

            } else {
                btn_user_login_form.setText("一键登录用户");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private View.OnClickListener listerer = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.rl_set:

                    Intent intent = new Intent(getContext(), SetPageActivity.class);

                    startActivity(intent);
                    break;
                case R.id.rl_mycollect:

//                    Intent intent1 = new Intent(getContext(), MycollectActivity.class);
//
//                    startActivity(intent1);
                    break;
                case R.id.rl_myreservation:

                    Intent intent2 = new Intent(getContext(), ReservationActivity.class);

                    startActivity(intent2);
                    break;
                case R.id.rl_personaldata:

                    Intent intent3 = new Intent(getContext(), PersonaldataActivity.class);

                    startActivity(intent3);
                    break;
                case R.id.rl_mydpctor:

//                    Intent intent4 = new Intent(getContext(), MyDoctorActivity.class);
//
//                    startActivity(intent4);
                    break;
                case R.id.rl_inquiryrecord:


                    Intent intent5 = new Intent(getContext(), InquiryrecordActivity.class);

                    startActivity(intent5);
                    break;

                case R.id.rl_MyCashCoupons:
                    Intent intent6 = new Intent(getContext(), MyCashCouponsActivity.class);
                    intent6.putExtra("type", "mypage");
                    startActivity(intent6);
                    break;

                case R.id.rl_mypage_share:

                    // showShare();
                    // showUmeng();
                    // MyLogcat.jLog().e("onclick：");
                    // rtfindallhot();
                    // retorfit();
                    // retorfitpost();
                    // rxjava();
                    //QQLogin();
                    //WEIXIN();
                    //SINA();
                 //   hotThinker();


                    break;

            }
        }
    };

    private void hotThinker() {
        String str = "123";
        MyLogcat.jLog().e("BugClass get String length:" + str.length());
        ToolUtils.showToast(UIUtils.getContext(),"这个修改的bug",2);
    }

    private void SINA() {
        UMShareAPI.get(getActivity()).getPlatformInfo(getActivity(), SHARE_MEDIA.SINA, new UMAuthListener() {
            @Override
            public void onStart(SHARE_MEDIA share_media) {

            }

            @Override
            public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                MyLogcat.jLog().e("onComplete:"+map.toString());
                MyLogcat.jLog().e("onComplete name :"+map.get("name"));


            }

            @Override
            public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {

            }

            @Override
            public void onCancel(SHARE_MEDIA share_media, int i) {

            }
        });
    }

    private void WEIXIN() {
         UMShareAPI.get(getActivity()).getPlatformInfo(getActivity(), SHARE_MEDIA.WEIXIN, new UMAuthListener() {
             @Override
             public void onStart(SHARE_MEDIA share_media) {

             }

             @Override
             public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                 MyLogcat.jLog().e("onComplete:"+map.toString());
                 MyLogcat.jLog().e("onComplete name :" + map.get("name"));
                 //{unionid=ox14DvzKcSVld-Cpzpb4UELFfkx8, screen_name=summer, city=海淀, accessToken=zQ7rFfFrf4pyesaTeX4gjHE_ugfvRFEXoIbiofkdRFWS5NTi1SNVJk9IqzM1k_sTJOpb6-BRwyxESmDHAEE0qPDqCge73UfeBNSTt5uWOyE, refreshToken=JTSDm4Ww2sxCIXkj2JNHN4fTGkWBmTDZ8q57uHejOID8Z55YJDFY6DsD_ORuelmIjqGo3PnQZisWGMYm5WRKB4CD13HdJ9HV0kgWKHs0TFk, gender=男, province=北京, openid=omwn5v_MCIylOhveXvvNsgYVFm_M, profile_image_url=http://wx.qlogo.cn/mmopen/ajNVdqHZLLDxSiap7uic4rIU0cwKv3vN69DNevVuB8O4cGGJI4WnUtl4t32JBe9Tq7ibAytO55r2swND2Tpo6q6iaw/0, country=中国, access_token=zQ7rFfFrf4pyesaTeX4gjHE_ugfvRFEXoIbiofkdRFWS5NTi1SNVJk9IqzM1k_sTJOpb6-BRwyxESmDHAEE0qPDqCge73UfeBNSTt5uWOyE, iconurl=http://wx.qlogo.cn/mmopen/ajNVdqHZLLDxSiap7uic4rIU0cwKv3vN69DNevVuB8O4cGGJI4WnUtl4t32JBe9Tq7ibAytO55r2swND2Tpo6q6iaw/0, name=summer, uid=ox14DvzKcSVld-Cpzpb4UELFfkx8, expiration=1490265297665, language=zh_CN, expires_in=1490265297665}
                   //  03-23 16:34:58.159 9748-9748/? E/[AppNa
             }

             @Override
             public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {

             }

             @Override
             public void onCancel(SHARE_MEDIA share_media, int i) {

             }
         });

    }


    private void QQLogin() {

        UMShareAPI umShareAPI = UMShareAPI.get(getActivity());
        umShareAPI.getPlatformInfo(getActivity(), SHARE_MEDIA.QQ, new UMAuthListener() {
            @Override
            public void onStart(SHARE_MEDIA share_media) {
                MyLogcat.jLog().e("onStart:");
            }

            @Override
            public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                MyLogcat.jLog().e("onComplete:" + map.toString());
                MyLogcat.jLog().e("name:" + map.get("name") + "//" + map.get("uid"));
                //{unionid=, is_yellow_vip=0, screen_name=summer, msg=, vip=0,
                    //    city=, accessToken=1B88D04E81555F8A877AC1FA1A617D00, gender=男,
                  //      province=, is_yellow_year_vip=0, openid=24DE0A4DC828969520D2B78848904521,
                 //       yellow_vip_level=0, profile_image_url=http://q.qlogo.cn/qqapp/1105577776/24DE0A4DC828969520D2B78848904521/100,
                  //  access_token=1B88D04E81555F8A877AC1FA1A617D00, iconurl=http://q.qlogo.cn/qqapp/1105577776/24DE0A4DC828969520D2B78848904521/100,
                 //   name=summer, uid=24DE0A4DC828969520D2B78848904521, expiration=1498032516493, expires_in=1498032516493, level=0, ret=0}
            }

            @Override
            public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
                MyLogcat.jLog().e("onError:");
            }

            @Override
            public void onCancel(SHARE_MEDIA share_media, int i) {
                MyLogcat.jLog().e("onCancel:");
            }
        });


    }

    /*private void rxjava() {
        Retrofit rt = new Retrofit.Builder()
                .baseUrl(Http_data.http_data)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        GetService getService = rt.create(GetService.class);
       // Observable<List<FillHot>> ob = getService.();
        MyLogcat.jLog().e("ob:" + UIUtils.isRunOnUIThread() + "//");
        ob.subscribeOn(Schedulers.io())  // 网络请求切换在io线程中调用
                .unsubscribeOn(Schedulers.io())// 取消网络请求放在io线程
                .observeOn(AndroidSchedulers.mainThread())// 观察后放在主线程调用
                .subscribe(new Subscriber<List<FillHot>>() {
                    @Override
                    public void onCompleted() {
                        MyLogcat.jLog().e("onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        MyLogcat.jLog().e("onError");
                    }

                    @Override
                    public void onNext(List<FillHot> fillHots) {
                        MyLogcat.jLog().e("onnext:" + UIUtils.isRunOnUIThread());
                        for (FillHot ht : fillHots) {
                            MyLogcat.jLog().e("for:" + ht.getTitle());
                        }
                    }
                });


                    *//*    <Doctor>() {
                    @Override
                    public void onResponse(retrofit2.Call<Doctor> call, Response<Doctor> response) {
                        MyLogcat.jLog().e("onResponse:" + response.body().toString());
                        if (response.isSuccessful()) {
                            MyLogcat.jLog().e("getTitle:" + response.body().getTitle());
                        }
                    }

                    @Override
                    public void onFailure(retrofit2.Call<Doctor> call, Throwable t) {
                        MyLogcat.jLog().e("onFailure:");
                    }
                });*//*
    }*/

   /* private void retorfitpost() {
        //创建一个上游 Observable：
        Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onComplete();
            }
        });
        //创建一个下游 Observer
        Observer<Integer> observer = new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                MyLogcat.jLog().e("subscribe");
            }

            @Override
            public void onNext(Integer value) {
                MyLogcat.jLog().e("isuithreed:"+UIUtils.isRunOnUIThread());
                MyLogcat.jLog().e( "" + value);
            }

            @Override
            public void onError(Throwable e) {
                MyLogcat.jLog().e( "error");
            }

            @Override
            public void onComplete() {
                MyLogcat.jLog().e("complete");
            }
        };
        //建立连接
        observable.subscribe(observer);
    }*/


   /* private void retorfit() {
        try {
            Retrofit rt = new Retrofit.Builder()
                    .baseUrl(Http_data.http_data)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            GetService getService = rt.create(GetService.class);
            getService.get("2")
                    .enqueue(new Callback<DoctorRxBean>() {
                        @Override
                        public void onResponse(retrofit2.Call<DoctorRxBean> call, Response<DoctorRxBean> response) {
                            MyLogcat.jLog().e("onResponse:" + response.body().toString());
                            if (response.isSuccessful()) {
                                MyLogcat.jLog().e("getTitle:" + response.body().title);
                                MyLogcat.jLog().e("getname:" + response.body().name);
                            }
                        }

                        @Override
                        public void onFailure(retrofit2.Call<DoctorRxBean> call, Throwable t) {
                            MyLogcat.jLog().e("onFailure:");
                        }
                    });
        }catch (Exception e){
            MyLogcat.jLog().e("-----------"+e.getMessage());
        }
        }*/

    /**
     * retrofit   /** Log.e("fail:", "");Log.e("succes:", "");
     */
    JSONArray ja;

    /*private void rtfindallhot() {
        Retrofit rt = new Retrofit.Builder()
                .baseUrl(Http_data.http_data)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GetService getService = rt.create(GetService.class);
        getService.getFillhot()
                .enqueue(new Callback<List<FillHot>>() {
                    @Override
                    public void onResponse(retrofit2.Call<List<FillHot>> call, Response<List<FillHot>> response) {
                        UIUtils.runOnUIThread(new Runnable() {
                            @Override
                            public void run() {
                                int mainThreadId = UIUtils.getMainThreadId();
                                boolean runOnUIThread = UIUtils.isRunOnUIThread();
                                MyLogcat.jLog().e("mainThreadId:" + mainThreadId);
                                MyLogcat.jLog().e("runOnUIThread:" + runOnUIThread);

                            }
                        });

                        try {
                            ja = new JSONArray(response.toString());
                            for (int i = 0; i < ja.length(); i++) {
                                String title = ja.getJSONObject(i).getString("title");
                                MyLogcat.jLog().e("JsonArray title:" + title);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (response.isSuccessful()) {
                            List<FillHot> body = response.body();
                            for (FillHot fl : body) {
                                MyLogcat.jLog().e("for:" + fl.getTitle());
                            }
                            for (int i = 0; i < body.size(); i++) {
                                MyLogcat.jLog().e("第:" + i + body.get(i).getTitle());

                            }
                        }

                    }

                    @Override
                    public void onFailure(retrofit2.Call<List<FillHot>> call, Throwable t) {
                        MyLogcat.jLog().e("onFailure:");
                    }
                });

    }*/

    private void showUmeng() {

        permss = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(getActivity(), permss)) {
            MyLogcat.jLog().e("已经有这个权限了");
            web = new UMWeb("http://www.eryitianshi.com");
            web.setTitle("儿医天使（儿科名医）");//标题
            web.setThumb(new UMImage(getActivity(), R.drawable.app_log));  //缩略图
            web.setDescription("一键登录，三秒问诊，三甲儿科医生在线解决了我的问题，你也来试试");//描述
        } else {
            EasyPermissions.requestPermissions(getActivity(), "",
                    200, permss);
            MyLogcat.jLog().e("正在申请");
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


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        if (requestCode == 200) {
            web = new UMWeb("http://www.eryitianshi.com");
            web.setTitle("儿医天使");//标题
            web.setThumb(new UMImage(MypageFragment.this.getActivity(), R.drawable.app_log));  //缩略图
            web.setDescription("http://www.eryitianshi.com");//描述
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        MyLogcat.jLog().e("失败 onPermissionsDenied 回调");
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
                .addParams("userId", sp.getTag().getId() + "")
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

    private void send() {
        Intent intent = new Intent(getContext(), LeadActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                getContext(), 0, intent,
                PendingIntent.FLAG_CANCEL_CURRENT);

        Notification noti = new NotificationCompat.Builder(getContext())
                .setSmallIcon(R.drawable.image_emoticon25)
                .setContentText("骚年，该吃药了")
                .setContentTitle("一条咸鱼干")
                .setTicker("你有一包咸鱼干到了")
                .setContentIntent(pendingIntent)
                .build();

        NotificationManager mNotificationManager = (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);
        noti.defaults = Notification.DEFAULT_ALL;
        mNotificationManager.notify(0, noti);

    }


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
//                Intent intent = new Intent(getActivity(), InquiryActivity.class);
//                startActivity(intent);
            }
        });
    }
}
