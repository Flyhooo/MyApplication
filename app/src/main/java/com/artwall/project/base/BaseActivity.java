package com.artwall.project.base;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.artwall.project.R;
import com.artwall.project.http.HttpClient;
import com.artwall.project.manager.AppManager;
import com.artwall.project.util.LogUtil;
import com.artwall.project.util.NetworkUtil;
import com.artwall.project.util.ToastUtils;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


/**
 * 基Activity
 *
 * @author Administrator
 */
public abstract class BaseActivity extends AppCompatActivity {
    public Activity activity;

    public Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedactivityState) {
        super.onCreate(savedactivityState);

        // 设置统一风格
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);// 竖屏
        if (getContentLayout() != 0) {
            setContentView(getContentLayout());
        }
        activity = this;

        AppManager.getAppManager().addActivity(this);


        initGui();
        initData();
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
        // 消除提示对话框,避免窗体泄露
        AppManager.getAppManager().finishActivity(this);
    }

    /**
     * @return 设置布局文件
     */
    protected abstract int getContentLayout();

    /**
     * 初始化UI
     *
     * @Description
     * @author huxiang
     */
    protected abstract void initGui();


    /**
     * 初始化数据
     *
     * @Description
     * @author huxiang
     */
    protected abstract void initData();

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * post网络请求
     *
     * @param url
     * @param params
     */
    public void post(final String url, RequestParams params) {
        LogUtil.logE(url+"---->"+params.toString());
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
                LogUtil.logE(url+"---->"+responseString);
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


    public void initToolBar(String title) {
        toolbar = (Toolbar) this.findViewById(R.id.common_toolbar);
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }


}
