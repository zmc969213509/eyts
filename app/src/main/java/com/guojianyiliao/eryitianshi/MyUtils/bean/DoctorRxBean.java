package com.guojianyiliao.eryitianshi.MyUtils.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/3/24.
 */

public class DoctorRxBean implements Serializable{


    /**
     * name : 陈昌辉
     * username : 13912345678
     * icon : http://i1.piimg.com/578949/7160550529bf9224.png
     * title : 主任医师
     * section : 小儿神经
     * hospital : 四川省人民医院儿科主任
     * chatCost : 25
     * callCost : 25
     * seniority : 中华医学会围产医学分会委员
     中华医学会临床流行病学分会委员
     《中国小儿急救医学》杂志委员
     四川省医学会儿科学会副主任委员兼新生儿学组副组长
     四川省临床流行病学学会副主任委员
     《实用医院临床杂志》编委
     * bio : 1986年华西医科大学硕士研究生毕业， 从事儿科医疗、教学及科研工作二十余年，能够熟练诊治儿科各种疾病，尤其擅长新生儿黄疸、缺氧缺血性脑损伤、免疫系统疾病及各种危急重症的诊治。共获四川省科技进步奖６项，在国内外杂志公开发表论文80余篇，
     参编《胎儿和新生儿脑损伤（精装）》（上海科技教育出版社2008年）、《中枢神经系统疾病的基因治疗》（科学出版社2005年）等专业学术著作5部。多次参加国际和国内儿科会议，并进行学术交流和讲座。连续多年承担四川省医学会围产医学专业委员会、儿科专业委员会、新生儿学组专题讲课任务。
     * adept : 新生儿黄疸、缺氧缺血性脑损伤、免疫系统疾病及各种危急重症的诊治
     * commentCount : 0
     */

    public String name;
    private String username;

    public String icon;
    @SerializedName("title")
    public String title;
    private String section;
    private String hospital;
    private String chatCost;
    private String callCost;
    private String seniority;
    private String bio;
    private String adept;
    private String commentCount;
}
