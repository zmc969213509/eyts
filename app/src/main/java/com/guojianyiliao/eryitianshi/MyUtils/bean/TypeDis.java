package com.guojianyiliao.eryitianshi.MyUtils.bean;

import java.util.List;

/**
 * Created by zmc on 2017/5/22.
 * 所有疾病 实体类
 */

public class TypeDis{

    String sectionId;
    String name;
    String icon1;
    String icon2;
    String memo;
    List<SearchDetailsBean.Diseases> disList;

    public String getSectionId() {
        return sectionId;
    }

    public String getName() {
        return name;
    }

    public String getIcon1() {
        return icon1;
    }

    public String getIcon2() {
        return icon2;
    }

    public String getMemo() {
        return memo;
    }

    public List<SearchDetailsBean.Diseases> getDisList() {
        return disList;
    }
}


