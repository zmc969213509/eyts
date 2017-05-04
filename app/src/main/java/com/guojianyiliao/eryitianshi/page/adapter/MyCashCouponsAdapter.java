package com.guojianyiliao.eryitianshi.page.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.guojianyiliao.eryitianshi.MyUtils.bean.myCashCouponsBean;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.MyLogcat;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.UIUtils;
import com.guojianyiliao.eryitianshi.R;

import java.util.List;

/**
 * 代金券 item 页面
 */
public class MyCashCouponsAdapter extends BaseAdapter {

    public boolean COUPONS = true;

    private Context context;
    private List<myCashCouponsBean> list;

    public static int TYPE = 1;
    public static int TYPEGONE = 2;

    public MyCashCouponsAdapter(Context context, List<myCashCouponsBean> list) {
        this.context = context;
        this.list = list;
    }

    public void onDateChange(List<myCashCouponsBean> list) {
        this.list = list;
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public myCashCouponsBean getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    boolean iscount = false;

    public void istrue(boolean bl) {
        iscount = bl;
    }

    @Override
    public int getItemViewType(int position) {
        /*if (iscount) {
            return TYPEGONE;
        } else {*/
        return TYPE;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    Integer typeInt;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        typeInt = position;
        ViewHolder viewHolder = null;
        ViewHolderGone viewHoldergone = null;
        int itemViewType = getItemViewType(position);
        if (convertView == null) {
            switch (itemViewType) {
                case 1:
                    convertView = UIUtils.getinflate(R.layout.a_item_coupons_recy);
                    // convertView = inflater.inflate(R.layout.mycashcoupons_listview_item, parent, false);//1.0版本的代金券样式
                    viewHolder = new ViewHolder(convertView);
                    convertView.setTag(viewHolder);
                    break;
                case 2:
                    convertView = UIUtils.getinflate(R.layout.a_item_coupons_recy_gone);
                    viewHoldergone = new ViewHolderGone(convertView);
                    convertView.setTag(viewHoldergone);
                    break;
            }
        } else {
            switch (itemViewType) {
                case 1:
                    viewHolder = (ViewHolder) convertView.getTag();
                    break;
                case 2:
                    viewHoldergone = (ViewHolderGone) convertView.getTag();
                    break;
            }
        }

        switch (itemViewType) {
            case 3:
                if (list.get(position).voutype == 1) {
                    viewHolder.backsrc.setBackgroundResource(R.drawable.mine_djq02);
                    viewHolder.money.setText("10");
                    viewHolder.data.setText("请在" + list.get(position).endtime + "前使用");
                    viewHolder.use.setText("未使用");
                }
                if (list.get(position).voutype == 2) {
                    viewHolder.backsrc.setBackgroundResource(R.drawable.mine_djq02);
                    viewHolder.money.setText("15");
                    viewHolder.data.setText("请在" + list.get(position).endtime + "前使用");
                    viewHolder.use.setText("未使用");
                }
                if (list.get(position).voutype == 3) {
                    viewHolder.backsrc.setBackgroundResource(R.drawable.mine_djq01);
                    viewHolder.money.setText("25");
                    viewHolder.data.setText("请在" + list.get(position).endtime + "前使用");
                    viewHolder.use.setText("未使用");

                }
                break;
            //case 2:
                //viewHoldergone.tv_gong.setText("");
               // break;
        }

        // String s = list.get(position).getEndTime().substring(0, 10);
        // String substring = list.get(position).getEndTime().substring(8, 10);
        // viewHolder.time.setText("请在" + s + "前使用");
        return convertView;
    }

    static class ViewHolder {
        TextView money;
        TextView data;
        TextView use;
        RelativeLayout backsrc;
        TextView tv_gong;

        ViewHolder(View v) {
            money = (TextView) v.findViewById(R.id.tv_money_coupons);
            data = (TextView) v.findViewById(R.id.tv_date_coupons);
            use = (TextView) v.findViewById(R.id.tv_use_coupons);
            backsrc = (RelativeLayout) v.findViewById(R.id.rl_monry_backsrc);
            tv_gong = (TextView) v.findViewById(R.id.tv_gong);

        }
    }

    static class ViewHolderGone {
        TextView tv_gong;

        ViewHolderGone(View v) {
            tv_gong = (TextView) v.findViewById(R.id.tv_gong);

        }
    }

}