package com.guojianyiliao.eryitianshi.MyUtils.bean;

/**
 * 用户信息
 *
 */

public class UserInfoLogin {

    /**
     *
     *
     "age": 0,
     "gender": "男",
     "icon": "http://192.168.1.11:10010/AppServer/images/2/3/36e0612f6e6b42e89b7121d47ad8fd8d.png",
     "name": "Rip",
     "password": "F59BD65F7EDAFB087A81D4DCA06C4910",
     "phone": "18684040826",
     "qq": "D3AEBDF55E326F21FDAB8AEBA2A163F7",
     "role": "1",
     "userid": "22476ffbe1b24590bb0cf9be501aec90"

     * age : null
     * gender : null
     * icon : null
     * name : null
     * password : 51596103F385101CE16BCC58B3C3B85B
     * phone : 18310904818
     * qq : null
     * wechat : null
     * weibo : null
     * email : null
     * role : 1
     * userid : 586265ced6884501b320237da74ab6ab
     */


    private String age;
    private String gender;
    private String icon = "";
    private String name = "";
    private String password;
    private String phone;
    private String qq;
    private String wechat;
    private String weibo;
    private String email;
    private String role;
    private String userid;
    /**登录次数**/
    private String time;
    private String message;

    public UserInfoLogin(String age, String gender, String icon, String name, String password, String phone, String qq, String wechat, String weibo, String email, String role, String userid, String time, String message) {
        this.age = age;
        this.gender = gender;
        this.icon = icon;
        this.name = name;
        this.password = password;
        this.phone = phone;
        this.qq = qq;
        this.wechat = wechat;
        this.weibo = weibo;
        this.email = email;
        this.role = role;
        this.userid = userid;
        this.time = time;
        this.message = message;
    }

    public UserInfoLogin(String gender, String icon, String name, String userid) {
        this.gender = gender;
        this.icon = icon;
        this.name = name;
        this.userid = userid;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
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

    public String getWeibo() {
        return weibo;
    }

    public void setWeibo(String weibo) {
        this.weibo = weibo;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
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
