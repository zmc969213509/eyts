package com.guojianyiliao.eryitianshi.Utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.v4.widget.ViewDragHelper;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;


public class MySlideBackLayout extends FrameLayout {

    private ViewGroup mDecorView;

    private View mRootView;

    private Activity mActivity;

    private ViewDragHelper mViewDragHelper;

    private float mSlideWidth;

    private int mScreenWidth;
    private int mScreenHeight;

    private Paint mPaint;

    private int curSlideX;


    public MySlideBackLayout(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {

        mActivity = (Activity) context;
        mViewDragHelper = ViewDragHelper.create(this, new DragCallback());
        mViewDragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_LEFT);
        mPaint = new Paint();
        mPaint.setStrokeWidth(2);
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.GRAY);
    }

    public void bind() {
        mDecorView = (ViewGroup) mActivity.getWindow().getDecorView();
        mRootView = mDecorView.getChildAt(0);
        mDecorView.removeView(mRootView);
        this.addView(mRootView);
        mDecorView.addView(this);


        DisplayMetrics dm = new DisplayMetrics();
        mActivity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        mScreenWidth = dm.widthPixels;
        mScreenHeight = dm.heightPixels;
        mSlideWidth = dm.widthPixels * 0.28f;
    }

    @Override
    public boolean onInterceptHoverEvent(MotionEvent event) {
        return mViewDragHelper.shouldInterceptTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mViewDragHelper.processTouchEvent(event);
        return true;
    }

    class DragCallback extends ViewDragHelper.Callback {

        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return false;
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {

            int left = releasedChild.getLeft();
            if (left <= mSlideWidth) {

                mViewDragHelper.settleCapturedViewAt(0, 0);
            } else {

                mViewDragHelper.settleCapturedViewAt(mScreenWidth, 0);
            }

            invalidate();

        }

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            curSlideX = left;

            invalidate();

            if (changedView == mRootView && left >= mScreenWidth) {
                mActivity.finish();
            }
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {

            left = left >= 0 ? left : 0;
            return left;
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {

            return 0;
        }

        @Override
        public void onEdgeDragStarted(int edgeFlags, int pointerId) {

            mViewDragHelper.captureChildView(mRootView, pointerId);
        }
    }


    @Override
    public void computeScroll() {

        if (mViewDragHelper.continueSettling(true))
            invalidate();

    }

    @Override
    protected void dispatchDraw(Canvas canvas) {

        drawShadow(canvas);
        super.dispatchDraw(canvas);

    }


    private void drawShadow(Canvas canvas) {
        canvas.save();

        Shader mShader = new LinearGradient(curSlideX - 40, 0, curSlideX, 0, new int[]{Color.parseColor("#1edddddd"), Color.parseColor("#6e666666"), Color.parseColor("#9e666666")}, null, Shader.TileMode.REPEAT);

        mPaint.setShader(mShader);

        RectF rectF = new RectF(curSlideX - 40, 0, curSlideX, mScreenHeight);
        canvas.drawRect(rectF, mPaint);
        canvas.restore();
    }

}
