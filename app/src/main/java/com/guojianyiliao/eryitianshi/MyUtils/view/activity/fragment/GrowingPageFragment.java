package com.guojianyiliao.eryitianshi.MyUtils.view.activity.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.guojianyiliao.eryitianshi.MyUtils.view.activity.MyPublishedActivity;
import com.guojianyiliao.eryitianshi.MyUtils.view.activity.PublishedActivity;
import com.guojianyiliao.eryitianshi.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 *
 */
public class GrowingPageFragment extends Fragment {

    @BindView(R.id.tv_foot_center)
    TextView tvFootCenter;
    @BindView(R.id.iv_add_edit)
    ImageView ivAddEdit;
    @BindView(R.id.tab_set)
    TabLayout tabSet;
    @BindView(R.id.vp_set)
    ViewPager vpSet;
    @BindView(R.id.ivb_back_finsh)
    ImageButton ivbBackFinsh;
    @BindView(R.id.ivb_published)
    ImageButton ivbPublished;
    private ArrayList<String> tabTitle = new ArrayList<String>() {{
        add("成长树");
        add("我的关注");
    }};
    GrowUpFragment guf = new GrowUpFragment();
    MyWatchlistFragment mwf = new MyWatchlistFragment();

    private ArrayList<Fragment> fragmentList = new ArrayList<Fragment>() {{
        add(guf);
        add(mwf);
    }};

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.a_fragment_growing, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ivbBackFinsh.setVisibility(View.GONE);
        ivbPublished.setVisibility(View.VISIBLE);
        tvFootCenter.setText("成长");
        ivAddEdit.setVisibility(View.VISIBLE);
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
        startActivity(new Intent(getActivity(), MyPublishedActivity.class));
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
