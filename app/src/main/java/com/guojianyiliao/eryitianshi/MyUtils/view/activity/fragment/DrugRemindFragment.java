package com.guojianyiliao.eryitianshi.MyUtils.view.activity.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.guojianyiliao.eryitianshi.MyUtils.bean.DrugRemindBean;
import com.guojianyiliao.eryitianshi.MyUtils.interfaceservice.EventData;
import com.guojianyiliao.eryitianshi.MyUtils.interfaceservice.GetService;
import com.guojianyiliao.eryitianshi.MyUtils.manager.BusProvider;
import com.guojianyiliao.eryitianshi.MyUtils.manager.RetrofitClient;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.DateUtils;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.MyLogcat;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.SpUtils;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.UIUtils;
import com.guojianyiliao.eryitianshi.MyUtils.view.activity.AddremindActivityDetail;
import com.guojianyiliao.eryitianshi.R;
import com.squareup.otto.Subscribe;

import java.text.SimpleDateFormat;
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

public class DrugRemindFragment extends Fragment implements AddremindActivityDetail.RemindListener {
    @BindView(R.id.recy_drug)
    RecyclerView recyDrug;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HttpData();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.a_fragment_drug, container, false);

        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        BusProvider.getInstance().register(getActivity());//订阅事件
    }

    @Subscribe
    public void subscribeEvent(EventData data) {
        MyLogcat.jLog().e("otto:"+ data.getContent());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        BusProvider.getInstance().unregister(getActivity());//注销订阅
    }
// List<DrugRemindBean> Data = new ArrayList<>();

    private void HttpData() {
        // "1492099200000"

        String userid = SpUtils.getInstance(getActivity()).get("userid", null);

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        try {
            String data = DateUtils.timesOne("1492099200000");
            MyLogcat.jLog().e(" q Data:" + data);
        } catch (Exception e) {
            MyLogcat.jLog().e("1492099200000:" + e.getMessage());
        }

        Date curDate = new Date(System.currentTimeMillis());
        String startData = df.format(curDate);
        MyLogcat.jLog().e("startData:" + startData);


        RetrofitClient.getinstance(getActivity()).create(GetService.class).DrugRemind(userid, startData).enqueue(new Callback<List<DrugRemindBean>>() {
            @Override
            public void onResponse(Call<List<DrugRemindBean>> call, Response<List<DrugRemindBean>> response) {
                if (response.isSuccessful()) {
                    MyLogcat.jLog().e("sucess: 12");
                    List<DrugRemindBean> body = response.body();
                    MyLogcat.jLog().e("多少条:" + body.size());
                    // for (DrugRemindBean bean : body) {
                    //Data.add(bean);
                    // MyLogcat.jLog().e(":" + bean.addtime);
                    // }

                    //LinearLayoutManager Manager = new LinearLayoutManager(getActivity());
                    //Manager.setOrientation(OrientationHelper.VERTICAL);
                    recyDrug.setLayoutManager(new LinearLayoutManager(getActivity()) {{
                        setOrientation(OrientationHelper.VERTICAL);
                    }});

                    recyDrug.setAdapter(new myAdapter(body));
                }
            }

            @Override
            public void onFailure(Call<List<DrugRemindBean>> call, Throwable t) {
                MyLogcat.jLog().e("onfail:");
            }
        });
    }

    @Override
    public void setnotify() {

    }

    class myAdapter extends RecyclerView.Adapter {

        private List<DrugRemindBean> data;

        public myAdapter(List<DrugRemindBean> data) {
            this.data = data;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = UIUtils.getinflate(R.layout.a_fragment_drug_item);
            MyHolder myHolder = new MyHolder(view);
            return myHolder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            MyHolder myholder = (MyHolder) holder;
           /* if (data.size() == 2) {
                myholder.rl2.setVisibility(View.VISIBLE);
            } else if (data.size() == 5) {
                myholder.rl2.setVisibility(View.VISIBLE);
                myholder.rl3.setVisibility(View.VISIBLE);
            }*/

            myholder.time1.setText("用药时间：" + data.get(position).time1);
            myholder.time2.setText("用药时间：" + data.get(position).time2);
            myholder.time3.setText("用药时间：" + data.get(position).time3);

            myholder.content1.setText("内容：" + data.get(position).content);
            myholder.content2.setText("内容：" + data.get(position).content2);
            myholder.content3.setText("内容：" + data.get(position).content3);

            myholder.detail1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    nextActivity(position);
                }
            });

            myholder.detail2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    nextActivity(position);
                }
            });

            myholder.detail2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    nextActivity(position);
                }
            });
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        private void nextActivity(int position) {

            Intent intent = new Intent(getActivity(), AddremindActivityDetail.class);
            String remindid = data.get(position).remindid;
            MyLogcat.jLog().e("remindid:" + remindid);
            intent.putExtra("remindid", remindid);
            startActivity(intent);
        }
    }

    class MyHolder extends RecyclerView.ViewHolder {

        private TextView start_end_time;
        private TextView time1;
        private TextView content1;
        private TextView time2;
        private TextView content2;
        private TextView time3;
        private TextView content3;

        private RelativeLayout rl2;
        private RelativeLayout rl3;

        private LinearLayout detail1;
        private LinearLayout detail2;
        private LinearLayout detail3;

        public MyHolder(View itemView) {
            super(itemView);
            start_end_time = (TextView) itemView.findViewById(R.id.start_end_time);
            time1 = (TextView) itemView.findViewById(R.id.time_1);
            content1 = (TextView) itemView.findViewById(R.id.content_1);
            time2 = (TextView) itemView.findViewById(R.id.time_2);
            content2 = (TextView) itemView.findViewById(R.id.content_2);
            time3 = (TextView) itemView.findViewById(R.id.time_3);
            content3 = (TextView) itemView.findViewById(R.id.content_3);

            /**第二，第三提醒*/
            rl2 = (RelativeLayout) itemView.findViewById(R.id.rl_2);
            rl3 = (RelativeLayout) itemView.findViewById(R.id.rl_3);

            detail1 = (LinearLayout) itemView.findViewById(R.id.detail1);
            detail2 = (LinearLayout) itemView.findViewById(R.id.detail2);
            detail3 = (LinearLayout) itemView.findViewById(R.id.detail3);

        }
    }
}
