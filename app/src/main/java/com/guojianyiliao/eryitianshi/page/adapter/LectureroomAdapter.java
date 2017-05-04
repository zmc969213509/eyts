package com.guojianyiliao.eryitianshi.page.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.guojianyiliao.eryitianshi.MyUtils.bean.BaikeVideoData;
import com.guojianyiliao.eryitianshi.MyUtils.bean.MyFocus;
import com.guojianyiliao.eryitianshi.R;
import com.guojianyiliao.eryitianshi.MyUtils.view.activity.view.RoundCornerImageView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * 視屏播放的適配器
 */
public class LectureroomAdapter extends RecyclerView.Adapter {
    List<BaikeVideoData> list;
    Context context;
    LayoutInflater inflater;
    private RecylerViewListener listener;

    public List<BaikeVideoData> getList() {
        return list;
    }

    public void setList(List<BaikeVideoData> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public LectureroomAdapter(Context context, List<BaikeVideoData> list) {
        this.context = context;
        this.list = list;
        this.inflater = LayoutInflater.from(context);
    }

    class Viewholder extends RecyclerView.ViewHolder {
        private TextView tv_lectr;
        private TextView tv_time;
        private TextView tv_browse;
        private ImageView img_like;
        private RoundCornerImageView rcImageView;
        private LinearLayout linearLayout;

        public Viewholder(View itemView) {
            super(itemView);
            tv_lectr = (TextView) itemView.findViewById(R.id.tv_lectr);
            rcImageView = (RoundCornerImageView) itemView.findViewById(R.id.rcImageView);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.linearLayout);
            tv_time = (TextView) itemView.findViewById(R.id.tv_time);
            tv_browse = (TextView) itemView.findViewById(R.id.tv_browse);
            img_like = (ImageView) itemView.findViewById(R.id.img_like);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lectureroom_item, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        Viewholder viewholder = (Viewholder) holder;
        viewholder.tv_lectr.setText(list.get(position).title);
        String uri = list.get(position).cover;
        ImageLoader.getInstance().displayImage(uri, viewholder.rcImageView);
        viewholder.tv_time.setText("时长:" + list.get(position).lduration);
        viewholder.tv_browse.setText(list.get(position).agrees+"");

        /** 点赞*/
        viewholder.img_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        viewholder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.parse(list.get(position).video), "video/mp4");
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public interface RecylerViewListener {
        void onClick(View v, int position);
    }

    public void setRecylerViewListener(RecylerViewListener listener) {
        this.listener = listener;
    }


}
