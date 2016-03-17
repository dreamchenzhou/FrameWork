package com.umframework.location.coords;

public class ZhengZhouTransRegions implements ICoordTrans
{
	public PointDElement LonLanToXY(PointDElement pt)
	{
		try
		{
			TranItem item = BL2xy(pt.y, pt.x);
			return new PointDElement(item.y80, item.x80);
		}
		catch (Exception ex)
		{
			return new PointDElement((double) 0.0, (double) 0.0);
		}
	}

	public PointDElement XYToLonLan(PointDElement pt)
	{
		return new PointDElement();
	}

	public static TranItem BL2xy(double B, double L)
	{
		TranItem temp = new TranItem();
		TranItem resultOut = new TranItem();

		// WGS84ToXiAn80(B, L, out X80, out Y80);
		WGS84ToXiAn80_Quick(B, L, temp.x80, temp.y80);

		resultOut.x80 = temp.x80 - 1.2065489;
		resultOut.y80 = temp.y80 + 500000 - 119.316700755;

		return resultOut;
	}

	// / <summary>
	// / 快速
	// / 坐标系转换，由WGS84经纬度坐标系(GPS的经纬度)经高斯克吕格投影到XiAn80(西安80)坐标系xy值
	// / x轴为中央经线，y轴为赤道
	// / </summary>
	private static TranItem WGS84ToXiAn80_Quick(double B84, double L84,
			double X80, double Y80)
	{
		TranItem item = new TranItem();

		// //得到与中央经线的经差
		double L2 = 114.0;// 郑州---西安80坐标中央经度114
		double j1 = L84 - L2;

		// 化为弧度
		L84 = (j1 / 180.0) * Math.PI;
		B84 = (B84 / 180.0) * Math.PI;

		double p0 = (32144.5189 - (135.3646 - 0.7034 * Math.cos(B84) * Math.cos(B84)) * Math.cos(B84) * Math.cos(B84)) * Math.cos(B84);
		double p3 = (0.3333333 + 0.0011233 * Math.cos(B84) * Math.cos(B84)) * Math.cos(B84) * Math.cos(B84) - 0.1666667;
		double p4 = (0.25 + 0.00253 * Math.cos(B84) * Math.cos(B84)) * Math.cos(B84) * Math.cos(B84) - 0.04167;
		double p5 = 0.00878 - (0.1702 - 0.20382 * Math.cos(B84) * Math.cos(B84)) * Math.cos(B84) * Math.cos(B84);
		double p6 = Math.cos(B84) * Math.cos(B84) * (0.167 * Math.cos(B84) * Math.cos(B84) - 0.083);
		double N1 = (6399596.652 - (21565.045 - (108.996 - 0.603 * Math.cos(B84) * Math.cos(B84)) * Math.cos(B84) * Math.cos(B84)) * Math.cos(B84) * Math.cos(B84)) * Math.cos(B84);

		item.x80 = 6367452.1328 * B84 - (p0 - (0.5 + (p4 + p6 * L84 * L84) * L84 * L84) * L84 * L84 * N1) * Math.sin(B84);
		item.y80 = (1 + (p3 + p5 * L84 * L84) * L84 * L84) * L84 * N1;
		return item;
	}
}