package com.umframework.location.coords;

public interface ICoordTrans
{
	 PointDElement LonLanToXY(PointDElement pt);
     PointDElement XYToLonLan(PointDElement pt);
}
