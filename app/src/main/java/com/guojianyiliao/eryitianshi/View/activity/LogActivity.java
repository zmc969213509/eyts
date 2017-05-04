package com.guojianyiliao.eryitianshi.View.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.guojianyiliao.eryitianshi.Data.Http_data;
import com.guojianyiliao.eryitianshi.Data.entity.Launch;
import com.guojianyiliao.eryitianshi.MyUtils.bean.UserInfoLogin;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.MyLogcat;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.SpUtils;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.StringUtils;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.ToolUtils;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.UIUtils;
import com.guojianyiliao.eryitianshi.MyUtils.view.activity.BindingInfoData;
import com.guojianyiliao.eryitianshi.MyUtils.view.activity.HomeAcitivtyMy;
import com.guojianyiliao.eryitianshi.R;
import com.guojianyiliao.eryitianshi.Utils.SharedPsaveuser;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import cn.jpush.android.api.JPushInterface;
import okhttp3.Call;


public class LogActivity extends AppCompatActivity {
    SharedPsaveuser sp;

    int i = 0;

    ImageView iv_page, iv_log;

    File sdcardTempFile;
    File audioFile;

    Launch launch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.defaultTheme);
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            //
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }

        setContentView(R.layout.activity_log);

        sp = new SharedPsaveuser(LogActivity.this);

        iv_page = (ImageView) findViewById(R.id.iv_page);

        iv_log = (ImageView) findViewById(R.id.iv_log);

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            Date curDate = new Date(System.currentTimeMillis());//
            String str1 = sdf.format(curDate);

            if (sp.setStartPage().getDate() == null || sp.setStartPage().getDate().equals("")) {
                iv_log.setBackgroundResource(R.drawable.app_log_page);
            } else if (getTimeStamp(sp.setStartPage().getDate(), "yyyy-MM-dd") >= getTimeStamp(str1, "yyyy-MM-dd")) {
                ImageLoader.getInstance().displayImage("file:///" + sp.setStartPage().getImg(), iv_log);
            } else {
                iv_log.setBackgroundResource(R.drawable.app_log_page);
            }
        } catch (Exception e) {
            e.printStackTrace();
            iv_log.setBackgroundResource(R.drawable.app_log_page);
        }

        try {
            //
            UIUtils.getHandler().postDelayed(new Runnable() {
                public void run() {


                    judgeWhetherRegister();

                    pageInit();

                }
            }, 2000);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static long getTimeStamp(String timeStr, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        Date date = null;
        try {
            date = simpleDateFormat.parse(timeStr);
            long timeStamp = date.getTime();
            return timeStamp;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private Gson gson;

    private void judgeWhetherRegister() {
        /**
         * phone name gender userid iconurl type
         * */


        SpUtils.getInstance(LogActivity.this).put("userid", "b5df631f62cc40e6b932acd997cdc5c9");
        SpUtils.getInstance(LogActivity.this).put("name", "安卓");
        SpUtils.getInstance(LogActivity.this).put("gender", "男");
        SpUtils.getInstance(LogActivity.this).put("phone", "18310904818");
        startActivity(new Intent(LogActivity.this, HomeAcitivtyMy.class));

        String phone = SpUtils.getInstance(LogActivity.this).get("phone", null);
        String passwrode = SpUtils.getInstance(LogActivity.this).get("password", null);

       /* String userid = "b5df631f62cc40e6b932acd997cdc5c9";
        String phone = "18310904818";
        String passwrode = "FCEA920F7412B5DA7BE0CF42B8C93759";*/

        if (phone != null && passwrode != null) {
            MyLogcat.jLog().e("judge phone:" + phone + " //passwrode:" + passwrode + " //userid:");
            gson = new Gson();
            // HttpLoginPas(phone, passwrode);
        } else {
            MyLogcat.jLog().e("judge  null");
            Intent intent = new Intent(LogActivity.this, LoginSelectActivity.class);
            //startActivity(intent);
            finish();

        }
    }

    private void HttpLoginPas(String phone, String pasMD5) {
        OkHttpUtils
                .post()
                .url(Http_data.http_data + "user/Login")
                .addParams("phone", phone)
                .addParams("password", pasMD5)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        MyLogcat.jLog().e("快捷登录 失败！onError");
                        startActivity(new Intent(LogActivity.this, LoginSelectActivity.class));
                        //startActivity(new Intent(LogActivity.this, HomeAcitivtyMy.class));
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        //{"name":"??","password":"51596103F385101CE16BCC58B3C3B85B","phone":"18310904818","role":"1",
                        // "userid":"4e4d86c1fab74d4982ccceec9c0ca38b"}
                        MyLogcat.jLog().e("login: " + response.toString());
                        if (response.equals("1")) {
                            ToolUtils.showToast(LogActivity.this, "请重新登录", Toast.LENGTH_SHORT);
                            startActivity(new Intent(LogActivity.this, LoginSelectActivity.class));
                            finish();

                        } else if (response.equals("2")) {
                            ToolUtils.showToast(LogActivity.this, "登录异常，请重新登录", Toast.LENGTH_SHORT);
                            startActivity(new Intent(LogActivity.this, LoginSelectActivity.class));
                            finish();
                        } else if (response.equals("error")) {
                            ToolUtils.showToast(LogActivity.this, "账号或密码错误", Toast.LENGTH_SHORT);
                            startActivity(new Intent(LogActivity.this, LoginSelectActivity.class));
                            finish();
                        } else {
                            UserInfoLogin user = gson.fromJson(response, UserInfoLogin.class);
                            try {

                                SpUtils.getInstance(LogActivity.this).put("userid", user.getUserid());
                                SpUtils.getInstance(LogActivity.this).put("name", user.getName());
                                SpUtils.getInstance(LogActivity.this).put("gender", user.getGender());
                                SpUtils.getInstance(LogActivity.this).put("iconurl", user.getIcon());
                                SpUtils.getInstance(LogActivity.this).put("phone", user.getPhone());

                                if (StringUtils.isEmpty(user.getName())) {
                                    Intent intent = new Intent(LogActivity.this, BindingInfoData.class);
                                    startActivity(intent);
                                } else {
                                    //Intent intent = new Intent(LoginSelectActivity.this, SetdataActivity.class);
                                    startActivity(new Intent(LogActivity.this, HomeAcitivtyMy.class));
                                }
                                finish();
                            } catch (Exception e) {
                                ToolUtils.showToast(LogActivity.this, "登录异常，请重新登录", Toast.LENGTH_SHORT);
                                startActivity(new Intent(LogActivity.this, LoginSelectActivity.class));
                                MyLogcat.jLog().e("Login ex:" + e.getMessage());
                                finish();
                            }
                        }
                    }
                });
    }

    public void pageInit() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpUtils
                        .post()
                        .url(Http_data.http_data + "/launch")
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {

                            }

                            @Override
                            public void onResponse(String response, int id) {

                                Gson gson = new Gson();

                                launch = gson.fromJson(response, Launch.class);

                                if (sp.setStartPage().getDate() == null || sp.setStartPage().getDate().equals("")) {
                                    handler.sendEmptyMessage(0);
                                } else {
                                    if (!sp.setStartPage().getDate().equals(launch.getDate())) {
                                        handler.sendEmptyMessage(0);

                                    } else {

                                    }
                                }

                            }
                        });
            }
        }).start();


    }


    private void saveicon() {
        new Thread() {
            public void run() {
                try {
                    audioFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/text/launch/");
                    audioFile.mkdirs();
                    sdcardTempFile = File.createTempFile(".launchPage", ".jpg", audioFile);
                    URL url = new URL(launch.getImg());
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setConnectTimeout(6 * 1000);  //
                    if (conn.getResponseCode() != 200) throw new RuntimeException("请求url失败");
                    InputStream inSream = conn.getInputStream();
                    //
                    readAsFile(inSream, new File(String.valueOf(sdcardTempFile)));
                    String launchImg = sdcardTempFile.getAbsolutePath();
                    //
                    sp.getStartPage(launchImg, launch.getDate());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }


    //
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


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            saveicon();

        }
    };


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
}
