package com.guojianyiliao.eryitianshi.MyUtils.bean;

/**
 * 获取用户收藏的文章
 * jnluo
 */

public class UserCollectBean {



    public String ctime;
    public String cyclopediaid;
    public String id;
    public String userid;
    private HotTalkBean cyclopedia;


    public String getCtime() {
        return ctime;
    }

    public String getCyclopediaid() {
        return cyclopediaid;
    }

    public String getId() {
        return id;
    }

    public String getUserid() {
        return userid;
    }

    public HotTalkBean getCyclopedia() {
        return cyclopedia;
    }
}
