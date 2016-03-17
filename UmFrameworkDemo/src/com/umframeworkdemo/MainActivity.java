package com.umframeworkdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.umframework.collection.StringCollection;
import com.umframework.media.UmImageLoader;

public class MainActivity extends Activity implements OnItemClickListener
{
	private MainActivity wThis = this;

	private ListView listView;
	private StringCollection items = new StringCollection();

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.um_test_main);
		onCreate();

		UmImageLoader.init(wThis);
	}

	private void onCreate()
	{
		initView();
		initData();
		initAdapter();
	}

	private void initView()
	{
		listView = (ListView) this.findViewById(R.id.listView);
		listView.setOnItemClickListener(this);
	}

	private void initData()
	{
		items.add("gps定位");
		items.add("拍照");
		items.add("摄像");
		items.add("录音");
	}

	private void initAdapter()
	{
		int textViewResourceId = android.R.layout.simple_list_item_1;
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(wThis, textViewResourceId, items);
		listView.setAdapter(adapter);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id)
	{
		Intent intent = new Intent();

		switch (position)
		{
		case 0:
			intent.setClass(wThis, LocationActivity.class);
			break;
		case 1:
			intent.setClass(wThis, MediaImageActivity.class);
			break;
		case 2:
			intent.setClass(wThis, MediaVideoActivity.class);
			break;
		case 3:
			intent.setClass(wThis, MediaAudioActivity.class);
			break;
		default:
			break;
		}
		if (intent.getComponent() != null)
		{
			startActivity(intent);
		}
	}
}
