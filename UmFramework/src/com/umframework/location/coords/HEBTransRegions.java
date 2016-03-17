/**
 * 
 */
package com.umframework.location.coords;

/**
 * @author martin.zheng
 * 
 */
public class HEBTransRegions implements ICoordTrans
{
	@Override
	public PointDElement LonLanToXY(PointDElement pt)
	{
		double x = pt.x * 20037508.34 / 180;
		double y = Math.log(Math.tan((90 + pt.y) * Math.PI / 360))
				/ (Math.PI / 180);
		y = y * 20037508.34 / 180;
		return new PointDElement(x, y);
	}

	@Override
	public PointDElement XYToLonLan(PointDElement pt)
	{
		double x = pt.x / 20037508.34 * 180;
		double y = pt.y / 20037508.34 * 180;
		y = 180 / Math.PI
				* (2 * Math.atan(Math.exp(y * Math.PI / 180)) - Math.PI / 2);
		return new PointDElement(x, y);
	}
}
