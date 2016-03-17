package com.umframework.media;

import java.util.List;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.umframework.R;
import com.umframework.io.SystemManager;

@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
public class MediaGridAdapter extends ArrayAdapter<MediaItem>
{
	public static final int textViewResourceId = R.layout.media_grid_item;
	private Activity mActivity;
	private GridView mGridView;
	private int widthPixels = 0;
	private int numColumns = 0;

	public MediaGridAdapter(Activity activity, GridView gridView, List<MediaItem> objects)
	{
		super(activity, textViewResourceId, objects);
		this.mActivity = activity;
		this.mGridView = gridView;
		this.numColumns = this.mGridView.getNumColumns();
		DisplayMetrics dm = SystemManager.getDisplayMetrics(mActivity);

		widthPixels = dm.widthPixels < dm.heightPixels ? dm.widthPixels : dm.heightPixels;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		MediaGridItem mediaGridItem = null;
		MediaItem mediaItem = getItem(position);
		if (convertView == null)
		{
			mediaGridItem = new MediaGridItem(mActivity, mediaItem);
			convertView = mediaGridItem;
		}
		else
		{
			mediaGridItem = (MediaGridItem) convertView;
		}

		String fileUri = mediaItem.getFileUri();
		ImageView imageView = mediaGridItem.getImageView();

		UmImageLoader.displayImage(fileUri, imageView, false, false);

		if (convertView.getLayoutParams() == null)
		{
			convertView.setLayoutParams(getLayoutParams());
		}

		return convertView;
	}

	private GridView.LayoutParams getLayoutParams()
	{
		int height = widthPixels / numColumns;

		int padding = mGridView.getPaddingLeft();
		height -= (padding * 2 + padding * (numColumns - 1));

		GridView.LayoutParams params = new GridView.LayoutParams(GridView.LayoutParams.MATCH_PARENT, height);
		return params;
	}
}
