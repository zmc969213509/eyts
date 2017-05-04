package com.guojianyiliao.eryitianshi.page.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.guojianyiliao.eryitianshi.R;

/**
 * Created by Administrator on 2016/9/6 0006.
 *
 */
public class RegistrationView extends View {
    private Paint paint;
    private RectF oval;
    private int baseColor;


    public RegistrationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        baseColor = getResources().getColor(R.color.pink);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int viewWidth = MeasureSpec.getSize(widthMeasureSpec);
        int viewHeight = MeasureSpec.getSize(heightMeasureSpec);
        oval = new RectF(0, 0, viewWidth, viewHeight);
        setMeasuredDimension(viewWidth, viewHeight);
        paint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setColor(baseColor);
        canvas.drawArc(oval, 0, 360, true, paint);
    }
}
