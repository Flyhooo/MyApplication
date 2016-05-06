package com.artwall.project.activity;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.artwall.project.R;
import com.artwall.project.base.BaseActivity;

/**
 * Created by 95 on 2016/4/13.
 */
public class MessageActivity extends BaseActivity{


    @Override
    protected int getContentLayout() {
        return R.layout.activity_message;
    }

    @Override
    protected void initGui() {
        toolbar= (Toolbar) this.findViewById(R.id.common_toolbar);
        toolbar.setTitle("消息通知");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }

    @Override
    protected void initData() {

    }

    public void click(View view){
        switch (view.getId()){
            case R.id.Message_chat_RL:

                startActivity(new Intent(activity,MessageChatActivity.class));
                break;

        }
    }




}
