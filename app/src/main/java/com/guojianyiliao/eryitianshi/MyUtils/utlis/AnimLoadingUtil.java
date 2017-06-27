package com.guojianyiliao.eryitianshi.MyUtils.utlis;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.guojianyiliao.eryitianshi.R;

/**
 * Created by zmc on 2017/6/9.
 * 文字动画加载类
 */

public class AnimLoadingUtil {

    private TextView tv;
    private View animView;
    /**当前动画是否正在运行**/
    private boolean animisRun = false;

    public AnimLoadingUtil(View animView) {
        this.animView = animView;
        tv = (TextView) animView.findViewById(R.id.anim_view_tv);
//        viewGroup = (RelativeLayout) animView.findViewById(R.id.anim_view_layout);
    }

    /**
     * 开启动画
     * @param text
     */
    public void startAnim(String text){
        tv.setText(text+"");
        animView.setVisibility(View.VISIBLE);
    }

    /**
     * 关闭动画
     */
    public void finishAnim(){
        animView.setVisibility(View.GONE);
    }

}
