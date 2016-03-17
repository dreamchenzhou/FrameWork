package com.zbar.lib;

import android.content.Context;
import android.content.Intent;

/**
 * 扫描
 * 
 * @author martin.zheng
 * 
 */
public final class ScanManager
{
	private static ScanCallback mScanCallback = null;

	/**
	 * 扫描
	 * 
	 * @param packageContext
	 */
	public static void scan(Context packageContext, ScanCallback callback)
	{
		mScanCallback = callback;

		Intent intent = new Intent();
		// intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setClass(packageContext, CaptureActivity.class);
		packageContext.startActivity(intent);
	}

	public static void onScanResult(String result)
	{
		if (mScanCallback != null)
		{
			mScanCallback.onScanResult(result);
			mScanCallback = null;
		}
	}

	public static interface ScanCallback
	{
		void onScanResult(String result);
	}
}
