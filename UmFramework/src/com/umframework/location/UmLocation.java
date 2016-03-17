package com.umframework.location;

import java.io.Serializable;
import java.util.Date;

import android.location.Location;

import com.baidu.location.BDLocation;
import com.tencent.map.geolocation.TencentLocation;
import com.tencent.map.geolocation.TencentPoi;
import com.umframework.calendar.DateManager;

@SuppressWarnings("serial")
public class UmLocation implements Serializable
{
	private long mTime = 0;//
	private double mLatitude = 0.0;
	private double mLongitude = 0.0;

	private double mLatitudeGCJ02 = 0.0;
	private double mLongitudeGCJ02 = 0.0;

	private double mAltitude = 0.0f;
	private float mSpeed = 0.0f;
	private float mBearing = 0.0f;
	private float mAccuracy = 0.0f;

	private String mAddress;
	private double mAbsX = 0.0;
	private double mAbsY = 0.0;
	private int mSatelliteSearchCount = 0;
	private int mSatelliteConnectCount = 0;

	public UmLocation()
	{

	}

	public UmLocation(Location location)
	{
		set(location);
	}

	public UmLocation(TencentLocation location)
	{
		set(location);
	}

	public UmLocation(BDLocation location)
	{
		set(location);
	}

	public void set(Location location)
	{
		if (location != null)
		{
			this.setAccuracy(location.hasAccuracy() ? location.getAccuracy() : 0.0f);
			this.setAltitude(location.hasAltitude() ? location.getAltitude() : 0.0f);
			this.setBearing(location.hasBearing() ? location.getBearing() : 0.0f);
			this.setLatitude(location.getLatitude());
			this.setLongitude(location.getLongitude());
			this.setAbsX(0.0);
			this.setAbsY(0.0);
			this.setAddress(null);
			this.setSatelliteConnectCount(0);
			this.setSatelliteSearchCount(0);
			this.setSpeed(location.hasSpeed() ? location.getSpeed() : 0.0f);
			this.setTime(location.getTime());
		}
	}

	public void set(TencentLocation location)
	{
		if (location != null)
		{
			String address = null;
			if (location.getPoiList() != null && location.getPoiList().size() > 0)
			{
				TencentPoi tencentPoi = location.getPoiList().get(0);
				address = tencentPoi.getAddress();
			}

			this.setAccuracy(location.getAccuracy());
			this.setAltitude(location.getAltitude());
			this.setBearing(location.getBearing());
			this.setLatitude(location.getLatitude());
			this.setLongitude(location.getLongitude());
			this.setLatitudeGCJ02(location.getLatitude());
			this.setLongitudeGCJ02(location.getLongitude());
			this.setAbsX(0.0);
			this.setAbsY(0.0);
			this.setAddress(address);
			this.setSatelliteConnectCount(0);
			this.setSatelliteSearchCount(0);
			this.setSpeed(location.getSpeed());
			this.setTime(location.getTime());
			EvilTransform.transform(this);
		}
	}

	public void set(BDLocation location)
	{
		if (location != null)
		{
			this.setAccuracy(location.getRadius());
			this.setAltitude(location.getAltitude());
			this.setBearing(location.getDerect());
			this.setLatitude(location.getLatitude());
			this.setLongitude(location.getLongitude());
			this.setLatitudeGCJ02(location.getLatitude());
			this.setLongitudeGCJ02(location.getLongitude());
			this.setAbsX(0.0);
			this.setAbsY(0.0);
			this.setAddress(location.getAddrStr());
			this.setSatelliteConnectCount(0);
			this.setSatelliteSearchCount(0);
			this.setSpeed(location.getSpeed());
			this.setTime(toMilliseconds(location.getTime()));
			EvilTransform.transform(this);
		}
	}

	private long toMilliseconds(String string)
	{
		long milliseconds = 0;

		try
		{
			milliseconds = DateManager.toMilliseconds(string);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			milliseconds = new Date().getTime();
		}
		return milliseconds;
	}

	public long getTime()
	{
		return mTime;
	}

	public void setTime(long time)
	{
		mTime = time;
	}

	public double getLatitude()
	{
		return mLatitude;
	}

	public void setLatitude(double latitude)
	{
		mLatitude = latitude;
	}

	public double getLongitude()
	{
		return mLongitude;
	}

	public void setLongitude(double longitude)
	{
		mLongitude = longitude;
	}

	public double getLatitudeGCJ02()
	{
		return mLatitudeGCJ02;
	}

	public void setLatitudeGCJ02(double latitude)
	{
		mLatitudeGCJ02 = latitude;
	}

	public double getLongitudeGCJ02()
	{
		return mLongitudeGCJ02;
	}

	public void setLongitudeGCJ02(double longitude)
	{
		mLongitudeGCJ02 = longitude;
	}

	public double getAltitude()
	{
		return mAltitude;
	}

	public void setAltitude(double altitude)
	{
		mAltitude = altitude;
	}

	public float getSpeed()
	{
		return mSpeed;
	}

	public void setSpeed(float speed)
	{
		mSpeed = speed;
	}

	public float getBearing()
	{
		return mBearing;
	}

	public void setBearing(float bearing)
	{
		mBearing = bearing;
	}

	public float getAccuracy()
	{
		return mAccuracy;
	}

	public void setAccuracy(float accuracy)
	{
		mAccuracy = accuracy;
	}

	public String getAddress()
	{
		return mAddress;
	}

	public void setAddress(String address)
	{
		this.mAddress = address;
	}

	public double getAbsX()
	{
		return mAbsX;
	}

	public void setAbsX(double absX)
	{
		this.mAbsX = absX;
	}

	public double getAbsY()
	{
		return mAbsY;
	}

	public void setAbsY(double absY)
	{
		this.mAbsY = absY;
	}

	public int getSatelliteSearchCount()
	{
		return mSatelliteSearchCount;
	}

	public void setSatelliteSearchCount(int satelliteSearchCount)
	{
		this.mSatelliteSearchCount = satelliteSearchCount;
	}

	public int getSatelliteConnectCount()
	{
		return mSatelliteConnectCount;
	}

	public void setSatelliteConnectCount(int satelliteConnectCount)
	{
		this.mSatelliteConnectCount = satelliteConnectCount;
	}
}
