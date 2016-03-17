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
 * 录音
 * 
 * @author martin.zheng
 * 
 */
public class MediaAudioActivity extends MediaBaseActivity implements OnClickListener, OnItemClickListener
{
	private MediaAudioActivity wThis = this;
	private Button btnAudio;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.um_test_audio);
		getContentView();
	}

	@Override
	public void getContentView()
	{
		gridView = (GridView) this.findViewById(R.id.gridView);
		btnAudio = (Button) this.findViewById(R.id.btnAudio);

		gridView.setOnItemClickListener(wThis);
		btnAudio.setOnClickListener(wThis);
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
		case R.id.btnAudio:
			MediaManager.singleAudio(wThis, wThis);
			break;
		default:
			break;
		}
	}

	@Override
	public String getRootPath()
	{
		return PathManager.getAudio();
	}
}
