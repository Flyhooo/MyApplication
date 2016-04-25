package com.artwall.project.activity;

import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.artwall.project.R;
import com.artwall.project.adapter.PhotographyCommentAdapter;
import com.artwall.project.adapter.PhotographyDetailAdapter;
import com.artwall.project.api.IMG;
import com.artwall.project.base.BaseActivity;
import com.artwall.project.bean.PhotographyComment;
import com.artwall.project.bean.PhotographyDetail;

import java.util.ArrayList;

/**
 * Created by 95 on 2016/4/6.
 */
public class PhotographyDetailActivity extends BaseActivity {

    private ListView listView;
    private ListView listViewComment;
    private ArrayList<PhotographyDetail> list = new ArrayList<>();
    private ArrayList<PhotographyComment> listComment = new ArrayList<>();
    private PhotographyDetailAdapter adapter;
    private PhotographyCommentAdapter adapterComment;

    private View headView;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_photography_detail;
    }

    @Override
    protected void initGui() {
        toolbar = (Toolbar) this.findViewById(R.id.common_toolbar);
        toolbar.setTitle("西藏采风合集");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        headView = LayoutInflater.from(this).inflate(R.layout.headview_photography, null);
        listView = (ListView) this.findViewById(R.id.photographyDetail_listview);
        listViewComment = (ListView) this.findViewById(R.id.photographyDetail_comment_listview);
    }

    @Override
    protected void initData() {

        for (int i = 0; i < 6; i++) {
            PhotographyDetail detail = new PhotographyDetail();
            switch (i) {
                case 0:
                    detail.setImg(IMG.PIC1);
                    break;
                case 1:
                    detail.setImg(IMG.PIC2);
                    break;
                case 2:
                    detail.setImg(IMG.PIC3);
                    break;
                case 3:
                    detail.setImg(IMG.PIC4);
                    break;
                default:
                    detail.setImg(IMG.PIC5);
                    break;
            }

            detail.setText("这是说明...");
            list.add(detail);
        }

        for (int i = 0; i < 2; i++) {
            PhotographyComment data = new PhotographyComment();
            switch (i) {
                case 0:
                    data.setContent("恩！你这几幅作品都很有新意，创新点比较多！是一个不可多得的佳作，如果作为考试答卷的话不考虑文化课的情况下有可能会进入中央美院！");
                    data.setMemberName("央美吴老师");
                    data.setPhoto(IMG.IMG2);
                    data.setCreateDate("2016-01-04 10:10");
                    break;
                case 1:
                    data.setContent("太好了！谢谢吴老师的指导，我会加倍努力的！争取能上央美！！！");
                    data.setMemberName("学生李逵");
                    data.setPhoto(IMG.IMG1);
                    data.setReMemberName("央美吴老师");
                    data.setCreateDate("2016-01-05 20:15");
                    break;
            }
            listComment.add(data);
        }
        adapter = new PhotographyDetailAdapter(this, list);
        listView.setAdapter(adapter);
        listView.addHeaderView(headView);
        adapterComment = new PhotographyCommentAdapter(this, listComment);
        listViewComment.setAdapter(adapterComment);

    }
}
