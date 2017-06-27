package com.guojianyiliao.eryitianshi.MyUtils.customView;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.guojianyiliao.eryitianshi.R;

/**
 * Created by zmc on 2017/5/31.
 */

public class RefreshListView extends ListView{


    private View mListViewFoot;
    private ImageView animLoadimg;
    private TextView tv_noMoreData,tv_loadFail;


    public RefreshListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
//        init(context);
    }

    public RefreshListView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
//        init(context);
    }

    public RefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
//        init(context);
    }

//    private void init(Context context) {
//        // TODO Auto-generated method stub
//        mListViewFoot = LayoutInflater.from(context).inflate(R.layout.listview_footview, null);
//        //为自定义的listview添加一个脚视图
//        addFooterView(mListViewFoot);
//        animLoadimg = (ImageView) findViewById(R.id.listview_footview_img);
//        tv_loadFail = (TextView) findViewById(R.id.listview_footview_load_fail);
//        tv_loadFail.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                // TODO Auto-generated method stub
//                OnFootClickListener.refresh();
//            }
//        });
//        tv_noMoreData = (TextView) findViewById(R.id.listview_footview_no_more_data);
//    }

    /**
     * 开始加载更多数据时  启动动画 隐藏不相关控件
     */
    private AnimationDrawable mAnim;
//    public void startLoadAnim(){
//        if(mAnim == null){
//            animLoadimg.setBackgroundResource(R.anim.loading);
//            mAnim = (AnimationDrawable) animLoadimg.getBackground();
//        }
//        mAnim.start();
//        animLoadimg.setVisibility(View.VISIBLE);
//        tv_loadFail.setVisibility(View.GONE);
//        tv_noMoreData.setVisibility(View.GONE);
//    }

    /**
     * 数据加载失败 关闭动画 隐藏不相关控件
     */
    public void loadFail(){
        mAnim.stop();
        tv_noMoreData.setVisibility(View.GONE);
        animLoadimg.setVisibility(View.GONE);
        tv_loadFail.setVisibility(View.VISIBLE);
    }

    /**
     * 数据加载成功后 隐藏所有控件 并关闭动画
     */
    public void loadSuccess(){
        mAnim.stop();
        tv_noMoreData.setVisibility(View.GONE);
        animLoadimg.setVisibility(View.GONE);
        tv_loadFail.setVisibility(View.GONE);
    }

    /**
     * 没有更多数据时  关闭动画 隐藏不相关控件
     */
    public void noMoreData(){
        mAnim.stop();
        tv_noMoreData.setVisibility(View.VISIBLE);
        animLoadimg.setVisibility(View.GONE);
        tv_loadFail.setVisibility(View.GONE);
    }


    /**
     * 监听器
     */
    private OnFootClickListener OnFootClickListener;
    public interface OnFootClickListener{
        /**重新加载数据的方法**/
        void refresh();
    }
    public void setOnFootClickListener(OnFootClickListener onFootClickListener) {
        OnFootClickListener = onFootClickListener;
    }

}
