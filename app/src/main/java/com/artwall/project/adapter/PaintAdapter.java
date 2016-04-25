package com.artwall.project.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.artwall.project.R;
import com.artwall.project.bean.Paint;
import com.artwall.project.util.ImageLoaderUtil;

import java.util.List;

/**
 * Created by 95 on 2016/3/30.
 */
public class PaintAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ITEM_LEFT = -1;
    private static final int TYPE_ITEM_RIGHT = 1;
    private static final int TYPE_FOOTER = 0;

    private List<Paint> mData;
    private boolean mShowFooter = true;
    private Context mContext;

    private OnItemClickListener mOnItemClickListener;

    public PaintAdapter(Context context, List<Paint> data) {
        this.mContext = context;
        this.mData = data;
    }

    public void setmDate(List<Paint> data) {
        this.mData = data;
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        // 最后一个item设置为footerView
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else if (position % 2 == 0) {
            return TYPE_ITEM_LEFT;
        } else {
            return TYPE_ITEM_RIGHT;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                      int viewType) {
        if (viewType == TYPE_ITEM_LEFT) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.listitem_teach1, parent, false);
            ItemViewHolder vh = new ItemViewHolder(v);
            return vh;
        } else if (viewType == TYPE_ITEM_RIGHT) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.listitem_teach2, parent, false);
            ItemViewHolder2 vh2 = new ItemViewHolder2(v);
            return vh2;
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.footer, null);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            return new FooterViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {

            Paint paint = mData.get(position);
            if (paint == null) {
                return;
            }
            ((ItemViewHolder) holder).pinglunTV.setText(paint.getComment());
            ((ItemViewHolder) holder).collectTV.setText(paint.getExamine());
            ((ItemViewHolder) holder).contentTV.setText(paint.getTitle());
            ((ItemViewHolder) holder).shangTV.setText(paint.getFang());
            ((ItemViewHolder) holder).fangTV.setText(paint.getFang());
            ((ItemViewHolder) holder).tagTV.setText(paint.getTag());
            ((ItemViewHolder) holder).userName.setText(paint.getNickname());

            ImageLoaderUtil.loadImg(((ItemViewHolder) holder).userImg, paint.getUserimage());
            ImageLoaderUtil.loadImg(((ItemViewHolder) holder).img, paint.getImg());
        } else if (holder instanceof ItemViewHolder2) {
            Paint paint = mData.get(position);
            if (paint == null) {
                return;
            }
            ((ItemViewHolder2) holder).pinglunTV.setText(paint.getComment());
            ((ItemViewHolder2) holder).collectTV.setText(paint.getExamine());
            ((ItemViewHolder2) holder).contentTV.setText(paint.getTitle());
            ((ItemViewHolder2) holder).shangTV.setText(paint.getFang());
            ((ItemViewHolder2) holder).fangTV.setText(paint.getFang());
            ((ItemViewHolder2) holder).tagTV.setText(paint.getTag());
            ((ItemViewHolder2) holder).userName.setText(paint.getNickname());

            ImageLoaderUtil.loadImg(((ItemViewHolder2) holder).userImg, paint.getUserimage());
            ImageLoaderUtil.loadImg(((ItemViewHolder2) holder).img, paint.getImg());
        }
    }

    @Override
    public int getItemCount() {
        int begin = mShowFooter ? 1 : 0;
        if (mData == null) {
            return begin;
        }
        return mData.size() + begin;
    }

    public Paint getItem(int position) {
        return mData == null ? null : mData.get(position);
    }

    public void isShowFooter(boolean showFooter) {
        this.mShowFooter = showFooter;
    }

    public boolean isShowFooter() {
        return this.mShowFooter;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public class FooterViewHolder extends RecyclerView.ViewHolder {

        public FooterViewHolder(View view) {
            super(view);
        }

    }

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView img;
        ImageView userImg;
        TextView userName;
        TextView contentTV;
        TextView tagTV;
        TextView shangTV;
        TextView fangTV;
        TextView collectTV;
        TextView pinglunTV;

        public ItemViewHolder(View v) {
            super(v);
            img = (ImageView) v.findViewById(R.id.listitem_teach_Img);
            userImg = (ImageView) v.findViewById(R.id.listitem_teach_userImg_IV);
            userName = (TextView) v.findViewById(R.id.listitem_teach_name_TV);
            contentTV = (TextView) v.findViewById(R.id.listitem_teach_content_TV);
            tagTV = (TextView) v.findViewById(R.id.listitem_teach_tag_TV);
            shangTV = (TextView) v.findViewById(R.id.listitem_teach_shang_TV);
            fangTV = (TextView) v.findViewById(R.id.listitem_teach_fang_TV);
            collectTV = (TextView) v.findViewById(R.id.listitem_teach_collect_TV);
            pinglunTV = (TextView) v.findViewById(R.id.listitem_teach_pinglun_TV);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(view, this.getPosition());
            }
        }
    }

    public class ItemViewHolder2 extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView img;
        ImageView userImg;
        TextView userName;
        TextView contentTV;
        TextView tagTV;
        TextView shangTV;
        TextView fangTV;
        TextView collectTV;
        TextView pinglunTV;

        public ItemViewHolder2(View v) {
            super(v);
            img = (ImageView) v.findViewById(R.id.listitem_teach_Img2);
            userImg = (ImageView) v.findViewById(R.id.listitem_teach_userImg_IV2);
            userName = (TextView) v.findViewById(R.id.listitem_teach_name_TV2);
            contentTV = (TextView) v.findViewById(R.id.listitem_teach_content_TV2);
            tagTV = (TextView) v.findViewById(R.id.listitem_teach_tag_TV2);
            shangTV = (TextView) v.findViewById(R.id.listitem_teach_shang_TV2);
            fangTV = (TextView) v.findViewById(R.id.listitem_teach_fang_TV2);
            collectTV = (TextView) v.findViewById(R.id.listitem_teach_collect_TV2);
            pinglunTV = (TextView) v.findViewById(R.id.listitem_teach_pinglun_TV2);
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
