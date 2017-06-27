package com.guojianyiliao.eryitianshi.MyUtils.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.guojianyiliao.eryitianshi.MyUtils.adaper.HotTalkAdapter;
import com.guojianyiliao.eryitianshi.MyUtils.bean.HotTalkBean;
import com.guojianyiliao.eryitianshi.MyUtils.bean.UserCollectBean;
import com.guojianyiliao.eryitianshi.MyUtils.bean.UserInfoLogin;
import com.guojianyiliao.eryitianshi.MyUtils.interfaceservice.GetService;
import com.guojianyiliao.eryitianshi.MyUtils.manager.RetrofitClient;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.SharedPreferencesTools;
import com.guojianyiliao.eryitianshi.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by zmc on 2017/6/2.
 * 用户收藏的文章
 */

public class MycollectActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private int RESULT_SUC = 10;

    @BindView(R.id.chat_history_title)
    TextView tv_title;
    @BindView(R.id.chat_history_listview)
    ListView listView;

    //正在加载数据布局
    @BindView(R.id.chat_history_loading_view)
    RelativeLayout loading_layout;
    @BindView(R.id.chat_history_loading_img)
    ImageView loading_img;
    @BindView(R.id.chat_history_loading_tv)
    TextView loading_tv;
    @BindView(R.id.chat_history_loading_btn)
    TextView reLoading_tv;

    /**获取到的用户收藏文章**/
    List<UserCollectBean> data = new ArrayList<>();

    Gson gson;
    UserInfoLogin user ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zmc_activity_chat_history);
        ButterKnife.bind(this);
        loading_layout.setVisibility(View.VISIBLE);
        tv_title.setText("我的收藏");

        gson = new Gson();
        String s = SharedPreferencesTools.GetUsearInfo(this, "userSave", "userInfo");
        user = gson.fromJson(s, UserInfoLogin.class);

        getData();
    }

    /**
     * 重新加载
     */
    @OnClick(R.id.chat_history_loading_btn)
    public void chongxianjiazai(){
        getData();
        reLoading_tv.setVisibility(View.GONE);
        loading_img.setImageResource(R.drawable.loading);
        loading_tv.setText("内容加载中");
    }

    @OnClick(R.id.chat_history_back)
    public void back(){
        onBackPressed();
    }

    public void getData() {
        RetrofitClient.getinstance(this).create(GetService.class).getUserCollect(user.getUserid()).enqueue(new Callback<List<UserCollectBean>>() {
            @Override
            public void onResponse(Call<List<UserCollectBean>> call, Response<List<UserCollectBean>> response) {
                if(response.isSuccessful()){
                    data.removeAll(data);
                    data = response.body();
                    Deduplication();
                    loadSucc();
                }
            }

            @Override
            public void onFailure(Call<List<UserCollectBean>> call, Throwable t) {
                Toast.makeText(MycollectActivity.this,"获取数据失败",Toast.LENGTH_SHORT).show();
                loadFail();
            }
        });
    }

    /**
     * 加载数据成功
     */
    private void loadSucc() {
        listView.setVisibility(View.VISIBLE);
        loading_layout.setVisibility(View.GONE);
    }

    /**
     * 加载数据失败
     */
    private void loadFail() {
        loading_img.setImageResource(R.drawable.loadfail);
        loading_tv.setText("内容被外星人劫持了");
        reLoading_tv.setVisibility(View.VISIBLE);
        listView.setVisibility(View.GONE);
    }


    /**去重后的数据**/
    List<UserCollectBean> dataOnly = new ArrayList<>();

    /**
     * 此处获取到的数据有可能会有重复  我们先进行一次去重
     */
    private void Deduplication() {
        dataOnly.removeAll(dataOnly);
        if(data.size() != 0){
            dataOnly.add(data.get(0));
            for (int i = 1; i < data.size() ; i++) {
                for (int j = 0; j < dataOnly.size(); j++) {
                    if(data.get(i).getCyclopediaid().equals(dataOnly.get(j).getCyclopediaid())){
                        break;
                    }
                    if(j == dataOnly.size() - 1){
                        dataOnly.add(data.get(i));
                        break;
                    }
                }
            }

            hotData.removeAll(hotData);
            for (int i = 0; i < dataOnly.size() ; i++) {
                hotData.add(dataOnly.get(i).getCyclopedia());
            }
            showListView();
        }
    }

    /**文章实体**/
    List<HotTalkBean> hotData = new ArrayList();
    HotTalkAdapter hotTalkAdapter;

    public void showListView() {
        if (hotTalkAdapter == null) {
            hotTalkAdapter = new HotTalkAdapter(hotData);
            listView.setAdapter(hotTalkAdapter);
            listView.setOnItemClickListener(this);
        } else {
            hotTalkAdapter.setData(hotData);
        }
    }

    /**用户点击的文章item**/
    private int Selected = 0;
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Selected = position;
        Intent intent = new Intent(this, ArticleDetailActivity.class);
        intent.putExtra("cyclopediaid", hotData.get(Selected).getCyclopediaid());
        intent.putExtra("site", "app.html");
        intent.putExtra("title", hotData.get(position).getTitle());
        intent.putExtra("content", hotData.get(position).getContent());
        intent.putExtra("icon", hotData.get(position).getIcon());
        startActivityForResult(intent,RESULT_SUC);
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        if(isChange){
            Log.e("onActivityResult","data have change");
            hotData.remove(Selected);
            showListView();
            isChange = false;
        }
    }

    /**用户上一次点击文章是否被取消收藏了**/
    private boolean isChange = false;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RESULT_SUC){
            if(resultCode == RESULT_OK){
                isChange = true;
                Log.e("onActivityResult","onActivityResult    isChange = "+isChange);
            }
        }
    }
}
