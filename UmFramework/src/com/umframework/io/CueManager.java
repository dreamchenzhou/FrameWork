package com.umframework.io;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Vibrator;

/**
 * 播放声音或者震动手机
 * 
 * initialize初始化
 * 
 * @author martin.zheng
 * 
 */
public class CueManager
{
	private static Object lockValue = new Object();
	private static CueManager instance = null;

	private Vibrator vibrator = null; // 振动器
	private SoundPool soundPool; // 播放声音
	private int hitOkSfx;

	private CueManager()
	{

	}

	public static CueManager getInstance()
	{
		if (instance == null)
		{
			synchronized (lockValue)
			{
				if (instance == null)
				{
					instance = new CueManager();
				}
			}
		}
		return instance;
	}

	/**
	 * 初始化
	 * 
	 * @param context
	 * @param raw
	 *            声音文件
	 */
	public void initialize(Context context, int raw)
	{
		try
		{
			soundPool = new SoundPool(10, AudioManager.STREAM_SYSTEM, 5);
			hitOkSfx = soundPool.load(context, raw, 0);
			vibrator = (Vibrator) context
					.getSystemService(Context.VIBRATOR_SERVICE);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}

	/**
	 * 先播放声音再震动
	 */
	public void playAndShake()
	{
		try
		{
			if (soundPool != null)
			{
				soundPool.play(hitOkSfx, 1, 1, 0, 0, 1);
			}

			if (vibrator != null)
			{
				vibrator.vibrate(2000);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * 先震动再播放声音
	 */
	public void shakeAndPlay()
	{
		try
		{
			if (vibrator != null)
			{
				vibrator.vibrate(2000);
			}

			if (soundPool != null)
			{
				soundPool.play(hitOkSfx, 1, 1, 0, 0, 1);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * 播放声音
	 */
	public void play()
	{
		try
		{
			if (soundPool != null)
			{
				soundPool.play(hitOkSfx, 1, 1, 0, 0, 1);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * 震动
	 */
	public void shake()
	{
		try
		{
			if (vibrator != null)
			{
				vibrator.vibrate(2000);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
