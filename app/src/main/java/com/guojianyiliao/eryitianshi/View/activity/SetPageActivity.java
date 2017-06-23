package com.guojianyiliao.eryitianshi.View.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.guojianyiliao.eryitianshi.MyUtils.bean.UserInfoLogin;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.SharedPreferencesTools;
import com.guojianyiliao.eryitianshi.MyUtils.view.activity.HomeAcitivtyMy;
import com.guojianyiliao.eryitianshi.R;
import com.guojianyiliao.eryitianshi.Utils.MyBaseActivity;

import cn.jpush.im.android.api.JMessageClient;

/**
 *
 */
public class SetPageActivity extends MyBaseActivity implements View.OnClickListener {
    private RelativeLayout rl_themese, rl_feedback, rl_aboutus, rl_remidset, rl_setpass, rl_exit;
    private ImageView ll_return;
    TextView tv_pass_set;

    Gson gson;
    UserInfoLogin user ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_page);

        gson = new Gson();
        String s = SharedPreferencesTools.GetUsearInfo(this, "userSave", "userInfo");
        user = gson.fromJson(s, UserInfoLogin.class);

        pas = user.getPassword();

        try {
            findView();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    String pas;

    private void findView() {
        rl_feedback = (RelativeLayout) findViewById(R.id.rl_feedback);
        rl_aboutus = (RelativeLayout) findViewById(R.id.rl_aboutus);
        rl_remidset = (RelativeLayout) findViewById(R.id.rl_remidset);
        rl_setpass = (RelativeLayout) findViewById(R.id.rl_setpass);
        ll_return = (ImageView) findViewById(R.id.ivb_back_finsh);
        rl_exit = (RelativeLayout) findViewById(R.id.rl_exit);
        tv_pass_set = (TextView) findViewById(R.id.tv_pass_set);
        ll_return.setOnClickListener(this);
        rl_exit.setOnClickListener(this);
        rl_feedback.setOnClickListener(this);
        rl_aboutus.setOnClickListener(this);
        rl_remidset.setOnClickListener(this);
        rl_setpass.setOnClickListener(this);

        if (pas.isEmpty()) {
            tv_pass_set.setText("密码修改");
        } else {
            tv_pass_set.setText("密码设置");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_feedback:
                Intent intent1 = new Intent(SetPageActivity.this, FeedbackActivity.class);
                startActivity(intent1);
                break;
            case R.id.rl_aboutus:
                Intent intent2 = new Intent(SetPageActivity.this, AboutusActivity.class);
                startActivity(intent2);
                break;
            case R.id.rl_remidset:
                Intent intent3 = new Intent(SetPageActivity.this, RemindsetActivity.class);
                startActivity(intent3);
                break;
            case R.id.rl_setpass:
                Intent intent4 = new Intent(SetPageActivity.this, PasswordSettingActivity.class);
                startActivity(intent4);
                break;
            case R.id.rl_exit:
                JMessageClient.logout();
                Intent intent5 = new Intent(SetPageActivity.this, LoginSelectActivity.class);
                startActivity(intent5);
                Toast.makeText(SetPageActivity.this, "请重新登录", Toast.LENGTH_SHORT).show();
                SharedPreferencesTools.SaveUserInfo(this, "userSave", "userInfo","");
                SharedPreferencesTools.SaveLoginStatus(this,"userSave","userLoginStatus",false);
                HomeAcitivtyMy.homeAcitivtyMy.finish();
                finish();
                break;
            case R.id.ll_return:
                finish();
                break;
        }
    }
}
