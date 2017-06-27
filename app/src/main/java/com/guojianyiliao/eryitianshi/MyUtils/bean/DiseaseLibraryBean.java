package com.guojianyiliao.eryitianshi.MyUtils.bean;

/**
 * Created by zmc on 2017/5/18.
 * 疾病详情
 */

public class DiseaseLibraryBean {

    /**疾病id**/
    String disid;
    /**疾病名称**/
    String name;
    /**疾病介绍**/
    String bio;
    /**用户头像**/
    String usericon;
    /**用户姓名**/
    String username;
    /**用户提问**/
    String userputquestion;
    /**与用户进行沟通的医生头像**/
    String doctoricon;
    /**与用户进行沟通的医生姓名**/
    String doctorname;
    /**医生回答**/
    String doctoranswerquestion;
    /**症状**/
    String symptom;
    /**造成因数**/
    String cure;
    /**提示**/
    String prompt;
    /**疾病对应科室id**/
    String sectionid;
    /**疾病对应科室名称**/
    String secname;
    /**该疾病推荐医生id**/
    String doctorid;
    /**该疾病推荐医生名称**/
    String docname;
    /**是不是常见疾病  0代表不是  1代表是**/
    String iscommon;
    /**推荐医生实体**/
    Doctors doctor;

    public String getDisid() {
        return disid;
    }

    public String getName() {
        return name;
    }

    public String getBio() {
        return bio;
    }

    public String getUsericon() {
        return usericon;
    }

    public String getUsername() {
        return username;
    }

    public String getUserputquestion() {
        return userputquestion;
    }

    public String getDoctoricon() {
        return doctoricon;
    }

    public String getDoctorname() {
        return doctorname;
    }

    public String getDoctoranswerquestion() {
        return doctoranswerquestion;
    }

    public String getSymptom() {
        return symptom;
    }

    public String getCure() {
        return cure;
    }

    public String getPrompt() {
        return prompt;
    }

    public String getSectionid() {
        return sectionid;
    }

    public String getSecname() {
        return secname;
    }

    public String getDoctorid() {
        return doctorid;
    }

    public String getDocname() {
        return docname;
    }

    public String getIscommon() {
        return iscommon;
    }

    public Doctors getDoctors() {
        return doctor;
    }

    /**
     * 推荐医生类
     */
    public class Doctors{
        /**推荐医生id**/
        String doctorid;
        /**推荐医生名字**/
        String name;
        /**极光推送对应医生的标识**/
        String username;
        /**密码**/
        String password;
        /**推荐医生头像**/
        String icon;
        /**科室**/
        String section;
        /****/
        String hospital;
        /**级别**/
        String title;
        /**聊天费用**/
        String chatcost;
        /**资历，排行**/
        String seniority;
        /**通话费用**/
        String callcost;
        /**个人简介**/
        String bio;
        /**擅长**/
        String adept;
        /**评论数**/
        int commentcount;
        /**城市**/
        String city;
        /**科室id**/
        String sectionid;
        /**级别id**/
        String titleid;
        /**是否是专家**/
        int isexpert;
        /**是否可以在线**/
        int canonline;

        public String getDoctorid() {
            return doctorid;
        }

        public String getName() {
            return name;
        }

        public String getUsername() {
            return username;
        }

        public String getPassword() {
            return password;
        }

        public String getIcon() {
            return icon;
        }

        public String getSection() {
            return section;
        }

        public String getHospital() {
            return hospital;
        }

        public String getTitle() {
            return title;
        }

        public String getChatcost() {
            return chatcost;
        }

        public String getSeniority() {
            return seniority;
        }

        public String getCallcost() {
            return callcost;
        }

        public String getBio() {
            return bio;
        }

        public String getAdept() {
            return adept;
        }

        public int getCommentcount() {
            return commentcount;
        }

        public String getCity() {
            return city;
        }

        public String getSectionid() {
            return sectionid;
        }

        public String getTitleid() {
            return titleid;
        }

        public int getIsexpert() {
            return isexpert;
        }

        public int getCanonline() {
            return canonline;
        }
    }

    @Override
    public String toString() {
        return "DiseaseLibraryBean{" +
                "disid='" + disid + '\'' +
                ", name='" + name + '\'' +
                ", bio='" + bio + '\'' +
                ", usericon='" + usericon + '\'' +
                ", username='" + username + '\'' +
                ", userputquestion='" + userputquestion + '\'' +
                ", doctoricon='" + doctoricon + '\'' +
                ", doctorname='" + doctorname + '\'' +
                ", doctoranswerquestion='" + doctoranswerquestion + '\'' +
                ", symptom='" + symptom + '\'' +
                ", cure='" + cure + '\'' +
                ", prompt='" + prompt + '\'' +
                ", sectionid='" + sectionid + '\'' +
                ", secname='" + secname + '\'' +
                ", doctorid='" + doctorid + '\'' +
                ", docname='" + docname + '\'' +
                ", iscommon='" + iscommon + '\'' +
                ", doctors=" + doctor +
                '}';
    }
}
