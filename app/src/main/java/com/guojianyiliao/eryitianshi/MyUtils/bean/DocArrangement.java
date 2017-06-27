package com.guojianyiliao.eryitianshi.MyUtils.bean;

import java.io.Serializable;

/**
 * Created by zmc on 2017/5/17.
 * 医生值班表实体
 */

public class DocArrangement implements Serializable{

    /****/
    private String arrid;
    /***医生id*/
    private String docid;
    /**星期一值班情况**/
    private int monday = -1;
    /**星期二值班情况**/
    private int tuesday = -1;
    /**星期三值班情况**/
    private int wensday = -1;
    /**星期四值班情况**/
    private int thursday = -1;
    /**星期五值班情况**/
    private int friday = -1;
    /**星期六值班情况**/
    private int saturday = -1;
    /**星期天值班情况**/
    private int sunday = -1;
    /**备注**/
    private String memo;

    public String getArrid() {
        return arrid;
    }

    public String getDocid() {
        return docid;
    }

    public int getMonday() {
        return monday;
    }

    public int getTuesday() {
        return tuesday;
    }

    public int getWensday() {
        return wensday;
    }

    public int getThursday() {
        return thursday;
    }

    public int getFriday() {
        return friday;
    }

    public int getSaturday() {
        return saturday;
    }

    public int getSunday() {
        return sunday;
    }

    public String getMemo() {
        return memo;
    }

    @Override
    public String toString() {
        return "DocArrangement{" +
                "arrid='" + arrid + '\'' +
                ", docid='" + docid + '\'' +
                ", monday=" + monday +
                ", tuesday=" + tuesday +
                ", wensday=" + wensday +
                ", thursday=" + thursday +
                ", friday=" + friday +
                ", saturday=" + saturday +
                ", sunday=" + sunday +
                ", memo='" + memo + '\'' +
                '}';
    }
}
