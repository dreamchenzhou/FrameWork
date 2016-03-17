package com.umframework.media;

/**
 * 操作类型
 * 
 * @author martin.zheng
 * 
 */
public enum MediaWorkEnum
{
	/**
	 * 未知
	 */
	nothing,
	/**
	 * 独立拍照
	 */
	single_image,
	/**
	 * 独立录像
	 */
	single_video,
	/**
	 * 独立录音
	 */
	single_audio,
	/**
	 * 连续拍照或者连续录像，可跳转
	 */
	multi_camera,
}
