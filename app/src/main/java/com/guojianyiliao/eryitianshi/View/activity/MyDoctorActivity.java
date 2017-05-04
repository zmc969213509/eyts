package com.guojianyiliao.eryitianshi.View.activity;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.guojianyiliao.eryitianshi.Data.Http_data;
import com.guojianyiliao.eryitianshi.Data.entity.MyDoctor;
import com.guojianyiliao.eryitianshi.MyUtils.interfaceservice.EventData;
import com.guojianyiliao.eryitianshi.MyUtils.manager.BusProvider;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.MyLogcat;
import com.guojianyiliao.eryitianshi.R;
import com.guojianyiliao.eryitianshi.Utils.MyBaseActivity;
import com.guojianyiliao.eryitianshi.Utils.SharedPsaveuser;
import com.guojianyiliao.eryitianshi.page.adapter.MyDoctorAdapter;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;
import com.squareup.otto.Subscribe;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
/**
 * description:  我的医生界面
 * autour: jnluo jnluo5889@126.com
 * version: Administrator
*/

public class MyDoctorActivity extends MyBaseActivity {
    @BindView(R.id.ivb_back_finsh)
    ImageButton ivbBackFinsh;
    @BindView(R.id.tv_foot_center)
    TextView tvFootCenter;
    private SwipeMenuListView lv_mydoctor;
    Gson gson;
    MyDoctorAdapter adapter;
    List<MyDoctor> list;

    private RelativeLayout rl_nonetwork, rl_loading;
    SharedPsaveuser sp;

    @Subscribe
    public void ottoevent(EventData data) {
        MyLogcat.jLog().e("otto:" + data.getContent());
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_doctor);
        ButterKnife.bind(this);

        BusProvider.getInstance().register(this);

        tvFootCenter.setText("我的医生 S");
        try {

            findView();
            init();
            httpinit();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.ivb_back_finsh)
    public void back() {
        finish();
    }

    private void findView() {
        lv_mydoctor = (SwipeMenuListView) findViewById(R.id.lv_mydoctor);

        rl_nonetwork = (RelativeLayout) findViewById(R.id.rl_nonetwork);
        rl_loading = (RelativeLayout) findViewById(R.id.rl_loading);

        lv_mydoctor.setOnItemClickListener(lvlistener);
        lv_mydoctor.setVerticalScrollBarEnabled(false);

        ImageLoader imageLoader = ImageLoader.getInstance();

        lv_mydoctor.setOnScrollListener(new PauseOnScrollListener(imageLoader, true, true));

        DisplayMetrics dm = new DisplayMetrics();
        dm = this.getResources().getDisplayMetrics();
        final float density = dm.density;

        //
        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                SwipeMenuItem openItem = new SwipeMenuItem(getApplicationContext());
                openItem.setBackground(new ColorDrawable(Color.RED));
                openItem.setWidth((int) (100 * density));
                openItem.setTitle("删除");
                openItem.setTitleSize(17);
                openItem.setTitleColor(Color.WHITE);
                menu.addMenuItem(openItem);
            }
        };

        lv_mydoctor.setMenuCreator(creator);
        lv_mydoctor.setOnMenuItemClickListener(onmentlistener);
    }

    private void init() {
        sp = new SharedPsaveuser(this);
        list = new ArrayList<>();
        adapter = new MyDoctorAdapter(MyDoctorActivity.this, list);
        lv_mydoctor.setAdapter(adapter);

    }


    private SwipeMenuListView.OnMenuItemClickListener onmentlistener = new SwipeMenuListView.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
            OkHttpUtils
                    .post()
                    .url(Http_data.http_data + "/DeleteMyDoctor")
                    .addParams("userId", sp.getTag().getId() + "")
                    .addParams("doctorId", list.get(position).getDoctorId() + "")
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            Toast.makeText(MyDoctorActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onResponse(String response, int id) {
                            if (response.equals("0")) {
                                Toast.makeText(MyDoctorActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                                list.clear();
                                httpinit();
                            } else {
                                Toast.makeText(MyDoctorActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

            return false;
        }
    };


    private void httpinit() {
        gson = new Gson();
        OkHttpUtils
                .post()
                .url(Http_data.http_data + "/FindDoctorListByUserId")
                .addParams("userId", sp.getTag().getId() + "")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(MyDoctorActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();
                        rl_loading.setVisibility(View.GONE);
                        rl_nonetwork.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Type listtype = new TypeToken<LinkedList<MyDoctor>>() {
                        }.getType();
                        LinkedList<MyDoctor> leclist = gson.fromJson(response, listtype);
                        for (Iterator it = leclist.iterator(); it.hasNext(); ) {
                            MyDoctor inquiry = (MyDoctor) it.next();
                            list.add(inquiry);
                        }
                        adapter.notifyDataSetChanged();
                        rl_loading.setVisibility(View.GONE);
                    }
                });
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:

                    break;
                case 1:

                    break;
            }
        }
    };

    private AdapterView.OnItemClickListener lvlistener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(MyDoctorActivity.this, DoctorparticularsActivity.class);
            intent.putExtra("doctot_id", list.get(position).getDoctorId() + "");
            startActivity(intent);
        }
    };


    @Override
    protected void onDestroy() {
        super.onDestroy();
        BusProvider.getInstance().unregister(this);
    }
}
