package com.guojianyiliao.eryitianshi.page.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.guojianyiliao.eryitianshi.Data.entity.DiseaseDepartment;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.UIUtils;
import com.guojianyiliao.eryitianshi.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by Administrator on 2016/8/25 0025.
 */
public class DiseaseliblistvAdapter extends BaseAdapter {
    private List<DiseaseDepartment> list;
    Context context;


    public DiseaseliblistvAdapter(Context context, List<DiseaseDepartment> list) {
        this.context = context;
        this.list = list;
    }

    int mSelect = 0;

    public void changeSelected(int positon) {
        if (positon != mSelect) {
            mSelect = positon;
            notifyDataSetChanged();
        }

    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public DiseaseDepartment getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = UIUtils.getinflate(R.layout.disease_listv_item);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tv_dislistv.setText(list.get(position).getName());
        ImageLoader.getInstance().displayImage(list.get(position).getIcon1(), viewHolder.iv_dislistv);
        ImageLoader.getInstance().displayImage(list.get(position).getIcon2(), viewHolder.iv_dislistv1);

        if (mSelect == position) {
            viewHolder.iv_dislistv1.setVisibility(View.VISIBLE);
            viewHolder.iv_dislistv.setVisibility(View.GONE);
            viewHolder.dise_ll.setBackgroundColor(android.graphics.Color.parseColor("#6fc9e6"));
            viewHolder.tv_dislistv.setTextColor(0xffffffff);
        } else {
            viewHolder.iv_dislistv1.setVisibility(View.GONE);
            viewHolder.iv_dislistv.setVisibility(View.VISIBLE);
            viewHolder.dise_ll.setBackgroundColor(android.graphics.Color.parseColor("#f4f4f4"));
            viewHolder.tv_dislistv.setTextColor(android.graphics.Color.parseColor("#666666"));
        }
        return convertView;
    }

    static class ViewHolder {
        public TextView tv_dislistv;
        public ImageView iv_dislistv, iv_dislistv1;
        public LinearLayout dise_ll;

        ViewHolder(View v) {
            tv_dislistv = (TextView) v.findViewById(R.id.tv_dislistv);
            iv_dislistv = (ImageView) v.findViewById(R.id.iv_dislistv);
            dise_ll = (LinearLayout) v.findViewById(R.id.dise_ll);
            iv_dislistv1 = (ImageView) v.findViewById(R.id.iv_dislistv1);
        }
    }


}
