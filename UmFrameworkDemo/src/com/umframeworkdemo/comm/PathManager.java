package com.umframeworkdemo.comm;

import java.io.File;

import android.content.Context;

import com.umframework.io.FileManager;
import com.umframework.io.SdCardManager;

public class PathManager
{
	public static String sdcard;
	public static String startup;
	public static String nape = "umdemo";

	private PathManager()
	{

	}

	public static void init(Context context)
	{
		String[] strs = SdCardManager.init(context, nape);
		if(strs != null && strs.length >= 2)
		{
			sdcard = strs[0];
			startup = strs[1];
		}
	}

	public static String getImage()
	{
		String path = FileManager.mkdirs(startup, "image");
		if(path == null)
		{
			path = startup + "image";
		}
		check(path);
		return path;
	}

	public static void check(String path)
	{
		File file = new File(path);
		if(!file.exists())
		{
			file.mkdirs();
		}
	}

	public static String getVideo()
	{
		String path = FileManager.mkdirs(startup, "video");
		return path;
	}

	public static String getAudio()
	{
		String path = FileManager.mkdirs(startup, "audio");
		return path;
	}
}