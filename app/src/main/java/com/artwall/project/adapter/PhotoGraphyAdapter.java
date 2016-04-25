package com.artwall.project.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.artwall.project.R;
import com.artwall.project.bean.Children;
import com.artwall.project.util.ImageLoaderUtil;

import java.util.ArrayList;

/**
 * Created by 95 on 2016/4/1.
 */
public class PhotoGraphyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<Children> list;
    private Context context;

    private OnItemClickListener mOnItemClickListener;

    public PhotoGraphyAdapter(Context context, ArrayList<Children> list) {
        this.list = list;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.listitem_children, viewGroup, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            Children children = list.get(position);
            ImageLoaderUtil.loadImg( ((ItemViewHolder) holder).imageView, children.getImg());
            ((ItemViewHolder) holder).title.setText(children.getTitle());

        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imageView;
        TextView title;
        CardView cardView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.listitem_children_img);
            title = (TextView) itemView.findViewById(R.id.listitem_children_title);
            cardView = (CardView) itemView.findViewById(R.id.listitem_children_cardView);

            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(v, this.getPosition());
            }
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

}