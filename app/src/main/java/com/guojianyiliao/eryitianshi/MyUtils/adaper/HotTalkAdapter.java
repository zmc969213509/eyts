package com.guojianyiliao.eryitianshi.MyUtils.adaper;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.guojianyiliao.eryitianshi.MyUtils.bean.HotTalkBean;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.MyLogcat;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.UIUtils;
import com.guojianyiliao.eryitianshi.MyUtils.view.activity.view.RoundCornerImageView;
import com.guojianyiliao.eryitianshi.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;


public class HotTalkAdapter extends BaseAdapter {

    private List<HotTalkBean> hotData;

    public HotTalkAdapter(List<HotTalkBean> hotData) {
        this.hotData = hotData;
    }

    @Override
    public int getCount() {
        return hotData.size();
    }

    @Override
    public Object getItem(int position) {
        return hotData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Myholder Myholder;
        if (convertView == null) {
            convertView = UIUtils.getinflate(R.layout.a_fragment_item_hot);
            Myholder = new Myholder(convertView);
            convertView.setTag(Myholder);
        } else {
            Myholder = (HotTalkAdapter.Myholder) convertView.getTag();
        }
        try {
            ImageLoader.getInstance().displayImage(hotData.get(position).getIcon(), Myholder.icon);
            Myholder.title.setText(hotData.get(position).getTitle());
            Myholder.content.setText(hotData.get(position).getContent());
            Myholder.time.setText(hotData.get(position).getCtime());
            Myholder.collectnumber.setText(hotData.get(position).getCollectcount() + "");
        } catch (Exception e) {
            MyLogcat.jLog().e("Exception:" + e.getMessage());
        }

        return convertView;
    }

    public void setData(List<HotTalkBean> hotData) {
        this.hotData = hotData;
        this.notifyDataSetChanged();
    }

    class Myholder {

        private RoundCornerImageView icon;
        private TextView title;
        private TextView content;
        private TextView time;
        private TextView collectnumber;

        public Myholder(View itemView) {
            icon = (RoundCornerImageView) itemView.findViewById(R.id.iv_hot_icon);
            title = (TextView) itemView.findViewById(R.id.tv_hot_title);
            content = (TextView) itemView.findViewById(R.id.tv_hot_content);
            time = (TextView) itemView.findViewById(R.id.tv_hot_time);
            collectnumber = (TextView) itemView.findViewById(R.id.tv_hot_collectnumber);
        }
    }

}
