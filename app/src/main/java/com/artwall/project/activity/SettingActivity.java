package com.artwall.project.activity;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.artwall.project.R;
import com.artwall.project.base.BaseActivity;
import com.artwall.project.util.DialogUtils;

/**
 * Created by 95 on 2016/4/13.
 */
public class SettingActivity extends BaseActivity {
    @Override
    protected int getContentLayout() {
        return R.layout.activity_settings;
    }

    @Override
    protected void initGui() {
        toolbar = (Toolbar) this.findViewById(R.id.common_toolbar);
        toolbar.setTitle("设置");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }

    @Override
    protected void initData() {

    }

    public void click(View view) {
        switch (view.getId()) {
            //退出登录
            case R.id.Setting_logout_RL:
                DialogUtils.showDialog(activity, "", "确定退出登录么", "退出", "不了", null, null);
                break;
            //个人资料
            case R.id.Setting_userinfo_RL:
                startActivity(new Intent(activity, UserInfoActivity.class));
                break;
            //版本更新
            case R.id.Setting_update_RL:

                break;
            //帮助支持
            case R.id.Setting_help_RL:
                startActivity(new Intent(activity, HelpActivity.class));
                break;
            //账户与安全
            case R.id.Setting_account_RL:
                startActivity(new Intent(activity, AccountSafeActivity.class));
                break;
            //清除缓存
            case R.id.Setting_cache_RL:

                break;
            //建议反馈
            case R.id.Setting_suggest_RL:
                startActivity(new Intent(activity, SuggestActivity.class));
                break;

        }
    }

}
