package com.artwall.project.activity;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

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
    StringBuilder groups = new StringBuilder();

    private boolean isFromRegister = false;

    private TextView characterTV, groupsTV;
    private LinearLayout tagLL;

    @Override
    protected void initGui() {
        characterTV = (TextView) this.findViewById(R.id.SelectCharacter_character);
        groupsTV = (TextView) this.findViewById(R.id.SelectCharacter_groups);
        tagLL = (LinearLayout) this.findViewById(R.id.SelectCharacter_tag_LL);

    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        isFromRegister = intent.getBooleanExtra("isFromRegister", false);
        if (isFromRegister) {
            //注册选择用户角色信息
            initToolBar("选择角色");
            nickname = intent.getStringExtra("nickname");
            username = intent.getStringExtra("username");
            password = intent.getStringExtra("password");
        } else {
            //修改用户角色信息
            initToolBar("更新角色");
            characterTV.setText(App.userInfo.getCharacte());
            if (null != App.userInfo.getGroups()) {
                groupsTV.setText(App.userInfo.getGroups().replaceAll(",", " "));
            }
        }


    }

    public void click(View view) {
        switch (view.getId()) {
            case R.id.SelectCharacter_submit_TV:
                String s = groups.toString().replace(" ", ",");
                if (s.endsWith(",")) {
                    s = s.substring(0, s.length() - 1);
                }
                if (isFromRegister) {
                    //注册选择用户角色信息
                    RequestParams params = new RequestParams();
                    params.put("username", username);
                    params.put("password", password);
                    params.put("nickname", nickname);
                    params.put("characte", character);
                    params.put("groups", s);
                    post(API.Register, params);
                } else {
                    //修改用户角色信息
                    RequestParams params = new RequestParams();
                    params.put("username", App.userInfo.getUsername());
                    params.put("nickname", App.userInfo.getNickname());
                    params.put("portrait", App.userInfo.getPortrait());
                    params.put("introduce", App.userInfo.getIntroduce());
                    params.put("characte", character);
                    params.put("groups", s);
                    params.put("sex", App.userInfo.getSex());
                    params.put("phone", App.userInfo.getPhone());
                    params.put("email", App.userInfo.getEmail());
                    post(API.Update_Info, params);

                }
                break;

            case R.id.SelectCharacter_teacher_TV:
                character = "老师";
                characterTV.setText(character);
                groups.setLength(0);
                groupsTV.setText("");
                tagLL.setVisibility(View.VISIBLE);
                break;
            case R.id.SelectCharacter_painter_TV:
                character = "画家";
                characterTV.setText(character);
                groups.setLength(0);
                groupsTV.setText("");
                tagLL.setVisibility(View.VISIBLE);
                break;
            case R.id.SelectCharacter_student_TV:
                character = "学生";
                characterTV.setText(character);
                groups.setLength(0);
                groupsTV.setText("");
                tagLL.setVisibility(View.GONE);
                break;
            case R.id.SelectCharacter_else_TV:
                character = "其他";
                characterTV.setText(character);
                groups.setLength(0);
                groupsTV.setText("");
                tagLL.setVisibility(View.GONE);
                break;

            case R.id.SelectCharacter_tags_11:
                selectGroups("素描");
                break;
            case R.id.SelectCharacter_tags_12:
                selectGroups("速写");
                break;
            case R.id.SelectCharacter_tags_13:
                selectGroups("彩铅");
                break;
            case R.id.SelectCharacter_tags_14:
                selectGroups("水彩");
                break;
            case R.id.SelectCharacter_tags_21:
                selectGroups("水粉");
                break;
            case R.id.SelectCharacter_tags_22:
                selectGroups("油画");
                break;
            case R.id.SelectCharacter_tags_23:
                selectGroups("国画");
                break;
            case R.id.SelectCharacter_tags_24:
                selectGroups("漫画");
                break;
            case R.id.SelectCharacter_tags_31:
                selectGroups("版画");
                break;
            case R.id.SelectCharacter_tags_32:
                selectGroups("插画");
                break;
            case R.id.SelectCharacter_tags_33:
                selectGroups("儿童");
                break;
        }

    }

    private void selectGroups(String group) {
        groups.append(group + " ");
        groupsTV.setText(groups.toString());
    }

    @Override
    public void onDataOK(String url, String responseString) {
        super.onDataOK(url, responseString);
        if (url.equals(API.Register)) {
            //注册选择用户角色信息
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
        } else if (url.equals(API.Update_Info)) {
            //修改用户角色信息
            try {
                JSONObject obj = new JSONObject(responseString);
                JSONObject data = obj.getJSONObject("data");
                Gson gson = new Gson();
                User user = gson.fromJson(data.toString(), User.class);
                new UserInfoService(this).saveObject(user);
                App.userInfo = user;
                finish();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }
}
