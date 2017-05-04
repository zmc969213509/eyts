package com.guojianyiliao.eryitianshi.MyUtils.view.activity.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.guojianyiliao.eryitianshi.MyUtils.bean.AllLecturesBean;
import com.guojianyiliao.eryitianshi.MyUtils.interfaceservice.GetService;
import com.guojianyiliao.eryitianshi.MyUtils.manager.RetrofitClient;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.MyLogcat;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.SpUtils;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.StringUtils;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.TimeUtil;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.ToolUtils;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.UIUtils;
import com.guojianyiliao.eryitianshi.MyUtils.view.activity.GrowUpDetailcopy;
import com.guojianyiliao.eryitianshi.MyUtils.view.activity.view.OnReFlashListView.ReFlashListView;
import com.guojianyiliao.eryitianshi.R;
import com.lzy.ninegrid.ImageInfo;
import com.lzy.ninegrid.NineGridView;
import com.lzy.ninegrid.NineGridViewAdapter;
import com.lzy.ninegrid.preview.ImagePreviewActivity;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.squareup.picasso.Picasso;
import com.wx.goodview.GoodView;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * description: 成长树页面
 * autour: jnluo jnluo5889@126.com
 * date: 2017/4/18 9:35
 * update: 2017/4/18
 * version: Administrator
 */
public class GrowUpFragment extends Fragment implements ReFlashListView.IReflashListener {
    String userid;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userid = SpUtils.getInstance(getActivity()).get("userid", null);
        HttpData("0");
    }

    ReFlashListView listview;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.a_fragment_growup, container, false);
        listview = (ReFlashListView) view.findViewById(R.id.listview);
        return view;
    }

    Myadaper myadaper;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listview.setInterface(this);
        View emptyview = UIUtils.getinflate(R.layout.layout_emptyview);
        listview.setEmptyView(emptyview);
        emptyview.findViewById(R.id.tv_emptyview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HttpData("0");
            }
        });
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MyLogcat.jLog().e("ListVIew click item:" + position + "//eid:" + data.get(position - 1).getEid());
                Intent intent = new Intent(getActivity(), GrowUpDetailcopy.class);
                intent.putExtra("isfocus", data.get(position - 1).isIsfocus());
                intent.putExtra("eid", data.get(position - 1).getEid());
                startActivity(intent);
            }
        });
    }

    private void showList() {

        if (myadaper == null) {
            myadaper = new Myadaper(data);
            listview.setAdapter(myadaper);
        } else {
            myadaper.onDateChange(data);
        }
    }

    @Override
    public void onReflash() {
        MyLogcat.jLog().e("onReflash：" + data.size());
        UIUtils.getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                MyLogcat.jLog().e("onReflash");
                //获取最新数据
                HttpData("0");
                //通知界面显示
                showList();
                //通知listview 刷新数据完毕；
                listview.reflashComplete();
            }
        }, 1000);
    }

    List<String> page = new ArrayList<String>() {{
        add("");
    }};

    @Override
    public void onLoadingMore() {
        MyLogcat.jLog().e("onLoadingMore：" + data.size());
        UIUtils.getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //获取最新数据
                page.add("");
                HttpData(page.size() + "");
                MyLogcat.jLog().e("Page++:" + page.size());
                //通知界面显示
                showList();
                //通知listview 刷新数据完毕；
                listview.reflashComplete();
            }
        }, 0);
    }

    class Myadaper extends BaseAdapter implements NineGridView.ImageLoader {
        List<AllLecturesBean.ListBean> data;

        public Myadaper(List<AllLecturesBean.ListBean> data) {
            this.data = data;
        }

        public void onDateChange(List<AllLecturesBean.ListBean> data) {
            this.data = data;
            this.notifyDataSetChanged();
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

        String eid;
        Integer eagrees;
        String focusedid;
        ArrayList<ImageInfo> imageInfo = new ArrayList<>();

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final myViewholder holder;
            if (convertView == null) {
                convertView = UIUtils.getinflate(R.layout.a_fragment_growup_listviewitem);
                holder = new myViewholder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (myViewholder) convertView.getTag();
            }
            ImageLoader.getInstance().displayImage(data.get(position).getUser().getIcon(), holder.ivIcon);
            holder.tvGrowName.setText(data.get(position).getUser().getName());
            try {
                long epubtime = data.get(position).getEpubtime();
                MyLogcat.jLog().e(" q：" + epubtime);
                String data = TimeUtil.SJC(epubtime + "");
                holder.tvGrowTime.setText(data);
                MyLogcat.jLog().e("data：" + data);
            } catch (Exception e) {
                MyLogcat.jLog().e("：" + e.getMessage());
            }
            if (!StringUtils.isEmpty(data.get(position).getEmemo())) {
                holder.tvGrowTitle.setVisibility(View.VISIBLE);
                holder.tvGrowTitle.setText(data.get(position).getEmemo());//标题
            } else {
                holder.tvGrowTitle.setVisibility(View.GONE);
            }
            if (!StringUtils.isEmpty(data.get(position).getEcontent())) {
                holder.tvGrowContent.setVisibility(View.VISIBLE);
                holder.tvGrowContent.setText(data.get(position).getEcontent());//内容
            } else {
                holder.tvGrowContent.setVisibility(View.GONE);
            }
            holder.tvGrowSee.setText("浏览" + data.get(position).getEcommontcount() + "次");//浏览
            /**
             * 是否关注
             */
            if (data.get(position).isIsfocus()) {
                holder.tvGrowGz.setText("已关注");
            } else {
                holder.tvGrowGz.setText("+ 关注");
            }
            holder.tvGrowGz.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!ToolUtils.isFastDoubleClick()) {
                        focusedid = data.get(position).getUser().getUserid();
                        if (data.get(position).isIsfocus()) {
                            MyLogcat.jLog().e("关注 onClick 1:" + data.get(position).isIsfocus());
                            HttpDelGz(holder.tvGrowGz, focusedid, position);
                        } else {
                            MyLogcat.jLog().e("关注 onClick 2:" + data.get(position).isIsfocus());
                            HttpGz(holder.tvGrowGz, focusedid, position);
                        }
                    }
                }
            });
            holder.tvGrowEagrees.setText(data.get(position).getEagrees() + "");
            if (data.get(position).isIsagree()) {
                MyLogcat.jLog().e("已经点赞：" + data.get(position).isIsagree());
                holder.ivGrowDef.setSelected(true);
            } else {
                holder.ivGrowDef.setSelected(false);
                MyLogcat.jLog().e("未点赞：" + data.get(position).isIsagree());
            }
            eid = data.get(position).getEid();
            eagrees = data.get(position).getEagrees();
            holder.ivGrowDef.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!ToolUtils.isFastDoubleClick()) {
                        MyLogcat.jLog().e("点赞：onClick" + data.get(position).isIsagree());
                        if (data.get(position).isIsagree()) {
                            HttpDisDZ(holder.ivGrowDef, holder.tvGrowEagrees, eid, eagrees, position);
                        } else {
                            GoodView goodView = new GoodView(getActivity());
                            goodView.setText("+1");
                            goodView.show(v);
                            HttpDZ(holder.ivGrowDef, holder.tvGrowEagrees, eid, eagrees, position);
                        }
                    }
                }
            });
            holder.tvGrowComment.setText(data.get(position).getEagrees() + "");//多少评论
            // MyLogcat.jLog().e("Eimages adpter:" + data.get(position).getEimages());
            try {
                String[] split = data.get(position).getEimages().split(";");
                if (split != null) {
                    if (imageInfo.size() != 0) {
                        imageInfo.clear();
                    }
                    for (int i = 0; i < split.length; i++) {
                        //   MyLogcat.jLog().e("split:" + split[i]);
                        ImageInfo imageInfoGrow = new ImageInfo();
                        imageInfoGrow.setBigImageUrl(split[i]);
                        imageInfoGrow.setThumbnailUrl(split[i]);
                        imageInfo.add(imageInfoGrow);
                    }
                    holder.nineGrid.setVisibility(View.VISIBLE);
                    //MyLogcat.jLog().e("ImageInfo adpter:" + imageInfo.toString());
                    MyNineAdapter myNineAdapter = new MyNineAdapter(getActivity(), imageInfo);
                    holder.nineGrid.setAdapter(myNineAdapter);
                    holder.nineGrid.setImageLoader(this);
                    MyLogcat.jLog().e("singleImageSize:" + holder.nineGrid.getHeight());
                    holder.nineGrid.setMaxSize(9);
                    // holder.nineGrid.setSingleImageRatio(UIUtils.dip2px(220));
                    //holder.nineGrid.setSingleImageSize(UIUtils.dip2px(220));
                    holder.nineGrid.setGridSpacing(UIUtils.dip2px(5));
                } else {
                    holder.nineGrid.setVisibility(View.GONE);
                }
            } catch (NullPointerException e) {
                MyLogcat.jLog().e("NineGridViewClickAdapter:" + "//pos:" + position + "//excep:" + e.getMessage());
            }
            return convertView;
        }

        @Override
        public void onDisplayImage(Context context, ImageView imageView, String url) {
            MyLogcat.jLog().e("onDisplayImage:" + url);
            Object tag = new Object();
            Picasso.with(context).load(url)
                    .config(Bitmap.Config.RGB_565)
                    .tag(tag)
                    .fit()
                    .into(imageView);
            //ImageLoader.getInstance().displayImage(url, imageView);
        }

        /**
         * 大图 缓存
         */
        @Override
        public Bitmap getCacheImage(String url) {
            MyLogcat.jLog().e("Bitmap:" + url);
            Bitmap bitmap = null;
            try {
                bitmap = Picasso.with(getActivity()).load(url).get();
            } catch (IOException e) {
                MyLogcat.jLog().e("Bitmap:" + e.getMessage());
            }
            return bitmap;
        }
    }

    static class myViewholder {
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
        com.lzy.ninegrid.NineGridView nineGrid;
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

        public myViewholder(View convertView) {
            rootView = convertView;
            ButterKnife.bind(this, convertView);
        }
    }

    List<AllLecturesBean.ListBean> data = new ArrayList<>();

    private void HttpData(String page) {
        MyLogcat.jLog().e("说说列表 page:" + page);
        String userid = SpUtils.getInstance(getActivity()).get("userid", null);
        RetrofitClient.getinstance(getActivity()).create(GetService.class).getAllLectures(page, userid).enqueue(new Callback<AllLecturesBean>() {
            @Override
            public void onResponse(Call<AllLecturesBean> call, Response<AllLecturesBean> response) {
                if (response.isSuccessful()) {
                    if (data.size() != 0) {
                        data.clear();
                    }
                    MyLogcat.jLog().e("获取说说列 w onResponse：" + response.body().toString());
                    List<AllLecturesBean.ListBean> list = response.body().getList();
                    for (AllLecturesBean.ListBean bean : list) {
                        MyLogcat.jLog().e("List Bean:" + bean.getUser().getName());
                        data.add(bean);
                    }
                    MyLogcat.jLog().e("List Bean size:" + data.size());
                    showList();
                }
            }

            @Override
            public void onFailure(Call<AllLecturesBean> call, Throwable t) {
                MyLogcat.jLog().e("获取说说列表 e onFailure：");
            }
        });
    }

    private int statusHeight;

    class MyNineAdapter extends NineGridViewAdapter {
        public MyNineAdapter(Context context, List<ImageInfo> imageInfo) {
            super(context, imageInfo);
        }

        @Override
        protected void onImageItemClick(Context context, NineGridView nineGridView, int index, List<ImageInfo> imageInfo) {
            statusHeight = getStatusHeight(getActivity());
            MyLogcat.jLog().e("onImageItemClick:");
            for (int i = 0; i < imageInfo.size(); i++) {
                ImageInfo info = imageInfo.get(i);
                View imageView;
                if (i < nineGridView.getMaxSize()) {
                    imageView = nineGridView.getChildAt(i);
                } else {
                    //如果图片的数量大于显示的数量，则超过部分的返回动画统一退回到最后一个图片的位置
                    imageView = nineGridView.getChildAt(nineGridView.getMaxSize() - 1);
                }
                info.imageViewWidth = imageView.getWidth();
                info.imageViewHeight = imageView.getHeight();
                int[] points = new int[2];
                imageView.getLocationInWindow(points);
                info.imageViewX = points[0];
                info.imageViewY = points[1] - statusHeight;
            }
            Intent intent = new Intent(context, ImagePreviewActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Bundle bundle = new Bundle();
            bundle.putSerializable(ImagePreviewActivity.IMAGE_INFO, (Serializable) imageInfo);
            bundle.putInt(ImagePreviewActivity.CURRENT_ITEM, index);
            intent.putExtras(bundle);
            getActivity().startActivity(intent);
            getActivity().overridePendingTransition(0, 0);
        }

        @Override
        protected ImageView generateImageView(Context context) {
            return super.generateImageView(context);
        }
    }

    /**
     * 获得状态栏的高度
     */
    public int getStatusHeight(Context context) {
        int statusHeight = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height").get(object).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }

    /**
     * 请求点赞
     */
    private void HttpDZ(final ImageView ivGrowDef, final TextView tvGrowEagrees, String eid, final Integer eagrees, final int pos) {
        String time = TimeUtil.currectTimess();
        MyLogcat.jLog().e("id:" + userid + "//" + time + "//" + eid);
        RetrofitClient.getinstance(getActivity()).create(GetService.class).agreeWithEssay(eid, userid, time).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    MyLogcat.jLog().e("请求点赞 onResponse:" + response.body().toString());
                    if ("true".equals(response.body().toString())) {
                        tvGrowEagrees.setText(eagrees + 1 + "");
                        ivGrowDef.setSelected(true);
                        data.get(pos).setIsagree(true);
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                MyLogcat.jLog().e("请求点赞 onFailure:");
            }
        });
    }

    /**
     * 取消点赞
     */
    private void HttpDisDZ(final ImageView ivGrowDef, final TextView tvGrowEagrees, String eid, final Integer eagrees, final int pos) {
        MyLogcat.jLog().e("id:" + userid + "//" + eid);
        RetrofitClient.getinstance(getActivity()).create(GetService.class).disAgreeWithEssay(eid, userid).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    MyLogcat.jLog().e("取消点赞 onResponse:" + response.body().toString());
                    if ("true".equals(response.body().toString())) {
                        if (eagrees <= 1) {
                            tvGrowEagrees.setText("");
                        } else {
                            tvGrowEagrees.setText((eagrees - 1) + "");
                        }
                        ivGrowDef.setSelected(false);
                        data.get(pos).setIsagree(false);
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                MyLogcat.jLog().e("取消点赞 onFailure:");
            }
        });
    }

    /**
     * 请求关注
     */
    private void HttpGz(final TextView tvGrowGz, String focusedid, final int pos) {
        RetrofitClient.getinstance(getActivity()).create(GetService.class).addMyFocus(userid, "", focusedid, "").enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    MyLogcat.jLog().e("请求关注 onResponse:" + response.body().toString());
                    if ("true".equals(response.body().toString())) {
                        ToolUtils.showToast(getActivity(), "关注成功！", Toast.LENGTH_SHORT);
                        tvGrowGz.setText("已关注");
                        data.get(pos).setIsfocus(true);
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                MyLogcat.jLog().e("请求关注 onFailure:");
            }
        });
    }

    /**
     * 取消关注
     */
    private void HttpDelGz(final TextView tvGrowGz, String focusedid, final int pos) {
        RetrofitClient.getinstance(getActivity()).create(GetService.class).delMyFocus(userid, focusedid).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    MyLogcat.jLog().e("取消关注 onResponse:" + response.body().toString());
                    if ("true".equals(response.body().toString())) {
                        ToolUtils.showToast(getActivity(), "取消成功！", Toast.LENGTH_SHORT);
                        tvGrowGz.setText("+ 关注");
                        data.get(pos).setIsfocus(false);
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                MyLogcat.jLog().e("取消关注 onFailure:");
            }
        });
    }
}