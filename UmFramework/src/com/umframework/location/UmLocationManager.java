package com.umframework.location;

import android.content.Context;

import com.umframework.location.coords.AFTransRegions;
import com.umframework.location.coords.BJTransRegions;
import com.umframework.location.coords.FSTransRegions;
import com.umframework.location.coords.FZTransRegions;
import com.umframework.location.coords.HEBTransRegions;
import com.umframework.location.coords.HaiKouTransRegions;
import com.umframework.location.coords.HuangGangCoordTrans;
import com.umframework.location.coords.ICoordTrans;
import com.umframework.location.coords.JMTransRegions;
import com.umframework.location.coords.PointDElement;
import com.umframework.location.coords.SZCoordTrans;
import com.umframework.location.coords.XCTransRegions;
import com.umframework.location.coords.ZHTransRegions;
import com.umframework.location.coords.ZhengZhouTransRegions;

public class UmLocationManager
{
	// private static Object lockValue = new Object();
	// private static UmLocationManager instance = null;
	/**
	 * 0内置，1百度，2腾讯
	 */
	private static int mUmLocationDeviceType = 0;
	private static Context mContext;
	private static ICoordTrans coordTrans;
	private static IUmLocationDevice mUmLocationDevice;
	private static UmLocationRegion mUmLocationRegion = UmLocationRegion.None;

	private UmLocationManager()
	{

	}

	// public static UmLocationManager getInstance()
	// {
	// if (instance == null)
	// {
	// synchronized (lockValue)
	// {
	// if (instance == null)
	// {
	// instance = new UmLocationManager();
	// }
	// }
	// }
	// return instance;
	// }

	/**
	 * 0内置，1百度，2腾讯
	 */
	public static void setContext(Context context, int type, UmLocationRegion region)
	{
		mContext = context;

		mUmLocationDeviceType = type;
		mUmLocationRegion = region;

		mUmLocationDevice = getUmLocationDevice(mUmLocationDeviceType);
		coordTrans = getCoordTrans(mUmLocationRegion);

		if (mUmLocationDevice != null)
		{
			mUmLocationDevice.setContext(context);
		}
	}

	public static void setContext(Context context)
	{
		mContext = context;
	}

	public static void setUmLocationRegion(UmLocationRegion region)
	{
		mUmLocationRegion = region;
		coordTrans = getCoordTrans(mUmLocationRegion);
	}

	/**
	 * 0内置，1百度，2腾讯
	 */
	public static void setUmLocationDeviceType(int type)
	{
		if (mUmLocationDevice != null)
		{
			mUmLocationDevice.stopLocation();
		}
		mUmLocationDeviceType = type;
		mUmLocationDevice = getUmLocationDevice(mUmLocationDeviceType);
		if (mUmLocationDevice != null && mContext != null)
		{
			mUmLocationDevice.setContext(mContext);
		}
	}

	/**
	 * 0内置，1百度，2腾讯
	 */
	public static IUmLocationDevice getUmLocationDevice(int type)
	{
		IUmLocationDevice mUmLocationDevice = null;

		switch (type)
		{
		case 0:
			mUmLocationDevice = new UmLocationDeviceInner();
			break;
		case 1:
			mUmLocationDevice = new UmLocationDeviceBaidu();
			break;
		case 2:
			mUmLocationDevice = new UmLocationDeviceTencent();
			break;
		default:
			mUmLocationDevice = new UmLocationDeviceInner();
			break;
		}
		return mUmLocationDevice;
	}

	public static ICoordTrans getCoordTrans(UmLocationRegion region)
	{
		ICoordTrans coordTrans = null;

		switch (region)
		{
		case ShenZhen:
			coordTrans = new SZCoordTrans();
			break;
		case JiangMen:
			coordTrans = new JMTransRegions();
			break;
		case HaErBin:
			coordTrans = new HEBTransRegions();
			break;
		case FuZhou:
			coordTrans = new FZTransRegions();
			break;
		case XuChang:
			coordTrans = new XCTransRegions();
			break;
		case ZhuHai:
			coordTrans = new ZHTransRegions();
			break;
		case BeiJing:
			coordTrans = new BJTransRegions();
			break;
		case HaiKou:
			coordTrans = new HaiKouTransRegions();
			break;
		case ZhengZhou:
			coordTrans = new ZhengZhouTransRegions();
			break;
		case FoShan:
			coordTrans = new FSTransRegions();
			break;
		case HuangGang:
			coordTrans = new HuangGangCoordTrans();
			break;
		case AnFu:
			coordTrans = new AFTransRegions();
			break;
		default:
			break;
		}
		return coordTrans;
	}

	public static Context getContext()
	{
		return mContext;
	}

	public static boolean isStarted()
	{
		if (mUmLocationDevice != null)
		{
			return mUmLocationDevice.isStarted();
		}
		return false;
	}

	public static void startLocation()
	{
		if (mUmLocationDevice != null)
		{
			mUmLocationDevice.startLocation();
		}
	}

	public static void stopLocation()
	{
		if (mUmLocationDevice != null)
		{
			mUmLocationDevice.stopLocation();
		}
	}

	public static UmLocation getUmLocation()
	{
		UmLocation umLocation = null;

		if (mUmLocationDevice != null)
		{
			umLocation = mUmLocationDevice.getUmLocation();
		}
		transToXY(umLocation);
		return umLocation;
	}

	public static UmLocation getLastKnownUmLocation()
	{
		UmLocation umLocation = null;
		if (mUmLocationDevice != null)
		{
			umLocation = mUmLocationDevice.getLastKnownUmLocation();
		}
		transToXY(umLocation);
		return umLocation;
	}

	/**
	 * 经纬度转换为绝对坐标
	 * 
	 * @param umLocation
	 */
	public static void transToXY(UmLocation umLocation, UmLocationRegion region)
	{
		if (umLocation != null)
		{
			ICoordTrans coordTrans = getCoordTrans(region);

			if (coordTrans != null)
			{
				PointDElement pointLongLat = new PointDElement(umLocation.getLongitude(), umLocation.getLatitude());
				PointDElement ptAreaCoor = coordTrans.LonLanToXY(pointLongLat);

				umLocation.setAbsX(ptAreaCoor.x);
				umLocation.setAbsY(ptAreaCoor.y);
			}
		}
	}

	/**
	 * 经纬度转换为绝对坐标
	 * 
	 * @param umLocation
	 */
	public static void transToXY(UmLocation umLocation)
	{
		if (umLocation != null)
		{
			if (coordTrans != null)
			{
				PointDElement pointLongLat = new PointDElement(umLocation.getLongitude(), umLocation.getLatitude());
				PointDElement ptAreaCoor = coordTrans.LonLanToXY(pointLongLat);

				umLocation.setAbsX(ptAreaCoor.x);
				umLocation.setAbsY(ptAreaCoor.y);
			}
		}
	}

	public static boolean isProviderEnabled()
	{
		if (mUmLocationDevice != null)
		{
			return mUmLocationDevice.isProviderEnabled();
		}
		return false;
	}
}
