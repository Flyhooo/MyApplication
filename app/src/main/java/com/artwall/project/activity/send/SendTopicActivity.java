package com.artwall.project.activity.send;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;

import com.artwall.project.R;
import com.artwall.project.adapter.ImageAdapter;
import com.artwall.project.api.API;
import com.artwall.project.application.App;
import com.artwall.project.base.BaseActivity;
import com.artwall.project.bean.TopicType;
import com.artwall.project.service.RuntimeInfoService;
import com.artwall.project.service.SendTopicService;
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

    private EditText titleET;
    private EditText introduceET;

    @Override
    protected void initGui() {
        initToolBar("发布话题");

        titleET = (EditText) this.findViewById(R.id.SendTopic_title);
        introduceET = (EditText) this.findViewById(R.id.SendTopic_introduce);

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
                if (imageList != null && !"".equals(imageList.get(0))) {
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
        } else if (url.equals(API.Topic_Send)) {
            ToastUtils.toastShaort(activity, "success");
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
                // 刷新UI--已选择图片
                imageList.removeAll(imageList);
                if (data1.size() == 0) {
                    imageList.add("");
                } else {
                    imageList.addAll(data1);
                }
                adapter.notifyDataSetChanged();
            }
        }
    }

    public void click(View view) {
        switch (view.getId()) {
            case R.id.SendTopic_send:
                String title = titleET.getText().toString();
                String introduce = introduceET.getText().toString();
                if (title.equals("")) {
                    ToastUtils.toastShaort(activity, "标题不能为空！");
                    break;
                }
                if (introduce.equals("")) {
                    ToastUtils.toastShaort(activity, "描述不能为空！");
                    break;
                }
                if (imageList != null && !imageList.get(0).equals("")) {
                    sendWithImg(title, introduce, imageList);
                } else {
                    sendWithOutImg(title, introduce);
                }
                break;
        }
    }

    /**
     * 不带图片发送
     *
     * @param title
     * @param introduce
     */
    private void sendWithOutImg(String title, String introduce) {
        RequestParams params = new RequestParams();
        params.put("userid", App.userInfo.getUserid());
        params.put("tag", "");
        params.put("fication", "");
        params.put("title", title);
        params.put("content", introduce);
        params.put("images", "");
        params.put("location", "合肥市蜀山区");
        post(API.Topic_Send, params);

    }

    /**
     * 带图片发送
     *
     * @param title
     * @param introduce
     * @param imageList
     */
    private void sendWithImg(String title, String introduce, ArrayList<String> imageList) {
        Intent intent = new Intent(activity, SendTopicService.class);
        intent.putStringArrayListExtra("imageList", imageList);
        intent.putExtra("title", title);
        intent.putExtra("introduce", introduce);
        startService(intent);
        finish();
    }


}
