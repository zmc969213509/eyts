package com.guojianyiliao.eryitianshi.MyUtils.view.activity;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.guojianyiliao.eryitianshi.MyUtils.base.BaseActivity;
import com.guojianyiliao.eryitianshi.MyUtils.bean.RemidBean;
import com.guojianyiliao.eryitianshi.MyUtils.interfaceservice.EventData;
import com.guojianyiliao.eryitianshi.MyUtils.interfaceservice.GetService;
import com.guojianyiliao.eryitianshi.MyUtils.manager.BusProvider;
import com.guojianyiliao.eryitianshi.MyUtils.manager.RetrofitClient;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.MyLogcat;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.SpUtils;
import com.guojianyiliao.eryitianshi.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
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

    @BindView(R.id.ivb_back_finsh)
    ImageButton ivbBackFinsh;
    @BindView(R.id.tv_foot_center)
    TextView tvFootCenter;
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

    String reminddate, enddate;//开始和结束时间不可修改

    @BindView(R.id.ll_content1)
    LinearLayout llContent1;
    @BindView(R.id.ll_content2)
    LinearLayout llContent2;

    String remindid;

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_activity_add_remind);
        ButterKnife.bind(this);

        EventData mEventData = new EventData();
        mEventData.setContent("hello world!");
        BusProvider.getInstance().post(mEventData);//发布事件

        remindid = getIntent().getStringExtra("remindid");
        MyLogcat.jLog().e("remindid:" + remindid);

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
        String userid = SpUtils.getInstance(this).get("userid", null);

        String t1 = time1.getText().toString();
        String dontent1 = ed1.getText().toString();

        String t2 = time2.getText().toString();
        String dontent2 = ed2.getText().toString();

        String t3 = time3.getText().toString();
        String dontent3 = ed3.getText().toString();
        RetrofitClient.getinstance(this).create(GetService.class).editRemind(remindid, reminddate, enddate, t1, t2, t3, dontent1, dontent2, dontent3, userid).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    MyLogcat.jLog().e("修改用药提醒 succes:" + response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                MyLogcat.jLog().e("修改用药提醒 onFailure:");
            }
        });

    }

    /**
     * 删除
     */
    @OnClick(R.id.btn_add_remind)
    public void remove() {
        RetrofitClient.getinstance(this).create(GetService.class).deleteRemind(remindid).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    MyLogcat.jLog().e("删除用药提醒 succes:" + response.body().toString());
                    remindListener.setnotify();

                    EventData mEventData = new EventData();
                    mEventData.setContent("hello world!");
                    BusProvider.getInstance().post(mEventData);//发布事件

                    finish();
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
        finish();
    }

    /**
     * 获取用药提醒的详情
     */
    public void getRemind() {

        RetrofitClient.getinstance(this).create(GetService.class).getRemind(remindid).enqueue(new Callback<RemidBean>() {
            @Override
            public void onResponse(Call<RemidBean> call, Response<RemidBean> response) {
                if (response.isSuccessful()) {
                    MyLogcat.jLog().e("获取用药提醒的详情 success:" + response.body().toString());
                    RemidBean body = response.body();

                    reminddate = body.reminddate + "";
                    enddate = body.enddate + "";
                    if (body.time1.isEmpty() && body.time2.isEmpty() && body.time3.isEmpty()) {
                        llContent1.setVisibility(View.VISIBLE);
                        llContent2.setVisibility(View.VISIBLE);
                    }
                    if (body.time1.isEmpty() && body.time2.isEmpty()) {
                        llContent1.setVisibility(View.VISIBLE);
                    }
                    time1.setText(body.time1);
                    time2.setText(body.time2);
                    time3.setText(body.time3);

                    ed1.setText(body.content1);
                    ed2.setText(body.content2);
                    ed3.setText(body.content3);
                }

            }

            @Override
            public void onFailure(Call<RemidBean> call, Throwable t) {
                MyLogcat.jLog().e("获取用药提醒的详情 q onFailure:");
            }
        });
    }

    public RemindListener remindListener;

    public void setRemindListener(RemindListener remindListener) {
        this.remindListener = remindListener;
    }

    public interface RemindListener {
        public void setnotify();
    }
}
