package com.artwall.project.activity;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;

import com.artwall.project.R;
import com.artwall.project.api.API;
import com.artwall.project.application.App;
import com.artwall.project.base.BaseActivity;
import com.artwall.project.bean.User;
import com.artwall.project.service.UserInfoService;
import com.artwall.project.util.ProgressDialogUtils;
import com.artwall.project.util.ToastUtils;
import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends BaseActivity {

    // UI references.
    private EditText userNameET;
    private EditText passwordET;
    private View mProgressView;


    @Override
    protected int getContentLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void initGui() {
        userNameET = (EditText) findViewById(R.id.login_email);
        passwordET = (EditText) findViewById(R.id.login_password);
        mProgressView = findViewById(R.id.login_progress);


    }

    @Override
    protected void initData() {


    }

    @Override
    public void onDataError(String url, String responseString) {
        super.onDataError(url, responseString);
        ProgressDialogUtils.showProgress(activity, false, mProgressView);
    }

    @Override
    public void onDataOK(String url, String responseString) {
        super.onDataOK(url, responseString);
        ProgressDialogUtils.showProgress(activity, false, mProgressView);
        try {
            JSONObject obj = new JSONObject(responseString);
            JSONObject data = obj.getJSONObject("data");
            Gson gson = new Gson();
            User user = gson.fromJson(data.toString(), User.class);
            new UserInfoService(this).saveObject(user);
            App.isLogin = true;
            App.userInfo = user;
            startActivity(new Intent(activity, MainActivity.class));

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void click(View view) {
        switch (view.getId()) {
            //登录
            case R.id.login_login_TV:
                String userName = userNameET.getText().toString();
                String password = passwordET.getText().toString();
                doLogin(userName, password);
                break;
            //关闭
            case R.id.login_close_Img:
                finish();
                break;
            //注册账号
            case R.id.login_register_TV:
                startActivity(new Intent(activity, RegisterActivity.class));
                break;
            //忘记密码
            case R.id.login_forgetPwd_TV:
                startActivity(new Intent(activity, FrogetPwdStep1Activity.class));
                break;
        }
    }

    public void doLogin(String userName, String password) {
        if (userName.isEmpty() || password.isEmpty()) {
            ToastUtils.toastShaort(activity, "用户名或密码不能为空！");
            return;
        }
        if (password.length() < 6) {
            ToastUtils.toastShaort(activity, "密码不低于六位！");
            return;
        }
        ProgressDialogUtils.showProgress(activity, true, mProgressView);
        RequestParams params = new RequestParams();
        params.put("username", userName);
        params.put("password", password);
        post(API.Login, params);

    }

}

