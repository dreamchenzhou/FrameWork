package com.umframework.io;

import android.text.TextUtils;

/**
 * 常用类型数据转换
 * 
 * @author martin.zheng
 * 
 */
public class ConvertManager
{
	private ConvertManager()
	{

	}

	public static String toString(Object value)
	{
		return String.valueOf(value);
	}

	public static String toString(byte value)
	{
		return Byte.toString(value);
	}

	public static String toString(int value)
	{
		return Integer.toString(value);
	}

	public static String toString(long value)
	{
		return Long.toString(value);
	}

	public static String toString(float value)
	{
		return Float.toString(value);
	}

	public static String toString(double value)
	{
		return Double.toString(value);
	}

	public static byte toByte(String string)
	{
		if (!TextUtils.isEmpty(string))
		{
			return Byte.parseByte(string);
		}
		return Byte.MIN_VALUE;
	}

	public static int toInt(String string)
	{
		if (!TextUtils.isEmpty(string))
		{
			return Integer.parseInt(string);
		}
		return Integer.MIN_VALUE;
	}

	public static long toLong(String string)
	{
		if (!TextUtils.isEmpty(string))
		{
			return Long.parseLong(string);
		}
		return Long.MIN_VALUE;
	}

	public static float toFloat(String string)
	{
		if (!TextUtils.isEmpty(string))
		{
			return Float.parseFloat(string);
		}
		return Float.MIN_VALUE;
	}

	public static double toDouble(String string)
	{
		if (!TextUtils.isEmpty(string))
		{
			return Double.parseDouble(string);
		}
		return Double.MIN_VALUE;
	}
}
