package com.guojianyiliao.eryitianshi.MyUtils.bean;

import java.io.Serializable;

/**
 * Created by zmc on 2017/6/21.
 * 用户挂号实体
 */

public class zmc_UserGHInfo implements Serializable{

    /**就诊人年龄**/
    String age;
    /**订单创建时间**/
    String createtime;
    /**预约医生姓名**/
    String docname;
    /**预约医生id**/
    String doctorid;
    /**就诊人性别**/
    String gender;
    /**医生是否是专家**/
    int isexpert;
    /**是否评价了  0：未评价**/
    int iscommented;
    /**预约费用**/
    String money;
    /**就诊人姓名**/
    String name;
    /**就诊人电话**/
    String phone;
    /**订单id**/
    String regid;
    /**预约时间**/
    String reservationdate;
    /**医生科室**/
    String section;
    /**科室id**/
    String sectionid;
    /**订单状态  0：未确认  1：已确认  2：已取消**/
    String status;
    /**用户id**/
    String userid;
    /**医生实体**/
    zmc_docInfo doctor;

    public void setIscommented(int iscommented) {
        this.iscommented = iscommented;
    }

    public int getIscommented() {
        return iscommented;
    }

    public zmc_docInfo getDocInfo() {
        return doctor;
    }

    public String getAge() {
        return age;
    }

    public String getCreatetime() {
        return createtime;
    }

    public String getDocname() {
        return docname;
    }

    public String getDoctorid() {
        return doctorid;
    }

    public String getGender() {
        return gender;
    }

    public int getIsexpert() {
        return isexpert;
    }

    public String getMoney() {
        return money;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getRegid() {
        return regid;
    }

    public String getReservationdate() {
        return reservationdate;
    }

    public String getSection() {
        return section;
    }

    public String getSectionid() {
        return sectionid;
    }

    public String getStatus() {
        return status;
    }

    public String getUserid() {
        return userid;
    }
}
