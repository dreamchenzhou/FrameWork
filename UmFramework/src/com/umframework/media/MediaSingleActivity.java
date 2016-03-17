package com.umframework.media;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.MediaStore.Audio.Media;

/**
 * 启动相机，摄像机，录音机
 */
public class MediaSingleActivity extends Activity
{
	private String pathTemp;
	private File fileTemp;
	private MediaItem item = new MediaItem();

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		onCreate();
	}

	private void onCreate()
	{
		onAction();
	}

	private void onCreateImage()
	{
		String dir = MediaManager.getTempDirectory();
		MediaManager.clearTempDirectory(dir);
		String name = MediaManager.getMediaName();
		pathTemp = dir + "/" + name + ".jpg";
		fileTemp = new File(pathTemp);
	}

	private void onAction()
	{
		Intent intent = new Intent();
		String action = null;
		switch (MediaManager.mediaWorkEnum)
		{
		case single_image:
			onCreateImage();
			item.setMediaType(MediaTypeEnum.image);
			action = MediaStore.ACTION_IMAGE_CAPTURE;
			intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(fileTemp));
			break;
		case single_video:
			item.setMediaType(MediaTypeEnum.video);
			action = MediaStore.ACTION_VIDEO_CAPTURE;
			intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
			break;
		case single_audio:
			item.setMediaType(MediaTypeEnum.audio);
			action = Media.RECORD_SOUND_ACTION;
			break;
		default:
			break;
		}

		item.setFile(fileTemp);
		intent.setAction(action);
		startActivityForResult(intent, 0);
	}

	@Override
	public void finish()
	{
		super.finish();

		if(item != null && item.exists())
		{
			MediaManager.mediaCallback(true, null, item);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);

		switch (MediaManager.mediaWorkEnum)
		{
		case single_image:
			if(item != null && item.exists())
			{
				MediaImage.compress(MediaManager.mediaOption, pathTemp);
			}
			break;
		case single_video:
		case single_audio:
		{

			if(data != null && data.getData() != null)
			{
				Cursor cursor = getContentResolver().query(data.getData(), null, null, null, null);
				if(cursor != null)
				{
					if(cursor.moveToNext())
					{
						String path = cursor.getString(cursor.getColumnIndex("_data"));
						item.setPath(path);
					}
				}
				else
				{
					File file = null;
					try
					{
						file = new File(new URI(data.getData().toString()));
					}
					catch (URISyntaxException e)
					{
						// TODO 自动生成的 catch 块
						e.printStackTrace();
					}
					if(file != null)
					{
						String path = file.getPath();
						item.setPath(path);
					}
				}
			}
		}
			break;
		default:
			break;
		}

		finish();
	}
}
