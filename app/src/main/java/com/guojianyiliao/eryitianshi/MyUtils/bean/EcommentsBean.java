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
     **/

    /**评论id**/
    private String ecid;
    /**评论内容**/
    private String eccontent = "";
    /**评论人的id 或 被回复人的id （取值决定于 replyid 是否有值）**/
    private String ecuserid;
    /** ecuserid 对应的名字**/
    private String ecusername;
    /**评论时间**/
    private long ectime;
    /**被回复的评论id**/
    private String replyid = "";
    /**评论的说说id**/
    private String ressayid;
    /**回复人的id**/
    private String ruserid;
    /** ruserid 对应的name**/
    private String rusername;
    /**回复的评论内容**/
    private String rcontent = "";
    /**回复的时间**/
    private long rtime;
    /**备注**/
    private String memo;
    /**评论说说人实体**/
    private EcUserBean ecUser;
    /**回复评论人实体**/
    private ReplyUserBean replyUser;


    public String getEcid() {
        return ecid;
    }

    public String getEccontent() {
        return eccontent;
    }

    public String getEcuserid() {
        return ecuserid;
    }

    public String getEcusername() {
        return ecusername;
    }

    public long getEctime() {
        return ectime;
    }

    public String getReplyid() {
        return replyid;
    }

    public String getRessayid() {
        return ressayid;
    }

    public String getRuserid() {
        return ruserid;
    }

    public String getRusername() {
        return rusername;
    }

    public String getRcontent() {
        return rcontent;
    }

    public long getRtime() {
        return rtime;
    }

    public String getMemo() {
        return memo;
    }

    public EcUserBean getEcUser() {
        return ecUser;
    }

    public ReplyUserBean getReplyUser() {
        return replyUser;
    }

    /**
     * 评论说说人实体
     */
    public static class EcUserBean {

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

        public String getUserid() {
            return userid;
        }

        public String getPhone() {
            return phone;
        }

        public String getPassword() {
            return password;
        }

        public String getName() {
            return name;
        }

        public String getGender() {
            return gender;
        }

        public int getAge() {
            return age;
        }

        public String getIcon() {
            return icon;
        }

        public String getQq() {
            return qq;
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

        public String getRole() {
            return role;
        }

        public String getTime() {
            return time;
        }

        public String getMessage() {
            return message;
        }
    }

    /**
     * 回复评论人实体
     */
    public static class ReplyUserBean {

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

        public String getUserid() {
            return userid;
        }

        public String getPhone() {
            return phone;
        }

        public String getPassword() {
            return password;
        }

        public String getName() {
            return name;
        }

        public String getGender() {
            return gender;
        }

        public int getAge() {
            return age;
        }

        public String getIcon() {
            return icon;
        }

        public String getQq() {
            return qq;
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

        public String getRole() {
            return role;
        }

        public String getTime() {
            return time;
        }

        public String getMessage() {
            return message;
        }
    }

    @Override
    public String toString() {
        return "EcommentsBean{" +
                "ecid='" + ecid + '\'' +
                ", eccontent='" + eccontent + '\'' +
                ", ecuserid='" + ecuserid + '\'' +
                ", ecusername='" + ecusername + '\'' +
                ", ectime=" + ectime +
                ", replyid='" + replyid + '\'' +
                ", ressayid='" + ressayid + '\'' +
                ", ruserid='" + ruserid + '\'' +
                ", rusername='" + rusername + '\'' +
                ", rcontent='" + rcontent + '\'' +
                ", rtime=" + rtime +
                ", memo='" + memo + '\'' +
                ", ecUser=" + ecUser +
                ", replyUser=" + replyUser +
                '}';
    }

}
