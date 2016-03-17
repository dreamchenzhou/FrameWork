package com.umframework.io;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.os.storage.StorageManager;

@TargetApi(Build.VERSION_CODES.GINGERBREAD)
public class SdCardManager
{
	/**
	 * 初始化启动项
	 * 
	 * String[] strs = new String[2]; strs[0] = sdcard;strs[1] = startup;
	 * 
	 * String sdcard = null;
	 * 
	 * String startup = null;
	 * 
	 * @param context
	 * @param nape
	 *            指定启动位置
	 * @return
	 */
	public static String[] init(Context context, String nape)
	{
		String[] strs = new String[2];
		String sdcard = null;
		String startup = null;

		boolean exists = false;
		String[] paths = SdCardManager.getVolumePaths(context);
		if (paths != null && paths.length > 0)
		{
			for (String path : paths)
			{
				String temp = path + File.separator + nape;
				if (FileManager.exists(temp))
				{
					sdcard = path;
					startup = temp;
					exists = true;
					break;
				}
			}
		}

		if (!exists)
		{
			sdcard = sdCardPath();
			startup = sdcard + File.separator + nape;
			FileManager.mkdirs(startup);
		}

		strs[0] = sdcard;
		strs[1] = startup;
		return strs;
	}

	public static String sdCardPath()
	{
		String path = Environment.getExternalStorageDirectory().getPath();
		return path;
	}

	public static File sdCardFile()
	{
		File file = Environment.getExternalStorageDirectory();
		return file;
	}

	/**
	 * 返回存储卡路径，若有多个，第一个为内置，其余为扩展
	 * 
	 * @param context
	 * @return
	 */
	public static String[] getVolumePaths(Context context)
	{
		String[] paths = null;

		StorageManager storageManager = (StorageManager) context.getSystemService(Context.STORAGE_SERVICE);
		try
		{
			Class<?>[] paramClasses =
			{};
			Method getVolumePathsMethod = StorageManager.class.getMethod("getVolumePaths", paramClasses);
			getVolumePathsMethod.setAccessible(true);
			Object[] params =
			{};
			Object invoke = getVolumePathsMethod.invoke(storageManager, params);
			paths = (String[]) invoke;

			// for (int i = 0; i < paths.length; i++)
			// {
			// StatFs stat = getStatFs(paths[i]);
			// Log.i("", paths[i] + "剩余空间：" + calculateSizeInMB(stat));
			// }
		}
		catch (NoSuchMethodException e1)
		{
			e1.printStackTrace();
		}
		catch (IllegalArgumentException e)
		{
			e.printStackTrace();
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		return paths;
	}

	public static StatFs getStatFs(String path)
	{
		try
		{
			return new StatFs(path);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 剩余空间
	 * 
	 * @param path
	 * @return
	 */
	public static float calculateSizeInMB(String path)
	{
		return calculateSizeInMB(new StatFs(path));
	}

	/**
	 * 剩余空间
	 * 
	 * @param stat
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static float calculateSizeInMB(StatFs stat)
	{
		if (stat != null)
			return stat.getAvailableBlocks() * (stat.getBlockSize() / (1024f * 1024f));
		return 0.0f;
	}
}
