package com.guojianyiliao.eryitianshi.MyUtils.bean;

/**
 * description:我的关注
 * autour: jnluo jnluo5889@126.com
 * date: 2017/4/26 10:00
 * update: 2017/4/26
 * version: Administrator
*/

public class MyFocus {

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

    /**用户id**/
    public String userid;
    /**用户级别**/
    public String role;
    /**用户密码**/
    public String password;
    /**用户qq**/
    public String qq;
    /**用户电话**/
    public String phone;
    /**用户姓名**/
    public String name;
    /**用户头像**/
    public String icon;
    /**用户性别**/
    public String gender;

    public String getUserid() {
        return userid;
    }

    public String getRole() {
        return role;
    }

    public String getPassword() {
        return password;
    }

    public String getQq() {
        return qq;
    }

    public String getPhone() {
        return phone;
    }

    public String getName() {
        return name;
    }

    public String getIcon() {
        return icon;
    }

    public String getGender() {
        return gender;
    }
}
