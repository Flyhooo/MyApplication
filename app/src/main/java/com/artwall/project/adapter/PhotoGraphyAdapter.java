package com.artwall.project.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.artwall.project.R;
import com.artwall.project.bean.Meet;
import com.artwall.project.util.ImageLoaderUtil;

import java.util.ArrayList;

/**
 * Created by 95 on 2016/4/1.
 */
public class PhotoGraphyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<Meet> list;
    private Context context;

    private OnItemClickListener mOnItemClickListener;

    public PhotoGraphyAdapter(Context context, ArrayList<Meet> list) {
        this.list = list;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.listitem_bigshot, viewGroup, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            Meet meet = list.get(position);
            if (meet != null) {
                ImageLoaderUtil.loadImg(((ItemViewHolder) holder).imageview, meet.getPortrait());
                ((ItemViewHolder) holder).titleTV.setText(meet.getTitle());
                ((ItemViewHolder) holder).priceTV.setText(meet.getPrice());
                ((ItemViewHolder) holder).nicknameTV.setText(meet.getNickname());
                ((ItemViewHolder) holder).scoreTV.setText(meet.getScore());
                ((ItemViewHolder) holder).introduceTV.setText(meet.getIntroduce());
                ((ItemViewHolder) holder).seenTV.setText(meet.getSeen());
                ((ItemViewHolder) holder).seeTV.setText(meet.getSee());
            }
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageview;
        TextView titleTV;
        TextView priceTV;
        TextView nicknameTV;
        TextView scoreTV;
        TextView introduceTV;
        TextView seenTV;
        TextView seeTV;

        public ItemViewHolder(View v) {
            super(v);
            imageview = (ImageView) v.findViewById(R.id.listitem_meet_img);
            titleTV = (TextView) v.findViewById(R.id.listitem_meet_title_TV);
            priceTV = (TextView) v.findViewById(R.id.listitem_meet_price_TV);
            nicknameTV = (TextView) v.findViewById(R.id.listitem_meet_nickname_TV);
            scoreTV = (TextView) v.findViewById(R.id.listitem_meet_score_TV);
            introduceTV = (TextView) v.findViewById(R.id.listitem_meet_introduce_TV);
            seenTV = (TextView) v.findViewById(R.id.listitem_meet_seen_TV);
            seeTV = (TextView) v.findViewById(R.id.listitem_meet_see_TV);

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
