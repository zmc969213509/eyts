package com.guojianyiliao.eryitianshi.MyUtils;

import android.app.Application;

/**
 * Created by Chen on 2016/6/8.
 */
public class MyAppliction extends Application {
    public static final String TAG = "Tinker.SampleApplicationLike";
    public static int UserId = 0;
    public static final int PAY_OTIEM_COUPON = 1;

  //  private static Context mcontext;
   // private static Handler handler;
  //  private static int mainThreadId;


   /* @Override
    public void onCreate() {
        super.onCreate();
      *//*  // 设置是否自动下载补丁
        Beta.canAutoDownloadPatch = true;
        // 设置是否提示用户重启
        Beta.canNotifyUserRestart = false;
        // 设置是否自动合成补丁
        Beta.canAutoPatch = true;
*//*
        mcontext = getApplicationContext();
        handler = new Handler();
        mainThreadId = Process.myTid();

       // MobclickAgent.setScenarioType(mcontext, MobclickAgent.EScenarioType. E_UM_NORMAL);

        JMessageClient.setDebugMode(true);
        JMessageClient.init(mcontext);
        JPushInterface.setDebugMode(true);
        JPushInterface.init(mcontext);
        UMShareAPI.get(mcontext);
        // 调试时，将第三个参数改为true de84e6ddd9
        Bugly.init(this, "de84e6ddd9", true);

        *//**https://bugly.qq.com/docs/user-guide/instruction-manual-android/?v=20170316174927 *//*
        String packageName = mcontext.getPackageName();
        String processName = getProcessName(android.os.Process.myPid());
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(mcontext);
        strategy.setUploadProcess(processName == null || processName.equals(packageName));
        CrashReport.initCrashReport(mcontext, "de84e6ddd9", true, strategy);

        //  MobclickAgent.setScenarioType(mcontext, MobclickAgent.EScenarioType.E_UM_NORMAL);
        // MobclickAgent.startWithConfigure(new MobclickAgent.UMAnalyticsConfig(mcontext, appkey, "xiaomi", MobclickAgent.EScenarioType.E_UM_NORMAL, true));
        Config.DEBUG = true;
        UMShareAPI.get(mcontext);
        PlatformConfig.setWeixin("wx3f904bdd5496a1f3", "32b7fcb2b3e25d4d5ff2e57167c6688f");
        PlatformConfig.setQQZone("1105577776", "ooJU4TpecWWpvaQv");
        PlatformConfig.setSinaWeibo("3039479683", "63abeac9ceb22b215e323e8772945f3d", "http://mobile.umeng.com/social");

        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnFail(R.drawable.defeated_picture)
                .showImageForEmptyUri(R.drawable.defeated_picture)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .resetViewBeforeLoading(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
        // Im
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(mcontext)
                .memoryCacheSize(5 * 1024 * 1024) // 内
                .defaultDisplayImageOptions(options) // 设
                .threadPoolSize(3)
                .build();

        // 初始
        ImageLoader.getInstance().init(config);

    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        // you must install multiDex whatever tinker is installed!
        MultiDex.install(base);
        // 安装tinker
        Beta.installTinker();
        MyLogcat.jLog().e("Bugly hinkler: 初始化");
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
    }*/
}
