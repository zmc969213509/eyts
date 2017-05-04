package com.guojianyiliao.eryitianshi.View.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.guojianyiliao.eryitianshi.MyUtils.base.BaseActivity;
import com.guojianyiliao.eryitianshi.MyUtils.bean.myCashCouponsBean;
import com.guojianyiliao.eryitianshi.MyUtils.interfaceservice.GetService;
import com.guojianyiliao.eryitianshi.MyUtils.manager.RetrofitClient;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.MyLogcat;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.SpUtils;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.UIUtils;
import com.guojianyiliao.eryitianshi.MyUtils.view.activity.InquiryActivityNew;
import com.guojianyiliao.eryitianshi.MyUtils.view.activity.view.OnReFlashListView.ReFlashListView;
import com.guojianyiliao.eryitianshi.R;
import com.guojianyiliao.eryitianshi.page.adapter.MyCashCouponsAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * 我的代金券页面
 */
public class MyCashCouponsActivity extends BaseActivity implements ReFlashListView.IReflashListener {

    @BindView(R.id.ivb_back_finsh)
    ImageButton ivbBackFinsh;
    @BindView(R.id.tv_foot_center)
    TextView tvFootCenter;

    private ReFlashListView lv_mycash;
    MyCashCouponsAdapter adapter;
    List<String> list = new ArrayList<>();
    String type;

    @Override
    protected void onStart() {
        super.onStart();
        Httpinit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_my_cash_coupons);
        ButterKnife.bind(this);

        tvFootCenter.setText("我的代金券");
        type = getIntent().getStringExtra("pay");
        showList();
        findView();

    }

    @OnClick(R.id.ivb_back_finsh)
    public void back() {
        finish();
    }

    List<myCashCouponsBean> couponsBeen = new ArrayList<>();

    private void findView() {

        View view = UIUtils.getinflate(R.layout.a_item_coupons_recy_gone);
        lv_mycash.addFooterView(view);

        // lv_mycash.setLayoutManager(new LinearLayoutManager(MyCashCouponsActivity.this, LinearLayoutManager.VERTICAL, false));
        //  lv_mycash.setHasFixedSize(true);
        lv_mycash.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (type != null && type.equals("pay")) {
                    Intent intent = new Intent();
                    /*intent.putExtra("money", (int) couponsBeen.get(position).getMoney());
                    intent.putExtra("id", couponsBeen.get(position).getId());
                    intent.putExtra("type", couponsBeen.get(position).getType());*/
                    // setResult(200, intent);
                    // finish();
                } else {
                    startActivity(new Intent(MyCashCouponsActivity.this, InquiryActivityNew.class));
                    finish();
                }
            }
        });

    }

    private void showList() {
        if (adapter == null) {
            lv_mycash = (ReFlashListView) findViewById(R.id.lv_mycash);
            lv_mycash.setInterface(this);
            // lv_mycash = (PullToRefreshListView) findViewById(R.id.lv_mycash);
            // lv_mycash.setOnRefreshListener(this);
            adapter = new MyCashCouponsAdapter(MyCashCouponsActivity.this, couponsBeen);
            lv_mycash.setAdapter(adapter);
            lv_mycash.setLoadMore(false);
        } else {
            adapter.onDateChange(couponsBeen);
        }
    }


    private void Httpinit() {

        for (int i = 0; i < 5; i++) {
            myCashCouponsBean myCashCouponsBean = new myCashCouponsBean();
            couponsBeen.add(myCashCouponsBean);
        }

        String userid = SpUtils.getInstance(this).get("userid", null);
        RetrofitClient.getinstance(this).create(GetService.class).getMyVoucher(userid).enqueue(new Callback<List<myCashCouponsBean>>() {
            @Override
            public void onResponse(retrofit2.Call<List<myCashCouponsBean>> call, Response<List<myCashCouponsBean>> response) {
                if (response.isSuccessful()) {
                    MyLogcat.jLog().e("onResponse:" + response.body().toString());
                    List<myCashCouponsBean> body = response.body();
                    for (myCashCouponsBean data : body) {
                        couponsBeen.add(data);
                    }

                }
            }

            @Override
            public void onFailure(retrofit2.Call<List<myCashCouponsBean>> call, Throwable t) {
                MyLogcat.jLog().e("onFailure:");
            }
        });


    }


    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onReflash() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                //获取最新数据
                Httpinit1();
                //通知界面显示
                showList();
                //通知listview 刷新数据完毕；
                lv_mycash.reflashComplete();
            }


        }, 2000);

    }

    @Override
    public void onLoadingMore() {

    }

    private void Httpinit1() {
        for (int i = 0; i < 2; i++) {
            myCashCouponsBean myCashCouponsBean = new myCashCouponsBean();
            couponsBeen.add(myCashCouponsBean);
        }
    }

    /*@Override
    public void onDownPullRefresh() {
        handler.sendEmptyMessage(0);
    }

    @Override
    public void onLoadingMore() {
        handler.sendEmptyMessage(1);
    }*/

    /*private Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0://下拉刷新
                    Httpinit1();
                    showList();
                    lv_mycash.onRefreshComplete();
                   *//* if (list.size()<10) {
                        lv_mycash.setLoadMore(true);
                    }*//*
                    break;
                case 1://加载更多
                    Httpinit1();
                    showList();
                    adapter.notifyDataSetChanged();
                    lv_mycash.onRefreshComplete();
                   *//* if (list.size()>=30) {
                        lv_mycash.setLoadMore(false);
                    }*//*
                    break;

                default:
                    break;
            }
        }
    };*/
}
