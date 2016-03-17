package com.umframework.location.coords;

public class DegreesMinutesSeconds
{
    int _degrees;
    /// <summary>
    /// The degrees unit of the coordinate
    /// </summary>
    public int Degrees()
    {
    	return _degrees;
    }

    int _minutes;
    /// <summary>
    /// The minutes unit of the coordinate
    /// </summary>
    public int Minutes()
    {
    	return _minutes;
    }

    double _seconds;
    /// <summary>
    /// The seconds unit of the coordinate
    /// </summary>
    public double Seconds()
    {
    	return _seconds;
    }

    /// <summary>
    /// Constructs a new instance of DegreesMinutesSeconds converting 
    /// from decimal degrees
    /// </summary>
    /// <param name="decimalDegrees">Initial value as decimal degrees</param>
    public DegreesMinutesSeconds(double decimalDegrees)
    {
        _degrees = (int)decimalDegrees;
        
        double doubleMinutes = (Math.abs(decimalDegrees) - Math.abs((double)_degrees)) * 60.0;

        _minutes = (int)doubleMinutes;
        _seconds = (doubleMinutes - (double)_minutes) * 60.0;
    }

    /// <summary>
    /// Constructs a new instance of DegreesMinutesSeconds converting 
    /// from degrees,如 11359.3902，表示 113°59' (0.3902 * 60)" = 113°59' 23"
    //  ddmm.mmmm（度分）格式
    //  11359.3902，表示 113°59' (0.3902 * 60)" = 113°59' 23"
    //  2239.3902，表示 22°39' (0.3902 * 60)" = 22°39' 23"
    /// </summary>
    /// <param name="decimalDegrees">Initial value as decimal degrees</param>
    public DegreesMinutesSeconds(double dDegrees, boolean NoUseParam)
    {
        double degreestmp = (dDegrees / 100.0);
        double minutestmp = (Math.abs(degreestmp) - Math.abs((double)(int)(degreestmp))) * 100;
        double secondstmp = (Math.abs(dDegrees) - Math.abs((double)(int)dDegrees)) * 60.0;

        _degrees = (int)degreestmp;
        _minutes = (int)minutestmp;
        _seconds = secondstmp;
    }

    /// <summary>
    /// Constructs a new instance of DegreesMinutesSeconds
    /// </summary>
    /// <param name="degrees">Degrees unit of the coordinate</param>
    /// <param name="minutes">Minutes unit of the coordinate</param>
    /// <param name="seconds">Seconds unit of the coordinate</param>
    public DegreesMinutesSeconds(int degrees, int minutes, double seconds)
    {
        this._degrees = degrees;
        this._minutes = minutes;
        this._seconds = seconds;
    }

    /// <summary>
    /// Converts the decimal, minutes, seconds coordinate to 
    /// decimal degrees
    /// </summary>
    /// <returns></returns>
    public double ToDecimalDegrees()
    {
        double val = (double)_degrees + ((double)_minutes / 60.0) + ((double)_seconds / 3600.0);
        return val;
    }

    /// <summary>
    /// Converts the instance to a string in format: D M' S"
    /// </summary>
    /// <returns>string representation of degrees, minutes, seconds</returns>
//    public override string ToString()
//    {
//        return _degrees + "d " + _minutes + "' " + _seconds + "\"";
//    }
}