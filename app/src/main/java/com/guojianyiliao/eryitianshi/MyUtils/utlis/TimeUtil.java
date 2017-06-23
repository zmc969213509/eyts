package com.guojianyiliao.eryitianshi.MyUtils.utlis;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 *
 */
public class TimeUtil {
    public final static String FORMAT_YEAR = "yyyy";
    public final static String FORMAT_MONTH_DAY = "MM月dd日";

    public final static String FORMAT_DATE = "yyyy-MM-dd";
    public final static String FORMAT_TIME = "HH:mm";
    public final static String FORMAT_MONTH_DAY_TIME = "MM月dd日  HH:mm";

    public final static String FORMAT_DATE_TIME = "yyyy-MM-dd HH:mm";
    public final static String FORMAT_DATE1_TIME = "yyyy/MM/dd HH:mm";
    public final static String FORMAT_DATE_TIME_SECOND = "yyyy/MM/dd HH:mm:ss";

    private static SimpleDateFormat sdf = new SimpleDateFormat();
    private static final int YEAR = 365 * 24 * 60 * 60;// 年
    private static final int MONTH = 30 * 24 * 60 * 60;// 月
    private static final int DAY = 24 * 60 * 60;// 天
    private static final int HOUR = 60 * 60;// 小时
    private static final int MINUTE = 60;// 分钟


    public static String currectTime_day(long t) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date curDate = new Date(t);
        String time = formatter.format(curDate);
        return time;
    }
    public static String currectTime_day() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date curDate = new Date(System.currentTimeMillis());
        String time = formatter.format(curDate);
        return time;
    }
    public static String currectTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date curDate = new Date(System.currentTimeMillis());
        String time = formatter.format(curDate);
        return time;
    }
    public static String currectTime(long curDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String time = formatter.format(curDate);
        return time;
    }
    public static String currectTimehour(long t) {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        String time = formatter.format(t);
        return time;
    }

    public static String currectTimess() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());
        String time = formatter.format(curDate);
        return time;
    }

    public static String    SJC(String time) {
        // String aaa = "1492671101000";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Long itest = new Long(time.trim());
        Date date = new Date(itest);
        String str = sdf.format(date);
        System.out.println(str);
        return str;
    }

    /**
     * 根据时间戳获取描述性时间，如3分钟前，1天前
     *
     * @param timestamp 时间戳 单位为毫秒
     * @return 时间字符串
     */
    public static String getDescriptionTimeFromTimestamp(long timestamp) {
        long currentTime = System.currentTimeMillis();
        long timeGap = (currentTime - timestamp) / 1000;// 与现在时间相差秒数
        System.out.println("timeGap: " + timeGap);
        String timeStr = null;
        if (timeGap > YEAR) {
            timeStr = timeGap / YEAR + "年前";
        } else if (timeGap > MONTH) {
            timeStr = timeGap / MONTH + "个月前";
        } else if (timeGap > DAY) {// 1天以上
            timeStr = timeGap / DAY + "天前";
        } else if (timeGap > HOUR) {// 1小时-24小时
            timeStr = timeGap / HOUR + "小时前";
        } else if (timeGap > MINUTE) {// 1分钟-59分钟
            timeStr = timeGap / MINUTE + "分钟前";
        } else {// 1秒钟-59秒钟
            timeStr = "刚刚";
        }
        return timeStr;
    }

    public static String getDescriptionTimeFromTimestamp_2(long timestamp) {
        long currentTime = System.currentTimeMillis();
        long timeGap = (currentTime - timestamp) / 1000;// 与现在时间相差秒数
        System.out.println("timeGap: " + timeGap);
        String timeStr = null;
        if (timeGap > YEAR) {
            timeStr = timeGap / YEAR + "年";
        } else if (timeGap > MONTH) {
            timeStr = timeGap / MONTH + "个月";
        } else if (timeGap > DAY) {// 1天以上
            timeStr = timeGap / DAY + "天";
        } else if (timeGap > HOUR) {// 1小时-24小时
            timeStr = timeGap / HOUR + "小时";
        } else if (timeGap > MINUTE) {// 1分钟-59分钟
            timeStr = timeGap / MINUTE + "分钟";
        } else {// 1秒钟-59秒钟
            timeStr = "1分钟";
        }
        return timeStr;
    }

    /**
     * 获取时间差
     *
     * @param
     * @return
     */

    public static String getTimeDifference(long startTime, long finishTime) {

        String timeDiff = "";
        if (startTime == 0 || finishTime == 0) {
            return "错误";
        }
        long timeGap = (finishTime - startTime) / 1000; // 秒数

        if (timeGap > DAY) {// 1天以上
            timeDiff = timeGap / DAY + "天";
        } else if (timeGap > HOUR) {// 1小时-24小时
            timeDiff += timeGap / HOUR + "小时";
        } else if (timeGap > MINUTE) {// 1分钟-59分钟
            timeDiff = timeGap / MINUTE + "分钟";
        } else {// 1秒钟-59秒钟
            timeDiff = "1分钟";
        }

        return timeDiff;
    }

    /**
     * 获取与当前时间的时间差
     *
     * @param
     * @return
     */

    public static String getTimeDifference_2(Date startTime) {

        String timeDiff = "";
        if (startTime == null) {
            return "错误";
        }
        long timeGap = (System.currentTimeMillis() - startTime.getTime()) / 1000; // 秒数

        timeDiff = String.format("%02d:%02d:%02d", timeGap / HOUR, (timeGap % HOUR) / MINUTE, (timeGap % HOUR) % MINUTE);

        return timeDiff;
    }

    /**
     * 获取任务用时
     *
     * @param
     * @return
     */

    public static String timeDifference2String(long timedifference) {

        String timeDiff = "";
        long timeGap = timedifference / 1000; // 秒数

        timeDiff = String.format("%02d:%02d:%02d", timeGap / HOUR, (timeGap % HOUR) / MINUTE, (timeGap % HOUR) % MINUTE);

        return timeDiff;
    }

    /**
     * 获取当前日期的指定格式的字符串
     *
     * @param format 指定的日期时间格式，若为null或""则使用指定的格式"yyyy-MM-dd HH:MM"
     * @return
     */
    public static String getCurrentTime(String format) {
        if (format == null || format.trim().equals("")) {
            sdf.applyPattern(FORMAT_DATE_TIME);
        } else {
            sdf.applyPattern(format);
        }
        return sdf.format(new Date());
    }

    // date类型转换为String类型
    // formatType格式为yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日 HH时mm分ss秒
    // data Date类型的时间
    public static String dateToString(Date data, String formatType) {
        return new SimpleDateFormat(formatType).format(data);
    }

    // long类型转换为String类型
    // currentTime要转换的long类型的时间
    // formatType要转换的string类型的时间格式
    public static String longToString(long currentTime, String formatType) {
        String strTime = "";
        Date date = longToDate(currentTime, formatType);// long类型转成Date类型
        strTime = dateToString(date, formatType); // date类型转成String
        return strTime;
    }

    // string类型转换为date类型
    // strTime要转换的string类型的时间，formatType要转换的格式yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日
    // HH时mm分ss秒，
    // strTime的时间格式必须要与formatType的时间格式相同
    public static Date stringToDate(String strTime, String formatType) {
        SimpleDateFormat formatter = new SimpleDateFormat(formatType);
        Date date = null;
        try {
            date = formatter.parse(strTime);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return date;
    }

    // long转换为Date类型
    // currentTime要转换的long类型的时间
    // formatType要转换的时间格式yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日 HH时mm分ss秒
    public static Date longToDate(long currentTime, String formatType) {
        Date dateOld = new Date(currentTime); // 根据long类型的毫秒数生命一个date类型的时间
        String sDateTime = dateToString(dateOld, formatType); // 把date类型的时间转换为string
        Date date = stringToDate(sDateTime, formatType); // 把String类型转换为Date类型
        return date;
    }

    // string类型转换为long类型
    // strTime要转换的String类型的时间
    // formatType时间格式
    // strTime的时间格式和formatType的时间格式必须相同
    public static long stringToLong(String strTime, String formatType) {
        Date date = stringToDate(strTime, formatType); // String类型转成date类型
        if (date == null) {
            return 0;
        } else {
            long currentTime = dateToLong(date); // date类型转成long类型
            return currentTime;
        }
    }

    // date类型转换为long类型
    // date要转换的date类型的时间
    public static long dateToLong(Date date) {
        return date.getTime();
    }

    public static String getTime(long time) {
        SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd HH:mm");
        return format.format(new Date(time));
    }

    public static String getHourAndMin(long time) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        return format.format(new Date(time));
    }

    /**
     * 获取聊天时间：因为sdk的时间默认到秒故应该乘1000
     *
     * @param @param  timesamp
     * @param @return
     * @return String
     * @throws
     * @Title: getChatTime
     * @Description: TODO
     */
    public static String getChatTime(long timesamp) {
        long clearTime = timesamp * 1000;
        String result = "";
        SimpleDateFormat sdf = new SimpleDateFormat("dd");
        Date today = new Date(System.currentTimeMillis());
        Date otherDay = new Date(clearTime);
        int temp = Integer.parseInt(sdf.format(today))
                - Integer.parseInt(sdf.format(otherDay));

        switch (temp) {
            case 0:
                result = "今天 " + getHourAndMin(clearTime);
                break;
            case 1:
                result = "昨天 " + getHourAndMin(clearTime);
                break;
            case 2:
                result = "前天 " + getHourAndMin(clearTime);
                break;

            default:
                result = getTime(clearTime);
                break;
        }

        return result;
    }


    public static String getChatTime_2(Date otherDay) {
        String result = "";
        SimpleDateFormat sdf = new SimpleDateFormat("dd");
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        Date today = new Date(System.currentTimeMillis());
        int temp = Integer.parseInt(sdf.format(today)) - Integer.parseInt(sdf.format(otherDay));
        switch (temp) {
            case 0:
                result = "今天 " + format.format(otherDay);
                break;
            case 1:
                result = "昨天 " + format.format(otherDay);
                break;
            case 2:
                result = "前天 " + format.format(otherDay);
                break;
            default:
                result = dateToString(otherDay, FORMAT_MONTH_DAY_TIME);
                break;
        }

        return result;
    }

    /**
     * 传入毫秒数
     *
     * @param time 毫秒数
     * @return yyyy年MM月dd日
     */
    public static String getYearMothDay(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        String datestr = sdf.format(time);
        return datestr;
    }


    /**
     * 毫秒数转换成 00:00:00
     *
     * @param time
     * @return
     */
    public static String formatHoursMinutesSeconds(long time) {
        String str = "";
        long days = time / (1000 * 60 * 60 * 24);
        long hours = (time % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
        long minutes = (time % (1000 * 60 * 60)) / (1000 * 60);
        long seconds = (time % (1000 * 60)) / 1000;
        if (days != 0) {
            str = (days * 24 + hours) + ":" + minutes + ":" + seconds;
        } else {
            str = hours + ":" + minutes + ":" + seconds;
        }
        return str;
        // return days + " days " + hours + " hours " + minutes + " minutes "+ seconds + " seconds ";
    }
}
