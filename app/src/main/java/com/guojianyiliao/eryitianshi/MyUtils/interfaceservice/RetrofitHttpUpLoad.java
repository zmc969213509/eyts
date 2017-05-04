package com.guojianyiliao.eryitianshi.MyUtils.interfaceservice;

import android.content.Context;
import android.widget.Toast;

import com.google.gson.Gson;
import com.guojianyiliao.eryitianshi.Data.Http_data;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.MyLogcat;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.ToolUtils;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.UIUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by DELL on 2017/3/20.
 * 上传文件用（包含图片）
 */

public class RetrofitHttpUpLoad {
    /**
     * 超时时间60s
     */
    private static final long DEFAULT_TIMEOUT = 60;
    private volatile static RetrofitHttpUpLoad mInstance;
    public Retrofit mRetrofit;
    public GetService mHttpService;
    private static Map<String, RequestBody> params;

    private RetrofitHttpUpLoad() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(Http_data.http_data)
                .client(genericClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mHttpService = mRetrofit.create(GetService.class);
    }

    public static RetrofitHttpUpLoad getInstance() {
        if (mInstance == null) {
            synchronized (RetrofitHttpUpLoad.class) {
                if (mInstance == null)
                    mInstance = new RetrofitHttpUpLoad();
                params = new HashMap<String, RequestBody>();
            }
        }
        return mInstance;
    }

    /**
     * 添加统一超时时间,http日志打印
     *
     * @return
     */
    private OkHttpClient genericClient() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .build();
        return httpClient;
    }


    /**
     * 将call加入队列并实现回调
     *
     * @param call             调入的call
     * @param retrofitCallBack 回调
     * @param method           调用方法标志，回调用
     * @param <T>              泛型参数
     */
    public <T> void addToEnqueue(Call<T> call, final RetrofitCallBack retrofitCallBack, final int method) {
        final Context context = UIUtils.getContext();
        call.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                MyLogcat.jLog().d("retrofit back code ====" + response.code());
                if (null != response.body()) {
                    if (response.code() == 200) {
                        MyLogcat.jLog().d("retrofit back body ====" + new Gson().toJson(response.body()));
                        retrofitCallBack.onResponse(response, method);
                    } else {
                        MyLogcat.jLog().d("toEnqueue, onResponse Fail:" + response.code());
                        ToolUtils.showToast(UIUtils.getContext(), "网络连接错误 q" + response.code(), Toast.LENGTH_SHORT);
                        retrofitCallBack.onFailure(response, method);
                    }
                } else {
                    MyLogcat.jLog().d("toEnqueue, onResponse Fail m:" + response.message());
                    ToolUtils.showToast(context, "网络连接错误 w " + response.message(),Toast.LENGTH_SHORT);
                    retrofitCallBack.onFailure(response, method);
                }
            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                MyLogcat.jLog().d("toEnqueue, onResponse Fail unKnown:" + t.getMessage());
                t.printStackTrace();
                ToolUtils.showToast(context, "网络连接错误 e" + t.getMessage(),Toast.LENGTH_SHORT);
                retrofitCallBack.onFailure(null, method);
            }
        });
    }

    /**
     * 添加参数
     * 根据传进来的Object对象来判断是String还是File类型的参数
     */
    public RetrofitHttpUpLoad addParameter(String key, Object o) {

        if (o instanceof String) {
            RequestBody body = RequestBody.create(MediaType.parse("text/plain;charset=UTF-8"), (String) o);
            params.put(key, body);
        } else if (o instanceof File) {
            RequestBody body = RequestBody.create(MediaType.parse("multipart/form-data;charset=UTF-8"), (File) o);
            params.put(key + "\"; filename=\"" + ((File) o).getName() + "", body);
        }
        return this;
    }

    /**
     * 构建RequestBody
     */
    public Map<String, RequestBody> bulider() {

        return params;
    }

    public void clear(){
        params.clear();
    }
}
