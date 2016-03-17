package com.umframeworkdemo;

import java.io.File;
import java.util.Arrays;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ProgressBar;

import com.umframework.io.FileComparator;
import com.umframework.io.FileManager;
import com.umframework.media.MediaCallback;
import com.umframework.media.MediaGridAdapter;
import com.umframework.media.MediaItem;
import com.umframework.media.MediaItems;
import com.umframework.media.MediaTypeEnum;

/**
 * 拍照
 * 
 * @author martin.zheng
 * 
 */
public abstract class MediaBaseActivity extends Activity implements MediaCallback
{
	public MediaItems items = new MediaItems();
	public String root = getRootPath();
	public GridView gridView;
	public ProgressBar pBar;
	public MediaGridAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		onCreateData();
	}

	public void getContentView()
	{
		pBar = (ProgressBar) this.findViewById(R.id.pBar);
	}

	public void showBar()
	{
		if (pBar != null)
		{
			pBar.setVisibility(View.VISIBLE);
		}
	}

	public void hideBar()
	{
		if (pBar != null)
		{
			pBar.setVisibility(View.GONE);
		}
	}

	public void onCreateData()
	{
		File[] files = FileManager.listFiles(root);
		if (files != null && files.length > 0)
		{
			Arrays.sort(files, new FileComparator());
			for (File f : files)
			{
				items.addItem(f, MediaTypeEnum.image);
			}
		}
	}

	public void notifyDataSetChanged()
	{
		if (adapter == null)
		{
			adapter = new MediaGridAdapter(this, gridView, items);
			gridView.setAdapter(adapter);
		}
		else
		{
			adapter.notifyDataSetChanged();
		}
	}

	@Override
	public void mediaCallback(boolean isSingle, MediaItems items, MediaItem item)
	{
		if (isSingle)
		{
			moveTo(item);
		}
		else
		{
			if (items != null && items.size() > 0)
			{
				for (MediaItem item1 : items)
				{
					moveTo(item1);
				}
			}
		}

		notifyDataSetChanged();
		if (gridView.getLastVisiblePosition() > this.items.size() - 1)
		{
			gridView.postDelayed(new Runnable()
			{
				@Override
				public void run()
				{
					gridView.setSelection(0);
				}
			}, 1000);
		}
	}

	public void moveTo(MediaItem item)
	{
		if (item != null && item.exists())
		{
			String toPath = root + "/" + item.getDisplayName();
			item.moveTo(toPath);
			FileManager.delete(item.getPath());
			item.setPath(toPath);
			items.add(0, item);
		}
	}

	public abstract String getRootPath();
}
