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
import android.widget.TextView;

import com.guojianyiliao.eryitianshi.MyUtils.bean.myCasesBean;
import com.guojianyiliao.eryitianshi.MyUtils.interfaceservice.GetService;
import com.guojianyiliao.eryitianshi.MyUtils.manager.RetrofitClient;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.MyLogcat;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.SpUtils;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.UIUtils;
import com.guojianyiliao.eryitianshi.MyUtils.view.activity.DiseaseRecordDetail;
import com.guojianyiliao.eryitianshi.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 我的_我的诊疗_病历记录
 * jnluo
 */
public class DiseaseRecordFragment extends Fragment {

    String userid;
    @BindView(R.id.recy_drug)
    RecyclerView recyDrug;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userid = SpUtils.getInstance(getActivity()).get("userid", null);
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

    }

    private void HttpData() {
        RetrofitClient.getinstance(getActivity()).create(GetService.class).getMyCases(userid).enqueue(new Callback<List<myCasesBean>>() {
            @Override
            public void onResponse(Call<List<myCasesBean>> call, Response<List<myCasesBean>> response) {
                if (response.isSuccessful()) {
                    MyLogcat.jLog().e("我的病历 onresponse：" + response.body().size());
                    List<myCasesBean> body = response.body();
                    for (myCasesBean bean : body) {
                        MyLogcat.jLog().e("" + bean.casename);
                    }
                    recyDrug.setLayoutManager(new LinearLayoutManager(getActivity()) {{
                        setOrientation(OrientationHelper.VERTICAL);
                    }});
                    myDiseaseAdapter myDiseaseAdapter = new myDiseaseAdapter(body);
                    recyDrug.setAdapter(myDiseaseAdapter);
                   // myDiseaseAdapter.notifyAll();
                }
            }

            @Override
            public void onFailure(Call<List<myCasesBean>> call, Throwable t) {
                MyLogcat.jLog().e("我的病历 onfail：");
            }
        });
    }

    class myDiseaseAdapter extends RecyclerView.Adapter {

        List<myCasesBean> data;

        public myDiseaseAdapter(List<myCasesBean> data) {
            this.data = data;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = UIUtils.getinflate(R.layout.a_fragment_disease_record);
            myDiseaseHolder myDiseaseHolder = new myDiseaseHolder(view);
            return myDiseaseHolder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            final myDiseaseHolder myholder1 = (myDiseaseHolder) holder;
            myholder1.tv_1.setText("疾病：" + data.get(position).casename);
            myholder1.tv_2.setText("疾病：" + data.get(position).secname);
            myholder1.tv_3.setText("疾病：" + data.get(position).hospitalname);
            myholder1.tv_4.setText("疾病：" + data.get(position).docname);
            myholder1.detail1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), DiseaseRecordDetail.class);
                    intent.putExtra("caseid", data.get(position).caseid);
                    MyLogcat.jLog().e("caseid:" + data.get(position).caseid);
                    startActivity(intent);
                }
            });

        }

        @Override
        public int getItemCount() {
            return data.size();
        }
    }

    class myDiseaseHolder extends RecyclerView.ViewHolder {
        TextView tvstartend;
        TextView tv_1;
        TextView tv_2;
        TextView tv_3;
        TextView tv_4;
        LinearLayout detail1;

        public myDiseaseHolder(View itemView) {
            super(itemView);
            tvstartend = (TextView) itemView.findViewById(R.id.start_end_time);
            tv_1 = (TextView) itemView.findViewById(R.id.tv_1);
            tv_2 = (TextView) itemView.findViewById(R.id.tv_2);
            tv_3 = (TextView) itemView.findViewById(R.id.tv_3);
            tv_4 = (TextView) itemView.findViewById(R.id.tv_4);
            detail1 = (LinearLayout) itemView.findViewById(R.id.detail1);
        }

    }
}
