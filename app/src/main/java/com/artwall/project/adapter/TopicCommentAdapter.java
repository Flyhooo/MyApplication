package com.artwall.project.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.artwall.project.R;
import com.artwall.project.bean.QuestionComment;
import com.artwall.project.util.ImageLoaderUtil;

import java.util.ArrayList;

/**
 * Created by 95 on 2016/3/31.
 */
public class TopicCommentAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<QuestionComment> list;

    public TopicCommentAdapter(Context context, ArrayList<QuestionComment> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int p, View convertView, ViewGroup parent) {
        if (list.size() == 0) {
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.listitem_empey_textview, null);
            TextView emptyTV = (TextView) convertView
                    .findViewById(R.id.listitem_empty_tv);
            emptyTV.setText(" ");
            convertView.setTag("empty");
            return convertView;
        }

        Holder holder;

        if (convertView == null
                || convertView.getTag().toString().equals("empty")) {
            holder = new Holder();
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.listitem_questiondetail_comment, null);

            holder.iv_userhead = (ImageView) convertView
                    .findViewById(R.id.listitem_questionDetail_comment_Img);
            holder.tv_nicename = (TextView) convertView
                    .findViewById(R.id.listitem_questionDetail_comment_name_TV);
            holder.tv_time = (TextView) convertView
                    .findViewById(R.id.listitem_questionDetail_comment_time_TV);
            holder.tv_content = (TextView) convertView
                    .findViewById(R.id.listitem_questionDetail_comment_content_TV);

            convertView.setTag(holder);
        } else {

            holder = (Holder) convertView.getTag();
        }

        final QuestionComment data = list.get(p);

        ImageLoaderUtil.loadImg( holder.iv_userhead, data.getPhoto());

        holder.tv_nicename.setText(data.getMemberName());
        holder.tv_time.setText(data.getCreateDate());

        String newMessageInfo = "";
        if (null == data.getReMemberName()
                || data.getReMemberName().equals("")) {
            newMessageInfo = "<font color='#666666';style='font-weight:100;'<b>"
                    + data.getContent() + "</b></font>";
        } else {
            newMessageInfo = "<font color='#666666';style='font-weight:100;'<b>"
                    + "回复  "
                    + "</b></font>"
                    + "<font color='#00aeff'<b>"
                    + data.getReMemberName()
                    + "</b></font>"
                    + "<font color='#666666';style='font-weight:100;'<b>:"
                    + data.getContent() + "</b></font>";
        }
        holder.tv_content.setText(Html.fromHtml(newMessageInfo));

        holder.iv_userhead.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

            }
        });

        return convertView;
    }

    class Holder {

        /**
         * 头像
         */
        ImageView iv_userhead;
        /**
         * 昵称
         */
        TextView tv_nicename;
        /**
         * 时间
         */
        TextView tv_time;
        /**
         * 内容
         */
        TextView tv_content;
    }
}
