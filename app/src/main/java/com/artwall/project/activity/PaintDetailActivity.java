package com.artwall.project.activity;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.artwall.project.R;
import com.artwall.project.adapter.TeachDetailAdapter;
import com.artwall.project.api.API;
import com.artwall.project.base.BaseActivity;
import com.artwall.project.bean.PaintDetail;
import com.artwall.project.bean.PaintDetailContent;
import com.artwall.project.util.ImageLoaderUtil;
import com.artwall.project.widget.NScroll.NoScrollListView;
import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by 95 on 2016/4/1.
 */
public class PaintDetailActivity extends BaseActivity {

    private NoScrollListView nsListView;
    private TeachDetailAdapter adapter;
    private ArrayList<PaintDetailContent> list = new ArrayList<PaintDetailContent>();
    /**
     * 头部布局
     */
    private View headview;
    /**
     * 头部布局中的用户头像以及成品图片
     */
    private ImageView headUserImg, headImg;
    private TextView headNickname, headInputtime, headIntroduce, headMaterial;

    private TextView tips;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_paint_detail;
    }

    @Override
    protected void initGui() {
        nsListView = (NoScrollListView) this
                .findViewById(R.id.teachDetail_noscrollListview);
        headview = LayoutInflater.from(activity).inflate(
                R.layout.headview_teachdetail, null);
        headUserImg = (ImageView) headview
                .findViewById(R.id.teachDetail_headview_userImg);
        headImg = (ImageView) headview
                .findViewById(R.id.teachDetail_headview_img);
        headNickname = (TextView) headview.findViewById(R.id.teachDetail_headview_nickname);
        headInputtime = (TextView) headview.findViewById(R.id.teachDetail_headview_inputtime);
        headIntroduce = (TextView) headview.findViewById(R.id.teachDetail_headview_introduce);
        headMaterial = (TextView) headview.findViewById(R.id.teachDetail_headview_meterial_TV);

        tips = (TextView) this.findViewById(R.id.teachDetail_tips);

    }


    @Override
    protected void initData() {
        String id = getIntent().getStringExtra("ID");
        if (null == id) {
            finish();
        }

        adapter = new TeachDetailAdapter(activity, list);
        nsListView.setAdapter(adapter);
        nsListView.addHeaderView(headview);
        getData(id);
    }

    public void click(View view) {
        switch (view.getId()) {
            // 上传模仿
            case R.id.teachDetail_upload_TV:
                break;

            // 打赏
            case R.id.teachDetail_bottom_shang_TV:

                break;
            // 点赞
            case R.id.teachDetail_bottom_zan_TV:

                break;
            // 评论
            case R.id.teachDetail_bottom_ping_TV:
                break;
            // 一键购齐
            case R.id.teachDetail_headview_buy_TV:
                break;
            // 一键导入耗材
            case R.id.teachDetail_headview_import_TV:
                break;
            //头部布局中用户头像
            case R.id.teachDetail_headview_userImg:
                break;
            default:
                break;
        }
    }

    private void getData(String id) {
        RequestParams params = new RequestParams();
        params.put("id", id);
        post(API.Paint_Content, params);

    }

    @Override
    public void onDataOK(String url, String responseString) {
        super.onDataOK(url, responseString);
        try {
            JSONObject obj = new JSONObject(responseString);
            JSONObject data = obj.getJSONObject("data");
            Gson gson = new Gson();
            PaintDetail paintDetail = gson.fromJson(data.toString(), PaintDetail.class);
            initUI(paintDetail);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void initUI(PaintDetail paintDetail) {
        list.addAll(paintDetail.getContent());
        adapter.notifyDataSetChanged();
        initToolBar(paintDetail.getTitle().length() > 12 ? paintDetail.getTitle().substring(0, 12) + "..." : paintDetail.getTitle());

        ImageLoaderUtil.loadImg(headUserImg, paintDetail.getPortrait());
        ImageLoaderUtil.loadImg(headImg, paintDetail.getThumb());
        headNickname.setText(paintDetail.getNickname());
        headInputtime.setText(paintDetail.getInputtime());
        headIntroduce.setText(paintDetail.getIntroduce());
        headMaterial.setText(paintDetail.getMaterial().replaceAll(",", "  "));

        tips.setText("小贴士: " + paintDetail.getTips());

    }

}
