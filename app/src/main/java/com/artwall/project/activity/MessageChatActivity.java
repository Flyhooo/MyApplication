package com.artwall.project.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.artwall.project.HXService.Constants;
import com.artwall.project.R;
import com.artwall.project.adapter.MessageChatAdapter;
import com.artwall.project.api.IMG;
import com.artwall.project.base.BaseActivity;
import com.artwall.project.bean.Message;

import java.util.ArrayList;

/**
 * Created by 95 on 2016/4/1.
 */
public class MessageChatActivity extends BaseActivity {

    private RecyclerView recyclerView;

    private ArrayList<Message> list = new ArrayList<>();
    private MessageChatAdapter adapter;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_messagechat;
    }

    @Override
    protected void initGui() {
        toolbar = (Toolbar) this.findViewById(R.id.common_toolbar);
        toolbar.setTitle("聊天");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = (RecyclerView) this.findViewById(R.id.MessageChat_recyclerview);
    }

    @Override
    protected void initData() {

        for (int i = 0; i < 5; i++) {
            Message message = new Message();
            message.setContent("请问您这个周末有时间帮我辅导一下素描么");
            message.setName("学生张");
            message.setTime("昨天");
            message.setImg(IMG.IMG2);
            list.add(message);
        }

        adapter = new MessageChatAdapter(this, list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new MessageChatAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent=new Intent(activity, WeChatActivity.class);
                intent.putExtra(Constants.User_ID,"18556526064");
                intent.putExtra(Constants.TYPE,WeChatActivity.CHATTYPE_SINGLE);
                startActivity(intent);
            }
        });

    }
}
