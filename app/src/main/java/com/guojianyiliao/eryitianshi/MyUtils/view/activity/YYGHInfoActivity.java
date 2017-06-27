package com.guojianyiliao.eryitianshi.MyUtils.view.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.OptionsPickerView;
import com.google.gson.Gson;
import com.guojianyiliao.eryitianshi.MyUtils.bean.DocArrangement;
import com.guojianyiliao.eryitianshi.MyUtils.bean.UserInfoLogin;
import com.guojianyiliao.eryitianshi.MyUtils.bean.zmc_UserGHInfo;
import com.guojianyiliao.eryitianshi.MyUtils.bean.zmc_docInfo;
import com.guojianyiliao.eryitianshi.MyUtils.interfaceservice.GetService;
import com.guojianyiliao.eryitianshi.MyUtils.manager.RetrofitClient;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.AnimLoadingUtil;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.DateUtils;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.SharedPreferencesTools;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.TimeUtil;
import com.guojianyiliao.eryitianshi.R;
import com.nostra13.universalimageloader.core.ImageLoader;


import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by zmc on 2017/6/14.
 * 预约挂号信息完善界面
 */

public class YYGHInfoActivity extends AppCompatActivity {


    private static final String TAG = "YYGHInfoActivity";
    @BindView(R.id.yygh_i_title)
    TextView title_tv;
    @BindView(R.id.iv_icon)
    CircleImageView ivIcon;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_intro)
    TextView tvIntro;
    @BindView(R.id.tv_section)
    TextView tvSection;

    @BindView(R.id.chat_img)
    ImageView chat_img;
    @BindView(R.id.guahao_img)
    ImageView guahao_img;

    @BindView(R.id.yygh_i_phone)
    EditText phone_et;
    @BindView(R.id.yygh_i_name)
    EditText name_et;
    @BindView(R.id.yygh_i_money)
    TextView money_tv;
    @BindView(R.id.yygh_i_type)
    TextView type_tv;
    @BindView(R.id.yygh_i_time)
    TextView time_tv;
    @BindView(R.id.yygh_i_gender)
    TextView gender_tv;
    @BindView(R.id.yygh_i_age)
    TextView age_tv;
    @BindView(R.id.yygh_i_sure)
    Button sure_btn;

    View animView;
    AnimLoadingUtil animLoadingUtil;

    /**
     * 医生实体
     **/
    zmc_docInfo docInfo;
    /**
     * 预约信息实体
     */
    zmc_UserGHInfo userGHInfo;
    /**
     * 值班实体
     **/
    DocArrangement arrangement;
    /**
     * 当前显示类型
     * 0：进行预约挂号等级
     * 1：查看预约挂号信息
     */
    private int flag;

    /**返回到查看预约挂号历史时  是否需要更新数据标识**/
    private boolean isUpdate = false;

    UserInfoLogin user;
    Gson gson;

    /**
     * 医生预约时间选择三级列表
     **/
    private ArrayList<String> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();

    /**
     * 患者性别选择
     **/
    private ArrayList<String> gender = new ArrayList<>();
    /**
     * 患者年龄选择
     **/
    private ArrayList<String> age = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zmc_activity_yygh_i);

        flag = getIntent().getIntExtra("flag",0);
        if(flag == 0){
            docInfo = (zmc_docInfo) getIntent().getSerializableExtra("docInfo");
        }else if(flag == 1){
            userGHInfo = (zmc_UserGHInfo) getIntent().getSerializableExtra("userGHInfo");
        }


        ButterKnife.bind(this);

        gson = new Gson();
        String s = SharedPreferencesTools.GetUsearInfo(this, "userSave", "userInfo");
        user = gson.fromJson(s, UserInfoLogin.class);


        if(flag == 0){

            animView = findViewById(R.id.anim_view_layout);
            animLoadingUtil = new AnimLoadingUtil(animView);

            init();
            getDocWork();
        }else if(flag == 1 ){
            sure_btn.setVisibility(View.GONE);
            title_tv.setText("预约信息");
            init(userGHInfo);
        }

    }

    /**
     * 获取医生上班时间
     */
    private void getDocWork() {

        long time = System.currentTimeMillis();

        int week = DateUtils.getWeeks(System.currentTimeMillis());

        String s2 = TimeUtil.currectTimehour(time);
        String hour = s2.substring(0,2);//当前时间的小时数
        String minute = s2.substring(3);//当前时间的分钟数

        for (int i = 0; i < 7; i++) {
            long l = time + (i * 3600 * 24 * 1000);
            String s = TimeUtil.currectTime_day(l);
            options1Items.add(s);
            int dayWork = getDayWork((week + i) % 7);
            ArrayList<String> list = new ArrayList<>();
            if (dayWork == 0) { //上午
//                if(i == 0){
//                    if(Integer.parseInt(hour) > 11){//12点以后
//                        list.add("已过时");
//                    }else if(Integer.parseInt(hour) == 11 && Integer.parseInt(minute) >= 30){//11点半以后
//                        list.add("已过时");
//                    }else{
//                        list.add("上午");
//                    }
//                }else{
                    list.add("上午");
//                }
            } else if (dayWork == 1) { //下午
//                if(i == 0){
//                    if(Integer.parseInt(hour) > 17){//17点以后
//                        list.add("已过时");
//                    }else{
//                        list.add("下午");
//                    }
//                }else{
                    list.add("下午");
//                }
            } else if (dayWork == 2) { //全天
//                if(i == 0){
//                    if(Integer.parseInt(hour) < 11 || (Integer.parseInt(hour) == 11 && Integer.parseInt(minute) < 30)){
//                        list.add("上午");
//                        list.add("下午");
//                    }else if(Integer.parseInt(hour) < 17 || (Integer.parseInt(hour) == 17 && Integer.parseInt(minute) < 30)){
//                        list.add("已过时");
//                        list.add("下午");
//                    }else{
//                        list.add("已过时");
//                        list.add("已过时");
//                    }
//                }else{
                    list.add("上午");
                    list.add("下午");
//                }
            } else if (dayWork == 3) { //不上班
                list.add("休息");
            }
            options2Items.add(list);
        }

        for (int i = 0; i < 7; i++) {
            ArrayList<ArrayList<String>> lists = new ArrayList<>();
            for (int j = 0; j < options2Items.size(); j++) {
                ArrayList<String> list = options2Items.get(i);
                if (list.size() == 1) {
                    String s1 = list.get(0);
                    ArrayList<String> listss = new ArrayList<>();
                    if (s1.equals("上午")) {
                        if(i == 0) {
                            if(Integer.parseInt(hour) > 11 || (Integer.parseInt(hour) == 11 && Integer.parseInt(minute) >= 30)){ //已过时
                                listss.add("已过时");
                            }else{
                                for (int k = Integer.parseInt(hour); k <= 11; k++) {
                                    for (int l = 0; l <= 1; l++) {
                                        if (l == 0) {
                                            if (k >= 10) {
                                                listss.add(k + ":00 - " + k + ":30");
                                            } else {
                                                listss.add("0" + k + ":00 - 0" + k + ":30");
                                            }
                                        } else if (l == 1) {
                                            if (k >= 10) {
                                                listss.add(k + ":30 - " + (k + 1) + ":00");
                                            } else if (k + 1 >= 10) {
                                                listss.add("0" + k + ":30 - " + (k + 1) + ":00");
                                            } else {
                                                listss.add("0" + k + ":30 - 0" + (k + 1) + ":00");
                                            }
                                        }
                                    }
                                }
                                String s = listss.get(0);
                                if (s.equals("08:00 - 08:30")) {
                                    listss.remove(0);
                                }
                                String s3 = listss.get(listss.size() - 1);
                                if (s3.equals("11:30 - 12:00")) {
                                    listss.remove(listss.size() - 1);
                                }
                                if (Integer.parseInt(minute) >= 30 && Integer.parseInt(hour) > 8) {
                                    listss.remove(0);
                                }
                            }
                        }else{
                            listss.add("08:30 - 09:00");
                            listss.add("09:00 - 09:30");
                            listss.add("09:30 - 10:00");
                            listss.add("10:00 - 10:30");
                            listss.add("10:30 - 11:00");
                            listss.add("11:00 - 11:30");
                        }
                    } else if (s1.equals("下午")) {
                        if(i == 0) {
                            int currentHour = Integer.parseInt(hour);
                            if(currentHour >= 17){
                                listss.add("已过时");
                            }else{
                                for (int k = currentHour >= 13 ? currentHour : 13; k < 17; k++) {
                                    for (int l = 0; l <= 1; l++) {
                                        if (l == 0) {
                                            listss.add(k + ":00 - " + k + ":30");
                                        } else if (l == 1) {
                                            listss.add(k + ":30 - " + (k + 1) + ":00");
                                        }
                                    }
                                }
                                String s = listss.get(0);
                                if (s.equals("13:00 - 13:30")) {
                                    listss.remove(0);
                                }
                                String s3 = listss.get(listss.size() - 1);
                                if (s3.equals("17:30 - 18:00")) {
                                    listss.remove(listss.size() - 1);
                                }
                                if (Integer.parseInt(minute) >= 30 && Integer.parseInt(hour) > 13) {
                                    listss.remove(0);
                                }
                            }
                        }else{
                            listss.add("13:30 - 14:00");
                            listss.add("14:00 - 14:30");
                            listss.add("14:30 - 15:00");
                            listss.add("15:00 - 15:30");
                            listss.add("15:30 - 16:00");
                            listss.add("16:00 - 16:30");
                            listss.add("16:30 - 17:00");
                        }
                    } else if (s1.equals("休息")) {
                        listss.add("");
                    }else if(s1.equals("已过时")){
                        listss.add("已过时");
                    }
                    lists.add(listss);
                } else if (list.size() == 2) {
                    ArrayList<String> listss1 = new ArrayList<>();
                    ArrayList<String> listss2 = new ArrayList<>();
                    if(i == 0){
                        if(Integer.parseInt(hour) < 11 || (Integer.parseInt(hour) == 11 && Integer.parseInt(minute) < 30)) { // 当前是上午
                            for (int k = Integer.parseInt(hour); k <= 11; k++) {
                                for (int l = 0; l <= 1; l++) {
                                    if (l == 0) {
                                        if (k >= 10) {
                                            listss1.add(k + ":00 - " + k + ":30");
                                        } else {
                                            listss1.add("0" + k + ":00 - 0" + k + ":30");
                                        }
                                    } else if (l == 1) {
                                        if (k >= 10) {
                                            listss1.add(k + ":30 - " + (k + 1) + ":00");
                                        } else if (k + 1 >= 10) {
                                            listss1.add("0" + k + ":30 - " + (k + 1) + ":00");
                                        } else {
                                            listss1.add("0" + k + ":30 - 0" + (k + 1) + ":00");
                                        }
                                    }
                                }
                            }
                            String s = listss1.get(0);
                            if (s.equals("08:00 - 08:30")) {
                                listss1.remove(0);
                            }
                            String s3 = listss1.get(listss1.size() - 1);
                            if (s3.equals("11:30 - 12:00")) {
                                listss1.remove(listss1.size() - 1);
                            }
                            if (Integer.parseInt(minute) >= 30 && Integer.parseInt(hour) > 8) {
                                listss1.remove(0);
                            }
                            listss2.add("13:30 - 14:00");
                            listss2.add("14:00 - 14:30");
                            listss2.add("14:30 - 15:00");
                            listss2.add("15:00 - 15:30");
                            listss2.add("15:30 - 16:00");
                            listss2.add("16:00 - 16:30");
                            listss2.add("16:30 - 17:00");
                        }else if(Integer.parseInt(hour) < 17){ //当前是下午
                            listss1.add("已过时");
                            int currentHour = Integer.parseInt(hour);
                            for (int k = currentHour >= 13 ? currentHour : 13 ; k < 17 ; k++) {
                                for (int l = 0; l <=1 ; l++) {
                                    if(l == 0){
                                        listss2.add(k+":00 - "+k+":30");
                                    }else if(l == 1) {
                                        listss2.add(k + ":30 - " + (k + 1) + ":00");
                                    }
                                }
                            }
                            String s = listss2.get(0);
                            if(s.equals("13:00 - 13:30")){
                                listss2.remove(0);
                            }
                            String s3 = listss2.get(listss2.size() - 1);
                            if(s3.equals("17:30 - 18:00")){
                                listss2.remove(listss2.size() - 1);
                            }
                            if(Integer.parseInt(minute) >= 30 && Integer.parseInt(hour) > 13){
                                listss2.remove(0);
                            }
                        }else{ // 已过时
                            listss1.add("已过时");
                            listss2.add("已过时");
                        }
                    }else{
                        listss1.add("08:30 - 09:00");
                        listss1.add("09:00 - 09:30");
                        listss1.add("09:30 - 10:00");
                        listss1.add("10:00 - 10:30");
                        listss1.add("10:30 - 11:00");
                        listss1.add("11:00 - 11:30");

                        listss2.add("13:30 - 14:00");
                        listss2.add("14:00 - 14:30");
                        listss2.add("14:30 - 15:00");
                        listss2.add("15:00 - 15:30");
                        listss2.add("15:30 - 16:00");
                        listss2.add("16:00 - 16:30");
                        listss2.add("16:30 - 17:00");
                    }

                    lists.add(listss1);
                    lists.add(listss2);
                }
            }
            options3Items.add(lists);
        }
    }

    /**
     * 获取当前值班情况
     *
     * @param day
     * @return
     */
    private int getDayWork(int day) {
        int i = -1;
        switch (day) {
            case 1:
                i = arrangement.getMonday();
                break;
            case 2:
                i = arrangement.getTuesday();
                break;
            case 3:
                i = arrangement.getWensday();
                break;
            case 4:
                i = arrangement.getThursday();
                break;
            case 5:
                i = arrangement.getFriday();
                break;
            case 6:
                i = arrangement.getSaturday();
                break;
            case 0:
                i = arrangement.getSunday();
                break;
        }
        return i;
    }

    /**
     * 数据初始化
     */
    private void init(zmc_UserGHInfo userGHInfo) {
        tvTitle.setText(userGHInfo.getDocInfo().getTitle());
        tvName.setText(userGHInfo.getDocname());
        tvIntro.setText("擅长：" + userGHInfo.getDocInfo().getAdept());
        tvSection.setText(userGHInfo.getSection());
        ImageLoader.getInstance().displayImage(userGHInfo.getDocInfo().getIcon(), ivIcon);

        chat_img.setVisibility(View.GONE);
        guahao_img.setVisibility(View.GONE);

        name_et.setFocusable(false);
        phone_et.setFocusable(false);
        name_et.setText(userGHInfo.getName());
        phone_et.setText(userGHInfo.getPhone());
        name_et.setTextColor(getResources().getColor(R.color.aaaaaa));
        phone_et.setTextColor(getResources().getColor(R.color.aaaaaa));
        age_tv.setText(userGHInfo.getAge()+"岁");
        gender_tv.setText(userGHInfo.getGender());

        //确定预约时段
        long Reservationdate = Long.parseLong(userGHInfo.getReservationdate());
        String currectTime = TimeUtil.currectTime(Reservationdate);
        String s = TimeUtil.currectTime(Reservationdate + (30 * 60 * 1000));
        currectTime = currectTime + " - "+s.substring(11);
        time_tv.setText(currectTime);

        int isexpert = userGHInfo.getDocInfo().getIsexpert();
        if (isexpert == 1) {
            type_tv.setText("专家号");
        } else if (isexpert == 0) {
            type_tv.setText("普通号");
        }
        money_tv.setText(userGHInfo.getMoney() + ".00元");

        String reservationdate = userGHInfo.getReservationdate();
        long currentTime = System.currentTimeMillis();
        long reservationTime = Long.parseLong(reservationdate);
        reservationTime += 30 * 60 * 1000;
        if(userGHInfo.getStatus().equals("1")){
            if(currentTime > reservationTime){
                if(userGHInfo.getIscommented() == 0){ //未评价
                    sure_btn.setText("去评价");
                    sure_btn.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    /**
     * 数据初始化
     */
    private void init() {
        if (docInfo != null) {
            arrangement = docInfo.getArrangement();
            tvTitle.setText(docInfo.getTitle());
            tvName.setText(docInfo.getName());
            tvIntro.setText("擅长：" + docInfo.getAdept());
            tvSection.setText(docInfo.getSection());
            ImageLoader.getInstance().displayImage(docInfo.getIcon(), ivIcon);

            chat_img.setVisibility(View.GONE);
            guahao_img.setVisibility(View.GONE);

            money_tv.setText(docInfo.getChatcost() + ".00元");
            if (docInfo.getIsexpert() == 1) {
                type_tv.setText("专家号");
            } else if (docInfo.getIsexpert() == 0) {
                type_tv.setText("普通号");
            }
        }
        if (user != null) {
            phone_et.setText(user.getPhone());
        }

        gender.add("男");
        gender.add("女");

        for (int i = 0; i < 19; i++) {
            age.add(i + "岁");
        }
    }

    /**
     * 选择性别
     */

    @OnClick(R.id.yygh_i_gender)
    public void selectorGender(){
        if(flag == 0){
            OptionsPickerView pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
                @Override
                public void onOptionsSelect(int options1, int options2, int options3, View v) {
                    gender_tv.setText(gender.get(options1));
                }
            })

                    .setTitleText("性别选择").setTitleColor(getResources().getColor(R.color.fontcolor))
                    .setCancelColor(getResources().getColor(R.color.view))
                    .setSubmitColor(getResources().getColor(R.color.view))
                    .setDividerColor(getResources().getColor(R.color.fontcolor))
                    .setTextColorCenter(getResources().getColor(R.color.fontcolor))//设置选中项文字颜色
                    .setContentTextSize(20)
                    .setOutSideCancelable(false)// default is true
                    .build();

            pvOptions.setPicker(gender);//一级选择器
            pvOptions.show();
        }
    }

    /**
     * 选择年龄
     */
    @OnClick(R.id.yygh_i_age)
    public void selectorAge(){
        if(flag == 0){
            OptionsPickerView pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
                @Override
                public void onOptionsSelect(int options1, int options2, int options3, View v) {
                    age_tv.setText(age.get(options1));
                }
            })

                    .setTitleText("年龄选择").setTitleColor(getResources().getColor(R.color.fontcolor))
                    .setCancelColor(getResources().getColor(R.color.view))
                    .setSubmitColor(getResources().getColor(R.color.view))
                    .setDividerColor(getResources().getColor(R.color.fontcolor))
                    .setTextColorCenter(getResources().getColor(R.color.fontcolor)) //设置选中项文字颜色
                    .setContentTextSize(20)
                    .setOutSideCancelable(false)// default is true
                    .build();

            pvOptions.setPicker(age);//一级选择器
            pvOptions.show();
        }
    }

    /**
     * 选择预约时间
     */
    @OnClick(R.id.yygh_i_time)
    public void selectorTime() {
        if(flag == 0){
            OptionsPickerView pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
                @Override
                public void onOptionsSelect(int options1, int options2, int options3, View v) {
                    //返回的分别是三个级别的选中位置
                    if (options2Items.get(options1).get(options2).equals("休息")) {
                        Toast.makeText(YYGHInfoActivity.this, "当天医生不值班", Toast.LENGTH_SHORT).show();
                        selectorTime();
                    }else if(options3Items.get(options1).get(options2).get(options3).equals("已过时")){
                        Toast.makeText(YYGHInfoActivity.this, "该时段已过，请重新选择", Toast.LENGTH_SHORT).show();
                        selectorTime();
                    } else {
                        String tx = options1Items.get(options1) + " " +
                                options3Items.get(options1).get(options2).get(options3);

                        time_tv.setText(tx);
                    }
                }
            })

                    .setTitleText("时间选择").setTitleColor(getResources().getColor(R.color.fontcolor))
                    .setCancelColor(getResources().getColor(R.color.view))
                    .setSubmitColor(getResources().getColor(R.color.view))
                    .setDividerColor(getResources().getColor(R.color.fontcolor))
                    .setTextColorCenter(getResources().getColor(R.color.fontcolor)) //设置选中项文字颜色
                    .setContentTextSize(15)
                    .setOutSideCancelable(false)// default is true
                    .build();

            pvOptions.setPicker(options1Items, options2Items, options3Items);//三级选择器
            pvOptions.show();
        }
    }

    /**
     * 提交预约挂号信息
     */
    @OnClick(R.id.yygh_i_sure)
    public void submit(){
        if(sure_btn.getText().toString().equals("去评价")){ //进入医生评价
            Intent intent = new Intent(this,AddCommentActivity.class);
            intent.putExtra("docId",userGHInfo.getDoctorid());
            intent.putExtra("regid",userGHInfo.getRegid());
            intent.putExtra("flag",1);
            startActivityForResult(intent,0);
        }else{ //提交挂号信息
            String name = name_et.getText().toString();
            if(TextUtils.isEmpty(name)){
                Toast.makeText(this,"请填写就诊人姓名",Toast.LENGTH_SHORT).show();
                return;
            }
            String phone = phone_et.getText().toString();
            if(TextUtils.isEmpty(phone)){
                Toast.makeText(this,"请填写就诊人电话",Toast.LENGTH_SHORT).show();
                return;
            }
            String age = age_tv.getText().toString();
            if(age.equals("选择年龄")){
                Toast.makeText(this,"请选择就诊人年龄",Toast.LENGTH_SHORT).show();
                return;
            }
            String gender = gender_tv.getText().toString();
            if(gender.equals("选择性别")){
                Toast.makeText(this,"请选择就诊人性别",Toast.LENGTH_SHORT).show();
                return;
            }
            String time = time_tv.getText().toString();
            if(time.equals("预约就诊时间")){
                Toast.makeText(this,"请选择预约时间",Toast.LENGTH_SHORT).show();
                return;
            }
            animLoadingUtil.startAnim("正在上传信息...");
            age = age.replace("岁","");
            time = time.substring(0,16);
            time = time + ":00";

            RetrofitClient.getinstance(this).create(GetService.class).addRegistered(time,name,gender,age,phone,docInfo.getChatcost(),docInfo.getDoctorid(),user.getUserid()).enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    animLoadingUtil.finishAnim();
                    if(response.isSuccessful()){
                        String body = response.body();
                        Log.e(TAG,"body = " + body);
                        if(body.contains("success")){
                            Toast.makeText(YYGHInfoActivity.this,"预约挂号成功",Toast.LENGTH_SHORT).show();
                            finish();
                        }else if(body.contains("false")){
                            Toast.makeText(YYGHInfoActivity.this,"预约挂号失败",Toast.LENGTH_SHORT).show();
                        }else if(body.contains("msgError")){
                            Toast.makeText(YYGHInfoActivity.this,"挂号成功稍后工作人员会致电确认信息请留意谢谢",Toast.LENGTH_SHORT).show();
                            finish();
                        }else{
                            Toast.makeText(YYGHInfoActivity.this,"挂号失败 = "+body,Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    animLoadingUtil.finishAnim();
                    Toast.makeText(YYGHInfoActivity.this,"预约挂号失败",Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


    @OnClick(R.id.yygh_i_back)
    public void back() {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        if(isUpdate){
            setResult(RESULT_OK);
        }
        super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 0 && resultCode == RESULT_OK){//说明是评价完后返回的
            sure_btn.setVisibility(View.GONE);
            isUpdate = true;
        }
    }
}
