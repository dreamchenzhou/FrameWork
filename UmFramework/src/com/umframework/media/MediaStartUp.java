package com.umframework.media;

import java.util.List;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.text.TextUtils;
import android.widget.Toast;

/**
 * 相机启动项
 * 
 * @author martin.zheng
 * 
 */
public class MediaStartUp
{
	static String packageName = "";
	static String className = "";

	public static void start(Activity mActivity)
	{
		if(mActivity == null)
		{
			return;
		}

		try
		{
			if(TextUtils.isEmpty(packageName) || TextUtils.isEmpty(className))
			{
				getPackageInfo(mActivity);
			}

			if(TextUtils.isEmpty(packageName) || TextUtils.isEmpty(className))
			{
				makeText(mActivity, "找不到启动项");
			}
			else
			{
				Intent intent = new Intent(Intent.ACTION_MAIN);
				intent.addCategory(Intent.CATEGORY_LAUNCHER);
				ComponentName cn = new ComponentName(packageName, className);
				intent.setComponent(cn);
				mActivity.startActivityForResult(intent, 1);
			}
		}
		catch (Exception e)
		{
			makeText(mActivity, e.getMessage());
		}
	}

	private static void makeText(Activity mActivity, String string)
	{
		Toast.makeText(mActivity, string, Toast.LENGTH_LONG).show();
		mActivity.finish();
	}

	public static void getPackageInfo(Activity mActivity)
	{
		PackageInfo pi = null;

		PackageManager pm = mActivity.getPackageManager();
		List<PackageInfo> packageInfos = pm.getInstalledPackages(0);
		pi = getPackageInfo(pm, packageInfos);

		if(pi == null)
		{
			return;
		}

		// 获得当前应用程序的包管理器
		Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
		resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);
		resolveIntent.setPackage(pi.packageName);
		List<ResolveInfo> apps = pm.queryIntentActivities(resolveIntent, 0);

		ResolveInfo ri = null;
		if(apps.size() >= 2)
		{
			ri = apps.get(1);
		}
		else
		{
			ri = apps.get(0);
		}

		if(ri != null)
		{
			packageName = ri.activityInfo.packageName;
			className = ri.activityInfo.name;
		}
	}

	private static PackageInfo getPackageInfo(PackageManager pm, List<PackageInfo> packageInfos)
	{
		PackageInfo packageInfo = null;

		for (PackageInfo pack : packageInfos)
		{
			String name = pack.applicationInfo.loadLabel(pm).toString();

			if(name.equalsIgnoreCase("照相机测试"))
			{
				continue;
			}

			if(name.indexOf("照相机") >= 0 || name.indexOf("摄相机") >= 0 || name.indexOf("相机") >= 0)
			{
				packageInfo = pack;
				break;
			}
		}

		if(packageInfo == null)
		{
			for (PackageInfo pack : packageInfos)
			{
				String name = pack.applicationInfo.loadLabel(pm).toString();
				if(name.indexOf("图库") >= 0 || name.indexOf("相册") >= 0)
				{
					packageInfo = pack;
					break;
				}
			}
		}
		return packageInfo;
	}
}
