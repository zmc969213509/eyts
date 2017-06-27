package com.guojianyiliao.eryitianshi.MyUtils.bean;

import java.io.Serializable;

/**
 * Created by zmc on 2017/6/3.
 * 医生详细信息
 */

public class zmc_docInfo implements Serializable{

    /**医生id**/
    private String  doctorid;
    /**医生名字**/
    private String  name;
    /**极光推送对应医生的标识**/
    private String  username;
    /**密码**/
    private String  password;
    /**头像**/
    private String  icon;
    /**级别**/
    private String  title;
    /**科室id**/
    private String  sectionid;
    /**科室**/
    private String  section;
    /**所属医院**/
    private String  hospital;
    /**图文问诊费用**/
    private String  chatcost;
    /**是否可以预约挂号  0：可以预约挂号  1：不可以预约挂号  2：只提供电话预约**/
    private String  callcost;
    /**资历，排行**/
    private String  seniority;
    /**评论数**/
    private int commentcount;
    /**城市**/
    private String  city;
    /**级别id**/
    private String  titleid;
    /**是否是专家  1：专家  0：非专家**/
    private int isexpert;
    /**是否可以在线**/
    private int canonline;
    /**个人简介**/
    private String  bio;
    /**擅长**/
    private String  adept;
    /**排班信息**/
    private DocArrangement arrangement;
    /**是否收藏**/
    private boolean collected;


    public String getDoctorid() {
        return doctorid;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getIcon() {
        return icon;
    }

    public String getTitle() {
        return title;
    }

    public String getSectionid() {
        return sectionid;
    }

    public String getSection() {
        return section;
    }

    public String getHospital() {
        return hospital;
    }

    public String getChatcost() {
        return chatcost;
    }

    public String getCallcost() {
        return callcost;
    }

    public String getSeniority() {
        return seniority;
    }

    public int getCommentcount() {
        return commentcount;
    }

    public String getCity() {
        return city;
    }

    public String getTitleid() {
        return titleid;
    }

    public int getIsexpert() {
        return isexpert;
    }

    public int getCanonline() {
        return canonline;
    }

    public String getBio() {
        return bio;
    }

    public String getAdept() {
        return adept;
    }

    public DocArrangement getArrangement() {
        return arrangement;
    }

    public boolean isCollected() {
        return collected;
    }
}
