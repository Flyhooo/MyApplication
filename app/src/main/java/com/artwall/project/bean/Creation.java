package com.artwall.project.bean;

/**
 * Created by 95 on 2016/4/27.
 * 儿童创作
 */
public class Creation {

//    id：ID
//    userid：用户ID
//    nickname：昵称
//    userimage：头像
//    time：时间
//    content：内容
//    zan：点赞次数
//    comment：评论次数
//    share：分享次数
//    imglist：图片列表


//    id：ID
//    userid：用户ID
//    nickname：昵称
//    portrait：头像
//    inputtime：时间
//    introduce：内容
//    zambia：点赞次数
//    comment：评论次数
//    share：分享次数
//    content：图片列表

    private String id;
    private String userid;
    private String portrait;
    private String nickname;
    private String inputtime;
    private String introduce;
    private String content;
    private String zambia;
    private String comment;
    private String share;

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

    public String getInputtime() {
        return inputtime;
    }

    public void setInputtime(String inputtime) {
        this.inputtime = inputtime;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getZambia() {
        return zambia;
    }

    public void setZambia(String zambia) {
        this.zambia = zambia;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getShare() {
        return share;
    }

    public void setShare(String share) {
        this.share = share;
    }
}
