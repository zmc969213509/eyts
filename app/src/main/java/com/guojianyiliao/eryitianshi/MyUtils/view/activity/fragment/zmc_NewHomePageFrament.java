package com.guojianyiliao.eryitianshi.MyUtils.view.activity.fragment;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.guojianyiliao.eryitianshi.Data.entity.DiseaseBanner;
import com.guojianyiliao.eryitianshi.MyUtils.adaper.HotTalkAdapter;
import com.guojianyiliao.eryitianshi.MyUtils.bean.HotTalkBean;
import com.guojianyiliao.eryitianshi.MyUtils.bean.SearchDetailsBean;
import com.guojianyiliao.eryitianshi.MyUtils.customView.MyListview;
import com.guojianyiliao.eryitianshi.MyUtils.customView.ShowTextLinearLayout;
import com.guojianyiliao.eryitianshi.MyUtils.interfaceservice.GetService;
import com.guojianyiliao.eryitianshi.MyUtils.manager.RetrofitClient;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.MyLogcat;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.UIUtils;
import com.guojianyiliao.eryitianshi.MyUtils.view.activity.AllDoctorActivity;
import com.guojianyiliao.eryitianshi.MyUtils.view.activity.ArticleDetailActivity;
import com.guojianyiliao.eryitianshi.MyUtils.view.activity.DiseasesDetailActivity;
import com.guojianyiliao.eryitianshi.MyUtils.view.activity.DoctorDetailActivity;
import com.guojianyiliao.eryitianshi.MyUtils.view.activity.MyInquiryActivity;
import com.guojianyiliao.eryitianshi.MyUtils.view.activity.SearActivity;
import com.guojianyiliao.eryitianshi.MyUtils.view.activity.YYGHSelectActivity;
import com.guojianyiliao.eryitianshi.R;
import com.guojianyiliao.eryitianshi.Utils.BannerImageLoader;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerClickListener;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import q.rorbin.badgeview.Badge;
import q.rorbin.badgeview.QBadgeView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.guojianyiliao.eryitianshi.R.id.rand_order_icon_1;

/**
 * Created by zmc on 2017/5/21.
 */

public class zmc_NewHomePageFrament extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener {

    @BindView(R.id.bannerView)
    Banner bannerView;
    /**轮播控件高度**/
    int height = 0 ;


    @BindView(R.id.frg_home_myscrollview)
    com.guojianyiliao.eryitianshi.MyUtils.customView.MyScrollView myScrollView;

    /**
     * 搜索框
     */
    @BindView(R.id.zmc_frg_home_include)
    View include_view;
    @BindView(R.id.iv_sms)
    ImageView ivSms;
    @BindView(R.id.et_home_search)
    TextView sear_tv;
    @BindView(R.id.rl_background_color)
    RelativeLayout rlBackgroundColor;

    /**
     * 常见疾病
     */
    @BindView(R.id.zmc_frg_home_showtext)
    ShowTextLinearLayout showTextLinearLayout;
    @BindView(R.id.rl_see_order_more)
    TextView rlSeeOrderMore;
    @BindView(R.id.onListview)
    MyListview onListview;

    /**
     * 在线问诊 预约挂号 家长课堂 我的诊疗 视图
     */
    @BindView(R.id.rl_line_set)
    RelativeLayout rlLineSet;
    @BindView(R.id.rl_order_yugh)
    RelativeLayout rlOrderYugh;
    @BindView(R.id.rl_parent_set)
    RelativeLayout rlParentSet;
    @BindView(R.id.rl_inquiry_set)
    RelativeLayout rlInquirySet;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = UIUtils.getinflate(R.layout.zmc_frg_home);
        ButterKnife.bind(this, view);
        view.findViewById(R.id.zmc_viewgroud).getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {//绘图完成后的监听  可以获取控件高宽
                if(height == 0){
                    height = bannerView.getHeight();
                    myScrollView.smoothScrollTo(0,0);
                }
                myScrollView.setLinstener(new com.guojianyiliao.eryitianshi.MyUtils.customView.MyScrollView.onScrollChangedLinstener() {
                    @Override
                    public void onScrollChange(int l, int t, int oldl, int oldt) {
                        if(t >= 0){
                            if(height >= t){ //当前滑动的距离小于bannerview的高
                                if(CurrentAlpha != (int)((t*1.0f)/height*255)){
                                    try {
                                        CurrentAlpha = (int)((t*1.0f)/height*255);
                                        setActionBarAlpha(CurrentAlpha);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }else{
                                if(CurrentAlpha != 255){
                                    CurrentAlpha = 255;
                                    try {
                                        setActionBarAlpha(CurrentAlpha);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                    }
                });
            }
        });
        return view;
    }
    /**当前透明度**/
    private int CurrentAlpha = 0;
    private void setActionBarBgDrawable(Drawable bgDrawable) {
        mBgDrawable = bgDrawable;
        mBgDrawable.setAlpha(CurrentAlpha);
        include_view.setBackgroundDrawable(mBgDrawable);
    }
    private Drawable mBgDrawable;
    private void setActionBarAlpha(int alpha) throws Exception {
        mBgDrawable.setAlpha(alpha);
        include_view.setBackgroundDrawable(mBgDrawable);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ColorDrawable bgDrawable = new ColorDrawable(getResources().getColor(R.color.view));
        setActionBarBgDrawable(bgDrawable);
        /** 解析出来的数据有错时不会挂掉*/
        try {
            initBannerData();//首页广告
            initOrder();//随机三名医生
            HttpCommonData();//常见疾病
            initHot();//热门话题
        } catch (Exception e) {
            MyLogcat.jLog().e("" + e.getMessage());
        }
        /**消息指示器 */
        Badge badge = new QBadgeView(getActivity()).bindTarget(ivSms).setBadgeNumber(5);
        rlSeeOrderMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AllDoctorActivity.class));
            }
        });

        sear_tv.setOnClickListener(this);
        ivSms.setOnClickListener(this);
    }

    /**
     * 在线问诊
     */
    @OnClick(R.id.rl_line_set)
    public void lineset() {
        startActivity(new Intent(getActivity(), AllDoctorActivity.class));
    }

    /**
     * 预约挂号
     */
    @OnClick(R.id.rl_order_yugh)
    public void ygh() {
        startActivity(new Intent(getActivity(), YYGHSelectActivity.class));
    }

    /**
     * 显示更多热门话题
     */
    @OnClick(R.id.zmc_frg_home_more)
    public void moreArticle(){
        EventBus.getDefault().post("baike_article");
    }


    /**
     * 家长课堂
     */
    @OnClick(R.id.rl_parent_set)
    public void classroom() {
        EventBus.getDefault().post("baike_school");
//        startActivity(new Intent(getActivity(), SchoolActivityLryv.class));
    }

    /**
     * 我的诊疗
     */
    @OnClick(R.id.rl_inquiry_set)
    public void myinquiry() {
        startActivity(new Intent(getContext(), MyInquiryActivity.class));
    }

    private void initHot() {
        initHotData();
    }

    List<HotTalkBean> hotData = new ArrayList();
    /**
     * 获取热门文章
     */
    private void initHotData() {
        RetrofitClient.getinstance(getActivity()).create(GetService.class).HotTalkData().enqueue(new Callback<List<HotTalkBean>>() {
            @Override
            public void onResponse(Call<List<HotTalkBean>> call, Response<List<HotTalkBean>> response) {
                if (response.isSuccessful()) {
                    MyLogcat.jLog().e("onResponse:" + response.body().toString());
                    List<HotTalkBean> body = response.body();
                    hotData.addAll(body);
                    showListView();
                }
            }

            @Override
            public void onFailure(Call<List<HotTalkBean>> call, Throwable t) {
                MyLogcat.jLog().e("onFailure:");
            }
        });
    }

    HotTalkAdapter hotTalkAdapter;

    public void showListView() {
        if (hotTalkAdapter == null) {
            hotTalkAdapter = new HotTalkAdapter(hotData);
            onListview.setAdapter(hotTalkAdapter);
            onListview.setOnItemClickListener(this);
        } else {
            hotTalkAdapter.setData(hotData);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getActivity(), ArticleDetailActivity.class);
        intent.putExtra("cyclopediaid", hotData.get(position).getCyclopediaid());
        intent.putExtra("title", hotData.get(position).getTitle());
        intent.putExtra("content", hotData.get(position).getContent());
        intent.putExtra("icon", hotData.get(position).getIcon());
        intent.putExtra("site", "app.html");
        startActivity(intent);
    }


    /**
     * 随机医生的详情页面
     */
    @BindView(R.id.foot_rand_order)
    RelativeLayout randDocView;
    @BindView(R.id.ll_foot_1)
    LinearLayout randDoc1;
    @BindView(R.id.ll_foot_2)
    LinearLayout randDoc2;
    @BindView(R.id.ll_foot_3)
    LinearLayout randDoc3;

    @BindView(rand_order_icon_1)
    CircleImageView randOrderIcon1;
    @BindView(R.id.rand_order_name_1)
    TextView randOrderName1;
    @BindView(R.id.rand_order_type_1)
    TextView randOrderType1;
    @BindView(R.id.rand_order_detail_1)
    TextView randOrderDetail1;
    @BindView(R.id.rand_order_icon_2)
    CircleImageView randOrderIcon2;
    @BindView(R.id.rand_order_name_2)
    TextView randOrderName2;
    @BindView(R.id.rand_order_type_2)
    TextView randOrderType2;
    @BindView(R.id.rand_order_detail_2)
    TextView randOrderDetail2;
    @BindView(R.id.rand_order_icon_3)
    CircleImageView randOrderIcon3;
    @BindView(R.id.rand_order_name_3)
    TextView randOrderName3;
    @BindView(R.id.rand_order_type_3)
    TextView randOrderType3;
    @BindView(R.id.rand_order_detail_3)
    TextView randOrderDetail3;
    List<SearchDetailsBean.Doctors> orderData = new ArrayList();

    private void initOrder() {
        OrderData();
        randDoc1.setOnClickListener(this);
        randDoc2.setOnClickListener(this);
        randDoc3.setOnClickListener(this);
    }

    /**
     * 获取随机医生
     */
    private void OrderData() {

        RetrofitClient.getinstance(getActivity()).create(GetService.class).RanDomDrData().enqueue(new Callback<List<SearchDetailsBean.Doctors>>() {
            @Override
            public void onResponse(Call<List<SearchDetailsBean.Doctors>> call, Response<List<SearchDetailsBean.Doctors>> response) {
                if (response.isSuccessful()) {
                    randDocView.setVisibility(View.VISIBLE);
                    MyLogcat.jLog().e("ran order onResponse:");
                    List<SearchDetailsBean.Doctors> body = response.body();
                    for (SearchDetailsBean.Doctors order : body) {
                        orderData.add(order);
                    }
                    MyLogcat.jLog().e("order size :" + orderData.size());
                }
                try {
                    randOrderName1.setText(orderData.get(0).getName());//NullPointerException，name没抽取变量，擦
                    ImageLoader.getInstance().displayImage(orderData.get(0).getIcon(), randOrderIcon1);
                    randOrderType1.setText(orderData.get(0).getTitle());
                    randOrderDetail1.setText("擅长：" + orderData.get(0).getAdept());

                    randOrderName2.setText(orderData.get(1).getName());//NullPointerException，name没抽取变量，擦
                    ImageLoader.getInstance().displayImage(orderData.get(1).getIcon(), randOrderIcon2);
                    randOrderType2.setText(orderData.get(1).getTitle());
                    randOrderDetail2.setText("擅长：" + orderData.get(1).getAdept());

                    randOrderName3.setText(orderData.get(2).getName());//NullPointerException，name没抽取变量，擦
                    ImageLoader.getInstance().displayImage(orderData.get(2).getIcon(), randOrderIcon3);
                    randOrderType3.setText(orderData.get(2).getTitle());
                    randOrderDetail3.setText("擅长：" + orderData.get(2).getAdept());
                } catch (Exception e) {
                    MyLogcat.jLog().e("" + e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<List<SearchDetailsBean.Doctors>> call, Throwable t) {
                MyLogcat.jLog().e("ran order onFailure:");
            }
        });
    }

    private void initBannerData() {
        NannerData();
        bannerView.setDelayTime(10000);
        bannerView.setOnBannerClickListener(new OnBannerClickListener() {
            @Override
            public void OnBannerClick(int position) {

                if (bannerList.get(position - 1).getCyclopediaid() == null || bannerList.get(position - 1).getCyclopediaid().equals("")) {
//                    Intent intent = new Intent(getActivity(), WebActivity.class);
//                    intent.putExtra("url", bannerList.get(position - 1).getSite());
//                    startActivity(intent);
                    Toast.makeText(getActivity(),"呀   数据消失在遥远的二次元啦！！！",Toast.LENGTH_SHORT).show();
                } else {
//                    Intent intent = new Intent(getActivity(), ConsultPageActivity.class);
//                    intent.putExtra("information", bannerList.get(position - 1).getCyclopediaid());
//                    intent.putExtra("collect", "0");
//                    startActivity(intent);
                    Intent intent = new Intent(getActivity(), ArticleDetailActivity.class);
                    intent.putExtra("cyclopediaid", bannerList.get(position - 1).getCyclopediaid());
                    intent.putExtra("site", bannerList.get(position - 1).getSite());
                    intent.putExtra("title", "");
                    intent.putExtra("content", "");
                    intent.putExtra("icon",  bannerList.get(position - 1).getCover());
                    startActivity(intent);
                }
            }
        });

    }

    List<DiseaseBanner> bannerList = new ArrayList<>();
    List<String> bannerData = new ArrayList<>();

    private void NannerData() {
        RetrofitClient.getinstance(getActivity()).create(GetService.class).BannerData().enqueue(new Callback<List<DiseaseBanner>>() {
            @Override
            public void onResponse(Call<List<DiseaseBanner>> call, Response<List<DiseaseBanner>> response) {
                if (response.isSuccessful()) {
                    MyLogcat.jLog().e("banner success:" + response.body().toString());
                    List<DiseaseBanner> body = response.body();
                    for (DiseaseBanner banner : body) {
                        MyLogcat.jLog().e("getCover :" + banner.getCover());
                        bannerList.add(banner);
                        bannerData.add(banner.getCover());
                        handler.sendEmptyMessage(10);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<DiseaseBanner>> call, Throwable t) {
                MyLogcat.jLog().e("banner onfailure:" + call.toString());
            }
        });
    }

    /**
     * 更多疾病
     */
    @OnClick(R.id.rl_see_illness_more)
    public void moreDis(){
        EventBus.getDefault().post("baike_dis");
    }

    /**常见疾病**/
    List<SearchDetailsBean.Diseases> hotDis = new ArrayList<>();
    /**
     *  获取常见疾病
     */
    private void HttpCommonData() {
        RetrofitClient.getinstance(UIUtils.getContext()).create(GetService.class).getCommonDis().enqueue(new Callback<List<SearchDetailsBean.Diseases>>() {
            @Override
            public void onResponse(Call<List<SearchDetailsBean.Diseases>> call, Response<List<SearchDetailsBean.Diseases>> response) {
                if (response.isSuccessful()) {

                    hotDis = response.body();
                    showHotDis(hotDis);
                }
            }
            @Override
            public void onFailure(Call<List<SearchDetailsBean.Diseases>> call, Throwable t) {
                MyLogcat.jLog().e("onFailure:" + call.hashCode());
            }
        });
    }

    /**
     * 显示常见疾病
     * @param body
     */
    private void showHotDis(List<SearchDetailsBean.Diseases> body) {

        List<String> list = new ArrayList<>();
        for (int i = 0; i < body.size() ; i++) {
            list.add(body.get(i).getName());
        }
        showTextLinearLayout.setData(list,getActivity());
        showTextLinearLayout.setListener(new ShowTextLinearLayout.onItemClickListener() {
            @Override
            public void onItemClick(View v) {
                String sear = ((TextView) v).getText().toString();
                for (int i = 0; i < hotDis.size() ; i++) {
                    if(sear.equals(hotDis.get(i).getName())){
                        Intent intent = new Intent(getActivity(),DiseasesDetailActivity.class);
                        intent.putExtra("disid",hotDis.get(i).getDisid());
                        startActivity(intent);
                        return;
                    }
                }
            }
        });

    }

    private Handler handler = new Handler() {
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
    };

    @Override
    public void onClick(View v) {
        if(v == ivSms || v == sear_tv){
            Intent intent = new Intent(getActivity(), SearActivity.class);
            startActivity(intent);
        }else if(v == randDoc1){
            Intent intent = new Intent(getActivity(), DoctorDetailActivity.class);
            intent.putExtra("docid", orderData.get(0).getDoctorid());
            startActivity(intent);
        }else if(v == randDoc2){
            Intent intent = new Intent(getActivity(), DoctorDetailActivity.class);
            intent.putExtra("docid", orderData.get(1).getDoctorid());
            startActivity(intent);
        }else if(v == randDoc3){
            Intent intent = new Intent(getActivity(), DoctorDetailActivity.class);
            intent.putExtra("docid", orderData.get(2).getDoctorid());
            startActivity(intent);
        }
    }

}
