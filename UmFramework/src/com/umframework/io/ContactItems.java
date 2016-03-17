package com.umframework.io;

import java.util.ArrayList;

/**
 * 手机上的联系人
 * 
 * @author martin.zheng
 * 
 */
@SuppressWarnings("serial")
public class ContactItems extends ArrayList<ContactItem>
{
	public void add(String id, String name, String number)
	{
		ContactItem item = new ContactItem(id, name, number);
		this.add(item);
	}

	public void add(String name, String number)
	{
		ContactItem item = new ContactItem(name, number);
		this.add(item);
	}

	public ContactItem[] toArray()
	{
		ContactItem[] contents = new ContactItem[this.size()];
		this.toArray(contents);
		return contents;
	}
}
