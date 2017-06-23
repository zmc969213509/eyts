package com.guojianyiliao.eryitianshi.MyUtils.bean;

import java.util.List;

/**
 * description: 获取说说列表
 * autour: jnluo jnluo5889@126.com
 * date: 2017/4/18 9:33
 * update: 2017/4/18
 * version: Administrator
 */


public class AllLecturesBean {
    /**
     {
     "eagrees": 0,
     "ecommontcount": 1,
     "econtent": "android",
     "eid": "9b67f74895494e32bf3c9a8573035b98",
     "eimages": "http://192.168.1.11:10010/AppServer/images/3/14/daf9c610729c4ddf80f6fdc5d459524b.jpg;http://192.168.1.11:10010/AppServer/images/0/10/13899de00fea4ae6b15815b6ab82436d.jpg;http://192.168.1.11:10010/AppServer/images/1/10/6065dc7747904e61ab8c265fd69ce322.jpg;",
     "ememo": "",
     "epubtime": 1494008285000,
     "etimes": 5,
     "isagree": false,
     "isfocus": false,
     "user": {
     "age": 0,
     "gender": "男",
     "icon": "http://192.168.1.11:10010/AppServer/images/8/0/76e002347b1d43ec9f4b0a4f39fdee9e.jpg",
     "name": "安卓",
     "password": "51596103F385101CE16BCC58B3C3B85B",
     "phone": "18310904818",
     "role": "1",
     "userid": "b5df631f62cc40e6b932acd997cdc5c9"
     },
     "userid": "b5df631f62cc40e6b932acd997cdc5c9"
     }
     */
    /**
     * eagrees : 0   点赞数量
     * ecommontcount : 0  评论数量
     * econtent :  评论内容
     * eid : c307c8a4b23b4aadbc7a528fb7c6cf4b
     * eimages : http://192.168.1.11:10010/AppServer/images/5/5/d7c54d415437403781878c7096414dc7.png;http://192.168.1.11:10010/AppServer/images/5/5/0ac06c5a07f141c4b96c9ef4605fa095.png;
     * ememo : 备用字段
     * epubtime : 1492498776000
     * etimes : 0  浏览量
     * isagree : false
     * isfocus : false  是否关注
     * user : {"gender":"男","icon":"http://192.168.1.11:10010/AppServer/images/8/6/5bbff047b9e24323a430ca6e5b6d574f.png","name":"Rip","password":"F59BD65F7EDAFB087A81D4DCA06C4910","phone":"18684040826","qq":"D3AEBDF55E326F21FDAB8AEBA2A163F7","role":"2","userid":"22476ffbe1b24590bb0cf9be501aec90"}
     * userid : 22476ffbe1b24590bb0cf9be501aec90
     */


    private int maxPage;
    private List<ListBean> list;

    public int getMaxPage() {
        return maxPage;
    }

    public void setMaxPage(int maxPage) {
        this.maxPage = maxPage;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * eagrees : 2
         * ecommontcount : 0
         * econtent : android
         * eid : 3e9a691252354ef6b136d52042d70c9d
         * eimages : http://192.168.1.11:10010/AppServer/images/8/0/1267b84271f74f109c7752f1644a6054.jpg;http://192.168.1.11:10010/AppServer/images/4/2/2c5be87e31884807868017ec79a17dea.jpg;
         * ememo :
         * epubtime : 1492678369000
         * etimes : 0
         * isagree : true
         * isfocus : true
         * user : {"gender":"男","icon":"http://192.168.1.11:10010/AppServer/images/8/0/76e002347b1d43ec9f4b0a4f39fdee9e.jpg","name":"安卓","password":"51596103F385101CE16BCC58B3C3B85B","phone":"18310904818","role":"1","userid":"b5df631f62cc40e6b932acd997cdc5c9"}
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
        private boolean isagree;
        private boolean isfocus = false;
        private UserBean user;
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

        public boolean isIsagree() {
            return isagree;
        }

        public void setIsagree(boolean isagree) {
            this.isagree = isagree;
        }

        public boolean isIsfocus() {
            return isfocus;
        }

        public void setIsfocus(boolean isfocus) {
            this.isfocus = isfocus;
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
             * icon : http://192.168.1.11:10010/AppServer/images/8/0/76e002347b1d43ec9f4b0a4f39fdee9e.jpg
             * name : 安卓
             * password : 51596103F385101CE16BCC58B3C3B85B
             * phone : 18310904818
             * role : 1
             * userid : b5df631f62cc40e6b932acd997cdc5c9
             */
            /**头像地址**/
            private String icon;
            /**用户id**/
            private String userid;
            /**角色1.代表普通用户,2代表管理员,3代表医生**/
            private String role;
            /**电话**/
            private String phone;
            /**密码**/
            private String password;
            /**名字**/
            private String name;
            /**性别**/
            private String gender;

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
