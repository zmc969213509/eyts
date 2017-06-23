package com.guojianyiliao.eryitianshi.MyUtils.view.activity;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.guojianyiliao.eryitianshi.MyUtils.base.BaseActivity;
import com.guojianyiliao.eryitianshi.MyUtils.bean.DrugRemindBean;
import com.guojianyiliao.eryitianshi.MyUtils.bean.RemidBean;
import com.guojianyiliao.eryitianshi.MyUtils.interfaceservice.EventData;
import com.guojianyiliao.eryitianshi.MyUtils.interfaceservice.GetService;
import com.guojianyiliao.eryitianshi.MyUtils.manager.BusProvider;
import com.guojianyiliao.eryitianshi.MyUtils.manager.RetrofitClient;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.MyLogcat;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.SharedPreferencesTools;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.SpUtils;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.TimeUtil;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.ToolUtils;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.UIUtils;
import com.guojianyiliao.eryitianshi.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 用药提醒的详细页面；此页面 1.点击编辑修改用药提醒 2，点击删除删除用药提醒
 * jnluo,jnluo5889@126.com
 */

public class AddremindActivityDetail extends BaseActivity {

    private static final String TAG = "AddremindActivityDetail";
    @BindView(R.id.ivb_back_finsh)
    ImageButton ivbBackFinsh;
    @BindView(R.id.tv_foot_center)
    TextView tvFootCenter;
    @BindView(R.id.tv_1)
    TextView startTime;
    @BindView(R.id.tv_2)
    TextView endTime;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.time_1)
    TextView time1;
    @BindView(R.id.ed_1)
    EditText ed1;
    @BindView(R.id.time_2)
    TextView time2;
    @BindView(R.id.ed_2)
    EditText ed2;
    @BindView(R.id.time_3)
    TextView time3;
    @BindView(R.id.ed_3)
    EditText ed3;
    @BindView(R.id.btn_add_remind)
    Button btnAddRemind;

    @BindView(R.id.ll_content1)
    LinearLayout llContent1;
    @BindView(R.id.ll_content2)
    LinearLayout llContent2;


    DrugRemindBean drugRemindBean;

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_activity_add_remind);
        ButterKnife.bind(this);

        drugRemindBean = (DrugRemindBean) getIntent().getSerializableExtra("drugremind");

        /**设置数据*/
        getRemind();

        tvFootCenter.setText("用药提醒");
        tvRight.setText("提交");
        tvRight.setVisibility(View.VISIBLE);
        btnAddRemind.setText("删除提醒");
        btnAddRemind.setBackgroundResource(R.drawable.aa_btn_default_red);

    }

    Calendar dateAndTime = Calendar.getInstance(Locale.CHINA);
    SimpleDateFormat fmtTime = new SimpleDateFormat("HH:mm");

    @OnClick(R.id.time_1)
    public void addTime1() {
        TimePickerDialog timeDlg = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                dateAndTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                dateAndTime.set(Calendar.MINUTE, minute);
                time1.setText(fmtTime.format(dateAndTime.getTime()));
            }
        }, dateAndTime.get(Calendar.HOUR_OF_DAY), dateAndTime.get(Calendar.MINUTE), true);
        timeDlg.show();
    }

    @OnClick(R.id.time_2)
    public void addTime2() {
        TimePickerDialog timeDlg = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                dateAndTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                dateAndTime.set(Calendar.MINUTE, minute);
                time2.setText(fmtTime.format(dateAndTime.getTime()));
            }
        }, dateAndTime.get(Calendar.HOUR_OF_DAY), dateAndTime.get(Calendar.MINUTE), true);
        timeDlg.show();
    }

    @OnClick(R.id.time_3)
    public void addTime3() {
        TimePickerDialog timeDlg = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                dateAndTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                dateAndTime.set(Calendar.MINUTE, minute);
                time3.setText(fmtTime.format(dateAndTime.getTime()));
            }
        }, dateAndTime.get(Calendar.HOUR_OF_DAY), dateAndTime.get(Calendar.MINUTE), true);
        timeDlg.show();
    }

    /**
     * 提交修改用药提醒
     */
    @OnClick(R.id.tv_right)
    public void submit() {
        Log.e(TAG,"submit");

        String t1 = time1.getText().toString();
        String dontent1 = ed1.getText().toString();

        String t2 = time2.getText().toString();
        String dontent2 = ed2.getText().toString();

        String t3 = time3.getText().toString();
        String dontent3 = ed3.getText().toString();

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String startTime = TimeUtil.currectTimess();
        String endTime = df.format( System.currentTimeMillis()+(3600*26*100));

        Call<String> stringCall = null;
        Log.e(TAG,"num = " + num);
        if(num == 1){
            if(TextUtils.isEmpty(dontent1)){
                ToolUtils.showToast(this, "请添加用药提醒", Toast.LENGTH_SHORT);
                return;
            }
            stringCall = RetrofitClient.getinstance(this).create(GetService.class).editRemind(drugRemindBean.getRemindid(), startTime,endTime, t1, dontent1);
        }else if(num == 2){
            if(TextUtils.isEmpty(dontent1) || TextUtils.isEmpty(dontent2) ){
                ToolUtils.showToast(this, "请添加用药提醒", Toast.LENGTH_SHORT);
                return;
            }
            stringCall = RetrofitClient.getinstance(this).create(GetService.class).editRemind(drugRemindBean.getRemindid(), startTime,endTime, t1, t2,  dontent1, dontent2);
        }else if(num == 3){
            if(TextUtils.isEmpty(dontent1) || TextUtils.isEmpty(dontent2) || TextUtils.isEmpty(dontent3)){
                ToolUtils.showToast(this, "请添加用药提醒", Toast.LENGTH_SHORT);
                return;
            }
            stringCall = RetrofitClient.getinstance(this).create(GetService.class).editRemind(drugRemindBean.getRemindid(), startTime,endTime, t1, t2, t3, dontent1, dontent2, dontent3);
        }

        stringCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.e(TAG,"onResponse:" + response.toString());
                if (response.isSuccessful()) {
                    Log.e(TAG,"onResponse.isSuccessful" + num);
                    ToolUtils.showToast(AddremindActivityDetail.this, "修改用药成功", Toast.LENGTH_SHORT);
                    flag = 1;
                    onBackPressed();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                ToolUtils.showToast(AddremindActivityDetail.this, "修改用药失败", Toast.LENGTH_SHORT);
                Log.e(TAG,"onFailure" + num);
            }
        });

    }

    /**
     * 删除
     */
    @OnClick(R.id.btn_add_remind)
    public void remove() {
        RetrofitClient.getinstance(this).create(GetService.class).deleteRemind(drugRemindBean.getRemindid()).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(AddremindActivityDetail.this,"删除用药成功",Toast.LENGTH_SHORT).show();
                    flag = 2;
                    onBackPressed();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                MyLogcat.jLog().e("删除用药提醒 onFailure:");
            }
        });

    }


    @OnClick(R.id.ivb_back_finsh)
    public void back() {
        onBackPressed();
    }

    /**当前界面操作标记  0：什么也没做  1：修改用药  2：删除用药**/
    private int flag = 0;
    /**当前用药数**/
    private int num = 1;
    /**
     * 获取用药提醒的详情
     */
    public void getRemind() {

        if (!drugRemindBean.getTime2().isEmpty() && !drugRemindBean.getTime3().isEmpty()) {
            llContent1.setVisibility(View.VISIBLE);
            llContent2.setVisibility(View.VISIBLE);
            num = 3;
        }else if(!drugRemindBean.getTime2().isEmpty()){
            llContent1.setVisibility(View.VISIBLE);
            num = 2;
        }
        time1.setText(drugRemindBean.getTime1());
        time2.setText(drugRemindBean.getTime2());
        time3.setText(drugRemindBean.getTime3());

        ed1.setText(drugRemindBean.getContent1());
        ed2.setText(drugRemindBean.getContent2());
        ed3.setText(drugRemindBean.getContent3());

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        startTime.setText(df.format(drugRemindBean.getReminddate())+"");
        endTime.setText(df.format(drugRemindBean.getReminddate())+"");
    }


    @Override
    public void onBackPressed() {
        setResult(flag);
        super.onBackPressed();
    }
}
