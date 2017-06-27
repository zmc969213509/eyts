package com.guojianyiliao.eryitianshi.MyUtils.bean;

/**
 * Created by zmc on 2017/5/26.
 * 与用户聊天的医生实体
 */

public class zmc_DocChat {

    String docName;
    String docId;
    String docIcon;
    String userName;


    public zmc_DocChat(String docName, String docId, String docIcon, String userName) {
        this.docName = docName;
        this.docId = docId;
        this.docIcon = docIcon;
        this.userName = userName;
    }

    public String getDocName() {
        return docName;
    }

    public String getDocId() {
        return docId;
    }

    public String getDocIcon() {
        return docIcon;
    }

    public String getUserName() {
        return userName;
    }
}
