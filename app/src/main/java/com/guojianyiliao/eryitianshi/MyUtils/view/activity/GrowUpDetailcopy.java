package com.guojianyiliao.eryitianshi.MyUtils.view.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.guojianyiliao.eryitianshi.MyUtils.adaper.zmc_EssayCommentRecycleviewAdapter;
import com.guojianyiliao.eryitianshi.MyUtils.base.BaseActivity;
import com.guojianyiliao.eryitianshi.MyUtils.base.Post;
import com.guojianyiliao.eryitianshi.MyUtils.bean.EcommentsBean;
import com.guojianyiliao.eryitianshi.MyUtils.bean.EssayInfoBean;
import com.guojianyiliao.eryitianshi.MyUtils.bean.UserInfoLogin;
import com.guojianyiliao.eryitianshi.MyUtils.customView.DianZanView;
import com.guojianyiliao.eryitianshi.MyUtils.customView.MyGridView;
import com.guojianyiliao.eryitianshi.MyUtils.customView.MyRecycleView;
import com.guojianyiliao.eryitianshi.MyUtils.interfaceservice.GetService;
import com.guojianyiliao.eryitianshi.MyUtils.manager.RetrofitClient;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.MyLogcat;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.SharedPreferencesTools;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.SpUtils;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.StringUtils;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.TimeUtil;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.ToolUtils;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.UIUtils;
import com.guojianyiliao.eryitianshi.MyUtils.view.activity.view.XCFlowLayout;
import com.guojianyiliao.eryitianshi.R;
import com.jaeger.ninegridimageview.NineGridImageView;
import com.jaeger.ninegridimageview.NineGridImageViewAdapter;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import com.wx.goodview.GoodView;

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

public class GrowUpDetailcopy extends BaseActivity implements zmc_EssayCommentRecycleviewAdapter.onItemClickListener {

    @BindView(R.id.recycler)
    MyRecycleView recycler;
//    @BindView(R.id.iv_hot_icon)
//    XCFlowLayout ivHotIcon;
    @BindView(R.id.addEComment)
    TextView addEComment;

    String eid;
    boolean isfocus;
    String name;
    String icon;
    @BindView(R.id.ed_add_pl)
    EditText edAddPl;
    @BindView(R.id.ngl_images)
    NineGridImageView nglImages;

    /**
     * 头部视图
     */
    @BindView(R.id.ivb_back_finsh)
    ImageButton ivbBackFinsh;
    @BindView(R.id.tv_foot_center)
    TextView tvFootCenter;

    /**
     * 内容视图
     */
    @BindView(R.id.tv_grow_content)
    TextView tvGrowContent;
    @BindView(R.id.iv_icon)
    CircleImageView ivIcon;
    @BindView(R.id.tv_grow_name)
    TextView tvGrowName;
    @BindView(R.id.tv_grow_time)
    TextView tvGrowTime;
    @BindView(R.id.tv_grow_gz_deail)
    TextView tvGrowGzDeail;
    @BindView(R.id.tv_grow_see)
    TextView getTvGrowSee;
    @BindView(R.id.tv_grow_eagrees)
    TextView tvGrowAgrees;
    @BindView(R.id.tv_grow_comment)
    TextView tvGrowComment;
    @BindView(R.id.iv_grow_def)
    ImageView dianZanImg;
    //点赞视图
    @BindView(R.id.show_zan_view)
    DianZanView dianZanView;


    /**点赞人头像**/
    List<String> isIcon = new ArrayList<String>();
    zmc_EssayCommentRecycleviewAdapter adapter;
    /**当前评论状态  true：回复   false：评论  **/
    private boolean isReply = false;
    /**回复评论的下标**/
    private int replyPosition = -1;
    /**评论列表**/
    private List<EcommentsBean> bean = new ArrayList<>();

    Gson gson;
    UserInfoLogin user ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_activity_growup_detail);
        ButterKnife.bind(this);
        gson = new Gson();
        String s = SharedPreferencesTools.GetUsearInfo(this, "userSave", "userInfo");
        user = gson.fromJson(s, UserInfoLogin.class);
        tvFootCenter.setText("详情");
        nglImages.setMaxSize(9);
        nglImages.setGap(UIUtils.dip2px(10));
        nglImages.setAdapter(mAdapter);
        dianZanView.setmListener(new DianZanView.mListener() {
            @Override
            public void onDisplayImage(Context context, ImageView imageView, String url) {
                Log.e("Test----","url = "+url);
                Picasso.with(GrowUpDetailcopy.this).load(url).config(Bitmap.Config.RGB_565).fit().placeholder(R.drawable.default_icon).into(imageView);
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        name = getIntent().getStringExtra("name");//名字
        icon = getIntent().getStringExtra("icon");//头像
        eid = getIntent().getStringExtra("eid");//说说id
        isfocus = getIntent().getBooleanExtra("isfocus", false);//是否关注
        MyLogcat.jLog().e("eid:" + eid + "/isfocus:" + isfocus);
        ImageLoader.getInstance().displayImage(icon, ivIcon);
        tvGrowName.setText(name);
        if (isfocus) {
            tvGrowGzDeail.setText("已关注");
        } else {
            tvGrowGzDeail.setText("+ 关注");
        }
        HttpData(eid);

    }

    /**
     * 用户关注
     */
    @OnClick(R.id.tv_grow_gz_deail)
    public void guanzhu(){
        if (!ToolUtils.isFastDoubleClick()) {
            if(isfocus){//已关注
                HttpDisDZ(essayBean.getUserid());
            }else{
                HttpGz(essayBean.getUserid());
            }
        }
    }

    /**
     * 用户点赞
     */
    @OnClick(R.id.iv_grow_def)
    public void dianzan(){
        if (!ToolUtils.isFastDoubleClick()) {
            if (isDianZan) {
                HttpDisDZ(eid);
            } else {
                GoodView goodView = new GoodView(this);
                goodView.setText("+1");
                goodView.show(dianZanImg);
                HttpDZ(eid);
            }
        }
    }

    /**
     * 返回键
     */
    @OnClick(R.id.ivb_back_finsh)
    public void back(){
        onBackPressed();
    }

    /**
     * 发送消息
     */
    @OnClick(R.id.addEComment)
    public void addEcomment() {
        if (!ToolUtils.isFastDoubleClick()) {
            HttpEcomment();
        }
    }

    /**
     * 请求点赞
     */
    private void HttpDZ(String eid) {
        String time = TimeUtil.currectTimess();
        RetrofitClient.getinstance(this).create(GetService.class).agreeWithEssay(eid, user.getUserid(), time).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    Log.e("dianzan","onResponse:" + response.body().toString());
                    if ("true".equals(response.body().toString())) {
                        essayBean.setEagrees(essayBean.getEagrees()+1);
                        tvGrowAgrees.setText(essayBean.getEagrees()+"");
                        isDianZan = true;
                        dianZanImg.setSelected(isDianZan);
                        //添加自己点赞头像
                        dianZanView.addImageView(user.getIcon());
                        isChange = true;
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(GrowUpDetailcopy.this,"网络连接错误，请检查网络",Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 取消点赞
     */
    private void HttpDisDZ(String eid) {
        RetrofitClient.getinstance(this).create(GetService.class).disAgreeWithEssay(eid, user.getUserid()).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    MyLogcat.jLog().e("取消点赞 onResponse:" + response.body().toString());
                    if ("true".equals(response.body().toString())) {
                        essayBean.setEagrees(essayBean.getEagrees()-1);
                        tvGrowAgrees.setText(essayBean.getEagrees()+"");
                        isDianZan = false;
                        dianZanImg.setSelected(isDianZan);
                        //去除自己点赞头像
                        dianZanView.removeImg(user.getIcon());
                        isChange = true;
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(GrowUpDetailcopy.this,"网络连接错误，请检查网络",Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 请求关注
     */
    private void HttpGz(final String focusedid) {
        RetrofitClient.getinstance(this).create(GetService.class).addMyFocus(user.getUserid(), "", focusedid, "").enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    MyLogcat.jLog().e("请求关注 onResponse:" + response.body().toString());
                    if ("true".equals(response.body().toString())) {
                        tvGrowGzDeail.setText("已关注");
                        isfocus = true;
                        isChange = true;
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
    private void HttpDelGz(final String focusedid) {
        RetrofitClient.getinstance(this).create(GetService.class).delMyFocus(user.getUserid(), focusedid).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    MyLogcat.jLog().e("取消关注 onResponse:" + response.body().toString());
                    if ("true".equals(response.body().toString())) {
                        tvGrowGzDeail.setText("+ 关注");
                        isfocus = false;
                        isChange = true;
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                MyLogcat.jLog().e("取消关注 onFailure:");
            }
        });
    }


    /**
     * 添加评论
     */
    private void HttpEcomment() {
        if(isReply){//回复
            /**
             * 回复说说评论
             * replyid:评论ID,回复的哪一条评论;ressayid:说说ID;ruserid:回复者的ID;rusername:回复者的昵称;
             * rcontent:回复内容;rtime:回复时间;ecuserid:评论者的ID;ecusername:评论者的昵称;ectime:与回复时间值相同
             */
            String replyid = bean.get(replyPosition).getEcid();
            String eressayid = eid;
            String ruserid = user.getUserid();
            String rusername = user.getName();
            String rcontent = edAddPl.getText().toString().replace(context,"");
            String rtime = ToolUtils.CurrentTime();
            String euserid = "";
            String eusername = "";
            if(bean.get(replyPosition).getReplyid().isEmpty()){//评论
                euserid = bean.get(replyPosition).getEcUser().getUserid();
                eusername = bean.get(replyPosition).getEcUser().getName();
            }else{//回复
                euserid = bean.get(replyPosition).getReplyUser().getUserid();
                eusername = bean.get(replyPosition).getReplyUser().getName();
            }

            if (StringUtils.isEmpty(rcontent)) {
                ToolUtils.showToast(GrowUpDetailcopy.this, "请填写评论内容！", Toast.LENGTH_SHORT);
                return;
            }

            edAddPl.setText("");

            RetrofitClient.getinstance(this).create(GetService.class).addReplyEComment(replyid, eressayid, ruserid, rusername, rcontent, rtime, euserid, eusername, rtime).enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if ("true".equals(response.body())) {
                        ToolUtils.showToast(GrowUpDetailcopy.this, "评论成功！", Toast.LENGTH_SHORT);
                        HttpDataPL(eid);
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    ToolUtils.showToast(GrowUpDetailcopy.this, "评论失败！", Toast.LENGTH_SHORT);
                }
            });
        }else{ //评论
            String eccontent = edAddPl.getText().toString();
            if (StringUtils.isEmpty(eccontent)) {
                ToolUtils.showToast(GrowUpDetailcopy.this, "请填写评论内容！", Toast.LENGTH_SHORT);
                return;
            }
            edAddPl.setText("");
            String ecuserid = user.getUserid();
            String ecusername = user.getName();
            String ectime = TimeUtil.currectTimess();
            String ressayid = eid;//eid

            RetrofitClient.getinstance(this).create(GetService.class).addEComment(eccontent, ecuserid, ecusername, ectime, ressayid).enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if (response.isSuccessful()) {
                        MyLogcat.jLog().e("添加: onResponse" + response.body().toString());
                        if ("true".equals(response.body())) {
                            ToolUtils.showToast(GrowUpDetailcopy.this, "评论成功！", Toast.LENGTH_SHORT);
                            HttpDataPL(eid);
                        }
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    MyLogcat.jLog().e("添加: onFailure");
                    ToolUtils.showToast(GrowUpDetailcopy.this, "评论失败！", Toast.LENGTH_SHORT);
                }
            });
        }
        isReply = false;
        ToolUtils.closeKeybord(edAddPl,this);
    }

    /**
     * 说说评论信息
     * @param eid
     */
    private void HttpDataPL(String eid){
        RetrofitClient.getinstance(this).create(GetService.class).getEComments(eid).enqueue(new Callback<List<EcommentsBean>>() {
            @Override
            public void onResponse(Call<List<EcommentsBean>> call, Response<List<EcommentsBean>> response) {
                if(response.isSuccessful()){
                    List<EcommentsBean> body = response.body();
                    showEcomments(body);
                    isChange = true;
                }
            }

            @Override
            public void onFailure(Call<List<EcommentsBean>> call, Throwable t) {
                MyLogcat.jLog().e("说说列表详情页面: onFailure");
            }
        });
    }

    /**
     * 获取说说详细信息
     * @param eid
     */
    private void HttpData(String eid) {
        RetrofitClient.getinstance(this).create(GetService.class).getEssayInfo(eid).enqueue(new Callback<EssayInfoBean>() {
            @Override
            public void onResponse(Call<EssayInfoBean> call, Response<EssayInfoBean> response) {
                if (response.isSuccessful()) {
                    Log.e("Test~~~","获取数据成功");
                    EssayInfoBean body = response.body();
                    showEssay(body.getEssay());
                    showEssayAgree(body.getEssayAgreeList());
                    showEcomments(body.getEssayCommentList());
                    isChange = true;
                }
            }

            @Override
            public void onFailure(Call<EssayInfoBean> call, Throwable t) {
                MyLogcat.jLog().e("说说列表详情页面: onFailure");
            }
        });
    }

    /**说说信息**/
    private EssayInfoBean.EssayBean essayBean;
    /**
     * 显示说说信息
     * @param bean
     */
    private void showEssay(EssayInfoBean.EssayBean bean){
        this.essayBean = bean;
        if(bean.getUserid().equals(SharedPreferencesTools.GetUsearId(this,"userSave","userId"))){
            tvGrowGzDeail.setVisibility(View.GONE);
        }else{
            tvGrowGzDeail.setVisibility(View.VISIBLE);
        }
        tvGrowContent.setText(bean.getEcontent());
        long epubtime = bean.getEpubtime();
        String sjc = TimeUtil.SJC(epubtime + "");
        tvGrowTime.setText(sjc);
        getTvGrowSee.setText("浏览"+bean.getEtimes()+"次");
        tvGrowAgrees.setText(bean.getEagrees()+"");
        tvGrowComment.setText(bean.getEcommontcount()+"");

        try {
            String[] split = bean.getEimages().split(";");
            List<String> imgUrlList = new ArrayList<>();
            for (int i = 0; i <split.length ; i++) {
                imgUrlList.add(split[i]);
            }
            nglImages.setImagesData(imgUrlList,epubtime+"");
        } catch (NullPointerException e) {
            /**
             *  此处抛出异常 我们也进行setImagesData  不过传递为空
             *  我们对imgUrlList的值进行监听  当为空我们将nineGrid进行隐藏
             *  这样可以防止滑动过快时  没有图片显示  nineGrid却为VISIBLE
             */
            nglImages.setImagesData(new ArrayList<String>(),epubtime+"");
        }
    }

    /**自己是否对当前说说点赞**/
    private boolean isDianZan = false;
    /**
     * 显示说说点赞情况
     * @param bean
     */
    private void showEssayAgree(List<EssayInfoBean.EssayAgreeListBean> bean){
        for (int i = 0; i <bean.size() ; i++) {
            try{
                isIcon.removeAll(isIcon);
                isIcon.add(bean.get(i).getUser().getIcon());
                if(bean.get(i).getUserid().equals(user.getUserid())){//自己点赞了
                    isDianZan = true;
                }
            }catch (Exception e){

            }
        }
        Log.e("Test~~~","isIcon:" + isIcon.size());
        dianZanView.setImageResources(isIcon);
        dianZanImg.setSelected(isDianZan);
    }

    /**
     * 显示评论情况
      * @param bean
     */
    private void showEcomments(List<EcommentsBean> bean){
        //评论集合
        List<EcommentsBean> commentList = new ArrayList<>();
        //回复集合
        List<EcommentsBean> replyList = new ArrayList<>();

        for (int i = 0; i < bean.size() ; i++) {
            if(bean.get(i).getReplyid().isEmpty()){
                commentList.add(bean.get(i));
            }else{
                replyList.add(bean.get(i));
            }
        }
        //对评论集合时间进行排序  由小到大
        for (int i = 0; i < commentList.size() - 1 ; i++) {
            for (int j = 0; j < commentList.size() - 1 - i ; j++) {
                if(commentList.get(j).getEctime() > commentList.get(j+1).getEctime()){
                    EcommentsBean bean1 = commentList.get(j);
                    commentList.remove(j);
                    commentList.add(j+1,bean1);
                }
            }
        }
       //将回复的消息 对应的插入到评论的下方   有可能是回复评论 也有可能是回复回复
        for (int i = 0; i < replyList.size() ; i++) {
            for (int j = 0; j < commentList.size() ; j++) {
                if(replyList.get(i).getReplyid().equals(commentList.get(j).getEcid())){
                    commentList.add(j+1,replyList.get(i));
                    //将回复评论的踢出   剩下的就是回复回复的
                    replyList.remove(i);
                    i -- ;
                    break;
                }
            }
        }
        //将回复的回复 对应插入到回复下面
        if(replyList.size() != 0){
            for (int i = 0; i < replyList.size(); i++) {
                for (int j = 0; j < commentList.size() ; j++) {
                    if(replyList.get(i).getReplyid().equals(commentList.get(j).getEcid())){
                        commentList.add(j+1,replyList.get(i));
                    }
                }
            }
        }
        this.bean = commentList;
        showRecycler(commentList);
    }

    public void showRecycler(List<EcommentsBean> data) {
        if (adapter == null) {
            adapter = new zmc_EssayCommentRecycleviewAdapter(data,this);
            adapter.setListener(this);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
            recycler.setLayoutManager(linearLayoutManager);
            recycler.setAdapter(adapter);
        } else {
            adapter.Update(data);
        }

    }
    private NineGridImageViewAdapter<String> mAdapter = new NineGridImageViewAdapter<String>() {

        @Override
        protected void onHideView(NineGridImageView<String> view) {
            Log.e("myBind","onHideView ");
            view.setVisibility(View.GONE);
        }

        @Override
        protected void onDisplayImage(Context context, ImageView imageView, String s, String tag, NineGridImageView<String> view) {
            imageView.setScaleType(ImageView.ScaleType.CENTER);
            Picasso.with(context).load(s).config(Bitmap.Config.RGB_565).fit().placeholder(R.drawable.default_icon).into(imageView);
        }

        @Override
        protected ImageView generateImageView(Context context) {
            return super.generateImageView(context);
        }

        @Override
        protected void onItemImageClick(Context context, ImageView imageView, int index, List<String> list) {
            //图片点击查看处理
            imageBrower(index, (ArrayList<String>) list);
        }
    };

    /**
     * 打开图片查看器
     *
     * @param position
     * @param urls
     */
    protected void imageBrower(int position, ArrayList<String> urls) {
        Intent intent = new Intent(this, ImagePagerActivity.class);
        intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_URLS, urls);
        intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_INDEX, position);
        this.startActivity(intent);
    }

    /**回复者文本**/
    String context = "";
    @Override
    public void onItemLongClick(View v, int position) {
        // 长按回复
        isReply = true;
        if(bean.get(position).getReplyid().isEmpty()){//评论内容
            context = "回复 @"+bean.get(position).getEcUser().getName()+" : ";
        }else{//回复内容
            context = "回复 @"+bean.get(position).getReplyUser().getName()+" : ";
        }
        replyPosition = position;
        ToolUtils.openKeyBord(edAddPl,this);
        edAddPl.setText(context);
        edAddPl.setSelection(context.length());
    }

    /**当前界面是否进行了数据修改**/
    private boolean isChange = false;
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(isChange){
            setResult(RESULT_OK);
        }
    }
}

