package com.artwall.project.activity;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.artwall.project.R;
import com.artwall.project.api.API;
import com.artwall.project.application.App;
import com.artwall.project.base.BaseActivity;
import com.artwall.project.bean.PaintType;
import com.artwall.project.bean.User;
import com.artwall.project.service.RuntimeInfoService;
import com.artwall.project.service.UserInfoService;
import com.artwall.project.util.LogUtil;
import com.artwall.project.util.NetworkUtil;
import com.artwall.project.util.ToastUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 95 on 2016/4/20.
 * 一打开便通过判断数据库中是否存储用户信息，如果有的话则拿这个信息去请求登录，同时加载页面图片播放动画
 * 根据网络请求是否结束标志跟动画播放是否结束标志位  确定最后一个动作结束时进行页面跳转
 */
public class SplashActivity extends BaseActivity {


    ImageView imageView;
    private Animation animation;
    private boolean isAnimaFinish = false;
    private boolean isLoginFinish = false;//登录请求是否结束
    private boolean isGettypeFinish = false;//获取分类是否结束

    @Override
    protected int getContentLayout() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initGui() {
        imageView = (ImageView) this.findViewById(R.id.splash_img);

    }

    @Override
    protected void initData() {

        App.userInfo = new UserInfoService(activity).getObject();
        if (null != App.userInfo && null != App.userInfo.getUsername() && null != App.userInfo.getPassword()) {
            doLogin(App.userInfo.getUsername().toString(), App.userInfo.getPassword().toString());
            HXLogin("18511111111");
        } else {
            isLoginFinish = true;
        }
        if (!NetworkUtil.getInstance().checkNetworkAvailable()) {
            isGettypeFinish = true;
        } else {
            post(API.Paint_Type, new RequestParams());
        }

        animation = AnimationUtils.loadAnimation(this, R.anim.ani_splash);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //如果此时登录请求结束则跳转  否则等待登录请求结束
                if (isLoginFinish && isGettypeFinish) {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();
                }
                isAnimaFinish = true;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        if (!NetworkUtil.getInstance().checkNetworkAvailable()) {
            //网络不可连接时加载本地图片

        } else {
            String dimen;
            int width = getResources().getDisplayMetrics().widthPixels;
            if (width >= 900) {
                dimen = "1080*1776";
            } else if (width >= 600 && width < 900) {
                dimen = "720*1184";
            } else if (width >= 400 && width < 600) {
                dimen = "480*728";
            } else {
                dimen = "320*432";
            }
//            post(API.Login, null);
        }
        init();
        ImageLoader.getInstance().loadImage(
                "http://g.hiphotos.baidu.com/image/pic/item/6c224f4a20a446230761b9b79c22720e0df3d7bf.jpg",
                options,
                new MyImageListener()); //ImageLoadingListener

    }

    @Override
    public void onDataOK(String url, String responseString) {
        super.onDataOK(url, responseString);
//        JSONObject jsonObject = null;
//        try {
//            jsonObject = new JSONObject(responseString);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        String img = jsonObject.optString("img");

        ImageLoader.getInstance().loadImage(
                "http://g.hiphotos.baidu.com/image/pic/item/6c224f4a20a446230761b9b79c22720e0df3d7bf.jpg",
                options,
                new MyImageListener()); //ImageLoadingListener

        if (API.Login.equals(url)) {
            try {
                JSONObject jobj = new JSONObject(responseString);
                String errorCode = jobj.getString("errorCode");
                if (Integer.parseInt(errorCode) == 0) {

                    JSONObject obj = new JSONObject(responseString);
                    JSONObject data = obj.getJSONObject("data");
                    Gson gson = new Gson();
                    User user = gson.fromJson(data.toString(), User.class);
                    new UserInfoService(activity).saveObject(user);
                    App.isLogin = true;
                    App.userInfo = user;
                    //如果此时动画结束则跳转  否则等待动画结束
                    if (isAnimaFinish && isGettypeFinish) {
                        startActivity(new Intent(SplashActivity.this, MainActivity.class));
                        finish();
                    }
                } else {
                    ToastUtils.toastShaort(activity, jobj.getString("errorMsg"));
                    if (isAnimaFinish && isGettypeFinish) {
                        startActivity(new Intent(SplashActivity.this, MainActivity.class));
                        finish();
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            isLoginFinish = true;//设置网络请求结束标志
        }
        //获取绘画教学列表分类
        if (API.Paint_Type.equals(url)) {
            try {
                JSONObject jobj = new JSONObject(responseString);
                String errorCode = jobj.getString("errorCode");
                if (Integer.parseInt(errorCode) == 0) {
                    JSONObject obj = new JSONObject(responseString);
                    JSONArray data = obj.getJSONArray("data");
                    Gson gson = new Gson();
                    ArrayList<PaintType> list = gson.fromJson(data.toString(), new TypeToken<List<PaintType>>() {
                    }.getType());
                    if (null != list || list.size() != 0) {
                        RuntimeInfoService.setPaintTypeList(activity, list);
                    }
                    //如果此时动画结束则跳转  否则等待动画结束
                    if (isAnimaFinish && isLoginFinish) {
                        startActivity(new Intent(SplashActivity.this, MainActivity.class));
                        finish();
                    }
                } else {
                    ToastUtils.toastShaort(activity, jobj.getString("errorMsg"));
                    if (isAnimaFinish && isLoginFinish) {
                        startActivity(new Intent(SplashActivity.this, MainActivity.class));
                        finish();
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            isGettypeFinish = true;
        }

    }

    @Override
    public void onDataError(String url, String responseString) {
        super.onDataError(url, responseString);
        ToastUtils.toastShaort(activity, "网络连接失败");
        if (API.Login.equals(url)) {
            isLoginFinish = true;//设置网络请求结束标志
            if (isAnimaFinish && isGettypeFinish) {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
            }
        }
        if (API.Paint_Type.equals(url)) {
            isGettypeFinish = true;//获取绘画分类结束
            if (isAnimaFinish && isLoginFinish) {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
            }
        }

    }

    class MyImageListener extends SimpleImageLoadingListener {

        @Override
        public void onLoadingStarted(String imageUri, View view) {
            super.onLoadingStarted(imageUri, view);
        }

        @Override
        public void onLoadingFailed(String imageUri, View view,
                                    FailReason failReason) {
            super.onLoadingFailed(imageUri, view, failReason);
        }

        @Override
        public void onLoadingComplete(String imageUri, View view,
                                      Bitmap loadedImage) {
            imageView.setImageBitmap(loadedImage);
            imageView.startAnimation(animation);
            super.onLoadingComplete(imageUri, view, loadedImage);
        }

        @Override
        public void onLoadingCancelled(String imageUri, View view) {
            super.onLoadingCancelled(imageUri, view);
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            //闪屏页禁掉返回键
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    DisplayImageOptions options;

    public void init() {
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.white_bg) // 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.drawable.white_bg) // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.drawable.white_bg) // 设置图片加载或解码过程中发生错误显示的图片
                .resetViewBeforeLoading(false)  // default 设置图片在加载前是否重置、复位
                .delayBeforeLoading(0)  // 下载前的延迟时间
                .cacheInMemory(false) // default  设置下载的图片是否缓存在内存中
                .cacheOnDisk(true) // default  设置下载的图片是否缓存在SD卡中
                .considerExifParams(false) // default
                .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2) // default 设置图片以如何的编码方式显示
                .bitmapConfig(Bitmap.Config.ARGB_8888) // default 设置图片的解码类型
                .displayer(new SimpleBitmapDisplayer()) // default  还可以设置圆角图片new RoundedBitmapDisplayer(20)
                .handler(new Handler()) // default
                .build();
    }

    public void doLogin(String userName, String password) {
        if (!NetworkUtil.getInstance().checkNetworkAvailable()) {
            ToastUtils.toastShaort(activity, "请检查网络");
            isLoginFinish = true;
            return;
        }

        RequestParams params = new RequestParams();
        params.put("username", userName);
        params.put("password", password);

        post(API.Login, params);
    }

    public void HXLogin(String username) {

        EMClient.getInstance().login(username, "123456", new EMCallBack() {//回调
            @Override
            public void onSuccess() {
                runOnUiThread(new Runnable() {
                    public void run() {
                        EMClient.getInstance().groupManager().loadAllGroups();
                        EMClient.getInstance().chatManager().loadAllConversations();
                        LogUtil.logE("HXLogin--->登陆聊天服务器成功！");
                    }
                });
            }

            @Override
            public void onProgress(int progress, String status) {

            }

            @Override
            public void onError(int code, String message) {
                LogUtil.logE("HXLogin--->登陆聊天服务器失败！");
            }
        });

    }

}