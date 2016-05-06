package com.artwall.project.base;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.artwall.project.http.HttpClient;
import com.artwall.project.util.LogUtil;
import com.artwall.project.util.NetworkUtil;
import com.artwall.project.util.ToastUtils;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by 95 on 2016/5/4.
 */
public class BaseService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    /**
     * post网络请求
     *
     * @param url
     * @param params
     */
    public void post(final String url, RequestParams params) {
        LogUtil.logE(url + "---->" + params.toString());
        if (!NetworkUtil.getInstance().checkNetworkAvailable()) {
            return;
        }

        HttpClient.post(url, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                onDataOK(url, "error");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                LogUtil.logE(url + "---->" + responseString);
                if (statusCode == 200) {
                    try {
                        JSONObject jobj = new JSONObject(responseString);
                        String errorCode = jobj.getString("errorCode");
                        if (Integer.parseInt(errorCode) == 0) {
                            onDataOK(url, responseString);
                        } else {
                            onDataError(url, responseString);
                            ToastUtils.toastShaort(getApplicationContext(), jobj.getString("errorMsg"));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        });
    }

    /**
     * 网络请求返回数据处理
     *
     * @param url
     * @param responseString
     */
    public void onDataOK(String url, String responseString) {

    }

    /**
     * 网络请求返回数据处理
     *
     * @param url
     * @param responseString
     */
    public void onDataError(String url, String responseString) {

    }

}
