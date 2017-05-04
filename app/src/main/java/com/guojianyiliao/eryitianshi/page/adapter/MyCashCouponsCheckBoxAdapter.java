package com.guojianyiliao.eryitianshi.page.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.guojianyiliao.eryitianshi.Data.entity.CouponsBean;
import com.guojianyiliao.eryitianshi.R;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.MyLogcat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2016/11/2 0002.
 */
public class MyCashCouponsCheckBoxAdapter extends BaseAdapter {
    public boolean COUPONS = true;
    private Context context;
    private List<CouponsBean> list;
    public static int TYPECOUPONS = 3;
    public static int TYPECOUPTWO = 4;
    List<Integer> myid = new ArrayList<>();
    List<Integer> mymoney = new ArrayList<>();
    // 用来控制CheckBox的选中状况
    HashMap<String, Object> checkBoxIDList;           //存储checkBox的值

    //get set
    public HashMap<String, Object> getCheckBoxIDList() {
        return checkBoxIDList;
    }

    public void setCheckBoxIDList(HashMap<String, Object> checkBoxIDList) {
        this.checkBoxIDList = checkBoxIDList;
    }

    public MyCashCouponsCheckBoxAdapter(Context context, List<CouponsBean> list) {
        this.context = context;
        this.list = list;
        checkBoxIDList = new HashMap<>();
    }


    @Override
    public int getCount() {
        MyLogcat.jLog().e("优惠券：" + list.size());
        return list.size();
    }

    @Override
    public CouponsBean getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            convertView = inflater.inflate(R.layout.mycashcoupons_listview_checkbox_item, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    myid.add(list.get(position).getId());
                    mymoney.add((int)list.get(position).getMoney());
                    checkBoxIDList.put("id",myid);
                    checkBoxIDList.put("money",mymoney);
                    MyLogcat.jLog().e("put ID:" + list.get(position).getId());
                    MyLogcat.jLog().e("put money:" + list.get(position).getMoney());
                } else {
                    checkBoxIDList.remove("id");
                    checkBoxIDList.remove("money");
                }
            }
        });
        viewHolder.money.setText("￥" + (int) list.get(position).getMoney());
        String s = list.get(position).getEndTime().substring(0, 10);
        // String substring = list.get(position).getEndTime().substring(8, 10);
        viewHolder.time.setText("请在" + s + "前使用");
        return convertView;
    }

    static class ViewHolder {
        TextView money;
        TextView time;
        CheckBox cb;

        ViewHolder(View v) {
            money = (TextView) v.findViewById(R.id.tv_money);
            time = (TextView) v.findViewById(R.id.tv_valid_time);
            cb = (CheckBox) v.findViewById(R.id.cb_item);

        }
    }

}