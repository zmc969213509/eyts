package com.guojianyiliao.eryitianshi.MyUtils.view.activity.fragment;

/**
 * 首页界面
 * jnluo，jnluo5889@126.com
 */
public class NewHomePageFragment
//        extends Fragment implements MyScrollView.OnScrollListener, ReFlashListView.IReflashListener, ReFlashListViewhomepage.IReflashListener, View.OnClickListener
{
//
//    @BindView(R.id.bannerView)
//    Banner bannerView;
//
//    /**
//     * http://www.jianshu.com/p/b5385f2939bd 悬浮吸停
//     */
//    @BindView(R.id.ll_tab)
//    LinearLayout llTab;
//    @BindView(R.id.scrollview)
//    MyScrollView scrollview;
//    @BindView(R.id.iv_sms)
//    ImageView ivSms;
//    @BindView(R.id.et_home_search)
//    TextView sear_tv;
//    @BindView(R.id.rl_background_color)
//    RelativeLayout rlBackgroundColor;
//    @BindView(R.id.rl_see_order_more)
//    TextView rlSeeOrderMore;
//    /**
//     * 常见疾病
//     */
//    @BindView(R.id.adviser_label_xl)
//    XCFlowLayout adviserLabelXl;
//    @BindView(R.id.onListview)
//    ReFlashListViewhomepage onListview;
//
//    /**
//     * 在线问诊 预约挂号 家长课堂 我的诊疗 视图
//     */
//    @BindView(R.id.rl_line_set)
//    RelativeLayout rlLineSet;
//    @BindView(R.id.rl_order_yugh)
//    RelativeLayout rlOrderYugh;
//    @BindView(R.id.rl_parent_set)
//    RelativeLayout rlParentSet;
//    @BindView(R.id.rl_inquiry_set)
//    RelativeLayout rlInquirySet;
//
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View view = UIUtils.getinflate(R.layout.a_fragment_home);
//        ButterKnife.bind(this, view);
//        showListView(hotData);
//        view.findViewById(R.id.rl_man).getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            @Override
//            public void onGlobalLayout() {
//                onScroll(scrollview.getScrollY());
//            }
//        });
//        return view;
//    }
//
//    @Override
//    public void onScroll(int scrollY) {
//        int top = Math.max(scrollY, 20);
//        //      MyLogcat.jLog().e("scrollY = " + scrollY + "/" + llTab.getHeight() + "/top =" + top);
//        llTab.layout(0, top, llTab.getWidth(), top + llTab.getHeight());
//        if (top > 20 && top < 40) {
//            //llTab.setBackgroundResource(R.drawable.af_tab_color);
//            // llTab.setBackgroundResource(R.color.view);
//        }
//        if (top < 20) {
//            //  llTab.setBackgroundResource(R.color.white);
//            //  llTab.setAlpha((float) 0);
//        }
//    }
//
//    @Override
//    public void onActivityCreated(Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        scrollview.setOnScrollListener(this);
//        llTab.setOnClickListener(this);
//        /** 解析出来的数据有错时不会挂掉*/
//        try {
//            initBannerData();//首页广告
//            initOrder();//随机三名医生
//            HttpCommonData();//常见疾病
//            initHot();//热门话题
//        } catch (Exception e) {
//            MyLogcat.jLog().e("" + e.getMessage());
//        }
//        /**消息指示器 */
//        //TODO 消息指示器
//        Badge badge = new QBadgeView(getActivity()).bindTarget(ivSms).setBadgeNumber(5);
//        rlSeeOrderMore.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                startActivity(new Intent(getActivity(), InquiryActivityNew.class));
//                startActivity(new Intent(getActivity(), AllDoctorActivity.class));
//            }
//        });
//    }
//
//
//    @OnClick(R.id.rl_line_set)
//    public void lineset() {
//        startActivity(new Intent(getActivity(), InquiryActivityNew.class));
//    }
//
//    @OnClick(R.id.rl_order_yugh)
//    public void ygh() {
//        //TODO 预约挂号
//    }
//
//    @OnClick(R.id.rl_parent_set)
//    public void classroom() {
//        startActivity(new Intent(getActivity(), SchoolActivityLryv.class));
//    }
//
//    @OnClick(R.id.rl_inquiry_set)
//    public void myinquiry() {
//        startActivity(new Intent(getContext(), MyInquiryActivity.class));
//    }
//
//    private void initHot() {
//        initHotData();
//    }
//
//    List<HotTalkBean> hotData = new ArrayList();
//    //  private BaseQuickAdapter adapter;
//
//    private void initHotData() {
//
//        RetrofitClient.getinstance(getActivity()).create(GetService.class).HotTalkData().enqueue(new Callback<List<HotTalkBean>>() {
//            @Override
//            public void onResponse(Call<List<HotTalkBean>> call, Response<List<HotTalkBean>> response) {
//                if (response.isSuccessful()) {
//                    MyLogcat.jLog().e("onResponse:" + response.body().toString());
//                    List<HotTalkBean> body = response.body();
//                    for (HotTalkBean hotBean : body) {
//                        MyLogcat.jLog().e("HotTalkBean name :" + hotBean.title);
//                        hotData.add(hotBean);
//                    }
//
//                    showListView(body);
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<HotTalkBean>> call, Throwable t) {
//                MyLogcat.jLog().e("onFailure:");
//            }
//        });
//    }
//
//    HotTalkAdapter hotTalkAdapter;
//
//    public void showListView(List<HotTalkBean> hotData) {
//        if (hotTalkAdapter == null) {
//            hotTalkAdapter = new HotTalkAdapter(hotData);
//            onListview.setInterface(this);
//            onListview.setAdapter(hotTalkAdapter);
//        } else {
//            hotTalkAdapter.setData(hotData);
//        }
//    }
//
//    // TODO 随机医生的详情页面
//
//    @BindView(rand_order_icon_1)
//    CircleImageView randOrderIcon1;
//    @BindView(R.id.rand_order_name_1)
//    TextView randOrderName1;
//    @BindView(R.id.rand_order_type_1)
//    TextView randOrderType1;
//    @BindView(R.id.rand_order_detail_1)
//    TextView randOrderDetail1;
//    @BindView(R.id.rand_order_icon_2)
//    CircleImageView randOrderIcon2;
//    @BindView(R.id.rand_order_name_2)
//    TextView randOrderName2;
//    @BindView(R.id.rand_order_type_2)
//    TextView randOrderType2;
//    @BindView(R.id.rand_order_detail_2)
//    TextView randOrderDetail2;
//    @BindView(R.id.rand_order_icon_3)
//    CircleImageView randOrderIcon3;
//    @BindView(R.id.rand_order_name_3)
//    TextView randOrderName3;
//    @BindView(R.id.rand_order_type_3)
//    TextView randOrderType3;
//    @BindView(R.id.rand_order_detail_3)
//    TextView randOrderDetail3;
//    List<RandOrderBean> orderData = new ArrayList();
//
//    private void initOrder() {
//        OrderData();
//        /** 支持滑动*/
//        //recyOrderSet.setAdapter(new RandOrderAdapter(orderData));
//    }
//
//    private void OrderData() {
//
//        RetrofitClient.getinstance(getActivity()).create(GetService.class).RanDomDrData().enqueue(new Callback<List<RandOrderBean>>() {
//            @Override
//            public void onResponse(Call<List<RandOrderBean>> call, Response<List<RandOrderBean>> response) {
//                if (response.isSuccessful()) {
//                    MyLogcat.jLog().e("ran order onResponse:");
//                    List<RandOrderBean> body = response.body();
//                    for (RandOrderBean order : body) {
//                        orderData.add(order);
//                    }
//                    MyLogcat.jLog().e("order size :" + orderData.size());
//                }
//                try {
//                    randOrderName1.setText(orderData.get(0).getName());//NullPointerException，name没抽取变量，擦
//                    ImageLoader.getInstance().displayImage(orderData.get(0).getIcon(), randOrderIcon1);
//                    randOrderType1.setText(orderData.get(0).getTitle());
//                    randOrderDetail1.setText("擅长：" + orderData.get(0).getAdept());
//
//                    randOrderName2.setText(orderData.get(1).getName());//NullPointerException，name没抽取变量，擦
//                    ImageLoader.getInstance().displayImage(orderData.get(1).getIcon(), randOrderIcon2);
//                    randOrderType2.setText(orderData.get(1).getTitle());
//                    randOrderDetail2.setText("擅长：" + orderData.get(1).getAdept());
//
//                    randOrderName3.setText(orderData.get(2).getName());//NullPointerException，name没抽取变量，擦
//                    ImageLoader.getInstance().displayImage(orderData.get(2).getIcon(), randOrderIcon3);
//                    randOrderType3.setText(orderData.get(2).getTitle());
//                    randOrderDetail3.setText("擅长：" + orderData.get(2).getAdept());
//                } catch (Exception e) {
//                    MyLogcat.jLog().e("" + e.getMessage());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<RandOrderBean>> call, Throwable t) {
//                MyLogcat.jLog().e("ran order onFailure:");
//            }
//        });
//    }
//
//    private void initBannerData() {
//
//        NannerData();
//
//        bannerView.setDelayTime(10000);
//        bannerView.setOnBannerClickListener(new OnBannerClickListener() {
//            @Override
//            public void OnBannerClick(int position) {
//
//                if (bannerList.get(position - 1).getCyclopediaid() == null || bannerList.get(position - 1).getCyclopediaid().equals("")) {
//                    Intent intent = new Intent(getActivity(), WebActivity.class);
//                    intent.putExtra("url", bannerList.get(position - 1).getSite());
//                    startActivity(intent);
//                } else {
//                    Intent intent = new Intent(getActivity(), ConsultPageActivity.class);
//                    intent.putExtra("information", bannerList.get(position - 1).getCyclopediaid());
//                    intent.putExtra("collect", "0");
//                    startActivity(intent);
//                }
//            }
//        });
//
//    }
//
//    List<DiseaseBanner> bannerList = new ArrayList<>();
//    List<String> bannerData = new ArrayList<>();
//
//    private void NannerData() {
//
//        RetrofitClient.getinstance(getActivity()).create(GetService.class).BannerData().enqueue(new Callback<List<DiseaseBanner>>() {
//            @Override
//            public void onResponse(Call<List<DiseaseBanner>> call, Response<List<DiseaseBanner>> response) {
//                if (response.isSuccessful()) {
//                    MyLogcat.jLog().e("banner success:" + response.body().toString());
//                    List<DiseaseBanner> body = response.body();
//                    for (DiseaseBanner banner : body) {
//                        MyLogcat.jLog().e("getCover :" + banner.getCover());
//                        bannerList.add(banner);
//                        bannerData.add(banner.getCover());
//                        handler.sendEmptyMessage(10);
//                    }
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<DiseaseBanner>> call, Throwable t) {
//                MyLogcat.jLog().e("banner onfailure:" + call.toString());
//            }
//        });
//    }
//
//    /**
//     * 常见疾病
//     */
//    List<String> isCommon = new ArrayList<>();
//
//    private void HttpCommonData() {
//        RetrofitClient.getinstance(UIUtils.getContext()).create(GetService.class).getCommonDis().enqueue(new Callback<List<CommonDisData>>() {
//            @Override
//            public void onResponse(Call<List<CommonDisData>> call, Response<List<CommonDisData>> response) {
//                if (response.isSuccessful()) {
//                    MyLogcat.jLog().e("success:");
//
//                    if (isCommon.size() != 0) {
//                        isCommon.clear();
//                    }
//                    List<CommonDisData> body = response.body();
//                    for (CommonDisData data : body) {
//                        if (data.getIscommon() == 1) {
//                            isCommon.add(data.getName());
//                        }
//                    }
//
//                    int padding = UIUtils.dip2px(6);
//                    adviserLabelXl.removeAllViews();
//                    for (int i = 0; i < isCommon.size(); i++) {
//                        TextView tvIscommon = new TextView(getActivity());
//                        tvIscommon.setText(isCommon.get(i));
//                        tvIscommon.setTextSize(UIUtils.px2dip(28));
//                        tvIscommon.setTextColor(getResources().getColor(R.color.view));
//                        tvIscommon.setPadding(padding, padding, padding, padding);
//                        tvIscommon.setBackgroundResource(R.drawable.af_tx_set_illness);
//                        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//                        lp.setMargins(0, 0, padding, padding);
//                        tvIscommon.setLayoutParams(lp);
//                        adviserLabelXl.addView(tvIscommon);
//
//                        tvIscommon.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                //TODO 热门疾病
//                                MyLogcat.jLog().e("tvIscommont:" + v.getId());
//                            }
//                        });
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<CommonDisData>> call, Throwable t) {
//                MyLogcat.jLog().e("onFailure:" + call.hashCode());
//            }
//        });
//
//    }
//
//    private Handler handler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            switch (msg.what) {
//                case 10:
//                    bannerView.setImageLoader(new BannerImageLoader());
//                    bannerView.setImages(bannerData);
//                    bannerView.start();
//                    break;
//
//            }
//
//        }
//    };
//
//    @Override
//    public void onReflash() {
//
//    }
//
//    @Override
//    public void onLoadingMore() {
//
//        //TODO  这里加载跟多
//    }
//
//    @Override
//    public void onClick(View v) {
//        if(v == llTab || v == sear_tv){
//            Intent intent = new Intent(getActivity(), SearActivity.class);
//            startActivity(intent);
//        }
//    }
}
