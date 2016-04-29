package com.artwall.project.activity;

import android.view.View;
import android.widget.EditText;

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
 * Created by 95 on 2016/4/27.
 */
public class UpdateUserinfoActivity extends BaseActivity {

    private String title = "";
    private String content = "";

    private EditText contentET;

    private int type = 1;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_update_userinfo;
    }

    @Override
    protected void initGui() {
        contentET = (EditText) this.findViewById(R.id.Update_Usernfo_ET);
    }

    @Override
    protected void initData() {
        title = getIntent().getStringExtra("title");
        initToolBar(title);
        content = getIntent().getStringExtra("content");

        contentET.setText(content);
        if ("昵称".equals(title)) {
            type = 1;
        } else if ("个人简介".equals(title)) {
            type = 2;
        }

    }

    public void click(View view) {
        switch (view.getId()) {
            case R.id.Update_Usernfo_save_TV:
                String content = contentET.getText().toString();
                update(content, type);
                break;
        }
    }

    /**
     * 修改信息
     *
     * @param content
     * @param type    1昵称 2个人简介
     */
    private void update(String content, int type) {
        switch (type) {
            case 1:
                RequestParams params = new RequestParams();
                params.put("username", App.userInfo.getUsername());
                params.put("nickname", content);
                params.put("portrait", App.userInfo.getPortrait());
                params.put("introduce", App.userInfo.getIntroduce());
                params.put("characte", App.userInfo.getCharacte());
                params.put("groups", App.userInfo.getGroups());
                params.put("sex", App.userInfo.getSex());
                params.put("phone", App.userInfo.getPhone());
                params.put("email", App.userInfo.getEmail());
                post(API.Update_Info, params);
                break;
            case 2:
                RequestParams params2 = new RequestParams();
                params2.put("username", App.userInfo.getUsername());
                params2.put("nickname", App.userInfo.getNickname());
                params2.put("portrait", App.userInfo.getPortrait());
                params2.put("introduce", content);
                params2.put("characte", App.userInfo.getCharacte());
                params2.put("groups", App.userInfo.getGroups());
                params2.put("sex", App.userInfo.getSex());
                params2.put("phone", App.userInfo.getPhone());
                params2.put("email", App.userInfo.getEmail());
                post(API.Update_Info, params2);
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
            setResult(type);
            finish();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
