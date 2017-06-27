package com.guojianyiliao.eryitianshi.MyUtils.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.guojianyiliao.eryitianshi.MyUtils.adaper.zmc_SearDoctorListviewAdapter;
import com.guojianyiliao.eryitianshi.MyUtils.bean.SearchDetailsBean;
import com.guojianyiliao.eryitianshi.MyUtils.bean.zmc_YYGHtotal;
import com.guojianyiliao.eryitianshi.MyUtils.customView.MyScrollView;
import com.guojianyiliao.eryitianshi.MyUtils.interfaceservice.GetService;
import com.guojianyiliao.eryitianshi.MyUtils.manager.RetrofitClient;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.SharedPreferencesTools;
import com.guojianyiliao.eryitianshi.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.guojianyiliao.eryitianshi.R.id.rand_order_icon_1;

/**
 * Created by zmc on 2017/6/13.
 * 预约挂号选择界面
 */

public class YYGHSelectActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener {

    private String tag = "YYGHSelectActivity";

    @BindView(R.id.yygh_s_scrollview)
    MyScrollView myScrollView;

    @BindView(R.id.tv_1)
    TextView title_tv;

    /**
     * 随机医生的详情页面
     */
    @BindView(R.id.foot_rand_order)
    RelativeLayout randdoc_allView;
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
    @BindView(R.id.rand_order_icon_2)
    CircleImageView randOrderIcon2;
    @BindView(R.id.rand_order_name_2)
    TextView randOrderName2;
    @BindView(R.id.rand_order_type_2)
    TextView randOrderType2;
    @BindView(R.id.rand_order_icon_3)
    CircleImageView randOrderIcon3;
    @BindView(R.id.rand_order_name_3)
    TextView randOrderName3;
    @BindView(R.id.rand_order_type_3)
    TextView randOrderType3;

    @BindView(R.id.yygh_s_listview)
    ListView listview;

    @BindView(R.id.yygh_s_layout1)
    LinearLayout layout1;
    @BindView(R.id.yygh_s_layout2)
    LinearLayout layout2;

    /**当前显示布局标记  0：显示在线医生   1：显示医院地址**/
    private int flag = 0;

    /**完整的医生数据**/
    List<SearchDetailsBean.Doctors> doc_all = new ArrayList<>();
    /**显示listview的医生数据**/
    List<SearchDetailsBean.Doctors> doc_list = new ArrayList<>();

    zmc_SearDoctorListviewAdapter adapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zmc_activity_yygh_s);
        ButterKnife.bind(this);
        getNum();
        randDoc1.setOnClickListener(this);
        randDoc2.setOnClickListener(this);
        randDoc3.setOnClickListener(this);
        getData();
    }

    /**
     * 获取预约挂号总人数
     */
    private void getNum() {
        int num = SharedPreferencesTools.GetUserNum(this, "userSave", "userNum");
        num += (int)(Math.random() * 10);
        SharedPreferencesTools.SaveUserNum(this, "userSave", "userNum",num);
        title_tv.setText("就诊患者"+num+"人  |  医生"+HomeAcitivtyMy.docNum+"位");
//        RetrofitClient.getinstance(this).create(GetService.class).getYYGHTotal("5000000").enqueue(new Callback<zmc_YYGHtotal>() {
//            @Override
//            public void onResponse(Call<zmc_YYGHtotal> call, Response<zmc_YYGHtotal> response) {
//                if(response.isSuccessful()){
//                    zmc_YYGHtotal body = response.body();
//                    title_tv.setText("已通过软件就诊患者"+body.getTotal()+"人  |  医生"+HomeAcitivtyMy.docNum+"位");
//                }
//            }
//
//            @Override
//            public void onFailure(Call<zmc_YYGHtotal> call, Throwable t) {
//            }
//        });
    }


    @OnClick(R.id.rl_see_order_more)
    public void allDoc(){
        startActivity(new Intent(this, AllDoctorActivity.class));
    }

    /**
     * 地址的显示和隐藏
     */
    @OnClick(R.id.yygh_s_address)
    public void address(){
        if(flag == 0 ){
            layout1.setVisibility(View.GONE);
            layout2.setVisibility(View.VISIBLE);
            flag = 1;
        }else if(flag == 1){
            myScrollView.smoothScrollTo(0,0);
            layout1.setVisibility(View.VISIBLE);
            layout2.setVisibility(View.GONE);
            flag = 0;
        }
    }

    /**
     * 返回键
     */
    @OnClick(R.id.yygg_s_back)
    public void back(){
        onBackPressed();
    }

    /**
     * 获取在线医生
     */
    public void getData() {
        RetrofitClient.getinstance(this).create(GetService.class).getOnlineDoc().enqueue(new Callback<List<SearchDetailsBean.Doctors>>() {
            @Override
            public void onResponse(Call<List<SearchDetailsBean.Doctors>> call, Response<List<SearchDetailsBean.Doctors>> response) {
                if(response.isSuccessful()){
                    Log.e(tag,"response = "+response.body().toString());
                    doc_all.removeAll(doc_all);
                    doc_list.removeAll(doc_list);
                    doc_all.addAll(response.body());
                    showData();
                }
            }

            @Override
            public void onFailure(Call<List<SearchDetailsBean.Doctors>> call, Throwable t) {
                Log.e(tag,"onFailure = "+t.getMessage());
            }
        });
    }

    /**
     * 展示数据
     */
    private void showData() {
        try{
            randdoc_allView.setVisibility(View.VISIBLE);
            //显示头部三个医生数据
            randOrderName1.setText(doc_all.get(0).getName());//NullPointerException，name没抽取变量
            ImageLoader.getInstance().displayImage(doc_all.get(0).getIcon(), randOrderIcon1);
            randOrderType1.setText(doc_all.get(0).getTitle());

            randOrderName2.setText(doc_all.get(1).getName());//NullPointerException，name没抽取变量
            ImageLoader.getInstance().displayImage(doc_all.get(1).getIcon(), randOrderIcon2);
            randOrderType2.setText(doc_all.get(1).getTitle());

            randOrderName3.setText(doc_all.get(2).getName());//NullPointerException，name没抽取变量
            ImageLoader.getInstance().displayImage(doc_all.get(2).getIcon(), randOrderIcon3);
            randOrderType3.setText(doc_all.get(2).getTitle());

            doc_list.addAll(doc_all);
            for (int i = 0; i < 3 ; i++) {
                doc_list.remove(0);
            }

            if (adapter == null) {
                adapter = new zmc_SearDoctorListviewAdapter(doc_list, this);
                listview.setAdapter(adapter);
                listview.setOnItemClickListener(this);
            } else {
                adapter.Update(doc_list);
            }
        }catch (Exception e){

        }
        myScrollView.smoothScrollTo(0,0);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, DoctorDetailActivity.class);
        intent.putExtra("docid", doc_list.get(position).getDoctorid());
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        if(v == randDoc1){
            Intent intent = new Intent(this, DoctorDetailActivity.class);
            intent.putExtra("docid", doc_all.get(0).getDoctorid());
            startActivity(intent);
        }else if(v == randDoc2){
            Intent intent = new Intent(this, DoctorDetailActivity.class);
            intent.putExtra("docid", doc_all.get(1).getDoctorid());
            startActivity(intent);
        }else if(v == randDoc3){
            Intent intent = new Intent(this, DoctorDetailActivity.class);
            intent.putExtra("docid", doc_all.get(2).getDoctorid());
            startActivity(intent);
        }
    }

    @Override
    public void onBackPressed() {
        if(flag == 0){
            super.onBackPressed();
        }else if(flag == 1){
            myScrollView.smoothScrollTo(0,0);
            layout1.setVisibility(View.VISIBLE);
            layout2.setVisibility(View.GONE);
            flag = 0;
        }
    }
}
