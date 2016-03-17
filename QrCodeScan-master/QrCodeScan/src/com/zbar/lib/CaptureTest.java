package com.zbar.lib;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.zbar.lib.ScanManager.ScanCallback;

public class CaptureTest extends Activity implements OnClickListener, ScanCallback
{
	private static final int resource = android.R.layout.simple_list_item_1;

	private CaptureTest wThis = this;
	private ListView listView;
	private Button btnScan;
	private ArrayList<String> items = new ArrayList<String>();
	private ArrayAdapter<String> adapter = null;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_qr_scan_test);
		listView = (ListView) this.findViewById(R.id.listView);
		btnScan = (Button) this.findViewById(R.id.btnScan);
		btnScan.setOnClickListener(this);

		adapter = new ArrayAdapter<String>(wThis, resource, items);
		listView.setAdapter(adapter);
	}

	@Override
	public void onClick(View v)
	{
		ScanManager.scan(wThis, wThis);
	}

	@Override
	public void onScanResult(String result)
	{
		if (!TextUtils.isEmpty(result))
		{
			if (result.startsWith("http://"))
			{
				onOpen(result);
			}
			else
			{
				Toast.makeText(wThis, result, Toast.LENGTH_SHORT).show();
			}
			items.add(0, result);
			adapter.notifyDataSetInvalidated();
		}
	}

	void onOpen(String uriString)
	{
		try
		{
			Intent intent = new Intent();
			// intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			intent.setAction("android.intent.action.VIEW");
			Uri content_url = Uri.parse(uriString);
			intent.setData(content_url);
			startActivity(intent);
			// count++;
		}
		catch (Exception e)
		{
			Toast.makeText(wThis, e.getMessage(), Toast.LENGTH_SHORT).show();
		}
	}
}