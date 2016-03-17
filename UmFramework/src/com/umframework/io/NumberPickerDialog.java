package com.umframework.io;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.NumberPicker.OnValueChangeListener;

public class NumberPickerDialog extends Builder implements OnValueChangeListener
{
	private static final int WRAP_CONTENT = LinearLayout.LayoutParams.WRAP_CONTENT;

	private static final String NUMBERPICK_TAG = "numberPicker";

	private Context mContext;
	private NumberPicker numberPicker;
	private OnNumberSetListener onNumberSetListener;
	int curValue;
	int maxValue;
	int minValue;

	private NumberPickerDialog(Context context, int maxValue, int minValue, int curValue)
	{
		super(context);

		this.mContext = context;
		this.maxValue = maxValue;
		this.minValue = minValue;
		this.curValue = curValue;

		setTitle("数字选择器");
		// setIcon(android.R.drawable.ic_dialog_info);
		onCreate();
	}

	private void onCreate()
	{
		View view = getView(mContext);
		setView(view);

		numberPicker = (NumberPicker) view.findViewWithTag(NUMBERPICK_TAG);
		numberPicker.setMaxValue(maxValue);
		numberPicker.setMinValue(minValue);
		numberPicker.setValue(curValue);
		numberPicker.setOnValueChangedListener(this);

		setNegativeButton("选择", new DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				dialog.dismiss();
				if (onNumberSetListener != null)
				{
					onNumberSetListener.onNumberSet(curValue);
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
	public void onValueChange(NumberPicker picker, int oldVal, int newVal)
	{
		curValue = newVal;
	}

	private void showNumer()
	{
		super.show();
	}

	public void setOnNumberSetListener(OnNumberSetListener onNumberSetListener)
	{
		this.onNumberSetListener = onNumberSetListener;
	}

	public static interface OnNumberSetListener
	{
		void onNumberSet(int newValue);
	}

	public static void showNumer(Context context, int maxValue, int minValue, int curValue, OnNumberSetListener onNumberSetListener)
	{
		NumberPickerDialog dialog = new NumberPickerDialog(context, maxValue, minValue, curValue);
		dialog.setOnNumberSetListener(onNumberSetListener);
		dialog.showNumer();
	}

	private static LinearLayout getView(Context context)
	{
		NumberPicker numberPicker = new NumberPicker(context);
		numberPicker.setTag(NUMBERPICK_TAG);
		LinearLayout linearLayout = new LinearLayout(context);
		linearLayout.setOrientation(LinearLayout.VERTICAL);
		linearLayout.addView(numberPicker, getLayoutParams());
		linearLayout.setGravity(Gravity.CENTER);
		return linearLayout;
	}

	private static LinearLayout.LayoutParams getLayoutParams()
	{
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
		return params;
	}
}
