package com.umframework.media;

/**
 * 多媒体设置选项
 * 
 * @author martin.zheng
 * 
 */
public class MediaOption
{
	private int width = 240;
	private int height = 320;
	/**
	 * 保存质量
	 */
	private int quality = 90;

	/**
	 * 是否压缩图片
	 */
	private boolean isCompress = true;

	public MediaOption()
	{

	}

	public MediaOption(int width, int height, int quality)
	{
		this.width = width;
		this.height = height;
		this.quality = quality;
	}

	public int getWidth()
	{
		return width;
	}

	public void setWidth(int width)
	{
		this.width = width;
	}

	public int getHeight()
	{
		return height;
	}

	public void setHeight(int height)
	{
		this.height = height;
	}

	/**
	 * 保存质量
	 */
	public int getQuality()
	{
		return quality;
	}

	/**
	 * 保存质量
	 */
	public void setQuality(int quality)
	{
		this.quality = quality;
	}

	public boolean isCompress()
	{
		return isCompress;
	}

	public void setCompress(boolean isCompress)
	{
		this.isCompress = isCompress;
	}
}
