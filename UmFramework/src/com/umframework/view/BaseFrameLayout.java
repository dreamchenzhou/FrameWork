package com.umframework.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

/**
 * 
 * @author martin.zheng
 * 
 */
public abstract class BaseFrameLayout extends FrameLayout
{
	public static final int FILL_PARENT = ViewManager.FILL_PARENT;
	public static final int WRAP_CONTENT = ViewManager.WRAP_CONTENT;
	public static final int MATCH_PARENT = ViewManager.MATCH_PARENT;

	public LayoutInflater mInflater;
	public View convertView;

	public BaseFrameLayout(Context context)
	{
		super(context);
		onCreate(context);
	}

	public BaseFrameLayout(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		onCreate(context);
	}

	public abstract void onCreateConvertView(View convertView);

	public abstract int getConvertViewId();

	public void onCreate(Context context)
	{
		mInflater = LayoutInflater.from(context);
		convertView = mInflater.inflate(getConvertViewId(), null);
		onCreateConvertView(convertView);
		this.addView(convertView, MATCH_PARENT, WRAP_CONTENT);
	}

	public LayoutInflater getLayoutInflater()
	{
		return mInflater;
	}

	public void setLayoutInflater(LayoutInflater mInflater)
	{
		this.mInflater = mInflater;
	}

	public View getConvertView()
	{
		return convertView;
	}

	public void setConvertView(View convertView)
	{
		this.convertView = convertView;
	}
}
