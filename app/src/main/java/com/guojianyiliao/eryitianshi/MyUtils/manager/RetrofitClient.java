package com.guojianyiliao.eryitianshi.MyUtils.manager;

import android.content.Context;

import com.guojianyiliao.eryitianshi.Data.Http_data;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
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
                    //设置连接读取写入的时间限制
                    OkHttpClient client = new OkHttpClient.Builder().
                            connectTimeout(6, TimeUnit.SECONDS).
                            readTimeout(6, TimeUnit.SECONDS).
                            writeTimeout(6, TimeUnit.SECONDS).build();
                    rt = new Retrofit.Builder()
                            .baseUrl(Http_data.http_data).client(client)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                }
            }
        }

        return rt;
    }

}
