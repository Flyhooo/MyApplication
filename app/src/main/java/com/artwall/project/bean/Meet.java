package com.artwall.project.bean;

/**
 * Created by 95 on 2016/5/3.
 */
public class Meet {

//    id：ID
//    userid：用户ID
//    nickname：用户昵称
//    portrait：用户头像
//    userintroduce：用户简介
//    fication：分类
//    title：标题
//    introduce：简介
//    score：评分
//    seen：约见次数
//    see：想见次数
//    price：价格
//    inputtime：发布时间

    private String id;
    private String userid;
    private String nickname;
    private String portrait;
    private String userintroduce;
    private String fication;
    private String title;
    private String introduce;
    private String score;
    private String seen;
    private String see;
    private String price;
    private String inputtime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public String getUserintroduce() {
        return userintroduce;
    }

    public void setUserintroduce(String userintroduce) {
        this.userintroduce = userintroduce;
    }

    public String getFication() {
        return fication;
    }

    public void setFication(String fication) {
        this.fication = fication;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getSeen() {
        return seen;
    }

    public void setSeen(String seen) {
        this.seen = seen;
    }

    public String getSee() {
        return see;
    }

    public void setSee(String see) {
        this.see = see;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getInputtime() {
        return inputtime;
    }

    public void setInputtime(String inputtime) {
        this.inputtime = inputtime;
    }
}
