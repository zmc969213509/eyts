package com.guojianyiliao.eryitianshi.Data.entity;

/**
 * Created by Administrator on 2016/10/29 0029.
 */
public class DiseaseBanner {

    /**
     * categorycode : 1
     * cover : http://ohpgig5p4.bkt.clouddn.com/banner1.jpg
     * cyclopediaid : 54
     * id : eaefee28cff64ce08be72e8a17896427
     * site :
     */

    private String cover;
    private String cyclopediaid;
    private String id;
    private String site;

    public DiseaseBanner(String cover, String cyclopediaid, String id, String site) {
        this.cover = cover;
        this.cyclopediaid = cyclopediaid;
        this.id = id;
        this.site = site;
    }


    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getCyclopediaid() {
        return cyclopediaid;
    }

    public void setCyclopediaid(String cyclopediaid) {
        this.cyclopediaid = cyclopediaid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    @Override
    public String toString() {
        return "DiseaseBanner{" +
                ", cover='" + cover + '\'' +
                ", cyclopediaid='" + cyclopediaid + '\'' +
                ", id='" + id + '\'' +
                ", site='" + site + '\'' +
                '}';
    }
}
