package com.guojianyiliao.eryitianshi.MyUtils.view.activity.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
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
 * 百科-课堂页面
 * jnluo
 */
public class SchoolFragment extends Fragment {

    @BindView(R.id.recyclerView)
    LRecyclerView recyclerView;
   /* @BindView(R.id.rl_nonetwork)
    RelativeLayout rlNonetwork;
    @BindView(R.id.rl_loading)
    RelativeLayout rlLoading;*/

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initData();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lectureroom, container, false);
        ButterKnife.bind(this, view);

        recyclerView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData();
                recyclerView.refreshComplete(1);
                mLRecyclerViewAdapter.notifyDataSetChanged();
            }
        });
        return view;
    }

    LectureroomAdapter adapter;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    List<BaikeVideoData> Data = new ArrayList<>();
    LRecyclerViewAdapter mLRecyclerViewAdapter;

    private void initData() {

        RetrofitClient.getinstance(getActivity()).create(GetService.class).BaikeVideoData().enqueue(new Callback<List<BaikeVideoData>>() {
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
                    recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
                    adapter = new LectureroomAdapter(getActivity(), Data);
                    recyclerView.setVerticalScrollBarEnabled(true);//滚动条
                    mLRecyclerViewAdapter = new LRecyclerViewAdapter(adapter);
                    recyclerView.setAdapter(mLRecyclerViewAdapter);

                    // handler.sendEmptyMessage(1);
                }
            }

            @Override
            public void onFailure(retrofit2.Call<List<BaikeVideoData>> call, Throwable t) {
                //  handler.sendEmptyMessage(0);
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
