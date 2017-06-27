package com.guojianyiliao.eryitianshi.MyUtils.adaper;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.guojianyiliao.eryitianshi.MyUtils.bean.AllLecturesBean;
import com.guojianyiliao.eryitianshi.MyUtils.bean.zmc_ChatBean;
import com.guojianyiliao.eryitianshi.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by zmc on 2017/5/24.
 * TODO　聊天adapter
 */

public class zmc_ImChatRecycleviewAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    /**患者对话标记**/
    private static int DIS = 1;
    /**医生对话标记**/
    private static int DOC = 2;

    private List<zmc_ChatBean> data;
    private LayoutInflater inflater;
    private Context context;
    private String docIcon;
    private String disIcon;


    public zmc_ImChatRecycleviewAdapter(Context context, List<zmc_ChatBean> data,String docIcon,String disIcon) {
        this.context = context;
        this.data = data;
        inflater = LayoutInflater.from(context);
        this.disIcon = disIcon;
        this.docIcon = docIcon;
    }

    @Override
    public int getItemViewType(int position) {
        if(data.get(position).getSendFlag().equals("1")){
            return DIS;
        }else if(data.get(position).getSendFlag().equals("2")){
            return DOC;
        }
        return 0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == DIS){//患者聊天
            View view = inflater.inflate(R.layout.zmc_item_imchat_dis,parent,false);
            return new zmc_ImChatRecycleviewAdapter.disViewHolder(view);
        }else if(viewType == DOC){//医生聊天
            View view = inflater.inflate(R.layout.zmc_item_imchat_doc,parent,false);
            return new zmc_ImChatRecycleviewAdapter.docViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof zmc_ImChatRecycleviewAdapter.disViewHolder){
            ((zmc_ImChatRecycleviewAdapter.disViewHolder)holder).bind((zmc_ImChatRecycleviewAdapter.disViewHolder)holder,position);
        }else if(holder instanceof zmc_ImChatRecycleviewAdapter.docViewHolder){
            ((zmc_ImChatRecycleviewAdapter.docViewHolder)holder).bind((zmc_ImChatRecycleviewAdapter.docViewHolder)holder,position);
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class disViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.item_imchat_dis_icon)
        CircleImageView icon;
        @BindView(R.id.item_imchat_dis_text)
        TextView tv_text;
        @BindView(R.id.item_imchat_dis_img)
        ImageView img;
        @BindView(R.id.item_imchat_msg_pb)
        ProgressBar pb;
        @BindView(R.id.item_imchat_msg_fail)
        ImageView fail_img;

        public disViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        public void bind(zmc_ImChatRecycleviewAdapter.disViewHolder vh, int position){
            Log.e("zmc_Adapter",data.get(position).toString());
            ImageLoader.getInstance().displayImage(disIcon,vh.icon);
            if(data.get(position).getMsgFlag().equals("0")){
                pb.setVisibility(View.VISIBLE);
                fail_img.setVisibility(View.GONE);
            } else if (data.get(position).getMsgFlag().equals("1")) {
                pb.setVisibility(View.GONE);
                fail_img.setVisibility(View.GONE);
            }else if(data.get(position).getMsgFlag().equals("-1")){
                pb.setVisibility(View.GONE);
                fail_img.setVisibility(View.VISIBLE);
            }

            if(!data.get(position).getContent().isEmpty()){
                vh.tv_text.setVisibility(View.VISIBLE);
                vh.img.setVisibility(View.GONE);
                vh.tv_text.setText(data.get(position).getContent());
            }else if(!data.get(position).getFilePath().isEmpty()){
                vh.tv_text.setVisibility(View.GONE);
                vh.img.setVisibility(View.VISIBLE);
                ImageLoader.getInstance().displayImage("file:///" + data.get(position).getFilePath(),vh.img);
            }
        }
    }
    class docViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.item_imchat_doc_icon)
        CircleImageView icon;
        @BindView(R.id.item_imchat_doc_text)
        TextView tv_text;
        @BindView(R.id.item_imchat_doc_img)
        ImageView img;

        public docViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
        public void bind(zmc_ImChatRecycleviewAdapter.docViewHolder vh, int position){
            Log.e("zmc_Adapter",data.get(position).toString());
            ImageLoader.getInstance().displayImage(docIcon,vh.icon);
            if(!data.get(position).getContent().isEmpty()){
                vh.tv_text.setVisibility(View.VISIBLE);
                vh.img.setVisibility(View.GONE);
                vh.tv_text.setText(data.get(position).getContent());
            }else if(!data.get(position).getFilePath().isEmpty()){
                vh.tv_text.setVisibility(View.GONE);
                vh.img.setVisibility(View.VISIBLE);
                ImageLoader.getInstance().displayImage("file:///" + data.get(position).getFilePath(),vh.img);
            }
        }
    }

    /**
     * 更新单条数据
     * @param data
     * @param position
     */
    public void UpdateItemChange(List<zmc_ChatBean> data, int position) {
        this.data = data;
        this.notifyItemChanged(position);
    }


    /**
     * 插入单条数据
     */
    public void UpdateItemInserted(List<zmc_ChatBean> data, int position) {
//        //移除某个item
//        list.remove(position);
//        this.notifyItemRemoved(position);
        //添加某个item
        this.data = data;
        this.notifyItemInserted(position);
    }
}
