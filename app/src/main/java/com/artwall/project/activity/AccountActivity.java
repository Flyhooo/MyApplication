package com.artwall.project.activity;

import android.support.v7.widget.Toolbar;
import android.view.View;

import com.artwall.project.R;
import com.artwall.project.base.BaseActivity;

/**
 * Created by 95 on 2016/4/13.
 */
public class AccountActivity extends BaseActivity {
    @Override
    protected int getContentLayout() {
        return R.layout.activity_account;
    }

    @Override
    protected void initGui() {
        toolbar = (Toolbar) this.findViewById(R.id.common_toolbar);
        toolbar.setTitle("我的账户");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    protected void initData() {

    }

    public void click(View view) {
        switch (view.getId()) {
            //提現
            case R.id.Account_withdrawCash_RL:

                break;
            //购物记录
            case R.id.Account_gouwu_RL:

                break;
            //打赏记录
            case R.id.Account_dashang_RL:

                break;
            //充值记录
            case R.id.Account_chongzhi_RL:

                break;
            //提现记录
            case R.id.Account_tixian_RL:

                break;
            //退款记录
            case R.id.Account_tuikuan_RL:

                break;
            //收货地址
            case R.id.Account_address_RL:

                break;

        }
    }

}
