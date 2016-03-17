package com.umframework.io;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.umframework.R;

/**
 * 
 * @author martin.zheng
 * 
 */
public class AnimationManager
{
	/**
	 * 抖动
	 * 
	 * @param context
	 * @param view
	 */
	public static void onJitter(Context context, View view)
	{
		Animation anim = AnimationUtils.loadAnimation(context, R.anim.myanim);
		view.startAnimation(anim);
	}
}
