package com.guojianyiliao.eryitianshi.MyUtils.bean;

/**
 * Created by zmc on 2017/5/24.
 * 在线问诊 医生与病者聊天实体
 */

public class zmc_ChatBean {

    /**医生账号**/
    String docName;
    /**患者账号**/
    String disName;
    /**文本聊天信息**/
    String Content;
    /**图像聊天资源地址**/
    String filePath;
    /**聊天时间**/
    String Time;
    /**发送者标识  1：患者发送消息  2：医生发送消息**/
    String sendFlag;
    /**消息发送状态标记  0：正在发送   1：发送成功   -1：发送失败**/
    String msgFlag;

    public zmc_ChatBean(String docName, String disName, String content, String filePath, String time, String sendFlag, String msgFlag) {
        this.docName = docName;
        this.disName = disName;
        Content = content;
        this.filePath = filePath;
        Time = time;
        this.sendFlag = sendFlag;
        this.msgFlag = msgFlag;
    }

    public void setMsgFlag(String msgFlag) {
        this.msgFlag = msgFlag;
    }

    public String getDocName() {
        return docName;
    }

    public String getDisName() {
        return disName;
    }

    public String getContent() {
        return Content;
    }

    public String getFilePath() {
        return filePath;
    }

    public String getTime() {
        return Time;
    }

    public String getSendFlag() {
        return sendFlag;
    }

    public String getMsgFlag() {
        return msgFlag;
    }

    @Override
    public String toString() {
        return "zmc_ChatBean{" +
                "docName='" + docName + '\'' +
                ", disName='" + disName + '\'' +
                ", Content='" + Content + '\'' +
                ", filePath='" + filePath + '\'' +
                ", Time='" + Time + '\'' +
                ", sendFlag='" + sendFlag + '\'' +
                '}';
    }
}
