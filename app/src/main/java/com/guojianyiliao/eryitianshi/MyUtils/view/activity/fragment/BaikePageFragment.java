package com.guojianyiliao.eryitianshi.MyUtils.view.activity.fragment;

import android.content.Context;
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
import android.widget.TextView;

import com.guojianyiliao.eryitianshi.MyUtils.utlis.MyLogcat;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.UIUtils;
import com.guojianyiliao.eryitianshi.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 百科页面
 * jnluo
 */
public class BaikePageFragment extends Fragment {


    @BindView(R.id.bk_tab_set)
    TabLayout tabSet;
    @BindView(R.id.bk_vp_set)
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
    private ArrayList<Fragment> fragmentList = new ArrayList<Fragment>();

                                                                                                                                                                                                                                           @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.a_fragment_baike, container, false);
        ButterKnife.bind(this, view);
        ivbBackFinsh.setVisibility(View.INVISIBLE);
        tvFootCenter.setText("百科");
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        EventBus.getDefault().register(this);

        DoctoringFragment doctoringFragment = new DoctoringFragment();
        HotingFragment hotingFragment = new HotingFragment();
        SchoolFragment schoolFragment = new SchoolFragment();
        fragmentList.add(doctoringFragment);
        fragmentList.add(hotingFragment);
        fragmentList.add(schoolFragment);

        MyAdapter adapter = new MyAdapter(getChildFragmentManager(), tabTitle, fragmentList);
        vpSet.setAdapter(adapter);
        tabSet.setupWithViewPager(vpSet);

        /**
         * 对tabLayout的点击事件进行处理
         */
//        for (int i = 0; i < tabSet.getTabCount() ; i++) {
//            TabLayout.Tab tab = tabSet.getTabAt(i);
//            if(tab != null){
//                if (tab.getCustomView() != null) {
//                    View tabView = (View) tab.getCustomView().getParent();
//                    tabView.setTag(i);
//                    tabView.setOnClickListener(mTabOnClickListener);
//                }
//            }
//        }
    }
//    private View.OnClickListener mTabOnClickListener = new View.OnClickListener() {
//        @Override
//        public void onClick(View view) {
//            int pos = (int) view.getTag();
//            TabLayout.Tab tab = tabSet.getTabAt(pos);
//            if (tab != null) {
//                tab.select();
//            }
//        }
//    };

    //evenbus接受消息
    @Subscribe
    public void BaikePageFragment(String msg) {
        Log.e("zmc_HomeActivity","接收到消息："+msg);
        if(msg.equals("dis")){
            tabSet.getTabAt(0).select();
        }else if(msg.equals("article")){
            tabSet.getTabAt(1).select();
        }else if(msg.equals("school")){
            tabSet.getTabAt(2).select();
        }
    }

    @Override
    public void onDestroyView() {
        EventBus.getDefault().unregister(this);
        super.onDestroyView();
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
