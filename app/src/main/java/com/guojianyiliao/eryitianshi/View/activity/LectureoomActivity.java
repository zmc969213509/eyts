package com.guojianyiliao.eryitianshi.View.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.guojianyiliao.eryitianshi.Data.Http_data;
import com.guojianyiliao.eryitianshi.Data.entity.Lecture;
import com.guojianyiliao.eryitianshi.R;
import com.guojianyiliao.eryitianshi.page.adapter.LectureroomAdapter;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import okhttp3.Call;

/**
 * 课堂页面
 * */

public class LectureoomActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LectureroomAdapter adapter;
    private RelativeLayout rl_nonetwork, rl_loading;

    List<Lecture> list;
    Gson gson;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_lectureroom);

        try {

            findView();
            init();
            gson = new Gson();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }
    //XRefreshView xRefreshView;
    private void findView() {

      //  xRefreshView = (XRefreshView) findViewById(R.id.xrefreshview);
      //  xRefreshView.setPullLoadEnable(true);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
      //  adapter = new LectureroomAdapter(LectureoomActivity.this, list);
      //  recyclerView.setAdapter(adapter);

        rl_nonetwork = (RelativeLayout) findViewById(R.id.rl_nonetwork);
        rl_loading = (RelativeLayout) findViewById(R.id.rl_loading);

        //recyclerView.setVerticalScrollBarEnabled(false);//显示滚动条

    }


    private void init() {


        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpUtils
                        .post()
                        .url(Http_data.http_data + "/FindLectureAll")
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {
                                handler.sendEmptyMessage(0);
                            }

                            @Override
                            public void onResponse(String response, int id) {

                                Type listtype = new TypeToken<LinkedList<Lecture>>() {
                                }.getType();
                                LinkedList<Lecture> leclist = gson.fromJson(response, listtype);


                                for (Iterator it = leclist.iterator(); it.hasNext(); ) {
                                    Lecture lecture = (Lecture) it.next();
                                    list.add(lecture);
                                }
                                handler.sendEmptyMessage(1);
                            }
                        });
            }
        }).start();


    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 0:
                    rl_loading.setVisibility(View.GONE);
                    rl_nonetwork.setVisibility(View.VISIBLE);
                    break;
                case 1:
                //    adapter.setList(list);
                    rl_loading.setVisibility(View.GONE);
                    break;


            }
        }
    };


    /**
     * 这是一条分割线
     */

//    LecturereoomTopBanner banner_lectureoom_top;
//
//    LectureroomAmongBanner banner_lectureoom_among;
//
//    TextView tv_lectureroom, tv_lectureroom_among_bannertext, tv_lecure_beforetext;
//
//    ListViewForScrollView lecture_room_listView;
//
//    ScrollView scrollView;
//
//    List<String> list = new ArrayList<>();
//    LectureListAdapter adapter;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            //透明状态栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            //透明导航栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//        }
//
//
//        setContentView(R.layout.fragment_lectureroom);
//        findView();
//        init();
//
//
//    }
//
//    private void findView() {
//        banner_lectureoom_top = (LecturereoomTopBanner) findViewById(R.id.banner_lectureoom_top);
//        tv_lectureroom = (TextView) findViewById(R.id.tv_lectureroom);
//        banner_lectureoom_among = (LectureroomAmongBanner) findViewById(R.id.banner_lectureoom_among);
//        tv_lectureroom_among_bannertext = (TextView) findViewById(R.id.tv_lectureroom_among_bannertext);
//        lecture_room_listView = (ListViewForScrollView) findViewById(R.id.lecture_room_listView);
//        scrollView = (ScrollView) findViewById(R.id.scrollView);
//        tv_lecure_beforetext = (TextView) findViewById(R.id.tv_lecure_beforetext);
//
//        lecture_room_listView.setVerticalScrollBarEnabled(false);
//        scrollView.setVerticalScrollBarEnabled(false);
//
//        TextPaint tp = tv_lectureroom.getPaint();
//        tp.setFakeBoldText(true);
//
//        TextPaint tp1 = tv_lectureroom_among_bannertext.getPaint();
//        tp1.setFakeBoldText(true);
//
//        TextPaint tp2 = tv_lecure_beforetext.getPaint();
//        tp2.setFakeBoldText(true);
//    }
//
//    private void init() {
//        list.add("1");
//        list.add("2");
//        list.add("3");
//        list.add("4");
//        list.add("5");
//
//        adapter = new LectureListAdapter(this, list);
//        lecture_room_listView.setAdapter(adapter);
//        adapter.notifyDataSetChanged();
//    }
//
//
//    //banner开始滑动
//    public void onResume() {
//        super.onResume();
//        banner_lectureoom_top.bannerStartPlay();
//        banner_lectureoom_among.bannerStartPlay();
//    }
//
//
//    //banner停止滑动
//    @Override
//    public void onPause() {
//        super.onPause();
//        banner_lectureoom_top.bannerStopPlay();
//        banner_lectureoom_among.bannerStopPlay();
//    }
}
