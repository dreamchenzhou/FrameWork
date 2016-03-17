package com.umframework.io;

import java.io.Serializable;

/**
 * 手机上的联系人
 * 
 * @author martin.zheng
 * 
 */
@SuppressWarnings("serial")
public class ContactItem implements Serializable
{
	private String id;
	private String name;
	private String number;
	private boolean checked;

	public ContactItem()
	{

	}

	public ContactItem(String name, String number)
	{
		this.name = name;
		this.number = number;
	}

	public ContactItem(String id, String name, String number)
	{
		this.id = id;
		this.name = name;
		this.number = number;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getNumber()
	{
		return number;
	}

	public void setNumber(String number)
	{
		this.number = number;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public boolean isChecked()
	{
		return checked;
	}

	public void setChecked(boolean checked)
	{
		this.checked = checked;
	}
}