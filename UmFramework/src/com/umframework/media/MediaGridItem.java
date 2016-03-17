package com.umframework.media;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.umframework.R;
import com.umframework.view.BaseFrameLayout;

public class MediaGridItem extends BaseFrameLayout
{
	private MediaItem mediaItem;
	private ImageView imageView;
	private CheckBox checkBox;

	public MediaGridItem(Context context)
	{
		super(context);
	}

	public MediaGridItem(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	public MediaGridItem(Context context, MediaItem mediaItem)
	{
		super(context);
		this.mediaItem = mediaItem;
	}

	@Override
	public void onCreateConvertView(View convertView)
	{
		imageView = (ImageView) convertView.findViewById(R.id.imageView);
		checkBox = (CheckBox) convertView.findViewById(R.id.checkBox);

		checkBox.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				checkBox.setChecked(!checkBox.isChecked());
				if (mediaItem != null)
				{
					mediaItem.setChecked(checkBox.isChecked());
				}
				invalidate();
			}
		});
	}

	@Override
	public int getConvertViewId()
	{
		return R.layout.media_grid_item;
	}

	public void setChecked(boolean checked)
	{
		int resid = checked ? R.drawable.media_grid_item_bg_on : R.drawable.media_grid_item_bg_off;
		int padding = checked ? 2 : 0;
		setBackgroundResource(resid);
		setPadding(padding, padding, padding, padding);

		checkBox.setChecked(checked);

		if (mediaItem != null)
		{
			mediaItem.setChecked(checked);
		}

		invalidate();
	}

	public MediaItem getMediaItem()
	{
		return mediaItem;
	}

	public void setMediaItem(MediaItem mediaItem)
	{
		this.mediaItem = mediaItem;
	}

	public ImageView getImageView()
	{
		return imageView;
	}

	public CheckBox getCheckBox()
	{
		return checkBox;
	}
}
