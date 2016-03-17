package com.umframework.location;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

/**
 * 
 * 内置
 */
@SuppressLint("HandlerLeak")
public class UmLocationDeviceInner extends BaseUmLocationDevice implements LocationListener
{
	/**
	 * gps
	 */
	public static final String provider_default = LocationManager.GPS_PROVIDER;

	private String provider = "gps";//

	@Override
	public void setContext(Context context)
	{
		super.setContext(context);
		setProvider();
	}

	private void setProvider()
	{
		// 查找到服务信息
		Criteria criteria = new Criteria();
		// 设置定位精确度
		// Criteria.ACCURACY_COARSE比较粗略，Criteria.ACCURACY_FINE则比较精细
		criteria.setAccuracy(Criteria.ACCURACY_FINE);
		criteria.setAltitudeRequired(true);// 设置是否需要海拔信息
		criteria.setBearingRequired(true);// 设置是否需要方位信息
		criteria.setSpeedRequired(true);// 设置是否要求速度
		criteria.setCostAllowed(true);// 设置是否允许运营商收费
		criteria.setPowerRequirement(Criteria.POWER_LOW);// 设置对电源的需求
		// 第一个条件criteria，第二个条件：false，查找不管provider是否关闭，如果为true，只查找打开的provider
		provider = mLocationManager.getBestProvider(criteria, true); // 获取GPS信息
	}

	@Override
	public void startLocation()
	{
		// 绑定监听，有4个参数
		// 参数1，设备：有GPS_PROVIDER和NETWORK_PROVIDER两种
		// 参数2，位置信息更新周期，单位毫秒
		// 参数3，位置变化最小距离：当位置距离变化超过此值时，将更新位置信息
		// 参数4，监听
		// 备注：参数2和3，如果参数3不为0，则以参数3为准；
		// 参数3为0，则通过时间来定时更新；两者为0，则随时刷新
		// 1秒更新一次，或最小位移变化超过1米更新一次；
		// 注意：此处更新准确度非常低，推荐在service里面启动一个Thread，在run中sleep(10000);然后执行handler.sendMessage(),更新位置
		long minTime = 1000;// 更新频率(毫秒)
		float minDistance = 0;// 0 米不考虑位移

		if (!TextUtils.isEmpty(provider))
		{
			mLocationManager.requestLocationUpdates(provider, minTime, minDistance, this);
			mStarted = true;
		}
	}

	@Override
	public boolean isStarted()
	{
		if (!mStarted)
		{
			startHandler.sendEmptyMessage(0);
		}
		return mStarted;
	}

	@Override
	public void stopLocation()
	{
		// if (mStarted)
		{
			mLocationManager.removeUpdates(this);
			mStarted = false;
		}
	}

	@Override
	public UmLocation getLastKnownUmLocation()
	{
		if (isStarted())
		{
			Location location = mLocationManager.getLastKnownLocation(provider);
			if (location != null)
			{
				UmLocation umLocation = new UmLocation(location);
				return umLocation;
			}
		}
		return null;
	}

	@Override
	public void onLocationChanged(Location location)
	{
		if (location != null)
		{
			mUmLocation = new UmLocation(location);
		}
		else
		{
			mUmLocation = null;
		}
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras)
	{

	}

	@Override
	public void onProviderEnabled(String provider)
	{

	}

	@Override
	public void onProviderDisabled(String provider)
	{

	}

	private Handler startHandler = new Handler()
	{
		@Override
		public void handleMessage(Message msg)
		{
			super.handleMessage(msg);

			setProvider();
			startLocation();
		}
	};
}
