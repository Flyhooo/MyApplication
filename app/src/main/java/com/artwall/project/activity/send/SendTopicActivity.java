package com.artwall.project.activity.send;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;

import com.artwall.project.R;
import com.artwall.project.adapter.ImageAdapter;
import com.artwall.project.api.API;
import com.artwall.project.base.BaseActivity;
import com.artwall.project.bean.TopicType;
import com.artwall.project.service.RuntimeInfoService;
import com.artwall.project.util.LogUtil;
import com.artwall.project.util.ToastUtils;
import com.artwall.project.widget.NScroll.NoScrollGridView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import me.nereo.multi_image_selector.MultiImageSelectorActivity;

/**
 * Created by 95 on 2016/4/15.
 */
public class SendTopicActivity extends BaseActivity {
    @Override
    protected int getContentLayout() {
        return R.layout.activity_send_question;
    }

    private NoScrollGridView imageGrid;
    private ArrayList<String> imageList = new ArrayList<>();
    ImageAdapter adapter;
    /**
     * 选择图片的请求码
     */
    private static final int REQUEST_IMAGE = 2;

    @Override
    protected void initGui() {
        toolbar = (Toolbar) this.findViewById(R.id.common_toolbar);
        toolbar.setTitle("发布话题");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        imageGrid = (NoScrollGridView) this.findViewById(R.id.SendTopic_nsGridview);
        imageList.add("");

        adapter = new ImageAdapter(activity, imageList);
        imageGrid.setAdapter(adapter);

    }

    @Override
    protected void initData() {
        //获取话题分类列表
        post(API.Topic_Type, new RequestParams());


        imageGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(activity,
                        MultiImageSelectorActivity.class);
                // 是否允许拍照
                intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA,
                        true);
                // 图片选择上限
                intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, 4);
                // 图片选择模式：单选/多选
                intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE,
                        MultiImageSelectorActivity.MODE_MULTI);
                //
                if (imageList != null && imageList.size() > 1) {
                    imageList.remove(imageList.size() - 1);
                    intent.putExtra(
                            MultiImageSelectorActivity.EXTRA_DEFAULT_SELECTED_LIST,
                            imageList);
                }
                startActivityForResult(intent, REQUEST_IMAGE);
            }
        });

    }

    @Override
    public void onDataOK(String url, String responseString) {
        super.onDataOK(url, responseString);
        if (url.equals(API.Topic_Type)) {
            try {
                JSONObject jobj = new JSONObject(responseString);
                String errorCode = jobj.getString("errorCode");
                if (Integer.parseInt(errorCode) == 0) {
                    JSONObject obj = new JSONObject(responseString);
                    JSONArray data = obj.getJSONArray("data");
                    Gson gson = new Gson();
                    ArrayList<TopicType> list = gson.fromJson(data.toString(), new TypeToken<List<TopicType>>() {
                    }.getType());
                    RuntimeInfoService.save(activity, "API.Topic_Type", list);
                } else {
                    ToastUtils.toastShaort(activity, jobj.getString("errorMsg"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE) {// 图片
            if (resultCode == RESULT_OK) {
                ArrayList<String> data1 = new ArrayList<>();
                data1 = data
                        .getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                LogUtil.logE(imageList.size() + "");
                for (int i = 0; i < imageList.size(); i++) {
                    LogUtil.logE(imageList.get(i));
                }
                // 刷新UI--已选择图片
                imageList.removeAll(imageList);
                imageList.addAll(data1);
                imageList.add("");
                adapter.notifyDataSetChanged();
            }
        }
    }
}
