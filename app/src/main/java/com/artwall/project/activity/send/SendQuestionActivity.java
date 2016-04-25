package com.artwall.project.activity.send;

import android.support.v7.widget.Toolbar;

import com.artwall.project.R;
import com.artwall.project.base.BaseActivity;

/**
 * Created by 95 on 2016/4/15.
 */
public class SendQuestionActivity extends BaseActivity {
    @Override
    protected int getContentLayout() {
        return R.layout.activity_send_question;
    }

    @Override
    protected void initGui() {
        toolbar= (Toolbar) this.findViewById(R.id.common_toolbar);
        toolbar.setTitle("发布问答");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



    }

    @Override
    protected void initData() {

    }
}
