package com.umframework.media;

import java.io.File;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.cache.disc.DiskCache;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.umframework.R;

public class UmImageLoader
{
	// String imageUri = "http://site.com/image.png"; // from Web

	// String imageUri = "file:///mnt/sdcard/image.png"; // from SD card

	// String imageUri = "content://media/external/audio/albumart/13"; // from
	// content provider

	// String imageUri = "assets://image.png"; // from assets

	// String imageUri = "drawable://" + R.drawable.image; // from drawables
	// (only images, non-9patch)

	/**
	 * 默认显示图片
	 */
	public static int defaultImageId = R.drawable.post_image_loding;
	public static int defaultImageFailId = R.drawable.post_image_loading_failed;
	public static int durationMillis = 500;
	public static int maxImageWidth = 0;
	public static int maxImageHeight = 0;
	public static int compressQuality = 75;

	public static void init(Context context)
	{
		init(context, null);
	}

	public static void init(Context context, String cacheDir)
	{
		com.nostra13.universalimageloader.core.ImageLoaderConfiguration.Builder builder = new ImageLoaderConfiguration.Builder(context);
		builder = builder.threadPriority(Thread.NORM_PRIORITY - 2);
		builder = builder.denyCacheImageMultipleSizesInMemory();
		builder = builder.discCacheFileNameGenerator(new Md5FileNameGenerator());
		// builder = builder.discCacheFileNameGenerator(new
		// HashCodeFileNameGenerator());

		builder = builder.memoryCache(new LruMemoryCache(8 * 1024 * 1024));
		builder = builder.memoryCacheSize(8 * 1024 * 1024);
		builder = builder.discCacheSize(8 * 1024 * 1024);
		builder = builder.discCacheFileCount(64);

		builder = builder.tasksProcessingOrder(QueueProcessingType.LIFO);

		// if (maxImageWidth > 0 && maxImageHeight > 0)
		// {
		// builder = builder.memoryCacheExtraOptions(maxImageWidth,
		// maxImageHeight);
		// builder = builder.discCacheExtraOptions(maxImageWidth,
		// maxImageHeight, CompressFormat.JPEG, compressQuality, null);
		// }

		if(!TextUtils.isEmpty(cacheDir))
		{
			StorageUtils.getOwnCacheDirectory(context, cacheDir);
			// builder = builder.discCache(new UnlimitedDiscCache(new
			// File(cacheDir)));
			builder = builder.diskCache(new UnlimitedDiskCache(new File(cacheDir)));
		}
		// final int size = 64 * 1024;
		//
		// builder = builder.imageDownloader(new ImageDownloader()
		// {
		// @Override
		// public InputStream getStream(String imageUri, Object extra) throws
		// IOException
		// {
		// String filePath = imageUri.replace("file://", "");
		// return new BufferedInputStream(new FileInputStream(filePath), size);
		// }
		// });

		ImageLoaderConfiguration config = builder.build();
		ImageLoader.getInstance().init(config);
	}

	// public static DisplayImageOptions getDisplayImageOptions()
	// {
	// return getDisplayImageOptions(false, true);
	// }

	public static DisplayImageOptions getDisplayImageOptions(boolean cacheInMemory, boolean cacheOnDisc)
	{
		if(defaultImageFailId == 0)
		{
			defaultImageFailId = defaultImageId;
		}

		com.nostra13.universalimageloader.core.DisplayImageOptions.Builder builder = new DisplayImageOptions.Builder();
		builder = builder.showImageForEmptyUri(defaultImageId);
		builder = builder.showImageOnFail(defaultImageFailId);
		builder = builder.showImageOnLoading(defaultImageId);
		builder = builder.cacheInMemory(cacheInMemory);
		builder = builder.cacheOnDisc(cacheOnDisc);
		// builder = builder.imageScaleType(imageScaleType);

		// builder = builder.displayer(new RoundedBitmapDisplayer(10));

		DisplayImageOptions mDisplayImageOptions = builder.build();
		return mDisplayImageOptions;
	}

	private static ImageLoader getImageLoader()
	{
		return ImageLoader.getInstance();
	}

	public static void onDestroy()
	{
		ImageLoader mImageLoader = getImageLoader();
		if(mImageLoader != null)
		{
			mImageLoader.clearMemoryCache();
			mImageLoader.clearDiscCache();
		}

		for (int i = 0; i < 3; i++)
		{
			System.gc();
		}
	}

	/**
	 * 获取缓存文件路径
	 * 
	 * @param uri
	 * @return
	 */
	public static String getCachePath(String uri)
	{
		// DiscCacheAware discCache = getImageLoader().getDiscCache();
		DiskCache discCache = getImageLoader().getDiskCache();
		File imageFile = discCache.get(uri);
		String path = imageFile.getAbsolutePath();
		return path;
	}

	/**
	 * 获取缓存文件路径
	 * 
	 * @param uri
	 * @return
	 */
	public static File getCacheFile(String uri)
	{
		// DiscCacheAware discCache = getImageLoader().getDiscCache();
		DiskCache discCache = getImageLoader().getDiskCache();
		File imageFile = discCache.get(uri);
		return imageFile;
	}

	static DisplayImageOptions mDisplayImageOptions = null;

	// static ImageLoadingListenerImpl mImageLoadingListenerImpl = null;

	public static void displayImage(String uri, ImageView imageView, boolean cacheInMemory, boolean cacheOnDisc)
	{
		if(mDisplayImageOptions == null)
		{
			mDisplayImageOptions = getDisplayImageOptions(cacheInMemory, cacheOnDisc);
		}
		// if (mImageLoadingListenerImpl == null)
		// {
		// mImageLoadingListenerImpl = new ImageLoadingListenerImpl();
		// }

		getImageLoader().displayImage(uri, imageView, mDisplayImageOptions, null);
	}

	public static class ImageLoadingListenerImpl extends SimpleImageLoadingListener
	{
		public static final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());

		@Override
		public void onLoadingComplete(String imageUri, View view, Bitmap bitmap)
		{
			// if (bitmap != null)
			// {
			// ImageView imageView = (ImageView) view;
			// boolean isFirstDisplay = !displayedImages.contains(imageUri);
			// if (isFirstDisplay)
			// {
			// // 图片的淡入效果
			// FadeInBitmapDisplayer.animate(imageView, durationMillis);
			// displayedImages.add(imageUri);
			// }
			// }
		}
	}
}
