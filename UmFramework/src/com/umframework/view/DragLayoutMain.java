//package com.umframework.view;
//
//import android.annotation.SuppressLint;
//import android.content.Context;
//import android.util.AttributeSet;
//import android.view.MotionEvent;
//import android.widget.RelativeLayout;
//
//import com.umframework.view.DragLayout.Status;
//
//@SuppressLint("ClickableViewAccessibility")
//public class DragLayoutMain extends RelativeLayout
//{
//	private DragLayout dl;
//
//	public DragLayoutMain(Context context)
//	{
//		super(context);
//	}
//
//	public DragLayoutMain(Context context, AttributeSet attrs)
//	{
//		super(context, attrs);
//	}
//
//	public DragLayoutMain(Context context, AttributeSet attrs, int defStyle)
//	{
//		super(context, attrs, defStyle);
//	}
//
//	public void setDragLayout(DragLayout dl)
//	{
//		this.dl = dl;
//	}
//
//	@Override
//	public boolean onInterceptTouchEvent(MotionEvent event)
//	{
//		if (dl.getStatus() != Status.Close)
//		{
//			return true;
//		}
//		return super.onInterceptTouchEvent(event);
//	}
//
//	@Override
//	public boolean onTouchEvent(MotionEvent event)
//	{
//		if (dl.getStatus() != Status.Close)
//		{
//			if (event.getAction() == MotionEvent.ACTION_UP)
//			{
//				dl.close();
//			}
//			return true;
//		}
//		return super.onTouchEvent(event);
//	}
// }
