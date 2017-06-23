package com.guojianyiliao.eryitianshi.MyUtils.view.activity.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.guojianyiliao.eryitianshi.MyUtils.bean.UserInfoLogin;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.SharedPreferencesTools;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.SpUtils;
import com.guojianyiliao.eryitianshi.MyUtils.view.activity.MyPublishedActivity;
import com.guojianyiliao.eryitianshi.MyUtils.view.activity.PublishedActivity;
import com.guojianyiliao.eryitianshi.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 成长
 */
public class GrowingPageFragment extends Fragment {

    @BindView(R.id.tv_foot_center)
    TextView tvFootCenter;
    @BindView(R.id.iv_add_edit)
    ImageView ivAddEdit;
    @BindView(R.id.cz_tab_set)
    TabLayout tabSet;
    @BindView(R.id.cz_vp_set)
    ViewPager vpSet;
    @BindView(R.id.ivb_back_finsh)
    ImageButton ivbBackFinsh;
    @BindView(R.id.ivb_published)
    ImageButton ivbPublished;
    private ArrayList<String> tabTitle = new ArrayList<String>() {{
        add("成长树");
        add("我的关注");
    }};
    GrowUpFragment guf ;
    MyWatchlistFragment mwf ;

    private ArrayList<Fragment> fragmentList = new ArrayList<>();

    Gson gson;
    UserInfoLogin user ;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.a_fragment_growing, container, false);
        ButterKnife.bind(this, view);
        ivbBackFinsh.setVisibility(View.GONE);
        ivbPublished.setVisibility(View.VISIBLE);
        tvFootCenter.setText("成长");
        ivAddEdit.setVisibility(View.VISIBLE);

        gson = new Gson();
        String s = SharedPreferencesTools.GetUsearInfo(getActivity(), "userSave", "userInfo");
        user = gson.fromJson(s, UserInfoLogin.class);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        guf = new GrowUpFragment();
        mwf = new MyWatchlistFragment();

        fragmentList.add(guf);
        fragmentList.add(mwf);

        MyAdapter myAdapter = new MyAdapter(getFragmentManager(), tabTitle, fragmentList);
        vpSet.setAdapter(myAdapter);
        tabSet.setupWithViewPager(vpSet);

    }

    /**
     * 发布说说
     */
    @OnClick(R.id.iv_add_edit)
    public void add() {
        startActivity(new Intent(getActivity(), PublishedActivity.class));
    }

    /**
     * 进入我发布的说说界面
     */
    @OnClick(R.id.ivb_published)
    public void MyPublished() {
        Intent intent = new Intent(getActivity(), MyPublishedActivity.class);
        String userid = user.getUserid();
        String name = user.getName();
        String icon = user.getIcon();
        intent.putExtra("name", name);
        intent.putExtra("uuerid", userid);
        intent.putExtra("icon", icon);
        startActivity(intent);
    }


    private class MyAdapter extends FragmentPagerAdapter {

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
