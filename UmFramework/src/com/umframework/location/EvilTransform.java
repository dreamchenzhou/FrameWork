package com.umframework.location;

/**
 * 经纬度纠正
 * 
 * @author martin.zheng
 * 
 */
public class EvilTransform
{
	static final double pi = 3.14159265358979324;

	public static class EvilValue
	{
		public double mgLon;
		public double mgLat;

		public EvilValue()
		{

		}

		public EvilValue(double mgLon, double mgLat)
		{
			this.mgLon = mgLon;
			this.mgLat = mgLat;
		}

		public void setValue(double mgLon, double mgLat)
		{
			this.mgLon = mgLon;
			this.mgLat = mgLat;
		}
	}

	//
	// Krasovsky 1940
	//
	// a = 6378245.0, 1/f = 298.3
	// b = a * (1 - f)
	// ee = (a^2 - b^2) / a^2;
	static final double a = 6378245.0;
	static final double ee = 0.00669342162296594323;

	//
	// World Geodetic System ==> Mars Geodetic System
	public static EvilValue transform(double wgLat, double wgLon)
	{
		EvilValue result = new EvilValue();

		if (outOfChina(wgLat, wgLon))
		{
			result.setValue(wgLon, wgLat);
			return result;
		}
		else
		{
			double dLat = transformLat(wgLon - 105.0, wgLat - 35.0);
			double dLon = transformLon(wgLon - 105.0, wgLat - 35.0);
			double radLat = wgLat / 180.0 * pi;
			double magic = Math.sin(radLat);
			magic = 1 - ee * magic * magic;
			double sqrtMagic = Math.sqrt(magic);
			dLat = (dLat * 180.0) / ((a * (1 - ee)) / (magic * sqrtMagic) * pi);
			dLon = (dLon * 180.0) / (a / sqrtMagic * Math.cos(radLat) * pi);

			double lat = wgLat + dLat;
			double lon = wgLon + dLon;

			result.setValue(lon, lat);

			return result;
		}
	}

	static boolean outOfChina(double lat, double lon)
	{
		if (lon < 72.004 || lon > 137.8347)
			return true;
		if (lat < 0.8293 || lat > 55.8271)
			return true;
		return false;
	}

	static double transformLat(double x, double y)
	{
		double ret = -100.0 + 2.0 * x + 3.0 * y + 0.2 * y * y + 0.1 * x * y + 0.2 * Math.sqrt(Math.abs(x));
		ret += (20.0 * Math.sin(6.0 * x * pi) + 20.0 * Math.sin(2.0 * x * pi)) * 2.0 / 3.0;
		ret += (20.0 * Math.sin(y * pi) + 40.0 * Math.sin(y / 3.0 * pi)) * 2.0 / 3.0;
		ret += (160.0 * Math.sin(y / 12.0 * pi) + 320 * Math.sin(y * pi / 30.0)) * 2.0 / 3.0;
		return ret;
	}

	static double transformLon(double x, double y)
	{
		double ret = 300.0 + x + 2.0 * y + 0.1 * x * x + 0.1 * x * y + 0.1 * Math.sqrt(Math.abs(x));
		ret += (20.0 * Math.sin(6.0 * x * pi) + 20.0 * Math.sin(2.0 * x * pi)) * 2.0 / 3.0;
		ret += (20.0 * Math.sin(x * pi) + 40.0 * Math.sin(x / 3.0 * pi)) * 2.0 / 3.0;
		ret += (150.0 * Math.sin(x / 12.0 * pi) + 300.0 * Math.sin(x / 30.0 * pi)) * 2.0 / 3.0;
		return ret;
	}

	/**
	 * 经纬度纠正
	 * 
	 * @param umLocation
	 */
	public static void transform(UmLocation umLocation)
	{
		double lon = umLocation.getLongitude();
		double lat = umLocation.getLatitude();

		EvilValue evilValue = EvilTransform.transform(lat, lon);

		double offLon = evilValue.mgLon - lon;
		double offLat = evilValue.mgLat - lat;

		umLocation.setLongitude(lon - offLon);
		umLocation.setLatitude(lat - offLat);
	}
}