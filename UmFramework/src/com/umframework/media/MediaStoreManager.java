package com.umframework.media;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;

/**
 * 
 * @author martin.zheng
 * 
 */
@SuppressWarnings("deprecation")
public class MediaStoreManager
{
	public static final Uri uri_image_EXTERNAL = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
	public static final Uri uri_video_EXTERNAL = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
	public static final Uri uri_audio_EXTERNAL = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;

	public static final Uri uri_image_INTERNAL = MediaStore.Images.Media.INTERNAL_CONTENT_URI;
	public static final Uri uri_video_INTERNAL = MediaStore.Video.Media.INTERNAL_CONTENT_URI;
	public static final Uri uri_audio_INTERNAL = MediaStore.Audio.Media.INTERNAL_CONTENT_URI;

	public static final String[] projection_show = new String[]
	{ "_id", "_data", "_display_name", };

	public static String getMediaImageId(Activity activity, String displayName)
	{
		return getMediaId(activity, uri_image_EXTERNAL, displayName);
	}

	public static String getMediaVideoId(Activity activity, String displayName)
	{
		return getMediaId(activity, uri_video_EXTERNAL, displayName);
	}

	public static String getMediaAudioId(Activity activity, String displayName)
	{
		return getMediaId(activity, uri_audio_EXTERNAL, displayName);
	}

	public static String getMediaId(Activity activity, Uri uri, String displayName)
	{
		String id = null;
		String[] projection = new String[]
		{ "_id" };
		String selection = "_display_name = ?";
		String[] selectionArgs = new String[]
		{ displayName };
		String sortOrder = "_id desc";

		Cursor cursor = activity.managedQuery(uri, projection, selection, selectionArgs, sortOrder);
		if (cursor != null && cursor.getCount() > 0)
		{
			cursor.moveToFirst();
			id = cursor.getString(0);
		}
		return id;
	}

	public static MediaItem getLastMediaImage(Activity activity)
	{
		return getLastMedia(activity, uri_image_EXTERNAL);
	}

	public static MediaItem getLastMediaVideo(Activity activity)
	{
		return getLastMedia(activity, uri_video_EXTERNAL);
	}

	public static MediaItem getLastMediaAudio(Activity activity)
	{
		return getLastMedia(activity, uri_audio_EXTERNAL);
	}

	public static MediaItem getLastMedia(Activity activity, Uri uri)
	{
		MediaItem item = null;

		String[] projection = projection_show;
		String selection = null;
		String[] selectionArgs = null;
		String sortOrder = " _id desc ";

		Cursor cursor = activity.managedQuery(uri, projection, selection, selectionArgs, sortOrder);
		if (cursor != null && cursor.getCount() > 0)
		{
			item = new MediaItem();

			cursor.moveToFirst();
			String id = cursor.getString(0);
			String path = cursor.getString(1);
			item.setId(id);
			item.setPath(path);

			if (uri.equals(uri_image_EXTERNAL))
			{
				item.setMediaType(MediaTypeEnum.image);
			}
			else if (uri.equals(uri_video_EXTERNAL))
			{
				item.setMediaType(MediaTypeEnum.video);
			}
			else if (uri.equals(uri_audio_EXTERNAL))
			{
				item.setMediaType(MediaTypeEnum.audio);
			}
		}
		return item;
	}

	public static MediaItem getMediaById(Activity activity, Uri uri, String mediaId)
	{
		MediaItem item = null;

		String[] projection = projection_show;
		String selection = "_id = ? ";
		String[] selectionArgs = new String[]
		{ mediaId };
		String sortOrder = " _id desc ";

		Cursor cursor = activity.managedQuery(uri, projection, selection, selectionArgs, sortOrder);
		if (cursor != null && cursor.getCount() > 0)
		{
			item = new MediaItem();

			cursor.moveToFirst();
			String id = cursor.getString(0);
			String path = cursor.getString(1);
			item.setId(id);
			item.setPath(path);

			if (uri.equals(uri_image_EXTERNAL))
			{
				item.setMediaType(MediaTypeEnum.image);
			}
			else if (uri.equals(uri_video_EXTERNAL))
			{
				item.setMediaType(MediaTypeEnum.video);
			}
			else if (uri.equals(uri_audio_EXTERNAL))
			{
				item.setMediaType(MediaTypeEnum.audio);
			}
		}
		return item;
	}

	public static String getLastMediaImageId(Activity activity)
	{
		return getLastMediaId(activity, uri_image_EXTERNAL);
	}

	public static String getLastMediaVideoId(Activity activity)
	{
		return getLastMediaId(activity, uri_video_EXTERNAL);
	}

	public static String getLastMediaAudioId(Activity activity)
	{
		return getLastMediaId(activity, uri_audio_EXTERNAL);
	}

	public static String getLastMediaId(Activity activity, Uri uri)
	{
		String id = null;

		String[] projection = new String[]
		{ "_id" };
		String selection = null;
		String[] selectionArgs = null;
		String sortOrder = " _id desc ";

		Cursor cursor = activity.managedQuery(uri, projection, selection, selectionArgs, sortOrder);
		if (cursor != null && cursor.getCount() > 0)
		{
			cursor.moveToFirst();
			id = cursor.getString(0);
		}
		return id;
	}

	public static void deleteMediaImageById(Activity mContext, String id)
	{
		deleteMedia(mContext, uri_image_EXTERNAL, id, null);
	}

	public static void deleteMediaImageByName(Activity mContext, String dispalyName)
	{
		deleteMedia(mContext, uri_image_EXTERNAL, null, dispalyName);
	}

	public static void deleteMediaVideoById(Activity mContext, String id)
	{
		deleteMedia(mContext, uri_video_EXTERNAL, id, null);
	}

	public static void deleteMediaVideoByName(Activity mContext, String dispalyName)
	{
		deleteMedia(mContext, uri_video_EXTERNAL, null, dispalyName);
	}

	public static void deleteMediaAudioById(Activity mContext, String id)
	{
		deleteMedia(mContext, uri_audio_EXTERNAL, id, null);
	}

	public static void deleteMediaAudioByName(Activity mContext, String dispalyName)
	{
		deleteMedia(mContext, uri_audio_EXTERNAL, null, dispalyName);
	}

	public static void deleteMedia(Activity mContext, Uri uri, String id, String displayName)
	{
		try
		{
			if (TextUtils.isEmpty(id) || TextUtils.isEmpty(displayName))
			{
				String where = null;
				String[] selectionArgs = null;

				boolean isContinue = false;

				if (!TextUtils.isEmpty(id))
				{
					where = " _id =? ";
					selectionArgs = new String[]
					{ id };
					isContinue = true;
				}
				else if (!TextUtils.isEmpty(displayName))
				{
					where = " _display_name =? ";
					selectionArgs = new String[]
					{ displayName };
					isContinue = true;
				}
				if (isContinue)
				{
					ContentResolver cr = mContext.getContentResolver();
					cr.delete(uri, where, selectionArgs);
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
