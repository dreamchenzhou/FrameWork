package com.umframework.media;

import java.util.Comparator;

/**
 * 反序
 * 
 * @author martin.zheng
 * 
 */
public class MediaComparator implements Comparator<MediaItem>
{
	@Override
	public int compare(MediaItem object1, MediaItem object2)
	{
		Long time1 = object1.lastModified();
		Long time2 = object2.lastModified();

		if (time1 < time2)
		{
			return 1;
		}
		else if (time1 > time2)
		{
			return -1;
		}
		return 0;
	}
}
