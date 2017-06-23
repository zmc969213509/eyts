package com.guojianyiliao.eryitianshi.View.activity;
//
//import android.content.Intent;
//import android.graphics.Color;
//import android.graphics.drawable.ColorDrawable;
//import android.os.Bundle;
//import android.util.DisplayMetrics;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.ImageButton;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.baoyz.swipemenulistview.SwipeMenu;
//import com.baoyz.swipemenulistview.SwipeMenuCreator;
//import com.baoyz.swipemenulistview.SwipeMenuItem;
//import com.baoyz.swipemenulistview.SwipeMenuListView;
//import com.google.gson.Gson;
//import com.google.gson.reflect.TypeToken;
//import com.guojianyiliao.eryitianshi.Data.Http_data;
//import com.guojianyiliao.eryitianshi.Data.entity.Information;
//import com.guojianyiliao.eryitianshi.MyUtils.interfaceservice.EventData;
//import com.guojianyiliao.eryitianshi.MyUtils.manager.BusProvider;
//import com.guojianyiliao.eryitianshi.MyUtils.utlis.MyLogcat;
//import com.guojianyiliao.eryitianshi.R;
//import com.guojianyiliao.eryitianshi.Utils.IListener;
//import com.guojianyiliao.eryitianshi.Utils.ListenerManager;
//import com.guojianyiliao.eryitianshi.Utils.MyBaseActivity;
//import com.guojianyiliao.eryitianshi.Utils.SharedPsaveuser;
//import com.guojianyiliao.eryitianshi.page.adapter.CharactersafeAdapter;
//import com.nostra13.universalimageloader.core.ImageLoader;
//import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;
//import com.zhy.http.okhttp.OkHttpUtils;
//import com.zhy.http.okhttp.callback.StringCallback;
//
//import java.lang.reflect.Type;
//import java.util.ArrayList;
//import java.util.Iterator;
//import java.util.LinkedList;
//import java.util.List;
//
//import butterknife.BindView;
//import butterknife.ButterKnife;
//import butterknife.OnClick;
//import okhttp3.Call;
//
///**
// * 我的收藏界面
// */
//public class MycollectActivity extends MyBaseActivity implements IListener {
//
//    @BindView(R.id.ivb_back_finsh)
//    ImageButton ivbBackFinsh;
//    @BindView(R.id.tv_foot_center)
//    TextView tvFootCenter;
//    private SwipeMenuListView lv_collect;
//    CharactersafeAdapter adapter;
//    List<Information> list;
//
//    private RelativeLayout rl_nonetwork, rl_loading;
//    Gson gson;
//    SharedPsaveuser sp;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_mycollect);
//        ButterKnife.bind(this);
//
//        tvFootCenter.setText("我的收藏 p");
//        tvFootCenter.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                MyLogcat.jLog().e("otto.post");
//                EventData eventData = new EventData();
//                eventData.setContent("2017年4月15日");
//                BusProvider.getInstance().post(eventData);
//            }
//        });
//
//        try {
//
//            ListenerManager.getInstance().registerListtener(this);
//            findView();
//            init();
//            initHttp();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//
//
//    }
//
//    @OnClick(R.id.ivb_back_finsh)
//    public void back() {
//        finish();
//    }
//
//    private void findView() {
//        lv_collect = (SwipeMenuListView) findViewById(R.id.lv_collect);
//
//        rl_nonetwork = (RelativeLayout) findViewById(R.id.rl_nonetwork);
//        rl_loading = (RelativeLayout) findViewById(R.id.rl_loading);
//        lv_collect.setVerticalScrollBarEnabled(false);
//
//        lv_collect.setOnItemClickListener(lvlitener);
//
//        ImageLoader imageLoader = ImageLoader.getInstance();
//
//        lv_collect.setOnScrollListener(new PauseOnScrollListener(imageLoader, true, true));
//
//        DisplayMetrics dm = new DisplayMetrics();
//        dm = this.getResources().getDisplayMetrics();
//        final float density = dm.density;
//
//        SwipeMenuCreator creator = new SwipeMenuCreator() {
//            @Override
//            public void create(SwipeMenu menu) {
//                SwipeMenuItem openItem = new SwipeMenuItem(getApplicationContext());
//                openItem.setBackground(new ColorDrawable(Color.RED));
//                openItem.setWidth((int) (75 * density));
//                openItem.setTitle("删除");
//                openItem.setTitleSize(17);
//                openItem.setTitleColor(Color.WHITE);
//                menu.addMenuItem(openItem);
//            }
//        };
//
//        lv_collect.setMenuCreator(creator);
//        lv_collect.setOnMenuItemClickListener(onmentlistener);
//    }
//
//    private void init() {
//        gson = new Gson();
//        list = new ArrayList<>();
//        adapter = new CharactersafeAdapter(this, list);
//        lv_collect.setAdapter(adapter);
//    }
//
//    private void initHttp() {
//        sp = new SharedPsaveuser(this);
//        OkHttpUtils
//                .post()
//                .url(Http_data.http_data + "/findCollectList")
//                .addParams("userId", sp.getTag().getId() + "")
//                .build()
//                .execute(new StringCallback() {
//                    @Override
//                    public void onError(Call call, Exception e, int id) {
//                        Toast.makeText(MycollectActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();
//                        rl_loading.setVisibility(View.GONE);
//                        rl_nonetwork.setVisibility(View.VISIBLE);
//                    }
//
//                    @Override
//                    public void onResponse(String response, int id) {
//                        list.clear();
//                        Type listtype = new TypeToken<LinkedList<Information>>() {
//                        }.getType();
//                        LinkedList<Information> leclist = gson.fromJson(response, listtype);
//                        for (Iterator it = leclist.iterator(); it.hasNext(); ) {
//                            Information information = (Information) it.next();
//                            list.add(information);
//                        }
//                        adapter.notifyDataSetChanged();
//                        rl_loading.setVisibility(View.GONE);
//                    }
//                });
//    }
//
//    private AdapterView.OnItemClickListener lvlitener = new AdapterView.OnItemClickListener() {
//        @Override
//        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//            Intent intent = new Intent(MycollectActivity.this, ConsultPageActivity.class);
//            intent.putExtra("collect", "1");
//            intent.putExtra("information", list.get(position).getCyclopediaId());
//            startActivity(intent);
//        }
//    };
//
//    private SwipeMenuListView.OnMenuItemClickListener onmentlistener = new SwipeMenuListView.OnMenuItemClickListener() {
//        @Override
//        public boolean onMenuItemClick(final int position, SwipeMenu menu, int index) {
//            OkHttpUtils
//                    .post()
//                    .url(Http_data.http_data + "/DeleteCollectById")
//                    .addParams("id", list.get(position).getId() + "")
//                    .build()
//                    .execute(new StringCallback() {
//                        @Override
//                        public void onError(Call call, Exception e, int id) {
//                            Toast.makeText(MycollectActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();
//                        }
//
//                        @Override
//                        public void onResponse(String response, int id) {
//                            if (response.equals("0")) {
//                                Toast.makeText(MycollectActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
//                                list.clear();
//                                initHttp();
//                            } else {
//                                Toast.makeText(MycollectActivity.this, "删除失败，请稍后再试", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    });
//
//            return false;
//        }
//    };
//
//
//    @Override
//    public void notifyAllActivity(String str) {
//        if (str.equals("更新我的收藏")) {
//            initHttp();
//        }
//    }
//}
