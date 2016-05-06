package com.artwall.project.service;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.artwall.project.api.API;
import com.artwall.project.application.App;
import com.artwall.project.base.BaseService;
import com.artwall.project.http.HttpClient;
import com.artwall.project.util.ImageUtil;
import com.artwall.project.util.LogUtil;
import com.artwall.project.util.ToastUtils;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * Created by 95 on 2016/5/4.
 */
public class SendTopicService extends BaseService {

    private StringBuilder images = new StringBuilder();
    private int imgNum;
    private int uploadNum = 0;

    private String title;
    private String introduce;
    private ArrayList<String> imageList = new ArrayList<>();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        imageList = intent.getStringArrayListExtra("imageList");
        title = intent.getStringExtra("title");
        introduce = intent.getStringExtra("introduce");
        imgNum = imageList.size();
        uploadImage(imageList.get(0));
        return super.onStartCommand(intent, flags, startId);
    }

    private void uploadImage(String imageFile) {
        Bitmap bm = null;
        bm = ImageUtil.convertToBitmap(imageFile);
        String image = ImageUtil.convertIconToString(bm);
        RequestParams params = new RequestParams();
        params.put("images", image);
        HttpClient.post(API.IMAGE_UPLOAD, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                ToastUtils.toastLong(getApplicationContext(), "发送失败！");
                stopSelf();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                LogUtil.logE(API.IMAGE_UPLOAD + "---->" + responseString);
                if (statusCode == 200) {
                    try {
                        JSONObject jobj = new JSONObject(responseString);
                        String errorCode = jobj.getString("errorCode");
                        if (Integer.parseInt(errorCode) == 0) {
                            String image = jobj.getJSONObject("data").getString("images");
                            images.append("," + image);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFinish() {
                uploadNum++;
                if (uploadNum < imgNum) {// 图片未上传完
                    uploadImage(imageList.get(uploadNum));
                } else {
                    submit();
                }
            }
        });

    }

    private void submit() {
        RequestParams params = new RequestParams();
        params.put("userid", App.userInfo.getUserid());
        params.put("tag", "");
        params.put("fication", "");
        params.put("title", title);
        params.put("content", introduce);
        params.put("images", images.subSequence(1, images.length()));
        params.put("location", "合肥市蜀山区");
        post(API.Topic_Send, params);

    }

    @Override
    public void onDataOK(String url, String responseString) {
        super.onDataOK(url, responseString);

        if (url.equals(API.Topic_Send)) {
            ToastUtils.toastLong(getApplicationContext(), "发送成功！");
            stopSelf();
        }

    }
}
