package com.guojianyiliao.eryitianshi.MyUtils.bean;

/**
 * description: 说说评论列表
 * autour: jnluo jnluo5889@126.com
 * date: 2017/4/22 11:25
 * update: 2017/4/22
 * version: Administrator
 */


public class EcommentsBean {


    /**
     *  通过判读replyid是否有值，加载评论内容或回复内容
     * */

    /**
     * ecUser : {"gender":"男","name":"雷老板","password":"C766E66AAE9F229C18B1C9286713C6FA","phone":"18380115753","role":"2","userid":"db0c279714c84007b7ce1a6f661a96ca"}
     * ecid : 356770044d094a7fbad5a31d42df1c07
     * ectime : 1492654210000
     * ecuserid : db0c279714c84007b7ce1a6f661a96ca
     * ecusername : 雷老板
     * rcontent : 评论回复
     * replyUser : {"gender":"男","icon":"http://192.168.1.11:10010/AppServer/images/13/8/44f45c1707da4c878f6b5d6f5bee3954.png","name":"LiDo","password":"C766E66AAE9F229C18B1C9286713C6FA","phone":"18380447953","qq":"15CDF2CA327FAF9D1FFBB936C3B1B08F","role":"2","userid":"9ce44d6a677641ad86b1f3ac05c2209e"}
     * replyid : 56369093d83b4dfd93ceb216480dfb41
     * ressayid : 10e4084158d94170a8e3915427c4914c
     * rtime : 1492654210000
     * ruserid : 9ce44d6a677641ad86b1f3ac05c2209e
     * rusername : LiDo
     * eccontent : 老子日你个鬼
     */

    private EcUserBean ecUser;
    private String ecid;
    private long ectime;
    private String ecuserid;
    private String ecusername;
    private String rcontent;
    private ReplyUserBean replyUser;
    private String replyid = "";
    private String ressayid;
    private long rtime;
    private String ruserid;
    private String rusername;
    private String eccontent;

    public EcUserBean getEcUser() {
        return ecUser;
    }

    public void setEcUser(EcUserBean ecUser) {
        this.ecUser = ecUser;
    }

    public String getEcid() {
        return ecid;
    }

    public void setEcid(String ecid) {
        this.ecid = ecid;
    }

    public long getEctime() {
        return ectime;
    }

    public void setEctime(long ectime) {
        this.ectime = ectime;
    }

    public String getEcuserid() {
        return ecuserid;
    }

    public void setEcuserid(String ecuserid) {
        this.ecuserid = ecuserid;
    }

    public String getEcusername() {
        return ecusername;
    }

    public void setEcusername(String ecusername) {
        this.ecusername = ecusername;
    }

    public String getRcontent() {
        return rcontent;
    }

    public void setRcontent(String rcontent) {
        this.rcontent = rcontent;
    }

    public ReplyUserBean getReplyUser() {
        return replyUser;
    }

    public void setReplyUser(ReplyUserBean replyUser) {
        this.replyUser = replyUser;
    }

    public String getReplyid() {
        return replyid;
    }

    public void setReplyid(String replyid) {
        this.replyid = replyid;
    }

    public String getRessayid() {
        return ressayid;
    }

    public void setRessayid(String ressayid) {
        this.ressayid = ressayid;
    }

    public long getRtime() {
        return rtime;
    }

    public void setRtime(long rtime) {
        this.rtime = rtime;
    }

    public String getRuserid() {
        return ruserid;
    }

    public void setRuserid(String ruserid) {
        this.ruserid = ruserid;
    }

    public String getRusername() {
        return rusername;
    }

    public void setRusername(String rusername) {
        this.rusername = rusername;
    }

    public String getEccontent() {
        return eccontent;
    }

    public void setEccontent(String eccontent) {
        this.eccontent = eccontent;
    }

    public static class EcUserBean {
        /**
         * gender : 男
         * name : 雷老板
         * password : C766E66AAE9F229C18B1C9286713C6FA
         * phone : 18380115753
         * role : 2
         * userid : db0c279714c84007b7ce1a6f661a96ca
         */

        private String gender;
        private String name;
        private String password;
        private String phone;
        private String role;
        private String userid;

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }
    }

    public static class ReplyUserBean {
        /**
         * gender : 男
         * icon : http://192.168.1.11:10010/AppServer/images/13/8/44f45c1707da4c878f6b5d6f5bee3954.png
         * name : LiDo
         * password : C766E66AAE9F229C18B1C9286713C6FA
         * phone : 18380447953
         * qq : 15CDF2CA327FAF9D1FFBB936C3B1B08F
         * role : 2
         * userid : 9ce44d6a677641ad86b1f3ac05c2209e
         */

        private String gender;
        private String icon;
        private String name;
        private String password;
        private String phone;
        private String qq;
        private String role;
        private String userid;

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getQq() {
            return qq;
        }

        public void setQq(String qq) {
            this.qq = qq;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }
    }

    @Override
    public String toString() {
        return "EcommentsBean{" +
                "ecUser=" + ecUser +
                ", ecid='" + ecid + '\'' +
                ", ectime=" + ectime +
                ", ecuserid='" + ecuserid + '\'' +
                ", ecusername='" + ecusername + '\'' +
                ", rcontent='" + rcontent + '\'' +
                ", replyUser=" + replyUser +
                ", replyid='" + replyid + '\'' +
                ", ressayid='" + ressayid + '\'' +
                ", rtime=" + rtime +
                ", ruserid='" + ruserid + '\'' +
                ", rusername='" + rusername + '\'' +
                ", eccontent='" + eccontent + '\'' +
                '}';
    }
}
