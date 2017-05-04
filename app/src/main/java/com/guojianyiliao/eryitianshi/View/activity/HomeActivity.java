package com.guojianyiliao.eryitianshi.View.activity;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.guojianyiliao.eryitianshi.Data.Http_data;
import com.guojianyiliao.eryitianshi.Data.User_Http;
import com.guojianyiliao.eryitianshi.Data.entity.Chatcontent;
import com.guojianyiliao.eryitianshi.Data.entity.Inquiryrecord;
import com.guojianyiliao.eryitianshi.Data.entity.Update;
import com.guojianyiliao.eryitianshi.Data.entity.User;
import com.guojianyiliao.eryitianshi.Data.entity.VersionsUpdate;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.MyLogcat;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.SpUtils;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.UIUtils;
import com.guojianyiliao.eryitianshi.R;
import com.guojianyiliao.eryitianshi.Utils.IListener;
import com.guojianyiliao.eryitianshi.Utils.ListenerManager;
import com.guojianyiliao.eryitianshi.Utils.MyBaseActivity;
import com.guojianyiliao.eryitianshi.Utils.ServiceOne;
import com.guojianyiliao.eryitianshi.Utils.ServiceTwo;
import com.guojianyiliao.eryitianshi.Utils.SharedPsaveuser;
import com.guojianyiliao.eryitianshi.Utils.Version_numberSP;
import com.guojianyiliao.eryitianshi.Utils.db.ChatpageDao;
import com.guojianyiliao.eryitianshi.Utils.db.InquiryrecordDao;
import com.guojianyiliao.eryitianshi.page.fragment.ConsultingFragment;
import com.guojianyiliao.eryitianshi.page.fragment.EncyclopediaFragment;
import com.guojianyiliao.eryitianshi.page.fragment.HomepageFragment;
import com.guojianyiliao.eryitianshi.page.fragment.MypageFragment;
import com.tencent.bugly.crashreport.CrashReport;
import com.umeng.socialize.UMShareAPI;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.content.ImageContent;
import cn.jpush.im.android.api.content.TextContent;
import cn.jpush.im.android.api.event.MessageEvent;
import cn.jpush.im.android.api.event.NotificationClickEvent;
import cn.jpush.im.android.api.model.Message;
import cn.jpush.im.api.BasicCallback;
import okhttp3.Call;

public class HomeActivity extends MyBaseActivity implements View.OnClickListener, IListener {
    FragmentManager fm;
    FragmentTransaction ft;
    private RadioButton rb_encyclopedia, rb_lectureroom, rb_mypage, rb_diagnosis;
    private RadioGroup radioGroup_right, radioGroup_left;
    private FrameLayout framelayout, fl_registration;
    private EncyclopediaFragment encyclopediaFragment;
    private MypageFragment mypageFragment;
    //    private InquiryFragment inquiryFragment;
    private ConsultingFragment consultingFragment;
    private ImageView iv_inquiry;
    ChatpageDao db;
    Set<User> set;
    File sdcardTempFile;
    File audioFile;
    Context context;
    private Dialog setHeadDialog;
    private View dialogView;
    public static HomeActivity text_homeactivity;

    Animation translateAnimation;

    Animation translateAnimation1;
    int fileSize;

    int downLoadFileSize;
    File updateaudioFile;
    File updatesdcardTempFile;
    ProgressBar pb_update_load;

    String updateurl;

    Version_numberSP version_numberSp;

    VersionsUpdate versionsUpdate;

    Update update;

    HomepageFragment homepageFragment;

    SharedPsaveuser sp;
    User user;
    Integer id;
    String name;
    String phone;
    String gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //GuideDailog();//测试
        try {
            Intent serviceOne = new Intent();
            serviceOne.setClass(HomeActivity.this, ServiceOne.class);
            startService(serviceOne);

            Intent serviceTwo = new Intent();
            serviceTwo.setClass(HomeActivity.this, ServiceTwo.class);
            startService(serviceTwo);


            ListenerManager.getInstance().registerListtener(this);


            version_numberSp = new Version_numberSP(this);


            if (version_numberSp.getversionNumber() == null || version_numberSp.getversionNumber().equals("") || (!version_numberSp.getversionNumber().trim().equals(Http_data.version_number))) {
                version_numberSp.setspversionNumber(Http_data.version_number);
            }

            text_homeactivity = this;


            JMessageClient.registerEventReceiver(this);
            JMessageClient.setNotificationMode(JMessageClient.NOTI_MODE_DEFAULT);
            db = new ChatpageDao(this);
            findView();
            init();


            Chatcontent chatcontent = new Chatcontent(null, 0L, null, null, null, User_Http.user.getPhone());
            db.addchatcont(chatcontent);
            sp = new SharedPsaveuser(HomeActivity.this);

            context = getBaseContext();
            DisplayMetrics dm = new DisplayMetrics();
            WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            windowManager.getDefaultDisplay().getMetrics(dm);


            if (sp.getTag().getIcon() == null && User_Http.user.getIcon() != null) {

                saveicon();
            }
            updateaudioFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/text/update/");
            updateaudioFile.mkdirs();

            jmessage();

            // updatedetection();

            if (Http_data.giveCashState == 2) {
                giveCashCoupons();
                Http_data.giveCashState = 1;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void spStorage() {
        /*if (User_Http.user.getId() != null) {
            id = User_Http.user.getId();
            name = User_Http.user.getName();
            phone = User_Http.user.getPhone();
            gender = User_Http.user.getGender();
            sp.setspUser(id, phone, name, gender);
        }*/
        String userid = SpUtils.getInstance(this).get("Userid", null);
        String phone = SpUtils.getInstance(this).get("phone", null);
        CrashReport.putUserData(UIUtils.getContext(), "userkey", userid + "phone:" + phone);/**carsh 上报 ID*/
        MyLogcat.jLog().e("user info id:" + userid + "/" + "phone:" + phone);
    }

    private Dialog dialog;
    private Dialog dialognext;
    private Dialog dialognextnext;

    /**
     * 引导页面
     */
    private void GuideDailog() {
        dialog = new Dialog(HomeActivity.this, R.style.GuideHelperDialog);
        dialog.show();
        View viewDialog = UIUtils.getinflate(R.layout.dialog_guide_helper);
        ImageButton ivb_next = (ImageButton) viewDialog.findViewById(R.id.ivb_next);
        ivb_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogshownext();
            }
        });
        int width = UIUtils.getDisplay(HomeActivity.this).getWidth();
        int height = UIUtils.getDisplay(HomeActivity.this).getHeight();
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
        dialognext = new Dialog(HomeActivity.this, R.style.GuideHelperDialog);
        dialognext.show();
        View viewDialog = UIUtils.getinflate(R.layout.dialog_guide_helper_next);
        int width = UIUtils.getDisplay(HomeActivity.this).getWidth();
        int height = UIUtils.getDisplay(HomeActivity.this).getHeight();
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
        dialognextnext = new Dialog(HomeActivity.this, R.style.GuideHelperDialog);
        dialognextnext.show();
        View viewDialog = UIUtils.getinflate(R.layout.dialog_guide_helper_next_next);
        int width = UIUtils.getDisplay(HomeActivity.this).getWidth();
        int height = UIUtils.getDisplay(HomeActivity.this).getHeight();
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


    private void updatedetection() {
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

                            }

                            @Override
                            public void onResponse(String response, int id) {
                                update = gson.fromJson(response, Update.class);
                                try {
                                    if (update.getVersion().trim().equals(version_numberSp.getversionNumber().trim())) {

                                    } else {
                                        updatedialog();
                                    }
                                } catch (Exception n) {

                                }

                            }
                        });
            }
        });

        thread.start();
    }


    private void updatedialog() {

        setHeadDialog = new Dialog(this, R.style.GuideHelperDialog);
        setHeadDialog.show();
        dialogView = View.inflate(getApplicationContext(), R.layout.home_update_dialog, null);
        setHeadDialog.setCanceledOnTouchOutside(false);
        setHeadDialog.getWindow().setContentView(dialogView);
        WindowManager.LayoutParams lp = setHeadDialog.getWindow().getAttributes();
        setHeadDialog.getWindow().setAttributes(lp);

        updatedialogclick();
    }


    private void updatedialogclick() {
        final TextView tv_update = (TextView) dialogView.findViewById(R.id.tv_update);
        pb_update_load = (ProgressBar) dialogView.findViewById(R.id.pb_update_load);
        final RelativeLayout rl_update_confirm = (RelativeLayout) dialogView.findViewById(R.id.rl_update_confirm);
        final RelativeLayout rl_update_cancel = (RelativeLayout) dialogView.findViewById(R.id.rl_update_cancel);
        final TextView tv_update_confirm = (TextView) dialogView.findViewById(R.id.tv_update_confirm);
        final TextView tv_update_cancel = (TextView) dialogView.findViewById(R.id.tv_update_cancel);
        LinearLayout linearlayout = (LinearLayout) dialogView.findViewById(R.id.linearlayout);
        TextView tv_version = (TextView) dialogView.findViewById(R.id.tv_version);
        TextView tv_updateLog = (TextView) dialogView.findViewById(R.id.tv_updateLog);


        tv_version.setText("最新版本：" + update.getVersion());

        tv_updateLog.setText(update.getUpdateLog());

        linearlayout.setOnClickListener(null);

        rl_update_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                versionUpdate();
                tv_update.setText("下载中，请稍等...");
                rl_update_confirm.setOnClickListener(null);
                tv_update_confirm.setTextColor(getResources().getColor(R.color.xmgray));
                rl_update_cancel.setOnClickListener(null);
                tv_update_cancel.setTextColor(getResources().getColor(R.color.xmgray));

                setHeadDialog.setOnKeyListener(keylistener);
            }
        });


        rl_update_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setHeadDialog.dismiss();
            }
        });

    }


    DialogInterface.OnKeyListener keylistener = new DialogInterface.OnKeyListener() {
        public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
                return true;
            } else {
                return false;
            }
        }
    };


    private void versionUpdate() {


        updateurl = update.getDownloadPath();


        new Thread() {
            public void run() {
                try {
                    down_file(updateurl);

                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }.start();

    }

    //？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？
    public void down_file(String url) throws IOException {
        updatesdcardTempFile = new File(updateaudioFile, "textupdate.apk");

        URL myURL = new URL(url);
        URLConnection conn = myURL.openConnection();
        conn.connect();
        InputStream is = conn.getInputStream();
        this.fileSize = conn.getContentLength();

        if (this.fileSize <= 0) {
            throw new RuntimeException("无法获知文件大小 ");
        }

        if (is == null) {
            throw new RuntimeException("下载文件为空");
        }

        FileOutputStream fos = new FileOutputStream(updatesdcardTempFile);

        byte buf[] = new byte[1024];
        downLoadFileSize = 0;
        sendMsg(0);
        do {

            int numread = is.read(buf);
            if (numread == -1) {
                break;
            }
            fos.write(buf, 0, numread);
            downLoadFileSize += numread;

            sendMsg(1);
        } while (true);
        sendMsg(2);
        try {
            is.close();
        } catch (Exception ex) {
            Toast.makeText(HomeActivity.this, "下载错误", Toast.LENGTH_SHORT).show();
        }

    }


    private void sendMsg(int flag) {
        android.os.Message msg = new android.os.Message();
        msg.what = flag;
        handler.sendMessage(msg);
    }


    public static boolean isServiceWorked(Context context, String serviceName) {
        ActivityManager myManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ArrayList<ActivityManager.RunningServiceInfo> runningService = (ArrayList<ActivityManager.RunningServiceInfo>) myManager.getRunningServices(Integer.MAX_VALUE);
        for (int i = 0; i < runningService.size(); i++) {
            if (runningService.get(i).service.getClassName().toString().equals(serviceName)) {
                return true;
            }
        }
        return false;
    }


    private void openFile(File file) {
        Intent intent = new Intent();

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        intent.setAction(android.content.Intent.ACTION_VIEW);

        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");

        startActivity(intent);
    }


    private android.os.Handler handler = new android.os.Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            if (!Thread.currentThread().isInterrupted()) {
                switch (msg.what) {
                    case 0:
                        pb_update_load.setMax(fileSize);
                    case 1:
                        pb_update_load.setProgress(downLoadFileSize);

                        break;
                    case 2:

                        Toast.makeText(HomeActivity.this, "文件下载完成", Toast.LENGTH_SHORT).show();
                        setHeadDialog.dismiss();
                        try {
                            openFile(updatesdcardTempFile);
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(HomeActivity.this, "打开更新文件失败", Toast.LENGTH_SHORT).show();
                        }

                        break;

                    case -1:

                        Toast.makeText(HomeActivity.this, "错误", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
            super.handleMessage(msg);
        }
    };

    private void saveicon() {
        new Thread() {
            public void run() {
                try {
                    audioFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/text/icon/");
                    audioFile.mkdirs();
                    sdcardTempFile = File.createTempFile(".icon", ".jpg", audioFile);
                    String urlPath = User_Http.user.getIcon();
                    URL url = new URL(urlPath);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setConnectTimeout(6 * 1000);
                    if (conn.getResponseCode() != 200) throw new RuntimeException("请求url失败");
                    InputStream inSream = conn.getInputStream();

                    readAsFile(inSream, new File(String.valueOf(sdcardTempFile)));
                    String icon = sdcardTempFile.getAbsolutePath();

                    sp.setUsericon(icon);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }


    public static void readAsFile(InputStream inSream, File file) throws Exception {
        FileOutputStream outStream = new FileOutputStream(file);
        byte[] buffer = new byte[1024];
        int len = -1;
        while ((len = inSream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        outStream.close();
        inSream.close();

    }


    @Override
    protected void onStart() {
        super.onStart();

        try {

            spStorage();

            final ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);

            ActivityManager.MemoryInfo info = new ActivityManager.MemoryInfo();

            activityManager.getMemoryInfo(info);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    protected void onDestroy() {
        JMessageClient.unRegisterEventReceiver(this);

        db.closedb();

        super.onDestroy();
    }

    private void jmessage() {
        //sp.getTag().getId();
        phone = SpUtils.getInstance(this).get("phone", null);

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

    /***
     * JMessageClient.NOTI_MODE_DEFAULT 显示通知，有声音，有震动。
     * JMessageClient.NOTI_MODE_NO_SOUND 显示通知，无声音，有震动。
     * JMessageClient.NOTI_MODE_NO_VIBRATE 显示通知，有声音，无震动。
     * JMessageClient.NOTI_MODE_SILENCE 显示通知，无声音，无震动。
     * JMessageClient.NOTI_MODE_NO_NOTIFICATION 不显示通知。
     */


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void onEventMainThread(MessageEvent event) {
        Message msg = event.getMessage();
        switch (msg.getContentType()) {
            case text:
                TextContent textContent = (TextContent) msg.getContent();
                String content = textContent.getText();
                String username = msg.getFromID();

                Date dt = new Date();
                Long time = dt.getTime();
                Chatcontent chatcontent = new Chatcontent("2" + content, time, null, null, username, sp.getTag().getPhone());
                db.addchatcont(chatcontent);
                break;
            case image:

                ImageContent imageContent = (ImageContent) msg.getContent();
                String mfile = imageContent.getLocalPath();

                String file = imageContent.getLocalThumbnailPath();

                Date dt1 = new Date();
                Long time1 = dt1.getTime();

                chatcontent = new Chatcontent("2*2", time1, file, file, msg.getTargetID(), sp.getTag().getPhone());
                db.addchatcont(chatcontent);
        }
    }


    public void onEvent(NotificationClickEvent event) {
        InquiryrecordDao db = new InquiryrecordDao(this);
        Message msg = event.getMessage();
        Inquiryrecord inquiryrecord = db.chatfinddoctor(msg.getTargetID());

        Intent intent = new Intent(HomeActivity.this, ChatpageActivity.class);
        intent.putExtra("doctorID", inquiryrecord.getId());
        intent.putExtra("name", inquiryrecord.getDoctorname());
        intent.putExtra("icon", inquiryrecord.getDoctoricon());
        intent.putExtra("username", inquiryrecord.getDoctorid());
        startActivity(intent);
    }


    private void findView() {
        rb_encyclopedia = (RadioButton) findViewById(R.id.rb_encyclopedia);
        framelayout = (FrameLayout) findViewById(R.id.framelayout);
        rb_lectureroom = (RadioButton) findViewById(R.id.rb_lectureroom);
        rb_mypage = (RadioButton) findViewById(R.id.rb_mypage);
        rb_diagnosis = (RadioButton) findViewById(R.id.rb_diagnosis);
        radioGroup_left = (RadioGroup) findViewById(R.id.radioGroup_left);
        radioGroup_right = (RadioGroup) findViewById(R.id.radioGroup_right);
        iv_inquiry = (ImageView) findViewById(R.id.iv_inquiry);
        fl_registration = (FrameLayout) findViewById(R.id.fl_registration);
        rb_encyclopedia.setChecked(true);
        rb_encyclopedia.setOnClickListener(this);
        rb_lectureroom.setOnClickListener(this);
        rb_mypage.setOnClickListener(this);
        rb_diagnosis.setOnClickListener(this);
        iv_inquiry.setOnClickListener(inquirylistener);
        fl_registration.setOnClickListener(listener);

    }


    private void init() {
        fm = getSupportFragmentManager();

        rb_encyclopedia.performClick();
    }


    private void hideAllFragment(FragmentTransaction fragmentTransaction) {
        if (encyclopediaFragment != null) fragmentTransaction.hide(encyclopediaFragment);

        if (homepageFragment != null) fragmentTransaction.hide(homepageFragment);

        if (mypageFragment != null) fragmentTransaction.hide(mypageFragment);

        if (consultingFragment != null) fragmentTransaction.hide(consultingFragment);
    }


    @Override
    public void onClick(View v) {
        ft = fm.beginTransaction();
        hideAllFragment(ft);
        switch (v.getId()) {
            case R.id.rb_encyclopedia:
                radioGroup_right.clearCheck();
                fl_registration.setVisibility(View.GONE);

                if (homepageFragment == null) {
                    homepageFragment = new HomepageFragment();
                    ft.add(R.id.framelayout, homepageFragment);
                } else {
                    ft.show(homepageFragment);
                }

                break;

            case R.id.rb_lectureroom:
                fl_registration.setVisibility(View.GONE);
                radioGroup_left.clearCheck();
                if (encyclopediaFragment == null) {
                    encyclopediaFragment = new EncyclopediaFragment();
                    ft.add(R.id.framelayout, encyclopediaFragment);
                } else {
                    ft.show(encyclopediaFragment);
                }


                break;


            case R.id.rb_mypage:
                fl_registration.setVisibility(View.GONE);
                radioGroup_left.clearCheck();
                if (mypageFragment == null) {
                    mypageFragment = new MypageFragment();

                    ft.add(R.id.framelayout, mypageFragment);
                } else {
                    ft.show(mypageFragment);
                }
                break;


            case R.id.rb_diagnosis:
                fl_registration.setVisibility(View.GONE);
                radioGroup_right.clearCheck();
                if (consultingFragment == null) {
                    consultingFragment = new ConsultingFragment();
                    ft.add(R.id.framelayout, consultingFragment);
                } else {
                    ft.show(consultingFragment);
                }
                break;
        }
        ft.commit();

    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(HomeActivity.this, RegistrationAtivity.class);
            startActivity(intent);
        }
    };

    private View.OnClickListener inquirylistener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            serveshowDialog();
        }
    };


    public void serveshowDialog() {

        setHeadDialog = new Dialog(this, R.style.CustomDialog);

        setHeadDialog.show();
        dialogView = View.inflate(this, R.layout.inquiry_popup_dialog, null);
        setHeadDialog.getWindow().setContentView(dialogView);
        WindowManager.LayoutParams lp = setHeadDialog.getWindow().getAttributes();

        Window window = setHeadDialog.getWindow();

        window.setWindowAnimations(0);

        setHeadDialog.getWindow().setAttributes(lp);

        serveonclick();

    }

    private void serveonclick() {
        RelativeLayout rl_inquiry_dialog = (RelativeLayout) dialogView.findViewById(R.id.rl_inquiry_dialog);
        LinearLayout ll_serve_registration = (LinearLayout) dialogView.findViewById(R.id.ll_serve_registration);
        LinearLayout ll_online_inquiry = (LinearLayout) dialogView.findViewById(R.id.ll_online_inquiry);
        final ImageView inquiry_dialog_tag = (ImageView) dialogView.findViewById(R.id.inquiry_dialog_tag);
        final LinearLayout ll_online_inquiry1 = (LinearLayout) dialogView.findViewById(R.id.ll_online_inquiry1);
        final LinearLayout ll_serve_registration1 = (LinearLayout) dialogView.findViewById(R.id.ll_serve_registration1);

        rl_inquiry_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RotateAnimation myAnimation_Rotate = new RotateAnimation(45.0f, 90.0f,
                        Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                inquiry_dialog_tag.startAnimation(myAnimation_Rotate);
                myAnimation_Rotate.setDuration(150);
                myAnimation_Rotate.setFillAfter(true);


                WindowManager wm = (WindowManager) HomeActivity.this.getSystemService(Context.WINDOW_SERVICE);

                int width = wm.getDefaultDisplay().getWidth();
                int height = wm.getDefaultDisplay().getHeight();


                translateAnimation = new TranslateAnimation(0.1f, width / 4.6f, 0.1f, height * 0.163f);
                translateAnimation.setDuration(150);
                ll_online_inquiry1.startAnimation(translateAnimation);

                //
                translateAnimation1 = new TranslateAnimation(0.1f, -width / 4.6f, 0.1f, height * 0.163f);
                translateAnimation1.setDuration(150);
                ll_serve_registration1.startAnimation(translateAnimation1);


                new Thread() {
                    public void run() {
                        try {
                            sleep(150);
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        } finally {
                            setHeadDialog.dismiss();
                        }


                    }
                }.start();

            }
        });

        ll_serve_registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(HomeActivity.this, RegistrationAtivity.class);
                startActivity(intent);
                setHeadDialog.dismiss();
            }
        });

        ll_online_inquiry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(HomeActivity.this, InquiryActivity.class);
                startActivity(intent);
                setHeadDialog.dismiss();
            }
        });

        RotateAnimation myAnimation_Rotate = new RotateAnimation(0.5f, -45.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        inquiry_dialog_tag.startAnimation(myAnimation_Rotate);
        myAnimation_Rotate.setDuration(150);
        myAnimation_Rotate.setFillAfter(true);

        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);

        int width = wm.getDefaultDisplay().getWidth();

        int height = wm.getDefaultDisplay().getHeight();

        translateAnimation = new TranslateAnimation(width / 4.6f, 0.1f, height * 0.163f, 0.1f);

        translateAnimation.setDuration(150);
        ll_online_inquiry1.startAnimation(translateAnimation);

        translateAnimation1 = new TranslateAnimation(-width / 4.6f, 0.1f, height * 0.163f, 0.1f);
        translateAnimation1.setDuration(150);
        ll_serve_registration1.startAnimation(translateAnimation1);
    }


    @Override
    protected void onPause() {
        super.onPause();
        JPushInterface.onPause(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        JPushInterface.onResume(this);
    }


    @Override
    public void notifyAllActivity(String str) {
        try {

            ft = fm.beginTransaction();
            hideAllFragment(ft);


            if (str.equals("显示诊疗页面")) {

                fl_registration.setVisibility(View.GONE);
                rb_diagnosis.setChecked(true);
                if (consultingFragment == null) {
                    consultingFragment = new ConsultingFragment();
                    ft.add(R.id.framelayout, consultingFragment);
                } else {
                    ft.show(consultingFragment);
                }

                ft.commitAllowingStateLoss();


            } else if (str.equals("显示资讯页面")) {
                fl_registration.setVisibility(View.GONE);
                radioGroup_left.clearCheck();
                rb_lectureroom.setChecked(true);

                if (encyclopediaFragment == null) {

                    encyclopediaFragment = new EncyclopediaFragment();
                    ft.add(R.id.framelayout, encyclopediaFragment);
                } else {

                    ft.show(encyclopediaFragment);
                }
                ft.commitAllowingStateLoss();
            } else {

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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
                Intent intent = new Intent(HomeActivity.this, InquiryActivity.class);
                startActivity(intent);
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
                HomeActivity.this.finish();
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
