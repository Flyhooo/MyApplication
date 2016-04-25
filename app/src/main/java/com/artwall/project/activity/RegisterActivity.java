package com.artwall.project.activity;

import android.content.Intent;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.artwall.project.R;
import com.artwall.project.api.API;
import com.artwall.project.base.BaseActivity;
import com.artwall.project.util.ToastUtils;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by 95 on 2016/4/19.
 */
public class RegisterActivity extends BaseActivity {

    private TextView getCodeTV;
    private CountDownTimer timer = new CountDownTimer(60000, 1000) {

        @Override
        public void onTick(long millisUntilFinished) {
            getCodeTV.setEnabled(false);
            getCodeTV.setText((millisUntilFinished / 1000) + "秒...");
        }

        @Override
        public void onFinish() {
            getCodeTV.setEnabled(true);
            getCodeTV.setText("获取验证码");
        }
    };

    private EditText nicknameET, usernameET, passwordET;
    private EditText codeET;
    /**
     * 本地记录code用于本地验证
     */
    private String code = "";

    String nickname, username, password;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_register;
    }

    @Override
    protected void initGui() {
        getCodeTV = (TextView) this.findViewById(R.id.register_getCode_TV);
        nicknameET = (EditText) this.findViewById(R.id.register_nickname);
        usernameET = (EditText) this.findViewById(R.id.register_email);
        passwordET = (EditText) this.findViewById(R.id.register_password);
        codeET = (EditText) this.findViewById(R.id.register_code);

    }

    @Override
    protected void initData() {

    }

    public void click(View view) {

        switch (view.getId()) {
            case R.id.register_getCode_TV:
                timer.start();
                nickname = nicknameET.getText().toString();
                username = usernameET.getText().toString();
                password = passwordET.getText().toString();
                getCode(nickname, username, password);
                break;

            case R.id.register_close_IV:
                finish();
                break;

            case R.id.register_register_TV:
                String coodeInput = codeET.getText().toString();
                judge(coodeInput);
                break;
        }


    }

    /**
     * 获取验证码
     */
    private void getCode(String nickname, String username, String password) {
        if (nickname.isEmpty() || username.isEmpty() || password.isEmpty()) {
            ToastUtils.toastShaort(activity, "请完善以上信息！");
            return;
        }
        RequestParams params = new RequestParams();
        params.put("username", username);
        post(API.Code, params);
    }

    /**
     * 服务端判断验证码
     */
    private void judge(String code) {
        //先做本地判断
        if (code.isEmpty() || !code.equals(code)) {
            ToastUtils.toastShaort(activity, "验证码错误！");
            return;
        }
        RequestParams params = new RequestParams();
        params.put("username", username);
        params.put("password", password);
        params.put("code", code);
        post(API.Judge, params);
    }


    @Override
    public void onDataOK(String url, String responseString) {
        super.onDataOK(url, responseString);
        if (url.equals(API.Code)) {
            try {
                code = new JSONObject(responseString).getJSONObject("data").getString("code");
                codeET.setText(code);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if (url.equals(API.Judge)) {
            Intent intent = new Intent(activity, SelectCharacterActivity.class);
            intent.putExtra("nickname", nickname);
            intent.putExtra("username", username);
            intent.putExtra("password", password);
            //下一步选择用户角色
            startActivity(intent);
        }

    }
}
