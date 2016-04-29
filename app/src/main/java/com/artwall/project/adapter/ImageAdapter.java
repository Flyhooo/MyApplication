package com.artwall.project.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.artwall.project.R;
import com.artwall.project.util.ImageLoaderUtil;

import java.util.ArrayList;

/**
 * Created by 95 on 2016/4/28.
 */
public class ImageAdapter extends BaseAdapter {


    private ArrayList<String> list;
    private LayoutInflater listContainer;

    public ImageAdapter(Context context, ArrayList<String> list) {
        this.list = list;
        this.listContainer = LayoutInflater.from(context);

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
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Holder holder;

        if (convertView == null) {
            holder = new Holder();
            convertView = listContainer.inflate(
                    R.layout.griditem_image, null);
            holder.iv = (ImageView) convertView
                    .findViewById(R.id.griditem_image);

            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        if (list.get(position) != null && !list.get(position).isEmpty()) {
            ImageLoaderUtil.loadLocalImage(list.get(position), holder.iv);
        } else {
            holder.iv.setImageResource(R.drawable.ic_menu_camera);
        }

        return convertView;
    }

    class Holder {
        ImageView iv;
    }

}
