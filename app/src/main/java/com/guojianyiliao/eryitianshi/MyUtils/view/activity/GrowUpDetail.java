package com.guojianyiliao.eryitianshi.MyUtils.view.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.guojianyiliao.eryitianshi.MyUtils.base.BaseActivity;
import com.guojianyiliao.eryitianshi.MyUtils.bean.EcommentsBean;
import com.guojianyiliao.eryitianshi.MyUtils.bean.EssayInfoBean;
import com.guojianyiliao.eryitianshi.MyUtils.interfaceservice.GetService;
import com.guojianyiliao.eryitianshi.MyUtils.manager.RetrofitClient;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.MyLogcat;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.SpUtils;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.StringUtils;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.TimeUtil;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.ToolUtils;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.UIUtils;
import com.guojianyiliao.eryitianshi.MyUtils.view.activity.view.XCFlowLayout;
import com.guojianyiliao.eryitianshi.R;
import com.lzy.ninegrid.ImageInfo;
import com.lzy.ninegrid.NineGridView;
import com.lzy.ninegrid.NineGridViewAdapter;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * description: 说说列表详情页面
 * autour: jnluo jnluo5889@126.com
 * date: 2017/4/21 16:38
 * update: 2017/4/21
 * version: Administrator
 */

public class GrowUpDetail extends BaseActivity implements NineGridView.ImageLoader {

    @BindView(R.id.nineGrid)
    NineGridView nineGrid;
    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.iv_hot_icon)
    XCFlowLayout ivHotIcon;
    @BindView(R.id.addEComment)
    TextView addEComment;

    String eid;
    @BindView(R.id.ed_add_pl)
    EditText edAddPl;

    @Override
    protected void onStart() {
        super.onStart();
        eid = getIntent().getStringExtra("eid");
        MyLogcat.jLog().e("eid:" + eid);
        HttpData(eid);
        HttpDataPL(eid);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_activity_growup_detail);
        ButterKnife.bind(this);
    }

    String eimages;
    ArrayList<ImageInfo> imageInfo = new ArrayList<>();
    List<String> isIcon = new ArrayList<String>();
    Integer etag = 0;


    @OnClick(R.id.addEComment)
    public void addEcomment() {
        if (!ToolUtils.isFastDoubleClick()) {
            HttpEcomment();
        }
    }

    private void HttpEcomment() {

        String eccontent = edAddPl.getText().toString();

        String ecuserid = SpUtils.getInstance(GrowUpDetail.this).get("userid", null);
        String ecusername = SpUtils.getInstance(GrowUpDetail.this).get("name", null);
        String ectime = TimeUtil.currectTimess();
        String ressayid = eid;//eid

        if (StringUtils.isEmpty(eccontent)) {
            ToolUtils.showToast(GrowUpDetail.this, "请填写评论内容！", Toast.LENGTH_SHORT);
            return;
        }
        if (StringUtils.isEmpty(ecuserid)) {
            return;
        }
        if (StringUtils.isEmpty(ecusername)) {
            return;
        }
        if (StringUtils.isEmpty(ectime)) {
            return;
        }
        if (StringUtils.isEmpty(ressayid)) {
            return;
        }
        RetrofitClient.getinstance(this).create(GetService.class).addEComment(eccontent, ecuserid, ecusername, ectime, ressayid).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    MyLogcat.jLog().e("说说列表详情页面: onResponse" + response.body().toString());
                    if ("true".equals(response.body())) {
                        ToolUtils.showToast(GrowUpDetail.this, "评论成功！", Toast.LENGTH_SHORT);
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                MyLogcat.jLog().e("说说列表详情页面: onFailure");
                ToolUtils.showToast(GrowUpDetail.this, "评论失败！", Toast.LENGTH_SHORT);
            }
        });

    }

    private void HttpData(String eid) {
        RetrofitClient.getinstance(this).create(GetService.class).getEssayInfo(eid).enqueue(new Callback<EssayInfoBean>() {
            @Override
            public void onResponse(Call<EssayInfoBean> call, Response<EssayInfoBean> response) {
                if (response.isSuccessful()) {
                    MyLogcat.jLog().e("说说列表详情页面:");

                    eimages = response.body().getEssay().getEimages();
                    MyLogcat.jLog().e("eimages:" + eimages);
                    if (!StringUtils.isEmpty(eimages)) {
                        if (imageInfo.size() != 0) {
                            imageInfo.clear();
                        }
                        try {
                            String[] split = eimages.split(";");
                            if (split != null) {

                                MyLogcat.jLog().e("growupdetail:" + split.toString());

                                for (int i = 0; i < split.length; i++) {
                                    ImageInfo imageInfoGrow = new ImageInfo();
                                    imageInfoGrow.setBigImageUrl(split[i]);
                                    imageInfoGrow.setThumbnailUrl(split[i]);
                                    imageInfo.add(imageInfoGrow);
                                    MyLogcat.jLog().e("--------------------" + split[i]);
                                }
                                nineGrid.setVisibility(View.VISIBLE);

                                NineGridViewAdapter myNineAdapter = new NineGridViewAdapter(GrowUpDetail.this, imageInfo);
                                myNineAdapter.setImageInfoList(imageInfo);
                                MyLogcat.jLog().e("--------------------");
                                nineGrid.setAdapter(myNineAdapter);
                                nineGrid.setImageLoader(GrowUpDetail.this);

                                nineGrid.setMaxSize(9);
                                //nineGrid.setSingleImageRatio(UIUtils.dip2px(220));
                                //nineGrid.setSingleImageSize(UIUtils.dip2px(220));
                                nineGrid.setGridSpacing(UIUtils.dip2px(5));
                            } else {
                                nineGrid.setVisibility(View.GONE);
                            }

                        } catch (NullPointerException e) {
                            MyLogcat.jLog().e("growupDetail:" + e.getMessage());
                        }

                        int padding = UIUtils.dip2px(6);
                        ivHotIcon.removeAllViews();

                        List<EssayInfoBean.EssayAgreeListBean> essayAgreeList = response.body().getEssayAgreeList();
                        if (essayAgreeList.size() != 0) {
                            for (EssayInfoBean.EssayAgreeListBean bean : essayAgreeList) {
                                isIcon.add(bean.getUser().getIcon());
                            }
                            MyLogcat.jLog().e("isIcon:" + isIcon.size());
                        }

                        for (int i = 0; i < isIcon.size(); i++) {
                            final ImageView ivIscommon = new ImageView(GrowUpDetail.this);
                            ivIscommon.setTag(etag++);
                            ivIscommon.setAdjustViewBounds(true);
                            ivIscommon.setMaxHeight(UIUtils.dip2px(50));
                            ivIscommon.setMaxWidth(UIUtils.dip2px(50));
                            /*Picasso.with(GrowUpDetail.this).load(isIcon.get(i))
                                    .resize(UIUtils.dip2px(50), UIUtils.dip2px(50))
                                    .centerCrop()
                                    .into(ivIscommon);*/
                            ImageLoader.getInstance().displayImage(isIcon.get(i), ivIscommon);
                            ivIscommon.setPadding(padding, padding, padding, padding);
                            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                            lp.setMargins(0, 0, padding, padding);
                            ivIscommon.setLayoutParams(lp);
                            ivHotIcon.addView(ivIscommon);
                            ivIscommon.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    MyLogcat.jLog().e("tag:" + ivIscommon.getTag());
                                    int tag = (int) ivIscommon.getTag();
                                    //typeid = isCommon.get(tag).typeid;
                                    //HttpHotData(typeid);

                                }
                            });
                        }

                    }

                    //  showRecycler();
                }
            }

            @Override
            public void onFailure(Call<EssayInfoBean> call, Throwable t) {
                MyLogcat.jLog().e("说说列表详情页面: onFailure");
            }
        });
    }


    Myadpter myadpter;

    public void showRecycler(List<EcommentsBean> data) {
        if (myadpter == null) {
            myadpter = new Myadpter(data);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
            recycler.setLayoutManager(linearLayoutManager);
            recycler.setAdapter(myadpter);
        } else {
            myadpter.setData(data);
        }

    }

    class Myadpter extends RecyclerView.Adapter {
        List<EcommentsBean> data;

        public Myadpter(List<EcommentsBean> data) {
            this.data = data;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = UIUtils.getinflate(R.layout.a_fragment_growup_recyitem);
            Myholder holder = new Myholder(view);
            return holder;
        }

        public void setData(List<EcommentsBean> data) {
            this.data = data;
            this.notifyDataSetChanged();
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            Myholder myholder = (Myholder) holder;
               /**
                *  通过判读replyid是否有值，加载评论内容或回复内容
               * */
            try {
                if (StringUtils.isEmpty(data.get(position).getReplyid())) {
                    MyLogcat.jLog().e("Replyid:" + data.get(position).getReplyid());
                    myholder.tvGrowContent.setText(data.get(position).getEccontent());
                    //  ImageLoader.getInstance().displayImage(data.get(position), myholder.ivIcon);//头像icon,暂时没有
                    myholder.tvGrowName.setText(data.get(position).getEcusername());
                    myholder.tvGrowTime.setText(TimeUtil.SJC(data.get(position).getEctime() + ""));
                } else {
                    MyLogcat.jLog().e(" ! Replyid:" + data.get(position).getReplyid());
                    //   ImageLoader.getInstance().displayImage(data.get(position).getReplyUser().getIcon(), myholder.ivIcon);
                    myholder.tvGrowName.setText(data.get(position).getRusername());
                    myholder.tvGrowTime.setText(TimeUtil.SJC(data.get(position).getRtime() + ""));
                    String dec = data.get(position).getEcusername();//@回复
                    String content = data.get(position).getRcontent();//内容
                    //  myholder.tvGrowContent.setText("回复" + "@" + dec + ":" + content);

                    String str = "回复" + "@" + dec + ":" + content;
                    // int bstart = str.indexOf("@" + dec);
                    // int bend = bstart + ("@" + dec).length();
                    int fstart = str.indexOf("@" + dec);
                    int fend = fstart + ("@" + dec).length();
                    SpannableStringBuilder style = new SpannableStringBuilder(str);
                    // style.setSpan(new BackgroundColorSpan(Color.RED),bstart,bend,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    style.setSpan(new ForegroundColorSpan(getColor(R.color.view)), fstart, fend, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
                    myholder.tvGrowContent.setText(style);
                }
            } catch (Exception e) {
                MyLogcat.jLog().e("SpannableStringBuilder:" + e.getMessage());
            }
            /** 回复*/
           /* myholder.tvGrowContent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MyLogcat.jLog().e("onClick tvGrowContent:");
                    ToolUtils.openKeyBord(edAddPl, GrowUpDetail.this);
                }
            });*/
            /*myholder.tvGrowContent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MyLogcat.jLog().e("onClick tvGrowContent:");

                    if (StringUtils.isEmpty(data.get(position).getReplyid())) {
                        *//**取到 replyid ressayid*//*
                        MyLogcat.jLog().e("  getReplyid");
                        //ereplyid = data.get(position).getReplyid();
                        eressayid = data.get(position).getRessayid();
                        euserid = data.get(position).getEcuserid();
                        eusername = data.get(position).getEcusername();
                    } else {
                        MyLogcat.jLog().e(" ! getReplyid");
                       // ereplyid = data.get(position).getReplyid();
                        eressayid = data.get(position).getRessayid();
                        euserid = data.get(position).getRuserid();
                        eusername = data.get(position).getRusername();

                    }
                    addReplyEComment(eressayid, euserid, eusername);

                }
            });*/

        }

        @Override
        public int getItemCount() {
            return data.size();
        }
    }

    String eressayid = "";
    String ereplyid = "";
    String euserid = "";
    String eusername = "";

    class Myholder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_icon)
        CircleImageView ivIcon;
        @BindView(R.id.tv_grow_name)
        TextView tvGrowName;
        @BindView(R.id.tv_grow_time)
        TextView tvGrowTime;
        @BindView(R.id.tv_grow_content)
        TextView tvGrowContent;

        public Myholder(View itemView) {
            super(itemView);
            ButterKnife.bind(itemView);

        }
    }

    @Override
    public void onDisplayImage(Context context, ImageView imageView, String url) {
        Object tag = new Object();
        Picasso.with(context).load(url)
                .config(Bitmap.Config.RGB_565)
                .tag(tag)
                .fit()
                .into(imageView);
    }

    @Override
    public Bitmap getCacheImage(String url) {
        return null;
    }

    /**
     * 评论列表
     * ressayid 说说ID
     * replyid 回复ID，当有回复信息时，此时replyid 才有值
     */
    private void HttpDataPL(String eid) {
        //eid = "10e4084158d94170a8e3915427c4914c";
        RetrofitClient.getinstance(this).create(GetService.class).getEComments(eid).enqueue(new Callback<List<EcommentsBean>>() {
            @Override
            public void onResponse(Call<List<EcommentsBean>> call, Response<List<EcommentsBean>> response) {
                if (response.isSuccessful()) {
                    MyLogcat.jLog().e("评论列表: onResponse");

                    List<EcommentsBean> body = response.body();
                    showRecycler(body);


                }
            }

            @Override
            public void onFailure(Call<List<EcommentsBean>> call, Throwable t) {
                MyLogcat.jLog().e("评论列表: onFailure");
            }
        });
    }

    public void addReplyEComment(String eressayid, String euserid, String eusername) {
        /**replyid,ressayid,ruserid,rusername,rcontent,rtime,ecuserid,ecusername,ectime * */
        String ruserid = SpUtils.getInstance(this).get("userid", null);
        String rusername = SpUtils.getInstance(this).get("name", null);
        String rcontent = edAddPl.getText().toString();
        String rtime = "";
        String etime = "";
        if (StringUtils.isEmpty(rcontent)) {
            ToolUtils.showToast(GrowUpDetail.this, "请填写评论内容！", Toast.LENGTH_SHORT);
            return;
        }
        RetrofitClient.getinstance(this).create(GetService.class).addReplyEComment(eid, eressayid, ruserid, rusername, rcontent, rtime, euserid, eusername, etime).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                MyLogcat.jLog().e("回复: onResponse");
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                MyLogcat.jLog().e("回复: onFailure");
            }
        });
    }

}

