package com.umframework.location;

import android.content.Context;

import com.tencent.map.geolocation.TencentLocation;
import com.tencent.map.geolocation.TencentLocationListener;
import com.tencent.map.geolocation.TencentLocationManager;
import com.tencent.map.geolocation.TencentLocationRequest;

/**
 * 腾讯
 * 
 * @author martin.zheng
 * 
 */
public class UmLocationDeviceTencent extends BaseUmLocationDevice implements TencentLocationListener
{
	private TencentLocationManager mTencentLocationManager;

	@Override
	public void setContext(Context context)
	{
		super.setContext(context);
		mTencentLocationManager = TencentLocationManager.getInstance(mContext);

		// 坐标系
		// mLocationManager.setCoordinateType(TencentLocationManager.COORDINATE_TYPE_WGS84);
		mTencentLocationManager.setCoordinateType(TencentLocationManager.COORDINATE_TYPE_GCJ02);
	}

	@Override
	public void startLocation()
	{
		TencentLocationRequest request = TencentLocationRequest.create();
		// 设置定位周期(位置监听器回调周期), 单位为 ms (毫秒)，默认为10秒
		request.setInterval(1000);
		// 决定定位结果中包含哪些信息,(REQUEST_LEVEL_POI=4包含经纬度，位置所处的中国大陆行政区划及周边POI列表)
		request.setRequestLevel(TencentLocationRequest.REQUEST_LEVEL_POI);
		// 设置是否允许使用缓存, 连续多次定位时建议允许缓存，默认true
		request.setAllowCache(false);

		// 0注册成功，非0失败
		if (mTencentLocationManager.requestLocationUpdates(request, this) == 0)
		{
			mStarted = true;
		}
	}

	@Override
	public void stopLocation()
	{
		mTencentLocationManager.removeUpdates(this);
		mStarted = false;
	}

	@Override
	public UmLocation getLastKnownUmLocation()
	{
		UmLocation location = null;
		TencentLocation tencentLocation = mTencentLocationManager.getLastKnownLocation();
		if (tencentLocation != null)
		{
			location = new UmLocation(tencentLocation);
		}
		return location;
	}

	@Override
	public void onLocationChanged(TencentLocation location, int error, String reason)
	{
		if (TencentLocation.ERROR_OK == error)
		{
			mUmLocation = new UmLocation(location);
		}
		else
		{
			mUmLocation = null;
		}
	}

	@Override
	public void onStatusUpdate(String name, int status, String desc)
	{

	}
}
