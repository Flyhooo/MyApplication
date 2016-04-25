package com.artwall.project.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.artwall.project.R;
import com.artwall.project.bean.TeachDetail;
import com.artwall.project.util.ImageLoaderUtil;

import java.util.ArrayList;

public class TeachDetailAdapter extends BaseAdapter {
	private ArrayList<TeachDetail> list = null;
	private Context context;

	public TeachDetailAdapter(Context context, ArrayList<TeachDetail> list) {
		this.context = context;
		this.list = list;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		return list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {

		View row = arg1;
		Holder holder = null;

		if (row == null) {
			row = ((Activity) context).getLayoutInflater().inflate(
					R.layout.listitem_teachdetail, arg2, false);

			holder = new Holder();
			holder.image = (ImageView) row
					.findViewById(R.id.listitem_teachDetail_img);
			holder.text = (TextView) row
					.findViewById(R.id.listitem_teachDetail_text);
			row.setTag(holder);
		} else {
			holder = (Holder) row.getTag();
		}

		holder.text.setText(list.get(arg0).getText().toString());
		ImageLoaderUtil.loadImg(holder.image,list.get(arg0).getImage());

		return row;
	}

	class Holder {
		ImageView image;
		TextView text;
	}

}
