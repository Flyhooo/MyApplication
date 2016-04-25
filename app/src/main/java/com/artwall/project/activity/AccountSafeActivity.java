package com.artwall.project.activity;

import android.support.v7.widget.Toolbar;
import android.view.View;

import com.artwall.project.R;
import com.artwall.project.base.BaseActivity;

/**
 * Created by 95 on 2016/4/13.
 */
public class AccountSafeActivity extends BaseActivity {
    @Override
    protected int getContentLayout() {
        return R.layout.activity_accountsafe;
    }

    @Override
    protected void initGui() {
        toolbar = (Toolbar) this.findViewById(R.id.common_toolbar);
        toolbar.setTitle("账号安全");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    protected void initData() {

    }

    public void click(View view) {
        switch (view.getId()) {
            case R.id.AccountAndSafe_phone_RL:

                break;

            case R.id.AccountAndSafe_email_RL:

                break;

            case R.id.AccountAndSafe_payPwd_RL:

                break;

            case R.id.AccountAndSafe_loginPwd_RL:

                break;
        }
    }
}
