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
    public static final String Login = Config.HTTP_HOST + "/Api.php?m=login";
    /**
     * 注册获取验证码接口
     */
    public static final String Code = Config.HTTP_HOST + "/Api.php?m=code";
    /**
     * 注册判断验证码接口
     */
    public static final String Judge = Config.HTTP_HOST + "/Api.php?m=judge";
    /**
     * 注册接口
     */
    public static final String Register = Config.HTTP_HOST + "/Api.php?m=register";

    /**
     * 绘画分类
     */
    public static final String Paint_Type = Config.HTTP_HOST + "/Api.php?m=label";
    /**
     * 绘画列表
     */
    public static final String Paint_List = Config.HTTP_HOST + "/Api.php?m=painting";
    /**
     * 绘画列表
     */
    public static final String Yaoping_List = Config.HTTP_HOST + "/Api.php?m=invita";
    /**
     * 儿童列表
     */
    public static final String Children_List = Config.HTTP_HOST + "/Api.php?m=children";
    /**
     * 儿童创作列表
     */
    public static final String Children_Creation_List = Config.HTTP_HOST + "/Api.php?m=works";
    /**
     * 修改用户信息接口
     */
    public static final String Update_Info = Config.HTTP_HOST + "/Api.php?m=modify";
    /**
     * 话题列表接口
     */
    public static final String Topic_List = Config.HTTP_HOST + "/Api.php?m=message";
    /**
     * 话题分类接口
     */
    public static final String Topic_Type = Config.HTTP_HOST + "/Api.php?m=messageclass";


}
