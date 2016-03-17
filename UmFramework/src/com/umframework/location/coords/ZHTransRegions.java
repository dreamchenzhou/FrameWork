package com.umframework.location.coords;

/**
 * 珠海独立坐标转换公式
 * 
 * @author martin.zheng
 * 
 */
public class ZHTransRegions implements ICoordTrans
{
	public PointDElement LonLanToXY(PointDElement pt)
	{
		PointDElement tmp = GetXY(pt.y, pt.x);
		return new PointDElement(tmp.y + 122158, tmp.x + 3844359);
	}

	public PointDElement XYToLonLan(PointDElement pt)
	{
		// throw new Exception("The method or operation is not implemented.");
		return null;
	}

	// 常数定义
	// private static final double A54 = 6378245.0;
	// private static final double B54 = 6356863.0;
	// private static final double F54 = 298.299738;

	private static final double A84 = 6378137.0;
	private static final double B84 = 6356752.314;
	// private static final double F84 = 298.257224;

	private static PointDElement tempPoint = new PointDElement();

	// 度转换为弧
	private static double DMS2RAD(double dms)
	{
		double rad;
		rad = (dms / 180) * Math.PI;
		return (rad);
	}

	// WGS84大地坐标转换为高斯投影坐标
	private static void M_DD2GS(double B, double L)
	{
		double X, Y;
		double m0, m2, m4, m6, m8;
		double a0, a2, a4, a6, X0;
		double l, t, Xita;
		double E, E1, N;

		try
		{
			B = DMS2RAD(B);
			L = DMS2RAD(L);

			E = Math.sqrt((A84 * A84 - B84 * B84) / (A84 * A84)); // 第一偏心率
			E1 = Math.sqrt((A84 * A84 - B84 * B84) / (B84 * B84)); // 第二偏心率

			m0 = A84 * (1 - E * E);
			m2 = (3.0 / 2.0) * m0 * E * E;
			m4 = (5.0 / 4.0) * m2 * E * E;
			m6 = (7.0 / 6.0) * m4 * E * E;
			m8 = (9.0 / 8.0) * m6 * E * E;
			a0 = m0 + m2 / 2.0 + (3.0 * m4) / 8.0 + (5.0 * m6) / 16.0 + (35.0 * m8) / 128.0;
			a2 = m2 / 2.0 + m4 / 2.0 + (15 * m6) / 32.0 + (7 * m8) / 16.0;
			a4 = m4 / 8.0 + (3.0 * m6) / 16 + (7.0 * m8) / 32.0;
			a6 = m6 / 32.0 + m8 / 16.0;
			X0 = a0 * B - Math.sin(B) * Math.cos(B) * ((a2 - a4 + a6) + (2.0 * a4 - (16.0 / 3.0) * a6) * Math.sin(B) * Math.sin(B) + (16.0 / 3.0) * a6 * Math.pow(Math.sin(B), 4));

			N = A84 * A84 / (B84 * Math.sqrt(1 + E1 * E1 * Math.cos(B) * Math.cos(B)));
			l = L - (113.35 / 180.0) * Math.PI;

			t = Math.tan(B);
			Xita = E1 * Math.cos(B);

			X = X0 + (N / 2) * Math.sin(B) * Math.cos(B) * l * l + (N / 24) * Math.sin(B) * Math.pow(Math.cos(B), 3) * (5 - t * t + 9 * Xita * Xita + 4 * Math.pow(Xita, 4)) * Math.pow(l, 4) + (N / 720) * Math.sin(B) * Math.pow(Math.cos(B), 5) * (61 - 58 * t * t + Math.pow(t, 4)) * Math.pow(l, 6);

			Y = 100000 + N * Math.cos(B) * l + (N / 6) * Math.pow(Math.cos(B), 3) * (1 - t * t + Xita * Xita) * Math.pow(l, 3) + (N / 120) * Math.pow(Math.cos(B), 5) * (5 - 18 * t * t + Math.pow(t, 4) + 14 * Xita * Xita - 58 * Xita * Xita * t * t) * Math.pow(l, 5);

			tempPoint.x = X;
			tempPoint.y = Y;
		}
		catch (Exception e1)
		{
			// MessageBox.Show(e1.Message);
		}
	}

	/**
	 * WGS84坐标系下的高斯投影坐标转换为北京54坐标系下的高斯投影坐标
	 * 
	 * @param X84
	 * @param Y84
	 */
	private static void M_84GS2GS54(double X84, double Y84)
	{
		// double m, n, S1, S2;
		double X54 = 0;
		double Y54 = 0;

		double X054, Y054;
		double X084, Y084;
		double K, Alpha;
		try
		{
			X054 = 2885439.576;
			Y054 = 439145.855;
			X084 = 2885386.237;
			Y084 = 439217.6886;
			K = 0.99998658699926;
			Alpha = -0.00000300280551273363;

			X54 = X054 + K * Math.cos(Alpha) * (X84 - X084) - K * Math.sin(Alpha) * (Y84 - Y084);
			Y54 = Y054 + K * Math.cos(Alpha) * (Y84 - Y084) + K * Math.sin(Alpha) * (X84 - X084);

			// 偏移量
			X54 += 5;
			Y54 += 8;
		}
		catch (Exception e1)
		{
			e1.printStackTrace();
		}
		tempPoint.x = X54;
		tempPoint.y = Y54;
	}

	/**
	 * 处理纬度,经度
	 * 
	 * @param tmpstr
	 * @return
	 */
	private static String DealDegree(String tmpstr)
	{
		return ChangeIntoDegree(tmpstr);
	}

	/**
	 * 转为度数
	 * 
	 * @param tmpstr
	 * @return
	 */
	private static String ChangeIntoDegree(String tmpstr)
	{
		// 度, 度的长度
		int degree = 0, degreelength = 0;
		// 最终的度数, 分
		Double returndegree, minutenumber;

		// 小数点的位置
		int tmppoisiton = tmpstr.lastIndexOf(".");
		degreelength = tmppoisiton - 2;
		if (degreelength > 0)
		{ // 度
			degree = Integer.parseInt(tmpstr.substring(0, degreelength));
		}
		// 分
		minutenumber = Double.parseDouble(tmpstr.substring(degreelength));

		returndegree = degree + minutenumber / 60;

		return returndegree.toString();
	}

	/**
	 * 坐标转换 B:纬度 L:经度
	 */
	private static PointDElement GetXY(double B, double L)
	{
		M_DD2GS(B, L);
		M_84GS2GS54(tempPoint.x, tempPoint.y);
		return tempPoint;
	}

	/**
	 * 坐标转换(WGS84--北京54) latitude纬度,longitude经度
	 */
	public static PointDElement get84CoordSys(String latitude, String longitude)
	{
		if (latitude.length() > 0 & longitude.length() > 0)
		{
			if (latitude != "0000.0000" & longitude != "00000.0000")
			{
				double px = 0, py = 0;
				// 转换成度
				py = Double.parseDouble(DealDegree(longitude));
				px = Double.parseDouble(DealDegree(latitude));
				if (px > 0 & py > 0)
				{
					// 转换坐标
					tempPoint = GetXY(px, py);
					if (tempPoint.x > 0 & tempPoint.y > 0)
					{
						return tempPoint;
					}
				}
			}
		}
		return tempPoint;
	}
}
