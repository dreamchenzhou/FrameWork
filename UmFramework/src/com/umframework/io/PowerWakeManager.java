package com.umframework.io;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import android.content.Context;
import android.os.PowerManager;

/**
 * 申请电源
 * 
 * @author martin.zheng
 * 
 */
public class PowerWakeManager
{
	private static HashMap<String, PowerWake> items = new HashMap<String, PowerWake>();

	/**
	 * 申请电源
	 * 
	 * @param context
	 */
	public static void acquireWakeLock(Context context)
	{
		String key = context.getClass().getName();
		PowerWake item = new PowerWake(context);
		items.put(key, item);
	}

	/**
	 * 释放电源
	 */
	public static void releaseWakeLock(Context context)
	{
		String key = context.getClass().getName();
		if (items.containsKey(key))
		{
			PowerWake item = items.get(key);
			if (item != null)
			{
				item.releaseWakeLock();
				items.remove(key);
			}
		}
	}

	public static void onDestroy()
	{
		Iterator<Entry<String, PowerWake>> iter = items.entrySet().iterator();
		while (iter.hasNext())
		{
			PowerWake item = (PowerWake) iter.next();
			item.releaseWakeLock();
		}
		items.clear();
	}

	public static class PowerWake
	{
		PowerManager.WakeLock wakeLock = null;

		public PowerWake()
		{

		}

		public PowerWake(Context context)
		{
			acquireWakeLock(context);
		}

		/**
		 * 申请电源管理权限，防止手机修复导致程序停止运行 super.onCreate(savedInstanceState)，后加载
		 * 
		 * @param context
		 */
		public void acquireWakeLock(Context context)
		{
			if (wakeLock == null)
			{
				PowerManager pm = (PowerManager) context
						.getSystemService(Context.POWER_SERVICE);
				wakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
						context.getClass().getCanonicalName());
				wakeLock.acquire();
			}
		}

		/**
		 * 释放电源管理权限 onDestroy()后加载
		 */
		public void releaseWakeLock()
		{
			if (wakeLock != null && wakeLock.isHeld())
			{
				wakeLock.release();
				wakeLock = null;
			}
		}
	}
}
