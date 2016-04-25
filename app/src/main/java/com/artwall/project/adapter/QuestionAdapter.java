package com.artwall.project.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.artwall.project.R;
import com.artwall.project.api.IMG;
import com.artwall.project.bean.Question;
import com.artwall.project.util.ImageLoaderUtil;

import java.util.ArrayList;

/**
 * Created by 95 on 2016/3/31.
 */
public class QuestionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private ArrayList<Question> list;

    private OnItemClickListener mOnItemClickListener;

    public QuestionAdapter(Context context, ArrayList<Question> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listitem_question, parent, false);
        ItemViewHolder vh = new ItemViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            Question question = list.get(position);
            if (question != null) {
                ((ItemViewHolder) holder).userName.setText(question.getUserName());
                ImageLoaderUtil.loadImg(((ItemViewHolder) holder).userImage, IMG.PIC1);
                ((ItemViewHolder) holder).title.setText(question.getTitle());
                ((ItemViewHolder) holder).content.setText(question.getContent());
                if (null == question.getImage() || question.getImage().isEmpty()) {
                    ((ItemViewHolder) holder).image.setVisibility(View.GONE);
                } else {
                    ((ItemViewHolder) holder).image.setVisibility(View.VISIBLE);
                    ImageLoaderUtil.loadImg(((ItemViewHolder) holder).image, question.getImage());
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
        TextView tag1, tag2, tag3;

        public ItemViewHolder(View v) {
            super(v);
            userImage = (ImageView) v.findViewById(R.id.listitem_Question_userImg);
            image = (ImageView) v.findViewById(R.id.listitem_Question_img);
            userName = (TextView) v.findViewById(R.id.listitem_Question_userName);
            content = (TextView) v.findViewById(R.id.listitem_Question_content);
            time = (TextView) v.findViewById(R.id.listitem_Question_time);
            address = (TextView) v.findViewById(R.id.listitem_Question_address);
            title = (TextView) v.findViewById(R.id.listitem_Question_title);
            tag1 = (TextView) v.findViewById(R.id.listitem_Question_tag1);
            tag2 = (TextView) v.findViewById(R.id.listitem_Question_tag2);
            tag3 = (TextView) v.findViewById(R.id.listitem_Question_tag3);
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
