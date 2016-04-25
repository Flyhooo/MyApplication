package com.artwall.project.bean;

import java.io.Serializable;

/**
 * Created by 95 on 2016/4/21.
 */
public class User implements Serializable{

    private String userid;
    /**登录名*/
    private String username;
    /**密码*/
    private String password;
    /**性别*/
    private String sex;
    /**头像*/
    private String img;
    /** 昵称*/
    private String nickname;
    /**个人简介*/
    private String introduction;
    /**最后一次登录时间*/
    private String lastlogin;
    /**用户身份*/
    private String character;
    /**账户余额*/
    private String account;
    /**手机号码*/
    private String phone;
    /**邮箱*/
    private String email;

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

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

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getLastlogin() {
        return lastlogin;
    }

    public void setLastlogin(String lastlogin) {
        this.lastlogin = lastlogin;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
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
