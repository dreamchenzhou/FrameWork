package com.umframeworkdemo;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.umframework.calendar.DateManager;
import com.umframework.location.UmLocation;
import com.umframework.location.UmLocationItems;
import com.umframework.location.UmLocationManager;
import com.umframework.location.UmLocationRegion;

@SuppressLint("HandlerLeak")
public class LocationActivity extends Activity implements OnClickListener, OnCheckedChangeListener
{
	private LocationActivity wThis = this;
	private ToggleButton btnStart, btnAuto;
	private Button btnGet, btnClear;
	private ListView listView;
	private RadioGroup mRadioGroup;
	private UmLocationAdapter adapter;
	/**
	 * 0内置，1百度，2腾讯
	 */
	private int mUmLocationDeviceType = 0;
	private UmLocationItems items = new UmLocationItems();
	private Timer locationTimer;

	private boolean isStart = false;
	private boolean isAuto = false;

	// private UmLocationDeviceBaidu mUmLocationDeviceBaidu;
	// private UmLocationDeviceTencent mUmLocationDeviceTencent;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.um_test_location);
		onCreate();
	}

	private void onCreate()
	{
		UmLocationManager.setContext(wThis);
		UmLocationManager.setUmLocationRegion(UmLocationRegion.ShenZhen);

		initView();
		initAdapter();

		// UmLocationManager.getInstance().setContext(wThis, 0,
		// UmLocationRegion.ShenZhen);

		// mUmLocationDeviceBaidu = new UmLocationDeviceBaidu();
		// mUmLocationDeviceTencent = new UmLocationDeviceTencent();
		//
		// mUmLocationDeviceBaidu.setContext(wThis);
		// mUmLocationDeviceTencent.setContext(wThis);
	}

	private void initView()
	{
		mRadioGroup = (RadioGroup) wThis.findViewById(R.id.rbBody);
		mRadioGroup.setOnCheckedChangeListener(this);

		btnStart = (ToggleButton) wThis.findViewById(R.id.btnStart);
		btnAuto = (ToggleButton) wThis.findViewById(R.id.btnAuto);
		btnGet = (Button) wThis.findViewById(R.id.btnGet);
		btnClear = (Button) wThis.findViewById(R.id.btnClear);

		btnStart.setOnClickListener(wThis);
		btnAuto.setOnClickListener(wThis);
		btnGet.setOnClickListener(wThis);
		btnClear.setOnClickListener(wThis);

		listView = (ListView) wThis.findViewById(R.id.listView);

		mRadioGroup.check(R.id.rbInner);
	}

	private void initAdapter()
	{
		notifyDataSetChanged();
	}

	private void notifyDataSetChanged()
	{
		if (adapter == null)
		{
			adapter = new UmLocationAdapter(wThis, items);
			listView.setAdapter(adapter);
		}
		else
		{
			adapter.notifyDataSetChanged();
		}
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId)
	{
		switch (checkedId)
		{
			case R.id.rbInner:
				mUmLocationDeviceType = 0;
				break;
			case R.id.rbBaidu:
				mUmLocationDeviceType = 1;
				break;
			case R.id.rbTencent:
				mUmLocationDeviceType = 2;
				break;
			default:
				break;
		}

		UmLocationManager.setUmLocationDeviceType(mUmLocationDeviceType);
	}

	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
			case R.id.btnStart:
				if (isStart)
				{
					isStart = false;
					stopLocation();
				}
				else
				{
					isStart = true;
					startLocation();
				}
				break;
			case R.id.btnAuto:
				isAuto = !isAuto;
				break;
			case R.id.btnGet:
				getLocation();
				break;
			case R.id.btnClear:
				clearLocation();
				break;
			default:
				break;
		}
	}

	private void startLocation()
	{
		UmLocationManager.startLocation();
		startTimer();

		// mUmLocationDeviceBaidu.startLocation();
		// mUmLocationDeviceTencent.startLocation();
	}

	private void stopLocation()
	{
		stopTimer();
		UmLocationManager.stopLocation();

		// mUmLocationDeviceBaidu.stopLocation();
		// mUmLocationDeviceTencent.stopLocation();
	}

	private void clearLocation()
	{
		items.clear();

		// UmLocation umLocation = null;
		// umLocation = mUmLocationDeviceBaidu.getUmLocation();
		// if (umLocation != null)
		// {
		// items.add(0, umLocation);
		// }
		// umLocation = mUmLocationDeviceTencent.getUmLocation();
		// if (umLocation != null)
		// {
		// items.add(0, umLocation);
		// }
		//
		notifyDataSetChanged();
	}

	private void startTimer()
	{
		stopTimer();

		long delay = 2000;
		long period = 3000;

		locationTimer = new Timer();
		locationTimer.schedule(new TimerTask()
		{
			@Override
			public void run()
			{
				if (isAuto)
				{
					getLocation();
				}
			}
		}, delay, period);
	}

	private void getLocation()
	{
		UmLocation umLocation = UmLocationManager.getUmLocation();
		if (umLocation != null)
		{
			items.add(0, umLocation);
			locationHandler.sendEmptyMessage(0);
		}
	}

	private void stopTimer()
	{
		if (locationTimer != null)
		{
			locationTimer.cancel();
			locationTimer = null;
		}
	}

	private Handler locationHandler = new Handler()
	{
		@Override
		public void handleMessage(Message msg)
		{
			super.handleMessage(msg);
			notifyDataSetChanged();
		}
	};

	@Override
	public void finish()
	{
		stopTimer();
		stopLocation();
		super.finish();
	}
}

class UmLocationAdapter extends ArrayAdapter<UmLocation>
{
	private static final int textViewResourceId = R.layout.um_test_location_item;
	private LayoutInflater mInflater;

	public UmLocationAdapter(Context context, List<UmLocation> objects)
	{
		super(context, textViewResourceId, objects);
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		ViewHolder holder = null;
		if (convertView == null)
		{
			holder = new ViewHolder();
			convertView = holder.getView();
			convertView.setTag(holder);
		}
		else
		{
			holder = (ViewHolder) convertView.getTag();
		}

		UmLocation item = getItem(position);
		StringBuffer sb = new StringBuffer();

		sb.append("经度:");
		sb.append(item.getLongitude());
		sb.append("\r\n");
		sb.append("纬度:");
		sb.append(item.getLatitude());
		sb.append("\r\n");
		sb.append("时间:");
		sb.append(DateManager.toString(item.getTime()));
		sb.append("\r\n");
		sb.append("地址:");
		sb.append(item.getAddress());

		String string = sb.toString();
		holder.txtLocation.setText(string);

		return convertView;
	}

	private class ViewHolder
	{
		TextView txtLocation;

		public View getView()
		{
			View convertView = mInflater.inflate(textViewResourceId, null);

			txtLocation = (TextView) convertView.findViewById(R.id.txtLocation);
			return convertView;
		}
	}
}
