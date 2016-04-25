package com.artwall.project.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by 95 on 2016/4/20.
 */
public class ToastUtils {

    public static void toastLong(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }


    public static void toastShaort(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

}
