package com.guojianyiliao.eryitianshi.MyUtils.adaper;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.guojianyiliao.eryitianshi.MyUtils.bean.MyFocus;
import com.guojianyiliao.eryitianshi.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by zmc on 2017/5/11.
 */

public class zmc_MyFollowUsersRecycleviewAdapter extends RecyclerView.Adapter<zmc_MyFollowUsersRecycleviewAdapter.ViewHolder> implements View.OnClickListener {

    private List<MyFocus> data;
    private Context context;
    private LayoutInflater inflater;
    private onItemClickListener listener;

    public interface onItemClickListener{
        void onItemClick(View view,int position);
    }

    public void setListener(onItemClickListener listener) {
        this.listener = listener;
    }

    public zmc_MyFollowUsersRecycleviewAdapter(List<MyFocus> data, Context context) {
        this.data = data;
        inflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_rcy_mywatchlist,null);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(holder,position);
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public void onClick(View v) {
        if(listener != null){
            listener.onItemClick(v,(int)v.getTag());
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_watch_icon)
        CircleImageView ivWatchIcon;
        @BindView(R.id.tv_watch_name)
        TextView tvWatchName;
        @BindView(R.id.img_watch_sex)
        ImageView sexImg;
        @BindView(R.id.tv_watch_gz)
        TextView tvWatchGz;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(ViewHolder holder, int position) {
            MyFocus myFocus = data.get(position);
            String icon_url = myFocus.getIcon();
            Picasso.with(context).load(icon_url).config(Bitmap.Config.RGB_565).fit().into(holder.ivWatchIcon);

            holder.tvWatchName.setText(myFocus.getName()+"");

            String gender = myFocus.getGender();
            if(gender.equals("男")){
                holder.sexImg.setImageResource(R.drawable.sex_boy_icon);
            }else if(gender.equals("女")){
                holder.sexImg.setImageResource(R.drawable.sex_girl_icon);
            }
        }
    }

    public void Updata(List<MyFocus> list){
        this.data = list;
        this.notifyDataSetChanged();
    }
}
