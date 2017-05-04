package com.guojianyiliao.eryitianshi.MyUtils.view.activity.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.guojianyiliao.eryitianshi.MyUtils.adaper.BaikeHotAdapter;
import com.guojianyiliao.eryitianshi.MyUtils.bean.BaikeHotTalkBean;
import com.guojianyiliao.eryitianshi.MyUtils.bean.GetAllTypesTalkBean;
import com.guojianyiliao.eryitianshi.MyUtils.interfaceservice.GetService;
import com.guojianyiliao.eryitianshi.MyUtils.manager.RetrofitClient;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.MyLogcat;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.UIUtils;
import com.guojianyiliao.eryitianshi.MyUtils.view.activity.view.DividerLine;
import com.guojianyiliao.eryitianshi.MyUtils.view.activity.view.XCFlowLayout;
import com.guojianyiliao.eryitianshi.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 百科-热门页面
 * jnluo
 */
public class HotingFragment extends Fragment {

    @BindView(R.id.fl_content_set)
    XCFlowLayout flContentSet;
    @BindView(R.id.recy_hot)
    LRecyclerView recyHot;

    List<BaikeHotTalkBean> itemHotData = new ArrayList<>();//热门
    List<GetAllTypesTalkBean> isCommon = new ArrayList<>();//分类

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HttpTypesData();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.a_baike_item_hot, container, false);
        ButterKnife.bind(this, view);
        init(itemHotData);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        recyHot.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                /**刷新数据*/
                HttpHotData(typeid);
                mLRecyclerViewAdapter.notifyDataSetChanged();
            }
        });

        recyHot.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {

               /* if (mCurrentPage < totalPage) {
                    // loading data
                    requestData();
                } else {
                    recyHot.setNoMore(true);
                }*/
            }
        });
    }

    private Integer etag = 0;//记录每次点击的位置
    String typeid;

    private void HttpTypesData() {
        RetrofitClient.getinstance(getActivity()).create(GetService.class).GetAllTypesTalk().enqueue(new Callback<List<GetAllTypesTalkBean>>() {
            @Override
            public void onResponse(Call<List<GetAllTypesTalkBean>> call, Response<List<GetAllTypesTalkBean>> response) {
                if (response.isSuccessful()) {
                    MyLogcat.jLog().e("百科-热门文章分类");
                    if (isCommon.size() != 0) {
                        isCommon.clear();
                    }
                    List<GetAllTypesTalkBean> body = response.body();
                    for (GetAllTypesTalkBean data : body) {
                        // isCommon.add(data.typename);
                        isCommon.add(data);
                    }
                    int padding = UIUtils.dip2px(6);
                    flContentSet.removeAllViews();
                    for (int i = 0; i < isCommon.size(); i++) {
                        final TextView tvIscommon = new TextView(getActivity());
                        tvIscommon.setTag(etag++);
                        tvIscommon.setText(isCommon.get(i).typename);
                        tvIscommon.setTextSize(UIUtils.px2dip(28));
                        tvIscommon.setTextColor(getResources().getColor(R.color.fontcolor));
                        tvIscommon.setPadding(padding, padding, padding, padding);
                        tvIscommon.setBackgroundResource(R.drawable.af_tx_set_illness);
                        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        lp.setMargins(0, 0, padding, padding);
                        tvIscommon.setLayoutParams(lp);
                        flContentSet.addView(tvIscommon);
                        tvIscommon.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int tag = (int) tvIscommon.getTag();
                                typeid = isCommon.get(tag).typeid;
                                /**获取分类文章*/
                                HttpHotData(typeid);
                            }
                        });
                    }
                    try {
                        /**默认加载 热门文章*/
                        HttpHotData(isCommon.get(0).typeid);
                    } catch (Exception e) {
                    }
                }
            }

            @Override
            public void onFailure(Call<List<GetAllTypesTalkBean>> call, Throwable t) {
                MyLogcat.jLog().e("百科 热门分类 onfail：");
            }
        });
    }

    private void HttpHotData(String id) {
        String id1 = "2a522e37f21843cc977a17f1bb664e7b";
        String page = "0";
        RetrofitClient.getinstance(getActivity()).create(GetService.class).TypesTalkId(id, page + "").enqueue(new Callback<List<BaikeHotTalkBean>>() {
            @Override
            public void onResponse(Call<List<BaikeHotTalkBean>> call, Response<List<BaikeHotTalkBean>> response) {
                if (response.isSuccessful()) {
                    MyLogcat.jLog().e("分类文章 onResponse:");
                    List<BaikeHotTalkBean> body = response.body();
                    for (BaikeHotTalkBean bean : body) {
                        itemHotData.add(bean);
                    }

                    if (isonRefresh) {
                        recyHot.refreshComplete(1);
                        isonRefresh = false;
                    }
                    init(body);
                }
            }

            @Override
            public void onFailure(Call<List<BaikeHotTalkBean>> call, Throwable t) {
                recyHot.refreshComplete(1);
                MyLogcat.jLog().e("分类文章 onFailure:");
            }
        });
    }

    BaikeHotAdapter hotTalkAdapter;
    boolean isonRefresh = false;
    LRecyclerViewAdapter mLRecyclerViewAdapter;

    private void init(List<BaikeHotTalkBean> body) {
        if (hotTalkAdapter == null) {

            hotTalkAdapter = new BaikeHotAdapter(body);
            LinearLayoutManager Manager = new LinearLayoutManager(getActivity());
            Manager.setOrientation(OrientationHelper.VERTICAL);
            recyHot.setLayoutManager(Manager);

            DividerLine dividerLine = new DividerLine(DividerLine.VERTICAL);
            dividerLine.setSize(1);
            dividerLine.setColor(R.color.lineback);
            recyHot.addItemDecoration(dividerLine);
            mLRecyclerViewAdapter = new LRecyclerViewAdapter(hotTalkAdapter);
            recyHot.setAdapter(mLRecyclerViewAdapter);
        } else {
            hotTalkAdapter.setData(body);
        }

    }

    @Override
    public void onPause() {
        super.onPause();
    }

}