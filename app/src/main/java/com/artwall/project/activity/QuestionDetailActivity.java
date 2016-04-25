package com.artwall.project.activity;

import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.artwall.project.R;
import com.artwall.project.adapter.QuestionCommentAdapter;
import com.artwall.project.api.IMG;
import com.artwall.project.base.BaseActivity;
import com.artwall.project.bean.QuestionComment;

import java.util.ArrayList;

/**
 * Created by 95 on 2016/4/6.
 */
public class QuestionDetailActivity extends BaseActivity {

    private View headview;
    private ListView listView;
    private ArrayList<QuestionComment> list = new ArrayList<>();
    private QuestionCommentAdapter adapter;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_question_detail;
    }

    @Override
    protected void initGui() {
        toolbar = (Toolbar) this.findViewById(R.id.common_toolbar);
        toolbar.setTitle("详情");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        headview = LayoutInflater.from(this).inflate(R.layout.headview_question_detail, null);
        listView = (ListView) this.findViewById(R.id.questionDetail_listview);

    }

    @Override
    protected void initData() {

        for (int i = 0; i < 2; i++) {
            QuestionComment data = new QuestionComment();
            switch (i) {
                case 0:
                    data.setContent("比较棘手的问题，在美国终身教授是很难的。");
                    data.setMemberName("巅峰而非");
                    data.setPhoto(IMG.IMG2);
                    data.setCreateDate("2016-01-04 10:10");
                    break;
                case 1:
                    data.setContent("是的，可能他们认为终身制对于自身的发展没有好处。也是从侧面去督促他们更好的进步！");
                    data.setMemberName("科技俊");
                    data.setPhoto(IMG.IMG1);
                    data.setReMemberName("巅峰而非");
                    data.setCreateDate("2016-01-05 20:15");
                    break;
            }
            list.add(data);
        }
        adapter = new QuestionCommentAdapter(this, list);
        listView.addHeaderView(headview);
        listView.setAdapter(adapter);

    }
}
