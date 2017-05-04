package com.guojianyiliao.eryitianshi.View.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.guojianyiliao.eryitianshi.Data.Http_data;
import com.guojianyiliao.eryitianshi.Data.entity.DoctorList;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.MyLogcat;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.UIUtils;
import com.guojianyiliao.eryitianshi.R;
import com.guojianyiliao.eryitianshi.Utils.IListener;
import com.guojianyiliao.eryitianshi.Utils.ListenerManager;
import com.guojianyiliao.eryitianshi.page.adapter.InquiryAdapter;
import com.guojianyiliao.eryitianshi.page.adapter.InquirySecletAdapter;
import com.guojianyiliao.eryitianshi.page.adapter.InquirylistAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
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

import okhttp3.Call;

/**
 * 在线问诊页面
 */
public class InquiryActivity extends AppCompatActivity implements IListener {
    View view;
    InquiryAdapter adapter;
    private ListView lv_inquiry;

    List<DoctorList> list;//所有医师

    List<DoctorList> sectionData;//科室

    List<DoctorList> titleData;//职称

    List<DoctorList> isexpertData;//挂号
    List<String> data;
    private ImageButton ivb_back;

    private RelativeLayout rl_nonetwork, rl_loading;
    Gson gson;
    InquirylistAdapter listadapter;

    private DropDownMenu dropDownMenu;
    private String headers[] = {"科室", "职称", "挂号"};
    View contentView;

    String tag = "默认";
    String tag1 = "默认";
    String tag2 = "默认";
    InquirySecletAdapter secletAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inquiry);
        list = new ArrayList<>();
        try {

            findView();
            listinit(list);
            httpinit();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void findView() {
        sectionData = new ArrayList<>();
        titleData = new ArrayList<>();
        isexpertData = new ArrayList<>();

        contentView = getLayoutInflater().inflate(R.layout.contentview, null);
        lv_inquiry = (ListView) contentView.findViewById(R.id.lv_inquiry);

        rl_nonetwork = (RelativeLayout) findViewById(R.id.rl_nonetwork);
        rl_loading = (RelativeLayout) findViewById(R.id.rl_loading);
        ivb_back = (ImageButton) findViewById(R.id.ivb_back);
        dropDownMenu = (DropDownMenu) findViewById(R.id.dropDownMenu);

        lv_inquiry.setVerticalScrollBarEnabled(false);
        ivb_back.setOnClickListener(listener);

        ImageLoader imageLoader = ImageLoader.getInstance();

        lv_inquiry.setOnScrollListener(new PauseOnScrollListener(imageLoader, true, true));

        try {

            dropDownMenu.setDropDownMenu(Arrays.asList(headers), initViewData(), contentView);

        } catch (Exception e) {
            e.printStackTrace();
        }

        lv_inquiry.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(InquiryActivity.this, DoctorparticularsActivity.class);

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

    String sectionTab[] = {"全部科室", "行为发育", "小儿神经", "内分泌", "眼科", "耳鼻喉", "小儿外科", "小儿消化", "儿童口腔", "小儿呼吸", "儿童保健", "小儿肾病", "新生儿科", "其他"};

    private View Section() {
        data = new ArrayList<>();
        for (int i = 0; i < sectionTab.length; i++) {
            data.add(sectionTab[i]);
        }
        View v = UIUtils.getinflate(R.layout.inquiry_select_dialog);
        GridView lv_registration = (GridView) v.findViewById(R.id.lv_registration);

        lv_registration.setVerticalScrollBarEnabled(false);
        secletAdapter = new InquirySecletAdapter(InquiryActivity.this, data);
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
                        Toast.makeText(InquiryActivity.this, "没有此科室的医生", Toast.LENGTH_SHORT).show();
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
        View v = View.inflate(InquiryActivity.this, R.layout.inquiry_sort_dialog, null);
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
                isexpertData.clear();
                for (int i = 0; i < titleData.size(); i++) {

                    isexpertData.add(titleData.get(i));
                }
                listinit(isexpertData);
                MyLogcat.jLog().e("挂号：" + isexpertData.size());
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
                isexpertData.clear();
                for (int i = 0; i < titleData.size(); i++) {
                    int isexpert = titleData.get(i).getIsexpert();
                    if (isexpert == 0) {
                        isexpertData.add(titleData.get(i));
                    }
                }
                listinit(isexpertData);

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
                isexpertData.clear();
                for (int i = 0; i < titleData.size(); i++) {
                    int isexpert = titleData.get(i).getIsexpert();
                    if (isexpert == 1) {
                        isexpertData.add(titleData.get(i));
                    }
                }
                listinit(isexpertData);

            }
        });
        return v;
    }


    private View Tltle() {
        View v = View.inflate(InquiryActivity.this, R.layout.inquiry_city_dialog, null);

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
                titleData.clear();
                for (int i = 0; i < sectionData.size(); i++) {
                    titleData.add(sectionData.get(i));
                }
                listinit(titleData);
                MyLogcat.jLog().e("titleData:" + titleData.size());
            }
        });


        ll_city_shenzheng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dropDownMenu.setTabText(1, "主任医师");
                dropDownMenu.closeMenu();
                tag1 = "主任医师";
                titleData.clear();
                for (int i = 0; i < sectionData.size(); i++) {
                    if (sectionData.get(i).getTitle().equals("主任医师")) {
                        titleData.add(sectionData.get(i));
                    }
                }
                listinit(titleData);
                MyLogcat.jLog().e("titleData:" + titleData.size());
            }
        });


        ll_city_chengdu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dropDownMenu.setTabText(1, "副主任医师");
                dropDownMenu.closeMenu();
                tag1 = "副主任医师";
                titleData.clear();
                for (int i = 0; i < sectionData.size(); i++) {
                    if (sectionData.get(i).getTitle().equals("副主任医师")) {
                        titleData.add(sectionData.get(i));
                    }
                }
                listinit(titleData);
                MyLogcat.jLog().e("titleData:" + titleData.size());
            }
        });


        ll_city_beijin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dropDownMenu.setTabText(1, "主治医师");
                dropDownMenu.closeMenu();
                tag1 = "主治医师";
                titleData.clear();
                for (int i = 0; i < sectionData.size(); i++) {
                    if (sectionData.get(i).getTitle().equals("主治医师")) {
                        titleData.add(sectionData.get(i));
                    }
                }
                listinit(titleData);
                MyLogcat.jLog().e("titleData:" + titleData.size());
            }
        });


        ll_city_shanghai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dropDownMenu.setTabText(1, "执业医师");
                dropDownMenu.closeMenu();
                tag1 = "执业医师";
                titleData.clear();
                for (int i = 0; i < sectionData.size(); i++) {
                    if (sectionData.get(i).getTitle().equals("执业医师")) {
                        titleData.add(sectionData.get(i));
                    }
                }
                listinit(titleData);
                MyLogcat.jLog().e("titleData:" + titleData.size());
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


    private void listinit(List<DoctorList> list) {
        adapter = new InquiryAdapter(InquiryActivity.this, list);
        lv_inquiry.setAdapter(adapter);
    }


    private void httpinit() {
        gson = new Gson();
        OkHttpUtils
                .post()
                .url(Http_data.http_data + "doctor/getDrList")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        rl_loading.setVisibility(View.GONE);
                        rl_nonetwork.setVisibility(View.VISIBLE);
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

                        rl_loading.setVisibility(View.GONE);
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
