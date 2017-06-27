package com.guojianyiliao.eryitianshi.MyUtils.bean;


import java.util.List;

/**
 * Created by zmc on 2017/5/8.
 *
 * 搜索结果
 */

public class SearchResult {

    List<diseases> diseases;
    List<doctor> doctors;
    List<sections> sectionses;

    public List<diseases> getDiseases() {
        return diseases;
    }

    public List<doctor> getDoctors() {
        return doctors;
    }

    public List<sections> getSectionses() {
        return sectionses;
    }

    /**
     * 搜索结果疾病类
     */
    public static class diseases{


    }

    /**
     * 搜索结果医生类
     */
    public static class doctor{

    }

    /**
     * 搜索结果文章类
     */
    public static class sections{

    }
}
