package com.artwall.project.bean;

import java.io.Serializable;

/**
 * Created by 95 on 2016/4/21.
 */
public class User implements Serializable {

//    userid：ID
//    username：账号
//    password：密码
//    nickname：昵称
//    portrait：头像
//    introduce：简介
//    lastdate：上次登录时间
//    characte：角色
//    groups：分组
//    sex：性别
//    amount：余额
//    phone：手机
//    email：邮箱

    private String userid;
    /**
     * 登录名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 性别
     */
    private String sex;
    /**
     * 头像
     */
    private String portrait;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 个人简介
     */
    private String introduce;
    /**
     * 最后一次登录时间
     */
    private String lastdate;
    /**
     * 用户身份
     */
    private String characte;
    /**
     * 角色中感兴趣
     */
    private String groups;
    /**
     * 账户余额
     */
    private String amount;
    /**
     * 手机号码
     */
    private String phone;
    /**
     * 邮箱
     */
    private String email;


    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getLastdate() {
        return lastdate;
    }

    public void setLastdate(String lastdate) {
        this.lastdate = lastdate;
    }

    public String getCharacte() {
        return characte;
    }

    public void setCharacte(String characte) {
        this.characte = characte;
    }

    public String getGroups() {
        return groups;
    }

    public void setGroups(String groups) {
        this.groups = groups;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
