package com.artwall.project.application;

import android.app.ActivityManager;
import android.app.Application;
import android.content.pm.PackageManager;

import com.artwall.project.HXService.HXHelper;
import com.artwall.project.R;
import com.artwall.project.bean.User;
import com.artwall.project.http.HttpClient;
import com.artwall.project.util.ImageLoaderUtil;
import com.artwall.project.util.NetworkUtil;

import java.util.Iterator;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by 95 on 2016/3/30.
 */
public class App extends Application {
    public static App mainApp;

    /**
     * 标志是否登录
     */
    public static boolean isLogin = false;

    /**
     * 存放个人资料
     */
    public static User userInfo = null;


    @Override
    public void onCreate() {
        super.onCreate();

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fangzhengthin.TTF")
                .setFontAttrId(R.attr.fontPath).build());
        mainApp = this;
        ImageLoaderUtil.init(this);
        HttpClient.init(this);
        NetworkUtil.init(this);

        new HXHelper().init(getApplicationContext(), getAppName());
    }

    private String getAppName() {
        int pid = android.os.Process.myPid();
        String processName = null;
        ActivityManager am = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
        List l = am.getRunningAppProcesses();
        Iterator i = l.iterator();
        PackageManager pm = this.getPackageManager();
        while (i.hasNext()) {
            ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i.next());
            try {
                if (info.pid == pid) {
                    processName = info.processName;
                    return processName;
                }
            } catch (Exception e) {
            }
        }
        return processName;
    }




}
