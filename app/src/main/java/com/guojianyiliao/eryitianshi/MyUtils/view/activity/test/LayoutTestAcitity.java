package com.guojianyiliao.eryitianshi.MyUtils.view.activity.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.guojianyiliao.eryitianshi.Data.entity.DiseaseBanner;
import com.guojianyiliao.eryitianshi.MyUtils.view.activity.view.MyScrollView;
import com.guojianyiliao.eryitianshi.R;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */

public class LayoutTestAcitity extends AppCompatActivity implements MyScrollView.OnScrollListener  {

   /* @BindView(R.id.bannerView)
    Banner bannerView;*/

    /*private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 10:
                    bannerView.setImageLoader(new BannerImageLoader());

                    bannerView.setImages(bannerData);

                    bannerView.start();
                    break;

            }

        }
    };*/
    private static final String TAG = "MainActivity";
    private MyScrollView mScrollView;
    private LinearLayout ll_tab;
    private RelativeLayout iv_pic;
    private int picBottom;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aa_test_activity_main);
        //getSupportActionBar().hide();
        mScrollView = (MyScrollView) findViewById(R.id.mScrollView);
        mScrollView.setOnScrollListener(this);
        ll_tab = (LinearLayout) findViewById(R.id.ll_tab);
        //iv_pic = (RelativeLayout) findViewById(R.id.rl_title);
        findViewById(R.id.ll_main).getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                onScroll(mScrollView.getScrollY());
            }
        });
        ll_tab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             Log.e(TAG,"tab点击了");
            }
        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
          //  picBottom = iv_pic.getBottom();
        }
    }

    @Override
    public void onScroll(int scrollY) {
        int top = Math.max(scrollY, 500);
        Log.i(TAG,"scrollY = " +scrollY + " , picBottom = " + picBottom+"////top ="+top);
        ll_tab.layout(0, top, ll_tab.getWidth(), top + ll_tab.getHeight());
    }

    ArrayList arrayList = new ArrayList();
    List<DiseaseBanner> bannerList = new ArrayList<>();
    List<String> bannerData = new ArrayList<>();
    Gson gson = new Gson();

    class myDaper extends BaseAdapter {
        @Override
        public int getCount() {
            return arrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return null;
        }
    }

    private void init() {
    } /*{
        bannerinit();
        bannerView.setDelayTime(10000);
        bannerView.setOnBannerClickListener(new OnBannerClickListener() {
            @Override
            public void OnBannerClick(int position) {

                if (bannerList.get(position - 1).getCyclopediaid() == null || bannerList.get(position - 1).getCyclopediaid().equals("")) {
                    Intent intent = new Intent(LayoutTestAcitity.this, WebActivity.class);
                    intent.putExtra("url", bannerList.get(position - 1).getSite());
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(LayoutTestAcitity.this, ConsultPageActivity.class);
                    intent.putExtra("information", bannerList.get(position - 1).getCyclopediaid());
                    intent.putExtra("collect", "0");
                    startActivity(intent);
                }
            }
        });
    }*/

    private void bannerinit() {
    } /*{
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpUtils
                        .post()
                        //.url(Http_data.http_data + "/FindBannerListByCategoryCode1") banner/getHomeBanners
                        .url(Http_data.http_data + "/banner/getHomeBanners")
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {

                            }

                            @Override
                            public void onResponse(String response, int id) {
                                Type listtype = new TypeToken<LinkedList<DiseaseBanner>>() {
                                }.getType();
                                LinkedList<DiseaseBanner> leclist = gson.fromJson(response, listtype);
                                bannerList.clear();
                                bannerData.clear();

                                for (Iterator it = leclist.iterator(); it.hasNext(); ) {
                                    DiseaseBanner diseaseBanner = (DiseaseBanner) it.next();
                                    bannerList.add(diseaseBanner);
                                    bannerData.add(diseaseBanner.getCover());
                                }
                                handler.sendEmptyMessage(10);

                            }

                        });

            }
        }).start();
    }*/
}
