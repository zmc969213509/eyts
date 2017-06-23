package com.guojianyiliao.eryitianshi.MyUtils.view.activity.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.guojianyiliao.eryitianshi.MyUtils.adaper.zmc_GrowUpRecyclerviewAdapter;
import com.guojianyiliao.eryitianshi.MyUtils.bean.AllLecturesBean;
import com.guojianyiliao.eryitianshi.MyUtils.interfaceservice.GetService;
import com.guojianyiliao.eryitianshi.MyUtils.manager.RetrofitClient;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.MyLogcat;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.SharedPreferencesTools;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.SpUtils;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.TimeUtil;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.ToolUtils;
import com.guojianyiliao.eryitianshi.MyUtils.view.activity.GrowUpDetailcopy;
import com.guojianyiliao.eryitianshi.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * description: 成长树页面
 * autour: jnluo jnluo5889@126.com
 * date: 2017/4/18 9:35
 * update: 2017/4/18
 * version: Administrator
 */
public class GrowUpFragment extends Fragment implements zmc_GrowUpRecyclerviewAdapter.onItemClickListener {
    String userid;
    View view;

    /**当前加载数据页码**/
    private int currentPage = 0;
    /**加载数据最大页码**/
    private int maxPage;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userid = SharedPreferencesTools.GetUsearId(getActivity(),"userSave","userId");
    }

    SwipeRefreshLayout refreshLayout;
    RecyclerView recyclerView;
    zmc_GrowUpRecyclerviewAdapter myadaper = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.a_fragment_growup, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swlayout);
//        HttpData("0");
        refreshLayout.setColorSchemeResources(R.color.backgroundblue,R.color.view);
        refreshLayout.post(new Runnable() {
            @Override
            public void run() {
                Log.e("GrowUpFragment","post 第一次进入刷新数据");
                HttpData();
                refreshLayout.setRefreshing(true);
            }
        });
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.e("GrowUpFragment","正在刷新数据");
                currentPage = 0;
                HttpData();
            }
        });


    }

    /**当前是否正在加载更多数据**/
    private boolean isLoading = false;

    private void showList() {
        Log.e("GrowUpFragment","showlist");
        if (myadaper == null) {
            final GridLayoutManager manager = new GridLayoutManager(getActivity(),1,GridLayoutManager.VERTICAL,false);
            recyclerView.setLayoutManager(manager);
            myadaper = new zmc_GrowUpRecyclerviewAdapter(getActivity(),data,userid);
            recyclerView.setAdapter(myadaper);
            myadaper.setOnItemClickListener(this);
            //设置滑动监听
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                }

                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    Log.d("test", "onScrolled");

                    int lastVisibleItemPosition = manager.findLastVisibleItemPosition();
                    if (lastVisibleItemPosition + 1 == myadaper.getItemCount()) {
                        Log.d("test", "loading executed");

                        boolean isRefreshing = refreshLayout.isRefreshing();
                        if (isRefreshing) {
                            myadaper.notifyItemRemoved(myadaper.getItemCount());
                            return;
                        }
                        if (!isLoading) {
                            isLoading = true;
                            if(currentPage > maxPage){

                            }else if(maxPage == currentPage){
                                Toast.makeText(getActivity(),"数据见底啦",Toast.LENGTH_SHORT).show();
                                myadaper.setDataNoMore(isLoading);
                                isLoading = false;
                                currentPage ++;
                            }else{
                                currentPage ++;
                                HttpData();
                            }
                        }
                    }
                }
            });
            MyLogcat.jLog().e("showList：" + data.size());
        } else {
            myadaper.Updata(data);
            MyLogcat.jLog().e("showList：notifyDataSetChanged" );
        }
    }

    List<String> page = new ArrayList<String>() {{
        add("");
    }};

    List<AllLecturesBean.ListBean> data = new ArrayList<>();

    /**
     * 获取数据
     */
    public void HttpData() {
        String userid = SharedPreferencesTools.GetUsearId(getActivity(),"userSave","userId");
        RetrofitClient.getinstance(getActivity()).create(GetService.class).getAllLectures(currentPage+"", userid).enqueue(new Callback<AllLecturesBean>() {
            @Override
            public void onResponse(Call<AllLecturesBean> call, Response<AllLecturesBean> response) {
                Log.e("GrowUpFragment","访问成功");
                if(currentPage == 0){ //刷新
                    isLoading = false;
                    if(myadaper != null){
                        myadaper.setDataNoMore(isLoading);
                    }
                    if (response.isSuccessful()) {
                        if (data.size() != 0) {
                            data.clear();
                        }
                        Log.e("GrowUpFragment","解析数据");
                        MyLogcat.jLog().e("获取说说列 w onResponse：" + response.body().toString());
                        maxPage = response.body().getMaxPage();
                        List<AllLecturesBean.ListBean> list = response.body().getList();
                        for (AllLecturesBean.ListBean bean : list) {
                            MyLogcat.jLog().e("List Bean:" + bean.getUser().getName());
                            data.add(bean);
                        }
                        MyLogcat.jLog().e("List Bean size:" + data.size());
                        if(data == null || data.size() == 0){
                            refreshLayout.setRefreshing(false);
                            Toast.makeText(getActivity(),"暂无数据",Toast.LENGTH_SHORT).show();
                            showList();
                        }else{
                            showList();
                            refreshLayout.setRefreshing(false);
//                        Toast.makeText(getActivity(),"刷新成功",Toast.LENGTH_SHORT).show();
                        }
                    }
                }else{ //加载更多
                    List<AllLecturesBean.ListBean> list = response.body().getList();
                    maxPage = response.body().getMaxPage();
                    for (AllLecturesBean.ListBean bean : list) {
                        MyLogcat.jLog().e("List Bean:" + bean.getUser().getName());
                        data.add(bean);
                        showList();
                    }
                }
                isLoading = false;
            }

            @Override
            public void onFailure(Call<AllLecturesBean> call, Throwable t) {
                MyLogcat.jLog().e("获取说说列表 e onFailure：");
                Log.e("GrowUpFragment","获取数据失败");
                if(currentPage == 0){
                    refreshLayout.setRefreshing(false);
                    Toast.makeText(getActivity(),"刷新失败，请检查网络",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getActivity(),"加载数据失败，请检查网络",Toast.LENGTH_SHORT).show();
                }
                isLoading = false;
            }
        });
    }

    private int statusHeight;

    /**
     * 获得状态栏的高度
     */
    public int getStatusHeight(Context context) {
        int statusHeight = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height").get(object).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }

    /**当前选中的item项**/
    private int Selected = -1;
    @Override
    public void onItemClick(View v, int position) {
        AllLecturesBean.ListBean bean = data.get(position);
        Selected = position;
        if(bean != null){
            Intent intent = new Intent(getActivity(),GrowUpDetailcopy.class);
            intent.putExtra("eid",bean.getEid());
            intent.putExtra("isfocus",bean.isIsfocus());
            intent.putExtra("icon",bean.getUser().getIcon());
            intent.putExtra("name",bean.getUser().getName());
            startActivityForResult(intent,0);
        }
    }

    @Override
    public void Fabulous(ImageView ivGrowDef, TextView tvGrowEagrees, String eid, Integer eagrees,int position) {
        HttpDZ(eid,position);
    }

    @Override
    public void unFabulous(ImageView ivGrowDef, TextView tvGrowEagrees, String eid, Integer eagrees,int position) {
        HttpDisDZ(eid,position);
    }

    @Override
    public void Follow(TextView tvGrowGz, String focusedid,int position) {
        HttpGz(focusedid);
    }

    @Override
    public void unFollow(TextView tvGrowGz, String focusedid,int position) {
        HttpDelGz(focusedid);
    }

    /**
     * 请求点赞
     */
    private void HttpDZ(String eid, final int position) {
        String time = TimeUtil.currectTimess();
        Log.e("mybind","id:" + userid + "/time:" + time + "/eid:" + eid);
        RetrofitClient.getinstance(getActivity()).create(GetService.class).agreeWithEssay(eid, userid, time).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    Log.e("dianzan","onResponse:" + response.body().toString());
                    if ("true".equals(response.body().toString())) {
                        int eagrees = data.get(position).getEagrees();
                        data.get(position).setEagrees(eagrees+1);
                        data.get(position).setIsagree(true);
                        myadaper.UpdateItem(data,position,"dianzan");
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                MyLogcat.jLog().e("请求点赞 onFailure:");
            }
        });
    }

    /**
     * 取消点赞
     */
    private void HttpDisDZ(String eid, final int position) {
        MyLogcat.jLog().e("id:" + userid + "//" + eid);
        RetrofitClient.getinstance(getActivity()).create(GetService.class).disAgreeWithEssay(eid, userid).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    MyLogcat.jLog().e("取消点赞 onResponse:" + response.body().toString());
                    if ("true".equals(response.body().toString())) {
                        int eagrees = data.get(position).getEagrees();
                        data.get(position).setEagrees(eagrees - 1);
                        data.get(position).setIsagree(false);
                        myadaper.UpdateItem(data,position,"dianzan");
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                MyLogcat.jLog().e("取消点赞 onFailure:");
            }
        });
    }

    /**
     * 请求关注
     */
    private void HttpGz(final String focusedid) {
        RetrofitClient.getinstance(getActivity()).create(GetService.class).addMyFocus(userid, "", focusedid, "").enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    MyLogcat.jLog().e("请求关注 onResponse:" + response.body().toString());
                    if ("true".equals(response.body().toString())) {
                        ToolUtils.showToast(getActivity(), "关注成功！", Toast.LENGTH_SHORT);
                        List<Integer> position = new ArrayList<Integer>();//需要更新的控件的位置
                        for (int i = 0; i <data.size() ; i++) {
                            if(data.get(i).getUser().getUserid().equals(focusedid)){
                                data.get(i).setIsfocus(true);
                                position.add(i);
                            }
                        }
                        myadaper.Updata(data,position,"guanzhu");
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                MyLogcat.jLog().e("请求关注 onFailure:");
            }
        });
    }

    /**
     * 取消关注
     */
    private void HttpDelGz(final String focusedid) {
        RetrofitClient.getinstance(getActivity()).create(GetService.class).delMyFocus(userid, focusedid).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    MyLogcat.jLog().e("取消关注 onResponse:" + response.body().toString());
                    if ("true".equals(response.body().toString())) {
                        ToolUtils.showToast(getActivity(), "取消成功！", Toast.LENGTH_SHORT);
                        List<Integer> position = new ArrayList<Integer>();//需要更新的控件的位置
                        for (int i = 0; i <data.size() ; i++) {//将数据中所有该用户改为相对应的状态
                            if(data.get(i).getUser().getUserid().equals(focusedid)){
                                data.get(i).setIsfocus(false);
                                position.add(i);
                            }
                        }
                        myadaper.Updata(data,position,"guanzhu");
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                MyLogcat.jLog().e("取消关注 onFailure:");
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 0){
            if(resultCode == Activity.RESULT_OK){
                //TODO  点击数据发生改变  返回进行数据更新
            }
        }
    }
}