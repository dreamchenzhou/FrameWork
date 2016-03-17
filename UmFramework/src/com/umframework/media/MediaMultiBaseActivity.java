package com.umframework.media;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;

/**
 * 连续拍照或者录像（基本）
 */
@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
@SuppressLint("HandlerLeak")
public class MediaMultiBaseActivity extends Activity
{
	public static final Uri uri_image = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
	public static final Uri uri_video = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
	public MediaMultiBaseActivity wThis = this;
	public String mLastImageId = null;
	public String mLastVideoId = null;

	public MediaItems items = new MediaItems();

	public Handler handler = new Handler()
	{
		@Override
		public void handleMessage(Message msg)
		{
			super.handleMessage(msg);
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		mLastImageId = MediaStoreManager.getLastMediaImageId(wThis);
		mLastVideoId = MediaStoreManager.getLastMediaVideoId(wThis);

		onCreate();
	}

	private void onCreate()
	{
		MediaStartUp.start(this);
		registerContentObserver();
	}

	@Override
	public void finish()
	{
		unregisterContentObserver();
		super.finish();
		if(items.size() > 0)
		{
			MediaManager.mediaCallback(false, items, null);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);

		finish();
	}

	public void unregisterContentObserver()
	{
		getContentResolver().unregisterContentObserver(observer);
		getContentResolver().unregisterContentObserver(observer);
	}

	public void registerContentObserver()
	{
		getContentResolver().registerContentObserver(uri_image, true, observer);
		getContentResolver().registerContentObserver(uri_video, true, observer);
	}

	private ContentObserver observer = new ContentObserver(handler)
	{
		// @Override
		// public void onChange(boolean selfChange)
		// {
		// super.onChange(selfChange);
		//
		// MediaItem item = null;
		//
		// String lastImageId = MediaStoreManager.getLastMediaImageId(wThis);
		// String lastVideoId = MediaStoreManager.getLastMediaVideoId(wThis);
		//
		// if (!mLastImageId.equals(lastImageId))
		// {
		// mLastImageId = lastImageId;
		// item = MediaStoreManager.getLastMediaImage(wThis);
		// }
		// else if (!mLastVideoId.equals(lastVideoId))
		// {
		// mLastVideoId = lastVideoId;
		// item = MediaStoreManager.getLastMediaVideo(wThis);
		// }
		// if (item != null && item.exists())
		// {
		// items.add(item);
		// }
		// }

		@Override
		public void onChange(boolean selfChange, Uri uri)
		{
			super.onChange(selfChange);

			MediaItem item = null;
			if(uri.equals(uri_image))
			{
				item = MediaStoreManager.getLastMediaImage(wThis);
			}
			else if(uri.equals(uri_video))
			{
				item = MediaStoreManager.getLastMediaVideo(wThis);
			}
			if(item != null && item.exists())
			{
				items.add(item);
			}
		}

		@Override
		public boolean deliverSelfNotifications()
		{
			// TODO 自动生成的方法存根
			return super.deliverSelfNotifications();
		}

		@Override
		public void onChange(boolean selfChange)
		{
			// TODO 自动生成的方法存根
			super.onChange(selfChange);

			MediaItem item = null;
			item = MediaStoreManager.getLastMediaImage(wThis);

			if(item == null)
			{
				item = MediaStoreManager.getLastMediaVideo(wThis);
			}

			if(item != null && item.exists())
			{
				items.add(item);
			}
		}
	};
}