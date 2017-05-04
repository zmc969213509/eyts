package com.guojianyiliao.eryitianshi.Data.entity;

/**
 * Created by Administrator on 2016/10/13 0013.
 */
public class CouponsBean {

    /**
     * endTime : 2017-03-18 00:00:00
     * id : 51
     * money : 10.0
     * status : 0
     * type : 1
     * userid : 39
     */

    private String endTime;
    private int id;
    private double money;
    private int type;

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "CouponsBean{" +
                "endTime='" + endTime + '\'' +
                ", id=" + id +
                ", money=" + money +
                ", type=" + type +
                '}';
    }
}
