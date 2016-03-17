package com.umframework.location;

import android.content.Context;
import android.location.LocationManager;

public abstract class BaseUmLocationDevice implements IUmLocationDevice, android.location.GpsStatus.Listener
{
	public LocationManager mLocationManager;
	public Context mContext;
	public boolean mStarted = false;
	public UmLocation mUmLocation;

	@Override
	public void setContext(Context context)
	{
		this.mContext = context;
		mLocationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
	}

	public Context getContext()
	{
		return this.mContext;
	}

	@Override
	public boolean isStarted()
	{
		return mStarted;
	}

	@Override
	public boolean isProviderEnabled()
	{
		return mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
	}

	@Override
	public UmLocation getUmLocation()
	{
		if (isStarted())
		{
			return mUmLocation;
		}
		return null;
	}

	@Override
	public void onGpsStatusChanged(int event)
	{

	}
}
