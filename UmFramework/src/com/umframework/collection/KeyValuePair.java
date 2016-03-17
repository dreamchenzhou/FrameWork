package com.umframework.collection;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

@SuppressWarnings("serial")
public class KeyValuePair<TKey, TValue> implements Serializable
{
	public HashMap<TKey, TValue> items = new HashMap<TKey, TValue>();

	public void test()
	{
		Iterator<Entry<TKey, TValue>> iter = items.entrySet().iterator();
		while (iter.hasNext())
		{
			iter.next();
		}
	}
}
