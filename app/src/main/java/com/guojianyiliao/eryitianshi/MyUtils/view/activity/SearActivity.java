package com.guojianyiliao.eryitianshi.MyUtils.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.guojianyiliao.eryitianshi.MyUtils.adaper.zmc_SearDiseasesListviewAdapter;
import com.guojianyiliao.eryitianshi.MyUtils.adaper.zmc_SearDoctorListviewAdapter;
import com.guojianyiliao.eryitianshi.MyUtils.bean.HotWords;
import com.guojianyiliao.eryitianshi.MyUtils.bean.SearchDetailsBean;
import com.guojianyiliao.eryitianshi.MyUtils.customView.MyListview;
import com.guojianyiliao.eryitianshi.MyUtils.customView.ShowTextLinearLayout;
import com.guojianyiliao.eryitianshi.MyUtils.interfaceservice.GetService;
import com.guojianyiliao.eryitianshi.MyUtils.manager.RetrofitClient;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.AnimLoadingUtil;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.MyLogcat;
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
 * Created by zmc 2017/5/6.
 */

public class SearActivity extends AppCompatActivity implements View.OnClickListener, ShowTextLinearLayout.onItemClickListener, AdapterView.OnItemClickListener {
    //title栏控件
    /**搜索按钮**/
    @BindView(R.id.iv_sms)
    ImageView sear_img;
    /**搜索框**/
    @BindView(R.id.et_home_search)
    EditText sear_et;
    /**取消**/
    @BindView(R.id.cancel_tv)
    TextView cancel_tv;

    //历史记录layout控件
    /****/
    @BindView(R.id.history_layout)
    LinearLayout history_layout;
    @BindView(R.id.delete_img)
    ImageView delete_img;
    @BindView(R.id.history_showtextlinearlayout)
    ShowTextLinearLayout history_showTextLinearLayout;
    @BindView(R.id.hot_showtextlinearlayout)
    ShowTextLinearLayout hot_showTextLinearLayout;

    //搜索结果layout控件
    @BindView(R.id.sear_layout)
    LinearLayout sear_layout;
    @BindView(R.id.disease_layout)
    LinearLayout disease_layout;
    @BindView(R.id.doctor_layout)
    LinearLayout doctor_layout;
    @BindView(R.id.disease_listview)
    MyListview disease_listview;
    @BindView(R.id.doctor_listview)
    MyListview doctor_listview;
    @BindView(R.id.show_more_doctor)
    TextView show_more;

    private List<String> list = new ArrayList<>();
    zmc_SearDiseasesListviewAdapter adapter;
    zmc_SearDoctorListviewAdapter doctor_adapter;

    View animView;
    AnimLoadingUtil animLoadingUtil;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zmc_activity_sear);

        ButterKnife.bind(this);

        animView = findViewById(R.id.anim_view_layout);
        animLoadingUtil = new AnimLoadingUtil(animView);

        try {
            String sear = SharedPreferencesTools.GetUsearSear(this, "userSave", "userSear");
            if (TextUtils.isEmpty(sear)) {

            } else {
                String[] split = sear.split(",");
                for (int i = 0; i < split.length; i++) {
                    list.add(split[i]);
                }
            }
        } catch (NullPointerException e) {

        }

        getHotSear();

        sear_img.setOnClickListener(this);
        cancel_tv.setOnClickListener(this);
        show_more.setOnClickListener(this);
        delete_img.setOnClickListener(this);
        history_showTextLinearLayout.setListener(this);
        history_showTextLinearLayout.setData(list);

        sear_et.setOnKeyListener(new View.OnKeyListener() {

            @Override

            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    // 先隐藏键盘
                    ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(SearActivity.this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    //进行搜索操作的方法，在该方法中可以加入mEditSearchUser的非空判断
                    String sear = sear_et.getText().toString();
                    if (TextUtils.isEmpty(sear)) {
                        Toast.makeText(SearActivity.this, "请输入搜索关键字", Toast.LENGTH_SHORT).show();
                    } else {
                        getSear(sear);
                    }
                }
                return false;
            }
        });

    }

    /**
     * 获取热词
     */
    private void getHotSear() {
        RetrofitClient.getinstance(SearActivity.this).create(GetService.class).getHotWords().enqueue(new Callback<List<HotWords>>() {
            @Override
            public void onResponse(Call<List<HotWords>> call, Response<List<HotWords>> response) {
                if(response.isSuccessful()){
                    List<HotWords> body = response.body();
                    if(body.size() != 0){
                        List<String> list = new ArrayList<String>();
                        for (int i = 0; i < body.size() ; i++) {
                            list.add(body.get(i).getScontent());
                        }
                        hot_showTextLinearLayout.setData(list);
                        hot_showTextLinearLayout.setListener(SearActivity.this);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<HotWords>> call, Throwable t) {
                MyLogcat.jLog().e("getHotWords : 获取热词失败" );
                MyLogcat.jLog().e("getHotWords : " + t);
            }
        });
    }

    /**
     * 获取搜索内容
     * @param sear
     */
    private void getSear(String sear){
        animLoadingUtil.startAnim("搜索中...");
        sear_et.setText(sear+"");
        String s;
        try{
            //将搜索的内容进行保存
            s = SharedPreferencesTools.GetUsearSear(this, "userSave", "userSear");
            Log.e("getSear","s = " + s);
            s = s.replace(sear+",","");
            Log.e("getSear","s.replace = " + s);
        }catch (NullPointerException e){
            s = "";
        }
        SharedPreferencesTools.SaveUserSear(this, "userSave", "userSear",sear+","+s);

        RetrofitClient.getinstance(SearActivity.this).create(GetService.class).getSear(sear).enqueue(new Callback<SearchDetailsBean>() {
            @Override
            public void onResponse(Call<SearchDetailsBean> call, Response<SearchDetailsBean> response) {
                if(response.isSuccessful()){
                    animLoadingUtil.finishAnim();
                    history_layout.setVisibility(View.GONE);
                    sear_layout.setVisibility(View.VISIBLE);
                    SearchDetailsBean body = response.body();
                    diseases_list = body.getDiseases();
                    doctors_list = body.getDoctors();
                    showDiseases(diseases_list);
                    showDoctor(doctors_list);
                }
            }

            @Override
            public void onFailure(Call<SearchDetailsBean> call, Throwable t) {
                Log.e("SearActivity","err:"+t.getMessage());
                animLoadingUtil.finishAnim();
                Toast.makeText(SearActivity.this, "网络连接错误", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**医生列表**/
    List<SearchDetailsBean.Doctors> doctors_list = new ArrayList<>();
    /**疾病列表**/
    List<SearchDetailsBean.Diseases> diseases_list = new ArrayList<>();
    @Override
    public void onClick(View v) {
        if(v == sear_img ){
            String sear = sear_et.getText().toString();
            if(TextUtils.isEmpty(sear)){
                Toast.makeText(this,"请输入搜索关键字",Toast.LENGTH_SHORT).show();
            }else{
                getSear(sear);
            }
        }else if(v == cancel_tv){
            /**
             * a、取消按钮  如果当前是显示搜索列表则  返回到显示搜索结果界面
             * b、如果当前是搜索结果界面  1、如果当前搜索框有文本 则去掉文本
             *                            2、如果当前没有文本就退出
             */
            if(sear_layout.getVisibility() == View.VISIBLE){
                history_showTextLinearLayout.remove();
                sear_layout.setVisibility(View.GONE);
                history_layout.setVisibility(View.VISIBLE);
                list.removeAll(list);
                try {
                    String sear = SharedPreferencesTools.GetUsearSear(this, "userSave", "userSear");
                    String[] split = sear.split(",");
                    for (int i = 0; i < split.length; i++) {
                        list.add(split[i]);
                    }
                }catch (NullPointerException e){
                }
                sear_et.setText("");
                history_showTextLinearLayout.setData(list);
            }else{
                if(TextUtils.isEmpty(sear_et.getText().toString())){
                    onBackPressed();
                }else{
                    sear_et.setText("");
                }
            }
        }else if(v == delete_img){
            history_showTextLinearLayout.remove();
            SharedPreferencesTools.SaveUserSear(this, "userSave", "userSear",null);
        }else if(v == show_more){
            Intent intent = new Intent(this,AllDoctorActivity.class);
            startActivity(intent);
        }
    }

    /**
     * 显示搜索出疾病列表
     */
    private void showDiseases(List<SearchDetailsBean.Diseases> diseases){
        if(adapter == null){
            disease_listview.setVisibility(View.VISIBLE);
            adapter = new zmc_SearDiseasesListviewAdapter(diseases,this);
            disease_listview.setAdapter(adapter);
            disease_listview.setOnItemClickListener(this);
        }else{
            adapter.Update(diseases);
        }
    }

    /**
     * 显示搜索出来医生列表
     */
    private void showDoctor(List<SearchDetailsBean.Doctors> doctors){
        if(doctor_adapter == null){
            doctor_listview.setVisibility(View.VISIBLE);
            doctor_adapter = new zmc_SearDoctorListviewAdapter(doctors,this);
            doctor_listview.setAdapter(doctor_adapter);
            doctor_listview.setOnItemClickListener(this);
        }else{
            doctor_adapter.Update(doctors);
        }
    }


    @Override
    public void onItemClick(View v) {
        String sear = ((TextView) v).getText().toString();
        getSear(sear);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(parent == disease_listview){
            Intent intent = new Intent(this,DiseasesDetailActivity.class);
            intent.putExtra("disid",diseases_list.get(position).getDisid());
            startActivity(intent);
        }else if(parent == doctor_listview){
            Intent intent = new Intent(this,DoctorDetailActivity.class);
            intent.putExtra("docid",doctors_list.get(position).getDoctorid());
            startActivity(intent);
        }
    }
}
