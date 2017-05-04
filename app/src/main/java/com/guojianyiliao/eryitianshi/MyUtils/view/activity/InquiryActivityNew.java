package com.guojianyiliao.eryitianshi.MyUtils.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.guojianyiliao.eryitianshi.Data.Http_data;
import com.guojianyiliao.eryitianshi.Data.entity.DoctorList;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.MyLogcat;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.UIUtils;
import com.guojianyiliao.eryitianshi.MyUtils.view.activity.view.LineGridView;
import com.guojianyiliao.eryitianshi.R;
import com.guojianyiliao.eryitianshi.Utils.IListener;
import com.guojianyiliao.eryitianshi.Utils.ListenerManager;
import com.guojianyiliao.eryitianshi.View.activity.DoctorparticularsActivity;
import com.guojianyiliao.eryitianshi.page.adapter.InquiryAdapter;
import com.guojianyiliao.eryitianshi.page.adapter.InquirySecletAdapter;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zxl.library.DropDownMenu;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * description: 在线问诊页面
 * autour: jnluo jnluo5889@126.com
 * version: Administrator
 */

public class InquiryActivityNew extends AppCompatActivity implements IListener {

    @BindView(R.id.ivb_back)
    ImageButton ivbBack;
    @BindView(R.id.iv_sms)
    ImageView ivSms;
    @BindView(R.id.et_home_search)
    EditText etHomeSearch;
    @BindView(R.id.ll_search_foot)
    LinearLayout llSearchFoot;
    @BindView(R.id.rl_background_color)
    RelativeLayout rlBackgroundColor;

    @BindView(R.id.rl_nonetwork)
    RelativeLayout rlNonetwork;
    @BindView(R.id.iv_lading)
    ImageView ivLading;
    @BindView(R.id.rl_loading)
    RelativeLayout rlLoading;

    /**
     * 内容区域视图
     */
    private ListView lvInquiry;

    List<DoctorList> list = new ArrayList<>();
    List<DoctorList> sectionData = new ArrayList<>();
    List<DoctorList> titleData = new ArrayList<>();
    List<DoctorList> isexpertData = new ArrayList<>();

    /**
     * 筛选菜单控件
     */
    private DropDownMenu dropDownMenu;
    private String headers[] = {"科室", "职称", "挂号"};
    String tag = "默认";
    String tag1 = "默认";
    String tag2 = "默认";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inquiry);
        contentView = UIUtils.getinflate(R.layout.contentview);
        ButterKnife.bind(this);

        initView();
        listinit(list);
        httpinit();
    }

    View contentView;

    private void initView() {
        dropDownMenu = (DropDownMenu) findViewById(R.id.dropDownMenu);
        lvInquiry = (ListView) contentView.findViewById(R.id.lv_inquiry);
        lvInquiry.setVerticalScrollBarEnabled(false);
        ivbBack.setOnClickListener(listener);
        lvInquiry.setOnScrollListener(new PauseOnScrollListener(ImageLoader.getInstance(), true, true));

        try {

            dropDownMenu.setDropDownMenu(Arrays.asList(headers), initViewData(), contentView);

        } catch (Exception e) {
            e.printStackTrace();
        }

        lvInquiry.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(InquiryActivityNew.this, DoctorparticularsActivity.class);

                if (tag.equals("全部科室") || tag == null || tag.equals("") || tag.equals("职称") || tag.equals("挂号") || tag.equals("默认")) {
                    intent.putExtra("doctot_id", list.get(position).getDoctorid());
                } else if (tag1.equals("主任医师") || tag1.equals("副主任医师") || tag1.equals("主治医师") || tag1.equals("执业医师")) {
                    intent.putExtra("doctot_id", titleData.get(position).getDoctorid());
                } else if (tag2.equals("普通号") || tag2.equals("专家号")) {
                    intent.putExtra("doctot_id", titleData.get(position).getDoctorid());
                } else {
                    intent.putExtra("doctot_id", isexpertData.get(position).getDoctorid());
                }
                startActivity(intent);

            }
        });
    }

    private List<HashMap<String, Object>> initViewData() {
        List<HashMap<String, Object>> viewDatas = new ArrayList<>();
        HashMap<String, Object> map;
        for (int i = 0; i < headers.length; i++) {
            map = new HashMap<String, Object>();
            map.put(DropDownMenu.KEY, DropDownMenu.TYPE_CUSTOM);

            if (i == 0) {

                map.put(DropDownMenu.VALUE, Section());

                map.put(DropDownMenu.SELECT_POSITION, 0);
            } else if (i == 1) {
                map.put(DropDownMenu.VALUE, Tltle());
                map.put(DropDownMenu.SELECT_POSITION, 0);
            } else {
                map.put(DropDownMenu.VALUE, Isexpert());
                map.put(DropDownMenu.SELECT_POSITION, 0);
            }

            viewDatas.add(map);
        }
        return viewDatas;
    }

    List<String> data = new ArrayList<>();

    private View Section() {
        View v = View.inflate(InquiryActivityNew.this, R.layout.inquiry_select_dialog, null);
        LineGridView lv_registration = (LineGridView) v.findViewById(R.id.lv_registration);

        data.add("全部科室");
        data.add("行为发育");
        data.add("小儿神经");
        data.add("内分泌");
        data.add("眼科");
        data.add("耳鼻喉");
        data.add("小儿外科");
        data.add("小儿消化");
        data.add("儿童口腔");
        data.add("小儿呼吸");
        data.add("儿童保健");
        data.add("小儿肾病");
        data.add("新生儿科");
        data.add("其他");

        lv_registration.setVerticalScrollBarEnabled(false);
        InquirySecletAdapter secletAdapter = new InquirySecletAdapter(InquiryActivityNew.this, data);
        lv_registration.setAdapter(secletAdapter);
        secletAdapter.notifyDataSetChanged();


        lv_registration.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dropDownMenu.setTabText(0, "科室");
                dropDownMenu.closeMenu();//关闭menu
                sectionData.clear();
                if (position == 0) {
                    listinit(list);
                    tag = "全部科室";
                    dropDownMenu.setTabText(0, "全部科室");
                    MyLogcat.jLog().e("2 list:" + list.size());
                } else {
                    for (int i = 0; i < list.size(); i++) {

                        if ((data.get(position)).equals(list.get(i).getSection())) {
                            sectionData.add(list.get(i));
                            tag = data.get(position);
                        }
                    }
                    if (sectionData.size() == 0) {
                        Toast.makeText(InquiryActivityNew.this, "没有此科室的医生", Toast.LENGTH_SHORT).show();
                    } else {
                        listinit(sectionData);
                        MyLogcat.jLog().e("2 list:" + sectionData.size());
                    }
                    dropDownMenu.setTabText(0, data.get(position));
                }

            }
        });

        return v;
    }


    private View Isexpert() {
        View v = View.inflate(InquiryActivityNew.this, R.layout.inquiry_sort_dialog, null);
        LinearLayout ll_title = (LinearLayout) v.findViewById(R.id.ll_title);

        LinearLayout ll_capacity_sort = (LinearLayout) v.findViewById(R.id.ll_capacity_sort);

        LinearLayout ll_title_sort = (LinearLayout) v.findViewById(R.id.ll_title_sort);

        ll_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dropDownMenu.closeMenu();//关闭menu
                dropDownMenu.setTabText(1, tag1);
                dropDownMenu.setTabText(2, "全部");
                tag2 = "全部";
                if (tag1.equals("默认")) {
                    listinit(list);
                } else {
                    isexpertData.clear();
                    for (int i = 0; i < titleData.size(); i++) {
                        isexpertData.add(titleData.get(i));
                    }
                    listinit(isexpertData);
                    MyLogcat.jLog().e("挂号：" + isexpertData.size());
                }
            }
        });

        ll_capacity_sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dropDownMenu.setTabText(0, tag);
                dropDownMenu.closeMenu();//关闭menu
                dropDownMenu.setTabText(1, tag1);
                dropDownMenu.setTabText(2, "普通号");
                tag2 = "普通号";
                if (tag1.equals("默认")) {
                    isexpertData.clear();
                    for (int i = 0; i < list.size(); i++) {
                        int isexpert = list.get(i).getIsexpert();
                        if (isexpert == 0) {
                            isexpertData.add(list.get(i));
                        }
                    }
                    listinit(isexpertData);
                } else {
                    isexpertData.clear();
                    for (int i = 0; i < titleData.size(); i++) {
                        int isexpert = titleData.get(i).getIsexpert();
                        if (isexpert == 0) {
                            isexpertData.add(titleData.get(i));
                        }
                    }
                    listinit(isexpertData);
                }
            }
        });
        ll_title_sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dropDownMenu.setTabText(0, tag);
                dropDownMenu.closeMenu();//关闭menu
                dropDownMenu.setTabText(1, tag1);
                dropDownMenu.setTabText(2, "专家号");
                tag2 = "专家号";
                if (tag1.equals("默认")) {
                    isexpertData.clear();
                    for (int i = 0; i < list.size(); i++) {
                        int isexpert = list.get(i).getIsexpert();
                        if (isexpert == 1) {
                            isexpertData.add(list.get(i));
                        }
                    }
                    listinit(isexpertData);
                } else {
                    isexpertData.clear();
                    for (int i = 0; i < titleData.size(); i++) {
                        int isexpert = titleData.get(i).getIsexpert();
                        if (isexpert == 1) {
                            isexpertData.add(titleData.get(i));
                        }
                    }
                    listinit(isexpertData);
                }
            }
        });
        return v;
    }


    private View Tltle() {
        View v = View.inflate(InquiryActivityNew.this, R.layout.inquiry_city_dialog, null);

        LinearLayout ll_all_areas = (LinearLayout) v.findViewById(R.id.ll_all_areas);

        LinearLayout ll_city_shenzheng = (LinearLayout) v.findViewById(R.id.ll_city_shenzheng);

        LinearLayout ll_city_chengdu = (LinearLayout) v.findViewById(R.id.ll_city_chengdu);

        LinearLayout ll_city_beijin = (LinearLayout) v.findViewById(R.id.ll_city_beijin);

        LinearLayout ll_city_shanghai = (LinearLayout) v.findViewById(R.id.ll_city_shanghai);
        //LinearLayout ll_city_guangdong = (LinearLayout) v.findViewById(R.id.ll_city_guangdong);

        ll_all_areas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dropDownMenu.closeMenu();
                dropDownMenu.setTabText(1, "全部职称");
                dropDownMenu.setTabText(2, "挂号");
                tag1 = "全部职称";
                MyLogcat.jLog().e("科室-职称 tag:" + tag);
                if (tag.equals("默认")) {
                    listinit(list);
                    MyLogcat.jLog().e("科室-职称:" + list.size());

                } else {
                    titleData.clear();
                    for (int i = 0; i < sectionData.size(); i++) {
                        titleData.add(sectionData.get(i));
                    }
                    listinit(titleData);
                    MyLogcat.jLog().e("titleData:" + titleData.size());
                }
            }
        });


        ll_city_shenzheng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dropDownMenu.setTabText(1, "主任医师");
                dropDownMenu.closeMenu();
                tag1 = "主任医师";
                if (tag.equals("默认")) {
                    titleData.clear();
                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i).getTitle().equals("主任医师")) {
                            titleData.add(list.get(i));
                        }
                    }
                    listinit(titleData);
                } else {
                    titleData.clear();
                    for (int i = 0; i < sectionData.size(); i++) {
                        if (sectionData.get(i).getTitle().equals("主任医师")) {
                            titleData.add(sectionData.get(i));
                        }
                    }
                    listinit(titleData);
                    MyLogcat.jLog().e("titleData:" + titleData.size());
                }
            }
        });


        ll_city_chengdu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dropDownMenu.setTabText(1, "副主任医师");
                dropDownMenu.closeMenu();
                tag1 = "副主任医师";

                if (tag.equals("默认")) {
                    titleData.clear();
                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i).getTitle().equals("副主任医师")) {
                            titleData.add(list.get(i));
                        }
                    }
                    listinit(titleData);
                } else {
                    titleData.clear();
                    for (int i = 0; i < sectionData.size(); i++) {
                        if (sectionData.get(i).getTitle().equals("副主任医师")) {
                            titleData.add(sectionData.get(i));
                        }
                    }
                    listinit(titleData);
                    MyLogcat.jLog().e("titleData:" + titleData.size());
                }
            }
        });


        ll_city_beijin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dropDownMenu.setTabText(1, "主治医师");
                dropDownMenu.closeMenu();
                tag1 = "主治医师";

                if (tag.equals("默认")) {
                    titleData.clear();
                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i).getTitle().equals("主治医师")) {
                            titleData.add(list.get(i));
                        }
                    }
                    listinit(titleData);
                } else {

                    titleData.clear();
                    for (int i = 0; i < sectionData.size(); i++) {
                        if (sectionData.get(i).getTitle().equals("主治医师")) {
                            titleData.add(sectionData.get(i));
                        }
                    }
                    listinit(titleData);
                    MyLogcat.jLog().e("titleData:" + titleData.size());
                }
            }
        });


        ll_city_shanghai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dropDownMenu.setTabText(1, "执业医师");
                dropDownMenu.closeMenu();
                tag1 = "执业医师";

                if (tag.equals("默认")) {
                    titleData.clear();
                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i).getTitle().equals("执业医师")) {
                            titleData.add(list.get(i));
                        }
                    }
                    listinit(titleData);
                } else {

                    titleData.clear();
                    for (int i = 0; i < sectionData.size(); i++) {
                        if (sectionData.get(i).getTitle().equals("执业医师")) {
                            titleData.add(sectionData.get(i));
                        }
                    }
                    listinit(titleData);
                    MyLogcat.jLog().e("titleData:" + titleData.size());
                }
            }
        });

        /*ll_city_guangdong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dropDownMenu.setTabText(0, "广州");
                dropDownMenu.setTabText(1, " 全部科室");
                dropDownMenu.setTabText(2, "智能排序");
                dropDownMenu.closeMenu();
                tag = "广州";
                citylist.clear();
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).getCity().equals("广州")) {
                        citylist.add(list.get(i));
                    }
                }
                listinit(citylist);
            }
        });*/


        return v;
    }


    @Override
    public void onBackPressed() {

        if (dropDownMenu.isShowing()) {
            dropDownMenu.closeMenu();
        } else {
            super.onBackPressed();
        }
    }


    InquiryAdapter adapter;

    private void listinit(List<DoctorList> list) {
        adapter = new InquiryAdapter(InquiryActivityNew.this, list);
        lvInquiry.setAdapter(adapter);
    }

    Gson gson = new Gson();

    private void httpinit() {
        OkHttpUtils
                .post()
                .url(Http_data.http_data + "doctor/getDrList")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        rlLoading.setVisibility(View.GONE);
                        rlNonetwork.setVisibility(View.VISIBLE);
                        dropDownMenu.setVisibility(View.GONE);
                    }


                    @Override
                    public void onResponse(String response, int id) {
                        Type listtype = new TypeToken<LinkedList<DoctorList>>() {
                        }.getType();

                        LinkedList<DoctorList> leclist = gson.fromJson(response, listtype);
                        for (Iterator it = leclist.iterator(); it.hasNext(); ) {
                            DoctorList inquiry = (DoctorList) it.next();
                            list.add(inquiry);
                            MyLogcat.jLog().e("职称：" + inquiry.getTitle() + "//" + inquiry.getName());

                        }
                        adapter.notifyDataSetChanged();

                        rlLoading.setVisibility(View.GONE);
                    }
                });
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.ivb_back:
                    finish();
                    break;
            }

        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ListenerManager.getInstance().unRegisterListener(this);
    }

    @Override
    public void notifyAllActivity(String str) {

    }
}
