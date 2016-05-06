package com.artwall.project.activity;

import android.view.View;
import android.widget.EditText;
import android.widget.GridView;

import com.artwall.project.HXService.HXMessage;
import com.artwall.project.R;
import com.artwall.project.adapter.EmojiAdapter;
import com.artwall.project.base.BaseActivity;
import com.artwall.project.util.KeyboardUtils;
import com.artwall.project.util.ToastUtils;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;

import java.util.List;

/**
 * Created by 95 on 2016/4/14.
 */
public class ChatActivity extends BaseActivity {

    private GridView gridView;
    private EmojiAdapter adapter;
    private boolean isKeyboardVisiable = false;

    private EditText inputET;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_chat;
    }

    @Override
    protected void initGui() {
        initToolBar("张同学");


        gridView = (GridView) this.findViewById(R.id.Chat_input_emoji_GV);
        inputET = (EditText) this.findViewById(R.id.Chat_input_edit_ET);

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
                    isKeyboardVisiable = visible;
                    if (gridView.getVisibility() == View.VISIBLE)
                        gridView.setVisibility(View.GONE);
                }
            }
        });


        EMMessageListener msgListener = new EMMessageListener() {

            @Override
            public void onMessageReceived(List<EMMessage> messages) {
                //收到消息
                ToastUtils.toastShaort(activity, messages.get(0).toString());
            }

            @Override
            public void onCmdMessageReceived(List<EMMessage> messages) {
                //收到透传消息
            }

            @Override
            public void onMessageReadAckReceived(List<EMMessage> messages) {
                //收到已读回执
            }

            @Override
            public void onMessageDeliveryAckReceived(List<EMMessage> message) {
                //收到已送达回执
            }

            @Override
            public void onMessageChanged(EMMessage message, Object change) {
                //消息状态变动
            }
        };
        EMClient.getInstance().chatManager().addMessageListener(msgListener);


    }

    public void click(View view) {

        switch (view.getId()) {
            case R.id.Chat_input_emoji_IV:
                if (gridView.getVisibility() != View.VISIBLE) {
                    if (isKeyboardVisiable) {
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
                HXMessage.sendMessage(inputET.getText().toString(), "18556526064");
                break;

        }

    }


}
