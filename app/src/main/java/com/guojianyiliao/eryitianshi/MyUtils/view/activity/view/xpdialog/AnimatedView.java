package com.guojianyiliao.eryitianshi.MyUtils.view.activity.view.xpdialog;

import android.content.Context;
import android.view.View;

/**
 * Created by baidu on 15/8/31.
 */
class AnimatedView extends View {

    private int target;

    public AnimatedView(Context context) {
        super(context);
    }

    public float getXFactor() {
        return getX() / target;
    }

    public void setXFactor(float xFactor) {
        setX(target * xFactor);
    }

    public void setTarget(int target) {
        this.target = target;
    }

    public int getTarget() {
        return target;
    }
}
