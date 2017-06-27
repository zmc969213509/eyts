package com.guojianyiliao.eryitianshi.MyUtils.adaper;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.guojianyiliao.eryitianshi.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/5/18.
 */

public class zmc_DownMenuRecycleviewAdapter extends RecyclerView.Adapter<zmc_DownMenuRecycleviewAdapter.ViewHolder> implements View.OnClickListener {

    List<String> data;
    Context context;
    LayoutInflater inflater;
    /**当前选中的项**/
    int position_check;
    onItemClickListener listener;

    public void setListener(onItemClickListener listener) {
        Log.e("AllDoctorActivity","setListener");
        this.listener = listener;
    }

    public zmc_DownMenuRecycleviewAdapter(List<String> data, Context context, int position_check) {
        this.data = data;
        this.context = context;
        this.position_check = position_check;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.zmc_item_dowm_menu,null);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemView.setTag(position);
        holder.bind(holder,position);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public void onClick(View v) {
        Log.e("AllDoctorActivity","onClick    listener是否为空："+(listener == null));
        if(listener != null){
            listener.onItemClick((int)v.getTag());
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.item_menu_vertical_view)
        View vertical_view;
        @BindView(R.id.item_menu_name)
        TextView tv_name;
        @BindView(R.id.item_menu_img)
        ImageView img;
        @BindView(R.id.item_menu_button_view)
        View button_view;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        public void bind(ViewHolder vh,int position){
            vh.tv_name.setText(data.get(position));
            if(position % 2 == 0){
                vh.vertical_view.setVisibility(View.VISIBLE);
            }else if(position % 2 == 1){
                vh.vertical_view.setVisibility(View.GONE);
            }
            if(data.size() - position <= 2){
                button_view.setVisibility(View.VISIBLE);
            }
            if(position == position_check){//当前项是选中项
                vh.img.setVisibility(View.VISIBLE);
                int color = context.getResources().getColor(R.color.view);
                vh.tv_name.setTextColor(color);
            }
        }
    }

    public interface onItemClickListener{
        void onItemClick(int position);
    }
}
