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
 * 拍照
 * 
 * @author martin.zheng
 * 
 */
public class MediaImageActivity extends MediaBaseActivity implements OnClickListener, OnItemClickListener
{
	private MediaImageActivity wThis = this;

	private Button btnSingle, btnMulti;

	private boolean isLoaded = false;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.um_test_image);
		getContentView();
		showBar();
	}

	@Override
	public void getContentView()
	{
		super.getContentView();

		gridView = (GridView) this.findViewById(R.id.gridView);
		btnSingle = (Button) this.findViewById(R.id.btnSingle);
		btnMulti = (Button) this.findViewById(R.id.btnMulti);

		gridView.setOnItemClickListener(wThis);
		btnSingle.setOnClickListener(wThis);
		btnMulti.setOnClickListener(wThis);
	}

	@Override
	public void onResume()
	{
		super.onResume();

		if (!isLoaded)
		{
			isLoaded = true;

			gridView.postDelayed(new Runnable()
			{
				@Override
				public void run()
				{
					notifyDataSetChanged();
					hideBar();
				}
			}, 500);
		}
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
		case R.id.btnSingle:

			this.gridView.getNumColumns();

			MediaManager.singleImage(wThis, wThis);
			break;
		case R.id.btnMulti:
			MediaManager.multiCamera(wThis, wThis);
			break;
		default:
			break;
		}
	}

	@Override
	public String getRootPath()
	{
		return PathManager.getImage();
	}
}
