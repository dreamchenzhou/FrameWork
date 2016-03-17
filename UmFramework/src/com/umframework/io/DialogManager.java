package com.umframework.io;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

/**
 * 
 * @author martin.zheng
 * 
 */
public class DialogManager
{
	public static Context mContext;
	private static Builder dialog;
	public static String title = "提示";

	private DialogManager()
	{

	}

	static DialogInterface.OnClickListener oListener = new DialogInterface.OnClickListener()
	{
		@Override
		public void onClick(DialogInterface dialog, int which)
		{
			dialog.dismiss();
		};
	};

	static DialogInterface.OnCancelListener onCancelListener = new DialogInterface.OnCancelListener()
	{
		@Override
		public void onCancel(DialogInterface dialog)
		{
			dialog.dismiss();
		}
	};

	public static void makeTextSHORT(Context context, String text)
	{
		Context temp = mContext != null ? mContext : context;
		makeText(temp, text, Toast.LENGTH_SHORT);
	}

	public static void makeTextLONG(Context context, String text)
	{
		Context temp = mContext != null ? mContext : context;
		makeText(temp, text, Toast.LENGTH_LONG);
	}

	public static void makeText(Context context, String text, int duration)
	{
		Context temp = mContext != null ? mContext : context;
		Toast.makeText(temp, text, duration).show();
	}

	public static void show(Context context, Throwable throwable)
	{
		if (context != null && throwable != null)
		{
			String text = throwable.getMessage();
			show(context, text, title, null);
		}
	}

	public static void show(Context context, String text)
	{
		show(context, text, title, null);
	}

	public static void show(Context context, String text,
			DialogInterface.OnClickListener listener)
	{
		show(context, text, title, listener);
	}

	public static void show(Context context, String text, String title,
			DialogInterface.OnClickListener listener)
	{
		Context temp = mContext != null ? mContext : context;

		if (temp != null)
		{
			dialog = new Builder(temp);
			dialog.setTitle(title);
			dialog.setMessage(text);
			if (listener == null)
			{
				listener = oListener;
			}
			dialog.setPositiveButton("确定", listener);
			dialog.show();
		}
	}

	public static void askYesNo(Context context, String text,
			DialogInterface.OnClickListener listenerYes,
			DialogInterface.OnClickListener listenerNo)
	{
		askYesNo(context, text, title, listenerYes, listenerNo);
	}

	public static void askYesNo(Context context, String text, String title,
			DialogInterface.OnClickListener listenerYes,
			DialogInterface.OnClickListener listenerNo)
	{
		Context temp = mContext != null ? mContext : context;

		if (temp != null)
		{
			dialog = new Builder(temp);
			dialog.setTitle(title);
			dialog.setMessage(text);

			if (listenerNo == null)
			{
				listenerNo = oListener;
			}

			if (listenerYes == null)
			{
				listenerYes = oListener;
			}

			dialog.setPositiveButton("是", listenerYes);
			dialog.setNeutralButton("否", listenerNo);
			dialog.show();
		}
	}

	public static void askYesNoCancel(Context context, String text,
			DialogInterface.OnClickListener listenerYes,
			DialogInterface.OnClickListener listenerNo,
			DialogInterface.OnClickListener listenerCancel)
	{
		askYesNoCancel(context, text, title, listenerYes, listenerNo,
				listenerCancel);
	}

	public static void askYesNoCancel(Context context, String text,
			String title, DialogInterface.OnClickListener listenerYes,
			DialogInterface.OnClickListener listenerNo,
			DialogInterface.OnClickListener listenerCancel)
	{
		Context temp = mContext != null ? mContext : context;

		if (temp != null)
		{
			dialog = new Builder(temp);
			dialog.setTitle(title);
			dialog.setMessage(text);

			if (listenerYes == null)
			{
				listenerYes = oListener;
			}

			if (listenerNo == null)
			{
				listenerNo = oListener;
			}

			if (listenerCancel == null)
			{
				listenerCancel = oListener;
			}

			dialog.setPositiveButton("是", listenerYes);
			dialog.setNeutralButton("否", listenerNo);
			dialog.setNegativeButton("取消", listenerCancel);
			dialog.show();
		}
	}

	public static void askOKCancel(Context context, String text,
			DialogInterface.OnClickListener listenerYes,
			DialogInterface.OnClickListener listenerCancel)
	{
		askOKCancel(context, text, title, listenerYes, listenerCancel);
	}

	public static void askOKCancel(Context context, String text, String title,
			DialogInterface.OnClickListener listenerYes,
			DialogInterface.OnClickListener listenerCancel)
	{
		Context temp = mContext != null ? mContext : context;

		if (temp != null)
		{
			dialog = new Builder(temp);
			dialog.setTitle(title);
			dialog.setMessage(text);

			if (listenerYes == null)
			{
				listenerYes = oListener;
			}
			if (listenerCancel == null)
			{
				listenerCancel = oListener;
			}

			dialog.setPositiveButton("确定", listenerYes);
			dialog.setNeutralButton("取消", listenerCancel);
			dialog.show();
		}
	}

	public static void askRetryCancel(Context context, String text,
			DialogInterface.OnClickListener listenerRetry,
			DialogInterface.OnClickListener listenerCancel)
	{
		askRetryCancel(context, text, title, listenerRetry, listenerCancel);
	}

	public static void askRetryCancel(Context context, String text,
			String title, DialogInterface.OnClickListener listenerRetry,
			DialogInterface.OnClickListener listenerCancel)
	{
		Context temp = mContext != null ? mContext : context;

		if (temp != null)
		{
			dialog = new Builder(temp);
			dialog.setTitle(title);
			dialog.setMessage(text);

			if (listenerRetry == null)
			{
				listenerRetry = oListener;
			}
			if (listenerCancel == null)
			{
				listenerCancel = oListener;
			}

			dialog.setPositiveButton("重试", listenerRetry);
			dialog.setNeutralButton("取消", listenerCancel);
			dialog.show();
		}
	}
}