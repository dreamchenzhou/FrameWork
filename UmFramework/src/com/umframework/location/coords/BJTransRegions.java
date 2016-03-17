package com.umframework.location.coords;

/**
 * 北京绝对坐标系转换公式
 * 
 * @author martin.zheng
 * 
 */
public class BJTransRegions implements ICoordTrans
{

	public PointDElement LonLanToXY(PointDElement pt)
	{
		try
		{
			TranItem item = new TranItem();
			BL2xy(pt.y, pt.x, item);
			return new PointDElement(item.y80, item.x80);
		}
		catch (Exception ex)
		{
			return new PointDElement((double) 0.0, (double) 0.0);
		}
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

	private static double DMS2RAD(double dms)
	{
		return dms * Math.PI / 180;
	}

	private static void M_DD2GS(double B, double L, TranItem item)
	{
		double m0, m2, m4, m6, m8;
		double a0, a2, a4, a6, X0;
		double l, t, Xita;
		double E, E1, N;

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
		l = L - (116.350251805556 / 180.0) * Math.PI;
		t = Math.tan(B);
		Xita = E1 * Math.cos(B);

		item.x80 = -4414657 + 300000 + X0 + (N / 2) * Math.sin(B) * Math.cos(B) * l * l + (N / 24) * Math.sin(B) * Math.pow(Math.cos(B), 3) * (5 - t * t + 9 * Xita * Xita + 4 * Math.pow(Xita, 4)) * Math.pow(l, 4) + (N / 720) * Math.sin(B) * Math.pow(Math.cos(B), 5) * (61 - 58 * t * t + Math.pow(t, 4)) * Math.pow(l, 6);

		item.y80 = 500000 + N * Math.cos(B) * l + (N / 6) * Math.pow(Math.cos(B), 3) * (1 - t * t + Xita * Xita) * Math.pow(l, 3) + (N / 120) * Math.pow(Math.cos(B), 5) * (5 - 18 * t * t + Math.pow(t, 4) + 14 * Xita * Xita - 58 * Xita * Xita * t * t) * Math.pow(l, 5);
	}

	private static void M_84GS2GS54(double X84, double Y84, TranItem item)
	{
		double X054, Y054;
		double X084, Y084;
		double K, Alpha;

		X054 = 2885443.576;
		Y054 = 439140.855;
		X084 = 2885386.237;
		Y084 = 439217.6886;
		K = 0.99998658699926;
		Alpha = -0.00000300280551273363;

		item.x80 = X054 + K * Math.cos(Alpha) * (X84 - X084) - K * Math.sin(Alpha) * (Y84 - Y084);
		item.y80 = Y054 + K * Math.cos(Alpha) * (Y84 - Y084) + K * Math.sin(Alpha) * (X84 - X084);
	}

	public static void BL2xy(double B, double L, TranItem item)
	{
		TranItem tran54 = new TranItem();
		TranItem tran84 = new TranItem();

		M_DD2GS(B, L, tran84);
		M_84GS2GS54(tran84.x80, tran84.y80, tran54);

		item.x80 = tran54.x80 - 93;
		item.y80 = tran54.y80 + 18;
	}
}