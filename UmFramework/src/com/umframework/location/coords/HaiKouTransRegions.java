package com.umframework.location.coords;

/**
 * 海南省海口市绝对坐标系转换公式
 * 
 * @author martin.zheng
 * 
 */
public class HaiKouTransRegions implements ICoordTrans
{
	double xOut;
	double yOut;

	public PointDElement LonLanToXY(PointDElement pt)
	{
		LonLat2Mercator(pt.x, pt.y);
		return new PointDElement(xOut, yOut);
	}

	public PointDElement XYToLonLan(PointDElement pt)
	{
		Mercator2LonLat(pt.x, pt.y);
		return new PointDElement(xOut, yOut);
	}

	/**
	 * 经纬度转XY
	 * 
	 * @param xIn
	 * @param yIn
	 */
	public void LonLat2Mercator(double xIn, double yIn)
	{
		double x = xIn * 20037508.34 / 180;
		double y = Math.log(Math.tan((90 + yIn) * Math.PI / 360)) / (Math.PI / 180);
		y = y * 20037508.34 / 180;
		xOut = x;
		yOut = y;
	}

	/**
	 * XY转经纬度
	 * 
	 * @param xIn
	 * @param yIn
	 */
	public void Mercator2LonLat(double xIn, double yIn)
	{
		double x = xIn / 20037508.34 * 180;
		double y = yIn / 20037508.34 * 180;
		y = 180 / Math.PI * (2 * Math.atan(Math.exp(y * Math.PI / 180)) - Math.PI / 2);
		xOut = x;
		yOut = y;
	}
}
