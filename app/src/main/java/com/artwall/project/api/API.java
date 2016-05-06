package com.artwall.project.api;

import com.artwall.project.config.Config;

/**
 * Created by 95 on 2016/4/20.
 */
public class API {

//    http://139.196.11.127/Api/Api.php?m=login
    /**
     * 图片上传接口
     */
    public static final String IMAGE_UPLOAD = Config.HTTP_HOST + "/upload.php?m=upload";
    /**
     * 登录接口
     */
    public static final String Login = Config.HTTP_HOST + "/user.php?m=login";
    /**
     * 注册获取验证码接口
     */
    public static final String Code = Config.HTTP_HOST + "/user.php?m=code";
    /**
     * 注册判断验证码接口
     */
    public static final String Judge = Config.HTTP_HOST + "/user.php?m=judge";
    /**
     * 注册接口
     */
    public static final String Register = Config.HTTP_HOST + "/user.php?m=register";
    /**
     * 修改用户信息接口
     */
    public static final String Update_Info = Config.HTTP_HOST + "/user.php?m=modify";

    /**
     * 绘画分类
     */
    public static final String Paint_Type = Config.HTTP_HOST + "/painting.php?m=label";
    /**
     * 绘画列表
     */
    public static final String Paint_List = Config.HTTP_HOST + "/painting.php?m=painting";
    /**
     * 绘画详情页
     */
    public static final String Paint_Content = Config.HTTP_HOST + "/painting.php?m=painting_content";
    /**
     * 绘画邀评列表
     */
    public static final String Yaoping_List = Config.HTTP_HOST + "/painting.php?m=invita";
    /**
     * 儿童列表
     */
    public static final String Children_List = Config.HTTP_HOST + "/children.php?m=children";
    /**
     * 儿童教学详情
     */
    public static final String Children_Content = Config.HTTP_HOST + "/children.php?m=children_content";
    /**
     * 儿童创作列表
     */
    public static final String Children_Creation_List = Config.HTTP_HOST + "/children.php?m=works";
    /**
     * 话题列表接口
     */
    public static final String Topic_List = Config.HTTP_HOST + "/message.php?m=message";
    /**
     * 话题列表接口
     */
    public static final String Topic_Send = Config.HTTP_HOST + "/message.php?m=message_ask";
    /**
     * 话题分类接口
     */
    public static final String Topic_Type = Config.HTTP_HOST + "/message.php?m=messageclass";

    /**
     * 约见列表
     */
    public static final String Meeting_List = Config.HTTP_HOST + "/meet.php?m=meet";


}
