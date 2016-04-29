package com.artwall.project.activity.send;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.artwall.project.R;
import com.artwall.project.adapter.SendTeachAdapter;
import com.artwall.project.base.BaseActivity;
import com.artwall.project.bean.SendTeach;
import com.artwall.project.util.ImageLoaderUtil;
import com.artwall.project.util.LogUtil;
import com.artwall.project.widget.NScroll.NoScrollListView;

import java.util.ArrayList;

import me.nereo.multi_image_selector.MultiImageSelectorActivity;

/**
 * Created by 95 on 2016/4/15.
 */
public class SendTeachActivity extends BaseActivity {

    /**
     * 成品图图片
     */
    private final int REQUEST_IMAGE = 0x000;
    /**
     * 步骤图图片
     */
    private final int REQUEST_IMAGE_ADD = 0x001;
    private ImageView imageView;

    private SendTeachAdapter adapter;
    private ArrayList<SendTeach> list = new ArrayList<>();
    private NoScrollListView nsListView;
    private int position;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_send_teach;
    }

    @Override
    protected void initGui() {
        toolbar = (Toolbar) this.findViewById(R.id.common_toolbar);
        toolbar.setTitle("上传教学");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        imageView = (ImageView) this.findViewById(R.id.SendTeach_image);
        nsListView = (NoScrollListView) this.findViewById(R.id.SendTeach_nsListview);

        list.add(new SendTeach());
        adapter = new SendTeachAdapter(activity, list);
        nsListView.setAdapter(adapter);

    }

    @Override
    protected void initData() {
        adapter.setOnImageClickListener(new SendTeachAdapter.OnImageClickListener() {
            @Override
            public void onItemClick(View view, int p) {
                position = p;
                Intent intent = new Intent(activity,
                        MultiImageSelectorActivity.class);
                // 是否允许拍照
                intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA,
                        true);
                // 图片选择上限
                intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, 1);
                // 图片选择模式：单选/多选
                intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE,
                        MultiImageSelectorActivity.MODE_SINGLE);
                startActivityForResult(intent, REQUEST_IMAGE_ADD);
            }
        });


    }


    public void click(View view) {
        switch (view.getId()) {

            case R.id.SendTeach_image:
                Intent intent = new Intent(activity,
                        MultiImageSelectorActivity.class);
                // 是否允许拍照
                intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA,
                        true);
                // 图片选择上限
                intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, 1);
                // 图片选择模式：单选/多选
                intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE,
                        MultiImageSelectorActivity.MODE_SINGLE);
                startActivityForResult(intent, REQUEST_IMAGE);
                break;
            case R.id.SendTeach_add_TV:
                list.add(new SendTeach());
                adapter.notifyDataSetChanged();
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE) {// 图片
            if (resultCode == RESULT_OK) {
                ArrayList<String> url = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                LogUtil.logE(url.size() + "");
                ImageLoaderUtil.loadLocalImage(url.get(0), imageView);
            }
        } else if (requestCode == REQUEST_IMAGE_ADD) {// 图片
            if (resultCode == RESULT_OK) {
                ArrayList<String> url = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                LogUtil.logE(url.size() + "");
                list.get(position).setImage(url.get(0));
                adapter.notifyDataSetChanged();
            }
        }
    }
}
