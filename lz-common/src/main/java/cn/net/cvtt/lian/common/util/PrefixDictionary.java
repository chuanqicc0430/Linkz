package cn.net.cvtt.lian.common.util;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

/**
 * 前缀字典类
 * 
 * key 永远是String key是唯一的 查找的时候以最大前缀匹配方式返回结果
 * 
 * @author 
 */
public class PrefixDictionary<E>
{
	/*
	 * 返回最大匹配的前缀 要求最大复杂度是O(logn)
	 */
	private Map<String, E> parameters = new TreeMap<String, E>(new Comparator<String>()
	{

		@Override
		public int compare(String paramT1, String paramT2)
		{
			if (paramT1 == paramT2 || paramT1.equals(paramT2))
			{
				return 0;
			}
			else if (paramT1.toString().compareTo(paramT2.toString()) > 0)
			{
				return -1;
			}
			else
			{
				return 1;
			}
		}
	});

	public synchronized void put(String prefix, E obj)
	{
		parameters.put(prefix, obj);
	}

	public synchronized void remove(String prefix)
	{
		if (parameters != null)
		{
			parameters.remove(prefix);
		}
	}

	public synchronized E get(String key)
	{
		for (String _key : parameters.keySet())
		{
			if (key.startsWith(_key))
			{
				return parameters.get(_key);
			}
		}
		return null;
	}

	@Override
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		for (String key : parameters.keySet())
		{
			sb.append("{");
			sb.append("key:");
			sb.append(key);
			sb.append(",value:");
			sb.append(parameters.get(key));
			sb.append("} ");
		}		
		sb.append("]");
		return sb.toString().replace(" ", "");
	}
}
