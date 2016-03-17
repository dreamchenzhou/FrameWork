package com.umframeworkdemo.utils;

import android.content.Context;
import android.util.Log;

public class Storage
{
	public static final int SEEK_CUR = 1;
	public static final int SEEK_END = 2;
	public static final int SEEK_SET = 0;
	public static final int TEE_DATA_FLAG_ACCESS_READ = 1;
	public static final int TEE_DATA_FLAG_ACCESS_WRITE = 2;
	public static final int TEE_DATA_FLAG_ACCESS_WRITE_META = 4;
	public static final int TEE_DATA_FLAG_CREATE = 512;
	public static final int TEE_DATA_FLAG_EXCLUSIVE = 1024;
	public static final int TEE_DATA_FLAG_SHARE_READ = 16;
	public static final int TEE_DATA_FLAG_SHARE_WRITE = 32;
	private static final String TEE_PERSIST_STORAGE_DIR = "sec_storage";
	private static final String TEE_TEMP_STORAGE_DIR = "sec_storage_data";
	private final String TAG = "TrustZoneStorage";
	private String mPkgName = null;

	static
	{
		System.load("/system/lib/libtrustzone_storage.so");
	}

	public Storage(Context paramContext)
	{
		this.mPkgName = paramContext.getApplicationContext().getPackageName();
	}

	private native int _close(int paramInt);

	private native int _getErr();

	private native int _getSize(int paramInt);

	private native int _init();

	private native int _open(String paramString, int paramInt);

	private native int _read(int paramInt1, byte[] paramArrayOfByte, int paramInt2);

	private native int _remove(String paramString);

	private native int _seek(int paramInt1, int paramInt2, int paramInt3);

	private native int _sync(int paramInt);

	private native int _uninit();

	private native int _write(int paramInt1, byte[] paramArrayOfByte, int paramInt2);

	private String constructPath(String paramString, boolean paramBoolean)
	{
		if ((paramString == null) || (paramString.length() == 0))
		{
			Log.e("TrustZoneStorage", "constructPath,invalid original path");
			return null;
		}
		if ((this.mPkgName == null) || (this.mPkgName.length() == 0))
		{
			Log.e("TrustZoneStorage", "constructPath,invalid package name");
			return null;
		}
		if (paramBoolean)
			;
		for (String str1 = "sec_storage";; str1 = "sec_storage_data")
		{
			String str2 = str1 + "/" + this.mPkgName + "/" + paramString;
			Log.i("TrustZoneStorage", "constructPath:path = " + str2);
			return str2;
		}
	}

	public int close(int paramInt)
	{
		return _close(paramInt);
	}

	public int getErr()
	{
		return _getErr();
	}

	public int getSize(int paramInt)
	{
		return _getSize(paramInt);
	}

	public int init()
	{
		return _init();
	}

	public int open(String paramString, int paramInt)
	{
		String str = constructPath(paramString, false);
		if (str == null)
			return -1;
		return _open(str, paramInt);
	}

	public int open(String paramString, int paramInt, boolean paramBoolean)
	{
		String str = constructPath(paramString, paramBoolean);
		if (str == null)
			return -1;
		return _open(str, paramInt);
	}

	public int read(int paramInt1, byte[] paramArrayOfByte, int paramInt2)
	{
		return _read(paramInt1, paramArrayOfByte, paramInt2);
	}

	public int remove(String paramString)
	{
		String str = constructPath(paramString, false);
		if (str == null)
			return -1;
		return _remove(str);
	}

	public int remove(String paramString, boolean paramBoolean)
	{
		String str = constructPath(paramString, paramBoolean);
		if (str == null)
			return -1;
		return _remove(str);
	}

	public int seek(int paramInt1, int paramInt2, int paramInt3)
	{
		return _seek(paramInt1, paramInt2, paramInt3);
	}

	public int sync(int paramInt)
	{
		return _sync(paramInt);
	}

	public int uninit()
	{
		return _uninit();
	}

	public int write(int paramInt1, byte[] paramArrayOfByte, int paramInt2)
	{
		return _write(paramInt1, paramArrayOfByte, paramInt2);
	}
}
