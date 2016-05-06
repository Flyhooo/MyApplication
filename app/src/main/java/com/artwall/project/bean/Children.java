package com.artwall.project.bean;

import java.util.ArrayList;

/**
 * Created by 95 on 2016/3/31.
 */
public class Children {

//    id：ID
//    userid：用户ID
//    nickname：昵称
//    portrait：头像
//    thumb：图片
//    title：标题
//    label：标签
//    examine：浏览次数
//    zambia：点赞次数
//    comment：评论次数
//    copys：模仿次数


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
    private String thumb;
    private String title;
    private String label;
    private String examine;
    private String zambia;
    private String comment;
    private String copys;

    private ArrayList<Content> content;
    private String tips;
    private String reward;
    private String material;
    private String inputtime;
    private String introduce;

    public ArrayList<Content> getContent() {
        return content;
    }

    public void setContent(ArrayList<Content> content) {
        this.content = content;
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

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
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

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getExamine() {
        return examine;
    }

    public void setExamine(String examine) {
        this.examine = examine;
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

    public String getCopys() {
        return copys;
    }

    public void setCopys(String copys) {
        this.copys = copys;
    }

    class Content{
        private String images;
        private String content;

        public String getImages() {
            return images;
        }

        public void setImages(String images) {
            this.images = images;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
