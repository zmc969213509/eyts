package com.guojianyiliao.eryitianshi.MyUtils.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.guojianyiliao.eryitianshi.Data.Http_data;
import com.guojianyiliao.eryitianshi.MyUtils.adaper.zmc_DiseasesDetailCharRecycleviewAdapter;
import com.guojianyiliao.eryitianshi.MyUtils.bean.ChatBean;
import com.guojianyiliao.eryitianshi.MyUtils.bean.DiseaseLibraryBean;
import com.guojianyiliao.eryitianshi.MyUtils.bean.SearchDetailsBean;
import com.guojianyiliao.eryitianshi.MyUtils.bean.TypeDis;
import com.guojianyiliao.eryitianshi.MyUtils.customView.FullyLinearLayoutManager;
import com.guojianyiliao.eryitianshi.MyUtils.customView.MyRecycleView;
import com.guojianyiliao.eryitianshi.MyUtils.interfaceservice.GetService;
import com.guojianyiliao.eryitianshi.MyUtils.manager.RetrofitClient;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.SharedPreferencesTools;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.rocketAnimLoadingUtil;
import com.guojianyiliao.eryitianshi.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by zmc on 2017/5/18.
 * 疾病详情activity
 */

public class DiseasesDetailActivity extends AppCompatActivity implements View.OnClickListener, rocketAnimLoadingUtil.Listener {

    @BindView(R.id.diseases_back)
    ImageButton img_back;

    @BindView(R.id.diseases_title)
    TextView tv_title;

    @BindView(R.id.diseases_gs)
    LinearLayout layout_gs;
    @BindView(R.id.diseases_by)
    LinearLayout layout_by;
    @BindView(R.id.diseases_zz)
    LinearLayout layout_zz;

    @BindView(R.id.diseases_img1)
    ImageView img_1;
    @BindView(R.id.diseases_img2)
    ImageView img_2;
    @BindView(R.id.diseases_img3)
    ImageView img_3;

    @BindView(R.id.diseases_gs_tv)
    TextView tv_gs;
    @BindView(R.id.diseases_by_tv)
    TextView tv_by;
    @BindView(R.id.diseases_zz_tv)
    TextView tv_zz;

    @BindView(R.id.diseases_recycleview)
    MyRecycleView recycleView;

    @BindView(R.id.diseases_more)
    TextView tv_more;

    //include View
    @BindView(R.id.include_view)
    View includeView;

    @BindView(R.id.ll_fl_chat)
    LinearLayout llFlChat;
    @BindView(R.id.iv_icon)
    CircleImageView ivIcon;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_intro)
    TextView tvIntro;
    @BindView(R.id.tv_section)
    TextView tvSection;

    /**
     * 疾病id
     **/
    String disid;
    /**
     * 疾病实体
     **/
    DiseaseLibraryBean diseases;
    /**
     * 当前打开项
     **/
    int mCurrentindex = 0;
    /**
     * 患者于医生聊天数据
     **/
    List<ChatBean> chat_list = new ArrayList<>();

    zmc_DiseasesDetailCharRecycleviewAdapter adapter;

    // 动画
    private Animation ViewEnder;
    private Animation ViewExit;

    View loadView;
    rocketAnimLoadingUtil animLoadingUtil;
    Gson gson;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zmc_activity_diseases);
        ButterKnife.bind(this);
        disid = getIntent().getStringExtra("disid");

        loadView = findViewById(R.id.loadig_anim_view);
        animLoadingUtil = new rocketAnimLoadingUtil(loadView);
        animLoadingUtil.startAnim();
        animLoadingUtil.setListener(this);

        getDis(disid);
        ViewEnder = AnimationUtils.loadAnimation(this, R.anim.anim_tv_move_down_enter);
        ViewExit = AnimationUtils.loadAnimation(this, R.anim.anim_tv_move_up_exit);
        tv_more.setOnClickListener(this);
        img_back.setOnClickListener(this);
        layout_gs.setOnClickListener(this);
        layout_by.setOnClickListener(this);
        layout_zz.setOnClickListener(this);
        llFlChat.setOnClickListener(this);
    }

    /**
     * 获取疾病详情
     *
     * @param disid
     */
    private void getDis(String disid) {

        RetrofitClient.getinstance(this).create(GetService.class).getDis(disid).enqueue(new Callback<DiseaseLibraryBean>() {
            @Override
            public void onResponse(Call<DiseaseLibraryBean> call, Response<DiseaseLibraryBean> response) {
                animLoadingUtil.loadSucc();
                includeView.setVisibility(View.VISIBLE);
                if (response.isSuccessful()) {
                    diseases = response.body();
                    init();
                }
            }

            @Override
            public void onFailure(Call<DiseaseLibraryBean> call, Throwable t) {
                Toast.makeText(DiseasesDetailActivity.this, "获取最新数据失败", Toast.LENGTH_SHORT).show();
                animLoadingUtil.loadFail();
                //获取服务器数据失败  从缓存中获取
                getCacheData();
            }
        });
    }

    /**
     * 缓存获取数据
     */
    private void getCacheData() {
        if(gson == null){
            gson = new Gson();
        }
        String all = SharedPreferencesTools.GetAllDis(this, "userSave", "allDis");
        if(!TextUtils.isEmpty(all)){
            animLoadingUtil.loadSucc();
            List<TypeDis> typeDises = gson.fromJson(all,new TypeToken<List<TypeDis>>(){}.getType());

            for (int i = 0; i < typeDises.size() ; i++) {
                List<SearchDetailsBean.Diseases> disList = typeDises.get(i).getDisList();
                for (int j = 0; j < disList.size() ; j++) {
                    if(disList.get(j).getDisid().equals(disid)){
                        SearchDetailsBean.Diseases diseases = disList.get(j);
                        showCacheData(diseases);
                        return;
                    }
                }
            }
        }
    }

    /**
     * 显示缓存数据
     * @param diseases
     */
    private void showCacheData(SearchDetailsBean.Diseases diseases) {
        tv_title.setText(diseases.getName());

        layout_gs.setSelected(true);
        img_1.setSelected(true);
        layout_by.setSelected(false);
        img_2.setSelected(false);
        layout_zz.setSelected(false);
        img_3.setSelected(false);

        tv_gs.setVisibility(View.VISIBLE);
        tv_by.setVisibility(View.GONE);
        tv_zz.setVisibility(View.GONE);

        tv_gs.setText(diseases.getBio());
        tv_by.setText(diseases.getCure());
        tv_zz.setText(diseases.getSymptom());

        //添加患者与医生的聊天记录
        String userputquestion = diseases.getUserputquestion();
        String doctoranswerquestion = diseases.getDoctoranswerquestion();
        String docname = diseases.getDoctorname();
        String docicon = Http_data.http_data + diseases.getDoctoricon();
        Log.e("DiseasesDetailActivity", "docicon = " + docicon);
        String usericon = Http_data.http_data + diseases.getUsericon();
        Log.e("DiseasesDetailActivity", "usericon = " + usericon);

        String[] dis_split = userputquestion.split("&");
        String[] doc_split = doctoranswerquestion.split("&");
        int dis_index = 0;
        int doc_index = 0;
        for (int i = 0; i < (dis_split.length + doc_split.length); i++) {
            if (i % 2 == 0) {
                chat_list.add(new ChatBean(usericon, "患者", dis_split[dis_index]));
                dis_index++;
            } else if (i % 2 == 1) {
                chat_list.add(new ChatBean(docicon, docname + "医生", doc_split[doc_index]));
                doc_index++;
            }
        }
        showList();
        includeView.setVisibility(View.GONE);
    }

    /**
     * 填充数据
     */
    private void init() {
        tv_title.setText(diseases.getName());

        layout_gs.setSelected(true);
        img_1.setSelected(true);
        layout_by.setSelected(false);
        img_2.setSelected(false);
        layout_zz.setSelected(false);
        img_3.setSelected(false);

        tv_gs.setVisibility(View.VISIBLE);
        tv_by.setVisibility(View.GONE);
        tv_zz.setVisibility(View.GONE);

        tv_gs.setText(diseases.getBio());
        tv_by.setText(diseases.getCure());
        tv_zz.setText(diseases.getSymptom());

        //添加患者与医生的聊天记录
        String userputquestion = diseases.getUserputquestion();
        String doctoranswerquestion = diseases.getDoctoranswerquestion();
        String docname = diseases.getDoctorname();
        String docicon = Http_data.http_data + diseases.getDoctoricon();
        Log.e("DiseasesDetailActivity", "docicon = " + docicon);
        String usericon = Http_data.http_data + diseases.getUsericon();
        Log.e("DiseasesDetailActivity", "usericon = " + usericon);

        String[] dis_split = userputquestion.split("&");
        String[] doc_split = doctoranswerquestion.split("&");
        int dis_index = 0;
        int doc_index = 0;
        for (int i = 0; i < (dis_split.length + doc_split.length); i++) {
            if (i % 2 == 0) {
                try {
                    chat_list.add(new ChatBean(usericon, "患者", dis_split[dis_index]));
                    dis_index++;
                }catch (ArrayIndexOutOfBoundsException e){
                    try{
                        chat_list.add(new ChatBean(docicon, docname + "医生", doc_split[doc_index]));
                        doc_index++;
                    }catch (ArrayIndexOutOfBoundsException e1){
                    }
                }
            } else if (i % 2 == 1) {
                try{
                    chat_list.add(new ChatBean(docicon, docname + "医生", doc_split[doc_index]));
                    doc_index++;
                }catch (ArrayIndexOutOfBoundsException e){
                    try{
                        chat_list.add(new ChatBean(usericon, "患者", dis_split[dis_index]));
                        dis_index++;
                    }catch (ArrayIndexOutOfBoundsException e1){
                    }
                }
            }
        }
        showList();

        //推荐医生
        tvTitle.setText(diseases.getDoctors().getTitle());
        tvName.setText(diseases.getDoctors().getName());
        String adept = diseases.getDoctors().getAdept();
        String[] split = adept.split(";");
        tvIntro.setText("擅长：" + split[0]);
        tvSection.setText(diseases.getDoctors().getSection());
        ImageLoader.getInstance().displayImage(diseases.getDoctors().getIcon(), ivIcon);
    }

    private void showList() {
        if (adapter == null) {
            adapter = new zmc_DiseasesDetailCharRecycleviewAdapter(this, chat_list);
//            LinearLayoutManager manager = new LinearLayoutManager(this);
            FullyLinearLayoutManager manager = new FullyLinearLayoutManager(this);
            recycleView.setLayoutManager(manager);
            recycleView.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onClick(View v) {
        if (v == layout_gs) {
            if (mCurrentindex == 0) {
                return;
            } else {
                if (mCurrentindex == 1) {
                    layout_by.setSelected(false);
                    img_2.setSelected(false);
                    tv_by.setVisibility(View.GONE);
                } else if (mCurrentindex == 2) {
                    layout_zz.setSelected(false);
                    img_3.setSelected(false);
                    tv_zz.setVisibility(View.GONE);
                }
                layout_gs.setSelected(true);
                img_1.setSelected(true);
                tv_gs.setVisibility(View.VISIBLE);
                tv_gs.startAnimation(ViewEnder);
                mCurrentindex = 0;
            }
        } else if (v == layout_by) {
            if (mCurrentindex == 1) {
                return;
            } else {
                if (mCurrentindex == 0) {
                    layout_gs.setSelected(false);
                    img_1.setSelected(false);
                    tv_gs.setVisibility(View.GONE);
                } else if (mCurrentindex == 2) {
                    layout_zz.setSelected(false);
                    img_3.setSelected(false);
                    tv_zz.setVisibility(View.GONE);

                }
                layout_by.setSelected(true);
                img_2.setSelected(true);
                tv_by.setVisibility(View.VISIBLE);
                tv_by.startAnimation(ViewEnder);
                mCurrentindex = 1;
            }
        } else if (v == layout_zz) {
            if (mCurrentindex == 2) {
                return;
            } else {
                if (mCurrentindex == 1) {
                    layout_by.setSelected(false);
                    img_2.setSelected(false);
                    tv_by.setVisibility(View.GONE);
                } else if (mCurrentindex == 0) {
                    layout_gs.setSelected(false);
                    img_1.setSelected(false);
                    tv_gs.setVisibility(View.GONE);
                }
                layout_zz.setSelected(true);
                img_3.setSelected(true);
                tv_zz.setVisibility(View.VISIBLE);
                tv_zz.startAnimation(ViewEnder);
                mCurrentindex = 2;
            }
        } else if (v == tv_more) {
            Intent intent = new Intent(this, AllDoctorActivity.class);
            startActivity(intent);
        } else if (v == img_back) {
            onBackPressed();
        } else if (v == llFlChat) {
            Intent intent = new Intent(this, DoctorDetailActivity.class);
            intent.putExtra("docid", diseases.getDoctorid());
            startActivity(intent);
        }
    }

    @Override
    public void onAnimClick() {
        animLoadingUtil.startAnim();
        if(diseases == null){
            getDis(disid);
        }
    }
}
