package com.umframework.location.coords;

public class JMTransRegions implements ICoordTrans
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
            return new PointDElement((double)0.0, (double)0.0);
        }
    }

    public PointDElement XYToLonLan(PointDElement pt)
    {
        return null;
    }

    public static void BL2xy(double B, double L, TranItem item)
    {       	
        WGS84ToXiAn80_Quick(B, L, item);

        item.x80 = item.x80 - 4.6900831695646046;
        item.y80 = item.y80 + 500000 - 111.6122307786718;
    }

   
    private static void WGS84ToXiAn80_Quick(double B84, double L84, TranItem item)
    {        
        double j1 = L84 - (((int)L84 / 6 + 1) * 6 - 3);       
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
