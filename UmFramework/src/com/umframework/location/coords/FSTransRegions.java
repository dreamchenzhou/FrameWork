package com.umframework.location.coords;

/**
 * 佛山绝对坐标系转换公式
 * 
 * @author martin.zheng
 * 
 */
public class FSTransRegions implements ICoordTrans
{
	private final double dX1 = 781.328258;
	private final double dY1 = 1168.750716;
	private final double dT1 = -2.67615E-05;
	private final double dK1 = 0.999434199575;
	private final double dX2 = 602.018889;
	private final double dY2 = -2758.05582;
	private final double dT2 = 0.0006687599;
	private final double dK2 = 1.001060192672;

	public PointDElement LonLanToXY(PointDElement pt)
	{
		TranItem item = new TranItem();
		LonLanToXY(pt.x, pt.y, item);
		return new PointDElement(item.x80, item.y80);
	}

	public PointDElement XYToLonLan(PointDElement pt)
	{
		return new PointDElement();
	}

	public void LonLanToXY(double lon, double lan, TranItem item)
	{
		BL2xy(lan, lon, item);
		EncryptCoordinate(item);
		item.x80 -= 18.5;
		item.y80 += 9;
	}

	void EncryptCoordinate(TranItem item)
	{
		double x = item.x80;
		double y = item.y80;

		item.x80 = (((x * dK1) * Math.cos(dT1)) - ((y * dK1) * Math.sin(dT1))) + dX1;
		item.y80 = (((x * dK1) * Math.sin(dT1)) + ((y * dK1) * Math.cos(dT1))) + dY1;
	}

	void DecryptCoordinate(TranItem item)
	{
		double x = item.x80;
		double y = item.y80;

		item.x80 = (((x * dK2) * Math.cos(dT2)) - ((y * dK2) * Math.sin(dT2))) + dX2;
		item.y80 = (((x * dK2) * Math.sin(dT2)) + ((y * dK2) * Math.cos(dT2))) + dY2;
	}

	void BL2xy(double B, double L, TranItem item)
	{
		TranItem itemQ = new TranItem();
		WGS84ToXiAn80_Quick(B, L, itemQ);

		item.y80 = itemQ.x80 + 6.265489;// +18;////- 1.2065489;
		item.x80 = itemQ.y80 + 500000 - 115.342;// -37;//115.342;//119.316700755;
	}

	private void WGS84ToXiAn80_Quick(double B84, double L84, TranItem item)
	{
		// //得到与中央经线的经差
		double L2 = 113.0;// 佛山---西安80坐标中央经度114
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
	}
}