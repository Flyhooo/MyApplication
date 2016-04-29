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
import com.artwall.project.bean.Creation;
import com.artwall.project.util.ImageLoaderUtil;

import java.util.ArrayList;

/**
 * Created by 95 on 2016/3/31.
 */
public class CreationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int TYPE_FOOTER = 0;
    private final int TYPE_ITEM = 1;

    private Context context;
    private ArrayList<Creation> list;

    private OnItemClickListener mOnItemClickListener;

    private boolean mShowFooter = true;

    public CreationAdapter(Context context, ArrayList<Creation> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.listitem_yaoping, parent, false);
            ItemViewHolder vh = new ItemViewHolder(v);
            return vh;
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
            Creation data = list.get(position);
            if (data != null) {
                ((ItemViewHolder) holder).userName.setText(data.getNickname());
                ((ItemViewHolder) holder).time.setText(data.getInputtime());
                ((ItemViewHolder) holder).content.setText(data.getIntroduce());
                ((ItemViewHolder) holder).zanCount.setText("点赞(" + data.getZambia() + ")");
                ((ItemViewHolder) holder).shareCount.setText("分享(" + data.getShare() + ")");
                ((ItemViewHolder) holder).pinglunCount.setText("评论(" + data.getComment() + ")");
                ImageLoaderUtil.loadImg(((ItemViewHolder) holder).userImage, data.getPortrait());

                if (data.getContent() != null) {
                    String[] imgList = data.getContent().split(",");
                    int imgCount = imgList.length;
                    switch (imgCount) {
                        case 1:
                            ((ItemViewHolder) holder).imgLL1.setVisibility(View.VISIBLE);
                            ((ItemViewHolder) holder).imgLL2.setVisibility(View.GONE);
                            ImageLoaderUtil.loadImg(((ItemViewHolder) holder).img1, imgList[0]);
                            break;
                        case 2:
                            ((ItemViewHolder) holder).imgLL1.setVisibility(View.VISIBLE);
                            ((ItemViewHolder) holder).imgLL2.setVisibility(View.GONE);
                            ImageLoaderUtil.loadImg(((ItemViewHolder) holder).img1, imgList[0]);
                            ImageLoaderUtil.loadImg(((ItemViewHolder) holder).img2, imgList[1]);

                            break;
                        case 3:
                            ((ItemViewHolder) holder).imgLL1.setVisibility(View.VISIBLE);
                            ((ItemViewHolder) holder).imgLL2.setVisibility(View.VISIBLE);
                            ImageLoaderUtil.loadImg(((ItemViewHolder) holder).img1, imgList[0]);
                            ImageLoaderUtil.loadImg(((ItemViewHolder) holder).img2, imgList[1]);
                            ImageLoaderUtil.loadImg(((ItemViewHolder) holder).img3, imgList[2]);
                            break;
                        case 4:
                            ((ItemViewHolder) holder).imgLL1.setVisibility(View.VISIBLE);
                            ((ItemViewHolder) holder).imgLL2.setVisibility(View.VISIBLE);
                            ImageLoaderUtil.loadImg(((ItemViewHolder) holder).img1, imgList[0]);
                            ImageLoaderUtil.loadImg(((ItemViewHolder) holder).img2, imgList[1]);
                            ImageLoaderUtil.loadImg(((ItemViewHolder) holder).img3, imgList[2]);
                            ImageLoaderUtil.loadImg(((ItemViewHolder) holder).img4, imgList[3]);
                            break;

                    }
                } else {
                    ((ItemViewHolder) holder).imgLL1.setVisibility(View.GONE);
                    ((ItemViewHolder) holder).imgLL2.setVisibility(View.GONE);
                }

            }
        }

    }

    @Override
    public int getItemViewType(int position) {
        // 最后一个item设置为footerView
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }

    @Override
    public int getItemCount() {
        int begin = mShowFooter ? 1 : 0;
        if (list == null) {
            return begin;
        }
        return list.size() + begin;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    public class FooterViewHolder extends RecyclerView.ViewHolder {
        public FooterViewHolder(View view) {
            super(view);
        }
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        LinearLayout imgLL1, imgLL2;
        ImageView userImage;
        ImageView img1, img2, img3, img4;
        TextView userName;
        TextView time;
        TextView content;
        TextView zanCount, pinglunCount, shareCount;

        public ItemViewHolder(View v) {
            super(v);
            imgLL1 = (LinearLayout) v.findViewById(R.id.listitem_yaoping_imgLL1);
            imgLL2 = (LinearLayout) v.findViewById(R.id.listitem_yaoping_imgLL2);
            userImage = (ImageView) v.findViewById(R.id.listitem_yaoping_userImg);
            img1 = (ImageView) v.findViewById(R.id.listitem_yaoping_img11);
            img2 = (ImageView) v.findViewById(R.id.listitem_yaoping_img12);
            img3 = (ImageView) v.findViewById(R.id.listitem_yaoping_img21);
            img4 = (ImageView) v.findViewById(R.id.listitem_yaoping_img22);
            userName = (TextView) v.findViewById(R.id.listitem_yaoping_name_TV);
            content = (TextView) v.findViewById(R.id.listitem_yaoping_content_TV);
            time = (TextView) v.findViewById(R.id.listitem_yaoping_time_TV);
            zanCount = (TextView) v.findViewById(R.id.listitem_yaoping_zanCount);
            shareCount = (TextView) v.findViewById(R.id.listitem_yaoping_shareCount);
            pinglunCount = (TextView) v.findViewById(R.id.listitem_yaoping_pinglun);

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
