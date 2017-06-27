package com.guojianyiliao.eryitianshi.MyUtils.view.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.guojianyiliao.eryitianshi.MyUtils.adaper.zmc_DownMenuRecycleviewAdapter;
import com.guojianyiliao.eryitianshi.MyUtils.adaper.zmc_SearDoctorListviewAdapter;
import com.guojianyiliao.eryitianshi.MyUtils.bean.SearchDetailsBean;
import com.guojianyiliao.eryitianshi.MyUtils.bean.TypeDis;
import com.guojianyiliao.eryitianshi.MyUtils.interfaceservice.GetService;
import com.guojianyiliao.eryitianshi.MyUtils.manager.RetrofitClient;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.SharedPreferencesTools;
import com.guojianyiliao.eryitianshi.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by zmc on 2017/5/18
 * 所有医生activity
 */

public class AllDoctorActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener, zmc_DownMenuRecycleviewAdapter.onItemClickListener {

    @BindView(R.id.all_doctor_back)
    ImageButton img_back;

    @BindView(R.id.all_doctor_sear_layout)
    LinearLayout sear_layout;
    @BindView(R.id.all_doctor_sear_tv)
    EditText sear_tv;

    //三个menu选项
    @BindView(R.id.all_doctor_ks)
    RelativeLayout ks_layout;
    @BindView(R.id.all_doctor_zc)
    RelativeLayout zc_layout;
    @BindView(R.id.all_doctor_gh)
    RelativeLayout gh_layout;

    //正在加载数据布局
    @BindView(R.id.all_doctor_loading_view)
    RelativeLayout loading_layout;
    @BindView(R.id.all_doctor_loading_img)
    ImageView loading_img;
    @BindView(R.id.all_doctor_loading_tv)
    TextView loading_tv;
    @BindView(R.id.all_doctor_loading_btn)
    TextView reLoading_tv;

    //加载数据成功布局
    @BindView(R.id.all_doctor_loading_suc)
    LinearLayout loadSuc_layout;
    @BindView(R.id.all_doctor_listview)
    ListView listView;
    @BindView(R.id.all_doctor_bg_view)
    View bg_view;

    //弹出菜单的popupwindow
    PopupWindow mPopupWindow;
    View contentView;
    RecyclerView windowRecyclyeview;
    zmc_DownMenuRecycleviewAdapter windowAdapter = null;

    zmc_SearDoctorListviewAdapter adapter;
    /**
     * 医生列表
     **/
    List<SearchDetailsBean.Doctors> doctors_list = new ArrayList<>();
    /**当前显示的医生列表**/
    List<SearchDetailsBean.Doctors> currentList = new ArrayList<>();

    Gson gson ;

    List<String> ks_data = new ArrayList<>();
    List<String> zc_data = new ArrayList<>();
    List<String> gh_data = new ArrayList<>();
    /**
     * 当前挂号菜单内容选中项下标
     **/
    private int mCurrentIndex_gh = 0;
    /**
     * 当前职称菜单内容选中项下标
     **/
    private int mCurrentIndex_zc = 0;
    /**
     * 当前科室菜单内容选中项下标
     **/
    private int mCurrentIndex_ks = 0;
    /**
     * 当前选中的菜单类型  0：科室  1：职称  2：挂号
     **/
    private int mCurrentIndex_layout = -1;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zmc_activity_all_doctor);
        ButterKnife.bind(this);
        gson = new Gson();
        getData();
        initWindow();
        initData();
        reLoading_tv.setOnClickListener(this);
        img_back.setOnClickListener(this);
        ks_layout.setOnClickListener(this);
        zc_layout.setOnClickListener(this);
        gh_layout.setOnClickListener(this);

        sear_tv.setOnKeyListener(new View.OnKeyListener() {

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    // 先隐藏键盘
                    ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(AllDoctorActivity.this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    //进行搜索操作的方法，在该方法中可以加入mEditSearchUser的非空判断
                    String sear = sear_tv.getText().toString().trim();
                    if (TextUtils.isEmpty(sear)) {
                        Toast.makeText(AllDoctorActivity.this, "请输入搜索关键字", Toast.LENGTH_SHORT).show();
                    } else {
                        getSear(sear);
                    }
                }
                return false;
            }
        });

        sear_tv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                s = s.toString().trim();
                if(TextUtils.isEmpty(s)){
                    showList(doctors_list);
                }else{
                    getSear(s+"");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    /**
     * 初始化数据
     */
    private void initData() {
        ks_data.add("全部科室");
        ks_data.add("小儿呼吸科");
        ks_data.add("小儿消化科");
        ks_data.add("新生儿科（1-28天）");
        ks_data.add("新生儿外科");
        ks_data.add("发育行为科");
        ks_data.add("小儿神经科");
        ks_data.add("内分泌科");
        ks_data.add("儿童口腔科");
        ks_data.add("儿童皮肤科");
        ks_data.add("儿童眼科");
        ks_data.add("耳鼻喉科");
        ks_data.add("小儿外科");
        ks_data.add("小儿肾病科");
        ks_data.add("中医科");

        zc_data.add("全部职称");
        zc_data.add("主任医师");
        zc_data.add("副主任医师");
        zc_data.add("主治医师");
        zc_data.add("执业医师");

        gh_data.add("全部");
        gh_data.add("普通号");
        gh_data.add("专家号");
    }

    /**
     * 初始化菜单弹窗
     */
    private void initWindow() {
        mPopupWindow = new PopupWindow(LinearLayoutCompat.LayoutParams.MATCH_PARENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT);
        contentView = LayoutInflater.from(this).inflate(R.layout.zmc_window_down_menu, null);
        mPopupWindow.setContentView(contentView);
        //为mPopupWindow设置点击其他地方消失
        mPopupWindow.setFocusable(true);
        Bitmap bitmap = null;
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable(getResources(), bitmap));
        windowRecyclyeview = (RecyclerView) contentView.findViewById(R.id.zmc_window_recycleview);
        GridLayoutManager manager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        windowRecyclyeview.setLayoutManager(manager);
    }

    /**
     * 获取所有医生信息
     */
    public void getData() {

        RetrofitClient.getinstance(this).create(GetService.class).getAlldoctor().enqueue(new Callback<List<SearchDetailsBean.Doctors>>() {
            @Override
            public void onResponse(Call<List<SearchDetailsBean.Doctors>> call, Response<List<SearchDetailsBean.Doctors>> response) {
                if (response.isSuccessful()) {
                    doctors_list = response.body();
                    HomeAcitivtyMy.docNum = doctors_list.size();
                    SharedPreferencesTools.SaveAllDoc(AllDoctorActivity.this,"userSave","allDoc",gson.toJson(doctors_list));
                    loadSucc();
                    showList(doctors_list);
                }
            }

            @Override
            public void onFailure(Call<List<SearchDetailsBean.Doctors>> call, Throwable t) {
                getCacheData();
            }
        });
    }

    /**
     * 缓存获取数据
     */
    private void getCacheData() {
        String all = SharedPreferencesTools.GetAllDoc(this, "userSave", "allDoc");
        if(TextUtils.isEmpty(all)){
            loadFail();
        }else{
            Toast.makeText(AllDoctorActivity.this,"获取最新数据失败",Toast.LENGTH_SHORT).show();
            doctors_list = gson.fromJson(all,new TypeToken<List<SearchDetailsBean.Doctors>>(){}.getType());
            loadSucc();
            showList(doctors_list);
        }
    }

    /**
     * 加载数据成功
     */
    private void loadSucc() {
        loading_layout.setVisibility(View.GONE);
        loadSuc_layout.setVisibility(View.VISIBLE);
    }

    /**
     * 加载数据失败
     */
    private void loadFail() {
        loading_img.setImageResource(R.drawable.loadfail);
        loading_tv.setText("内容被外星人劫持了");
        reLoading_tv.setVisibility(View.VISIBLE);
    }

    private void showList(List<SearchDetailsBean.Doctors> list) {
        currentList = list;
        if (adapter == null) {
            adapter = new zmc_SearDoctorListviewAdapter(list, this);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(this);
        } else {
            adapter.Update(list);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, DoctorDetailActivity.class);
        intent.putExtra("docid", currentList.get(position).getDoctorid());
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        if (v == reLoading_tv) {//重新加载
            getData();
            reLoading_tv.setVisibility(View.GONE);
            loading_img.setImageResource(R.drawable.loading);
            loading_tv.setText("内容加载中");
        } else if (v == ks_layout || v == zc_layout || v == gh_layout) {
            showWindow(v);
        } else if(v == img_back){
            onBackPressed();
//        } else if(v == sear_tv){
//            Intent intent = new Intent(this, SearActivity.class);
//            startActivity(intent);
        }
    }

    /**
     * 显示对应的菜单选项
     *
     * @param v
     */
    private void showWindow(View v) {
        Log.e("showWindow","mPopupWindow是否显示"+mPopupWindow.isShowing());
        if (v == ks_layout) {
            windowAdapter = new zmc_DownMenuRecycleviewAdapter(ks_data, this, mCurrentIndex_ks);
            windowAdapter.setListener(this);
            windowRecyclyeview.setAdapter(windowAdapter);
            mCurrentIndex_layout = 0;
        } else if (v == zc_layout) {
            windowAdapter = new zmc_DownMenuRecycleviewAdapter(zc_data, this, mCurrentIndex_zc);
            windowAdapter.setListener(this);
            windowRecyclyeview.setAdapter(windowAdapter);
            mCurrentIndex_layout = 1;
        } else if (v == gh_layout) {
            windowAdapter = new zmc_DownMenuRecycleviewAdapter(gh_data, this, mCurrentIndex_gh);
            windowAdapter.setListener(this);
            windowRecyclyeview.setAdapter(windowAdapter);
            mCurrentIndex_layout = 2;
        }
        mPopupWindow.showAsDropDown(v);

        //弹出popupwindow的时候  背景变暗
        bg_view.setVisibility(View.VISIBLE);

        //对popupwindow设置监听  让其不显示的时候 背景颜色恢复
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                bg_view.setVisibility(View.GONE);
                //在弹窗消失时 设置空 并取消windowAdapter对当前actiivity的引用
                windowAdapter.setListener(null);
                windowAdapter = null;
            }
        });


    }

    @Override
    public void onItemClick(int position) {
        if (mCurrentIndex_layout == 0) {
            mCurrentIndex_ks = position;
        } else if (mCurrentIndex_layout == 1) {
            mCurrentIndex_zc = position;
        } else if (mCurrentIndex_layout == 2) {
            mCurrentIndex_gh = position;
        }
        mPopupWindow.dismiss();
        screenDoctor();
    }

    /**
     * 根据用户当前勾选项  去进行筛选医生

     */
    private void screenDoctor() {
        List<SearchDetailsBean.Doctors> list = new ArrayList<>();
        List<SearchDetailsBean.Doctors> needRemove_list = new ArrayList<>();
        /**
         * 此处如果写成  list = doctors_list  会导致doctors_list的值也会对应的减去
         * 因为List是引用数据类型 存于堆中 直接等号赋值  实际是将栈中变量名为list的地址指向
         * 存放于堆中doctors_list的地址  这样相当于  list与doctors_list在栈中是两个对象  实际指向的是堆中同一个地址
         */
        list.addAll(doctors_list);

        if (mCurrentIndex_ks == 0) {

        } else {
            for (int i = 0; i < list.size(); i++) {
                if (!list.get(i).getSection().equals(ks_data.get(mCurrentIndex_ks))) {
                    needRemove_list.add(list.get(i));
                }
            }
            list.removeAll(needRemove_list);
        }

        if (mCurrentIndex_zc == 0) {

        } else {
            needRemove_list.clear();
            for (int i = 0; i < list.size(); i++) {
                if (!list.get(i).getTitle().equals(zc_data.get(mCurrentIndex_zc))) {
                    needRemove_list.add(list.get(i));
                }
            }
            list.removeAll(needRemove_list);
        }

        if (mCurrentIndex_gh == 0) {

        } else if (mCurrentIndex_gh == 1) {//普通号
            needRemove_list.clear();
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getIsexpert() != 0) {
                    needRemove_list.add(list.get(i));
                }
            }
            list.removeAll(needRemove_list);
        } else if (mCurrentIndex_gh == 2) {//专家号
            needRemove_list.clear();
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getIsexpert() != 1) {
                    needRemove_list.add(list.get(i));
                }
            }
            list.removeAll(needRemove_list);
        }
        showList(list);
    }

    /**
     * 搜索
     * @param sear
     */
    private void getSear(String sear){
        List<SearchDetailsBean.Doctors> list = new ArrayList<>();
        for (int i = 0; i < doctors_list.size(); i++) {
            if(doctors_list.get(i).getName().indexOf(sear) != -1){
                list.add(doctors_list.get(i));
            }
        }
        showList(list);
    }
}
