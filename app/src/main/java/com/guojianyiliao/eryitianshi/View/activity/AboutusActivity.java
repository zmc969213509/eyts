package com.guojianyiliao.eryitianshi.View.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.guojianyiliao.eryitianshi.Data.Http_data;
import com.guojianyiliao.eryitianshi.Data.entity.Update;
import com.guojianyiliao.eryitianshi.R;
import com.guojianyiliao.eryitianshi.Utils.MyBaseActivity;
import com.guojianyiliao.eryitianshi.Utils.Version_numberSP;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import okhttp3.Call;


public class AboutusActivity extends MyBaseActivity {
    private LinearLayout ll_aboutus, ll_hint_version_number, ll_user_agreement;
    private TextView tv_aboutus_version_umber;
    Version_numberSP version_numberSP;
    Update update;
    private Dialog setHeadDialog;
    private View dialogView;

    private TextView tv_hint_version_number;


    String updateurl;

    ProgressBar pb_update_load;


    File updateaudioFile;
    File updatesdcardTempFile;

    int fileSize;

    int downLoadFileSize;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutus);

        try {

            version_numberSP = new Version_numberSP(this);
            updateaudioFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/text/update/");
            updateaudioFile.mkdirs();
            findView();
            init();
            // updatedetection();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void findView() {
        ll_aboutus = (LinearLayout) findViewById(R.id.ll_aboutus);
        tv_aboutus_version_umber = (TextView) findViewById(R.id.tv_aboutus_version_umber);
        ll_hint_version_number = (LinearLayout) findViewById(R.id.ll_hint_version_number);
        tv_hint_version_number = (TextView) findViewById(R.id.tv_hint_version_number);
        ll_user_agreement = (LinearLayout) findViewById(R.id.ll_user_agreement);
        ll_aboutus.setOnClickListener(litener);
        ll_user_agreement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AboutusActivity.this, UserAgreementActivity.class);
                startActivity(intent);
            }
        });
    }

    private void init() throws Exception {
        String version_number = version_numberSP.getversionNumber();
        // tv_aboutus_version_umber.setText("版本号" + version_number);
        tv_aboutus_version_umber.setText("版本号" + getVersionName());
        tv_hint_version_number.setText(getVersionName());
    }

    private String getVersionName() throws Exception {
        // 获取packagemanager的实例
        PackageManager packageManager = getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = packageManager.getPackageInfo(getPackageName(), 0);
        String version = packInfo.versionName;
        return version;
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
                                Toast.makeText(AboutusActivity.this, "检查更新失败", Toast.LENGTH_SHORT).show();

                            }

                            @Override
                            public void onResponse(String response, int id) {

                                update = gson.fromJson(response, Update.class);

                                if (update.getVersion().trim().equals(version_numberSP.getversionNumber().trim())) {

                                } else {
                                    handler1.sendEmptyMessage(0);
                                    ll_hint_version_number.setOnClickListener(verlistener);

                                }

                            }
                        });
            }
        });

        thread.start();

    }


    Handler handler1 = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case 0:
                        tv_hint_version_number.setText("版本号 " + update.getVersion());
                    break;
            }
        }
    };

    private View.OnClickListener verlistener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            updatedialog();
        }
    };


    private void updatedialog() {

        setHeadDialog = new Dialog(this, R.style.CustomDialog);
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


    public void down_file(String url) throws IOException {
        updatesdcardTempFile = new File(updateaudioFile, "textupdate.apk");

        URL myURL = new URL(url);
        URLConnection conn = myURL.openConnection();
        conn.connect();
        InputStream is = conn.getInputStream();
        this.fileSize = conn.getContentLength();//SB

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
            Log.e("下载错误", "下载错误");
        }

    }


    private void sendMsg(int flag) {
        android.os.Message msg = new android.os.Message();
        msg.what = flag;
        handler.sendMessage(msg);
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

                        Toast.makeText(AboutusActivity.this, "文件下载完成", Toast.LENGTH_SHORT).show();
                        setHeadDialog.dismiss();
                        try {
                            openFile(updatesdcardTempFile);
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(AboutusActivity.this, "打开更新文件失败", Toast.LENGTH_SHORT).show();
                        }

                        break;

                    case -1:

                        Toast.makeText(AboutusActivity.this, "错误", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
            super.handleMessage(msg);
        }
    };


    private void openFile(File file) {
        // TODO Auto-generated method stub

        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(android.content.Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file),
                "application/vnd.android.package-archive");
        startActivity(intent);
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


    private View.OnClickListener litener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };
}
