package com.umframework.location.coords;

public class AFTransRegions implements ICoordTrans
{
	private static final double dX1 = 781.328258;
	private static final double dY1 = 1168.750716;
	private static final double dT1 = -2.67615E-05;
	private static final double dK1 = 0.999434199575;
	private static final double dX2 = 602.018889;
	private static final double dY2 = -2758.05582;
	private static final double dT2 = 0.0006687599;
	private static final double dK2 = 1.001060192672;

	@Override
	public PointDElement LonLanToXY(PointDElement pt)
	{
		try
		{
			TranItem item = new TranItem();
			LonLanToXY(pt.x, pt.y, item);
			return new PointDElement(item.y80, item.x80);
		}
		catch (Exception ex)
		{
			return new PointDElement((double) 0.0, (double) 0.0);
		}
	}

	@Override
	public PointDElement XYToLonLan(PointDElement pt)
	{
		return new PointDElement();
	}

	// / <summary>
	// /
	// 经纬度转换为安福本地坐标系函数,如lonX:114.607411111111,latY:27.386925,X:559964.149927916,3030656.30524787
	// / </summary>
	// / <param name="lon">经度</param>
	// / <param name="lan">纬度</param>
	// / <param name="x">X</param>
	// / <param name="y">Y</param>
	public static void LonLanToXY(double lon, double lan, TranItem item)
	{
		BL2xy(lan, lon, item);
		// EncryptCoordinate(x, y, out x, out y);
		// x -= 18.5;
		// y += 9;
	}

	static void EncryptCoordinate(TranItem item)
	{
		double X = item.x80;
		double Y = item.y80;

		item.x80 = (((X * dK1) * Math.cos(dT1)) - ((Y * dK1) * Math.sin(dT1))) + dX1;
		item.y80 = (((X * dK1) * Math.sin(dT1)) + ((Y * dK1) * Math.cos(dT1))) + dY1;
	}

	static void DecryptCoordinate(TranItem item)
	{
		double X = item.x80;
		double Y = item.y80;

		item.x80 = (((X * dK2) * Math.cos(dT2)) - ((Y * dK2) * Math.sin(dT2))) + dX2;
		item.y80 = (((X * dK2) * Math.sin(dT2)) + ((Y * dK2) * Math.cos(dT2))) + dY2;
	}

	// //武功山大道与安平路交界, 114°36'26.68"东, 27°23'12.93"北
	// //如lonX:114.607411111111,latY:27.386925,X:559965.491927916,Y:3030653.53975887
	private static void BL2xy(double B, double L, TranItem item)
	{
		TranItem result = WGS84ToXiAn80_Quick(B, L);

		item.x80 = result.x80 + 1.5;
		item.y80 = result.y80 + 500000 - 116;
	}

	// / <summary>
	// / 快速
	// / 坐标系转换，由WGS84经纬度坐标系(GPS的经纬度)经高斯克吕格投影到XiAn80(西安80)坐标系xy值
	// / x轴为中央经线，y轴为赤道
	// / </summary>
	private static TranItem WGS84ToXiAn80_Quick(double B84, double L84)
	{
		// //得到与中央经线的经差
		double L2 = 114.0;// 安福---西安80坐标中央经度114
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

		TranItem tranItem = new TranItem();

		tranItem.x80 = 6367452.1328 * B84 - (p0 - (0.5 + (p4 + p6 * L84 * L84) * L84 * L84) * L84 * L84 * N1) * Math.sin(B84);
		tranItem.y80 = (1 + (p3 + p5 * L84 * L84) * L84 * L84) * L84 * N1;
		return tranItem;
	}
}