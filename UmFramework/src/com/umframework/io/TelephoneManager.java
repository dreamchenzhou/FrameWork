package com.umframework.io;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.text.TextUtils;
import android.widget.Toast;

/**
 * 
 * @author martin.zheng
 * 
 */
public class TelephoneManager
{
	private static final String[] PHONES_PROJECTION = new String[]
	{ Phone._ID, Phone.DISPLAY_NAME, Phone.NUMBER };

	private TelephoneManager()
	{

	}

	/**
	 * 直接拨打电话
	 * 
	 * @param context
	 * @param tel
	 */
	public static void onCall(Context context, String tel)
	{
		try
		{
			Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel://" + tel));
			context.startActivity(intent);
		}
		catch (Exception e)
		{
			Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
		}
	}

	/**
	 * 调出拨号界面
	 * 
	 * @param context
	 * @param tel
	 */
	public static void onDial(Context context, String tel)
	{
		try
		{
			Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel://" + tel));
			context.startActivity(intent);
		}
		catch (Exception e)
		{
			Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
		}
	}

	/**
	 * 存储在手机上的联系人
	 */
	public static ContactItems getPhoneContacts(Context mContext)
	{
		Uri uri = Phone.CONTENT_URI;
		return getContacts(mContext, uri);
	}

	/**
	 * 存储在手机卡上的联系人
	 */
	// public static ContactList getSimContacts(Context mContext)
	// {
	// Uri uri = Uri.parse("content://icc/adn");
	// return getContacts(mContext, uri);
	// }

	private static ContactItems getContacts(Context mContext, Uri uri)
	{
		ContactItems objects = new ContactItems();

		ContentResolver resolver = mContext.getContentResolver();
		Cursor phoneCursor = resolver.query(uri, PHONES_PROJECTION, null, null, null);

		if (phoneCursor != null)
		{
			while (phoneCursor.moveToNext())
			{
				String id = phoneCursor.getString(0);

				// 得到手机号码
				String number = phoneCursor.getString(1);
				// 当手机号码为空的或者为空字段 跳过当前循环
				if (TextUtils.isEmpty(number))
					continue;

				// 得到联系人名称
				String name = phoneCursor.getString(2);

				objects.add(id, name, number);
			}
			phoneCursor.close();
		}
		return objects;
	}
}
