package com.artwall.project.activity;

import android.view.View;

import com.artwall.project.R;
import com.artwall.project.base.BaseActivity;

/**
 * Created by 95 on 2016/4/20.
 * 找回密码
 */
public class FrogetPwdStep2Activity extends BaseActivity {
    @Override
    protected int getContentLayout() {
        return R.layout.activity_forgetpwd_step2;
    }

    @Override
    protected void initGui() {
        initToolBar("找回密码");

    }

    @Override
    protected void initData() {

    }

    public void click(View view) {

        switch (view.getId()) {

        }

    }

}
