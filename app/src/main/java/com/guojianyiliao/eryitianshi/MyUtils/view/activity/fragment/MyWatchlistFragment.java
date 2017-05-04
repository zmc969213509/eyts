package com.guojianyiliao.eryitianshi.MyUtils.view.activity.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.guojianyiliao.eryitianshi.MyUtils.bean.MyFocus;
import com.guojianyiliao.eryitianshi.MyUtils.interfaceservice.GetService;
import com.guojianyiliao.eryitianshi.MyUtils.manager.RetrofitClient;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.MyLogcat;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.SpUtils;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.UIUtils;
import com.guojianyiliao.eryitianshi.MyUtils.view.activity.view.OnReFlashRecyclerView.LoadRefreshRecyclerView;
import com.guojianyiliao.eryitianshi.MyUtils.view.activity.view.OnReFlashRecyclerView.LoadViewCreator;
import com.guojianyiliao.eryitianshi.MyUtils.view.activity.view.OnReFlashRecyclerView.RefreshRecyclerView;
import com.guojianyiliao.eryitianshi.MyUtils.view.activity.view.OnReFlashRecyclerView.RefreshViewCreator;
import com.guojianyiliao.eryitianshi.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * description:我的关注
 * autour: jnluo jnluo5889@126.com
 * date: 2017/4/20 18:28
 * update: 2017/4/20
 * version: Administrator
 */

public class MyWatchlistFragment extends Fragment implements RefreshRecyclerView.OnRefreshListener, LoadRefreshRecyclerView.OnLoadMoreListener {


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String userid = SpUtils.getInstance(getActivity()).get("userid", null);
        httpData(userid);
    }


    LoadRefreshRecyclerView recycler;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.a_fragment_mywatchlist, container, false);
        recycler = (LoadRefreshRecyclerView) view.findViewById(R.id.recycler);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        showRecycler(data);

        recycler.setOnRefreshListener(this);
        recycler.setOnLoadMoreListener(this);
    }

    List<MyFocus> data = new ArrayList<>();

    MyAdapter myAdapter;


    private void showRecycler(final List<MyFocus> data) {
        if (myAdapter == null) {
            myAdapter = new MyAdapter(data);
            recycler.setLayoutManager(new LinearLayoutManager(getActivity()) {{
                setOrientation(OrientationHelper.VERTICAL);
            }});
            recycler.addRefreshViewCreator(new RefreshViewCreator() {
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
            recycler.addLoadViewCreator(new LoadViewCreator() {
                @Override
                public View getLoadView(Context context, ViewGroup parent) {
                    View view = UIUtils.getinflate(R.layout.layout_emptyview);
                    return view;
                }

                @Override
                public void onPull(int currentDragHeight, int loadViewHeight, int currentLoadStatus) {

                }

                @Override
                public void onLoading() {

                }

                @Override
                public void onStopLoad() {

                }
            });
            recycler.setAdapter(myAdapter);
        } else {
            myAdapter.setData(data);
        }
    }

    @Override
    public void onRefresh() {
        UIUtils.getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                MyLogcat.jLog().e("Runnable onRefresh");
                recycler.onStopRefresh();
            }
        }, 2000);
    }

    @Override
    public void onLoad() {
        UIUtils.getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {

                MyLogcat.jLog().e("Runnable onLoad");
                recycler.onStopLoad();
                View mLoadView = recycler.mLoadView;
                TextView tvtime = (TextView) mLoadView.findViewById(R.id.tv_listview_header_state);
                tvtime.setText("android");


            }
        }, 2000);
    }

    class MyAdapter extends RecyclerView.Adapter {

        List<MyFocus> body;

        public MyAdapter(List<MyFocus> body) {
            this.body = body;
        }

        public void setData(List<MyFocus> body) {
            this.body = body;
            this.notifyDataSetChanged();
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = UIUtils.getinflate(R.layout.item_rcy_mywatchlist);
            MyHolder myHolder = new MyHolder(view);
            return myHolder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            MyHolder Myholder = (MyHolder) holder;
            Myholder.tvWatchName.setText(body.get(position).name);
            ImageLoader.getInstance().displayImage(body.get(position).icon, Myholder.ivWatchIcon);
        }

        @Override
        public int getItemCount() {
            return body.size();
        }
    }

    class MyHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_watch_icon)
        CircleImageView ivWatchIcon;
        @BindView(R.id.tv_watch_name)
        TextView tvWatchName;
        @BindView(R.id.tv_watch_time)
        TextView tvWatchTime;
        @BindView(R.id.tv_watch_gz)
        TextView tvWatchGz;

        public MyHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }


    private void httpData(String userid) {
        RetrofitClient.getinstance(getActivity()).create(GetService.class).getMyFocus(userid).enqueue(new Callback<List<MyFocus>>() {
            @Override
            public void onResponse(Call<List<MyFocus>> call, Response<List<MyFocus>> response) {
                if (response.isSuccessful()) {
                    MyLogcat.jLog().e("我的关注：onResponse:");
                    List<MyFocus> body = response.body();

                    showRecycler(body);
                }
            }

            @Override
            public void onFailure(Call<List<MyFocus>> call, Throwable t) {
                MyLogcat.jLog().e("我的关注：onFailure:");
            }
        });
    }
}
