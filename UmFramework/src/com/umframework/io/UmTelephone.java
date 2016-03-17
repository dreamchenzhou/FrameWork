//package com.umframework.io;
//
//import java.lang.reflect.Method;
//
//import android.content.Context;
//import android.telephony.TelephonyManager;
//import android.text.TextUtils;
//
//import com.android.internal.telephony.ITelephony;
//
///**
// * 对应 ITelephony
// * 
// * @author martin.zheng
// * 
// */
//public class UmTelephone
//{
//	private TelephonyManager telManager;
//	private ITelephony iTelephony;
//	private Context mContext;
//
//	private static Object lockValue = new Object();
//	private static UmTelephone instance = null;
//
//	public static UmTelephone getInstance(Context context)
//	{
//		if (instance == null)
//		{
//			synchronized (lockValue)
//			{
//				if (instance == null)
//				{
//					instance = new UmTelephone(context);
//				}
//			}
//		}
//		return instance;
//	}
//
//	public UmTelephone(Context context)
//	{
//		if (context != null)
//		{
//			this.mContext = context;
//			telManager = (TelephonyManager) this.mContext.getSystemService(Context.TELEPHONY_SERVICE);
//			Class<TelephonyManager> c = TelephonyManager.class;
//			try
//			{
//				Method methodEndCall = c.getDeclaredMethod("getITelephony", (Class[]) null);
//				methodEndCall.setAccessible(true);
//				iTelephony = (ITelephony) methodEndCall.invoke(telManager, (Object[]) null);
//			}
//			catch (Exception e)
//			{
//				e.printStackTrace();
//			}
//		}
//	}
//
//	/**
//	 * :获取当前设置的电话号码
//	 * 
//	 * @return
//	 */
//	public String getNativePhoneNumber()
//	{
//		String NativePhoneNumber = null;
//		NativePhoneNumber = telManager.getLine1Number();
//		return NativePhoneNumber;
//	}
//
//	/**
//	 * 调出拨号框拨打电话
//	 */
//	public void dial(String number)
//	{
//		if (iTelephony != null)
//		{
//			iTelephony.dial(number);
//		}
//	}
//
//	/**
//	 * 直接拨打电话
//	 */
//	public void call(String number)
//	{
//		if (iTelephony != null)
//		{
//			iTelephony.call(number);
//		}
//	}
//
//	/**
//	 * 挂断电话
//	 */
//	public void endCall()
//	{
//		if (iTelephony != null)
//		{
//			iTelephony.endCall();
//		}
//	}
//
//	/**
//	 * 手机设备IMEI，安放电池的地方可看到
//	 */
//	public String getIMEI()
//	{
//		if (telManager != null)
//		{
//			return telManager.getDeviceId();
//		}
//		return "";
//	}
//
//	/**
//	 * 手机IMSI，不可视
//	 */
//	public String getIMSI()
//	{
//		if (telManager != null)
//		{
//			return telManager.getSubscriberId();
//		}
//		return "";
//	}
//
//	/**
//	 * 手机卡串号，可在手机卡上的背面看到
//	 */
//	public String getSimSerialNumber()
//	{
//		if (telManager != null)
//		{
//			return telManager.getSimSerialNumber();
//		}
//		return "";
//	}
//
//	// /**
//	// * 当前手机卡号码
//	// */
//	// public String getCurNumber()
//	// {
//	// if (telManager != null)
//	// {
//	// return telManager.getLine1Number();
//	// }
//	// return "";
//	// }
//
//	/**
//	 * 46000,46002移动
//	 * 
//	 * 46001联通
//	 * 
//	 * 46003电信
//	 * 
//	 * @param context
//	 * @return
//	 */
//	public static int getSimOperator(Context context)
//	{
//		int result = -1;
//		if (context != null)
//		{
//			TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
//			if (tm != null && tm.getSimState() == TelephonyManager.SIM_STATE_READY)
//			{
//				String code = tm.getSimOperator();
//				if (!TextUtils.isEmpty(code))
//				{
//					result = Integer.parseInt(code);
//				}
//			}
//		}
//		return result;
//	}
//
//	/**
//	 * 手机卡类型：移动，联通，电信
//	 * 
//	 * @param context
//	 * @return
//	 */
//	public static String getSimOperatorText(Context context)
//	{
//		String result = "";
//		int value = getSimOperator(context);
//		if (value > -1)
//		{
//			switch (value)
//			{
//			case 46000:
//			case 46002:
//				result = "移动";
//				break;
//			case 46001:
//				result = "联通";
//				break;
//			case 46003:
//				result = "电信";
//				break;
//			}
//		}
//		return result;
//	}
// }
