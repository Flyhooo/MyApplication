package com.artwall.project.HXService.adpter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.text.Spannable;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TextView.BufferType;

import com.artwall.project.HXService.utils.Constant;
import com.artwall.project.HXService.utils.ImageCache;
import com.artwall.project.HXService.utils.ImageUtils;
import com.artwall.project.HXService.utils.SmileUtils;
import com.artwall.project.R;
import com.artwall.project.activity.ChatActivity;
import com.artwall.project.activity.WeChatActivity;
import com.artwall.project.util.ToastUtils;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMImageMessageBody;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.chat.EMVoiceMessageBody;
import com.hyphenate.util.DateUtils;
import com.hyphenate.util.EMLog;

import java.io.File;
import java.util.Date;
import java.util.Hashtable;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class MessageAdapter extends BaseAdapter {

    private final static String TAG = "msg";

    private static final int MESSAGE_TYPE_RECV_TXT = 0;
    private static final int MESSAGE_TYPE_SENT_TXT = 1;
    private static final int MESSAGE_TYPE_SENT_IMAGE = 2;
    private static final int MESSAGE_TYPE_RECV_IMAGE = 3;
    private static final int MESSAGE_TYPE_SENT_VOICE = 4;
    private static final int MESSAGE_TYPE_RECV_VOICE = 5;
    private static final int MESSAGE_TYPE_SENT_VOICE_CALL = 6;
    private static final int MESSAGE_TYPE_RECV_VOICE_CALL = 7;
    private static final int MESSAGE_TYPE_SENT_VIDEO_CALL = 8;
    private static final int MESSAGE_TYPE_RECV_VIDEO_CALL = 9;

    public static final String IMAGE_DIR = "chat/image/";
    public static final String VOICE_DIR = "chat/audio/";
    public static final String VIDEO_DIR = "chat/video";

    private String username;
    private LayoutInflater inflater;
    private Activity activity;

    // reference to conversation object in chatsdk
    private EMConversation conversation;

    private Context context;

    private Map<String, Timer> timers = new Hashtable<String, Timer>();

    public MessageAdapter(Context context, String username, int chatType) {
        this.username = username;
        this.context = context;
        inflater = LayoutInflater.from(context);
        activity = (Activity) context;
        this.conversation = EMClient.getInstance().chatManager().getConversation(username);
    }

    // public void setUser(String user) {
    // this.user = user;
    // }

    /**
     * 获取item数
     */
    public int getCount() {
        return conversation.getAllMessages().size();
    }

    /**
     * 刷新页面
     */
    public void refresh() {
        notifyDataSetChanged();
    }

    public EMMessage getItem(int position) {
        return conversation.getAllMessages().get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    /**
     * 获取item类型
     */
    public int getItemViewType(int position) {
        EMMessage message = conversation.getAllMessages().get(position);
        if (message.getType() == EMMessage.Type.TXT) {
            if (message.getBooleanAttribute(
                    Constant.MESSAGE_ATTR_IS_VOICE_CALL, false))
                return message.direct() == EMMessage.Direct.RECEIVE ? MESSAGE_TYPE_RECV_VOICE_CALL
                        : MESSAGE_TYPE_SENT_VOICE_CALL;
            else if (message.getBooleanAttribute(
                    Constant.MESSAGE_ATTR_IS_VIDEO_CALL, false))
                return message.direct() == EMMessage.Direct.RECEIVE ? MESSAGE_TYPE_RECV_VIDEO_CALL
                        : MESSAGE_TYPE_SENT_VIDEO_CALL;
            return message.direct() == EMMessage.Direct.RECEIVE ? MESSAGE_TYPE_RECV_TXT
                    : MESSAGE_TYPE_SENT_TXT;
        }
        if (message.getType() == EMMessage.Type.IMAGE) {
            return message.direct() == EMMessage.Direct.RECEIVE ? MESSAGE_TYPE_RECV_IMAGE
                    : MESSAGE_TYPE_SENT_IMAGE;

        }
        if (message.getType() == EMMessage.Type.VOICE) {
            return message.direct() == EMMessage.Direct.RECEIVE ? MESSAGE_TYPE_RECV_VOICE
                    : MESSAGE_TYPE_SENT_VOICE;
        }

        return -1;// invalid
    }

    public int getViewTypeCount() {
        return 10;
    }

    private View createViewByMessage(EMMessage message, int position) {
        switch (message.getType()) {
            case IMAGE:
                return message.direct() == EMMessage.Direct.RECEIVE ? inflater
                        .inflate(R.layout.row_received_picture, null) : inflater
                        .inflate(R.layout.row_sent_picture, null);

            case VOICE:
                return message.direct() == EMMessage.Direct.RECEIVE ? inflater
                        .inflate(R.layout.row_received_voice, null) : inflater
                        .inflate(R.layout.row_sent_voice, null);
            case TXT:
                return message.direct() == EMMessage.Direct.RECEIVE ? inflater
                        .inflate(R.layout.row_received_message, null) : inflater
                        .inflate(R.layout.row_sent_message, null);
            default:
                return message.direct() == EMMessage.Direct.RECEIVE ? inflater
                        .inflate(R.layout.row_received_message, null) : inflater
                        .inflate(R.layout.row_sent_message, null);
        }
    }

    @SuppressLint("NewApi")
    public View getView(final int position, View convertView, ViewGroup parent) {
        final EMMessage message = getItem(position);
        EMMessage.ChatType chatType = message.getChatType();
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = createViewByMessage(message, position);
            if (message.getType() == EMMessage.Type.IMAGE) {
                try {
                    holder.iv = ((ImageView) convertView
                            .findViewById(R.id.iv_sendPicture));
                    holder.head_iv = (ImageView) convertView
                            .findViewById(R.id.iv_userhead);
                    holder.tv = (TextView) convertView
                            .findViewById(R.id.percentage);
                    holder.pb = (ProgressBar) convertView
                            .findViewById(R.id.progressBar);
                    holder.staus_iv = (ImageView) convertView
                            .findViewById(R.id.msg_status);
                    holder.tv_userId = (TextView) convertView
                            .findViewById(R.id.tv_userid);
                } catch (Exception e) {
                }

            } else if (message.getType() == EMMessage.Type.TXT) {

                try {
                    holder.pb = (ProgressBar) convertView
                            .findViewById(R.id.pb_sending);
                    holder.staus_iv = (ImageView) convertView
                            .findViewById(R.id.msg_status);
                    holder.head_iv = (ImageView) convertView
                            .findViewById(R.id.iv_userhead);
                    // 这里是文字内容
                    holder.tv = (TextView) convertView
                            .findViewById(R.id.tv_chatcontent);
                    holder.tv_userId = (TextView) convertView
                            .findViewById(R.id.tv_userid);
                } catch (Exception e) {
                }


            } else if (message.getType() == EMMessage.Type.VOICE) {
                try {
                    holder.iv = ((ImageView) convertView
                            .findViewById(R.id.iv_voice));
                    holder.head_iv = (ImageView) convertView
                            .findViewById(R.id.iv_userhead);
                    holder.tv = (TextView) convertView
                            .findViewById(R.id.tv_length);
                    holder.pb = (ProgressBar) convertView
                            .findViewById(R.id.pb_sending);
                    holder.staus_iv = (ImageView) convertView
                            .findViewById(R.id.msg_status);
                    holder.tv_userId = (TextView) convertView
                            .findViewById(R.id.tv_userid);
                    holder.iv_read_status = (ImageView) convertView
                            .findViewById(R.id.iv_unread_voice);
                } catch (Exception e) {
                }
            }

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // 如果是发送的消息并且不是群聊消息，显示已读textview
        if (message.direct() == EMMessage.Direct.SEND
                && chatType != EMMessage.ChatType.GroupChat) {
            holder.tv_ack = (TextView) convertView.findViewById(R.id.tv_ack);
            holder.tv_delivered = (TextView) convertView
                    .findViewById(R.id.tv_delivered);
            if (holder.tv_ack != null) {
                if (message.isAcked()) {
                    if (holder.tv_delivered != null) {
                        holder.tv_delivered.setVisibility(View.INVISIBLE);
                    }
                    holder.tv_ack.setVisibility(View.VISIBLE);
                } else {
                    holder.tv_ack.setVisibility(View.INVISIBLE);

                    // check and display msg delivered ack status
                    if (holder.tv_delivered != null) {
                        if (message.isDelivered()) {
                            holder.tv_delivered.setVisibility(View.VISIBLE);
                        } else {
                            holder.tv_delivered.setVisibility(View.INVISIBLE);
                        }
                    }
                }
            }
        } else {
            // 如果是文本或者地图消息并且不是group messgae，显示的时候给对方发送已读回执
            if ((message.getType() == EMMessage.Type.TXT || message.getType() == EMMessage.Type.LOCATION)
                    && !message.isAcked() && chatType != EMMessage.ChatType.GroupChat) {
                // 不是语音通话记录
                if (!message.getBooleanAttribute(
                        Constant.MESSAGE_ATTR_IS_VOICE_CALL, false)) {
                    try {
                        EMClient.getInstance().chatManager().ackMessageRead(
                                message.getFrom(), message.getMsgId());
                        // 发送已读回执
                        message.setAcked(true);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        switch (message.getType()) {
            // 根据消息type显示item
            case IMAGE: // 图片
                handleImageMessage(message, holder, position, convertView);
                break;
            case TXT: // 文本
                handleTextMessage(message, holder, position);
                break;
            case VOICE: // 语音
                handleVoiceMessage(message, holder, position, convertView);
                break;
            default:
        }

        if (message.direct() == EMMessage.Direct.SEND) {
            View statusView = convertView.findViewById(R.id.msg_status);
            // 重发按钮点击事件
            statusView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {

                    // 显示重发消息的自定义alertdialog
                    Intent intent = new Intent(activity, AlertDialog.class);
                    intent.putExtra("msg", "是否重发?");
                    intent.putExtra("title", "重发");
                    intent.putExtra("cancel", true);
                    intent.putExtra("position", position);
                    if (message.getType() == EMMessage.Type.TXT)
                        activity.startActivityForResult(intent,
                                WeChatActivity.REQUEST_CODE_TEXT);
                    else if (message.getType() == EMMessage.Type.VOICE)
                        activity.startActivityForResult(intent,
                                WeChatActivity.REQUEST_CODE_VOICE);
                    else if (message.getType() == EMMessage.Type.IMAGE)
                        activity.startActivityForResult(intent,
                                WeChatActivity.REQUEST_CODE_PICTURE);

                }
            });

        } else {
            final String st = "加入黑名单";
            // 长按头像，移入黑名单
            holder.head_iv.setOnLongClickListener(new OnLongClickListener() {

                @Override
                public boolean onLongClick(View v) {
                    Intent intent = new Intent(activity, AlertDialog.class);
                    intent.putExtra("msg", st);
                    intent.putExtra("cancel", true);
                    intent.putExtra("position", position);
                    activity.startActivityForResult(intent,
                            WeChatActivity.REQUEST_CODE_ADD_TO_BLACKLIST);
                    return true;
                }
            });
        }

        TextView timestamp = (TextView) convertView
                .findViewById(R.id.timestamp);

        if (position == 0) {
            timestamp.setText(DateUtils.getTimestampString(new Date(message
                    .getMsgTime())));
            timestamp.setVisibility(View.VISIBLE);
        } else {
            // 两条消息时间离得如果稍长，显示时间
            if (DateUtils.isCloseEnough(message.getMsgTime(), conversation
                    .getAllMessages().get(position - 1).getMsgTime())) {
                timestamp.setVisibility(View.GONE);
            } else {
                timestamp.setText(DateUtils.getTimestampString(new Date(message
                        .getMsgTime())));
                timestamp.setVisibility(View.VISIBLE);
            }
        }
        return convertView;
    }

    /**
     * 文本消息
     *
     * @param message
     * @param holder
     * @param position
     */
    private void handleTextMessage(EMMessage message, ViewHolder holder,
                                   final int position) {
        EMTextMessageBody txtBody = (EMTextMessageBody) message.getBody();
        Spannable span = SmileUtils
                .getSmiledText(context, txtBody.getMessage());
        // 设置内容
        holder.tv.setText(span, BufferType.SPANNABLE);
        // 设置长按事件监听
        holder.tv.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {


                return true;
            }
        });

        if (message.direct() == EMMessage.Direct.SEND) {
            switch (message.status()) {
                case SUCCESS: // 发送成功
                    holder.pb.setVisibility(View.GONE);
                    holder.staus_iv.setVisibility(View.GONE);
                    break;
                case FAIL: // 发送失败
                    holder.pb.setVisibility(View.GONE);
                    holder.staus_iv.setVisibility(View.VISIBLE);
                    break;
                case INPROGRESS: // 发送中
                    holder.pb.setVisibility(View.VISIBLE);
                    holder.staus_iv.setVisibility(View.GONE);
                    break;
                default:
                    // 发送消息
                    sendMsgInBackground(message, holder);
            }
        }
    }


    /**
     * 图片消息
     *
     * @param message
     * @param holder
     * @param position
     * @param convertView
     */
    private void handleImageMessage(final EMMessage message,
                                    final ViewHolder holder, final int position, View convertView) {
        holder.pb.setTag(position);
        holder.iv.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                activity.startActivityForResult((new Intent(activity,
                                ContextMenu.class)).putExtra("position", position)
                                .putExtra("type", EMMessage.Type.IMAGE.ordinal()),
                        WeChatActivity.REQUEST_CODE_CONTEXT_MENU);
                return true;
            }
        });

        // 接收方向的消息
        if (message.direct() == EMMessage.Direct.RECEIVE) {
            // "it is receive msg";
            if (message.status() == EMMessage.Status.INPROGRESS) {
                // "!!!! back receive";
                holder.iv.setImageResource(R.drawable.ic_menu_camera);
                // downloadImage(message, holder);
            } else {
                // "!!!! not back receive, show image directly");
                holder.pb.setVisibility(View.GONE);
                holder.tv.setVisibility(View.GONE);
                holder.iv.setImageResource(R.drawable.ic_menu_camera);
                EMImageMessageBody imgBody = (EMImageMessageBody) message.getBody();
                if (imgBody.getLocalUrl() != null) {
                    // String filePath = imgBody.getLocalUrl();
                    String remotePath = imgBody.getRemoteUrl();
                    String filePath = ImageUtils.getImagePath(remotePath);
                    String thumbRemoteUrl = imgBody.getThumbnailUrl();
                    String thumbnailPath = ImageUtils
                            .getThumbnailImagePath(thumbRemoteUrl);
                    showImageView(thumbnailPath, holder.iv, filePath,
                            imgBody.getRemoteUrl(), message);
                }
            }
            return;
        }

        // 发送的消息
        // process send message
        // send pic, show the pic directly
        EMImageMessageBody imgBody = (EMImageMessageBody) message.getBody();
        String filePath = imgBody.getLocalUrl();
        if (filePath != null && new File(filePath).exists()) {
            showImageView(ImageUtils.getThumbnailImagePath(filePath),
                    holder.iv, filePath, null, message);
        } else {
            showImageView(ImageUtils.getThumbnailImagePath(filePath),
                    holder.iv, filePath, IMAGE_DIR, message);
        }

        switch (message.status()) {
            case SUCCESS:
                holder.pb.setVisibility(View.GONE);
                holder.tv.setVisibility(View.GONE);
                holder.staus_iv.setVisibility(View.GONE);
                break;
            case FAIL:
                holder.pb.setVisibility(View.GONE);
                holder.tv.setVisibility(View.GONE);
                holder.staus_iv.setVisibility(View.VISIBLE);
                break;
            case INPROGRESS:
                holder.staus_iv.setVisibility(View.GONE);
                holder.pb.setVisibility(View.VISIBLE);
                holder.tv.setVisibility(View.VISIBLE);
                if (timers.containsKey(message.getMsgId()))
                    return;
                // set a timer
                final Timer timer = new Timer();
                timers.put(message.getMsgId(), timer);
                timer.schedule(new TimerTask() {

                    @Override
                    public void run() {
                        activity.runOnUiThread(new Runnable() {
                            public void run() {
                                holder.pb.setVisibility(View.VISIBLE);
                                holder.tv.setVisibility(View.VISIBLE);
                                holder.tv.setText(message.progress() + "%");
                                if (message.status() == EMMessage.Status.SUCCESS) {
                                    holder.pb.setVisibility(View.GONE);
                                    holder.tv.setVisibility(View.GONE);
                                    // message.setSendingStatus(Message.SENDING_STATUS_SUCCESS);
                                    timer.cancel();
                                } else if (message.status() == EMMessage.Status.FAIL) {
                                    holder.pb.setVisibility(View.GONE);
                                    holder.tv.setVisibility(View.GONE);
                                    // message.setSendingStatus(Message.SENDING_STATUS_FAIL);
                                    // message.setProgress(0);
                                    holder.staus_iv.setVisibility(View.VISIBLE);
                                    ToastUtils.toastShaort(activity, "发送失败");
                                    timer.cancel();
                                }

                            }
                        });

                    }
                }, 0, 500);
                break;
            default:
                sendPictureMessage(message, holder);
        }
    }


    /**
     * 语音消息
     *
     * @param message
     * @param holder
     * @param position
     * @param convertView
     */
    private void handleVoiceMessage(final EMMessage message,
                                    final ViewHolder holder, final int position, View convertView) {
        EMVoiceMessageBody voiceBody = (EMVoiceMessageBody) message.getBody();
        holder.tv.setText(voiceBody.getLength() + "\"");
        holder.iv.setOnClickListener(new VoicePlayClickListener(message,
                holder.iv, holder.iv_read_status, this, activity, username));
        holder.iv.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                activity.startActivityForResult((new Intent(activity,
                                ContextMenu.class)).putExtra("position", position)
                                .putExtra("type", EMMessage.Type.VOICE.ordinal()),
                        WeChatActivity.REQUEST_CODE_CONTEXT_MENU);
                return true;
            }
        });
        if (((WeChatActivity) activity).playMsgId != null
                && ((WeChatActivity) activity).playMsgId.equals(message
                .getMsgId()) && VoicePlayClickListener.isPlaying) {
            AnimationDrawable voiceAnimation;
            if (message.direct() == EMMessage.Direct.RECEIVE) {
                holder.iv.setImageResource(R.drawable.ic_menu_camera);
            } else {
                holder.iv.setImageResource(R.drawable.ic_menu_camera);
            }
            voiceAnimation = (AnimationDrawable) holder.iv.getDrawable();
            voiceAnimation.start();
        } else {
            if (message.direct() == EMMessage.Direct.RECEIVE) {
                holder.iv.setImageResource(R.drawable.ic_menu_camera);
            } else {
                holder.iv.setImageResource(R.drawable.ic_menu_camera);
            }
        }

        if (message.direct() == EMMessage.Direct.RECEIVE) {
            if (message.isListened()) {
                // 隐藏语音未听标志
                holder.iv_read_status.setVisibility(View.INVISIBLE);
            } else {
                holder.iv_read_status.setVisibility(View.VISIBLE);
            }
            System.err.println("it is receive msg");
            if (message.status() == EMMessage.Status.INPROGRESS) {
                holder.pb.setVisibility(View.VISIBLE);
                System.err.println("!!!! back receive");

            } else {
                holder.pb.setVisibility(View.INVISIBLE);

            }
            return;
        }

        // until here, deal with send voice msg
        switch (message.status()) {
            case SUCCESS:
                holder.pb.setVisibility(View.GONE);
                holder.staus_iv.setVisibility(View.GONE);
                break;
            case FAIL:
                holder.pb.setVisibility(View.GONE);
                holder.staus_iv.setVisibility(View.VISIBLE);
                break;
            case INPROGRESS:
                holder.pb.setVisibility(View.VISIBLE);
                holder.staus_iv.setVisibility(View.GONE);
                break;
            default:
                sendMsgInBackground(message, holder);
        }
    }


    /**
     * 发送消息
     *
     * @param message
     * @param holder
     */
    public void sendMsgInBackground(final EMMessage message,
                                    final ViewHolder holder) {
        holder.staus_iv.setVisibility(View.GONE);
        holder.pb.setVisibility(View.VISIBLE);

        final long start = System.currentTimeMillis();
        // 调用sdk发送异步发送方法
        EMClient.getInstance().chatManager().sendMessage(message);
        message.setMessageStatusCallback(new EMCallBack() {

            @Override
            public void onSuccess() {

                updateSendedView(message, holder);
            }

            @Override
            public void onError(int code, String error) {

                updateSendedView(message, holder);
            }

            @Override
            public void onProgress(int progress, String status) {
            }

        });

    }


    /*
     * send message with new sdk
     */
    private void sendPictureMessage(final EMMessage message,
                                    final ViewHolder holder) {
        try {
            String to = message.getTo();

            // before send, update ui
            holder.staus_iv.setVisibility(View.GONE);
            holder.pb.setVisibility(View.VISIBLE);
            holder.tv.setVisibility(View.VISIBLE);
            holder.tv.setText("0%");

            final long start = System.currentTimeMillis();
            // if (chatType == ChatActivity.CHATTYPE_SINGLE) {
            EMClient.getInstance().chatManager().sendMessage(message);
            message.setMessageStatusCallback(new EMCallBack() {

                @Override
                public void onSuccess() {
                    Log.d(TAG, "send image message successfully");
                    activity.runOnUiThread(new Runnable() {
                        public void run() {
                            // send success
                            holder.pb.setVisibility(View.GONE);
                            holder.tv.setVisibility(View.GONE);
                        }
                    });
                }

                @Override
                public void onError(int code, String error) {

                    activity.runOnUiThread(new Runnable() {
                        public void run() {
                            holder.pb.setVisibility(View.GONE);
                            holder.tv.setVisibility(View.GONE);
                            // message.setSendingStatus(Message.SENDING_STATUS_FAIL);
                            holder.staus_iv.setVisibility(View.VISIBLE);
                            ToastUtils.toastShaort(activity, "连接失败");
                        }
                    });
                }

                @Override
                public void onProgress(final int progress, String status) {
                    activity.runOnUiThread(new Runnable() {
                        public void run() {
                            holder.tv.setText(progress + "%");
                        }
                    });
                }

            });


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 更新ui上消息发送状态
     *
     * @param message
     * @param holder
     */
    private void updateSendedView(final EMMessage message,
                                  final ViewHolder holder) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // send success
                if (message.getType() == EMMessage.Type.VIDEO) {
                    holder.tv.setVisibility(View.GONE);
                }
                if (message.status() == EMMessage.Status.SUCCESS) {
                    // if (message.getType() == EMMessage.Type.FILE) {
                    // holder.pb.setVisibility(View.INVISIBLE);
                    // holder.staus_iv.setVisibility(View.INVISIBLE);
                    // } else {
                    // holder.pb.setVisibility(View.GONE);
                    // holder.staus_iv.setVisibility(View.GONE);
                    // }

                } else if (message.status() == EMMessage.Status.FAIL) {
                    // if (message.getType() == EMMessage.Type.FILE) {
                    // holder.pb.setVisibility(View.INVISIBLE);
                    // } else {
                    // holder.pb.setVisibility(View.GONE);
                    // }
                    // holder.staus_iv.setVisibility(View.VISIBLE);
                    ToastUtils.toastShaort(activity, "连接失败");
                }

                notifyDataSetChanged();
            }
        });
    }

    /**
     * load image into image view
     *
     * @param thumbernailPath
     * @param iv
     * @return the image exists or not
     */
    private boolean showImageView(final String thumbernailPath,
                                  final ImageView iv, final String localFullSizePath,
                                  String remoteDir, final EMMessage message) {
        // String imagename =
        // localFullSizePath.substring(localFullSizePath.lastIndexOf("/") + 1,
        // localFullSizePath.length());
        // final String remote = remoteDir != null ? remoteDir+imagename :
        // imagename;
        final String remote = remoteDir;
        EMLog.d("###", "local = " + localFullSizePath + " remote: " + remote);
        // first check if the thumbnail image already loaded into cache
        Bitmap bitmap = ImageCache.getInstance().get(thumbernailPath);
        if (bitmap != null) {
            // thumbnail image is already loaded, reuse the drawable
            iv.setImageBitmap(bitmap);
            iv.setClickable(true);
            iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO 查看大图
                    System.err.println("image view on click");
                    Intent intent = new Intent(activity, ChatActivity.class);
                    File file = new File(localFullSizePath);
                    if (file.exists()) {
                        Uri uri = Uri.fromFile(file);
                        intent.putExtra("uri", uri);
                        System.err
                                .println("here need to check why download everytime");
                    } else {
                        // The local full size pic does not exist yet.
                        // ShowBigImage needs to download it from the server
                        // first
                        // intent.putExtra("", message.get);
                        EMImageMessageBody body = (EMImageMessageBody) message
                                .getBody();
                        intent.putExtra("secret", body.getSecret());
                        intent.putExtra("remotepath", remote);
                    }
                    if (message != null
                            && message.direct() == EMMessage.Direct.RECEIVE
                            && !message.isAcked()
                            && message.getChatType() != EMMessage.ChatType.GroupChat) {
                        try {
                            EMClient.getInstance().chatManager().ackMessageRead(
                                    message.getFrom(), message.getMsgId());
                            message.setAcked(true);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    activity.startActivity(intent);
                }
            });
            return true;
        } else {

//            new LoadImageTask().execute(thumbernailPath, localFullSizePath,
//                    remote, message.getChatType(), iv, activity, message);
            return true;
        }

    }


    public static class ViewHolder {
        ImageView iv;
        TextView tv;
        ProgressBar pb;
        ImageView staus_iv;
        ImageView head_iv;
        TextView tv_userId;
        ImageView iv_read_status;
        // 显示已读回执状态
        TextView tv_ack;
        // 显示送达回执状态
        TextView tv_delivered;

    }


}