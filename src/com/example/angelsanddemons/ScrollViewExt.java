package com.example.angelsanddemons;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ScrollView;

public class ScrollViewExt extends ScrollView {
	
	private static final String LOG_TAG = "MyActivity";
	
	OnBottomReachedListener onBottomReachedListener;
 
	public ScrollViewExt(Context context, AttributeSet attrs,
			int defStyle) 
	{
		super(context, attrs, defStyle);
	}
 
	public ScrollViewExt(Context context, AttributeSet attrs) 
	{
		super(context, attrs);
	}
 
	public ScrollViewExt(Context context) 
	{
		super(context);
	}
 
	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) 
	{
		
		View view = (View) getChildAt(getChildCount()-1);
		
		int diff = (view.getBottom()-(getHeight()+getScrollY()));
		
		if( diff == 0 )
		{
			// validate if we have a listener
			if( onBottomReachedListener != null )
			{
				// trigger the listener
				onBottomReachedListener.onBottomReached();
			}
		}
 
		super.onScrollChanged(l, t, oldl, oldt);
		
	}
 
	public OnBottomReachedListener getOnBottomReachedListener() {
		return onBottomReachedListener;
	}
 
	public void setOnBottomReachedListener(OnBottomReachedListener onBottomReachedListener) {
		this.onBottomReachedListener = onBottomReachedListener;
	}
	

	public interface OnBottomReachedListener
	{
		public void onBottomReached();
	}
	
}