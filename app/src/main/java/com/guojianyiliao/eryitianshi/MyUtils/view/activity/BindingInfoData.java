package com.guojianyiliao.eryitianshi.MyUtils.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.guojianyiliao.eryitianshi.Data.Http_data;
import com.guojianyiliao.eryitianshi.MyUtils.base.BaseActivity;
import com.guojianyiliao.eryitianshi.MyUtils.bean.UserInfoLogin;
import com.guojianyiliao.eryitianshi.MyUtils.interfaceservice.GetService;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.MyLogcat;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.SharedPreferencesTools;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.SpUtils;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.StringUtils;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.ToolUtils;
import com.guojianyiliao.eryitianshi.R;
import com.guojianyiliao.eryitianshi.View.activity.LoginSelectActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 第一次登录，绑定用户信息界面
 * jnluo，jnluo5889@126.com
 */
public class BindingInfoData extends BaseActivity {
    @BindView(R.id.ivb_back_finsh)
    ImageButton ivbBackFinsh;
    @BindView(R.id.tv_foot_center)
    TextView tvFootCenter;
    @BindView(R.id.ed_binding_name)
    EditText edBindingName;
    @BindView(R.id.iv_gender_man)
    ImageView ivGenderMan;
    @BindView(R.id.iv_gender_woman)
    ImageView ivGenderWoman;
    @BindView(R.id.btn_binding_info_data)
    Button btnBindingInfoData;
    @BindView(R.id.rl_stance_men)
    RelativeLayout rlStanceMen;
    @BindView(R.id.rl_stance_woman)
    RelativeLayout rlStanceWoman;

    Gson gson;
    UserInfoLogin user ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_activity_binding_info_data);
        ButterKnife.bind(this);
        tvFootCenter.setText("个人基本资料");
        gson = new Gson();
        String s = SharedPreferencesTools.GetUsearInfo(this, "userSave", "userInfo");
        user = gson.fromJson(s, UserInfoLogin.class);
    }

    @OnClick(R.id.ivb_back_finsh)
    public void Finsh() {
        this.finish();
    }

    String gender = "";

    @OnClick(R.id.rl_stance_men)
    public void SelectMan() {
        gender = "男";
        ivGenderMan.setBackgroundResource(R.drawable.sele_icon);
        ivGenderWoman.setBackgroundResource(R.drawable.read_true);
    }

    @OnClick(R.id.rl_stance_woman)
    public void SelectWoman() {
        gender = "女";
        ivGenderWoman.setBackgroundResource(R.drawable.sele_icon);
        ivGenderMan.setBackgroundResource(R.drawable.read_true);
    }

    @OnClick(R.id.btn_binding_info_data)
    public void HttpBindingData() {

        String userid = user.getUserid();
        String name = edBindingName.getText().toString();
        Log.e("BindingInfoData","userid = "+userid);
        Log.e("BindingInfoData","gender = "+gender);
        Log.e("BindingInfoData","name = "+name);
        if (StringUtils.isEmpty(name)) {
            ToolUtils.showToast(BindingInfoData.this, "请正确填写昵称！", Toast.LENGTH_SHORT);
            return;
        }
        if (StringUtils.isEmpty(gender)) {
            ToolUtils.showToast(BindingInfoData.this, "请选择性别！", Toast.LENGTH_SHORT);
            return;
        }
        Retrofit rt = new Retrofit.Builder()
                .baseUrl(Http_data.http_data)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GetService getService = rt.create(GetService.class);
        getService.BindingInfoData(userid, name, gender).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful()){
                    Log.e("BindingInfoData","response = "+response.body());
                }
                if (response.body().toString().equals("true")) {
                }
                ToolUtils.showToast(BindingInfoData.this, "更新昵称成功！", Toast.LENGTH_SHORT);

                /**保存 昵称，性别*/
                user.setGender(gender);
                user.setName(edBindingName.getText().toString());
                SharedPreferencesTools.SaveUserInfo(BindingInfoData.this,"userSave","userInfo",gson.toJson(user));

                startActivity(new Intent(BindingInfoData.this, HomeAcitivtyMy.class));
                finish();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                ToolUtils.showToast(BindingInfoData.this, "更新昵称失败，请重试！", Toast.LENGTH_SHORT);
                startActivity(new Intent(BindingInfoData.this, LoginSelectActivity.class));
                finish();
            }
        });
    }
}
