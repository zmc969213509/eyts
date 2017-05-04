package com.guojianyiliao.eryitianshi.MyUtils.bean;

/**
 * Created by Administrator on 2017/3/21.
 */
public class FillHot {

    private String site;
    private String id;
    private String title;

    public FillHot(String site, String id, String title) {
        this.site = site;
        this.id = id;
        this.title = title;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "FillHot{" +
                "site='" + site + '\'' +
                ", id='" + id + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
