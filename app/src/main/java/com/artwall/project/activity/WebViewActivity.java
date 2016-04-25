package com.artwall.project.activity;

import android.support.v7.widget.Toolbar;
import android.webkit.WebView;

import com.artwall.project.R;
import com.artwall.project.base.BaseActivity;
import com.artwall.project.widget.MyWebViewClient;

/**
 * Created by 95 on 2016/3/31.
 */
public class WebViewActivity extends BaseActivity {

    private WebView webView;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_webview;
    }

    @Override
    protected void initGui() {
        toolbar = (Toolbar) this.findViewById(R.id.common_toolbar);
        webView = (WebView) this.findViewById(R.id.Webview_webview);

    }

    @Override
    protected void initData() {
        toolbar.setTitle("WebView");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        webView.setWebViewClient(new MyWebViewClient());
        webView.loadUrl("http://www.baidu.com");

    }
}
