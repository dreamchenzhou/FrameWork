package com.umframework.collection;

import java.util.ArrayList;
import java.util.UUID;

@SuppressWarnings("serial")
public class UUIDCollection extends ArrayList<UUID>
{
	public void add(String uuid)
	{
		UUID item = UUID.fromString(uuid);
		this.add(item);
	}
}
