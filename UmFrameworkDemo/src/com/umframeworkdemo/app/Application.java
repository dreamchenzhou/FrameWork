package com.umframeworkdemo.app;

import com.umframeworkdemo.comm.PathManager;

public class Application extends android.app.Application
{
	@Override
	public void onCreate()
	{
		super.onCreate();
		PathManager.init(this);
	}
}