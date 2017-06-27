package com.guojianyiliao.eryitianshi.MyUtils.bean;

/**
 * Created by zmc on 2017/5/23.
 * 医生的评论实体
 */

public class DocCommend {

    String commentid;
    String content;
    String ctime;
    String doctorid;
    String userid;

    DiseaseLibraryBean.Doctors doctor;
    EssayInfoBean.EssayAgreeListBean.UserBean appUser;

    public String getCommentid() {
        return commentid;
    }

    public String getContent() {
        return content;
    }

    public String getCtime() {
        return ctime;
    }

    public String getDoctorid() {
        return doctorid;
    }

    public String getUserid() {
        return userid;
    }

    public DiseaseLibraryBean.Doctors getDoctor() {
        return doctor;
    }

    public EssayInfoBean.EssayAgreeListBean.UserBean getAppUser() {
        return appUser;
    }
}
