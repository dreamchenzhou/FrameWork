package com.umframework.calendar;

import java.util.Calendar;

import android.annotation.TargetApi;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.LinearLayout;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class DateTimerPickerDialog extends Builder implements OnDateChangedListener, OnTimeChangedListener
{
	private static final int WRAP_CONTENT = LinearLayout.LayoutParams.WRAP_CONTENT;

	private static final String DATEPICKER_TAG = "datepicker";
	private static final String TIMEPICKER_TAG = "timepicker";
	private static final String DATETIME_TITLE = "日期时间选择器";
	private static final String DATE_TITLE = "日期选择器";
	private static final String TIME_TITLE = "时间选择器";

	private Context mContext;
	private DatePicker datePicker;
	private TimePicker timePicker;

	private Calendar calendar = Calendar.getInstance();
	private OnDateTimeSetListener onDateTimeSetListener;
	private OnDateSetListener onDateSetListener;
	private OnTimeSetListener onTimeSetListener;
	private int year, monthOfYear, dayOfMonth, hourOfDay, minute;

	private DateTimerPickerDialog(Context context, Calendar calendar)
	{
		super(context);

		this.mContext = context;

		setTitle("日期时间选择器");
		// setIcon(android.R.drawable.ic_dialog_info);
		if (calendar != null)
		{
			this.calendar = calendar;
		}
		onCreate();
	}

	private void onCreate()
	{
		View view = getView(mContext);
		setView(view);

		datePicker = (DatePicker) view.findViewWithTag(DATEPICKER_TAG);
		timePicker = (TimePicker) view.findViewWithTag(TIMEPICKER_TAG);

		year = calendar.get(Calendar.YEAR);
		monthOfYear = calendar.get(Calendar.MONTH);
		dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
		hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
		minute = calendar.get(Calendar.MINUTE);
		timePicker.setCurrentMinute(minute);
		timePicker.setCurrentHour(hourOfDay);
		datePicker.init(year, monthOfYear, dayOfMonth, this);
		timePicker.setOnTimeChangedListener(this);

		setNegativeButton("选择", new DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				dialog.dismiss();

				calendar.set(year, monthOfYear, dayOfMonth, hourOfDay, minute);

				Calendar mCalendar = (Calendar) calendar.clone();

				if (onDateTimeSetListener != null)
				{
					onDateTimeSetListener.onDateTimeSet(mCalendar, year, monthOfYear, dayOfMonth, hourOfDay, minute);
				}
				if (onDateSetListener != null)
				{
					onDateSetListener.onDateSet(mCalendar, year, monthOfYear, dayOfMonth);
				}
				if (onTimeSetListener != null)
				{
					onTimeSetListener.onTimeSet(mCalendar, hourOfDay, minute);
				}
			}
		});
		setNeutralButton("取消", new DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				dialog.dismiss();
			}
		});
	}

	@Override
	public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth)
	{
		this.year = year;
		this.monthOfYear = monthOfYear;
		this.dayOfMonth = dayOfMonth;
	}

	@Override
	public void onTimeChanged(TimePicker view, int hourOfDay, int minute)
	{
		this.hourOfDay = hourOfDay;
		this.minute = minute;
	}

	private void showDateTime()
	{
		showPicker(DateTimeEnum.ALL);
		super.show();
	}

	private void showDate()
	{
		showPicker(DateTimeEnum.DATE);
		super.show();
	}

	private void showTime()
	{
		showPicker(DateTimeEnum.TIME);
		super.show();
	}

	private void showPicker(DateTimeEnum dateTimeEnum)
	{
		switch (dateTimeEnum)
		{
			case ALL:
				datePicker.setVisibility(View.VISIBLE);
				timePicker.setVisibility(View.VISIBLE);
				setTitle(DATETIME_TITLE);
				break;
			case DATE:
				datePicker.setVisibility(View.VISIBLE);
				timePicker.setVisibility(View.GONE);
				setTitle(DATE_TITLE);
				break;
			case TIME:
				datePicker.setVisibility(View.GONE);
				timePicker.setVisibility(View.VISIBLE);
				setTitle(TIME_TITLE);
				break;
			default:
				break;
		}
	}

	public static interface OnDateTimeSetListener
	{
		public void onDateTimeSet(Calendar calendar, int year, int month, int day, int hourOfDay, int minute);
	}

	public static interface OnDateSetListener
	{
		public void onDateSet(Calendar calendar, int year, int month, int day);
	}

	public static interface OnTimeSetListener
	{
		void onTimeSet(Calendar calendar, int hourOfDay, int minute);
	}

	private static enum DateTimeEnum
	{
		ALL, DATE, TIME
	}

	private static LinearLayout getView(Context context)
	{
		DatePicker datePicker = new DatePicker(context);
		datePicker.setTag(DATEPICKER_TAG);

		TimePicker timePicker = new TimePicker(context);
		timePicker.setTag(TIMEPICKER_TAG);

		datePicker.setCalendarViewShown(false);// 不显示日历
		timePicker.setIs24HourView(true);// 24小时制

		datePicker.setVisibility(View.GONE);
		timePicker.setVisibility(View.GONE);

		LinearLayout linearLayout = new LinearLayout(context);
		linearLayout.setOrientation(LinearLayout.VERTICAL);
		linearLayout.addView(datePicker, getLayoutParams());
		linearLayout.addView(timePicker, getLayoutParams());
		linearLayout.setGravity(Gravity.CENTER);
		return linearLayout;
	}

	private static LinearLayout.LayoutParams getLayoutParams()
	{
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
		return params;
	}

	private void setOnDateTimeSetListener(OnDateTimeSetListener onDateTimeSetListener)
	{
		this.onDateTimeSetListener = onDateTimeSetListener;
	}

	private void setOnDateSetListener(OnDateSetListener onDateSetListener)
	{
		this.onDateSetListener = onDateSetListener;
	}

	private void setOnTimeSetListener(OnTimeSetListener onTimeSetListener)
	{
		this.onTimeSetListener = onTimeSetListener;
	}

	public static void showDateTime(Context context, Calendar calendar, OnDateTimeSetListener onDateTimeSetListener)
	{
		DateTimerPickerDialog dialog = new DateTimerPickerDialog(context, calendar);
		dialog.setOnDateTimeSetListener(onDateTimeSetListener);
		dialog.showDateTime();
	}

	public static void showDate(Context context, Calendar calendar, OnDateSetListener onDateSetListener)
	{
		DateTimerPickerDialog dialog = new DateTimerPickerDialog(context, calendar);
		dialog.setOnDateSetListener(onDateSetListener);
		dialog.showDate();
	}

	public static void showTime(Context context, Calendar calendar, OnTimeSetListener onTimeSetListener)
	{
		DateTimerPickerDialog dialog = new DateTimerPickerDialog(context, calendar);
		dialog.setOnTimeSetListener(onTimeSetListener);
		dialog.showTime();
	}
}
