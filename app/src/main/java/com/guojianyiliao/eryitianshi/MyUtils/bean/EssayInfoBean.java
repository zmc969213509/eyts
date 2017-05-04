package com.guojianyiliao.eryitianshi.MyUtils.bean;

import java.util.List;

/**
 * description: 说说详情实体类
 * autour: jnluo jnluo5889@126.com
 * date: 2017/4/21 17:37
 * update: 2017/4/21
 * version: Administrator
*/

public class EssayInfoBean {


    /**
     * essay : {"eagrees":2,"ecommontcount":0,"econtent":"眼不见为净,于是,郭书恒闭着眼睛,捡狗屎O(∩_∩)O哈哈~","eid":"1df9a3f7fcb7474fa1826d0d985f3799","eimages":"http://192.168.1.11:10010/AppServer/images/15/10/54e7402f79ae44188c4c156bc784c4a0.jpg;http://192.168.1.11:10010/AppServer/images/12/1/85249712d5e04a4fbfb43acba00c4f26.jpg;http://192.168.1.11:10010/AppServer/images/3/12/fb55fd3c648d465789c1ca7edfd382a3.jpg;http://192.168.1.11:10010/AppServer/images/1/10/a8d3ab2aec3c47f3baa71488e540e821.jpg;http://192.168.1.11:10010/AppServer/images/12/6/760ea7bffce84963905204bf86e2db6d.jpg;http://192.168.1.11:10010/AppServer/images/0/9/e81a73a3717e460a980954ed79ae77b0.jpg;http://192.168.1.11:10010/AppServer/images/12/4/221b2fb229a44018a89475a505154901.jpg;http://192.168.1.11:10010/AppServer/images/13/4/17b729c0a02248c58eb2e76d13b54359.jpg;http://192.168.1.11:10010/AppServer/images/7/3/5af0e32b36524434bc36ee98f7f37757.jpg;","ememo":"","epubtime":1492588431000,"etimes":2,"userid":"b5df631f62cc40e6b932acd997cdc5c9"}
     * essayAgreeList : [{"agreeid":"19a34dc2c9df47bebc715259ceb229dc","agreetime":1492672264000,"eid":"1df9a3f7fcb7474fa1826d0d985f3799","user":{"gender":"男","icon":"http://192.168.1.11:10010/AppServer/images/8/14/f1883f43f90b4447b60fd0af58612f69.png","name":"Rip","password":"F59BD65F7EDAFB087A81D4DCA06C4910","phone":"18684040826","qq":"D3AEBDF55E326F21FDAB8AEBA2A163F7","role":"2","userid":"22476ffbe1b24590bb0cf9be501aec90"},"userid":"22476ffbe1b24590bb0cf9be501aec90"},{"agreeid":"c725159e89ed465eaf1e05e2dc77e0a7","agreetime":1492680580000,"eid":"1df9a3f7fcb7474fa1826d0d985f3799","user":{"gender":"男","icon":"http://192.168.1.11:10010/AppServer/images/13/8/44f45c1707da4c878f6b5d6f5bee3954.png","name":"LiDo","password":"C766E66AAE9F229C18B1C9286713C6FA","phone":"18380447953","qq":"15CDF2CA327FAF9D1FFBB936C3B1B08F","role":"2","userid":"9ce44d6a677641ad86b1f3ac05c2209e"},"userid":"9ce44d6a677641ad86b1f3ac05c2209e"},{"agreeid":"f8e7999311154936b28681ccca86f853","agreetime":1492678731000,"eid":"1df9a3f7fcb7474fa1826d0d985f3799","user":{"gender":"女","icon":"https://wx.qlogo.cn/mmopen/QzPtZqIzA0mKiawDvbSRC0qeAIiaZQhpHW9icNybzj0ZZyqiaN54R7YxWiaI2cRE623oiaTQ6vq1SqNjZ3VI7qoicsLScdRU606JGEq/0","name":"小佳","password":"F59BD65F7EDAFB087A81D4DCA06C4910","phone":"18380479771","role":"2","userid":"eb313d6d00374844bfcf210d3bdf77a7","wechat":"ox14Dv_a_hXsVPhI0T2U3NcipGRg"},"userid":"eb313d6d00374844bfcf210d3bdf77a7"}]
     * essayCommentList : []
     */

    private EssayBean essay;
    private List<EssayAgreeListBean> essayAgreeList;
    private List<?> essayCommentList;

    public EssayBean getEssay() {
        return essay;
    }

    public void setEssay(EssayBean essay) {
        this.essay = essay;
    }

    public List<EssayAgreeListBean> getEssayAgreeList() {
        return essayAgreeList;
    }

    public void setEssayAgreeList(List<EssayAgreeListBean> essayAgreeList) {
        this.essayAgreeList = essayAgreeList;
    }

    public List<?> getEssayCommentList() {
        return essayCommentList;
    }

    public void setEssayCommentList(List<?> essayCommentList) {
        this.essayCommentList = essayCommentList;
    }

    public static class EssayBean {
        /**
         * eagrees : 2
         * ecommontcount : 0
         * econtent : 眼不见为净,于是,郭书恒闭着眼睛,捡狗屎O(∩_∩)O哈哈~
         * eid : 1df9a3f7fcb7474fa1826d0d985f3799
         * eimages : http://192.168.1.11:10010/AppServer/images/15/10/54e7402f79ae44188c4c156bc784c4a0.jpg;http://192.168.1.11:10010/AppServer/images/12/1/85249712d5e04a4fbfb43acba00c4f26.jpg;http://192.168.1.11:10010/AppServer/images/3/12/fb55fd3c648d465789c1ca7edfd382a3.jpg;http://192.168.1.11:10010/AppServer/images/1/10/a8d3ab2aec3c47f3baa71488e540e821.jpg;http://192.168.1.11:10010/AppServer/images/12/6/760ea7bffce84963905204bf86e2db6d.jpg;http://192.168.1.11:10010/AppServer/images/0/9/e81a73a3717e460a980954ed79ae77b0.jpg;http://192.168.1.11:10010/AppServer/images/12/4/221b2fb229a44018a89475a505154901.jpg;http://192.168.1.11:10010/AppServer/images/13/4/17b729c0a02248c58eb2e76d13b54359.jpg;http://192.168.1.11:10010/AppServer/images/7/3/5af0e32b36524434bc36ee98f7f37757.jpg;
         * ememo :
         * epubtime : 1492588431000
         * etimes : 2
         * userid : b5df631f62cc40e6b932acd997cdc5c9
         */

        private int eagrees;
        private int ecommontcount;
        private String econtent;
        private String eid;
        private String eimages;
        private String ememo;
        private long epubtime;
        private int etimes;
        private String userid;

        public int getEagrees() {
            return eagrees;
        }

        public void setEagrees(int eagrees) {
            this.eagrees = eagrees;
        }

        public int getEcommontcount() {
            return ecommontcount;
        }

        public void setEcommontcount(int ecommontcount) {
            this.ecommontcount = ecommontcount;
        }

        public String getEcontent() {
            return econtent;
        }

        public void setEcontent(String econtent) {
            this.econtent = econtent;
        }

        public String getEid() {
            return eid;
        }

        public void setEid(String eid) {
            this.eid = eid;
        }

        public String getEimages() {
            return eimages;
        }

        public void setEimages(String eimages) {
            this.eimages = eimages;
        }

        public String getEmemo() {
            return ememo;
        }

        public void setEmemo(String ememo) {
            this.ememo = ememo;
        }

        public long getEpubtime() {
            return epubtime;
        }

        public void setEpubtime(long epubtime) {
            this.epubtime = epubtime;
        }

        public int getEtimes() {
            return etimes;
        }

        public void setEtimes(int etimes) {
            this.etimes = etimes;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }
    }

    public static class EssayAgreeListBean {
        /**
         * agreeid : 19a34dc2c9df47bebc715259ceb229dc
         * agreetime : 1492672264000
         * eid : 1df9a3f7fcb7474fa1826d0d985f3799
         * user : {"gender":"男","icon":"http://192.168.1.11:10010/AppServer/images/8/14/f1883f43f90b4447b60fd0af58612f69.png","name":"Rip","password":"F59BD65F7EDAFB087A81D4DCA06C4910","phone":"18684040826","qq":"D3AEBDF55E326F21FDAB8AEBA2A163F7","role":"2","userid":"22476ffbe1b24590bb0cf9be501aec90"}
         * userid : 22476ffbe1b24590bb0cf9be501aec90
         */

        private String agreeid;
        private long agreetime;
        private String eid;
        private UserBean user;
        private String userid;

        public String getAgreeid() {
            return agreeid;
        }

        public void setAgreeid(String agreeid) {
            this.agreeid = agreeid;
        }

        public long getAgreetime() {
            return agreetime;
        }

        public void setAgreetime(long agreetime) {
            this.agreetime = agreetime;
        }

        public String getEid() {
            return eid;
        }

        public void setEid(String eid) {
            this.eid = eid;
        }

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public static class UserBean {
            /**
             * gender : 男
             * icon : http://192.168.1.11:10010/AppServer/images/8/14/f1883f43f90b4447b60fd0af58612f69.png
             * name : Rip
             * password : F59BD65F7EDAFB087A81D4DCA06C4910
             * phone : 18684040826
             * qq : D3AEBDF55E326F21FDAB8AEBA2A163F7
             * role : 2
             * userid : 22476ffbe1b24590bb0cf9be501aec90
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
    }
}
