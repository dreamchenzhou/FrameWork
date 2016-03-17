package com.umframework.media;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Matrix;
import android.media.ExifInterface;

public class MediaImage
{
	/**
	 * 根据指定的保存质量，指定的宽高计算压缩比率
	 * 
	 * @param mediaRatio
	 * @param path
	 * @return
	 */
	public static void compress(MediaOption mediaOption, String path)
	{
		compress(mediaOption, new File(path));
	}

	/**
	 * 根据指定的保存质量，指定的宽高计算压缩比率
	 * 
	 * @param mediaRatio
	 * @param file
	 * @return
	 */
	public static void compress(MediaOption mediaOption, File file)
	{
		try
		{
			if (!mediaOption.isCompress())
			{
				return;
			}

			Bitmap bitmap;
			FileOutputStream stream;

			Options opts = new Options();
			opts.inJustDecodeBounds = true;
			bitmap = BitmapFactory.decodeFile(file.getAbsolutePath(), opts);
			opts.inJustDecodeBounds = false;

			float val = 0.0f;
			if (opts.outWidth < opts.outHeight)
			{
				val = opts.outWidth / (float) mediaOption.getWidth();
			}
			else
			{
				val = opts.outWidth / (float) mediaOption.getHeight();
			}

			if (val < 1)
			{
				val = 1;
			}

			BigDecimal bigDecimal = new BigDecimal(String.valueOf(val)).setScale(0, BigDecimal.ROUND_HALF_UP);
			int be = Integer.parseInt(String.valueOf(bigDecimal));
			opts.inSampleSize = be;

			bitmap = BitmapFactory.decodeFile(file.getAbsolutePath(), opts);

			stream = new FileOutputStream(file.getAbsoluteFile());
			bitmap.compress(Bitmap.CompressFormat.JPEG, mediaOption.getQuality(), stream);

			stream.flush();
			stream.close();
			stream = null;

			if (bitmap != null)
			{
				bitmap.recycle();
				bitmap = null;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * 图片压缩为指定宽、高、质量
	 * 
	 * @param mediaRatio
	 * @param path
	 */
	public static void createScaledBitmap(MediaOption mediaOption, String path)
	{
		createScaledBitmap(mediaOption, new File(path));
	}

	/**
	 * 图片压缩为指定宽、高、质量
	 * 
	 * @param mediaRatio
	 * @param file
	 */
	public static void createScaledBitmap(MediaOption mediaOption, File file)
	{
		try
		{
			if (!mediaOption.isCompress())
			{
				return;
			}

			Bitmap bitmap = null;
			FileOutputStream stream = null;

			Options opts = new Options();
			opts.inSampleSize = 2;
			bitmap = BitmapFactory.decodeFile(file.getAbsolutePath(), opts);

			int dstWidth = mediaOption.getWidth();
			int dstHeight = mediaOption.getHeight();

			if (bitmap.getWidth() > bitmap.getHeight())
			{
				dstWidth = mediaOption.getHeight();
				dstHeight = mediaOption.getWidth();
			}

			bitmap = Bitmap.createScaledBitmap(bitmap, dstWidth, dstHeight, false);

			stream = new FileOutputStream(file.getAbsoluteFile());
			bitmap.compress(Bitmap.CompressFormat.JPEG, mediaOption.getQuality(), stream);
			bitmap.recycle();
			stream.flush();
			stream.close();

			if (bitmap != null)
			{
				bitmap.recycle();
				bitmap = null;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static Bitmap postRotate(Bitmap bitmap, File file)
	{
		return postRotate(bitmap, file.getAbsoluteFile());
	}

	public static Bitmap postRotate(Bitmap bitmap, String path)
	{
		try
		{
			ExifInterface exif = new ExifInterface(path);
			String orientation = exif.getAttribute(ExifInterface.TAG_ORIENTATION);
			int orientationValue = Integer.parseInt(orientation);

			Matrix matrix = new Matrix();
			switch (orientationValue)
			{
			case 6:// 原始图片向左
				matrix.postRotate(90);
				bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
				break;
			case 8:// 原始图片向右
				matrix.postRotate(-90);
				bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
				break;
			default:
				break;
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return bitmap;
	}
}
