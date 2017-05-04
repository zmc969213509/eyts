package com.guojianyiliao.eryitianshi.page.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.guojianyiliao.eryitianshi.R;

import java.util.List;

/**
 * Created by Administrator on 2016/10/28 0028.
 */
public class InquirySecletAdapter extends BaseAdapter {
    private Context context;
    private List<String> list;

    public InquirySecletAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public String getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            convertView = inflater.inflate(R.layout.inquiry_seclect_item, parent, false);
            convertView.setTag(new ViewHolder(convertView));
        }
        ViewHolder viewHolder = (ViewHolder) convertView.getTag();
        viewHolder.textView.setText(list.get(position));
        /*if(position==0||position==2||position==4||position==6||position==8||position==10||position==12){
            viewHolder.viewline.setVisibility(View.VISIBLE);
        }*/
        return convertView;
    }

    static class ViewHolder {
        private TextView textView;

        ViewHolder(View v) {
            textView = (TextView) v.findViewById(R.id.textView);
        }
    }
}
