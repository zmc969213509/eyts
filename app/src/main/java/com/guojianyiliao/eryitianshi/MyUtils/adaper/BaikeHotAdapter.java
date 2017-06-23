package com.guojianyiliao.eryitianshi.MyUtils.adaper;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.guojianyiliao.eryitianshi.MyUtils.bean.BaikeHotTalkBean;
import com.guojianyiliao.eryitianshi.MyUtils.interfaceservice.GetService;
import com.guojianyiliao.eryitianshi.MyUtils.manager.RetrofitClient;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.MyLogcat;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.SharedPreferencesTools;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.SpUtils;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.TimeUtil;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.UIUtils;
import com.guojianyiliao.eryitianshi.MyUtils.view.activity.view.RoundCornerImageView;
import com.guojianyiliao.eryitianshi.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 首页 热门文章
 * jnluo
 */
public class BaikeHotAdapter extends RecyclerView.Adapter {

    private List<BaikeHotTalkBean> hotData;


    public BaikeHotAdapter(List<BaikeHotTalkBean> hotData) {
        this.hotData = hotData;
    }

    public void setData(List<BaikeHotTalkBean> body) {
        this.hotData = body;
        this.notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = UIUtils.getinflate(R.layout.a_fragment_item_hot);
        HotTalkHolder hotTalkHolder = new HotTalkHolder(view);
        return hotTalkHolder;
    }

    String cyclopediaid;

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        HotTalkHolder viewholder = (HotTalkHolder) holder;
        try {
            ImageLoader.getInstance().displayImage(hotData.get(position).icon, viewholder.icon);
            viewholder.title.setText(hotData.get(position).title);
            viewholder.content.setText(hotData.get(position).content);
            viewholder.time.setText(hotData.get(position).ctime);
            viewholder.collectnumber.setText(hotData.get(position).collectcount + "");
        } catch (Exception e) {
            MyLogcat.jLog().e("Exception:" + e.getMessage());
        }
        cyclopediaid = hotData.get(position).cyclopediaid;//文章id
        viewholder.collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyLogcat.jLog().e("收藏成功 Id:" + cyclopediaid);
                httpCollect();
            }


        });
    }

    private void httpCollect() {
        String time = TimeUtil.currectTime();
        MyLogcat.jLog().e("收藏文章 Time:" + time);
        String userid = SharedPreferencesTools.GetUsearId(UIUtils.getContext(),"userSave","userId");
        RetrofitClient.getinstance(UIUtils.getContext()).create(GetService.class).collectCycl(userid, cyclopediaid, time).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    MyLogcat.jLog().e("收藏文章 onResponse:" + response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                MyLogcat.jLog().e("收藏文章 onFailure:");
            }
        });

    }

    @Override
    public int getItemCount() {
        return hotData.size();
    }


    class HotTalkHolder extends RecyclerView.ViewHolder {
        private RoundCornerImageView icon;
        private TextView title;
        private TextView content;
        private TextView time;
        private TextView collectnumber;
        private RelativeLayout collect;

        public HotTalkHolder(View itemView) {
            super(itemView);
            icon = (RoundCornerImageView) itemView.findViewById(R.id.iv_hot_icon);
            title = (TextView) itemView.findViewById(R.id.tv_hot_title);
            content = (TextView) itemView.findViewById(R.id.tv_hot_content);
            time = (TextView) itemView.findViewById(R.id.tv_hot_time);
            collectnumber = (TextView) itemView.findViewById(R.id.tv_hot_collectnumber);
            collect = (RelativeLayout) itemView.findViewById(R.id.rl_foot_collect);
        }
    }
}
