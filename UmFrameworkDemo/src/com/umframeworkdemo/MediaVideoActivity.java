package com.umframeworkdemo;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;

import com.umframework.media.MediaManager;
import com.umframeworkdemo.comm.PathManager;

/**
 * 录像
 * 
 * @author martin.zheng
 * 
 */
public class MediaVideoActivity extends MediaBaseActivity implements OnClickListener, OnItemClickListener
{
	private MediaVideoActivity wThis = this;
	private Button btnVideo;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.um_test_video);
		getContentView();
	}

	@Override
	public void getContentView()
	{
		gridView = (GridView) this.findViewById(R.id.gridView);
		btnVideo = (Button) this.findViewById(R.id.btnVideo);

		gridView.setOnItemClickListener(wThis);
		btnVideo.setOnClickListener(wThis);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id)
	{

	}

	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
		case R.id.btnVideo:
			MediaManager.singleVideo(wThis, wThis);
			break;
		default:
			break;
		}
	}

	@Override
	public String getRootPath()
	{
		return PathManager.getVideo();
	}
}
