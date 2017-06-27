package com.guojianyiliao.eryitianshi.MyUtils.adaper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.guojianyiliao.eryitianshi.MyUtils.bean.SearchDetailsBean;
import com.guojianyiliao.eryitianshi.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by zmc on 2017/5/16.
 */

public class zmc_SearDoctorListviewAdapter extends BaseAdapter{

    private List<SearchDetailsBean.Doctors> data;
    private LayoutInflater inflater;

    public zmc_SearDoctorListviewAdapter(List<SearchDetailsBean.Doctors> data, Context context) {
        this.data = data;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.inquiry_list_item,null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tvTitle.setText(data.get(position).getTitle());
        viewHolder.tvName.setText(data.get(position).getName());
        viewHolder.tvIntro.setText("擅长：" + data.get(position).getAdept());
        viewHolder.tvSection.setText(data.get(position).getSection());
        ImageLoader.getInstance().displayImage(data.get(position).getIcon(), viewHolder.ivIcon);
        if("主治医师".equals(data.get(position).getTitle())){
            viewHolder.chat_img.setVisibility(View.VISIBLE);
        }else{
            viewHolder.chat_img.setVisibility(View.GONE);
        }

        return convertView;
    }

    class ViewHolder {
        @BindView(R.id.ll_fl_chat)
        LinearLayout llFlChat;

        @BindView(R.id.iv_icon)
        CircleImageView ivIcon;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_intro)
        TextView tvIntro;
        @BindView(R.id.tv_section)
        TextView tvSection;

        @BindView(R.id.chat_img)
        ImageView chat_img;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public void Update(List<SearchDetailsBean.Doctors> data){
        this.data = data;
        this.notifyDataSetChanged();
    }
}
