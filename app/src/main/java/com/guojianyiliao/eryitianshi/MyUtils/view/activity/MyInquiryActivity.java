package com.guojianyiliao.eryitianshi.MyUtils.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.guojianyiliao.eryitianshi.MyUtils.base.BaseActivity;
import com.guojianyiliao.eryitianshi.MyUtils.view.activity.fragment.DiseaseRecordFragment;
import com.guojianyiliao.eryitianshi.MyUtils.view.activity.fragment.DoctoringFragment;
import com.guojianyiliao.eryitianshi.MyUtils.view.activity.fragment.DrugRemindFragment;
import com.guojianyiliao.eryitianshi.MyUtils.view.activity.fragment.HotingFragment;
import com.guojianyiliao.eryitianshi.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 我的_头部_我的诊疗
 * jnluo
 */
public class MyInquiryActivity extends BaseActivity {

    @BindView(R.id.ivb_back_finsh)
    ImageButton ivbBackFinsh;
    @BindView(R.id.tv_foot_center)
    TextView tvFootCenter;
    @BindView(R.id.iv_add)
    ImageView ivAdd;
    @BindView(R.id.iv_icon)
    CircleImageView ivIcon;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.iv_sex)
    ImageView ivSex;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tab_set)
    TabLayout tabSet;
    @BindView(R.id.vp_set)
    ViewPager vpSet;


    private ArrayList<String> tabTitle = new ArrayList<String>() {{
        add("用药提醒");
        add("病历记录");
    }};
    // private ArrayList<Fragment> fragmentList = new ArrayList<Fragment>();
    DrugRemindFragment drugRemindFragment = new DrugRemindFragment();
    DiseaseRecordFragment dif = new DiseaseRecordFragment();

    private ArrayList<Fragment> fragmentList = new ArrayList<Fragment>() {{
        add(drugRemindFragment);
        add(dif);
    }};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_activity_myhelped);
        ButterKnife.bind(this);


        ivAdd.setVisibility(View.VISIBLE);
        tvFootCenter.setText("我的诊疗");

        MyAdapter myAdapter = new MyAdapter(getSupportFragmentManager(), tabTitle, fragmentList);
        vpSet.setAdapter(myAdapter);
        tabSet.setupWithViewPager(vpSet);
    }

    @OnClick(R.id.ivb_back_finsh)
    public void back() {
        finish();
    }


    /**
     * 添加用药提醒
     */
    @OnClick(R.id.iv_add)
    public void add() {
        startActivity(new Intent(this, AddremindActivity.class));
    }

    class MyAdapter extends FragmentPagerAdapter {

        ArrayList<String> tabTitle;
        private ArrayList<Fragment> fragmentList;

        public MyAdapter(FragmentManager childFragmentManager, ArrayList<String> tabTitle, ArrayList<Fragment> fragmentList) {
            super(childFragmentManager);
            this.tabTitle = tabTitle;
            this.fragmentList = fragmentList;
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitle.get(position);
        }
    }

}
