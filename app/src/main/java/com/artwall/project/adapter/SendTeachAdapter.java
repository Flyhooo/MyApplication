package com.artwall.project.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;

import com.artwall.project.R;
import com.artwall.project.bean.SendTeach;
import com.artwall.project.util.ImageLoaderUtil;

import java.util.ArrayList;

/**
 * Created by 95 on 2016/4/29.
 */
public class SendTeachAdapter extends BaseAdapter {

    private ArrayList<SendTeach> list;
    Context context;
    private OnImageClickListener mOnImageClickListener;

    public SendTeachAdapter(Context context, ArrayList<SendTeach> list) {
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        View row = convertView;
        Holder holder = null;

        if (row == null) {
            row = ((Activity) context).getLayoutInflater().inflate(
                    R.layout.listitem_send_teach, parent, false);

            holder = new Holder();
            holder.imageView = (ImageView) row
                    .findViewById(R.id.listitem_sendTeach_img);
            holder.editText = (EditText) row
                    .findViewById(R.id.listitem_sendTeach_edt);
            row.setTag(holder);
        } else {
            holder = (Holder) row.getTag();
        }
        if (null == list.get(position).getImage() || list.get(position).getImage().equals("")) {
            holder.imageView.setImageResource(R.drawable.ic_menu_camera);
        } else {
            ImageLoaderUtil.loadLocalImage(list.get(position).getImage(), holder.imageView);
        }

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnImageClickListener != null) {
                    mOnImageClickListener.onItemClick(v, position);
                }
            }
        });

        return row;
    }


    class Holder {
        ImageView imageView;
        EditText editText;

    }

    public void setOnImageClickListener(OnImageClickListener mOnImageClickListener) {
        this.mOnImageClickListener = mOnImageClickListener;
    }


    public interface OnImageClickListener {
        public void onItemClick(View view, int position);
    }

}
