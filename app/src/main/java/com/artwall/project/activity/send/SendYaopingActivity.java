package com.artwall.project.activity.send;

import android.support.v7.widget.Toolbar;

import com.artwall.project.R;
import com.artwall.project.base.BaseActivity;

/**
 * Created by 95 on 2016/4/15.
 */
public class SendYaopingActivity extends BaseActivity {
    @Override
    protected int getContentLayout() {
        return R.layout.activity_send_yaoping;
    }

    @Override
    protected void initGui() {
        toolbar = (Toolbar) this.findViewById(R.id.common_toolbar);
        toolbar.setTitle("发布邀评");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }

    @Override
    protected void initData() {

    }
}
