package com.artwall.project.widget.NScroll;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

public class NoScrollListView extends ListView {
	
	public NoScrollListView(Context context) {
		super(context);
	}
	
	public NoScrollListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public NoScrollListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		
		if (ev.getAction() == MotionEvent.ACTION_MOVE) {
			
			return true;//禁止GridView滑动
		}
		return super.dispatchTouchEvent(ev);
	}
	
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
	{
		int mExpandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
				MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, mExpandSpec);
	}
}