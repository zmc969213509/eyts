package com.guojianyiliao.eryitianshi.MyUtils.view.activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.guojianyiliao.eryitianshi.MyUtils.base.BaseActivity;
import com.guojianyiliao.eryitianshi.MyUtils.interfaceservice.GetService;
import com.guojianyiliao.eryitianshi.MyUtils.manager.RetrofitClient;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.MyLogcat;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.SpUtils;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.StringUtils;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.ToolUtils;
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
 * description: 添加用药提醒界面
 * autour: jnluo jnluo5889@126.com
 * version: Administrator
*/
public class AddremindActivity extends BaseActivity {

    /**
     * 头 ID
     */
    @BindView(R.id.ivb_back_finsh)
    ImageButton ivbBackFinsh;
    @BindView(R.id.tv_foot_center)
    TextView tvFootCenter;
    /**
     * 保存
     */
    @BindView(R.id.tv_right)
    TextView tvRight;

    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat fmtTime = new SimpleDateFormat("HH:mm");
    Date curDate = new Date(System.currentTimeMillis());
    Calendar dateAndTime = Calendar.getInstance(Locale.CHINA);


    //String startDate,endDade,timg;
    Calendar c;
    @BindView(R.id.tv_1)
    TextView tv1;
    @BindView(R.id.tv_2)
    TextView tv2;
    @BindView(R.id.tv_3)
    TextView tv3;
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

    /**
     * 2/3 条隐藏
     */
    @BindView(R.id.ll_content1)
    LinearLayout llContent1;
    @BindView(R.id.ll_content2)
    LinearLayout llContent2;


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_activity_add_remind);
        ButterKnife.bind(this);
        tvFootCenter.setText("用药提醒");
        tvRight.setVisibility(View.VISIBLE);

        tv1.setText(formatter.format(curDate));
        tv2.setText(formatter.format(curDate));

        MyLogcat.jLog().e("curDate:" + curDate);

        c = Calendar.getInstance();

        myear = c.get(Calendar.YEAR);

        mmonth = c.get(Calendar.MONTH);

        mday = c.get(Calendar.DAY_OF_MONTH);


        dataYears = c.get(Calendar.YEAR);

        dataMonth = c.get(Calendar.MONTH);

        dataDay = c.get(Calendar.DAY_OF_MONTH);

        // String shuchu = DateUtils.timesOne("1491911657000");
        // MyLogcat.jLog().e("1491911657000:" + shuchu);
    }


    @OnClick(R.id.tv_1)
    public void startTime() {
        acquisitionstatrtime();
    }

    @OnClick(R.id.tv_2)
    public void endTime() {
        acquisitionendtime();
    }

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

    DatePickerDialog dialog;
    int myear, mmonth, mday;
    int dataYears, dataMonth, dataDay;

    private void acquisitionendtime() {
        final Calendar c1 = Calendar.getInstance();
        dialog = new DatePickerDialog(AddremindActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

//                c1.set(myear, mmonth, mday);

                c1.set(dataYears, dataMonth - 1, dataDay);

                dialog.getDatePicker().setMinDate(c1.getTimeInMillis());


                if (year < dataYears || (year == dataYears && monthOfYear < dataMonth - 1) || (year == dataYears && monthOfYear == dataMonth - 1 && dayOfMonth < dataDay)) {

                    Toast.makeText(AddremindActivity.this, "不能选择开始之前的时间", Toast.LENGTH_SHORT).show();

                } else {

                    c1.set(year, monthOfYear, dayOfMonth);

                    date = (String) DateFormat.format("yyy-MM-dd", c1);

                    tv2.setText(date);

                }

            }
        }, dataYears, dataMonth - 1, dataDay);
        dialog.show();
    }

    private String date;

    private void acquisitionstatrtime() {
        final Calendar c1 = Calendar.getInstance();
        dialog = new DatePickerDialog(AddremindActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                c1.set(myear, mmonth, mday);
                dialog.getDatePicker().setMinDate(c1.getTimeInMillis());


                if (year < myear || (year == myear && monthOfYear < mmonth) || (year == myear && monthOfYear == mmonth && dayOfMonth < mday)) {

                    Toast.makeText(AddremindActivity.this, "不能选择以前的时间", Toast.LENGTH_SHORT).show();

                } else {

                    c1.set(year, monthOfYear, dayOfMonth);

                    date = (String) DateFormat.format("yyy-MM-dd", c1);


                    dataYears = Integer.parseInt((String) DateFormat.format("yyy", c1));

                    dataMonth = Integer.parseInt((String) DateFormat.format("MM", c1));

                    dataDay = Integer.parseInt((String) DateFormat.format("dd", c1));

                    tv1.setText(date);

                    tv2.setText(date);

                }

            }
        }, myear, mmonth, mday);
        dialog.show();
    }


    @OnClick(R.id.ivb_back_finsh)
    public void back() {
        finish();
    }


    /**
     * 添加三条
     */
    private boolean isAdd = false;

    @OnClick(R.id.btn_add_remind)
    public void add() {

        if (!isAdd) {
            if (StringUtils.isEmpty(ed1.getText().toString())) {
                ToolUtils.showToast(this, "你的第一条用药提醒还未设置用药", Toast.LENGTH_SHORT);
            } else {
                isAdd = true;
                llContent1.setVisibility(View.VISIBLE);
            }
        } else {
            if (StringUtils.isEmpty(ed2.getText().toString())) {
                ToolUtils.showToast(this, "你的第二条用药提醒还未设置用药", Toast.LENGTH_SHORT);
            } else {
                llContent2.setVisibility(View.VISIBLE);
            }
        }
    }

    /**
     * 保存到服务器
     */
    String startDateOut;

    @OnClick(R.id.tv_right)
    public void HttpSendData() {
        String userid = SpUtils.getInstance(this).get("userid", null);
        String startDate = tv1.getText().toString();
        String endDate = tv2.getText().toString();

        String t1 = time1.getText().toString();
        String dontent1 = ed1.getText().toString();

        String t2 = time2.getText().toString();
        String dontent2 = ed2.getText().toString();

        String t3 = time3.getText().toString();
        String dontent3 = ed3.getText().toString();

        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date d = df.parse(startDate);
            Calendar cal = Calendar.getInstance();
            cal.setTime(d);
            cal.add(Calendar.DATE, -1);  //减1天
            startDateOut = df.format(cal.getTime());

            MyLogcat.jLog().e("startDateOut:" + startDateOut);
        } catch (Exception e) {
            MyLogcat.jLog().e("添加用药提醒错误:" + e.getMessage());
        }

        MyLogcat.jLog().e("startDate:" + startDate + "//endDate" + endDate + "//userid:" + userid);
        MyLogcat.jLog().e("t1:" + t1 + "//dontent1:" + dontent1);
        MyLogcat.jLog().e("t2:" + t2 + "//dontent2:" + dontent2);
        MyLogcat.jLog().e("t3:" + t3 + "//dontent3:" + dontent3);

        if (StringUtils.isEmpty(startDateOut)) {
            ToolUtils.showToast(this, "添加时间出错！", Toast.LENGTH_SHORT);
            MyLogcat.jLog().e("添加用药提醒错误:");
            return;
        }

        RetrofitClient.getinstance(this).create(GetService.class).addRemind(startDateOut, endDate, t1, t2, t3, dontent1, dontent2, dontent3, userid).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                MyLogcat.jLog().e("onresponse:" + response.body());
                ToolUtils.showToast(AddremindActivity.this, "添加用药提醒成功！", Toast.LENGTH_LONG);
                finish();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                MyLogcat.jLog().e("onFailure:");
                ToolUtils.showToast(AddremindActivity.this, "添加用药提醒失败！", Toast.LENGTH_SHORT);
            }
        });
    }


}
