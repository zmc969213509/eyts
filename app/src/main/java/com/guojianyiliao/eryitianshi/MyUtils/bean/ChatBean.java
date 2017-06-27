package com.guojianyiliao.eryitianshi.MyUtils.bean;

/**
 * Created by zmc on 2017/5/18.
 * 病者患例 病患与医生聊天实体
 */

public class ChatBean {

    String icon;
    String name;
    String text;

    public ChatBean(String icon, String name, String text) {
        this.icon = icon;
        this.name = name;
        this.text = text;
    }

    public String getIcon() {
        return icon;
    }

    public String getName() {
        return name;
    }

    public String getText() {
        return text;
    }
}
