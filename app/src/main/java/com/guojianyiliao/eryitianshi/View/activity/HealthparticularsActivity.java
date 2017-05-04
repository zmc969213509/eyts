package com.guojianyiliao.eryitianshi.View.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.guojianyiliao.eryitianshi.Data.Http_data;
import com.guojianyiliao.eryitianshi.Data.entity.GetcommentBean;
import com.guojianyiliao.eryitianshi.R;
import com.guojianyiliao.eryitianshi.Utils.IListener;
import com.guojianyiliao.eryitianshi.Utils.ListenerManager;
import com.guojianyiliao.eryitianshi.page.adapter.GetCommentAdapter;
import com.guojianyiliao.eryitianshi.page.view.ListViewForScrollView;
import com.guojianyiliao.eryitianshi.page.view.WordWrapView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import okhttp3.Call;

public class HealthparticularsActivity extends AppCompatActivity implements IListener {
    private String time;
    private String tag;
    private String content;
    private String healthId;
    private TextView tv_content, tv_time;
    private LinearLayout ll_health_return, ll_healthcondition_delete;
    private WordWrapView wordWrapView;
    private ListViewForScrollView lv_getcomment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_healthparticulars);
        try {


            ListenerManager.getInstance().registerListtener(this);
            findView();
            init();
            commenInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
     private List<GetcommentBean> list;
    private void commenInit() {
        OkHttpUtils
                .get()
                .url(Http_data.http_data + "/getComment")
                .addParams("healthId", healthId)
                .build()

                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new Gson();
                       list = new ArrayList<>();
                        Type listtype = new TypeToken<LinkedList<GetcommentBean>>() {
                        }.getType();
                        LinkedList<GetcommentBean> leclist = gson.fromJson(response, listtype);
                        for (Iterator it = leclist.iterator(); it.hasNext(); ) {
                            GetcommentBean doctorcomment = (GetcommentBean) it.next();
                            list.add(doctorcomment);
                        }
                        GetCommentAdapter adapter = new GetCommentAdapter(HealthparticularsActivity.this, list);
                        lv_getcomment.setAdapter(adapter);
                        adapter.notifyDataSetChanged();

                    }

                });
    }


    private void findView() {
        time = getIntent().getStringExtra("time");
        tag = getIntent().getStringExtra("tag");
        content = getIntent().getStringExtra("content");
        healthId = getIntent().getStringExtra("healthId");
        ll_health_return = (LinearLayout) findViewById(R.id.ll_health_return);
        tv_content = (TextView) findViewById(R.id.tv_content);
        tv_time = (TextView) findViewById(R.id.tv_time);
        wordWrapView = (WordWrapView) findViewById(R.id.wordWrapView);
        ll_healthcondition_delete = (LinearLayout) findViewById(R.id.ll_healthcondition_delete);
        lv_getcomment = (ListViewForScrollView) findViewById(R.id.lv_getcomment);
        lv_getcomment.setVerticalScrollBarEnabled(false);
        tv_time.setText(time);
        tv_content.setText(content);
        ll_health_return.setOnClickListener(listner);
        try {
            ll_healthcondition_delete.setOnClickListener(deltetlistener);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void init() {

        String[] strArray = null;

        strArray = tag.split(",");
        List<String> wordList = Arrays.asList(strArray);



        for (int i = 0; i < wordList.size(); i++) {
            View view = View.inflate(this, R.layout.health_page_disease_item, null);
            LinearLayout ll_heath_disease_case = (LinearLayout) view.findViewById(R.id.ll_heath_disease_case);
            TextView tv_heath_disease_case = (TextView) view.findViewById(R.id.tv_heath_disease_case);


            if (wordList.get(i).trim().equals("流涕") || wordList.get(i).trim().equals("咳嗽") || wordList.get(i).trim().equals("头疼")) {
                ll_heath_disease_case.setBackgroundResource(R.drawable.healthcondition_rbtn_unmep_greencase);
                tv_heath_disease_case.setText(wordList.get(i));
                tv_heath_disease_case.setTextColor(android.graphics.Color.parseColor("#a6cd57"));

            } else if (wordList.get(i).trim().equals("发热") || wordList.get(i).trim().equals("口腔溃疡") || wordList.get(i).trim().equals("口干口渴") || wordList.get(i).trim().equals("鼻塞")) {
                ll_heath_disease_case.setBackgroundResource(R.drawable.healthcondition_rbtn_unmep_yellowcase);
                tv_heath_disease_case.setText(wordList.get(i));
                tv_heath_disease_case.setTextColor(android.graphics.Color.parseColor("#ebc668"));


            } else {
                ll_heath_disease_case.setBackgroundResource(R.drawable.healthcondition_rbtn_unmep_redcase);
                tv_heath_disease_case.setText(wordList.get(i));
                tv_heath_disease_case.setTextColor(android.graphics.Color.parseColor("#eb686c"));

            }

            wordWrapView.addView(view);
        }
    }

    private View.OnClickListener listner = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };


    private View.OnClickListener deltetlistener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            OkHttpUtils
                    .post()
                    .url(Http_data.http_data + "/DeleteHealthById")
                    .addParams("healthId", healthId)
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            Toast.makeText(HealthparticularsActivity.this, "网络连接失败", Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onResponse(String response, int id) {
                            if (response.equals("0")) {

                                ListenerManager.getInstance().sendBroadCast("更新日历页面");
                                Toast.makeText(HealthparticularsActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(HealthparticularsActivity.this, "删除失败", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

        }
    };


    @Override
    public void notifyAllActivity(String str) {

    }
}
