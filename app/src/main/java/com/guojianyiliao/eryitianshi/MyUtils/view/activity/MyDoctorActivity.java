package com.guojianyiliao.eryitianshi.MyUtils.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.guojianyiliao.eryitianshi.MyUtils.adaper.zmc_SearDoctorListviewAdapter;
import com.guojianyiliao.eryitianshi.MyUtils.bean.SearchDetailsBean;
import com.guojianyiliao.eryitianshi.MyUtils.bean.UserInfoLogin;
import com.guojianyiliao.eryitianshi.MyUtils.bean.zmc_CollDoc;
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
 * Created by zmc on 2017/6/3.
 * 用户收藏医生
 */

public class MyDoctorActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    @BindView(R.id.chat_history_title)
    TextView tv_title;
    @BindView(R.id.chat_history_listview)
    ListView listView;

    zmc_SearDoctorListviewAdapter adapter;

    //正在加载数据布局
    @BindView(R.id.chat_history_loading_view)
    RelativeLayout loading_layout;
    @BindView(R.id.chat_history_loading_img)
    ImageView loading_img;
    @BindView(R.id.chat_history_loading_tv)
    TextView loading_tv;
    @BindView(R.id.chat_history_loading_btn)
    TextView reLoading_tv;

    Gson gson;
    UserInfoLogin user ;

    List<zmc_CollDoc> myDoc = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zmc_activity_chat_history);
        ButterKnife.bind(this);
        loading_layout.setVisibility(View.VISIBLE);
        tv_title.setText("我的医生");

        gson = new Gson();
        String s = SharedPreferencesTools.GetUsearInfo(this, "userSave", "userInfo");
        user = gson.fromJson(s, UserInfoLogin.class);

        getData();

    }

    /**
     * 获取数据
     */
    public void getData() {
        RetrofitClient.getinstance(this).create(GetService.class).getMyDoc(user.getUserid()).enqueue(new Callback<List<zmc_CollDoc>>() {
            @Override
            public void onResponse(Call<List<zmc_CollDoc>> call, Response<List<zmc_CollDoc>> response) {
                if(response.isSuccessful()){
                    myDoc.removeAll(myDoc);
                    docInfo.removeAll(docInfo);
                    myDoc = response.body();
                    for (int i = 0; i < myDoc.size(); i++) {
                        docInfo.add(myDoc.get(i).getDoctor());
                    }
                    showList();
                }
                loadSucc();
            }

            @Override
            public void onFailure(Call<List<zmc_CollDoc>> call, Throwable t) {
                loadFail();
            }
        });
    }

    List<SearchDetailsBean.Doctors> docInfo = new ArrayList<>();
    private void showList() {
        if (adapter == null) {
            adapter = new zmc_SearDoctorListviewAdapter(docInfo, this);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(this);
        } else {
            adapter.Update(docInfo);
        }
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

    /**当前选中的id**/
    private int Selected = 0;
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Selected = position;
        Intent intent = new Intent(this, DoctorDetailActivity.class);
        intent.putExtra("docid", docInfo.get(position).getDoctorid());
        startActivityForResult(intent,0);
    }

    /**当前点击数据是否发生了改变**/
    private boolean isChange = false;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 0){
            if(resultCode == RESULT_OK){
                isChange = true;
            }
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if(isChange){
            docInfo.remove(Selected);
            showList();
            isChange = false;
        }
    }
}
