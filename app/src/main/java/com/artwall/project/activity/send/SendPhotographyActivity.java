package com.artwall.project.activity.send;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.artwall.project.R;
import com.artwall.project.base.BaseActivity;

import java.io.FileNotFoundException;

/**
 * Created by 95 on 2016/4/15.
 */
public class SendPhotographyActivity extends BaseActivity {

    private EditText contentET;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_send_photography;
    }

    @Override
    protected void initGui() {
        toolbar = (Toolbar) this.findViewById(R.id.common_toolbar);
        toolbar.setTitle("上传摄影作品");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        contentET = (EditText) this.findViewById(R.id.SendPhotography_content_ET);


    }

    @Override
    protected void initData() {

    }

    public void click(View view) {
        switch (view.getId()) {
            case R.id.SendPhotography_addImg_IV:

                break;

        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == 0) {
            ContentResolver resolver = getContentResolver();
            // 获得图片的uri
            Uri originalUri = data.getData();
            Bitmap bitmap = null;
            try {
                final Bitmap originalBitmap = BitmapFactory.decodeStream(resolver.openInputStream(originalUri));
                // 将原始图片的bitmap转换为文件
                // 上传该文件并获取url
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        insertPic(originalBitmap, 0);
                    }
                }).start();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * 插入图片
     */
    private void insertPic(Bitmap bm, final int index) {


    }
}
