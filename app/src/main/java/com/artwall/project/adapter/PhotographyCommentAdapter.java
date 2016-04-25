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
import com.artwall.project.bean.PhotographyComment;
import com.artwall.project.util.ImageLoaderUtil;

import java.util.ArrayList;

/**
 * Created by 95 on 2016/3/31.
 */
public class PhotographyCommentAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<PhotographyComment> list;

    public PhotographyCommentAdapter(Context context, ArrayList<PhotographyComment> list) {
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

        Holder holder;

        if (convertView == null) {
            holder = new Holder();
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.listitem_photography_comment, null);

            holder.iv_userhead = (ImageView) convertView
                    .findViewById(R.id.listitem_photography_comment_Img);
            holder.tv_nicename = (TextView) convertView
                    .findViewById(R.id.listitem_photography_comment_name_TV);
            holder.tv_time = (TextView) convertView
                    .findViewById(R.id.listitem_photography_comment_time_TV);
            holder.tv_content = (TextView) convertView
                    .findViewById(R.id.listitem_photography_comment_content_TV);

            convertView.setTag(holder);
        } else {

            holder = (Holder) convertView.getTag();
        }

        final PhotographyComment detail = list.get(p);

        ImageLoaderUtil.loadImg(holder.iv_userhead,detail.getPhoto());

        holder.tv_nicename.setText(detail.getMemberName());
        holder.tv_time.setText(detail.getCreateDate());

        String newMessageInfo = "";
        if (null == detail.getReMemberName()
                || detail.getReMemberName().equals("")) {
            newMessageInfo = "<font color='#666666';style='font-weight:100;'<b>"
                    + detail.getContent() + "</b></font>";
        } else {
            newMessageInfo = "<font color='#666666';style='font-weight:100;'<b>"
                    + "回复  "
                    + "</b></font>"
                    + "<font color='#00aeff'<b>"
                    + detail.getReMemberName()
                    + "</b></font>"
                    + "<font color='#666666';style='font-weight:100;'<b>:"
                    + detail.getContent() + "</b></font>";
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

        /** 头像 */
        ImageView iv_userhead;
        /** 昵称 */
        TextView tv_nicename;
        /** 时间 */
        TextView tv_time;
        /** 内容 */
        TextView tv_content;
    }
}
