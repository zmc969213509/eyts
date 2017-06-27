package com.guojianyiliao.eryitianshi.MyUtils.adaper;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.guojianyiliao.eryitianshi.MyUtils.bean.BaikeHotTalkBean;
import com.guojianyiliao.eryitianshi.MyUtils.view.activity.view.RoundCornerImageView;
import com.guojianyiliao.eryitianshi.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/6/19.
 */

public class zmc_BaikeWenzRecycleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    /**显示脚部布局**/
    private static final int TYPE_FOOTER = 1;
    /**显示数据布局**/
    private static final int TYPE_ITEM = 0;

    private List<BaikeHotTalkBean> data;
    /**没有更多数据标识**/
    private boolean DataNoMore = false;
    private FootViewHolder footViewHolder;
    /**是否需要分页加载数据   1：不支持上拉加载更多  2：支持上拉加载更多**/
    private int flag = -1;
    private Context context;
    private LayoutInflater inflater;
    private onItemClickListener listener;


    public void setListener(onItemClickListener listener) {
        this.listener = listener;
    }

    public void setDataNoMore(boolean dataNoMore) {
        DataNoMore = dataNoMore;
        if(footViewHolder != null){
            footViewHolder.bind();
        }
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public zmc_BaikeWenzRecycleAdapter(List<BaikeHotTalkBean> data, int flag, Context context) {
        this.data = data;
        this.flag = flag;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getItemViewType(int position) {
        if(flag == 1){
            return TYPE_ITEM;
        }else if(flag == 2){
            if(position + 1 == getItemCount()){
                return TYPE_FOOTER;
            }else{
                return TYPE_ITEM;
            }
        }
        return 0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == TYPE_ITEM){
            View vh = inflater.inflate(R.layout.a_fragment_item_hot, null);
            vh.setOnClickListener(this);
            return new zmc_BaikeWenzRecycleAdapter.ViewHolder(vh);
        }else if(viewType == TYPE_FOOTER){
            if(footViewHolder == null){
                View view = LayoutInflater.from(context).inflate(R.layout.zmc_item_foot_view,null);
                footViewHolder = new zmc_BaikeWenzRecycleAdapter.FootViewHolder(view);
            }
            return footViewHolder;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof zmc_BaikeWenzRecycleAdapter.ViewHolder){
            ((zmc_BaikeWenzRecycleAdapter.ViewHolder)holder).bind(data.get(position),(zmc_BaikeWenzRecycleAdapter.ViewHolder)holder);
            holder.itemView.setTag(position);
        }
    }

    @Override
    public int getItemCount() {
        if(flag == 1){
            return data.size();
        }else if(flag == 2){
            return data.size() == 0 ? 0 : data.size()+1;
        }
        return 0;
    }

    @Override
    public void onClick(View v) {
        if(listener != null){
            listener.onItemClick((int)v.getTag());
        }
    }


    /**
     * 脚部视图
     */
    public class FootViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.foot_view_pb)
        ProgressBar pb;
        @BindView(R.id.foot_view_tv)
        TextView tv;

        public FootViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(){
            if(DataNoMore){
                this.pb.setVisibility(View.GONE);
                this.tv.setText("数据见底啦");
            }else{
                this.pb.setVisibility(View.VISIBLE);
                this.tv.setText("加载中...");
            }
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_hot_icon)
        RoundCornerImageView icon;
        @BindView(R.id.tv_hot_title)
        TextView title;
//        @BindView(R.id.tv_hot_content)
//        TextView content;
        @BindView(R.id.tv_hot_time)
        TextView time;
        @BindView(R.id.tv_hot_collectnumber)
        TextView collectnumber;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(final BaikeHotTalkBean data, final zmc_BaikeWenzRecycleAdapter.ViewHolder holder){
            ImageLoader.getInstance().displayImage(data.getIcon(), holder.icon);
            holder.title.setText(data.getTitle());
//            holder.content.setText(data.getContent());
            holder.time.setText(data.getCtime());
            holder.collectnumber.setText(data.getCollectcount() + "");
        }
    }

    public void Update(List<BaikeHotTalkBean> data){
        this.data = data;
        this.notifyDataSetChanged();
    }

    public interface onItemClickListener{
        /**点击监听**/
        void onItemClick(int position);
    }
}
