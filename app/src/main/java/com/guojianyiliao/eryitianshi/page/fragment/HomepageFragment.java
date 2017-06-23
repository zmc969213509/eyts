package com.guojianyiliao.eryitianshi.page.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.guojianyiliao.eryitianshi.Data.Http_data;
import com.guojianyiliao.eryitianshi.Data.entity.Consult;
import com.guojianyiliao.eryitianshi.Data.entity.DiseaseBanner;
import com.guojianyiliao.eryitianshi.Data.entity.FindAllHot;
import com.guojianyiliao.eryitianshi.Data.entity.Inquiry;
import com.guojianyiliao.eryitianshi.R;
import com.guojianyiliao.eryitianshi.Utils.BannerImageLoader;
import com.guojianyiliao.eryitianshi.Utils.ListenerManager;
import com.guojianyiliao.eryitianshi.Utils.db.HomeBannerDao;
import com.guojianyiliao.eryitianshi.Utils.db.HomeDoctorDao;
import com.guojianyiliao.eryitianshi.Utils.db.HomeEassayDao;
import com.guojianyiliao.eryitianshi.Utils.db.HomeFindAllHotDao;
import com.guojianyiliao.eryitianshi.View.activity.ConsultPageActivity;
import com.guojianyiliao.eryitianshi.View.activity.DoctorparticularsActivity;
import com.guojianyiliao.eryitianshi.View.activity.HealthconditionActivity;
import com.guojianyiliao.eryitianshi.View.activity.LectureoomActivity;
import com.guojianyiliao.eryitianshi.View.activity.RegistrationAtivity;
import com.guojianyiliao.eryitianshi.View.activity.WebActivity;
import com.guojianyiliao.eryitianshi.page.adapter.HomeDoctorRecommendAdapter;
import com.guojianyiliao.eryitianshi.page.view.AutoVerticalScrollTextView;
import com.guojianyiliao.eryitianshi.page.view.ListViewForScrollView;
import com.guojianyiliao.eryitianshi.MyUtils.view.activity.view.RoundCornerImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerClickListener;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import okhttp3.Call;

/**
 * Created by Administrator on 2016/9/14 0014.
 * 首页
 */
public class HomepageFragment extends Fragment {
    View view;
    private AutoVerticalScrollTextView verticalScrollTV;
    private int number = 0;
    private boolean isRunning = true;
    private Banner diseaseBannerView;
    private ScrollView scrollView;
    private LinearLayout ll_patriarch_lecture_room, ll_home_order_registration, ll_home_health_record, ll_diagnosistreat_manage, ll_home_article_more, ll_home_doctor_more;

    private ListViewForScrollView home_listView;

    HomeDoctorRecommendAdapter adapter;


    private List<String> strings = new ArrayList<>();

    Gson gson = new Gson();

    List<FindAllHot> findAllHotList;

    List<Consult> consultList = new ArrayList<>();

    List<Inquiry> inquiryList = new ArrayList<>();


    private TextView tv_home_essay_title, tv_home_essay_title1;


    private RoundCornerImageView tv_home_essay_icon, tv_home_essay_icon1;


    private TextView tv_home_essay_content, tv_home_essay_content1;


    private TextView tv_home_essay_time, tv_home_essay_time1;

    private LinearLayout ll_home_essay, ll_home_essay1;


    private TextView tv_home_essay_collectnumber, tv_home_essay_collectnumber1;

    HomeDoctorDao db;

    HomeFindAllHotDao findAllHotdb;

    HomeEassayDao homeEassaydb;


    RelativeLayout rl_loading;

    Context c;
    HomeBannerDao homeBannerdb;

    List<DiseaseBanner> bannerList = new ArrayList<>();
    List<String> bannerData = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_homepage, null);

        try {
            homeBannerdb = new HomeBannerDao(getContext());
            findView();


            findAllHotinit();


            homeEssayinit();

            bannerinit();


            homeDoctorInit();


            init();

        } catch (Exception e) {
            e.printStackTrace();
        }


        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        homeBannerdb.closedb();
    }

    private void bannerinit() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpUtils
                        .post()
                        .url(Http_data.http_data + "/FindBannerListByCategoryCode1")
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
                                homeBannerdb.delbanner();

                                bannerList.clear();

                                bannerData.clear();

                                for (Iterator it = leclist.iterator(); it.hasNext(); ) {
                                    DiseaseBanner diseaseBanner = (DiseaseBanner) it.next();

                                    homeBannerdb.addbanner(diseaseBanner);

                                    bannerList.add(diseaseBanner);

                                    bannerData.add(diseaseBanner.getCover());
                                }


                                handler.sendEmptyMessage(10);

                            }

                        });

            }
        }).start();
    }


    private void findView() {
        db = new HomeDoctorDao(getContext());

        findAllHotdb = new HomeFindAllHotDao(getContext());

        homeEassaydb = new HomeEassayDao(getContext());

        findAllHotList = new ArrayList<>();

        diseaseBannerView = (Banner) view.findViewById(R.id.bannerView);

        diseaseBannerView.setDelayTime(10000);
        scrollView = (ScrollView) view.findViewById(R.id.scrollView);

        scrollView.smoothScrollTo(1, 1);

        tv_home_essay_title = (TextView) view.findViewById(R.id.tv_home_essay_title);

        tv_home_essay_title1 = (TextView) view.findViewById(R.id.tv_home_essay_title1);

        tv_home_essay_icon = (RoundCornerImageView) view.findViewById(R.id.tv_home_essay_icon);

        tv_home_essay_icon1 = (RoundCornerImageView) view.findViewById(R.id.tv_home_essay_icon1);

        tv_home_essay_content = (TextView) view.findViewById(R.id.tv_home_essay_content);

        tv_home_essay_content1 = (TextView) view.findViewById(R.id.tv_home_essay_content1);

        tv_home_essay_time = (TextView) view.findViewById(R.id.tv_home_essay_time);

        tv_home_essay_time1 = (TextView) view.findViewById(R.id.tv_home_essay_time1);

        ll_home_essay = (LinearLayout) view.findViewById(R.id.ll_home_essay);

        ll_home_essay1 = (LinearLayout) view.findViewById(R.id.ll_home_essay1);

        rl_loading = (RelativeLayout) view.findViewById(R.id.rl_loading);

        rl_loading.setVisibility(View.GONE);

        tv_home_essay_collectnumber = (TextView) view.findViewById(R.id.tv_home_essay_collectnumber);
        tv_home_essay_collectnumber1 = (TextView) view.findViewById(R.id.tv_home_essay_collectnumber1);


        ll_patriarch_lecture_room = (LinearLayout) view.findViewById(R.id.ll_patriarch_lecture_room);


        ll_home_order_registration = (LinearLayout) view.findViewById(R.id.ll_home_order_registration);


        ll_home_health_record = (LinearLayout) view.findViewById(R.id.ll_home_health_record);


        ll_diagnosistreat_manage = (LinearLayout) view.findViewById(R.id.ll_diagnosistreat_manage);


        ll_home_article_more = (LinearLayout) view.findViewById(R.id.ll_home_article_more);


        ll_home_doctor_more = (LinearLayout) view.findViewById(R.id.ll_home_doctor_more);

        home_listView = (ListViewForScrollView) view.findViewById(R.id.home_listView);


        verticalScrollTV = (AutoVerticalScrollTextView) view.findViewById(R.id.textview_auto_roll);


        scrollView.setVerticalScrollBarEnabled(false);


        new Thread(new Runnable() {
            @Override
            public void run() {
                if (db.findHomeDoctor().size() != 0 && homeEassaydb.findHomeEassay().size() != 0 && homeBannerdb.findHomebanner().size() != 0) {
                    inquiryList.addAll(db.findHomeDoctor());
                    handler.sendEmptyMessage(4);
                    handler.sendEmptyMessage(5);
                    handler.sendEmptyMessage(11);


                } else {

                    handler.sendEmptyMessage(8);

                }

            }
        }).start();

        ll_patriarch_lecture_room.setOnClickListener(listener);
        ll_home_order_registration.setOnClickListener(listener);
        ll_home_health_record.setOnClickListener(listener);
        ll_diagnosistreat_manage.setOnClickListener(listener);
        ll_home_doctor_more.setOnClickListener(listener);
        ll_home_article_more.setOnClickListener(listener);
        ll_home_essay.setOnClickListener(listener);
        ll_home_essay1.setOnClickListener(listener);
        home_listView.setOnItemClickListener(lvlistener);


        verticalScrollTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), WebActivity.class);
                intent.putExtra("url", findAllHotList.get(number % strings.size()).getSite());
                startActivity(intent);
            }
        });

        diseaseBannerView.setOnBannerClickListener(new OnBannerClickListener() {
            @Override
            public void OnBannerClick(int position) {

                if (bannerList.get(position - 1).getCyclopediaid() == null || bannerList.get(position - 1).getCyclopediaid().equals("")) {
                    Intent intent = new Intent(getContext(), WebActivity.class);
                    intent.putExtra("url", bannerList.get(position - 1).getSite());
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getContext(), ConsultPageActivity.class);
                    intent.putExtra("information", bannerList.get(position - 1).getCyclopediaid());
                    intent.putExtra("collect", "0");
                    startActivity(intent);
                }
            }
        });
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        scrollView.smoothScrollTo(1, 1);
    }

    private void findAllHotinit() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpUtils
                        .post()
                        .url(Http_data.http_data + "/FindAllHot")
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {
                                handler.sendEmptyMessage(7);
                            }

                            @Override
                            public void onResponse(String response, int id) {
                                Type listtype = new TypeToken<LinkedList<FindAllHot>>() {
                                }.getType();

                                LinkedList<FindAllHot> leclist = gson.fromJson(response, listtype);


                                findAllHotdb.delHomeFindAllHot();

                                strings.clear();

                                findAllHotList.clear();

                                for (Iterator it = leclist.iterator(); it.hasNext(); ) {
                                    FindAllHot findAllHot = (FindAllHot) it.next();

                                    findAllHotList.add(findAllHot);

                                    strings.add(findAllHot.getTitle());

                                    findAllHotdb.addHomeFindAllHot(findAllHot);
                                }
                                handler.sendEmptyMessage(3);
                            }
                        });
            }
        }).start();
    }


    private void homeEssayinit() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpUtils
                        .post()
                        .url(Http_data.http_data + "/FindCyclopediaRandomTwo")
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {

                            }

                            @Override
                            public void onResponse(String response, int id) {
                                Type listtype = new TypeToken<LinkedList<Consult>>() {
                                }.getType();
                                LinkedList<Consult> leclist = gson.fromJson(response, listtype);

                                homeEassaydb.delHomeEassay();

                                consultList.clear();

                                for (Iterator it = leclist.iterator(); it.hasNext(); ) {
                                    Consult consult = (Consult) it.next();
                                    consultList.add(consult);

                                    homeEassaydb.addHomeEassay(consult);
                                }

                                handler.sendEmptyMessage(1);

                            }
                        });

            }
        }).start();

    }


    private void homeDoctorInit() {
        OkHttpUtils
                .post()
                .url(Http_data.http_data + "/FindDoctorRandom")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        handler.sendEmptyMessage(9);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        inquiryList.clear();

                        Type listtype = new TypeToken<LinkedList<Inquiry>>() {
                        }.getType();

                        LinkedList<Inquiry> leclist = gson.fromJson(response, listtype);

                        for (Iterator it = leclist.iterator(); it.hasNext(); ) {

                            Inquiry inquiry = (Inquiry) it.next();

                            inquiryList.add(inquiry);
                        }

                        handler.sendEmptyMessage(2);

                    }
                });
    }

    private void init() {

        adapter = new HomeDoctorRecommendAdapter(getContext(), inquiryList);

        home_listView.setAdapter(adapter);
    }


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {

                case 0:

                    verticalScrollTV.next();

                    number++;

                    verticalScrollTV.setText(strings.get(number % strings.size()));
                    break;

                case 1:

                    tv_home_essay_title.setText(consultList.get(0).getTitle());
                    tv_home_essay_title1.setText(consultList.get(1).getTitle());


                    tv_home_essay_content.setText(consultList.get(0).getContent());
                    tv_home_essay_content1.setText(consultList.get(1).getContent());


                    tv_home_essay_time.setText(consultList.get(0).getTime());
                    tv_home_essay_time1.setText(consultList.get(1).getTime());


                    ImageLoader.getInstance().displayImage(consultList.get(0).getIcon(), tv_home_essay_icon);
                    ImageLoader.getInstance().displayImage(consultList.get(1).getIcon(), tv_home_essay_icon1);


                    tv_home_essay_collectnumber.setText(consultList.get(0).getCollectCount() + "");
                    tv_home_essay_collectnumber1.setText(consultList.get(1).getCollectCount() + "");


                    rl_loading.setVisibility(View.GONE);

                    scrollView.smoothScrollTo(1, 1);
                    break;

                case 2:

                    adapter.notifyDataSetChanged();

                    db.deldotor();
                    for (int i = 0; i < inquiryList.size(); i++) {
                        db.addHomeDoctor(inquiryList.get(i));
                    }
                    break;

                case 3:

                    verticalScrollTV.setText(strings.get(0));


                    new Thread() {
                        @Override
                        public void run() {
                            while (isRunning) {

                                SystemClock.sleep(5000);
                                handler.sendEmptyMessage(0);
                            }
                        }
                    }.start();

                    break;

                case 4:

                    adapter.notifyDataSetChanged();

                    break;


                case 5:
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            if (homeEassaydb.findHomeEassay().size() != 0) {

                                consultList.addAll(homeEassaydb.findHomeEassay());
                            }

                            handler.sendEmptyMessage(1);
//                            ???????????  fuck
                        }
                    }).start();
                    break;

                case 7:
                    List<FindAllHot> findList;
                    findList = findAllHotdb.findHomeFindAllHot();
                    findAllHotList.addAll(findList);

                    if (findList.size() != 0) {

                        for (int i = 0; i < findList.size(); i++) {

                            strings.add(findList.get(i).getTitle());
                        }
                        handler.sendEmptyMessage(3);

                    }
                    break;

                case 8:

                    rl_loading.setVisibility(View.VISIBLE);

                    break;

                case 9:
//                    Toast.makeText(getContext(), "网络连接失败", Toast.LENGTH_SHORT).show();

                    rl_loading.setVisibility(View.GONE);

                    break;

                case 10:
                    diseaseBannerView.setImageLoader(new BannerImageLoader());

                    diseaseBannerView.setImages(bannerData);

                    diseaseBannerView.start();
                    break;

                case 11:
                    bannerList.clear();

                    bannerData.clear();

                    bannerList = homeBannerdb.findHomebanner();

                    for (int i = 0; i < bannerList.size(); i++) {
                        bannerData.add(bannerList.get(i).getCover());
                    }

                    handler.sendEmptyMessage(10);
                    break;
            }
        }
    };

    private AdapterView.OnItemClickListener lvlistener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(getContext(), DoctorparticularsActivity.class);
            intent.putExtra("doctot_id", inquiryList.get(position).getId());
            intent.putExtra("doctor_chatcost", inquiryList.get(position).getChatCost());
            startActivity(intent);
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();

        isRunning = false;

        db.closedb();

        homeEassaydb.closedb();

        findAllHotdb.closedb();
    }


    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {

                case R.id.ll_patriarch_lecture_room:
                    Intent intent = new Intent(getContext(), LectureoomActivity.class);
                    startActivity(intent);
                    break;

                case R.id.ll_home_order_registration:
                    Intent intent1 = new Intent(getContext(), RegistrationAtivity.class);
                    startActivity(intent1);
                    break;

                case R.id.ll_home_health_record:
                    Intent intent2 = new Intent(getContext(), HealthconditionActivity.class);
                    startActivity(intent2);
                    break;

                case R.id.ll_home_doctor_more:
//                    Intent intent3 = new Intent(getContext(), InquiryActivity.class);
//                    startActivity(intent3);
                    break;
                //
                case R.id.ll_diagnosistreat_manage:
                    //
                    ListenerManager.getInstance().sendBroadCast("显示诊疗页面");
                    break;
                //
                case R.id.ll_home_article_more:
                    //
                    ListenerManager.getInstance().sendBroadCast("显示资讯页面");

                    break;


                case R.id.ll_home_essay1:
                    Intent intent4 = new Intent(getContext(), ConsultPageActivity.class);
                    intent4.putExtra("information", consultList.get(1).getId() + "");
                    intent4.putExtra("collect", "0");
                    startActivity(intent4);
                    break;


                case R.id.ll_home_essay:
                    Intent intent5 = new Intent(getContext(), ConsultPageActivity.class);
                    intent5.putExtra("information", consultList.get(0).getId() + "");
                    intent5.putExtra("collect", "0");
                    startActivity(intent5);
                    break;
            }
        }
    };


    @Override
    public void onDetach() {
        super.onDetach();

    }


}
