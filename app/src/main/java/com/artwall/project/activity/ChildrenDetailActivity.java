package com.artwall.project.activity;

import com.artwall.project.R;
import com.artwall.project.api.API;
import com.artwall.project.base.BaseActivity;
import com.artwall.project.bean.Children;
import com.artwall.project.util.LogUtil;
import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by 95 on 2016/5/3.
 */
public class ChildrenDetailActivity extends BaseActivity {
    @Override
    protected int getContentLayout() {
        return R.layout.activity_children_detail;
    }

    @Override
    protected void initGui() {
        initToolBar(" ");


    }

    @Override
    protected void initData() {
        String id = getIntent().getStringExtra("ID");
        getData(id);
    }

    private void getData(String id) {
        RequestParams params = new RequestParams();
        params.put("id", id);
        post(API.Children_Content, params);

    }

    @Override
    public void onDataOK(String url, String responseString) {
        super.onDataOK(url, responseString);

        LogUtil.logD("--->" + responseString);

        try {
            JSONObject obj = new JSONObject(responseString);
            JSONObject data = obj.getJSONObject("data");
            Gson gson = new Gson();
            Children children = gson.fromJson(data.toString(), Children.class);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
