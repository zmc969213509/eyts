package com.guojianyiliao.eryitianshi.MyUtils.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;

import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.guojianyiliao.eryitianshi.MyUtils.base.BaseActivity;
import com.guojianyiliao.eryitianshi.MyUtils.bean.BaikeVideoData;
import com.guojianyiliao.eryitianshi.MyUtils.interfaceservice.GetService;
import com.guojianyiliao.eryitianshi.MyUtils.manager.RetrofitClient;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.MyLogcat;
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

public class SchoolActivityLryv extends BaseActivity {

    @BindView(R.id.recyclerView)
    LRecyclerView recyclerView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_lectureroom);
        ButterKnife.bind(this);
        initData();

        recyclerView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData();
                recyclerView.refreshComplete(1);
                mLRecyclerViewAdapter.notifyDataSetChanged();
            }
        });

    }

    List<BaikeVideoData> Data = new ArrayList<>();

    LectureroomAdapter adapter;
    LRecyclerViewAdapter mLRecyclerViewAdapter;
    private void showRecycler(List<BaikeVideoData> data) {

        if (adapter == null) {

            adapter = new LectureroomAdapter(SchoolActivityLryv.this, Data);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
            recyclerView.setLayoutManager(gridLayoutManager);
             mLRecyclerViewAdapter = new LRecyclerViewAdapter(adapter);
            recyclerView.setAdapter(mLRecyclerViewAdapter);

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

}
