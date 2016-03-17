package com.umframework.location;

import android.content.Context;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

/**
 * 百度
 * 
 * @author martin.zheng
 * 
 */
public class UmLocationDeviceBaidu extends BaseUmLocationDevice implements BDLocationListener
{
	private LocationClient mLocationClient = null;

	@Override
	public void setContext(Context context)
	{
		super.setContext(context);
		mLocationClient = new LocationClient(context);

		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true); // 打开gps
		/**
		 * 度娘支持返回若干种坐标系，包括国测局坐标系、度娘坐标系，* 如果不指定，默认返回度娘坐标系。
		 * 
		 * 目前这些参数的代码为
		 * 
		 * 返回国测局经纬度坐标系 coor=gcj02
		 * 
		 * 返回百度墨卡托坐标系 coor=bd09
		 * 
		 * 返回百度经纬度坐标系 coor=bd09ll (手机地图对外接口中的坐标系默认是bd09ll)
		 */
		option.setCoorType("gcj02"); // 设置坐标类型
		option.disableCache(true);// 关闭缓存
		option.setServiceName("GpsBD");
		option.setPoiExtraInfo(false);// 不查找兴趣点
		option.setAddrType("all");// 若有地址信息，则显示所有
		option.setScanSpan(1000);// gps更新周期
		option.setTimeOut(500);// 网络定位超时时间
		option.setPriority(LocationClientOption.GpsFirst);// 设置定位优先级
															// LocationClientOption.NetWorkFirst;
		mLocationClient.setLocOption(option);
	}

	@Override
	public void startLocation()
	{
		mLocationClient.registerLocationListener(this);
		mLocationClient.start();
		mStarted = true;
	}

	@Override
	public void stopLocation()
	{
		mLocationClient.unRegisterLocationListener(this);
		mLocationClient.stop();
		mStarted = false;
	}

	@Override
	public UmLocation getLastKnownUmLocation()
	{
		BDLocation bdLocation = mLocationClient.getLastKnownLocation();
		if (bdLocation != null)
		{
			UmLocation umLocation = new UmLocation(bdLocation);
			return umLocation;
		}
		return null;
	}

	@Override
	public void onReceiveLocation(BDLocation bdLocation)
	{
		if (bdLocation != null)
		{
			mUmLocation = new UmLocation(bdLocation);
		}
		else
		{
			mUmLocation = null;
		}
	}

	@Override
	public void onReceivePoi(BDLocation bdLocation)
	{

	}
}
