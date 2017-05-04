package com.guojianyiliao.eryitianshi.MyUtils.view.activity.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.guojianyiliao.eryitianshi.MyUtils.base.Post;
import com.guojianyiliao.eryitianshi.MyUtils.bean.AllLecturesBean;
import com.guojianyiliao.eryitianshi.MyUtils.interfaceservice.GetService;
import com.guojianyiliao.eryitianshi.MyUtils.manager.RetrofitClient;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.MyLogcat;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.SpUtils;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.UIUtils;
import com.guojianyiliao.eryitianshi.MyUtils.view.activity.view.OnReFlashListView.ReFlashListView;
import com.guojianyiliao.eryitianshi.R;
import com.jaeger.ninegridimageview.NineGridImageView;
import com.jaeger.ninegridimageview.NineGridImageViewAdapter;
import com.lzy.ninegrid.ImageInfo;
import com.lzy.ninegrid.NineGridView;
import com.lzy.ninegrid.NineGridViewAdapter;
import com.lzy.ninegrid.preview.ImagePreviewActivity;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
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

public class GrowUpFragmentcopy extends Fragment implements ReFlashListView.IReflashListener {

    ArrayList<Post> imageInfo = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HttpData();
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
                HttpData();
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
        MyLogcat.jLog().e("onReflash");
        //获取最新数据
        HttpData();
        //通知界面显示
        showList();
        //通知listview 刷新数据完毕；
        listview.reflashComplete();
    }

    @Override
    public void onLoadingMore() {
        MyLogcat.jLog().e("onLoadingMore");
        //获取最新数据
        // HttpData();
        //通知界面显示
        // showList();
        //通知listview 刷新数据完毕；
        // listview.reflashComplete();

    }

    //implements NineGridView.ImageLoader
    class Myadaper extends BaseAdapter {

        List<AllLecturesBean> data;

        public Myadaper(List<AllLecturesBean> data) {
            this.data = data;
        }

        public void onDateChange(List<AllLecturesBean> data) {
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

        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            return  null;
        } /*{
            myViewholder holder = null;
            if (convertView == null) {
                convertView = UIUtils.getinflate(R.layout.a_fragment_growup_listviewitem);
                holder = new myViewholder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (myViewholder) convertView.getTag();
            }

            // setImage(context, holder.nineGrid, item.avatar == null ? null : item.avatar.smallPicUrl);

            //MyNineAdapter nineGridViewAdapter = new MyNineAdapter(getActivity(), imageInfo);
           *//* holder.nineGrid.setGridSpacing(UIUtils.dip2px(10));
            holder.nineGrid.setImageLoader(this);
            holder.nineGrid.setMaxSize(9);*//*

            try {
                String[] split = data.get(position).getEimages().split(";");
                if (split != null) {
                    url.clear();
                    for (int i = 0; i < split.length; i++) {
                        MyLogcat.jLog().e("split:" + split[i]);
                        //ImageInfo imageInfoGrow = new ImageInfo();
                        //imageInfoGrow.setBigImageUrl(split[i]);
                        // imageInfoGrow.setThumbnailUrl(split[i]);
                        url.add(split[i]);

                    }
                    MyLogcat.jLog().e("Url List:" + url.size());
                }

            } catch (NullPointerException e) {
                MyLogcat.jLog().e("split excp:");
            }

            Post post = new Post();
            post.setImgUrlList(url);
            imageInfo.add(post);


            MyLogcat.jLog().e("getImgUrlList:" + position + "//" + imageInfo.get(position).getImgUrlList().size());
            holder.nineGrid.setAdapter(mAdapter);
            holder.nineGrid.setImagesData(imageInfo.get(position).getImgUrlList());

            return convertView;
        }*/


        private NineGridImageViewAdapter<String> mAdapter = new NineGridImageViewAdapter<String>() {
            @Override
            protected void onDisplayImage(Context context, ImageView imageView, String s) {
                Picasso.with(context).load(s).placeholder(R.drawable.ic_error_page).error(R.drawable.ic_error_page).into(imageView);
            }

            @Override
            protected ImageView generateImageView(Context context) {
                return super.generateImageView(context);
            }

            @Override
            protected void onItemImageClick(Context context, ImageView imageView, int index, List<String> list) {
                Toast.makeText(context, "image position is " + index, Toast.LENGTH_SHORT).show();
            }
        };

    }


        /*@Override
        public void onDisplayImage(Context context, ImageView imageView, String url) {
            Picasso.with(context).load(url)//
                    .placeholder(R.drawable.ic_error_page)//
                    .error(R.drawable.ic_error_page)//ic_default_color
                    //.diskCacheStrategy(DiskCacheStrategy.ALL)//
                    .into(imageView);

        }

        @Override
        public Bitmap getCacheImage(String url) {
            try {
                Bitmap bitmap = Picasso.with(getActivity()).load(url).get();
                MyLogcat.jLog().e("picasso 获取bitmap:" + bitmap);
            } catch (Exception e) {
                MyLogcat.jLog().e("picasso 获取bitmap 失败" + e.getMessage());
            }
            MyLogcat.jLog().e("returnBitMap 获取bitmap:" + returnBitMap(url));
            return returnBitMap(url);
        }
*/
       /* @Override
        public void onDisplayImage(Context context, ImageView imageView, String url) {
            Picasso.with(context).load(url)//
                    .placeholder(R.drawable.ic_error_page)//
                    .error(R.drawable.ic_error_page)//ic_default_color
                    //.diskCacheStrategy(DiskCacheStrategy.ALL)//
                    .into(imageView);
        }

        @Override
        public Bitmap getCacheImage(String url) {
            return null;
        }*/

    public Bitmap returnBitMap(String url) {
        URL myFileUrl = null;
        Bitmap bitmap = null;
        try {
            myFileUrl = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            MyLogcat.jLog().e("returnBitMap 获取bitmap 失败" + e.getMessage());
        }
        return bitmap;
    }

    class myViewholder {
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

       /* @BindView(R.id.nineGrid)
        com.lzy.ninegrid.NineGridView nineGrid;*/

        @BindView(R.id.tv_grow_see)
        TextView tvGrowSee;
        @BindView(R.id.tv_grow_comment)
        TextView tvGrowComment;
        @BindView(R.id.iv_grow_evaluate)
        ImageView ivGrowEvaluate;
        @BindView(R.id.iv_grow_def)
        ImageView ivGrowDef;

        private View rootView;

        NineGridImageView nineGrid;

        public myViewholder(View convertView) {
            rootView = convertView;
            nineGrid = (NineGridImageView) convertView.findViewById(R.id.nineGrid);
            ButterKnife.bind(this, convertView);
        }
    }

    List<AllLecturesBean> data = new ArrayList<>();
    List<String> url = new ArrayList<>();

    private void HttpData() {}/*{
        String page = "1";
        String userid = SpUtils.getInstance(getActivity()).get("userid", null);
        RetrofitClient.getinstance(getActivity()).create(GetService.class).getAllLectures(page, userid).enqueue(new Callback<List<AllLecturesBean>>() {
            @Override
            public void onResponse(Call<List<AllLecturesBean>> call, Response<List<AllLecturesBean>> response) {
                if (response.isSuccessful()) {

                    if (url.size() != 0) {
                        url.clear();
                    }
                    if (imageInfo.size() != 0) {
                        imageInfo.clear();
                    }
                    MyLogcat.jLog().e("获取说说列 w onResponse：");
                    List<AllLecturesBean> body = response.body();
                    for (AllLecturesBean bean : body) {
                        data.add(bean);
                    }
                    showList();
                   *//* try {
                        String[] split = bean.getEimages().split(";");
                        if (split != null) {
                            for (int i = 0; i < split.length; i++) {
                                MyLogcat.jLog().e("split:" + split[i]);
                                ImageInfo imageInfoGrow = new ImageInfo();
                                imageInfoGrow.setBigImageUrl(split[i]);
                                imageInfoGrow.setThumbnailUrl(split[i]);
                                imageInfo.add(imageInfoGrow);
                            }
                        }
                        MyLogcat.jLog().e("ImageInfo adpter:" + imageInfo.toString());

                    } catch (NullPointerException e) {
                        MyLogcat.jLog().e("NineGridViewClickAdapter:" + "//pos:" + position + "//excep:" + e.getMessage());
                    }*//*

                }
            }

            @Override
            public void onFailure(Call<List<AllLecturesBean>> call, Throwable t) {
                MyLogcat.jLog().e("获取说说列表 onFailure：");
            }
        });
    }*/

    class MyNineAdapter extends NineGridViewAdapter {
        public MyNineAdapter(Context context, List<ImageInfo> imageInfo) {
            super(context, imageInfo);
        }

        @Override
        protected void onImageItemClick(Context context, NineGridView nineGridView, int index, List<ImageInfo> imageInfo) {
            //MyNineAdapter nineGridView = new MyNineAdapter(getActivity(), imageInfo);
            // NineGridView nineGridView1 = new NineGridView(getActivity());
            //nineGridView.onImageItemClick(getActivity(), nineGridView1, position, imageInfo);
            MyLogcat.jLog().e("onImageItemClick:");

            /*for (int i = 0; i < imageInfo.size(); i++) {
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
            }*/

            Intent intent = new Intent(context, ImagePreviewActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable(ImagePreviewActivity.IMAGE_INFO, (Serializable) imageInfo);
            bundle.putInt(ImagePreviewActivity.CURRENT_ITEM, index);
            intent.putExtras(bundle);
            context.startActivity(intent);
            ((Activity) context).overridePendingTransition(0, 0);
        }
    }
}
