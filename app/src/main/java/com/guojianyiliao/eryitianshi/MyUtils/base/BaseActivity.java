package com.guojianyiliao.eryitianshi.MyUtils.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.guojianyiliao.eryitianshi.MyUtils.view.activity.view.LoadingPage;

/**
 * Activity 基类
 * jnluo,jnluo5889@126.com
 */

public class BaseActivity extends AppCompatActivity {

    private LoadingPage mLoadingPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /** 这里是管理所有activity过场动画，子类可以实现，根据ResultState返回的状态决定加载LoadingPage那种视图 */

//        mLoadingPage = new LoadingPage(this) {
//
//            @Override
//            public View onCreateSuccessView() {
//                return BaseActivity.this.onCreateSuccessView();
//            }
//
//            @Override
//            public ResultState onLoad() {
//                return BaseActivity.this.onLoad();
//            }
//
//        };
//        return mLoadingPage;
    }

    // 由子类实现创建布局的方法
//    public  abstract void onCreateSuccessView();

    // 由子类实现加载网络数据的逻辑, 该方法运行在子线程
//     public abstract LoadingPage.ResultState onLoad();

    // 开始加载网络数据
    public void loadData() {
//             if (mLoadingPage != null) {
//               mLoadingPage.loadData();
//          }
    }
}
