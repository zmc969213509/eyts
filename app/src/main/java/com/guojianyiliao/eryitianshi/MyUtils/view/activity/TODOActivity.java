package com.guojianyiliao.eryitianshi.MyUtils.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.guojianyiliao.eryitianshi.R;

/**
 * Created by zmc on 2017/6/10.
 * 待完成页面
 */

public class TODOActivity extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zmc_activity_todo);
        findViewById(R.id.todo_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }



}
