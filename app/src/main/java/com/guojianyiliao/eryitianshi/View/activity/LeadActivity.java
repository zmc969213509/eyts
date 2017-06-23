package com.guojianyiliao.eryitianshi.View.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.guojianyiliao.eryitianshi.MyUtils.utlis.SharedPreferencesTools;
import com.guojianyiliao.eryitianshi.R;
import com.guojianyiliao.eryitianshi.page.adapter.LeadAdapter;

import cn.jpush.android.api.JPushInterface;

public class LeadActivity extends AppCompatActivity {
    private ViewPager viewpager;
    private Button tv_click;
    private LeadAdapter adapter;
    private ImageView iv_lead_spot1, iv_lead_spot2, iv_lead_spot3, iv_lead_spot4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        setContentView(R.layout.activity_lead);

        try {
            boolean isFirst = SharedPreferencesTools.GetUsearisFirst(this, "userSave", "userLogin");
            if (!isFirst) {
                Intent intent1 = new Intent(LeadActivity.this, LogActivity.class);
                startActivity(intent1);
                finish();
            } else {
                btclick();
                imageDate();
            }
            fidView();


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void fidView() {
        iv_lead_spot1 = (ImageView) findViewById(R.id.iv_lead_spot1);
        iv_lead_spot2 = (ImageView) findViewById(R.id.iv_lead_spot2);
        iv_lead_spot3 = (ImageView) findViewById(R.id.iv_lead_spot3);
        iv_lead_spot4 = (ImageView) findViewById(R.id.iv_lead_spot4);


    }

    private ViewPager.OnPageChangeListener listener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            tv_click.setVisibility(View.INVISIBLE);
            if (position == 3) {
                tv_click.setVisibility(View.VISIBLE);
                iv_lead_spot1.setImageResource(R.drawable.lead_slide_sign1);
                iv_lead_spot2.setImageResource(R.drawable.lead_slide_sign1);
                iv_lead_spot4.setImageResource(R.drawable.lead_slide_sign);
                iv_lead_spot3.setImageResource(R.drawable.lead_slide_sign1);
            } else if (position == 0) {
                iv_lead_spot1.setImageResource(R.drawable.lead_slide_sign);
                iv_lead_spot2.setImageResource(R.drawable.lead_slide_sign1);
                iv_lead_spot4.setImageResource(R.drawable.lead_slide_sign1);
                iv_lead_spot3.setImageResource(R.drawable.lead_slide_sign1);
            } else if (position == 1) {
                iv_lead_spot1.setImageResource(R.drawable.lead_slide_sign1);
                iv_lead_spot2.setImageResource(R.drawable.lead_slide_sign);
                iv_lead_spot4.setImageResource(R.drawable.lead_slide_sign1);
                iv_lead_spot3.setImageResource(R.drawable.lead_slide_sign1);
            } else if (position == 2) {
                iv_lead_spot1.setImageResource(R.drawable.lead_slide_sign1);
                iv_lead_spot2.setImageResource(R.drawable.lead_slide_sign1);
                iv_lead_spot4.setImageResource(R.drawable.lead_slide_sign1);
                iv_lead_spot3.setImageResource(R.drawable.lead_slide_sign);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    };

    //
    private void imageDate() {
        viewpager = (ViewPager) findViewById(R.id.viewpager);
        adapter = new LeadAdapter();
        viewpager.setAdapter(adapter);
        viewpager.addOnPageChangeListener(listener);
        ImageView imageView = null;
        imageView = (ImageView) View.inflate(this, R.layout.activity_lead_item, null);
        imageView.setImageResource(R.drawable.lead_item1);
        adapter.addim(imageView);
        imageView = (ImageView) View.inflate(this, R.layout.activity_lead_item, null);
        imageView.setImageResource(R.drawable.lead_item2);
        adapter.addim(imageView);
        imageView = (ImageView) View.inflate(this, R.layout.activity_lead_item, null);
        imageView.setImageResource(R.drawable.lead_item3);
        adapter.addim(imageView);
        imageView = (ImageView) View.inflate(this, R.layout.activity_lead_item, null);
        imageView.setImageResource(R.drawable.lead_item4);
        adapter.addim(imageView);
        adapter.notifyDataSetChanged();
    }

    //
    private void btclick() {
        tv_click = (Button) findViewById(R.id.tv_click);
        tv_click.setVisibility(View.INVISIBLE);
        tv_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferencesTools.UserLogin(LeadActivity.this, "userSave", "userLogin");
//                Intent intent = new Intent(LeadActivity.this, LogActivity.class);
                Intent intent = new Intent(LeadActivity.this, LoginSelectActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }


    @Override
    protected void onPause() {
        super.onPause();
        JPushInterface.onPause(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        JPushInterface.onResume(this);
    }


}
