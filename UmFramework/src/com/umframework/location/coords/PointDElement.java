package com.umframework.location.coords;

public class PointDElement
{
	public double x;
	public double y;

	public PointDElement()
	{
		
	}
	
	public PointDElement(double x, double y)
	{
		this.x = x;
		this.y = y;
	}
	
	 public static PointDElement DegToRad(PointDElement pt)
     {
         pt.x = (pt.x * 3.1415926535897931) / 180;
         pt.y = (pt.y * 3.1415926535897931) / 180;
         return pt;
     }
}
