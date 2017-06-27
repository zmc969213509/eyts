package com.guojianyiliao.eryitianshi.MyUtils.view.activity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.guojianyiliao.eryitianshi.Data.Http_data;
import com.guojianyiliao.eryitianshi.MyUtils.adaper.zmc_EssayCommentRecycleviewAdapter;
import com.guojianyiliao.eryitianshi.MyUtils.bean.DocArrangement;
import com.guojianyiliao.eryitianshi.MyUtils.bean.DocCommend;
import com.guojianyiliao.eryitianshi.MyUtils.bean.SearchDetailsBean;
import com.guojianyiliao.eryitianshi.MyUtils.bean.UserInfoLogin;
import com.guojianyiliao.eryitianshi.MyUtils.bean.zmc_docInfo;
import com.guojianyiliao.eryitianshi.MyUtils.customView.FullyLinearLayoutManager;
import com.guojianyiliao.eryitianshi.MyUtils.customView.MyListview;
import com.guojianyiliao.eryitianshi.MyUtils.customView.MyRecycleView;
import com.guojianyiliao.eryitianshi.MyUtils.customView.ShowTextLinearLayout;
import com.guojianyiliao.eryitianshi.MyUtils.customView.TableLinearLayout;
import com.guojianyiliao.eryitianshi.MyUtils.interfaceservice.GetService;
import com.guojianyiliao.eryitianshi.MyUtils.manager.RetrofitClient;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.AnimLoadingUtil;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.DateUtils;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.SharedPreferencesTools;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.ToolUtils;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.rocketAnimLoadingUtil;
import com.guojianyiliao.eryitianshi.R;
import com.guojianyiliao.eryitianshi.View.activity.ChatpageActivity;
import com.guojianyiliao.eryitianshi.View.activity.DoctorparticularsActivity;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by zmc on 2017/5/16.
 * 医生详情界面
 */

public class DoctorDetailActivity extends AppCompatActivity implements View.OnClickListener, rocketAnimLoadingUtil.Listener {

    @BindView(R.id.doctor_collection)
    ImageView img_collection;
    @BindView(R.id.doctor_icon)
    ImageView img_icon;

    @BindView(R.id.doctor_name)
    TextView tv_name;
    @BindView(R.id.doctor_title)
    TextView tv_title;
    @BindView(R.id.doctor_zxwz)
    RelativeLayout tv_zxwz;
    @BindView(R.id.doctor_yygh)
    RelativeLayout tv_yygh;
    @BindView(R.id.doctor_adept)
    TextView tv_adept;
    @BindView(R.id.doctor_comment_count)
    TextView tv_commentCount;
    @BindView(R.id.doctor_status)
    TextView tv_status;
    @BindView(R.id.doctor_jiaqian)
    TextView tv_jiaqian;

    @BindView(R.id.doctor_bg_view)
    View bg_view;
    @BindView(R.id.view_introduce)
    View view_introduce;
    @BindView(R.id.doctor_introduce_start)
    TextView tv_introduce_start;
    @BindView(R.id.doctor_introduce_end)
    TextView tv_introduce_end;
    @BindView(R.id.doctor_introduce_zl)
    TextView tv_zl;
    @BindView(R.id.doctor_introduce_grjj)
    TextView tv_grjj;
    @BindView(R.id.doctor_introduce_sc)
    TextView tv_sc;

    @BindView(R.id.doctor_adept_showtext)
    ShowTextLinearLayout showText_adept;

    @BindView(R.id.doctor_work_table)
    TableLinearLayout work_table;

    @BindView(R.id.doctor_recycleview)
    MyRecycleView recyclerview;
    zmc_EssayCommentRecycleviewAdapter adapter;


    View loadView;
    rocketAnimLoadingUtil animLoadingUtil;

    // 动画
    private Animation slidingViewEnder;
    private Animation slidingViewExit;

    String docid;
    /**医生值班表实体**/
    DocArrangement docArrangement;
    /**医生详情**/
    zmc_docInfo docInfo;
    /**当前医生是否被用户收藏**/
    private boolean isCollection = false;

    Gson gson;
    UserInfoLogin user ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zmc_activity_doctor_detail);
        ButterKnife.bind(this);
        docid = getIntent().getStringExtra("docid");

        gson = new Gson();
        String s = SharedPreferencesTools.GetUsearInfo(this, "userSave", "userInfo");
        user = gson.fromJson(s, UserInfoLogin.class);

        loadView = findViewById(R.id.loadig_anim_view);
        animLoadingUtil = new rocketAnimLoadingUtil(loadView);
        animLoadingUtil.startAnim();
        animLoadingUtil.setListener(this);

        getDocInfo(docid);
        getDocCom(docid);
        tv_zxwz.setOnClickListener(this);
        tv_yygh.setOnClickListener(this);
        tv_introduce_start.setOnClickListener(this);
        tv_introduce_end.setOnClickListener(this);
        img_collection.setOnClickListener(this);
        slidingViewEnder = AnimationUtils.loadAnimation(this,
                R.anim.anim_introduce_view_enter);
        slidingViewExit = AnimationUtils.loadAnimation(this,
                R.anim.anim_introduce_view_exit);

    }

    /**医生评论**/
    List<DocCommend> docCom = new ArrayList<>();
    /**
     * 获取医生评论
     * @param docid
     */
    private void getDocCom(String docid) {
        RetrofitClient.getinstance(this).create(GetService.class).getDocCom(docid).enqueue(new Callback<List<DocCommend>>() {
            @Override
            public void onResponse(Call<List<DocCommend>> call, Response<List<DocCommend>> response) {
                if(response.isSuccessful()){
                    docCom = response.body();
                    tv_commentCount.setText("患者评论 （"+docCom.size()+"条）");
                    showList();
                }
            }
            @Override
            public void onFailure(Call<List<DocCommend>> call, Throwable t) {

            }
        });
    }

    /**
     * 显示医生评论
     */
    private void showList() {
        adapter = new zmc_EssayCommentRecycleviewAdapter(this,docCom);
        FullyLinearLayoutManager linearLayoutManager = new FullyLinearLayoutManager(this);
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        recyclerview.setLayoutManager(linearLayoutManager);
        recyclerview.setAdapter(adapter);

    }

    /**
     * 获取医生详情
     * @param docid
     */
    private void getDocInfo(String docid) {

        RetrofitClient.getinstance(this).create(GetService.class).getdocInfo(docid,user.getUserid()).enqueue(new Callback<zmc_docInfo>() {
            @Override
            public void onResponse(Call<zmc_docInfo> call, Response<zmc_docInfo> response) {
                animLoadingUtil.loadSucc();
                if(response.isSuccessful()){
                    docInfo = response.body();
                    Log.e("getDocInfo","succ : "+docInfo.toString());
                    init();
                }
            }

            @Override
            public void onFailure(Call<zmc_docInfo> call, Throwable t) {
                animLoadingUtil.loadFail();
                getCacheData();
            }
        });
    }

    /**
     * 获取缓存数据
     */
    private void getCacheData() {
        String all = SharedPreferencesTools.GetAllDoc(this, "userSave", "allDoc");
        if(!TextUtils.isEmpty(all)){
            List<SearchDetailsBean.Doctors> doctors_list = gson.fromJson(all,new TypeToken<List<SearchDetailsBean.Doctors>>(){}.getType());
            for (int i = 0; i < doctors_list.size() ; i++) {
                if(doctors_list.get(i).getDoctorid().equals(docid)){
                    animLoadingUtil.loadSucc();
                    showCacheData(doctors_list.get(i));
                    return ;
                }
            }
        }
    }

    /**
     * 显示缓存数据
     * @param docInfo
     */
    private void showCacheData(SearchDetailsBean.Doctors docInfo) {

        isCollection = false;
        img_collection.setSelected(isCollection);

        ImageLoader.getInstance().displayImage(docInfo.getIcon(),img_icon);
        tv_name.setText(docInfo.getName());
        tv_title.setText(docInfo.getTitle()+"   "+docInfo.getSection());
        String adept = docInfo.getAdept();
        tv_zl.setText(docInfo.getSeniority());
        tv_grjj.setText(docInfo.getBio());
        String[] split = adept.split(";");
        tv_adept.setText(split[0]);
        tv_sc.setText(split[0]);
        List<String> list = new ArrayList<>();
        try {
            String[] split1 = split[1].split(",");
            for (int i = 0; i <split1.length ; i++) {
                list.add(split1[i]);
            }
        }catch (ArrayIndexOutOfBoundsException e){

        }
        if(docInfo.getCallcost().equals("1")){
            tv_jiaqian.setText("不可用");
            tv_jiaqian.setTextColor(getResources().getColor(R.color.bg));
        }else if(docInfo.getCallcost().equals("0")){
            tv_jiaqian.setText(docInfo.getChatcost()+"元/次");
        }else if(docInfo.getCallcost().equals("2")){
            tv_jiaqian.setText(docInfo.getChatcost()+"元/次");
        }
        if(docInfo.getIsexpert() == 1){ //专家
            tv_status.setText("不可用");
            tv_status.setTextColor(getResources().getColor(R.color.bg));
        }else if(docInfo.getIsexpert() == 0) { //普通
            tv_status.setText("免费");
        }

        showText_adept.setData(list);

        List<String> title = new ArrayList<>();
        title.add("周一");
        title.add("周二");
        title.add("周三");
        title.add("周四");
        title.add("周五");
        title.add("周六");
        title.add("周天");
        List<Integer> data = new ArrayList<>();
        work_table.setData(title,data);
    }

    private static final String TAG = "YYGHInfoActivity";
    /**
     * 填充数据
     */
    private void init(){
        isCollection = docInfo.isCollected();
        img_collection.setSelected(isCollection);

        ImageLoader.getInstance().displayImage(docInfo.getIcon(),img_icon);
        tv_name.setText(docInfo.getName());
        tv_title.setText(docInfo.getTitle()+"   "+docInfo.getSection());
        String adept = docInfo.getAdept();
        tv_zl.setText(docInfo.getSeniority());
        tv_grjj.setText(docInfo.getBio());
        String[] split = adept.split(";");
        tv_adept.setText(split[0]);
        tv_sc.setText(split[0]);
        List<String> list = new ArrayList<>();
        try {
            String[] split1 = split[1].split(",");
            for (int i = 0; i <split1.length ; i++) {
                list.add(split1[i]);
            }
        }catch (ArrayIndexOutOfBoundsException e){

        }
        if(docInfo.getCallcost().equals("1")){
            tv_jiaqian.setText("不可用");
            tv_jiaqian.setTextColor(getResources().getColor(R.color.bg));
        }else if(docInfo.getCallcost().equals("0")){
            tv_jiaqian.setText(docInfo.getChatcost()+"元/次");
        }else if(docInfo.getCallcost().equals("2")){
            tv_jiaqian.setText(docInfo.getChatcost()+"元/次");
        }
        if(docInfo.getIsexpert() == 1){ //专家
            tv_status.setText("不可用");
            tv_status.setTextColor(getResources().getColor(R.color.bg));
        }else if(docInfo.getIsexpert() == 0) { //普通
            tv_status.setText("免费");
        }
        showText_adept.setData(list);

        List<String> title = new ArrayList<>();
        title.add("周一");
        title.add("周二");
        title.add("周三");
        title.add("周四");
        title.add("周五");
        title.add("周六");
        title.add("周天");
        List<Integer> data = new ArrayList<>();
        try{
            docArrangement = docInfo.getArrangement();

            data.add(docArrangement.getMonday());
            data.add(docArrangement.getTuesday());
            data.add(docArrangement.getWensday());
            data.add(docArrangement.getThursday());
            data.add(docArrangement.getFriday());
            data.add(docArrangement.getSaturday());
            data.add(docArrangement.getSunday());
        }catch (NullPointerException e){
            tv_jiaqian.setTextColor(getResources().getColor(R.color.bg));
            tv_jiaqian.setText("不可用");
            Toast.makeText(this,"该医生暂无排班信息",Toast.LENGTH_SHORT).show();
        }
        work_table.setData(title,data);
    }

    @Override
    public void onClick(View v) {
        if(v == tv_yygh){
            if(docInfo != null){
                if(docInfo.getCallcost().equals("0")){
                    if(docArrangement == null){
//                    Toast.makeText(this,"该医生暂不提供预约挂号",Toast.LENGTH_SHORT).show();
                    }else{
                        Intent intent = new Intent(DoctorDetailActivity.this, YYGHInfoActivity.class);
                        intent.putExtra("docInfo",docInfo);
                        startActivity(intent);
                    }
                }else if(docInfo.getCallcost().equals("1")){
                    Toast.makeText(this,"当前医生暂不提供预约挂号功能",Toast.LENGTH_SHORT).show();
                }else if(docInfo.getCallcost().equals("2")){
                    showWindow(1);
                }
            }else{
                Toast.makeText(this,"获取医生信息失败",Toast.LENGTH_SHORT).show();
            }
        }else if(v == tv_zxwz){
            if("免费".equals(tv_status.getText().toString())){
                showWindow(0);
            }
        }else if(v == tv_introduce_start){
            animation();
        }else if(v == tv_introduce_end){
            animation();
        }else if(v == img_collection){//收藏  取消收藏
            collection();
        }
    }

    @OnClick(R.id.doctor_back)
    public void docBack(){
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        if(showViewFlag == 1){
            animation();
            return;
        }
        if(!isCollection){
            setResult(RESULT_OK);
        }
        super.onBackPressed();
    }

    /**
     * 收藏或者取消收藏
     */
    private void collection() {
        if (ToolUtils.isFastDoubleClick()) {

        } else {
            if (isCollection) {//当前收藏了
                RetrofitClient.getinstance(this).create(GetService.class).delCollDoc(user.getUserid(),docid).enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if(response.isSuccessful()) {
                            String body = response.body();
                            if (body.equals("true")) {
                                isCollection = false;
                                img_collection.setSelected(isCollection);
                            }
                        }
                    }
                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Log.e("getDocInfo","取消收藏fail : "+t.getMessage());
                    }
                });
            } else { //当前没有收藏
                RetrofitClient.getinstance(this).create(GetService.class).collDoc(user.getUserid(),docid).enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if(response.isSuccessful()){
                            String body = response.body();
                            if (body.equals("true")) {
                                isCollection = true;
                                img_collection.setSelected(isCollection);
                            }
                        }
                    }
                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Log.e("getDocInfo","收藏fail : "+t.getMessage());
                    }
                });
            }
        }
    }

    PopupWindow mPopupWindow;
    View contentView;

    /**
     * 弹出问诊window
     * @param flag 0：弹出问诊框  1：弹出打电话框
     */
    private void showWindow(int flag) {
        mPopupWindow = new PopupWindow(LinearLayoutCompat.LayoutParams.WRAP_CONTENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT);
        contentView = LayoutInflater.from(this).inflate(R.layout.zmc_window_wenzhen, null);
        mPopupWindow.setContentView(contentView);
        //为mPopupWindow设置点击其他地方消失
        mPopupWindow.setFocusable(true);
        Bitmap bitmap = null;
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable(getResources(), bitmap));
        mPopupWindow.showAtLocation(contentView, Gravity.CENTER, 0, 0);

        //取消
        contentView.findViewById(R.id.zmc_window_cancle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();
            }
        });
        if(flag == 0){
            ((TextView)contentView.findViewById(R.id.zmc_window_title)).setText("是否前往问诊");
            //确认
            contentView.findViewById(R.id.zmc_window_sure).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPopupWindow.dismiss();
                    Intent intent = new Intent(DoctorDetailActivity.this, imChatActivity.class);
                    intent.putExtra("name", docInfo.getName());
                    intent.putExtra("icon", docInfo.getIcon());
                    intent.putExtra("doctorID", docInfo.getDoctorid());
                    intent.putExtra("username", docInfo.getUsername());
                    intent.putExtra("flag", "1");
                    startActivity(intent);
                }
            });
        }else if(flag == 1){
            ((TextView)contentView.findViewById(R.id.zmc_window_title)).setText("如需立即挂号请拨打\n028-85056688");
            ((TextView)contentView.findViewById(R.id.zmc_window_sure)).setText("拨打");
            //拨打
            contentView.findViewById(R.id.zmc_window_sure).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPopupWindow.dismiss();
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:028-85056688"));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            });
        }


        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.5f;
        getWindow().setAttributes(lp);
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1f;
                getWindow().setAttributes(lp);
            }
        });

    }

    /**当前介绍打开标记**/
    private int showViewFlag = 0;
    /**
     * 医生简介执行动画方法
     */
    private void animation(){
        if (showViewFlag == 0) {// 当前显示主界面 执行菜单出现动画
            view_introduce.startAnimation(slidingViewEnder);
            view_introduce.setVisibility(View.VISIBLE);
            bg_view.setVisibility(View.VISIBLE);
            tv_introduce_start.setVisibility(View.GONE);
            showViewFlag = showViewFlag == 0 ? 1 : 0;
        } else if (showViewFlag == 1) {// 当前显示菜单界面 执行菜单退出动画
            view_introduce.startAnimation(slidingViewExit);
            try {
                Thread.sleep(350);
                view_introduce.setVisibility(View.GONE);
                bg_view.setVisibility(View.GONE);
                tv_introduce_start.setVisibility(View.VISIBLE);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            showViewFlag = showViewFlag == 0 ? 1 : 0;
        }
    }

    @Override
    public void onAnimClick() {
        animLoadingUtil.startAnim();
        if(docInfo == null){
            getDocInfo(docid);
        }
        if(docCom == null){
            getDocCom(docid);
        }
    }
}
