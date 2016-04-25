package com.artwall.project.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.artwall.project.R;

/**
 * Created by 95 on 2016/4/14.
 */
public class EmojiAdapter extends BaseAdapter {

    private Context context;
    private int[] emoji;
    Holder holder = null;



    public EmojiAdapter(Context context, int[] emoji) {
        this.emoji = emoji;
        this.context = context;
    }

    @Override
    public int getCount() {
        return emoji.length;
    }

    @Override
    public Object getItem(int position) {
        return emoji[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            holder = new Holder();
            convertView = LayoutInflater.from(context).inflate(R.layout.griditem_emoji, null);
            holder.imageView = (ImageView) convertView.findViewById(R.id.griditem_emoji);

            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        holder.imageView.setBackgroundResource(emoji[position]);
        return convertView;
    }

    class Holder {
        ImageView imageView;

    }

}
