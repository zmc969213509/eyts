package com.guojianyiliao.eryitianshi.MyUtils.bean;

/**
 * 用户信息
 * jnluo,jnluo5889@126.com
 */

public class UserInfoLogin {

    /**
     * qq : null
     * password : 51596103F385101CE16BCC58B3C3B85B
     * role : 1
     * gender : null
     * weibo : null
     * phone : 18310904818
     * icon : null
     * name : null
     * wechat : null
     * userid : 586265ced6884501b320237da74ab6ab
     * age : null
     * email : null
     */

    private Object qq;
    private String password;
    private String role;
    private String gender;
    private Object weibo;
    private String phone;
    private Object icon = "";
    private String name = "";
    private Object wechat;
    private String userid;
    private Object age;
    private Object email;

    public UserInfoLogin(Object qq, String password, String role, String gender, Object weibo, String phone, Object icon, String name, Object wechat, String userid, Object age, Object email) {
        this.qq = qq;
        this.password = password;
        this.role = role;
        this.gender = gender;
        this.weibo = weibo;
        this.phone = phone;
        this.icon = icon;
        this.name = name;
        this.wechat = wechat;
        this.userid = userid;
        this.age = age;
        this.email = email;
    }

    public Object getQq() {
        return qq;
    }

    public void setQq(Object qq) {
        this.qq = qq;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Object getWeibo() {
        return weibo;
    }

    public void setWeibo(Object weibo) {
        this.weibo = weibo;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Object getIcon() {
        return icon;
    }

    public void setIcon(Object icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getWechat() {
        return wechat;
    }

    public void setWechat(Object wechat) {
        this.wechat = wechat;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public Object getAge() {
        return age;
    }

    public void setAge(Object age) {
        this.age = age;
    }

    public Object getEmail() {
        return email;
    }

    public void setEmail(Object email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "UserInfoLogin{" +
                "qq=" + qq +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                ", gender='" + gender + '\'' +
                ", weibo=" + weibo +
                ", phone='" + phone + '\'' +
                ", icon=" + icon +
                ", name='" + name + '\'' +
                ", wechat=" + wechat +
                ", userid='" + userid + '\'' +
                ", age=" + age +
                ", email=" + email +
                '}';
    }
}
