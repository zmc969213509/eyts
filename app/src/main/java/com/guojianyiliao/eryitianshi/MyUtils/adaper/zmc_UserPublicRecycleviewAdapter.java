package com.guojianyiliao.eryitianshi.MyUtils.adaper;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.guojianyiliao.eryitianshi.MyUtils.bean.UserEssaies;
import com.guojianyiliao.eryitianshi.MyUtils.customView.FourGridImageView;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.MyLogcat;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.TimeUtil;
import com.guojianyiliao.eryitianshi.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zmc on 2017/5/11.
 */

public class zmc_UserPublicRecycleviewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    /**查看自己说说时 显示添加说说item**/
    private static final int TYPE_SHOW_ME = 0;
    /**不显示添加说说item**/
    private static final int TYPE_NOT_SHOW_ME = 1;

    private List<UserEssaies> data;
    private Context context;
    private LayoutInflater inflater;
    onItemClickListener listener;
    /**显示说说类型  0为查看自己说说  1为查看他人说说**/
    private int flag;

    public void setListener(onItemClickListener listener) {
        this.listener = listener;
    }

    public zmc_UserPublicRecycleviewAdapter(List<UserEssaies> data, Context context,int flag) {
        this.data = data;
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.flag = flag;
    }

    @Override
    public int getItemViewType(int position) {
        if(flag == 0 && position == 0){
            return TYPE_SHOW_ME;
        }
        return TYPE_NOT_SHOW_ME;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == TYPE_SHOW_ME){
            View view = inflater.inflate(R.layout.zmc_item_user_publish_my,null);
            return new headViewHolder(view);
        }else{
            View view = inflater.inflate(R.layout.zmc_item_user_publish,null);
            view.setOnClickListener(this);
            return new ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ViewHolder){
            ((ViewHolder)holder).bind((ViewHolder)holder,data.get(position));
            holder.itemView.setTag(position);
        }else if(holder instanceof  headViewHolder){
            ((headViewHolder)holder).bind((headViewHolder)holder);
//            holder.itemView.setTag(position);
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public void onClick(View v) {
        if(listener != null){
            listener.onItemClick(v,(int)v.getTag());
        }
    }

    public interface onItemClickListener{
        void onItemClick(View v,int position);
        void addImgClick();
    }

    public class headViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.zmc_item_publish_add)
        ImageView img_add;
        @BindView(R.id.zmc_item_publish_day_tv)
        TextView tv;

        public headViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        public void bind(headViewHolder holder) {
            holder.tv.getPaint().setFakeBoldText(true);//设置字体加粗
            holder.img_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        listener.addImgClick();
                    }
                }
            });
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.zmc_item_publish_day_tv)
        TextView tv_day;
        @BindView(R.id.zmc_item_publish_mouth_tv)
        TextView tv_mouth;
        @BindView(R.id.zmc_item_publish_fourview)
        FourGridImageView fourView;
        @BindView(R.id.zmc_item_publish_title_tv)
        TextView tv_title;
        @BindView(R.id.zmc_item_publish_number_tv)
        TextView tv_num;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            this.fourView.setmListener(new FourGridImageView.mListener() {
                @Override
                public void onDisplayImage(Context context, ImageView imageView, String url, String tag, FourGridImageView view) {
//                            if(tag.equals(view.getTag())){
                    Picasso.with(context).load(url).config(Bitmap.Config.RGB_565).fit().placeholder(R.drawable.ic_default_image).into(imageView);
//                            }else{
//                                view.setVisibility(View.GONE);
//                            }
                }
            });
        }

        /**
         * 数据与视图控件进行绑定  相当于getView
         * @param holder
         * @param userEssaies
         */
        public void bind(ViewHolder holder, UserEssaies userEssaies) {

            holder.tv_day.getPaint().setFakeBoldText(true);//设置字体加粗
            long epubtime = userEssaies.getEpubtime();

            try {
                String datas = TimeUtil.SJC(epubtime + "");
                String mouth = datas.substring(5,7);
                String day = datas.substring(8,10);
                holder.tv_day.setText(day+"");
                holder.tv_mouth.setText(mouth+"月");
                holder.tv_title.setText(userEssaies.getEcontent()+"");
                String eimages = userEssaies.getEimages();
                String[] split = eimages.split(";");
                if(split == null || split.length == 0){
                    holder.tv_num.setVisibility(View.GONE);
                    holder.fourView.setVisibility(View.GONE);
                }else{
                    holder.tv_num.setVisibility(View.VISIBLE);
                    holder.tv_num.setText("共"+split.length+"张");
                    List<String> list = new ArrayList<>();
                    for (int i = 0; i <split.length ; i++) {
                        list.add(split[i]);
                    }
                    holder.fourView.setVisibility(View.VISIBLE);
                    holder.fourView.setImagesData(list,epubtime+"");
                }
            } catch (Exception e) {
                MyLogcat.jLog().e("：" + e.getMessage());
            }
        }
    }

    public void Update(List<UserEssaies> data){
        this.data = data;
        this.notifyDataSetChanged();;
    }

}
