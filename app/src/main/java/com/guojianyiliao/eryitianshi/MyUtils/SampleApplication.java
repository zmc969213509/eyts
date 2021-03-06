package com.guojianyiliao.eryitianshi.MyUtils;

import android.content.Context;
import android.support.multidex.MultiDex;

import com.tencent.tinker.loader.app.TinkerApplication;
import com.tencent.tinker.loader.shareutil.ShareConstants;

/**
 * 热更新application 这里不做任何操作
 *  jnluo,jnluo5889@126.com
 */

public class SampleApplication extends TinkerApplication {
    public SampleApplication() {
        super(ShareConstants.TINKER_ENABLE_ALL, "com.guojianyiliao.eryitianshi.MyUtils.SampleApplicationLike",
                "com.tencent.tinker.loader.TinkerLoader", false);
    }

    //dex分包
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
