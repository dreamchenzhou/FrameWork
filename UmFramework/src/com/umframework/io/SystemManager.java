package com.umframework.io;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.UUID;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import dalvik.system.DexFile;

/**
 * 
 * @author martin.zheng
 * 
 */
public class SystemManager
{
	public static final String EMPTY_UUID_TEXT = "00000000-0000-0000-0000-000000000000";
	public static final UUID EMPTY_UUID = UUID.fromString(EMPTY_UUID_TEXT);

	private SystemManager()
	{

	}

	public static boolean isEmptyUUID(UUID uuid)
	{
		if (uuid != null)
		{
			return uuid.equals(EMPTY_UUID);
		}
		return true;
	}

	public static boolean isEmptyUUID(String uuid)
	{
		if (!TextUtils.isEmpty(uuid))
		{
			UUID id = UUID.fromString(uuid);
			return id.equals(EMPTY_UUID);
		}
		return true;
	}

	public static UUID emptyUUID()
	{
		UUID uuid = UUID.fromString(EMPTY_UUID_TEXT);
		return uuid;
	}

	public static void exit()
	{
		exit(0);
	}

	public static void exit(int code)
	{
		System.exit(code);
	}

	public static void killProcess()
	{
		killProcess(android.os.Process.myPid());
	}

	public static void killProcess(int pid)
	{
		android.os.Process.killProcess(pid);
	}

	/**
	 * 屏幕分辨率：像素 widthPixels，heightPixels
	 * 
	 * @param context
	 * @return
	 */
	public static DisplayMetrics getDisplayMetrics(Activity context)
	{
		DisplayMetrics displayMetrics = new DisplayMetrics();
		context.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
		return displayMetrics;
	}

	/**
	 * 屏幕分辨率：像素 getWidth()，getHeight()
	 * 
	 * @param context
	 * @return
	 */
	public static Display getDisplay(Activity context)
	{
		Display display = context.getWindowManager().getDefaultDisplay();
		return display;
	}

	public static int[] getLocationInWindow(View view)
	{
		int[] location = new int[2];
		view.getLocationInWindow(location);
		return location;
	}

	public static int[] getLocationOnScreen(View view)
	{
		int[] location = new int[2];
		view.getLocationOnScreen(location);
		return location;
	}

	/**
	 * 返回手机主界面
	 * 
	 * @param context
	 */
	public static void onBackHome(Context context)
	{
		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.addCategory(Intent.CATEGORY_HOME);
		context.startActivity(intent);
	}

	public static Object readData(String path) throws StreamCorruptedException, FileNotFoundException, IOException, ClassNotFoundException
	{
		return readData(new File(path));
	}

	public static Object readData(File file) throws StreamCorruptedException, FileNotFoundException, IOException, ClassNotFoundException
	{
		Object obj = null;
		ObjectInputStream objIn = new ObjectInputStream(new FileInputStream(file));
		obj = objIn.readObject();
		objIn.close();
		return obj;
	}

	public static void writeData(Object obj, String path) throws FileNotFoundException, IOException
	{
		writeData(obj, new File(path));
	}

	public static void writeData(Object obj, File file) throws FileNotFoundException, IOException
	{
		ObjectOutputStream objOut = new ObjectOutputStream(new FileOutputStream(file));
		objOut.writeObject(obj);
		objOut.flush();
		objOut.close();
	}

	/**
	 * 判断手机是否ROOT
	 */
	public static boolean isRoot()
	{
		boolean root = false;

		try
		{
			if ((!new File("/system/bin/su").exists()) && (!new File("/system/xbin/su").exists()))
			{
				root = false;
			}
			else
			{
				root = true;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return root;
	}

	/**
	 * 向ROOT权限发送请求信息,以获取ROOT权限 应用程序运行命令获取 Root权限，设备必须已破解(获得ROOT权限) 此方法不涉及底层,
	 * 这种方式需要用户点击确认才可以获取.
	 * 
	 * @param command
	 *            包名 命令： String apkRoot="chmod 777 "+getPackageCodePath();
	 *            RootCommand(apkRoot);
	 * 
	 *            // 返回系统包名 String apkRoot = "chmod 777 " +
	 *            Activity.getPackageCodePath(); RootCommand(apkRoot);
	 * 
	 * 
	 * @return 应用程序是/否获取Root权限
	 */
	public static boolean RootCommand(String command)
	{
		Process process = null;
		DataOutputStream os = null;

		try
		{
			process = Runtime.getRuntime().exec("su");
			os = new DataOutputStream(process.getOutputStream());
			os.writeBytes(command + "\n");
			os.writeBytes("exit\n");
			os.flush();
			process.waitFor();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
		finally
		{
			try
			{
				if (os != null)
				{
					os.close();
				}
				process.destroy();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return true;
	}

	/**
	 * 获取应用程序所有类
	 * 
	 * @param context
	 * @return
	 */
	public static ArrayList<Class<?>> getDexFile(Context context)
	{
		ArrayList<Class<?>> objects = new ArrayList<Class<?>>();

		try
		{
			String path = context.getPackageManager().getApplicationInfo(context.getPackageName(), 0).sourceDir;
			DexFile dexfile = new DexFile(path);
			Enumeration<String> entries = dexfile.entries();

			while (entries.hasMoreElements())
			{
				String className = (String) entries.nextElement();
				if (!TextUtils.isEmpty(className))
				{
					try
					{
						Class<?> cls = Class.forName(className);
						objects.add(cls);
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return objects;
	}
}
