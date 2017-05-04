package com.guojianyiliao.eryitianshi.MyUtils;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Handler;
import android.os.Process;
import android.support.multidex.MultiDex;
import android.text.TextUtils;

import com.guojianyiliao.eryitianshi.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.tinker.loader.app.DefaultApplicationLike;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.im.android.api.JMessageClient;

/**
 * 热更新 动态代理类
 * jnluo,jnluo5889@126.com
 */

public class SampleApplicationLike extends DefaultApplicationLike {
    public static final String TAG = "Tinker.SampleApplicationLike";
    private static Context mcontext;
    private static Handler handler;
    private static int mainThreadId;

    public SampleApplicationLike(Application application, int tinkerFlags,
                                 boolean tinkerLoadVerifyFlag, long applicationStartElapsedTime,
                                 long applicationStartMillisTime, Intent tinkerResultIntent) {
        super(application, tinkerFlags, tinkerLoadVerifyFlag, applicationStartElapsedTime,
                applicationStartMillisTime, tinkerResultIntent);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mcontext = getApplication();
        handler = new Handler();
        mainThreadId = Process.myTid();

        Bugly.init(getApplication(), "de84e6ddd9", true); // 这里实现SDK初始化，appId替换成你的在Bugly平台申请的appId
        Bugly.setIsDevelopmentDevice(getApplication(), true);// 设置开发设备

        // Beta.canAutoDownloadPatch = true;// 设置是否自动下载补丁
        // Beta.canNotifyUserRestart = false; // 设置是否提示用户重启
        // Beta.canAutoPatch = true; // 设置是否自动合成补丁

        /**https://bugly.qq.com/docs/user-guide/instruction-manual-android/?v=20170316174927 */
        String packageName = mcontext.getPackageName();
        String processName = getProcessName(android.os.Process.myPid());
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(mcontext);
        strategy.setUploadProcess(processName == null || processName.equals(packageName));
        CrashReport.initCrashReport(mcontext, "de84e6ddd9", true, strategy);


        //MobclickAgent.setScenarioType(mcontext, MobclickAgent.EScenarioType.E_UM_NORMAL);//umeng渠道统计
        UMShareAPI.get(mcontext);
        Config.DEBUG = true;
        UMShareAPI.get(mcontext);
        PlatformConfig.setWeixin("wx3f904bdd5496a1f3", "32b7fcb2b3e25d4d5ff2e57167c6688f");
        PlatformConfig.setQQZone("1105577776", "ooJU4TpecWWpvaQv");
        PlatformConfig.setSinaWeibo("3039479683", "63abeac9ceb22b215e323e8772945f3d", "http://mobile.umeng.com/social");

        JMessageClient.setDebugMode(true);
        JMessageClient.init(mcontext);
        JPushInterface.setDebugMode(true);
        JPushInterface.init(mcontext);

        //  MobclickAgent.setScenarioType(mcontext, MobclickAgent.EScenarioType.E_UM_NORMAL);
        // MobclickAgent.startWithConfigure(new MobclickAgent.UMAnalyticsConfig(mcontext, appkey, "xiaomi", MobclickAgent.EScenarioType.E_UM_NORMAL, true));

        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnFail(R.drawable.defeated_picture)
                .showImageForEmptyUri(R.drawable.defeated_picture)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .resetViewBeforeLoading(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(mcontext)
                .memoryCacheSize(5 * 1024 * 1024) // 内
                .defaultDisplayImageOptions(options) // 设
                .threadPoolSize(3)
                .build();

        ImageLoader.getInstance().init(config);


    }


    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    public void onBaseContextAttached(Context base) {
        super.onBaseContextAttached(base);
        // you must install multiDex whatever tinker is installed!
        MultiDex.install(base);

        // TODO: 安装tinker
        Beta.installTinker(this);
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public void registerActivityLifecycleCallback(Application.ActivityLifecycleCallbacks callbacks) {
        getApplication().registerActivityLifecycleCallbacks(callbacks);
    }

    public static Context getMcontext() {
        return mcontext;
    }

    public static Handler getHandler() {
        return handler;
    }

    public static int getMainThreadId() {
        return mainThreadId;
    }

    private static String getProcessName(int pid) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }
            return processName;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return null;
    }
}


