package com.guojianyiliao.eryitianshi.page.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.EditText;


/**
 *
 */
public class NoteEditor extends EditText {
    /**
     *
     */
    private int drawLine = 5;
    /**
     *
     */
    public int lineDis = 8;
    private Rect mRect;
    private Paint mPaint;

    public NoteEditor(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        mRect = new Rect();
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(android.graphics.Color.parseColor("#bbbbbb"));
    }

    public void setNotesMinLines(int lines) {
        this.drawLine = lines;
        setMinLines(lines);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        int count = getLineCount();
        Rect r = mRect;
        Paint paint = mPaint;
        int basicline = 0;
        //第
        for (int i = 0; i < count; i++) {
            int baseline = getLineBounds(i, r);
            basicline = baseline;
            canvas.drawLine(r.left, baseline + lineDis, r.right, baseline + lineDis, paint);
        }
        //
        if (count < drawLine) {
            for (int j = 1; j < drawLine; j++) {
                int baseline = basicline + j * getLineHeight();
                canvas.drawLine(r.left, baseline + lineDis, r.right, baseline + lineDis, paint);
            }
        }
        super.onDraw(canvas);
    }

}
