package com.guojianyiliao.eryitianshi.View.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.guojianyiliao.eryitianshi.Data.Http_data;
import com.guojianyiliao.eryitianshi.Data.entity.Launch;
import com.guojianyiliao.eryitianshi.MyUtils.bean.UserInfoLogin;
import com.guojianyiliao.eryitianshi.MyUtils.interfaceservice.GetService;
import com.guojianyiliao.eryitianshi.MyUtils.manager.RetrofitClient;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.MyLogcat;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.SharedPreferencesTools;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.SpUtils;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.StringUtils;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.ToolUtils;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.UIUtils;
import com.guojianyiliao.eryitianshi.MyUtils.view.activity.BindingInfoData;
import com.guojianyiliao.eryitianshi.MyUtils.view.activity.BindingPhoneActivity;
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
import retrofit2.Callback;
import retrofit2.Response;


public class LogActivity extends AppCompatActivity {
    SharedPsaveuser sp;

    int i = 0;

    ImageView iv_page, iv_log;

    File sdcardTempFile;
    File audioFile;

    Launch launch;

    Gson gson;
//    UserInfoLogin user ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.defaultTheme);
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        setContentView(R.layout.activity_log);

        gson = new Gson();
        String s = SharedPreferencesTools.GetUsearInfo(this, "userSave", "userInfo");
//        user = gson.fromJson(s, UserInfoLogin.class);

        sp = new SharedPsaveuser(LogActivity.this);

        iv_page = (ImageView) findViewById(R.id.iv_page);

        iv_log = (ImageView) findViewById(R.id.iv_log);

//        try {
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//
//            Date curDate = new Date(System.currentTimeMillis());//
//            String str1 = sdf.format(curDate);
//
//            if (sp.setStartPage().getDate() == null || sp.setStartPage().getDate().equals("")) {
//                iv_log.setBackgroundResource(R.drawable.app_log_page);
//            } else if (getTimeStamp(sp.setStartPage().getDate(), "yyyy-MM-dd") >= getTimeStamp(str1, "yyyy-MM-dd")) {
//                ImageLoader.getInstance().displayImage("file:///" + sp.setStartPage().getImg(), iv_log);
//            } else {
//                iv_log.setBackgroundResource(R.drawable.app_log_page);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            iv_log.setBackgroundResource(R.drawable.app_log_page);
//        }

        try {
            UIUtils.getHandler().postDelayed(new Runnable() {
                public void run() {
                    judgeWhetherRegister();
                }
            }, 1000);
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

    /**
     * 快捷登录
     */
    private void judgeWhetherRegister() {
        boolean loginStatus = SharedPreferencesTools.GetLoginStatus(this, "userSave", "userLoginStatus");
        if(loginStatus){
            String type = SharedPreferencesTools.GetLoginType(this,"userSave","userType");
            Log.e("Test~~~~","登录类型："+type);
            if(TextUtils.isEmpty(type)){
                Toast.makeText(this,"账号过期，请重新登录",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LogActivity.this, LoginSelectActivity.class);
                startActivity(intent);
                finish();
            }else if(type.equals("phone")){
                String s = SharedPreferencesTools.GetLoginData(this, "userSave", "userLoginData");
                Log.e("Test~~~~","登录信息："+s);
                if(TextUtils.isEmpty(s)){
                    Toast.makeText(this,"账号过期，请重新登录",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LogActivity.this, LoginSelectActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    try{
                        String[] split = s.split(",");
                        String phone = split[0];
                        String passwrode = split[1];
                        Log.e("LogActivity","phone = "+phone);
                        Log.e("LogActivity","passwrode = "+passwrode);

                        if (phone != null && passwrode != null && !TextUtils.isEmpty(phone) && !TextUtils.isEmpty(passwrode)) {
                            HttpLoginPas(phone, passwrode);
                        } else {
                            Toast.makeText(this,"账号过期，请重新登录",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LogActivity.this, LoginSelectActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }catch (Exception e){
                        Toast.makeText(this,"账号过期，请重新登录",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LogActivity.this, LoginSelectActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            }else if(type.equals("qq") || type.equals("wechat") || type.equals("webo")){//第三方登录
                String uid = SharedPreferencesTools.GetLoginData(this, "userSave", "userLoginData");
                Log.e("Test~~~~","第三方登录uid："+uid);
                if(TextUtils.isEmpty(uid)){
                    Toast.makeText(this,"账号过期，请重新登录",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LogActivity.this, LoginSelectActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    isRegistered(uid);
                }
            }
        }else{
            Intent intent = new Intent(LogActivity.this, LoginSelectActivity.class);
            startActivity(intent);
            finish();
        }
    }

    /**
     * 判断当前第三方UID是否已经注册了
     */
    private void isRegistered(final String uid ){
        Log.e("Test~~~~","第三方UID是否已经注册了");
        RetrofitClient.getinstance(this).create(GetService.class).isRegistered(uid).enqueue(new Callback<UserInfoLogin>() {
            @Override
            public void onResponse(retrofit2.Call<UserInfoLogin> call, Response<UserInfoLogin> response) {
                if(response.isSuccessful()){
                    Log.e("Test~~~~","isRegistered---response："+response.body());
                    try{
                        UserInfoLogin body = response.body();
                        String s = gson.toJson(body);
                        SharedPreferencesTools.SaveUserInfo(LogActivity.this,"userSave","userInfo",s);
                        SharedPreferencesTools.SaveUserId(LogActivity.this,"userSave","userId",body.getUserid());
                        SharedPreferencesTools.SaveLoginData(LogActivity.this, "userSave", "userLoginData", uid);
                        Intent intent = new Intent(LogActivity.this, HomeAcitivtyMy.class);
                        startActivity(intent);
                        finish();
                    }catch (Exception e){//未注册
                        Toast.makeText(LogActivity.this,"账号过期，请重新登录",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LogActivity.this, LoginSelectActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            }

            @Override
            public void onFailure(retrofit2.Call<UserInfoLogin> call, Throwable t) {
                Log.e("Test~~~~","第三方UID  onFailure   e:"+t.getMessage());
                Toast.makeText(LogActivity.this,"账号过期，请重新登录",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LogActivity.this, LoginSelectActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }



    /**
     * 账号密码登录
     * @param phone
     * @param pas
     */
    private void HttpLoginPas(String phone, String pas) {
        String pasMD5 = StringUtils.MD5encrypt(pas).toUpperCase();
        Log.e("LogActivity","pasMD5 = "+pasMD5);
        OkHttpUtils.post().url(Http_data.http_data + "user/Login")
                .addParams("phone", phone)
                .addParams("password", pasMD5)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        startActivity(new Intent(LogActivity.this, LoginSelectActivity.class));
                        Toast.makeText(LogActivity.this, "快捷登录，请重新登录", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("Test~~~~","HttpLoginPas---response："+response);
                        Log.e("LogActivity","response = "+response);
                        Log.e("LogActivity","response.leng() = "+response.length());
                        Log.e("LogActivity","pwdError.leng() = "+"pwdError".length());
//                        if (response.toString().equals("pwdError")) {
//                            ToolUtils.showToast(LogActivity.this, "密码错误，请重新登录", Toast.LENGTH_SHORT);
//                            startActivity(new Intent(LogActivity.this, LoginSelectActivity.class));
//                            Log.e("LogActivity","密码错误，请重新登录");
//                            finish();
//                        } else if (response.toString().equals("phoneError")) {
//                            ToolUtils.showToast(LogActivity.this, "电话号码错误，请重新登录", Toast.LENGTH_SHORT);
//                            startActivity(new Intent(LogActivity.this, LoginSelectActivity.class));
//                            Log.e("LogActivity","电话号码错误，请重新登录");
//                            finish();
//                        } else {
                            try {
                                UserInfoLogin user = gson.fromJson(response, UserInfoLogin.class);
                                SharedPreferencesTools.SaveUserInfo(LogActivity.this, "useaSave", "userInfo", response);

                                if (StringUtils.isEmpty(user.getName())) {
                                    Intent intent = new Intent(LogActivity.this, BindingInfoData.class);
                                    startActivity(intent);
                                } else {
                                    startActivity(new Intent(LogActivity.this, HomeAcitivtyMy.class));
                                }
                                finish();
                            } catch (Exception e) {
                                ToolUtils.showToast(LogActivity.this, "登录异常，请重新登录", Toast.LENGTH_SHORT);
                                startActivity(new Intent(LogActivity.this, LoginSelectActivity.class));
                                Log.e("LogActivity","登录异常，请重新登录");
                                finish();
                            }
                        }
//                    }
                });
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
