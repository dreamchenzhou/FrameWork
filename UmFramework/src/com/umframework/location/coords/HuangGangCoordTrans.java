package com.umframework.location.coords;

/// <summary>
/// 湖北黄冈绝对坐标系转换公式
/// </summary>
public class HuangGangCoordTrans implements ICoordTrans
{
	public PointDElement LonLanToXY(PointDElement pt)
	{
		PointDElement tmp = GetXY(pt.y, pt.x);
		return new PointDElement(tmp.y, tmp.x);
	}

	public PointDElement XYToLonLan(PointDElement pt)
	{
		// throw new Exception("The method or operation is not implemented.");
		return null;
	}

	static final double L0 = 115.0;
	// 常数定义
	static final double A54 = 6378245.0;
	static final double B54 = 6356863.0;
	static final double F54 = 298.299738;

	static final double A84 = 6378137.0;
	static final double B84 = 6356752.314;
	static final double F84 = 298.257224;

	private static PointDElement tempPoint = new PointDElement();

	// 度转换为弧
	private static double DMS2RAD(double dms)
	{
		// double d,m,s;
		double rad;

		// d=System.Convert.ToDouble(dms.Substring(0,3));
		// m=System.Convert.ToDouble(dms.Substring(3,2));
		// s=System.Convert.ToDouble(dms.Substring(5,8));

		// rad=d+m/60.0+s/3600.0;
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
			l = L - (L0 / 180.0) * Math.PI;
			t = Math.tan(B);
			Xita = E1 * Math.cos(B);

			X = X0 + (N / 2) * Math.sin(B) * Math.cos(B) * l * l + (N / 24) * Math.sin(B) * Math.pow(Math.cos(B), 3) * (5 - t * t + 9 * Xita * Xita + 4 * Math.pow(Xita, 4)) * Math.pow(l, 4) + (N / 720) * Math.sin(B) * Math.pow(Math.cos(B), 5) * (61 - 58 * t * t + Math.pow(t, 4)) * Math.pow(l, 6);

			Y = 500000 + N * Math.cos(B) * l + (N / 6) * Math.pow(Math.cos(B), 3) * (1 - t * t + Xita * Xita) * Math.pow(l, 3) + (N / 120) * Math.pow(Math.cos(B), 5) * (5 - 18 * t * t + Math.pow(t, 4) + 14 * Xita * Xita - 58 * Xita * Xita * t * t) * Math.pow(l, 5);

			tempPoint.x = X;
			tempPoint.y = Y;
		}
		catch (Exception e1)
		{
			// MessageBox.Show(e1.Message);
		}
	}

	// WGS84坐标系下的高斯投影坐标转换为北京54坐标系下的高斯投影坐标
	private static void M_84GS2GS54(double X84, double Y84)
	{
		// double m, n, S1, S2;
		double X54 = 0;
		double Y54 = 0;

		// m=-0.000150828089228486;
		// n=1.00188477981972;
		// S1=-5504.49446203488;
		// S2=-862.136605325504;

		// m=0.00144062140;
		// n=0.00011833686;
		// S1=-4197.14522569066;
		// S2=-1318.91367520791;

		// m=0.00159626901;
		// n=0.00009257753;
		// S1=-4626.24368168207;
		// S2=-1353.20856685131;

		double X054, Y054;
		double X084, Y084;
		double K, Alpha;
		try
		{
			// X54=n*X84-m*Y84+S1;
			// Y54=m*X84+n*Y84+S2;

			// X54=S1+(1+m)*X84+n*Y84;
			// Y54=S2+(1+m)*Y84+n*X84;

			X054 = 2885443.576;
			Y054 = 439140.855;
			X084 = 2885386.237;
			Y084 = 439217.6886;
			K = 0.99998658699926;
			Alpha = -0.00000300280551273363;

			// X054=2890681.9325;
			// Y054=434913.6845;
			// X084=2890624.984;
			// Y084=434990.338;
			// K=0.999994845645575;
			// Alpha=-0.0000133118934204646;

			X54 = X054 + K * Math.cos(Alpha) * (X84 - X084) - K * Math.sin(Alpha) * (Y84 - Y084);
			Y54 = Y054 + K * Math.cos(Alpha) * (Y84 - Y084) + K * Math.sin(Alpha) * (X84 - X084);

		}
		catch (Exception e1)
		{
			// MessageBox.Show(e1.Message);
		}
		tempPoint.x = X54;
		tempPoint.y = Y54;
	}

	// / <summary>
	// / 处理纬度,经度
	// / </summary>
	private static String DealDegree(String tmpstr)
	{
		return ChangeIntoDegree(tmpstr);
	}

	// / <summary>
	// / 转为度数
	// / </summary>
	// / <param name="tmpstr"></param>
	// / <returns></returns>
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

		return String.valueOf(returndegree);
	}

	// / <summary>
	// / 坐标转换
	// / </summary>
	// / <param name="B">纬度</param>
	// / <param name="L">经度</param>
	// / <returns></returns>
	private static PointDElement GetXY(double B, double L)
	{
		M_DD2GS(B, L);
		M_84GS2GS54(tempPoint.x, tempPoint.y);
		return tempPoint;
	}

	// / <summary>
	// / 坐标转换(WGS84--北京54)
	// / </summary>
	// / <param name="latitude">纬度</param>
	// / <param name="longitude">经度</param>
	// / <returns></returns>
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