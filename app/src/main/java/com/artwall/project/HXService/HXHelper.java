package com.artwall.project.HXService;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.widget.Toast;

import com.artwall.project.config.Config;
import com.artwall.project.util.LogUtil;
import com.artwall.project.util.ToastUtils;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMCmdMessageBody;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.exceptions.HyphenateException;

import java.util.List;

/**
 * Created by 95 on 2016/5/5.
 */
public class HXHelper {

    private Context mContext;
    /**
     * EMEventListener
     */
    protected EMMessageListener messageListener = null;

    public void init(Context context, String processAppName) {
        this.mContext = context;
        // 如果app启用了远程的service，此application:onCreate会被调用2次
        // 为了防止环信SDK被初始化2次，加此判断会保证SDK被初始化1次
        // 默认的app会在以包名为默认的process name下运行，如果查到的process name不是app的process name就立即返回
        if (processAppName == null || !processAppName.equalsIgnoreCase(context.getPackageName())) {
            // 则此application::onCreate 是被service 调用的，直接返回
            return;
        }

        EMOptions options = new EMOptions();
        // 默认添加好友时，是不需要验证的，改成需要验证
        options.setAcceptInvitationAlways(false);
        //初始化
        EMClient.getInstance().init(context, options);
        //在做打包混淆时，关闭debug模式，避免消耗不必要的资源
        EMClient.getInstance().setDebugMode(Config.isDebug);
        registerEventListener();
    }

    /**
     * 全局事件监听
     * 因为可能会有UI页面先处理到这个消息，所以一般如果UI页面已经处理，这里就不需要再次处理
     * activityList.size() <= 0 意味着所有页面都已经在后台运行，或者已经离开Activity Stack
     */
    protected void registerEventListener() {
        messageListener = new EMMessageListener() {
            private BroadcastReceiver broadCastReceiver = null;

            @Override
            public void onMessageReceived(List<EMMessage> messages) {
                for (EMMessage message : messages) {

                        LogUtil.logE("onMessageReceived id : " + message.getMsgId());
                        //应用在后台，不需要刷新UI,通知栏提示新消息
                    try {
                        ToastUtils.toastLong(mContext, message.getStringAttribute("avator"));
                    } catch (HyphenateException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onCmdMessageReceived(List<EMMessage> messages) {
                for (EMMessage message : messages) {
                    LogUtil.logE("收到透传消息");
                    //获取消息body
                    EMCmdMessageBody cmdMsgBody = (EMCmdMessageBody) message.getBody();
                    final String action = cmdMsgBody.action();//获取自定义action

                    //获取扩展属性 此处省略
                    //message.getStringAttribute("");
                    LogUtil.logE(String.format("透传消息：action:%s,message:%s", action, message.toString()));
//                    final String str = appContext.getString(R.string.receive_the_passthrough);

                    final String CMD_TOAST_BROADCAST = "hyphenate.demo.cmd.toast";
                    IntentFilter cmdFilter = new IntentFilter(CMD_TOAST_BROADCAST);

                    if (broadCastReceiver == null) {
                        broadCastReceiver = new BroadcastReceiver() {

                            @Override
                            public void onReceive(Context context, Intent intent) {
                                // TODO Auto-generated method stub
                                Toast.makeText(mContext, intent.getStringExtra("cmd_value"), Toast.LENGTH_SHORT).show();
                            }
                        };

                        //注册广播接收者
                        mContext.registerReceiver(broadCastReceiver, cmdFilter);
                    }

                    Intent broadcastIntent = new Intent(CMD_TOAST_BROADCAST);
                    mContext.sendBroadcast(broadcastIntent, null);
                }
            }

            @Override
            public void onMessageReadAckReceived(List<EMMessage> messages) {
            }

            @Override
            public void onMessageDeliveryAckReceived(List<EMMessage> message) {
            }

            @Override
            public void onMessageChanged(EMMessage message, Object change) {

            }
        };

        EMClient.getInstance().chatManager().addMessageListener(messageListener);
    }


}
