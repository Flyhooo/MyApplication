package com.artwall.project.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
 * Created by 95 on 2016/3/30.
 */
public abstract class BaseFragment extends Fragment {
    protected View contentView = null;
    protected FragmentActivity activity = null;
    protected Fragment fragment = null;

    public BaseFragment() {
        super();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = (FragmentActivity) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        contentView = inflater.inflate(getMianLayout(), container, false);
        initView();
        return contentView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        initData();
        super.onActivityCreated(savedInstanceState);
    }

    /**
     * 设置布局
     *
     * @return layout id
     */
    public abstract int getMianLayout();

    /**
     * 初始化界面
     */
    public abstract void initView();

    /**
     * 初始化数据
     */
    public abstract void initData();

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
                            ToastUtils.toastShaort(activity, jobj.getString("errorMsg"));
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
