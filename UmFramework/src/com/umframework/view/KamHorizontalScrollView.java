package com.umframework.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.TextView;

/**
 * 
 * 无限滚动的HorizontalScrollView 类似桌面程序”左右切换页的效果
 * 
 * @author martin.zheng
 * 
 */
/* http://www.cnblogs.com/ACkam/p/3954742.html */
/* 如果不需要支持Android2.3，可以将代码中所有KamLinearLayout替换为ViewGroup */
public class KamHorizontalScrollView extends HorizontalScrollView
{
	private static String tag = "KamHorizontalScrollView";
	private Context context;

	/* 记录当前的页数标识（做日视图的时候可以和该值今日的日期作差） */
	private int PageNo = 0;

	/* 保存ScrollView中的ViewGroup，如果不需要支持Android2.3，可以将KamLinearLayout替换为ViewGroup */
	private KamLinearLayout childGroup = null;

	/* 这是判断左右滑动用的（个人喜好，其实不需要这么麻烦） */
	private int poscache[] = new int[4];
	private int startpos;

	public KamHorizontalScrollView(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		this.context = context;
	}

	public KamHorizontalScrollView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		// TODO Auto-generated constructor stub
		this.context = context;
	}

	public KamHorizontalScrollView(Context context)
	{
		super(context);
		// TODO Auto-generated constructor stub
		this.context = context;
	}

	/* 重写触摸事件，判断左右滑动 */
	@Override
	public boolean onTouchEvent(MotionEvent ev)
	{
		switch (ev.getAction())
		{
			case MotionEvent.ACTION_DOWN:
				startpos = (int) ev.getX();
				/* 用于判断触摸滑动的速度 */
				initSpeedChange((int) ev.getX());
				break;
			case MotionEvent.ACTION_MOVE:
			{
				/* 更新触摸速度信息 */
				movingSpeedChange((int) ev.getX());
			}
				break;
			case MotionEvent.ACTION_UP:
			case MotionEvent.ACTION_CANCEL:
			{
				/* 先根据速度来判断向左或向右 */
				int speed = releaseSpeedChange((int) ev.getX());
				if (speed > 0)
				{
					nextPage();
					return true;
				}
				if (speed < 0)
				{
					prevPage();
					return true;
				}

				/* 这里是根据触摸起始和结束位置来判断向左或向右 */
				if (Math.abs((ev.getX() - startpos)) > getWidth() / 4)
				{
					if (ev.getX() - startpos > 0)
					{
						/* 向左 */
						prevPage();
					}
					else
					{
						/* 向右 */
						nextPage();
					}
				}
				else
				{
					/* 不变 */
					scrollToPage(1);
				}
				return true;
			}
		}
		return super.onTouchEvent(ev);
	}

	/* 完成实例化 */
	@Override
	protected void onFinishInflate()
	{
		super.onFinishInflate();
		Log.i(tag, "onFinishInflate Called!");
		init();
	}

	/* 初始化，加入三个子View */
	private void init()
	{
		// this.childGroup = (KamLinearLayout) findViewById(R.id.container);
		this.childGroup = (KamLinearLayout) getChildAt(0);
		/* 添加LayoutChange监听器 */
		childGroup.addKamLayoutChangeListener(listener);
		/* 调用其自身的LayoutChange监听器（不支持Android2.3） */
		/* childGroup.addOnLayoutChangeListener(listener); */

		addRight(createExampleView(-1));
		addRight(createExampleView(0));
		addRight(createExampleView(1));
	}

	/* 添加监听器 */
	kamLayoutChangeListener listener = new kamLayoutChangeListener()
	{

		@Override
		public void onLayoutChange()
		{
			// TODO Auto-generated method stub
			Log.i(tag, "onLayoutChanged Called!");
			scrollToPage(1);
		}
	};

	/*
	 * //注意，如果不需要支持Android2.3，可以将上面的listener替换成下方listener OnLayoutChangeListener
	 * listener = new OnLayoutChangeListener() {
	 * 
	 * @Override public void onLayoutChange(View arg0, int arg1, int arg2, int
	 * arg3, int arg4, int arg5, int arg6, int arg7, int arg8) { // TODO
	 * Auto-generated method stub Log.i(tag, "onLayoutChanged Called!");
	 * scrollToPage(1); } };
	 */

	/* 左翻页 */
	public void prevPage()
	{
		PageNo--;
		addLeft(createExampleView(PageNo - 1));
		removeRight();
	}

	/* 右翻页 */
	public void nextPage()
	{
		PageNo++;
		addRight(createExampleView(PageNo + 1));
		removeLeft();
	}

	/* 获取某个孩子的X坐标 */
	private int getChildLeft(int index)
	{
		if (index >= 0 && childGroup != null)
		{
			if (index < childGroup.getChildCount())
				return childGroup.getChildAt(index).getLeft();
		}
		return 0;
	}

	/**
	 * 向右边添加View
	 * 
	 * @param view
	 *            需要添加的View
	 * @return true添加成功|false添加失败
	 */
	public boolean addRight(View view)
	{
		if (view == null || childGroup == null)
			return false;
		childGroup.addView(view);
		return true;
	}

	/**
	 * 删除右边的View
	 * 
	 * @return true成功|false失败
	 */
	public boolean removeRight()
	{
		if (childGroup == null || childGroup.getChildCount() <= 0)
			return false;
		childGroup.removeViewAt(childGroup.getChildCount() - 1);
		return true;
	}

	/**
	 * 向左边添加View
	 * 
	 * @param view
	 *            需要添加的View
	 * @return true添加成功|false添加失败
	 */
	public boolean addLeft(View view)
	{
		if (view == null || childGroup == null)
			return false;
		childGroup.addView(view, 0);

		/* 因为在左边增加了View，因此所有View的x坐标都会增加，因此需要让ScrollView也跟着移动，才能从屏幕看来保持平滑。 */
		int tmpwidth = view.getLayoutParams().width;
		if (tmpwidth == 0)
			tmpwidth = getWinWidth();
		Log.i(tag, "the new view's width = " + view.getLayoutParams().width);
		this.scrollTo(this.getScrollX() + tmpwidth, 0);

		return true;
	}

	/**
	 * 删除左边的View
	 * 
	 * @return true成功|false失败
	 */
	public boolean removeLeft()
	{
		if (childGroup == null || childGroup.getChildCount() <= 0)
			return false;

		/* 因为在左边删除了View，因此所有View的x坐标都会减少，因此需要让ScrollView也跟着移动。 */
		int tmpwidth = childGroup.getChildAt(0).getWidth();
		childGroup.removeViewAt(0);
		this.scrollTo((int) (this.getScrollX() - tmpwidth), 0);

		return true;
	}

	/**
	 * 跳转到指定的页面
	 * 
	 * @param index
	 *            跳转的页码
	 * @return
	 */
	public boolean scrollToPage(int index)
	{
		if (childGroup == null)
			return false;
		if (index < 0 || index >= childGroup.getChildCount())
			return false;
		smoothScrollTo(getChildLeft(index), 0);
		return true;
	}

	private int getWinWidth()
	{
		DisplayMetrics dm = new DisplayMetrics();
		// 获取屏幕信息
		dm = context.getResources().getDisplayMetrics();
		return dm.widthPixels;
	}

	private int getWinHeight()
	{
		DisplayMetrics dm = new DisplayMetrics();
		// 获取屏幕信息
		dm = context.getResources().getDisplayMetrics();
		return dm.heightPixels;
	}

	/* 生成一个测试用View。真正使用的时候就不需要这个了。 */
	private View createExampleView(int index)
	{
		LayoutParams params = new LayoutParams(getWinWidth(), getWinHeight());
		/* 设置不同的背景色使效果更加明显 */
		int colorarr[] =
		{ Color.rgb(240, 180, 180), Color.rgb(240, 240, 180), Color.rgb(180, 240, 240), Color.rgb(180, 240, 180) };
		TextView txtview = new TextView(context);
		txtview.setBackgroundColor(colorarr[(index % 4 + 4) % 4]);
		txtview.setText(index + "");
		txtview.setTextSize(40);
		txtview.setGravity(Gravity.CENTER);
		txtview.setLayoutParams(params);

		return txtview;
	}

	/* 下面的方法仅仅是个人喜好加上的，用于判断用户手指左右滑动的速度。 */
	private void initSpeedChange(int x)
	{
		if (poscache.length <= 1)
			return;
		poscache[0] = 1;
		for (int i = 1; i < poscache.length; i++)
		{

		}
	}

	private void movingSpeedChange(int x)
	{
		poscache[0] %= poscache.length - 1;
		poscache[0]++;
		// Log.i(tag, "touch speed:"+(x-poscache[poscache[0]]));
		poscache[poscache[0]] = x;
	}

	private int releaseSpeedChange(int x)
	{
		return releaseSpeedChange(x, 30);
	}

	private int releaseSpeedChange(int x, int limit)
	{
		poscache[0] %= poscache.length - 1;
		poscache[0]++;
		/* 检测到向左的速度很大 */
		if (poscache[poscache[0]] - x > limit)
			return 1;
		/* 检测到向右的速度很大 */
		if (x - poscache[poscache[0]] > limit)
			return -1;

		return 0;
	}
}

/**
 * <?xml version="1.0" encoding="utf-8"?>
 * <com.kam.horizontalscrollviewtest.view.KamHorizontalScrollView
 * xmlns:android="http://schemas.android.com/apk/res/android"
 * android:layout_width="match_parent" android:layout_height="match_parent"
 * android:id="@+id/kamscrollview" android:fadingEdge="none"
 * android:scrollbars="none" >
 * 
 * <!-- 如果你不需要支持Android2.3，可以把后面的KamLinearLayout替换成普通的LinearLayout <LinearLayout
 * android:id="@+id/container1" android:layout_width="match_parent"
 * android:layout_height="match_parent" android:orientation="horizontal" >
 * 
 * </LinearLayout > --> <com.kam.horizontalscrollviewtest.view.KamLinearLayout
 * android:id="@+id/container" android:layout_width="match_parent"
 * android:layout_height="match_parent" android:orientation="horizontal">
 * 
 * </com.kam.horizontalscrollviewtest.view.KamLinearLayout>
 * </com.kam.horizontalscrollviewtest.view.KamHorizontalScrollView>
 */
