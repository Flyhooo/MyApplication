package com.artwall.project.http;

import android.content.Context;
import android.os.Build;

import com.artwall.project.config.Config;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.BinaryHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

/**
 *
 */
public class HttpClient {
    private static AsyncHttpClient asyncHttpClient;
    private static Context context;

    public static void init(Context ctx) {
        context = ctx;
        if (asyncHttpClient == null) {
            asyncHttpClient = new AsyncHttpClient();
            asyncHttpClient.setTimeout(20000);
            asyncHttpClient.setMaxRetriesAndTimeout(3, 20000);
            //below are not required
            asyncHttpClient.addHeader("x-api-version", "1");
            asyncHttpClient.addHeader("x-app-version", Config.APP_CERSION);
            asyncHttpClient.addHeader("x-device", android.os.Build.MODEL);
            asyncHttpClient.addHeader("x-os", "Android " + Build.VERSION.RELEASE);
        }
    }

    /**
     * GET request
     *
     * @param url                     server interface
     * @param textHttpResponseHandler
     */
    public static void get(String url, TextHttpResponseHandler textHttpResponseHandler) {
        asyncHttpClient.get(context, url, textHttpResponseHandler);
    }

    /**
     * POST request
     *
     * @param url                     server interface
     * @param params                  RequestParams
     * @param textHttpResponseHandler
     */
    public static void post( String url, RequestParams params, TextHttpResponseHandler textHttpResponseHandler) {
        asyncHttpClient.post(context, url, params, textHttpResponseHandler);
    }

    /**
     * download file
     *
     * @param url
     * @param handler
     */
    public static void downloadFile( String url, BinaryHttpResponseHandler handler) {
        asyncHttpClient.get(context, url, handler);
    }
}
