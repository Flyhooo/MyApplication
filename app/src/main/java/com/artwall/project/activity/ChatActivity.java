package com.artwall.project.activity;

import android.view.View;
import android.widget.GridView;

import com.artwall.project.R;
import com.artwall.project.adapter.EmojiAdapter;
import com.artwall.project.base.BaseActivity;
import com.artwall.project.util.KeyboardUtils;

/**
 * Created by 95 on 2016/4/14.
 */
public class ChatActivity extends BaseActivity {

    private GridView gridView;
    private EmojiAdapter adapter;
    private boolean isKeyboardVisiable=false;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_chat;
    }

    @Override
    protected void initGui() {
        initToolBar("张同学");


        gridView = (GridView) this.findViewById(R.id.Chat_input_emoji_GV);

    }

    int[] emoji = {R.drawable.emoji, R.drawable.emoji, R.drawable.emoji, R.drawable.emoji, R.drawable.emoji, R.drawable.emoji, R.drawable.emoji,
            R.drawable.emoji, R.drawable.emoji, R.drawable.emoji, R.drawable.emoji, R.drawable.emoji, R.drawable.emoji, R.drawable.emoji,
            R.drawable.emoji, R.drawable.emoji, R.drawable.emoji, R.drawable.emoji, R.drawable.emoji, R.drawable.emoji, R.drawable.emoji,
            R.drawable.emoji, R.drawable.emoji, R.drawable.emoji, R.drawable.emoji, R.drawable.emoji, R.drawable.emoji, R.drawable.emoji,};

    @Override
    protected void initData() {
        KeyboardUtils.observeSoftKeyboard(this, new KeyboardUtils.OnSoftKeyboardChangeListener() {
            @Override
            public void onSoftKeyBoardChange(int softKeybardHeight, boolean visible) {
                if (visible) {
                    isKeyboardVisiable=visible;
                    if (gridView.getVisibility() == View.VISIBLE)
                        gridView.setVisibility(View.GONE);
                }
            }
        });


    }

    public void click(View view) {

        switch (view.getId()) {
            case R.id.Chat_input_emoji_IV:
                if (gridView.getVisibility() != View.VISIBLE) {
                    if(isKeyboardVisiable){
                        KeyboardUtils.hideKeyboard(this);
                    }
                    gridView.setVisibility(View.VISIBLE);
                    if (adapter == null) {
                        adapter = new EmojiAdapter(activity, emoji);
                    }
                    gridView.setAdapter(adapter);
                } else {
                    gridView.setVisibility(View.GONE);
                }
                break;
            case R.id.Chat_input_addImg_IV:

                break;
            case R.id.Chat_input_send_TV:

                break;

        }

    }

}
