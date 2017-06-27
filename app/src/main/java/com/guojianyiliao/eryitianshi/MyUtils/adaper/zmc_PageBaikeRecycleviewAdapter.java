package com.guojianyiliao.eryitianshi.MyUtils.adaper;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.guojianyiliao.eryitianshi.MyUtils.bean.TypeDis;
import com.guojianyiliao.eryitianshi.MyUtils.bean.SearchDetailsBean;
import com.guojianyiliao.eryitianshi.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/5/22.
 */

public class zmc_PageBaikeRecycleviewAdapter extends RecyclerView.Adapter<zmc_PageBaikeRecycleviewAdapter.ViewHolder>{

    List<TypeDis> data;
    LayoutInflater inflater;
    Context context;

    onItemClickLinstener linstener;

    public void setLinstener(onItemClickLinstener linstener) {
        this.linstener = linstener;
    }

    public zmc_PageBaikeRecycleviewAdapter(List<TypeDis> data, Context context) {
        this.data = data;
        inflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.zmc_item_baike_dis,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(holder,position);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.zmc_item_baike_name)
        TextView tv_name;
        @BindView(R.id.zmc_item_baike_recycleview)
        RecyclerView recyclerView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        public void bind(ViewHolder holder,int position){
            Log.e("zmc_PageBaikeAdapter","name = "+data.get(position).getName());
            holder.tv_name.setText(data.get(position).getName());
            List<SearchDetailsBean.Diseases> disList = data.get(position).getDisList();
            itemRecycleviewAdapter adapter = new itemRecycleviewAdapter(disList);
            GridLayoutManager manager = new GridLayoutManager(context,2,GridLayoutManager.VERTICAL,false);
            holder.recyclerView.setLayoutManager(manager);
            holder.recyclerView.setAdapter(adapter);
        }
    }

    /**
     * 百科疾病类里单独的recycleview适配器
     */
    public class itemRecycleviewAdapter extends RecyclerView.Adapter<itemRecycleviewAdapter.ViewHolder> implements View.OnClickListener {

        List<SearchDetailsBean.Diseases> disList;

        public itemRecycleviewAdapter(List<SearchDetailsBean.Diseases> disList) {
            this.disList = disList;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = inflater.inflate(R.layout.zmc_item_dowm_menu,null);
            view.setOnClickListener(this);
            return new itemRecycleviewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.itemView.setTag(position);
            holder.bind(holder,position);
        }

        @Override
        public int getItemCount() {
            return disList.size();
        }

        @Override
        public void onClick(View v) {
            if(linstener != null){
                int position = (int)v.getTag();
                linstener.onItemClick(disList.get(position).getDisid());
            }
        }

        public class ViewHolder extends RecyclerView.ViewHolder{

            @BindView(R.id.item_menu_vertical_view)
            View vertical_view;
            @BindView(R.id.item_menu_name)
            TextView tv_name;
            @BindView(R.id.item_menu_button_view)
            View button_view;

            public ViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this,itemView);
            }

            public void bind(itemRecycleviewAdapter.ViewHolder vh, int position){
                vh.tv_name.setText(disList.get(position).getName());
                if(position % 2 == 0){
                    vh.vertical_view.setVisibility(View.VISIBLE);
                }else if(position % 2 == 1){
                    vh.vertical_view.setVisibility(View.GONE);
                }
                if(disList.size() - position <= 2){
                    button_view.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    public interface onItemClickLinstener{
        void onItemClick(String disId);
    }
}
