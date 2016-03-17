package com.umframework.location.coords;

public class SZCoordTrans implements ICoordTrans
{
	private double a = 0.0;
	private double b = 0.0;
	private double B = 0.0;
	public static int k0 = 1;
	private double L = 0.0;
	private double L0 = 0.0;

	public double getA()
	{
		return ((this.L - this.L0) * Math.cos(this.B));
	}

	public double getC()
	{
		return (Math.pow(this.getSEccentricity(), 2.0) * Math.pow(Math.cos(this.B), 2.0));
	}

	public double getEccentricity()
	{
		return Math.sqrt(1.0 - Math.pow(this.b / this.a, 2.0));
	}

	public double getFlattening()
	{
		return ((this.a - this.b) / this.a);
	}

	public double getN()
	{
		double x = this.getEccentricity();
		return (this.a / Math.sqrt(1.0 - (Math.pow(x, 2.0) * Math.pow(Math.sin(this.B), 2.0))));
	}

	public double getRad(double d)
	{
		return ((d / 180.0) * 3.1415926535897931);
	}

	public double getSEccentricity()
	{
		double num = (this.a * 1.0) / this.b;
		return Math.sqrt((num * num) - 1.0);
	}

	public double getT()
	{
		return Math.pow(Math.tan(this.B), 2.0);
	}

	public PointDElement ShareSuanFa(double jindu, double weidu)
	{
		long num5;
		double num22 = weidu;
		double num = jindu;
		double num2 = num22;
		double num21 = 0.0174532925199433;
		long num6 = 3L;
		double num12 = 6378245.0;
		double num13 = 0.003352329869259135;
		double d = num / ((double) num6);
		long num25 = (long) Math.floor(d);
		if (d > (num25 + 0.5))
		{
			num5 = num25 + 1L;
		}
		else
		{
			num5 = num25;
		}
		double num9 = num5 * num6;
		num9 *= num21;
		double num7 = num * num21;
		double a = num2 * num21;
		double num14 = (2.0 * num13) - (num13 * num13);
		double num15 = num14 * (1.0 - num14);
		double num16 = num12 / Math.sqrt(1.0 - ((num14 * Math.sin(a)) * Math.sin(a)));
		double num17 = Math.tan(a) * Math.tan(a);
		double num18 = (num15 * Math.cos(a)) * Math.cos(a);
		double num19 = (num7 - num9) * Math.cos(a);
		double num20 = num12 * (((((((1.0 - (num14 / 4.0)) - (((3.0 * num14) * num14) / 64.0)) - ((((5.0 * num14) * num14) * num14) / 256.0)) * a) - (((((3.0 * num14) / 8.0) + (((3.0 * num14) * num14) / 32.0)) + ((((45.0 * num14) * num14) * num14) / 1024.0)) * Math.sin(2.0 * a))) + (((((15.0 * num14) * num14) / 256.0) + ((((45.0 * num14) * num14) * num14) / 1024.0)) * Math.sin(4.0 * a))) - (((((35.0 * num14) * num14) * num14) / 3072.0) * Math.sin(6.0 * a)));
		double num10 = num16 * ((num19 + ((((((1.0 - num17) + num18) * num19) * num19) * num19) / 6.0)) + ((((((((((5.0 - (18.0 * num17)) + (num17 * num17)) + (72.0 * num18)) - (58.0 * num15)) * num19) * num19) * num19) * num19) * num19) / 120.0));
		double num11 = num20 + ((num16 * Math.tan(a)) * ((((num19 * num19) / 2.0) + ((((((((5.0 - num17) + (9.0 * num18)) + ((4.0 * num18) * num18)) * num19) * num19) * num19) * num19) / 24.0)) + (((((((((((61.0 - (58.0 * num17)) + (num17 * num17)) + (600.0 * num18)) - (330.0 * num15)) * num19) * num19) * num19) * num19) * num19) * num19) / 720.0)));
		num10 += 500000.0;
		// num11 = num11;
		double num3 = num10;
		double num4 = num11;
		double num26 = 6.2661048868010392;
		double num27 = (((num3 * Math.cos(num26)) * 1.00001) - ((num4 * Math.sin(num26)) * 1.00001)) - 433212.6458;
		double num28 = (((num4 * Math.cos(num26)) * 1.00001) + ((num3 * Math.sin(num26)) * 1.00001)) - 2465706.60421;
		num27 -= 87.0;
		num28 += 25.0;

		PointDElement result = new PointDElement(num27, num28);
		return result;
	}

	public double transDFMtoDegree(double dfm)
	{
		double num = Math.floor((double) (dfm / 100.0));
		double num2 = (dfm - (num * 100.0)) / 60.0;
		return (num + num2);
	}

	public double transDFMtoDegree1(double dfm)
	{
		double num = Math.floor(dfm);
		double num2 = ((1.0 * (Math.floor((double) (dfm * 100.0)) - (num * 100.0))) * 1.0) / 60.0;
		double num3 = ((dfm * 10000.0) - (Math.floor((double) (dfm * 100.0)) * 100.0)) / 3600.0;
		return ((num + num2) + num3);
	}

	public PointDElement LonLanToXY(PointDElement pt)
	{
		PointDElement result = ShareSuanFa(pt.x, pt.y);
		return result;
	}

	public PointDElement XYToLonLan(PointDElement pt)
	{
		return null;
	}
}
