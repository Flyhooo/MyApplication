package com.artwall.project.util;

import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by 95 on 2016/4/1.
 */
public class DialogUtils {


    /***
     * 弹出框
     * @param context
     * @param title
     * @param message
     * @param negative  取消
     * @param positive  确定
     * @param negativeClick 取消监听
     * @param positiveClick 确定监听
     */
    public static void showDialog(Context context, String title, String message, String negative, String positive, DialogInterface.OnClickListener negativeClick, DialogInterface.OnClickListener positiveClick) {

        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(context);
        if (!title.isEmpty()) {
            builder.setTitle(title);
        }
        if (!message.isEmpty()) {
            builder.setMessage(message);
        }
        if (!negative.isEmpty()) {
            builder.setNegativeButton(negative, negativeClick);
        }
        if (!positive.isEmpty()) {
            builder.setPositiveButton(positive, positiveClick);
        }
        builder.show();
    }

}
