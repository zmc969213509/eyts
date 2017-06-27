package com.guojianyiliao.eryitianshi.MyUtils.utlis;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.guojianyiliao.eryitianshi.R;

/**
 * Created by zmc on 2017/6/15.
 * 火箭动画加载类
 */

public class rocketAnimLoadingUtil {

    TextView tv;
    ImageView img;
    TextView btn;
    View animView;
    Listener listener;

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public rocketAnimLoadingUtil(View animView) {
        this.animView = animView;
        Log.e("DoctorDetailActivity","animView是否为空 " + (animView == null));
        tv = (TextView) this.animView.findViewById(R.id.zmc_loading_anim_tv);
        img = (ImageView) this.animView.findViewById(R.id.zmc_loading_anim_img);
        btn = (TextView) this.animView.findViewById(R.id.zmc_loading_anim_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null){
                    listener.onAnimClick();
                }
            }
        });
    }

    public void startAnim(){
        tv.setVisibility(View.VISIBLE);
        img.setVisibility(View.VISIBLE);
        btn.setVisibility(View.VISIBLE);
        animView.setVisibility(View.VISIBLE);
        tv.setText("内容加载中");
        img.setImageResource(R.drawable.loading);
        btn.setVisibility(View.GONE);
    }

    public void loadFail(){
        tv.setText("内容被外星人劫持了");
        img.setImageResource(R.drawable.loadfail);
        btn.setVisibility(View.VISIBLE);
    }

    public void loadSucc(){
        tv.setVisibility(View.GONE);
        img.setVisibility(View.GONE);
        btn.setVisibility(View.GONE);
        animView.setVisibility(View.GONE);
    }

    public interface Listener{
        void onAnimClick();
    }
}
