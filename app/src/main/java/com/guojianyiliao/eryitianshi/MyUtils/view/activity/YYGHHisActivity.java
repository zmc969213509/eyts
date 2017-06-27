package com.guojianyiliao.eryitianshi.MyUtils.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.guojianyiliao.eryitianshi.MyUtils.bean.zmc_UserGHInfo;
import com.guojianyiliao.eryitianshi.MyUtils.interfaceservice.GetService;
import com.guojianyiliao.eryitianshi.MyUtils.manager.RetrofitClient;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.SharedPreferencesTools;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.TimeUtil;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.rocketAnimLoadingUtil;
import com.guojianyiliao.eryitianshi.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by zmc on 2017/6/19.
 * 预约挂号历史
 */

public class YYGHHisActivity extends AppCompatActivity implements rocketAnimLoadingUtil.Listener, AdapterView.OnItemClickListener {

    private static final String TAG = "YYGHHisActivity";
    private ImageView back_btn;
    private ListView listView;
    List<zmc_UserGHInfo> data;
    MyAdapter adapter;

    View animView;
    rocketAnimLoadingUtil animLoadingUtil;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zmc_activity_yygh_h);

        listView = (ListView) findViewById(R.id.yygh_h_listview);
        back_btn = (ImageView) findViewById(R.id.yygh_h_back);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        animView = findViewById(R.id.loadig_anim_view);
        animLoadingUtil = new rocketAnimLoadingUtil(animView);
        animLoadingUtil.setListener(this);
        getData();
    }

    /**
     * 获取用户数据
     */
    private void getData() {
        animLoadingUtil.startAnim();
        RetrofitClient.getinstance(this).create(GetService.class).getReservation(SharedPreferencesTools.GetUsearId(this,"userSave","userId")).enqueue(new Callback<List<zmc_UserGHInfo>>() {
            @Override
            public void onResponse(Call<List<zmc_UserGHInfo>> call, Response<List<zmc_UserGHInfo>> response) {
                if(response.isSuccessful()){
                    animLoadingUtil.loadSucc();
                    data = response.body();
                    showList();
                }
            }

            @Override
            public void onFailure(Call<List<zmc_UserGHInfo>> call, Throwable t) {
                animLoadingUtil.loadFail();
            }
        });
    }

    public void showList(){
        if(adapter == null){
            adapter = new MyAdapter(data);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(this);
        }else{
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onAnimClick() {
        try {
            if(data.size() == 0){
                getData();
            }
        }catch (NullPointerException e){
            getData();
        }
    }

    /**当前点中的预约信息位置**/
    private int currentSelect = -1;
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        currentSelect = position;
        Intent intent = new Intent(YYGHHisActivity.this, YYGHInfoActivity.class);
        intent.putExtra("userGHInfo",data.get(position));
        intent.putExtra("flag",1);
        startActivityForResult(intent,0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0 && resultCode == RESULT_OK){//需要更新ui
            this.data.get(currentSelect).setIscommented(1);
            adapter.updateView(currentSelect,listView,this.data);
        }
    }

    /**
     * 适配器
     */
    class MyAdapter extends BaseAdapter{

        List<zmc_UserGHInfo>  list;

        public MyAdapter(List<zmc_UserGHInfo> list) {
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder vh ;
            if(convertView == null){
                convertView = LayoutInflater.from(YYGHHisActivity.this).inflate(R.layout.zmc_item_yygh,null);
                vh = new ViewHolder();
                vh.findView(convertView);
                convertView.setTag(vh);
            }else{
                vh = (ViewHolder) convertView.getTag();
            }
            zmc_UserGHInfo zmc_userGHInfo = data.get(position);
            vh.doc_tv.setText("预约医生："+zmc_userGHInfo.getDocname());
            vh.keshi_tv.setText(zmc_userGHInfo.getSection());

            String status = zmc_userGHInfo.getStatus();
            try{
                String reservationdate = zmc_userGHInfo.getReservationdate();
                Log.e(TAG,"reservationdate = " + reservationdate);
                long currentTime = System.currentTimeMillis();
                long reservationTime = Long.parseLong(reservationdate);
                String s = TimeUtil.currectTime(reservationTime);
                String s1 = TimeUtil.currectTime(reservationTime + (30 * 60 * 1000));
                s = s + " - "+s1.substring(11);
                reservationTime += 30 * 60 * 1000;

                //确定预约时段
                long Reservationdate = Long.parseLong(zmc_userGHInfo.getReservationdate());
                String currectTime = TimeUtil.currectTime(Reservationdate);

                if(status.equals("2")){ //已取消
                    vh.status_img.setImageResource(R.drawable.cancle_icon);
                }else if(status.equals("0")){ //未确认
                    vh.status_img.setImageResource(R.drawable.nosure_icon);
                }else if(status.equals("1")){ //已确认
                    if(currentTime > reservationTime){
                        int iscommented = zmc_userGHInfo.getIscommented();
                        if(iscommented == 0){ //未评价
                            vh.status_img.setImageResource(R.drawable.add_comment);
                        }else if(iscommented == 1){ //已评价
                            vh.status_img.setImageResource(R.drawable.past_icon);
                        }
                    }else{
                        vh.status_img.setImageResource(R.drawable.nosee_icon);
                    }
                }
                vh.time_tv.setText("预约时间："+ s);
            }catch (Exception e){
                Log.e(TAG,"e : " + e.getMessage());
            }
            return convertView;
        }

        class ViewHolder {
            TextView keshi_tv;
            TextView time_tv;
            TextView doc_tv;
            ImageView status_img;

            public void findView(View view){
                keshi_tv = (TextView) view.findViewById(R.id.item_yygh_keshi);
                time_tv = (TextView) view.findViewById(R.id.item_yygh_time);
                doc_tv = (TextView) view.findViewById(R.id.item_ttgh_docname);
                status_img = (ImageView) view.findViewById(R.id.item_yygh_status);
            }
        }

        /**
         * 更新单个item
         */
        public void updateView(int itemIndex,ListView listView,List<zmc_UserGHInfo>  list) {
            this.list = list;
            //得到第一个可显示控件的位置，
            int visiblePosition = listView.getFirstVisiblePosition();
            //只有当要更新的view在可见的位置时才更新，不可见时，跳过不更新
            if (itemIndex - visiblePosition >= 0) {
                //得到要更新的item的view
                View view = listView.getChildAt(itemIndex - visiblePosition);
                //从view中取得holder
                ViewHolder holder = (ViewHolder) view.getTag();
                holder.status_img.setImageResource(R.drawable.past_icon);
            }
        }
    }
}