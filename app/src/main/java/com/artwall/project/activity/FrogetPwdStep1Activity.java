package com.artwall.project.activity;

import android.content.Intent;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;

import com.artwall.project.R;
import com.artwall.project.base.BaseActivity;

/**
 * Created by 95 on 2016/4/20.
 * 找回密码
 */
public class FrogetPwdStep1Activity extends BaseActivity {

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

    @Override
    protected int getContentLayout() {
        return R.layout.activity_forgetpwd_step1;
    }

    @Override
    protected void initGui() {
        initToolBar("找回密码");

        getCodeTV = (TextView) this.findViewById(R.id.ForgetPwdStep1_getCode_TV);


    }

    @Override
    protected void initData() {

    }

    public void click(View view) {

        switch (view.getId()) {
            case R.id.ForgetPwdStep1_getCode_TV:
                timer.start();
                break;

            case R.id.ForgetPwdStep1_next_TV:
                startActivity(new Intent(activity, FrogetPwdStep2Activity.class));
                break;

        }

    }

}
