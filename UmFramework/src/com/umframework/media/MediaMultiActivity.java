package com.umframework.media;

import android.content.Intent;
import android.os.Bundle;

/**
 * 连续拍照或者录像
 */
public class MediaMultiActivity extends MediaMultiBaseActivity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
	}

	@Override
	public void finish()
	{
		super.finish();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);
	}
}
