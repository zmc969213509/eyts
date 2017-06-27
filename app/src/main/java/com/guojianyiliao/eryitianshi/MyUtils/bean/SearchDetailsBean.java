package com.guojianyiliao.eryitianshi.MyUtils.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zmc on 2017/5/16.
 *
 * 搜索详情 实体类
 */

public class SearchDetailsBean{

    /**疾病集合**/
    private List<Diseases> diseases;
    /**医生集合**/
    private List<Doctors> doctors;
    /**文章集合 (该版本暂不需要)**/
    private List<Sections> sections;

    @Override
    public String toString() {
        return "SearchDetailsBean{" +
                "diseases=" + diseases +
                ", doctors=" + doctors +
                ", sections=" + sections +
                '}';
    }

    public List<Diseases> getDiseases() {
        return diseases;
    }

    public List<Doctors> getDoctors() {
        return doctors;
    }

    public List<Sections> getSections() {
        return sections;
    }

    /**
     * 疾病类
     */
    public class Diseases{
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
        /**与用户进行沟通的医生id**/
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

        @Override
        public String toString() {
            return "Diseases{" +
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
                    '}';
        }
    }

    /**
     * 医生类
     */
    public class Doctors{
        /**医生id**/
        String doctorid;
        /**医生名字**/
        String name;
        /**极光推送对应医生的标识**/
        String username;
        /**密码**/
        String password;
        /**头像**/
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
        /**是否是专家  1：专家  0：非专家**/
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

    /**
     * 文章类
     */
    public class Sections{
        String sectionid;
        String name;
        String icon1;
        String icon2;
        String memo;

        public String getSectionid() {
            return sectionid;
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
    }


//    /**疾病集合**/
//    private List<diseases> diseases_list;
//    /**医生集合**/
//    private List<doctors> doctors_list;
//    /**文章集合 (该版本暂不需要)**/
//    private List<?> sections_list;
//
//
//    public List<diseases> getDiseases_list() {
//        return diseases_list;
//    }
//
//    public List<doctors> getDoctors_list() {
//        return doctors_list;
//    }
//
//    public List<?> getSections_list() {
//        return sections_list;
//    }
//
//    /**
//     * 疾病类
//     */
//    public class diseases{
//        /**疾病id**/
//        String DisId;
//        /**疾病名称**/
//        String Name;
//        /**疾病介绍**/
//        String Bio;
//        /**用户头像**/
//        String UserIcon;
//        /**用户姓名**/
//        String UserName;
//        /**用户提问**/
//        String UserPutQuestion;
//        /**与用户进行沟通的医生id**/
//        String DoctorIcon;
//        /**与用户进行沟通的医生姓名**/
//        String DoctorName;
//        /**医生回答**/
//        String DoctorAnswerQuestion;
//        /**症状**/
//        String Symptom;
//        /**造成因数**/
//        String Cure;
//        /**提示**/
//        String Prompt;
//        /**疾病对应科室id**/
//        String SectionId;
//        /**疾病对应科室名称**/
//        String SecName;
//        /**该疾病推荐医生id**/
//        String DoctorId;
//        /**该疾病推荐医生名称**/
//        String DocName;
//        /**是不是常见疾病  0代表不是  1代表是**/
//        String isCommon;
//
//
//        public String getDisId() {
//            return DisId;
//        }
//
//        public String getName() {
//            return Name;
//        }
//
//        public String getBio() {
//            return Bio;
//        }
//
//        public String getUserIcon() {
//            return UserIcon;
//        }
//
//        public String getUserName() {
//            return UserName;
//        }
//
//        public String getUserPutQuestion() {
//            return UserPutQuestion;
//        }
//
//        public String getDoctorIcon() {
//            return DoctorIcon;
//        }
//
//        public String getDoctorName() {
//            return DoctorName;
//        }
//
//        public String getDoctorAnswerQuestion() {
//            return DoctorAnswerQuestion;
//        }
//
//        public String getSymptom() {
//            return Symptom;
//        }
//
//        public String getCure() {
//            return Cure;
//        }
//
//        public String getPrompt() {
//            return Prompt;
//        }
//
//        public String getSectionId() {
//            return SectionId;
//        }
//
//        public String getSecName() {
//            return SecName;
//        }
//
//        public String getDoctorId() {
//            return DoctorId;
//        }
//
//        public String getDocName() {
//            return DocName;
//        }
//
//        public String getIsCommon() {
//            return isCommon;
//        }
//    }
//
//    /**
//     * 医生类
//     */
//    public class doctors{
//        /**医生id**/
//        String DoctorId;
//        /**医生名字**/
//        String Name;
//        /**极光推送对应医生的标识**/
//        String UserName;
//        /**密码**/
//        String Password;
//        /**头像**/
//        String Icon;
//        /**科室**/
//        String Section;
//        /****/
//        String Hospital;
//        /**级别**/
//        String Title;
//        /**聊天费用**/
//        String ChatCost;
//        /**资历，排行**/
//        String Seniority;
//        /**通话费用**/
//        String callcost;
//        /**个人简介**/
//        String Bio;
//        /**擅长**/
//        String Adept;
//        /**评论数**/
//        int CommentCount;
//        /**城市**/
//        String City;
//        /**科室id**/
//        String SectionId;
//        /**级别id**/
//        String TitleId;
//        /**是否是专家**/
//        int isExpert;
//        /**是否可以在线**/
//        int canOnline;
//
//        public String getDoctorId() {
//            return DoctorId;
//        }
//
//        public String getName() {
//            return Name;
//        }
//
//        public String getUserName() {
//            return UserName;
//        }
//
//        public String getPassword() {
//            return Password;
//        }
//
//        public String getIcon() {
//            return Icon;
//        }
//
//        public String getSection() {
//            return Section;
//        }
//
//        public String getHospital() {
//            return Hospital;
//        }
//
//        public String getTitle() {
//            return Title;
//        }
//
//        public String getChatCost() {
//            return ChatCost;
//        }
//
//        public String getSeniority() {
//            return Seniority;
//        }
//
//        public String getCallcost() {
//            return callcost;
//        }
//
//        public String getBio() {
//            return Bio;
//        }
//
//        public String getAdept() {
//            return Adept;
//        }
//
//        public int getCommentCount() {
//            return CommentCount;
//        }
//
//        public String getCity() {
//            return City;
//        }
//
//        public String getSectionId() {
//            return SectionId;
//        }
//
//        public String getTitleId() {
//            return TitleId;
//        }
//
//        public int getIsExpert() {
//            return isExpert;
//        }
//
//        public int getCanOnline() {
//            return canOnline;
//        }
//    }
//
//    /**
//     * 文章类
//     */
//    public class sections{
//
//    }

}
