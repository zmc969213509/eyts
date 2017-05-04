package com.guojianyiliao.eryitianshi.Utils;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;

import com.guojianyiliao.eryitianshi.View.activity.LogActivity;

/**
 * Created by Administrator on 2016/9/28 0028.
 */
public abstract class MyBaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);/**screen_orientation_portrait*/
        super.onCreate(savedInstanceState);

        if (null != savedInstanceState) {

            Intent intent = new Intent(MyBaseActivity.this, LogActivity.class);
            startActivity(intent);
        }

    }

}
