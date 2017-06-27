package com.guojianyiliao.eryitianshi.MyUtils.adaper;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.guojianyiliao.eryitianshi.MyUtils.bean.DrugRemindBean;
import com.guojianyiliao.eryitianshi.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by zmc on 2017/6/12.
 * 用药提醒
 */

public class zmc_RemindRecycleviewAdapter extends RecyclerView.Adapter<zmc_RemindRecycleviewAdapter.ViewHolder> implements View.OnClickListener {

    private List<DrugRemindBean> data;
    private Context context;
    private LayoutInflater inflater;

    private ItemOnclickListener listener;

    public void setListener(ItemOnclickListener listener) {
        this.listener = listener;
    }

    public zmc_RemindRecycleviewAdapter(List<DrugRemindBean> data, Context context) {
        this.data = data;
        inflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.zmc_item_reminder_yonyao,null);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(position,holder);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public void onClick(View v) {
        if(listener != null){
            listener.onItemClick((int)v.getTag());
        }
    }

    public interface ItemOnclickListener{
        void onItemClick(int position);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView day;

        TextView content1;
        TextView content2;
        TextView content3;

        private RelativeLayout layout1;
        private RelativeLayout layout2;
        private RelativeLayout layout3;

        public ViewHolder(View itemView) {
            super(itemView);
            day = (TextView) itemView.findViewById(R.id.zmc_item_remind_day);
            content1 = (TextView) itemView.findViewById(R.id.zmc_item_remind_tv1);
            content2 = (TextView) itemView.findViewById(R.id.zmc_item_remind_tv2);
            content3 = (TextView) itemView.findViewById(R.id.zmc_item_remind_tv3);

            layout1 = (RelativeLayout) itemView.findViewById(R.id.zmc_item_remind_layout1);
            layout2 = (RelativeLayout) itemView.findViewById(R.id.zmc_item_remind_layout2);
            layout3 = (RelativeLayout) itemView.findViewById(R.id.zmc_item_remind_layout3);

//            day.setOnClickListener(zmc_RemindRecycleviewAdapter.this);


        }

        public void bind(int position, ViewHolder holder) {


            if(TextUtils.isEmpty(data.get(position).getContent1())){
                holder.layout1.setVisibility(View.GONE);
            }else{
                holder.layout1.setVisibility(View.VISIBLE);
            }
            if(TextUtils.isEmpty(data.get(position).getContent2())){
                holder.layout2.setVisibility(View.GONE);
            }else{
                holder.layout2.setVisibility(View.VISIBLE);
            }
            if(TextUtils.isEmpty(data.get(position).getContent3())){
                holder.layout3.setVisibility(View.GONE);
            }else{
                holder.layout3.setVisibility(View.VISIBLE);
            }

            long reminddate = data.get(position).getReminddate();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date(reminddate);
            String res = simpleDateFormat.format(date);
            holder.day.setText(res.substring(0, 10));
            holder.content1.setText("用药时间：" + data.get(position).getTime1()+"\n内容：" + data.get(position).getContent1());
            holder.content2.setText("用药时间：" + data.get(position).getTime2()+"\n内容：" + data.get(position).getContent2());
            holder.content3.setText("用药时间：" + data.get(position).getTime3()+"\n内容：" + data.get(position).getContent3());
        }
    }
}
