package com.umframeworkdemo;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import com.umframework.io.PowerWakeManager;

public class LocationService extends Service
{
	private LocationService wThis = this;

	public static void startService(Context context)
	{
		context.startService(new Intent(context, LocationService.class));
	}

	public static void stopService(Context context)
	{
		context.stopService(new Intent(context, LocationService.class));
	}

	@Override
	public IBinder onBind(Intent intent)
	{
		return null;
	}

	@Override
	public void onCreate()
	{
		super.onCreate();
		PowerWakeManager.acquireWakeLock(wThis);
	}

	@Override
	public void onDestroy()
	{
		PowerWakeManager.releaseWakeLock(wThis);
		super.onDestroy();
	}
}
