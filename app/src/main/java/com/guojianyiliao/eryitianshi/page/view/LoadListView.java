package com.guojianyiliao.eryitianshi.page.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import com.guojianyiliao.eryitianshi.R;

/**
 * Created by Administrator on 2016/11/23 0023.
 */
public class LoadListView extends ListView implements AbsListView.OnScrollListener {
    private View footer;//
    int totalItemCount;//
    int lastVisibieItem;// 最
    boolean isLoading;// 判
    IloadListener iLoadListener;// 接
    public LoadListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public LoadListView(Context context) {
        super(context);
        initView(context);
    }

    public LoadListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context);
        // TODO Auto-generated constructor stub
    }

    // l
    private void initView(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        footer = inflater.inflate(R.layout.charactersafe_listview_foot, null);
        // 设
        footer.findViewById(R.id.footer_layout).setVisibility(View.GONE);
        this.addFooterView(footer);
        this.setOnScrollListener(this);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        // TODO Auto-generated method stub
        if (totalItemCount == lastVisibieItem && scrollState == SCROLL_STATE_IDLE) {
            if (!isLoading) {
                isLoading = true;
                footer.findViewById(R.id.footer_layout).setVisibility(View.VISIBLE);
                // 加载
                iLoadListener.onLoad();
            }
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        // TODO Auto-generated method stub
        this.lastVisibieItem = firstVisibleItem + visibleItemCount;
        this.totalItemCount = totalItemCount;
    }

    public void setInterface(IloadListener iLoadListener) {

        this.iLoadListener = iLoadListener;
    }

    // 加
    public interface IloadListener {
        public void onLoad();
    }

    // 加
    public void loadComplete() {
        isLoading = false;
        footer.findViewById(R.id.footer_layout).setVisibility(View.GONE);

    }

}
