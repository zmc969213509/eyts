package com.guojianyiliao.eryitianshi.MyUtils.view.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.guojianyiliao.eryitianshi.MyUtils.adaper.zmc_GrowUpRecyclerviewAdapter;
import com.guojianyiliao.eryitianshi.MyUtils.adaper.zmc_UserPublicRecycleviewAdapter;
import com.guojianyiliao.eryitianshi.MyUtils.bean.UserEssaies;
import com.guojianyiliao.eryitianshi.MyUtils.interfaceservice.GetService;
import com.guojianyiliao.eryitianshi.MyUtils.manager.RetrofitClient;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.MyLogcat;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.SharedPreferencesTools;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.SpUtils;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.UIUtils;
import com.guojianyiliao.eryitianshi.R;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.guojianyiliao.eryitianshi.R.id.recyclerView;

/**
 * description: 用户发布的说说界面
 * autour: jnluo jnluo5889@126.com
 * date: 2017/4/28 11:53
 * update: 2017/4/28
 * version: Administrator
 */

public class MyPublishedActivity extends AppCompatActivity implements zmc_UserPublicRecycleviewAdapter.onItemClickListener {

    @BindView(R.id.iv_publish_src)
    ImageView ivPublishSrc;
    @BindView(R.id.iv_publish_back)
    ImageView ivPublishBack;
    @BindView(R.id.iv_publish_icon)
    ImageView ivPublishIcon;
    @BindView(R.id.iv_publish_name)
    TextView ivPublishName;
    @BindView(R.id.recycler_publish)
    RecyclerView recyclerPublish;
    @BindView(R.id.swlayout)
    SwipeRefreshLayout swipeRefreshLayout;

    /**用户发布说说数据集合**/
    List<UserEssaies> data = new ArrayList<>();
    /**查看说说页码**/
    String pageNum = "0";
    /**上个activity用户id**/
    String userid;
    /**当前用户自己的id**/
    String uuserid_my;
    /**当前查看的用户说说是否是属于自己**/
    private boolean isMe;
    /**当前用户的头像url**/
    String icon_url;
    /**用户名字**/
    String name;
    zmc_UserPublicRecycleviewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_activity_mypublished);
        ButterKnife.bind(this);
        userid = getIntent().getStringExtra("uuerid");
        name = getIntent().getStringExtra("name");
        icon_url = getIntent().getStringExtra("icon");
        uuserid_my = SharedPreferencesTools.GetUsearId(this,"userSave","userId");
        if(uuserid_my.equals(userid)){
            isMe = true;

        }else{
            isMe = false;
        }
        Picasso.with(this).load(icon_url).config(Bitmap.Config.RGB_565).fit().placeholder(R.drawable.default_icon).into(ivPublishIcon);
        ivPublishName.setText(name+"");
        httpData(userid);
        swipeRefreshLayout.setColorSchemeResources(R.color.backgroundblue,R.color.view);
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                httpData(userid);
                swipeRefreshLayout.setRefreshing(true);
            }
        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                httpData(userid);
            }
        });
    }

    private void httpData(String userid) {

        RetrofitClient.getinstance(this).create(GetService.class).getUserEssaies(userid, pageNum).enqueue(new Callback<List<UserEssaies>>() {
            @Override
            public void onResponse(Call<List<UserEssaies>> call, Response<List<UserEssaies>> response) {
                if (response.isSuccessful()) {
                    MyLogcat.jLog().e("我发布的说说：");
                    if(data.size() != 0){
                        data.clear();
                    }
                    data = response.body();
                    if(data.size() == 0){
                        swipeRefreshLayout.setRefreshing(false);
                        Toast.makeText(MyPublishedActivity.this,"暂无数据",Toast.LENGTH_SHORT).show();
                    }else{
                        swipeRefreshLayout.setRefreshing(false);
                        showRecycleView();
                        Toast.makeText(MyPublishedActivity.this,"刷新成功",Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<UserEssaies>> call, Throwable t) {
                MyLogcat.jLog().e("我发布的说说：onFailure" + t);
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(MyPublishedActivity.this,"获取数据失败，请检查网络",Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 显示recyleview
     */
    private void showRecycleView() {
        if (adapter == null) {
            GridLayoutManager manager = new GridLayoutManager(this,1,GridLayoutManager.VERTICAL,false);
            recyclerPublish.setLayoutManager(manager);
            if(isMe){
                data.add(0,new UserEssaies());
                adapter = new zmc_UserPublicRecycleviewAdapter(data,this,0);
            }else{
                adapter = new zmc_UserPublicRecycleviewAdapter(data,this,1);
            }
            recyclerPublish.setAdapter(adapter);
            adapter.setListener(this);
            MyLogcat.jLog().e("showList：" + data.size());
        } else {
            if(isMe){
                data.add(0,new UserEssaies());
            }
            adapter.Update(data);
            MyLogcat.jLog().e("showList：notifyDataSetChanged" );
        }

    }

    @Override
    public void onItemClick(View v, int position) {
        //跳转详情页
        Intent intent = new Intent(this, GrowUpDetailcopy.class);
        intent.putExtra("eid", data.get(position).getEid());
        intent.putExtra("icon", icon_url);
        intent.putExtra("name", name);
        if(isMe){

        }else{
            intent.putExtra("isfocus", true);
        }
        startActivity(intent);
    }

    @Override
    public void addImgClick() {
        //跳转发布说说界面
        startActivity(new Intent(this, PublishedActivity.class));
    }

    /**
     * 图片预览，兼容6.0动态权限
     */
    //@AfterPermissionGranted(REQUEST_CODE_PERMISSION_PHOTO_PREVIEW)
//    private void photoPreviewWrapper() {
//        if (mCurrentClickNpl == null) {
//            return;
//        }
//
//        // 保存图片的目录，改成你自己要保存图片的目录。如果不传递该参数的话就不会显示右上角的保存按钮
//        File downloadDir = new File(Environment.getExternalStorageDirectory(), "BGAPhotoPickerDownload");
//
//        String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
//        if (EasyPermissions.hasPermissions(this, perms)) {
//            if (mCurrentClickNpl.getItemCount() == 1) {
//                // 预览单张图片
//
//                startActivity(BGAPhotoPreviewActivity.newIntent(this, null, mCurrentClickNpl.getCurrentClickItem()));
//            } else if (mCurrentClickNpl.getItemCount() > 1) {
//                // 预览多张图片
//
//                startActivity(BGAPhotoPreviewActivity.newIntent(this, null, mCurrentClickNpl.getData(), mCurrentClickNpl.getCurrentClickItemPosition()));
//            }
//        } else {
//            // EasyPermissions.requestPermissions(this, "图片预览需要以下权限:\n\n1.访问设备上的照片", REQUEST_CODE_PERMISSION_PHOTO_PREVIEW, perms);
//        }
//    }


}
