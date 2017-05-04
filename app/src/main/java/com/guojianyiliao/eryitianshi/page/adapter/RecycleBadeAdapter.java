package com.guojianyiliao.eryitianshi.page.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.guojianyiliao.eryitianshi.Data.entity.CouponsBean;
import com.guojianyiliao.eryitianshi.R;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.MyLogcat;

import java.util.List;


public class RecycleBadeAdapter extends RecyclerView.Adapter<RecycleBadeAdapter.Myholder>{

    private List<CouponsBean> data;
    private Context mcontext;
    private LayoutInflater inflater;

    public RecycleBadeAdapter(List<CouponsBean> data, Context context) {
        this.data = data;
        this.mcontext = context;
        inflater = LayoutInflater.from(mcontext);
    }

    private Myholder myholder;

    @Override
    public Myholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.mycashcoupons_listview_item, parent, false);
        return new Myholder(view);

    }

    @Override
    public void onBindViewHolder(Myholder holder, int position) {
        holder.money.setText("￥" + (int) data.get(position).getMoney());
        String s = data.get(position).getEndTime().substring(0, 10);
        String substring = data.get(position).getEndTime().substring(8, 10);
        holder.time.setText("请在" + s + "前使用");
        holder.itemView.setTag(data.get(position));
        MyLogcat.jLog().e("Recycle");
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class Myholder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView money;
        TextView time;
        RelativeLayout rootView;
        public Myholder(View itemView) {
            super(itemView);
            money = (TextView) itemView.findViewById(R.id.tv_money);
            time = (TextView) itemView.findViewById(R.id.tv_valid_time);
            rootView = (RelativeLayout) itemView.findViewById(R.id.rl_mycash_use_background);
            rootView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (clickListener != null) {
                clickListener.onClick(itemView, getAdapterPosition());
            }
        }
    }


    private OnItemClickListener clickListener;

    public void setClickListener(OnItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public static interface OnItemClickListener {
        void onClick(View view, int position);
    }
}