package com.artwall.project.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.artwall.project.R;
import com.artwall.project.api.API;
import com.artwall.project.application.App;
import com.artwall.project.base.BaseActivity;
import com.artwall.project.bean.User;
import com.artwall.project.service.UserInfoService;
import com.artwall.project.util.ImageLoaderUtil;
import com.artwall.project.util.ImageUtil;
import com.artwall.project.util.LogUtil;
import com.artwall.project.util.ToastUtils;
import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by 95 on 2016/4/7.
 */
public class UserInfoActivity extends BaseActivity implements View.OnClickListener {


    @Override
    protected int getContentLayout() {
        return R.layout.activity_userinfo;
    }

    //昵称 性别 简介 身份类型 手机号码 邮箱
    private TextView nicknameTV;
    private TextView sexTV;
    private TextView introduceTV;
    private TextView characterTV;
    private TextView phoneTV;
    private TextView emailTV;

    private ImageView image;

    private final int REQUEST_CODE = 0;
    private final int REQUEST_IMAGE = 1;
    private final int REQUEST_CHARACTER = 2;

    @Override
    protected void initGui() {
        initToolBar("个人信息");
        nicknameTV = (TextView) this.findViewById(R.id.userInfo_nickname);
        sexTV = (TextView) this.findViewById(R.id.userInfo_sex);
        introduceTV = (TextView) this.findViewById(R.id.userInfo_introduce);
        characterTV = (TextView) this.findViewById(R.id.userInfo_character);
        phoneTV = (TextView) this.findViewById(R.id.userInfo_phone);
        emailTV = (TextView) this.findViewById(R.id.userInfo_email);

        image = (ImageView) this.findViewById(R.id.userInfo_image);

    }

    @Override
    protected void initData() {
        updateData();
    }

    private void updateData() {
        if (App.userInfo != null) {
            nicknameTV.setText(App.userInfo.getNickname());
            sexTV.setText(App.userInfo.getSex());
            if (App.userInfo.getIntroduce().length() > 11) {
                introduceTV.setText(App.userInfo.getIntroduce().substring(0, 10) + "...");
            } else {
                introduceTV.setText(App.userInfo.getIntroduce());
            }
            characterTV.setText(App.userInfo.getCharacte());
            phoneTV.setText(App.userInfo.getPhone());
            emailTV.setText(App.userInfo.getEmail());
            ImageLoaderUtil.loadImg(image, App.userInfo.getPortrait());
        } else {
            ToastUtils.toastShaort(activity, "获取信息失败");
            finish();
        }
    }


    public void click(View view) {
        switch (view.getId()) {
            case R.id.Userinfo_userimg_RL:
                startActivityForResult(
                        new Intent(this, SelectPicPopupWindow.class), REQUEST_IMAGE);
                break;
            case R.id.Userinfo_nickname_RL:
                Intent intent = new Intent(activity, UpdateUserinfoActivity.class);
                intent.putExtra("title", "昵称");
                intent.putExtra("content", nicknameTV.getText().toString());
                startActivityForResult(intent, REQUEST_CODE);
                break;
            case R.id.Userinfo_sex_RL:
                selectSex();
                break;
            case R.id.Userinfo_introduct_RL:
                Intent intent1 = new Intent(activity, UpdateUserinfoActivity.class);
                intent1.putExtra("title", "个人简介");
                intent1.putExtra("content", App.userInfo.getIntroduce());
                startActivityForResult(intent1, REQUEST_CODE);
                break;
            case R.id.Userinfo_character_RL:
                Intent intent2 = new Intent(activity, SelectCharacterActivity.class);
                //是否是修改用户角色  还有一种是注册时跳转到用户角色的选择
                startActivityForResult(intent2, REQUEST_CHARACTER);
                break;
            case R.id.Userinfo_phone_RL:

                break;
            case R.id.Userinfo_email_RL:

                break;

        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //修改个人简介
        if (requestCode == REQUEST_CODE && resultCode == 2) {
            if (App.userInfo.getIntroduce().length() > 11) {
                introduceTV.setText(App.userInfo.getIntroduce().substring(0, 10) + "...");
            } else {
                introduceTV.setText(App.userInfo.getIntroduce());
            }
        } else if (requestCode == REQUEST_CODE && resultCode == 1) {
            //修改昵称
            nicknameTV.setText(App.userInfo.getNickname());
        } else if (requestCode == REQUEST_IMAGE && data != null) {
            String file = null;
            Bitmap bm = null;
            switch (resultCode) {
                case 8:
                    if (data != null) {
                        file = data.getStringExtra("file");
                        bm = ImageUtil.convertToBitmap(file, 200, 200);
                    }
                    break;

                case 9:
                    if (data != null) {
                        file = data.getStringExtra("file");
                        LogUtil.logE("file---" + file);
                        bm = ImageUtil.convertToBitmap(file, 200, 200);
                    }
                    break;
            }
            String image = ImageUtil.convertIconToString(bm);
            RequestParams params = new RequestParams();
            params.put("images", image);
            post(API.IMAGE_UPLOAD, params);

        } else if (requestCode == REQUEST_CHARACTER) {
            characterTV.setText(App.userInfo.getCharacte());
        }
    }

    PopupWindow popWin;

    private void selectSex() {
        // 一个自定义的布局，作为显示的内容
        View view = LayoutInflater.from(activity).inflate(
                R.layout.popup_select_sex, null);
        popWin = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //在PopupWindow里面就加上下面代码，让键盘弹出时，不会挡住pop窗口。
        popWin.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        popWin.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        //点击空白处时，隐藏掉pop窗口
        popWin.setFocusable(true);
        popWin.setBackgroundDrawable(new BitmapDrawable());
        backgroundAlpha(0.4f);
        popWin.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1f);
            }
        });
        TextView male = (TextView) view.findViewById(R.id.popup_selectSex_male_TV);
        TextView female = (TextView) view.findViewById(R.id.popup_selectSex_female_TV);
        TextView secert = (TextView) view.findViewById(R.id.popup_selectSex_secert_TV);
        TextView cancle = (TextView) view.findViewById(R.id.popup_selectSex_cancle_TV);
        male.setOnClickListener(this);
        female.setOnClickListener(this);
        secert.setOnClickListener(this);
        cancle.setOnClickListener(this);
        popWin.showAtLocation(toolbar, Gravity.BOTTOM, 0, 0);
    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getWindow().setAttributes(lp);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.popup_selectSex_male_TV:
                App.userInfo.setSex("男");
                updateUserInfo();
                popWin.dismiss();
                break;
            case R.id.popup_selectSex_female_TV:
                App.userInfo.setSex("女");
                updateUserInfo();
                popWin.dismiss();
                break;
            case R.id.popup_selectSex_secert_TV:
                App.userInfo.setSex("保密");
                updateUserInfo();
                popWin.dismiss();
                break;
            case R.id.popup_selectSex_cancle_TV:
                popWin.dismiss();
                break;
        }
    }

    private void updateUserInfo() {
        RequestParams params = new RequestParams();
        params.put("username", App.userInfo.getUsername());
        params.put("nickname", App.userInfo.getNickname());
        params.put("portrait", App.userInfo.getPortrait());
        params.put("introduce", App.userInfo.getIntroduce());
        params.put("characte", App.userInfo.getCharacte());
        params.put("groups", App.userInfo.getGroups());
        params.put("sex", App.userInfo.getSex());
        params.put("phone", App.userInfo.getPhone());
        params.put("email", App.userInfo.getEmail());
        post(API.Update_Info, params);
    }


    @Override
    public void onDataOK(String url, String responseString) {
        super.onDataOK(url, responseString);

        if (url.equals(API.IMAGE_UPLOAD)) {
            try {
                JSONObject obj = new JSONObject(responseString);
                String images = obj.getJSONObject("data").getString("images");
                App.userInfo.setPortrait(images);
                updateUserInfo();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        if (url.equals(API.Update_Info)) {
            try {
                JSONObject obj = new JSONObject(responseString);
                JSONObject data = obj.getJSONObject("data");
                Gson gson = new Gson();
                User user = gson.fromJson(data.toString(), User.class);
                new UserInfoService(this).saveObject(user);
                App.isLogin = true;
                App.userInfo = user;
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //修改完成之后更新显示
            updateData();
        }
    }
}
