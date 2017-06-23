package com.guojianyiliao.eryitianshi.MyUtils.view.activity.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.guojianyiliao.eryitianshi.MyUtils.adaper.zmc_RemindRecycleviewAdapter;
import com.guojianyiliao.eryitianshi.MyUtils.bean.DrugRemindBean;
import com.guojianyiliao.eryitianshi.MyUtils.interfaceservice.EventData;
import com.guojianyiliao.eryitianshi.MyUtils.interfaceservice.GetService;
import com.guojianyiliao.eryitianshi.MyUtils.manager.BusProvider;
import com.guojianyiliao.eryitianshi.MyUtils.manager.RetrofitClient;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.DateUtils;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.MyLogcat;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.SharedPreferencesTools;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.SpUtils;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.UIUtils;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.rocketAnimLoadingUtil;
import com.guojianyiliao.eryitianshi.MyUtils.view.activity.AddremindActivityDetail;
import com.guojianyiliao.eryitianshi.R;
import com.squareup.otto.Subscribe;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 我的_我的诊疗_用药提醒界面
 * jnluo
 */

public class DrugRemindFragment extends Fragment implements rocketAnimLoadingUtil.Listener {
    private static final String TAG = "DrugRemindFragment";
    @BindView(R.id.recy_drug)
    RecyclerView recyDrug;

    myAdapter adapter;
    List<DrugRemindBean> data = new ArrayList<>();

    View loadView;
    rocketAnimLoadingUtil animLoadingUtil;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.e(TAG,"onCreate");
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.a_fragment_drug, container, false);
        ButterKnife.bind(this, view);
        loadView = view.findViewById(R.id.loadig_anim_view);
        animLoadingUtil = new rocketAnimLoadingUtil(loadView);
        animLoadingUtil.startAnim();
        animLoadingUtil.setListener(this);
        HttpData();
        Log.e(TAG,"onCreateView");
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.e(TAG,"onActivityCreated");
    }



    private void HttpData() {

        String userid = SharedPreferencesTools.GetUsearId(getActivity(),"userSave","userId");

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        Date curDate = new Date(System.currentTimeMillis());
        String startData = df.format(curDate);
        MyLogcat.jLog().e("startData:" + startData);


        RetrofitClient.getinstance(getActivity()).create(GetService.class).DrugRemind(userid, startData).enqueue(new Callback<List<DrugRemindBean>>() {
            @Override
            public void onResponse(Call<List<DrugRemindBean>> call, Response<List<DrugRemindBean>> response) {
                if (response.isSuccessful()) {
                    MyLogcat.jLog().e("sucess: 12");
                    if(data.size() != 0){
                        data.removeAll(data);
                    }
                    animLoadingUtil.loadSucc();
                    data = response.body();
                    showList();
                }
            }

            @Override
            public void onFailure(Call<List<DrugRemindBean>> call, Throwable t) {
                MyLogcat.jLog().e("onfail:");
                animLoadingUtil.loadFail();
            }
        });
    }

    private void showList(){
        if(adapter == null){
            adapter = new myAdapter(data);
            LinearLayoutManager manager = new LinearLayoutManager(getActivity());
            recyDrug.setLayoutManager(manager);
            recyDrug.setAdapter(adapter);
        }else{
            adapter.update(data);
        }
    }

    /**当前选中下标**/
    private int currentIndex = -1;

    @Override
    public void onAnimClick() {
        animLoadingUtil.startAnim();
        HttpData();
    }

    class myAdapter extends RecyclerView.Adapter {

        private List<DrugRemindBean> data;

        public myAdapter(List<DrugRemindBean> data) {
            this.data = data;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = UIUtils.getinflate(R.layout.zmc_item_reminder_yonyao);
            MyHolder myHolder = new MyHolder(view);
            return myHolder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            MyHolder myholder = (MyHolder) holder;

            if(TextUtils.isEmpty(data.get(position).getContent1())){
                myholder.layout1.setVisibility(View.GONE);
            }else{
                myholder.layout1.setVisibility(View.VISIBLE);
            }
            if(TextUtils.isEmpty(data.get(position).getContent2())){
                myholder.layout2.setVisibility(View.GONE);
            }else{
                myholder.layout2.setVisibility(View.VISIBLE);
            }
            if(TextUtils.isEmpty(data.get(position).getContent3())){
                myholder.layout3.setVisibility(View.GONE);
            }else{
                myholder.layout3.setVisibility(View.VISIBLE);
            }

            long reminddate = data.get(position).getReminddate();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date(reminddate);
            String res = simpleDateFormat.format(date);
            myholder.day.setText(res.substring(0, 10));
            myholder.content1.setText("用药时间：" + data.get(position).getTime1()+"\n内容：" + data.get(position).getContent1());
            myholder.content2.setText("用药时间：" + data.get(position).getTime2()+"\n内容：" + data.get(position).getContent2());
            myholder.content3.setText("用药时间：" + data.get(position).getTime3()+"\n内容：" + data.get(position).getContent3());

            myholder.content1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    nextActivity(position,0);
                }
            });

            myholder.content2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    nextActivity(position,1);
                }
            });

            myholder.content3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    nextActivity(position,2);
                }
            });
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        private void nextActivity(int position,int index) {
            currentIndex = position;
            DrugRemindBean drugRemindBean = data.get(position);

            Intent intent = new Intent(getActivity(), AddremindActivityDetail.class);
            intent.putExtra("drugremind",drugRemindBean);
            startActivityForResult(intent,0);
        }

        public void update(List<DrugRemindBean> data){
            this.data = data;
            this.notifyDataSetChanged();
        }
    }

    class MyHolder extends RecyclerView.ViewHolder {

        TextView day;

        TextView content1;
        TextView content2;
        TextView content3;

        private RelativeLayout layout1;
        private RelativeLayout layout2;
        private RelativeLayout layout3;

        public MyHolder(View itemView) {
            super(itemView);
            day = (TextView) itemView.findViewById(R.id.zmc_item_remind_day);
            content1 = (TextView) itemView.findViewById(R.id.zmc_item_remind_tv1);
            content2 = (TextView) itemView.findViewById(R.id.zmc_item_remind_tv2);
            content3 = (TextView) itemView.findViewById(R.id.zmc_item_remind_tv3);

            layout1 = (RelativeLayout) itemView.findViewById(R.id.zmc_item_remind_layout1);
            layout2 = (RelativeLayout) itemView.findViewById(R.id.zmc_item_remind_layout2);
            layout3 = (RelativeLayout) itemView.findViewById(R.id.zmc_item_remind_layout3);
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e(TAG,"onActivityResult");
        if(requestCode == 0){
            if(resultCode == 1){ //修改用药
                HttpData();
            }else if(resultCode == 2){ //删除用药
                this.data.remove(currentIndex);
                showList();
            }
        }
    }
}
