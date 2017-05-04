package com.guojianyiliao.eryitianshi.MyUtils.view.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import com.guojianyiliao.eryitianshi.MyUtils.base.BaseActivity;
import com.guojianyiliao.eryitianshi.MyUtils.bean.BaikeVideoData;
import com.guojianyiliao.eryitianshi.MyUtils.interfaceservice.GetService;
import com.guojianyiliao.eryitianshi.MyUtils.manager.RetrofitClient;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.MyLogcat;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.UIUtils;
import com.guojianyiliao.eryitianshi.MyUtils.view.activity.view.OnReFlashRecyclerView.LoadRefreshRecyclerView;
import com.guojianyiliao.eryitianshi.MyUtils.view.activity.view.OnReFlashRecyclerView.LoadViewCreator;
import com.guojianyiliao.eryitianshi.MyUtils.view.activity.view.OnReFlashRecyclerView.RefreshRecyclerView;
import com.guojianyiliao.eryitianshi.MyUtils.view.activity.view.OnReFlashRecyclerView.RefreshViewCreator;
import com.guojianyiliao.eryitianshi.R;
import com.guojianyiliao.eryitianshi.page.adapter.LectureroomAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * description: 百科-课堂页面
 * autour: jnluo jnluo5889@126.com
 * version: Administrator
 */

public class SchoolActivity extends BaseActivity implements RefreshRecyclerView.OnRefreshListener {

    @BindView(R.id.recyclerView)
    LoadRefreshRecyclerView recyclerView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_lectureroom);
        ButterKnife.bind(this);
        initData();
        recyclerView.setOnRefreshListener(this);
    }

    List<BaikeVideoData> Data = new ArrayList<>();

    LectureroomAdapter adapter;

    private void showRecycler(List<BaikeVideoData> data) {
        if (adapter == null) {
            adapter = new LectureroomAdapter(SchoolActivity.this, Data);
            recyclerView.setVerticalScrollBarEnabled(true);//滚动条
            GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
            recyclerView.setLayoutManager(gridLayoutManager);
            recyclerView.setAdapter(adapter);
            recyclerView.addRefreshViewCreator(new RefreshViewCreator() {
                @Override
                public View getRefreshView(Context context, ViewGroup parent) {
                    View view = UIUtils.getinflate(R.layout.listview_header_pullto);
                    return view;
                }

                @Override
                public void onPull(int currentDragHeight, int refreshViewHeight, int currentRefreshStatus) {
                    MyLogcat.jLog().e("onPull");
                }

                @Override
                public void onRefreshing() {
                    MyLogcat.jLog().e("onRefreshing");
                }

                @Override
                public void onStopRefresh() {

                }
            });

        } else {
            adapter.setList(data);
        }
    }

    private void initData() {

        RetrofitClient.getinstance(this).create(GetService.class).BaikeVideoData().enqueue(new Callback<List<BaikeVideoData>>() {
            @Override
            public void onResponse(retrofit2.Call<List<BaikeVideoData>> call, Response<List<BaikeVideoData>> response) {
                if (response.isSuccessful()) {
                    MyLogcat.jLog().e("success:");
                    if (Data.size() != 0) {
                        Data.clear();
                    }
                    List<BaikeVideoData> body = response.body();
                    for (BaikeVideoData data : body) {
                        MyLogcat.jLog().e("title:" + data.title);
                        Data.add(data);

                    }
                    showRecycler(body);
                }
            }

            @Override
            public void onFailure(retrofit2.Call<List<BaikeVideoData>> call, Throwable t) {

                MyLogcat.jLog().e("fail:");
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    @Override
    public void onRefresh() {
        UIUtils.getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                MyLogcat.jLog().e("Runnable onRefresh");
               /* View mLoadView = recyclerView.mLoadView;
                TextView tvtime = (TextView) mLoadView.findViewById(R.id.tv_listview_header_state);
                tvtime.setText("android");*/
                recyclerView.onStopRefresh();
            }
        }, 2000);
    }
}
