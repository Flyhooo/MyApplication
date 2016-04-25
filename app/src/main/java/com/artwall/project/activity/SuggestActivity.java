package com.artwall.project.activity;

import android.support.v7.widget.Toolbar;

import com.artwall.project.R;
import com.artwall.project.base.BaseActivity;

/**
 * Created by 95 on 2016/4/13.
 */
public class SuggestActivity extends BaseActivity {
    @Override
    protected int getContentLayout() {
        return R.layout.activity_suggest;
    }

    @Override
    protected void initGui() {
        toolbar = (Toolbar) this.findViewById(R.id.common_toolbar);
        toolbar.setTitle("意见反馈");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    protected void initData() {

    }

}
