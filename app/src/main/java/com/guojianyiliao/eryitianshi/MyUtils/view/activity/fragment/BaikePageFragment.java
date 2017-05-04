package com.guojianyiliao.eryitianshi.MyUtils.view.activity.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.guojianyiliao.eryitianshi.MyUtils.utlis.MyLogcat;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.UIUtils;
import com.guojianyiliao.eryitianshi.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 百科页面
 * jnluo
 */
public class BaikePageFragment extends Fragment {


    @BindView(R.id.tab_set)
    TabLayout tabSet;
    @BindView(R.id.vp_set)
    ViewPager vpSet;
    @BindView(R.id.ivb_back_finsh)
    ImageButton ivbBackFinsh;
    @BindView(R.id.tv_foot_center)
    TextView tvFootCenter;

    private ArrayList<String> tabTitle = new ArrayList<String>() {{
        add("疾病");
        add("文章");
        add("课堂");
    }};
    // private ArrayList<Fragment> fragmentList = new ArrayList<Fragment>();
    private ArrayList<Fragment> fragmentList = new ArrayList<Fragment>();
        /* {{add(new DoctoringFragment());
        add(new HotingFragment());
        add(new SchoolFragment());}};*/


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //View view = UIUtils.getinflate(R.layout.a_fragment_baike);
        View view = inflater.inflate(R.layout.a_fragment_baike, container, false);
        ButterKnife.bind(this, view);
        ivbBackFinsh.setVisibility(View.INVISIBLE);
        tvFootCenter.setText("百科");
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        int i = UIUtils.dip2px(6);
        MyLogcat.jLog().e("dip2px:" + i);


        DoctoringFragment doctoringFragment = new DoctoringFragment();
        HotingFragment hotingFragment = new HotingFragment();
        SchoolFragment schoolFragment = new SchoolFragment();
        fragmentList.add(doctoringFragment);
        fragmentList.add(hotingFragment);
        fragmentList.add(schoolFragment);

        MyAdapter adapter = new MyAdapter(getChildFragmentManager(), tabTitle, fragmentList);
        vpSet.setAdapter(adapter);
        tabSet.setupWithViewPager(vpSet);
        //tabSet.setupWithViewPager(vpSet, true);
        // tabSet.setTabsFromPagerAdapter(adapter);

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
