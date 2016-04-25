package com.artwall.project.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.artwall.project.R;
import com.artwall.project.bean.PhotographyDetail;
import com.artwall.project.util.ImageLoaderUtil;

import java.util.ArrayList;

/**
 * Created by 95 on 2016/3/31.
 */
public class PhotographyDetailAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<PhotographyDetail> list;

    public PhotographyDetailAdapter(Context context, ArrayList<PhotographyDetail> list) {
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
                    R.layout.listitem_photography, null);

            holder.img = (ImageView) convertView
                    .findViewById(R.id.listitem_photography_img);
            holder.text = (TextView) convertView
                    .findViewById(R.id.listitem_photography_text);

            convertView.setTag(holder);
        } else {

            holder = (Holder) convertView.getTag();
        }

        final PhotographyDetail ypDetail = list.get(p);

        ImageLoaderUtil.loadImg( holder.img, ypDetail.getImg());
        holder.text.setText(ypDetail.getText());

        return convertView;
    }

    class Holder {

        /**
         * 图片
         */
        ImageView img;
        /**
         * 说明
         */
        TextView text;
    }
}
