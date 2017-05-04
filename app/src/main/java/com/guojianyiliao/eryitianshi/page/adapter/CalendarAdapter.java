package com.guojianyiliao.eryitianshi.page.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.guojianyiliao.eryitianshi.Data.entity.ConsultingRemindState;
import com.guojianyiliao.eryitianshi.Data.entity.PharmacyState;
import com.guojianyiliao.eryitianshi.R;
import com.guojianyiliao.eryitianshi.Utils.SpecialCalendar;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 日历gridview中的每一个item显示的textview
 *
 * @author Vincent Lee
 */
public class CalendarAdapter extends BaseAdapter {
    private boolean isLeapyear = false;
    private int daysOfMonth = 0;
    private int dayOfWeek = 0;
    private int lastDaysOfMonth = 0;
    private Context context;
    private String[] dayNumber = new String[42];
    private SpecialCalendar sc = null;
    private Resources res = null;
    private Drawable drawable = null;

    private String currentYear = "";
    private String currentMonth = "";
    private String currentDay = "";

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d");
    private int currentFlag = -1;


    private String showYear = "";
    private String showMonth = "";
    private String animalsYear = "";
    private String leapMonth = "";


    private String sysDate = "";
    private String sys_year = "";
    private String sys_month = "";
    private String sys_day = "";

    List<PharmacyState> list;

    List<ConsultingRemindState> data;

    String scheduleDay;



    int pharmacy = 0;

    public void pharmacyremind(int position) {
        if (position != pharmacy) {
            pharmacy = position;
            notifyDataSetChanged();
        }
    }


    int registration = 0;

    public void registrationremind(int position) {
        if (position != registration) {
            registration = position;
            notifyDataSetChanged();
        }
    }


    int health = 0;

    public void healthremind(int position) {
        if (position != health) {
            health = position;
            notifyDataSetChanged();
        }
    }


    public CalendarAdapter() {
        Date date = new Date();
        sysDate = sdf.format(date);
        sys_year = sysDate.split("-")[0];
        sys_month = sysDate.split("-")[1];
        sys_day = sysDate.split("-")[2];

    }


    int mSelect;

    //点击改变背景
    public void changeSelected(int positon) {
        if (positon != mSelect) {
            mSelect = positon;
            notifyDataSetChanged();
        }

    }

    public CalendarAdapter(Context context, Resources rs, int jumpMonth, int jumpYear, int year_c, int month_c, int day_c, List<ConsultingRemindState> data) {
        this();
        this.context = context;
        sc = new SpecialCalendar();
        this.res = rs;
        this.data = data;

        int stepYear = year_c + jumpYear;
        int stepMonth = month_c + jumpMonth;
        if (stepMonth > 0) {

            if (stepMonth % 12 == 0) {
                stepYear = year_c + stepMonth / 12 - 1;
                stepMonth = 12;
            } else {
                stepYear = year_c + stepMonth / 12;
                stepMonth = stepMonth % 12;
            }
        } else {

            stepYear = year_c - 1 + stepMonth / 12;
            stepMonth = stepMonth % 12 + 12;
            if (stepMonth % 12 == 0) {

            }
        }

        currentYear = String.valueOf(stepYear);
        currentMonth = String.valueOf(stepMonth);

        currentDay = String.valueOf(day_c);

        getCalendar(Integer.parseInt(currentYear), Integer.parseInt(currentMonth));

    }


    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return dayNumber.length;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.calendar_item, null);
        }
        final TextView textView = (TextView) convertView.findViewById(R.id.tvtext);
        final LinearLayout ll_calendar = (LinearLayout) convertView.findViewById(R.id.ll_calendar);
        String d = dayNumber[position];


        textView.setText(d);

        if (position < daysOfMonth + dayOfWeek && position >= dayOfWeek) {


        } else {
            ll_calendar.setVisibility(View.GONE);
        }

        if (currentFlag == position) {

            textView.setText("今");

        }

        if (getDateByClickItem(position).equals("1") || getDateByClickItem(position).equals("2") || getDateByClickItem(position).equals("3") || getDateByClickItem(position).equals("4") || getDateByClickItem(position).equals("5") || getDateByClickItem(position).equals("6") || getDateByClickItem(position).equals("7") || getDateByClickItem(position).equals("8") || getDateByClickItem(position).equals("9")) {
            scheduleDay = "0" + getDateByClickItem(position);
        } else {
            scheduleDay = getDateByClickItem(position);
        }

        String scheduleYear = getShowYear();
        String scheduleMonth = getShowMonth();



        if (mSelect == position) {

            textView.setBackgroundResource(R.drawable.cousulting_checked);


        } else {

            textView.setBackgroundColor(android.graphics.Color.parseColor("#ffffff"));


        }



        if (scheduleMonth.length() == 1) {
            scheduleMonth = "0" + scheduleMonth;
        }



        if (pharmacy == 2 && health == 2 && registration == 2) {

        }

        //只有用药提醒被选中
        else if (pharmacy == 1 && registration == 2 && health == 2) {
            for (int i = 0; i < data.size(); i++) {
                if ((scheduleYear + "-" + scheduleMonth + "-" + scheduleDay).equals(data.get(i).getDate()) && position < daysOfMonth + dayOfWeek && position >= dayOfWeek) {
                    if (data.get(i).getRemindId() != 0) {
                        if (mSelect == position) {
                            textView.setBackgroundResource(R.drawable.consulting_onclick9);
                        } else {
                            textView.setBackgroundResource(R.drawable.consulting9);
                        }
                    }
                }
            }
        }

        else if (pharmacy == 2 && registration == 2 && health == 1) {
            for (int i = 0; i < data.size(); i++) {
                if ((scheduleYear + "-" + scheduleMonth + "-" + scheduleDay).equals(data.get(i).getDate()) && position < daysOfMonth + dayOfWeek && position >= dayOfWeek) {
                    if (data.get(i).getHealthId() != 0) {

                        if (mSelect == position) {
                            textView.setBackgroundResource(R.drawable.consulting_onclick7);
                        } else {
                            textView.setBackgroundResource(R.drawable.consulting7);
                        }

                    }
                }

            }
        }

        else if (pharmacy == 2 && registration == 1 && health == 2) {
            for (int i = 0; i < data.size(); i++) {
                if ((scheduleYear + "-" + scheduleMonth + "-" + scheduleDay).equals(data.get(i).getDate()) && position < daysOfMonth + dayOfWeek && position >= dayOfWeek) {
                    if (data.get(i).getRegistrationId() != 0) {

                        if (mSelect == position) {
                            textView.setBackgroundResource(R.drawable.consulting_onclick6);
                        } else {
                            textView.setBackgroundResource(R.drawable.consulting6);
                        }

                    }
                }

            }
        }


        else if (pharmacy == 2 && registration == 1 && health == 1) {
            for (int i = 0; i < data.size(); i++) {
                if ((scheduleYear + "-" + scheduleMonth + "-" + scheduleDay).equals(data.get(i).getDate()) && position < daysOfMonth + dayOfWeek && position >= dayOfWeek) {
                    if (data.get(i).getRegistrationId() != 0) {

                        if (mSelect == position) {
                            textView.setBackgroundResource(R.drawable.consulting_onclick6);
                        } else {
                            textView.setBackgroundResource(R.drawable.consulting6);
                        }

                    } else if (data.get(i).getHealthId() != 0 && data.get(i).getRegistrationId() == 0) {
                        if (mSelect == position) {
                            textView.setBackgroundResource(R.drawable.consulting_onclick7);
                        } else {
                            textView.setBackgroundResource(R.drawable.consulting7);
                        }
                    }
                }

            }
        }

        else if (pharmacy == 1 && registration == 1 && health == 2) {
            for (int i = 0; i < data.size(); i++) {
                if ((scheduleYear + "-" + scheduleMonth + "-" + scheduleDay).equals(data.get(i).getDate()) && position < daysOfMonth + dayOfWeek && position >= dayOfWeek) {
                    if ((data.get(i).getRemindId() != 0 && data.get(i).getRegistrationId() == 0 && data.get(i).getHealthId() == 0) || (data.get(i).getHealthId() != 0 && data.get(i).getRegistrationId() == 0 && data.get(i).getRemindId() != 0)) {
                        if (mSelect == position) {
                            textView.setBackgroundResource(R.drawable.consulting_onclick9);
                        } else {
                            textView.setBackgroundResource(R.drawable.consulting9);
                        }
                    } else if (data.get(i).getRegistrationId() != 0 && data.get(i).getRemindId() == 0) {
                        if (mSelect == position) {
                            textView.setBackgroundResource(R.drawable.consulting_onclick6);
                        } else {
                            textView.setBackgroundResource(R.drawable.consulting6);
                        }
                    } else if (data.get(i).getRegistrationId() != 0 && data.get(i).getRemindId() != 0) {
                        if (mSelect == position) {
                            textView.setBackgroundResource(R.drawable.consulting_onclick3);
                        } else {
                            textView.setBackgroundResource(R.drawable.consulting3);
                        }
                    }

                }
            }
        }


        else if (pharmacy == 1 && registration == 2 && health == 1) {
            for (int i = 0; i < data.size(); i++) {
                if ((scheduleYear + "-" + scheduleMonth + "-" + scheduleDay).equals(data.get(i).getDate()) && position < daysOfMonth + dayOfWeek && position >= dayOfWeek) {
                    if ((data.get(i).getRemindId() != 0 && data.get(i).getRegistrationId() == 0 && data.get(i).getHealthId() == 0) || (data.get(i).getRemindId() != 0 && data.get(i).getRegistrationId() != 0 && data.get(i).getHealthId() == 0)) {
                        if (mSelect == position) {
                            textView.setBackgroundResource(R.drawable.consulting_onclick9);
                        } else {
                            textView.setBackgroundResource(R.drawable.consulting9);
                        }
                    } else if (data.get(i).getHealthId() != 0 && data.get(i).getRemindId() == 0) {
                        if (mSelect == position) {
                            textView.setBackgroundResource(R.drawable.consulting_onclick7);
                        } else {
                            textView.setBackgroundResource(R.drawable.consulting7);
                        }
                    } else if (data.get(i).getHealthId() != 0 && data.get(i).getRemindId() != 0) {
                        if (mSelect == position) {
                            textView.setBackgroundResource(R.drawable.consulting_onclick8);
                        } else {
                            textView.setBackgroundResource(R.drawable.consulting8);
                        }
                    }
                }

            }
        }

        else {

            for (int i = 0; i < data.size(); i++) {

                if ((scheduleYear + "-" + scheduleMonth + "-" + scheduleDay).equals(data.get(i).getDate()) && position < daysOfMonth + dayOfWeek && position >= dayOfWeek) {

                    if ((data.get(i).getHealthId() != 0 && data.get(i).getRegistrationId() != 0 && data.get(i).getRemindId() != 0) || (data.get(i).getHealthId() == 0 && data.get(i).getRegistrationId() != 0 && data.get(i).getRemindId() != 0)) {
                        if (mSelect == position) {
                            textView.setBackgroundResource(R.drawable.consulting_onclick3);
                        } else {
                            textView.setBackgroundResource(R.drawable.consulting3);
                        }
                    } else if ((data.get(i).getRegistrationId() != 0 && data.get(i).getRemindId() == 0 && data.get(i).getHealthId() != 0) || (data.get(i).getRegistrationId() != 0 && data.get(i).getRemindId() == 0 && data.get(i).getHealthId() == 0)) {
                        if (mSelect == position) {
                            textView.setBackgroundResource(R.drawable.consulting_onclick6);
                        } else {
                            textView.setBackgroundResource(R.drawable.consulting6);
                        }
                    } else if (data.get(i).getHealthId() != 0 && data.get(i).getRemindId() == 0 && data.get(i).getRegistrationId() == 0) {
                        if (mSelect == position) {
                            textView.setBackgroundResource(R.drawable.consulting_onclick7);
                        } else {
                            textView.setBackgroundResource(R.drawable.consulting7);
                        }
                    } else if (data.get(i).getHealthId() != 0 && data.get(i).getRemindId() != 0 && data.get(i).getRegistrationId() == 0) {
                        if (mSelect == position) {
                            textView.setBackgroundResource(R.drawable.consulting_onclick8);
                        } else {
                            textView.setBackgroundResource(R.drawable.consulting8);
                        }
                    } else if (data.get(i).getRemindId() != 0 && data.get(i).getHealthId() == 0 && data.get(i).getRegistrationId() == 0) {
                        if (mSelect == position) {
                            textView.setBackgroundResource(R.drawable.consulting_onclick9);
                        } else {
                            textView.setBackgroundResource(R.drawable.consulting9);
                        }
                    }
                }
            }
        }

        if (position == 0) {
            textView.setBackgroundColor(android.graphics.Color.parseColor("#ffffff"));
        }

        return convertView;
    }



    public void getCalendar(int year, int month) {
        isLeapyear = sc.isLeapYear(year);
        daysOfMonth = sc.getDaysOfMonth(isLeapyear, month);
        dayOfWeek = sc.getWeekdayOfMonth(year, month);
        lastDaysOfMonth = sc.getDaysOfMonth(isLeapyear, month - 1);
        getweek(year, month);
    }


    private void getweek(int year, int month) {

        for (int i = 0; i < dayNumber.length; i++) {
            if (i < daysOfMonth + dayOfWeek) {
                String day = String.valueOf(i - dayOfWeek + 1);
                dayNumber[i] = day;

                if (sys_year.equals(String.valueOf(year)) && sys_month.equals(String.valueOf(month)) && sys_day.equals(day)) {

                    currentFlag = i;
                }
                setShowYear(String.valueOf(year));
                setShowMonth(String.valueOf(month));
            } else {
                dayNumber[i] = "";
            }
        }
    }


    public String getDateByClickItem(int position) {
        return dayNumber[position];
    }


    public int getStartPositon() {
        return dayOfWeek + 7;
    }


    public int getEndPosition() {
        return (dayOfWeek + daysOfMonth + 7) - 1;
    }

    public String getShowYear() {
        return showYear;
    }

    public void setShowYear(String showYear) {
        this.showYear = showYear;
    }

    public String getShowMonth() {
        return showMonth;
    }

    public void setShowMonth(String showMonth) {
        this.showMonth = showMonth;
    }

    public String getAnimalsYear() {
        return animalsYear;
    }

    public void setAnimalsYear(String animalsYear) {
        this.animalsYear = animalsYear;
    }

    public String getLeapMonth() {
        return leapMonth;
    }

    public void setLeapMonth(String leapMonth) {
        this.leapMonth = leapMonth;
    }

}
