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


    private EssayBean essay;
    private List<EssayAgreeListBean> essayAgreeList;
    private List<EcommentsBean> essayCommentList;

    /**
     * 说说信息
     */
    public static class EssayBean {

        /**评论数**/
        private int ecommontcount;
        /**说说内容**/
        private String econtent;
        /**说说id**/
        private String eid;
        /**说说图片url**/
        private String eimages;
        /**备注**/
        private String ememo;
        /**说说发布时间**/
        private long epubtime;
        /**浏览数量**/
        private int etimes;
        /**点赞数**/
        private int eagrees;
        /**发布人ID**/
        private String userid;

        public void setEcommontcount(int ecommontcount) {
            this.ecommontcount = ecommontcount;
        }

        public void setEcontent(String econtent) {
            this.econtent = econtent;
        }

        public void setEid(String eid) {
            this.eid = eid;
        }

        public void setEimages(String eimages) {
            this.eimages = eimages;
        }

        public void setEmemo(String ememo) {
            this.ememo = ememo;
        }

        public void setEpubtime(long epubtime) {
            this.epubtime = epubtime;
        }

        public void setEtimes(int etimes) {
            this.etimes = etimes;
        }

        public void setEagrees(int eagrees) {
            this.eagrees = eagrees;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public int getEcommontcount() {
            return ecommontcount;
        }

        public String getEcontent() {
            return econtent;
        }

        public String getEid() {
            return eid;
        }

        public String getEimages() {
            return eimages;
        }

        public String getEmemo() {
            return ememo;
        }

        public long getEpubtime() {
            return epubtime;
        }

        public int getEtimes() {
            return etimes;
        }

        public int getEagrees() {
            return eagrees;
        }

        public String getUserid() {
            return userid;
        }
    }

    /**
     * 点赞信息
     */
    public static class EssayAgreeListBean {

        /**主键**/
        private String agreeid;
        /**点赞说说id**/
        private String eid;
        /**点赞人的id**/
        private String userid;
        /**点赞时间**/
        private long agreetime;
        /**备注*/
        private String memo;
        /**点赞人实体**/
        private UserBean user;


        /**
         * 用户信息
         */
        public static class UserBean {

            /**id**/
            private String userid;
            /**电话**/
            private String phone;
            /**密码**/
            private String password;
            /**名字**/
            private String name;
            /**性别**/
            private String gender;
            /**年龄**/
            private int age;
            /**头像**/
            private String icon;
            /**qq**/
            private String qq;
            /**微博**/
            private String weibo;
            /**微信**/
            private String wechat;
            /**email**/
            private String email;
            /**权限级别**/
            private String role;
            /**登录次数**/
            private String time;
            /****/
            private String message;

            public void setAge(int age) {
                this.age = age;
            }

            public void setWeibo(String weibo) {
                this.weibo = weibo;
            }

            public void setWechat(String wechat) {
                this.wechat = wechat;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public void setMessage(String message) {
                this.message = message;
            }

            public int getAge() {
                return age;
            }

            public String getWeibo() {
                return weibo;
            }

            public String getWechat() {
                return wechat;
            }

            public String getEmail() {
                return email;
            }

            public String getTime() {
                return time;
            }

            public String getMessage() {
                return message;
            }

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

        public long getAgreetime() {
            return agreetime;
        }

        public String getEid() {
            return eid;
        }

        public UserBean getUser() {
            return user;
        }

        public String getUserid() {
            return userid;
        }

        public String getAgreeid() {
            return agreeid;
        }
    }

    public EssayBean getEssay() {
        return essay;
    }

    public List<EssayAgreeListBean> getEssayAgreeList() {
        return essayAgreeList;
    }

    public List<EcommentsBean> getEssayCommentList() {
        return essayCommentList;
    }
}
