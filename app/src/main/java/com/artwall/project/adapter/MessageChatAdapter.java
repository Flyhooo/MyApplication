package com.artwall.project.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.artwall.project.R;
import com.artwall.project.bean.Message;
import com.artwall.project.util.ImageLoaderUtil;

import java.util.ArrayList;

/**
 * Created by 95 on 2016/3/31.
 */
public class MessageChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private ArrayList<Message> list;

    private OnItemClickListener mOnItemClickListener;

    public MessageChatAdapter(Context context, ArrayList<Message> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listitem_message, parent, false);
        ItemViewHolder vh = new ItemViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            Message message = list.get(position);
            if (message != null) {
                ((ItemViewHolder) holder).userName.setText(message.getName());
                ((ItemViewHolder) holder).time.setText(message.getTime());
                ((ItemViewHolder) holder).content.setText(message.getContent());
                ImageLoaderUtil.loadImg(((ItemViewHolder) holder).userImage, message.getImg());

            }
        }

    }


    @Override
    public int getItemCount() {

        return list.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView userImage;
        TextView userName;
        TextView time;
        TextView content;

        public ItemViewHolder(View v) {
            super(v);
            userImage = (ImageView) v.findViewById(R.id.listitem_message_userImg_IV);
            userName = (TextView) v.findViewById(R.id.listitem_message_name_TV);
            content = (TextView) v.findViewById(R.id.listitem_message_content_TV);
            time = (TextView) v.findViewById(R.id.listitem_message_time_TV);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(view, this.getPosition());
            }
        }
    }
}
