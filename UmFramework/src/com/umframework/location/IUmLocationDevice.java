package com.umframework.location;

import android.content.Context;

public interface IUmLocationDevice
{
	public void setContext(Context context);

	public Context getContext();

	public boolean isStarted();

	public void startLocation();

	public void stopLocation();

	public UmLocation getUmLocation();

	public UmLocation getLastKnownUmLocation();

	public boolean isProviderEnabled();
}
