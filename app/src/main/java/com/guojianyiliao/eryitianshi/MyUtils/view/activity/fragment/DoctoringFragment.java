package com.guojianyiliao.eryitianshi.MyUtils.view.activity.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.guojianyiliao.eryitianshi.MyUtils.bean.AllTypesSecListBean;
import com.guojianyiliao.eryitianshi.MyUtils.bean.CommonDisData;
import com.guojianyiliao.eryitianshi.MyUtils.interfaceservice.GetService;
import com.guojianyiliao.eryitianshi.MyUtils.manager.RetrofitClient;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.MyLogcat;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.UIUtils;
import com.guojianyiliao.eryitianshi.MyUtils.view.activity.view.MyFlowLayout;
import com.guojianyiliao.eryitianshi.MyUtils.view.activity.view.XCFlowLayout;
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
public class DoctoringFragment extends Fragment {

    List<String> isCommon = new ArrayList<>();


    @BindView(R.id.adviser_label_xl)
    XCFlowLayout adviserLabelXl;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HttpCommonData();
        HttpAllTypesDecList();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.a_baike_item_doctoring, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    List<String> DesName = new ArrayList<>();//科室
    List<String> DisName = new ArrayList<>();//疾病

    private void HttpAllTypesDecList() {
        RetrofitClient.getinstance(UIUtils.getContext()).create(GetService.class).AllTypesDecList().enqueue(new Callback<List<AllTypesSecListBean>>() {
            @Override
            public void onResponse(Call<List<AllTypesSecListBean>> call, Response<List<AllTypesSecListBean>> response) {
                if (response.isSuccessful()) {
                    MyLogcat.jLog().e("HttpAllTypesDecList succes:");
                    if (DesName.size() != 0) {
                        MyLogcat.jLog().e("DesName.size():" + DesName.size());
                    }
                    if (DisName.size() != 0) {
                        MyLogcat.jLog().e("DisName.size():" + DisName.size());
                    }
                    List<AllTypesSecListBean> body = response.body();
                    for (int i = 0; i < body.size(); i++) {
                        MyLogcat.jLog().e("科室：" + body.get(i).getName());
                        DesName.add(body.get(i).getName());
                        List<AllTypesSecListBean.DisListBean> disList = body.get(i).getDisList();
                        for (AllTypesSecListBean.DisListBean Data : disList) {
                            MyLogcat.jLog().e(":" + Data.getName());
                            DisName.add(Data.getName());
                        }

                    }
                }
            }

            @Override
            public void onFailure(Call<List<AllTypesSecListBean>> call, Throwable t) {
                MyLogcat.jLog().e("HttpAllTypesDecList fail:");
            }
        });
    }

    private void HttpCommonData() {
        RetrofitClient.getinstance(UIUtils.getContext()).create(GetService.class).getCommonDis().enqueue(new Callback<List<CommonDisData>>() {
            @Override
            public void onResponse(Call<List<CommonDisData>> call, Response<List<CommonDisData>> response) {
                if (response.isSuccessful()) {
                    MyLogcat.jLog().e("success:");
                    List<CommonDisData> body = response.body();
                    if (isCommon.size() != 0) {
                        //MyLogcat.jLog().e("常见疾病 isCommon:" + isCommon.size());
                        isCommon.clear();
                    }
                    for (CommonDisData data : body) {
                        if (data.getIscommon() == 1) {
                            isCommon.add(data.getName());
                        }
                        //MyLogcat.jLog().e("常见疾病 size:" + isCommon.size());
                    }

                    int padding = UIUtils.dip2px(6);
                    adviserLabelXl.removeAllViews();
                    for (int i = 0; i < isCommon.size(); i++) {
                        TextView tvIscommon = new TextView(getActivity());
                        tvIscommon.setText(isCommon.get(i));
                        tvIscommon.setTextSize(UIUtils.px2dip(28));
                        tvIscommon.setTextColor(getResources().getColor(R.color.fontcolor));
                        tvIscommon.setPadding(padding, padding, padding, padding);
                        tvIscommon.setBackgroundResource(R.drawable.af_tx_set_illness);
                        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        lp.setMargins(0, 0, padding, padding);
                        tvIscommon.setLayoutParams(lp);
                        adviserLabelXl.addView(tvIscommon);

                        tvIscommon.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                MyLogcat.jLog().e("tvIscommont:" + v.getId());
                            }
                        });
                    }
                }
            }

            @Override
            public void onFailure(Call<List<CommonDisData>> call, Throwable t) {
                MyLogcat.jLog().e("onFailure:" + call.hashCode());
            }
        });

    }
}
