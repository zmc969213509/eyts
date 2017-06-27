package com.guojianyiliao.eryitianshi.MyUtils.adaper;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.guojianyiliao.eryitianshi.MyUtils.bean.DocCommend;
import com.guojianyiliao.eryitianshi.MyUtils.bean.EcommentsBean;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.TimeUtil;
import com.guojianyiliao.eryitianshi.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by zmc on 2017/5/15.
 */

public class zmc_EssayCommentRecycleviewAdapter extends RecyclerView.Adapter<zmc_EssayCommentRecycleviewAdapter.ViewHolder> implements View.OnLongClickListener {


    private List<EcommentsBean> bean;
    private Context context;
    private LayoutInflater  inflater;
    private onItemClickListener listener;
    /**加载内容标记  1：成长树说说评论  2：医生详情界面评论**/
    private int flag = 0;
    private List<DocCommend> commends;

    public zmc_EssayCommentRecycleviewAdapter(Context context, List<DocCommend> commends) {
        this.context = context;
        this.commends = commends;
        inflater = LayoutInflater.from(context);
        flag = 2;
    }

    public zmc_EssayCommentRecycleviewAdapter(List<EcommentsBean> bean, Context context) {
        this.bean = bean;
        this.context = context;
        inflater = LayoutInflater.from(context);
        flag = 1;
    }

    public void setListener(onItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.a_fragment_growup_recyitem,null);
        view.setOnLongClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemView.setTag(position);
        holder.bind(holder,position);
    }

    @Override
    public int getItemCount() {
        if(flag == 1){
            return bean.size();
        }else if(flag == 2){
            return commends.size();
        }
        return 0;
    }

    @Override
    public boolean onLongClick(View v) {
        if(listener != null){
            listener.onItemLongClick(v,(int)v.getTag());
        }
        return false;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        CircleImageView ivIcon;
        TextView tvGrowName;
        TextView tvGrowTime;
        TextView tvGrowItemContent;

        public ViewHolder(View itemView) {
            super(itemView);
            ivIcon = (CircleImageView) itemView.findViewById(R.id.iv_icon);
            tvGrowName = (TextView) itemView.findViewById(R.id.tv_grow_name);
            tvGrowTime = (TextView) itemView.findViewById(R.id.tv_grow_time);
            tvGrowItemContent = (TextView) itemView.findViewById(R.id.tv_grow_item_content);
        }

        public void bind(ViewHolder holder, int position){
            try{
                if(flag == 1){
                    if(bean.get(position).getReplyid().isEmpty()){//评论内容
                        holder.tvGrowItemContent.setText(bean.get(position).getEccontent());
                        ImageLoader.getInstance().displayImage(bean.get(position).getEcUser().getIcon(), holder.ivIcon);
                        holder.tvGrowName.setText(bean.get(position).getEcUser().getName());
                        holder.tvGrowTime.setText(TimeUtil.SJC(bean.get(position).getEctime() + ""));
                    }else{//回复内容
                        holder.tvGrowItemContent.setText("回复 @"+bean.get(position).getEcUser().getName()+" : "+bean.get(position).getRcontent());
                        ImageLoader.getInstance().displayImage(bean.get(position).getReplyUser().getIcon(), holder.ivIcon);
                        holder.tvGrowName.setText(bean.get(position).getReplyUser().getName());
                        holder.tvGrowTime.setText(TimeUtil.SJC(bean.get(position).getEctime() + ""));
                    }
                }else if(flag == 2){
                    //倒序排序
                    holder.tvGrowItemContent.setText(commends.get(commends.size() - 1 - position).getContent());
                    ImageLoader.getInstance().displayImage(commends.get(commends.size() - 1 - position).getAppUser().getIcon(),holder.ivIcon);
                    holder.tvGrowName.setText(commends.get(commends.size() - 1 - position).getAppUser().getName());
                    holder.tvGrowTime.setText(commends.get(commends.size() - 1 - position).getCtime() + "");
                }
            }catch (Exception e){

            }
        }
    }

    public void Update(List<EcommentsBean> bean){
        this.bean = bean;
        this.notifyDataSetChanged();
    }

    public interface onItemClickListener{
        void onItemLongClick(View v,int position);
    }
}
