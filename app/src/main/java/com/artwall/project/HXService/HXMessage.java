package com.artwall.project.HXService;

import com.artwall.project.application.App;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;

/**
 * Created by 95 on 2016/5/5.
 */
public class HXMessage {

    public static void sendMessage(String content, String toChatUsername) {
        //创建一条文本消息,content为消息文字内容，toChatUsername为对方用户或者群聊的id，后文皆是如此
        EMMessage message = EMMessage.createTxtSendMessage(content, toChatUsername);
        message.setAttribute("avator", App.userInfo.getPortrait());
        //发送消息
        EMClient.getInstance().chatManager().sendMessage(message);
    }

}
