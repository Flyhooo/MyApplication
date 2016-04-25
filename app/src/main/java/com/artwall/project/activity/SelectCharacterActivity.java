package com.artwall.project.activity;

import android.content.Intent;
import android.view.View;

import com.artwall.project.R;
import com.artwall.project.api.API;
import com.artwall.project.application.App;
import com.artwall.project.base.BaseActivity;
import com.artwall.project.bean.User;
import com.artwall.project.service.UserInfoService;
import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by 95 on 2016/4/21.
 */
public class SelectCharacterActivity extends BaseActivity {

    /**
     * 账号：username
     * 密码：password
     * 昵称：nickname
     * 角色：character
     *
     * @return
     */

    @Override
    protected int getContentLayout() {
        return R.layout.activity_select_character;
    }

    String username, password, nickname, character;

    @Override
    protected void initGui() {
        initToolBar("选择角色");
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        nickname = intent.getStringExtra("nickname");
        username = intent.getStringExtra("username");
        password = intent.getStringExtra("password");

    }

    public void click(View view) {

        switch (view.getId()) {
            case R.id.SelectCharacter_submit_TV:
                RequestParams params = new RequestParams();
                params.put("username", username);
                params.put("password", password);
                params.put("nickname", nickname);
                params.put("character", "学生");
                post(API.Register, params);

                break;

        }

    }

    @Override
    public void onDataOK(String url, String responseString) {
        super.onDataOK(url, responseString);
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
}
