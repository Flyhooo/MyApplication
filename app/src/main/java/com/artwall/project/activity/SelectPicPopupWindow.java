package com.artwall.project.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.artwall.project.R;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


/**
 * 选择图片对话框式Activity
 */
public class SelectPicPopupWindow extends Activity {

    /**
     * 拍照,选择图片,取消
     */
//    private Button btn_take_photo, btn_pick_photo, btn_cancel;
    private LinearLayout layout;
    private Intent intent;

    /**
     * 拍照图片的存放路径
     */
    private static final String IMAGE_FILE_LOCATION = "file:///sdcard/image.jpg";
    /**
     * 拍照图片的存放路径
     */
    private Uri imageUri = Uri.parse(IMAGE_FILE_LOCATION);
    /**
     * 拍照图片的存放路径
     */
    private String file;

    /**
     * 调用照相机的请求码
     */
    private final int REQUESTCODE_CAPTURE = 1;
    /**
     * 调用图片选择请求码
     */
    private final int REQUESTCODE_IMAGESLECT = 2;
    /**
     * 调用图片裁剪请求码
     */
    private final int REQUESTCODE_CROP = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectpic_popupwindow);
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);

        intent = getIntent();


        layout = (LinearLayout) findViewById(R.id.pop_layout);

        // 添加选择窗口范围监听可以优先获取触点，即不再执行onTouchEvent()函数，点击其他地方时执行onTouchEvent()函数销毁Activity
        layout.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                Toast.makeText(getApplicationContext(), "提示：点击窗口外部关闭窗口！", Toast.LENGTH_SHORT).show();
            }
        });

    }

    // 实现onTouchEvent触屏函数但点击屏幕时销毁本Activity
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        finish();
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode != RESULT_OK) {

            return;
        }

        // 选择完或者拍完照后会在这里处理，然后我们继续使用setResult返回Intent以便可以传递数据和调用
        switch (requestCode) {

            case REQUESTCODE_CAPTURE://照相机

                cropImageUri(imageUri, 200, 200);
                break;

            case REQUESTCODE_IMAGESLECT://图片选择

                if (data.getData() != null) {

                    imageUri = data.getData();
                    cropImageUri(imageUri, 200, 200);
                }
                break;

            case REQUESTCODE_CROP://图片裁剪

                if (data.getExtras() != null) {
                    //存储到本地
                    Bitmap bitmap = decodeUriAsBitmap(imageUri);
                    try {
                        saveFile(bitmap, "image.jpg");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    intent.putExtra("file", file);
                    setResult(9, intent);
                    finish();
                }
                break;
        }
    }

    public void click(View v) {

        switch (v.getId()) {

            case R.id.btn_take_photo://照相机

                try {
                    Intent intent_capture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//action
                    intent_capture.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    startActivityForResult(intent_capture, REQUESTCODE_CAPTURE);

                } catch (Exception e) {

                    e.printStackTrace();
                }
                break;

            case R.id.btn_pick_photo://图片选择

                //直接通过系统相册裁剪可能会由于图片过大导致崩溃，所以先请求相册回去图片的uri再跟拍照一样裁剪即可
                Intent intent_imageselect = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent_imageselect, REQUESTCODE_IMAGESLECT);
                break;

            case R.id.btn_cancel://返回

                finish();
                break;

            default:
                break;
        }
    }

    /**
     * 裁剪图片
     */
    private void cropImageUri(Uri uri, int outputX, int outputY) {

        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", outputX);
        intent.putExtra("outputY", outputY);
        intent.putExtra("scale", true);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        intent.putExtra("return-data", false);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.PNG.toString());
        intent.putExtra("noFaceDetection", true);
        startActivityForResult(intent, REQUESTCODE_CROP);

    }

    /**
     * 根据URI加载Bitmap
     */
    private Bitmap decodeUriAsBitmap(Uri uri) {
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        return bitmap;
    }

    /**
     * 获取SD卡路径
     */
    public static String getSDPath() {
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED); // 判断sd卡是否存在
        if (sdCardExist) {
            sdDir = Environment.getExternalStorageDirectory();// 获取跟目录
        }
        return sdDir.toString();
    }

    /**
     * 保存文件
     */
    public void saveFile(Bitmap bm, String fileName) throws IOException {

        String path = getSDPath() + "/qmqclient/images/";
        File dirFile = new File(path);
        if (!dirFile.exists()) {
            dirFile.mkdir();
        }
        file = path + fileName;
        File myCaptureFile = new File(path + fileName);
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
        bm.compress(Bitmap.CompressFormat.PNG, 100, bos);
        bos.flush();
        bos.close();
    }
}