package com.artwall.project.activity;

import android.widget.TextView;

import com.artwall.project.R;
import com.artwall.project.application.App;
import com.artwall.project.base.BaseActivity;
import com.artwall.project.util.ToastUtils;

/**
 * Created by 95 on 2016/4/7.
 */
public class UserInfoActivity extends BaseActivity {


    @Override
    protected int getContentLayout() {
        return R.layout.activity_userinfo;
    }

    //昵称 性别 简介 身份类型 手机号码 邮箱
    private TextView nicknameTV;
    private TextView sexTV;
    private TextView introduceTV;
    private TextView characterTV;
    private TextView phoneTV;
    private TextView emailTV;


    @Override
    protected void initGui() {
        initToolBar("个人信息");
        nicknameTV = (TextView) this.findViewById(R.id.userInfo_nickname);
        sexTV = (TextView) this.findViewById(R.id.userInfo_sex);
        introduceTV = (TextView) this.findViewById(R.id.userInfo_introduce);
        characterTV = (TextView) this.findViewById(R.id.userInfo_character);
        phoneTV = (TextView) this.findViewById(R.id.userInfo_phone);
        emailTV = (TextView) this.findViewById(R.id.userInfo_email);

    }

    @Override
    protected void initData() {

        if (App.userInfo != null) {
            nicknameTV.setText(App.userInfo.getNickname());
            sexTV.setText(App.userInfo.getNickname());
            introduceTV.setText(App.userInfo.getNickname());
            characterTV.setText(App.userInfo.getCharacter());
            phoneTV.setText(App.userInfo.getPhone());
            emailTV.setText(App.userInfo.getEmail());
        } else {
            ToastUtils.toastShaort(activity, "获取信息失败");
            finish();
        }

    }
}
