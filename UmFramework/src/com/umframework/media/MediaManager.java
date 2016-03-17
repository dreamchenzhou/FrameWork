package com.umframework.media;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.umframework.io.FileManager;
import com.umframework.io.SdCardManager;

@SuppressLint(
{ "SimpleDateFormat", "DefaultLocale" })
public class MediaManager
{
	private static final String pattern = "yyyyMMdd_HHmmss";
	private static final SimpleDateFormat sdf = new SimpleDateFormat(pattern);

	private static MediaCallback mediaCallback = null;

	public static MediaOption mediaOption = new MediaOption();
	public static MediaWorkEnum mediaWorkEnum = MediaWorkEnum.nothing;

	/**
	 * 根据指定的宽高计算压缩比率，默认为240*320,90
	 * 
	 * @param width
	 * @param height
	 */
	public static void setMediaOption(int width, int height, int quality)
	{
		mediaOption.setWidth(width);
		mediaOption.setHeight(height);
		mediaOption.setQuality(quality);
	}

	public static void setMediaOption(int width, int height)
	{
		mediaOption.setWidth(width);
		mediaOption.setHeight(height);
	}

	public static void setMediaOption(int quality)
	{
		mediaOption.setQuality(quality);
	}

	/**
	 * 是否压缩图片
	 */
	public static void setMediaOption(boolean isCompress)
	{
		mediaOption.setCompress(isCompress);
	}

	/**
	 * 独立拍照
	 * 
	 * @param context
	 */
	public static void singleImage(Context context, MediaCallback callback)
	{
		mediaWorkEnum = MediaWorkEnum.single_image;
		mediaCallback = callback;
		Intent intent = new Intent();
		intent.setClass(context, MediaSingleActivity.class);
		context.startActivity(intent);
	}

	/**
	 * 独立录像
	 * 
	 * @param context
	 */
	public static void singleVideo(Context context, MediaCallback callback)
	{
		mediaWorkEnum = MediaWorkEnum.single_video;
		mediaCallback = callback;
		Intent intent = new Intent();
		intent.setClass(context, MediaSingleActivity.class);
		context.startActivity(intent);
	}

	/**
	 * 独立录音
	 * 
	 * @param context
	 */
	public static void singleAudio(Context context, MediaCallback callback)
	{
		mediaWorkEnum = MediaWorkEnum.single_audio;
		mediaCallback = callback;
		Intent intent = new Intent();
		intent.setClass(context, MediaSingleActivity.class);
		context.startActivity(intent);
	}

	/**
	 * 连续拍照或者连续录像
	 * 
	 * @param context
	 */
	public static void multiCamera(Context context, MediaCallback callback)
	{
		mediaWorkEnum = MediaWorkEnum.multi_camera;
		mediaCallback = callback;
		Intent intent = new Intent();
		intent.setClass(context, MediaMultiActivity.class);
		context.startActivity(intent);
	}

	public static void mediaCallback(boolean isSingle, MediaItems items, MediaItem item)
	{
		if (mediaCallback != null)
		{
			mediaCallback.mediaCallback(isSingle, items, item);
			mediaCallback = null;
		}
	}

	public static String getTempDirectory()
	{
		String path = SdCardManager.sdCardPath();
		path = path + "/" + "TempDirectory";
		FileManager.mkdirs(path);
		return path;
	}

	public static void clearTempDirectory(String path)
	{
		FileManager.clear(path);
	}

	public static void clearTempDirectory()
	{
		String path = getTempDirectory();
		clearTempDirectory(path);
	}

	public static String getMediaName()
	{
		Date date = new Date();
		String name = sdf.format(date);
		return name;
	}

	/**
	 * 调用系统预览(图片，音频，视频等)
	 */
	public static void preview(Context context, String path)
	{
		preview(context, new File(path));
	}

	/**
	 * 调用系统预览(图片，音频，视频等)
	 */
	public static void preview(Context context, File file)
	{
		Intent intent = getFileIntent(file);
		context.startActivity(intent);
	}

	public static Intent getFileIntent(String path)
	{
		return getFileIntent(new File(path));
	}

	public static Intent getFileIntent(File file)
	{
		// Uri uri = Uri.parse("http://m.ql18.com.cn/hpf10/1.pdf");
		Uri uri = Uri.fromFile(file);
		String type = getMIMEType(file);
		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setDataAndType(uri, type);
		return intent;
	}

	public static String getMIMEType(String path)
	{
		return getMIMEType(new File(path));
	}

	public static String getMIMEType(File file)
	{
		String type = "";
		String fName = file.getName();
		/* 取得扩展名 */
		String end = fName.substring(fName.lastIndexOf(".") + 1, fName.length()).toLowerCase();

		/* 依扩展名的类型决定MimeType */
		if (end.equals("pdf"))
		{
			type = "application/pdf";//
		}
		else if (end.equals("m4a") || end.equals("mp3") || end.equals("mid") || end.equals("xmf") || end.equals("ogg") || end.equals("wav"))
		{
			type = "audio/*";
		}
		else if (end.equals("3gp") || end.equals("mp4"))
		{
			type = "video/*";
		}
		else if (end.equals("jpg") || end.equals("gif") || end.equals("png") || end.equals("jpeg") || end.equals("bmp"))
		{
			type = "image/*";
		}
		else if (end.equals("apk"))
		{
			/* android.permission.INSTALL_PACKAGES */
			type = "application/vnd.android.package-archive";
		}
		else if (end.equals("pptx") || end.equals("ppt"))
		{
			type = "application/vnd.ms-powerpoint";
		}
		else if (end.equals("docx") || end.equals("doc"))
		{
			type = "application/vnd.ms-word";
		}
		else if (end.equals("xlsx") || end.equals("xls"))
		{
			type = "application/vnd.ms-excel";
		}
		else
		{
			// /*如果无法直接打开，就跳出软件列表给用户选择 */
			type = "*/*";
		}
		return type;
	}
}
