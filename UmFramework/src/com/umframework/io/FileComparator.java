package com.umframework.io;

import java.io.File;
import java.util.Comparator;

/**
 * 反序
 * 
 * @author martin.zheng
 * 
 */
public class FileComparator implements Comparator<File>
{
	@Override
	public int compare(File object1, File object2)
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
