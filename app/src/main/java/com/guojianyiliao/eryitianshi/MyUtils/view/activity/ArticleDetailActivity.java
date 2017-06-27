package com.guojianyiliao.eryitianshi.MyUtils.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.guojianyiliao.eryitianshi.Data.Http_data;
import com.guojianyiliao.eryitianshi.MyUtils.bean.UserInfoLogin;
import com.guojianyiliao.eryitianshi.MyUtils.bean.zmc_ArtDetail;
import com.guojianyiliao.eryitianshi.MyUtils.interfaceservice.GetService;
import com.guojianyiliao.eryitianshi.MyUtils.manager.RetrofitClient;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.DateUtils;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.MyLogcat;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.SharedPreferencesTools;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.SpUtils;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.StringUtils;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.ToolUtils;
import com.guojianyiliao.eryitianshi.R;
import com.guojianyiliao.eryitianshi.View.activity.LoginSelectActivity;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMVideo;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.media.UMusic;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by zmc on 2017/5/27.
 * 文章详情界面
 */

public class ArticleDetailActivity extends Activity{

    @BindView(R.id.article_webview)
    WebView webView;
    @BindView(R.id.article_collection)
    ImageView imgCollection;

    private String cyclopediaid;
    private String site;
    private String content;
    private String title;
    String icon;
    Bitmap bitmap;
    /**当前文章是否收藏**/
    private boolean isCollection = false;

    String url = "";
    private UserInfoLogin user;
    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zmc_activity_article_detail);
        ButterKnife.bind(this);
        cyclopediaid = getIntent().getStringExtra("cyclopediaid");
        site = getIntent().getStringExtra("site");
        content = getIntent().getStringExtra("content");
        title = getIntent().getStringExtra("title");
        icon = getIntent().getStringExtra("icon");
        bitmap = ImageLoader.getInstance().loadImageSync(icon);
        if(TextUtils.isEmpty(title) && TextUtils.isEmpty(content)){
            getArtDetail();
        }
        loading();
    }

    /**
     * 获取文章详情
     */
    private void getArtDetail() {
        RetrofitClient.getinstance(this).create(GetService.class).getArtDetail(cyclopediaid).enqueue(new Callback<zmc_ArtDetail>() {
            @Override
            public void onResponse(retrofit2.Call<zmc_ArtDetail> call, Response<zmc_ArtDetail> response) {
                if(response.isSuccessful()){
                    zmc_ArtDetail body = response.body();
                    content = body.getContent();
                    title = body.getTitle();
                }
            }

            @Override
            public void onFailure(retrofit2.Call<zmc_ArtDetail> call, Throwable t) {

            }
        });
    }

    private void loading() {
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setBuiltInZoomControls(true);
        url = Http_data.http_data+site+"?id="+cyclopediaid;
        webView.loadUrl(url);
        getisColl();
    }

    /**
     * 获取当前文章是否收藏
     * @return
     */
    public void getisColl() {

        gson = new Gson();
        String json = SharedPreferencesTools.GetUsearInfo(this, "userSave", "userInfo");
        user = gson.fromJson(json,UserInfoLogin.class);


        OkHttpUtils.post().url(Http_data.http_data + "collect/isCollected")
                .addParams("userId", user.getUserid())
                .addParams("cyclId", cyclopediaid)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id){
            }

            @Override
            public void onResponse(String response, int id) {
                if(response.equals("true")){
                    isCollection = true;
                }
                imgCollection.setSelected(isCollection);
            }
        });
    }

    /**
     * 返回键
     */
    @OnClick(R.id.article_back)
    public void back(){
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        Log.e("onActivityResult","isCollection = "+isCollection);
        if(!isCollection){
            setResult(RESULT_OK);
            Log.e("onActivityResult","setResult ");
        }
        super.onBackPressed();
    }

    /**
     * 收藏或者取消收藏
     */
    @OnClick(R.id.article_collection)
    public void collection() {
        if (ToolUtils.isFastDoubleClick()) {

        } else {
            if (isCollection) {//当前收藏了
                OkHttpUtils.post().url(Http_data.http_data + "collect/cancleCollect")
                        .addParams("userId", user.getUserid())
                        .addParams("cyclId", cyclopediaid)
                        .build().execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        if (response.equals("true")) {
                            isCollection = false;
                        }
                        imgCollection.setSelected(isCollection);
                    }
                });
            } else {//当前没有收藏
                Date date = new Date();
                String dateTimeByMillisecond = DateUtils.getDateTimeByMillisecond(date.getTime() + "", "yyyy-MM-dd HH:mm:ss");
                OkHttpUtils.post().url(Http_data.http_data + "collect/collectCycl")
                        .addParams("userid", user.getUserid())
                        .addParams("cyclopediaid", cyclopediaid)
                        .addParams("ctime", dateTimeByMillisecond)
                        .build().execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        if (response.equals("true")) {
                            isCollection = true;
                        }
                        imgCollection.setSelected(isCollection);
                    }
                });
            }
        }
    }

    /**
     * 分享
     */
    @OnClick(R.id.article_share)
    public void share() {
        final SHARE_MEDIA[] displaylist = new SHARE_MEDIA[]
                    {
                            SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE,
                            SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE, SHARE_MEDIA.SINA
                    };
        //分享成功后显示的图片
        UMImage image = new UMImage(ArticleDetailActivity.this,bitmap);
        //分享的视频
        UMVideo video = new UMVideo("http://video.sina.com.cn/p/sports/cba/v/2013-10-22/144463050817.html");
        //分享的音乐
        UMusic music = new UMusic("http://music.huoxing.com/upload/20130330/1364651263157_1085.mp3");
        music.setTitle(title);
        music.setThumb(new UMImage(ArticleDetailActivity.this, "http://www.umeng.com/images/pic/social/chart_1.png"));

        UMWeb web = new UMWeb(url);
        web.setTitle(title);
        web.setDescription(content);
        web.setThumb(image);

        new ShareAction(this).setDisplayList(displaylist)
                .withMedia(web)
                .setListenerList(new umShareListener())
                .open();
    }

    class umShareListener implements UMShareListener {

        @Override
        public void onCancel(SHARE_MEDIA arg0) {
            // TODO Auto-generated method stub
        }

        @Override
        public void onError(SHARE_MEDIA arg0, Throwable arg1) {
            // TODO Auto-generated method stub
            Log.e("ArticleDetailActivity",arg1.getMessage());
            Toast.makeText(ArticleDetailActivity.this," 分享失败啦", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onStart(SHARE_MEDIA share_media) {

        }

        @Override
        public void onResult(SHARE_MEDIA arg0) {
            // TODO Auto-generated method stub
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get( this ).onActivityResult( requestCode, resultCode, data);
    }

}
