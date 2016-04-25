package com.artwall.project.activity;

import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.artwall.project.R;
import com.artwall.project.adapter.TeachDetailAdapter;
import com.artwall.project.api.IMG;
import com.artwall.project.base.BaseActivity;
import com.artwall.project.bean.TeachDetail;
import com.artwall.project.util.ImageLoaderUtil;
import com.artwall.project.widget.NScroll.NoScrollListView;

import java.util.ArrayList;

/**
 * Created by 95 on 2016/4/1.
 */
public class PaintDetailActivity extends BaseActivity {

    private NoScrollListView nsListView;
    private TeachDetailAdapter adapter;
    private ArrayList<TeachDetail> list = new ArrayList<TeachDetail>();
    /**
     * 头部布局
     */
    private View headview;
    /**
     * 头部布局中的用户头像以及成品图片
     */
    private ImageView headUserImg, headImg;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_pandw_detail;
    }

    @Override
    protected void initGui() {
        toolbar = (Toolbar) this.findViewById(R.id.common_toolbar);
        nsListView = (NoScrollListView) this
                .findViewById(R.id.teachDetail_noscrollListview);
        headview = LayoutInflater.from(activity).inflate(
                R.layout.headview_teachdetail, null);
        headUserImg = (ImageView) headview
                .findViewById(R.id.teachDetail_headview_userImg);
        headImg = (ImageView) headview
                .findViewById(R.id.teachDetail_headview_img);
        toolbar.setTitle("详情");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }


    @Override
    protected void initData() {
        for (int i = 0; i < 5; i++) {
            TeachDetail teachDetail = new TeachDetail();
            switch (i) {
                case 0:
                    teachDetail.setText("第一步：先画眼睛，注意事项已在图中标注出来了！");
                    teachDetail
                            .setImage("http://7xpo0z.com2.z0.glb.qiniucdn.com/1.jpg");
                    break;
                case 1:
                    teachDetail
                            .setText("第二步：眼睛的大致框架出来后就是眼神的描绘了，眼神的描绘主要体现在色彩的搭配上面！");
                    teachDetail
                            .setImage("http://7xpo0z.com2.z0.glb.qiniucdn.com/2.jpg");
                    break;
                case 2:
                    teachDetail.setText("第三步：然后就是手的画法，手部主要注意关节部位！");
                    teachDetail
                            .setImage("http://7xpo0z.com2.z0.glb.qiniucdn.com/3.jpg");
                    break;
                case 3:
                    teachDetail
                            .setText("第四步：不同形态下的手部绘画要注意不同的点，例如本画中手部就有两种形态，关节部位很重要！");
                    teachDetail
                            .setImage("http://7xpo0z.com2.z0.glb.qiniucdn.com/4.jpg");
                    break;
                case 4:
                    teachDetail.setText("第五步：脚步就相对较为简单一点，注意好整体的形态就差不多！图中有注释！");
                    teachDetail
                            .setImage("http://7xpo0z.com2.z0.glb.qiniucdn.com/5.jpg");
                    break;
            }
            list.add(teachDetail);
        }

        ImageLoaderUtil.loadImg(headUserImg, IMG.IMG1);
        ImageLoaderUtil.loadImg( headImg, "http://7xpo0z.com2.z0.glb.qiniucdn.com/result.jpg");
        adapter = new TeachDetailAdapter(activity, list);
        nsListView.setAdapter(adapter);
        nsListView.addHeaderView(headview);
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

//    private void popMenu() {
//        View view = LayoutInflater.from(activity).inflate(
//                R.layout.popup_teachdetail_menu_share, null);
//        final PopupWindow pop = new PopupWindow(view,
//                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
//        pop.setBackgroundDrawable(new BitmapDrawable());
//        pop.setOutsideTouchable(true);
//
//        pop.showAsDropDown(toolbar);
//    }

}
