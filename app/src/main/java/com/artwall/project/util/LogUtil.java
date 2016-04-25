package com.artwall.project.util;

import android.util.Log;

import com.artwall.project.config.Config;

public class LogUtil {
	private static String TAG = "--ArtHelp-->";

	/***
	 * 红色提示日志
	 * @param msg
	 */
	public static void logE(String msg) {
		if (Config.isDebug) {
			Log.e(TAG, msg);
		}
	}

	/***
	 * 普通提示日志
	 * @param msg
	 */
	public static void logD(String msg) {
		if (Config.isDebug) {
			Log.d(TAG, msg);
		}
	}

}
