package com.umframework.media;

import java.io.File;
import java.util.ArrayList;

/**
 * 
 * @author martin.zheng
 * 
 */
@SuppressWarnings("serial")
public class MediaItems extends ArrayList<MediaItem>
{
	public void addItem(String path)
	{
		MediaItem item = new MediaItem(path);
		this.add(item);
	}

	public void addItem(File File)
	{
		MediaItem item = new MediaItem(File);
		this.add(item);
	}

	public void addItem(String path, MediaTypeEnum mediaType)
	{
		MediaItem item = new MediaItem(path, mediaType);
		this.add(item);
	}

	public void addItem(File File, MediaTypeEnum mediaType)
	{
		MediaItem item = new MediaItem(File, mediaType);
		this.add(item);
	}
}
