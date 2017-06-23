package com.guojianyiliao.eryitianshi.MyUtils.bean;

import java.io.Serializable;

/**
 * 用药提醒的实体类
 */

public class DrugRemindBean implements Serializable {

    private long addtime;
    private String content1;
    private String content2;
    private String content3;
    private long enddate;
    private long reminddate;
    private String remindid;
    private String time1;
    private String time2;
    private String time3;
    private String userid;

    @Override
    public String toString() {
        return "DrugRemindBean{" +
                "addtime=" + addtime +
                ", content1='" + content1 + '\'' +
                ", content2='" + content2 + '\'' +
                ", content3='" + content3 + '\'' +
                ", enddate=" + enddate +
                ", reminddate=" + reminddate +
                ", remindid='" + remindid + '\'' +
                ", time1='" + time1 + '\'' +
                ", time2='" + time2 + '\'' +
                ", time3='" + time3 + '\'' +
                ", userid='" + userid + '\'' +
                '}';
    }

    public long getAddtime() {
        return addtime;
    }

    public String getContent1() {
        return content1;
    }

    public String getContent2() {
        return content2;
    }

    public String getContent3() {
        return content3;
    }

    public long getEnddate() {
        return enddate;
    }

    public long getReminddate() {
        return reminddate;
    }

    public String getRemindid() {
        return remindid;
    }

    public String getTime1() {
        return time1;
    }

    public String getTime2() {
        return time2;
    }

    public String getTime3() {
        return time3;
    }

    public String getUserid() {
        return userid;
    }
}
