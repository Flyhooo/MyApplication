package com.artwall.project.activity;

import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.artwall.project.R;
import com.artwall.project.adapter.YaoPingCommentAdapter;
import com.artwall.project.api.IMG;
import com.artwall.project.base.BaseActivity;
import com.artwall.project.bean.YaoPingComment;

import java.util.ArrayList;

/**
 * Created by 95 on 2016/4/6.
 */
public class YaoPingDetailActivity extends BaseActivity {

    private ListView listView;
    private ArrayList<YaoPingComment> list = new ArrayList<>();
    private YaoPingCommentAdapter adapter;
    private View headView;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_yaoping_detail;
    }

    @Override
    protected void initGui() {
        toolbar = (Toolbar) this.findViewById(R.id.common_toolbar);
        toolbar.setTitle("详情");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listView = (ListView) this.findViewById(R.id.yaopingdetail_listview);
        headView = LayoutInflater.from(this).inflate(R.layout.headview_yaopingdetail, null);

    }

    @Override
    protected void initData() {

        for (int i = 0; i < 2; i++) {
            YaoPingComment ypDetail = new YaoPingComment();
            switch (i) {
                case 0:
                    ypDetail.setContent("恩！你这几幅作品都很有新意，创新点比较多！是一个不可多得的佳作，如果作为考试答卷的话不考虑文化课的情况下有可能会进入中央美院！");
                    ypDetail.setMemberName("央美吴老师");
                    ypDetail.setPhoto(IMG.IMG2);
                    ypDetail.setCreateDate("2016-01-04 10:10");
                    break;
                case 1:
                    ypDetail.setContent("太好了！谢谢吴老师的指导，我会加倍努力的！争取能上央美！！！");
                    ypDetail.setMemberName("学生李逵");
                    ypDetail.setPhoto(IMG.IMG1);
                    ypDetail.setReMemberName("央美吴老师");
                    ypDetail.setCreateDate("2016-01-05 20:15");
                    break;
            }
            list.add(ypDetail);
        }
        adapter = new YaoPingCommentAdapter(activity, list);
        listView.setAdapter(adapter);
        listView.addHeaderView(headView);

    }
}
