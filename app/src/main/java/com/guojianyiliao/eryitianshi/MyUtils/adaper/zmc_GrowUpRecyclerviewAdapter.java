package com.guojianyiliao.eryitianshi.MyUtils.adaper;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.guojianyiliao.eryitianshi.MyUtils.bean.AllLecturesBean;
import com.guojianyiliao.eryitianshi.MyUtils.interfaceservice.GetService;
import com.guojianyiliao.eryitianshi.MyUtils.manager.RetrofitClient;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.MyLogcat;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.SharedPreferencesTools;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.StringUtils;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.TimeUtil;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.ToolUtils;
import com.guojianyiliao.eryitianshi.MyUtils.view.activity.ImagePagerActivity;
import com.guojianyiliao.eryitianshi.R;
import com.jaeger.ninegridimageview.ItemImageClickListener;
import com.jaeger.ninegridimageview.NineGridImageView;
import com.jaeger.ninegridimageview.NineGridImageViewAdapter;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.squareup.picasso.Picasso;
import com.wx.goodview.GoodView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by zmc on 2017/5/8.
 */

public class zmc_GrowUpRecyclerviewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    /**显示脚部布局**/
    private static final int TYPE_FOOTER = 1;
    /**显示数据布局**/
    private static final int TYPE_ITEM = 0;

    private Context context;
    private List<AllLecturesBean.ListBean> list;
    private LayoutInflater inflater;
    String userid;
    private onItemClickListener onItemClickListener;

    private FootViewHolder footViewHolder;

    /**没有更多数据标识**/
    private boolean DataNoMore = false;

    public void setDataNoMore(boolean dataNoMore) {
        DataNoMore = dataNoMore;
        if(footViewHolder != null){
            footViewHolder.bind();
        }
    }

    public void setOnItemClickListener(zmc_GrowUpRecyclerviewAdapter.onItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public zmc_GrowUpRecyclerviewAdapter(Context context, List<AllLecturesBean.ListBean> list, String userid) {
        this.list = list;
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.userid = userid;
    }

    @Override
    public int getItemViewType(int position) {
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }

    /**
     * 创建viewholder
     *
     * @return ViewHolder
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == TYPE_ITEM){
            View vh = inflater.inflate(R.layout.a_fragment_growup_listviewitem, null);
            vh.setOnClickListener(this);
            return new ViewHolder(vh);
        }else if(viewType == TYPE_FOOTER){
            if(footViewHolder == null){
                View view = LayoutInflater.from(context).inflate(R.layout.zmc_item_foot_view,null);
                footViewHolder = new FootViewHolder(view);
            }
            return footViewHolder;
        }
        return null;
    }

    /**
     * 将视图与数据进行绑定
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof  ViewHolder){
            ((ViewHolder)holder).bind(list.get(position),(ViewHolder)holder,position);
            holder.itemView.setTag(position);

        }else if(holder instanceof FootViewHolder){
//            ((FootViewHolder)holder).bind((FootViewHolder)holder);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position, List<Object> payloads) {
        if(holder instanceof ViewHolder){
            if(((ViewHolder)holder).isInitialization){//已经初始化了 可以对部分控件进行数据修改
                if(payloads.isEmpty()){
                    ((ViewHolder)holder).bind(list.get(position),((ViewHolder)holder),position);
                    holder.itemView.setTag(position);
                }else if("dianzan".equals((String)payloads.get(0))){//更新点赞事件
                    AllLecturesBean.ListBean bean = list.get(position);
                    ((ViewHolder)holder).ivGrowDef.setSelected(bean.isIsagree());
                    ((ViewHolder)holder).tvGrowEagrees.setText(bean.getEagrees() + "");
                }else if("guanzhu".equals((String)payloads.get(0))){//更新关注事件
                    AllLecturesBean.ListBean bean = list.get(position);
                    if (bean.isIsfocus()) {
                        ((ViewHolder)holder).tvGrowGz.setText("已关注");
                    } else {
                        ((ViewHolder)holder).tvGrowGz.setText("+ 关注");
                    }
                }
            }else{//当前viewholder没有初始化的时候 我们不能只对部分控件进行数据绑定
                ((ViewHolder)holder).bind(list.get(position),((ViewHolder)holder),position);
                holder.itemView.setTag(position);
            }
        }else if(holder instanceof FootViewHolder){
//            if(payloads.isEmpty()){//
//                ((FootViewHolder)holder).bind((FootViewHolder)holder);
//            }
        }

    }

    @Override
    public int getItemCount() {
        return list.size() == 0 ? 0 : list.size() + 1;
    }


    @Override
    public void onClick(View v) {
        if(onItemClickListener != null){
            onItemClickListener.onItemClick(v,(int)v.getTag());
        }
    }

    /**
     * 脚部视图
     */
    public class FootViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.foot_view_pb)
        ProgressBar pb;
        @BindView(R.id.foot_view_tv)
        TextView tv;

        public FootViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(){
            if(DataNoMore){
                this.pb.setVisibility(View.GONE);
                this.tv.setText("数据见底啦");
            }else{
                this.pb.setVisibility(View.VISIBLE);
                this.tv.setText("加载中...");
            }
        }
//        public void bind(FootViewHolder holder){
//
//        }
    }

    /**
     * 内容视图
     */
    public class ViewHolder extends RecyclerView.ViewHolder{

        /**当前的ViewHolder是否初始化了**/
        boolean isInitialization = false;

        @BindView(R.id.iv_icon)
        CircleImageView ivIcon;
        @BindView(R.id.tv_grow_name)
        TextView tvGrowName;
        @BindView(R.id.tv_grow_time)
        TextView tvGrowTime;
        @BindView(R.id.tv_grow_gz)
        TextView tvGrowGz;
        @BindView(R.id.tv_grow_title)
        TextView tvGrowTitle;
        @BindView(R.id.tv_grow_content)
        TextView tvGrowContent;
        @BindView(R.id.nineGrid)
        NineGridImageView nineGrid;
        @BindView(R.id.tv_grow_see)
        TextView tvGrowSee;
        @BindView(R.id.tv_grow_comment)
        TextView tvGrowComment;
        @BindView(R.id.iv_grow_evaluate)
        ImageView ivGrowEvaluate;
        @BindView(R.id.iv_grow_def)
        ImageView ivGrowDef;
        @BindView(R.id.tv_grow_eagrees)
        TextView tvGrowEagrees;
        private View rootView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            nineGrid.setAdapter(mAdapter);
        }

        private NineGridImageViewAdapter<String> mAdapter = new NineGridImageViewAdapter<String>() {

            @Override
            protected void onHideView(NineGridImageView<String> view) {
                Log.e("myBind","onHideView ");
                view.setVisibility(View.GONE);
            }

            @Override
            protected void onDisplayImage(Context context, ImageView imageView, String s, String tag, NineGridImageView<String> view) {
                //根据tag去防止错位以及NineGridImageView的显示错误
                Log.e("myBind","onDisplayImage ");
                if(view.getTag().equals(tag)){
                    Log.e("onDisplayImage","url:"+s);
                    view.setVisibility(View.VISIBLE);
                    Picasso.with(context).load(s).config(Bitmap.Config.RGB_565).fit().placeholder(R.drawable.default_icon).into(imageView);
                }else{
                    view.setVisibility(View.GONE);
                }
            }

            @Override
            protected ImageView generateImageView(Context context) {
                return super.generateImageView(context);
            }

            @Override
            protected void onItemImageClick(Context context, ImageView imageView, int index, List<String> list) {
                //TODO 图片点击查看处理
                imageBrower(index, (ArrayList<String>) list);
            }
        };

        public void bind(final AllLecturesBean.ListBean data,final ViewHolder holder,final int position){
            isInitialization = true;
            Log.e("myBind","position = "+position);
            ImageLoader.getInstance().displayImage(data.getUser().getIcon(), this.ivIcon);
            this.tvGrowName.setText(data.getUser().getName());
            long epubtime = 0;
            try {
                epubtime = data.getEpubtime();
                MyLogcat.jLog().e(" q：" + epubtime);
                String datas = TimeUtil.SJC(epubtime + "");
                this.tvGrowTime.setText(datas);
                MyLogcat.jLog().e("data：" + datas);
            } catch (Exception e) {
                MyLogcat.jLog().e("：" + e.getMessage());
            }
            if (!StringUtils.isEmpty(data.getEmemo())) {
                this.tvGrowTitle.setVisibility(View.VISIBLE);
                this.tvGrowTitle.setText(data.getEmemo());//标题
            } else {
                this.tvGrowTitle.setVisibility(View.GONE);
            }
            if (!StringUtils.isEmpty(data.getEcontent())) {
                this.tvGrowContent.setVisibility(View.VISIBLE);
                this.tvGrowContent.setText(data.getEcontent());//内容
            } else {
                this.tvGrowContent.setVisibility(View.GONE);
            }
            this.tvGrowSee.setText("浏览" + data.getEtimes() + "次");//浏览
            if(data.getUserid().equals(SharedPreferencesTools.GetUsearId(context,"userSave","userId"))){//自己
                this.tvGrowGz.setVisibility(View.GONE);
            }else{
                //是否关注
                if (data.isIsfocus()) {
                    this.tvGrowGz.setText("已关注");
                } else {
                    this.tvGrowGz.setText("+ 关注");
                }
            }

            this.tvGrowGz.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!ToolUtils.isFastDoubleClick()) {
                        if (data.isIsfocus()) {
                            if(onItemClickListener != null){
                                MyLogcat.jLog().e("关注 onClick 1:" + data.isIsfocus());
                                onItemClickListener.unFollow(holder.tvGrowGz, data.getUser().getUserid(),position);
                            }
                        } else {
                            if(onItemClickListener != null){
                                MyLogcat.jLog().e("关注 onClick 2:" + data.isIsfocus());
                                onItemClickListener.Follow(holder.tvGrowGz, data.getUser().getUserid(),position);
                            }
                        }
                    }
                }
            });
            this.tvGrowEagrees.setText(data.getEagrees() + "");
            if (data.isIsagree()) {
                MyLogcat.jLog().e("已经点赞：" + data.isIsagree());
                this.ivGrowDef.setSelected(true);
            } else {
                this.ivGrowDef.setSelected(false);
                MyLogcat.jLog().e("未点赞：" + data.isIsagree());
            }

            this.ivGrowDef.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!ToolUtils.isFastDoubleClick()) {
                        MyLogcat.jLog().e("点赞：onClick" + data.isIsagree());
                        if (data.isIsagree()) {
                            if(onItemClickListener != null){
                                onItemClickListener.unFabulous(holder.ivGrowDef, holder.tvGrowEagrees, data.getEid(), data.getEagrees(),position);
                            }
                        } else {
                            GoodView goodView = new GoodView(context);
                            goodView.setText("+1");
                            goodView.show(v);
                            if(onItemClickListener != null){
                                onItemClickListener.Fabulous(holder.ivGrowDef, holder.tvGrowEagrees, data.getEid(), data.getEagrees(),position);
                            }
                        }
                    }
                }
            });
            this.tvGrowComment.setText(data.getEcommontcount() + "");//多少评论
            try {
                nineGrid.setTag(epubtime+"");
                String[] split = data.getEimages().split(";");
                List<String> imgUrlList = new ArrayList<>();
                for (int i = 0; i <split.length ; i++) {
                    imgUrlList.add(split[i]);
                }
                nineGrid.setImagesData(imgUrlList,epubtime+"");
            } catch (NullPointerException e) {
                /**
                 *  此处抛出异常 我们也进行setImagesData  不过传递为空
                 *  我们对imgUrlList的值进行监听  当为空我们将nineGrid进行隐藏
                 *  这样可以防止滑动过快时  没有图片显示  nineGrid却为VISIBLE
                 */
                nineGrid.setImagesData(new ArrayList<String>(),epubtime+"");
            }
        }
    }

    /**
     * 打开图片查看器
     *
     * @param position
     * @param urls
     */
    protected void imageBrower(int position, ArrayList<String> urls) {
        Intent intent = new Intent(context, ImagePagerActivity.class);
        // 图片url,为了演示这里使用常量，一般从数据库中或网络中获取
        intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_URLS, urls);
        intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_INDEX, position);
        context.startActivity(intent);
    }


    /**
     * 自定义点击接口回调
     */
    public interface onItemClickListener{
        /**点击监听**/
        void onItemClick(View v,int position);
        /***请求点赞*/
        void Fabulous(ImageView ivGrowDef, TextView tvGrowEagrees, String eid, Integer eagrees,int position);
        /**取消点赞**/
        void unFabulous(ImageView ivGrowDef, TextView tvGrowEagrees, String eid, Integer eagrees,int position);
        /**请求关注**/
        void Follow(TextView tvGrowGz, String focusedid,int position);
        /**取消关注**/
        void unFollow(TextView tvGrowGz, String focusedid,int position);
    }

    /**
     *  更新所有数据
     * @param list
     */
    public void Updata(List<AllLecturesBean.ListBean> list){
        this.list = list;
        this.notifyDataSetChanged();
    }
    /**
     *  更新选中数据集合
     * @param list
     */
    public void Updata(List<AllLecturesBean.ListBean> list,List<Integer> position,String payloads){
        this.list = list;
        for (int i = 0; i <position.size() ; i++) {
            this.notifyItemChanged(position.get(i),payloads);
        }
    }

    /**
     * 更新单条数据
     */
    public void UpdateItem(List<AllLecturesBean.ListBean> list,int position,String payloads){
//        //移除某个item
//        list.remove(position);
//        this.notifyItemRemoved(position);
//        //添加某个item
//        list.add(position,bean);
//        this.notifyItemInserted(position);
        this.list = list;
        this.notifyItemChanged(position,payloads);
    }
}
