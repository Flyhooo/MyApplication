package com.artwall.project.bean;

import java.util.ArrayList;

/**
 * 作品详情  图文混排
 **/
public class PaintDetail {

//    private String text;
//    private String image;
//
//    public String getText() {
//        return text;
//    }
//
//    public void setText(String text) {
//        this.text = text;
//    }
//
//    public String getImage() {
//        return image;
//    }
//
//    public void setImage(String image) {
//        this.image = image;
//    }


//    id：ID
//    userid：用户ID
//    nickname：用户昵称
//    portrait：用户头像
//    userintroduce：用户简介
//    inputtime：时间
//    title：标题
//    introduce：简介
//    thumb：成品图
//    material：耗材列表
//    content：步骤列表
//    copyslist：模仿列表
//    tips：小贴士
//    reward：打赏
//    zambia：点赞
//    comment：评论

    private String id;
    private String userid;
    private String nickname;
    private String portrait;
    private String userintroduce;
    private String inputtime;
    private String title;
    private String introduce;
    private String thumb;
    private String material;
    private ArrayList<PaintDetailContent> content;
    private String copyslist;
    private String tips;
    private String reward;
    private String zambia;
    private String comment;

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

    public String getInputtime() {
        return inputtime;
    }

    public void setInputtime(String inputtime) {
        this.inputtime = inputtime;
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

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public ArrayList<PaintDetailContent> getContent() {
        return content;
    }

    public void setContent(ArrayList<PaintDetailContent> content) {
        this.content = content;
    }

    public String getCopyslist() {
        return copyslist;
    }

    public void setCopyslist(String copyslist) {
        this.copyslist = copyslist;
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    public String getReward() {
        return reward;
    }

    public void setReward(String reward) {
        this.reward = reward;
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

}
