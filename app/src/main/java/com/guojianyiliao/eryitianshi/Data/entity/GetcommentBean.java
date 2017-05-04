package com.guojianyiliao.eryitianshi.Data.entity;

/**
 * jnluo 健康状况回复实体类
 */
public class GetcommentBean {

    /**
     * id : 1
     * healthId : 4
     * docId : 2
     * content : 要来医院检查一下吗
     * doctor : {"id":2,"name":"陈昌辉","username":"13912345678","password":"123456","icon":"http://i1.piimg.com/578949/7160550529bf9224.png","title":"主任医师","section":"小儿神经","hospital":"四川省人民医院儿科主任","seniority":"中华医学会围产医学分会委员\r\n中华医学会临床流行病学分会委员\r\n《中国小儿急救医学》杂志委员\r\n四川省医学会儿科学会副主任委员兼新生儿学组副组长\r\n四川省临床流行病学学会副主任委员\r\n《实用医院临床杂志》编委","bio":"1986年华西医科大学硕士研究生毕业， 从事儿科医疗、教学及科研工作二十余年，能够熟练诊治儿科各种疾病，尤其擅长新生儿黄疸、缺氧缺血性脑损伤、免疫系统疾病及各种危急重症的诊治。共获四川省科技进步奖６项，在国内外杂志公开发表论文80余篇，\n 参编《胎儿和新生儿脑损伤（精装）》（上海科技教育出版社2008年）、《中枢神经系统疾病的基因治疗》（科学出版社2005年）等专业学术著作5部。多次参加国际和国内儿科会议，并进行学术交流和讲座。连续多年承担四川省医学会围产医学专业委员会、儿科专业委员会、新生儿学组专题讲课任务。","adept":"新生儿黄疸、缺氧缺血性脑损伤、免疫系统疾病及各种危急重症的诊治"}
     */

    private int id;
    private int healthId;
    private int docId;
    private String content;
    private DoctorBean doctor;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHealthId() {
        return healthId;
    }

    public void setHealthId(int healthId) {
        this.healthId = healthId;
    }

    public int getDocId() {
        return docId;
    }

    public void setDocId(int docId) {
        this.docId = docId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public DoctorBean getDoctor() {
        return doctor;
    }

    public void setDoctor(DoctorBean doctor) {
        this.doctor = doctor;
    }

    public static class DoctorBean {
        /**
         * id : 2
         * name : 陈昌辉
         * username : 13912345678
         * password : 123456
         * icon : http://i1.piimg.com/578949/7160550529bf9224.png
         * title : 主任医师
         * section : 小儿神经
         * hospital : 四川省人民医院儿科主任
         * seniority : 中华医学会围产医学分会委员
         中华医学会临床流行病学分会委员
         《中国小儿急救医学》杂志委员
         四川省医学会儿科学会副主任委员兼新生儿学组副组长
         四川省临床流行病学学会副主任委员
         《实用医院临床杂志》编委
         * bio : 1986年华西医科大学硕士研究生毕业， 从事儿科医疗、教学及科研工作二十余年，能够熟练诊治儿科各种疾病，尤其擅长新生儿黄疸、缺氧缺血性脑损伤、免疫系统疾病及各种危急重症的诊治。共获四川省科技进步奖６项，在国内外杂志公开发表论文80余篇，
         参编《胎儿和新生儿脑损伤（精装）》（上海科技教育出版社2008年）、《中枢神经系统疾病的基因治疗》（科学出版社2005年）等专业学术著作5部。多次参加国际和国内儿科会议，并进行学术交流和讲座。连续多年承担四川省医学会围产医学专业委员会、儿科专业委员会、新生儿学组专题讲课任务。
         * adept : 新生儿黄疸、缺氧缺血性脑损伤、免疫系统疾病及各种危急重症的诊治
         */

        private int id;
        private String name;
        private String username;
        private String password;
        private String icon;
        private String title;
        private String section;
        private String hospital;
        private String seniority;
        private String bio;
        private String adept;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSection() {
            return section;
        }

        public void setSection(String section) {
            this.section = section;
        }

        public String getHospital() {
            return hospital;
        }

        public void setHospital(String hospital) {
            this.hospital = hospital;
        }

        public String getSeniority() {
            return seniority;
        }

        public void setSeniority(String seniority) {
            this.seniority = seniority;
        }

        public String getBio() {
            return bio;
        }

        public void setBio(String bio) {
            this.bio = bio;
        }

        public String getAdept() {
            return adept;
        }

        public void setAdept(String adept) {
            this.adept = adept;
        }
    }
}
