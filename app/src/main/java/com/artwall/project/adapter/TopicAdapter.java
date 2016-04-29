package com.artwall.project.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.artwall.project.R;
import com.artwall.project.bean.Topic;
import com.artwall.project.util.ImageLoaderUtil;

import java.util.ArrayList;

/**
 * Created by 95 on 2016/3/31.
 */
public class TopicAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private ArrayList<Topic> list;

    private OnItemClickListener mOnItemClickListener;

    public TopicAdapter(Context context, ArrayList<Topic> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listitem_topic, parent, false);
        ItemViewHolder vh = new ItemViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            Topic topic = list.get(position);
            if (topic != null) {
                ((ItemViewHolder) holder).userName.setText(topic.getNickname());
                ImageLoaderUtil.loadImg(((ItemViewHolder) holder).userImage, topic.getPortrait());
                ((ItemViewHolder) holder).title.setText(topic.getTitle());
                ((ItemViewHolder) holder).content.setText(topic.getContent());
                if (null == topic.getImages() || topic.getImages().isEmpty()) {
                    ((ItemViewHolder) holder).image.setVisibility(View.GONE);
                } else {
                    String[] arr = topic.getImages().split(",");
                    ((ItemViewHolder) holder).image.setVisibility(View.VISIBLE);
                    ImageLoaderUtil.loadImg(((ItemViewHolder) holder).image, arr[0]);
                }
                if (null == topic.getTag() || topic.getTag().isEmpty()) {
                    ((ItemViewHolder) holder).tagLL.setVisibility(View.GONE);
                } else {
                    ((ItemViewHolder) holder).tagLL.setVisibility(View.VISIBLE);
                    String[] arr = topic.getTag().split(",");
                    switch (arr.length) {
                        case 1:
                            ((ItemViewHolder) holder).tag1.setVisibility(View.VISIBLE);
                            ((ItemViewHolder) holder).tag2.setVisibility(View.GONE);
                            ((ItemViewHolder) holder).tag3.setVisibility(View.GONE);
                            ((ItemViewHolder) holder).tag4.setVisibility(View.GONE);
                            ((ItemViewHolder) holder).tag1.setText(arr[0]);
                            break;
                        case 2:
                            ((ItemViewHolder) holder).tag1.setVisibility(View.VISIBLE);
                            ((ItemViewHolder) holder).tag2.setVisibility(View.VISIBLE);
                            ((ItemViewHolder) holder).tag3.setVisibility(View.GONE);
                            ((ItemViewHolder) holder).tag4.setVisibility(View.GONE);
                            ((ItemViewHolder) holder).tag1.setText(arr[0]);
                            ((ItemViewHolder) holder).tag2.setText(arr[1]);
                            break;
                        case 3:
                            ((ItemViewHolder) holder).tag1.setVisibility(View.VISIBLE);
                            ((ItemViewHolder) holder).tag2.setVisibility(View.VISIBLE);
                            ((ItemViewHolder) holder).tag3.setVisibility(View.VISIBLE);
                            ((ItemViewHolder) holder).tag4.setVisibility(View.GONE);
                            ((ItemViewHolder) holder).tag1.setText(arr[0]);
                            ((ItemViewHolder) holder).tag2.setText(arr[1]);
                            ((ItemViewHolder) holder).tag3.setText(arr[2]);
                            break;
                        default:
                            ((ItemViewHolder) holder).tag1.setVisibility(View.VISIBLE);
                            ((ItemViewHolder) holder).tag2.setVisibility(View.VISIBLE);
                            ((ItemViewHolder) holder).tag3.setVisibility(View.VISIBLE);
                            ((ItemViewHolder) holder).tag4.setVisibility(View.VISIBLE);
                            ((ItemViewHolder) holder).tag1.setText(arr[0]);
                            ((ItemViewHolder) holder).tag2.setText(arr[1]);
                            ((ItemViewHolder) holder).tag3.setText(arr[2]);
                            ((ItemViewHolder) holder).tag4.setText(arr[3]);
                            break;
                    }
                }
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
        ImageView image;
        TextView userName;
        TextView time;
        TextView address;
        TextView title;
        TextView content;
        TextView tag1, tag2, tag3, tag4;
        LinearLayout tagLL;

        public ItemViewHolder(View v) {
            super(v);
            userImage = (ImageView) v.findViewById(R.id.listitem_Question_userImg);
            image = (ImageView) v.findViewById(R.id.listitem_Question_img);
            userName = (TextView) v.findViewById(R.id.listitem_Question_userName);
            content = (TextView) v.findViewById(R.id.listitem_Question_content);
            time = (TextView) v.findViewById(R.id.listitem_Question_time);
            address = (TextView) v.findViewById(R.id.listitem_Question_address);
            title = (TextView) v.findViewById(R.id.listitem_Question_title);
            tagLL = (LinearLayout) v.findViewById(R.id.listitem_Question_tag_LL);
            tag1 = (TextView) v.findViewById(R.id.listitem_Question_tag1);
            tag2 = (TextView) v.findViewById(R.id.listitem_Question_tag2);
            tag3 = (TextView) v.findViewById(R.id.listitem_Question_tag3);
            tag4 = (TextView) v.findViewById(R.id.listitem_Question_tag4);
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
