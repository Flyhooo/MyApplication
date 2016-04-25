package com.artwall.project.activity;

import android.support.v7.widget.Toolbar;
import android.view.View;

import com.artwall.project.R;
import com.artwall.project.base.BaseActivity;

/**
 * Created by 95 on 2016/4/14.
 */
public class HelpActivity extends BaseActivity {
    @Override
    protected int getContentLayout() {
        return R.layout.activity_help;
    }

    @Override
    protected void initGui() {
        toolbar = (Toolbar) this.findViewById(R.id.common_toolbar);
        toolbar.setTitle("帮助与支持");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    protected void initData() {

    }

    public void click(View view) {
        switch (view.getId()) {
            case R.id.Help_gymw_RL:

                break;

            case R.id.Help_sybz_RL:

                break;
            case R.id.Help_yszc_RL:

                break;

            case R.id.Help_sytk_RL:

                break;

        }
    }

}
