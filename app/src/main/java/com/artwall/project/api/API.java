package com.artwall.project.api;

import com.artwall.project.config.Config;

/**
 * Created by 95 on 2016/4/20.
 */
public class API {

//    http://139.196.11.127/Api/Api.php?m=login

    /**
     * 登录接口
     */
    public static final String Login = Config.HTTP_HOST + "m=login";
    /**
     * 注册获取验证码接口
     */
    public static final String Code = Config.HTTP_HOST + "m=code";
    /**
     * 注册判断验证码接口
     */
    public static final String Judge = Config.HTTP_HOST + "m=judge";
    /**
     * 注册接口
     */
    public static final String Register = Config.HTTP_HOST + "m=register";

    /**
     * 绘画分类
     */
    public static final String Paint_Type = Config.HTTP_HOST + "m=label";
    /**
     * 绘画列表
     */
    public static final String Paint_List = Config.HTTP_HOST + "m=painting";
    /**
     * 绘画列表
     */
    public static final String Yaoping_List = Config.HTTP_HOST + "m=invita";
    /**
     * 儿童列表
     */
    public static final String Children_List = Config.HTTP_HOST + "m=children";

}
