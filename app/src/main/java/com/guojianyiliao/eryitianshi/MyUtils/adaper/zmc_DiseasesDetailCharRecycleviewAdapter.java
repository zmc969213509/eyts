package com.guojianyiliao.eryitianshi.MyUtils.adaper;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.guojianyiliao.eryitianshi.MyUtils.bean.ChatBean;
import com.guojianyiliao.eryitianshi.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by zmc on 2017/5/18.
 */

public class zmc_DiseasesDetailCharRecycleviewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    /**患者对话标记**/
    private static int DIS = 1;
    /**医生对话标记**/
    private static int DOC = 2;

    private List<ChatBean> data;
    private LayoutInflater inflater;
    private Context context;


    public zmc_DiseasesDetailCharRecycleviewAdapter(Context context, List<ChatBean> data) {
        this.context = context;
        this.data = data;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getItemViewType(int position) {
        if(data.get(position).getName().contains("患者")){
            return DIS;
        }else if(data.get(position).getName().contains("医生")){
            return DOC;
        }
        return 0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == DIS){//患者聊天
            View view = inflater.inflate(R.layout.zmc_item_chat_dis,null);
            return new disViewHolder(view);
        }else if(viewType == DOC){//医生聊天
            View view = inflater.inflate(R.layout.zmc_item_chat_doc,null);
            return new docViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof disViewHolder){
            ((disViewHolder)holder).bind((disViewHolder)holder,position);
        }else if(holder instanceof  docViewHolder){
            ((docViewHolder)holder).bind((docViewHolder)holder,position);
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class disViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.item_chat_dis_icon)
        CircleImageView icon;
        @BindView(R.id.item_chat_dis_name)
        TextView tv_name;
        @BindView(R.id.item_chat_dis_text)
        TextView tv_text;

        public disViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        public void bind(disViewHolder vh,int position){
//            ImageLoader.getInstance().displayImage(data.get(position).getIcon(),vh.icon);
            vh.icon.setImageResource(R.drawable.default_icon);
            vh.tv_name.setText(data.get(position).getName());
            vh.tv_text.setText(data.get(position).getText());
        }
    }
    class docViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.item_chat_doc_icon)
        CircleImageView icon;
        @BindView(R.id.item_chat_doc_name)
        TextView tv_name;
        @BindView(R.id.item_chat_doc_text)
        TextView tv_text;

        public docViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
        public void bind(docViewHolder vh,int position){
//            ImageLoader.getInstance().displayImage(data.get(position).getIcon(),vh.icon);
            vh.icon.setImageResource(R.drawable.doctor_head);
            vh.tv_name.setText(data.get(position).getName());
            vh.tv_text.setText(data.get(position).getText());
        }
    }
}
