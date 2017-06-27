package com.guojianyiliao.eryitianshi.MyUtils.customView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.ScrollView;

/**
 * Created by zmc on 2017/5/21.
 *  实现scrollview的滑动监听
 *  并且解决了嵌套recycleview 无滑动惯性问题
 */

public class MyScrollView extends ScrollView {

    private int downX;
    private int downY;
    private int mTouchSlop;


    onScrollChangedLinstener linstener;

    public void setLinstener(onScrollChangedLinstener linstener) {
        this.linstener = linstener;
    }

    public MyScrollView(Context context) {
        super(context);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        int action = e.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                downX = (int) e.getRawX();
                downY = (int) e.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                int moveY = (int) e.getRawY();
                if (Math.abs(moveY - downY) > mTouchSlop) {
                    return true;
                }
        }
        return super.onInterceptTouchEvent(e);
    }



    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if(linstener != null){
            linstener.onScrollChange(l, t, oldl, oldt);
        }
    }

    public interface onScrollChangedLinstener{
        void onScrollChange(int l, int t, int oldl, int oldt);
    }
}
