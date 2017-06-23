package com.guojianyiliao.eryitianshi.MyUtils.view.activity.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.guojianyiliao.eryitianshi.MyUtils.adaper.zmc_PageBaikeRecycleviewAdapter;
import com.guojianyiliao.eryitianshi.MyUtils.bean.TypeDis;
import com.guojianyiliao.eryitianshi.MyUtils.bean.SearchDetailsBean;
import com.guojianyiliao.eryitianshi.MyUtils.customView.FullyLinearLayoutManager;
import com.guojianyiliao.eryitianshi.MyUtils.customView.ShowTextLinearLayout;
import com.guojianyiliao.eryitianshi.MyUtils.interfaceservice.GetService;
import com.guojianyiliao.eryitianshi.MyUtils.manager.RetrofitClient;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.MyLogcat;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.SharedPreferencesTools;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.UIUtils;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.rocketAnimLoadingUtil;
import com.guojianyiliao.eryitianshi.MyUtils.view.activity.DiseasesDetailActivity;
import com.guojianyiliao.eryitianshi.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 百科—疾病页面
 * jnluo
 */
public class DoctoringFragment extends Fragment implements zmc_PageBaikeRecycleviewAdapter.onItemClickLinstener, rocketAnimLoadingUtil.Listener {

    @BindView(R.id.zmc_page_dis_hot)
    ShowTextLinearLayout showTextLinearLayout;

    @BindView(R.id.zmc_page_dis_recycleview)
    RecyclerView recyclerView;

    zmc_PageBaikeRecycleviewAdapter adapter;

    View loadView;
    rocketAnimLoadingUtil animLoadingUtil;

    Gson gson;

    View view;

    /**第一次进入标记**/
    private boolean isFirst = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isFirst = true;
        Log.e("DoctoringFragment","onCreate");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.zmc_page_dis_view, container, false);
        ButterKnife.bind(this, view);
        Log.e("DoctoringFragment","onCreateView");
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        loadView = view.findViewById(R.id.loadig_anim_view);
        animLoadingUtil = new rocketAnimLoadingUtil(loadView);
        gson = new Gson();

        if(isFirst){
            animLoadingUtil.startAnim();
            animLoadingUtil.setListener(this);
            HttpCommonData();
            HttpAllTypesDecList();
            isFirst = false;
        }else{
            showCacheData();
        }
    }

    /**
     * 加载缓存数据
     */
    private void showCacheData(){
        String all = SharedPreferencesTools.GetAllDis(getActivity(), "userSave", "allDis");
        String hot = SharedPreferencesTools.GetAllDis(getActivity(), "userSave", "hotDis");
        Log.e("DoctoringFragment","get = "+ hot);
        if(!TextUtils.isEmpty(all)){
            animLoadingUtil.loadSucc();
            typeDises = gson.fromJson(all,new TypeToken<List<TypeDis>>(){}.getType());
            showAllDis();
        }
        if(!TextUtils.isEmpty(hot)){
            hotDis = gson.fromJson(hot,new TypeToken<List<SearchDetailsBean.Diseases>>(){}.getType());
            showHotDis(hotDis);
        }
    }


    /**所有类型疾病**/
    List<TypeDis> typeDises = new ArrayList<>();
    /**
     * 获取所有疾病
     */
    private void HttpAllTypesDecList() {
        Log.e("DoctoringFragment","HttpAllTypesDecList 获取所有疾病");
        RetrofitClient.getinstance(UIUtils.getContext()).create(GetService.class).AllTypesDecList().enqueue(new Callback<List<TypeDis>>() {
            @Override
            public void onResponse(Call<List<TypeDis>> call, Response<List<TypeDis>> response) {

                if (response.isSuccessful()) {
                    Log.e("DoctoringFragment","HttpAllTypesDecList  succ");
                    animLoadingUtil.loadSucc();
                    typeDises = response.body();
                    SharedPreferencesTools.SaveAllDis(getActivity(),"userSave","allDis",gson.toJson(typeDises));
                    showAllDis();
                }
            }

            @Override
            public void onFailure(Call<List<TypeDis>> call, Throwable t) {
                MyLogcat.jLog().e("HttpAllTypesDecList fail:"+t.getMessage());
                animLoadingUtil.loadFail();
                Toast.makeText(getActivity(),"获取最新数据失败",Toast.LENGTH_SHORT).show();
                showCacheData();
            }
        });
    }

    /**
     * 显示所有疾病
     */
    private void showAllDis() {
        adapter = new zmc_PageBaikeRecycleviewAdapter(typeDises, getActivity());
        adapter.setLinstener(this);
        GridLayoutManager manager = new GridLayoutManager(getActivity(),1,GridLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
    }



    /**
     * 获取常见疾病
     */
    private void HttpCommonData() {
        Log.e("DoctoringFragment","HttpCommonData 获取常见疾病");
        RetrofitClient.getinstance(UIUtils.getContext()).create(GetService.class).getCommonDis().enqueue(new Callback<List<SearchDetailsBean.Diseases>>() {
            @Override
            public void onResponse(Call<List<SearchDetailsBean.Diseases>> call, Response<List<SearchDetailsBean.Diseases>> response) {
                if (response.isSuccessful()) {
                    Log.e("DoctoringFragment","HttpCommonData  succ");
                    hotDis = response.body();
                    String s = gson.toJson(hotDis);
                    SharedPreferencesTools.SaveAllDis(getActivity(),"userSave","hotDis",s);
                    showHotDis(hotDis);
                }
            }

            @Override
            public void onFailure(Call<List<SearchDetailsBean.Diseases>> call, Throwable t) {
                MyLogcat.jLog().e("onFailure:" + call.hashCode());
            }
        });
    }

    /**常见疾病**/
    List<SearchDetailsBean.Diseases> hotDis = new ArrayList<>();
    /**
     * 显示常见疾病
     * @param body
     */
    private void showHotDis(List<SearchDetailsBean.Diseases> body) {

        Log.e("DoctoringFragment","body.size() = " + body.size());
        List<String> list = new ArrayList<>();
        for (int i = 0; i < body.size() ; i++) {
            list.add(body.get(i).getName());
        }
        Log.e("DoctoringFragment","list : " + list);
        showTextLinearLayout.setData(list,getActivity());
        showTextLinearLayout.setListener(new ShowTextLinearLayout.onItemClickListener() {
            @Override
            public void onItemClick(View v) {
                String sear = ((TextView) v).getText().toString();
                for (int i = 0; i < hotDis.size() ; i++) {
                    if(sear.equals(hotDis.get(i).getName())){
                        Intent intent = new Intent(getActivity(),DiseasesDetailActivity.class);
                        intent.putExtra("disid",hotDis.get(i).getDisid());
                        startActivity(intent);
                        return;
                    }
                }
            }
        });

    }

    @Override
    public void onItemClick(String disId) {
        Intent intent = new Intent(getActivity(),DiseasesDetailActivity.class);
        intent.putExtra("disid",disId);
        startActivity(intent);
    }

    @Override
    public void onAnimClick() {
        animLoadingUtil.startAnim();
        try{
            if(hotDis.size() == 0){
                HttpCommonData();
            }
        }catch (NullPointerException e){
            HttpCommonData();
        }
        try{
            if(typeDises.size() == 0){
                HttpAllTypesDecList();
            }
        }catch (NullPointerException e){
            HttpAllTypesDecList();
        }
    }
}
