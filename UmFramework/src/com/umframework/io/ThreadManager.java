package com.umframework.io;

/**
 * 
 * @author martin.zheng
 * 
 */
public class ThreadManager
{
	private ThreadManager()
	{

	}

	public static Thread start(Runnable runnable)
	{
		return start(runnable, true);
	}

	/**
	 * @param runnable
	 * @param isStarted
	 *            是否马上开始
	 * @return
	 */
	public static Thread start(Runnable runnable, boolean isStarted)
	{
		Thread thread = new Thread(runnable);
		if (isStarted)
		{
			thread.start();
		}
		return thread;
	}

	public static void sleep(long time)
	{
		try
		{
			Thread.sleep(time);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
