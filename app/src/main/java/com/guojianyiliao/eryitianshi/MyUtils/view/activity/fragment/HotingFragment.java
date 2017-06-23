package com.guojianyiliao.eryitianshi.MyUtils.view.activity.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.guojianyiliao.eryitianshi.MyUtils.adaper.zmc_BaikeWenzRecycleAdapter;
import com.guojianyiliao.eryitianshi.MyUtils.bean.BaikeHotTalkBean;
import com.guojianyiliao.eryitianshi.MyUtils.bean.GetAllTypesTalkBean;
import com.guojianyiliao.eryitianshi.MyUtils.customView.ShowTextLinearLayout;
import com.guojianyiliao.eryitianshi.MyUtils.interfaceservice.GetService;
import com.guojianyiliao.eryitianshi.MyUtils.manager.RetrofitClient;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.MyLogcat;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.rocketAnimLoadingUtil;
import com.guojianyiliao.eryitianshi.MyUtils.view.activity.ArticleDetailActivity;
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
public class HotingFragment extends Fragment implements ShowTextLinearLayout.onItemClickListener, zmc_BaikeWenzRecycleAdapter.onItemClickListener, rocketAnimLoadingUtil.Listener {

    private String TAG = "HotingFragment";

    @BindView(R.id.zmc_frg_baike_showtext)
    ShowTextLinearLayout showTextLinearLayout;
    @BindView(R.id.zmc_frg_baike_rcyview)
    RecyclerView recyclerView;

    zmc_BaikeWenzRecycleAdapter adapter = null;

    /**当前页码**/
    private int currentPage = 0;
    /**当前是否能上拉加载更多**/
    private boolean canLoadMore = false;
    /**当前是否还有更多数据**/
    private boolean haveMoreData = true;
    /**当前是否正在加载更多数据**/
    private boolean isLoading = false;

    View loadView;
    rocketAnimLoadingUtil animLoadingUtil;

    /**文章**/
    List<BaikeHotTalkBean> itemHotData = new ArrayList<>();//热门
    /**分类**/
    List<GetAllTypesTalkBean> isCommon = new ArrayList<>();//分类

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.a_baike_item_hot, container, false);
        ButterKnife.bind(this, view);
        loadView = view.findViewById(R.id.loadig_anim_view);
        animLoadingUtil = new rocketAnimLoadingUtil(loadView);
        animLoadingUtil.startAnim();
        animLoadingUtil.setListener(this);
        HttpTypesData();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private Integer etag = 0;//记录每次点击的位置

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
                        isCommon.add(data);
                    }

                    List<String> data = new ArrayList<String>();
                    for (int i = 0; i < isCommon.size() ; i++) {
                        data.add(isCommon.get(i).typename);
                    }

                    showTextLinearLayout.setData(data);
                    showTextLinearLayout.setListener(HotingFragment.this);

                    try {
                        /**默认加载 热门文章*/
                        currentPage = 0;
                        typeid = isCommon.get(0).typeid;
                        HttpHotData();
                        showTextLinearLayout.setColoer(0,R.color.red1);
                    } catch (Exception e) {
                    }
                }
            }

            @Override
            public void onFailure(Call<List<GetAllTypesTalkBean>> call, Throwable t) {
                MyLogcat.jLog().e("百科 热门分类 onfail：");
                animLoadingUtil.loadFail();
            }
        });
    }

    /**当前加载的文章类型**/
    private String typeid = "";
    private void HttpHotData() {
        if(adapter != null){
            adapter.setDataNoMore(false);
        }
        RetrofitClient.getinstance(getActivity()).create(GetService.class).TypesTalkId(typeid, currentPage + "").enqueue(new Callback<List<BaikeHotTalkBean>>() {
            @Override
            public void onResponse(Call<List<BaikeHotTalkBean>> call, Response<List<BaikeHotTalkBean>> response) {
                if (response.isSuccessful()) {
                    List<BaikeHotTalkBean> body;
                    if(canLoadMore){
                        Log.e("HotingFragment", "HttpHotData ---- canLoadMore");
                        if(currentPage == 0){
                            Log.e("HotingFragment", "currentPage = 0");
                            isLoading = false;
                            haveMoreData = true;
                            animLoadingUtil.loadSucc();
                            body = response.body();
                            itemHotData.removeAll(itemHotData);
                            for (BaikeHotTalkBean bean : body) {
                                itemHotData.add(bean);
                            }
                            showRcyView(body);
                        }else{
                            Log.e("HotingFragment", "currentPage > 0");
                            body = response.body();
                            if(body == null || body.size() == 0){
                                haveMoreData = false;
                                adapter.setDataNoMore(true);
//                                Toast.makeText(getActivity(), "数据见底啦", Toast.LENGTH_SHORT).show();
                            }else{
                                for (BaikeHotTalkBean bean : body) {
                                    itemHotData.add(bean);
                                }
                                showRcyView(body);
                            }
                        }
                    }else{
                        animLoadingUtil.loadSucc();
                        body = response.body();
                        itemHotData.removeAll(itemHotData);
                        for (BaikeHotTalkBean bean : body) {
                            itemHotData.add(bean);
                        }
                        showRcyView(body);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<BaikeHotTalkBean>> call, Throwable t) {
                animLoadingUtil.loadFail();
            }
        });
    }
    LinearLayoutManager manager;
    private void showRcyView(List<BaikeHotTalkBean> body) {
        if(adapter == null){
            adapter = new zmc_BaikeWenzRecycleAdapter(body,1,getActivity());
            adapter.setListener(this);
            manager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(manager);
            recyclerView.setAdapter(adapter);
            //设置滑动监听
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                }

                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    int lastVisibleItemPosition = manager.findLastVisibleItemPosition();
                    Log.d("HotingFragment", "lastVisibleItemPosition = " + lastVisibleItemPosition);
                    Log.d("HotingFragment", "adapter.getItemCount() = " + adapter.getItemCount());
                    if(canLoadMore) {
                        if (lastVisibleItemPosition + 1 == adapter.getItemCount()) {
//                            Log.d("HotingFragment", "当前是否能加载更多 " + canLoadMore);
//                            Log.d("HotingFragment", "当前是否正在加载更多数据 " + isLoading);
//                            Log.d("HotingFragment", "当前是否还有更多数据 " + haveMoreData);
                            if (!isLoading) {
                                Log.e("HotingFragment", "正在加载更多数据");
                                isLoading = true;
                                if (haveMoreData) {
                                    currentPage++;
                                    Log.e("HotingFragment", "currentPage = " + currentPage);
                                    HttpHotData();
                                }
                            }
                        }
                    }
                }
            });
        }else{
            if(canLoadMore){
                adapter.setFlag(2);
            }else{
                adapter.setFlag(1);
            }
            adapter.Update(body);
        }
        int lastItem = manager.findLastVisibleItemPosition();
        if(lastItem + 1 == adapter.getItemCount()){ // 当前数据一页就可以显示完
            canLoadMore = false;
            adapter.setDataNoMore(true);
        }
    }


    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onItemClick(View v) {
        int tag = (int) v.getTag();
        typeid = isCommon.get(tag).typeid;
        if(tag == 0){
            canLoadMore = false;
        }else{
            canLoadMore = true;
        }
        animLoadingUtil.startAnim();
        currentPage = 0;
        /**获取分类文章*/
        HttpHotData();
        showTextLinearLayout.setColoer(tag,R.color.red1);
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(getActivity(), ArticleDetailActivity.class);
        intent.putExtra("cyclopediaid", itemHotData.get(position).getCyclopediaid());
        intent.putExtra("title", itemHotData.get(position).getTitle());
        intent.putExtra("content", itemHotData.get(position).getContent());
        intent.putExtra("icon", itemHotData.get(position).getIcon());
        intent.putExtra("site", "app.html");
        startActivity(intent);
    }

    @Override
    public void onAnimClick() {
        animLoadingUtil.startAnim();
        if(isCommon.size() == 0){
            HttpTypesData();
        }else if(!TextUtils.isEmpty(typeid)){
            HttpHotData();
        }else if(TextUtils.isEmpty(typeid)){
            HttpTypesData();
        }
    }
}