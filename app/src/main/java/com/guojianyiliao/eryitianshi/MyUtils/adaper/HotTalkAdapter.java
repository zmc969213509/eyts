package com.guojianyiliao.eryitianshi.MyUtils.adaper;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.guojianyiliao.eryitianshi.MyUtils.bean.BaikeHotTalkBean;
import com.guojianyiliao.eryitianshi.MyUtils.bean.HotTalkBean;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.MyLogcat;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.UIUtils;
import com.guojianyiliao.eryitianshi.MyUtils.view.activity.view.RoundCornerImageView;
import com.guojianyiliao.eryitianshi.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;


public class HotTalkAdapter extends BaseAdapter {

    private List<HotTalkBean> hotData;
    private List<BaikeHotTalkBean> baikehotData;
    /** 0：加载首页热门话题   1：加载百科文章**/
    private int flag = -1;

    public HotTalkAdapter(List<HotTalkBean> hotData) {
        this.hotData = hotData;
        flag = 0;
    }

    public HotTalkAdapter(List<BaikeHotTalkBean> baikehotData,int flag) {
        this.baikehotData = baikehotData;
        this.flag = flag;
    }

    @Override
    public int getCount() {
        if(flag == 0){
            return hotData.size();
        }else if(flag == 1){
            return baikehotData.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if(flag == 0){
            return hotData.get(position);
        }else if(flag == 1){
            return baikehotData.get(position);
        }
        return null;

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
            if(flag == 0){
                ImageLoader.getInstance().displayImage(hotData.get(position).getIcon(), Myholder.icon);
                Myholder.title.setText(hotData.get(position).getTitle());
                Myholder.content.setText(hotData.get(position).getContent());
                Myholder.time.setText(hotData.get(position).getCtime());
                Myholder.collectnumber.setText(hotData.get(position).getCollectcount() + "");
            }else if(flag == 1){
                ImageLoader.getInstance().displayImage(baikehotData.get(position).getIcon(), Myholder.icon);
                Myholder.title.setText(baikehotData.get(position).getTitle());
                Myholder.content.setText(baikehotData.get(position).getContent());
                Myholder.time.setText(baikehotData.get(position).getCtime());
                Myholder.collectnumber.setText(baikehotData.get(position).getCollectcount() + "");
            }
        } catch (Exception e) {
            MyLogcat.jLog().e("Exception:" + e.getMessage());
        }
        return convertView;
    }

    public void setData(List<HotTalkBean> hotData) {
        this.hotData = hotData;
        this.notifyDataSetChanged();
    }

    public void setData(List<BaikeHotTalkBean> baikehotData,int flag) {
        this.baikehotData = baikehotData;
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
