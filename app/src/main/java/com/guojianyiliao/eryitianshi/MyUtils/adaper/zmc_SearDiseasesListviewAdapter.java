package com.guojianyiliao.eryitianshi.MyUtils.adaper;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.guojianyiliao.eryitianshi.MyUtils.bean.SearchDetailsBean;
import com.guojianyiliao.eryitianshi.R;

import java.util.List;

/**
 * Created by zmc on 2017/5/16.
 */

public class zmc_SearDiseasesListviewAdapter extends BaseAdapter{

    private List<SearchDetailsBean.Diseases> data;
    private LayoutInflater inflater;

    public zmc_SearDiseasesListviewAdapter(List<SearchDetailsBean.Diseases> data, Context context) {
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
        ViewHolder vh;
        if(convertView == null){
            convertView = inflater.inflate(R.layout.zmc_item_sear_diseases,null);
            vh = new ViewHolder();
            vh.name = (TextView) convertView.findViewById(R.id.item_name_tv);
            convertView.setTag(vh);
        }else{
            vh = (ViewHolder) convertView.getTag();
        }
        vh.name.setText(data.get(position).getName()+"");
        return convertView;
    }

    private class ViewHolder{
        TextView name;
    }

    public void Update(List<SearchDetailsBean.Diseases> data){
        this.data = data;
        this.notifyDataSetChanged();
    }
}
