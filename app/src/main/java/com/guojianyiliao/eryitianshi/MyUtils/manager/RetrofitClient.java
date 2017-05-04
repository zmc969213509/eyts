package com.guojianyiliao.eryitianshi.MyUtils.manager;

import android.content.Context;

import com.guojianyiliao.eryitianshi.Data.Http_data;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 *
 */

public class RetrofitClient {

    private static Retrofit rt;

    // 获取单例的线程池对象
    public static Retrofit getinstance(Context context) {
        if (rt == null) {
            synchronized (ThreadManager.class) {
                if (rt == null) {
                    rt = new Retrofit.Builder()
                            .baseUrl(Http_data.http_data)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                }
            }
        }

        return rt;
    }

}
